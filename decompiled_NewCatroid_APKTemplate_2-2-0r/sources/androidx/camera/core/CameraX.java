package androidx.camera.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraRepository;
import androidx.camera.core.impl.CameraThreadConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.HandlerCompat;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public final class CameraX {
    private static final String TAG = "CameraX";
    private static final long WAIT_INITIALIZED_TIMEOUT = 3;
    private static CameraXConfig.Provider sConfigProvider;
    static CameraX sInstance;
    private Application mApplication;
    private final Executor mCameraExecutor;
    private CameraFactory mCameraFactory;
    private final CameraXConfig mCameraXConfig;
    private UseCaseConfigFactory mDefaultConfigFactory;
    private final Handler mSchedulerHandler;
    private final HandlerThread mSchedulerThread;
    private CameraDeviceSurfaceManager mSurfaceManager;
    static final Object INSTANCE_LOCK = new Object();
    private static ListenableFuture<Void> sInitializeFuture = Futures.immediateFailedFuture(new IllegalStateException("CameraX is not initialized."));
    private static ListenableFuture<Void> sShutdownFuture = Futures.immediateFuture(null);
    final CameraRepository mCameraRepository = new CameraRepository();
    private final Object mInitializeLock = new Object();
    private InternalInitState mInitState = InternalInitState.UNINITIALIZED;
    private ListenableFuture<Void> mShutdownInternalFuture = Futures.immediateFuture(null);

    private enum InternalInitState {
        UNINITIALIZED,
        INITIALIZING,
        INITIALIZED,
        SHUTDOWN
    }

    static /* synthetic */ CameraXConfig lambda$configureInstance$1(CameraXConfig cameraXConfig) {
        return cameraXConfig;
    }

    static /* synthetic */ CameraX lambda$getInstanceLocked$6(CameraX cameraX, Void r1) {
        return cameraX;
    }

    static /* synthetic */ CameraXConfig lambda$initialize$0(CameraXConfig cameraXConfig) {
        return cameraXConfig;
    }

    CameraX(CameraXConfig cameraXConfig) {
        this.mCameraXConfig = (CameraXConfig) Preconditions.checkNotNull(cameraXConfig);
        Executor cameraExecutor = cameraXConfig.getCameraExecutor(null);
        Handler schedulerHandler = cameraXConfig.getSchedulerHandler(null);
        this.mCameraExecutor = cameraExecutor == null ? new CameraExecutor() : cameraExecutor;
        if (schedulerHandler == null) {
            HandlerThread handlerThread = new HandlerThread("CameraX-scheduler", 10);
            this.mSchedulerThread = handlerThread;
            handlerThread.start();
            this.mSchedulerHandler = HandlerCompat.createAsync(handlerThread.getLooper());
            return;
        }
        this.mSchedulerThread = null;
        this.mSchedulerHandler = schedulerHandler;
    }

    public static boolean hasCamera(CameraSelector cameraSelector) {
        try {
            cameraSelector.select(checkInitialized().getCameraRepository().getCameras());
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static CameraInternal getCameraWithCameraSelector(CameraSelector cameraSelector) {
        return cameraSelector.select(checkInitialized().getCameraRepository().getCameras());
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0043  */
    /* JADX WARN: Code duplicated, block: B:12:0x0048  */
    public static int getDefaultLensFacing() {
        checkInitialized();
        for (Integer num : Arrays.asList(1, 0)) {
            if (hasCamera(new CameraSelector.Builder().requireLensFacing(num.intValue()).build())) {
                if (num != null) {
                    throw new IllegalStateException("Unable to get default lens facing.");
                }
                return num.intValue();
            }
        }
        num = null;
        if (num != null) {
            throw new IllegalStateException("Unable to get default lens facing.");
        }
        return num.intValue();
    }

    public static CameraInfoInternal getCameraInfo(String str) {
        return checkInitialized().getCameraRepository().getCamera(str).getCameraInfoInternal();
    }

    public static CameraDeviceSurfaceManager getSurfaceManager() {
        return checkInitialized().getCameraDeviceSurfaceManager();
    }

    public static <C extends UseCaseConfig<?>> C getDefaultUseCaseConfig(Class<C> cls, CameraInfo cameraInfo) {
        return (C) checkInitialized().getDefaultConfigFactory().getConfig(cls, cameraInfo);
    }

    public static ListenableFuture<Void> initialize(Context context, final CameraXConfig cameraXConfig) {
        ListenableFuture<Void> listenableFuture;
        synchronized (INSTANCE_LOCK) {
            Preconditions.checkNotNull(context);
            configureInstanceLocked(new CameraXConfig.Provider() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda2
                @Override // androidx.camera.core.CameraXConfig.Provider
                public final CameraXConfig getCameraXConfig() {
                    return CameraX.lambda$initialize$0(cameraXConfig);
                }
            });
            initializeInstanceLocked(context);
            listenableFuture = sInitializeFuture;
        }
        return listenableFuture;
    }

    public static void configureInstance(final CameraXConfig cameraXConfig) {
        synchronized (INSTANCE_LOCK) {
            configureInstanceLocked(new CameraXConfig.Provider() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda9
                @Override // androidx.camera.core.CameraXConfig.Provider
                public final CameraXConfig getCameraXConfig() {
                    return CameraX.lambda$configureInstance$1(cameraXConfig);
                }
            });
        }
    }

    private static void configureInstanceLocked(CameraXConfig.Provider provider) {
        Preconditions.checkNotNull(provider);
        Preconditions.checkState(sConfigProvider == null, "CameraX has already been configured. To use a different configuration, shutdown() must be called.");
        sConfigProvider = provider;
    }

    private static void initializeInstanceLocked(final Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkState(sInstance == null, "CameraX already initialized.");
        Preconditions.checkNotNull(sConfigProvider);
        final CameraX cameraX = new CameraX(sConfigProvider.getCameraXConfig());
        sInstance = cameraX;
        sInitializeFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda4
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return CameraX.lambda$initializeInstanceLocked$3(this.f$0, context, completer);
            }
        });
    }

    static /* synthetic */ Object lambda$initializeInstanceLocked$3(final CameraX cameraX, final Context context, final CallbackToFutureAdapter.Completer completer) throws Exception {
        synchronized (INSTANCE_LOCK) {
            Futures.addCallback(FutureChain.from(sShutdownFuture).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda1
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.initInternal(context);
                }
            }, CameraXExecutors.directExecutor()), new FutureCallback<Void>() { // from class: androidx.camera.core.CameraX.1
                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onSuccess(Void r2) {
                    completer.set(null);
                }

                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onFailure(Throwable th) {
                    Log.w(CameraX.TAG, "CameraX initialize() failed", th);
                    synchronized (CameraX.INSTANCE_LOCK) {
                        if (CameraX.sInstance == cameraX) {
                            CameraX.shutdownLocked();
                        }
                    }
                    completer.setException(th);
                }
            }, CameraXExecutors.directExecutor());
        }
        return "CameraX-initialize";
    }

    public static ListenableFuture<Void> shutdown() {
        ListenableFuture<Void> listenableFutureShutdownLocked;
        synchronized (INSTANCE_LOCK) {
            sConfigProvider = null;
            listenableFutureShutdownLocked = shutdownLocked();
        }
        return listenableFutureShutdownLocked;
    }

    static ListenableFuture<Void> shutdownLocked() {
        final CameraX cameraX = sInstance;
        if (cameraX == null) {
            return sShutdownFuture;
        }
        sInstance = null;
        ListenableFuture<Void> future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda10
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return CameraX.lambda$shutdownLocked$5(this.f$0, completer);
            }
        });
        sShutdownFuture = future;
        return future;
    }

    static /* synthetic */ Object lambda$shutdownLocked$5(final CameraX cameraX, final CallbackToFutureAdapter.Completer completer) throws Exception {
        synchronized (INSTANCE_LOCK) {
            sInitializeFuture.addListener(new Runnable() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Futures.propagate(this.f$0.shutdownInternal(), completer);
                }
            }, CameraXExecutors.directExecutor());
        }
        return "CameraX shutdown";
    }

    public static Context getContext() {
        return checkInitialized().mApplication;
    }

    public static boolean isInitialized() {
        boolean z;
        synchronized (INSTANCE_LOCK) {
            CameraX cameraX = sInstance;
            z = cameraX != null && cameraX.isInitializedInternal();
        }
        return z;
    }

    public CameraFactory getCameraFactory() {
        CameraFactory cameraFactory = this.mCameraFactory;
        if (cameraFactory != null) {
            return cameraFactory;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    private static CameraX checkInitialized() {
        CameraX cameraXWaitInitialized = waitInitialized();
        Preconditions.checkState(cameraXWaitInitialized.isInitializedInternal(), "Must call CameraX.initialize() first");
        return cameraXWaitInitialized;
    }

    public static ListenableFuture<CameraX> getOrCreateInstance(Context context) {
        ListenableFuture<CameraX> instanceLocked;
        Preconditions.checkNotNull(context, "Context must not be null.");
        synchronized (INSTANCE_LOCK) {
            boolean z = sConfigProvider != null;
            instanceLocked = getInstanceLocked();
            if (instanceLocked.isDone()) {
                try {
                    try {
                        instanceLocked.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Unexpected thread interrupt. Should not be possible since future is already complete.", e);
                    }
                } catch (ExecutionException unused) {
                    shutdownLocked();
                    instanceLocked = null;
                }
            }
            if (instanceLocked == null) {
                Application application = (Application) context.getApplicationContext();
                if (!z) {
                    CameraXConfig.Provider configProvider = getConfigProvider(application);
                    if (configProvider == null) {
                        throw new IllegalStateException("CameraX is not configured properly. The most likely cause is you did not include a default implementation in your build such as 'camera-camera2'.");
                    }
                    configureInstanceLocked(configProvider);
                }
                initializeInstanceLocked(application);
                instanceLocked = getInstanceLocked();
            }
        }
        return instanceLocked;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static CameraXConfig.Provider getConfigProvider(Application application) {
        if (application instanceof CameraXConfig.Provider) {
            return (CameraXConfig.Provider) application;
        }
        try {
            return (CameraXConfig.Provider) Class.forName(application.getResources().getString(R.string.androidx_camera_default_config_provider)).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Resources.NotFoundException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "Failed to retrieve default CameraXConfig.Provider from resources", e);
            return null;
        }
    }

    private static ListenableFuture<CameraX> getInstance() {
        ListenableFuture<CameraX> instanceLocked;
        synchronized (INSTANCE_LOCK) {
            instanceLocked = getInstanceLocked();
        }
        return instanceLocked;
    }

    private static ListenableFuture<CameraX> getInstanceLocked() {
        final CameraX cameraX = sInstance;
        if (cameraX == null) {
            return Futures.immediateFailedFuture(new IllegalStateException("Must call CameraX.initialize() first"));
        }
        return Futures.transform(sInitializeFuture, new Function() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda8
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return CameraX.lambda$getInstanceLocked$6(this.f$0, (Void) obj);
            }
        }, CameraXExecutors.directExecutor());
    }

    private static CameraX waitInitialized() {
        try {
            return getInstance().get(3L, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new IllegalStateException(e);
        }
    }

    public CameraDeviceSurfaceManager getCameraDeviceSurfaceManager() {
        CameraDeviceSurfaceManager cameraDeviceSurfaceManager = this.mSurfaceManager;
        if (cameraDeviceSurfaceManager != null) {
            return cameraDeviceSurfaceManager;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    public CameraRepository getCameraRepository() {
        return this.mCameraRepository;
    }

    private UseCaseConfigFactory getDefaultConfigFactory() {
        UseCaseConfigFactory useCaseConfigFactory = this.mDefaultConfigFactory;
        if (useCaseConfigFactory != null) {
            return useCaseConfigFactory;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ListenableFuture<Void> initInternal(final Context context) {
        ListenableFuture<Void> future;
        synchronized (this.mInitializeLock) {
            Preconditions.checkState(this.mInitState == InternalInitState.UNINITIALIZED, "CameraX.initInternal() should only be called once per instance");
            this.mInitState = InternalInitState.INITIALIZING;
            final Executor executor = this.mCameraExecutor;
            future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda6
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m65lambda$initInternal$8$androidxcameracoreCameraX(executor, context, completer);
                }
            });
        }
        return future;
    }

    /* JADX INFO: renamed from: lambda$initInternal$8$androidx-camera-core-CameraX, reason: not valid java name */
    /* synthetic */ Object m65lambda$initInternal$8$androidxcameracoreCameraX(final Executor executor, final Context context, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executor.execute(new Runnable() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m64lambda$initInternal$7$androidxcameracoreCameraX(context, executor, completer);
            }
        });
        return "CameraX initInternal";
    }

    /* JADX INFO: renamed from: lambda$initInternal$7$androidx-camera-core-CameraX, reason: not valid java name */
    /* synthetic */ void m64lambda$initInternal$7$androidxcameracoreCameraX(Context context, Executor executor, CallbackToFutureAdapter.Completer completer) {
        try {
            try {
                this.mApplication = (Application) context.getApplicationContext();
                CameraFactory.Provider cameraFactoryProvider = this.mCameraXConfig.getCameraFactoryProvider(null);
                if (cameraFactoryProvider == null) {
                    throw new InitializationException(new IllegalArgumentException("Invalid app configuration provided. Missing CameraFactory."));
                }
                this.mCameraFactory = cameraFactoryProvider.newInstance(context, CameraThreadConfig.create(this.mCameraExecutor, this.mSchedulerHandler));
                CameraDeviceSurfaceManager.Provider deviceSurfaceManagerProvider = this.mCameraXConfig.getDeviceSurfaceManagerProvider(null);
                if (deviceSurfaceManagerProvider == null) {
                    throw new InitializationException(new IllegalArgumentException("Invalid app configuration provided. Missing CameraDeviceSurfaceManager."));
                }
                this.mSurfaceManager = deviceSurfaceManagerProvider.newInstance(context);
                UseCaseConfigFactory.Provider useCaseConfigFactoryProvider = this.mCameraXConfig.getUseCaseConfigFactoryProvider(null);
                if (useCaseConfigFactoryProvider == null) {
                    throw new InitializationException(new IllegalArgumentException("Invalid app configuration provided. Missing UseCaseConfigFactory."));
                }
                this.mDefaultConfigFactory = useCaseConfigFactoryProvider.newInstance(context);
                if (executor instanceof CameraExecutor) {
                    ((CameraExecutor) executor).init(this.mCameraFactory);
                }
                this.mCameraRepository.init(this.mCameraFactory);
                synchronized (this.mInitializeLock) {
                    this.mInitState = InternalInitState.INITIALIZED;
                }
                completer.set(null);
            } catch (InitializationException e) {
                synchronized (this.mInitializeLock) {
                    this.mInitState = InternalInitState.INITIALIZED;
                    completer.setException(e);
                }
            } catch (RuntimeException e2) {
                InitializationException initializationException = new InitializationException(e2);
                synchronized (this.mInitializeLock) {
                    this.mInitState = InternalInitState.INITIALIZED;
                    completer.setException(initializationException);
                }
            }
        } catch (Throwable th) {
            synchronized (this.mInitializeLock) {
                this.mInitState = InternalInitState.INITIALIZED;
                completer.set(null);
                throw th;
            }
        }
    }

    /* JADX INFO: renamed from: androidx.camera.core.CameraX$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$CameraX$InternalInitState;

        static {
            int[] iArr = new int[InternalInitState.values().length];
            $SwitchMap$androidx$camera$core$CameraX$InternalInitState = iArr;
            try {
                iArr[InternalInitState.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.SHUTDOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private ListenableFuture<Void> shutdownInternal() {
        synchronized (this.mInitializeLock) {
            int i = AnonymousClass2.$SwitchMap$androidx$camera$core$CameraX$InternalInitState[this.mInitState.ordinal()];
            if (i == 1) {
                this.mInitState = InternalInitState.SHUTDOWN;
                return Futures.immediateFuture(null);
            }
            if (i == 2) {
                throw new IllegalStateException("CameraX could not be shutdown when it is initializing.");
            }
            if (i == 3) {
                this.mInitState = InternalInitState.SHUTDOWN;
                this.mShutdownInternalFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda7
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        return this.f$0.m66lambda$shutdownInternal$10$androidxcameracoreCameraX(completer);
                    }
                });
            }
            return this.mShutdownInternalFuture;
        }
    }

    /* JADX INFO: renamed from: lambda$shutdownInternal$10$androidx-camera-core-CameraX, reason: not valid java name */
    /* synthetic */ Object m66lambda$shutdownInternal$10$androidxcameracoreCameraX(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCameraRepository.deinit().addListener(new Runnable() { // from class: androidx.camera.core.CameraX$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m67lambda$shutdownInternal$9$androidxcameracoreCameraX(completer);
            }
        }, this.mCameraExecutor);
        return "CameraX shutdownInternal";
    }

    /* JADX INFO: renamed from: lambda$shutdownInternal$9$androidx-camera-core-CameraX, reason: not valid java name */
    /* synthetic */ void m67lambda$shutdownInternal$9$androidxcameracoreCameraX(CallbackToFutureAdapter.Completer completer) {
        if (this.mSchedulerThread != null) {
            Executor executor = this.mCameraExecutor;
            if (executor instanceof CameraExecutor) {
                ((CameraExecutor) executor).deinit();
            }
            this.mSchedulerThread.quit();
            completer.set(null);
        }
    }

    private boolean isInitializedInternal() {
        boolean z;
        synchronized (this.mInitializeLock) {
            z = this.mInitState == InternalInitState.INITIALIZED;
        }
        return z;
    }
}
