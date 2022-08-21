package com.decagon.fintechpaymentapisqd11b.response;

import com.decagon.fintechpaymentapisqd11b.entities.FlwBank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlwBankResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private List<FlwBank> data;
}
