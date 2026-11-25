package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import androidx.camera.core.impl.DeferrableSurface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class CaptureSessionRepository {
    final Executor mExecutor;
    final Object mLock = new Object();
    final Set<SynchronizedCaptureSession> mCaptureSessions = new LinkedHashSet();
    final Set<SynchronizedCaptureSession> mClosingCaptureSession = new LinkedHashSet();
    final Set<SynchronizedCaptureSession> mCreatingCaptureSessions = new LinkedHashSet();
    final Map<SynchronizedCaptureSession, List<DeferrableSurface>> mDeferrableSurfaceMap = new HashMap();
    private final CameraDevice.StateCallback mCameraStateCallback = new AnonymousClass1();

    CaptureSessionRepository(Executor executor) {
        this.mExecutor = executor;
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.internal.CaptureSessionRepository$1, reason: invalid class name */
    class AnonymousClass1 extends CameraDevice.StateCallback {
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
        }

        AnonymousClass1() {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            CaptureSessionRepository.this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.CaptureSessionRepository$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m42x5bc3f292();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onDisconnected$0$androidx-camera-camera2-internal-CaptureSessionRepository$1, reason: not valid java name */
        /* synthetic */ void m42x5bc3f292() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            synchronized (CaptureSessionRepository.this.mLock) {
                linkedHashSet.addAll(new LinkedHashSet(CaptureSessionRepository.this.mCreatingCaptureSessions));
                linkedHashSet.addAll(new LinkedHashSet(CaptureSessionRepository.this.mCaptureSessions));
            }
            CaptureSessionRepository.forceOnClosed(linkedHashSet);
        }
    }

    CameraDevice.StateCallback getCameraStateCallback() {
        return this.mCameraStateCallback;
    }

    static void forceOnClosed(Set<SynchronizedCaptureSession> set) {
        for (SynchronizedCaptureSession synchronizedCaptureSession : set) {
            synchronizedCaptureSession.getStateCallback().onClosed(synchronizedCaptureSession);
        }
    }

    List<SynchronizedCaptureSession> getCaptureSessions() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mCaptureSessions);
        }
        return arrayList;
    }

    List<SynchronizedCaptureSession> getClosingCaptureSession() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mClosingCaptureSession);
        }
        return arrayList;
    }

    List<SynchronizedCaptureSession> getCreatingCaptureSessions() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mCreatingCaptureSessions);
        }
        return arrayList;
    }

    Map<SynchronizedCaptureSession, List<DeferrableSurface>> registerDeferrableSurface(SynchronizedCaptureSession synchronizedCaptureSession, List<DeferrableSurface> list) {
        HashMap map;
        synchronized (this.mLock) {
            this.mDeferrableSurfaceMap.put(synchronizedCaptureSession, list);
            map = new HashMap(this.mDeferrableSurfaceMap);
        }
        return map;
    }

    void unregisterDeferrableSurface(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mLock) {
            this.mDeferrableSurfaceMap.remove(synchronizedCaptureSession);
        }
    }

    void onCreateCaptureSession(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mLock) {
            this.mCreatingCaptureSessions.add(synchronizedCaptureSession);
        }
    }

    void onCaptureSessionConfigureFail(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mLock) {
            this.mCreatingCaptureSessions.remove(synchronizedCaptureSession);
        }
    }

    void onCaptureSessionCreated(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mLock) {
            this.mCaptureSessions.add(synchronizedCaptureSession);
            this.mCreatingCaptureSessions.remove(synchronizedCaptureSession);
        }
    }

    void onCaptureSessionClosed(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mLock) {
            this.mCaptureSessions.remove(synchronizedCaptureSession);
            this.mClosingCaptureSession.remove(synchronizedCaptureSession);
        }
    }

    void onCaptureSessionClosing(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mLock) {
            this.mClosingCaptureSession.add(synchronizedCaptureSession);
        }
    }
}
