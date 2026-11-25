package com.android.apksig.internal.apk.v1;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.asn1.Asn1EncodingException;
import com.android.apksig.internal.jar.ManifestWriter;
import com.android.apksig.internal.jar.SignatureFileWriter;
import com.android.apksig.internal.pkcs7.AlgorithmIdentifier;
import com.android.apksig.internal.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes.dex */
public abstract class V1SchemeSigner {
    private static final String ATTRIBUTE_VALUE_MANIFEST_VERSION = "1.0";
    private static final String ATTRIBUTE_VALUE_SIGNATURE_VERSION = "1.0";
    public static final String MANIFEST_ENTRY_NAME = "META-INF/MANIFEST.MF";
    private static final Attributes.Name ATTRIBUTE_NAME_CREATED_BY = new Attributes.Name("Created-By");
    private static final Attributes.Name SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME = new Attributes.Name(V1SchemeConstants.SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME_STR);

    public static class OutputManifestFile {
        public byte[] contents;
        public SortedMap<String, byte[]> individualSectionsContents;
        public Attributes mainSectionAttributes;
    }

    public static class SignerConfig {
        public List<X509Certificate> certificates;
        public boolean deterministicDsaSigning;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f63name;
        public PrivateKey privateKey;
        public DigestAlgorithm signatureDigestAlgorithm;
    }

    private V1SchemeSigner() {
    }

    public static DigestAlgorithm getSuggestedSignatureDigestAlgorithm(PublicKey signingKey, int minSdkVersion) throws InvalidKeyException {
        String algorithm = signingKey.getAlgorithm();
        if ("RSA".equalsIgnoreCase(algorithm) || "1.2.840.113549.1.1.1".equals(algorithm)) {
            if (minSdkVersion < 18) {
                return DigestAlgorithm.SHA1;
            }
            return DigestAlgorithm.SHA256;
        }
        if ("DSA".equalsIgnoreCase(algorithm)) {
            if (minSdkVersion < 21) {
                return DigestAlgorithm.SHA1;
            }
            return DigestAlgorithm.SHA256;
        }
        if (!"EC".equalsIgnoreCase(algorithm)) {
            throw new InvalidKeyException("Unsupported key algorithm: " + algorithm);
        }
        if (minSdkVersion < 18) {
            throw new InvalidKeyException("ECDSA signatures only supported for minSdkVersion 18 and higher");
        }
        return DigestAlgorithm.SHA256;
    }

    public static String getSafeSignerName(String name2) {
        if (name2.isEmpty()) {
            throw new IllegalArgumentException("Empty name");
        }
        StringBuilder sb = new StringBuilder();
        char[] charArray = name2.toUpperCase(Locale.US).toCharArray();
        for (int i = 0; i < Math.min(charArray.length, 8); i++) {
            char c = charArray[i];
            if ((c >= 'A' && c <= 'Z') || ((c >= '0' && c <= '9') || c == '-' || c == '_')) {
                sb.append(c);
            } else {
                sb.append('_');
            }
        }
        return sb.toString();
    }

    private static MessageDigest getMessageDigestInstance(DigestAlgorithm digestAlgorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(digestAlgorithm.getJcaMessageDigestAlgorithm());
    }

    public static String getJcaMessageDigestAlgorithm(DigestAlgorithm digestAlgorithm) {
        return digestAlgorithm.getJcaMessageDigestAlgorithm();
    }

    public static boolean isJarEntryDigestNeededInManifest(String entryName) {
        if (entryName.endsWith("/")) {
            return false;
        }
        if (!entryName.startsWith("META-INF/") || entryName.indexOf(47, "META-INF/".length()) != -1) {
            return true;
        }
        String lowerCase = entryName.substring("META-INF/".length()).toLowerCase(Locale.US);
        return ("manifest.mf".equals(lowerCase) || lowerCase.endsWith(".sf") || lowerCase.endsWith(".rsa") || lowerCase.endsWith(".dsa") || lowerCase.endsWith(".ec") || lowerCase.startsWith("sig-")) ? false : true;
    }

    public static List<Pair<String, byte[]>> sign(List<SignerConfig> signerConfigs, DigestAlgorithm jarEntryDigestAlgorithm, Map<String, byte[]> jarEntryDigests, List<Integer> apkSigningSchemeIds, byte[] sourceManifestBytes, String createdBy) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, ApkFormatException, CertificateException {
        if (signerConfigs.isEmpty()) {
            throw new IllegalArgumentException("At least one signer config must be provided");
        }
        return signManifest(signerConfigs, jarEntryDigestAlgorithm, apkSigningSchemeIds, createdBy, generateManifestFile(jarEntryDigestAlgorithm, jarEntryDigests, sourceManifestBytes));
    }

    public static List<Pair<String, byte[]>> signManifest(List<SignerConfig> signerConfigs, DigestAlgorithm digestAlgorithm, List<Integer> apkSigningSchemeIds, String createdBy, OutputManifestFile manifest) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException {
        if (signerConfigs.isEmpty()) {
            throw new IllegalArgumentException("At least one signer config must be provided");
        }
        ArrayList arrayList = new ArrayList((signerConfigs.size() * 2) + 1);
        byte[] bArrGenerateSignatureFile = generateSignatureFile(apkSigningSchemeIds, digestAlgorithm, createdBy, manifest);
        for (SignerConfig signerConfig : signerConfigs) {
            String str = signerConfig.f63name;
            try {
                byte[] bArrGenerateSignatureBlock = generateSignatureBlock(signerConfig, bArrGenerateSignatureFile);
                arrayList.add(Pair.of("META-INF/" + str + ".SF", bArrGenerateSignatureFile));
                arrayList.add(Pair.of("META-INF/" + str + "." + signerConfig.certificates.get(0).getPublicKey().getAlgorithm().toUpperCase(Locale.US), bArrGenerateSignatureBlock));
            } catch (InvalidKeyException e) {
                throw new InvalidKeyException("Failed to sign using signer \"" + str + "\"", e);
            } catch (SignatureException e2) {
                throw new SignatureException("Failed to sign using signer \"" + str + "\"", e2);
            } catch (CertificateException e3) {
                throw new CertificateException("Failed to sign using signer \"" + str + "\"", e3);
            }
        }
        arrayList.add(Pair.of("META-INF/MANIFEST.MF", manifest.contents));
        return arrayList;
    }

    public static Set<String> getOutputEntryNames(List<SignerConfig> signerConfigs) {
        HashSet hashSet = new HashSet((signerConfigs.size() * 2) + 1);
        for (SignerConfig signerConfig : signerConfigs) {
            String str = signerConfig.f63name;
            hashSet.add("META-INF/" + str + ".SF");
            hashSet.add("META-INF/" + str + "." + signerConfig.certificates.get(0).getPublicKey().getAlgorithm().toUpperCase(Locale.US));
        }
        hashSet.add("META-INF/MANIFEST.MF");
        return hashSet;
    }

    public static OutputManifestFile generateManifestFile(DigestAlgorithm jarEntryDigestAlgorithm, Map<String, byte[]> jarEntryDigests, byte[] sourceManifestBytes) throws ApkFormatException {
        Manifest manifest;
        if (sourceManifestBytes != null) {
            try {
                manifest = new Manifest(new ByteArrayInputStream(sourceManifestBytes));
            } catch (IOException e) {
                throw new ApkFormatException("Malformed source META-INF/MANIFEST.MF", e);
            }
        } else {
            manifest = null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Attributes attributes = new Attributes();
        if (manifest != null) {
            attributes.putAll(manifest.getMainAttributes());
        } else {
            attributes.put(Attributes.Name.MANIFEST_VERSION, BuildConfig.VERSION_NAME);
        }
        try {
            ManifestWriter.writeMainSection(byteArrayOutputStream, attributes);
            ArrayList<String> arrayList = new ArrayList(jarEntryDigests.keySet());
            Collections.sort(arrayList);
            TreeMap treeMap = new TreeMap();
            String entryDigestAttributeName = getEntryDigestAttributeName(jarEntryDigestAlgorithm);
            for (String str : arrayList) {
                checkEntryNameValid(str);
                byte[] bArr = jarEntryDigests.get(str);
                Attributes attributes2 = new Attributes();
                attributes2.putValue(entryDigestAttributeName, Base64.getEncoder().encodeToString(bArr));
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    ManifestWriter.writeIndividualSection(byteArrayOutputStream2, str, attributes2);
                    byte[] byteArray = byteArrayOutputStream2.toByteArray();
                    byteArrayOutputStream.write(byteArray);
                    treeMap.put(str, byteArray);
                } catch (IOException e2) {
                    throw new RuntimeException("Failed to write in-memory MANIFEST.MF", e2);
                }
            }
            OutputManifestFile outputManifestFile = new OutputManifestFile();
            outputManifestFile.contents = byteArrayOutputStream.toByteArray();
            outputManifestFile.mainSectionAttributes = attributes;
            outputManifestFile.individualSectionsContents = treeMap;
            return outputManifestFile;
        } catch (IOException e3) {
            throw new RuntimeException("Failed to write in-memory MANIFEST.MF", e3);
        }
    }

    private static void checkEntryNameValid(String name2) throws ApkFormatException {
        for (char c : name2.toCharArray()) {
            if (c == '\r' || c == '\n' || c == 0) {
                throw new ApkFormatException(String.format("Unsupported character 0x%1$02x in ZIP entry name \"%2$s\"", Integer.valueOf(c), name2));
            }
        }
    }

    private static byte[] generateSignatureFile(List<Integer> apkSignatureSchemeIds, DigestAlgorithm manifestDigestAlgorithm, String createdBy, OutputManifestFile manifest) throws NoSuchAlgorithmException {
        Attributes mainAttributes = new Manifest().getMainAttributes();
        mainAttributes.put(Attributes.Name.SIGNATURE_VERSION, BuildConfig.VERSION_NAME);
        mainAttributes.put(ATTRIBUTE_NAME_CREATED_BY, createdBy);
        if (!apkSignatureSchemeIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator<Integer> it = apkSignatureSchemeIds.iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(String.valueOf(iIntValue));
            }
            mainAttributes.put(SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME, sb.toString());
        }
        MessageDigest messageDigestInstance = getMessageDigestInstance(manifestDigestAlgorithm);
        mainAttributes.putValue(getManifestDigestAttributeName(manifestDigestAlgorithm), Base64.getEncoder().encodeToString(messageDigestInstance.digest(manifest.contents)));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            SignatureFileWriter.writeMainSection(byteArrayOutputStream, mainAttributes);
            String entryDigestAttributeName = getEntryDigestAttributeName(manifestDigestAlgorithm);
            for (Map.Entry<String, byte[]> entry : manifest.individualSectionsContents.entrySet()) {
                String key = entry.getKey();
                byte[] bArrDigest = messageDigestInstance.digest(entry.getValue());
                Attributes attributes = new Attributes();
                attributes.putValue(entryDigestAttributeName, Base64.getEncoder().encodeToString(bArrDigest));
                try {
                    SignatureFileWriter.writeIndividualSection(byteArrayOutputStream, key, attributes);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to write in-memory .SF file", e);
                }
            }
            if (byteArrayOutputStream.size() > 0 && byteArrayOutputStream.size() % 1024 == 0) {
                try {
                    SignatureFileWriter.writeSectionDelimiter(byteArrayOutputStream);
                } catch (IOException e2) {
                    throw new RuntimeException("Failed to write to ByteArrayOutputStream", e2);
                }
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e3) {
            throw new RuntimeException("Failed to write in-memory .SF file", e3);
        }
    }

    private static byte[] generateSignatureBlock(SignerConfig signerConfig, byte[] signatureFileBytes) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException {
        List<X509Certificate> list = signerConfig.certificates;
        PublicKey publicKey = list.get(0).getPublicKey();
        DigestAlgorithm digestAlgorithm = signerConfig.signatureDigestAlgorithm;
        Pair<String, AlgorithmIdentifier> signerInfoSignatureAlgorithm = AlgorithmIdentifier.getSignerInfoSignatureAlgorithm(publicKey, digestAlgorithm, signerConfig.deterministicDsaSigning);
        String first = signerInfoSignatureAlgorithm.getFirst();
        try {
            Signature signature = Signature.getInstance(first);
            signature.initSign(signerConfig.privateKey);
            signature.update(signatureFileBytes);
            byte[] bArrSign = signature.sign();
            try {
                Signature signature2 = Signature.getInstance(first);
                signature2.initVerify(publicKey);
                signature2.update(signatureFileBytes);
                if (!signature2.verify(bArrSign)) {
                    throw new SignatureException("Signature did not verify");
                }
                try {
                    return ApkSigningBlockUtils.generatePkcs7DerEncodedMessage(bArrSign, null, list, AlgorithmIdentifier.getSignerInfoDigestAlgorithmOid(digestAlgorithm), signerInfoSignatureAlgorithm.getSecond());
                } catch (Asn1EncodingException | CertificateEncodingException unused) {
                    throw new SignatureException("Failed to encode signature block");
                }
            } catch (InvalidKeyException e) {
                throw new InvalidKeyException("Failed to verify generated " + first + " signature using public key from certificate", e);
            } catch (SignatureException e2) {
                throw new SignatureException("Failed to verify generated " + first + " signature using public key from certificate", e2);
            }
        } catch (InvalidKeyException e3) {
            throw new InvalidKeyException("Failed to sign using " + first, e3);
        } catch (SignatureException e4) {
            throw new SignatureException("Failed to sign using " + first, e4);
        }
    }

    /* JADX INFO: renamed from: com.android.apksig.internal.apk.v1.V1SchemeSigner$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm;

        static {
            int[] iArr = new int[DigestAlgorithm.values().length];
            $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm = iArr;
            try {
                iArr[DigestAlgorithm.SHA1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[DigestAlgorithm.SHA256.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static String getEntryDigestAttributeName(DigestAlgorithm digestAlgorithm) {
        int i = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[digestAlgorithm.ordinal()];
        if (i == 1) {
            return "SHA1-Digest";
        }
        if (i == 2) {
            return "SHA-256-Digest";
        }
        throw new IllegalArgumentException("Unexpected content digest algorithm: " + digestAlgorithm);
    }

    private static String getManifestDigestAttributeName(DigestAlgorithm digestAlgorithm) {
        int i = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[digestAlgorithm.ordinal()];
        if (i == 1) {
            return "SHA1-Digest-Manifest";
        }
        if (i == 2) {
            return "SHA-256-Digest-Manifest";
        }
        throw new IllegalArgumentException("Unexpected content digest algorithm: " + digestAlgorithm);
    }
}
