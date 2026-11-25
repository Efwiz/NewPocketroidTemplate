package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Looper;
import androidx.camera.core.CameraControl;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.ImmutableZoomState;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
final class ZoomControl {
    public static final float DEFAULT_ZOOM_RATIO = 1.0f;
    public static final float MIN_ZOOM = 1.0f;
    private static final String TAG = "ZoomControl";
    private final Camera2CameraControl mCamera2CameraControl;
    private final ZoomStateImpl mCurrentZoomState;
    CallbackToFutureAdapter.Completer<Void> mPendingZoomRatioCompleter;
    private final MutableLiveData<ZoomState> mZoomStateLiveData;
    final Object mCompleterLock = new Object();
    Rect mPendingZoomCropRegion = null;
    final Object mActiveLock = new Object();
    private boolean mIsActive = false;
    private Camera2CameraControl.CaptureResultListener mCaptureResultListener = new Camera2CameraControl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.ZoomControl.1
        /* JADX WARN: Code duplicated, block: B:15:0x0039  */
        @Override // androidx.camera.camera2.internal.Camera2CameraControl.CaptureResultListener
        public boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
            CallbackToFutureAdapter.Completer<Void> completer;
            synchronized (ZoomControl.this.mCompleterLock) {
                if (ZoomControl.this.mPendingZoomRatioCompleter == null) {
                    completer = null;
                } else {
                    CaptureRequest request = totalCaptureResult.getRequest();
                    Rect rect = request == null ? null : (Rect) request.get(CaptureRequest.SCALER_CROP_REGION);
                    if (ZoomControl.this.mPendingZoomCropRegion == null || !ZoomControl.this.mPendingZoomCropRegion.equals(rect)) {
                        completer = null;
                    } else {
                        completer = ZoomControl.this.mPendingZoomRatioCompleter;
                        ZoomControl.this.mPendingZoomRatioCompleter = null;
                        ZoomControl.this.mPendingZoomCropRegion = null;
                    }
                }
            }
            if (completer == null) {
                return false;
            }
            completer.set(null);
            return false;
        }
    };

    ZoomControl(Camera2CameraControl camera2CameraControl, CameraCharacteristics cameraCharacteristics) {
        this.mCamera2CameraControl = camera2CameraControl;
        ZoomStateImpl zoomStateImpl = new ZoomStateImpl(getMaxDigitalZoom(cameraCharacteristics), 1.0f);
        this.mCurrentZoomState = zoomStateImpl;
        zoomStateImpl.setZoomRatio(1.0f);
        this.mZoomStateLiveData = new MutableLiveData<>(ImmutableZoomState.create(zoomStateImpl));
        camera2CameraControl.addCaptureResultListener(this.mCaptureResultListener);
    }

    void setActive(boolean z) {
        boolean z2;
        CallbackToFutureAdapter.Completer<Void> completer;
        synchronized (this.mActiveLock) {
            if (this.mIsActive == z) {
                return;
            }
            this.mIsActive = z;
            if (z) {
                z2 = false;
                completer = null;
            } else {
                synchronized (this.mCompleterLock) {
                    completer = this.mPendingZoomRatioCompleter;
                    if (completer != null) {
                        this.mPendingZoomRatioCompleter = null;
                        this.mPendingZoomCropRegion = null;
                    } else {
                        completer = null;
                    }
                }
                this.mCurrentZoomState.setZoomRatio(1.0f);
                updateLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
                z2 = true;
            }
            if (z2) {
                this.mCamera2CameraControl.setCropRegion(null);
            }
            if (completer != null) {
                completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            }
        }
    }

    ListenableFuture<Void> setZoomRatio(float f) {
        synchronized (this.mActiveLock) {
            if (!this.mIsActive) {
                return Futures.immediateFailedFuture(new CameraControl.OperationCanceledException("Camera is not active."));
            }
            try {
                this.mCurrentZoomState.setZoomRatio(f);
                updateLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
                return submitCameraZoomRatio(f);
            } catch (IllegalArgumentException e) {
                return Futures.immediateFailedFuture(e);
            }
        }
    }

    static Rect getCropRectByRatio(Rect rect, float f) {
        float fWidth = rect.width() / f;
        float fHeight = rect.height() / f;
        float fWidth2 = (rect.width() - fWidth) / 2.0f;
        float fHeight2 = (rect.height() - fHeight) / 2.0f;
        return new Rect((int) fWidth2, (int) fHeight2, (int) (fWidth2 + fWidth), (int) (fHeight2 + fHeight));
    }

    private ListenableFuture<Void> submitCameraZoomRatio(float f) {
        final Rect cropRectByRatio = getCropRectByRatio(this.mCamera2CameraControl.getSensorRect(), f);
        this.mCamera2CameraControl.setCropRegion(cropRectByRatio);
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.ZoomControl$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m61x238f6c18(cropRectByRatio, completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$submitCameraZoomRatio$0$androidx-camera-camera2-internal-ZoomControl, reason: not valid java name */
    /* synthetic */ Object m61x238f6c18(Rect rect, CallbackToFutureAdapter.Completer completer) throws Exception {
        CallbackToFutureAdapter.Completer<Void> completer2;
        synchronized (this.mCompleterLock) {
            completer2 = this.mPendingZoomRatioCompleter;
            if (completer2 != null) {
                this.mPendingZoomRatioCompleter = null;
            } else {
                completer2 = null;
            }
            this.mPendingZoomCropRegion = rect;
            this.mPendingZoomRatioCompleter = completer;
        }
        if (completer2 != null) {
            completer2.setException(new CameraControl.OperationCanceledException("There is a new zoomRatio being set"));
            return "setZoomRatio";
        }
        return "setZoomRatio";
    }

    ListenableFuture<Void> setLinearZoom(float f) {
        synchronized (this.mActiveLock) {
            if (!this.mIsActive) {
                return Futures.immediateFailedFuture(new CameraControl.OperationCanceledException("Camera is not active."));
            }
            try {
                this.mCurrentZoomState.setLinearZoom(f);
                updateLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
                return submitCameraZoomRatio(this.mCurrentZoomState.getZoomRatio());
            } catch (IllegalArgumentException e) {
                return Futures.immediateFailedFuture(e);
            }
        }
    }

    private void updateLiveData(ZoomState zoomState) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.mZoomStateLiveData.setValue(zoomState);
        } else {
            this.mZoomStateLiveData.postValue(zoomState);
        }
    }

    LiveData<ZoomState> getZoomState() {
        return this.mZoomStateLiveData;
    }

    private static float getMaxDigitalZoom(CameraCharacteristics cameraCharacteristics) {
        Float f = (Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
        if (f == null) {
            return 1.0f;
        }
        return f.floatValue();
    }
}
