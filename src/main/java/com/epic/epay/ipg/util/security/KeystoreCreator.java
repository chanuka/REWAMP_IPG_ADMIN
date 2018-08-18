/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.security;

import com.epic.epay.ipg.bean.util.security.KeystoreCreatorBean;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.X509V3CertificateGenerator;

/**
 * Date 2014-10-02
 *
 * @author Asela Indika
 */
public class KeystoreCreator {

    // create keystore and certificate
    public void CreateKeystoreWithCertificate(KeystoreCreatorBean bean) throws Exception {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // GENERATE THE X509 CERTIFICATE
            X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
            v3CertGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
            v3CertGen.setIssuerDN(new X509Principal("CN=Epic Lanka, O=Software, L=Battaramulla, ST=Western, C=SL"));
            v3CertGen.setNotBefore(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24));
            v3CertGen.setNotAfter(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 1)));
            v3CertGen.setSubjectDN(new X509Principal("CN=" + bean.getName() + ", O=" + bean.getOrganizationalUnitName() + ", L=" + bean.getCity() + ", ST=" + bean.getProvince() + ", C=" + bean.getTwo_letter_countrycode()));
            v3CertGen.setPublicKey(keyPair.getPublic());
            v3CertGen.setSignatureAlgorithm("SHA1WithRSAEncryption");
            X509Certificate cert = v3CertGen.generateX509Certificate(keyPair.getPrivate());

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);
            keyStore.setKeyEntry(bean.getAlias(), keyPair.getPrivate(), bean.getAliaspassword().toCharArray(), new java.security.cert.Certificate[]{cert});
            File file = new File(bean.getKeystorepath());
            keyStore.store(new FileOutputStream(file), bean.getKeystorepassword().toCharArray());

            FileOutputStream fos = new FileOutputStream(bean.getCertificatepath());
            fos.write(cert.getEncoded());
            fos.close();

        } catch (Exception ex) {
            throw ex;
        }

    }

}
