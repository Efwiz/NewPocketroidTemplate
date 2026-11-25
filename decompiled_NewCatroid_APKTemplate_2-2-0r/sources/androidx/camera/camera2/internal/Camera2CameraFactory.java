package androidx.camera.camera2.internal;

import android.content.Context;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraStateRegistry;
import androidx.camera.core.impl.CameraThreadConfig;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2CameraFactory implements CameraFactory {
    private static final int DEFAULT_ALLOWED_CONCURRENT_OPEN_CAMERAS = 1;
    private final CameraManagerCompat mCameraManager;
    private final CameraStateRegistry mCameraStateRegistry = new CameraStateRegistry(1);
    private final CameraThreadConfig mThreadConfig;

    public Camera2CameraFactory(Context context, CameraThreadConfig cameraThreadConfig) {
        this.mThreadConfig = cameraThreadConfig;
        this.mCameraManager = CameraManagerCompat.from(context, cameraThreadConfig.getSchedulerHandler());
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public CameraInternal getCamera(String str) throws CameraUnavailableException {
        if (!getAvailableCameraIds().contains(str)) {
            throw new IllegalArgumentException("The given camera id is not on the available camera id list.");
        }
        return new Camera2CameraImpl(this.mCameraManager, str, this.mCameraStateRegistry, this.mThreadConfig.getCameraExecutor(), this.mThreadConfig.getSchedulerHandler());
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public Set<String> getAvailableCameraIds() throws CameraUnavailableException {
        try {
            return new LinkedHashSet(Arrays.asList(this.mCameraManager.getCameraIdList()));
        } catch (CameraAccessExceptionCompat e) {
            throw CameraUnavailableExceptionHelper.createFrom(e);
        }
    }
}
