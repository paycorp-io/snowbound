package io.paycorp.smartmandate.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.paycorp.smartmandate.client.ApiClient;
import io.paycorp.smartmandate.client.Client;
import io.paycorp.smartmandate.client.domain.Mandate;
import io.paycorp.smartmandate.client.service.HelperUtility;

public class IndexControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private IndexController indexController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testSignIn() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in"));
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("accountTypeMap"))
                .andExpect(model().attributeExists("frqcyMap"));
    }

    @Test
    public void testGetEncrypt() throws Exception {
        mockMvc.perform(get("/encrypt"))
                .andExpect(status().isOk())
                .andExpect(view().name("encrypt"));
    }

    @Test
    public void testPostEncrypt() throws Exception {
        String encryptionAction = "Encrypt";
        String request = "Hello, World!";
        String encryptionKey = "et6DhZzAqMfh3Im045rL82aUrCt8Bn+SjjDZWs/S8LE=";

        mockMvc.perform(post("/encrypt")
                .param("encryptionAction", encryptionAction)
                .param("request", request)
                .param("encryptionKey", encryptionKey))
                .andExpect(status().isOk())
                .andExpect(view().name("encrypt"))
                .andExpect(model().attributeExists("response"));
    }

    @Test
    public void testQueryMandate() throws Exception {
        mockMvc.perform(get("/queryMandate"))
                .andExpect(status().isOk())
                .andExpect(view().name("query_mandate"));
    }

    @Test
    public void testPostQueryMandate() throws Exception {
        String referenceNumber = "referenceNumber";
        String encryptionKey = "encryptionKey";
        String url = "http://example.com";
        String apiKey = "apiKey";

        mockMvc.perform(post("/queryMandate")
                .param("referenceNumber", referenceNumber)
                .param("encryptionKey", encryptionKey)
                .param("url", url)
                .param("apiKey", apiKey))
                .andExpect(status().isOk())
                .andExpect(view().name("query_mandate"))
                .andExpect(model().attributeExists("response"));
    }

    @Test
    public void testCreateRandomAesKey() throws Exception {
        mockMvc.perform(get("/createKey"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_key"))
                .andExpect(model().attributeExists("aesKey"))
                .andExpect(model().attributeExists("apiKey"));
    }

    @Test
    public void testPostIndex() throws Exception {
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

        mockMvc.perform(post("/home")
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
                .param("bnkId", bnkId))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect_to_gateway"))
                .andExpect(model().attributeExists("encData"))
                .andExpect(model().attributeExists("clientId"));
    }
}
