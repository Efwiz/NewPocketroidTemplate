package androidx.camera.core;

import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import androidx.core.util.Preconditions;
import com.beust.jcommander.Parameters;
import com.google.common.util.concurrent.ListenableFuture;
import com.hivemq.client.mqtt.MqttProxyConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ImageAnalysis extends UseCase {
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final int NON_BLOCKING_IMAGE_DEPTH = 4;
    public static final int STRATEGY_BLOCK_PRODUCER = 1;
    public static final int STRATEGY_KEEP_ONLY_LATEST = 0;
    private static final String TAG = "ImageAnalysis";
    private final Object mAnalysisLock;
    private DeferrableSurface mDeferrableSurface;
    final ImageAnalysisAbstractAnalyzer mImageAnalysisAbstractAnalyzer;
    private Analyzer mSubscribedAnalyzer;

    public interface Analyzer {
        void analyze(ImageProxy imageProxy);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackpressureStrategy {
    }

    ImageAnalysis(ImageAnalysisConfig imageAnalysisConfig) {
        super(imageAnalysisConfig);
        this.mAnalysisLock = new Object();
        if (((ImageAnalysisConfig) getUseCaseConfig()).getBackpressureStrategy() == 1) {
            this.mImageAnalysisAbstractAnalyzer = new ImageAnalysisBlockingAnalyzer();
        } else {
            this.mImageAnalysisAbstractAnalyzer = new ImageAnalysisNonBlockingAnalyzer(imageAnalysisConfig.getBackgroundExecutor(CameraXExecutors.highPriorityExecutor()));
        }
    }

    SessionConfig.Builder createPipeline(final String str, final ImageAnalysisConfig imageAnalysisConfig, final Size size) {
        SafeCloseImageReaderProxy safeCloseImageReaderProxy;
        Threads.checkMainThread();
        Executor executor = (Executor) Preconditions.checkNotNull(imageAnalysisConfig.getBackgroundExecutor(CameraXExecutors.highPriorityExecutor()));
        int imageQueueDepth = imageAnalysisConfig.getBackpressureStrategy() == 1 ? imageAnalysisConfig.getImageQueueDepth() : 4;
        if (imageAnalysisConfig.getImageReaderProxyProvider() != null) {
            safeCloseImageReaderProxy = new SafeCloseImageReaderProxy(imageAnalysisConfig.getImageReaderProxyProvider().newInstance(size.getWidth(), size.getHeight(), getImageFormat(), imageQueueDepth, 0L));
        } else {
            safeCloseImageReaderProxy = new SafeCloseImageReaderProxy(ImageReaderProxys.createIsolatedReader(size.getWidth(), size.getHeight(), getImageFormat(), imageQueueDepth));
        }
        tryUpdateRelativeRotation();
        this.mImageAnalysisAbstractAnalyzer.open();
        safeCloseImageReaderProxy.setOnImageAvailableListener(this.mImageAnalysisAbstractAnalyzer, executor);
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(imageAnalysisConfig);
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        ImmediateSurface immediateSurface = new ImmediateSurface(safeCloseImageReaderProxy.getSurface());
        this.mDeferrableSurface = immediateSurface;
        ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
        Objects.requireNonNull(safeCloseImageReaderProxy);
        terminationFuture.addListener(new ImageAnalysis$$ExternalSyntheticLambda1(safeCloseImageReaderProxy), CameraXExecutors.mainThreadExecutor());
        builderCreateFrom.addSurface(this.mDeferrableSurface);
        builderCreateFrom.addErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.ImageAnalysis$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
            public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
                this.f$0.m68lambda$createPipeline$0$androidxcameracoreImageAnalysis(str, imageAnalysisConfig, size, sessionConfig, sessionError);
            }
        });
        return builderCreateFrom;
    }

    /* JADX INFO: renamed from: lambda$createPipeline$0$androidx-camera-core-ImageAnalysis, reason: not valid java name */
    /* synthetic */ void m68lambda$createPipeline$0$androidxcameracoreImageAnalysis(String str, ImageAnalysisConfig imageAnalysisConfig, Size size, SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        clearPipeline();
        if (isCurrentCamera(str)) {
            updateSessionConfig(createPipeline(str, imageAnalysisConfig, size).build());
            notifyReset();
        }
    }

    void clearPipeline() {
        Threads.checkMainThread();
        this.mImageAnalysisAbstractAnalyzer.close();
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
            this.mDeferrableSurface = null;
        }
    }

    public void clearAnalyzer() {
        synchronized (this.mAnalysisLock) {
            this.mImageAnalysisAbstractAnalyzer.setAnalyzer(null, null);
            this.mImageAnalysisAbstractAnalyzer.close();
            if (this.mSubscribedAnalyzer != null) {
                notifyInactive();
            }
            this.mSubscribedAnalyzer = null;
        }
    }

    public int getTargetRotation() {
        return ((ImageAnalysisConfig) getUseCaseConfig()).getTargetRotation();
    }

    public void setTargetRotation(int i) {
        ImageAnalysisConfig imageAnalysisConfig = (ImageAnalysisConfig) getUseCaseConfig();
        Builder builderFromConfig = Builder.fromConfig(imageAnalysisConfig);
        int targetRotation = imageAnalysisConfig.getTargetRotation(-1);
        if (targetRotation == -1 || targetRotation != i) {
            UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(builderFromConfig, i);
            updateUseCaseConfig(builderFromConfig.getUseCaseConfig());
            try {
                tryUpdateRelativeRotation();
            } catch (Exception unused) {
                Log.w(TAG, "Unable to get camera id for the use case.");
            }
        }
    }

    public void setAnalyzer(Executor executor, final Analyzer analyzer) {
        synchronized (this.mAnalysisLock) {
            this.mImageAnalysisAbstractAnalyzer.open();
            this.mImageAnalysisAbstractAnalyzer.setAnalyzer(executor, new Analyzer() { // from class: androidx.camera.core.ImageAnalysis$$ExternalSyntheticLambda0
                @Override // androidx.camera.core.ImageAnalysis.Analyzer
                public final void analyze(ImageProxy imageProxy) {
                    this.f$0.m69lambda$setAnalyzer$1$androidxcameracoreImageAnalysis(analyzer, imageProxy);
                }
            });
            if (this.mSubscribedAnalyzer == null) {
                notifyActive();
            }
            this.mSubscribedAnalyzer = analyzer;
        }
    }

    /* JADX INFO: renamed from: lambda$setAnalyzer$1$androidx-camera-core-ImageAnalysis, reason: not valid java name */
    /* synthetic */ void m69lambda$setAnalyzer$1$androidxcameracoreImageAnalysis(Analyzer analyzer, ImageProxy imageProxy) {
        if (getViewPortCropRect() != null) {
            imageProxy.setCropRect(getViewPortCropRect());
        }
        analyzer.analyze(imageProxy);
    }

    public int getBackpressureStrategy() {
        return ((ImageAnalysisConfig) getUseCaseConfig()).getBackpressureStrategy();
    }

    public int getImageQueueDepth() {
        return ((ImageAnalysisConfig) getUseCaseConfig()).getImageQueueDepth();
    }

    public String toString() {
        return "ImageAnalysis:" + getName();
    }

    @Override // androidx.camera.core.UseCase
    public void clear() {
        clearPipeline();
    }

    @Override // androidx.camera.core.UseCase
    public void onDestroy() {
        clearAnalyzer();
    }

    @Override // androidx.camera.core.UseCase
    public UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo cameraInfo) {
        ImageAnalysisConfig imageAnalysisConfig = (ImageAnalysisConfig) CameraX.getDefaultUseCaseConfig(ImageAnalysisConfig.class, cameraInfo);
        if (imageAnalysisConfig != null) {
            return Builder.fromConfig(imageAnalysisConfig);
        }
        return null;
    }

    @Override // androidx.camera.core.UseCase
    protected Size onSuggestedResolutionUpdated(Size size) {
        updateSessionConfig(createPipeline(getCameraId(), (ImageAnalysisConfig) getUseCaseConfig(), size).build());
        return size;
    }

    private void tryUpdateRelativeRotation() {
        ImageOutputConfig imageOutputConfig = (ImageOutputConfig) getUseCaseConfig();
        this.mImageAnalysisAbstractAnalyzer.setRelativeRotation(getCamera().getCameraInfoInternal().getSensorRotationDegrees(imageOutputConfig.getTargetRotation(0)));
    }

    public static final class Defaults implements ConfigProvider<ImageAnalysisConfig> {
        private static final int DEFAULT_BACKPRESSURE_STRATEGY = 0;
        private static final ImageAnalysisConfig DEFAULT_CONFIG;
        private static final int DEFAULT_IMAGE_QUEUE_DEPTH = 6;
        private static final Size DEFAULT_MAX_RESOLUTION;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 1;
        private static final Size DEFAULT_TARGET_RESOLUTION;

        static {
            Size size = new Size(640, 480);
            DEFAULT_TARGET_RESOLUTION = size;
            Size size2 = new Size(1920, MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT);
            DEFAULT_MAX_RESOLUTION = size2;
            DEFAULT_CONFIG = new Builder().setBackpressureStrategy(0).setImageQueueDepth(6).setDefaultResolution(size).setMaxResolution(size2).setSurfaceOccupancyPriority(1).getUseCaseConfig();
        }

        @Override // androidx.camera.core.impl.ConfigProvider
        public ImageAnalysisConfig getConfig(CameraInfo cameraInfo) {
            return DEFAULT_CONFIG;
        }
    }

    public static final class Builder implements ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder>, UseCaseConfig.Builder<ImageAnalysis, ImageAnalysisConfig, Builder> {
        private final MutableOptionsBundle mMutableConfig;

        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public /* bridge */ /* synthetic */ Builder setSupportedResolutions(List list) {
            return setSupportedResolutions((List<Pair<Integer, Size[]>>) list);
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public /* bridge */ /* synthetic */ Object setTargetClass(Class cls) {
            return setTargetClass((Class<ImageAnalysis>) cls);
        }

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableOptionsBundle) {
            this.mMutableConfig = mutableOptionsBundle;
            Class cls = (Class) mutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (cls != null && !cls.equals(ImageAnalysis.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            setTargetClass(ImageAnalysis.class);
        }

        public static Builder fromConfig(ImageAnalysisConfig imageAnalysisConfig) {
            return new Builder(MutableOptionsBundle.from((Config) imageAnalysisConfig));
        }

        public Builder setBackpressureStrategy(int i) {
            getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_BACKPRESSURE_STRATEGY, Integer.valueOf(i));
            return this;
        }

        public Builder setImageQueueDepth(int i) {
            getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_IMAGE_QUEUE_DEPTH, Integer.valueOf(i));
            return this;
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public ImageAnalysisConfig getUseCaseConfig() {
            return new ImageAnalysisConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public ImageAnalysis build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) != null && getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) != null) {
                throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
            }
            return new ImageAnalysis(getUseCaseConfig());
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetClass(Class<ImageAnalysis> cls) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_TARGET_CLASS, cls);
            if (getMutableConfig().retrieveOption(UseCaseConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(cls.getCanonicalName() + Parameters.DEFAULT_OPTION_PREFIXES + UUID.randomUUID());
            }
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetName(String str) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_TARGET_NAME, str);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetAspectRatioCustom(Rational rational) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, rational);
            getMutableConfig().removeOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetAspectRatio(int i) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetRotation(int i) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetResolution(Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, size);
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(size.getWidth(), size.getHeight()));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setDefaultResolution(Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setMaxResolution(Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setSupportedResolutions(List<Pair<Integer, Size[]>> list) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, list);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.internal.ThreadConfig.Builder
        public Builder setBackgroundExecutor(Executor executor) {
            getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, executor);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setDefaultSessionConfig(SessionConfig sessionConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setDefaultCaptureConfig(CaptureConfig captureConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setSurfaceOccupancyPriority(int i) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(i));
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
            getMutableConfig().insertOption(UseCaseConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
            return this;
        }

        public Builder setImageReaderProxyProvider(ImageReaderProxyProvider imageReaderProxyProvider) {
            getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_IMAGE_READER_PROXY_PROVIDER, imageReaderProxyProvider);
            return this;
        }
    }
}
