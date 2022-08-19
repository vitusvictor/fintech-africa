package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.TokenNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.ConfirmationTokenRepository;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.security.filter.JwtUtils;
import com.decagon.fintechpaymentapisqd11b.service.ConfirmationTokenService;
import com.decagon.fintechpaymentapisqd11b.service.LoginService;
import com.decagon.fintechpaymentapisqd11b.service.RegistrationService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service @RequiredArgsConstructor @Slf4j
public class LoginServiceImpl implements LoginService {

    private final UsersRepository usersRepository;

    private final UsersServiceImpl usersService;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String login(LoginRequestPayload loginRequestPayload) throws JSONException {
        Users user = usersRepository.findByEmail(loginRequestPayload.getEmail()).
                orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        if (user.getUsersStatus() != UsersStatus.ACTIVE){
            ConfirmationToken confirmationToken = confirmationTokenRepository.findConfirmationTokenByUser(user)
                            .orElseThrow(()-> new TokenNotFoundException("Token not found"));
            if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) { // already expired
                usersService.deleteUnverifiedToken(confirmationToken);
                registrationService.resendVerificationEmail(user);
            }


            return "Please verify your account from your email";
        }

        try{
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken
                    (loginRequestPayload.getEmail(), loginRequestPayload.getPassword())
            );
        }catch (BadCredentialsException ex){
            throw new UsernameNotFoundException("Invalid Credential");
        }
        final UserDetails userDetails = usersService.loadUserByUsername(loginRequestPayload.getEmail());
        return jwtUtils.generateToken(userDetails);

    }
}
