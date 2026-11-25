package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.CameraEventCallbacks;
import androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurfaces;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: classes.dex */
final class CaptureSession {
    private static final String TAG = "CaptureSession";
    private static final long TIMEOUT_GET_SURFACE_IN_MS = 5000;
    volatile Config mCameraEventOnRepeatingOptions;
    CallbackToFutureAdapter.Completer<Void> mReleaseCompleter;
    ListenableFuture<Void> mReleaseFuture;
    volatile SessionConfig mSessionConfig;
    State mState;
    SynchronizedCaptureSession mSynchronizedCaptureSession;
    SynchronizedCaptureSessionOpener mSynchronizedCaptureSessionOpener;
    final Object mStateLock = new Object();
    private final List<CaptureConfig> mCaptureConfigs = new ArrayList();
    private final CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: androidx.camera.camera2.internal.CaptureSession.1
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
        }
    };
    private Map<DeferrableSurface, Surface> mConfiguredSurfaceMap = new HashMap();
    List<DeferrableSurface> mConfiguredDeferrableSurfaces = Collections.emptyList();
    private final StateCallback mCaptureSessionStateCallback = new StateCallback();

    enum State {
        UNINITIALIZED,
        INITIALIZED,
        GET_SURFACE,
        OPENING,
        OPENED,
        CLOSED,
        RELEASING,
        RELEASED
    }

    CaptureSession() {
        this.mState = State.UNINITIALIZED;
        this.mState = State.INITIALIZED;
    }

    SessionConfig getSessionConfig() {
        SessionConfig sessionConfig;
        synchronized (this.mStateLock) {
            sessionConfig = this.mSessionConfig;
        }
        return sessionConfig;
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.internal.CaptureSession$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State = iArr;
            try {
                iArr[State.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.GET_SURFACE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.OPENING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.OPENED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.CLOSED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.RELEASING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.RELEASED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    void setSessionConfig(SessionConfig sessionConfig) {
        synchronized (this.mStateLock) {
            switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()]) {
                case 1:
                    throw new IllegalStateException("setSessionConfig() should not be possible in state: " + this.mState);
                case 2:
                case 3:
                case 4:
                    this.mSessionConfig = sessionConfig;
                    break;
                case 5:
                    this.mSessionConfig = sessionConfig;
                    if (!this.mConfiguredSurfaceMap.keySet().containsAll(sessionConfig.getSurfaces())) {
                        Log.e(TAG, "Does not have the proper configured lists");
                        return;
                    } else {
                        Log.d(TAG, "Attempting to submit CaptureRequest after setting");
                        issueRepeatingCaptureRequests();
                    }
                    break;
                case 6:
                case 7:
                case 8:
                    throw new IllegalStateException("Session configuration cannot be set on a closed/released session.");
            }
        }
    }

    ListenableFuture<Void> open(final SessionConfig sessionConfig, final CameraDevice cameraDevice, SynchronizedCaptureSessionOpener synchronizedCaptureSessionOpener) {
        synchronized (this.mStateLock) {
            if (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()] == 2) {
                this.mState = State.GET_SURFACE;
                ArrayList arrayList = new ArrayList(sessionConfig.getSurfaces());
                this.mConfiguredDeferrableSurfaces = arrayList;
                this.mSynchronizedCaptureSessionOpener = synchronizedCaptureSessionOpener;
                FutureChain futureChainTransformAsync = FutureChain.from(synchronizedCaptureSessionOpener.startWithDeferrableSurface(arrayList, 5000L)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.CaptureSession$$ExternalSyntheticLambda1
                    @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                    public final ListenableFuture apply(Object obj) {
                        return this.f$0.m40lambda$open$0$androidxcameracamera2internalCaptureSession(sessionConfig, cameraDevice, (List) obj);
                    }
                }, this.mSynchronizedCaptureSessionOpener.getExecutor());
                Futures.addCallback(futureChainTransformAsync, new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.CaptureSession.2
                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onSuccess(Void r1) {
                    }

                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onFailure(Throwable th) {
                        CaptureSession.this.mSynchronizedCaptureSessionOpener.stop();
                    }
                }, this.mSynchronizedCaptureSessionOpener.getExecutor());
                return Futures.nonCancellationPropagating(futureChainTransformAsync);
            }
            Log.e(TAG, "Open not allowed in state: " + this.mState);
            return Futures.immediateFailedFuture(new IllegalStateException("open() should not allow the state: " + this.mState));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: openCaptureSession, reason: merged with bridge method [inline-methods] */
    public ListenableFuture<Void> m40lambda$open$0$androidxcameracamera2internalCaptureSession(List<Surface> list, SessionConfig sessionConfig, CameraDevice cameraDevice) {
        synchronized (this.mStateLock) {
            int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()];
            if (i != 1 && i != 2) {
                if (i == 3) {
                    try {
                        DeferrableSurfaces.incrementAll(this.mConfiguredDeferrableSurfaces);
                        this.mConfiguredSurfaceMap.clear();
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            this.mConfiguredSurfaceMap.put(this.mConfiguredDeferrableSurfaces.get(i2), list.get(i2));
                        }
                        ArrayList arrayList = new ArrayList(new HashSet(list));
                        this.mState = State.OPENING;
                        Log.d(TAG, "Opening capture session.");
                        SynchronizedCaptureSession.StateCallback stateCallbackCreateComboCallback = SynchronizedCaptureSessionStateCallbacks.createComboCallback(this.mCaptureSessionStateCallback, new SynchronizedCaptureSessionStateCallbacks.Adapter(sessionConfig.getSessionStateCallbacks()));
                        List<CaptureConfig> listOnPresetSession = new Camera2ImplConfig(sessionConfig.getImplementationOptions()).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onPresetSession();
                        CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(sessionConfig.getRepeatingCaptureConfig());
                        Iterator<CaptureConfig> it = listOnPresetSession.iterator();
                        while (it.hasNext()) {
                            builderFrom.addImplementationOptions(it.next().getImplementationOptions());
                        }
                        ArrayList arrayList2 = new ArrayList();
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(new OutputConfigurationCompat((Surface) it2.next()));
                        }
                        SessionConfigurationCompat sessionConfigurationCompatCreateSessionConfigurationCompat = this.mSynchronizedCaptureSessionOpener.createSessionConfigurationCompat(0, arrayList2, stateCallbackCreateComboCallback);
                        try {
                            CaptureRequest captureRequestBuildWithoutTarget = Camera2CaptureRequestBuilder.buildWithoutTarget(builderFrom.build(), cameraDevice);
                            if (captureRequestBuildWithoutTarget != null) {
                                sessionConfigurationCompatCreateSessionConfigurationCompat.setSessionParameters(captureRequestBuildWithoutTarget);
                            }
                            return this.mSynchronizedCaptureSessionOpener.openCaptureSession(cameraDevice, sessionConfigurationCompatCreateSessionConfigurationCompat);
                        } catch (CameraAccessException e) {
                            return Futures.immediateFailedFuture(e);
                        }
                    } catch (DeferrableSurface.SurfaceClosedException e2) {
                        this.mConfiguredDeferrableSurfaces.clear();
                        return Futures.immediateFailedFuture(e2);
                    }
                }
                if (i != 5) {
                    return Futures.immediateFailedFuture(new CancellationException("openCaptureSession() not execute in state: " + this.mState));
                }
            }
            return Futures.immediateFailedFuture(new IllegalStateException("openCaptureSession() should not be possible in state: " + this.mState));
        }
    }

    void close() {
        synchronized (this.mStateLock) {
            int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()];
            if (i == 1) {
                throw new IllegalStateException("close() should not be possible in state: " + this.mState);
            }
            if (i == 2) {
                this.mState = State.RELEASED;
            } else if (i == 3) {
                Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                this.mSynchronizedCaptureSessionOpener.stop();
                this.mState = State.RELEASED;
            } else if (i == 4) {
                Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                this.mSynchronizedCaptureSessionOpener.stop();
                this.mState = State.CLOSED;
                this.mSessionConfig = null;
                this.mCameraEventOnRepeatingOptions = null;
            } else if (i == 5) {
                if (this.mSessionConfig != null) {
                    List<CaptureConfig> listOnDisableSession = new Camera2ImplConfig(this.mSessionConfig.getImplementationOptions()).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onDisableSession();
                    if (!listOnDisableSession.isEmpty()) {
                        try {
                            issueCaptureRequests(setupConfiguredSurface(listOnDisableSession));
                        } catch (IllegalStateException e) {
                            Log.e(TAG, "Unable to issue the request before close the capture session", e);
                        }
                    }
                }
                Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                this.mSynchronizedCaptureSessionOpener.stop();
                this.mState = State.CLOSED;
                this.mSessionConfig = null;
                this.mCameraEventOnRepeatingOptions = null;
            }
        }
    }

    ListenableFuture<Void> release(boolean z) {
        synchronized (this.mStateLock) {
            switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()]) {
                case 1:
                    throw new IllegalStateException("release() should not be possible in state: " + this.mState);
                case 3:
                    Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                    this.mSynchronizedCaptureSessionOpener.stop();
                case 2:
                    this.mState = State.RELEASED;
                    return Futures.immediateFuture(null);
                case 5:
                case 6:
                    SynchronizedCaptureSession synchronizedCaptureSession = this.mSynchronizedCaptureSession;
                    if (synchronizedCaptureSession != null) {
                        if (z) {
                            try {
                                synchronizedCaptureSession.abortCaptures();
                            } catch (CameraAccessException e) {
                                Log.e(TAG, "Unable to abort captures.", e);
                            }
                        }
                        this.mSynchronizedCaptureSession.close();
                        break;
                    }
                case 4:
                    this.mState = State.RELEASING;
                    Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                    if (this.mSynchronizedCaptureSessionOpener.stop()) {
                        finishClose();
                        return Futures.immediateFuture(null);
                    }
                case 7:
                    if (this.mReleaseFuture == null) {
                        this.mReleaseFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.CaptureSession$$ExternalSyntheticLambda0
                            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                                return this.f$0.m41lambda$release$1$androidxcameracamera2internalCaptureSession(completer);
                            }
                        });
                    }
                    return this.mReleaseFuture;
                default:
                    return Futures.immediateFuture(null);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$release$1$androidx-camera-camera2-internal-CaptureSession, reason: not valid java name */
    /* synthetic */ Object m41lambda$release$1$androidxcameracamera2internalCaptureSession(CallbackToFutureAdapter.Completer completer) throws Exception {
        String str;
        synchronized (this.mStateLock) {
            Preconditions.checkState(this.mReleaseCompleter == null, "Release completer expected to be null");
            this.mReleaseCompleter = completer;
            str = "Release[session=" + this + "]";
        }
        return str;
    }

    void clearConfiguredSurfaces() {
        DeferrableSurfaces.decrementAll(this.mConfiguredDeferrableSurfaces);
        this.mConfiguredDeferrableSurfaces.clear();
    }

    void issueCaptureRequests(List<CaptureConfig> list) {
        synchronized (this.mStateLock) {
            switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()]) {
                case 1:
                    throw new IllegalStateException("issueCaptureRequests() should not be possible in state: " + this.mState);
                case 2:
                case 3:
                case 4:
                    this.mCaptureConfigs.addAll(list);
                    break;
                case 5:
                    this.mCaptureConfigs.addAll(list);
                    issuePendingCaptureRequest();
                    break;
                case 6:
                case 7:
                case 8:
                    throw new IllegalStateException("Cannot issue capture request on a closed/released session.");
            }
        }
    }

    List<CaptureConfig> getCaptureConfigs() {
        List<CaptureConfig> listUnmodifiableList;
        synchronized (this.mStateLock) {
            listUnmodifiableList = Collections.unmodifiableList(this.mCaptureConfigs);
        }
        return listUnmodifiableList;
    }

    State getState() {
        State state;
        synchronized (this.mStateLock) {
            state = this.mState;
        }
        return state;
    }

    void finishClose() {
        if (this.mState == State.RELEASED) {
            Log.d(TAG, "Skipping finishClose due to being state RELEASED.");
            return;
        }
        this.mState = State.RELEASED;
        this.mSynchronizedCaptureSession = null;
        clearConfiguredSurfaces();
        CallbackToFutureAdapter.Completer<Void> completer = this.mReleaseCompleter;
        if (completer != null) {
            completer.set(null);
            this.mReleaseCompleter = null;
        }
    }

    void issueRepeatingCaptureRequests() {
        if (this.mSessionConfig == null) {
            Log.d(TAG, "Skipping issueRepeatingCaptureRequests for no configuration case.");
            return;
        }
        CaptureConfig repeatingCaptureConfig = this.mSessionConfig.getRepeatingCaptureConfig();
        try {
            Log.d(TAG, "Issuing request for session.");
            CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(repeatingCaptureConfig);
            this.mCameraEventOnRepeatingOptions = mergeOptions(new Camera2ImplConfig(this.mSessionConfig.getImplementationOptions()).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onRepeating());
            if (this.mCameraEventOnRepeatingOptions != null) {
                builderFrom.addImplementationOptions(this.mCameraEventOnRepeatingOptions);
            }
            CaptureRequest captureRequestBuild = Camera2CaptureRequestBuilder.build(builderFrom.build(), this.mSynchronizedCaptureSession.getDevice(), this.mConfiguredSurfaceMap);
            if (captureRequestBuild == null) {
                Log.d(TAG, "Skipping issuing empty request for session.");
            } else {
                this.mSynchronizedCaptureSession.setSingleRepeatingRequest(captureRequestBuild, createCamera2CaptureCallback(repeatingCaptureConfig.getCameraCaptureCallbacks(), this.mCaptureCallback));
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Unable to access camera: " + e.getMessage());
            Thread.dumpStack();
        }
    }

    void issuePendingCaptureRequest() {
        if (this.mCaptureConfigs.isEmpty()) {
            return;
        }
        try {
            issueBurstCaptureRequest(this.mCaptureConfigs);
        } finally {
            this.mCaptureConfigs.clear();
        }
    }

    void issueBurstCaptureRequest(List<CaptureConfig> list) {
        if (list.isEmpty()) {
            return;
        }
        try {
            CameraBurstCaptureCallback cameraBurstCaptureCallback = new CameraBurstCaptureCallback();
            ArrayList arrayList = new ArrayList();
            Log.d(TAG, "Issuing capture request.");
            for (CaptureConfig captureConfig : list) {
                if (captureConfig.getSurfaces().isEmpty()) {
                    Log.d(TAG, "Skipping issuing empty capture request.");
                } else {
                    Iterator<DeferrableSurface> it = captureConfig.getSurfaces().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            DeferrableSurface next = it.next();
                            if (!this.mConfiguredSurfaceMap.containsKey(next)) {
                                Log.d(TAG, "Skipping capture request with invalid surface: " + next);
                                break;
                            }
                        } else {
                            CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(captureConfig);
                            if (this.mSessionConfig != null) {
                                builderFrom.addImplementationOptions(this.mSessionConfig.getRepeatingCaptureConfig().getImplementationOptions());
                            }
                            if (this.mCameraEventOnRepeatingOptions != null) {
                                builderFrom.addImplementationOptions(this.mCameraEventOnRepeatingOptions);
                            }
                            builderFrom.addImplementationOptions(captureConfig.getImplementationOptions());
                            CaptureRequest captureRequestBuild = Camera2CaptureRequestBuilder.build(builderFrom.build(), this.mSynchronizedCaptureSession.getDevice(), this.mConfiguredSurfaceMap);
                            if (captureRequestBuild == null) {
                                Log.d(TAG, "Skipping issuing request without surface.");
                                return;
                            }
                            ArrayList arrayList2 = new ArrayList();
                            Iterator<CameraCaptureCallback> it2 = captureConfig.getCameraCaptureCallbacks().iterator();
                            while (it2.hasNext()) {
                                CaptureCallbackConverter.toCaptureCallback(it2.next(), arrayList2);
                            }
                            cameraBurstCaptureCallback.addCamera2Callbacks(captureRequestBuild, arrayList2);
                            arrayList.add(captureRequestBuild);
                            break;
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                Log.d(TAG, "Skipping issuing burst request due to no valid request elements");
            } else {
                this.mSynchronizedCaptureSession.captureBurstRequests(arrayList, cameraBurstCaptureCallback);
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Unable to access camera: " + e.getMessage());
            Thread.dumpStack();
        }
    }

    void cancelIssuedCaptureRequests() {
        if (this.mCaptureConfigs.isEmpty()) {
            return;
        }
        Iterator<CaptureConfig> it = this.mCaptureConfigs.iterator();
        while (it.hasNext()) {
            Iterator<CameraCaptureCallback> it2 = it.next().getCameraCaptureCallbacks().iterator();
            while (it2.hasNext()) {
                it2.next().onCaptureCancelled();
            }
        }
        this.mCaptureConfigs.clear();
    }

    private CameraCaptureSession.CaptureCallback createCamera2CaptureCallback(List<CameraCaptureCallback> list, CameraCaptureSession.CaptureCallback... captureCallbackArr) {
        ArrayList arrayList = new ArrayList(list.size() + captureCallbackArr.length);
        Iterator<CameraCaptureCallback> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(CaptureCallbackConverter.toCaptureCallback(it.next()));
        }
        Collections.addAll(arrayList, captureCallbackArr);
        return Camera2CaptureCallbacks.createComboCallback(arrayList);
    }

    private static Config mergeOptions(List<CaptureConfig> list) {
        MutableOptionsBundle mutableOptionsBundleCreate = MutableOptionsBundle.create();
        Iterator<CaptureConfig> it = list.iterator();
        while (it.hasNext()) {
            Config implementationOptions = it.next().getImplementationOptions();
            for (Config.Option<?> option : implementationOptions.listOptions()) {
                Object objRetrieveOption = implementationOptions.retrieveOption(option, null);
                if (mutableOptionsBundleCreate.containsOption(option)) {
                    Object objRetrieveOption2 = mutableOptionsBundleCreate.retrieveOption(option, null);
                    if (!Objects.equals(objRetrieveOption2, objRetrieveOption)) {
                        Log.d(TAG, "Detect conflicting option " + option.getId() + " : " + objRetrieveOption + " != " + objRetrieveOption2);
                    }
                } else {
                    mutableOptionsBundleCreate.insertOption(option, objRetrieveOption);
                }
            }
        }
        return mutableOptionsBundleCreate;
    }

    final class StateCallback extends SynchronizedCaptureSession.StateCallback {
        StateCallback() {
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onConfigured(SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mStateLock) {
                switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 8:
                        throw new IllegalStateException("onConfigured() should not be possible in state: " + CaptureSession.this.mState);
                    case 4:
                        CaptureSession.this.mState = State.OPENED;
                        CaptureSession.this.mSynchronizedCaptureSession = synchronizedCaptureSession;
                        if (CaptureSession.this.mSessionConfig != null) {
                            List<CaptureConfig> listOnEnableSession = new Camera2ImplConfig(CaptureSession.this.mSessionConfig.getImplementationOptions()).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onEnableSession();
                            if (!listOnEnableSession.isEmpty()) {
                                CaptureSession captureSession = CaptureSession.this;
                                captureSession.issueBurstCaptureRequest(captureSession.setupConfiguredSurface(listOnEnableSession));
                            }
                        }
                        Log.d(CaptureSession.TAG, "Attempting to send capture request onConfigured");
                        CaptureSession.this.issueRepeatingCaptureRequests();
                        CaptureSession.this.issuePendingCaptureRequest();
                        break;
                    case 6:
                        CaptureSession.this.mSynchronizedCaptureSession = synchronizedCaptureSession;
                        break;
                    case 7:
                        synchronizedCaptureSession.close();
                        break;
                }
                Log.d(CaptureSession.TAG, "CameraCaptureSession.onConfigured() mState=" + CaptureSession.this.mState);
            }
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onReady(SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mStateLock) {
                if (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()] == 1) {
                    throw new IllegalStateException("onReady() should not be possible in state: " + CaptureSession.this.mState);
                }
                Log.d(CaptureSession.TAG, "CameraCaptureSession.onReady() " + CaptureSession.this.mState);
            }
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onClosed(SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mStateLock) {
                if (CaptureSession.this.mState == State.UNINITIALIZED) {
                    throw new IllegalStateException("onClosed() should not be possible in state: " + CaptureSession.this.mState);
                }
                Log.d(CaptureSession.TAG, "CameraCaptureSession.onClosed()");
                CaptureSession.this.finishClose();
            }
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onConfigureFailed(SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mStateLock) {
                switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 8:
                        throw new IllegalStateException("onConfigureFailed() should not be possible in state: " + CaptureSession.this.mState);
                    case 4:
                    case 6:
                    case 7:
                        CaptureSession.this.finishClose();
                        break;
                }
                Log.e(CaptureSession.TAG, "CameraCaptureSession.onConfigureFailed() " + CaptureSession.this.mState);
            }
        }
    }

    List<CaptureConfig> setupConfiguredSurface(List<CaptureConfig> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<CaptureConfig> it = list.iterator();
        while (it.hasNext()) {
            CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(it.next());
            builderFrom.setTemplateType(1);
            Iterator<DeferrableSurface> it2 = this.mSessionConfig.getRepeatingCaptureConfig().getSurfaces().iterator();
            while (it2.hasNext()) {
                builderFrom.addSurface(it2.next());
            }
            arrayList.add(builderFrom.build());
        }
        return arrayList;
    }
}
