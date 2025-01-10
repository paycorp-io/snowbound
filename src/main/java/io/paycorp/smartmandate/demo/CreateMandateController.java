package io.paycorp.smartmandate.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.paycorp.smartmandate.client.ApiClient;
import io.paycorp.smartmandate.client.domain.Mandate;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/create")
@Controller
@Slf4j
public class CreateMandateController {

    @GetMapping("/nachMandate")
    public String getNachMandate(Model model) {
        Map<String, String> accountTypeMap = Mandate.Nach.accountTypeMap();
        Map<String, String> frqcyMap = Mandate.Nach.frqcyMap();
        Map<String, String> authModeMap = Mandate.Nach.authModeMap();

        model.addAttribute("accountTypeMap", accountTypeMap);
        model.addAttribute("frqcyMap", frqcyMap);
        model.addAttribute("authModeMap", authModeMap);
        return "create_nach_mandate";
    }

    @PostMapping("/nachMandate")
    public String postNachMandate(Model model,
            @RequestParam String url,
            @RequestParam String utilityCode,
            @RequestParam String apiKey,
            @RequestParam String encryptionKey,
            @RequestParam String consumerRefNumber,
            @RequestParam String referenceNumber,
            @RequestParam double amount,
            @RequestParam String frqcy,
            @RequestParam String firstCollectionDate,
            @RequestParam String finalCollectionDate,
            @RequestParam String dbtrNm,
            @RequestParam String mobile,
            @RequestParam String dbtrAccNo,
            @RequestParam String dbtrAccTp,
            @RequestParam String bnkId,
            @RequestParam String authMode) {
        log.info("Consumer Reference Number: " + consumerRefNumber);
        log.info("Reference Number: " + referenceNumber);
        log.info("Amount: " + amount);
        log.info("Frequency: " + frqcy);
        log.info("First Collection Date: " + firstCollectionDate);
        log.info("Final Collection Date: " + finalCollectionDate);
        log.info("Debtor Name: " + dbtrNm);
        log.info("Mobile: " + mobile);
        log.info("Debtor Account Number: " + dbtrAccNo);
        log.info("Debtor Account Type: " + dbtrAccTp);
        log.info("Bank ID: " + bnkId);

        Mandate mandate = new Mandate.Builder()
                .utilityCode(utilityCode)
                .schmNm("demo")
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

        ApiClient apiClient = new ApiClient(url, apiKey, encryptionKey);
        var apiResponse = apiClient.create(UUID.randomUUID().toString(), mandate);
        if (apiResponse.isSuccess()) {
            model.addAttribute("response", apiResponse.message());
        } else {
            model.addAttribute("response", apiResponse.toString());
        }
        return "create_nach_mandate";
    }

    @GetMapping("/upiMandate")
    public String getUpiMandate() {
        return "create_upi_mandate";
    }

}
