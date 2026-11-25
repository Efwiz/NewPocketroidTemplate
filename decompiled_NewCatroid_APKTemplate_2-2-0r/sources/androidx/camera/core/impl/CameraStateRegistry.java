package androidx.camera.core.impl;

import android.util.Log;
import androidx.camera.core.Camera;
import androidx.core.util.Preconditions;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: loaded from: classes.dex */
public final class CameraStateRegistry {
    private int mAvailableCameras;
    private final Map<Camera, CameraRegistration> mCameraStates;
    private StringBuilder mDebugString;
    private final Object mLock;
    private final int mMaxAllowedOpenedCameras;
    private static final String TAG = "CameraStateRegistry";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public interface OnOpenAvailableListener {
        void onOpenAvailable();
    }

    public CameraStateRegistry(int i) {
        this.mDebugString = DEBUG ? new StringBuilder() : null;
        this.mLock = new Object();
        this.mCameraStates = new HashMap();
        this.mMaxAllowedOpenedCameras = i;
        synchronized ("mLock") {
            this.mAvailableCameras = i;
        }
    }

    public void registerCamera(Camera camera, Executor executor, OnOpenAvailableListener onOpenAvailableListener) {
        synchronized (this.mLock) {
            Preconditions.checkState(!this.mCameraStates.containsKey(camera), "Camera is already registered: " + camera);
            this.mCameraStates.put(camera, new CameraRegistration(null, executor, onOpenAvailableListener));
        }
    }

    public boolean tryOpenCamera(Camera camera) {
        boolean z;
        synchronized (this.mLock) {
            CameraRegistration cameraRegistration = (CameraRegistration) Preconditions.checkNotNull(this.mCameraStates.get(camera), "Camera must first be registered with registerCamera()");
            boolean z2 = DEBUG;
            z = false;
            if (z2) {
                this.mDebugString.setLength(0);
                this.mDebugString.append(String.format(Locale.US, "tryOpenCamera(%s) [Available Cameras: %d, Already Open: %b (Previous state: %s)]", camera, Integer.valueOf(this.mAvailableCameras), Boolean.valueOf(isOpen(cameraRegistration.getState())), cameraRegistration.getState()));
            }
            if (this.mAvailableCameras > 0 || isOpen(cameraRegistration.getState())) {
                cameraRegistration.setState(CameraInternal.State.OPENING);
                z = true;
            }
            if (z2) {
                this.mDebugString.append(String.format(Locale.US, " --> %s", z ? MonitorResult.SUCCESS : "FAIL"));
                Log.d(TAG, this.mDebugString.toString());
            }
            if (z) {
                recalculateAvailableCameras();
            }
        }
        return z;
    }

    public void markCameraState(Camera camera, CameraInternal.State state) {
        CameraInternal.State stateUpdateAndVerifyState;
        List listSingletonList;
        synchronized (this.mLock) {
            int i = this.mAvailableCameras;
            if (state == CameraInternal.State.RELEASED) {
                stateUpdateAndVerifyState = unregisterCamera(camera);
            } else {
                stateUpdateAndVerifyState = updateAndVerifyState(camera, state);
            }
            if (stateUpdateAndVerifyState == state) {
                return;
            }
            if (i < 1 && this.mAvailableCameras > 0) {
                listSingletonList = new ArrayList();
                for (Map.Entry<Camera, CameraRegistration> entry : this.mCameraStates.entrySet()) {
                    if (entry.getValue().getState() == CameraInternal.State.PENDING_OPEN) {
                        listSingletonList.add(entry.getValue());
                    }
                }
            } else {
                listSingletonList = (state != CameraInternal.State.PENDING_OPEN || this.mAvailableCameras <= 0) ? null : Collections.singletonList(this.mCameraStates.get(camera));
            }
            if (listSingletonList != null) {
                Iterator it = listSingletonList.iterator();
                while (it.hasNext()) {
                    ((CameraRegistration) it.next()).notifyListener();
                }
            }
        }
    }

    private CameraInternal.State unregisterCamera(Camera camera) {
        CameraRegistration cameraRegistrationRemove = this.mCameraStates.remove(camera);
        if (cameraRegistrationRemove == null) {
            return null;
        }
        recalculateAvailableCameras();
        return cameraRegistrationRemove.getState();
    }

    private CameraInternal.State updateAndVerifyState(Camera camera, CameraInternal.State state) {
        CameraInternal.State state2 = ((CameraRegistration) Preconditions.checkNotNull(this.mCameraStates.get(camera), "Cannot update state of camera which has not yet been registered. Register with CameraAvailabilityRegistry.registerCamera()")).setState(state);
        if (state == CameraInternal.State.OPENING) {
            Preconditions.checkState(isOpen(state) || state2 == CameraInternal.State.OPENING, "Cannot mark camera as opening until camera was successful at calling CameraAvailabilityRegistry.tryOpen()");
        }
        if (state2 != state) {
            recalculateAvailableCameras();
        }
        return state2;
    }

    private static boolean isOpen(CameraInternal.State state) {
        return state != null && state.holdsCameraSlot();
    }

    private void recalculateAvailableCameras() {
        if (DEBUG) {
            this.mDebugString.setLength(0);
            this.mDebugString.append("Recalculating open cameras:\n");
            this.mDebugString.append(String.format(Locale.US, "%-45s%-22s\n", "Camera", "State"));
            this.mDebugString.append("-------------------------------------------------------------------\n");
        }
        int i = 0;
        for (Map.Entry<Camera, CameraRegistration> entry : this.mCameraStates.entrySet()) {
            if (DEBUG) {
                this.mDebugString.append(String.format(Locale.US, "%-45s%-22s\n", entry.getKey().toString(), entry.getValue().getState() != null ? entry.getValue().getState().toString() : GrsBaseInfo.CountryCodeSource.UNKNOWN));
            }
            if (isOpen(entry.getValue().getState())) {
                i++;
            }
        }
        if (DEBUG) {
            this.mDebugString.append("-------------------------------------------------------------------\n");
            this.mDebugString.append(String.format(Locale.US, "Open count: %d (Max allowed: %d)", Integer.valueOf(i), Integer.valueOf(this.mMaxAllowedOpenedCameras)));
            Log.d(TAG, this.mDebugString.toString());
        }
        this.mAvailableCameras = Math.max(this.mMaxAllowedOpenedCameras - i, 0);
    }

    private static class CameraRegistration {
        private final OnOpenAvailableListener mCameraAvailableListener;
        private final Executor mNotifyExecutor;
        private CameraInternal.State mState;

        CameraRegistration(CameraInternal.State state, Executor executor, OnOpenAvailableListener onOpenAvailableListener) {
            this.mState = state;
            this.mNotifyExecutor = executor;
            this.mCameraAvailableListener = onOpenAvailableListener;
        }

        CameraInternal.State setState(CameraInternal.State state) {
            CameraInternal.State state2 = this.mState;
            this.mState = state;
            return state2;
        }

        CameraInternal.State getState() {
            return this.mState;
        }

        void notifyListener() {
            try {
                Executor executor = this.mNotifyExecutor;
                final OnOpenAvailableListener onOpenAvailableListener = this.mCameraAvailableListener;
                Objects.requireNonNull(onOpenAvailableListener);
                executor.execute(new Runnable() { // from class: androidx.camera.core.impl.CameraStateRegistry$CameraRegistration$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        onOpenAvailableListener.onOpenAvailable();
                    }
                });
            } catch (RejectedExecutionException e) {
                Log.e(CameraStateRegistry.TAG, "Unable to notify camera.", e);
            }
        }
    }
}
