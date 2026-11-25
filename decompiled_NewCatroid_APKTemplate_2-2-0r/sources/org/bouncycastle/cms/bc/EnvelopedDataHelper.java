package org.bouncycastle.cms.bc;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.xerces.dom3.as.ASDataType;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.bouncycastle.asn1.misc.CAST5CBCParameters;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RC2CBCParameter;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.CAST5Engine;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.RC2Engine;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.bouncycastle.crypto.generators.DESKeyGenerator;
import org.bouncycastle.crypto.generators.DESedeKeyGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestProvider;

/* JADX INFO: loaded from: classes13.dex */
class EnvelopedDataHelper {
    protected static final Map BASE_CIPHER_NAMES;
    protected static final Map MAC_ALG_NAMES;
    private static final Map prfs;
    private static final short[] rc2Ekb;
    private static final short[] rc2Table;

    static {
        HashMap map = new HashMap();
        BASE_CIPHER_NAMES = map;
        HashMap map2 = new HashMap();
        MAC_ALG_NAMES = map2;
        prfs = createTable();
        map.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDE");
        map.put(CMSAlgorithm.AES128_CBC, "AES");
        map.put(CMSAlgorithm.AES192_CBC, "AES");
        map.put(CMSAlgorithm.AES256_CBC, "AES");
        map2.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDEMac");
        map2.put(CMSAlgorithm.AES128_CBC, "AESMac");
        map2.put(CMSAlgorithm.AES192_CBC, "AESMac");
        map2.put(CMSAlgorithm.AES256_CBC, "AESMac");
        map2.put(CMSAlgorithm.RC2_CBC, "RC2Mac");
        rc2Table = new short[]{189, 86, 234, 242, 162, 241, 172, 42, 176, 147, ASDataType.SHORT_DATATYPE, 156, 27, 51, 253, ASDataType.INT_DATATYPE, 48, 4, 182, 220, 125, 223, 50, 75, 247, ASDataType.TOKEN_DATATYPE, 69, 155, 49, 187, 33, 90, 65, 159, 225, 217, 74, 77, 158, 218, 160, ASDataType.HEXBINARY_DATATYPE, 44, 195, 39, 95, 128, 54, 62, 238, 251, 149, 26, 254, ASDataType.NEGATIVEINTEGER_DATATYPE, 168, 52, 169, 19, 240, 166, 63, ASDataType.POSITIVEINTEGER_DATATYPE, 12, 120, 36, 175, 35, 82, 193, 103, 23, 245, 102, 144, 231, 232, 7, 184, 96, 72, 230, 30, 83, 243, 146, 164, 114, 140, 8, 21, 110, 134, 0, 132, 250, 244, 127, 138, 66, 25, 246, 219, ASDataType.NONPOSITIVEINTEGER_DATATYPE, 20, 141, 80, 18, 186, 60, 6, 78, 236, 179, 53, 17, 161, 136, 142, 43, 148, 153, 183, 113, ASDataType.GMONTH_DATATYPE, ASDataType.NONNEGATIVEINTEGER_DATATYPE, 228, 191, 58, 222, 150, 14, 188, 10, 237, 119, 252, 55, ASDataType.QNAME_DATATYPE, 3, 121, 137, 98, 198, ASDataType.UNSIGNEDBYTE_DATATYPE, 192, ASDataType.BYTE_DATATYPE, 124, ASDataType.ANYURI_DATATYPE, 139, 34, 163, 91, 5, 93, 2, ASDataType.INTEGER, ASDataType.UNSIGNEDINT_DATATYPE, 97, 227, 24, 143, 85, 81, 173, 31, 11, 94, 133, 229, 194, 87, 99, ASDataType.NORMALIZEDSTRING_DATATYPE, 61, ASDataType.DURATION_DATATYPE, 180, 197, ASDataType.LANGUAGE_DATATYPE, 112, 178, 145, 89, 13, 71, 32, ASDataType.NAME_DATATYPE, 79, 88, 224, 1, 226, 22, 56, 196, 111, 59, 15, 101, 70, 190, 126, 45, 123, 130, 249, 64, 181, 29, 115, 248, 235, 38, 199, 135, 151, 37, 84, 177, 40, 170, 152, 157, 165, 100, ASDataType.DATETIME_DATATYPE, 122, ASDataType.UNSIGNEDLONG_DATATYPE, 16, 129, 68, 239, 73, ASDataType.UNSIGNEDSHORT_DATATYPE, 174, 46, 221, 118, 92, 47, 167, 28, ASDataType.NCNAME_DATATYPE, 9, ASDataType.BASE64BINARY_DATATYPE, 154, 131, ASDataType.LONG_DATATYPE, 41, 57, 185, 233, 76, 255, 67, 171};
        rc2Ekb = new short[]{93, 190, 155, 139, 17, 153, 110, 77, 89, 243, 133, 166, 63, 183, 131, 197, 228, 115, ASDataType.QNAME_DATATYPE, 58, ASDataType.HEXBINARY_DATATYPE, 90, 192, 71, 160, 100, 52, 12, 241, ASDataType.INT_DATATYPE, 82, 165, 185, 30, 150, 67, 65, ASDataType.POSITIVEINTEGER_DATATYPE, ASDataType.UNSIGNEDLONG_DATATYPE, 44, 219, 248, 7, 119, 42, ASDataType.NORMALIZEDSTRING_DATATYPE, 235, 239, 16, 28, 22, 13, 56, 114, 47, 137, 193, 249, 128, 196, ASDataType.DATETIME_DATATYPE, 174, 48, 61, ASDataType.NEGATIVEINTEGER_DATATYPE, 32, 99, 254, 230, 26, 199, 184, 80, 232, 36, 23, 252, 37, 111, 187, ASDataType.ANYURI_DATATYPE, 163, 68, 83, 217, 162, 1, 171, 188, 182, 31, 152, 238, 154, 167, 45, 79, 158, 142, 172, 224, 198, 73, 70, 41, 244, 148, 138, 175, 225, 91, 195, 179, 123, 87, ASDataType.SHORT_DATATYPE, 124, 156, 237, 135, 64, 140, 226, ASDataType.TOKEN_DATATYPE, 147, 20, ASDataType.NCNAME_DATATYPE, 97, 46, 229, ASDataType.LANGUAGE_DATATYPE, 246, 94, 168, 92, ASDataType.UNSIGNEDSHORT_DATATYPE, ASDataType.INTEGER, 141, 98, 149, 88, ASDataType.BASE64BINARY_DATATYPE, 118, 161, 74, 181, 85, 9, 120, 51, 130, ASDataType.UNSIGNEDBYTE_DATATYPE, 221, 121, 245, 27, 11, 222, 38, 33, 40, ASDataType.GMONTH_DATATYPE, 4, 151, 86, 223, 60, 240, 55, 57, 220, 255, 6, 164, 234, 66, 8, 218, 180, 113, 176, ASDataType.LONG_DATATYPE, 18, 122, 78, 250, ASDataType.DURATION_DATATYPE, 29, 132, 0, ASDataType.NAME_DATATYPE, 127, 145, 69, 170, 43, 194, 177, 143, ASDataType.UNSIGNEDINT_DATATYPE, 186, 242, 173, 25, 178, 103, 54, 247, 15, 10, 146, 125, 227, 157, 233, 144, 62, 35, 39, 102, 19, 236, 129, 21, 189, 34, 191, 159, 126, 169, 81, 75, 76, 251, 2, ASDataType.NONNEGATIVEINTEGER_DATATYPE, 112, 134, 49, 231, 59, 5, 3, 84, 96, 72, 101, 24, ASDataType.BYTE_DATATYPE, ASDataType.NONPOSITIVEINTEGER_DATATYPE, 95, 50, 136, 14, 53, 253};
    }

    EnvelopedDataHelper() {
    }

    static BufferedBlockCipher createCipher(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws CMSException {
        CBCBlockCipher cBCBlockCipher;
        if (NISTObjectIdentifiers.id_aes128_CBC.equals(aSN1ObjectIdentifier) || NISTObjectIdentifiers.id_aes192_CBC.equals(aSN1ObjectIdentifier) || NISTObjectIdentifiers.id_aes256_CBC.equals(aSN1ObjectIdentifier)) {
            cBCBlockCipher = new CBCBlockCipher(new AESEngine());
        } else if (PKCSObjectIdentifiers.des_EDE3_CBC.equals(aSN1ObjectIdentifier)) {
            cBCBlockCipher = new CBCBlockCipher(new DESedeEngine());
        } else if (OIWObjectIdentifiers.desCBC.equals(aSN1ObjectIdentifier)) {
            cBCBlockCipher = new CBCBlockCipher(new DESEngine());
        } else if (PKCSObjectIdentifiers.RC2_CBC.equals(aSN1ObjectIdentifier)) {
            cBCBlockCipher = new CBCBlockCipher(new RC2Engine());
        } else {
            if (!MiscObjectIdentifiers.cast5CBC.equals(aSN1ObjectIdentifier)) {
                throw new CMSException("cannot recognise cipher: " + aSN1ObjectIdentifier);
            }
            cBCBlockCipher = new CBCBlockCipher(new CAST5Engine());
        }
        return new PaddedBufferedBlockCipher(cBCBlockCipher, new PKCS7Padding());
    }

    private CipherKeyGenerator createCipherKeyGenerator(SecureRandom secureRandom, int i) {
        CipherKeyGenerator cipherKeyGenerator = new CipherKeyGenerator();
        cipherKeyGenerator.init(new KeyGenerationParameters(secureRandom, i));
        return cipherKeyGenerator;
    }

    static Object createContentCipher(boolean z, CipherParameters cipherParameters, AlgorithmIdentifier algorithmIdentifier) throws CMSException {
        ParametersWithIV parametersWithIV;
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        if (algorithm.equals(PKCSObjectIdentifiers.rc4)) {
            RC4Engine rC4Engine = new RC4Engine();
            rC4Engine.init(z, cipherParameters);
            return rC4Engine;
        }
        BufferedBlockCipher bufferedBlockCipherCreateCipher = createCipher(algorithmIdentifier.getAlgorithm());
        ASN1Primitive aSN1Primitive = algorithmIdentifier.getParameters().toASN1Primitive();
        if (aSN1Primitive != null && !(aSN1Primitive instanceof ASN1Null)) {
            if (algorithm.equals(CMSAlgorithm.DES_EDE3_CBC) || algorithm.equals(CMSAlgorithm.IDEA_CBC) || algorithm.equals(CMSAlgorithm.AES128_CBC) || algorithm.equals(CMSAlgorithm.AES192_CBC) || algorithm.equals(CMSAlgorithm.AES256_CBC) || algorithm.equals(CMSAlgorithm.CAMELLIA128_CBC) || algorithm.equals(CMSAlgorithm.CAMELLIA192_CBC) || algorithm.equals(CMSAlgorithm.CAMELLIA256_CBC) || algorithm.equals(CMSAlgorithm.SEED_CBC) || algorithm.equals(OIWObjectIdentifiers.desCBC)) {
                parametersWithIV = new ParametersWithIV(cipherParameters, ASN1OctetString.getInstance(aSN1Primitive).getOctets());
            } else if (algorithm.equals(CMSAlgorithm.CAST5_CBC)) {
                parametersWithIV = new ParametersWithIV(cipherParameters, CAST5CBCParameters.getInstance(aSN1Primitive).getIV());
            } else {
                if (!algorithm.equals(CMSAlgorithm.RC2_CBC)) {
                    throw new CMSException("cannot match parameters");
                }
                RC2CBCParameter rC2CBCParameter = RC2CBCParameter.getInstance(aSN1Primitive);
                parametersWithIV = new ParametersWithIV(new RC2Parameters(((KeyParameter) cipherParameters).getKey(), rc2Ekb[rC2CBCParameter.getRC2ParameterVersion().intValue()]), rC2CBCParameter.getIV());
            }
            bufferedBlockCipherCreateCipher.init(z, parametersWithIV);
        } else if (algorithm.equals(CMSAlgorithm.DES_EDE3_CBC) || algorithm.equals(CMSAlgorithm.IDEA_CBC) || algorithm.equals(CMSAlgorithm.CAST5_CBC)) {
            bufferedBlockCipherCreateCipher.init(z, new ParametersWithIV(cipherParameters, new byte[8]));
        } else {
            bufferedBlockCipherCreateCipher.init(z, cipherParameters);
        }
        return bufferedBlockCipherCreateCipher;
    }

    static Wrapper createRFC3211Wrapper(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws CMSException {
        if (NISTObjectIdentifiers.id_aes128_CBC.equals(aSN1ObjectIdentifier) || NISTObjectIdentifiers.id_aes192_CBC.equals(aSN1ObjectIdentifier) || NISTObjectIdentifiers.id_aes256_CBC.equals(aSN1ObjectIdentifier)) {
            return new RFC3211WrapEngine(new AESEngine());
        }
        if (PKCSObjectIdentifiers.des_EDE3_CBC.equals(aSN1ObjectIdentifier)) {
            return new RFC3211WrapEngine(new DESedeEngine());
        }
        if (OIWObjectIdentifiers.desCBC.equals(aSN1ObjectIdentifier)) {
            return new RFC3211WrapEngine(new DESEngine());
        }
        if (PKCSObjectIdentifiers.RC2_CBC.equals(aSN1ObjectIdentifier)) {
            return new RFC3211WrapEngine(new RC2Engine());
        }
        throw new CMSException("cannot recognise wrapper: " + aSN1ObjectIdentifier);
    }

    private static Map createTable() {
        HashMap map = new HashMap();
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA1, new BcDigestProvider() { // from class: org.bouncycastle.cms.bc.EnvelopedDataHelper.1
            @Override // org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA1Digest();
            }
        });
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA224, new BcDigestProvider() { // from class: org.bouncycastle.cms.bc.EnvelopedDataHelper.2
            @Override // org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA224Digest();
            }
        });
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA256, new BcDigestProvider() { // from class: org.bouncycastle.cms.bc.EnvelopedDataHelper.3
            @Override // org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA256Digest();
            }
        });
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA384, new BcDigestProvider() { // from class: org.bouncycastle.cms.bc.EnvelopedDataHelper.4
            @Override // org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA384Digest();
            }
        });
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA512, new BcDigestProvider() { // from class: org.bouncycastle.cms.bc.EnvelopedDataHelper.5
            @Override // org.bouncycastle.operator.bc.BcDigestProvider
            public ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) {
                return new SHA512Digest();
            }
        });
        return Collections.unmodifiableMap(map);
    }

    static ExtendedDigest getPRF(AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException {
        return ((BcDigestProvider) prfs.get(algorithmIdentifier.getAlgorithm())).get(null);
    }

    CipherKeyGenerator createKeyGenerator(ASN1ObjectIdentifier aSN1ObjectIdentifier, SecureRandom secureRandom) throws CMSException {
        if (NISTObjectIdentifiers.id_aes128_CBC.equals(aSN1ObjectIdentifier)) {
            return createCipherKeyGenerator(secureRandom, 128);
        }
        if (NISTObjectIdentifiers.id_aes192_CBC.equals(aSN1ObjectIdentifier)) {
            return createCipherKeyGenerator(secureRandom, 192);
        }
        if (NISTObjectIdentifiers.id_aes256_CBC.equals(aSN1ObjectIdentifier)) {
            return createCipherKeyGenerator(secureRandom, 256);
        }
        if (PKCSObjectIdentifiers.des_EDE3_CBC.equals(aSN1ObjectIdentifier)) {
            DESedeKeyGenerator dESedeKeyGenerator = new DESedeKeyGenerator();
            dESedeKeyGenerator.init(new KeyGenerationParameters(secureRandom, 192));
            return dESedeKeyGenerator;
        }
        if (NTTObjectIdentifiers.id_camellia128_cbc.equals(aSN1ObjectIdentifier)) {
            return createCipherKeyGenerator(secureRandom, 128);
        }
        if (NTTObjectIdentifiers.id_camellia192_cbc.equals(aSN1ObjectIdentifier)) {
            return createCipherKeyGenerator(secureRandom, 192);
        }
        if (NTTObjectIdentifiers.id_camellia256_cbc.equals(aSN1ObjectIdentifier)) {
            return createCipherKeyGenerator(secureRandom, 256);
        }
        if (!KISAObjectIdentifiers.id_seedCBC.equals(aSN1ObjectIdentifier) && !CMSAlgorithm.CAST5_CBC.equals(aSN1ObjectIdentifier)) {
            if (OIWObjectIdentifiers.desCBC.equals(aSN1ObjectIdentifier)) {
                DESKeyGenerator dESKeyGenerator = new DESKeyGenerator();
                dESKeyGenerator.init(new KeyGenerationParameters(secureRandom, 64));
                return dESKeyGenerator;
            }
            if (!PKCSObjectIdentifiers.rc4.equals(aSN1ObjectIdentifier) && !PKCSObjectIdentifiers.RC2_CBC.equals(aSN1ObjectIdentifier)) {
                throw new CMSException("cannot recognise cipher: " + aSN1ObjectIdentifier);
            }
            return createCipherKeyGenerator(secureRandom, 128);
        }
        return createCipherKeyGenerator(secureRandom, 128);
    }

    AlgorithmIdentifier generateAlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, CipherParameters cipherParameters, SecureRandom secureRandom) throws CMSException {
        if (aSN1ObjectIdentifier.equals(CMSAlgorithm.AES128_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.AES192_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.AES256_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.CAMELLIA128_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.CAMELLIA192_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.CAMELLIA256_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.SEED_CBC)) {
            byte[] bArr = new byte[16];
            secureRandom.nextBytes(bArr);
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, new DEROctetString(bArr));
        }
        if (aSN1ObjectIdentifier.equals(CMSAlgorithm.DES_EDE3_CBC) || aSN1ObjectIdentifier.equals(CMSAlgorithm.IDEA_CBC) || aSN1ObjectIdentifier.equals(OIWObjectIdentifiers.desCBC)) {
            byte[] bArr2 = new byte[8];
            secureRandom.nextBytes(bArr2);
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, new DEROctetString(bArr2));
        }
        if (aSN1ObjectIdentifier.equals(CMSAlgorithm.CAST5_CBC)) {
            byte[] bArr3 = new byte[8];
            secureRandom.nextBytes(bArr3);
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, new CAST5CBCParameters(bArr3, ((KeyParameter) cipherParameters).getKey().length * 8));
        }
        if (aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.rc4)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
        }
        if (!aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.RC2_CBC)) {
            throw new CMSException("unable to match algorithm");
        }
        byte[] bArr4 = new byte[8];
        secureRandom.nextBytes(bArr4);
        return new AlgorithmIdentifier(aSN1ObjectIdentifier, new RC2CBCParameter(rc2Table[128], bArr4));
    }

    String getBaseCipherName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) BASE_CIPHER_NAMES.get(aSN1ObjectIdentifier);
        return str == null ? aSN1ObjectIdentifier.getId() : str;
    }
}
