package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.request.PasswordRequest;
import com.decagon.fintechpaymentapisqd11b.response.BaseResponse;
import com.decagon.fintechpaymentapisqd11b.security.filter.JwtUtils;
import com.decagon.fintechpaymentapisqd11b.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;



@Service @RequiredArgsConstructor @Slf4j
public class LoginServiceImpl implements LoginService {

    private final UsersRepository usersRepository;

    private final UsersServiceImpl usersService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(LoginRequestPayload loginRequestPayload) {
        Users user = usersRepository.findByEmail(loginRequestPayload.getEmail()).
                orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        if (user.getUsersStatus() != UsersStatus.ACTIVE){
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

    @Override
    public BaseResponse<String> generateResetToken(PasswordRequest passwordRequest) throws MessagingException {

        return null;
    }

    @Override
    public BaseResponse<String> resetPassword(PasswordRequest passwordRequest, String token) {
        return null;
    }

    @Override
    public BaseResponse<String> changePassword(PasswordRequest passwordRequest) {
        if(!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())){
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "new password must match with confirm password", null);
        }
        String loggedInUsername =  SecurityContextHolder.getContext().getAuthentication().getName();

        Users users = usersRepository.findUsersByEmail(loggedInUsername);

        if (users == null){
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED, "User is not logged In", null);
        }
        boolean matchPasswordWithOldPassword = passwordEncoder.matches(passwordRequest.getOldPassword(), users.getPassword());

        if(!matchPasswordWithOldPassword){
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "old password is not correct", null);
        }
        users.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));

        usersRepository.save(users);
        return new BaseResponse<>(HttpStatus.OK, "password changed successfully", null);
    }
}
