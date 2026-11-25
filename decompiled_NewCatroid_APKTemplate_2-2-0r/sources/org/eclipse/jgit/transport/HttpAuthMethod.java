package org.eclipse.jgit.transport;

import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.Base64;
import org.eclipse.jgit.util.GSSManagerFactory;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
abstract class HttpAuthMethod {
    static final String EMPTY_STRING = "";
    static final String SCHEMA_NAME_SEPARATOR = " ";
    protected final Type type;

    abstract void authorize(String str, String str2);

    abstract void configureRequest(HttpConnection httpConnection) throws IOException;

    public enum Type {
        NONE { // from class: org.eclipse.jgit.transport.HttpAuthMethod.Type.1
            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public HttpAuthMethod method(String str) {
                return None.INSTANCE;
            }

            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public String getSchemeName() {
                return "None";
            }
        },
        BASIC { // from class: org.eclipse.jgit.transport.HttpAuthMethod.Type.2
            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public HttpAuthMethod method(String str) {
                return new Basic();
            }

            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public String getSchemeName() {
                return "Basic";
            }
        },
        DIGEST { // from class: org.eclipse.jgit.transport.HttpAuthMethod.Type.3
            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public HttpAuthMethod method(String str) {
                return new Digest(str);
            }

            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public String getSchemeName() {
                return "Digest";
            }
        },
        NEGOTIATE { // from class: org.eclipse.jgit.transport.HttpAuthMethod.Type.4
            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public HttpAuthMethod method(String str) {
                return new Negotiate(str);
            }

            @Override // org.eclipse.jgit.transport.HttpAuthMethod.Type
            public String getSchemeName() {
                return "Negotiate";
            }
        };

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Type[] valuesCustom() {
            Type[] typeArrValuesCustom = values();
            int length = typeArrValuesCustom.length;
            Type[] typeArr = new Type[length];
            System.arraycopy(typeArrValuesCustom, 0, typeArr, 0, length);
            return typeArr;
        }

        public abstract String getSchemeName();

        public abstract HttpAuthMethod method(String str);

        /* synthetic */ Type(Type type) {
            this();
        }
    }

    static HttpAuthMethod scanResponse(HttpConnection httpConnection, Collection<Type> collection) {
        Map<String, List<String>> headerFields = httpConnection.getHeaderFields();
        HttpAuthMethod httpAuthMethodMethod = Type.NONE.method("");
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            if ("WWW-Authenticate".equalsIgnoreCase(entry.getKey())) {
                if (entry.getValue() == null) {
                    break;
                }
                for (String str : entry.getValue()) {
                    if (str != null && str.length() != 0) {
                        String[] strArrSplit = str.split(" ", 2);
                        try {
                            Type typeValueOf = Type.valueOf(strArrSplit[0].toUpperCase(Locale.ROOT));
                            if (collection == null || !collection.contains(typeValueOf)) {
                                if (httpAuthMethodMethod.getType().compareTo(typeValueOf) < 0) {
                                    httpAuthMethodMethod = typeValueOf.method(strArrSplit.length == 1 ? "" : strArrSplit[1]);
                                }
                            }
                        } catch (IllegalArgumentException unused) {
                        }
                    }
                }
                break;
            }
        }
        return httpAuthMethodMethod;
    }

    protected HttpAuthMethod(Type type) {
        this.type = type;
    }

    boolean authorize(URIish uRIish, CredentialsProvider credentialsProvider) {
        String pass;
        String value;
        if (credentialsProvider != null) {
            CredentialItem.Username username = new CredentialItem.Username();
            CredentialItem.Password password = new CredentialItem.Password();
            if (!credentialsProvider.supports(username, password) || !credentialsProvider.get(uRIish, username, password)) {
                return false;
            }
            value = username.getValue();
            pass = password.getValue() == null ? null : new String(password.getValue());
            password.clear();
        } else {
            String user = uRIish.getUser();
            pass = uRIish.getPass();
            value = user;
        }
        if (value == null) {
            return false;
        }
        authorize(value, pass);
        return true;
    }

    public Type getType() {
        return this.type;
    }

    private static class None extends HttpAuthMethod {
        static final None INSTANCE = new None();

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void authorize(String str, String str2) {
        }

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void configureRequest(HttpConnection httpConnection) throws IOException {
        }

        public None() {
            super(Type.NONE);
        }
    }

    private static class Basic extends HttpAuthMethod {
        private String pass;
        private String user;

        public Basic() {
            super(Type.BASIC);
        }

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void authorize(String str, String str2) {
            this.user = str;
            this.pass = str2;
        }

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void configureRequest(HttpConnection httpConnection) throws IOException {
            httpConnection.setRequestProperty("Authorization", String.valueOf(this.type.getSchemeName()) + " " + Base64.encodeBytes((String.valueOf(this.user) + ":" + this.pass).getBytes(StandardCharsets.UTF_8)));
        }
    }

    private static class Digest extends HttpAuthMethod {
        private final Map<String, String> params;
        private String pass;
        private int requestCount;
        private String user;
        private static final SecureRandom PRNG = new SecureRandom();
        private static final char[] LHEX = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, 'e', 'f'};

        Digest(String str) {
            super(Type.DIGEST);
            Map<String, String> map = parse(str);
            this.params = map;
            if (MqttAuthHandler.NAME.equals(map.get("qop"))) {
                byte[] bArr = new byte[8];
                PRNG.nextBytes(bArr);
                map.put("cnonce", Base64.encodeBytes(bArr));
            }
        }

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void authorize(String str, String str2) {
            this.user = str;
            this.pass = str2;
        }

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void configureRequest(HttpConnection httpConnection) throws IOException {
            String strKD;
            String str;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            String str2 = this.params.get("realm");
            String str3 = this.params.get("nonce");
            String str4 = this.params.get("cnonce");
            String strUri = uri(httpConnection.getURL());
            String str5 = this.params.get("qop");
            String requestMethod = httpConnection.getRequestMethod();
            String str6 = String.valueOf(this.user) + ":" + str2 + ":" + this.pass;
            String str7 = String.valueOf(requestMethod) + ":" + strUri;
            linkedHashMap.put("username", this.user);
            linkedHashMap.put("realm", str2);
            linkedHashMap.put("nonce", str3);
            linkedHashMap.put("uri", strUri);
            if (MqttAuthHandler.NAME.equals(str5)) {
                int i = this.requestCount + 1;
                this.requestCount = i;
                str = String.format("%08x", Integer.valueOf(i));
                strKD = KD(H(str6), String.valueOf(str3) + ":" + str + ":" + str4 + ":" + str5 + ":" + H(str7));
            } else {
                strKD = KD(H(str6), String.valueOf(str3) + ":" + H(str7));
                str = null;
            }
            linkedHashMap.put("response", strKD);
            if (this.params.containsKey(ConfigConstants.CONFIG_KEY_ALGORITHM)) {
                linkedHashMap.put(ConfigConstants.CONFIG_KEY_ALGORITHM, "MD5");
            }
            if (str4 != null && str5 != null) {
                linkedHashMap.put("cnonce", str4);
            }
            if (this.params.containsKey("opaque")) {
                linkedHashMap.put("opaque", this.params.get("opaque"));
            }
            if (str5 != null) {
                linkedHashMap.put("qop", str5);
            }
            if (str != null) {
                linkedHashMap.put("nc", str);
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append((String) entry.getKey());
                sb.append('=');
                sb.append('\"');
                sb.append((String) entry.getValue());
                sb.append('\"');
            }
            httpConnection.setRequestProperty("Authorization", String.valueOf(this.type.getSchemeName()) + " " + ((Object) sb));
        }

        private static String uri(URL url) {
            StringBuilder sb = new StringBuilder();
            sb.append(url.getProtocol());
            sb.append("://");
            sb.append(url.getHost());
            if (url.getPort() > 0 && ((url.getPort() != 80 || !"http".equals(url.getProtocol())) && (url.getPort() != 443 || !"https".equals(url.getProtocol())))) {
                sb.append(':').append(url.getPort());
            }
            sb.append(url.getPath());
            if (url.getQuery() != null) {
                sb.append(MangleConstant.Q_MARK).append(url.getQuery());
            }
            return sb.toString();
        }

        private static String H(String str) {
            MessageDigest messageDigestNewMD5 = newMD5();
            messageDigestNewMD5.update(str.getBytes(StandardCharsets.UTF_8));
            return LHEX(messageDigestNewMD5.digest());
        }

        private static String KD(String str, String str2) {
            MessageDigest messageDigestNewMD5 = newMD5();
            messageDigestNewMD5.update(str.getBytes(StandardCharsets.UTF_8));
            messageDigestNewMD5.update((byte) 58);
            messageDigestNewMD5.update(str2.getBytes(StandardCharsets.UTF_8));
            return LHEX(messageDigestNewMD5.digest());
        }

        private static MessageDigest newMD5() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("No MD5 available", e);
            }
        }

        private static String LHEX(byte[] bArr) {
            StringBuilder sb = new StringBuilder(bArr.length * 2);
            for (byte b : bArr) {
                char[] cArr = LHEX;
                sb.append(cArr[(b >>> 4) & 15]);
                sb.append(cArr[b & 15]);
            }
            return sb.toString();
        }

        private static Map<String, String> parse(String str) {
            int i;
            String strSubstring;
            int i2;
            HashMap map = new HashMap();
            int i3 = 0;
            while (i3 < str.length()) {
                if (i3 < str.length() && str.charAt(i3) == ',') {
                    i3++;
                }
                while (i3 < str.length() && Character.isWhitespace(str.charAt(i3))) {
                    i3++;
                }
                int iIndexOf = str.indexOf(61, i3);
                if (iIndexOf < 0 || (i = iIndexOf + 1) == str.length()) {
                    return Collections.emptyMap();
                }
                String strSubstring2 = str.substring(i3, iIndexOf);
                if (str.charAt(i) == '\"') {
                    int i4 = iIndexOf + 2;
                    int iIndexOf2 = str.indexOf(34, i4);
                    if (iIndexOf2 < 0) {
                        return Collections.emptyMap();
                    }
                    String strSubstring3 = str.substring(i4, iIndexOf2);
                    int i5 = iIndexOf2 + 1;
                    strSubstring = strSubstring3;
                    i2 = i5;
                } else {
                    int iIndexOf3 = str.indexOf(32, i);
                    int iIndexOf4 = str.indexOf(44, i);
                    if (iIndexOf3 < 0) {
                        iIndexOf3 = str.length();
                    }
                    if (iIndexOf4 < 0) {
                        iIndexOf4 = str.length();
                    }
                    int iMin = Math.min(iIndexOf3, iIndexOf4);
                    strSubstring = str.substring(i, iMin);
                    i2 = iMin + 1;
                }
                map.put(strSubstring2, strSubstring);
                i3 = i2;
            }
            return map;
        }
    }

    private static class Negotiate extends HttpAuthMethod {
        private static final GSSManagerFactory GSS_MANAGER_FACTORY = GSSManagerFactory.detect();
        private static final Oid OID;
        private final byte[] prevToken;

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void authorize(String str, String str2) {
        }

        static {
            try {
                OID = new Oid("1.3.6.1.5.5.2");
            } catch (GSSException e) {
                throw new Error("Cannot create NEGOTIATE oid.", e);
            }
        }

        public Negotiate(String str) {
            super(Type.NEGOTIATE);
            this.prevToken = Base64.decode(str);
        }

        @Override // org.eclipse.jgit.transport.HttpAuthMethod
        void configureRequest(HttpConnection httpConnection) throws IOException {
            GSSManager gSSManagerNewInstance = GSS_MANAGER_FACTORY.newInstance(httpConnection.getURL());
            try {
                GSSContext gSSContextCreateContext = gSSManagerNewInstance.createContext(gSSManagerNewInstance.createName("HTTP@" + httpConnection.getURL().getHost().toLowerCase(Locale.ROOT), GSSName.NT_HOSTBASED_SERVICE), OID, (GSSCredential) null, 0);
                gSSContextCreateContext.requestCredDeleg(true);
                byte[] bArr = this.prevToken;
                httpConnection.setRequestProperty("Authorization", String.valueOf(getType().getSchemeName()) + " " + Base64.encodeBytes(gSSContextCreateContext.initSecContext(bArr, 0, bArr.length)));
            } catch (GSSException e) {
                throw new IOException((Throwable) e);
            }
        }
    }
}
