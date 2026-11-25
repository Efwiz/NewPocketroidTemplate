package com.koushikdutta.async;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.util.Allocator;
import com.koushikdutta.async.wrapper.AsyncSocketWrapper;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.StrictHostnameVerifier;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncSSLSocketWrapper implements AsyncSocketWrapper, AsyncSSLSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static SSLContext defaultSSLContext;
    boolean clientMode;
    final DataCallback dataCallback;
    SSLEngine engine;
    boolean finishedHandshake;
    HandshakeCallback handshakeCallback;
    HostnameVerifier hostnameVerifier;
    DataCallback mDataCallback;
    CompletedCallback mEndCallback;
    Exception mEndException;
    boolean mEnded;
    private String mHost;
    private int mPort;
    BufferedDataSink mSink;
    AsyncSocket mSocket;
    boolean mUnwrapping;
    private boolean mWrapping;
    WritableCallback mWriteableCallback;
    X509Certificate[] peerCertificates;
    final ByteBufferList pending = new ByteBufferList();
    TrustManager[] trustManagers;
    ByteBufferList writeList;

    public interface HandshakeCallback {
        void onHandshakeCompleted(Exception exc, AsyncSSLSocket asyncSSLSocket);
    }

    @Override // com.koushikdutta.async.DataEmitter
    public String charset() {
        return null;
    }

    static {
        try {
            defaultSSLContext = SSLContext.getInstance("Default");
        } catch (Exception e) {
            try {
                defaultSSLContext = SSLContext.getInstance("TLS");
                defaultSSLContext.init(null, new TrustManager[]{new X509TrustManager() { // from class: com.koushikdutta.async.AsyncSSLSocketWrapper.1
                    @Override // javax.net.ssl.X509TrustManager
                    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
                    }

                    @Override // javax.net.ssl.X509TrustManager
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override // javax.net.ssl.X509TrustManager
                    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                        for (X509Certificate x509Certificate : x509CertificateArr) {
                            if (x509Certificate != null && x509Certificate.getCriticalExtensionOIDs() != null) {
                                x509Certificate.getCriticalExtensionOIDs().remove("2.5.29.15");
                            }
                        }
                    }
                }}, null);
            } catch (Exception e2) {
                e.printStackTrace();
                e2.printStackTrace();
            }
        }
    }

    public static SSLContext getDefaultSSLContext() {
        return defaultSSLContext;
    }

    public static void handshake(AsyncSocket asyncSocket, String str, int i, SSLEngine sSLEngine, TrustManager[] trustManagerArr, HostnameVerifier hostnameVerifier, boolean z, final HandshakeCallback handshakeCallback) {
        AsyncSSLSocketWrapper asyncSSLSocketWrapper = new AsyncSSLSocketWrapper(asyncSocket, str, i, sSLEngine, trustManagerArr, hostnameVerifier, z);
        asyncSSLSocketWrapper.handshakeCallback = handshakeCallback;
        asyncSocket.setClosedCallback(new CompletedCallback() { // from class: com.koushikdutta.async.AsyncSSLSocketWrapper.2
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (exc != null) {
                    handshakeCallback.onHandshakeCompleted(exc, null);
                } else {
                    handshakeCallback.onHandshakeCompleted(new SSLException("socket closed during handshake"), null);
                }
            }
        });
        try {
            asyncSSLSocketWrapper.engine.beginHandshake();
            asyncSSLSocketWrapper.handleHandshakeStatus(asyncSSLSocketWrapper.engine.getHandshakeStatus());
        } catch (SSLException e) {
            asyncSSLSocketWrapper.report(e);
        }
    }

    private AsyncSSLSocketWrapper(AsyncSocket asyncSocket, String str, int i, SSLEngine sSLEngine, TrustManager[] trustManagerArr, HostnameVerifier hostnameVerifier, boolean z) {
        DataCallback dataCallback = new DataCallback() { // from class: com.koushikdutta.async.AsyncSSLSocketWrapper.5
            final Allocator allocator = new Allocator().setMinAlloc(8192);
            final ByteBufferList buffered = new ByteBufferList();

            @Override // com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                if (AsyncSSLSocketWrapper.this.mUnwrapping) {
                    return;
                }
                try {
                    try {
                        AsyncSSLSocketWrapper.this.mUnwrapping = true;
                        byteBufferList.get(this.buffered);
                        if (this.buffered.hasRemaining()) {
                            this.buffered.add(this.buffered.getAll());
                        }
                        ByteBuffer byteBufferRemove = ByteBufferList.EMPTY_BYTEBUFFER;
                        while (true) {
                            if (byteBufferRemove.remaining() == 0 && this.buffered.size() > 0) {
                                byteBufferRemove = this.buffered.remove();
                            }
                            int iRemaining = byteBufferRemove.remaining();
                            int iRemaining2 = AsyncSSLSocketWrapper.this.pending.remaining();
                            ByteBuffer byteBufferAllocate = this.allocator.allocate();
                            SSLEngineResult sSLEngineResultUnwrap = AsyncSSLSocketWrapper.this.engine.unwrap(byteBufferRemove, byteBufferAllocate);
                            AsyncSSLSocketWrapper asyncSSLSocketWrapper = AsyncSSLSocketWrapper.this;
                            asyncSSLSocketWrapper.addToPending(asyncSSLSocketWrapper.pending, byteBufferAllocate);
                            this.allocator.track(AsyncSSLSocketWrapper.this.pending.remaining() - iRemaining2);
                            if (sSLEngineResultUnwrap.getStatus() == SSLEngineResult.Status.BUFFER_OVERFLOW) {
                                Allocator allocator = this.allocator;
                                allocator.setMinAlloc(allocator.getMinAlloc() * 2);
                            } else if (sSLEngineResultUnwrap.getStatus() != SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                                AsyncSSLSocketWrapper.this.handleHandshakeStatus(sSLEngineResultUnwrap.getHandshakeStatus());
                                if (byteBufferRemove.remaining() != iRemaining && iRemaining2 == AsyncSSLSocketWrapper.this.pending.remaining()) {
                                    this.buffered.addFirst(byteBufferRemove);
                                    break;
                                }
                            } else {
                                this.buffered.addFirst(byteBufferRemove);
                                if (this.buffered.size() <= 1) {
                                    break;
                                }
                                this.buffered.addFirst(this.buffered.getAll());
                                byteBufferRemove = ByteBufferList.EMPTY_BYTEBUFFER;
                            }
                            iRemaining = -1;
                            AsyncSSLSocketWrapper.this.handleHandshakeStatus(sSLEngineResultUnwrap.getHandshakeStatus());
                            if (byteBufferRemove.remaining() != iRemaining) {
                            }
                        }
                        AsyncSSLSocketWrapper.this.onDataAvailable();
                    } catch (SSLException e) {
                        e.printStackTrace();
                        AsyncSSLSocketWrapper.this.report(e);
                    }
                } finally {
                    AsyncSSLSocketWrapper.this.mUnwrapping = false;
                }
            }
        };
        this.dataCallback = dataCallback;
        this.writeList = new ByteBufferList();
        this.mSocket = asyncSocket;
        this.hostnameVerifier = hostnameVerifier;
        this.clientMode = z;
        this.trustManagers = trustManagerArr;
        this.engine = sSLEngine;
        this.mHost = str;
        this.mPort = i;
        sSLEngine.setUseClientMode(z);
        BufferedDataSink bufferedDataSink = new BufferedDataSink(asyncSocket);
        this.mSink = bufferedDataSink;
        bufferedDataSink.setWriteableCallback(new WritableCallback() { // from class: com.koushikdutta.async.AsyncSSLSocketWrapper.3
            @Override // com.koushikdutta.async.callback.WritableCallback
            public void onWriteable() {
                if (AsyncSSLSocketWrapper.this.mWriteableCallback != null) {
                    AsyncSSLSocketWrapper.this.mWriteableCallback.onWriteable();
                }
            }
        });
        this.mSocket.setEndCallback(new CompletedCallback() { // from class: com.koushikdutta.async.AsyncSSLSocketWrapper.4
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (AsyncSSLSocketWrapper.this.mEnded) {
                    return;
                }
                AsyncSSLSocketWrapper.this.mEnded = true;
                AsyncSSLSocketWrapper.this.mEndException = exc;
                if (AsyncSSLSocketWrapper.this.pending.hasRemaining() || AsyncSSLSocketWrapper.this.mEndCallback == null) {
                    return;
                }
                AsyncSSLSocketWrapper.this.mEndCallback.onCompleted(exc);
            }
        });
        this.mSocket.setDataCallback(dataCallback);
    }

    public void onDataAvailable() {
        CompletedCallback completedCallback;
        Util.emitAllData(this, this.pending);
        if (!this.mEnded || this.pending.hasRemaining() || (completedCallback = this.mEndCallback) == null) {
            return;
        }
        completedCallback.onCompleted(this.mEndException);
    }

    @Override // com.koushikdutta.async.AsyncSSLSocket
    public SSLEngine getSSLEngine() {
        return this.engine;
    }

    void addToPending(ByteBufferList byteBufferList, ByteBuffer byteBuffer) {
        byteBuffer.flip();
        if (byteBuffer.hasRemaining()) {
            byteBufferList.add(byteBuffer);
        } else {
            ByteBufferList.reclaim(byteBuffer);
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void end() {
        this.mSocket.end();
    }

    public String getHost() {
        return this.mHost;
    }

    public int getPort() {
        return this.mPort;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHandshakeStatus(SSLEngineResult.HandshakeStatus handshakeStatus) {
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_TASK) {
            this.engine.getDelegatedTask().run();
        }
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
            write(this.writeList);
        }
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
            this.dataCallback.onDataAvailable(this, new ByteBufferList());
        }
        try {
            try {
                if (this.finishedHandshake) {
                    return;
                }
                if (this.engine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING || this.engine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    if (this.clientMode) {
                        TrustManager[] trustManagers = this.trustManagers;
                        if (trustManagers == null) {
                            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                            trustManagerFactory.init((KeyStore) null);
                            trustManagers = trustManagerFactory.getTrustManagers();
                        }
                        boolean z = false;
                        Throwable e = null;
                        for (TrustManager trustManager : trustManagers) {
                            try {
                                X509Certificate[] x509CertificateArr = (X509Certificate[]) this.engine.getSession().getPeerCertificates();
                                this.peerCertificates = x509CertificateArr;
                                ((X509TrustManager) trustManager).checkServerTrusted(x509CertificateArr, "SSL");
                                String str = this.mHost;
                                if (str != null) {
                                    HostnameVerifier hostnameVerifier = this.hostnameVerifier;
                                    if (hostnameVerifier == null) {
                                        new StrictHostnameVerifier().verify(this.mHost, StrictHostnameVerifier.getCNs(this.peerCertificates[0]), StrictHostnameVerifier.getDNSSubjectAlts(this.peerCertificates[0]));
                                    } else if (!hostnameVerifier.verify(str, this.engine.getSession())) {
                                        throw new SSLException("hostname <" + this.mHost + "> has been denied");
                                    }
                                }
                                z = true;
                                break;
                            } catch (GeneralSecurityException | SSLException e2) {
                                e = e2;
                            }
                        }
                        this.finishedHandshake = true;
                        if (!z) {
                            AsyncSSLException asyncSSLException = new AsyncSSLException(e);
                            report(asyncSSLException);
                            if (!asyncSSLException.getIgnore()) {
                                throw asyncSSLException;
                            }
                        }
                    } else {
                        this.finishedHandshake = true;
                    }
                    this.handshakeCallback.onHandshakeCompleted(null, this);
                    this.handshakeCallback = null;
                    this.mSocket.setClosedCallback(null);
                    getServer().post(new Runnable() { // from class: com.koushikdutta.async.AsyncSSLSocketWrapper.6
                        @Override // java.lang.Runnable
                        public void run() {
                            if (AsyncSSLSocketWrapper.this.mWriteableCallback != null) {
                                AsyncSSLSocketWrapper.this.mWriteableCallback.onWriteable();
                            }
                        }
                    });
                    onDataAvailable();
                }
            } catch (GeneralSecurityException e3) {
                report(e3);
            }
        } catch (AsyncSSLException e4) {
            report(e4);
        } catch (NoSuchAlgorithmException e5) {
            throw new RuntimeException(e5);
        }
    }

    int calculateAlloc(int i) {
        int i2 = (i * 3) / 2;
        if (i2 == 0) {
            return 8192;
        }
        return i2;
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(ByteBufferList byteBufferList) {
        if (!this.mWrapping && this.mSink.remaining() <= 0) {
            this.mWrapping = true;
            ByteBuffer byteBufferObtain = ByteBufferList.obtain(calculateAlloc(byteBufferList.remaining()));
            SSLEngineResult sSLEngineResultWrap = null;
            do {
                if (!this.finishedHandshake || byteBufferList.remaining() != 0) {
                    int iRemaining = byteBufferList.remaining();
                    try {
                        ByteBuffer[] allArray = byteBufferList.getAllArray();
                        sSLEngineResultWrap = this.engine.wrap(allArray, byteBufferObtain);
                        byteBufferList.addAll(allArray);
                        byteBufferObtain.flip();
                        this.writeList.add(byteBufferObtain);
                        if (this.writeList.remaining() > 0) {
                            this.mSink.write(this.writeList);
                        }
                        int iCapacity = byteBufferObtain.capacity();
                        try {
                            if (sSLEngineResultWrap.getStatus() == SSLEngineResult.Status.BUFFER_OVERFLOW) {
                                byteBufferObtain = ByteBufferList.obtain(iCapacity * 2);
                                iRemaining = -1;
                            } else {
                                byteBufferObtain = ByteBufferList.obtain(calculateAlloc(byteBufferList.remaining()));
                                handleHandshakeStatus(sSLEngineResultWrap.getHandshakeStatus());
                            }
                        } catch (SSLException e) {
                            e = e;
                            byteBufferObtain = null;
                            report(e);
                        }
                    } catch (SSLException e2) {
                        e = e2;
                    }
                    if (iRemaining == byteBufferList.remaining() && (sSLEngineResultWrap == null || sSLEngineResultWrap.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NEED_WRAP)) {
                        break;
                    }
                } else {
                    break;
                }
            } while (this.mSink.remaining() == 0);
            this.mWrapping = false;
            ByteBufferList.reclaim(byteBufferObtain);
        }
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        this.mWriteableCallback = writableCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        return this.mWriteableCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void report(Exception exc) {
        HandshakeCallback handshakeCallback = this.handshakeCallback;
        if (handshakeCallback != null) {
            this.handshakeCallback = null;
            this.mSocket.setDataCallback(new DataCallback.NullDataCallback());
            this.mSocket.end();
            this.mSocket.setClosedCallback(null);
            this.mSocket.close();
            handshakeCallback.onHandshakeCompleted(exc, null);
            return;
        }
        CompletedCallback endCallback = getEndCallback();
        if (endCallback != null) {
            endCallback.onCompleted(exc);
        }
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.mDataCallback = dataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return this.mSocket.isChunked();
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        return this.mSocket.isOpen();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void close() {
        this.mSocket.close();
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.mSocket.setClosedCallback(completedCallback);
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        return this.mSocket.getClosedCallback();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setEndCallback(CompletedCallback completedCallback) {
        this.mEndCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public CompletedCallback getEndCallback() {
        return this.mEndCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void pause() {
        this.mSocket.pause();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void resume() {
        this.mSocket.resume();
        onDataAvailable();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.mSocket.isPaused();
    }

    @Override // com.koushikdutta.async.AsyncSocket, com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    @Override // com.koushikdutta.async.wrapper.AsyncSocketWrapper
    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    @Override // com.koushikdutta.async.wrapper.DataEmitterWrapper
    public DataEmitter getDataEmitter() {
        return this.mSocket;
    }

    @Override // com.koushikdutta.async.AsyncSSLSocket
    public X509Certificate[] getPeerCertificates() {
        return this.peerCertificates;
    }
}
