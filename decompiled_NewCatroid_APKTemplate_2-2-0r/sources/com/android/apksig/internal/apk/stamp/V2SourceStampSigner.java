package com.android.apksig.internal.apk.stamp;

import com.android.apksig.SigningCertificateLineage;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.util.Pair;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class V2SourceStampSigner {
    public static final int V2_SOURCE_STAMP_BLOCK_ID = 1845461005;
    private final Map<Integer, Map<ContentDigestAlgorithm, byte[]>> mSignatureSchemeDigestInfos;
    private final ApkSigningBlockUtils.SignerConfig mSourceStampSignerConfig;
    private final boolean mSourceStampTimestampEnabled;

    private V2SourceStampSigner(Builder builder) {
        this.mSourceStampSignerConfig = builder.mSourceStampSignerConfig;
        this.mSignatureSchemeDigestInfos = builder.mSignatureSchemeDigestInfos;
        this.mSourceStampTimestampEnabled = builder.mSourceStampTimestampEnabled;
    }

    public static Pair<byte[], Integer> generateSourceStampBlock(ApkSigningBlockUtils.SignerConfig sourceStampSignerConfig, Map<Integer, Map<ContentDigestAlgorithm, byte[]>> signatureSchemeDigestInfos) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        return new Builder(sourceStampSignerConfig, signatureSchemeDigestInfos).build().generateSourceStampBlock();
    }

    public Pair<byte[], Integer> generateSourceStampBlock() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (this.mSourceStampSignerConfig.certificates.isEmpty()) {
            throw new SignatureException("No certificates configured for signer");
        }
        ArrayList arrayList = new ArrayList();
        getSignedDigestsFor(3, this.mSignatureSchemeDigestInfos, this.mSourceStampSignerConfig, arrayList);
        getSignedDigestsFor(2, this.mSignatureSchemeDigestInfos, this.mSourceStampSignerConfig, arrayList);
        getSignedDigestsFor(1, this.mSignatureSchemeDigestInfos, this.mSourceStampSignerConfig, arrayList);
        Collections.sort(arrayList, Comparator.comparing(new V1SourceStampSigner$$ExternalSyntheticLambda0()));
        SourceStampBlock sourceStampBlock = new SourceStampBlock();
        try {
            sourceStampBlock.stampCertificate = this.mSourceStampSignerConfig.certificates.get(0).getEncoded();
            sourceStampBlock.signedDigests = arrayList;
            sourceStampBlock.stampAttributes = encodeStampAttributes(generateStampAttributes(this.mSourceStampSignerConfig.mSigningCertificateLineage));
            sourceStampBlock.signedStampAttributes = ApkSigningBlockUtils.generateSignaturesOverData(this.mSourceStampSignerConfig, sourceStampBlock.stampAttributes);
            return Pair.of(ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{sourceStampBlock.stampCertificate, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(sourceStampBlock.signedDigests), sourceStampBlock.stampAttributes, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(sourceStampBlock.signedStampAttributes)})), 1845461005);
        } catch (CertificateEncodingException e) {
            throw new SignatureException("Retrieving the encoded form of the stamp certificate failed", e);
        }
    }

    private static void getSignedDigestsFor(int signatureSchemeVersion, Map<Integer, Map<ContentDigestAlgorithm, byte[]>> mSignatureSchemeDigestInfos, ApkSigningBlockUtils.SignerConfig mSourceStampSignerConfig, List<Pair<Integer, byte[]>> signatureSchemeDigests) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (mSignatureSchemeDigestInfos.containsKey(Integer.valueOf(signatureSchemeVersion))) {
            Map<ContentDigestAlgorithm, byte[]> map = mSignatureSchemeDigestInfos.get(Integer.valueOf(signatureSchemeVersion));
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<ContentDigestAlgorithm, byte[]> entry : map.entrySet()) {
                arrayList.add(Pair.of(Integer.valueOf(entry.getKey().getId()), entry.getValue()));
            }
            Collections.sort(arrayList, Comparator.comparing(new V1SourceStampSigner$$ExternalSyntheticLambda0()));
            signatureSchemeDigests.add(Pair.of(Integer.valueOf(signatureSchemeVersion), ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(ApkSigningBlockUtils.generateSignaturesOverData(mSourceStampSignerConfig, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(arrayList)))));
        }
    }

    private static byte[] encodeStampAttributes(Map<Integer, byte[]> stampAttributes) {
        Iterator<byte[]> it = stampAttributes.values().iterator();
        int length = 0;
        while (it.hasNext()) {
            length += it.next().length + 8;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length + 4);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(length);
        for (Map.Entry<Integer, byte[]> entry : stampAttributes.entrySet()) {
            byteBufferAllocate.putInt(entry.getValue().length + 4);
            byteBufferAllocate.putInt(entry.getKey().intValue());
            byteBufferAllocate.put(entry.getValue());
        }
        return byteBufferAllocate.array();
    }

    private Map<Integer, byte[]> generateStampAttributes(SigningCertificateLineage lineage) {
        HashMap map = new HashMap();
        if (this.mSourceStampTimestampEnabled) {
            long epochSecond = Instant.now().getEpochSecond();
            if (epochSecond > 0) {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
                byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
                byteBufferAllocate.putLong(epochSecond);
                map.put(Integer.valueOf(SourceStampConstants.STAMP_TIME_ATTR_ID), byteBufferAllocate.array());
            } else {
                throw new IllegalStateException("Received an invalid value from Instant#getTimestamp: " + epochSecond);
            }
        }
        if (lineage != null) {
            map.put(Integer.valueOf(SourceStampConstants.PROOF_OF_ROTATION_ATTR_ID), lineage.encodeSigningCertificateLineage());
        }
        return map;
    }

    private static final class SourceStampBlock {
        public List<Pair<Integer, byte[]>> signedDigests;
        public List<Pair<Integer, byte[]>> signedStampAttributes;
        public byte[] stampAttributes;
        public byte[] stampCertificate;

        private SourceStampBlock() {
        }
    }

    public static class Builder {
        private final Map<Integer, Map<ContentDigestAlgorithm, byte[]>> mSignatureSchemeDigestInfos;
        private final ApkSigningBlockUtils.SignerConfig mSourceStampSignerConfig;
        private boolean mSourceStampTimestampEnabled = true;

        public Builder(ApkSigningBlockUtils.SignerConfig sourceStampSignerConfig, Map<Integer, Map<ContentDigestAlgorithm, byte[]>> signatureSchemeDigestInfos) {
            this.mSourceStampSignerConfig = sourceStampSignerConfig;
            this.mSignatureSchemeDigestInfos = signatureSchemeDigestInfos;
        }

        public Builder setSourceStampTimestampEnabled(boolean value) {
            this.mSourceStampTimestampEnabled = value;
            return this;
        }

        public V2SourceStampSigner build() {
            return new V2SourceStampSigner(this);
        }
    }
}
