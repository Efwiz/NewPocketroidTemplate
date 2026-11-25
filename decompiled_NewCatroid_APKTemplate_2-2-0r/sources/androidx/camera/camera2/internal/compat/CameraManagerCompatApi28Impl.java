package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class CameraManagerCompatApi28Impl extends CameraManagerCompatBaseImpl {
    CameraManagerCompatApi28Impl(Context context, Object obj) {
        super(context, obj);
    }

    static CameraManagerCompatApi28Impl create(Context context) {
        return new CameraManagerCompatApi28Impl(context, new CameraManagerCompatParamsApi28());
    }

    @Override // androidx.camera.camera2.internal.compat.CameraManagerCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraManagerCompat.CameraManagerCompatImpl
    public void registerAvailabilityCallback(Executor executor, CameraManager.AvailabilityCallback availabilityCallback) {
        this.mCameraManager.registerAvailabilityCallback(executor, availabilityCallback);
    }

    @Override // androidx.camera.camera2.internal.compat.CameraManagerCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraManagerCompat.CameraManagerCompatImpl
    public void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback availabilityCallback) {
        this.mCameraManager.unregisterAvailabilityCallback(availabilityCallback);
    }

    @Override // androidx.camera.camera2.internal.compat.CameraManagerCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraManagerCompat.CameraManagerCompatImpl
    public void openCamera(String str, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessExceptionCompat {
        try {
            this.mCameraManager.openCamera(str, executor, stateCallback);
        } catch (CameraAccessException e) {
            throw CameraAccessExceptionCompat.toCameraAccessExceptionCompat(e);
        } catch (IllegalArgumentException e2) {
            throw e2;
        } catch (SecurityException e3) {
        } catch (RuntimeException e4) {
            if (isDndFailCase(e4)) {
                throwDndException(e4);
            }
            throw e4;
        }
    }

    @Override // androidx.camera.camera2.internal.compat.CameraManagerCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraManagerCompat.CameraManagerCompatImpl
    public CameraCharacteristics getCameraCharacteristics(String str) throws CameraAccessExceptionCompat {
        try {
            CameraCharacteristics cameraCharacteristics = super.getCameraCharacteristics(str);
            if (Build.VERSION.SDK_INT == 28) {
                synchronized (CameraManagerCompatParamsApi28.sCameraCharacteristicsMap) {
                    if (!CameraManagerCompatParamsApi28.sCameraCharacteristicsMap.containsKey(str)) {
                        CameraManagerCompatParamsApi28.sCameraCharacteristicsMap.put(str, cameraCharacteristics);
                    }
                }
            }
            return cameraCharacteristics;
        } catch (RuntimeException e) {
            if (isDndFailCase(e)) {
                synchronized (CameraManagerCompatParamsApi28.sCameraCharacteristicsMap) {
                    if (CameraManagerCompatParamsApi28.sCameraCharacteristicsMap.containsKey(str)) {
                        return CameraManagerCompatParamsApi28.sCameraCharacteristicsMap.get(str);
                    }
                    throwDndException(e);
                }
            }
            throw e;
        }
    }

    private void throwDndException(Throwable th) throws CameraAccessExceptionCompat {
        throw new CameraAccessExceptionCompat(CameraAccessExceptionCompat.CAMERA_UNAVAILABLE_DO_NOT_DISTURB, th);
    }

    private boolean isDndFailCase(Throwable th) {
        return Build.VERSION.SDK_INT == 28 && isDndRuntimeException(th);
    }

    private static boolean isDndRuntimeException(Throwable th) {
        StackTraceElement[] stackTrace;
        if (!th.getClass().equals(RuntimeException.class) || (stackTrace = th.getStackTrace()) == null || stackTrace.length < 0) {
            return false;
        }
        return "_enableShutterSound".equals(stackTrace[0].getMethodName());
    }

    static final class CameraManagerCompatParamsApi28 {
        static final Map<String, CameraCharacteristics> sCameraCharacteristicsMap = new HashMap();

        CameraManagerCompatParamsApi28() {
        }
    }
}
