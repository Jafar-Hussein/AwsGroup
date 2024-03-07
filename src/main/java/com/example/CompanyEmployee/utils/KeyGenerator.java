package com.example.CompanyEmployee.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Denna klass används för att generera RSA-nyckelpar.
 * @author Fredrik
 */
public class KeyGenerator {

    /**
     * Genererar ett RSA-nyckelpar.
     * @return Ett RSA-nyckelpar.
     * @throws IllegalStateException Om det uppstår ett fel under genereringen.
     */
    public static KeyPair generateRsaKey() {

        KeyPair keyPair;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.genKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        return keyPair;
    }
}
