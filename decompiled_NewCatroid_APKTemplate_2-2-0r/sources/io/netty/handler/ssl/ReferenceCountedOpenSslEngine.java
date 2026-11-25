package io.netty.handler.ssl;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.SSL;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes11.dex */
public class ReferenceCountedOpenSslEngine extends SSLEngine implements ReferenceCounted, ApplicationProtocolAccessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV2 = 0;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV3 = 1;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1 = 2;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_1 = 3;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_2 = 4;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_3 = 5;
    private Object algorithmConstraints;
    final ByteBufAllocator alloc;
    private final OpenSslApplicationProtocolNegotiator apn;
    private volatile String applicationProtocol;
    private volatile ClientAuth clientAuth;
    private final boolean clientMode;
    private volatile boolean destroyed;
    private final boolean enableOcsp;
    private String endPointIdentificationAlgorithm;
    private final OpenSslEngineMap engineMap;
    private Throwable handshakeException;
    private HandshakeState handshakeState;
    private boolean isInboundDone;
    final boolean jdkCompatibilityMode;
    private volatile long lastAccessed;
    private final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
    private volatile Certificate[] localCertificateChain;
    private volatile Collection<?> matchers;
    private int maxWrapBufferSize;
    private int maxWrapOverhead;
    private volatile boolean needTask;
    private long networkBIO;
    private boolean outboundClosed;
    private final ReferenceCountedOpenSslContext parentContext;
    private boolean receivedShutdown;
    private final AbstractReferenceCounted refCnt;
    private final OpenSslSession session;
    private final ByteBuffer[] singleDstBuffer;
    private final ByteBuffer[] singleSrcBuffer;
    private List<String> sniHostNames;
    private long ssl;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ReferenceCountedOpenSslEngine.class);
    private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
    private static final int[] OPENSSL_OP_NO_PROTOCOLS = {SSL.SSL_OP_NO_SSLv2, SSL.SSL_OP_NO_SSLv3, SSL.SSL_OP_NO_TLSv1, SSL.SSL_OP_NO_TLSv1_1, SSL.SSL_OP_NO_TLSv1_2, SSL.SSL_OP_NO_TLSv1_3};
    static final int MAX_PLAINTEXT_LENGTH = SSL.SSL_MAX_PLAINTEXT_LENGTH;
    private static final int MAX_RECORD_SIZE = SSL.SSL_MAX_RECORD_LENGTH;
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);

    private enum HandshakeState {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED
    }

    @Override // javax.net.ssl.SSLEngine
    public final boolean getEnableSessionCreation() {
        return false;
    }

    ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, ByteBufAllocator byteBufAllocator, String str, int i, boolean z, boolean z2) {
        super(str, i);
        this.handshakeState = HandshakeState.NOT_STARTED;
        this.refCnt = new AbstractReferenceCounted() { // from class: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // io.netty.util.ReferenceCounted
            public ReferenceCounted touch(Object obj) {
                if (ReferenceCountedOpenSslEngine.this.leak != null) {
                    ReferenceCountedOpenSslEngine.this.leak.record(obj);
                }
                return ReferenceCountedOpenSslEngine.this;
            }

            @Override // io.netty.util.AbstractReferenceCounted
            protected void deallocate() {
                ReferenceCountedOpenSslEngine.this.shutdown();
                if (ReferenceCountedOpenSslEngine.this.leak != null) {
                    ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this);
                }
                ReferenceCountedOpenSslEngine.this.parentContext.release();
            }
        };
        this.clientAuth = ClientAuth.NONE;
        this.lastAccessed = -1L;
        this.singleSrcBuffer = new ByteBuffer[1];
        this.singleDstBuffer = new ByteBuffer[1];
        OpenSsl.ensureAvailability();
        this.alloc = (ByteBufAllocator) ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        this.apn = (OpenSslApplicationProtocolNegotiator) referenceCountedOpenSslContext.applicationProtocolNegotiator();
        boolean zIsClient = referenceCountedOpenSslContext.isClient();
        this.clientMode = zIsClient;
        if (PlatformDependent.javaVersion() >= 7) {
            this.session = new ExtendedOpenSslSession(new DefaultOpenSslSession(referenceCountedOpenSslContext.sessionContext())) { // from class: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.2
                private String[] peerSupportedSignatureAlgorithms;
                private List requestedServerNames;

                @Override // io.netty.handler.ssl.ExtendedOpenSslSession, javax.net.ssl.ExtendedSSLSession
                public List getRequestedServerNames() {
                    List list;
                    if (ReferenceCountedOpenSslEngine.this.clientMode) {
                        return Java8SslUtils.getSniHostNames((List<String>) ReferenceCountedOpenSslEngine.this.sniHostNames);
                    }
                    synchronized (ReferenceCountedOpenSslEngine.this) {
                        if (this.requestedServerNames == null) {
                            if (ReferenceCountedOpenSslEngine.this.isDestroyed() || SSL.getSniHostname(ReferenceCountedOpenSslEngine.this.ssl) == null) {
                                this.requestedServerNames = Collections.emptyList();
                            } else {
                                this.requestedServerNames = Java8SslUtils.getSniHostName(SSL.getSniHostname(ReferenceCountedOpenSslEngine.this.ssl).getBytes(CharsetUtil.UTF_8));
                            }
                        }
                        list = this.requestedServerNames;
                    }
                    return list;
                }

                @Override // javax.net.ssl.ExtendedSSLSession
                public String[] getPeerSupportedSignatureAlgorithms() {
                    String[] strArr;
                    String[] sigAlgs;
                    synchronized (ReferenceCountedOpenSslEngine.this) {
                        if (this.peerSupportedSignatureAlgorithms == null) {
                            if (ReferenceCountedOpenSslEngine.this.isDestroyed() || (sigAlgs = SSL.getSigAlgs(ReferenceCountedOpenSslEngine.this.ssl)) == null) {
                                this.peerSupportedSignatureAlgorithms = EmptyArrays.EMPTY_STRINGS;
                            } else {
                                LinkedHashSet linkedHashSet = new LinkedHashSet(sigAlgs.length);
                                for (String str2 : sigAlgs) {
                                    String javaName = SignatureAlgorithmConverter.toJavaName(str2);
                                    if (javaName != null) {
                                        linkedHashSet.add(javaName);
                                    }
                                }
                                this.peerSupportedSignatureAlgorithms = (String[]) linkedHashSet.toArray(new String[0]);
                            }
                        }
                        strArr = (String[]) this.peerSupportedSignatureAlgorithms.clone();
                    }
                    return strArr;
                }

                @Override // io.netty.handler.ssl.ExtendedOpenSslSession
                public List<byte[]> getStatusResponses() {
                    byte[] ocspResponse = null;
                    if (ReferenceCountedOpenSslEngine.this.enableOcsp && ReferenceCountedOpenSslEngine.this.clientMode) {
                        synchronized (ReferenceCountedOpenSslEngine.this) {
                            ocspResponse = ReferenceCountedOpenSslEngine.this.isDestroyed() ? null : SSL.getOcspResponse(ReferenceCountedOpenSslEngine.this.ssl);
                        }
                    }
                    return ocspResponse == null ? Collections.emptyList() : Collections.singletonList(ocspResponse);
                }
            };
        } else {
            this.session = new DefaultOpenSslSession(referenceCountedOpenSslContext.sessionContext());
        }
        this.engineMap = referenceCountedOpenSslContext.engineMap;
        boolean z3 = referenceCountedOpenSslContext.enableOcsp;
        this.enableOcsp = z3;
        this.localCertificateChain = referenceCountedOpenSslContext.keyCertChain;
        this.jdkCompatibilityMode = z;
        Lock lock = referenceCountedOpenSslContext.ctxLock.readLock();
        lock.lock();
        try {
            long jNewSSL = SSL.newSSL(referenceCountedOpenSslContext.ctx, true ^ referenceCountedOpenSslContext.isClient());
            lock.unlock();
            synchronized (this) {
                this.ssl = jNewSSL;
                try {
                    this.networkBIO = SSL.bioNewByteBuffer(jNewSSL, referenceCountedOpenSslContext.getBioNonApplicationBufferSize());
                    setClientAuth(zIsClient ? ClientAuth.NONE : referenceCountedOpenSslContext.clientAuth);
                    if (referenceCountedOpenSslContext.protocols != null) {
                        setEnabledProtocols(referenceCountedOpenSslContext.protocols);
                    }
                    if (zIsClient && SslUtils.isValidHostNameForSNI(str)) {
                        SSL.setTlsExtHostName(this.ssl, str);
                        this.sniHostNames = Collections.singletonList(str);
                    }
                    if (z3) {
                        SSL.enableOcsp(this.ssl);
                    }
                    if (!z) {
                        long j = this.ssl;
                        SSL.setMode(j, SSL.getMode(j) | SSL.SSL_MODE_ENABLE_PARTIAL_WRITE);
                    }
                    calculateMaxWrapOverhead();
                } catch (Throwable th) {
                    shutdown();
                    PlatformDependent.throwException(th);
                }
            }
            this.parentContext = referenceCountedOpenSslContext;
            referenceCountedOpenSslContext.retain();
            this.leak = z2 ? leakDetector.track(this) : null;
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    final synchronized String[] authMethods() {
        if (isDestroyed()) {
            return EmptyArrays.EMPTY_STRINGS;
        }
        return SSL.authenticationMethods(this.ssl);
    }

    final boolean setKeyMaterial(OpenSslKeyMaterial openSslKeyMaterial) throws Exception {
        synchronized (this) {
            if (isDestroyed()) {
                return false;
            }
            SSL.setKeyMaterial(this.ssl, openSslKeyMaterial.certificateChainAddress(), openSslKeyMaterial.privateKeyAddress());
            this.localCertificateChain = openSslKeyMaterial.certificateChain();
            return true;
        }
    }

    final synchronized SecretKeySpec masterKey() {
        if (isDestroyed()) {
            return null;
        }
        return new SecretKeySpec(SSL.getMasterKey(this.ssl), "AES");
    }

    public void setOcspResponse(byte[] bArr) {
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        }
        if (this.clientMode) {
            throw new IllegalStateException("Not a server SSLEngine");
        }
        synchronized (this) {
            if (!isDestroyed()) {
                SSL.setOcspResponse(this.ssl, bArr);
            }
        }
    }

    public byte[] getOcspResponse() {
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        }
        if (!this.clientMode) {
            throw new IllegalStateException("Not a client SSLEngine");
        }
        synchronized (this) {
            if (isDestroyed()) {
                return EmptyArrays.EMPTY_BYTES;
            }
            return SSL.getOcspResponse(this.ssl);
        }
    }

    @Override // io.netty.util.ReferenceCounted
    public final int refCnt() {
        return this.refCnt.refCnt();
    }

    @Override // io.netty.util.ReferenceCounted
    public final ReferenceCounted retain() {
        this.refCnt.retain();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public final ReferenceCounted retain(int i) {
        this.refCnt.retain(i);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public final ReferenceCounted touch() {
        this.refCnt.touch();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public final ReferenceCounted touch(Object obj) {
        this.refCnt.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public final boolean release() {
        return this.refCnt.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public final boolean release(int i) {
        return this.refCnt.release(i);
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLSession getHandshakeSession() {
        int i = AnonymousClass4.$SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[this.handshakeState.ordinal()];
        if (i == 1 || i == 2) {
            return null;
        }
        return this.session;
    }

    public final synchronized long sslPointer() {
        return this.ssl;
    }

    public final synchronized void shutdown() {
        if (!this.destroyed) {
            this.destroyed = true;
            this.engineMap.remove(this.ssl);
            SSL.freeSSL(this.ssl);
            this.networkBIO = 0L;
            this.ssl = 0L;
            this.outboundClosed = true;
            this.isInboundDone = true;
        }
        SSL.clearError();
    }

    private int writePlaintextData(ByteBuffer byteBuffer, int i) {
        int iWriteToSSL;
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit();
        if (byteBuffer.isDirect()) {
            iWriteToSSL = SSL.writeToSSL(this.ssl, bufferAddress(byteBuffer) + ((long) iPosition), i);
            if (iWriteToSSL > 0) {
                byteBuffer.position(iPosition + iWriteToSSL);
            }
        } else {
            ByteBuf byteBufDirectBuffer = this.alloc.directBuffer(i);
            try {
                byteBuffer.limit(iPosition + i);
                byteBufDirectBuffer.setBytes(0, byteBuffer);
                byteBuffer.limit(iLimit);
                iWriteToSSL = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(byteBufDirectBuffer), i);
                if (iWriteToSSL > 0) {
                    byteBuffer.position(iPosition + iWriteToSSL);
                } else {
                    byteBuffer.position(iPosition);
                }
            } finally {
                byteBufDirectBuffer.release();
            }
        }
        return iWriteToSSL;
    }

    private ByteBuf writeEncryptedData(ByteBuffer byteBuffer, int i) throws Throwable {
        int iPosition = byteBuffer.position();
        if (byteBuffer.isDirect()) {
            SSL.bioSetByteBuffer(this.networkBIO, bufferAddress(byteBuffer) + ((long) iPosition), i, false);
            return null;
        }
        ByteBuf byteBufDirectBuffer = this.alloc.directBuffer(i);
        try {
            int iLimit = byteBuffer.limit();
            byteBuffer.limit(iPosition + i);
            byteBufDirectBuffer.writeBytes(byteBuffer);
            byteBuffer.position(iPosition);
            byteBuffer.limit(iLimit);
            SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(byteBufDirectBuffer), i, false);
            return byteBufDirectBuffer;
        } catch (Throwable th) {
            byteBufDirectBuffer.release();
            PlatformDependent.throwException(th);
            return null;
        }
    }

    private int readPlaintextData(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        if (byteBuffer.isDirect()) {
            int fromSSL = SSL.readFromSSL(this.ssl, bufferAddress(byteBuffer) + ((long) iPosition), byteBuffer.limit() - iPosition);
            if (fromSSL <= 0) {
                return fromSSL;
            }
            byteBuffer.position(iPosition + fromSSL);
            return fromSSL;
        }
        int iLimit = byteBuffer.limit();
        int iMin = Math.min(maxEncryptedPacketLength0(), iLimit - iPosition);
        ByteBuf byteBufDirectBuffer = this.alloc.directBuffer(iMin);
        try {
            int fromSSL2 = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(byteBufDirectBuffer), iMin);
            if (fromSSL2 > 0) {
                byteBuffer.limit(iPosition + fromSSL2);
                byteBufDirectBuffer.getBytes(byteBufDirectBuffer.readerIndex(), byteBuffer);
                byteBuffer.limit(iLimit);
            }
            return fromSSL2;
        } finally {
            byteBufDirectBuffer.release();
        }
    }

    final synchronized int maxWrapOverhead() {
        return this.maxWrapOverhead;
    }

    final synchronized int maxEncryptedPacketLength() {
        return maxEncryptedPacketLength0();
    }

    final int maxEncryptedPacketLength0() {
        return this.maxWrapOverhead + MAX_PLAINTEXT_LENGTH;
    }

    final int calculateMaxLengthForWrap(int i, int i2) {
        return (int) Math.min(this.maxWrapBufferSize, ((long) i) + (((long) this.maxWrapOverhead) * ((long) i2)));
    }

    final synchronized int sslPending() {
        return sslPending0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateMaxWrapOverhead() {
        this.maxWrapOverhead = SSL.getMaxWrapOverhead(this.ssl);
        this.maxWrapBufferSize = this.jdkCompatibilityMode ? maxEncryptedPacketLength0() : maxEncryptedPacketLength0() << 4;
    }

    private int sslPending0() {
        if (this.handshakeState != HandshakeState.FINISHED) {
            return 0;
        }
        return SSL.sslPending(this.ssl);
    }

    private boolean isBytesAvailableEnoughForWrap(int i, int i2, int i3) {
        return ((long) i) - (((long) this.maxWrapOverhead) * ((long) i3)) >= ((long) i2);
    }

    /* JADX WARN: Code duplicated, block: B:277:0x04c9 A[Catch: all -> 0x04e1, TryCatch #1 {, blocks: (B:11:0x0013, B:13:0x0019, B:15:0x001f, B:18:0x0026, B:20:0x002b, B:19:0x0029, B:32:0x0083, B:34:0x008a, B:36:0x00a0, B:35:0x0092, B:41:0x00b0, B:43:0x00b7, B:45:0x00ce, B:44:0x00c0, B:50:0x00dc, B:52:0x00e3, B:54:0x00fa, B:53:0x00ec, B:59:0x0109, B:61:0x0110, B:63:0x0127, B:62:0x0119, B:275:0x04c2, B:277:0x04c9, B:279:0x04e0, B:278:0x04d8, B:79:0x0156, B:81:0x015d, B:83:0x0174, B:82:0x0166, B:86:0x017e, B:88:0x0185, B:90:0x019c, B:89:0x018e, B:95:0x01b2, B:97:0x01b9, B:99:0x01d0, B:98:0x01c2, B:109:0x01f2, B:111:0x01f9, B:113:0x0210, B:112:0x0202, B:121:0x0221, B:123:0x0228, B:125:0x023f, B:124:0x0231, B:131:0x024f, B:133:0x0256, B:135:0x026d, B:134:0x025f, B:160:0x02ca, B:162:0x02d1, B:164:0x02e8, B:163:0x02da, B:196:0x0367, B:198:0x036e, B:200:0x0385, B:199:0x0377, B:216:0x03c3, B:218:0x03ca, B:220:0x03e1, B:219:0x03d3, B:223:0x03e9, B:225:0x03f0, B:227:0x0407, B:226:0x03f9, B:232:0x0413, B:234:0x041a, B:236:0x0431, B:235:0x0423, B:241:0x043d, B:243:0x0444, B:245:0x045b, B:244:0x044d, B:257:0x0477, B:259:0x047e, B:261:0x0495, B:260:0x0487, B:177:0x0320, B:179:0x0327, B:181:0x033e, B:180:0x0330, B:264:0x049b, B:266:0x04a2, B:268:0x04b9, B:267:0x04ab), top: B:292:0x0013 }] */
    /* JADX WARN: Code duplicated, block: B:278:0x04d8 A[Catch: all -> 0x04e1, TryCatch #1 {, blocks: (B:11:0x0013, B:13:0x0019, B:15:0x001f, B:18:0x0026, B:20:0x002b, B:19:0x0029, B:32:0x0083, B:34:0x008a, B:36:0x00a0, B:35:0x0092, B:41:0x00b0, B:43:0x00b7, B:45:0x00ce, B:44:0x00c0, B:50:0x00dc, B:52:0x00e3, B:54:0x00fa, B:53:0x00ec, B:59:0x0109, B:61:0x0110, B:63:0x0127, B:62:0x0119, B:275:0x04c2, B:277:0x04c9, B:279:0x04e0, B:278:0x04d8, B:79:0x0156, B:81:0x015d, B:83:0x0174, B:82:0x0166, B:86:0x017e, B:88:0x0185, B:90:0x019c, B:89:0x018e, B:95:0x01b2, B:97:0x01b9, B:99:0x01d0, B:98:0x01c2, B:109:0x01f2, B:111:0x01f9, B:113:0x0210, B:112:0x0202, B:121:0x0221, B:123:0x0228, B:125:0x023f, B:124:0x0231, B:131:0x024f, B:133:0x0256, B:135:0x026d, B:134:0x025f, B:160:0x02ca, B:162:0x02d1, B:164:0x02e8, B:163:0x02da, B:196:0x0367, B:198:0x036e, B:200:0x0385, B:199:0x0377, B:216:0x03c3, B:218:0x03ca, B:220:0x03e1, B:219:0x03d3, B:223:0x03e9, B:225:0x03f0, B:227:0x0407, B:226:0x03f9, B:232:0x0413, B:234:0x041a, B:236:0x0431, B:235:0x0423, B:241:0x043d, B:243:0x0444, B:245:0x045b, B:244:0x044d, B:257:0x0477, B:259:0x047e, B:261:0x0495, B:260:0x0487, B:177:0x0320, B:179:0x0327, B:181:0x033e, B:180:0x0330, B:264:0x049b, B:266:0x04a2, B:268:0x04b9, B:267:0x04ab), top: B:292:0x0013 }] */
    @Override // javax.net.ssl.SSLEngine
    public final SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer) throws SSLException {
        int i3;
        int i4;
        int iWritePlaintextData;
        if (byteBufferArr == null) {
            throw new IllegalArgumentException("srcs is null");
        }
        if (byteBuffer == null) {
            throw new IllegalArgumentException("dst is null");
        }
        if (i >= byteBufferArr.length || (i3 = i + i2) > byteBufferArr.length) {
            throw new IndexOutOfBoundsException("offset: " + i + ", length: " + i2 + " (expected: offset <= offset + length <= srcs.length (" + byteBufferArr.length + "))");
        }
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        synchronized (this) {
            if (isOutboundDone()) {
                return (isInboundDone() || isDestroyed()) ? CLOSED_NOT_HANDSHAKING : NEED_UNWRAP_CLOSED;
            }
            int i5 = 0;
            int i6 = 0;
            ByteBuf byteBufDirectBuffer = null;
            byteBufDirectBuffer = null;
            try {
                if (byteBuffer.isDirect()) {
                    SSL.bioSetByteBuffer(this.networkBIO, bufferAddress(byteBuffer) + ((long) byteBuffer.position()), byteBuffer.remaining(), true);
                } else {
                    byteBufDirectBuffer = this.alloc.directBuffer(byteBuffer.remaining());
                    SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(byteBufDirectBuffer), byteBufDirectBuffer.writableBytes(), true);
                }
                int iBioLengthByteBuffer = SSL.bioLengthByteBuffer(this.networkBIO);
                try {
                    try {
                        if (this.outboundClosed) {
                            if (!isBytesAvailableEnoughForWrap(byteBuffer.remaining(), 2, 1)) {
                                SSLEngineResult sSLEngineResult = new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), 0, 0);
                                SSL.bioClearByteBuffer(this.networkBIO);
                                if (byteBufDirectBuffer == null) {
                                    byteBuffer.position(byteBuffer.position());
                                } else {
                                    byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), 0));
                                    byteBufDirectBuffer.release();
                                }
                                return sSLEngineResult;
                            }
                            int iBioFlushByteBuffer = SSL.bioFlushByteBuffer(this.networkBIO);
                            if (iBioFlushByteBuffer <= 0) {
                                SSLEngineResult sSLEngineResultNewResultMayFinishHandshake = newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
                                SSL.bioClearByteBuffer(this.networkBIO);
                                if (byteBufDirectBuffer == null) {
                                    byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer);
                                } else {
                                    byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer));
                                    byteBufDirectBuffer.release();
                                }
                                return sSLEngineResultNewResultMayFinishHandshake;
                            }
                            if (!doSSLShutdown()) {
                                SSLEngineResult sSLEngineResultNewResultMayFinishHandshake2 = newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, iBioFlushByteBuffer);
                                SSL.bioClearByteBuffer(this.networkBIO);
                                if (byteBufDirectBuffer == null) {
                                    byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer);
                                } else {
                                    byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer));
                                    byteBufDirectBuffer.release();
                                }
                                return sSLEngineResultNewResultMayFinishHandshake2;
                            }
                            int iBioLengthByteBuffer2 = iBioLengthByteBuffer - SSL.bioLengthByteBuffer(this.networkBIO);
                            SSLEngineResult sSLEngineResultNewResultMayFinishHandshake3 = newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, iBioLengthByteBuffer2);
                            SSL.bioClearByteBuffer(this.networkBIO);
                            if (byteBufDirectBuffer == null) {
                                byteBuffer.position(byteBuffer.position() + iBioLengthByteBuffer2);
                            } else {
                                byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioLengthByteBuffer2));
                                byteBufDirectBuffer.release();
                            }
                            return sSLEngineResultNewResultMayFinishHandshake3;
                        }
                        SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                        if (this.handshakeState != HandshakeState.FINISHED) {
                            if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                                this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                            }
                            int iBioFlushByteBuffer2 = SSL.bioFlushByteBuffer(this.networkBIO);
                            try {
                                if (this.handshakeException != null) {
                                    if (iBioFlushByteBuffer2 > 0) {
                                        SSLEngineResult sSLEngineResultNewResult = newResult(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, iBioFlushByteBuffer2);
                                        SSL.bioClearByteBuffer(this.networkBIO);
                                        if (byteBufDirectBuffer == null) {
                                            byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer2);
                                        } else {
                                            byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer2));
                                            byteBufDirectBuffer.release();
                                        }
                                        return sSLEngineResultNewResult;
                                    }
                                    SSLEngineResult sSLEngineResultNewResult2 = newResult(handshakeException(), 0, 0);
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer == null) {
                                        byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer2);
                                    } else {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer2));
                                        byteBufDirectBuffer.release();
                                    }
                                    return sSLEngineResultNewResult2;
                                }
                                SSLEngineResult.HandshakeStatus handshakeStatusHandshake = handshake();
                                int iBioLengthByteBuffer3 = iBioLengthByteBuffer - SSL.bioLengthByteBuffer(this.networkBIO);
                                if (handshakeStatusHandshake == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                                    SSLEngineResult sSLEngineResultNewResult3 = newResult(handshakeStatusHandshake, 0, iBioLengthByteBuffer3);
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer == null) {
                                        byteBuffer.position(byteBuffer.position() + iBioLengthByteBuffer3);
                                    } else {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioLengthByteBuffer3));
                                        byteBufDirectBuffer.release();
                                    }
                                    return sSLEngineResultNewResult3;
                                }
                                if (iBioLengthByteBuffer3 > 0) {
                                    SSLEngineResult sSLEngineResultNewResult4 = newResult(mayFinishHandshake(handshakeStatusHandshake != SSLEngineResult.HandshakeStatus.FINISHED ? iBioLengthByteBuffer3 == iBioLengthByteBuffer ? SSLEngineResult.HandshakeStatus.NEED_WRAP : getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)) : SSLEngineResult.HandshakeStatus.FINISHED), 0, iBioLengthByteBuffer3);
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer == null) {
                                        byteBuffer.position(byteBuffer.position() + iBioLengthByteBuffer3);
                                    } else {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioLengthByteBuffer3));
                                        byteBufDirectBuffer.release();
                                    }
                                    return sSLEngineResultNewResult4;
                                }
                                if (handshakeStatusHandshake == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                                    SSLEngineResult sSLEngineResult2 = isOutboundDone() ? NEED_UNWRAP_CLOSED : NEED_UNWRAP_OK;
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer == null) {
                                        byteBuffer.position(byteBuffer.position() + iBioLengthByteBuffer3);
                                    } else {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioLengthByteBuffer3));
                                        byteBufDirectBuffer.release();
                                    }
                                    return sSLEngineResult2;
                                }
                                if (this.outboundClosed) {
                                    int iBioFlushByteBuffer3 = SSL.bioFlushByteBuffer(this.networkBIO);
                                    SSLEngineResult sSLEngineResultNewResultMayFinishHandshake4 = newResultMayFinishHandshake(handshakeStatusHandshake, 0, iBioFlushByteBuffer3);
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer == null) {
                                        byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer3);
                                    } else {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer3));
                                        byteBufDirectBuffer.release();
                                    }
                                    return sSLEngineResultNewResultMayFinishHandshake4;
                                }
                                i4 = iBioLengthByteBuffer3;
                                handshakeStatus = handshakeStatusHandshake;
                            } catch (Throwable th) {
                                th = th;
                                i5 = iBioFlushByteBuffer2;
                                SSL.bioClearByteBuffer(this.networkBIO);
                                if (byteBufDirectBuffer != null) {
                                    byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i5));
                                    byteBufDirectBuffer.release();
                                } else {
                                    byteBuffer.position(byteBuffer.position() + i5);
                                }
                                throw th;
                            }
                        } else {
                            i4 = 0;
                        }
                        try {
                            if (this.jdkCompatibilityMode) {
                                int iRemaining = 0;
                                for (int i7 = i; i7 < i3; i7++) {
                                    ByteBuffer byteBuffer2 = byteBufferArr[i7];
                                    if (byteBuffer2 == null) {
                                        throw new IllegalArgumentException("srcs[" + i7 + "] is null");
                                    }
                                    int i8 = MAX_PLAINTEXT_LENGTH;
                                    if (iRemaining != i8 && ((iRemaining = iRemaining + byteBuffer2.remaining()) > i8 || iRemaining < 0)) {
                                        iRemaining = i8;
                                    }
                                }
                                if (!isBytesAvailableEnoughForWrap(byteBuffer.remaining(), iRemaining, 1)) {
                                    SSLEngineResult sSLEngineResult3 = new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), 0, 0);
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer == null) {
                                        byteBuffer.position(byteBuffer.position() + i4);
                                    } else {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i4));
                                        byteBufDirectBuffer.release();
                                    }
                                    return sSLEngineResult3;
                                }
                            }
                            int iBioFlushByteBuffer4 = SSL.bioFlushByteBuffer(this.networkBIO);
                            while (i < i3) {
                                try {
                                    ByteBuffer byteBuffer3 = byteBufferArr[i];
                                    int iRemaining2 = byteBuffer3.remaining();
                                    if (iRemaining2 != 0) {
                                        if (this.jdkCompatibilityMode) {
                                            iWritePlaintextData = writePlaintextData(byteBuffer3, Math.min(iRemaining2, MAX_PLAINTEXT_LENGTH - i6));
                                        } else {
                                            int iRemaining3 = (byteBuffer.remaining() - iBioFlushByteBuffer4) - this.maxWrapOverhead;
                                            if (iRemaining3 <= 0) {
                                                SSLEngineResult sSLEngineResult4 = new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), i6, iBioFlushByteBuffer4);
                                                SSL.bioClearByteBuffer(this.networkBIO);
                                                if (byteBufDirectBuffer == null) {
                                                    byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer4);
                                                } else {
                                                    byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer4));
                                                    byteBufDirectBuffer.release();
                                                }
                                                return sSLEngineResult4;
                                            }
                                            iWritePlaintextData = writePlaintextData(byteBuffer3, Math.min(iRemaining2, iRemaining3));
                                        }
                                        int iBioLengthByteBuffer4 = SSL.bioLengthByteBuffer(this.networkBIO);
                                        int i9 = (iBioLengthByteBuffer - iBioLengthByteBuffer4) + iBioFlushByteBuffer4;
                                        if (iWritePlaintextData > 0) {
                                            i6 += iWritePlaintextData;
                                            if (!this.jdkCompatibilityMode && i9 != byteBuffer.remaining()) {
                                                iBioFlushByteBuffer4 = i9;
                                                iBioLengthByteBuffer = iBioLengthByteBuffer4;
                                            }
                                            SSLEngineResult sSLEngineResultNewResultMayFinishHandshake5 = newResultMayFinishHandshake(handshakeStatus, i6, i9);
                                            SSL.bioClearByteBuffer(this.networkBIO);
                                            if (byteBufDirectBuffer == null) {
                                                byteBuffer.position(byteBuffer.position() + i9);
                                            } else {
                                                byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i9));
                                                byteBufDirectBuffer.release();
                                            }
                                            return sSLEngineResultNewResultMayFinishHandshake5;
                                        }
                                        int error = SSL.getError(this.ssl, iWritePlaintextData);
                                        if (error == SSL.SSL_ERROR_ZERO_RETURN) {
                                            if (this.receivedShutdown) {
                                                SSLEngineResult sSLEngineResultNewResult5 = newResult(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, i6, i9);
                                                SSL.bioClearByteBuffer(this.networkBIO);
                                                if (byteBufDirectBuffer == null) {
                                                    byteBuffer.position(byteBuffer.position() + i9);
                                                } else {
                                                    byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i9));
                                                    byteBufDirectBuffer.release();
                                                }
                                                return sSLEngineResultNewResult5;
                                            }
                                            closeAll();
                                            int iBioLengthByteBuffer5 = i9 + (iBioLengthByteBuffer4 - SSL.bioLengthByteBuffer(this.networkBIO));
                                            SSLEngineResult sSLEngineResultNewResult6 = newResult(mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? iBioLengthByteBuffer5 == byteBuffer.remaining() ? SSLEngineResult.HandshakeStatus.NEED_WRAP : getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)) : SSLEngineResult.HandshakeStatus.FINISHED), i6, iBioLengthByteBuffer5);
                                            SSL.bioClearByteBuffer(this.networkBIO);
                                            if (byteBufDirectBuffer == null) {
                                                byteBuffer.position(byteBuffer.position() + iBioLengthByteBuffer5);
                                            } else {
                                                byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioLengthByteBuffer5));
                                                byteBufDirectBuffer.release();
                                            }
                                            return sSLEngineResultNewResult6;
                                        }
                                        if (error == SSL.SSL_ERROR_WANT_READ) {
                                            SSLEngineResult sSLEngineResultNewResult7 = newResult(SSLEngineResult.HandshakeStatus.NEED_UNWRAP, i6, i9);
                                            SSL.bioClearByteBuffer(this.networkBIO);
                                            if (byteBufDirectBuffer == null) {
                                                byteBuffer.position(byteBuffer.position() + i9);
                                            } else {
                                                byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i9));
                                                byteBufDirectBuffer.release();
                                            }
                                            return sSLEngineResultNewResult7;
                                        }
                                        if (error == SSL.SSL_ERROR_WANT_WRITE) {
                                            SSLEngineResult sSLEngineResultNewResult8 = newResult(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatus, i6, i9);
                                            SSL.bioClearByteBuffer(this.networkBIO);
                                            if (byteBufDirectBuffer == null) {
                                                byteBuffer.position(byteBuffer.position() + i9);
                                            } else {
                                                byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i9));
                                                byteBufDirectBuffer.release();
                                            }
                                            return sSLEngineResultNewResult8;
                                        }
                                        if (error != SSL.SSL_ERROR_WANT_X509_LOOKUP && error != SSL.SSL_ERROR_WANT_CERTIFICATE_VERIFY && error != SSL.SSL_ERROR_WANT_PRIVATE_KEY_OPERATION) {
                                            throw shutdownWithError("SSL_write", error);
                                        }
                                        SSLEngineResult sSLEngineResultNewResult9 = newResult(SSLEngineResult.HandshakeStatus.NEED_TASK, i6, i9);
                                        SSL.bioClearByteBuffer(this.networkBIO);
                                        if (byteBufDirectBuffer == null) {
                                            byteBuffer.position(byteBuffer.position() + i9);
                                        } else {
                                            byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i9));
                                            byteBufDirectBuffer.release();
                                        }
                                        return sSLEngineResultNewResult9;
                                    }
                                    i++;
                                } catch (Throwable th2) {
                                    th = th2;
                                    i5 = iBioFlushByteBuffer4;
                                    SSL.bioClearByteBuffer(this.networkBIO);
                                    if (byteBufDirectBuffer != null) {
                                        byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i5));
                                        byteBufDirectBuffer.release();
                                    } else {
                                        byteBuffer.position(byteBuffer.position() + i5);
                                    }
                                    throw th;
                                }
                            }
                            SSLEngineResult sSLEngineResultNewResultMayFinishHandshake6 = newResultMayFinishHandshake(handshakeStatus, i6, iBioFlushByteBuffer4);
                            SSL.bioClearByteBuffer(this.networkBIO);
                            if (byteBufDirectBuffer == null) {
                                byteBuffer.position(byteBuffer.position() + iBioFlushByteBuffer4);
                            } else {
                                byteBuffer.put(byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), iBioFlushByteBuffer4));
                                byteBufDirectBuffer.release();
                            }
                            return sSLEngineResultNewResultMayFinishHandshake6;
                        } catch (Throwable th3) {
                            th = th3;
                            i5 = i4;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        i5 = iBioLengthByteBuffer;
                    }
                } catch (Throwable th5) {
                    i5 = byteBufferArr;
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        }
    }

    private SSLEngineResult newResult(SSLEngineResult.HandshakeStatus handshakeStatus, int i, int i2) {
        return newResult(SSLEngineResult.Status.OK, handshakeStatus, i, i2);
    }

    private SSLEngineResult newResult(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus handshakeStatus, int i, int i2) {
        if (isOutboundDone()) {
            if (isInboundDone()) {
                handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                shutdown();
            }
            return new SSLEngineResult(SSLEngineResult.Status.CLOSED, handshakeStatus, i, i2);
        }
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_TASK) {
            this.needTask = true;
        }
        return new SSLEngineResult(status, handshakeStatus, i, i2);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus handshakeStatus, int i, int i2) throws SSLException {
        return newResult(mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED), i, i2);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus handshakeStatus, int i, int i2) throws SSLException {
        return newResult(status, mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED), i, i2);
    }

    private SSLException shutdownWithError(String str, int i) {
        return shutdownWithError(str, i, SSL.getLastErrorNumber());
    }

    private SSLException shutdownWithError(String str, int i, int i2) {
        String errorString = SSL.getErrorString(i2);
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("{} failed with {}: OpenSSL error: {} {}", str, Integer.valueOf(i), Integer.valueOf(i2), errorString);
        }
        shutdown();
        if (this.handshakeState == HandshakeState.FINISHED) {
            return new SSLException(errorString);
        }
        SSLHandshakeException sSLHandshakeException = new SSLHandshakeException(errorString);
        Throwable th = this.handshakeException;
        if (th != null) {
            sSLHandshakeException.initCause(th);
            this.handshakeException = null;
        }
        return sSLHandshakeException;
    }

    public final SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer[] byteBufferArr2, int i3, int i4) throws SSLException {
        int i5;
        int i6;
        int i7;
        int i8;
        int iMin;
        int iMin2;
        ByteBuf byteBufWriteEncryptedData;
        int i9 = i;
        int i10 = i3;
        ObjectUtil.checkNotNull(byteBufferArr, "srcs");
        if (i9 >= byteBufferArr.length || (i5 = i9 + i2) > byteBufferArr.length) {
            throw new IndexOutOfBoundsException("offset: " + i9 + ", length: " + i2 + " (expected: offset <= offset + length <= srcs.length (" + byteBufferArr.length + "))");
        }
        if (byteBufferArr2 == null) {
            throw new IllegalArgumentException("dsts is null");
        }
        if (i10 >= byteBufferArr2.length || (i6 = i10 + i4) > byteBufferArr2.length) {
            throw new IndexOutOfBoundsException("offset: " + i10 + ", length: " + i4 + " (expected: offset <= offset + length <= dsts.length (" + byteBufferArr2.length + "))");
        }
        long jRemaining = 0;
        for (int i11 = i10; i11 < i6; i11++) {
            ByteBuffer byteBuffer = byteBufferArr2[i11];
            if (byteBuffer == null) {
                throw new IllegalArgumentException("dsts[" + i11 + "] is null");
            }
            if (byteBuffer.isReadOnly()) {
                throw new ReadOnlyBufferException();
            }
            jRemaining += (long) byteBuffer.remaining();
        }
        long jRemaining2 = 0;
        for (int i12 = i9; i12 < i5; i12++) {
            ByteBuffer byteBuffer2 = byteBufferArr[i12];
            if (byteBuffer2 == null) {
                throw new IllegalArgumentException("srcs[" + i12 + "] is null");
            }
            jRemaining2 += (long) byteBuffer2.remaining();
        }
        synchronized (this) {
            if (isInboundDone()) {
                return (isOutboundDone() || isDestroyed()) ? CLOSED_NOT_HANDSHAKING : NEED_WRAP_CLOSED;
            }
            SSLEngineResult.HandshakeStatus handshakeStatusHandshake = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            if (this.handshakeState != HandshakeState.FINISHED) {
                if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                    this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                }
                handshakeStatusHandshake = handshake();
                if (handshakeStatusHandshake == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                    return newResult(handshakeStatusHandshake, 0, 0);
                }
                if (handshakeStatusHandshake == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                    return NEED_WRAP_OK;
                }
                if (this.isInboundDone) {
                    return NEED_WRAP_CLOSED;
                }
            }
            int iSslPending0 = sslPending0();
            if (!this.jdkCompatibilityMode) {
                i7 = iSslPending0;
                if (jRemaining2 == 0 && i7 <= 0) {
                    return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, handshakeStatusHandshake, 0, 0);
                }
                if (jRemaining == 0) {
                    return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatusHandshake, 0, 0);
                }
                i8 = 0;
                iMin = (int) Math.min(2147483647L, jRemaining2);
            } else {
                if (jRemaining2 < 5) {
                    return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, handshakeStatusHandshake, 0, 0);
                }
                iMin = SslUtils.getEncryptedPacketLength(byteBufferArr, i);
                if (iMin == -2) {
                    throw new NotSslRecordException("not an SSL/TLS record");
                }
                int i13 = iMin - 5;
                i7 = iSslPending0;
                if (i13 > jRemaining) {
                    if (i13 > MAX_RECORD_SIZE) {
                        throw new SSLException("Illegal packet length: " + i13 + " > " + this.session.getApplicationBufferSize());
                    }
                    this.session.tryExpandApplicationBufferSize(i13);
                    return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatusHandshake, 0, 0);
                }
                if (jRemaining2 < iMin) {
                    return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, handshakeStatusHandshake, 0, 0);
                }
                i8 = 0;
            }
            int iSslPending1 = i7;
            int i14 = i8;
            loop2: while (true) {
                try {
                    ByteBuffer byteBuffer3 = byteBufferArr[i9];
                    int iRemaining = byteBuffer3.remaining();
                    if (iRemaining != 0) {
                        iMin2 = Math.min(iMin, iRemaining);
                        byteBufWriteEncryptedData = writeEncryptedData(byteBuffer3, iMin2);
                    } else if (iSslPending1 <= 0) {
                        i9++;
                        if (i9 >= i5) {
                            break;
                        }
                    } else {
                        iMin2 = SSL.bioLengthByteBuffer(this.networkBIO);
                        byteBufWriteEncryptedData = null;
                    }
                    while (true) {
                        try {
                            ByteBuffer byteBuffer4 = byteBufferArr2[i10];
                            if (!byteBuffer4.hasRemaining()) {
                                i10++;
                                if (i10 >= i6) {
                                    if (byteBufWriteEncryptedData == null) {
                                        break loop2;
                                    }
                                    byteBufWriteEncryptedData.release();
                                    break loop2;
                                }
                            } else {
                                int i15 = iSslPending1;
                                int plaintextData = readPlaintextData(byteBuffer4);
                                SSLEngineResult.HandshakeStatus handshakeStatus = handshakeStatusHandshake;
                                int i16 = i5;
                                int iBioLengthByteBuffer = iMin2 - SSL.bioLengthByteBuffer(this.networkBIO);
                                i14 += iBioLengthByteBuffer;
                                iMin -= iBioLengthByteBuffer;
                                iMin2 -= iBioLengthByteBuffer;
                                byteBuffer3.position(byteBuffer3.position() + iBioLengthByteBuffer);
                                if (plaintextData > 0) {
                                    i8 += plaintextData;
                                    if (byteBuffer4.hasRemaining()) {
                                        handshakeStatusHandshake = handshakeStatus;
                                        if (iMin != 0 && !this.jdkCompatibilityMode) {
                                            iSslPending1 = i15;
                                        }
                                        if (byteBufWriteEncryptedData == null) {
                                            break loop2;
                                        }
                                        byteBufWriteEncryptedData.release();
                                        break loop2;
                                    }
                                    iSslPending1 = sslPending0();
                                    i10++;
                                    if (i10 >= i6) {
                                        SSLEngineResult sSLEngineResultNewResult = iSslPending1 > 0 ? newResult(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatus, i14, i8) : newResultMayFinishHandshake(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, handshakeStatus, i14, i8);
                                        if (byteBufWriteEncryptedData != null) {
                                            byteBufWriteEncryptedData.release();
                                        }
                                        SSL.bioClearByteBuffer(this.networkBIO);
                                        rejectRemoteInitiatedRenegotiation();
                                        return sSLEngineResultNewResult;
                                    }
                                    handshakeStatusHandshake = handshakeStatus;
                                    i5 = i16;
                                } else {
                                    handshakeStatusHandshake = handshakeStatus;
                                    int error = SSL.getError(this.ssl, plaintextData);
                                    if (error != SSL.SSL_ERROR_WANT_READ && error != SSL.SSL_ERROR_WANT_WRITE) {
                                        if (error == SSL.SSL_ERROR_ZERO_RETURN) {
                                            if (!this.receivedShutdown) {
                                                closeAll();
                                            }
                                            SSLEngineResult sSLEngineResultNewResultMayFinishHandshake = newResultMayFinishHandshake(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, handshakeStatusHandshake, i14, i8);
                                            if (byteBufWriteEncryptedData != null) {
                                                byteBufWriteEncryptedData.release();
                                            }
                                            SSL.bioClearByteBuffer(this.networkBIO);
                                            rejectRemoteInitiatedRenegotiation();
                                            return sSLEngineResultNewResultMayFinishHandshake;
                                        }
                                        if (error != SSL.SSL_ERROR_WANT_X509_LOOKUP && error != SSL.SSL_ERROR_WANT_CERTIFICATE_VERIFY && error != SSL.SSL_ERROR_WANT_PRIVATE_KEY_OPERATION) {
                                            SSLEngineResult sSLEngineResultSslReadErrorResult = sslReadErrorResult(error, SSL.getLastErrorNumber(), i14, i8);
                                            if (byteBufWriteEncryptedData != null) {
                                                byteBufWriteEncryptedData.release();
                                            }
                                            SSL.bioClearByteBuffer(this.networkBIO);
                                            rejectRemoteInitiatedRenegotiation();
                                            return sSLEngineResultSslReadErrorResult;
                                        }
                                        SSLEngineResult sSLEngineResultNewResult2 = newResult(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_TASK, i14, i8);
                                        if (byteBufWriteEncryptedData != null) {
                                            byteBufWriteEncryptedData.release();
                                        }
                                        SSL.bioClearByteBuffer(this.networkBIO);
                                        rejectRemoteInitiatedRenegotiation();
                                        return sSLEngineResultNewResult2;
                                    }
                                    i9++;
                                    i5 = i16;
                                    if (i9 >= i5) {
                                        if (byteBufWriteEncryptedData == null) {
                                            break;
                                        }
                                        byteBufWriteEncryptedData.release();
                                        break loop2;
                                    }
                                    if (byteBufWriteEncryptedData != null) {
                                        byteBufWriteEncryptedData.release();
                                    }
                                    iSslPending1 = i15;
                                }
                            }
                        } catch (Throwable th) {
                            if (byteBufWriteEncryptedData != null) {
                                byteBufWriteEncryptedData.release();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    SSL.bioClearByteBuffer(this.networkBIO);
                    rejectRemoteInitiatedRenegotiation();
                    throw th2;
                }
            }
            SSL.bioClearByteBuffer(this.networkBIO);
            rejectRemoteInitiatedRenegotiation();
            if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & SSL.SSL_RECEIVED_SHUTDOWN) == SSL.SSL_RECEIVED_SHUTDOWN) {
                closeAll();
            }
            return newResultMayFinishHandshake(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, handshakeStatusHandshake, i14, i8);
        }
    }

    private SSLEngineResult sslReadErrorResult(int i, int i2, int i3, int i4) throws SSLException {
        if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            if (this.handshakeException == null && this.handshakeState != HandshakeState.FINISHED) {
                this.handshakeException = new SSLHandshakeException(SSL.getErrorString(i2));
            }
            SSL.clearError();
            return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, i3, i4);
        }
        throw shutdownWithError("SSL_read", i, i2);
    }

    private void closeAll() throws SSLException {
        this.receivedShutdown = true;
        closeOutbound();
        closeInbound();
    }

    private void rejectRemoteInitiatedRenegotiation() throws SSLHandshakeException {
        if (isDestroyed() || SSL.getHandshakeCount(this.ssl) <= 1 || "TLSv1.3".equals(this.session.getProtocol()) || this.handshakeState != HandshakeState.FINISHED) {
            return;
        }
        shutdown();
        throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
    }

    public final SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) throws SSLException {
        return unwrap(byteBufferArr, 0, byteBufferArr.length, byteBufferArr2, 0, byteBufferArr2.length);
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.singleSrcBuffer;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.singleDstBuffer;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) throws SSLException {
        SSLEngineResult sSLEngineResultUnwrap;
        try {
            sSLEngineResultUnwrap = unwrap(singleSrcBuffer(byteBuffer), 0, 1, byteBufferArr, i, i2);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return sSLEngineResultUnwrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult sSLEngineResultWrap;
        try {
            sSLEngineResultWrap = wrap(singleSrcBuffer(byteBuffer), byteBuffer2);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return sSLEngineResultWrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult sSLEngineResultUnwrap;
        try {
            sSLEngineResultUnwrap = unwrap(singleSrcBuffer(byteBuffer), singleDstBuffer(byteBuffer2));
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
            throw th;
        }
        return sSLEngineResultUnwrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException {
        SSLEngineResult sSLEngineResultUnwrap;
        try {
            sSLEngineResultUnwrap = unwrap(singleSrcBuffer(byteBuffer), byteBufferArr);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return sSLEngineResultUnwrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized Runnable getDelegatedTask() {
        if (isDestroyed()) {
            return null;
        }
        final Runnable task = SSL.getTask(this.ssl);
        if (task == null) {
            return null;
        }
        return new Runnable() { // from class: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.3
            @Override // java.lang.Runnable
            public void run() {
                if (ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    return;
                }
                try {
                    task.run();
                } finally {
                    ReferenceCountedOpenSslEngine.this.needTask = false;
                }
            }
        };
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized void closeInbound() throws SSLException {
        if (this.isInboundDone) {
            return;
        }
        this.isInboundDone = true;
        if (isOutboundDone()) {
            shutdown();
        }
        if (this.handshakeState != HandshakeState.NOT_STARTED && !this.receivedShutdown) {
            throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized boolean isInboundDone() {
        return this.isInboundDone;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized void closeOutbound() {
        if (this.outboundClosed) {
            return;
        }
        this.outboundClosed = true;
        if (this.handshakeState != HandshakeState.NOT_STARTED && !isDestroyed()) {
            if ((SSL.getShutdown(this.ssl) & SSL.SSL_SENT_SHUTDOWN) != SSL.SSL_SENT_SHUTDOWN) {
                doSSLShutdown();
            }
        } else {
            shutdown();
        }
    }

    private boolean doSSLShutdown() {
        if (SSL.isInInit(this.ssl) != 0) {
            return false;
        }
        int iShutdownSSL = SSL.shutdownSSL(this.ssl);
        if (iShutdownSSL >= 0) {
            return true;
        }
        int error = SSL.getError(this.ssl, iShutdownSSL);
        if (error == SSL.SSL_ERROR_SYSCALL || error == SSL.SSL_ERROR_SSL) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                int lastErrorNumber = SSL.getLastErrorNumber();
                internalLogger.debug("SSL_shutdown failed: OpenSSL error: {} {}", Integer.valueOf(lastErrorNumber), SSL.getErrorString(lastErrorNumber));
            }
            shutdown();
            return false;
        }
        SSL.clearError();
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0015  */
    @Override // javax.net.ssl.SSLEngine
    public final synchronized boolean isOutboundDone() {
        boolean z;
        if (this.outboundClosed) {
            long j = this.networkBIO;
            if (j == 0 || SSL.bioLengthNonApplication(j) == 0) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        return z;
    }

    @Override // javax.net.ssl.SSLEngine
    public final String[] getSupportedCipherSuites() {
        return (String[]) OpenSsl.AVAILABLE_CIPHER_SUITES.toArray(new String[0]);
    }

    @Override // javax.net.ssl.SSLEngine
    public final String[] getEnabledCipherSuites() {
        synchronized (this) {
            if (!isDestroyed()) {
                String[] ciphers = SSL.getCiphers(this.ssl);
                if (ciphers == null) {
                    return EmptyArrays.EMPTY_STRINGS;
                }
                ArrayList arrayList = new ArrayList();
                synchronized (this) {
                    for (int i = 0; i < ciphers.length; i++) {
                        String javaCipherSuite = toJavaCipherSuite(ciphers[i]);
                        if (javaCipherSuite == null) {
                            javaCipherSuite = ciphers[i];
                        }
                        if (OpenSsl.isTlsv13Supported() || !SslUtils.isTLSv13Cipher(javaCipherSuite)) {
                            arrayList.add(javaCipherSuite);
                        }
                    }
                }
                return (String[]) arrayList.toArray(new String[0]);
            }
            return EmptyArrays.EMPTY_STRINGS;
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final void setEnabledCipherSuites(String[] strArr) {
        ObjectUtil.checkNotNull(strArr, "cipherSuites");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        CipherSuiteConverter.convertToCipherStrings(Arrays.asList(strArr), sb, sb2, OpenSsl.isBoringSSL());
        String string = sb.toString();
        String string2 = sb2.toString();
        if (!OpenSsl.isTlsv13Supported() && !string2.isEmpty()) {
            throw new IllegalArgumentException("TLSv1.3 is not supported by this java version.");
        }
        synchronized (this) {
            if (!isDestroyed()) {
                try {
                    SSL.setCipherSuites(this.ssl, string, false);
                    if (OpenSsl.isTlsv13Supported()) {
                        SSL.setCipherSuites(this.ssl, string2, true);
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("failed to enable cipher suites: " + string, e);
                }
            } else {
                throw new IllegalStateException("failed to enable cipher suites: " + string);
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final String[] getSupportedProtocols() {
        return (String[]) OpenSsl.SUPPORTED_PROTOCOLS_SET.toArray(new String[0]);
    }

    @Override // javax.net.ssl.SSLEngine
    public final String[] getEnabledProtocols() {
        ArrayList arrayList = new ArrayList(6);
        arrayList.add("SSLv2Hello");
        synchronized (this) {
            if (!isDestroyed()) {
                int options = SSL.getOptions(this.ssl);
                if (isProtocolEnabled(options, SSL.SSL_OP_NO_TLSv1, "TLSv1")) {
                    arrayList.add("TLSv1");
                }
                if (isProtocolEnabled(options, SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1")) {
                    arrayList.add("TLSv1.1");
                }
                if (isProtocolEnabled(options, SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2")) {
                    arrayList.add("TLSv1.2");
                }
                if (isProtocolEnabled(options, SSL.SSL_OP_NO_TLSv1_3, "TLSv1.3")) {
                    arrayList.add("TLSv1.3");
                }
                if (isProtocolEnabled(options, SSL.SSL_OP_NO_SSLv2, "SSLv2")) {
                    arrayList.add("SSLv2");
                }
                if (isProtocolEnabled(options, SSL.SSL_OP_NO_SSLv3, "SSLv3")) {
                    arrayList.add("SSLv3");
                }
                return (String[]) arrayList.toArray(new String[0]);
            }
            return (String[]) arrayList.toArray(new String[0]);
        }
    }

    private static boolean isProtocolEnabled(int i, int i2, String str) {
        return (i & i2) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(str);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0032 A[PHI: r0 r5
      0x0032: PHI (r0v19 int) = (r0v11 int), (r0v13 int), (r0v15 int), (r0v17 int), (r0v20 int) binds: [B:46:0x006d, B:39:0x005e, B:32:0x004f, B:25:0x0040, B:18:0x0030] A[DONT_GENERATE, DONT_INLINE]
      0x0032: PHI (r5v13 int) = (r5v9 int), (r5v10 int), (r5v11 int), (r5v12 int), (r5v0 int) binds: [B:46:0x006d, B:39:0x005e, B:32:0x004f, B:25:0x0040, B:18:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // javax.net.ssl.SSLEngine
    public final void setEnabledProtocols(String[] strArr) {
        if (strArr == null) {
            throw new IllegalArgumentException();
        }
        int length = OPENSSL_OP_NO_PROTOCOLS.length;
        int length2 = strArr.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = 1;
            if (i < length2) {
                String str = strArr[i];
                if (!OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(str)) {
                    throw new IllegalArgumentException("Protocol " + str + " is not supported.");
                }
                if (str.equals("SSLv2")) {
                    if (length > 0) {
                        length = 0;
                    }
                    if (i2 < 0) {
                        i2 = 0;
                    }
                } else if (str.equals("SSLv3")) {
                    if (length > 1) {
                        length = 1;
                    }
                    if (i2 < 1) {
                        i2 = i3;
                    }
                } else if (str.equals("TLSv1")) {
                    i3 = 2;
                    if (length > 2) {
                        length = 2;
                    }
                    if (i2 < 2) {
                        i2 = i3;
                    }
                } else if (str.equals("TLSv1.1")) {
                    i3 = 3;
                    if (length > 3) {
                        length = 3;
                    }
                    if (i2 < 3) {
                        i2 = i3;
                    }
                } else if (str.equals("TLSv1.2")) {
                    i3 = 4;
                    if (length > 4) {
                        length = 4;
                    }
                    if (i2 < 4) {
                        i2 = i3;
                    }
                } else if (str.equals("TLSv1.3")) {
                    i3 = 5;
                    if (length > 5) {
                        length = 5;
                    }
                    if (i2 < 5) {
                        i2 = i3;
                    }
                }
                i++;
            } else {
                synchronized (this) {
                    if (!isDestroyed()) {
                        SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2 | SSL.SSL_OP_NO_TLSv1_3);
                        int i4 = 0;
                        for (int i5 = 0; i5 < length; i5++) {
                            i4 |= OPENSSL_OP_NO_PROTOCOLS[i5];
                        }
                        int i6 = i2 + 1;
                        while (true) {
                            int[] iArr = OPENSSL_OP_NO_PROTOCOLS;
                            if (i6 < iArr.length) {
                                i4 |= iArr[i6];
                                i6++;
                            } else {
                                SSL.setOptions(this.ssl, i4);
                            }
                        }
                    } else {
                        throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(strArr));
                    }
                }
                return;
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final SSLSession getSession() {
        return this.session;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized void beginHandshake() throws SSLException {
        int i = AnonymousClass4.$SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[this.handshakeState.ordinal()];
        if (i == 1) {
            this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
            if (handshake() == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                this.needTask = true;
            }
            calculateMaxWrapOverhead();
        } else {
            if (i == 2) {
                throw new SSLException("renegotiation unsupported");
            }
            if (i == 3) {
                checkEngineClosed();
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                calculateMaxWrapOverhead();
            } else if (i != 4) {
                throw new Error();
            }
        }
    }

    private void checkEngineClosed() throws SSLException {
        if (isDestroyed()) {
            throw new SSLException("engine closed");
        }
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int i) {
        return i > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isEmpty(Object[] objArr) {
        return objArr == null || objArr.length == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    private SSLEngineResult.HandshakeStatus handshakeException() throws SSLException {
        if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            return SSLEngineResult.HandshakeStatus.NEED_WRAP;
        }
        Throwable th = this.handshakeException;
        this.handshakeException = null;
        shutdown();
        if (th instanceof SSLHandshakeException) {
            throw ((SSLHandshakeException) th);
        }
        SSLHandshakeException sSLHandshakeException = new SSLHandshakeException("General OpenSslEngine problem");
        sSLHandshakeException.initCause(th);
        throw sSLHandshakeException;
    }

    final void initHandshakeException(Throwable th) {
        this.handshakeException = th;
    }

    private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
        if (this.needTask) {
            return SSLEngineResult.HandshakeStatus.NEED_TASK;
        }
        if (this.handshakeState == HandshakeState.FINISHED) {
            return SSLEngineResult.HandshakeStatus.FINISHED;
        }
        checkEngineClosed();
        if (this.handshakeException != null) {
            if (SSL.doHandshake(this.ssl) <= 0) {
                SSL.clearError();
            }
            return handshakeException();
        }
        this.engineMap.add(this);
        if (this.lastAccessed == -1) {
            this.lastAccessed = System.currentTimeMillis();
        }
        int iDoHandshake = SSL.doHandshake(this.ssl);
        if (iDoHandshake <= 0) {
            int error = SSL.getError(this.ssl, iDoHandshake);
            if (error == SSL.SSL_ERROR_WANT_READ || error == SSL.SSL_ERROR_WANT_WRITE) {
                return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
            }
            if (error == SSL.SSL_ERROR_WANT_X509_LOOKUP || error == SSL.SSL_ERROR_WANT_CERTIFICATE_VERIFY || error == SSL.SSL_ERROR_WANT_PRIVATE_KEY_OPERATION) {
                return SSLEngineResult.HandshakeStatus.NEED_TASK;
            }
            if (this.handshakeException != null) {
                return handshakeException();
            }
            throw shutdownWithError("SSL_do_handshake", error);
        }
        if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            return SSLEngineResult.HandshakeStatus.NEED_WRAP;
        }
        this.session.handshakeFinished();
        return SSLEngineResult.HandshakeStatus.FINISHED;
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        return (handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING || this.handshakeState == HandshakeState.FINISHED) ? handshakeStatus : handshake();
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        if (needPendingStatus()) {
            if (this.needTask) {
                return SSLEngineResult.HandshakeStatus.NEED_TASK;
            }
            return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
        }
        return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int i) {
        if (needPendingStatus()) {
            if (this.needTask) {
                return SSLEngineResult.HandshakeStatus.NEED_TASK;
            }
            return pendingStatus(i);
        }
        return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean needPendingStatus() {
        return (this.handshakeState == HandshakeState.NOT_STARTED || isDestroyed() || (this.handshakeState == HandshakeState.FINISHED && !isInboundDone() && !isOutboundDone())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String toJavaCipherSuite(String str) {
        if (str == null) {
            return null;
        }
        return CipherSuiteConverter.toJava(str, toJavaCipherSuitePrefix(SSL.getVersion(this.ssl)));
    }

    private static String toJavaCipherSuitePrefix(String str) {
        char cCharAt = 0;
        if (str != null && !str.isEmpty()) {
            cCharAt = str.charAt(0);
        }
        if (cCharAt == 'S') {
            return "SSL";
        }
        if (cCharAt == 'T') {
            return "TLS";
        }
        return GrsBaseInfo.CountryCodeSource.UNKNOWN;
    }

    @Override // javax.net.ssl.SSLEngine
    public final void setUseClientMode(boolean z) {
        if (z != this.clientMode) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final boolean getUseClientMode() {
        return this.clientMode;
    }

    @Override // javax.net.ssl.SSLEngine
    public final void setNeedClientAuth(boolean z) {
        setClientAuth(z ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    @Override // javax.net.ssl.SSLEngine
    public final boolean getNeedClientAuth() {
        return this.clientAuth == ClientAuth.REQUIRE;
    }

    @Override // javax.net.ssl.SSLEngine
    public final void setWantClientAuth(boolean z) {
        setClientAuth(z ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    @Override // javax.net.ssl.SSLEngine
    public final boolean getWantClientAuth() {
        return this.clientAuth == ClientAuth.OPTIONAL;
    }

    public final synchronized void setVerify(int i, int i2) {
        SSL.setVerify(this.ssl, i, i2);
    }

    private void setClientAuth(ClientAuth clientAuth) {
        if (this.clientMode) {
            return;
        }
        synchronized (this) {
            if (this.clientAuth == clientAuth) {
                return;
            }
            int i = AnonymousClass4.$SwitchMap$io$netty$handler$ssl$ClientAuth[clientAuth.ordinal()];
            if (i == 1) {
                SSL.setVerify(this.ssl, 0, 10);
            } else if (i == 2) {
                SSL.setVerify(this.ssl, 2, 10);
            } else if (i == 3) {
                SSL.setVerify(this.ssl, 1, 10);
            } else {
                throw new Error(clientAuth.toString());
            }
            this.clientAuth = clientAuth;
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final void setEnableSessionCreation(boolean z) {
        if (z) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized SSLParameters getSSLParameters() {
        SSLParameters sSLParameters;
        sSLParameters = super.getSSLParameters();
        int iJavaVersion = PlatformDependent.javaVersion();
        if (iJavaVersion >= 7) {
            sSLParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
            Java7SslParametersUtils.setAlgorithmConstraints(sSLParameters, this.algorithmConstraints);
            if (iJavaVersion >= 8) {
                List<String> list = this.sniHostNames;
                if (list != null) {
                    Java8SslUtils.setSniHostNames(sSLParameters, list);
                }
                if (!isDestroyed()) {
                    Java8SslUtils.setUseCipherSuitesOrder(sSLParameters, (SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
                }
                Java8SslUtils.setSNIMatchers(sSLParameters, this.matchers);
            }
        }
        return sSLParameters;
    }

    @Override // javax.net.ssl.SSLEngine
    public final synchronized void setSSLParameters(SSLParameters sSLParameters) {
        int iJavaVersion = PlatformDependent.javaVersion();
        if (iJavaVersion >= 7) {
            if (sSLParameters.getAlgorithmConstraints() != null) {
                throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
            }
            if (iJavaVersion >= 8) {
                if (!isDestroyed()) {
                    if (this.clientMode) {
                        List<String> sniHostNames = Java8SslUtils.getSniHostNames(sSLParameters);
                        Iterator<String> it = sniHostNames.iterator();
                        while (it.hasNext()) {
                            SSL.setTlsExtHostName(this.ssl, it.next());
                        }
                        this.sniHostNames = sniHostNames;
                    }
                    if (Java8SslUtils.getUseCipherSuitesOrder(sSLParameters)) {
                        SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    } else {
                        SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    }
                }
                this.matchers = sSLParameters.getSNIMatchers();
            }
            String endpointIdentificationAlgorithm = sSLParameters.getEndpointIdentificationAlgorithm();
            boolean zIsEndPointVerificationEnabled = isEndPointVerificationEnabled(endpointIdentificationAlgorithm);
            if (this.clientMode && zIsEndPointVerificationEnabled) {
                SSL.setVerify(this.ssl, 2, -1);
            }
            this.endPointIdentificationAlgorithm = endpointIdentificationAlgorithm;
            this.algorithmConstraints = sSLParameters.getAlgorithmConstraints();
        }
        super.setSSLParameters(sSLParameters);
    }

    private static boolean isEndPointVerificationEnabled(String str) {
        return (str == null || str.isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDestroyed() {
        return this.destroyed;
    }

    final boolean checkSniHostnameMatch(byte[] bArr) {
        return Java8SslUtils.checkSniHostnameMatch(this.matchers, bArr);
    }

    @Override // io.netty.handler.ssl.ApplicationProtocolAccessor
    public String getNegotiatedApplicationProtocol() {
        return this.applicationProtocol;
    }

    private static long bufferAddress(ByteBuffer byteBuffer) {
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.directBufferAddress(byteBuffer);
        }
        return Buffer.address(byteBuffer);
    }

    private final class DefaultOpenSslSession implements OpenSslSession {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile int applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH;
        private String cipher;
        private long creationTime;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        private byte[] f281id;
        private Certificate[] peerCerts;
        private String protocol;
        private final OpenSslSessionContext sessionContext;
        private Map<String, Object> values;
        private X509Certificate[] x509PeerCerts;

        DefaultOpenSslSession(OpenSslSessionContext openSslSessionContext) {
            this.sessionContext = openSslSessionContext;
        }

        private SSLSessionBindingEvent newSSLSessionBindingEvent(String str) {
            return new SSLSessionBindingEvent(ReferenceCountedOpenSslEngine.this.session, str);
        }

        @Override // javax.net.ssl.SSLSession
        public byte[] getId() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                byte[] bArr = this.f281id;
                if (bArr == null) {
                    return EmptyArrays.EMPTY_BYTES;
                }
                return (byte[]) bArr.clone();
            }
        }

        @Override // javax.net.ssl.SSLSession
        public SSLSessionContext getSessionContext() {
            return this.sessionContext;
        }

        @Override // javax.net.ssl.SSLSession
        public long getCreationTime() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.creationTime == 0 && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000;
                }
            }
            return this.creationTime;
        }

        @Override // javax.net.ssl.SSLSession
        public long getLastAccessedTime() {
            long j = ReferenceCountedOpenSslEngine.this.lastAccessed;
            return j == -1 ? getCreationTime() : j;
        }

        @Override // javax.net.ssl.SSLSession
        public void invalidate() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0L);
                }
            }
        }

        @Override // javax.net.ssl.SSLSession
        public boolean isValid() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    return false;
                }
                return System.currentTimeMillis() - (SSL.getTimeout(ReferenceCountedOpenSslEngine.this.ssl) * 1000) < SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000;
            }
        }

        @Override // javax.net.ssl.SSLSession
        public void putValue(String str, Object obj) {
            Object objPut;
            ObjectUtil.checkNotNull(str, "name");
            ObjectUtil.checkNotNull(obj, "value");
            synchronized (this) {
                Map map = this.values;
                if (map == null) {
                    map = new HashMap(2);
                    this.values = map;
                }
                objPut = map.put(str, obj);
            }
            if (obj instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener) obj).valueBound(newSSLSessionBindingEvent(str));
            }
            notifyUnbound(objPut, str);
        }

        @Override // javax.net.ssl.SSLSession
        public Object getValue(String str) {
            ObjectUtil.checkNotNull(str, "name");
            synchronized (this) {
                Map<String, Object> map = this.values;
                if (map == null) {
                    return null;
                }
                return map.get(str);
            }
        }

        @Override // javax.net.ssl.SSLSession
        public void removeValue(String str) {
            ObjectUtil.checkNotNull(str, "name");
            synchronized (this) {
                Map<String, Object> map = this.values;
                if (map == null) {
                    return;
                }
                notifyUnbound(map.remove(str), str);
            }
        }

        @Override // javax.net.ssl.SSLSession
        public String[] getValueNames() {
            synchronized (this) {
                Map<String, Object> map = this.values;
                if (map != null && !map.isEmpty()) {
                    return (String[]) map.keySet().toArray(new String[0]);
                }
                return EmptyArrays.EMPTY_STRINGS;
            }
        }

        private void notifyUnbound(Object obj, String str) {
            if (obj instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener) obj).valueUnbound(newSSLSessionBindingEvent(str));
            }
        }

        @Override // io.netty.handler.ssl.OpenSslSession
        public void handshakeFinished() throws SSLException {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.f281id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
                    ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
                    this.cipher = referenceCountedOpenSslEngine.toJavaCipherSuite(SSL.getCipherForSSL(referenceCountedOpenSslEngine.ssl));
                    this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                    initPeerCerts();
                    selectApplicationProtocol();
                    ReferenceCountedOpenSslEngine.this.calculateMaxWrapOverhead();
                    ReferenceCountedOpenSslEngine.this.handshakeState = HandshakeState.FINISHED;
                } else {
                    throw new SSLException("Already closed");
                }
            }
        }

        private void initPeerCerts() {
            byte[][] peerCertChain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
            if (ReferenceCountedOpenSslEngine.this.clientMode) {
                if (ReferenceCountedOpenSslEngine.isEmpty(peerCertChain)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                    return;
                } else {
                    this.peerCerts = new Certificate[peerCertChain.length];
                    this.x509PeerCerts = new X509Certificate[peerCertChain.length];
                    initCerts(peerCertChain, 0);
                    return;
                }
            }
            byte[] peerCertificate = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
            if (!ReferenceCountedOpenSslEngine.isEmpty(peerCertificate)) {
                if (ReferenceCountedOpenSslEngine.isEmpty(peerCertChain)) {
                    this.peerCerts = new Certificate[]{new OpenSslX509Certificate(peerCertificate)};
                    this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(peerCertificate)};
                    return;
                }
                Certificate[] certificateArr = new Certificate[peerCertChain.length + 1];
                this.peerCerts = certificateArr;
                this.x509PeerCerts = new X509Certificate[peerCertChain.length + 1];
                certificateArr[0] = new OpenSslX509Certificate(peerCertificate);
                this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(peerCertificate);
                initCerts(peerCertChain, 1);
                return;
            }
            this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
            this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
        }

        private void initCerts(byte[][] bArr, int i) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                int i3 = i + i2;
                this.peerCerts[i3] = new OpenSslX509Certificate(bArr[i2]);
                this.x509PeerCerts[i3] = new OpenSslJavaxX509Certificate(bArr[i2]);
            }
        }

        private void selectApplicationProtocol() throws SSLException {
            ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
            List<String> listProtocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
            int i = AnonymousClass4.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ReferenceCountedOpenSslEngine.this.apn.protocol().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    String alpnSelected = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (alpnSelected != null) {
                        ReferenceCountedOpenSslEngine.this.applicationProtocol = selectApplicationProtocol(listProtocols, selectedListenerFailureBehavior, alpnSelected);
                        return;
                    }
                    return;
                }
                if (i == 3) {
                    String nextProtoNegotiated = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    if (nextProtoNegotiated != null) {
                        ReferenceCountedOpenSslEngine.this.applicationProtocol = selectApplicationProtocol(listProtocols, selectedListenerFailureBehavior, nextProtoNegotiated);
                        return;
                    }
                    return;
                }
                if (i == 4) {
                    String alpnSelected2 = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (alpnSelected2 == null) {
                        alpnSelected2 = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    }
                    if (alpnSelected2 != null) {
                        ReferenceCountedOpenSslEngine.this.applicationProtocol = selectApplicationProtocol(listProtocols, selectedListenerFailureBehavior, alpnSelected2);
                        return;
                    }
                    return;
                }
                throw new Error();
            }
        }

        private String selectApplicationProtocol(List<String> list, ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior, String str) throws SSLException {
            if (selectedListenerFailureBehavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
                return str;
            }
            int size = list.size();
            if (list.contains(str)) {
                return str;
            }
            if (selectedListenerFailureBehavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                return list.get(size - 1);
            }
            throw new SSLException("unknown protocol " + str);
        }

        @Override // javax.net.ssl.SSLSession
        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            Certificate[] certificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (ReferenceCountedOpenSslEngine.isEmpty(this.peerCerts)) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                certificateArr = (Certificate[]) this.peerCerts.clone();
            }
            return certificateArr;
        }

        @Override // javax.net.ssl.SSLSession
        public Certificate[] getLocalCertificates() {
            Certificate[] certificateArr = ReferenceCountedOpenSslEngine.this.localCertificateChain;
            if (certificateArr == null) {
                return null;
            }
            return (Certificate[]) certificateArr.clone();
        }

        @Override // javax.net.ssl.SSLSession
        public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
            X509Certificate[] x509CertificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (ReferenceCountedOpenSslEngine.isEmpty(this.x509PeerCerts)) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                x509CertificateArr = (X509Certificate[]) this.x509PeerCerts.clone();
            }
            return x509CertificateArr;
        }

        @Override // javax.net.ssl.SSLSession
        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            return ((java.security.cert.X509Certificate) getPeerCertificates()[0]).getSubjectX500Principal();
        }

        @Override // javax.net.ssl.SSLSession
        public Principal getLocalPrincipal() {
            Certificate[] certificateArr = ReferenceCountedOpenSslEngine.this.localCertificateChain;
            if (certificateArr == null || certificateArr.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate) certificateArr[0]).getIssuerX500Principal();
        }

        @Override // javax.net.ssl.SSLSession
        public String getCipherSuite() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                String str = this.cipher;
                return str == null ? "SSL_NULL_WITH_NULL_NULL" : str;
            }
        }

        @Override // javax.net.ssl.SSLSession
        public String getProtocol() {
            String version = this.protocol;
            if (version == null) {
                synchronized (ReferenceCountedOpenSslEngine.this) {
                    if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                        version = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                    } else {
                        version = "";
                    }
                }
            }
            return version;
        }

        @Override // javax.net.ssl.SSLSession
        public String getPeerHost() {
            return ReferenceCountedOpenSslEngine.this.getPeerHost();
        }

        @Override // javax.net.ssl.SSLSession
        public int getPeerPort() {
            return ReferenceCountedOpenSslEngine.this.getPeerPort();
        }

        @Override // javax.net.ssl.SSLSession
        public int getPacketBufferSize() {
            return ReferenceCountedOpenSslEngine.this.maxEncryptedPacketLength();
        }

        @Override // javax.net.ssl.SSLSession
        public int getApplicationBufferSize() {
            return this.applicationBufferSize;
        }

        @Override // io.netty.handler.ssl.OpenSslSession
        public void tryExpandApplicationBufferSize(int i) {
            if (i <= ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH || this.applicationBufferSize == ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE) {
                return;
            }
            this.applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE;
        }
    }

    /* JADX INFO: renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslEngine$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ClientAuth;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState;

        static {
            int[] iArr = new int[ApplicationProtocolConfig.Protocol.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol = iArr;
            try {
                iArr[ApplicationProtocolConfig.Protocol.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.ALPN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.NPN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.NPN_AND_ALPN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ClientAuth.values().length];
            $SwitchMap$io$netty$handler$ssl$ClientAuth = iArr2;
            try {
                iArr2[ClientAuth.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.REQUIRE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.OPTIONAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr3 = new int[HandshakeState.values().length];
            $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState = iArr3;
            try {
                iArr3[HandshakeState.NOT_STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[HandshakeState.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[HandshakeState.STARTED_IMPLICITLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[HandshakeState.STARTED_EXPLICITLY.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }
}
