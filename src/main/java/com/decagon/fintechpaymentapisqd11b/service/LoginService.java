package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface LoginService {

    String login(LoginRequestPayload loginRequestPayload) throws JSONException;
}
