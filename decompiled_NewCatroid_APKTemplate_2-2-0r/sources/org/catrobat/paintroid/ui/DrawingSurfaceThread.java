package org.catrobat.paintroid.ui;

import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DrawingSurfaceThread.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000f\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/ui/DrawingSurfaceThread;", "", "drawingSurface", "Lorg/catrobat/paintroid/ui/DrawingSurface;", "threadRunnable", "Ljava/lang/Runnable;", "(Lorg/catrobat/paintroid/ui/DrawingSurface;Ljava/lang/Runnable;)V", "internalThread", "Ljava/lang/Thread;", "running", "", "internalRun", "", "start", "stop", "Companion", "InternalRunnable", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DrawingSurfaceThread {
    private static final String TAG = "DrawingSurfaceThread";
    private final DrawingSurface drawingSurface;
    private final Thread internalThread;
    private boolean running;
    private final Runnable threadRunnable;

    public DrawingSurfaceThread(DrawingSurface drawingSurface, Runnable threadRunnable) {
        Intrinsics.checkNotNullParameter(drawingSurface, "drawingSurface");
        Intrinsics.checkNotNullParameter(threadRunnable, "threadRunnable");
        this.drawingSurface = drawingSurface;
        this.threadRunnable = threadRunnable;
        Thread thread = new Thread(new InternalRunnable(this), "DrawingSurfaceThread");
        this.internalThread = thread;
        thread.setDaemon(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void internalRun() {
        while (this.running) {
            this.threadRunnable.run();
        }
    }

    public final synchronized void start() {
        String str = TAG;
        Log.d(str, "DrawingSurfaceThread.start");
        if (!this.running && this.internalThread.getState() != Thread.State.TERMINATED) {
            if (!this.internalThread.isAlive()) {
                this.running = true;
                this.internalThread.start();
            }
            this.drawingSurface.refreshDrawingSurface();
            return;
        }
        Log.d(str, "DrawingSurfaceThread.start returning");
    }

    public final synchronized void stop() {
        String str = TAG;
        Log.d(str, "DrawingSurfaceThread.stop");
        this.running = false;
        Log.d(str, "DrawingSurfaceThread.join");
        while (this.internalThread.isAlive()) {
            try {
                this.internalThread.interrupt();
                this.internalThread.join();
                Log.d(TAG, "DrawingSurfaceThread.stopped");
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrupt while joining DrawingSurfaceThread\n", e);
            }
        }
    }

    /* JADX INFO: compiled from: DrawingSurfaceThread.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/ui/DrawingSurfaceThread$InternalRunnable;", "Ljava/lang/Runnable;", "(Lorg/catrobat/paintroid/ui/DrawingSurfaceThread;)V", "run", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private final class InternalRunnable implements Runnable {
        final /* synthetic */ DrawingSurfaceThread this$0;

        public InternalRunnable(DrawingSurfaceThread this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // java.lang.Runnable
        public void run() {
            Thread.yield();
            this.this$0.internalRun();
        }
    }
}
