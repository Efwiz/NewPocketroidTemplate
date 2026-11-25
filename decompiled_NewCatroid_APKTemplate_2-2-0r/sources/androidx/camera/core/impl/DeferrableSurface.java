package androidx.camera.core.impl;

import android.util.Log;
import android.view.Surface;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class DeferrableSurface {
    private CallbackToFutureAdapter.Completer<Void> mTerminationCompleter;
    private final ListenableFuture<Void> mTerminationFuture;
    private static final String TAG = "DeferrableSurface";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static AtomicInteger sUsedCount = new AtomicInteger(0);
    private static AtomicInteger sTotalCount = new AtomicInteger(0);
    private final Object mLock = new Object();
    private int mUseCount = 0;
    private boolean mClosed = false;

    protected abstract ListenableFuture<Surface> provideSurface();

    public static final class SurfaceUnavailableException extends Exception {
        public SurfaceUnavailableException(String str) {
            super(str);
        }
    }

    public static final class SurfaceClosedException extends Exception {
        DeferrableSurface mDeferrableSurface;

        public SurfaceClosedException(String str, DeferrableSurface deferrableSurface) {
            super(str);
            this.mDeferrableSurface = deferrableSurface;
        }

        public DeferrableSurface getDeferrableSurface() {
            return this.mDeferrableSurface;
        }
    }

    public DeferrableSurface() {
        ListenableFuture<Void> future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.DeferrableSurface$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m103lambda$new$0$androidxcameracoreimplDeferrableSurface(completer);
            }
        });
        this.mTerminationFuture = future;
        if (DEBUG) {
            printGlobalDebugCounts("Surface created", sTotalCount.incrementAndGet(), sUsedCount.get());
            final String stackTraceString = Log.getStackTraceString(new Exception());
            future.addListener(new Runnable() { // from class: androidx.camera.core.impl.DeferrableSurface$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m104lambda$new$1$androidxcameracoreimplDeferrableSurface(stackTraceString);
                }
            }, CameraXExecutors.directExecutor());
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$androidx-camera-core-impl-DeferrableSurface, reason: not valid java name */
    /* synthetic */ Object m103lambda$new$0$androidxcameracoreimplDeferrableSurface(CallbackToFutureAdapter.Completer completer) throws Exception {
        synchronized (this.mLock) {
            this.mTerminationCompleter = completer;
        }
        return "DeferrableSurface-termination(" + this + Constant.AFTER_QUTO;
    }

    /* JADX INFO: renamed from: lambda$new$1$androidx-camera-core-impl-DeferrableSurface, reason: not valid java name */
    /* synthetic */ void m104lambda$new$1$androidxcameracoreimplDeferrableSurface(String str) {
        try {
            this.mTerminationFuture.get();
            printGlobalDebugCounts("Surface terminated", sTotalCount.decrementAndGet(), sUsedCount.get());
        } catch (Exception e) {
            Log.e(TAG, "Unexpected surface termination for " + this + "\nStack Trace:\n" + str);
            throw new IllegalArgumentException("DeferrableSurface terminated with unexpected exception.", e);
        }
    }

    private void printGlobalDebugCounts(String str, int i, int i2) {
        Log.d(TAG, str + "[total_surfaces=" + i + ", used_surfaces=" + i2 + "](" + this + "}");
    }

    public final ListenableFuture<Surface> getSurface() {
        synchronized (this.mLock) {
            if (this.mClosed) {
                return Futures.immediateFailedFuture(new SurfaceClosedException("DeferrableSurface already closed.", this));
            }
            return provideSurface();
        }
    }

    public ListenableFuture<Void> getTerminationFuture() {
        return Futures.nonCancellationPropagating(this.mTerminationFuture);
    }

    public void incrementUseCount() throws SurfaceClosedException {
        synchronized (this.mLock) {
            int i = this.mUseCount;
            if (i == 0 && this.mClosed) {
                throw new SurfaceClosedException("Cannot begin use on a closed surface.", this);
            }
            int i2 = i + 1;
            this.mUseCount = i2;
            if (DEBUG) {
                if (i2 == 1) {
                    printGlobalDebugCounts("New surface in use", sTotalCount.get(), sUsedCount.incrementAndGet());
                }
                Log.d(TAG, "use count+1, useCount=" + this.mUseCount + " " + this);
            }
        }
    }

    public final void close() {
        CallbackToFutureAdapter.Completer<Void> completer;
        synchronized (this.mLock) {
            if (this.mClosed) {
                completer = null;
            } else {
                this.mClosed = true;
                if (this.mUseCount == 0) {
                    completer = this.mTerminationCompleter;
                    this.mTerminationCompleter = null;
                } else {
                    completer = null;
                }
                if (DEBUG) {
                    Log.d(TAG, "surface closed,  useCount=" + this.mUseCount + " closed=true " + this);
                }
            }
        }
        if (completer != null) {
            completer.set(null);
        }
    }

    public void decrementUseCount() {
        CallbackToFutureAdapter.Completer<Void> completer;
        synchronized (this.mLock) {
            int i = this.mUseCount;
            if (i == 0) {
                throw new IllegalStateException("Decrementing use count occurs more times than incrementing");
            }
            int i2 = i - 1;
            this.mUseCount = i2;
            if (i2 == 0 && this.mClosed) {
                completer = this.mTerminationCompleter;
                this.mTerminationCompleter = null;
            } else {
                completer = null;
            }
            boolean z = DEBUG;
            if (z) {
                Log.d(TAG, "use count-1,  useCount=" + this.mUseCount + " closed=" + this.mClosed + " " + this);
                if (this.mUseCount == 0 && z) {
                    printGlobalDebugCounts("Surface no longer in use", sTotalCount.get(), sUsedCount.decrementAndGet());
                }
            }
        }
        if (completer != null) {
            completer.set(null);
        }
    }

    public int getUseCount() {
        int i;
        synchronized (this.mLock) {
            i = this.mUseCount;
        }
        return i;
    }
}
