package io.paycorp.smartmandate.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import io.paycorp.smartmandate.client.ApiClient;
import io.paycorp.smartmandate.client.domain.Mandate;

public class CreateMandateControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private CreateMandateController createMandateController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(createMandateController).build();
    }

    @Test
    public void testGetNachMandate() throws Exception {
        mockMvc.perform(get("/create/nachMandate"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_nach_mandate"))
                .andExpect(model().attributeExists("accountTypeMap"))
                .andExpect(model().attributeExists("frqcyMap"))
                .andExpect(model().attributeExists("authModeMap"));
    }

    @Test
    public void testPostNachMandate() throws Exception {
        String url = "http://example.com";
        String utilityCode = "utilityCode";
        String apiKey = "apiKey";
        String encryptionKey = "encryptionKey";
        String consumerRefNumber = "consumerRefNumber";
        String referenceNumber = "referenceNumber";
        double amount = 100.0;
        String frqcy = "MONTHLY";
        String firstCollectionDate = "2023-01-01";
        String finalCollectionDate = "2023-12-31";
        String dbtrNm = "dbtrNm";
        String mobile = "mobile";
        String dbtrAccNo = "dbtrAccNo";
        String dbtrAccTp = "SAVINGS";
        String bnkId = "bnkId";
        String authMode = "E";

        Mandate mandate = new Mandate.Builder()
                .utilityCode(utilityCode)
                .consRefNo(consumerRefNumber)
                .sourceReferenceNumber(referenceNumber)
                .colltnAmt(new BigDecimal(amount))
                .frqcy(Mandate.Nach.Frqcy.valueOf(frqcy))
                .frstColltnDt(LocalDate.parse(firstCollectionDate))
                .fnlColltnDt(LocalDate.parse(finalCollectionDate))
                .dbtrNm(dbtrNm)
                .mobile(mobile)
                .dbtrAccNo(dbtrAccNo)
                .dbtrAccTp(Mandate.Nach.AccountType.valueOf(dbtrAccTp))
                .authMode(Mandate.Nach.AuthMode.valueOf(authMode))
                .bnkId(bnkId)
                .build();

        when(apiClient.create(UUID.randomUUID().toString(), mandate)).thenReturn(new ApiResponse(true, "Success"));

        mockMvc.perform(post("/create/nachMandate")
                .param("url", url)
                .param("utilityCode", utilityCode)
                .param("apiKey", apiKey)
                .param("encryptionKey", encryptionKey)
                .param("consumerRefNumber", consumerRefNumber)
                .param("referenceNumber", referenceNumber)
                .param("amount", String.valueOf(amount))
                .param("frqcy", frqcy)
                .param("firstCollectionDate", firstCollectionDate)
                .param("finalCollectionDate", finalCollectionDate)
                .param("dbtrNm", dbtrNm)
                .param("mobile", mobile)
                .param("dbtrAccNo", dbtrAccNo)
                .param("dbtrAccTp", dbtrAccTp)
                .param("bnkId", bnkId)
                .param("authMode", authMode))
                .andExpect(status().isOk())
                .andExpect(view().name("create_nach_mandate"))
                .andExpect(model().attributeExists("response"));
    }
}
