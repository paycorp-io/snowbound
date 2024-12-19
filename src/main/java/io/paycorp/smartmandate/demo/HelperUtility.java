package io.paycorp.smartmandate.demo;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelperUtility {
   private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;
    private static final int GCM_IV_LENGTH = 12;

    private static SecureRandom random = new SecureRandom();

    public static void requireNotEmpty(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static Optional<String> encrypt(String key, String data) {
        requireNotEmpty(key, "Key cannot be null or empty");
        requireNotEmpty(data, "Data cannot be null or empty");
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            log.info("Key bytes: {}", keyBytes.length);
            byte[] dataBytes = data.getBytes();
            byte[] iv = new byte[GCM_IV_LENGTH];
            random.nextBytes(iv);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
            byte[] encrypted = cipher.doFinal(dataBytes);
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
            return Optional.of(Base64.getEncoder().encodeToString(result));
        } catch (Exception e) {
            log.error("Encryption failed with exception: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> decrypt(String key, String data) {
        requireNotEmpty(key, "Key cannot be null or empty");
        requireNotEmpty(data, "Data cannot be null or empty");
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] dataBytes = Base64.getDecoder().decode(data);
            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] encrypted = new byte[dataBytes.length - iv.length];
            System.arraycopy(dataBytes, 0, iv, 0, iv.length);
            System.arraycopy(dataBytes, iv.length, encrypted, 0, encrypted.length);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return Optional.of(new String(decrypted));
        } catch (Exception e) {
            log.error("Decryption failed: {} ", e.getMessage());
            return Optional.empty();
        }
    } 
}
