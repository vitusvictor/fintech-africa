package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.dto.LoginResponseDto;
import com.decagon.fintechpaymentapisqd11b.dto.UsersResponse;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.pagination_criteria.TransactionHistoryPages;
import com.decagon.fintechpaymentapisqd11b.request.EmailVerifyRequest;
import com.decagon.fintechpaymentapisqd11b.request.PasswordRequest;
import com.decagon.fintechpaymentapisqd11b.request.ResetPasswordRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.response.TransactionHistoryResponse;

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

import javax.mail.MessagingException;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping
@CrossOrigin
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


    @GetMapping("/viewUser")
    public ResponseEntity<UsersResponse> getUsers(){
       UsersResponse usersResponse = usersService.getUser();
        return new ResponseEntity<>(usersResponse, HttpStatus.OK);
    }

    @GetMapping("/transactionHistory")
    public BaseResponse<Page<TransactionHistoryResponse>> getTransactionHistory(TransactionHistoryPages transactionHistoryPages) {
        return usersService.getTransactionHistory(transactionHistoryPages);
    }

    @PostMapping("/changePassword")
    public BaseResponse<String> changePassword(@RequestBody PasswordRequest passwordRequest){
        return usersService.changePassword(passwordRequest);
    }

    @PostMapping("/forgot-Password")
    public BaseResponse<String> forgotPassword(@RequestBody EmailVerifyRequest emailVerifyRequest) throws MessagingException{
        return usersService.generateResetToken(emailVerifyRequest);
    }

    @PostMapping("/reset-Password")
    public BaseResponse<String> resetPassword(@RequestBody ResetPasswordRequest passwordRequest, @RequestParam ("token") String token){
        return usersService.resetPassword(passwordRequest, token);
    }

    @GetMapping("/getUserName")
    public BaseResponse<String> getName(){
        return usersService.getUserName();
    }

}
