package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.request.PasswordRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;

import javax.mail.MessagingException;

public interface LoginService {

    String login(LoginRequestPayload loginRequestPayload);

    BaseResponse<String> generateResetToken(PasswordRequest passwordRequest) throws MessagingException;

    BaseResponse<String> resetPassword(PasswordRequest passwordRequest, String token);

    BaseResponse<String> changePassword(PasswordRequest passwordRequest);
}
