package com.android.apksig.internal.apk.stamp;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.internal.apk.ApkSigningBlockUtilsLite;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SourceStampCertificateLineage {
    private static final int CURRENT_VERSION = 1;
    private static final int FIRST_VERSION = 1;

    public static List<SigningCertificateNode> readSigningCertificateLineage(ByteBuffer inputBytes) throws IOException {
        String str;
        Throwable th;
        String str2;
        String str3 = " when parsing SourceStampCertificateLineage object";
        ArrayList arrayList = new ArrayList();
        GuaranteedEncodedFormX509Certificate guaranteedEncodedFormX509Certificate = null;
        if (inputBytes == null || !inputBytes.hasRemaining()) {
            return null;
        }
        ApkSigningBlockUtilsLite.checkByteOrderLittleEndian(inputBytes);
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            int i = 0;
            try {
                try {
                    try {
                        try {
                            if (inputBytes.getInt() != 1) {
                                throw new IllegalArgumentException("Encoded SigningCertificateLineage has a version different than any of which we are aware");
                            }
                            try {
                                HashSet hashSet = new HashSet();
                                int i2 = 0;
                                while (inputBytes.hasRemaining()) {
                                    i++;
                                    ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(inputBytes);
                                    ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(lengthPrefixedSlice);
                                    int i3 = lengthPrefixedSlice.getInt();
                                    int i4 = lengthPrefixedSlice.getInt();
                                    SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i2);
                                    byte[] lengthPrefixedByteArray = ApkSigningBlockUtilsLite.readLengthPrefixedByteArray(lengthPrefixedSlice);
                                    if (guaranteedEncodedFormX509Certificate != null) {
                                        String first = signatureAlgorithmFindById.getJcaSignatureAlgorithmAndParams().getFirst();
                                        AlgorithmParameterSpec second = signatureAlgorithmFindById.getJcaSignatureAlgorithmAndParams().getSecond();
                                        PublicKey publicKey = guaranteedEncodedFormX509Certificate.getPublicKey();
                                        Signature signature = Signature.getInstance(first);
                                        signature.initVerify(publicKey);
                                        if (second != null) {
                                            signature.setParameter(second);
                                        }
                                        signature.update(lengthPrefixedSlice2);
                                        if (!signature.verify(lengthPrefixedByteArray)) {
                                            throw new SecurityException("Unable to verify signature of certificate #" + i + " using " + first + " when verifying SourceStampCertificateLineage object");
                                        }
                                    }
                                    lengthPrefixedSlice2.rewind();
                                    byte[] lengthPrefixedByteArray2 = ApkSigningBlockUtilsLite.readLengthPrefixedByteArray(lengthPrefixedSlice2);
                                    int i5 = lengthPrefixedSlice2.getInt();
                                    if (guaranteedEncodedFormX509Certificate != null && i2 != i5) {
                                        throw new SecurityException("Signing algorithm ID mismatch for certificate #" + lengthPrefixedSlice + " when verifying SourceStampCertificateLineage object");
                                    }
                                    GuaranteedEncodedFormX509Certificate guaranteedEncodedFormX509Certificate2 = new GuaranteedEncodedFormX509Certificate((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(lengthPrefixedByteArray2)), lengthPrefixedByteArray2);
                                    if (hashSet.contains(guaranteedEncodedFormX509Certificate2)) {
                                        throw new SecurityException("Encountered duplicate entries in SigningCertificateLineage at certificate #" + i + ".  All signing certificates should be unique");
                                    }
                                    hashSet.add(guaranteedEncodedFormX509Certificate2);
                                    arrayList.add(new SigningCertificateNode(guaranteedEncodedFormX509Certificate2, SignatureAlgorithm.findById(i5), SignatureAlgorithm.findById(i4), lengthPrefixedByteArray, i3));
                                    guaranteedEncodedFormX509Certificate = guaranteedEncodedFormX509Certificate2;
                                    i2 = i4;
                                    str3 = str3;
                                }
                                return arrayList;
                            } catch (InvalidAlgorithmParameterException e) {
                                e = e;
                                String str4 = str3;
                                th = e;
                                str = str4;
                                throw new SecurityException("Failed to verify signature over signed data for certificate #" + i + str, th);
                            } catch (InvalidKeyException e2) {
                                e = e2;
                                String str5 = str3;
                                th = e;
                                str = str5;
                                throw new SecurityException("Failed to verify signature over signed data for certificate #" + i + str, th);
                            } catch (NoSuchAlgorithmException e3) {
                                e = e3;
                                String str6 = str3;
                                th = e;
                                str = str6;
                                throw new SecurityException("Failed to verify signature over signed data for certificate #" + i + str, th);
                            } catch (SignatureException e4) {
                                e = e4;
                                String str7 = str3;
                                th = e;
                                str = str7;
                                throw new SecurityException("Failed to verify signature over signed data for certificate #" + i + str, th);
                            }
                        } catch (InvalidAlgorithmParameterException e5) {
                            e = e5;
                        } catch (InvalidKeyException e6) {
                            e = e6;
                        } catch (NoSuchAlgorithmException e7) {
                            e = e7;
                        } catch (SignatureException e8) {
                            e = e8;
                        } catch (CertificateException e9) {
                            e = e9;
                        }
                    } catch (CertificateException e10) {
                        e = e10;
                        str2 = str3;
                    }
                } catch (ApkFormatException | BufferUnderflowException e11) {
                    throw new IOException("Failed to parse SourceStampCertificateLineage object", e11);
                }
            } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e12) {
                str = " when parsing SourceStampCertificateLineage object";
                th = e12;
            }
            throw new SecurityException("Failed to decode certificate #" + i + str2, e);
        } catch (CertificateException e13) {
            throw new IllegalStateException("Failed to obtain X.509 CertificateFactory", e13);
        }
    }

    public static class SigningCertificateNode {
        public int flags;
        public final SignatureAlgorithm parentSigAlgorithm;
        public SignatureAlgorithm sigAlgorithm;
        public final byte[] signature;
        public final X509Certificate signingCert;

        public SigningCertificateNode(X509Certificate signingCert, SignatureAlgorithm parentSigAlgorithm, SignatureAlgorithm sigAlgorithm, byte[] signature, int flags) {
            this.signingCert = signingCert;
            this.parentSigAlgorithm = parentSigAlgorithm;
            this.sigAlgorithm = sigAlgorithm;
            this.signature = signature;
            this.flags = flags;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SigningCertificateNode)) {
                return false;
            }
            SigningCertificateNode signingCertificateNode = (SigningCertificateNode) o;
            return this.signingCert.equals(signingCertificateNode.signingCert) && this.parentSigAlgorithm == signingCertificateNode.parentSigAlgorithm && this.sigAlgorithm == signingCertificateNode.sigAlgorithm && Arrays.equals(this.signature, signingCertificateNode.signature) && this.flags == signingCertificateNode.flags;
        }

        public int hashCode() {
            X509Certificate x509Certificate = this.signingCert;
            int iHashCode = ((x509Certificate == null ? 0 : x509Certificate.hashCode()) + 31) * 31;
            SignatureAlgorithm signatureAlgorithm = this.parentSigAlgorithm;
            int iHashCode2 = (iHashCode + (signatureAlgorithm == null ? 0 : signatureAlgorithm.hashCode())) * 31;
            SignatureAlgorithm signatureAlgorithm2 = this.sigAlgorithm;
            return ((((iHashCode2 + (signatureAlgorithm2 != null ? signatureAlgorithm2.hashCode() : 0)) * 31) + Arrays.hashCode(this.signature)) * 31) + this.flags;
        }
    }
}
