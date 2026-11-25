package com.koushikdutta.async.http;

import android.net.Uri;
import android.util.Log;
import com.koushikdutta.async.AsyncSSLException;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import java.util.Locale;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncHttpRequest {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_TIMEOUT = 30000;
    String LOGTAG;
    long executionTime;
    int logLevel;
    private AsyncHttpRequestBody mBody;
    private boolean mFollowRedirect;
    private String mMethod;
    private Headers mRawHeaders;
    int mTimeout;
    String proxyHost;
    int proxyPort;
    Uri uri;

    public void onHandshakeException(AsyncSSLException asyncSSLException) {
    }

    public RequestLine getRequestLine() {
        return new RequestLine() { // from class: com.koushikdutta.async.http.AsyncHttpRequest.1
            @Override // com.koushikdutta.async.http.RequestLine
            public String getUri() {
                return AsyncHttpRequest.this.getUri().toString();
            }

            @Override // com.koushikdutta.async.http.RequestLine
            public ProtocolVersion getProtocolVersion() {
                return new ProtocolVersion("HTTP", 1, 1);
            }

            @Override // com.koushikdutta.async.http.RequestLine
            public String getMethod() {
                return AsyncHttpRequest.this.mMethod;
            }

            public String toString() {
                if (AsyncHttpRequest.this.proxyHost != null) {
                    return String.format(Locale.ENGLISH, "%s %s HTTP/1.1", AsyncHttpRequest.this.mMethod, AsyncHttpRequest.this.getUri());
                }
                String encodedPath = AsyncHttpRequest.this.getUri().getEncodedPath();
                if (encodedPath == null || encodedPath.length() == 0) {
                    encodedPath = "/";
                }
                String encodedQuery = AsyncHttpRequest.this.getUri().getEncodedQuery();
                if (encodedQuery != null && encodedQuery.length() != 0) {
                    encodedPath = encodedPath + TypeDescription.Generic.OfWildcardType.SYMBOL + encodedQuery;
                }
                return String.format(Locale.ENGLISH, "%s %s HTTP/1.1", AsyncHttpRequest.this.mMethod, encodedPath);
            }
        };
    }

    protected static String getDefaultUserAgent() {
        String property = System.getProperty("http.agent");
        return property != null ? property : "Java" + System.getProperty("java.version");
    }

    public String getMethod() {
        return this.mMethod;
    }

    public AsyncHttpRequest setMethod(String str) {
        if (getClass() != AsyncHttpRequest.class) {
            throw new UnsupportedOperationException("can't change method on a subclass of AsyncHttpRequest");
        }
        this.mMethod = str;
        return this;
    }

    public AsyncHttpRequest(Uri uri, String str) {
        this(uri, str, null);
    }

    public static void setDefaultHeaders(Headers headers, Uri uri) {
        if (uri != null) {
            String host = uri.getHost();
            if (uri.getPort() != -1) {
                host = host + ":" + uri.getPort();
            }
            if (host != null) {
                headers.set("Host", host);
            }
        }
        headers.set("User-Agent", getDefaultUserAgent());
        headers.set("Accept-Encoding", "gzip, deflate");
        headers.set("Connection", "keep-alive");
        headers.set("Accept", "*/*");
    }

    public AsyncHttpRequest(Uri uri, String str, Headers headers) {
        this.mRawHeaders = new Headers();
        this.mFollowRedirect = true;
        this.mTimeout = 30000;
        this.proxyPort = -1;
        this.mMethod = str;
        this.uri = uri;
        if (headers == null) {
            this.mRawHeaders = new Headers();
        } else {
            this.mRawHeaders = headers;
        }
        if (headers == null) {
            setDefaultHeaders(this.mRawHeaders, uri);
        }
    }

    public Uri getUri() {
        return this.uri;
    }

    public Headers getHeaders() {
        return this.mRawHeaders;
    }

    public boolean getFollowRedirect() {
        return this.mFollowRedirect;
    }

    public AsyncHttpRequest setFollowRedirect(boolean z) {
        this.mFollowRedirect = z;
        return this;
    }

    public void setBody(AsyncHttpRequestBody asyncHttpRequestBody) {
        this.mBody = asyncHttpRequestBody;
    }

    public AsyncHttpRequestBody getBody() {
        return this.mBody;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public AsyncHttpRequest setTimeout(int i) {
        this.mTimeout = i;
        return this;
    }

    public AsyncHttpRequest setHeader(String str, String str2) {
        getHeaders().set(str, str2);
        return this;
    }

    public AsyncHttpRequest addHeader(String str, String str2) {
        getHeaders().add(str, str2);
        return this;
    }

    public void enableProxy(String str, int i) {
        this.proxyHost = str;
        this.proxyPort = i;
    }

    public void disableProxy() {
        this.proxyHost = null;
        this.proxyPort = -1;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public int getProxyPort() {
        return this.proxyPort;
    }

    public String toString() {
        Headers headers = this.mRawHeaders;
        if (headers == null) {
            return super.toString();
        }
        return headers.toPrefixString(this.uri.toString());
    }

    public void setLogging(String str, int i) {
        this.LOGTAG = str;
        this.logLevel = i;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public String getLogTag() {
        return this.LOGTAG;
    }

    private String getLogMessage(String str) {
        return String.format(Locale.ENGLISH, "(%d ms) %s: %s", Long.valueOf(this.executionTime != 0 ? System.currentTimeMillis() - this.executionTime : 0L), getUri(), str);
    }

    public void logi(String str) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 4) {
            Log.i(str2, getLogMessage(str));
        }
    }

    public void logv(String str) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 2) {
            Log.v(str2, getLogMessage(str));
        }
    }

    public void logw(String str) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 5) {
            Log.w(str2, getLogMessage(str));
        }
    }

    public void logd(String str) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 3) {
            Log.d(str2, getLogMessage(str));
        }
    }

    public void logd(String str, Exception exc) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 3) {
            Log.d(str2, getLogMessage(str));
            Log.d(this.LOGTAG, exc.getMessage(), exc);
        }
    }

    public void loge(String str) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 6) {
            Log.e(str2, getLogMessage(str));
        }
    }

    public void loge(String str, Exception exc) {
        String str2 = this.LOGTAG;
        if (str2 != null && this.logLevel <= 6) {
            Log.e(str2, getLogMessage(str));
            Log.e(this.LOGTAG, exc.getMessage(), exc);
        }
    }
}
