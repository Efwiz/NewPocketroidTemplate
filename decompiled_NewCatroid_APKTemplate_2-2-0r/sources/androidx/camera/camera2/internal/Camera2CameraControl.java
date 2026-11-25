package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Rational;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
final class Camera2CameraControl implements CameraControlInternal {
    private static final String TAG = "Camera2CameraControl";
    private final AeFpsRange mAeFpsRange;
    private final CameraCaptureCallbackSet mCameraCaptureCallbackSet;
    private final CameraCharacteristics mCameraCharacteristics;
    private final CameraControlInternal.ControlUpdateCallback mControlUpdateCallback;
    private Rect mCropRect;
    final Executor mExecutor;
    private volatile int mFlashMode;
    private final FocusMeteringControl mFocusMeteringControl;
    private volatile boolean mIsTorchOn;
    volatile Rational mPreviewAspectRatio;
    final CameraControlSessionCallback mSessionCallback;
    private final SessionConfig.Builder mSessionConfigBuilder;
    private final TorchControl mTorchControl;
    private final ZoomControl mZoomControl;

    interface CaptureResultListener {
        boolean onCaptureResult(TotalCaptureResult totalCaptureResult);
    }

    int getDefaultTemplate() {
        return 1;
    }

    Camera2CameraControl(CameraCharacteristics cameraCharacteristics, ScheduledExecutorService scheduledExecutorService, Executor executor, CameraControlInternal.ControlUpdateCallback controlUpdateCallback) {
        SessionConfig.Builder builder = new SessionConfig.Builder();
        this.mSessionConfigBuilder = builder;
        this.mPreviewAspectRatio = null;
        this.mIsTorchOn = false;
        this.mFlashMode = 2;
        this.mCropRect = null;
        CameraCaptureCallbackSet cameraCaptureCallbackSet = new CameraCaptureCallbackSet();
        this.mCameraCaptureCallbackSet = cameraCaptureCallbackSet;
        this.mCameraCharacteristics = cameraCharacteristics;
        this.mControlUpdateCallback = controlUpdateCallback;
        this.mExecutor = executor;
        CameraControlSessionCallback cameraControlSessionCallback = new CameraControlSessionCallback(executor);
        this.mSessionCallback = cameraControlSessionCallback;
        builder.setTemplateType(getDefaultTemplate());
        builder.addRepeatingCameraCaptureCallback(CaptureCallbackContainer.create(cameraControlSessionCallback));
        builder.addRepeatingCameraCaptureCallback(cameraCaptureCallbackSet);
        this.mFocusMeteringControl = new FocusMeteringControl(this, scheduledExecutorService, executor);
        this.mZoomControl = new ZoomControl(this, cameraCharacteristics);
        this.mTorchControl = new TorchControl(this, cameraCharacteristics);
        this.mAeFpsRange = new AeFpsRange(cameraCharacteristics);
        executor.execute(new Camera2CameraControl$$ExternalSyntheticLambda2(this));
    }

    public ZoomControl getZoomControl() {
        return this.mZoomControl;
    }

    public TorchControl getTorchControl() {
        return this.mTorchControl;
    }

    void setActive(boolean z) {
        this.mFocusMeteringControl.setActive(z);
        this.mZoomControl.setActive(z);
        this.mTorchControl.setActive(z);
    }

    public void setPreviewAspectRatio(Rational rational) {
        this.mPreviewAspectRatio = rational;
    }

    public void setDefaultRequestBuilder(CaptureRequest.Builder builder) {
        this.mFocusMeteringControl.setDefaultRequestBuilder(builder);
    }

    @Override // androidx.camera.core.CameraControl
    public ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction focusMeteringAction) {
        return this.mFocusMeteringControl.startFocusAndMetering(focusMeteringAction, this.mPreviewAspectRatio);
    }

    @Override // androidx.camera.core.CameraControl
    public ListenableFuture<Void> cancelFocusAndMetering() {
        return this.mFocusMeteringControl.cancelFocusAndMetering();
    }

    @Override // androidx.camera.core.CameraControl
    public ListenableFuture<Void> setZoomRatio(float f) {
        return this.mZoomControl.setZoomRatio(f);
    }

    @Override // androidx.camera.core.CameraControl
    public ListenableFuture<Void> setLinearZoom(float f) {
        return this.mZoomControl.setLinearZoom(f);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public void setCropRegion(final Rect rect) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m19xb83eda08(rect);
            }
        });
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public int getFlashMode() {
        return this.mFlashMode;
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public void setFlashMode(int i) {
        this.mFlashMode = i;
        this.mExecutor.execute(new Camera2CameraControl$$ExternalSyntheticLambda2(this));
    }

    @Override // androidx.camera.core.CameraControl
    public ListenableFuture<Void> enableTorch(boolean z) {
        return this.mTorchControl.enableTorch(z);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public ListenableFuture<CameraCaptureResult> triggerAf() {
        return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m24xb2b6a561(completer);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$triggerAf$1$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ void m23x89625020(CallbackToFutureAdapter.Completer completer) {
        this.mFocusMeteringControl.triggerAf(completer);
    }

    /* JADX INFO: renamed from: lambda$triggerAf$2$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ Object m24xb2b6a561(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m23x89625020(completer);
            }
        });
        return "triggerAf";
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public ListenableFuture<CameraCaptureResult> triggerAePrecapture() {
        return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda5
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m22x5273da85(completer);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$triggerAePrecapture$3$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ void m21x291f8544(CallbackToFutureAdapter.Completer completer) {
        this.mFocusMeteringControl.triggerAePrecapture(completer);
    }

    /* JADX INFO: renamed from: lambda$triggerAePrecapture$4$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ Object m22x5273da85(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m21x291f8544(completer);
            }
        });
        return "triggerAePrecapture";
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public void cancelAfAeTrigger(final boolean z, final boolean z2) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m16xc3fc219c(z, z2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$cancelAfAeTrigger$5$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ void m16xc3fc219c(boolean z, boolean z2) {
        this.mFocusMeteringControl.cancelAfAeTrigger(z, z2);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public void submitCaptureRequests(final List<CaptureConfig> list) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m20x53d174da(list);
            }
        });
    }

    void updateSessionConfig() {
        this.mSessionConfigBuilder.setImplementationOptions(getSessionOptions());
        this.mControlUpdateCallback.onCameraControlUpdateSessionConfig(this.mSessionConfigBuilder.build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: setCropRegionInternal, reason: merged with bridge method [inline-methods] */
    public void m19xb83eda08(Rect rect) {
        this.mCropRect = rect;
        updateSessionConfig();
    }

    Rect getCropSensorRegion() {
        Rect rect = this.mCropRect;
        return rect == null ? getSensorRect() : rect;
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public Rect getSensorRect() {
        return (Rect) Preconditions.checkNotNull((Rect) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE));
    }

    void removeCaptureResultListener(CaptureResultListener captureResultListener) {
        this.mSessionCallback.removeListener(captureResultListener);
    }

    void addCaptureResultListener(CaptureResultListener captureResultListener) {
        this.mSessionCallback.addListener(captureResultListener);
    }

    void addSessionCameraCaptureCallback(final Executor executor, final CameraCaptureCallback cameraCaptureCallback) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m15x11a0eb7a(executor, cameraCaptureCallback);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$addSessionCameraCaptureCallback$7$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ void m15x11a0eb7a(Executor executor, CameraCaptureCallback cameraCaptureCallback) {
        this.mCameraCaptureCallbackSet.addCaptureCallback(executor, cameraCaptureCallback);
    }

    void removeSessionCameraCaptureCallback(final CameraCaptureCallback cameraCaptureCallback) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m18x9aa425f8(cameraCaptureCallback);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$removeSessionCameraCaptureCallback$8$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ void m18x9aa425f8(CameraCaptureCallback cameraCaptureCallback) {
        this.mCameraCaptureCallbackSet.removeCaptureCallback(cameraCaptureCallback);
    }

    void enableTorchInternal(final boolean z) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m17xfdab4581(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$enableTorchInternal$9$androidx-camera-camera2-internal-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ void m17xfdab4581(boolean z) {
        this.mIsTorchOn = z;
        if (!z) {
            CaptureConfig.Builder builder = new CaptureConfig.Builder();
            builder.setTemplateType(getDefaultTemplate());
            builder.setUseRepeatingSurface(true);
            Camera2ImplConfig.Builder builder2 = new Camera2ImplConfig.Builder();
            builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(getSupportedAeMode(1)));
            builder2.setCaptureRequestOption(CaptureRequest.FLASH_MODE, 0);
            builder.addImplementationOptions(builder2.build());
            m20x53d174da(Collections.singletonList(builder.build()));
        }
        updateSessionConfig();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: submitCaptureRequestsInternal, reason: merged with bridge method [inline-methods] */
    public void m20x53d174da(List<CaptureConfig> list) {
        this.mControlUpdateCallback.onCameraControlCaptureRequests(list);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x004f  */
    Config getSessionOptions() {
        Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
        builder.setCaptureRequestOption(CaptureRequest.CONTROL_MODE, 1);
        this.mFocusMeteringControl.addFocusMeteringOptions(builder);
        this.mAeFpsRange.addAeFpsRangeOptions(builder);
        int i = 2;
        if (this.mIsTorchOn) {
            builder.setCaptureRequestOption(CaptureRequest.FLASH_MODE, 2);
        } else {
            int i2 = this.mFlashMode;
            if (i2 != 0) {
                if (i2 == 1) {
                    i = 3;
                }
            }
            builder.setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(getSupportedAeMode(i)));
            builder.setCaptureRequestOption(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(getSupportedAwbMode(1)));
            if (this.mCropRect != null) {
                builder.setCaptureRequestOption(CaptureRequest.SCALER_CROP_REGION, this.mCropRect);
            }
            return builder.build();
        }
        i = 1;
        builder.setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(getSupportedAeMode(i)));
        builder.setCaptureRequestOption(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(getSupportedAwbMode(1)));
        if (this.mCropRect != null) {
            builder.setCaptureRequestOption(CaptureRequest.SCALER_CROP_REGION, this.mCropRect);
        }
        return builder.build();
    }

    int getSupportedAfMode(int i) {
        int[] iArr = (int[]) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
        if (iArr == null) {
            return 0;
        }
        if (isModeInList(i, iArr)) {
            return i;
        }
        if (isModeInList(4, iArr)) {
            return 4;
        }
        return isModeInList(1, iArr) ? 1 : 0;
    }

    private int getSupportedAeMode(int i) {
        int[] iArr = (int[]) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
        if (iArr == null) {
            return 0;
        }
        if (isModeInList(i, iArr)) {
            return i;
        }
        return isModeInList(1, iArr) ? 1 : 0;
    }

    private int getSupportedAwbMode(int i) {
        int[] iArr = (int[]) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
        if (iArr == null) {
            return 0;
        }
        if (isModeInList(i, iArr)) {
            return i;
        }
        return isModeInList(1, iArr) ? 1 : 0;
    }

    private boolean isModeInList(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    int getMaxAfRegionCount() {
        Integer num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    int getMaxAeRegionCount() {
        Integer num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    int getMaxAwbRegionCount() {
        Integer num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    static final class CameraControlSessionCallback extends CameraCaptureSession.CaptureCallback {
        private final Executor mExecutor;
        final Set<CaptureResultListener> mResultListeners = new HashSet();

        CameraControlSessionCallback(Executor executor) {
            this.mExecutor = executor;
        }

        void addListener(CaptureResultListener captureResultListener) {
            this.mResultListeners.add(captureResultListener);
        }

        void removeListener(CaptureResultListener captureResultListener) {
            this.mResultListeners.remove(captureResultListener);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, final TotalCaptureResult totalCaptureResult) {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$CameraControlSessionCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m25xf3491787(totalCaptureResult);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onCaptureCompleted$0$androidx-camera-camera2-internal-Camera2CameraControl$CameraControlSessionCallback, reason: not valid java name */
        /* synthetic */ void m25xf3491787(TotalCaptureResult totalCaptureResult) {
            HashSet hashSet = new HashSet();
            for (CaptureResultListener captureResultListener : this.mResultListeners) {
                if (captureResultListener.onCaptureResult(totalCaptureResult)) {
                    hashSet.add(captureResultListener);
                }
            }
            if (hashSet.isEmpty()) {
                return;
            }
            this.mResultListeners.removeAll(hashSet);
        }
    }

    static final class CameraCaptureCallbackSet extends CameraCaptureCallback {
        Set<CameraCaptureCallback> mCallbacks = new HashSet();
        Map<CameraCaptureCallback, Executor> mCallbackExecutors = new ArrayMap();

        CameraCaptureCallbackSet() {
        }

        void addCaptureCallback(Executor executor, CameraCaptureCallback cameraCaptureCallback) {
            this.mCallbacks.add(cameraCaptureCallback);
            this.mCallbackExecutors.put(cameraCaptureCallback, executor);
        }

        void removeCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
            this.mCallbacks.remove(cameraCaptureCallback);
            this.mCallbackExecutors.remove(cameraCaptureCallback);
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureCompleted(final CameraCaptureResult cameraCaptureResult) {
            for (final CameraCaptureCallback cameraCaptureCallback : this.mCallbacks) {
                try {
                    this.mCallbackExecutors.get(cameraCaptureCallback).execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$CameraCaptureCallbackSet$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            cameraCaptureCallback.onCaptureCompleted(cameraCaptureResult);
                        }
                    });
                } catch (RejectedExecutionException e) {
                    Log.e(Camera2CameraControl.TAG, "Executor rejected to invoke onCaptureCompleted.", e);
                }
            }
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureFailed(final CameraCaptureFailure cameraCaptureFailure) {
            for (final CameraCaptureCallback cameraCaptureCallback : this.mCallbacks) {
                try {
                    this.mCallbackExecutors.get(cameraCaptureCallback).execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$CameraCaptureCallbackSet$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            cameraCaptureCallback.onCaptureFailed(cameraCaptureFailure);
                        }
                    });
                } catch (RejectedExecutionException e) {
                    Log.e(Camera2CameraControl.TAG, "Executor rejected to invoke onCaptureFailed.", e);
                }
            }
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureCancelled() {
            for (final CameraCaptureCallback cameraCaptureCallback : this.mCallbacks) {
                try {
                    this.mCallbackExecutors.get(cameraCaptureCallback).execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraControl$CameraCaptureCallbackSet$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            cameraCaptureCallback.onCaptureCancelled();
                        }
                    });
                } catch (RejectedExecutionException e) {
                    Log.e(Camera2CameraControl.TAG, "Executor rejected to invoke onCaptureCancelled.", e);
                }
            }
        }
    }
}
