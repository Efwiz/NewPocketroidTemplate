package com.badlogic.gdx;

import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Pool;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Net.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Net {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Net$HttpMethods.class
      classes8.dex
     */
    public interface HttpMethods {
        public static final String DELETE = "DELETE";
        public static final String GET = "GET";
        public static final String HEAD = "HEAD";
        public static final String PATCH = "PATCH";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Net$HttpResponse.class
      classes8.dex
     */
    public interface HttpResponse {
        String getHeader(String str);

        Map<String, List<String>> getHeaders();

        byte[] getResult();

        InputStream getResultAsStream();

        String getResultAsString();

        HttpStatus getStatus();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Net$HttpResponseListener.class
      classes8.dex
     */
    public interface HttpResponseListener {
        void cancelled();

        void failed(Throwable th);

        void handleHttpResponse(HttpResponse httpResponse);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Net$Protocol.class
      classes8.dex
     */
    public enum Protocol {
        TCP
    }

    void cancelHttpRequest(HttpRequest httpRequest);

    boolean isHttpRequestPending(HttpRequest httpRequest);

    Socket newClientSocket(Protocol protocol, String str, int i, SocketHints socketHints);

    ServerSocket newServerSocket(Protocol protocol, int i, ServerSocketHints serverSocketHints);

    ServerSocket newServerSocket(Protocol protocol, String str, int i, ServerSocketHints serverSocketHints);

    boolean openURI(String str);

    void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Net$HttpRequest.class
      classes8.dex
     */
    public static class HttpRequest implements Pool.Poolable {
        private String content;
        private long contentLength;
        private InputStream contentStream;
        private boolean followRedirects;
        private Map<String, String> headers;
        private String httpMethod;
        private boolean includeCredentials;
        private int timeOut;
        private String url;

        public HttpRequest() {
            this.timeOut = 0;
            this.followRedirects = true;
            this.includeCredentials = false;
            this.headers = new HashMap();
        }

        public HttpRequest(String str) {
            this();
            this.httpMethod = str;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public void setHeader(String str, String str2) {
            this.headers.put(str, str2);
        }

        public void setContent(String str) {
            this.content = str;
        }

        public void setContent(InputStream inputStream, long j) {
            this.contentStream = inputStream;
            this.contentLength = j;
        }

        public void setTimeOut(int i) {
            this.timeOut = i;
        }

        public void setFollowRedirects(boolean z) throws IllegalArgumentException {
            if (z || Gdx.f82app.getType() != Application.ApplicationType.WebGL) {
                this.followRedirects = z;
                return;
            }
            throw new IllegalArgumentException("Following redirects can't be disabled using the GWT/WebGL backend!");
        }

        public void setIncludeCredentials(boolean z) {
            this.includeCredentials = z;
        }

        public void setMethod(String str) {
            this.httpMethod = str;
        }

        public int getTimeOut() {
            return this.timeOut;
        }

        public String getMethod() {
            return this.httpMethod;
        }

        public String getUrl() {
            return this.url;
        }

        public String getContent() {
            return this.content;
        }

        public InputStream getContentStream() {
            return this.contentStream;
        }

        public long getContentLength() {
            return this.contentLength;
        }

        public Map<String, String> getHeaders() {
            return this.headers;
        }

        public boolean getFollowRedirects() {
            return this.followRedirects;
        }

        public boolean getIncludeCredentials() {
            return this.includeCredentials;
        }

        @Override // com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            this.httpMethod = null;
            this.url = null;
            this.headers.clear();
            this.timeOut = 0;
            this.content = null;
            this.contentStream = null;
            this.contentLength = 0L;
            this.followRedirects = true;
        }
    }
}
