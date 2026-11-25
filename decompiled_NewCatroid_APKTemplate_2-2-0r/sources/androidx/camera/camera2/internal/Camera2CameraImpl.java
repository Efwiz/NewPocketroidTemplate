package androidx.camera.camera2.internal;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraStateRegistry;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.LiveDataObservable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseAttachState;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
final class Camera2CameraImpl implements CameraInternal {
    private static final int ERROR_NONE = 0;
    private final CameraAvailability mCameraAvailability;
    private final Camera2CameraControl mCameraControlInternal;
    SessionConfig mCameraControlSessionConfig;
    CameraDevice mCameraDevice;
    int mCameraDeviceError;
    final Camera2CameraInfoImpl mCameraInfoInternal;
    private final CameraManagerCompat mCameraManager;
    private final CameraStateRegistry mCameraStateRegistry;
    CaptureSession mCaptureSession;
    private final SynchronizedCaptureSessionOpener.Builder mCaptureSessionOpenerBuilder;
    private final CaptureSessionRepository mCaptureSessionRepository;
    final Set<CaptureSession> mConfiguringForClose;
    private final Executor mExecutor;
    private MeteringRepeatingSession mMeteringRepeatingSession;
    private final LiveDataObservable<CameraInternal.State> mObservableState;
    final AtomicInteger mReleaseRequestCount;
    final Map<CaptureSession, ListenableFuture<Void>> mReleasedCaptureSessions;
    volatile InternalState mState = InternalState.INITIALIZED;
    private final StateCallback mStateCallback;
    private final UseCaseAttachState mUseCaseAttachState;
    ListenableFuture<Void> mUserReleaseFuture;
    CallbackToFutureAdapter.Completer<Void> mUserReleaseNotifier;
    private static final String TAG = "Camera2CameraImpl";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    enum InternalState {
        INITIALIZED,
        PENDING_OPEN,
        OPENING,
        OPENED,
        CLOSING,
        REOPENING,
        RELEASING,
        RELEASED
    }

    Camera2CameraImpl(CameraManagerCompat cameraManagerCompat, String str, CameraStateRegistry cameraStateRegistry, Executor executor, Handler handler) throws CameraUnavailableException {
        LiveDataObservable<CameraInternal.State> liveDataObservable = new LiveDataObservable<>();
        this.mObservableState = liveDataObservable;
        this.mCameraDeviceError = 0;
        this.mCameraControlSessionConfig = SessionConfig.defaultEmptySessionConfig();
        this.mReleaseRequestCount = new AtomicInteger(0);
        this.mReleasedCaptureSessions = new LinkedHashMap();
        this.mConfiguringForClose = new HashSet();
        this.mCameraManager = cameraManagerCompat;
        this.mCameraStateRegistry = cameraStateRegistry;
        ScheduledExecutorService scheduledExecutorServiceNewHandlerExecutor = CameraXExecutors.newHandlerExecutor(handler);
        Executor executorNewSequentialExecutor = CameraXExecutors.newSequentialExecutor(executor);
        this.mExecutor = executorNewSequentialExecutor;
        this.mStateCallback = new StateCallback(executorNewSequentialExecutor, scheduledExecutorServiceNewHandlerExecutor);
        this.mUseCaseAttachState = new UseCaseAttachState(str);
        liveDataObservable.postValue(CameraInternal.State.CLOSED);
        CaptureSessionRepository captureSessionRepository = new CaptureSessionRepository(executorNewSequentialExecutor);
        this.mCaptureSessionRepository = captureSessionRepository;
        try {
            CameraCharacteristics cameraCharacteristics = cameraManagerCompat.getCameraCharacteristics(str);
            Camera2CameraControl camera2CameraControl = new Camera2CameraControl(cameraCharacteristics, scheduledExecutorServiceNewHandlerExecutor, executorNewSequentialExecutor, new ControlUpdateListenerInternal());
            this.mCameraControlInternal = camera2CameraControl;
            Camera2CameraInfoImpl camera2CameraInfoImpl = new Camera2CameraInfoImpl(str, cameraCharacteristics, camera2CameraControl);
            this.mCameraInfoInternal = camera2CameraInfoImpl;
            this.mCaptureSessionOpenerBuilder = new SynchronizedCaptureSessionOpener.Builder(executorNewSequentialExecutor, scheduledExecutorServiceNewHandlerExecutor, handler, captureSessionRepository, camera2CameraInfoImpl.getSupportedHardwareLevel());
            this.mCaptureSession = new CaptureSession();
            CameraAvailability cameraAvailability = new CameraAvailability(str);
            this.mCameraAvailability = cameraAvailability;
            cameraStateRegistry.registerCamera(this, executorNewSequentialExecutor, cameraAvailability);
            cameraManagerCompat.registerAvailabilityCallback(executorNewSequentialExecutor, cameraAvailability);
        } catch (CameraAccessExceptionCompat e) {
            throw CameraUnavailableExceptionHelper.createFrom(e);
        }
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public void open() {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.openInternal();
            }
        });
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.internal.Camera2CameraImpl$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState;

        static {
            int[] iArr = new int[InternalState.values().length];
            $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState = iArr;
            try {
                iArr[InternalState.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.CLOSING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.OPENED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.OPENING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.REOPENING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.PENDING_OPEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.RELEASING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[InternalState.RELEASED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openInternal() {
        int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[this.mState.ordinal()];
        if (i == 1) {
            openCameraDevice();
            return;
        }
        if (i == 2) {
            setState(InternalState.REOPENING);
            if (isSessionCloseComplete() || this.mCameraDeviceError != 0) {
                return;
            }
            Preconditions.checkState(this.mCameraDevice != null, "Camera Device should be open if session close is not complete");
            setState(InternalState.OPENED);
            openCaptureSession();
            return;
        }
        debugLog("open() ignored due to being in state: " + this.mState);
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public void close() {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.closeInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeInternal() {
        debugLog("Closing camera.");
        int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[this.mState.ordinal()];
        if (i == 3) {
            setState(InternalState.CLOSING);
            closeCamera(false);
            return;
        }
        if (i != 4 && i != 5) {
            if (i == 6) {
                Preconditions.checkState(this.mCameraDevice == null);
                setState(InternalState.INITIALIZED);
                return;
            } else {
                debugLog("close() ignored due to being in state: " + this.mState);
                return;
            }
        }
        boolean zCancelScheduledReopen = this.mStateCallback.cancelScheduledReopen();
        setState(InternalState.CLOSING);
        if (zCancelScheduledReopen) {
            Preconditions.checkState(isSessionCloseComplete());
            finishClose();
        }
    }

    private void configAndClose(boolean z) {
        final CaptureSession captureSession = new CaptureSession();
        this.mConfiguringForClose.add(captureSession);
        resetCaptureSession(z);
        final SurfaceTexture surfaceTexture = new SurfaceTexture(0);
        surfaceTexture.setDefaultBufferSize(640, 480);
        final Surface surface = new Surface(surfaceTexture);
        final Runnable runnable = new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                Camera2CameraImpl.lambda$configAndClose$0(surface, surfaceTexture);
            }
        };
        SessionConfig.Builder builder = new SessionConfig.Builder();
        builder.addNonRepeatingSurface(new ImmediateSurface(surface));
        builder.setTemplateType(1);
        debugLog("Start configAndClose.");
        captureSession.open(builder.build(), (CameraDevice) Preconditions.checkNotNull(this.mCameraDevice), this.mCaptureSessionOpenerBuilder.build()).addListener(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m27x2a0cb35d(captureSession, runnable);
            }
        }, this.mExecutor);
    }

    static /* synthetic */ void lambda$configAndClose$0(Surface surface, SurfaceTexture surfaceTexture) {
        surface.release();
        surfaceTexture.release();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: releaseDummySession, reason: merged with bridge method [inline-methods] */
    public void m27x2a0cb35d(CaptureSession captureSession, Runnable runnable) {
        this.mConfiguringForClose.remove(captureSession);
        releaseSession(captureSession, false).addListener(runnable, CameraXExecutors.directExecutor());
    }

    boolean isSessionCloseComplete() {
        return this.mReleasedCaptureSessions.isEmpty() && this.mConfiguringForClose.isEmpty();
    }

    void finishClose() {
        Preconditions.checkState(this.mState == InternalState.RELEASING || this.mState == InternalState.CLOSING);
        Preconditions.checkState(this.mReleasedCaptureSessions.isEmpty());
        this.mCameraDevice = null;
        if (this.mState == InternalState.CLOSING) {
            setState(InternalState.INITIALIZED);
            return;
        }
        this.mCameraManager.unregisterAvailabilityCallback(this.mCameraAvailability);
        setState(InternalState.RELEASED);
        CallbackToFutureAdapter.Completer<Void> completer = this.mUserReleaseNotifier;
        if (completer != null) {
            completer.set(null);
            this.mUserReleaseNotifier = null;
        }
    }

    void closeCamera(boolean z) {
        Preconditions.checkState(this.mState == InternalState.CLOSING || this.mState == InternalState.RELEASING || (this.mState == InternalState.REOPENING && this.mCameraDeviceError != 0), "closeCamera should only be called in a CLOSING, RELEASING or REOPENING (with error) state. Current state: " + this.mState + " (error: " + getErrorMessage(this.mCameraDeviceError) + Constant.AFTER_QUTO);
        if (Build.VERSION.SDK_INT < 29 && isLegacyDevice() && this.mCameraDeviceError == 0) {
            configAndClose(z);
        } else {
            resetCaptureSession(z);
        }
        this.mCaptureSession.cancelIssuedCaptureRequests();
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public ListenableFuture<Void> release() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m37xc715f071(completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$release$3$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ Object m37xc715f071(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m36x993d5612(completer);
            }
        });
        return "Release[request=" + this.mReleaseRequestCount.getAndIncrement() + "]";
    }

    /* JADX INFO: renamed from: lambda$release$2$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ void m36x993d5612(CallbackToFutureAdapter.Completer completer) {
        Futures.propagate(releaseInternal(), completer);
    }

    private ListenableFuture<Void> releaseInternal() {
        ListenableFuture<Void> orCreateUserReleaseFuture = getOrCreateUserReleaseFuture();
        switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[this.mState.ordinal()]) {
            case 1:
            case 6:
                Preconditions.checkState(this.mCameraDevice == null);
                setState(InternalState.RELEASING);
                Preconditions.checkState(isSessionCloseComplete());
                finishClose();
                return orCreateUserReleaseFuture;
            case 2:
            case 4:
            case 5:
            case 7:
                boolean zCancelScheduledReopen = this.mStateCallback.cancelScheduledReopen();
                setState(InternalState.RELEASING);
                if (zCancelScheduledReopen) {
                    Preconditions.checkState(isSessionCloseComplete());
                    finishClose();
                }
                return orCreateUserReleaseFuture;
            case 3:
                setState(InternalState.RELEASING);
                closeCamera(true);
                return orCreateUserReleaseFuture;
            default:
                debugLog("release() ignored due to being in state: " + this.mState);
                return orCreateUserReleaseFuture;
        }
    }

    private ListenableFuture<Void> getOrCreateUserReleaseFuture() {
        if (this.mUserReleaseFuture == null) {
            if (this.mState != InternalState.RELEASED) {
                this.mUserReleaseFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda5
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        return this.f$0.m29x42886673(completer);
                    }
                });
            } else {
                this.mUserReleaseFuture = Futures.immediateFuture(null);
            }
        }
        return this.mUserReleaseFuture;
    }

    /* JADX INFO: renamed from: lambda$getOrCreateUserReleaseFuture$4$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ Object m29x42886673(CallbackToFutureAdapter.Completer completer) throws Exception {
        Preconditions.checkState(this.mUserReleaseNotifier == null, "Camera can only be released once, so release completer should be null on creation.");
        this.mUserReleaseNotifier = completer;
        return "Release[camera=" + this + "]";
    }

    ListenableFuture<Void> releaseSession(final CaptureSession captureSession, boolean z) {
        captureSession.close();
        ListenableFuture<Void> listenableFutureRelease = captureSession.release(z);
        debugLog("Releasing session in state " + this.mState.name());
        this.mReleasedCaptureSessions.put(captureSession, listenableFutureRelease);
        Futures.addCallback(listenableFutureRelease, new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Void r2) {
                Camera2CameraImpl.this.mReleasedCaptureSessions.remove(captureSession);
                int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
                if (i != 2) {
                    if (i != 5) {
                        if (i != 7) {
                            return;
                        }
                    } else if (Camera2CameraImpl.this.mCameraDeviceError == 0) {
                        return;
                    }
                }
                if (!Camera2CameraImpl.this.isSessionCloseComplete() || Camera2CameraImpl.this.mCameraDevice == null) {
                    return;
                }
                Camera2CameraImpl.this.mCameraDevice.close();
                Camera2CameraImpl.this.mCameraDevice = null;
            }
        }, CameraXExecutors.directExecutor());
        return listenableFutureRelease;
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public Observable<CameraInternal.State> getCameraState() {
        return this.mObservableState;
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseActive(final UseCase useCase) {
        Preconditions.checkNotNull(useCase);
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m32xeff284f8(useCase);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onUseCaseActive$5$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ void m32xeff284f8(UseCase useCase) {
        debugLog("Use case " + useCase + " ACTIVE");
        try {
            this.mUseCaseAttachState.setUseCaseActive(useCase.getName() + useCase.hashCode(), useCase.getSessionConfig());
            this.mUseCaseAttachState.updateUseCase(useCase.getName() + useCase.hashCode(), useCase.getSessionConfig());
            updateCaptureSessionConfig();
        } catch (NullPointerException unused) {
            debugLog("Failed to set already detached use case active");
        }
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseInactive(final UseCase useCase) {
        Preconditions.checkNotNull(useCase);
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m33x8da1a1d2(useCase);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onUseCaseInactive$6$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ void m33x8da1a1d2(UseCase useCase) {
        debugLog("Use case " + useCase + " INACTIVE");
        this.mUseCaseAttachState.setUseCaseInactive(useCase.getName() + useCase.hashCode());
        updateCaptureSessionConfig();
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseUpdated(final UseCase useCase) {
        Preconditions.checkNotNull(useCase);
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m35xe8973177(useCase);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onUseCaseUpdated$7$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ void m35xe8973177(UseCase useCase) {
        debugLog("Use case " + useCase + " UPDATED");
        this.mUseCaseAttachState.updateUseCase(useCase.getName() + useCase.hashCode(), useCase.getSessionConfig());
        updateCaptureSessionConfig();
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseReset(final UseCase useCase) {
        Preconditions.checkNotNull(useCase);
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m34x7c3de682(useCase);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onUseCaseReset$8$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ void m34x7c3de682(UseCase useCase) {
        debugLog("Use case " + useCase + " RESET");
        this.mUseCaseAttachState.updateUseCase(useCase.getName() + useCase.hashCode(), useCase.getSessionConfig());
        resetCaptureSession(false);
        updateCaptureSessionConfig();
        if (this.mState == InternalState.OPENED) {
            openCaptureSession();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean isUseCaseAttached(final UseCase useCase) {
        try {
            return ((Boolean) CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda4
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m30x9bdb441b(useCase, completer);
                }
            }).get()).booleanValue();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Unable to check if use case is attached.", e);
        }
    }

    /* JADX INFO: renamed from: lambda$isUseCaseAttached$10$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ Object m30x9bdb441b(final UseCase useCase, final CallbackToFutureAdapter.Completer completer) throws Exception {
        try {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m31x93ceab21(completer, useCase);
                }
            });
            return "isUseCaseAttached";
        } catch (RejectedExecutionException unused) {
            completer.setException(new RuntimeException("Unable to check if use case is attached. Camera executor shut down."));
            return "isUseCaseAttached";
        }
    }

    /* JADX INFO: renamed from: lambda$isUseCaseAttached$9$androidx-camera-camera2-internal-Camera2CameraImpl, reason: not valid java name */
    /* synthetic */ void m31x93ceab21(CallbackToFutureAdapter.Completer completer, UseCase useCase) {
        completer.set(Boolean.valueOf(this.mUseCaseAttachState.isUseCaseAttached(useCase.getName() + useCase.hashCode())));
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public void attachUseCases(final Collection<UseCase> collection) {
        if (collection.isEmpty()) {
            return;
        }
        this.mCameraControlInternal.setActive(true);
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m26x65960ee4(collection);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: tryAttachUseCases, reason: merged with bridge method [inline-methods] */
    public void m26x65960ee4(Collection<UseCase> collection) {
        ArrayList arrayList = new ArrayList();
        for (UseCase useCase : collection) {
            if (!this.mUseCaseAttachState.isUseCaseAttached(useCase.getName() + useCase.hashCode())) {
                try {
                    this.mUseCaseAttachState.setUseCaseAttached(useCase.getName() + useCase.hashCode(), useCase.getSessionConfig());
                    arrayList.add(useCase);
                } catch (NullPointerException unused) {
                    debugLog("Failed to attach a detached use case");
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        debugLog("Use cases [" + TextUtils.join(", ", arrayList) + "] now ATTACHED");
        notifyStateAttachedToUseCases(arrayList);
        addOrRemoveMeteringRepeatingUseCase();
        updateCaptureSessionConfig();
        resetCaptureSession(false);
        if (this.mState == InternalState.OPENED) {
            openCaptureSession();
        } else {
            openInternal();
        }
        updateCameraControlPreviewAspectRatio(arrayList);
    }

    private void notifyStateAttachedToUseCases(final List<UseCase> list) {
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                Camera2CameraImpl.lambda$notifyStateAttachedToUseCases$12(list);
            }
        });
    }

    static /* synthetic */ void lambda$notifyStateAttachedToUseCases$12(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((UseCase) it.next()).onStateAttached();
        }
    }

    private void notifyStateDetachedToUseCases(final List<UseCase> list) {
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                Camera2CameraImpl.lambda$notifyStateDetachedToUseCases$13(list);
            }
        });
    }

    static /* synthetic */ void lambda$notifyStateDetachedToUseCases$13(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((UseCase) it.next()).onStateDetached();
        }
    }

    private void updateCameraControlPreviewAspectRatio(Collection<UseCase> collection) {
        for (UseCase useCase : collection) {
            if (useCase instanceof Preview) {
                Size size = (Size) Preconditions.checkNotNull(useCase.getAttachedSurfaceResolution());
                this.mCameraControlInternal.setPreviewAspectRatio(new Rational(size.getWidth(), size.getHeight()));
                return;
            }
        }
    }

    private void clearCameraControlPreviewAspectRatio(Collection<UseCase> collection) {
        Iterator<UseCase> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof Preview) {
                this.mCameraControlInternal.setPreviewAspectRatio(null);
                return;
            }
        }
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public void detachUseCases(final Collection<UseCase> collection) {
        if (collection.isEmpty()) {
            return;
        }
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m28xe41392cf(collection);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: tryDetachUseCases, reason: merged with bridge method [inline-methods] */
    public void m28xe41392cf(Collection<UseCase> collection) {
        List<UseCase> arrayList = new ArrayList<>();
        for (UseCase useCase : collection) {
            if (this.mUseCaseAttachState.isUseCaseAttached(useCase.getName() + useCase.hashCode())) {
                this.mUseCaseAttachState.setUseCaseDetached(useCase.getName() + useCase.hashCode());
                arrayList.add(useCase);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        debugLog("Use cases [" + TextUtils.join(", ", arrayList) + "] now DETACHED for camera");
        clearCameraControlPreviewAspectRatio(arrayList);
        notifyStateDetachedToUseCases(arrayList);
        addOrRemoveMeteringRepeatingUseCase();
        if (this.mUseCaseAttachState.getAttachedSessionConfigs().isEmpty()) {
            this.mCameraControlInternal.setActive(false);
            resetCaptureSession(false);
            this.mCaptureSession = new CaptureSession();
            closeInternal();
            return;
        }
        updateCaptureSessionConfig();
        resetCaptureSession(false);
        if (this.mState == InternalState.OPENED) {
            openCaptureSession();
        }
    }

    private void addOrRemoveMeteringRepeatingUseCase() {
        SessionConfig sessionConfigBuild = this.mUseCaseAttachState.getAttachedBuilder().build();
        CaptureConfig repeatingCaptureConfig = sessionConfigBuild.getRepeatingCaptureConfig();
        int size = repeatingCaptureConfig.getSurfaces().size();
        int size2 = sessionConfigBuild.getSurfaces().size();
        if (sessionConfigBuild.getSurfaces().isEmpty()) {
            return;
        }
        if (repeatingCaptureConfig.getSurfaces().isEmpty()) {
            if (this.mMeteringRepeatingSession == null) {
                this.mMeteringRepeatingSession = new MeteringRepeatingSession();
            }
            addMeteringRepeating();
        } else if (size2 == 1 && size == 1) {
            removeMeteringRepeating();
        } else if (size >= 2) {
            removeMeteringRepeating();
        } else {
            Log.d(TAG, "mMeteringRepeating is ATTACHED, SessionConfig Surfaces: " + size2 + ", CaptureConfig Surfaces: " + size);
        }
    }

    private void removeMeteringRepeating() {
        if (this.mMeteringRepeatingSession != null) {
            this.mUseCaseAttachState.setUseCaseDetached(this.mMeteringRepeatingSession.getName() + this.mMeteringRepeatingSession.hashCode());
            this.mUseCaseAttachState.setUseCaseInactive(this.mMeteringRepeatingSession.getName() + this.mMeteringRepeatingSession.hashCode());
            this.mMeteringRepeatingSession.clear();
            this.mMeteringRepeatingSession = null;
        }
    }

    private void addMeteringRepeating() {
        if (this.mMeteringRepeatingSession != null) {
            this.mUseCaseAttachState.setUseCaseAttached(this.mMeteringRepeatingSession.getName() + this.mMeteringRepeatingSession.hashCode(), this.mMeteringRepeatingSession.getSessionConfig());
            this.mUseCaseAttachState.setUseCaseActive(this.mMeteringRepeatingSession.getName() + this.mMeteringRepeatingSession.hashCode(), this.mMeteringRepeatingSession.getSessionConfig());
        }
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public CameraInfoInternal getCameraInfoInternal() {
        return this.mCameraInfoInternal;
    }

    void openCameraDevice() {
        this.mStateCallback.cancelScheduledReopen();
        if (!this.mCameraAvailability.isCameraAvailable() || !this.mCameraStateRegistry.tryOpenCamera(this)) {
            debugLog("No cameras available. Waiting for available camera before opening camera.");
            setState(InternalState.PENDING_OPEN);
            return;
        }
        setState(InternalState.OPENING);
        debugLog("Opening camera.");
        try {
            this.mCameraManager.openCamera(this.mCameraInfoInternal.getCameraId(), this.mExecutor, createDeviceStateCallback());
        } catch (CameraAccessExceptionCompat e) {
            debugLog("Unable to open camera due to " + e.getMessage());
            if (e.getReason() != 10001) {
                return;
            }
            setState(InternalState.INITIALIZED);
        }
    }

    void updateCaptureSessionConfig() {
        SessionConfig.ValidatingBuilder activeAndAttachedBuilder = this.mUseCaseAttachState.getActiveAndAttachedBuilder();
        if (activeAndAttachedBuilder.isValid()) {
            activeAndAttachedBuilder.add(this.mCameraControlSessionConfig);
            this.mCaptureSession.setSessionConfig(activeAndAttachedBuilder.build());
        }
    }

    void openCaptureSession() {
        Preconditions.checkState(this.mState == InternalState.OPENED);
        SessionConfig.ValidatingBuilder attachedBuilder = this.mUseCaseAttachState.getAttachedBuilder();
        if (!attachedBuilder.isValid()) {
            debugLog("Unable to create capture session due to conflicting configurations");
        } else {
            Futures.addCallback(this.mCaptureSession.open(attachedBuilder.build(), (CameraDevice) Preconditions.checkNotNull(this.mCameraDevice), this.mCaptureSessionOpenerBuilder.build()), new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl.2
                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onSuccess(Void r1) {
                }

                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onFailure(Throwable th) {
                    if (th instanceof CameraAccessException) {
                        Camera2CameraImpl.this.debugLog("Unable to configure camera due to " + th.getMessage());
                        return;
                    }
                    if (th instanceof CancellationException) {
                        Camera2CameraImpl.this.debugLog("Unable to configure camera cancelled");
                        return;
                    }
                    if (th instanceof DeferrableSurface.SurfaceClosedException) {
                        SessionConfig sessionConfigFindSessionConfigForSurface = Camera2CameraImpl.this.findSessionConfigForSurface(((DeferrableSurface.SurfaceClosedException) th).getDeferrableSurface());
                        if (sessionConfigFindSessionConfigForSurface != null) {
                            Camera2CameraImpl.this.postSurfaceClosedError(sessionConfigFindSessionConfigForSurface);
                            return;
                        }
                        return;
                    }
                    if (th instanceof TimeoutException) {
                        Log.e(Camera2CameraImpl.TAG, "Unable to configure camera " + Camera2CameraImpl.this.mCameraInfoInternal.getCameraId() + ", timeout!");
                        return;
                    }
                    throw new RuntimeException(th);
                }
            }, this.mExecutor);
        }
    }

    private boolean isLegacyDevice() {
        return ((Camera2CameraInfoImpl) getCameraInfoInternal()).getSupportedHardwareLevel() == 2;
    }

    SessionConfig findSessionConfigForSurface(DeferrableSurface deferrableSurface) {
        for (SessionConfig sessionConfig : this.mUseCaseAttachState.getAttachedSessionConfigs()) {
            if (sessionConfig.getSurfaces().contains(deferrableSurface)) {
                return sessionConfig;
            }
        }
        return null;
    }

    void postSurfaceClosedError(final SessionConfig sessionConfig) {
        ScheduledExecutorService scheduledExecutorServiceMainThreadExecutor = CameraXExecutors.mainThreadExecutor();
        List<SessionConfig.ErrorListener> errorListeners = sessionConfig.getErrorListeners();
        if (errorListeners.isEmpty()) {
            return;
        }
        final SessionConfig.ErrorListener errorListener = errorListeners.get(0);
        debugLog("Posting surface closed", new Throwable());
        scheduledExecutorServiceMainThreadExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                errorListener.onError(sessionConfig, SessionConfig.SessionError.SESSION_ERROR_SURFACE_NEEDS_RESET);
            }
        });
    }

    void resetCaptureSession(boolean z) {
        Preconditions.checkState(this.mCaptureSession != null);
        debugLog("Resetting Capture Session");
        CaptureSession captureSession = this.mCaptureSession;
        SessionConfig sessionConfig = captureSession.getSessionConfig();
        List<CaptureConfig> captureConfigs = captureSession.getCaptureConfigs();
        CaptureSession captureSession2 = new CaptureSession();
        this.mCaptureSession = captureSession2;
        captureSession2.setSessionConfig(sessionConfig);
        this.mCaptureSession.issueCaptureRequests(captureConfigs);
        releaseSession(captureSession, z);
    }

    private CameraDevice.StateCallback createDeviceStateCallback() {
        ArrayList arrayList = new ArrayList(this.mUseCaseAttachState.getAttachedBuilder().build().getDeviceStateCallbacks());
        arrayList.add(this.mStateCallback);
        arrayList.add(this.mCaptureSessionRepository.getCameraStateCallback());
        return CameraDeviceStateCallbacks.createComboCallback(arrayList);
    }

    private boolean checkAndAttachRepeatingSurface(CaptureConfig.Builder builder) {
        if (!builder.getSurfaces().isEmpty()) {
            Log.w(TAG, "The capture config builder already has surface inside.");
            return false;
        }
        Iterator<SessionConfig> it = this.mUseCaseAttachState.getActiveAndAttachedSessionConfigs().iterator();
        while (it.hasNext()) {
            List<DeferrableSurface> surfaces = it.next().getRepeatingCaptureConfig().getSurfaces();
            if (!surfaces.isEmpty()) {
                Iterator<DeferrableSurface> it2 = surfaces.iterator();
                while (it2.hasNext()) {
                    builder.addSurface(it2.next());
                }
            }
        }
        if (!builder.getSurfaces().isEmpty()) {
            return true;
        }
        Log.w(TAG, "Unable to find a repeating surface to attach to CaptureConfig");
        return false;
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public CameraControlInternal getCameraControlInternal() {
        return this.mCameraControlInternal;
    }

    void submitCaptureRequests(List<CaptureConfig> list) {
        ArrayList arrayList = new ArrayList();
        for (CaptureConfig captureConfig : list) {
            CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(captureConfig);
            if (!captureConfig.getSurfaces().isEmpty() || !captureConfig.isUseRepeatingSurface() || checkAndAttachRepeatingSurface(builderFrom)) {
                arrayList.add(builderFrom.build());
            }
        }
        debugLog("Issue capture request");
        this.mCaptureSession.issueCaptureRequests(arrayList);
    }

    public String toString() {
        return String.format(Locale.US, "Camera@%x[id=%s]", Integer.valueOf(hashCode()), this.mCameraInfoInternal.getCameraId());
    }

    void debugLog(String str) {
        debugLog(str, null);
    }

    private void debugLog(String str, Throwable th) {
        if (DEBUG) {
            String str2 = String.format("{%s} %s", toString(), str);
            if (th == null) {
                Log.d(TAG, str2);
            } else {
                Log.d(TAG, str2, th);
            }
        }
    }

    @Override // androidx.camera.core.Camera
    public CameraControl getCameraControl() {
        return getCameraControlInternal();
    }

    @Override // androidx.camera.core.Camera
    public CameraInfo getCameraInfo() {
        return getCameraInfoInternal();
    }

    void setState(InternalState internalState) {
        CameraInternal.State state;
        debugLog("Transitioning camera internal state: " + this.mState + " --> " + internalState);
        this.mState = internalState;
        switch (AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[internalState.ordinal()]) {
            case 1:
                state = CameraInternal.State.CLOSED;
                break;
            case 2:
                state = CameraInternal.State.CLOSING;
                break;
            case 3:
                state = CameraInternal.State.OPEN;
                break;
            case 4:
            case 5:
                state = CameraInternal.State.OPENING;
                break;
            case 6:
                state = CameraInternal.State.PENDING_OPEN;
                break;
            case 7:
                state = CameraInternal.State.RELEASING;
                break;
            case 8:
                state = CameraInternal.State.RELEASED;
                break;
            default:
                throw new IllegalStateException("Unknown state: " + internalState);
        }
        this.mCameraStateRegistry.markCameraState(this, state);
        this.mObservableState.postValue(state);
    }

    static String getErrorMessage(int i) {
        if (i == 0) {
            return "ERROR_NONE";
        }
        if (i == 1) {
            return "ERROR_CAMERA_IN_USE";
        }
        if (i == 2) {
            return "ERROR_MAX_CAMERAS_IN_USE";
        }
        if (i == 3) {
            return "ERROR_CAMERA_DISABLED";
        }
        if (i == 4) {
            return "ERROR_CAMERA_DEVICE";
        }
        if (i == 5) {
            return "ERROR_CAMERA_SERVICE";
        }
        return "UNKNOWN ERROR";
    }

    final class StateCallback extends CameraDevice.StateCallback {
        private static final int REOPEN_DELAY_MS = 700;
        private final Executor mExecutor;
        ScheduledFuture<?> mScheduledReopenHandle;
        private ScheduledReopen mScheduledReopenRunnable;
        private final ScheduledExecutorService mScheduler;

        StateCallback(Executor executor, ScheduledExecutorService scheduledExecutorService) {
            this.mExecutor = executor;
            this.mScheduler = scheduledExecutorService;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
            Camera2CameraImpl.this.debugLog("CameraDevice.onOpened()");
            Camera2CameraImpl.this.mCameraDevice = cameraDevice;
            Camera2CameraImpl.this.updateDefaultRequestBuilderToCameraControl(cameraDevice);
            Camera2CameraImpl.this.mCameraDeviceError = 0;
            int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
            if (i == 2 || i == 7) {
                Preconditions.checkState(Camera2CameraImpl.this.isSessionCloseComplete());
                Camera2CameraImpl.this.mCameraDevice.close();
                Camera2CameraImpl.this.mCameraDevice = null;
            } else {
                if (i == 4 || i == 5) {
                    Camera2CameraImpl.this.setState(InternalState.OPENED);
                    Camera2CameraImpl.this.openCaptureSession();
                    return;
                }
                throw new IllegalStateException("onOpened() should not be possible from state: " + Camera2CameraImpl.this.mState);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClosed(CameraDevice cameraDevice) {
            Camera2CameraImpl.this.debugLog("CameraDevice.onClosed()");
            Preconditions.checkState(Camera2CameraImpl.this.mCameraDevice == null, "Unexpected onClose callback on camera device: " + cameraDevice);
            int i = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
            if (i != 2) {
                if (i == 5) {
                    if (Camera2CameraImpl.this.mCameraDeviceError != 0) {
                        Preconditions.checkState(this.mScheduledReopenRunnable == null);
                        Preconditions.checkState(this.mScheduledReopenHandle == null);
                        this.mScheduledReopenRunnable = new ScheduledReopen(this.mExecutor);
                        Camera2CameraImpl.this.debugLog("Camera closed due to error: " + Camera2CameraImpl.getErrorMessage(Camera2CameraImpl.this.mCameraDeviceError) + ". Attempting re-open in 700ms: " + this.mScheduledReopenRunnable);
                        this.mScheduledReopenHandle = this.mScheduler.schedule(this.mScheduledReopenRunnable, 700L, TimeUnit.MILLISECONDS);
                        return;
                    }
                    Camera2CameraImpl.this.openCameraDevice();
                    return;
                }
                if (i != 7) {
                    throw new IllegalStateException("Camera closed while in state: " + Camera2CameraImpl.this.mState);
                }
            }
            Preconditions.checkState(Camera2CameraImpl.this.isSessionCloseComplete());
            Camera2CameraImpl.this.finishClose();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            Camera2CameraImpl.this.debugLog("CameraDevice.onDisconnected()");
            onError(cameraDevice, 1);
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i) {
            Camera2CameraImpl.this.mCameraDevice = cameraDevice;
            Camera2CameraImpl.this.mCameraDeviceError = i;
            int i2 = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
            if (i2 != 2) {
                if (i2 == 3 || i2 == 4 || i2 == 5) {
                    handleErrorOnOpen(cameraDevice, i);
                    return;
                } else if (i2 != 7) {
                    throw new IllegalStateException("onError() should not be possible from state: " + Camera2CameraImpl.this.mState);
                }
            }
            Log.e(Camera2CameraImpl.TAG, "CameraDevice.onError(): " + cameraDevice.getId() + " with error: " + Camera2CameraImpl.getErrorMessage(i));
            Camera2CameraImpl.this.closeCamera(false);
        }

        private void handleErrorOnOpen(CameraDevice cameraDevice, int i) {
            Preconditions.checkState(Camera2CameraImpl.this.mState == InternalState.OPENING || Camera2CameraImpl.this.mState == InternalState.OPENED || Camera2CameraImpl.this.mState == InternalState.REOPENING, "Attempt to handle open error from non open state: " + Camera2CameraImpl.this.mState);
            if (i == 1 || i == 2 || i == 4) {
                reopenCameraAfterError();
                return;
            }
            Log.e(Camera2CameraImpl.TAG, "Error observed on open (or opening) camera device " + cameraDevice.getId() + ": " + Camera2CameraImpl.getErrorMessage(i));
            Camera2CameraImpl.this.setState(InternalState.CLOSING);
            Camera2CameraImpl.this.closeCamera(false);
        }

        private void reopenCameraAfterError() {
            Preconditions.checkState(Camera2CameraImpl.this.mCameraDeviceError != 0, "Can only reopen camera device after error if the camera device is actually in an error state.");
            Camera2CameraImpl.this.setState(InternalState.REOPENING);
            Camera2CameraImpl.this.closeCamera(false);
        }

        boolean cancelScheduledReopen() {
            if (this.mScheduledReopenHandle == null) {
                return false;
            }
            Camera2CameraImpl.this.debugLog("Cancelling scheduled re-open: " + this.mScheduledReopenRunnable);
            this.mScheduledReopenRunnable.cancel();
            this.mScheduledReopenRunnable = null;
            this.mScheduledReopenHandle.cancel(false);
            this.mScheduledReopenHandle = null;
            return true;
        }

        class ScheduledReopen implements Runnable {
            private boolean mCancelled = false;
            private Executor mExecutor;

            ScheduledReopen(Executor executor) {
                this.mExecutor = executor;
            }

            void cancel() {
                this.mCancelled = true;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CameraImpl$StateCallback$ScheduledReopen$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m38x10b58748();
                    }
                });
            }

            /* JADX INFO: renamed from: lambda$run$0$androidx-camera-camera2-internal-Camera2CameraImpl$StateCallback$ScheduledReopen, reason: not valid java name */
            /* synthetic */ void m38x10b58748() {
                if (this.mCancelled) {
                    return;
                }
                Preconditions.checkState(Camera2CameraImpl.this.mState == InternalState.REOPENING);
                Camera2CameraImpl.this.openCameraDevice();
            }
        }
    }

    void updateDefaultRequestBuilderToCameraControl(CameraDevice cameraDevice) {
        try {
            this.mCameraControlInternal.setDefaultRequestBuilder(cameraDevice.createCaptureRequest(this.mCameraControlInternal.getDefaultTemplate()));
        } catch (CameraAccessException e) {
            Log.e(TAG, "fail to create capture request.", e);
        }
    }

    final class CameraAvailability extends CameraManager.AvailabilityCallback implements CameraStateRegistry.OnOpenAvailableListener {
        private boolean mCameraAvailable = true;
        private final String mCameraId;

        CameraAvailability(String str) {
            this.mCameraId = str;
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraAvailable(String str) {
            if (this.mCameraId.equals(str)) {
                this.mCameraAvailable = true;
                if (Camera2CameraImpl.this.mState == InternalState.PENDING_OPEN) {
                    Camera2CameraImpl.this.openCameraDevice();
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraUnavailable(String str) {
            if (this.mCameraId.equals(str)) {
                this.mCameraAvailable = false;
            }
        }

        @Override // androidx.camera.core.impl.CameraStateRegistry.OnOpenAvailableListener
        public void onOpenAvailable() {
            if (Camera2CameraImpl.this.mState == InternalState.PENDING_OPEN) {
                Camera2CameraImpl.this.openCameraDevice();
            }
        }

        boolean isCameraAvailable() {
            return this.mCameraAvailable;
        }
    }

    final class ControlUpdateListenerInternal implements CameraControlInternal.ControlUpdateCallback {
        ControlUpdateListenerInternal() {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal.ControlUpdateCallback
        public void onCameraControlUpdateSessionConfig(SessionConfig sessionConfig) {
            Camera2CameraImpl.this.mCameraControlSessionConfig = (SessionConfig) Preconditions.checkNotNull(sessionConfig);
            Camera2CameraImpl.this.updateCaptureSessionConfig();
        }

        @Override // androidx.camera.core.impl.CameraControlInternal.ControlUpdateCallback
        public void onCameraControlCaptureRequests(List<CaptureConfig> list) {
            Camera2CameraImpl.this.submitCaptureRequests((List) Preconditions.checkNotNull(list));
        }
    }
}
