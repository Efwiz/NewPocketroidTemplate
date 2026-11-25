package com.topjohnwu.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;

/* JADX INFO: loaded from: classes11.dex */
class CryptoUtils {
    private static final Map<String, String> ALG_TO_ID;
    private static final Map<String, String> ID_TO_ALG;

    CryptoUtils() {
    }

    static {
        HashMap map = new HashMap();
        ID_TO_ALG = map;
        HashMap map2 = new HashMap();
        ALG_TO_ID = map2;
        map.put(X9ObjectIdentifiers.ecdsa_with_SHA256.getId(), "SHA256withECDSA");
        map.put(X9ObjectIdentifiers.ecdsa_with_SHA384.getId(), "SHA384withECDSA");
        map.put(X9ObjectIdentifiers.ecdsa_with_SHA512.getId(), "SHA512withECDSA");
        map.put(PKCSObjectIdentifiers.sha1WithRSAEncryption.getId(), "SHA1withRSA");
        map.put(PKCSObjectIdentifiers.sha256WithRSAEncryption.getId(), "SHA256withRSA");
        map.put(PKCSObjectIdentifiers.sha512WithRSAEncryption.getId(), "SHA512withRSA");
        map2.put("SHA256withECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256.getId());
        map2.put("SHA384withECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384.getId());
        map2.put("SHA512withECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512.getId());
        map2.put("SHA1withRSA", PKCSObjectIdentifiers.sha1WithRSAEncryption.getId());
        map2.put("SHA256withRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption.getId());
        map2.put("SHA512withRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption.getId());
    }

    private static String getSignatureAlgorithm(Key key) throws Exception {
        int fieldSize;
        if ("EC".equals(key.getAlgorithm())) {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            if (key instanceof PublicKey) {
                fieldSize = ((ECPublicKeySpec) keyFactory.getKeySpec(key, ECPublicKeySpec.class)).getParams().getCurve().getField().getFieldSize();
            } else if (key instanceof PrivateKey) {
                fieldSize = ((ECPrivateKeySpec) keyFactory.getKeySpec(key, ECPrivateKeySpec.class)).getParams().getCurve().getField().getFieldSize();
            } else {
                throw new InvalidKeySpecException();
            }
            if (fieldSize <= 256) {
                return "SHA256withECDSA";
            }
            if (fieldSize <= 384) {
                return "SHA384withECDSA";
            }
            return "SHA512withECDSA";
        }
        if ("RSA".equals(key.getAlgorithm())) {
            return "SHA256withRSA";
        }
        throw new IllegalArgumentException("Unsupported key type " + key.getAlgorithm());
    }

    static AlgorithmIdentifier getSignatureAlgorithmIdentifier(Key key) throws Exception {
        String str = ALG_TO_ID.get(getSignatureAlgorithm(key));
        if (str == null) {
            throw new IllegalArgumentException("Unsupported key type " + key.getAlgorithm());
        }
        return new AlgorithmIdentifier(new ASN1ObjectIdentifier(str));
    }

    static boolean verify(PublicKey publicKey, byte[] bArr, byte[] bArr2, AlgorithmIdentifier algorithmIdentifier) throws Exception {
        String str = ID_TO_ALG.get(algorithmIdentifier.getAlgorithm().getId());
        if (str == null) {
            throw new IllegalArgumentException("Unsupported algorithm " + algorithmIdentifier.getAlgorithm());
        }
        Signature signature = Signature.getInstance(str);
        signature.initVerify(publicKey);
        signature.update(bArr);
        return signature.verify(bArr2);
    }

    static byte[] sign(PrivateKey privateKey, byte[] bArr) throws Exception {
        Signature signature = Signature.getInstance(getSignatureAlgorithm(privateKey));
        signature.initSign(privateKey);
        signature.update(bArr);
        return signature.sign();
    }

    static X509Certificate readCertificate(InputStream inputStream) throws GeneralSecurityException, IOException {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
        } finally {
            inputStream.close();
        }
    }

    static PrivateKey readPrivateKey(InputStream inputStream) throws GeneralSecurityException, IOException {
        try {
            ByteArrayStream byteArrayStream = new ByteArrayStream();
            byteArrayStream.readFrom(inputStream);
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(byteArrayStream.toByteArray());
            return KeyFactory.getInstance(PrivateKeyInfo.getInstance(new ASN1InputStream(new ByteArrayInputStream(pKCS8EncodedKeySpec.getEncoded())).readObject()).getPrivateKeyAlgorithm().getAlgorithm().getId()).generatePrivate(pKCS8EncodedKeySpec);
        } finally {
            inputStream.close();
        }
    }
}
