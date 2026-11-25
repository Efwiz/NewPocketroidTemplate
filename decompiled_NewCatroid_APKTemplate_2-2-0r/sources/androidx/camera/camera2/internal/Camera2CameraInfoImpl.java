package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Log;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2CameraInfoImpl implements CameraInfoInternal {
    private static final String TAG = "Camera2CameraInfo";
    private final Camera2CameraControl mCamera2CameraControl;
    private final CameraCharacteristics mCameraCharacteristics;
    private final String mCameraId;
    private final TorchControl mTorchControl;
    private final ZoomControl mZoomControl;

    Camera2CameraInfoImpl(String str, CameraCharacteristics cameraCharacteristics, Camera2CameraControl camera2CameraControl) {
        Preconditions.checkNotNull(cameraCharacteristics, "Camera characteristics map is missing");
        this.mCameraId = (String) Preconditions.checkNotNull(str);
        this.mCameraCharacteristics = cameraCharacteristics;
        this.mCamera2CameraControl = camera2CameraControl;
        this.mZoomControl = camera2CameraControl.getZoomControl();
        this.mTorchControl = camera2CameraControl.getTorchControl();
        logDeviceInfo();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public String getCameraId() {
        return this.mCameraId;
    }

    public CameraCharacteristics getCameraCharacteristics() {
        return this.mCameraCharacteristics;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Integer getLensFacing() {
        Integer num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
        Preconditions.checkNotNull(num);
        int iIntValue = num.intValue();
        if (iIntValue != 0) {
            return iIntValue != 1 ? null : 1;
        }
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees(int i) {
        boolean z;
        Integer numValueOf = Integer.valueOf(getSensorOrientation());
        int iSurfaceRotationToDegrees = CameraOrientationUtil.surfaceRotationToDegrees(i);
        Integer lensFacing = getLensFacing();
        if (lensFacing != null) {
            z = 1 == lensFacing.intValue();
        }
        return CameraOrientationUtil.getRelativeImageRotation(iSurfaceRotationToDegrees, numValueOf.intValue(), z);
    }

    int getSensorOrientation() {
        Integer num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        Preconditions.checkNotNull(num);
        return num.intValue();
    }

    int getSupportedHardwareLevel() {
        Integer num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        Preconditions.checkNotNull(num);
        return num.intValue();
    }

    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees() {
        return getSensorRotationDegrees(0);
    }

    private void logDeviceInfo() {
        logDeviceLevel();
    }

    private void logDeviceLevel() {
        String str;
        int supportedHardwareLevel = getSupportedHardwareLevel();
        if (supportedHardwareLevel == 0) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED";
        } else if (supportedHardwareLevel == 1) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_FULL";
        } else if (supportedHardwareLevel == 2) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY";
        } else if (supportedHardwareLevel == 3) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_3";
        } else if (supportedHardwareLevel == 4) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL";
        } else {
            str = "Unknown value: " + supportedHardwareLevel;
        }
        Log.i(TAG, "Device Level: " + str);
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean hasFlashUnit() {
        Boolean bool = (Boolean) this.mCameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        Preconditions.checkNotNull(bool);
        return bool.booleanValue();
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<Integer> getTorchState() {
        return this.mTorchControl.getTorchState();
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<ZoomState> getZoomState() {
        return this.mZoomControl.getZoomState();
    }

    @Override // androidx.camera.core.CameraInfo
    public String getImplementationType() {
        return getSupportedHardwareLevel() == 2 ? CameraInfo.IMPLEMENTATION_TYPE_CAMERA2_LEGACY : CameraInfo.IMPLEMENTATION_TYPE_CAMERA2;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public void addSessionCaptureCallback(Executor executor, CameraCaptureCallback cameraCaptureCallback) {
        this.mCamera2CameraControl.addSessionCameraCaptureCallback(executor, cameraCaptureCallback);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public void removeSessionCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
        this.mCamera2CameraControl.removeSessionCameraCaptureCallback(cameraCaptureCallback);
    }
}
