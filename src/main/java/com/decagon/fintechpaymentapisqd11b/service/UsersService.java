package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.dto.UsersResponse;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.pagination_criteria.TransactionHistoryPages;
import com.decagon.fintechpaymentapisqd11b.request.EmailVerifyRequest;
import com.decagon.fintechpaymentapisqd11b.request.PasswordRequest;
import com.decagon.fintechpaymentapisqd11b.request.ResetPasswordRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.response.TransactionHistoryResponse;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;

public interface UsersService {
    String registerUser(UsersDTO usersDTO) throws JSONException;
    void saveToken(String token, Users user);
    void enableUser(String email) throws JSONException;
    Wallet generateWallet(Users user) throws JSONException;
    UsersResponse getUser();

    BaseResponse<Page<TransactionHistoryResponse>>
    getTransactionHistory(TransactionHistoryPages transactionHistoryPages);
    void deleteUnverifiedToken(ConfirmationToken token);

    BaseResponse<String> generateResetToken(EmailVerifyRequest emailVerifyRequest) throws MessagingException;

    BaseResponse<String> resetPassword(ResetPasswordRequest resetPasswordRequest, String token);

    BaseResponse<String> changePassword(PasswordRequest passwordRequest);

    BaseResponse<String>  getUserName();


}
