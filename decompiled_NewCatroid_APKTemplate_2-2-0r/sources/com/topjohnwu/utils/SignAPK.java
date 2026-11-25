package com.topjohnwu.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.security.DigestOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes11.dex */
public class SignAPK {
    private static final String CERT_SF_NAME = "META-INF/CERT.SF";
    private static final String CERT_SIG_NAME = "META-INF/CERT.%s";
    private static final int USE_SHA1 = 1;
    private static final int USE_SHA256 = 2;
    private static Provider sBouncyCastleProvider;
    private static Pattern stripPattern;

    static {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        sBouncyCastleProvider = bouncyCastleProvider;
        Security.insertProviderAt(bouncyCastleProvider, 1);
        stripPattern = Pattern.compile("^(META-INF/((.*)[.](SF|RSA|DSA|EC)|com/android/otacert))|(" + Pattern.quote("META-INF/MANIFEST.MF") + ")$");
    }

    public static void sign(JarMap jarMap, OutputStream outputStream) throws Exception {
        sign(SignAPK.class.getResourceAsStream("/keys/testkey.x509.pem"), SignAPK.class.getResourceAsStream("/keys/testkey.pk8"), jarMap, outputStream);
    }

    public static void sign(InputStream inputStream, InputStream inputStream2, JarMap jarMap, OutputStream outputStream) throws Exception {
        sign(CryptoUtils.readCertificate(inputStream), CryptoUtils.readPrivateKey(inputStream2), jarMap, outputStream);
    }

    public static void sign(InputStream inputStream, String str, String str2, String str3, JarMap jarMap, OutputStream outputStream) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, str.toCharArray());
        sign((X509Certificate) keyStore.getCertificate(str2), ((KeyStore.PrivateKeyEntry) keyStore.getEntry(str2, new KeyStore.PasswordProtection(str3.toCharArray()))).getPrivateKey(), jarMap, outputStream);
    }

    private static void sign(X509Certificate x509Certificate, PrivateKey privateKey, JarMap jarMap, OutputStream outputStream) throws Exception {
        File fileCreateTempFile = File.createTempFile("signAPK", null);
        File fileCreateTempFile2 = File.createTempFile("signAPK", null);
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileCreateTempFile));
            try {
                sign(x509Certificate, privateKey, jarMap, bufferedOutputStream, false);
                bufferedOutputStream.close();
                ZipAdjust.adjust(fileCreateTempFile, fileCreateTempFile2);
                JarMap jarMap2 = new JarMap(fileCreateTempFile2, false);
                try {
                    sign(x509Certificate, privateKey, jarMap2, outputStream, true);
                    jarMap2.close();
                    fileCreateTempFile.delete();
                    fileCreateTempFile2.delete();
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            jarMap2.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (Throwable th4) {
                try {
                    throw th4;
                } catch (Throwable th5) {
                    try {
                        bufferedOutputStream.close();
                    } catch (Throwable th6) {
                        th4.addSuppressed(th6);
                    }
                    throw th5;
                }
            }
        } catch (Throwable th7) {
            fileCreateTempFile.delete();
            fileCreateTempFile2.delete();
            throw th7;
        }
    }

    private static void sign(X509Certificate x509Certificate, PrivateKey privateKey, JarMap jarMap, OutputStream outputStream, boolean z) throws Exception {
        int digestAlgorithm = getDigestAlgorithm(x509Certificate);
        long time = x509Certificate.getNotBefore().getTime() + DateUtils.MILLIS_PER_HOUR;
        if (z) {
            signWholeFile(jarMap.getFile(), x509Certificate, privateKey, outputStream);
            return;
        }
        JarOutputStream jarOutputStream = new JarOutputStream(outputStream);
        jarOutputStream.setLevel(9);
        Manifest manifestAddDigestsToManifest = addDigestsToManifest(jarMap, digestAlgorithm);
        copyFiles(manifestAddDigestsToManifest, jarMap, jarOutputStream, time, 4);
        signFile(manifestAddDigestsToManifest, jarMap, x509Certificate, privateKey, jarOutputStream);
        jarOutputStream.close();
    }

    private static int getDigestAlgorithm(X509Certificate x509Certificate) {
        String upperCase = x509Certificate.getSigAlgName().toUpperCase(Locale.US);
        if ("SHA1WITHRSA".equals(upperCase) || "MD5WITHRSA".equals(upperCase)) {
            return 1;
        }
        if (upperCase.startsWith("SHA256WITH")) {
            return 2;
        }
        throw new IllegalArgumentException("unsupported signature algorithm \"" + upperCase + "\" in cert [" + x509Certificate.getSubjectDN());
    }

    private static String getSignatureAlgorithm(X509Certificate x509Certificate) {
        x509Certificate.getSigAlgName().toUpperCase(Locale.US);
        String upperCase = x509Certificate.getPublicKey().getAlgorithm().toUpperCase(Locale.US);
        if ("RSA".equalsIgnoreCase(upperCase)) {
            if (getDigestAlgorithm(x509Certificate) == 2) {
                return "SHA256withRSA";
            }
            return "SHA1withRSA";
        }
        if ("EC".equalsIgnoreCase(upperCase)) {
            return "SHA256withECDSA";
        }
        throw new IllegalArgumentException("unsupported key type: " + upperCase);
    }

    private static Manifest addDigestsToManifest(JarMap jarMap, int i) throws GeneralSecurityException, IOException {
        Pattern pattern;
        Manifest manifest = jarMap.getManifest();
        Manifest manifest2 = new Manifest();
        Attributes mainAttributes = manifest2.getMainAttributes();
        if (manifest != null) {
            mainAttributes.putAll(manifest.getMainAttributes());
        } else {
            mainAttributes.putValue("Manifest-Version", BuildConfig.VERSION_NAME);
            mainAttributes.putValue("Created-By", "1.0 (Android SignApk)");
        }
        MessageDigest messageDigest = (i & 1) != 0 ? MessageDigest.getInstance("SHA1") : null;
        MessageDigest messageDigest2 = (i & 2) != 0 ? MessageDigest.getInstance("SHA256") : null;
        byte[] bArr = new byte[4096];
        TreeMap treeMap = new TreeMap();
        Enumeration<JarEntry> enumerationEntries = jarMap.entries();
        while (enumerationEntries.hasMoreElements()) {
            JarEntry jarEntryNextElement = enumerationEntries.nextElement();
            treeMap.put(jarEntryNextElement.getName(), jarEntryNextElement);
        }
        for (JarEntry jarEntry : treeMap.values()) {
            String name2 = jarEntry.getName();
            if (!jarEntry.isDirectory() && ((pattern = stripPattern) == null || !pattern.matcher(name2).matches())) {
                InputStream inputStream = jarMap.getInputStream(jarEntry);
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 <= 0) {
                        break;
                    }
                    if (messageDigest != null) {
                        messageDigest.update(bArr, 0, i2);
                    }
                    if (messageDigest2 != null) {
                        messageDigest2.update(bArr, 0, i2);
                    }
                }
                Attributes attributes = manifest != null ? manifest.getAttributes(name2) : null;
                Attributes attributes2 = attributes != null ? new Attributes(attributes) : new Attributes();
                if (messageDigest != null) {
                    attributes2.putValue("SHA1-Digest", new String(Base64.encode(messageDigest.digest()), "ASCII"));
                }
                if (messageDigest2 != null) {
                    attributes2.putValue("SHA-256-Digest", new String(Base64.encode(messageDigest2.digest()), "ASCII"));
                }
                manifest2.getEntries().put(name2, attributes2);
            }
        }
        return manifest2;
    }

    private static class CountOutputStream extends FilterOutputStream {
        private int mCount;

        public CountOutputStream(OutputStream outputStream) {
            super(outputStream);
            this.mCount = 0;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            super.write(i);
            this.mCount++;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            super.write(bArr, i, i2);
            this.mCount += i2;
        }

        public int size() {
            return this.mCount;
        }
    }

    private static void writeSignatureFile(Manifest manifest, OutputStream outputStream, int i) throws GeneralSecurityException, IOException {
        String str;
        Manifest manifest2 = new Manifest();
        Attributes mainAttributes = manifest2.getMainAttributes();
        mainAttributes.putValue("Signature-Version", BuildConfig.VERSION_NAME);
        mainAttributes.putValue("Created-By", "1.0 (Android SignApk)");
        MessageDigest messageDigest = MessageDigest.getInstance(i == 2 ? "SHA256" : "SHA1");
        PrintStream printStream = new PrintStream((OutputStream) new DigestOutputStream(new ByteArrayOutputStream(), messageDigest), true, "UTF-8");
        manifest.write(printStream);
        printStream.flush();
        mainAttributes.putValue(i == 2 ? "SHA-256-Digest-Manifest" : "SHA1-Digest-Manifest", new String(Base64.encode(messageDigest.digest()), "ASCII"));
        for (Map.Entry<String, Attributes> entry : manifest.getEntries().entrySet()) {
            printStream.print("Name: " + entry.getKey() + "\r\n");
            for (Map.Entry<Object, Object> entry2 : entry.getValue().entrySet()) {
                printStream.print(entry2.getKey() + ": " + entry2.getValue() + "\r\n");
            }
            printStream.print("\r\n");
            printStream.flush();
            Attributes attributes = new Attributes();
            if (i != 2) {
                str = "SHA1-Digest-Manifest";
            } else {
                str = "SHA-256-Digest";
            }
            attributes.putValue(str, new String(Base64.encode(messageDigest.digest()), "ASCII"));
            manifest2.getEntries().put(entry.getKey(), attributes);
        }
        CountOutputStream countOutputStream = new CountOutputStream(outputStream);
        manifest2.write(countOutputStream);
        if (countOutputStream.size() % 1024 == 0) {
            countOutputStream.write(13);
            countOutputStream.write(10);
        }
    }

    private static void writeSignatureBlock(CMSTypedData cMSTypedData, X509Certificate x509Certificate, PrivateKey privateKey, OutputStream outputStream) throws CMSException, OperatorCreationException, IOException, CertificateEncodingException {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(x509Certificate);
        JcaCertStore jcaCertStore = new JcaCertStore(arrayList);
        CMSSignedDataGenerator cMSSignedDataGenerator = new CMSSignedDataGenerator();
        cMSSignedDataGenerator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider(sBouncyCastleProvider).build()).setDirectSignature(true).build(new JcaContentSignerBuilder(getSignatureAlgorithm(x509Certificate)).setProvider(sBouncyCastleProvider).build(privateKey), x509Certificate));
        cMSSignedDataGenerator.addCertificates(jcaCertStore);
        new DEROutputStream(outputStream).writeObject(new ASN1InputStream(cMSSignedDataGenerator.generate(cMSTypedData, false).getEncoded()).readObject());
    }

    private static void copyFiles(Manifest manifest, JarMap jarMap, JarOutputStream jarOutputStream, long j, int i) throws IOException {
        byte[] bArr = new byte[4096];
        ArrayList<String> arrayList = new ArrayList(manifest.getEntries().keySet());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        boolean z = true;
        long length = 0;
        while (it.hasNext()) {
            JarEntry jarEntry = jarMap.getJarEntry((String) it.next());
            if (jarEntry.getMethod() == 0) {
                JarEntry jarEntry2 = new JarEntry(jarEntry);
                jarEntry2.setTime(j);
                length += (long) (jarEntry2.getName().length() + 30);
                if (z) {
                    length += 4;
                    z = false;
                }
                if (i > 0) {
                    long j2 = length % ((long) i);
                    if (j2 != 0) {
                        int i2 = i - ((int) j2);
                        jarEntry2.setExtra(new byte[i2]);
                        length += (long) i2;
                    }
                }
                jarOutputStream.putNextEntry(jarEntry2);
                InputStream inputStream = jarMap.getInputStream(jarEntry);
                while (true) {
                    int i3 = inputStream.read(bArr);
                    if (i3 <= 0) {
                        break;
                    }
                    jarOutputStream.write(bArr, 0, i3);
                    length += (long) i3;
                }
                jarOutputStream.flush();
            }
        }
        for (String str : arrayList) {
            JarEntry jarEntry3 = jarMap.getJarEntry(str);
            if (jarEntry3.getMethod() != 0) {
                JarEntry jarEntry4 = new JarEntry(str);
                jarEntry4.setTime(j);
                jarOutputStream.putNextEntry(jarEntry4);
                InputStream inputStream2 = jarMap.getInputStream(jarEntry3);
                while (true) {
                    int i4 = inputStream2.read(bArr);
                    if (i4 <= 0) {
                        break;
                    } else {
                        jarOutputStream.write(bArr, 0, i4);
                    }
                }
                jarOutputStream.flush();
            }
        }
    }

    private static class CMSProcessableFile implements CMSTypedData {
        private RandomAccessFile file;
        private ASN1ObjectIdentifier type = new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId());

        CMSProcessableFile(File file) throws FileNotFoundException {
            this.file = new RandomAccessFile(file, "r");
        }

        @Override // org.bouncycastle.cms.CMSTypedData
        public ASN1ObjectIdentifier getContentType() {
            return this.type;
        }

        @Override // org.bouncycastle.cms.CMSProcessable
        public void write(OutputStream outputStream) throws CMSException, IOException {
            this.file.seek(0L);
            byte[] bArr = new byte[4096];
            int length = ((int) this.file.length()) - 2;
            while (true) {
                int i = this.file.read(bArr, 0, length < 4096 ? length : 4096);
                if (i <= 0) {
                    return;
                }
                outputStream.write(bArr, 0, i);
                length -= i;
            }
        }

        @Override // org.bouncycastle.cms.CMSProcessable
        public Object getContent() {
            return this.file;
        }

        byte[] getTail() throws IOException {
            byte[] bArr = new byte[22];
            RandomAccessFile randomAccessFile = this.file;
            randomAccessFile.seek(randomAccessFile.length() - 22);
            this.file.readFully(bArr);
            return bArr;
        }
    }

    private static void signWholeFile(File file, X509Certificate x509Certificate, PrivateKey privateKey, OutputStream outputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = "signed by SignApk".getBytes("UTF-8");
        byteArrayOutputStream.write(bytes);
        byteArrayOutputStream.write(0);
        CMSProcessableFile cMSProcessableFile = new CMSProcessableFile(file);
        writeSignatureBlock(cMSProcessableFile, x509Certificate, privateKey, byteArrayOutputStream);
        byte[] tail = cMSProcessableFile.getTail();
        if (tail[tail.length - 22] != 80 || tail[tail.length - 21] != 75 || tail[tail.length - 20] != 5 || tail[tail.length - 19] != 6) {
            throw new IllegalArgumentException("zip data already has an archive comment");
        }
        int size = byteArrayOutputStream.size() + 6;
        if (size > 65535) {
            throw new IllegalArgumentException("signature is too big for ZIP file comment");
        }
        int length = (size - bytes.length) - 1;
        byteArrayOutputStream.write(length & 255);
        byteArrayOutputStream.write((length >> 8) & 255);
        byteArrayOutputStream.write(255);
        byteArrayOutputStream.write(255);
        int i = size & 255;
        byteArrayOutputStream.write(i);
        int i2 = (size >> 8) & 255;
        byteArrayOutputStream.write(i2);
        byteArrayOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        for (int i3 = 0; i3 < byteArray.length - 3; i3++) {
            if (byteArray[i3] == 80 && byteArray[i3 + 1] == 75 && byteArray[i3 + 2] == 5 && byteArray[i3 + 3] == 6) {
                throw new IllegalArgumentException("found spurious EOCD header at " + i3);
            }
        }
        cMSProcessableFile.write(outputStream);
        outputStream.write(i);
        outputStream.write(i2);
        byteArrayOutputStream.writeTo(outputStream);
        outputStream.close();
    }

    private static void signFile(Manifest manifest, JarMap jarMap, X509Certificate x509Certificate, PrivateKey privateKey, JarOutputStream jarOutputStream) throws Exception {
        long time = x509Certificate.getNotBefore().getTime() + DateUtils.MILLIS_PER_HOUR;
        JarEntry jarEntry = new JarEntry("META-INF/MANIFEST.MF");
        jarEntry.setTime(time);
        jarOutputStream.putNextEntry(jarEntry);
        manifest.write(jarOutputStream);
        JarEntry jarEntry2 = new JarEntry(CERT_SF_NAME);
        jarEntry2.setTime(time);
        jarOutputStream.putNextEntry(jarEntry2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        writeSignatureFile(manifest, byteArrayOutputStream, getDigestAlgorithm(x509Certificate));
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        jarOutputStream.write(byteArray);
        JarEntry jarEntry3 = new JarEntry(String.format(CERT_SIG_NAME, x509Certificate.getPublicKey().getAlgorithm()));
        jarEntry3.setTime(time);
        jarOutputStream.putNextEntry(jarEntry3);
        writeSignatureBlock(new CMSProcessableByteArray(byteArray), x509Certificate, privateKey, jarOutputStream);
    }
}
