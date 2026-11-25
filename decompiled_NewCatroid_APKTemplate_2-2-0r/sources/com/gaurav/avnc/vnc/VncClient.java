package com.gaurav.avnc.vnc;

import androidx.exifinterface.media.ExifInterface;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: VncClient.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b7\u0018\u0000 s2\u00020\u0001:\u0002stB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010 \u001a\u00020!H\u0003J\b\u0010\"\u001a\u00020!H\u0003J\u0018\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u0018H\u0003J\b\u0010&\u001a\u00020'H\u0003J\b\u0010(\u001a\u00020\u0015H\u0003J\u0018\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0006H\u0003J\u0018\u0010-\u001a\u00020!2\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u0018H\u0003J\u0010\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020+H\u0003J\u0006\u00102\u001a\u00020!J&\u00103\u001a\u00020!2\u0006\u00104\u001a\u00020\u00182\u0006\u00105\u001a\u00020\u00062\u0006\u00106\u001a\u00020\u00182\u0006\u00107\u001a\u00020\u0006J\u0016\u00108\u001a\u00020!2\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u0018J\u0006\u0010;\u001a\u00020\u0015J\u0017\u0010<\u001a\u00020!2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020!0>H\u0082\bJ\u0017\u0010?\u001a\u00020!2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020!0>H\u0082\bJ\u0006\u0010@\u001a\u00020!J\u0006\u0010A\u001a\u00020\u0006J\u0016\u0010B\u001a\u00020!2\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u0018J\u0011\u0010C\u001a\u00020!2\u0006\u0010D\u001a\u00020\u0017H\u0082 J\t\u0010E\u001a\u00020\u0017H\u0082 J1\u0010F\u001a\u00020!2\u0006\u0010D\u001a\u00020\u00172\u0006\u00104\u001a\u00020\u00182\u0006\u00105\u001a\u00020\u00062\u0006\u00106\u001a\u00020\u00182\u0006\u00107\u001a\u00020\u0006H\u0082 J\u0011\u0010G\u001a\u00020\u00152\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0011\u0010H\u001a\u00020\u00182\u0006\u0010D\u001a\u00020\u0017H\u0082 J\t\u0010I\u001a\u00020\u0015H\u0082 J\u0011\u0010J\u001a\u00020\u00182\u0006\u0010D\u001a\u00020\u0017H\u0082 J!\u0010K\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u0018H\u0082 J\u0011\u0010L\u001a\u00020!2\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0011\u0010M\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0011\u0010N\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0011\u0010O\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0019\u0010P\u001a\u00020!2\u0006\u0010D\u001a\u00020\u00172\u0006\u0010Q\u001a\u00020\u0006H\u0082 J\u0011\u0010R\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0011\u0010S\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u0017H\u0082 J!\u0010T\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00172\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0006H\u0082 J)\u0010U\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00172\u0006\u0010V\u001a\u00020\u00182\u0006\u0010W\u001a\u00020\u00182\u0006\u0010X\u001a\u00020\u0006H\u0082 J)\u0010Y\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u00182\u0006\u0010Z\u001a\u00020\u0018H\u0082 J!\u0010[\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00172\u0006\u0010\\\u001a\u00020\u00182\u0006\u0010]\u001a\u00020\u0018H\u0082 J!\u0010^\u001a\u00020!2\u0006\u0010D\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020\u0018H\u0082 J!\u0010_\u001a\u00020!2\u0006\u0010D\u001a\u00020\u00172\u0006\u0010`\u001a\u00020\u00182\u0006\u0010a\u001a\u00020\u0018H\u0082 J\u0011\u0010b\u001a\u00020!2\u0006\u0010D\u001a\u00020\u0017H\u0082 J\u0006\u0010c\u001a\u00020!J\u0006\u0010d\u001a\u00020!J\u000e\u0010e\u001a\u00020!2\u0006\u0010f\u001a\u00020\u0015J\u001e\u0010g\u001a\u00020!2\u0006\u0010V\u001a\u00020\u00182\u0006\u0010W\u001a\u00020\u00182\u0006\u0010X\u001a\u00020\u0006J\u001e\u0010h\u001a\u00020!2\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u00182\u0006\u0010Z\u001a\u00020\u0018J\u0016\u0010i\u001a\u00020!2\u0006\u0010\\\u001a\u00020\u00182\u0006\u0010]\u001a\u00020\u0018J\u000e\u0010j\u001a\u00020!2\u0006\u0010Q\u001a\u00020\u0006J\u000e\u0010k\u001a\u00020!2\u0006\u0010l\u001a\u00020\u0006J\u000e\u0010m\u001a\u00020!2\u0006\u0010n\u001a\u00020\u0018J\u0006\u0010o\u001a\u00020!J\u0006\u0010p\u001a\u00020!J!\u0010q\u001a\u00020!\"\u0004\b\u0000\u0010r*\u00020\u001f2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002Hr0>H\u0082\bR\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0018@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001e\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0018@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006u"}, d2 = {"Lcom/gaurav/avnc/vnc/VncClient;", "", "observer", "Lcom/gaurav/avnc/vnc/VncClient$Observer;", "(Lcom/gaurav/avnc/vnc/VncClient$Observer;)V", "<set-?>", "", "connected", "getConnected", "()Z", "destroyed", "frameBufferUpdatesPaused", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getFrameBufferUpdatesPaused", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "ignorePointerMovesByServer", "getIgnorePointerMovesByServer", "setIgnorePointerMovesByServer", "(Z)V", "inputDisabled", "lastCutText", "", "nativePtr", "", "", "pointerX", "getPointerX", "()I", "pointerY", "getPointerY", "stateLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "cbBell", "", "cbFinishedFrameBufferUpdate", "cbFramebufferSizeChanged", "w", "h", "cbGetCredential", "Lcom/gaurav/avnc/vnc/UserCredential;", "cbGetPassword", "cbGotXCutText", "bytes", "", "isUTF8", "cbHandleCursorPos", "x", "y", "cbVerifyServerCertificate", "der", "cleanup", "configure", "securityType", "useLocalCursor", "imageQuality", "useRawEncoding", MqttConnectHandler.NAME, "host", "port", "getDesktopName", "ifConnected", "block", "Lkotlin/Function0;", "ifConnectedAndInteractive", "interrupt", "isConnectedToMacOS", "moveClientPointer", "nativeCleanup", "clientPtr", "nativeClientCreate", "nativeConfigure", "nativeGetDesktopName", "nativeGetHeight", "nativeGetLastErrorStr", "nativeGetWidth", "nativeInit", "nativeInterrupt", "nativeIsEncrypted", "nativeIsServerMacOS", "nativeIsUTF8CutTextSupported", "nativePauseFramebufferUpdates", "pause", "nativeProcessServerMessage", "nativeRefreshFrameBuffer", "nativeSendCutText", "nativeSendKeyEvent", "keySym", "xtCode", "isDown", "nativeSendPointerEvent", "mask", "nativeSetDesktopSize", "width", "height", "nativeSetDest", "nativeUploadCursor", "px", "py", "nativeUploadFrameTexture", "processServerMessage", "refreshFrameBuffer", "sendCutText", "text", "sendKeyEvent", "sendPointerEvent", "setDesktopSize", "setFrameBufferUpdatesPaused", "setInputDisabled", CompilerOptions.DISABLED, "setupRepeater", "serverId", "uploadCursor", "uploadFrameTexture", "tryRead", ExifInterface.GPS_DIRECTION_TRUE, "Companion", "Observer", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VncClient {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private volatile boolean connected;
    private boolean destroyed;
    private final AtomicBoolean frameBufferUpdatesPaused;
    private volatile boolean ignorePointerMovesByServer;
    private final AtomicBoolean inputDisabled;
    private volatile String lastCutText;
    private final long nativePtr;
    private final Observer observer;
    private int pointerX;
    private int pointerY;
    private final ReentrantReadWriteLock stateLock;

    /* JADX INFO: compiled from: VncClient.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J\b\u0010\t\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\fH&J\u0018\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H&J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&¨\u0006\u0015"}, d2 = {"Lcom/gaurav/avnc/vnc/VncClient$Observer;", "", "onCredentialRequired", "Lcom/gaurav/avnc/vnc/UserCredential;", "onFramebufferSizeChanged", "", "width", "", "height", "onFramebufferUpdated", "onGotXCutText", "text", "", "onPasswordRequired", "onPointerMoved", "x", "y", "onVerifyCertificate", "", "certificate", "Ljava/security/cert/X509Certificate;", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Observer {
        UserCredential onCredentialRequired();

        void onFramebufferSizeChanged(int width, int height);

        void onFramebufferUpdated();

        void onGotXCutText(String text);

        String onPasswordRequired();

        void onPointerMoved(int x, int y);

        boolean onVerifyCertificate(X509Certificate certificate);
    }

    private final void cbBell() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @JvmStatic
    public static final native void initLibrary();

    private final native void nativeCleanup(long clientPtr);

    private final native long nativeClientCreate();

    private final native void nativeConfigure(long clientPtr, int securityType, boolean useLocalCursor, int imageQuality, boolean useRawEncoding);

    private final native String nativeGetDesktopName(long clientPtr);

    private final native int nativeGetHeight(long clientPtr);

    private final native String nativeGetLastErrorStr();

    private final native int nativeGetWidth(long clientPtr);

    private final native boolean nativeInit(long clientPtr, String host, int port);

    private final native void nativeInterrupt(long clientPtr);

    private final native boolean nativeIsEncrypted(long clientPtr);

    private final native boolean nativeIsServerMacOS(long clientPtr);

    private final native boolean nativeIsUTF8CutTextSupported(long clientPtr);

    private final native void nativePauseFramebufferUpdates(long clientPtr, boolean pause);

    private final native boolean nativeProcessServerMessage(long clientPtr);

    private final native boolean nativeRefreshFrameBuffer(long clientPtr);

    private final native boolean nativeSendCutText(long clientPtr, byte[] bytes, boolean isUTF8);

    private final native boolean nativeSendKeyEvent(long clientPtr, int keySym, int xtCode, boolean isDown);

    private final native boolean nativeSendPointerEvent(long clientPtr, int x, int y, int mask);

    private final native boolean nativeSetDesktopSize(long clientPtr, int width, int height);

    private final native void nativeSetDest(long clientPtr, String host, int port);

    private final native void nativeUploadCursor(long clientPtr, int px, int py);

    private final native void nativeUploadFrameTexture(long clientPtr);

    public VncClient(Observer observer) {
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.observer = observer;
        long jNativeClientCreate = nativeClientCreate();
        this.nativePtr = jNativeClientCreate;
        if (jNativeClientCreate == 0) {
            throw new RuntimeException("Could not create native rfbClient!");
        }
        this.stateLock = new ReentrantReadWriteLock();
        this.inputDisabled = new AtomicBoolean(false);
        this.frameBufferUpdatesPaused = new AtomicBoolean(false);
    }

    public final boolean getConnected() {
        return this.connected;
    }

    public final AtomicBoolean getFrameBufferUpdatesPaused() {
        return this.frameBufferUpdatesPaused;
    }

    public final int getPointerX() {
        return this.pointerX;
    }

    public final int getPointerY() {
        return this.pointerY;
    }

    public final boolean getIgnorePointerMovesByServer() {
        return this.ignorePointerMovesByServer;
    }

    public final void setIgnorePointerMovesByServer(boolean z) {
        this.ignorePointerMovesByServer = z;
    }

    public final void configure(int securityType, boolean useLocalCursor, int imageQuality, boolean useRawEncoding) {
        ReentrantReadWriteLock.ReadLock lock = this.stateLock.readLock();
        lock.lock();
        try {
            if (!this.connected && !this.destroyed) {
                nativeConfigure(this.nativePtr, securityType, useLocalCursor, imageQuality, useRawEncoding);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            lock.unlock();
        }
    }

    public final void setupRepeater(int serverId) {
        ReentrantReadWriteLock.ReadLock lock = this.stateLock.readLock();
        lock.lock();
        try {
            if (!this.connected && !this.destroyed) {
                nativeSetDest(this.nativePtr, SchemaSymbols.ATTVAL_ID, serverId);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            lock.unlock();
        }
    }

    public final void connect(String host, int port) {
        Intrinsics.checkNotNullParameter(host, "host");
        ReentrantReadWriteLock.ReadLock lock = this.stateLock.readLock();
        lock.lock();
        try {
            if (this.connected) {
                throw new IllegalStateException("Already connected".toString());
            }
            if (this.destroyed) {
                throw new IllegalStateException("Client has been destroyed".toString());
            }
            if (!nativeInit(this.nativePtr, host, port)) {
                throw new IOException(nativeGetLastErrorStr());
            }
            Unit unit = Unit.INSTANCE;
            lock.unlock();
            ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
            ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
            int i = 0;
            int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
            for (int i2 = 0; i2 < readHoldCount; i2++) {
                lock2.unlock();
            }
            ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                if (!this.destroyed) {
                    this.connected = true;
                }
                Unit unit2 = Unit.INSTANCE;
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
            } finally {
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
                writeLock.unlock();
            }
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    public final void processServerMessage() {
        ReentrantReadWriteLock.ReadLock lock = this.stateLock.readLock();
        lock.lock();
        try {
            if (this.connected && nativeProcessServerMessage(this.nativePtr)) {
                lock.unlock();
                return;
            }
            Unit unit = Unit.INSTANCE;
            lock.unlock();
            ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
            ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
            int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
            for (int i = 0; i < readHoldCount; i++) {
                lock2.unlock();
            }
            ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                this.connected = false;
                throw new IOException(nativeGetLastErrorStr());
            } catch (Throwable th) {
                for (int i2 = 0; i2 < readHoldCount; i2++) {
                    lock2.lock();
                }
                writeLock.unlock();
                throw th;
            }
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    public final void setInputDisabled(boolean disabled) {
        this.inputDisabled.set(disabled);
    }

    public final void setFrameBufferUpdatesPaused(boolean pause) {
        ReentrantReadWriteLock.ReadLock lock = this.stateLock.readLock();
        lock.lock();
        try {
            if (!this.destroyed && this.frameBufferUpdatesPaused.compareAndSet(!pause, pause)) {
                nativePauseFramebufferUpdates(this.nativePtr, pause);
                refreshFrameBuffer();
                Unit unit = Unit.INSTANCE;
            }
        } finally {
            lock.unlock();
        }
    }

    public final void interrupt() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (!this.destroyed) {
                    nativeInterrupt(this.nativePtr);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void cleanup() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (!this.destroyed) {
                nativeCleanup(this.nativePtr);
                this.connected = false;
                this.destroyed = true;
            }
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
        } finally {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    private final void ifConnected(Function0<Unit> block) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed) {
                    block.invoke();
                }
            } finally {
                InlineMarker.finallyStart(1);
                reentrantReadWriteLock.readLock().unlock();
                InlineMarker.finallyEnd(1);
            }
        }
    }

    private final <T> void tryRead(ReentrantReadWriteLock reentrantReadWriteLock, Function0<? extends T> function0) {
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                function0.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                reentrantReadWriteLock.readLock().unlock();
                InlineMarker.finallyEnd(1);
            }
        }
    }

    private final String cbGetPassword() {
        return this.observer.onPasswordRequired();
    }

    private final UserCredential cbGetCredential() {
        return this.observer.onCredentialRequired();
    }

    private final boolean cbVerifyServerCertificate(byte[] der) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(der);
        try {
            Certificate certificateGenerateCertificate = CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream);
            CloseableKt.closeFinally(byteArrayInputStream, null);
            Observer observer = this.observer;
            Intrinsics.checkNotNull(certificateGenerateCertificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
            return observer.onVerifyCertificate((X509Certificate) certificateGenerateCertificate);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(byteArrayInputStream, th);
                throw th2;
            }
        }
    }

    private final void cbGotXCutText(byte[] bytes, boolean isUTF8) {
        String string = (isUTF8 ? StandardCharsets.UTF_8 : StandardCharsets.ISO_8859_1).decode(ByteBuffer.wrap(bytes)).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        if (Intrinsics.areEqual(string, this.lastCutText)) {
            return;
        }
        this.lastCutText = string;
        this.observer.onGotXCutText(string);
    }

    private final void cbFinishedFrameBufferUpdate() {
        this.observer.onFramebufferUpdated();
    }

    private final void cbFramebufferSizeChanged(int w, int h) {
        this.observer.onFramebufferSizeChanged(w, h);
    }

    private final void cbHandleCursorPos(int x, int y) {
        if (this.ignorePointerMovesByServer) {
            return;
        }
        moveClientPointer(x, y);
    }

    /* JADX INFO: compiled from: VncClient.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0004H\u0083 J\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"Lcom/gaurav/avnc/vnc/VncClient$Companion;", "", "()V", "initLibrary", "", "loadLibrary", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @JvmStatic
        public final void initLibrary() {
            VncClient.initLibrary();
        }

        private Companion() {
        }

        public final void loadLibrary() {
            System.loadLibrary("native-vnc");
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        companion.initLibrary();
    }

    public final String getDesktopName() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (!reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            return "";
        }
        try {
            if (this.connected && !this.destroyed) {
                return nativeGetDesktopName(this.nativePtr);
            }
            return "";
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public final boolean isConnectedToMacOS() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (!reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            return false;
        }
        try {
            if (!this.connected || this.destroyed) {
                return false;
            }
            return nativeIsServerMacOS(this.nativePtr);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public final void sendKeyEvent(int keySym, int xtCode, boolean isDown) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed && !this.inputDisabled.get()) {
                    nativeSendKeyEvent(this.nativePtr, keySym, xtCode, isDown);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void sendPointerEvent(int x, int y, int mask) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed && !this.inputDisabled.get()) {
                    nativeSendPointerEvent(this.nativePtr, x, y, mask);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void moveClientPointer(int x, int y) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed) {
                    this.pointerX = x;
                    this.pointerY = y;
                    this.observer.onPointerMoved(x, y);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void sendCutText(String text) {
        boolean zNativeSendCutText;
        Intrinsics.checkNotNullParameter(text, "text");
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed && !this.inputDisabled.get() && !Intrinsics.areEqual(text, this.lastCutText)) {
                    if (nativeIsUTF8CutTextSupported(this.nativePtr)) {
                        long j = this.nativePtr;
                        Charset UTF_8 = StandardCharsets.UTF_8;
                        Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                        byte[] bytes = text.getBytes(UTF_8);
                        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                        zNativeSendCutText = nativeSendCutText(j, bytes, true);
                    } else {
                        long j2 = this.nativePtr;
                        Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
                        Intrinsics.checkNotNullExpressionValue(ISO_8859_1, "ISO_8859_1");
                        byte[] bytes2 = text.getBytes(ISO_8859_1);
                        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                        zNativeSendCutText = nativeSendCutText(j2, bytes2, false);
                    }
                    if (zNativeSendCutText) {
                        this.lastCutText = text;
                    }
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void setDesktopSize(int width, int height) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed && width > 0 && height > 0) {
                    nativeSetDesktopSize(this.nativePtr, width, height);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void refreshFrameBuffer() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed && !this.frameBufferUpdatesPaused.get()) {
                    nativeRefreshFrameBuffer(this.nativePtr);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void uploadFrameTexture() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed) {
                    nativeUploadFrameTexture(this.nativePtr);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    public final void uploadCursor() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed) {
                    nativeUploadCursor(this.nativePtr, this.pointerX, this.pointerY);
                }
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    private final void ifConnectedAndInteractive(Function0<Unit> block) {
        ReentrantReadWriteLock reentrantReadWriteLock = this.stateLock;
        if (reentrantReadWriteLock.readLock().tryLock(0L, TimeUnit.SECONDS)) {
            try {
                if (this.connected && !this.destroyed && !this.inputDisabled.get()) {
                    block.invoke();
                }
            } finally {
                InlineMarker.finallyStart(1);
                reentrantReadWriteLock.readLock().unlock();
                InlineMarker.finallyEnd(1);
            }
        }
    }
}
