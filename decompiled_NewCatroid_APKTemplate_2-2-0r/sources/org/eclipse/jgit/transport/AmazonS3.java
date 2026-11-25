package org.eclipse.jgit.transport;

import com.badlogic.gdx.Net;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.DigestOutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;
import org.apache.commons.lang3.time.TimeZones;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.Base64;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/* JADX INFO: loaded from: classes13.dex */
public class AmazonS3 {
    private static final String AWS_API_V2 = "2";
    private static final String AWS_API_V4 = "4";
    private static final String AWS_S3_SERVICE_NAME = "s3";
    private static final String HMAC = "HmacSHA1";
    private static final Set<String> SIGNED_HEADERS;
    private static final String X_AMZ_ACL = "x-amz-acl";
    private static final String X_AMZ_META = "x-amz-meta-";
    private final String acl;
    private final String awsApiSignatureVersion;
    private final String domain;
    private final WalkEncryption encryption;
    final int maxAttempts;
    private final ProxySelector proxySelector;
    private final String publicKey;
    private final String region;
    private final char[] secretKey;
    private final SecretKeySpec secretKeySpec;
    private final File tmpDir;

    interface Keys {
        public static final String ACCESS_KEY = "accesskey";
        public static final String ACL = "acl";
        public static final String AWS_API_SIGNATURE_VERSION = "aws.api.signature.version";
        public static final String CRYPTO_ALG = "crypto.algorithm";
        public static final String CRYPTO_VER = "crypto.version";
        public static final String DOMAIN = "domain";
        public static final String HTTP_RETRY = "httpclient.retry-max";
        public static final String PASSWORD = "password";
        public static final String REGION = "region";
        public static final String SECRET_KEY = "secretkey";
        public static final String TMP_DIR = "tmpdir";
    }

    static {
        HashSet hashSet = new HashSet();
        SIGNED_HEADERS = hashSet;
        hashSet.add("content-type");
        hashSet.add("content-md5");
        hashSet.add(SchemaSymbols.ATTVAL_DATE);
    }

    private static boolean isSignedHeader(String str) {
        String lowerCase = StringUtils.toLowerCase(str);
        return SIGNED_HEADERS.contains(lowerCase) || lowerCase.startsWith("x-amz-");
    }

    private static String toCleanString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(str.replaceAll("\n", "").trim());
        }
        return sb.toString();
    }

    private static String remove(Map<String, String> map, String str) {
        String strRemove = map.remove(str);
        return strRemove != null ? strRemove : "";
    }

    private static String httpNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
        return String.valueOf(simpleDateFormat.format(new Date())) + " GMT";
    }

    private static MessageDigest newMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(JGitText.get().JRELacksMD5Implementation, e);
        }
    }

    public AmazonS3(Properties properties) {
        String property = properties.getProperty(Keys.AWS_API_SIGNATURE_VERSION, "2");
        this.awsApiSignatureVersion = property;
        File file = null;
        if (property.equals(AWS_API_V4)) {
            String property2 = properties.getProperty("region");
            this.region = property2;
            if (property2 == null) {
                throw new IllegalArgumentException(JGitText.get().missingAwsRegion);
            }
        } else if (property.equals("2")) {
            this.region = null;
        } else {
            throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidAwsApiSignatureVersion, property));
        }
        this.domain = properties.getProperty(Keys.DOMAIN, "s3.amazonaws.com");
        String property3 = properties.getProperty(Keys.ACCESS_KEY);
        this.publicKey = property3;
        if (property3 == null) {
            throw new IllegalArgumentException(JGitText.get().missingAccesskey);
        }
        String property4 = properties.getProperty(Keys.SECRET_KEY);
        if (property4 == null) {
            throw new IllegalArgumentException(JGitText.get().missingSecretkey);
        }
        this.secretKeySpec = new SecretKeySpec(Constants.encodeASCII(property4), HMAC);
        this.secretKey = property4.toCharArray();
        String property5 = properties.getProperty(Keys.ACL, "PRIVATE");
        if (StringUtils.equalsIgnoreCase("PRIVATE", property5)) {
            this.acl = "private";
        } else if (StringUtils.equalsIgnoreCase("PUBLIC", property5) || StringUtils.equalsIgnoreCase("PUBLIC-READ", property5) || StringUtils.equalsIgnoreCase("PUBLIC_READ", property5)) {
            this.acl = "public-read";
        } else {
            throw new IllegalArgumentException("Invalid acl: " + property5);
        }
        try {
            this.encryption = WalkEncryption.instance(properties);
            this.maxAttempts = Integer.parseInt(properties.getProperty(Keys.HTTP_RETRY, "3"));
            this.proxySelector = ProxySelector.getDefault();
            String property6 = properties.getProperty(Keys.TMP_DIR);
            if (property6 != null && property6.length() > 0) {
                file = new File(property6);
            }
            this.tmpDir = file;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(JGitText.get().invalidEncryption, e);
        }
    }

    public URLConnection get(String str, String str2) throws IOException {
        for (int i = 0; i < this.maxAttempts; i++) {
            HttpURLConnection httpURLConnectionOpen = open("GET", str, str2);
            authorize(httpURLConnectionOpen, Collections.emptyMap(), 0L, null);
            int iResponse = HttpSupport.response(httpURLConnectionOpen);
            if (iResponse == 200) {
                this.encryption.validate(httpURLConnectionOpen, X_AMZ_META);
                return httpURLConnectionOpen;
            }
            if (iResponse == 404) {
                throw new FileNotFoundException(str2);
            }
            if (iResponse != 500) {
                throw error(JGitText.get().s3ActionReading, str2, httpURLConnectionOpen);
            }
        }
        throw maxAttempts(JGitText.get().s3ActionReading, str2);
    }

    public InputStream decrypt(URLConnection uRLConnection) throws IOException {
        return this.encryption.decrypt(uRLConnection.getInputStream());
    }

    public List<String> list(String str, String str2) throws IOException {
        if (str2.length() > 0 && !str2.endsWith("/")) {
            str2 = String.valueOf(str2) + "/";
        }
        ListParser listParser = new ListParser(str, str2);
        do {
            listParser.list();
        } while (listParser.truncated);
        return (List) listParser.entries.stream().sorted(Comparator.comparingLong(new ToLongFunction() { // from class: org.eclipse.jgit.transport.AmazonS3$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((AmazonS3.KeyInfo) obj).getLastModifiedSecs();
            }
        }).reversed()).map(new Function() { // from class: org.eclipse.jgit.transport.AmazonS3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AmazonS3.KeyInfo) obj).getName();
            }
        }).collect(Collectors.toList());
    }

    public void delete(String str, String str2) throws IOException {
        for (int i = 0; i < this.maxAttempts; i++) {
            HttpURLConnection httpURLConnectionOpen = open(Net.HttpMethods.DELETE, str, str2);
            authorize(httpURLConnectionOpen, Collections.emptyMap(), 0L, null);
            int iResponse = HttpSupport.response(httpURLConnectionOpen);
            if (iResponse == 204) {
                return;
            }
            if (iResponse != 500) {
                throw error(JGitText.get().s3ActionDeletion, str2, httpURLConnectionOpen);
            }
        }
        throw maxAttempts(JGitText.get().s3ActionDeletion, str2);
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x00a5 */
    /* JADX WARN: Code restructure failed: missing block: B:63:?, code lost:
    
        throw r13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void put(java.lang.String r13, java.lang.String r14, byte[] r15) throws java.io.IOException {
        /*
            r12 = this;
            org.eclipse.jgit.transport.WalkEncryption r0 = r12.encryption
            org.eclipse.jgit.transport.WalkEncryption r1 = org.eclipse.jgit.transport.WalkEncryption.NONE
            r2 = 0
            if (r0 == r1) goto L25
            java.io.OutputStream r13 = r12.beginPut(r13, r14, r2, r2)     // Catch: java.lang.Throwable -> L1b
            r13.write(r15)     // Catch: java.lang.Throwable -> L14
            if (r13 == 0) goto L13
            r13.close()     // Catch: java.lang.Throwable -> L1b
        L13:
            return
        L14:
            r2 = move-exception
            if (r13 == 0) goto L1a
            r13.close()     // Catch: java.lang.Throwable -> L1b
        L1a:
            throw r2     // Catch: java.lang.Throwable -> L1b
        L1b:
            r13 = move-exception
            if (r2 == 0) goto L24
            if (r2 == r13) goto L23
            r2.addSuppressed(r13)
        L23:
            r13 = r2
        L24:
            throw r13
        L25:
            java.security.MessageDigest r0 = newMD5()
            byte[] r0 = r0.digest(r15)
            java.lang.String r0 = org.eclipse.jgit.util.Base64.encodeBytes(r0)
            java.lang.String r1 = r12.awsApiSignatureVersion
            java.lang.String r3 = "4"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L40
            java.lang.String r1 = org.eclipse.jgit.transport.AwsRequestSignerV4.calculateBodyHash(r15)
            goto L41
        L40:
            r1 = r2
        L41:
            int r3 = r15.length
            java.lang.String r9 = java.lang.String.valueOf(r3)
            r3 = 0
            r10 = r3
        L48:
            int r3 = r12.maxAttempts
            if (r10 >= r3) goto Laf
            java.lang.String r3 = "PUT"
            java.net.HttpURLConnection r11 = r12.open(r3, r13, r14)
            java.lang.String r3 = "Content-Length"
            r11.setRequestProperty(r3, r9)
            java.lang.String r3 = "Content-MD5"
            r11.setRequestProperty(r3, r0)
            java.lang.String r3 = "x-amz-acl"
            java.lang.String r4 = r12.acl
            r11.setRequestProperty(r3, r4)
            java.util.Map r5 = java.util.Collections.emptyMap()
            int r3 = r15.length
            long r6 = (long) r3
            r3 = r12
            r4 = r11
            r8 = r1
            r3.authorize(r4, r5, r6, r8)
            r3 = 1
            r11.setDoOutput(r3)
            int r3 = r15.length
            r11.setFixedLengthStreamingMode(r3)
            java.io.OutputStream r3 = r11.getOutputStream()     // Catch: java.lang.Throwable -> La5
            r3.write(r15)     // Catch: java.lang.Throwable -> L9e
            if (r3 == 0) goto L83
            r3.close()     // Catch: java.lang.Throwable -> La5
        L83:
            int r3 = org.eclipse.jgit.util.HttpSupport.response(r11)
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 == r4) goto L9d
            r4 = 500(0x1f4, float:7.0E-43)
            if (r3 != r4) goto L92
            int r10 = r10 + 1
            goto L48
        L92:
            org.eclipse.jgit.internal.JGitText r13 = org.eclipse.jgit.internal.JGitText.get()
            java.lang.String r13 = r13.s3ActionWriting
            java.io.IOException r13 = r12.error(r13, r14, r11)
            throw r13
        L9d:
            return
        L9e:
            r2 = move-exception
            if (r3 == 0) goto La4
            r3.close()     // Catch: java.lang.Throwable -> La5
        La4:
            throw r2     // Catch: java.lang.Throwable -> La5
        La5:
            r13 = move-exception
            if (r2 == 0) goto Lae
            if (r2 == r13) goto Lad
            r2.addSuppressed(r13)
        Lad:
            r13 = r2
        Lae:
            throw r13
        Laf:
            org.eclipse.jgit.internal.JGitText r13 = org.eclipse.jgit.internal.JGitText.get()
            java.lang.String r13 = r13.s3ActionWriting
            java.io.IOException r13 = r12.maxAttempts(r13, r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jgit.transport.AmazonS3.put(java.lang.String, java.lang.String, byte[]):void");
    }

    public OutputStream beginPut(final String str, final String str2, final ProgressMonitor progressMonitor, final String str3) throws IOException {
        final MessageDigest messageDigestNewMD5 = newMD5();
        return this.encryption.encrypt(new DigestOutputStream(new TemporaryBuffer.LocalFile(this.tmpDir) { // from class: org.eclipse.jgit.transport.AmazonS3.1
            @Override // org.eclipse.jgit.util.TemporaryBuffer, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                super.close();
                try {
                    AmazonS3.this.putImpl(str, str2, messageDigestNewMD5.digest(), this, progressMonitor, str3);
                } finally {
                    destroy();
                }
            }
        }, messageDigestNewMD5));
    }

    void putImpl(String str, String str2, byte[] bArr, TemporaryBuffer temporaryBuffer, ProgressMonitor progressMonitor, String str3) throws Throwable {
        Throwable th;
        ProgressMonitor progressMonitor2 = progressMonitor == null ? NullProgressMonitor.INSTANCE : progressMonitor;
        String str4 = str3 == null ? MessageFormat.format(JGitText.get().progressMonUploading, str2) : str3;
        String strEncodeBytes = Base64.encodeBytes(bArr);
        String strCalculateBodyHash = this.awsApiSignatureVersion.equals(AWS_API_V4) ? AwsRequestSignerV4.calculateBodyHash(temporaryBuffer.toByteArray()) : null;
        long length = temporaryBuffer.length();
        for (int i = 0; i < this.maxAttempts; i++) {
            HttpURLConnection httpURLConnectionOpen = open("PUT", str, str2);
            httpURLConnectionOpen.setFixedLengthStreamingMode(length);
            httpURLConnectionOpen.setRequestProperty("Content-MD5", strEncodeBytes);
            httpURLConnectionOpen.setRequestProperty(X_AMZ_ACL, this.acl);
            this.encryption.request(httpURLConnectionOpen, X_AMZ_META);
            authorize(httpURLConnectionOpen, Collections.emptyMap(), length, strCalculateBodyHash);
            httpURLConnectionOpen.setDoOutput(true);
            progressMonitor2.beginTask(str4, (int) (length / 1024));
            try {
                OutputStream outputStream = httpURLConnectionOpen.getOutputStream();
                try {
                    temporaryBuffer.writeTo(outputStream, progressMonitor2);
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    progressMonitor2.endTask();
                    int iResponse = HttpSupport.response(httpURLConnectionOpen);
                    if (iResponse == 200) {
                        return;
                    }
                    if (iResponse != 500) {
                        throw error(JGitText.get().s3ActionWriting, str2, httpURLConnectionOpen);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (outputStream == null) {
                        throw th;
                    }
                    try {
                        outputStream.close();
                        throw th;
                    } catch (Throwable th3) {
                        th = th3;
                        if (th == null) {
                            throw th;
                        }
                        if (th == th) {
                            throw th;
                        }
                        try {
                            th.addSuppressed(th);
                            throw th;
                        } catch (Throwable th4) {
                            progressMonitor2.endTask();
                            throw th4;
                        }
                        progressMonitor2.endTask();
                        throw th4;
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                th = null;
            }
        }
        throw maxAttempts(JGitText.get().s3ActionWriting, str2);
    }

    IOException error(String str, String str2, HttpURLConnection httpURLConnection) throws IOException {
        IOException iOException = new IOException(MessageFormat.format(JGitText.get().amazonS3ActionFailed, str, str2, Integer.valueOf(HttpSupport.response(httpURLConnection)), httpURLConnection.getResponseMessage()));
        if (httpURLConnection.getErrorStream() == null) {
            return iOException;
        }
        Throwable th = null;
        try {
            InputStream errorStream = httpURLConnection.getErrorStream();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[2048];
                while (true) {
                    int i = errorStream.read(bArr);
                    if (i < 0) {
                        break;
                    }
                    if (i > 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (byteArray.length > 0) {
                    iOException.initCause(new IOException("\n" + new String(byteArray, StandardCharsets.UTF_8)));
                }
                return iOException;
            } finally {
                if (errorStream != null) {
                    errorStream.close();
                }
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null == th2) {
                throw null;
            }
            th.addSuppressed(th2);
            throw null;
        }
    }

    IOException maxAttempts(String str, String str2) {
        return new IOException(MessageFormat.format(JGitText.get().amazonS3ActionFailedGivingUp, str, str2, Integer.valueOf(this.maxAttempts)));
    }

    private HttpURLConnection open(String str, String str2, String str3) throws IOException {
        return open(str, str2, str3, Collections.emptyMap());
    }

    HttpURLConnection open(String str, String str2, String str3, Map<String, String> map) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(str2);
        sb.append('.');
        sb.append(this.domain);
        sb.append('/');
        if (str3.length() > 0) {
            if (this.awsApiSignatureVersion.equals("2")) {
                HttpSupport.encode(sb, str3);
            } else if (this.awsApiSignatureVersion.equals(AWS_API_V4)) {
                sb.append(str3);
            }
        }
        if (!map.isEmpty()) {
            sb.append(MangleConstant.Q_MARK);
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> next = it.next();
                sb.append(next.getKey());
                sb.append('=');
                HttpSupport.encode(sb, next.getValue());
                if (it.hasNext()) {
                    sb.append(Typography.amp);
                }
            }
        }
        URL url = new URL(sb.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(HttpSupport.proxyFor(this.proxySelector, url));
        httpURLConnection.setRequestMethod(str);
        httpURLConnection.setRequestProperty("User-Agent", "jgit/1.0");
        httpURLConnection.setRequestProperty("Date", httpNow());
        return httpURLConnection;
    }

    void authorize(HttpURLConnection httpURLConnection, Map<String, String> map, long j, String str) throws IOException {
        if (this.awsApiSignatureVersion.equals("2")) {
            authorizeV2(httpURLConnection);
        } else if (this.awsApiSignatureVersion.equals(AWS_API_V4)) {
            AwsRequestSignerV4.sign(httpURLConnection, map, j, str, AWS_S3_SERVICE_NAME, this.region, this.publicKey, this.secretKey);
        }
    }

    void authorizeV2(HttpURLConnection httpURLConnection) throws IOException {
        Map<String, List<String>> requestProperties = httpURLConnection.getRequestProperties();
        TreeMap treeMap = new TreeMap();
        for (Map.Entry<String, List<String>> entry : requestProperties.entrySet()) {
            String key = entry.getKey();
            if (isSignedHeader(key)) {
                treeMap.put(StringUtils.toLowerCase(key), toCleanString(entry.getValue()));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(httpURLConnection.getRequestMethod());
        sb.append('\n');
        sb.append(remove(treeMap, "content-md5"));
        sb.append('\n');
        sb.append(remove(treeMap, "content-type"));
        sb.append('\n');
        sb.append(remove(treeMap, SchemaSymbols.ATTVAL_DATE));
        sb.append('\n');
        for (Map.Entry entry2 : treeMap.entrySet()) {
            sb.append((String) entry2.getKey());
            sb.append(':');
            sb.append((String) entry2.getValue());
            sb.append('\n');
        }
        String host = httpURLConnection.getURL().getHost();
        sb.append('/');
        sb.append(host.substring(0, (host.length() - this.domain.length()) - 1));
        sb.append(httpURLConnection.getURL().getPath());
        try {
            Mac mac = Mac.getInstance(HMAC);
            mac.init(this.secretKeySpec);
            httpURLConnection.setRequestProperty("Authorization", "AWS " + this.publicKey + ":" + Base64.encodeBytes(mac.doFinal(sb.toString().getBytes(StandardCharsets.UTF_8))));
        } catch (InvalidKeyException e) {
            throw new IOException(MessageFormat.format(JGitText.get().invalidKey, e.getMessage()));
        } catch (NoSuchAlgorithmException e2) {
            throw new IOException(MessageFormat.format(JGitText.get().noHMACsupport, HMAC, e2.getMessage()));
        }
    }

    static Properties properties(File file) throws IOException {
        Properties properties = new Properties();
        Throwable th = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                properties.load(fileInputStream);
                return properties;
            } finally {
                fileInputStream.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class KeyInfo {
        private final long lastModifiedSecs;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f661name;

        public KeyInfo(String str, long j) {
            this.f661name = str;
            this.lastModifiedSecs = j;
        }

        public String getName() {
            return this.f661name;
        }

        public long getLastModifiedSecs() {
            return this.lastModifiedSecs;
        }
    }

    private final class ListParser extends DefaultHandler {
        private final String bucket;
        private StringBuilder data;
        final List<KeyInfo> entries = new ArrayList();
        private Instant keyLastModified;
        private String keyName;
        private final String prefix;
        boolean truncated;

        ListParser(String str, String str2) {
            this.bucket = str;
            this.prefix = str2;
        }

        void list() throws IOException {
            TreeMap treeMap = new TreeMap();
            if (this.prefix.length() > 0) {
                treeMap.put("prefix", this.prefix);
            }
            if (!this.entries.isEmpty()) {
                StringBuilder sb = new StringBuilder(String.valueOf(this.prefix));
                List<KeyInfo> list = this.entries;
                treeMap.put("marker", sb.append(list.get(list.size() - 1).getName()).toString());
            }
            for (int i = 0; i < AmazonS3.this.maxAttempts; i++) {
                HttpURLConnection httpURLConnectionOpen = AmazonS3.this.open("GET", this.bucket, "", treeMap);
                AmazonS3.this.authorize(httpURLConnectionOpen, treeMap, 0L, null);
                int iResponse = HttpSupport.response(httpURLConnectionOpen);
                if (iResponse == 200) {
                    this.truncated = false;
                    Throwable th = null;
                    this.data = null;
                    this.keyName = null;
                    this.keyLastModified = null;
                    try {
                        XMLReader xMLReaderCreateXMLReader = XMLReaderFactory.createXMLReader();
                        xMLReaderCreateXMLReader.setContentHandler(this);
                        try {
                            InputStream inputStream = httpURLConnectionOpen.getInputStream();
                            try {
                                xMLReaderCreateXMLReader.parse(new InputSource(inputStream));
                                if (inputStream != null) {
                                    return;
                                } else {
                                    return;
                                }
                            } finally {
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            }
                        } catch (Throwable th2) {
                            if (0 == 0) {
                                throw th2;
                            }
                            if (null == th2) {
                                throw null;
                            }
                            try {
                                th.addSuppressed(th2);
                                throw null;
                            } catch (SAXException e) {
                                throw new IOException(MessageFormat.format(JGitText.get().errorListing, this.prefix), e);
                            }
                            throw new IOException(MessageFormat.format(JGitText.get().errorListing, this.prefix), e);
                        }
                    } catch (SAXException e2) {
                        throw new IOException(JGitText.get().noXMLParserAvailable, e2);
                    }
                }
                if (iResponse != 500) {
                    throw AmazonS3.this.error("Listing", this.prefix, httpURLConnectionOpen);
                }
            }
            throw AmazonS3.this.maxAttempts("Listing", this.prefix);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if ("Key".equals(str2) || "IsTruncated".equals(str2) || "LastModified".equals(str2)) {
                this.data = new StringBuilder();
            }
            if ("Contents".equals(str2)) {
                this.keyName = null;
                this.keyLastModified = null;
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
            StringBuilder sb = this.data;
            if (sb != null) {
                sb.append(cArr, i, i2);
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) throws SAXException {
            StringBuilder sb = this.data;
            if (sb != null) {
                sb.append(cArr, i, i2);
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            if ("Key".equals(str2)) {
                this.keyName = this.data.toString().substring(this.prefix.length());
            } else if ("IsTruncated".equals(str2)) {
                this.truncated = StringUtils.equalsIgnoreCase("true", this.data.toString());
            } else if ("LastModified".equals(str2)) {
                this.keyLastModified = Instant.parse(this.data.toString());
            } else if ("Contents".equals(str2)) {
                this.entries.add(new KeyInfo(this.keyName, this.keyLastModified.getEpochSecond()));
            }
            this.data = null;
        }
    }
}
