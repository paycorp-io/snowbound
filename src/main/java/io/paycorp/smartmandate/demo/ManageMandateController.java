package io.paycorp.smartmandate.demo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.paycorp.smartmandate.client.ApiClient;
import io.paycorp.smartmandate.client.domain.AmendMandate;
import io.paycorp.smartmandate.client.domain.ManageMandate;

@Slf4j
@Controller
@RequestMapping("/mandate")
public class ManageMandateController {

    @GetMapping("/amend")
    public String getAmend(Model model) {
        Map<String, String> frqcyMap = AmendMandate.Amend.frqcyMap();
        Map<String, String> categoryCodeMap = AmendMandate.Amend.categoryCodeMap();

        model.addAttribute("frqcyMap", frqcyMap);
        model.addAttribute("categoryCodeMap", categoryCodeMap);
        return "amend_mandate";
    }

    @PostMapping("/amend")
    public String postAmend(@RequestParam String utilityCode, @RequestParam String referenceNumber,
            @RequestParam String categoryCode, @RequestParam String colltnAmt,
            @RequestParam String frqcy, @RequestParam String firstCollectionDate,
            @RequestParam String finalCollectionDate,
            @RequestParam String reasonCode, @RequestParam String apiKey, @RequestParam String encryptionKey,
            @RequestParam String url, @RequestParam String umrn, Model model) {
        log.info("Utility code : " + utilityCode);
        log.info("sourceReference : " + referenceNumber);
        log.info("Category code : " + categoryCode);
        log.info("Collection amount : " + colltnAmt);
        log.info("Frequency : " + frqcy);
        log.info("First collection date : " + firstCollectionDate);
        log.info("Final collection date : " + finalCollectionDate);
        log.info("Reason code : " + reasonCode);
        log.info("API Key : " + apiKey);
        log.info("Encryption Key : " + encryptionKey);
        log.info("URL : " + url);

        AmendMandate amendMandate = new AmendMandate.Amend.Builder()
                .utilityCode(utilityCode)
                .sourceReference(referenceNumber)
                .categoryCode(categoryCode)
                .colltnAmt(new BigDecimal(colltnAmt))
                .frqcy(frqcy)
                .frstColltnDt(firstCollectionDate)
                .fnlColltnDt(finalCollectionDate)
                .reasonCode(reasonCode)
                .umrn(umrn)
                .build();

        ApiClient apiClient = new ApiClient(url, apiKey, encryptionKey);
        var apiResponse = apiClient.amend(UUID.randomUUID().toString(), amendMandate);
        if (apiResponse.isSuccess()) {
            model.addAttribute("response", apiResponse.message());
        } else {
            model.addAttribute("response", apiResponse.toString());
        }

        return "amend_mandate";
    }

    @GetMapping("/cancel")
    public String getCancel(Model model) {
        return "cancel_mandate";
    }

    @PostMapping("/cancel")
    public String postCancel(Model model, @RequestParam String utilityCode,
            @RequestParam String referenceNumber, @RequestParam String reasonCode,
            @RequestParam String umrn, @RequestParam String apiKey,
            @RequestParam String encryptionKey, @RequestParam String url) {

        log.info("Utility code : " + utilityCode);
        log.info("sourceReference : " + referenceNumber);
        log.info("Reason code : " + reasonCode);
        log.info("UMRN : " + umrn);
        log.info("API Key : " + apiKey);
        log.info("Encryption Key : " + encryptionKey);
        log.info("URL : " + url);

        ManageMandate manageMandate = new ManageMandate.Cancel.Builder()
                .utilityCode(utilityCode)
                .sourceReference(referenceNumber)
                .reasonCode(reasonCode)
                .umrn(umrn)
                .build();

        ApiClient apiClient = new ApiClient(url, apiKey, encryptionKey);
        var apiResponse = apiClient.cancel(UUID.randomUUID().toString(), manageMandate);
        if (apiResponse.isSuccess()) {
            model.addAttribute("response", apiResponse.message());
        } else {
            model.addAttribute("response", apiResponse.toString());
        }

        return "cancel_mandate";
    }

    @GetMapping("/suspend")
    public String getSuspend(Model model) {
        return "suspend_mandate";
    }

    @PostMapping("/suspend")
    public String postSuspend(Model model, @RequestParam String utilityCode,
            @RequestParam String referenceNumber, @RequestParam String reasonCode,
            @RequestParam String umrn, @RequestParam String apiKey,
            @RequestParam String encryptionKey, @RequestParam String url) {

        log.info("Utility code : " + utilityCode);
        log.info("sourceReference : " + referenceNumber);
        log.info("Reason code : " + reasonCode);
        log.info("UMRN : " + umrn);
        log.info("API Key : " + apiKey);
        log.info("Encryption Key : " + encryptionKey);
        log.info("URL : " + url);

        ManageMandate manageMandate = new ManageMandate.Cancel.Builder()
                .utilityCode(utilityCode)
                .sourceReference(referenceNumber)
                .reasonCode(reasonCode)
                .umrn(umrn)
                .build();

        ApiClient apiClient = new ApiClient(url, apiKey, encryptionKey);
        var apiResponse = apiClient.suspend(UUID.randomUUID().toString(), manageMandate);
        if (apiResponse.isSuccess()) {
            model.addAttribute("response", apiResponse.message());
            log.info("response mesg : {}", apiResponse.message());
        } else {
            model.addAttribute("response", apiResponse.toString());
        }

        return "suspend_mandate";
    }

    @GetMapping("/revoke")
    public String getRevoke(Model model) {
        return "revoke_mandate";
    }

    @PostMapping("/revoke")
    public String postRevoke(Model model, @RequestParam String utilityCode,
            @RequestParam String referenceNumber, @RequestParam String reasonCode,
            @RequestParam String umrn, @RequestParam String apiKey,
            @RequestParam String encryptionKey, @RequestParam String url) {

        log.info("Utility code : " + utilityCode);
        log.info("sourceReference : " + referenceNumber);
        log.info("Reason code : " + reasonCode);
        log.info("UMRN : " + umrn);
        log.info("API Key : " + apiKey);
        log.info("Encryption Key : " + encryptionKey);
        log.info("URL : " + url);

        ManageMandate manageMandate = new ManageMandate.Cancel.Builder()
                .utilityCode(utilityCode)
                .sourceReference(referenceNumber)
                .reasonCode(reasonCode)
                .umrn(umrn)
                .build();

        ApiClient apiClient = new ApiClient(url, apiKey, encryptionKey);
        var apiResponse = apiClient.revoke(UUID.randomUUID().toString(), manageMandate);
        if (apiResponse.isSuccess()) {
            model.addAttribute("response", apiResponse.message());
        } else {
            model.addAttribute("response", apiResponse.toString());
        }

        return "revoke_mandate";
    }

}
