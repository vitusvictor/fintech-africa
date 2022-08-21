package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.dto.LoginResponseDto;
import com.decagon.fintechpaymentapisqd11b.dto.UsersResponse;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.pagination_criteria.TransactionHistoryPages;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.service.TransactionHistoryResponse;
import com.decagon.fintechpaymentapisqd11b.dto.*;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.LoginServiceImpl;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.UsersServiceImpl;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.WalletServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping
public class UsersController {

    private final UsersServiceImpl usersService;

    private final LoginServiceImpl loginService;

    private final WalletServiceImpl walletService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestPayload loginRequestPayload) throws JSONException {
            log.info("successful");
            String token = loginService.login(loginRequestPayload);
            return new ResponseEntity<>(new LoginResponseDto(token),HttpStatus.OK);
    }

    @GetMapping("/viewWalletDetails")
    public ResponseEntity<WalletDto> viewWalletDetails()  {
        return new ResponseEntity<>(walletService.viewWalletDetails(),HttpStatus.OK);
    }


    @GetMapping("/viewUser")
    public ResponseEntity<UsersResponse> getUsers(){
       UsersResponse usersResponse = usersService.getUser();
        return new ResponseEntity<>(usersResponse, HttpStatus.OK);
    }

    @GetMapping("/transactionHistory")
    public BaseResponse<Page<TransactionHistoryResponse>> getTransactionHistory(TransactionHistoryPages transactionHistoryPages) {
        return usersService.getTransactionHistory(transactionHistoryPages);
    }

}
