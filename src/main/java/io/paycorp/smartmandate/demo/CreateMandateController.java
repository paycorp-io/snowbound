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
        Map<String, String> amountTpMap = Mandate.Nach.amountTpMap();

        model.addAttribute("accountTypeMap", accountTypeMap);
        model.addAttribute("frqcyMap", frqcyMap);
        model.addAttribute("authModeMap", authModeMap);
        model.addAttribute("amountTpMap", amountTpMap);
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
            @RequestParam String amountTp,
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

        Mandate mandate = new Mandate.Nach.Builder()
                .utilityCode(utilityCode)
                .schmNm("demo")
                .consRefNo(consumerRefNumber)
                .sourceReferenceNumber(referenceNumber)
                .colltnAmt(new BigDecimal(amount))
                .amountTp(Mandate.Nach.AmountTp.valueOf(amountTp))
                .frqcy(Mandate.Nach.Frqcy.valueOf(frqcy))
                .frstColltnDt(firstCollectionDate)
                .fnlColltnDt(finalCollectionDate)
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
    public String getUpiMandate(Model model) {
        Map<String, String> frqcyMap = Mandate.Upi.frqcyMap();
        Map<String, String> accountValidationMap = Mandate.Upi.accountValidationMap();
        Map<String, String> debitRuleMap = Mandate.Upi.debitRuleMap();
        Map<String, String> categoryCodeMap = Mandate.Upi.categoryCodeMap();
        Map<String, String> amountTpMap = Mandate.Upi.amountTpMap();
        Map<String, String> revokeableMap = Mandate.Upi.revokeableMap();

        model.addAttribute("frqcyMap", frqcyMap);
        model.addAttribute("accountValidationMap", accountValidationMap);
        model.addAttribute("debitRuleMap", debitRuleMap);
        model.addAttribute("categoryCodeMap", categoryCodeMap);
        model.addAttribute("amountTpMap", amountTpMap);
        model.addAttribute("revokeableMap", revokeableMap);
        return "create_upi_mandate";
    }

    @PostMapping("/upiMandate")
    public String postUpiMandate(Model model,
            @RequestParam String url,
            @RequestParam String apiKey,
            @RequestParam String encryptionKey,
            @RequestParam String consumerRefNumber,
            @RequestParam String utilityCode,
            @RequestParam String categoryCode,
            @RequestParam String referenceNumber,
            @RequestParam double amount,
            @RequestParam String amountTp,
            @RequestParam String frqcy,
            @RequestParam String firstCollectionDate,
            @RequestParam String finalCollectionDate,
            @RequestParam String dbtrNm,
            @RequestParam String mobile,
            @RequestParam String virtualAddress,
            @RequestParam String accountValidation,
            @RequestParam String revokeable,
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
        log.info("UtilityCode: " + utilityCode);
        log.info("CategoryCode: " + categoryCode);

        Mandate mandate = new Mandate.Upi.Builder()
                .schmNm("demo")
                .consRefNo(consumerRefNumber)
                .sourceReferenceNumber(referenceNumber)
                .colltnAmt(new BigDecimal(amount))
                .frqcy(Mandate.Upi.Frqcy.valueOf(frqcy))
                .frstColltnDt(firstCollectionDate)
                .fnlColltnDt(finalCollectionDate)
                .dbtrNm(dbtrNm)
                .amountTp(Mandate.Upi.AmountTp.valueOf(amountTp))
                .mobile(mobile)
                .virtualAddress(virtualAddress)
                .utilityCode(utilityCode)
                .categoryCode(Mandate.Upi.CategoryCode.valueOf(categoryCode))
                .accountValidation(Mandate.Upi.AccountValidation.valueOf(accountValidation))
                .debitRule(Mandate.Upi.DebitRule.valueOf(debitRule))
                .debitDay(debitDay)
                .revokeable(Mandate.Upi.Revokeable.valueOf(revokeable))
                .build();
                log.info("CategoryCode: " + io.paycorp.smartmandate.client.domain.upi.Mandate.CategoryCode.valueOf(categoryCode));

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
