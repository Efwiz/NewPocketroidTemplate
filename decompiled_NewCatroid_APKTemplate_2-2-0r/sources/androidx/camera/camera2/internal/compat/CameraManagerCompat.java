package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class CameraManagerCompat {
    private final CameraManagerCompatImpl mImpl;

    interface CameraManagerCompatImpl {
        CameraCharacteristics getCameraCharacteristics(String str) throws CameraAccessExceptionCompat;

        String[] getCameraIdList() throws CameraAccessExceptionCompat;

        CameraManager getCameraManager();

        void openCamera(String str, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessExceptionCompat;

        void registerAvailabilityCallback(Executor executor, CameraManager.AvailabilityCallback availabilityCallback);

        void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback availabilityCallback);
    }

    private CameraManagerCompat(CameraManagerCompatImpl cameraManagerCompatImpl) {
        this.mImpl = cameraManagerCompatImpl;
    }

    public static CameraManagerCompat from(Context context) {
        return from(context, MainThreadAsyncHandler.getInstance());
    }

    public static CameraManagerCompat from(Context context, Handler handler) {
        if (Build.VERSION.SDK_INT >= 29) {
            return new CameraManagerCompat(new CameraManagerCompatApi29Impl(context));
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return new CameraManagerCompat(CameraManagerCompatApi28Impl.create(context));
        }
        return new CameraManagerCompat(CameraManagerCompatBaseImpl.create(context, handler));
    }

    public String[] getCameraIdList() throws CameraAccessExceptionCompat {
        return this.mImpl.getCameraIdList();
    }

    public void registerAvailabilityCallback(Executor executor, CameraManager.AvailabilityCallback availabilityCallback) {
        this.mImpl.registerAvailabilityCallback(executor, availabilityCallback);
    }

    public void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback availabilityCallback) {
        this.mImpl.unregisterAvailabilityCallback(availabilityCallback);
    }

    public CameraCharacteristics getCameraCharacteristics(String str) throws CameraAccessExceptionCompat {
        return this.mImpl.getCameraCharacteristics(str);
    }

    public void openCamera(String str, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessExceptionCompat {
        this.mImpl.openCamera(str, executor, stateCallback);
    }

    public CameraManager unwrap() {
        return this.mImpl.getCameraManager();
    }

    static final class AvailabilityCallbackExecutorWrapper extends CameraManager.AvailabilityCallback {
        private final Executor mExecutor;
        final CameraManager.AvailabilityCallback mWrappedCallback;
        private final Object mLock = new Object();
        private boolean mDisabled = false;

        AvailabilityCallbackExecutorWrapper(Executor executor, CameraManager.AvailabilityCallback availabilityCallback) {
            this.mExecutor = executor;
            this.mWrappedCallback = availabilityCallback;
        }

        void setDisabled() {
            synchronized (this.mLock) {
                this.mDisabled = true;
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraAccessPrioritiesChanged() {
            synchronized (this.mLock) {
                if (!this.mDisabled) {
                    this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.CameraManagerCompat.AvailabilityCallbackExecutorWrapper.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AvailabilityCallbackExecutorWrapper.this.mWrappedCallback.onCameraAccessPrioritiesChanged();
                        }
                    });
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraAvailable(final String str) {
            synchronized (this.mLock) {
                if (!this.mDisabled) {
                    this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.CameraManagerCompat.AvailabilityCallbackExecutorWrapper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AvailabilityCallbackExecutorWrapper.this.mWrappedCallback.onCameraAvailable(str);
                        }
                    });
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraUnavailable(final String str) {
            synchronized (this.mLock) {
                if (!this.mDisabled) {
                    this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.CameraManagerCompat.AvailabilityCallbackExecutorWrapper.3
                        @Override // java.lang.Runnable
                        public void run() {
                            AvailabilityCallbackExecutorWrapper.this.mWrappedCallback.onCameraUnavailable(str);
                        }
                    });
                }
            }
        }
    }
}
