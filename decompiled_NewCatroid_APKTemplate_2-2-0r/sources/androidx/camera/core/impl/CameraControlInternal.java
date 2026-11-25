package androidx.camera.core.impl;

import android.graphics.Rect;
import androidx.camera.core.CameraControl;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CameraControlInternal extends CameraControl {
    public static final CameraControlInternal DEFAULT_EMPTY_INSTANCE = new CameraControlInternal() { // from class: androidx.camera.core.impl.CameraControlInternal.1
        @Override // androidx.camera.core.impl.CameraControlInternal
        public void cancelAfAeTrigger(boolean z, boolean z2) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public int getFlashMode() {
            return 2;
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void setCropRegion(Rect rect) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void setFlashMode(int i) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void submitCaptureRequests(List<CaptureConfig> list) {
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> enableTorch(boolean z) {
            return Futures.immediateFuture(null);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public ListenableFuture<CameraCaptureResult> triggerAf() {
            return Futures.immediateFuture(CameraCaptureResult.EmptyCameraCaptureResult.create());
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public ListenableFuture<CameraCaptureResult> triggerAePrecapture() {
            return Futures.immediateFuture(CameraCaptureResult.EmptyCameraCaptureResult.create());
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public Rect getSensorRect() {
            return new Rect();
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction focusMeteringAction) {
            return Futures.immediateFuture(FocusMeteringResult.emptyInstance());
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> cancelFocusAndMetering() {
            return Futures.immediateFuture(null);
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> setZoomRatio(float f) {
            return Futures.immediateFuture(null);
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> setLinearZoom(float f) {
            return Futures.immediateFuture(null);
        }
    };

    public interface ControlUpdateCallback {
        void onCameraControlCaptureRequests(List<CaptureConfig> list);

        void onCameraControlUpdateSessionConfig(SessionConfig sessionConfig);
    }

    void cancelAfAeTrigger(boolean z, boolean z2);

    int getFlashMode();

    Rect getSensorRect();

    void setCropRegion(Rect rect);

    void setFlashMode(int i);

    void submitCaptureRequests(List<CaptureConfig> list);

    ListenableFuture<CameraCaptureResult> triggerAePrecapture();

    ListenableFuture<CameraCaptureResult> triggerAf();

    public static final class CameraControlException extends Exception {
        private CameraCaptureFailure mCameraCaptureFailure;

        public CameraControlException(CameraCaptureFailure cameraCaptureFailure) {
            this.mCameraCaptureFailure = cameraCaptureFailure;
        }

        public CameraControlException(CameraCaptureFailure cameraCaptureFailure, Throwable th) {
            super(th);
            this.mCameraCaptureFailure = cameraCaptureFailure;
        }

        public CameraCaptureFailure getCameraCaptureFailure() {
            return this.mCameraCaptureFailure;
        }
    }
}
