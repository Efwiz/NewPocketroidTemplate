package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
final class TorchControl {
    private static final String TAG = "TorchControl";
    private final Camera2CameraControl mCamera2CameraControl;
    private final Camera2CameraControl.CaptureResultListener mCaptureResultListener;
    CallbackToFutureAdapter.Completer<Void> mEnableTorchCompleter;
    private final boolean mHasFlashUnit;
    private boolean mIsActive;
    boolean mTargetTorchEnabled;
    private final MutableLiveData<Integer> mTorchState;
    final Object mEnableTorchLock = new Object();
    private final Object mActiveLock = new Object();

    TorchControl(Camera2CameraControl camera2CameraControl, CameraCharacteristics cameraCharacteristics) {
        Camera2CameraControl.CaptureResultListener captureResultListener = new Camera2CameraControl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.TorchControl.1
            /* JADX WARN: Code duplicated, block: B:15:0x0034  */
            @Override // androidx.camera.camera2.internal.Camera2CameraControl.CaptureResultListener
            public boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                CallbackToFutureAdapter.Completer<Void> completer;
                synchronized (TorchControl.this.mEnableTorchLock) {
                    if (TorchControl.this.mEnableTorchCompleter == null) {
                        completer = null;
                    } else {
                        Integer num = (Integer) totalCaptureResult.getRequest().get(CaptureRequest.FLASH_MODE);
                        if ((num != null && num.intValue() == 2) == TorchControl.this.mTargetTorchEnabled) {
                            completer = TorchControl.this.mEnableTorchCompleter;
                            TorchControl.this.mEnableTorchCompleter = null;
                        } else {
                            completer = null;
                        }
                    }
                }
                if (completer != null) {
                    completer.set(null);
                }
                return false;
            }
        };
        this.mCaptureResultListener = captureResultListener;
        this.mCamera2CameraControl = camera2CameraControl;
        Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        this.mHasFlashUnit = bool != null && bool.booleanValue();
        this.mTorchState = new MutableLiveData<>(0);
        camera2CameraControl.addCaptureResultListener(captureResultListener);
    }

    void setActive(boolean z) {
        CallbackToFutureAdapter.Completer<Void> completer;
        boolean z2;
        synchronized (this.mActiveLock) {
            if (this.mIsActive == z) {
                return;
            }
            this.mIsActive = z;
            synchronized (this.mEnableTorchLock) {
                completer = null;
                if (!z) {
                    CallbackToFutureAdapter.Completer<Void> completer2 = this.mEnableTorchCompleter;
                    if (completer2 != null) {
                        this.mEnableTorchCompleter = null;
                        completer = completer2;
                    }
                    if (this.mTargetTorchEnabled) {
                        this.mTargetTorchEnabled = false;
                        this.mCamera2CameraControl.enableTorchInternal(false);
                        z2 = true;
                    }
                }
                z2 = false;
            }
            if (z2) {
                setLiveDataValue(this.mTorchState, 0);
            }
            if (completer != null) {
                completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            }
        }
    }

    ListenableFuture<Void> enableTorch(final boolean z) {
        if (!this.mHasFlashUnit) {
            Log.d(TAG, "Unable to enableTorch due to there is no flash unit.");
            return Futures.immediateFailedFuture(new IllegalStateException("No flash unit"));
        }
        synchronized (this.mActiveLock) {
            if (!this.mIsActive) {
                return Futures.immediateFailedFuture(new CameraControl.OperationCanceledException("Camera is not active."));
            }
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.TorchControl$$ExternalSyntheticLambda0
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m60x4796b251(z, completer);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$enableTorch$0$androidx-camera-camera2-internal-TorchControl, reason: not valid java name */
    /* synthetic */ Object m60x4796b251(boolean z, CallbackToFutureAdapter.Completer completer) throws Exception {
        CallbackToFutureAdapter.Completer<Void> completer2;
        synchronized (this.mEnableTorchLock) {
            completer2 = this.mEnableTorchCompleter;
            if (completer2 == null) {
                completer2 = null;
            }
            this.mEnableTorchCompleter = completer;
            this.mTargetTorchEnabled = z;
            this.mCamera2CameraControl.enableTorchInternal(z);
        }
        setLiveDataValue(this.mTorchState, Integer.valueOf(z ? 1 : 0));
        if (completer2 != null) {
            completer2.setException(new CameraControl.OperationCanceledException("There is a new enableTorch being set"));
        }
        return "enableTorch: " + z;
    }

    LiveData<Integer> getTorchState() {
        return this.mTorchState;
    }

    private <T> void setLiveDataValue(MutableLiveData<T> mutableLiveData, T t) {
        if (Threads.isMainThread()) {
            mutableLiveData.setValue(t);
        } else {
            mutableLiveData.postValue(t);
        }
    }
}
