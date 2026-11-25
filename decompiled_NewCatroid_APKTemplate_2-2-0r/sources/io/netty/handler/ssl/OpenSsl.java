package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.CertificateCallback;
import io.netty.internal.tcnative.Library;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public final class OpenSsl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Set<String> AVAILABLE_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
    private static final String CERT = "-----BEGIN CERTIFICATE-----\nMIICrjCCAZagAwIBAgIIdSvQPv1QAZQwDQYJKoZIhvcNAQELBQAwFjEUMBIGA1UEAxMLZXhhbXBs\nZS5jb20wIBcNMTgwNDA2MjIwNjU5WhgPOTk5OTEyMzEyMzU5NTlaMBYxFDASBgNVBAMTC2V4YW1w\nbGUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAggbWsmDQ6zNzRZ5AW8E3eoGl\nqWvOBDb5Fs1oBRrVQHuYmVAoaqwDzXYJ0LOwa293AgWEQ1jpcbZ2hpoYQzqEZBTLnFhMrhRFlH6K\nbJND8Y33kZ/iSVBBDuGbdSbJShlM+4WwQ9IAso4MZ4vW3S1iv5fGGpLgbtXRmBf/RU8omN0Gijlv\nWlLWHWijLN8xQtySFuBQ7ssW8RcKAary3pUm6UUQB+Co6lnfti0Tzag8PgjhAJq2Z3wbsGRnP2YS\nvYoaK6qzmHXRYlp/PxrjBAZAmkLJs4YTm/XFF+fkeYx4i9zqHbyone5yerRibsHaXZWLnUL+rFoe\nMdKvr0VS3sGmhQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQADQi441pKmXf9FvUV5EHU4v8nJT9Iq\nyqwsKwXnr7AsUlDGHBD7jGrjAXnG5rGxuNKBQ35wRxJATKrUtyaquFUL6H8O6aGQehiFTk6zmPbe\n12Gu44vqqTgIUxnv3JQJiox8S2hMxsSddpeCmSdvmalvD6WG4NthH6B9ZaBEiep1+0s0RUaBYn73\nI7CCUaAtbjfR6pcJjrFk5ei7uwdQZFSJtkP2z8r7zfeANJddAKFlkaMWn7u+OIVuB4XPooWicObk\nNAHFtP65bocUYnDpTVdiyvn8DdqyZ/EO8n1bBKBzuSLplk2msW4pdgaFgY7Vw/0wzcFXfUXmL1uy\nG8sQD/wx\n-----END CERTIFICATE-----";
    static final List<String> DEFAULT_CIPHERS;
    private static final boolean IS_BORINGSSL;
    private static final String KEY = "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCBtayYNDrM3NFnkBbwTd6gaWp\na84ENvkWzWgFGtVAe5iZUChqrAPNdgnQs7Brb3cCBYRDWOlxtnaGmhhDOoRkFMucWEyuFEWUfops\nk0PxjfeRn+JJUEEO4Zt1JslKGUz7hbBD0gCyjgxni9bdLWK/l8YakuBu1dGYF/9FTyiY3QaKOW9a\nUtYdaKMs3zFC3JIW4FDuyxbxFwoBqvLelSbpRRAH4KjqWd+2LRPNqDw+COEAmrZnfBuwZGc/ZhK9\nihorqrOYddFiWn8/GuMEBkCaQsmzhhOb9cUX5+R5jHiL3OodvKid7nJ6tGJuwdpdlYudQv6sWh4x\n0q+vRVLewaaFAgMBAAECggEAP8tPJvFtTxhNJAkCloHz0D0vpDHqQBMgntlkgayqmBqLwhyb18pR\ni0qwgh7HHc7wWqOOQuSqlEnrWRrdcI6TSe8R/sErzfTQNoznKWIPYcI/hskk4sdnQ//Yn9/Jvnsv\nU/BBjOTJxtD+sQbhAl80JcA3R+5sArURQkfzzHOL/YMqzAsn5hTzp7HZCxUqBk3KaHRxV7NefeOE\nxlZuWSmxYWfbFIs4kx19/1t7h8CHQWezw+G60G2VBtSBBxDnhBWvqG6R/wpzJ3nEhPLLY9T+XIHe\nipzdMOOOUZorfIg7M+pyYPji+ZIZxIpY5OjrOzXHciAjRtr5Y7l99K1CG1LguQKBgQDrQfIMxxtZ\nvxU/1cRmUV9l7pt5bjV5R6byXq178LxPKVYNjdZ840Q0/OpZEVqaT1xKVi35ohP1QfNjxPLlHD+K\niDAR9z6zkwjIrbwPCnb5kuXy4lpwPcmmmkva25fI7qlpHtbcuQdoBdCfr/KkKaUCMPyY89LCXgEw\n5KTDj64UywKBgQCNfbO+eZLGzhiHhtNJurresCsIGWlInv322gL8CSfBMYl6eNfUTZvUDdFhPISL\nUljKWzXDrjw0ujFSPR0XhUGtiq89H+HUTuPPYv25gVXO+HTgBFZEPl4PpA+BUsSVZy0NddneyqLk\n42Wey9omY9Q8WsdNQS5cbUvy0uG6WFoX7wKBgQDZ1jpW8pa0x2bZsQsm4vo+3G5CRnZlUp+XlWt2\ndDcp5dC0xD1zbs1dc0NcLeGDOTDv9FSl7hok42iHXXq8AygjEm/QcuwwQ1nC2HxmQP5holAiUs4D\nWHM8PWs3wFYPzE459EBoKTxeaeP/uWAn+he8q7d5uWvSZlEcANs/6e77eQKBgD21Ar0hfFfj7mK8\n9E0FeRZBsqK3omkfnhcYgZC11Xa2SgT1yvs2Va2n0RcdM5kncr3eBZav2GYOhhAdwyBM55XuE/sO\neokDVutNeuZ6d5fqV96TRaRBpvgfTvvRwxZ9hvKF4Vz+9wfn/JvCwANaKmegF6ejs7pvmF3whq2k\ndrZVAoGAX5YxQ5XMTD0QbMAl7/6qp6S58xNoVdfCkmkj1ZLKaHKIjS/benkKGlySVQVPexPfnkZx\np/Vv9yyphBoudiTBS9Uog66ueLYZqpgxlM/6OhYg86Gm3U2ycvMxYjBM1NFiyze21AqAhI+HX+Ot\nmraV2/guSgDgZAhukRZzeQ2RucI=\n-----END PRIVATE KEY-----";
    static final Set<String> SUPPORTED_PROTOCOLS_SET;
    private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
    private static final boolean SUPPORTS_OCSP;
    private static final boolean TLSV13_SUPPORTED;
    private static final Throwable UNAVAILABILITY_CAUSE;
    private static final boolean USE_KEYMANAGER_FACTORY;
    private static final InternalLogger logger;

    /* JADX WARN: Code duplicated, block: B:114:0x0221 A[Catch: all -> 0x026f, TryCatch #7 {all -> 0x026f, blocks: (B:112:0x021a, B:114:0x0221, B:117:0x0228, B:120:0x022f, B:123:0x0236, B:133:0x024f, B:135:0x0256, B:138:0x025d, B:141:0x0264, B:144:0x026b, B:145:0x026e), top: B:194:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:117:0x0228 A[Catch: all -> 0x026f, TryCatch #7 {all -> 0x026f, blocks: (B:112:0x021a, B:114:0x0221, B:117:0x0228, B:120:0x022f, B:123:0x0236, B:133:0x024f, B:135:0x0256, B:138:0x025d, B:141:0x0264, B:144:0x026b, B:145:0x026e), top: B:194:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:120:0x022f A[Catch: all -> 0x026f, TryCatch #7 {all -> 0x026f, blocks: (B:112:0x021a, B:114:0x0221, B:117:0x0228, B:120:0x022f, B:123:0x0236, B:133:0x024f, B:135:0x0256, B:138:0x025d, B:141:0x0264, B:144:0x026b, B:145:0x026e), top: B:194:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:123:0x0236 A[Catch: all -> 0x026f, TRY_LEAVE, TryCatch #7 {all -> 0x026f, blocks: (B:112:0x021a, B:114:0x0221, B:117:0x0228, B:120:0x022f, B:123:0x0236, B:133:0x024f, B:135:0x0256, B:138:0x025d, B:141:0x0264, B:144:0x026b, B:145:0x026e), top: B:194:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:160:0x029f  */
    /* JADX WARN: Code duplicated, block: B:166:0x0309  */
    /* JADX WARN: Code duplicated, block: B:169:0x0316  */
    /* JADX WARN: Code duplicated, block: B:172:0x0323  */
    /* JADX WARN: Code duplicated, block: B:175:0x0332  */
    /* JADX WARN: Code duplicated, block: B:178:0x0341  */
    /* JADX WARN: Code duplicated, block: B:183:0x035a  */
    /* JADX WARN: Code duplicated, block: B:186:0x0370  */
    /* JADX WARN: Code duplicated, block: B:237:0x02be A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:239:0x02ab A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:243:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    static {
        Throwable th;
        boolean z;
        boolean z2;
        LinkedHashSet linkedHashSet;
        List<String> listUnmodifiableList;
        LinkedHashSet linkedHashSet2;
        Set<String> setUnmodifiableSet;
        InternalLogger internalLogger;
        long j;
        long bio;
        long x509Chain;
        long privateKey;
        long j2;
        boolean z3;
        long bio2;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        String str = "io.netty.handler.ssl.openssl.useKeyManagerFactory";
        InternalLogger internalLoggerFactory = InternalLoggerFactory.getInstance((Class<?>) OpenSsl.class);
        logger = internalLoggerFactory;
        boolean z11 = false;
        if (SystemPropertyUtil.getBoolean("io.netty.handler.ssl.noOpenSsl", false)) {
            e = new UnsupportedOperationException("OpenSSL was explicit disabled with -Dio.netty.handler.ssl.noOpenSsl=true");
            internalLoggerFactory.debug("netty-tcnative explicit disabled; OpenSslEngine will be unavailable.", e);
        } else {
            try {
                Class.forName("io.netty.internal.tcnative.SSL", false, OpenSsl.class.getClassLoader());
                e = null;
            } catch (ClassNotFoundException e) {
                e = e;
                logger.debug("netty-tcnative not in the classpath; OpenSslEngine will be unavailable.");
            }
            if (e == null) {
                try {
                    loadTcNative();
                    th = e;
                } catch (Throwable th2) {
                    th = th2;
                    logger.debug("Failed to load netty-tcnative; OpenSslEngine will be unavailable, unless the application has already loaded the symbols by some other means. See https://netty.io/wiki/forked-tomcat-native.html for more information.", th);
                }
                try {
                    String str2 = SystemPropertyUtil.get("io.netty.handler.ssl.openssl.engine", null);
                    if (str2 == null) {
                        logger.debug("Initialize netty-tcnative using engine: 'default'");
                    } else {
                        logger.debug("Initialize netty-tcnative using engine: '{}'", str2);
                    }
                    initializeTcNative(str2);
                    e = null;
                } catch (Throwable th3) {
                    if (th == null) {
                        th = th3;
                    }
                    logger.debug("Failed to initialize netty-tcnative; OpenSslEngine will be unavailable. See https://netty.io/wiki/forked-tomcat-native.html for more information.", th3);
                    e = th;
                }
            }
        }
        UNAVAILABILITY_CAUSE = e;
        if (e == null) {
            logger.debug("netty-tcnative using native library: {}", SSL.versionString());
            ArrayList arrayList = new ArrayList();
            LinkedHashSet linkedHashSet3 = new LinkedHashSet(128);
            IS_BORINGSSL = "BoringSSL".equals(versionString());
            try {
                try {
                    long jMake = SSLContext.make(63, 1);
                    try {
                        StringBuilder sb = new StringBuilder();
                        Iterator<String> it = SslUtils.TLSV13_CIPHERS.iterator();
                        while (it.hasNext()) {
                            String openSsl = CipherSuiteConverter.toOpenSsl(it.next(), IS_BORINGSSL);
                            if (openSsl != null) {
                                sb.append(openSsl).append(':');
                            }
                        }
                        if (sb.length() == 0) {
                            z10 = false;
                        } else {
                            sb.setLength(sb.length() - 1);
                            SSLContext.setCipherSuite(jMake, sb.toString(), true);
                            z10 = true;
                        }
                        z = z10;
                    } catch (Exception unused) {
                        z = false;
                    } catch (Throwable th4) {
                        th = th4;
                        SSLContext.free(jMake);
                        throw th;
                    }
                    try {
                        SSLContext.setCipherSuite(jMake, "ALL", false);
                        long jNewSSL = SSL.newSSL(jMake, true);
                        try {
                            try {
                                String[] ciphers = SSL.getCiphers(jNewSSL);
                                int length = ciphers.length;
                                for (String str3 : ciphers) {
                                    if (str3 != null && !str3.isEmpty() && !linkedHashSet3.contains(str3) && (z || !SslUtils.isTLSv13Cipher(str3))) {
                                        linkedHashSet3.add(str3);
                                    }
                                }
                                boolean z12 = IS_BORINGSSL;
                                j = length;
                                if (z12) {
                                    String[] strArr = {"TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256", "AEAD-AES128-GCM-SHA256", "AEAD-AES256-GCM-SHA384", "AEAD-CHACHA20-POLY1305-SHA256"};
                                    Collections.addAll(linkedHashSet3, strArr);
                                    j = strArr;
                                }
                                try {
                                    PemPrivateKey pemPrivateKeyValueOf = PemPrivateKey.valueOf(KEY.getBytes(CharsetUtil.US_ASCII));
                                    try {
                                        SSLContext.setCertificateCallback(jMake, (CertificateCallback) null);
                                        bio = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, selfSignedCertificate());
                                        try {
                                            x509Chain = SSL.parseX509Chain(bio);
                                            try {
                                                bio2 = ReferenceCountedOpenSslContext.toBIO(UnpooledByteBufAllocator.DEFAULT, pemPrivateKeyValueOf.retain());
                                                try {
                                                    privateKey = SSL.parsePrivateKey(bio2, (String) null);
                                                    try {
                                                        SSL.setKeyMaterial(jNewSSL, x509Chain, privateKey);
                                                        try {
                                                            boolean zContains = SystemPropertyUtil.contains("io.netty.handler.ssl.openssl.useKeyManagerFactory");
                                                            if (!z12) {
                                                                z9 = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useKeyManagerFactory", true);
                                                                if (zContains) {
                                                                    try {
                                                                        logger.info("System property 'io.netty.handler.ssl.openssl.useKeyManagerFactory' is deprecated and so will be ignored in the future");
                                                                    } catch (Throwable unused2) {
                                                                        z7 = z9;
                                                                        try {
                                                                            logger.debug("Failed to get useKeyManagerFactory system property.");
                                                                            z8 = z7;
                                                                        } catch (Error unused3) {
                                                                            z11 = true;
                                                                            z6 = z7;
                                                                            bio2 = bio2;
                                                                            z4 = z6;
                                                                            try {
                                                                                logger.debug("KeyManagerFactory not supported.");
                                                                                pemPrivateKeyValueOf.release();
                                                                                z5 = z4;
                                                                            } catch (Throwable th5) {
                                                                                th = th5;
                                                                                pemPrivateKeyValueOf.release();
                                                                                throw th;
                                                                            }
                                                                        } catch (Throwable th6) {
                                                                            th = th6;
                                                                            pemPrivateKeyValueOf.release();
                                                                            throw th;
                                                                        }
                                                                    }
                                                                }
                                                                z8 = z9;
                                                                try {
                                                                    pemPrivateKeyValueOf.release();
                                                                    z11 = true;
                                                                    bio2 = bio2;
                                                                    z5 = z8;
                                                                    SSL.freeSSL(jNewSSL);
                                                                    if (bio != 0) {
                                                                        SSL.freeBIO(bio);
                                                                    }
                                                                    if (bio2 != 0) {
                                                                        SSL.freeBIO(bio2);
                                                                    }
                                                                    if (x509Chain != 0) {
                                                                        SSL.freeX509Chain(x509Chain);
                                                                    }
                                                                    if (privateKey != 0) {
                                                                        SSL.freePrivateKey(privateKey);
                                                                    }
                                                                    SSLContext.free(jMake);
                                                                    z2 = z5;
                                                                } catch (Throwable th7) {
                                                                    th = th7;
                                                                    j = bio2;
                                                                    SSL.freeSSL(jNewSSL);
                                                                    if (bio != 0) {
                                                                        SSL.freeBIO(bio);
                                                                    }
                                                                    if (j != 0) {
                                                                        SSL.freeBIO(j);
                                                                    }
                                                                    if (x509Chain != 0) {
                                                                        SSL.freeX509Chain(x509Chain);
                                                                    }
                                                                    if (privateKey != 0) {
                                                                        SSL.freePrivateKey(privateKey);
                                                                    }
                                                                    throw th;
                                                                }
                                                            } else {
                                                                if (zContains) {
                                                                    try {
                                                                        logger.info("System property 'io.netty.handler.ssl.openssl.useKeyManagerFactory' is deprecated and will be ignored when using BoringSSL");
                                                                    } catch (Throwable unused4) {
                                                                        z7 = true;
                                                                        logger.debug("Failed to get useKeyManagerFactory system property.");
                                                                        z8 = z7;
                                                                    }
                                                                }
                                                                z9 = true;
                                                                z8 = z9;
                                                                pemPrivateKeyValueOf.release();
                                                                z11 = true;
                                                                bio2 = bio2;
                                                                z5 = z8;
                                                                SSL.freeSSL(jNewSSL);
                                                                if (bio != 0) {
                                                                    SSL.freeBIO(bio);
                                                                }
                                                                if (bio2 != 0) {
                                                                    SSL.freeBIO(bio2);
                                                                }
                                                                if (x509Chain != 0) {
                                                                    SSL.freeX509Chain(x509Chain);
                                                                }
                                                                if (privateKey != 0) {
                                                                    SSL.freePrivateKey(privateKey);
                                                                }
                                                                SSLContext.free(jMake);
                                                                z2 = z5;
                                                            }
                                                        } catch (Throwable unused5) {
                                                            z7 = false;
                                                        }
                                                    } catch (Error unused6) {
                                                        z6 = false;
                                                        z11 = false;
                                                    } catch (Throwable th8) {
                                                        th = th8;
                                                    }
                                                } catch (Error unused7) {
                                                    z4 = false;
                                                    z11 = false;
                                                    privateKey = 0;
                                                } catch (Throwable th9) {
                                                    th = th9;
                                                }
                                            } catch (Error unused8) {
                                                z4 = false;
                                                z11 = false;
                                                bio2 = 0;
                                                privateKey = 0;
                                            } catch (Throwable th10) {
                                                th = th10;
                                            }
                                        } catch (Error unused9) {
                                            z3 = false;
                                            z11 = false;
                                            bio2 = 0;
                                            x509Chain = 0;
                                            privateKey = x509Chain;
                                            z4 = z3;
                                            logger.debug("KeyManagerFactory not supported.");
                                            pemPrivateKeyValueOf.release();
                                            z5 = z4;
                                            SSL.freeSSL(jNewSSL);
                                            if (bio != 0) {
                                                SSL.freeBIO(bio);
                                            }
                                            if (bio2 != 0) {
                                                SSL.freeBIO(bio2);
                                            }
                                            if (x509Chain != 0) {
                                                SSL.freeX509Chain(x509Chain);
                                            }
                                            if (privateKey != 0) {
                                                SSL.freePrivateKey(privateKey);
                                            }
                                            SSLContext.free(jMake);
                                            z2 = z5;
                                            Set<String> setUnmodifiableSet2 = Collections.unmodifiableSet(linkedHashSet3);
                                            AVAILABLE_OPENSSL_CIPHER_SUITES = setUnmodifiableSet2;
                                            linkedHashSet = new LinkedHashSet(setUnmodifiableSet2.size() * 2);
                                            for (String str4 : setUnmodifiableSet2) {
                                                if (!SslUtils.isTLSv13Cipher(str4)) {
                                                    linkedHashSet.add(CipherSuiteConverter.toJava(str4, "TLS"));
                                                    linkedHashSet.add(CipherSuiteConverter.toJava(str4, "SSL"));
                                                } else {
                                                    linkedHashSet.add(str4);
                                                }
                                            }
                                            SslUtils.addIfSupported(linkedHashSet, arrayList, SslUtils.DEFAULT_CIPHER_SUITES);
                                            SslUtils.addIfSupported(linkedHashSet, arrayList, SslUtils.TLSV13_CIPHER_SUITES);
                                            SslUtils.useFallbackCiphersIfDefaultIsEmpty(arrayList, linkedHashSet);
                                            listUnmodifiableList = Collections.unmodifiableList(arrayList);
                                            DEFAULT_CIPHERS = listUnmodifiableList;
                                            Set<String> setUnmodifiableSet3 = Collections.unmodifiableSet(linkedHashSet);
                                            AVAILABLE_JAVA_CIPHER_SUITES = setUnmodifiableSet3;
                                            Set<String> set = AVAILABLE_OPENSSL_CIPHER_SUITES;
                                            LinkedHashSet linkedHashSet4 = new LinkedHashSet(set.size() + setUnmodifiableSet3.size());
                                            linkedHashSet4.addAll(set);
                                            linkedHashSet4.addAll(setUnmodifiableSet3);
                                            AVAILABLE_CIPHER_SUITES = linkedHashSet4;
                                            SUPPORTS_KEYMANAGER_FACTORY = z11;
                                            USE_KEYMANAGER_FACTORY = z2;
                                            linkedHashSet2 = new LinkedHashSet(6);
                                            linkedHashSet2.add("SSLv2Hello");
                                            if (doesSupportProtocol(1, SSL.SSL_OP_NO_SSLv2)) {
                                                linkedHashSet2.add("SSLv2");
                                            }
                                            if (doesSupportProtocol(2, SSL.SSL_OP_NO_SSLv3)) {
                                                linkedHashSet2.add("SSLv3");
                                            }
                                            if (doesSupportProtocol(4, SSL.SSL_OP_NO_TLSv1)) {
                                                linkedHashSet2.add("TLSv1");
                                            }
                                            if (doesSupportProtocol(8, SSL.SSL_OP_NO_TLSv1_1)) {
                                                linkedHashSet2.add("TLSv1.1");
                                            }
                                            if (doesSupportProtocol(16, SSL.SSL_OP_NO_TLSv1_2)) {
                                                linkedHashSet2.add("TLSv1.2");
                                            }
                                            if (!z) {
                                                TLSV13_SUPPORTED = false;
                                            } else {
                                                TLSV13_SUPPORTED = false;
                                            }
                                            setUnmodifiableSet = Collections.unmodifiableSet(linkedHashSet2);
                                            SUPPORTED_PROTOCOLS_SET = setUnmodifiableSet;
                                            SUPPORTS_OCSP = doesSupportOcsp();
                                            internalLogger = logger;
                                            if (internalLogger.isDebugEnabled()) {
                                                internalLogger.debug("Supported protocols (OpenSSL): {} ", setUnmodifiableSet);
                                                internalLogger.debug("Default cipher suites (OpenSSL): {}", listUnmodifiableList);
                                                return;
                                            }
                                            return;
                                        } catch (Throwable th11) {
                                            th = th11;
                                            j2 = 0;
                                            pemPrivateKeyValueOf.release();
                                            throw th;
                                        }
                                    } catch (Error unused10) {
                                        z3 = false;
                                        z11 = false;
                                        bio2 = 0;
                                        bio = 0;
                                        x509Chain = 0;
                                    } catch (Throwable th12) {
                                        th = th12;
                                        j2 = 0;
                                    }
                                } catch (Throwable th13) {
                                    th = th13;
                                }
                            } catch (Throwable th14) {
                                th = th14;
                                SSLContext.free(jMake);
                                throw th;
                            }
                        } catch (Throwable th15) {
                            th = th15;
                            j = 0;
                            bio = 0;
                            x509Chain = 0;
                            privateKey = 0;
                        }
                    } catch (Throwable th16) {
                        th = th16;
                    }
                } catch (Exception e2) {
                    e = e2;
                    logger.warn("Failed to get the list of available OpenSSL cipher suites.", (Throwable) e);
                    z2 = str;
                }
            } catch (Exception e3) {
                e = e3;
                str = null;
                z11 = false;
                z = false;
                logger.warn("Failed to get the list of available OpenSSL cipher suites.", (Throwable) e);
                z2 = str;
            }
            Set<String> setUnmodifiableSet4 = Collections.unmodifiableSet(linkedHashSet3);
            AVAILABLE_OPENSSL_CIPHER_SUITES = setUnmodifiableSet4;
            linkedHashSet = new LinkedHashSet(setUnmodifiableSet4.size() * 2);
            while (r0.hasNext()) {
                if (!SslUtils.isTLSv13Cipher(str4)) {
                    linkedHashSet.add(CipherSuiteConverter.toJava(str4, "TLS"));
                    linkedHashSet.add(CipherSuiteConverter.toJava(str4, "SSL"));
                } else {
                    linkedHashSet.add(str4);
                }
            }
            SslUtils.addIfSupported(linkedHashSet, arrayList, SslUtils.DEFAULT_CIPHER_SUITES);
            SslUtils.addIfSupported(linkedHashSet, arrayList, SslUtils.TLSV13_CIPHER_SUITES);
            SslUtils.useFallbackCiphersIfDefaultIsEmpty(arrayList, linkedHashSet);
            listUnmodifiableList = Collections.unmodifiableList(arrayList);
            DEFAULT_CIPHERS = listUnmodifiableList;
            Set<String> setUnmodifiableSet5 = Collections.unmodifiableSet(linkedHashSet);
            AVAILABLE_JAVA_CIPHER_SUITES = setUnmodifiableSet5;
            Set<String> set2 = AVAILABLE_OPENSSL_CIPHER_SUITES;
            LinkedHashSet linkedHashSet5 = new LinkedHashSet(set2.size() + setUnmodifiableSet5.size());
            linkedHashSet5.addAll(set2);
            linkedHashSet5.addAll(setUnmodifiableSet5);
            AVAILABLE_CIPHER_SUITES = linkedHashSet5;
            SUPPORTS_KEYMANAGER_FACTORY = z11;
            USE_KEYMANAGER_FACTORY = z2;
            linkedHashSet2 = new LinkedHashSet(6);
            linkedHashSet2.add("SSLv2Hello");
            if (doesSupportProtocol(1, SSL.SSL_OP_NO_SSLv2)) {
                linkedHashSet2.add("SSLv2");
            }
            if (doesSupportProtocol(2, SSL.SSL_OP_NO_SSLv3)) {
                linkedHashSet2.add("SSLv3");
            }
            if (doesSupportProtocol(4, SSL.SSL_OP_NO_TLSv1)) {
                linkedHashSet2.add("TLSv1");
            }
            if (doesSupportProtocol(8, SSL.SSL_OP_NO_TLSv1_1)) {
                linkedHashSet2.add("TLSv1.1");
            }
            if (doesSupportProtocol(16, SSL.SSL_OP_NO_TLSv1_2)) {
                linkedHashSet2.add("TLSv1.2");
            }
            if (!z && doesSupportProtocol(32, SSL.SSL_OP_NO_TLSv1_3)) {
                linkedHashSet2.add("TLSv1.3");
                TLSV13_SUPPORTED = true;
            } else {
                TLSV13_SUPPORTED = false;
            }
            setUnmodifiableSet = Collections.unmodifiableSet(linkedHashSet2);
            SUPPORTED_PROTOCOLS_SET = setUnmodifiableSet;
            SUPPORTS_OCSP = doesSupportOcsp();
            internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("Supported protocols (OpenSSL): {} ", setUnmodifiableSet);
                internalLogger.debug("Default cipher suites (OpenSSL): {}", listUnmodifiableList);
                return;
            }
            return;
        }
        DEFAULT_CIPHERS = Collections.emptyList();
        AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
        AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
        AVAILABLE_CIPHER_SUITES = Collections.emptySet();
        SUPPORTS_KEYMANAGER_FACTORY = false;
        USE_KEYMANAGER_FACTORY = false;
        SUPPORTED_PROTOCOLS_SET = Collections.emptySet();
        SUPPORTS_OCSP = false;
        TLSV13_SUPPORTED = false;
        IS_BORINGSSL = false;
    }

    static X509Certificate selfSignedCertificate() throws CertificateException {
        return (X509Certificate) SslContext.X509_CERT_FACTORY.generateCertificate(new ByteArrayInputStream(CERT.getBytes(CharsetUtil.US_ASCII)));
    }

    private static boolean doesSupportOcsp() throws Throwable {
        long jMake;
        if (version() < 268443648) {
            return false;
        }
        try {
            jMake = SSLContext.make(16, 1);
            try {
                SSLContext.enableOcsp(jMake, false);
                if (jMake != -1) {
                    SSLContext.free(jMake);
                }
                return true;
            } catch (Exception unused) {
                if (jMake == -1) {
                    return false;
                }
                SSLContext.free(jMake);
                return false;
            } catch (Throwable th) {
                th = th;
                if (jMake != -1) {
                    SSLContext.free(jMake);
                }
                throw th;
            }
        } catch (Exception unused2) {
            jMake = -1;
        } catch (Throwable th2) {
            th = th2;
            jMake = -1;
        }
    }

    private static boolean doesSupportProtocol(int i, int i2) {
        if (i2 == 0) {
            return false;
        }
        try {
            long jMake = SSLContext.make(i, 2);
            if (jMake == -1) {
                return true;
            }
            SSLContext.free(jMake);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    @Deprecated
    public static boolean isAlpnSupported() {
        return ((long) version()) >= 268443648;
    }

    public static boolean isOcspSupported() {
        return SUPPORTS_OCSP;
    }

    public static int version() {
        if (isAvailable()) {
            return SSL.version();
        }
        return -1;
    }

    public static String versionString() {
        if (isAvailable()) {
            return SSL.versionString();
        }
        return null;
    }

    public static void ensureAvailability() {
        Throwable th = UNAVAILABILITY_CAUSE;
        if (th != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(th));
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    @Deprecated
    public static Set<String> availableCipherSuites() {
        return availableOpenSslCipherSuites();
    }

    public static Set<String> availableOpenSslCipherSuites() {
        return AVAILABLE_OPENSSL_CIPHER_SUITES;
    }

    public static Set<String> availableJavaCipherSuites() {
        return AVAILABLE_JAVA_CIPHER_SUITES;
    }

    public static boolean isCipherSuiteAvailable(String str) {
        String openSsl = CipherSuiteConverter.toOpenSsl(str, IS_BORINGSSL);
        if (openSsl != null) {
            str = openSsl;
        }
        return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(str);
    }

    public static boolean supportsKeyManagerFactory() {
        return SUPPORTS_KEYMANAGER_FACTORY;
    }

    @Deprecated
    public static boolean supportsHostnameValidation() {
        return isAvailable();
    }

    static boolean useKeyManagerFactory() {
        return USE_KEYMANAGER_FACTORY;
    }

    static long memoryAddress(ByteBuf byteBuf) {
        return byteBuf.hasMemoryAddress() ? byteBuf.memoryAddress() : Buffer.address(byteBuf.nioBuffer());
    }

    private OpenSsl() {
    }

    private static void loadTcNative() throws Exception {
        String strNormalizedOs = PlatformDependent.normalizedOs();
        String strNormalizedArch = PlatformDependent.normalizedArch();
        LinkedHashSet linkedHashSet = new LinkedHashSet(5);
        if ("linux".equalsIgnoreCase(strNormalizedOs)) {
            Iterator<String> it = PlatformDependent.normalizedLinuxClassifiers().iterator();
            while (it.hasNext()) {
                linkedHashSet.add("netty_tcnative_" + strNormalizedOs + '_' + strNormalizedArch + "_" + it.next());
            }
            linkedHashSet.add("netty_tcnative_" + strNormalizedOs + '_' + strNormalizedArch);
            linkedHashSet.add("netty_tcnative_" + strNormalizedOs + '_' + strNormalizedArch + "_fedora");
        } else {
            linkedHashSet.add("netty_tcnative_" + strNormalizedOs + '_' + strNormalizedArch);
        }
        linkedHashSet.add("netty_tcnative_" + strNormalizedArch);
        linkedHashSet.add("netty_tcnative");
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[]) linkedHashSet.toArray(new String[0]));
    }

    private static boolean initializeTcNative(String str) throws Exception {
        return Library.initialize("provided", str);
    }

    static void releaseIfNeeded(ReferenceCounted referenceCounted) {
        if (referenceCounted.refCnt() > 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
    }

    static boolean isTlsv13Supported() {
        return TLSV13_SUPPORTED;
    }

    static boolean isBoringSSL() {
        return IS_BORINGSSL;
    }
}
