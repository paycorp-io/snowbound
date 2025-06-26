#!/usr/bin/env php
Array
(
    [mndtType] => NACH
    [referenceNumber] => 6767191901134cde92fb4ceef9c24e6f
    [utilityCode] => NACH00000000000019
    [categoryCode] => L002
    [schmNm] => Loan EMI
    [consRefNo] => CONS873ghSCONe73
    [seqTp] => RCUR
    [frqcy] => MNTH
    [frstColltnDt] => 2025-06-11
    [fnlColltnDt] => 2026-06-01
    [amountTp] => FIXA
    [colltnAmt] => 140
    [dbtrNm] => English5
    [mobile] => 9876543210
    [bnkId] => KCUB
    [dbtrAccTp] => SAVINGS
    [dbtrAccNo] => 123456789011
    [sourceReference] => SR78362786REF10
    [authMode] => DebitCard
    [mandateAction] => Create
    [productCode] => PROD-001
    [virtualAddress] => 
    [revokeable] => 
    [debitRule] => 
    [debitDay] => 
    [accountValidation] => 
)
key length = 32
iv length = 12
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authorize Mandate</title>
</head>
<body>
<form id="form" name="form" method="POST" action="http://localhost:4445/gateway/enc">
    <input type="hidden" id="clientId" name="clientId" value="ee0fc0fd-6772-4f39-8aac-c0bc7bed31bb">
    <input type="hidden" id="encData" name="encData" value="cYfHYgLwj3OKAaLwmB2/o4BrM6Rp5uA3ZZMmwtx83JjS6/xH/mKGdqDaiTaACmQs5Z531PJxy2ir8fjtVumrKIbev/UeCKyXHN3/Ky9BxrIPHGv1nbczNOFat9VmyEpeUJ41hh77f0UcI4C8p2gKAD/wSrkBLK1xYVODnhigkde1Nodfe+TfKAkHbnKoU+LxXG+Z54Upjr/Tyjx8BzdzO2QGk3khi8UqJgJ/WJAVFBjiymIrAl7uhWfaXNXC2W2FnGHmJGXcBcrWoCsDTDiKcTDpHmnFIp88RfzroF9IKjnc8nQsYqRcZpz0l88mUsNhLzvkLv34C6kjCuyll0ggdyC0u8mWbKIpcTSr0Dxi9M1J5RWbEzHsGXR+KOdSCG/LLMle/RWm9nPj15ulqMX/xRwb6+U1x+HRKm6g2u+GqybJh58Kk05a//n368B2GdcIpCl0NMlZwfe7jj/x2z3g5Z6Hh97LK3USTWqIhwfvnrLmx+Cmf4/4l0rPL/Hyo9pqq/HiD2NEBDJKN/gRcfdsDTiC0ouoZ7toK7qvTxv6uvN8AyiTMqwyR5xuIOEDYWG2TPr68JW+hXb2mxmqPppvej5NmQl6Ve+N+9UqCTAx6jMsJc1ID7AXoXy2wh6kJHpPzWX6kiCwW5Yq0H66uyz/nOcHfWopo/A73pHnqpdqFKpUv7ftXIkUqYZehcOCI8bOvgFgPBFOrWbtuXe3aXHjcDPgLknDswv8651Pc+uCg1+CdBiS7Ke9B5cEzBBHlYuUwYECrNaRia2KhrYy4RpUgomqnLfugLqEG2nBAd1g5Ah6x/Zydy8Ixg1fh78UgRSjJBA/Xv/RyiQe4/3ADoGWpw==">
</form>
</body>
<script type="text/javascript">
    var ready = (callback) => {
  if (document.readyState != "loading") callback();
  else document.addEventListener("DOMContentLoaded", callback);
}

ready(() => { 
    var form = document.getElementById('form');
   	    form.submit();
});
</script>
</html>