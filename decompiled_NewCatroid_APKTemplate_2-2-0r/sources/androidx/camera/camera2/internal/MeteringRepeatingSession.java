package androidx.camera.camera2.internal;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;

/* JADX INFO: loaded from: classes.dex */
class MeteringRepeatingSession {
    private DeferrableSurface mDeferrableSurface;
    private final SessionConfig mSessionConfig;
    private static final String TAG = "MeteringRepeating";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    MeteringRepeatingSession() {
        MeteringRepeatingConfig meteringRepeatingConfig = new MeteringRepeatingConfig();
        final SurfaceTexture surfaceTexture = new SurfaceTexture(0);
        surfaceTexture.setDefaultBufferSize(0, 0);
        final Surface surface = new Surface(surfaceTexture);
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(meteringRepeatingConfig);
        builderCreateFrom.setTemplateType(1);
        ImmediateSurface immediateSurface = new ImmediateSurface(surface);
        this.mDeferrableSurface = immediateSurface;
        Futures.addCallback(immediateSurface.getTerminationFuture(), new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.MeteringRepeatingSession.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Void r1) {
                surface.release();
                surfaceTexture.release();
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                throw new IllegalStateException("Future should never fail. Did it get completed by GC?", th);
            }
        }, CameraXExecutors.directExecutor());
        builderCreateFrom.addSurface(this.mDeferrableSurface);
        this.mSessionConfig = builderCreateFrom.build();
    }

    SessionConfig getSessionConfig() {
        return this.mSessionConfig;
    }

    String getName() {
        return TAG;
    }

    void clear() {
        if (DEBUG) {
            Log.d(TAG, "MeteringRepeating clear!");
        }
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        this.mDeferrableSurface = null;
    }

    private static class MeteringRepeatingConfig implements UseCaseConfig<UseCase> {
        private final Config mConfig;

        MeteringRepeatingConfig() {
            MutableOptionsBundle mutableOptionsBundleCreate = MutableOptionsBundle.create();
            mutableOptionsBundleCreate.insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, new Camera2SessionOptionUnpacker());
            this.mConfig = mutableOptionsBundleCreate;
        }

        @Override // androidx.camera.core.impl.ReadableConfig
        public Config getConfig() {
            return this.mConfig;
        }
    }
}
