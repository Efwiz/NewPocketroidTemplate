package org.eclipse.jgit.transport;

import com.huawei.hms.framework.common.ContainerUtils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.Hex;
import org.eclipse.jgit.util.HttpSupport;

/* JADX INFO: loaded from: classes13.dex */
public final class AwsRequestSignerV4 {
    private static final String ALGORITHM = "HMAC-SHA256";
    private static final String EMPTY_BODY_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private static final String MAC_ALGORITHM = "HmacSHA256";
    private static final String SCHEME = "AWS4";
    private static final String TERMINATOR = "aws4_request";
    private static final DateTimeFormatter AMZ_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    private static final DateTimeFormatter SCOPE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private AwsRequestSignerV4() {
    }

    public static void sign(final HttpURLConnection httpURLConnection, Map<String, String> map, long j, String str, String str2, String str3, String str4, char[] cArr) {
        String host;
        final HashMap map2 = new HashMap();
        httpURLConnection.getRequestProperties().forEach(new BiConsumer() { // from class: org.eclipse.jgit.transport.AwsRequestSignerV4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                map2.put((String) obj, String.join(",", (List) obj2));
            }
        });
        if (j > 0) {
            map2.put("Content-Length", String.valueOf(j));
        } else {
            str = EMPTY_BODY_SHA256;
        }
        map2.put("x-amz-content-sha256", str);
        OffsetDateTime offsetDateTimeAtOffset = Instant.now().atOffset(ZoneOffset.UTC);
        String str5 = offsetDateTimeAtOffset.format(AMZ_DATE_FORMAT);
        map2.put("x-amz-date", str5);
        URL url = httpURLConnection.getURL();
        int port = url.getPort();
        if (port > -1) {
            host = url.getHost().concat(":" + port);
        } else {
            host = url.getHost();
        }
        map2.put("Host", host);
        String canonicalizeHeaderNames = getCanonicalizeHeaderNames(map2);
        String str6 = String.valueOf(httpURLConnection.getRequestMethod()) + '\n' + getCanonicalizedResourcePath(url) + '\n' + getCanonicalizedQueryString(map) + '\n' + getCanonicalizedHeaderString(map2) + '\n' + canonicalizeHeaderNames + '\n' + str;
        String str7 = offsetDateTimeAtOffset.format(SCOPE_DATE_FORMAT);
        String str8 = String.valueOf(str7) + '/' + str3 + '/' + str2 + "/aws4_request";
        byte[] bArrSignStringWithKey = signStringWithKey("AWS4-HMAC-SHA256\n" + str5 + '\n' + str8 + '\n' + Hex.toHexString(hash(str6.getBytes(StandardCharsets.UTF_8))), signStringWithKey(TERMINATOR, signStringWithKey(str2, signStringWithKey(str3, signStringWithKey(str7, SCHEME.concat(new String(cArr)).getBytes())))));
        String str9 = "AWS4-HMAC-SHA256 " + ("Credential=" + str4 + '/' + str8) + ", " + ("SignedHeaders=" + canonicalizeHeaderNames) + ", " + ("Signature=" + Hex.toHexString(bArrSignStringWithKey));
        httpURLConnection.getClass();
        map2.forEach(new BiConsumer() { // from class: org.eclipse.jgit.transport.AwsRequestSignerV4$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                httpURLConnection.setRequestProperty((String) obj, (String) obj2);
            }
        });
        httpURLConnection.setRequestProperty("Authorization", str9);
    }

    public static String calculateBodyHash(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return EMPTY_BODY_SHA256;
        }
        return Hex.toHexString(hash(bArr));
    }

    private static String getCanonicalizeHeaderNames(Map<String, String> map) {
        return (String) map.keySet().stream().map(new Function() { // from class: org.eclipse.jgit.transport.AwsRequestSignerV4$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((String) obj).toLowerCase();
            }
        }).sorted().collect(Collectors.joining(";"));
    }

    private static String getCanonicalizedHeaderString(final Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        map.keySet().stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(new Consumer() { // from class: org.eclipse.jgit.transport.AwsRequestSignerV4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AwsRequestSignerV4.lambda$3(map, sb, (String) obj);
            }
        });
        return sb.toString();
    }

    static /* synthetic */ void lambda$3(Map map, StringBuilder sb, String str) {
        String strReplaceAll = str.toLowerCase().replaceAll(WalkEncryption.Vals.REGEX_WS, " ");
        sb.append(strReplaceAll).append(':').append(((String) map.get(str)).replaceAll(WalkEncryption.Vals.REGEX_WS, " ")).append('\n');
    }

    private static String getCanonicalizedResourcePath(URL url) {
        String path;
        if (url == null || (path = url.getPath()) == null || path.isEmpty()) {
            return "/";
        }
        String strUrlEncode = HttpSupport.urlEncode(path, true);
        return strUrlEncode.startsWith("/") ? strUrlEncode : "/".concat(strUrlEncode);
    }

    public static String getCanonicalizedQueryString(final Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return (String) map.keySet().stream().sorted().map(new Function() { // from class: org.eclipse.jgit.transport.AwsRequestSignerV4$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AwsRequestSignerV4.lambda$4(map, (String) obj);
            }
        }).collect(Collectors.joining(ContainerUtils.FIELD_DELIMITER));
    }

    static /* synthetic */ String lambda$4(Map map, String str) {
        return String.valueOf(HttpSupport.urlEncode(str, false)) + '=' + HttpSupport.urlEncode((String) map.get(str), false);
    }

    public static byte[] hash(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA256);
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Exception e) {
            throw new RuntimeException(JGitText.get().couldNotHashByteArrayWithSha256, e);
        }
    }

    private static byte[] signStringWithKey(String str, byte[] bArr) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            Mac mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(new SecretKeySpec(bArr, MAC_ALGORITHM));
            return mac.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException(JGitText.get().couldNotSignStringWithKey, e);
        }
    }
}
