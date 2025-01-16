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
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/create")
@Controller
@Slf4j
public class CreateMandateController {

    @GetMapping("/nachMandate")
    public String getNachMandate(Model model) {
        Map<String, String> accountTypeMap = io.paycorp.smartmandate.client.domain.nach.Mandate.accountTypeMap();
        Map<String, String> frqcyMap = io.paycorp.smartmandate.client.domain.nach.Mandate.frqcyMap();
        Map<String, String> authModeMap = io.paycorp.smartmandate.client.domain.nach.Mandate.authModeMap();

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

        io.paycorp.smartmandate.client.domain.nach.Mandate mandate = new io.paycorp.smartmandate.client.domain.nach.Mandate.Builder()
                .utilityCode(utilityCode)
                .schmNm("demo")
                .consRefNo(consumerRefNumber)
                .sourceReferenceNumber(referenceNumber)
                .colltnAmt(new BigDecimal(amount))
                .frqcy(io.paycorp.smartmandate.client.domain.nach.Mandate.Frqcy.valueOf(frqcy))
                .frstColltnDt(LocalDate.parse(firstCollectionDate))
                .fnlColltnDt(LocalDate.parse(finalCollectionDate))
                .dbtrNm(dbtrNm)
                .mobile(mobile)
                .dbtrAccNo(dbtrAccNo)
                .dbtrAccTp(io.paycorp.smartmandate.client.domain.nach.Mandate.AccountType.valueOf(dbtrAccTp))
                .authMode(io.paycorp.smartmandate.client.domain.nach.Mandate.AuthMode.valueOf(authMode))
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
    public String getUpiMandate(Model model) {
        Map<String, String> frqcyMap = io.paycorp.smartmandate.client.domain.upi.Mandate.frqcyMap();
        Map<String, String> accountValidationMap = io.paycorp.smartmandate.client.domain.upi.Mandate.accountValidationMap();
        Map<String, String> debitRuleMap = io.paycorp.smartmandate.client.domain.upi.Mandate.debitRuleMap();

        model.addAttribute("frqcyMap", frqcyMap);
        model.addAttribute("accountValidationMap", accountValidationMap);
        model.addAttribute("debitRuleMap", debitRuleMap);
        return "create_upi_mandate";
    }

    @PostMapping("/upiMandate")
    public String postUpiMandate(Model model,
            @RequestParam String url,
            @RequestParam String apiKey,
            @RequestParam String encryptionKey,
            @RequestParam String consumerRefNumber,
            @RequestParam String utilityCode,
            @RequestParam String referenceNumber,
            @RequestParam double amount,
            @RequestParam String frqcy,
            @RequestParam String firstCollectionDate,
            @RequestParam String finalCollectionDate,
            @RequestParam String dbtrNm,
            @RequestParam String mobile,
            @RequestParam String virtualAddress,
            @RequestParam String accountValidation,
            @RequestParam String debitRule,
            @RequestParam int debitDay) {
        log.info("Consumer Reference Number: " + consumerRefNumber);
        log.info("Reference Number: " + referenceNumber);
        log.info("Amount: " + amount);
        log.info("Frequency: " + frqcy);
        log.info("First Collection Date: " + firstCollectionDate);
        log.info("Final Collection Date: " + finalCollectionDate);
        log.info("Debtor Name: " + dbtrNm);
        log.info("Mobile: " + mobile);
        log.info("Debtor Account Number: " + virtualAddress);
        log.info("UtilityCode: ", utilityCode);

        io.paycorp.smartmandate.client.domain.upi.Mandate mandate = new io.paycorp.smartmandate.client.domain.upi.Mandate.Builder()
                .schmNm("demo")
                .consRefNo(consumerRefNumber)
                .sourceReferenceNumber(referenceNumber)
                .colltnAmt(new BigDecimal(amount))
                .frqcy(io.paycorp.smartmandate.client.domain.upi.Mandate.Frqcy.valueOf(frqcy))
                .frstColltnDt(LocalDate.parse(firstCollectionDate))
                .fnlColltnDt(LocalDate.parse(finalCollectionDate))
                .dbtrNm(dbtrNm)
                .amountTp(io.paycorp.smartmandate.client.domain.upi.Mandate.AmountTp.valueOf("MAX"))
                .mobile(mobile)
                .virtualAddress(virtualAddress)
                .utilityCode("NACH0000000001")
                .accountValidation(io.paycorp.smartmandate.client.domain.upi.Mandate.AccountValidation.valueOf(accountValidation))
                .debitRule(io.paycorp.smartmandate.client.domain.upi.Mandate.DebitRule.valueOf(debitRule))
                .debitDay(debitDay)
                .build();

        ApiClient apiClient = new ApiClient(url, apiKey, encryptionKey);
        var apiResponse = apiClient.create(UUID.randomUUID().toString(), mandate);
        if (apiResponse.isSuccess()) {
            model.addAttribute("response", apiResponse.message());
        } else {
            model.addAttribute("response", apiResponse.toString());
        }
        return "create_upi_mandate";
    }
}
