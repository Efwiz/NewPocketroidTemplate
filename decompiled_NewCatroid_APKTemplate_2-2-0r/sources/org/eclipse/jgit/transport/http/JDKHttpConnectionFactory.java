package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.transport.http.DelegatingSSLSocketFactory;
import org.eclipse.jgit.util.HttpSupport;

/* JADX INFO: loaded from: classes13.dex */
public class JDKHttpConnectionFactory implements HttpConnectionFactory2 {
    @Override // org.eclipse.jgit.transport.http.HttpConnectionFactory
    public HttpConnection create(URL url) throws IOException {
        return new JDKHttpConnection(url);
    }

    @Override // org.eclipse.jgit.transport.http.HttpConnectionFactory
    public HttpConnection create(URL url, Proxy proxy) throws IOException {
        return new JDKHttpConnection(url, proxy);
    }

    @Override // org.eclipse.jgit.transport.http.HttpConnectionFactory2
    public HttpConnectionFactory2.GitSession newSession() {
        return new JdkConnectionSession(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class JdkConnectionSession implements HttpConnectionFactory2.GitSession {
        private SSLContext securityContext;
        private SSLSocketFactory socketFactory;

        static /* synthetic */ boolean lambda$0(String str, SSLSession sSLSession) {
            return true;
        }

        private JdkConnectionSession() {
        }

        /* synthetic */ JdkConnectionSession(JdkConnectionSession jdkConnectionSession) {
            this();
        }

        @Override // org.eclipse.jgit.transport.http.HttpConnectionFactory2.GitSession
        public JDKHttpConnection configure(HttpConnection httpConnection, boolean z) throws GeneralSecurityException {
            if (!(httpConnection instanceof JDKHttpConnection)) {
                throw new IllegalArgumentException(MessageFormat.format(JGitText.get().httpWrongConnectionType, JDKHttpConnection.class.getName(), httpConnection.getClass().getName()));
            }
            JDKHttpConnection jDKHttpConnection = (JDKHttpConnection) httpConnection;
            if ("https".equals(jDKHttpConnection.getURL().getProtocol()) && !z) {
                if (this.securityContext == null) {
                    this.securityContext = SSLContext.getInstance("TLS");
                    this.securityContext.init(null, new TrustManager[]{new NoCheckX509TrustManager()}, null);
                    this.socketFactory = new DelegatingSSLSocketFactory(this.securityContext.getSocketFactory()) { // from class: org.eclipse.jgit.transport.http.JDKHttpConnectionFactory.JdkConnectionSession.1
                        @Override // org.eclipse.jgit.internal.transport.http.DelegatingSSLSocketFactory
                        protected void configure(SSLSocket sSLSocket) {
                            HttpSupport.configureTLS(sSLSocket);
                        }
                    };
                }
                jDKHttpConnection.setHostnameVerifier(new HostnameVerifier() { // from class: org.eclipse.jgit.transport.http.JDKHttpConnectionFactory$JdkConnectionSession$$ExternalSyntheticLambda0
                    @Override // javax.net.ssl.HostnameVerifier
                    public final boolean verify(String str, SSLSession sSLSession) {
                        return JDKHttpConnectionFactory.JdkConnectionSession.lambda$0(str, sSLSession);
                    }
                });
                ((HttpsURLConnection) jDKHttpConnection.wrappedUrlConnection).setSSLSocketFactory(this.socketFactory);
            }
            return jDKHttpConnection;
        }

        @Override // org.eclipse.jgit.transport.http.HttpConnectionFactory2.GitSession
        public void close() {
            this.securityContext = null;
            this.socketFactory = null;
        }
    }
}
