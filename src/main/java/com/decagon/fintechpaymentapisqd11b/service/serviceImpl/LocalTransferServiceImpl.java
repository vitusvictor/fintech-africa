package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.AccountDoesNotExistException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.IncorrectPinException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.InsufficientBalanceException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.InvalidAmountException;
import com.decagon.fintechpaymentapisqd11b.entities.Transaction;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.TransactionType;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.TransactionRepository;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.request.TransferRequest;
import com.decagon.fintechpaymentapisqd11b.service.LocalTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalTransferServiceImpl implements LocalTransferService {

    private final WalletRepository walletRepository;

    private final UsersRepository usersRepository;

    private final TransactionRepository transactionRepository;

    private final BCryptPasswordEncoder encoder;

    @Override
    public String localTransfer(TransferRequest transferRequest) {

        UUID uuid = UUID.randomUUID();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Users user1 = usersRepository.findUsersByEmail(user.getUsername());

        Wallet wallet = walletRepository.findWalletByUsers(user1);

        if(!encoder.matches(transferRequest.getPin(), encoder.encode(wallet.getUsers().getPin()))){
            throw new IncorrectPinException("Pin is incorrect");
        }

        Wallet wallet1 = walletRepository.findWalletByAccountNumber(transferRequest.getAccountNumber());
        if(wallet1 == null) {
            throw new AccountDoesNotExistException("Account number does not exist!");
        }

        if(transferRequest.getAmount().compareTo(BigDecimal.ZERO) == 0 ||
                transferRequest.getAmount().compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidAmountException("Invalid amount!");
        }

        if (wallet.getBalance().compareTo(transferRequest.getAmount()) < 0){
            throw new InsufficientBalanceException("Insufficient funds. Please check your account!");
        }

        BigDecimal newBalance = wallet.getBalance().subtract(transferRequest.getAmount());
        wallet.setBalance(newBalance);
        Wallet debitedWallet = walletRepository.save(wallet);

        BigDecimal creditBalance = wallet1.getBalance().add(transferRequest.getAmount());
        wallet1.setBalance(creditBalance);
        Wallet creditedWallet = walletRepository.save(wallet1);

        Transaction sender = Transaction.builder()
                .clientRef(uuid.toString())
                .userStatus(UsersStatus.ACTIVE)
                .flwRef(0L)
                .narration(transferRequest.getNarration())
                .amount(transferRequest.getAmount())
                .senderAccountNumber(wallet.getAccountNumber())
                .senderFullName(wallet.getUsers().getFirstName() + " " + wallet.getUsers().getLastName())
                .senderBankName(wallet.getBankName())
                .destinationAccountNumber(wallet1.getAccountNumber())
                .destinationFullName(wallet1.getUsers().getFirstName() + " " + wallet1.getUsers().getLastName())
                .destinationBank(wallet1.getBankName())
                .transactionType(TransactionType.DEBIT)
                .wallet(debitedWallet)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(sender);

        Transaction receiver = Transaction.builder()
                .clientRef(uuid.toString())
                .userStatus(UsersStatus.ACTIVE)
                .flwRef(0L)
                .narration(transferRequest.getNarration())
                .amount(transferRequest.getAmount())
                .destinationAccountNumber(wallet1.getAccountNumber())
                .destinationFullName(wallet1.getUsers().getFirstName() + " " + wallet1.getUsers().getLastName())
                .destinationBank(wallet1.getBankName())
                .senderAccountNumber(wallet.getAccountNumber())
                .senderFullName(wallet.getUsers().getFirstName() + " " + wallet.getUsers().getLastName())
                .senderBankName(wallet.getBankName())
                .transactionType(TransactionType.CREDIT)
                .wallet(creditedWallet)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(receiver);

        return "Transaction successful!";
    }

}
