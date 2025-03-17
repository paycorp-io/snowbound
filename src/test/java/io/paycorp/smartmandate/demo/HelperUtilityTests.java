package io.paycorp.smartmandate.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class HelperUtilityTests {

    @Test
    public void testEncrypt() {
        String key = "et6DhZzAqMfh3Im045rL82aUrCt8Bn+SjjDZWs/S8LE=";
        String data = "Hello, World!";
        Optional<String> encryptedData = HelperUtility.encrypt(key, data);
        assertTrue(encryptedData.isPresent());
    }

    @Test
    public void testDecrypt() {
        String key = "et6DhZzAqMfh3Im045rL82aUrCt8Bn+SjjDZWs/S8LE=";
        String data = "Hello, World!";
        Optional<String> encryptedData = HelperUtility.encrypt(key, data);
        assertTrue(encryptedData.isPresent());
        Optional<String> decryptedData = HelperUtility.decrypt(key, encryptedData.get());
        assertTrue(decryptedData.isPresent());
        assertEquals(data, decryptedData.get());
    }
}
