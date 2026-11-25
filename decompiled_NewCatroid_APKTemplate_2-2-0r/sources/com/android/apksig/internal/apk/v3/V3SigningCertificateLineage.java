package com.android.apksig.internal.apk.v3;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import com.android.apksig.internal.util.X509CertificateUtils;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class V3SigningCertificateLineage {
    private static final int CURRENT_VERSION = 1;
    private static final int FIRST_VERSION = 1;

    public static List<SigningCertificateNode> readSigningCertificateLineage(ByteBuffer inputBytes) throws IOException {
        String str;
        Throwable th;
        String str2;
        String str3 = " when parsing V3SigningCertificateLineage object";
        ArrayList arrayList = new ArrayList();
        GuaranteedEncodedFormX509Certificate guaranteedEncodedFormX509Certificate = null;
        if (inputBytes == null || !inputBytes.hasRemaining()) {
            return null;
        }
        ApkSigningBlockUtils.checkByteOrderLittleEndian(inputBytes);
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
                                ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(inputBytes);
                                ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                                int i3 = lengthPrefixedSlice.getInt();
                                int i4 = lengthPrefixedSlice.getInt();
                                SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i2);
                                byte[] lengthPrefixedByteArray = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice);
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
                                        throw new SecurityException("Unable to verify signature of certificate #" + i + " using " + first + " when verifying V3SigningCertificateLineage object");
                                    }
                                }
                                lengthPrefixedSlice2.rewind();
                                byte[] lengthPrefixedByteArray2 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice2);
                                int i5 = lengthPrefixedSlice2.getInt();
                                if (guaranteedEncodedFormX509Certificate != null && i2 != i5) {
                                    throw new SecurityException("Signing algorithm ID mismatch for certificate #" + lengthPrefixedSlice + " when verifying V3SigningCertificateLineage object");
                                }
                                GuaranteedEncodedFormX509Certificate guaranteedEncodedFormX509Certificate2 = new GuaranteedEncodedFormX509Certificate(X509CertificateUtils.generateCertificate(lengthPrefixedByteArray2), lengthPrefixedByteArray2);
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
                throw new IOException("Failed to parse V3SigningCertificateLineage object", e11);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e12) {
            str = " when parsing V3SigningCertificateLineage object";
            th = e12;
        }
        throw new SecurityException("Failed to decode certificate #" + i + str2, e);
    }

    public static byte[] encodeSigningCertificateLineage(List<SigningCertificateNode> signingCertificateLineage) {
        ArrayList arrayList = new ArrayList();
        Iterator<SigningCertificateNode> it = signingCertificateLineage.iterator();
        while (it.hasNext()) {
            arrayList.add(encodeSigningCertificateNode(it.next()));
        }
        byte[] bArrEncodeAsSequenceOfLengthPrefixedElements = ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(arrayList);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeAsSequenceOfLengthPrefixedElements.length + 4);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(1);
        byteBufferAllocate.put(bArrEncodeAsSequenceOfLengthPrefixedElements);
        return byteBufferAllocate.array();
    }

    public static byte[] encodeSigningCertificateNode(SigningCertificateNode node) {
        int id2 = node.parentSigAlgorithm != null ? node.parentSigAlgorithm.getId() : 0;
        int id3 = node.sigAlgorithm != null ? node.sigAlgorithm.getId() : 0;
        byte[] bArrEncodeSignedData = encodeSignedData(node.signingCert, id2);
        byte[] bArrEncodeAsLengthPrefixedElement = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(node.signature);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeSignedData.length + 8 + bArrEncodeAsLengthPrefixedElement.length);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.put(bArrEncodeSignedData);
        byteBufferAllocate.putInt(node.flags);
        byteBufferAllocate.putInt(id3);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement);
        return byteBufferAllocate.array();
    }

    public static byte[] encodeSignedData(X509Certificate certificate, int flags) {
        try {
            byte[] bArrEncodeAsLengthPrefixedElement = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(certificate.getEncoded());
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeAsLengthPrefixedElement.length + 4);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement);
            byteBufferAllocate.putInt(flags);
            return ApkSigningBlockUtils.encodeAsLengthPrefixedElement(byteBufferAllocate.array());
        } catch (CertificateEncodingException e) {
            throw new RuntimeException("Failed to encode V3SigningCertificateLineage certificate", e);
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
            return (Objects.hash(this.signingCert, this.parentSigAlgorithm, this.sigAlgorithm, Integer.valueOf(this.flags)) * 31) + Arrays.hashCode(this.signature);
        }
    }
}
