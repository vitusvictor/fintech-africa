package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;
import com.decagon.fintechpaymentapisqd11b.response.FlwBankResponse;
import com.decagon.fintechpaymentapisqd11b.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {OtherBanksTransferImpl.class})
@ExtendWith(SpringExtension.class)
class OtherBanksTransferImplTest {

    @Autowired
    private OtherBanksTransferImpl otherBanksTransferImpl;


    @Test
    void testGetBanks() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwBankResponse> request = new HttpEntity<>(null, headers);

        FlwBankResponse flwBankResponse = restTemplate.exchange(
                Constant.GET_BANKS_API + "/NG",
                HttpMethod.GET,
                request,
                FlwBankResponse.class
        ).getBody();

        assertEquals("success", flwBankResponse.getStatus());
        assertNotNull(flwBankResponse.getData());
    }

}

