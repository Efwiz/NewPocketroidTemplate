package androidx.camera.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageInfoProcessor;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.beust.jcommander.Parameters;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class Preview extends UseCase {
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final Executor DEFAULT_SURFACE_PROVIDER_EXECUTOR = CameraXExecutors.mainThreadExecutor();
    private static final String TAG = "Preview";
    private Size mLatestResolution;
    private Handler mProcessingPreviewHandler;
    private HandlerThread mProcessingPreviewThread;
    private DeferrableSurface mSessionDeferrableSurface;
    SurfaceProvider mSurfaceProvider;
    private CallbackToFutureAdapter.Completer<Pair<SurfaceProvider, Executor>> mSurfaceProviderCompleter;
    Executor mSurfaceProviderExecutor;

    public interface SurfaceProvider {
        void onSurfaceRequested(SurfaceRequest surfaceRequest);
    }

    Preview(PreviewConfig previewConfig) {
        super(previewConfig);
        this.mSurfaceProviderExecutor = DEFAULT_SURFACE_PROVIDER_EXECUTOR;
    }

    SessionConfig.Builder createPipeline(final String str, final PreviewConfig previewConfig, final Size size) {
        Threads.checkMainThread();
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(previewConfig);
        CaptureProcessor captureProcessor = previewConfig.getCaptureProcessor(null);
        DeferrableSurface deferrableSurface = this.mSessionDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        SurfaceRequest surfaceRequest = new SurfaceRequest(size, getCamera(), getViewPortCropRect());
        setUpSurfaceProviderWrap(surfaceRequest);
        if (captureProcessor != null) {
            CaptureStage.DefaultCaptureStage defaultCaptureStage = new CaptureStage.DefaultCaptureStage();
            if (this.mProcessingPreviewThread == null) {
                HandlerThread handlerThread = new HandlerThread("CameraX-preview_processing");
                this.mProcessingPreviewThread = handlerThread;
                handlerThread.start();
                this.mProcessingPreviewHandler = new Handler(this.mProcessingPreviewThread.getLooper());
            }
            ProcessingSurface processingSurface = new ProcessingSurface(size.getWidth(), size.getHeight(), previewConfig.getInputFormat(), this.mProcessingPreviewHandler, defaultCaptureStage, captureProcessor, surfaceRequest.getDeferrableSurface());
            builderCreateFrom.addCameraCaptureCallback(processingSurface.getCameraCaptureCallback());
            this.mSessionDeferrableSurface = processingSurface;
            builderCreateFrom.setTag(Integer.valueOf(defaultCaptureStage.getId()));
        } else {
            final ImageInfoProcessor imageInfoProcessor = previewConfig.getImageInfoProcessor(null);
            if (imageInfoProcessor != null) {
                builderCreateFrom.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.core.Preview.1
                    @Override // androidx.camera.core.impl.CameraCaptureCallback
                    public void onCaptureCompleted(CameraCaptureResult cameraCaptureResult) {
                        super.onCaptureCompleted(cameraCaptureResult);
                        if (imageInfoProcessor.process(new CameraCaptureResultImageInfo(cameraCaptureResult))) {
                            Preview.this.notifyUpdated();
                        }
                    }
                });
            }
            this.mSessionDeferrableSurface = surfaceRequest.getDeferrableSurface();
        }
        builderCreateFrom.addSurface(this.mSessionDeferrableSurface);
        builderCreateFrom.addErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.Preview.2
            @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
            public void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
                if (Preview.this.isCurrentCamera(str)) {
                    Preview.this.updateSessionConfig(Preview.this.createPipeline(str, previewConfig, size).build());
                    Preview.this.notifyReset();
                }
            }
        });
        return builderCreateFrom;
    }

    private void setUpSurfaceProviderWrap(SurfaceRequest surfaceRequest) {
        Futures.addCallback(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.Preview$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m91lambda$setUpSurfaceProviderWrap$0$androidxcameracorePreview(completer);
            }
        }), new AnonymousClass3(surfaceRequest), CameraXExecutors.directExecutor());
    }

    /* JADX INFO: renamed from: lambda$setUpSurfaceProviderWrap$0$androidx-camera-core-Preview, reason: not valid java name */
    /* synthetic */ Object m91lambda$setUpSurfaceProviderWrap$0$androidxcameracorePreview(CallbackToFutureAdapter.Completer completer) throws Exception {
        CallbackToFutureAdapter.Completer<Pair<SurfaceProvider, Executor>> completer2 = this.mSurfaceProviderCompleter;
        if (completer2 != null) {
            completer2.setCancelled();
        }
        this.mSurfaceProviderCompleter = completer;
        if (this.mSurfaceProvider != null) {
            completer.set(new Pair(this.mSurfaceProvider, this.mSurfaceProviderExecutor));
            this.mSurfaceProviderCompleter = null;
            return "surface provider and executor future";
        }
        return "surface provider and executor future";
    }

    /* JADX INFO: renamed from: androidx.camera.core.Preview$3, reason: invalid class name */
    class AnonymousClass3 implements FutureCallback<Pair<SurfaceProvider, Executor>> {
        final /* synthetic */ SurfaceRequest val$surfaceRequest;

        AnonymousClass3(SurfaceRequest surfaceRequest) {
            this.val$surfaceRequest = surfaceRequest;
        }

        @Override // androidx.camera.core.impl.utils.futures.FutureCallback
        public void onSuccess(Pair<SurfaceProvider, Executor> pair) {
            if (pair == null) {
                return;
            }
            final SurfaceProvider surfaceProvider = (SurfaceProvider) pair.first;
            Executor executor = (Executor) pair.second;
            if (surfaceProvider == null || executor == null) {
                return;
            }
            final SurfaceRequest surfaceRequest = this.val$surfaceRequest;
            executor.execute(new Runnable() { // from class: androidx.camera.core.Preview$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    surfaceProvider.onSurfaceRequested(surfaceRequest);
                }
            });
        }

        @Override // androidx.camera.core.impl.utils.futures.FutureCallback
        public void onFailure(Throwable th) {
            this.val$surfaceRequest.getDeferrableSurface().close();
        }
    }

    public void setSurfaceProvider(Executor executor, SurfaceProvider surfaceProvider) {
        Threads.checkMainThread();
        if (surfaceProvider == null) {
            this.mSurfaceProvider = null;
            notifyInactive();
            return;
        }
        this.mSurfaceProvider = surfaceProvider;
        this.mSurfaceProviderExecutor = executor;
        notifyActive();
        onSurfaceProviderAvailable();
        DeferrableSurface deferrableSurface = this.mSessionDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        notifyReset();
    }

    private void onSurfaceProviderAvailable() {
        CallbackToFutureAdapter.Completer<Pair<SurfaceProvider, Executor>> completer = this.mSurfaceProviderCompleter;
        if (completer != null) {
            completer.set(new Pair<>(this.mSurfaceProvider, this.mSurfaceProviderExecutor));
            this.mSurfaceProviderCompleter = null;
        } else if (this.mLatestResolution != null) {
            updateConfigAndOutput(getCameraId(), (PreviewConfig) getUseCaseConfig(), this.mLatestResolution);
        }
    }

    public void setSurfaceProvider(SurfaceProvider surfaceProvider) {
        setSurfaceProvider(DEFAULT_SURFACE_PROVIDER_EXECUTOR, surfaceProvider);
    }

    private void updateConfigAndOutput(String str, PreviewConfig previewConfig, Size size) {
        updateSessionConfig(createPipeline(str, previewConfig, size).build());
    }

    public int getTargetRotation() {
        return ((PreviewConfig) getUseCaseConfig()).getTargetRotation();
    }

    public String toString() {
        return "Preview:" + getName();
    }

    @Override // androidx.camera.core.UseCase
    public UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo cameraInfo) {
        PreviewConfig previewConfig = (PreviewConfig) CameraX.getDefaultUseCaseConfig(PreviewConfig.class, cameraInfo);
        if (previewConfig != null) {
            return Builder.fromConfig(previewConfig);
        }
        return null;
    }

    @Override // androidx.camera.core.UseCase
    public void clear() {
        notifyInactive();
        DeferrableSurface deferrableSurface = this.mSessionDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
            this.mSessionDeferrableSurface.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.Preview$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m90lambda$clear$1$androidxcameracorePreview();
                }
            }, CameraXExecutors.directExecutor());
        }
        CallbackToFutureAdapter.Completer<Pair<SurfaceProvider, Executor>> completer = this.mSurfaceProviderCompleter;
        if (completer != null) {
            completer.setCancelled();
            this.mSurfaceProviderCompleter = null;
        }
    }

    /* JADX INFO: renamed from: lambda$clear$1$androidx-camera-core-Preview, reason: not valid java name */
    /* synthetic */ void m90lambda$clear$1$androidxcameracorePreview() {
        HandlerThread handlerThread = this.mProcessingPreviewThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mProcessingPreviewThread = null;
        }
    }

    @Override // androidx.camera.core.UseCase
    public void onDestroy() {
        this.mSurfaceProvider = null;
    }

    @Override // androidx.camera.core.UseCase
    protected Size onSuggestedResolutionUpdated(Size size) {
        this.mLatestResolution = size;
        updateConfigAndOutput(getCameraId(), (PreviewConfig) getUseCaseConfig(), this.mLatestResolution);
        return this.mLatestResolution;
    }

    public static final class Defaults implements ConfigProvider<PreviewConfig> {
        private static final PreviewConfig DEFAULT_CONFIG;
        private static final Size DEFAULT_MAX_RESOLUTION;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 2;

        static {
            Size previewSize = CameraX.getSurfaceManager().getPreviewSize();
            DEFAULT_MAX_RESOLUTION = previewSize;
            DEFAULT_CONFIG = new Builder().setMaxResolution(previewSize).setSurfaceOccupancyPriority(2).getUseCaseConfig();
        }

        @Override // androidx.camera.core.impl.ConfigProvider
        public PreviewConfig getConfig(CameraInfo cameraInfo) {
            return DEFAULT_CONFIG;
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<Preview, PreviewConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public /* bridge */ /* synthetic */ Builder setSupportedResolutions(List list) {
            return setSupportedResolutions((List<Pair<Integer, Size[]>>) list);
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public /* bridge */ /* synthetic */ Object setTargetClass(Class cls) {
            return setTargetClass((Class<Preview>) cls);
        }

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableOptionsBundle) {
            this.mMutableConfig = mutableOptionsBundle;
            Class cls = (Class) mutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (cls != null && !cls.equals(Preview.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            setTargetClass(Preview.class);
        }

        public static Builder fromConfig(PreviewConfig previewConfig) {
            return new Builder(MutableOptionsBundle.from((Config) previewConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public PreviewConfig getUseCaseConfig() {
            return new PreviewConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public Preview build() {
            if (getMutableConfig().retrieveOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO, null) != null && getMutableConfig().retrieveOption(PreviewConfig.OPTION_TARGET_RESOLUTION, null) != null) {
                throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
            }
            if (getMutableConfig().retrieveOption(PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR, null) != null) {
                getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 35);
            } else {
                getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 34);
            }
            return new Preview(getUseCaseConfig());
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetClass(Class<Preview> cls) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_CLASS, cls);
            if (getMutableConfig().retrieveOption(PreviewConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(cls.getCanonicalName() + Parameters.DEFAULT_OPTION_PREFIXES + UUID.randomUUID());
            }
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetName(String str) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_NAME, str);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetAspectRatioCustom(Rational rational) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, rational);
            getMutableConfig().removeOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetAspectRatio(int i) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetRotation(int i) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ROTATION, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetResolution(Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, size);
            if (size != null) {
                getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(size.getWidth(), size.getHeight()));
            }
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setDefaultResolution(Size size) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_DEFAULT_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setMaxResolution(Size size) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_MAX_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setSupportedResolutions(List<Pair<Integer, Size[]>> list) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_SUPPORTED_RESOLUTIONS, list);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.internal.ThreadConfig.Builder
        public Builder setBackgroundExecutor(Executor executor) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_BACKGROUND_EXECUTOR, executor);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setDefaultSessionConfig(SessionConfig sessionConfig) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setDefaultCaptureConfig(CaptureConfig captureConfig) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setSurfaceOccupancyPriority(int i) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setCameraSelector(CameraSelector cameraSelector) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
            return this;
        }

        @Override // androidx.camera.core.internal.UseCaseEventConfig.Builder
        public Builder setUseCaseEventCallback(UseCase.EventCallback eventCallback) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
            return this;
        }

        public Builder setImageInfoProcessor(ImageInfoProcessor imageInfoProcessor) {
            getMutableConfig().insertOption(PreviewConfig.IMAGE_INFO_PROCESSOR, imageInfoProcessor);
            return this;
        }

        public Builder setCaptureProcessor(CaptureProcessor captureProcessor) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR, captureProcessor);
            return this;
        }
    }
}
