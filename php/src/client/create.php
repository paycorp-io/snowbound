<?php

require_once __DIR__ . '/vendor/autoload.php';                  
require_once __DIR__ . '/paycorp-client.phar';            

use Src\ApiClient;
use Src\Domain\Mandate;
use Src\Domain\MandateBuilder;

use Src\Domain\Enum\MndtType;
use Src\Domain\Enum\CategoryCode;
use Src\Domain\Enum\SeqTp;
use Src\Domain\Enum\Frqcy;
use Src\Domain\Enum\AmountTp;
use Src\Domain\Enum\AccountType;
use Src\Domain\Enum\AuthMode;
use Src\Domain\Enum\MandateAction;
use Src\Domain\Enum\AccountValidation;
use Src\Domain\Enum\DebitRule;
use Src\Domain\Enum\Revokeable;
use Src\Domain\MandateUpi;


use Ramsey\Uuid\Uuid;

$arid = Uuid::uuid4()->toString(); 

$upiMandate = MandateBuilder::upi()
    ->schmNm('demo')
    ->referenceNumber('17437437gffEbd45345')
    ->consRefNo('CONS873ghSCONe789')
    ->sourceReference('SR7832l784878776REF')
    ->colltnAmt(1500.00)
    ->frqcy(Frqcy::MONTHLY)                  // assuming string, replace with enum class if available
    ->frstColltnDt('2025-06-20')
    ->fnlColltnDt('2026-06-01')
    ->dbtrNm('inglish')
    ->amountTp(AmountTp::EXACT)               // string, can be enum
    ->mobile('9876543210')
    ->virtualAddress('user@bank')   // typical UPI VPA
    ->utilityCode('NACH00000000000019')
    ->categoryCode(CategoryCode::AMC)           // string or enum
    ->accountValidation(AccountValidation::N)     // example value, replace with actual enum/string
    ->debitRule(DebitRule::ON)             // example
    ->debitDay(1)
    ->revokeable(Revokeable::Y)              // example value
    ->build();

// 1. Build your Mandate object (either manually or using builder)
// $builder = new MandateBuilder();

// Step 2: Set all required properties on the builder, including enums
// $builder
//     ->setMndtType(MndtType::ONMAG)
//     ->setReferenceNumber('REF1283djhbdh345')
//     ->setUtilityCode('NACH00000000000019')
//     ->setCategoryCode(CategoryCode::L002)
//     ->setSchmNm('LOAN EMI')
//     ->setConsRefNo('CONS873ghe789')
//     ->setSeqTp(SeqTp::RCUR)
//     ->setFrqcy(Frqcy::MNTH)
//     ->setFrstColltnDt('2025-06-01')
//     ->setFnlColltnDt('2026-06-01')
//     ->setAmountTp(AmountTp::FIXA)
//     ->setColltnAmt(1500.00)
//     ->setDbtrNm('JohnDoe')
//     ->setMobile('9876543210')
//     ->setBnkId('KCUB')
//     ->setDbtrAccTp(AccountType::SAVINGS)
//     ->setDbtrAccNo('1234567890123456')
//     ->setSourceReference('SRC887gyg7REF')
//     ->setAuthMode(AuthMode::DebitCard)
//     ->setMandateAction(MandateAction::Create)
//     ->setProductCode('PROD-001');

    $mandate = MandateBuilder::nach()
    ->referenceNumber('12F887758768bd45365')
    ->utilityCode('NACH00000000000019')
    ->categoryCode(CategoryCode::L002)
    ->schmNm('Loan EMI')
    ->consRefNo('CONS873ghSCONe73')
    ->seqTp(SeqTp::RCUR)
    ->frqcy(Frqcy::MNTH)
    ->frstColltnDt('2025-06-11')
    ->fnlColltnDt('2026-06-01')
    ->amountTp(AmountTp::FIXA)
    ->colltnAmt(140.00)
    ->dbtrNm('English5')
    ->mobile('9876543210')
    ->bnkId('KCUB')
    ->dbtrAccTp(AccountType::SAVINGS)
    ->dbtrAccNo('123456789011')
    ->sourceReference('SR78362786REF10')
    ->authMode(AuthMode::DebitCard)
    ->mandateAction(MandateAction::Create)
    ->productCode('PROD-001')
    ->build();

// Step 3: Convert MandateBuilder to Mandate domain object
//$mandate = Mandate::fromNachBuilder($builder);

// Step 4: Use $mandate - for example, print some properties
print_r($mandate->toArray());


// 2. Create ApiClient instance
 $baseUrl = "http://localhost:10070/api/corporate";
// $baseUrl = "http://172.31.248.155:4445";
$baseUrl = "http://localhost:4445";
//$apiKey = "67e665bd-bd07-4bc2-9969-0609276f159a";
$apiKey = "ee0fc0fd-6772-4f39-8aac-c0bc7bed31bb";
$encryptionKey = "wiQnJ/9HZh3QGZyvCw6g0/tj5Ge+0MAFv80RgZKrJxU=";
$apiClient = new ApiClient($baseUrl, $apiKey, $encryptionKey);

//$response = $apiClient->create($arid, $mandate);
$response = $apiClient->gateway($arid, $upiMandate);
//error_log("Response: " . print_r($response, true));


// 4. Check response
if ($response->status == '200') {
    // echo file_get_contents("phar://paycorp-client.phar/Src/public/gatewayRedirect.html");
    $encData = $response->message; // assuming message is a Mandate object

    include 'phar://paycorp-client.phar/Src/public/gatewayRedirect.php';
    //file_put_contents('gatewayRedirect.php', ob_get_contents());
    exit;
} else {
    echo file_get_contents("phar://paycorp-client.phar/Src/public/error.html");
}

/*if ($response->isSuccess()) {
    echo "Mandate created successfully!";
} else {
    echo "Failed to create mandate: " . $response->moreInfo;
}*/


