package com.gaurav.avnc.vnc;

import android.graphics.PointF;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: Messenger.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0010\u001a\u00020\fJ\u0006\u0010\u0011\u001a\u00020\fJ\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014J\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0016J\u0016\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0016\u0010 \u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0018\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0016\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006J\u000e\u0010&\u001a\u00020\f2\u0006\u0010'\u001a\u00020\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lcom/gaurav/avnc/vnc/Messenger;", "", "client", "Lcom/gaurav/avnc/vnc/VncClient;", "(Lcom/gaurav/avnc/vnc/VncClient;)V", "pointerButtonMask", "", "sender", "Ljava/util/concurrent/ExecutorService;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "senderLock", "cleanup", "", "execute", "action", "Ljava/lang/Runnable;", "insertButtonUpDelay", "refreshFrameBuffer", "sendClipboardText", "text", "", "sendKey", "", "keySym", "xtCode", "isDown", "sendPointerButtonDown", "button", "Lcom/gaurav/avnc/vnc/PointerButton;", "p", "Landroid/graphics/PointF;", "sendPointerButtonRelease", "sendPointerButtonUp", "sendPointerEvent", "mask", "setDesktopSize", "width", "height", "setFrameBufferUpdatesPaused", "pause", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Messenger {
    private final VncClient client;
    private int pointerButtonMask;
    private final ExecutorService sender;
    private final Object senderLock;

    public Messenger(VncClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        this.client = client;
        this.sender = Executors.newSingleThreadExecutor();
        this.senderLock = new Object();
    }

    private final void execute(Runnable action) {
        synchronized (this.senderLock) {
            if (!this.sender.isShutdown()) {
                this.sender.execute(action);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void cleanup() {
        synchronized (this.senderLock) {
            this.sender.shutdown();
            Unit unit = Unit.INSTANCE;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            Messenger messenger = this;
            Result.m2898constructorimpl(Boolean.valueOf(this.sender.awaitTermination(60L, TimeUnit.SECONDS)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        if (this.sender.isTerminated()) {
            return;
        }
        Log.w(getClass().getSimpleName(), "Unable to fully stop Sender thread!");
    }

    private final void sendPointerEvent(final int mask, PointF p) {
        final int i = (int) p.x;
        final int i2 = (int) p.y;
        this.client.moveClientPointer(i, i2);
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.sendPointerEvent$lambda$3(this.f$0, i, i2, mask);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendPointerEvent$lambda$3(Messenger this$0, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.client.sendPointerEvent(i, i2, i3);
    }

    public final void sendPointerButtonDown(PointerButton button, PointF p) {
        Intrinsics.checkNotNullParameter(button, "button");
        Intrinsics.checkNotNullParameter(p, "p");
        int bitMask = button.getBitMask() | this.pointerButtonMask;
        this.pointerButtonMask = bitMask;
        sendPointerEvent(bitMask, p);
    }

    public final void sendPointerButtonUp(PointerButton button, PointF p) {
        Intrinsics.checkNotNullParameter(button, "button");
        Intrinsics.checkNotNullParameter(p, "p");
        int i = (~button.getBitMask()) & this.pointerButtonMask;
        this.pointerButtonMask = i;
        sendPointerEvent(i, p);
    }

    public final void sendPointerButtonRelease(PointF p) {
        Intrinsics.checkNotNullParameter(p, "p");
        if (this.pointerButtonMask != 0) {
            this.pointerButtonMask = 0;
            sendPointerEvent(0, p);
        }
    }

    public final boolean sendKey(final int keySym, final int xtCode, final boolean isDown) {
        if (!this.client.getConnected()) {
            return false;
        }
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.sendKey$lambda$4(this.f$0, keySym, xtCode, isDown);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendKey$lambda$4(Messenger this$0, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.client.sendKeyEvent(i, i2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void insertButtonUpDelay$lambda$6(Messenger this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            Result.Companion companion = Result.INSTANCE;
            Thread.sleep(200L);
            Result.m2898constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
    }

    public final void insertButtonUpDelay() {
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.insertButtonUpDelay$lambda$6(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendClipboardText$lambda$7(Messenger this$0, String text) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(text, "$text");
        this$0.client.sendCutText(text);
    }

    public final void sendClipboardText(final String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.sendClipboardText$lambda$7(this.f$0, text);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setDesktopSize$lambda$8(Messenger this$0, int i, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.client.setDesktopSize(i, i2);
    }

    public final void setDesktopSize(final int width, final int height) {
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.setDesktopSize$lambda$8(this.f$0, width, height);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshFrameBuffer$lambda$9(Messenger this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.client.refreshFrameBuffer();
    }

    public final void refreshFrameBuffer() {
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.refreshFrameBuffer$lambda$9(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setFrameBufferUpdatesPaused$lambda$10(Messenger this$0, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.client.setFrameBufferUpdatesPaused(z);
    }

    public final void setFrameBufferUpdatesPaused(final boolean pause) {
        execute(new Runnable() { // from class: com.gaurav.avnc.vnc.Messenger$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Messenger.setFrameBufferUpdatesPaused$lambda$10(this.f$0, pause);
            }
        });
    }
}
