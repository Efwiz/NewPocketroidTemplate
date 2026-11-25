package com.hivemq.client.util;

import com.hivemq.client.internal.util.Checks;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes10.dex */
public class KeyStoreUtil {
    private static final String KEYSTORE_TYPE = "JKS";

    public static TrustManagerFactory trustManagerFromKeystore(File file, String str) throws SSLException {
        Checks.notNull(file, "Trust store file");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
                keyStore.load(fileInputStream, str.toCharArray());
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                fileInputStream.close();
                return trustManagerFactory;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        } catch (IOException e) {
            e = e;
            throw new SSLException("Not able to open or read trust store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        } catch (KeyStoreException e2) {
            e = e2;
            throw new SSLException("Not able to open or read trust store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        } catch (NoSuchAlgorithmException e3) {
            e = e3;
            throw new SSLException("Not able to read certificate from trust store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        } catch (CertificateException e4) {
            e = e4;
            throw new SSLException("Not able to read certificate from trust store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        }
    }

    public static KeyManagerFactory keyManagerFromKeystore(File file, String str, String str2) throws SSLException {
        Checks.notNull(file, "Key store file");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
                keyStore.load(fileInputStream, str.toCharArray());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, str2.toCharArray());
                fileInputStream.close();
                return keyManagerFactory;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        } catch (IOException e) {
            e = e;
            throw new SSLException("Not able to open or read key store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        } catch (KeyStoreException e2) {
            e = e2;
            throw new SSLException("Not able to open or read key store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        } catch (NoSuchAlgorithmException e3) {
            e = e3;
            throw new SSLException("Not able to read certificate from key store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        } catch (UnrecoverableKeyException e4) {
            throw new SSLException("Not able to recover key from key store, please check your private key password and your key store password", e4);
        } catch (CertificateException e5) {
            e = e5;
            throw new SSLException("Not able to read certificate from key store '" + file.getAbsolutePath() + FormatHelper.QUOTE, e);
        }
    }
}
