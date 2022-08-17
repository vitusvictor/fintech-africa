package com.decagon.fintechpaymentapisqd11b.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private HttpStatus status;
    private String message;
    private T result;
}

