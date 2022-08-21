package com.decagon.fintechpaymentapisqd11b.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PasswordRequest {

    @Email
    private String email;

    @Size(min = 4, max = 4, message = "Pin must be of 4 characters")
    private String oldPassword;

    @Size(min = 4, message = "Minimum password length is 4")
    private String newPassword;

    @Size(min = 4, message = "Minimum password length is 4")
    private String confirmPassword;
}
