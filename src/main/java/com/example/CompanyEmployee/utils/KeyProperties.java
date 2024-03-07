package com.example.CompanyEmployee.utils;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Denna klass hanterar nyckel egenskaper för RSA-kryptering.
 * Den genererar och lagrar en RSA-nyckelpar vid skapandet.
 * @author Fredrik
 */
@Component
public class KeyProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    /**
     * Konstruktor för KeyProperties.
     * Genererar ett RSA-nyckelpar vid skapandet.
     */
    public KeyProperties() {
        KeyPair keyPair = KeyGenerator.generateRsaKey();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }
    /**
     * Hämtar den offentliga nyckeln.
     * @return Den offentliga nyckeln.
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Sätter den offentliga nyckeln.
     * @param publicKey Den offentliga nyckeln.
     */
    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Hämtar den privata nyckeln.
     * @return Den privata nyckeln.
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * Sätter den privata nyckeln.
     * @param privateKey Den privata nyckeln.
     */
    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
