package com.hadit1993.realestate.api.security.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
public class JWTConfig {


    @Bean
    public KeyPair keyPair(@Value("${keystore.alias}")
                           String keystoreAlias,
                           @Value("${keystore.password}")
                           String keystorePassword)
            throws IOException,
            KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            UnrecoverableKeyException {

        var inputStream = new ClassPathResource("RealEstate.keystore").getInputStream();
        var password = keystorePassword.toCharArray();
        var keystore = KeyStore.getInstance("PKCS12");
        keystore.load(inputStream, password);

        var privateKey = (PrivateKey) keystore.getKey(keystoreAlias, password);
        var publicKey = keystore.getCertificate(keystoreAlias).getPublicKey();

        return new KeyPair(publicKey, privateKey);

    }
}


