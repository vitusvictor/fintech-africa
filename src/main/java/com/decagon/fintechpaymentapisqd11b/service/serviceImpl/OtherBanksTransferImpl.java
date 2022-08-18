package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.InsufficientAmountException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.InvalidAmountException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.InvalidPinException;
import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.decagon.fintechpaymentapisqd11b.entities.Transfer;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.TransactionType;
import com.decagon.fintechpaymentapisqd11b.repository.TransferRepository;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.request.FlwAccountRequest;
import com.decagon.fintechpaymentapisqd11b.request.OtherBankTransferRequest;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.request.VerifyTransferRequest;
import com.decagon.fintechpaymentapisqd11b.response.FlwAccountResponse;
import com.decagon.fintechpaymentapisqd11b.response.FlwBankResponse;
import com.decagon.fintechpaymentapisqd11b.response.OtherBankTransferResponse;
import com.decagon.fintechpaymentapisqd11b.service.TransferService;
import com.decagon.fintechpaymentapisqd11b.util.Constant;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.userdetails.User;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OtherBanksTransferImpl implements TransferService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<FlwBank> getBanks() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwBankResponse> request = new HttpEntity<>(null, headers);

        FlwBankResponse flwBankResponse = restTemplate.exchange(
                Constant.GET_BANKS_API + "/NG",
                HttpMethod.GET,
                request,
                FlwBankResponse.class
        ).getBody();

        return flwBankResponse.getData();
    }

    // this wasn't used anywhere
    // and it doesn't call any method
    @Override
    public FlwAccountResponse resolveAccount(FlwAccountRequest flwAccountRequest) {
        // flwAccountRequest contains the name and bank
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwAccountRequest> request = new HttpEntity<>(flwAccountRequest, headers);

        return restTemplate.exchange(
                Constant.RESOLVE_ACCOUNT_API,
                HttpMethod.POST,
                request,
                FlwAccountResponse.class).getBody();
    }

    @Override
    public void initiateOtherBankTransfer(TransferRequest transferRequest) {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Users user = retrieveUserDetails(user1.getUsername());

        if (!validatePin(transferRequest.getPin(), user)) {
            throw new InvalidPinException("Pin error");
        }
        if (!validateRequestBalance(transferRequest.getAmount())) {
            throw new InvalidAmountException("Amount error");
        }
        if (!validateWalletBalance(transferRequest.getAmount(), user)) {
           throw new InsufficientAmountException("Balance error");
        }

        Transfer transfer = saveTransaction(user, transferRequest);
        OtherBankTransferResponse response = otherBankTransfer(transferRequest, transfer.getClientRef());
        transfer.setFlwRef(response.getData().getId());
        transferRepository.save(transfer);
    }

    private OtherBankTransferResponse otherBankTransfer(TransferRequest transferRequest, String clientRef) {
        OtherBankTransferRequest otherBankTransferRequest = OtherBankTransferRequest.builder()
//                .accountBank(transferRequest.getBankCode())
                .accountNumber(transferRequest.getAccountNumber())
                .amount(transferRequest.getAmount())
                .currency("NGN")
                .narration(transferRequest.getNarration())
                .reference(clientRef)
                .debitCurrency("NGN")
                .callbackUrl(Constant.VERIFY_TRANSFER)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<OtherBankTransferRequest> request = new HttpEntity<>(otherBankTransferRequest, headers);

        return restTemplate.exchange(
                Constant.OTHER_BANK_TRANSFER,
                HttpMethod.POST,
                request,
                OtherBankTransferResponse.class).getBody();
    }

    private Transfer saveTransaction(Users user, TransferRequest transferRequest) {
        String clientReference = UUID.randomUUID().toString();
        Wallet wallet = walletRepository.findWalletByUsers(user);

        BigDecimal amount = transferRequest.getAmount();
        BigDecimal balance = wallet.getBalance().subtract(amount);
        wallet.setBalance(balance);

        // bank may have to be enum. so as to get their code

        Transfer transfer = new Transfer();
                transfer.setSourceAccount(transferRequest.getAccountName());
                transfer.setAmount(transferRequest.getAmount());
                transfer.setClientRef(clientReference);
                transfer.setNarration(transferRequest.getNarration());
                transfer.setTransferStatus(Constant.STATUS);
                transfer.setDestinationAccountNumber(transferRequest.getAccountNumber());
//                transfer.setDestinationBank(transferRequest.getBankCode());
                transfer.setTransactionType(TransactionType.DEBIT);
                transfer.setCreatedAt(LocalDateTime.now());

        walletRepository.save(wallet);
        return transferRepository.save(transfer);
    }

    private Users retrieveUserDetails(String username) {
        return usersRepository.findUsersByEmail(username);
    }

    private boolean validatePin(String pin, Users user) {
        return passwordEncoder.matches(pin, user.getPin()); // confirm that password encoder is autowired. encode password
    }

    private boolean validateRequestBalance(BigDecimal requestAmount) {
        int result = requestAmount.compareTo(BigDecimal.valueOf(0));
        return result > 0;
    }

    private boolean validateWalletBalance(BigDecimal requestAmount, Users user) {
        Wallet wallet = walletRepository.findWalletByUsers(user);
        int result = wallet.getBalance().compareTo(requestAmount);
        if (result == 0 || result == 1) return true;
        return false;
    }

    @Override
    public void verifyTransfer(VerifyTransferRequest verifyTransferRequest){
        Long flwRef = Long.valueOf(verifyTransferRequest.getData().getId());
        String status = verifyTransferRequest.getData().getStatus();

        Transfer transfer = transferRepository.findTransfersByFlwRef(flwRef);
        transfer.setTransferStatus(status);
        transferRepository.save(transfer);
    }


}
