package com.android.apksig.internal.apk.v1;

import com.android.apksig.ApkVerifier;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.asn1.Asn1BerParser;
import com.android.apksig.internal.asn1.Asn1Class;
import com.android.apksig.internal.asn1.Asn1DecodingException;
import com.android.apksig.internal.asn1.Asn1Field;
import com.android.apksig.internal.asn1.Asn1OpaqueObject;
import com.android.apksig.internal.asn1.Asn1Type;
import com.android.apksig.internal.jar.ManifestParser;
import com.android.apksig.internal.oid.OidConstants;
import com.android.apksig.internal.pkcs7.AlgorithmIdentifier;
import com.android.apksig.internal.pkcs7.Attribute;
import com.android.apksig.internal.pkcs7.ContentInfo;
import com.android.apksig.internal.pkcs7.Pkcs7Constants;
import com.android.apksig.internal.pkcs7.Pkcs7DecodingException;
import com.android.apksig.internal.pkcs7.SignedData;
import com.android.apksig.internal.pkcs7.SignerInfo;
import com.android.apksig.internal.util.ByteBufferUtils;
import com.android.apksig.internal.util.InclusiveIntRange;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.internal.x509.Certificate;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public abstract class V1SchemeVerifier {
    private static final String[] JB_MR2_AND_NEWER_DIGEST_ALGS = {McElieceCCA2KeyGenParameterSpec.SHA512, McElieceCCA2KeyGenParameterSpec.SHA384, McElieceCCA2KeyGenParameterSpec.SHA256, McElieceCCA2KeyGenParameterSpec.SHA1};
    private static final Map<String, Integer> MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST;
    private static final Map<String, String> UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL;

    @Asn1Class(type = Asn1Type.CHOICE)
    public static class ObjectIdentifierChoice {

        @Asn1Field(type = Asn1Type.OBJECT_IDENTIFIER)
        public String value;
    }

    @Asn1Class(type = Asn1Type.CHOICE)
    public static class OctetStringChoice {

        @Asn1Field(type = Asn1Type.OCTET_STRING)
        public byte[] value;
    }

    private V1SchemeVerifier() {
    }

    public static Result verify(DataSource apk, ApkUtils.ZipSections apkSections, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException, IOException, ApkFormatException {
        if (minSdkVersion > maxSdkVersion) {
            throw new IllegalArgumentException("minSdkVersion (" + minSdkVersion + ") > maxSdkVersion (" + maxSdkVersion + Constant.AFTER_QUTO);
        }
        Result result = new Result();
        List<CentralDirectoryRecord> zipCentralDirectory = parseZipCentralDirectory(apk, apkSections);
        Set<String> setCheckForDuplicateEntries = checkForDuplicateEntries(zipCentralDirectory, result);
        if (result.containsErrors()) {
            return result;
        }
        Signers.verify(apk, apkSections.getZipCentralDirectoryOffset(), zipCentralDirectory, setCheckForDuplicateEntries, supportedApkSigSchemeNames, foundApkSigSchemeIds, minSdkVersion, maxSdkVersion, result);
        return result;
    }

    private static Set<String> checkForDuplicateEntries(List<CentralDirectoryRecord> cdRecords, Result result) {
        HashSet hashSet = new HashSet(cdRecords.size());
        Iterator<CentralDirectoryRecord> it = cdRecords.iterator();
        HashSet hashSet2 = null;
        while (it.hasNext()) {
            String name2 = it.next().getName();
            if (!hashSet.add(name2)) {
                if (hashSet2 == null) {
                    hashSet2 = new HashSet();
                }
                if (hashSet2.add(name2)) {
                    result.addError(ApkVerifier.Issue.JAR_SIG_DUPLICATE_ZIP_ENTRY, name2);
                }
            }
        }
        return hashSet;
    }

    public static Pair<ManifestParser.Section, Map<String, ManifestParser.Section>> parseManifest(byte[] manifestBytes, Set<String> cdEntryNames, Result result) {
        ManifestParser manifestParser = new ManifestParser(manifestBytes);
        ManifestParser.Section section = manifestParser.readSection();
        List<ManifestParser.Section> allSections = manifestParser.readAllSections();
        HashMap map = new HashMap(allSections.size());
        int i = 0;
        for (ManifestParser.Section section2 : allSections) {
            i++;
            String name2 = section2.getName();
            if (name2 != null) {
                if (map.put(name2, section2) == null) {
                    if (!cdEntryNames.contains(name2)) {
                        result.addError(ApkVerifier.Issue.JAR_SIG_MISSING_ZIP_ENTRY_REFERENCED_IN_MANIFEST, name2);
                    }
                } else {
                    result.addError(ApkVerifier.Issue.JAR_SIG_DUPLICATE_MANIFEST_SECTION, name2);
                }
            } else {
                result.addError(ApkVerifier.Issue.JAR_SIG_UNNNAMED_MANIFEST_SECTION, Integer.valueOf(i));
            }
        }
        return Pair.of(section, map);
    }

    private static class Signers {
        private Signers() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void verify(DataSource apk, long cdStartOffset, List<CentralDirectoryRecord> cdRecords, Set<String> cdEntryNames, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion, Result result) throws NoSuchAlgorithmException, IOException, ApkFormatException {
            HashMap map = new HashMap(1);
            ArrayList<CentralDirectoryRecord> arrayList = new ArrayList(1);
            CentralDirectoryRecord centralDirectoryRecord = null;
            for (CentralDirectoryRecord centralDirectoryRecord2 : cdRecords) {
                String name2 = centralDirectoryRecord2.getName();
                if (name2.startsWith("META-INF/")) {
                    if (centralDirectoryRecord == null && "META-INF/MANIFEST.MF".equals(name2)) {
                        centralDirectoryRecord = centralDirectoryRecord2;
                    } else if (name2.endsWith(".SF")) {
                        map.put(name2, centralDirectoryRecord2);
                    } else if (name2.endsWith(".RSA") || name2.endsWith(".DSA") || name2.endsWith(".EC")) {
                        arrayList.add(centralDirectoryRecord2);
                    }
                }
            }
            int i = 0;
            if (centralDirectoryRecord != null) {
                try {
                    byte[] uncompressedData = LocalFileRecord.getUncompressedData(apk, centralDirectoryRecord, cdStartOffset);
                    Pair<ManifestParser.Section, Map<String, ManifestParser.Section>> manifest = V1SchemeVerifier.parseManifest(uncompressedData, cdEntryNames, result);
                    if (result.containsErrors()) {
                        return;
                    }
                    ManifestParser.Section first = manifest.getFirst();
                    Map<String, ManifestParser.Section> second = manifest.getSecond();
                    ArrayList<Signer> arrayList2 = new ArrayList(arrayList.size());
                    for (CentralDirectoryRecord centralDirectoryRecord3 : arrayList) {
                        String name3 = centralDirectoryRecord3.getName();
                        int iLastIndexOf = name3.lastIndexOf(46);
                        if (iLastIndexOf == -1) {
                            throw new RuntimeException("Signature block file name does not contain extension: " + name3);
                        }
                        String str = name3.substring(i, iLastIndexOf) + ".SF";
                        CentralDirectoryRecord centralDirectoryRecord4 = (CentralDirectoryRecord) map.get(str);
                        if (centralDirectoryRecord4 != null) {
                            String strSubstring = name3.substring("META-INF/".length());
                            arrayList2.add(new Signer(strSubstring, centralDirectoryRecord3, centralDirectoryRecord4, new Result.SignerInfo(strSubstring, name3, centralDirectoryRecord4.getName())));
                            i = 0;
                        } else {
                            result.addWarning(ApkVerifier.Issue.JAR_SIG_MISSING_FILE, name3, str);
                        }
                    }
                    if (!arrayList2.isEmpty()) {
                        for (Signer signer : arrayList2) {
                            ArrayList arrayList3 = arrayList2;
                            byte[] bArr = uncompressedData;
                            signer.verifySigBlockAgainstSigFile(apk, cdStartOffset, minSdkVersion, maxSdkVersion);
                            if (signer.getResult().containsErrors()) {
                                result.signers.add(signer.getResult());
                            }
                            arrayList2 = arrayList3;
                            uncompressedData = bArr;
                        }
                        ArrayList<Signer> arrayList4 = arrayList2;
                        byte[] bArr2 = uncompressedData;
                        if (result.containsErrors()) {
                            return;
                        }
                        ArrayList<Signer> arrayList5 = new ArrayList(arrayList4.size());
                        for (Signer signer2 : arrayList4) {
                            signer2.verifySigFileAgainstManifest(bArr2, first, second, supportedApkSigSchemeNames, foundApkSigSchemeIds, minSdkVersion, maxSdkVersion);
                            if (signer2.isIgnored()) {
                                result.ignoredSigners.add(signer2.getResult());
                            } else if (signer2.getResult().containsErrors()) {
                                result.signers.add(signer2.getResult());
                            } else {
                                arrayList5.add(signer2);
                            }
                        }
                        if (result.containsErrors()) {
                            return;
                        }
                        if (!arrayList5.isEmpty()) {
                            Set<Signer> setVerifyJarEntriesAgainstManifestAndSigners = V1SchemeVerifier.verifyJarEntriesAgainstManifestAndSigners(apk, cdStartOffset, cdRecords, second, arrayList5, minSdkVersion, maxSdkVersion, result);
                            if (result.containsErrors()) {
                                return;
                            }
                            HashSet hashSet = new HashSet((result.signers.size() * 2) + 1);
                            hashSet.add(centralDirectoryRecord.getName());
                            for (Signer signer3 : setVerifyJarEntriesAgainstManifestAndSigners) {
                                hashSet.add(signer3.getSignatureBlockEntryName());
                                hashSet.add(signer3.getSignatureFileEntryName());
                            }
                            Iterator<CentralDirectoryRecord> it = cdRecords.iterator();
                            while (it.hasNext()) {
                                String name4 = it.next().getName();
                                if (name4.startsWith("META-INF/") && !name4.endsWith("/") && !hashSet.contains(name4)) {
                                    result.addWarning(ApkVerifier.Issue.JAR_SIG_UNPROTECTED_ZIP_ENTRY, name4);
                                }
                            }
                            for (Signer signer4 : arrayList5) {
                                if (setVerifyJarEntriesAgainstManifestAndSigners.contains(signer4)) {
                                    result.signers.add(signer4.getResult());
                                } else {
                                    result.ignoredSigners.add(signer4.getResult());
                                }
                            }
                            result.verified = true;
                            return;
                        }
                        result.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNATURES, new Object[0]);
                        return;
                    }
                    result.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNATURES, new Object[0]);
                    return;
                } catch (ZipFormatException e) {
                    throw new ApkFormatException("Malformed ZIP entry: " + centralDirectoryRecord.getName(), e);
                }
            }
            result.addError(ApkVerifier.Issue.JAR_SIG_NO_MANIFEST, new Object[0]);
        }
    }

    static class Signer {
        private boolean mIgnored;
        private final String mName;
        private final Result.SignerInfo mResult;
        private byte[] mSigFileBytes;
        private Set<String> mSigFileEntryNames;
        private final CentralDirectoryRecord mSignatureBlockEntry;
        private final CentralDirectoryRecord mSignatureFileEntry;

        private Signer(String name2, CentralDirectoryRecord sigBlockEntry, CentralDirectoryRecord sigFileEntry, Result.SignerInfo result) {
            this.mName = name2;
            this.mResult = result;
            this.mSignatureBlockEntry = sigBlockEntry;
            this.mSignatureFileEntry = sigFileEntry;
        }

        public String getName() {
            return this.mName;
        }

        public String getSignatureFileEntryName() {
            return this.mSignatureFileEntry.getName();
        }

        public String getSignatureBlockEntryName() {
            return this.mSignatureBlockEntry.getName();
        }

        void setIgnored() {
            this.mIgnored = true;
        }

        public boolean isIgnored() {
            return this.mIgnored;
        }

        public Set<String> getSigFileEntryNames() {
            return this.mSigFileEntryNames;
        }

        public Result.SignerInfo getResult() {
            return this.mResult;
        }

        public void verifySigBlockAgainstSigFile(DataSource apk, long cdStartOffset, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException, IOException, ApkFormatException {
            try {
                byte[] uncompressedData = LocalFileRecord.getUncompressedData(apk, this.mSignatureBlockEntry, cdStartOffset);
                try {
                    this.mSigFileBytes = LocalFileRecord.getUncompressedData(apk, this.mSignatureFileEntry, cdStartOffset);
                    try {
                        ContentInfo contentInfo = (ContentInfo) Asn1BerParser.parse(ByteBuffer.wrap(uncompressedData), ContentInfo.class);
                        if (!Pkcs7Constants.OID_SIGNED_DATA.equals(contentInfo.contentType)) {
                            throw new Asn1DecodingException("Unsupported ContentInfo.contentType: " + contentInfo.contentType);
                        }
                        SignedData signedData = (SignedData) Asn1BerParser.parse(contentInfo.content.getEncoded(), SignedData.class);
                        if (signedData.signerInfos.isEmpty()) {
                            this.mResult.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNERS, this.mSignatureBlockEntry.getName());
                            return;
                        }
                        SignerInfo signerInfo = null;
                        List<X509Certificate> certificates = null;
                        X509Certificate x509Certificate = null;
                        for (SignerInfo signerInfo2 : minSdkVersion < 24 ? Collections.singletonList(signedData.signerInfos.get(0)) : signedData.signerInfos) {
                            if (certificates == null) {
                                try {
                                    certificates = Certificate.parseCertificates(signedData.certificates);
                                } catch (CertificateException e) {
                                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_PARSE_EXCEPTION, this.mSignatureBlockEntry.getName(), e);
                                    return;
                                }
                            }
                            List<X509Certificate> list = certificates;
                            try {
                                X509Certificate x509CertificateVerifySignerInfoAgainstSigFile = verifySignerInfoAgainstSigFile(signedData, list, signerInfo2, this.mSigFileBytes, minSdkVersion, maxSdkVersion);
                                if (this.mResult.containsErrors()) {
                                    return;
                                }
                                if (x509CertificateVerifySignerInfoAgainstSigFile != null && signerInfo == null) {
                                    x509Certificate = x509CertificateVerifySignerInfoAgainstSigFile;
                                    signerInfo = signerInfo2;
                                }
                                certificates = list;
                            } catch (Pkcs7DecodingException e2) {
                                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_PARSE_EXCEPTION, this.mSignatureBlockEntry.getName(), e2);
                                return;
                            } catch (InvalidKeyException e3) {
                                e = e3;
                                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_VERIFY_EXCEPTION, this.mSignatureBlockEntry.getName(), this.mSignatureFileEntry.getName(), e);
                                return;
                            } catch (SignatureException e4) {
                                e = e4;
                                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_VERIFY_EXCEPTION, this.mSignatureBlockEntry.getName(), this.mSignatureFileEntry.getName(), e);
                                return;
                            }
                        }
                        if (signerInfo == null) {
                            this.mResult.addError(ApkVerifier.Issue.JAR_SIG_DID_NOT_VERIFY, this.mSignatureBlockEntry.getName(), this.mSignatureFileEntry.getName());
                            return;
                        }
                        List<X509Certificate> certificateChain = getCertificateChain(certificates, x509Certificate);
                        this.mResult.certChain.clear();
                        this.mResult.certChain.addAll(certificateChain);
                    } catch (Asn1DecodingException e5) {
                        e5.printStackTrace();
                        this.mResult.addError(ApkVerifier.Issue.JAR_SIG_PARSE_EXCEPTION, this.mSignatureBlockEntry.getName(), e5);
                    }
                } catch (ZipFormatException e6) {
                    throw new ApkFormatException("Malformed ZIP entry: " + this.mSignatureFileEntry.getName(), e6);
                }
            } catch (ZipFormatException e7) {
                throw new ApkFormatException("Malformed ZIP entry: " + this.mSignatureBlockEntry.getName(), e7);
            }
        }

        private X509Certificate verifySignerInfoAgainstSigFile(SignedData signedData, Collection<X509Certificate> signedDataCertificates, SignerInfo signerInfo, byte[] signatureFile, int minSdkVersion, int maxSdkVersion) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, Pkcs7DecodingException {
            String str = signerInfo.digestAlgorithm.algorithm;
            String str2 = signerInfo.signatureAlgorithm.algorithm;
            List<InclusiveIntRange> valuesNotIn = InclusiveIntRange.fromTo(minSdkVersion, maxSdkVersion).getValuesNotIn(OidConstants.getSigAlgSupportedApiLevels(str, str2));
            if (!valuesNotIn.isEmpty()) {
                String userFriendlyNameForOid = OidConstants.OidToUserFriendlyNameMapper.getUserFriendlyNameForOid(str);
                String str3 = userFriendlyNameForOid == null ? str : userFriendlyNameForOid;
                String userFriendlyNameForOid2 = OidConstants.OidToUserFriendlyNameMapper.getUserFriendlyNameForOid(str2);
                String str4 = userFriendlyNameForOid2 == null ? str2 : userFriendlyNameForOid2;
                StringBuilder sb = new StringBuilder();
                for (InclusiveIntRange inclusiveIntRange : valuesNotIn) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    if (inclusiveIntRange.getMin() == inclusiveIntRange.getMax()) {
                        sb.append(String.valueOf(inclusiveIntRange.getMin()));
                    } else if (inclusiveIntRange.getMax() == Integer.MAX_VALUE) {
                        sb.append(inclusiveIntRange.getMin() + Marker.ANY_NON_NULL_MARKER);
                    } else {
                        sb.append(inclusiveIntRange.getMin() + Parameters.DEFAULT_OPTION_PREFIXES + inclusiveIntRange.getMax());
                    }
                }
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_UNSUPPORTED_SIG_ALG, this.mSignatureBlockEntry.getName(), str, str2, sb.toString(), str3, str4);
                return null;
            }
            X509Certificate x509CertificateFindCertificate = Certificate.findCertificate(signedDataCertificates, signerInfo.sid);
            if (x509CertificateFindCertificate == null) {
                throw new SignatureException("Signing certificate referenced in SignerInfo not found in SignedData");
            }
            if (x509CertificateFindCertificate.hasUnsupportedCriticalExtension()) {
                throw new SignatureException("Signing certificate has unsupported critical extensions");
            }
            boolean[] keyUsage = x509CertificateFindCertificate.getKeyUsage();
            if (keyUsage != null) {
                boolean z = false;
                boolean z2 = keyUsage.length >= 1 && keyUsage[0];
                if (keyUsage.length >= 2 && keyUsage[1]) {
                    z = true;
                }
                if (!z2 && !z) {
                    throw new SignatureException("Signing certificate not authorized for use in digital signatures: keyUsage extension missing digitalSignature and nonRepudiation");
                }
            }
            String jcaSignatureAlgorithm = AlgorithmIdentifier.getJcaSignatureAlgorithm(str, str2);
            Signature signature = Signature.getInstance(jcaSignatureAlgorithm);
            PublicKey publicKey = x509CertificateFindCertificate.getPublicKey();
            try {
                signature.initVerify(publicKey);
            } catch (InvalidKeyException e) {
                try {
                    PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(publicKey.getAlgorithm()).generatePublic(new X509EncodedKeySpec(ApkSigningBlockUtils.encodePublicKey(publicKey)));
                    Signature signature2 = Signature.getInstance(jcaSignatureAlgorithm);
                    signature2.initVerify(publicKeyGeneratePublic);
                    signature = signature2;
                } catch (InvalidKeySpecException unused) {
                    throw e;
                }
            }
            if (signerInfo.signedAttrs != null) {
                if (minSdkVersion < 19) {
                    throw new SignatureException("APKs with Signed Attributes broken on platforms with API Level < 19");
                }
                try {
                    SignedAttributes signedAttributes = new SignedAttributes(Asn1BerParser.parseImplicitSetOf(signerInfo.signedAttrs.getEncoded(), Attribute.class));
                    if (maxSdkVersion >= 24) {
                        String singleObjectIdentifierValue = signedAttributes.getSingleObjectIdentifierValue(Pkcs7Constants.OID_CONTENT_TYPE);
                        if (singleObjectIdentifierValue == null) {
                            throw new SignatureException("No Content Type in signed attributes");
                        }
                        if (!singleObjectIdentifierValue.equals(signedData.encapContentInfo.contentType)) {
                            return null;
                        }
                    }
                    byte[] singleOctetStringValue = signedAttributes.getSingleOctetStringValue(Pkcs7Constants.OID_MESSAGE_DIGEST);
                    if (singleOctetStringValue == null) {
                        throw new SignatureException("No content digest in signed attributes");
                    }
                    if (!Arrays.equals(singleOctetStringValue, MessageDigest.getInstance(AlgorithmIdentifier.getJcaDigestAlgorithm(str)).digest(signatureFile))) {
                        return null;
                    }
                    ByteBuffer encoded = signerInfo.signedAttrs.getEncoded();
                    signature.update((byte) 49);
                    encoded.position(1);
                    signature.update(encoded);
                } catch (Asn1DecodingException e2) {
                    throw new SignatureException("Failed to parse signed attributes", e2);
                }
            } else {
                signature.update(signatureFile);
            }
            if (signature.verify(ByteBufferUtils.toByteArray(signerInfo.signature.slice()))) {
                return x509CertificateFindCertificate;
            }
            return null;
        }

        public static List<X509Certificate> getCertificateChain(List<X509Certificate> certs, X509Certificate leaf) {
            ArrayList arrayList = new ArrayList(certs);
            ArrayList arrayList2 = new ArrayList(1);
            arrayList2.add(leaf);
            arrayList.remove(leaf);
            while (!leaf.getSubjectDN().equals(leaf.getIssuerDN())) {
                Principal issuerDN = leaf.getIssuerDN();
                boolean z = false;
                for (int i = 0; i < arrayList.size(); i++) {
                    X509Certificate x509Certificate = (X509Certificate) arrayList.get(i);
                    if (issuerDN.equals(x509Certificate.getSubjectDN())) {
                        arrayList.remove(i);
                        arrayList2.add(x509Certificate);
                        z = true;
                        leaf = x509Certificate;
                        break;
                    }
                }
                if (!z) {
                    break;
                }
            }
            return arrayList2;
        }

        public void verifySigFileAgainstManifest(byte[] manifestBytes, ManifestParser.Section manifestMainSection, Map<String, ManifestParser.Section> entryNameToManifestSection, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            boolean z;
            ManifestParser manifestParser = new ManifestParser(this.mSigFileBytes);
            ManifestParser.Section section = manifestParser.readSection();
            if (section.getAttributeValue(Attributes.Name.SIGNATURE_VERSION) != null) {
                if (maxSdkVersion >= 24) {
                    checkForStrippedApkSignatures(section, supportedApkSigSchemeNames, foundApkSigSchemeIds);
                    if (this.mResult.containsErrors()) {
                        return;
                    }
                }
                String attributeValue = section.getAttributeValue("Created-By");
                int i = 0;
                if (attributeValue != null) {
                    z = attributeValue.indexOf("signtool") != -1;
                } else {
                    z = false;
                }
                boolean zVerifyManifestDigest = verifyManifestDigest(section, z, manifestBytes, minSdkVersion, maxSdkVersion);
                if (!z) {
                    verifyManifestMainSectionDigest(section, manifestMainSection, manifestBytes, minSdkVersion, maxSdkVersion);
                }
                if (this.mResult.containsErrors()) {
                    return;
                }
                List<ManifestParser.Section> allSections = manifestParser.readAllSections();
                HashSet hashSet = new HashSet(allSections.size());
                for (ManifestParser.Section section2 : allSections) {
                    i++;
                    String name2 = section2.getName();
                    if (name2 != null) {
                        if (!hashSet.add(name2)) {
                            this.mResult.addError(ApkVerifier.Issue.JAR_SIG_DUPLICATE_SIG_FILE_SECTION, this.mSignatureFileEntry.getName(), name2);
                            setIgnored();
                            return;
                        } else if (!zVerifyManifestDigest) {
                            ManifestParser.Section section3 = entryNameToManifestSection.get(name2);
                            if (section3 != null) {
                                verifyManifestIndividualSectionDigest(section2, z, section3, manifestBytes, minSdkVersion, maxSdkVersion);
                            } else {
                                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE, name2, this.mSignatureFileEntry.getName());
                                setIgnored();
                            }
                        }
                    } else {
                        this.mResult.addError(ApkVerifier.Issue.JAR_SIG_UNNNAMED_SIG_FILE_SECTION, this.mSignatureFileEntry.getName(), Integer.valueOf(i));
                        setIgnored();
                        return;
                    }
                }
                this.mSigFileEntryNames = hashSet;
                return;
            }
            this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MISSING_VERSION_ATTR_IN_SIG_FILE, this.mSignatureFileEntry.getName());
            setIgnored();
        }

        private boolean verifyManifestDigest(ManifestParser.Section sfMainSection, boolean createdBySigntool, byte[] manifestBytes, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            Collection<NamedDigest> digestsToVerify = V1SchemeVerifier.getDigestsToVerify(sfMainSection, createdBySigntool ? "-Digest" : "-Digest-Manifest", minSdkVersion, maxSdkVersion);
            if (!digestsToVerify.isEmpty()) {
                boolean z = true;
                for (NamedDigest namedDigest : digestsToVerify) {
                    String str = namedDigest.jcaDigestAlgorithm;
                    byte[] bArrDigest = V1SchemeVerifier.digest(str, manifestBytes);
                    byte[] bArr = namedDigest.digest;
                    if (!Arrays.equals(bArr, bArrDigest)) {
                        this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY, "META-INF/MANIFEST.MF", str, this.mSignatureFileEntry.getName(), Base64.getEncoder().encodeToString(bArrDigest), Base64.getEncoder().encodeToString(bArr));
                        z = false;
                    }
                }
                return z;
            }
            this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_NO_MANIFEST_DIGEST_IN_SIG_FILE, this.mSignatureFileEntry.getName());
            return false;
        }

        private void verifyManifestMainSectionDigest(ManifestParser.Section sfMainSection, ManifestParser.Section manifestMainSection, byte[] manifestBytes, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            Collection<NamedDigest> digestsToVerify = V1SchemeVerifier.getDigestsToVerify(sfMainSection, "-Digest-Manifest-Main-Attributes", minSdkVersion, maxSdkVersion);
            if (digestsToVerify.isEmpty()) {
                return;
            }
            for (NamedDigest namedDigest : digestsToVerify) {
                String str = namedDigest.jcaDigestAlgorithm;
                byte[] bArrDigest = V1SchemeVerifier.digest(str, manifestBytes, manifestMainSection.getStartOffset(), manifestMainSection.getSizeBytes());
                byte[] bArr = namedDigest.digest;
                if (!Arrays.equals(bArr, bArrDigest)) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MANIFEST_MAIN_SECTION_DIGEST_DID_NOT_VERIFY, str, this.mSignatureFileEntry.getName(), Base64.getEncoder().encodeToString(bArrDigest), Base64.getEncoder().encodeToString(bArr));
                }
            }
        }

        private void verifyManifestIndividualSectionDigest(ManifestParser.Section sfIndividualSection, boolean createdBySigntool, ManifestParser.Section manifestIndividualSection, byte[] manifestBytes, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            String name2 = sfIndividualSection.getName();
            Collection<NamedDigest> digestsToVerify = V1SchemeVerifier.getDigestsToVerify(sfIndividualSection, "-Digest", minSdkVersion, maxSdkVersion);
            if (!digestsToVerify.isEmpty()) {
                int startOffset = manifestIndividualSection.getStartOffset();
                int sizeBytes = manifestIndividualSection.getSizeBytes();
                if (createdBySigntool) {
                    int i = startOffset + sizeBytes;
                    if (manifestBytes[i - 1] == 10 && manifestBytes[i - 2] == 10) {
                        sizeBytes--;
                    }
                }
                for (NamedDigest namedDigest : digestsToVerify) {
                    String str = namedDigest.jcaDigestAlgorithm;
                    byte[] bArrDigest = V1SchemeVerifier.digest(str, manifestBytes, startOffset, sizeBytes);
                    byte[] bArr = namedDigest.digest;
                    if (!Arrays.equals(bArr, bArrDigest)) {
                        this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MANIFEST_SECTION_DIGEST_DID_NOT_VERIFY, name2, str, this.mSignatureFileEntry.getName(), Base64.getEncoder().encodeToString(bArrDigest), Base64.getEncoder().encodeToString(bArr));
                    }
                }
                return;
            }
            this.mResult.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE, name2, this.mSignatureFileEntry.getName());
        }

        private void checkForStrippedApkSignatures(ManifestParser.Section sfMainSection, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds) {
            String attributeValue = sfMainSection.getAttributeValue(V1SchemeConstants.SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME_STR);
            if (attributeValue == null) {
                if (foundApkSigSchemeIds.isEmpty()) {
                    return;
                }
                this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_NO_APK_SIG_STRIP_PROTECTION, this.mSignatureFileEntry.getName());
                return;
            }
            if (supportedApkSigSchemeNames.isEmpty()) {
                return;
            }
            Set<Integer> setKeySet = supportedApkSigSchemeNames.keySet();
            HashSet hashSet = new HashSet(1);
            StringTokenizer stringTokenizer = new StringTokenizer(attributeValue, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String strTrim = stringTokenizer.nextToken().trim();
                if (!strTrim.isEmpty()) {
                    try {
                        int i = Integer.parseInt(strTrim);
                        if (setKeySet.contains(Integer.valueOf(i))) {
                            hashSet.add(Integer.valueOf(i));
                        } else {
                            this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_UNKNOWN_APK_SIG_SCHEME_ID, this.mSignatureFileEntry.getName(), Integer.valueOf(i));
                        }
                    } catch (Exception unused) {
                    }
                }
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) it.next()).intValue();
                if (!foundApkSigSchemeIds.contains(Integer.valueOf(iIntValue))) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MISSING_APK_SIG_REFERENCED, this.mSignatureFileEntry.getName(), Integer.valueOf(iIntValue), supportedApkSigSchemeNames.get(Integer.valueOf(iIntValue)));
                }
            }
        }
    }

    public static Collection<NamedDigest> getDigestsToVerify(ManifestParser.Section section, String digestAttrSuffix, int minSdkVersion, int maxSdkVersion) {
        String canonicalJcaMessageDigestAlgorithm;
        Base64.Decoder decoder = Base64.getDecoder();
        ArrayList arrayList = new ArrayList(1);
        if (minSdkVersion < 18) {
            String attributeValue = section.getAttributeValue("Digest-Algorithms");
            if (attributeValue == null) {
                attributeValue = "SHA SHA1";
            }
            StringTokenizer stringTokenizer = new StringTokenizer(attributeValue);
            while (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                String attributeValue2 = section.getAttributeValue(strNextToken + digestAttrSuffix);
                if (attributeValue2 != null && (canonicalJcaMessageDigestAlgorithm = getCanonicalJcaMessageDigestAlgorithm(strNextToken)) != null && getMinSdkVersionFromWhichSupportedInManifestOrSignatureFile(canonicalJcaMessageDigestAlgorithm) <= minSdkVersion) {
                    arrayList.add(new NamedDigest(canonicalJcaMessageDigestAlgorithm, decoder.decode(attributeValue2)));
                    break;
                }
            }
            if (arrayList.isEmpty()) {
                return arrayList;
            }
        }
        if (maxSdkVersion >= 18) {
            for (String str : JB_MR2_AND_NEWER_DIGEST_ALGS) {
                String attributeValue3 = section.getAttributeValue(getJarDigestAttributeName(str, digestAttrSuffix));
                if (attributeValue3 != null) {
                    byte[] bArrDecode = decoder.decode(attributeValue3);
                    byte[] digest = getDigest(arrayList, str);
                    if (digest != null && Arrays.equals(digest, bArrDecode)) {
                        break;
                    }
                    arrayList.add(new NamedDigest(str, bArrDecode));
                    break;
                }
            }
        }
        return arrayList;
    }

    static {
        HashMap map = new HashMap(8);
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL = map;
        map.put("MD5", "MD5");
        map.put("SHA", McElieceCCA2KeyGenParameterSpec.SHA1);
        map.put("SHA1", McElieceCCA2KeyGenParameterSpec.SHA1);
        map.put(McElieceCCA2KeyGenParameterSpec.SHA1, McElieceCCA2KeyGenParameterSpec.SHA1);
        map.put(McElieceCCA2KeyGenParameterSpec.SHA256, McElieceCCA2KeyGenParameterSpec.SHA256);
        map.put(McElieceCCA2KeyGenParameterSpec.SHA384, McElieceCCA2KeyGenParameterSpec.SHA384);
        map.put(McElieceCCA2KeyGenParameterSpec.SHA512, McElieceCCA2KeyGenParameterSpec.SHA512);
        HashMap map2 = new HashMap(5);
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST = map2;
        map2.put("MD5", 0);
        map2.put(McElieceCCA2KeyGenParameterSpec.SHA1, 0);
        map2.put(McElieceCCA2KeyGenParameterSpec.SHA256, 0);
        map2.put(McElieceCCA2KeyGenParameterSpec.SHA384, 9);
        map2.put(McElieceCCA2KeyGenParameterSpec.SHA512, 9);
    }

    private static String getCanonicalJcaMessageDigestAlgorithm(String algorithm) {
        return UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.get(algorithm.toUpperCase(Locale.US));
    }

    public static int getMinSdkVersionFromWhichSupportedInManifestOrSignatureFile(String jcaAlgorithmName) {
        Integer num = MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.get(jcaAlgorithmName.toUpperCase(Locale.US));
        if (num != null) {
            return num.intValue();
        }
        return Integer.MAX_VALUE;
    }

    private static String getJarDigestAttributeName(String jcaDigestAlgorithm, String attrNameSuffix) {
        if (McElieceCCA2KeyGenParameterSpec.SHA1.equalsIgnoreCase(jcaDigestAlgorithm)) {
            return "SHA1" + attrNameSuffix;
        }
        return jcaDigestAlgorithm + attrNameSuffix;
    }

    private static byte[] getDigest(Collection<NamedDigest> digests, String jcaDigestAlgorithm) {
        for (NamedDigest namedDigest : digests) {
            if (namedDigest.jcaDigestAlgorithm.equalsIgnoreCase(jcaDigestAlgorithm)) {
                return namedDigest.digest;
            }
        }
        return null;
    }

    public static List<CentralDirectoryRecord> parseZipCentralDirectory(DataSource apk, ApkUtils.ZipSections apkSections) throws IOException, ApkFormatException {
        return ZipUtils.parseZipCentralDirectory(apk, apkSections);
    }

    private static boolean isJarEntryDigestNeededInManifest(String entryName) {
        if (entryName.startsWith("META-INF/")) {
            return false;
        }
        return !entryName.endsWith("/");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Set<Signer> verifyJarEntriesAgainstManifestAndSigners(DataSource apk, long cdOffsetInApk, Collection<CentralDirectoryRecord> cdRecords, Map<String, ManifestParser.Section> entryNameToManifestSection, List<Signer> signers, int minSdkVersion, int maxSdkVersion, Result result) throws NoSuchAlgorithmException, IOException, ApkFormatException {
        ArrayList arrayList = new ArrayList(cdRecords);
        Collections.sort(arrayList, CentralDirectoryRecord.BY_LOCAL_FILE_HEADER_OFFSET_COMPARATOR);
        Iterator it = arrayList.iterator();
        ArrayList arrayList2 = null;
        String str = null;
        while (true) {
            int i = 0;
            if (!it.hasNext()) {
                break;
            }
            CentralDirectoryRecord centralDirectoryRecord = (CentralDirectoryRecord) it.next();
            String name2 = centralDirectoryRecord.getName();
            if (isJarEntryDigestNeededInManifest(name2)) {
                ManifestParser.Section section = entryNameToManifestSection.get(name2);
                if (section != null) {
                    ArrayList arrayList3 = new ArrayList(signers.size());
                    for (Signer signer : signers) {
                        if (signer.getSigFileEntryNames().contains(name2)) {
                            arrayList3.add(signer);
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        if (arrayList2 == null) {
                            str = name2;
                            arrayList2 = arrayList3;
                        } else if (!arrayList3.equals(arrayList2)) {
                            result.addError(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_SIGNERS_MISMATCH, str, getSignerNames(arrayList2), name2, getSignerNames(arrayList3));
                        }
                        ArrayList arrayList4 = new ArrayList(getDigestsToVerify(section, "-Digest", minSdkVersion, maxSdkVersion));
                        if (!arrayList4.isEmpty()) {
                            MessageDigest[] messageDigestArr = new MessageDigest[arrayList4.size()];
                            for (int i2 = 0; i2 < arrayList4.size(); i2++) {
                                messageDigestArr[i2] = getMessageDigest(((NamedDigest) arrayList4.get(i2)).jcaDigestAlgorithm);
                            }
                            try {
                                LocalFileRecord.outputUncompressedData(apk, centralDirectoryRecord, cdOffsetInApk, DataSinks.asDataSink(messageDigestArr));
                                while (i < arrayList4.size()) {
                                    NamedDigest namedDigest = (NamedDigest) arrayList4.get(i);
                                    byte[] bArrDigest = messageDigestArr[i].digest();
                                    Iterator it2 = it;
                                    if (!Arrays.equals(namedDigest.digest, bArrDigest)) {
                                        result.addError(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY, name2, namedDigest.jcaDigestAlgorithm, "META-INF/MANIFEST.MF", Base64.getEncoder().encodeToString(bArrDigest), Base64.getEncoder().encodeToString(namedDigest.digest));
                                    }
                                    i++;
                                    it = it2;
                                    arrayList2 = arrayList2;
                                    str = str;
                                }
                            } catch (ZipFormatException e) {
                                throw new ApkFormatException("Malformed ZIP entry: " + name2, e);
                            } catch (IOException e2) {
                                throw new IOException("Failed to read entry: " + name2, e2);
                            }
                        } else {
                            result.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST, name2);
                        }
                        it = it;
                        arrayList2 = arrayList2;
                        str = str;
                    } else {
                        result.addError(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_NOT_SIGNED, name2);
                    }
                } else {
                    result.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST, name2);
                }
            }
        }
        if (arrayList2 != null) {
            return new HashSet(arrayList2);
        }
        result.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNED_ZIP_ENTRIES, new Object[0]);
        return Collections.emptySet();
    }

    private static List<String> getSignerNames(List<Signer> signers) {
        if (signers.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(signers.size());
        Iterator<Signer> it = signers.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    private static MessageDigest getMessageDigest(String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] digest(String algorithm, byte[] data, int offset, int length) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = getMessageDigest(algorithm);
        messageDigest.update(data, offset, length);
        return messageDigest.digest();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] digest(String algorithm, byte[] data) throws NoSuchAlgorithmException {
        return getMessageDigest(algorithm).digest(data);
    }

    public static class NamedDigest {
        public final byte[] digest;
        public final String jcaDigestAlgorithm;

        private NamedDigest(String jcaDigestAlgorithm, byte[] digest) {
            this.jcaDigestAlgorithm = jcaDigestAlgorithm;
            this.digest = digest;
        }
    }

    public static class Result {
        public boolean verified;
        public final List<SignerInfo> signers = new ArrayList();
        public final List<SignerInfo> ignoredSigners = new ArrayList();
        private final List<ApkVerifier.IssueWithParams> mWarnings = new ArrayList();
        private final List<ApkVerifier.IssueWithParams> mErrors = new ArrayList();

        /* JADX INFO: Access modifiers changed from: private */
        public boolean containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true;
            }
            Iterator<SignerInfo> it = this.signers.iterator();
            while (it.hasNext()) {
                if (it.next().containsErrors()) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addError(ApkVerifier.Issue msg, Object... parameters) {
            this.mErrors.add(new ApkVerifier.IssueWithParams(msg, parameters));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addWarning(ApkVerifier.Issue msg, Object... parameters) {
            this.mWarnings.add(new ApkVerifier.IssueWithParams(msg, parameters));
        }

        public List<ApkVerifier.IssueWithParams> getErrors() {
            return this.mErrors;
        }

        public List<ApkVerifier.IssueWithParams> getWarnings() {
            return this.mWarnings;
        }

        public static class SignerInfo {
            public final List<X509Certificate> certChain;
            private final List<ApkVerifier.IssueWithParams> mErrors;
            private final List<ApkVerifier.IssueWithParams> mWarnings;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            public final String f64name;
            public final String signatureBlockFileName;
            public final String signatureFileName;

            private SignerInfo(String name2, String signatureBlockFileName, String signatureFileName) {
                this.certChain = new ArrayList();
                this.mWarnings = new ArrayList();
                this.mErrors = new ArrayList();
                this.f64name = name2;
                this.signatureBlockFileName = signatureBlockFileName;
                this.signatureFileName = signatureFileName;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addError(ApkVerifier.Issue msg, Object... parameters) {
                this.mErrors.add(new ApkVerifier.IssueWithParams(msg, parameters));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addWarning(ApkVerifier.Issue msg, Object... parameters) {
                this.mWarnings.add(new ApkVerifier.IssueWithParams(msg, parameters));
            }

            public List<ApkVerifier.IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<ApkVerifier.IssueWithParams> getWarnings() {
                return this.mWarnings;
            }
        }
    }

    private static class SignedAttributes {
        private Map<String, List<Asn1OpaqueObject>> mAttrs;

        public SignedAttributes(Collection<Attribute> attrs) throws Pkcs7DecodingException {
            HashMap map = new HashMap(attrs.size());
            for (Attribute attribute : attrs) {
                if (map.put(attribute.attrType, attribute.attrValues) != null) {
                    throw new Pkcs7DecodingException("Duplicate signed attribute: " + attribute.attrType);
                }
            }
            this.mAttrs = map;
        }

        private Asn1OpaqueObject getSingleValue(String attrOid) throws Pkcs7DecodingException {
            List<Asn1OpaqueObject> list = this.mAttrs.get(attrOid);
            if (list == null || list.isEmpty()) {
                return null;
            }
            if (list.size() > 1) {
                throw new Pkcs7DecodingException("Attribute " + attrOid + " has multiple values");
            }
            return list.get(0);
        }

        public String getSingleObjectIdentifierValue(String attrOid) throws Pkcs7DecodingException {
            Asn1OpaqueObject singleValue = getSingleValue(attrOid);
            if (singleValue == null) {
                return null;
            }
            try {
                return ((ObjectIdentifierChoice) Asn1BerParser.parse(singleValue.getEncoded(), ObjectIdentifierChoice.class)).value;
            } catch (Asn1DecodingException e) {
                throw new Pkcs7DecodingException("Failed to decode OBJECT IDENTIFIER", e);
            }
        }

        public byte[] getSingleOctetStringValue(String attrOid) throws Pkcs7DecodingException {
            Asn1OpaqueObject singleValue = getSingleValue(attrOid);
            if (singleValue == null) {
                return null;
            }
            try {
                return ((OctetStringChoice) Asn1BerParser.parse(singleValue.getEncoded(), OctetStringChoice.class)).value;
            } catch (Asn1DecodingException e) {
                throw new Pkcs7DecodingException("Failed to decode OBJECT IDENTIFIER", e);
            }
        }
    }
}
