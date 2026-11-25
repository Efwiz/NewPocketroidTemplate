package org.catrobat.catroid.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.antlr.stringtemplate.language.ASTExpr;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.transport.HttpConfig;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NewCatroidHttpManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: NewCatroidHttpManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u000b*\u0001\f\bÆ\u0002\u0018\u00002\u00020\u0001:\u000212B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005J\u001c\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u001fJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u0005J\u0016\u0010\"\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0005J\u000e\u0010$\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0005J\u0010\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0005H\u0002J\u0016\u0010'\u001a\u00020(2\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0017J\u001e\u0010*\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00052\u0006\u0010,\u001a\u00020\u0005J&\u0010-\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR6\u0010\u000e\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fj\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/catrobat/catroid/common/NewCatroidHttpManager;", "", "()V", "activeRequests", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/catrobat/catroid/common/NewCatroidHttpManager$RequestState;", "baseClient", "Lokhttp3/OkHttpClient;", "completedResponses", "Lorg/catrobat/catroid/common/NewCatroidHttpManager$ResponseState;", "cookieJar", "org/catrobat/catroid/common/NewCatroidHttpManager$cookieJar$1", "Lorg/catrobat/catroid/common/NewCatroidHttpManager$cookieJar$1;", "cookieStore", "Ljava/util/HashMap;", "", "Lokhttp3/Cookie;", "Lkotlin/collections/HashMap;", "attachFile", "", "id", "file", "Ljava/io/File;", "partName", "mimeType", "createRequest", "method", "url", "executeRequest", "onComplete", "Lkotlin/Function0;", "getResponseCode", "", "getResponseHeader", "headerName", "getResponseText", "sanitizeUrl", "urlStr", "saveResponseToFile", "", "destinationFile", "setBodyText", "body", CMSAttributeTableGenerator.CONTENT_TYPE, "setConfig", ModuleXmlParser.TYPE, ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "RequestState", "ResponseState", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCatroidHttpManager {
    public static final NewCatroidHttpManager INSTANCE = new NewCatroidHttpManager();
    private static final OkHttpClient baseClient = new OkHttpClient();
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final NewCatroidHttpManager$cookieJar$1 cookieJar = new CookieJar() { // from class: org.catrobat.catroid.common.NewCatroidHttpManager$cookieJar$1
        @Override // okhttp3.CookieJar
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(cookies, "cookies");
            NewCatroidHttpManager.cookieStore.put(url.host(), cookies);
        }

        @Override // okhttp3.CookieJar
        public List<Cookie> loadForRequest(HttpUrl url) {
            Intrinsics.checkNotNullParameter(url, "url");
            List<Cookie> list = (List) NewCatroidHttpManager.cookieStore.get(url.host());
            return list == null ? CollectionsKt.emptyList() : list;
        }
    };
    private static final ConcurrentHashMap<String, RequestState> activeRequests = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ResponseState> completedResponses = new ConcurrentHashMap<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean executeRequest$lambda$0(String str, SSLSession sSLSession) {
        return true;
    }

    private NewCatroidHttpManager() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NewCatroidHttpManager$RequestState.class
      classes12.dex
     */
    /* JADX INFO: compiled from: NewCatroidHttpManager.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\tR\u001c\u0010!\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\t\"\u0004\b#\u0010\u000bR\u001e\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u0010\n\u0002\u0010*\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001d\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u001c¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001eR\u001a\u0010-\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0012\"\u0004\b/\u0010\u0014R\u001a\u00100\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0018\"\u0004\b2\u0010\u001aR\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u00109\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\t\"\u0004\b;\u0010\u000bR\u001a\u0010<\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\t\"\u0004\b>\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\t\"\u0004\b@\u0010\u000bR\u001a\u0010A\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u0018\"\u0004\bC\u0010\u001aR\u001a\u0010D\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0012\"\u0004\bF\u0010\u0014¨\u0006G"}, d2 = {"Lorg/catrobat/catroid/common/NewCatroidHttpManager$RequestState;", "", "id", "", "method", "url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "bodyContentType", "getBodyContentType", "()Ljava/lang/String;", "setBodyContentType", "(Ljava/lang/String;)V", "bodyText", "getBodyText", "setBodyText", "connectTimeoutMs", "", "getConnectTimeoutMs", "()J", "setConnectTimeoutMs", "(J)V", HttpConfig.FOLLOW_REDIRECTS_KEY, "", "getFollowRedirects", "()Z", "setFollowRedirects", "(Z)V", "headers", "", "getHeaders", "()Ljava/util/Map;", "getId", "getMethod", "proxyHost", "getProxyHost", "setProxyHost", "proxyPort", "", "getProxyPort", "()Ljava/lang/Integer;", "setProxyPort", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "queryParams", "getQueryParams", "readTimeoutMs", "getReadTimeoutMs", "setReadTimeoutMs", "trustAllCerts", "getTrustAllCerts", "setTrustAllCerts", "uploadFile", "Ljava/io/File;", "getUploadFile", "()Ljava/io/File;", "setUploadFile", "(Ljava/io/File;)V", "uploadFileMimeType", "getUploadFileMimeType", "setUploadFileMimeType", "uploadFilePartName", "getUploadFilePartName", "setUploadFilePartName", "getUrl", "setUrl", "useCookies", "getUseCookies", "setUseCookies", "writeTimeoutMs", "getWriteTimeoutMs", "setWriteTimeoutMs", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RequestState {
        private String bodyContentType;
        private String bodyText;
        private long connectTimeoutMs;
        private boolean followRedirects;
        private final Map<String, String> headers;
        private final String id;
        private final String method;
        private String proxyHost;
        private Integer proxyPort;
        private final Map<String, String> queryParams;
        private long readTimeoutMs;
        private boolean trustAllCerts;
        private File uploadFile;
        private String uploadFileMimeType;
        private String uploadFilePartName;
        private String url;
        private boolean useCookies;
        private long writeTimeoutMs;

        public RequestState(String id2, String method, String url) {
            Intrinsics.checkNotNullParameter(id2, "id");
            Intrinsics.checkNotNullParameter(method, "method");
            Intrinsics.checkNotNullParameter(url, "url");
            this.id = id2;
            this.method = method;
            this.url = url;
            this.headers = new LinkedHashMap();
            this.queryParams = new LinkedHashMap();
            this.bodyContentType = "application/json";
            this.uploadFilePartName = "file";
            this.uploadFileMimeType = "application/octet-stream";
            this.connectTimeoutMs = 10000L;
            this.readTimeoutMs = 10000L;
            this.writeTimeoutMs = 10000L;
            this.followRedirects = true;
            this.useCookies = true;
        }

        public final String getId() {
            return this.id;
        }

        public final String getMethod() {
            return this.method;
        }

        public final String getUrl() {
            return this.url;
        }

        public final void setUrl(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.url = str;
        }

        public final Map<String, String> getHeaders() {
            return this.headers;
        }

        public final Map<String, String> getQueryParams() {
            return this.queryParams;
        }

        public final String getBodyText() {
            return this.bodyText;
        }

        public final void setBodyText(String str) {
            this.bodyText = str;
        }

        public final String getBodyContentType() {
            return this.bodyContentType;
        }

        public final void setBodyContentType(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.bodyContentType = str;
        }

        public final File getUploadFile() {
            return this.uploadFile;
        }

        public final void setUploadFile(File file) {
            this.uploadFile = file;
        }

        public final String getUploadFilePartName() {
            return this.uploadFilePartName;
        }

        public final void setUploadFilePartName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.uploadFilePartName = str;
        }

        public final String getUploadFileMimeType() {
            return this.uploadFileMimeType;
        }

        public final void setUploadFileMimeType(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.uploadFileMimeType = str;
        }

        public final long getConnectTimeoutMs() {
            return this.connectTimeoutMs;
        }

        public final void setConnectTimeoutMs(long j) {
            this.connectTimeoutMs = j;
        }

        public final long getReadTimeoutMs() {
            return this.readTimeoutMs;
        }

        public final void setReadTimeoutMs(long j) {
            this.readTimeoutMs = j;
        }

        public final long getWriteTimeoutMs() {
            return this.writeTimeoutMs;
        }

        public final void setWriteTimeoutMs(long j) {
            this.writeTimeoutMs = j;
        }

        public final boolean getFollowRedirects() {
            return this.followRedirects;
        }

        public final void setFollowRedirects(boolean z) {
            this.followRedirects = z;
        }

        public final boolean getTrustAllCerts() {
            return this.trustAllCerts;
        }

        public final void setTrustAllCerts(boolean z) {
            this.trustAllCerts = z;
        }

        public final boolean getUseCookies() {
            return this.useCookies;
        }

        public final void setUseCookies(boolean z) {
            this.useCookies = z;
        }

        public final String getProxyHost() {
            return this.proxyHost;
        }

        public final void setProxyHost(String str) {
            this.proxyHost = str;
        }

        public final Integer getProxyPort() {
            return this.proxyPort;
        }

        public final void setProxyPort(Integer num) {
            this.proxyPort = num;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NewCatroidHttpManager$ResponseState.class
      classes12.dex
     */
    /* JADX INFO: compiled from: NewCatroidHttpManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\r\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/common/NewCatroidHttpManager$ResponseState;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "headers", "", "", "bodyBytes", "", "(ILjava/util/Map;[B)V", "getBodyBytes", "()[B", "bodyString", "getBodyString", "()Ljava/lang/String;", "bodyString$delegate", "Lkotlin/Lazy;", "getCode", "()I", "getHeaders", "()Ljava/util/Map;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ResponseState {
        private final byte[] bodyBytes;

        /* JADX INFO: renamed from: bodyString$delegate, reason: from kotlin metadata */
        private final Lazy bodyString;
        private final int code;
        private final Map<String, String> headers;

        public ResponseState(int i, Map<String, String> headers, byte[] bArr) {
            Intrinsics.checkNotNullParameter(headers, "headers");
            this.code = i;
            this.headers = headers;
            this.bodyBytes = bArr;
            this.bodyString = LazyKt.lazy(new Function0<String>() { // from class: org.catrobat.catroid.common.NewCatroidHttpManager$ResponseState$bodyString$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    String strDecodeToString;
                    byte[] bodyBytes = this.this$0.getBodyBytes();
                    return (bodyBytes == null || (strDecodeToString = StringsKt.decodeToString(bodyBytes)) == null) ? "" : strDecodeToString;
                }
            });
        }

        public final int getCode() {
            return this.code;
        }

        public final Map<String, String> getHeaders() {
            return this.headers;
        }

        public final byte[] getBodyBytes() {
            return this.bodyBytes;
        }

        public final String getBodyString() {
            return (String) this.bodyString.getValue();
        }
    }

    public final void createRequest(String id2, String method, String url) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(url, "url");
        ConcurrentHashMap<String, RequestState> concurrentHashMap = activeRequests;
        String upperCase = method.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        concurrentHashMap.put(id2, new RequestState(id2, upperCase, url));
        completedResponses.remove(id2);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void setConfig(String id2, String type, String key, String value) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        RequestState requestState = activeRequests.get(id2);
        if (requestState == null) {
        }
        String lowerCase = type.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        switch (lowerCase.hashCode()) {
            case -1861273383:
                if (lowerCase.equals("proxy_host")) {
                    requestState.setProxyHost(value);
                    break;
                }
                break;
            case -1861035086:
                if (lowerCase.equals("proxy_port")) {
                    requestState.setProxyPort(StringsKt.toIntOrNull(value));
                    break;
                }
                break;
            case -1221270899:
                if (lowerCase.equals("header")) {
                    requestState.getHeaders().put(key, value);
                    break;
                }
                break;
            case -611433078:
                if (lowerCase.equals("trust_all_certs")) {
                    requestState.setTrustAllCerts(Boolean.parseBoolean(value));
                    break;
                }
                break;
            case 107944136:
                if (lowerCase.equals("query")) {
                    requestState.getQueryParams().put(key, value);
                    break;
                }
                break;
            case 232011735:
                if (lowerCase.equals("use_cookies")) {
                    requestState.setUseCookies(Boolean.parseBoolean(value));
                    break;
                }
                break;
            case 332331444:
                if (lowerCase.equals("timeout_read")) {
                    Long longOrNull = StringsKt.toLongOrNull(value);
                    requestState.setReadTimeoutMs(longOrNull != null ? longOrNull.longValue() : 10000L);
                    break;
                }
                break;
            case 457473036:
                if (lowerCase.equals("timeout_connect")) {
                    Long longOrNull2 = StringsKt.toLongOrNull(value);
                    requestState.setConnectTimeoutMs(longOrNull2 != null ? longOrNull2.longValue() : 10000L);
                    break;
                }
                break;
            case 1485967625:
                if (lowerCase.equals("follow_redirects")) {
                    requestState.setFollowRedirects(Boolean.parseBoolean(value));
                    break;
                }
                break;
            case 1717353345:
                if (lowerCase.equals("timeout_write")) {
                    Long longOrNull3 = StringsKt.toLongOrNull(value);
                    requestState.setWriteTimeoutMs(longOrNull3 != null ? longOrNull3.longValue() : 10000L);
                    break;
                }
                break;
        }
    }

    private final String sanitizeUrl(String urlStr) {
        String strReplace$default = StringsKt.replace$default(urlStr, " ", "%20", false, 4, (Object) null);
        StringBuilder sb = new StringBuilder();
        int length = strReplace$default.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = strReplace$default.charAt(i);
            if (cCharAt > 127) {
                sb.append(URLEncoder.encode(String.valueOf(cCharAt), "UTF-8"));
            } else {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final void setBodyText(String id2, String body, String contentType) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        RequestState requestState = activeRequests.get(id2);
        if (requestState == null) {
            return;
        }
        requestState.setBodyText(body);
        requestState.setBodyContentType(contentType);
    }

    public final void attachFile(String id2, File file, String partName, String mimeType) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(partName, "partName");
        Intrinsics.checkNotNullParameter(mimeType, "mimeType");
        RequestState requestState = activeRequests.get(id2);
        if (requestState == null) {
            return;
        }
        requestState.setUploadFile(file);
        requestState.setUploadFilePartName(partName);
        if (StringsKt.trim((CharSequence) mimeType).toString().length() == 0) {
            mimeType = "application/octet-stream";
        }
        requestState.setUploadFileMimeType(mimeType);
    }

    /* JADX WARN: Code duplicated, block: B:45:0x01ed  */
    /* JADX WARN: Code duplicated, block: B:47:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:48:0x020b  */
    /* JADX WARN: Code duplicated, block: B:53:0x022a  */
    public final void executeRequest(String id2, Function0<Unit> onComplete) {
        MultipartBody multipartBodyCreate;
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        RequestState requestState = activeRequests.get(id2);
        if (requestState == null) {
            ConcurrentHashMap<String, ResponseState> concurrentHashMap = completedResponses;
            Map mapEmptyMap = MapsKt.emptyMap();
            byte[] bytes = ("Request failed: Request ID '" + id2 + "' was not created. Check for typos!").getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            concurrentHashMap.put(id2, new ResponseState(-1, mapEmptyMap, bytes));
            onComplete.invoke();
            return;
        }
        completedResponses.remove(id2);
        if (!requestState.getHeaders().containsKey("User-Agent")) {
            requestState.getHeaders().put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        }
        OkHttpClient.Builder builderFollowSslRedirects = baseClient.newBuilder().connectTimeout(requestState.getConnectTimeoutMs(), TimeUnit.MILLISECONDS).readTimeout(requestState.getReadTimeoutMs(), TimeUnit.MILLISECONDS).writeTimeout(requestState.getWriteTimeoutMs(), TimeUnit.MILLISECONDS).followRedirects(requestState.getFollowRedirects()).followSslRedirects(requestState.getFollowRedirects());
        if (requestState.getUseCookies()) {
            builderFollowSslRedirects.cookieJar(cookieJar);
        }
        if (requestState.getTrustAllCerts()) {
            try {
                X509TrustManager[] x509TrustManagerArr = {new X509TrustManager() { // from class: org.catrobat.catroid.common.NewCatroidHttpManager$executeRequest$trustAllCerts$1
                    @Override // javax.net.ssl.X509TrustManager
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override // javax.net.ssl.X509TrustManager
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override // javax.net.ssl.X509TrustManager
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};
                SSLContext sSLContext = SSLContext.getInstance("SSL");
                sSLContext.init(null, x509TrustManagerArr, new SecureRandom());
                SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
                Intrinsics.checkNotNullExpressionValue(socketFactory, "getSocketFactory(...)");
                builderFollowSslRedirects.sslSocketFactory(socketFactory, x509TrustManagerArr[0]);
                builderFollowSslRedirects.hostnameVerifier(new HostnameVerifier() { // from class: org.catrobat.catroid.common.NewCatroidHttpManager$$ExternalSyntheticLambda0
                    @Override // javax.net.ssl.HostnameVerifier
                    public final boolean verify(String str, SSLSession sSLSession) {
                        return NewCatroidHttpManager.executeRequest$lambda$0(str, sSLSession);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String proxyHost = requestState.getProxyHost();
        if (proxyHost != null && proxyHost.length() != 0 && requestState.getProxyPort() != null) {
            String proxyHost2 = requestState.getProxyHost();
            Integer proxyPort = requestState.getProxyPort();
            Intrinsics.checkNotNull(proxyPort);
            builderFollowSslRedirects.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost2, proxyPort.intValue())));
        }
        OkHttpClient okHttpClientBuild = builderFollowSslRedirects.build();
        String strSanitizeUrl = sanitizeUrl(requestState.getUrl());
        HttpUrl httpUrl = HttpUrl.INSTANCE.parse(strSanitizeUrl);
        HttpUrl.Builder builderNewBuilder = httpUrl != null ? httpUrl.newBuilder() : null;
        if (builderNewBuilder == null) {
            ConcurrentHashMap<String, ResponseState> concurrentHashMap2 = completedResponses;
            Map mapEmptyMap2 = MapsKt.emptyMap();
            byte[] bytes2 = ("Request failed: Invalid URL '" + strSanitizeUrl + FormatHelper.QUOTE).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
            concurrentHashMap2.put(id2, new ResponseState(-1, mapEmptyMap2, bytes2));
            onComplete.invoke();
            return;
        }
        for (Map.Entry<String, String> entry : requestState.getQueryParams().entrySet()) {
            builderNewBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        HttpUrl httpUrlBuild = builderNewBuilder.build();
        if (requestState.getUploadFile() != null) {
            File uploadFile = requestState.getUploadFile();
            Intrinsics.checkNotNull(uploadFile);
            if (uploadFile.exists()) {
                MediaType mediaType = MediaType.INSTANCE.parse(requestState.getUploadFileMimeType());
                RequestBody.Companion companion = RequestBody.INSTANCE;
                File uploadFile2 = requestState.getUploadFile();
                Intrinsics.checkNotNull(uploadFile2);
                RequestBody requestBodyCreate = companion.create(uploadFile2, mediaType);
                MultipartBody.Builder type = new MultipartBody.Builder(null, 1, null).setType(MultipartBody.FORM);
                String uploadFilePartName = requestState.getUploadFilePartName();
                File uploadFile3 = requestState.getUploadFile();
                Intrinsics.checkNotNull(uploadFile3);
                MultipartBody.Builder builderAddFormDataPart = type.addFormDataPart(uploadFilePartName, uploadFile3.getName(), requestBodyCreate);
                String bodyText = requestState.getBodyText();
                if (bodyText != null) {
                    builderAddFormDataPart.addFormDataPart("payload", bodyText);
                }
                multipartBodyCreate = builderAddFormDataPart.build();
            } else if (requestState.getBodyText() != null) {
                RequestBody.Companion companion2 = RequestBody.INSTANCE;
                String bodyText2 = requestState.getBodyText();
                Intrinsics.checkNotNull(bodyText2);
                multipartBodyCreate = companion2.create(bodyText2, MediaType.INSTANCE.parse(requestState.getBodyContentType()));
            } else if (!Intrinsics.areEqual(requestState.getMethod(), "GET") || Intrinsics.areEqual(requestState.getMethod(), "HEAD")) {
                multipartBodyCreate = null;
            } else {
                multipartBodyCreate = RequestBody.INSTANCE.create("", (MediaType) null);
            }
        } else if (requestState.getBodyText() != null) {
            RequestBody.Companion companion3 = RequestBody.INSTANCE;
            String bodyText3 = requestState.getBodyText();
            Intrinsics.checkNotNull(bodyText3);
            multipartBodyCreate = companion3.create(bodyText3, MediaType.INSTANCE.parse(requestState.getBodyContentType()));
        } else if (Intrinsics.areEqual(requestState.getMethod(), "GET")) {
            multipartBodyCreate = null;
        } else {
            multipartBodyCreate = null;
        }
        Request.Builder builderMethod = new Request.Builder().url(httpUrlBuild).method(requestState.getMethod(), multipartBodyCreate);
        for (Map.Entry<String, String> entry2 : requestState.getHeaders().entrySet()) {
            builderMethod.addHeader(entry2.getKey(), entry2.getValue());
        }
        try {
            Response responseExecute = okHttpClientBuild.newCall(builderMethod.build()).execute();
            try {
                Response response = responseExecute;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (String str : response.headers().names()) {
                    String strHeader$default = Response.header$default(response, str, null, 2, null);
                    if (strHeader$default == null) {
                        strHeader$default = "";
                    }
                    linkedHashMap.put(str, strHeader$default);
                }
                ResponseBody responseBodyBody = response.body();
                completedResponses.put(id2, new ResponseState(response.code(), linkedHashMap, responseBodyBody != null ? responseBodyBody.bytes() : null));
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(responseExecute, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(responseExecute, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                th3.printStackTrace();
                ConcurrentHashMap<String, ResponseState> concurrentHashMap3 = completedResponses;
                Map mapEmptyMap3 = MapsKt.emptyMap();
                byte[] bytes3 = ("Request failed: " + th3.getLocalizedMessage()).getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
                concurrentHashMap3.put(id2, new ResponseState(-1, mapEmptyMap3, bytes3));
            } finally {
                onComplete.invoke();
            }
        }
    }

    public final boolean saveResponseToFile(String id2, File destinationFile) {
        byte[] bodyBytes;
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(destinationFile, "destinationFile");
        ResponseState responseState = completedResponses.get(id2);
        if (responseState == null || (bodyBytes = responseState.getBodyBytes()) == null) {
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
            try {
                fileOutputStream.write(bodyBytes);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final String getResponseText(String id2) {
        String bodyString;
        Intrinsics.checkNotNullParameter(id2, "id");
        ResponseState responseState = completedResponses.get(id2);
        return (responseState == null || (bodyString = responseState.getBodyString()) == null) ? "" : bodyString;
    }

    public final int getResponseCode(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        ResponseState responseState = completedResponses.get(id2);
        if (responseState != null) {
            return responseState.getCode();
        }
        return -1;
    }

    public final String getResponseHeader(String id2, String headerName) {
        Map<String, String> headers;
        String str;
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(headerName, "headerName");
        ResponseState responseState = completedResponses.get(id2);
        return (responseState == null || (headers = responseState.getHeaders()) == null || (str = headers.get(headerName)) == null) ? "" : str;
    }
}
