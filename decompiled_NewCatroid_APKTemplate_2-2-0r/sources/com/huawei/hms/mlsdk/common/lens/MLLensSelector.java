package com.huawei.hms.mlsdk.common.lens;

import android.content.Context;
import android.hardware.Camera;
import android.view.WindowManager;
import com.huawei.hms.common.size.Size;
import com.huawei.hms.ml.common.utils.SmartLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes10.dex */
public class MLLensSelector implements LensSelector {
    public static final String TAG = "MLLensSelector";
    public Context context;
    public LensRequest request;

    private void initLensFlashMode(Camera.Parameters parameters) {
        String flashMode = this.request.getFlashMode();
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null) {
            supportedFlashModes = new ArrayList<>();
        }
        if (flashMode == null || !supportedFlashModes.contains(flashMode)) {
            return;
        }
        parameters.setFlashMode(flashMode);
    }

    private void initLensFocusMode(Camera.Parameters parameters) {
        String focusMode = this.request.getFocusMode();
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes == null) {
            supportedFocusModes = new ArrayList<>();
        }
        if (focusMode == null || !supportedFocusModes.contains(focusMode)) {
            return;
        }
        parameters.setFocusMode(focusMode);
    }

    private void initLensFpsRange(Camera.Parameters parameters) {
        int fps = this.request.getFps();
        Iterator<int[]> it = (parameters.getSupportedPreviewFpsRange() != null ? parameters.getSupportedPreviewFpsRange() : new ArrayList<>()).iterator();
        int[] iArr = null;
        int i = Integer.MAX_VALUE;
        while (it.hasNext() && i > 0) {
            int[] next = it.next();
            int i2 = fps * 1000;
            int iAbs = Math.abs(i2 - next[1]) + Math.abs(i2 - next[0]);
            if (iAbs < i) {
                iArr = next;
                i = iAbs;
            }
        }
        if (iArr != null) {
            parameters.setPreviewFpsRange(iArr[0], iArr[1]);
        }
    }

    private int initLensOrientation(int i, Camera camera, Camera.Parameters parameters) {
        int i2;
        int i3;
        Context context = this.context;
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        int rotation = ((WindowManager) context.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW)).getDefaultDisplay().getRotation() * 90;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        if (1 == cameraInfo.facing) {
            i2 = (cameraInfo.orientation + rotation) % 360;
            i3 = (360 - i2) % 360;
        } else {
            i2 = ((cameraInfo.orientation - rotation) + 360) % 360;
            i3 = i2;
        }
        camera.setDisplayOrientation(i3);
        parameters.setRotation(i2);
        return i2 / 90;
    }

    private void initLensSize(Camera.Parameters parameters) {
        Size displaySize = this.request.getDisplaySize();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes() != null ? parameters.getSupportedPreviewSizes() : new ArrayList<>();
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes() != null ? parameters.getSupportedPictureSizes() : new ArrayList<>();
        Camera.Size size = null;
        Camera.Size size2 = null;
        int i = Integer.MAX_VALUE;
        for (Camera.Size size3 : supportedPreviewSizes) {
            float f = (size3.width * 1.0f) / size3.height;
            for (Camera.Size size4 : supportedPictureSizes) {
                if (Math.abs(f - ((size4.width * 1.0f) / size4.height)) < 0.01f) {
                    int iAbs = Math.abs(size3.height - displaySize.getHeight()) + Math.abs(size3.width - displaySize.getWidth());
                    if (iAbs < i) {
                        i = iAbs;
                        size = size3;
                        size2 = size4;
                    }
                }
            }
        }
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        } else {
            int i2 = Integer.MAX_VALUE;
            for (Camera.Size size5 : supportedPreviewSizes) {
                int iAbs2 = Math.abs(size5.height - displaySize.getHeight()) + Math.abs(size5.width - displaySize.getWidth());
                if (iAbs2 < i2) {
                    size = size5;
                    i2 = iAbs2;
                    size2 = null;
                }
            }
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }
        }
        if (size2 != null) {
            parameters.setPictureSize(size2.width, size2.height);
        }
    }

    @Override // com.huawei.hms.mlsdk.common.lens.LensSelector
    public LensResponse selectLens(Context context, LensRequest lensRequest) {
        Camera cameraOpen;
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        this.context = context;
        this.request = lensRequest;
        int numberOfCameras = Camera.getNumberOfCameras();
        int i = 0;
        while (true) {
            if (i < numberOfCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == lensRequest.getLensType()) {
                    try {
                        cameraOpen = Camera.open(i);
                        break;
                    } catch (RuntimeException e) {
                        SmartLog.e(TAG, "Camera open failed, " + e.getMessage());
                        cameraOpen = null;
                        break;
                    }
                }
                i++;
            } else {
                i = -1;
            }
            cameraOpen = null;
            break;
        }
        if (i == -1 || cameraOpen == null) {
            return null;
        }
        Camera.Parameters parameters = cameraOpen.getParameters();
        initLensSize(parameters);
        initLensFpsRange(parameters);
        int iInitLensOrientation = initLensOrientation(i, cameraOpen, parameters);
        initLensFocusMode(parameters);
        initLensFlashMode(parameters);
        parameters.setZoom(0);
        parameters.setPreviewFormat(17);
        cameraOpen.setParameters(parameters);
        LensResponse lensResponse = new LensResponse();
        lensResponse.setLensId(i);
        lensResponse.setLens(cameraOpen);
        lensResponse.setQuadrant(iInitLensOrientation);
        return lensResponse;
    }
}
