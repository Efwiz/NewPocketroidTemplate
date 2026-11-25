package androidx.camera.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.arch.core.util.Function;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.IoConfig;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.beust.jcommander.Parameters;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class ImageCapture extends UseCase {
    public static final int CAPTURE_MODE_MAXIMIZE_QUALITY = 0;
    public static final int CAPTURE_MODE_MINIMIZE_LATENCY = 1;
    private static final long CHECK_3A_TIMEOUT_IN_MS = 1000;
    public static final int ERROR_CAMERA_CLOSED = 3;
    public static final int ERROR_CAPTURE_FAILED = 2;
    public static final int ERROR_FILE_IO = 1;
    public static final int ERROR_INVALID_CAMERA = 4;
    public static final int ERROR_UNKNOWN = 0;
    public static final int FLASH_MODE_AUTO = 0;
    public static final int FLASH_MODE_OFF = 2;
    public static final int FLASH_MODE_ON = 1;
    private static final byte JPEG_QUALITY_MAXIMIZE_QUALITY_MODE = 100;
    private static final byte JPEG_QUALITY_MINIMIZE_LATENCY_MODE = 95;
    private static final int MAX_IMAGES = 2;
    private final CaptureBundle mCaptureBundle;
    private final CaptureConfig mCaptureConfig;
    private final int mCaptureMode;
    private final CaptureProcessor mCaptureProcessor;
    private final ImageReaderProxy.OnImageAvailableListener mClosingListener;
    private DeferrableSurface mDeferrableSurface;
    private boolean mEnableCheck3AConverged;
    private final ExecutorService mExecutor;
    private int mFlashMode;
    private ImageCaptureRequestProcessor mImageCaptureRequestProcessor;
    SafeCloseImageReaderProxy mImageReader;
    final Executor mIoExecutor;
    private final int mMaxCaptureStages;
    private CameraCaptureCallback mMetadataMatchingCaptureCallback;
    ProcessingImageReader mProcessingImageReader;
    private final CaptureCallbackChecker mSessionCallbackChecker;
    SessionConfig.Builder mSessionConfigBuilder;
    private ImageCaptureConfig mUserSettingConfig;
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final String TAG = "ImageCapture";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);

    @Retention(RetentionPolicy.SOURCE)
    public @interface CaptureMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FlashMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageCaptureError {
    }

    public interface OnImageSavedCallback {
        void onError(ImageCaptureException imageCaptureException);

        void onImageSaved(OutputFileResults outputFileResults);
    }

    static /* synthetic */ Void lambda$issueTakePicture$15(List list) {
        return null;
    }

    static /* synthetic */ Void lambda$preTakePicture$12(Boolean bool) {
        return null;
    }

    static /* synthetic */ void lambda$triggerAf$13() {
    }

    static /* synthetic */ void lambda$new$0(ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            try {
                Log.d(TAG, "Discarding ImageProxy which was inadvertently acquired: " + imageProxyAcquireLatestImage);
                if (imageProxyAcquireLatestImage != null) {
                    imageProxyAcquireLatestImage.close();
                }
            } catch (Throwable th) {
                if (imageProxyAcquireLatestImage != null) {
                    try {
                        imageProxyAcquireLatestImage.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "Failed to acquire latest image.", e);
        }
    }

    ImageCapture(ImageCaptureConfig imageCaptureConfig) {
        super(imageCaptureConfig);
        this.mExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() { // from class: androidx.camera.core.ImageCapture.1
            private final AtomicInteger mId = new AtomicInteger(0);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "CameraX-image_capture_" + this.mId.getAndIncrement());
            }
        });
        this.mSessionCallbackChecker = new CaptureCallbackChecker();
        this.mClosingListener = new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda13
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                ImageCapture.lambda$new$0(imageReaderProxy);
            }
        };
        ImageCaptureConfig imageCaptureConfig2 = (ImageCaptureConfig) getUseCaseConfig();
        this.mUserSettingConfig = imageCaptureConfig2;
        int captureMode = imageCaptureConfig2.getCaptureMode();
        this.mCaptureMode = captureMode;
        this.mFlashMode = this.mUserSettingConfig.getFlashMode();
        this.mCaptureProcessor = this.mUserSettingConfig.getCaptureProcessor(null);
        int maxCaptureStages = this.mUserSettingConfig.getMaxCaptureStages(2);
        this.mMaxCaptureStages = maxCaptureStages;
        Preconditions.checkArgument(maxCaptureStages >= 1, "Maximum outstanding image count must be at least 1");
        this.mCaptureBundle = this.mUserSettingConfig.getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
        this.mIoExecutor = (Executor) Preconditions.checkNotNull(this.mUserSettingConfig.getIoExecutor(CameraXExecutors.ioExecutor()));
        if (captureMode == 0) {
            this.mEnableCheck3AConverged = true;
        } else if (captureMode == 1) {
            this.mEnableCheck3AConverged = false;
        }
        this.mCaptureConfig = CaptureConfig.Builder.createFrom(this.mUserSettingConfig).build();
    }

    SessionConfig.Builder createPipeline(final String str, final ImageCaptureConfig imageCaptureConfig, final Size size) {
        Threads.checkMainThread();
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(imageCaptureConfig);
        builderCreateFrom.addRepeatingCameraCaptureCallback(this.mSessionCallbackChecker);
        if (imageCaptureConfig.getImageReaderProxyProvider() != null) {
            this.mImageReader = new SafeCloseImageReaderProxy(imageCaptureConfig.getImageReaderProxyProvider().newInstance(size.getWidth(), size.getHeight(), getImageFormat(), 2, 0L));
            this.mMetadataMatchingCaptureCallback = new CameraCaptureCallback() { // from class: androidx.camera.core.ImageCapture.2
            };
        } else if (this.mCaptureProcessor != null) {
            ProcessingImageReader processingImageReader = new ProcessingImageReader(size.getWidth(), size.getHeight(), getImageFormat(), this.mMaxCaptureStages, this.mExecutor, getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle()), this.mCaptureProcessor);
            this.mProcessingImageReader = processingImageReader;
            this.mMetadataMatchingCaptureCallback = processingImageReader.getCameraCaptureCallback();
            this.mImageReader = new SafeCloseImageReaderProxy(this.mProcessingImageReader);
        } else {
            MetadataImageReader metadataImageReader = new MetadataImageReader(size.getWidth(), size.getHeight(), getImageFormat(), 2);
            this.mMetadataMatchingCaptureCallback = metadataImageReader.getCameraCaptureCallback();
            this.mImageReader = new SafeCloseImageReaderProxy(metadataImageReader);
        }
        this.mImageCaptureRequestProcessor = new ImageCaptureRequestProcessor(2, new ImageCaptureRequestProcessor.ImageCaptor() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.ImageCapture.ImageCaptureRequestProcessor.ImageCaptor
            public final ListenableFuture capture(ImageCapture.ImageCaptureRequest imageCaptureRequest) {
                return this.f$0.m73lambda$createPipeline$1$androidxcameracoreImageCapture(imageCaptureRequest);
            }
        });
        this.mImageReader.setOnImageAvailableListener(this.mClosingListener, CameraXExecutors.mainThreadExecutor());
        SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mImageReader;
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        ImmediateSurface immediateSurface = new ImmediateSurface(this.mImageReader.getSurface());
        this.mDeferrableSurface = immediateSurface;
        ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
        Objects.requireNonNull(safeCloseImageReaderProxy);
        terminationFuture.addListener(new ImageAnalysis$$ExternalSyntheticLambda1(safeCloseImageReaderProxy), CameraXExecutors.mainThreadExecutor());
        builderCreateFrom.addNonRepeatingSurface(this.mDeferrableSurface);
        builderCreateFrom.addErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda3
            @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
            public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
                this.f$0.m74lambda$createPipeline$2$androidxcameracoreImageCapture(str, imageCaptureConfig, size, sessionConfig, sessionError);
            }
        });
        return builderCreateFrom;
    }

    /* JADX INFO: renamed from: lambda$createPipeline$2$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ void m74lambda$createPipeline$2$androidxcameracoreImageCapture(String str, ImageCaptureConfig imageCaptureConfig, Size size, SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        clearPipeline();
        if (isCurrentCamera(str)) {
            SessionConfig.Builder builderCreatePipeline = createPipeline(str, imageCaptureConfig, size);
            this.mSessionConfigBuilder = builderCreatePipeline;
            updateSessionConfig(builderCreatePipeline.build());
            notifyReset();
        }
    }

    void clearPipeline() {
        Threads.checkMainThread();
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        this.mDeferrableSurface = null;
        this.mImageReader = null;
        this.mProcessingImageReader = null;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
    }

    @Override // androidx.camera.core.UseCase
    public UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo cameraInfo) {
        ImageCaptureConfig imageCaptureConfig = (ImageCaptureConfig) CameraX.getDefaultUseCaseConfig(ImageCaptureConfig.class, cameraInfo);
        if (imageCaptureConfig != null) {
            return Builder.fromConfig(imageCaptureConfig);
        }
        return null;
    }

    @Override // androidx.camera.core.UseCase
    protected void onCameraControlReady() {
        getCameraControl().setFlashMode(this.mFlashMode);
    }

    public int getFlashMode() {
        return this.mFlashMode;
    }

    public void setFlashMode(int i) {
        this.mFlashMode = i;
        if (getCamera() != null) {
            getCameraControl().setFlashMode(i);
        }
    }

    public void setCropAspectRatio(Rational rational) {
        Builder builderFromConfig = Builder.fromConfig((ImageCaptureConfig) getUseCaseConfig());
        if (rational.equals(this.mUserSettingConfig.getTargetAspectRatioCustom(null))) {
            return;
        }
        builderFromConfig.setTargetAspectRatioCustom(rational);
        updateUseCaseConfig(builderFromConfig.getUseCaseConfig());
        this.mUserSettingConfig = (ImageCaptureConfig) getUseCaseConfig();
    }

    public int getTargetRotation() {
        return ((ImageOutputConfig) getUseCaseConfig()).getTargetRotation();
    }

    public void setTargetRotation(int i) {
        ImageCaptureConfig imageCaptureConfig = (ImageCaptureConfig) getUseCaseConfig();
        Builder builderFromConfig = Builder.fromConfig(imageCaptureConfig);
        int targetRotation = imageCaptureConfig.getTargetRotation(-1);
        if (targetRotation == -1 || targetRotation != i) {
            UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(builderFromConfig, i);
            updateUseCaseConfig(builderFromConfig.getUseCaseConfig());
            this.mUserSettingConfig = (ImageCaptureConfig) getUseCaseConfig();
        }
    }

    public int getCaptureMode() {
        return this.mCaptureMode;
    }

    /* JADX INFO: renamed from: takePicture, reason: merged with bridge method [inline-methods] */
    public void m79lambda$takePicture$3$androidxcameracoreImageCapture(final Executor executor, final OnImageCapturedCallback onImageCapturedCallback) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m79lambda$takePicture$3$androidxcameracoreImageCapture(executor, onImageCapturedCallback);
                }
            });
        } else {
            sendImageCaptureRequest(executor, onImageCapturedCallback);
        }
    }

    /* JADX INFO: renamed from: takePicture, reason: merged with bridge method [inline-methods] */
    public void m80lambda$takePicture$4$androidxcameracoreImageCapture(final OutputFileOptions outputFileOptions, final Executor executor, final OnImageSavedCallback onImageSavedCallback) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m80lambda$takePicture$4$androidxcameracoreImageCapture(outputFileOptions, executor, onImageSavedCallback);
                }
            });
            return;
        }
        final ImageSaver.OnImageSavedCallback onImageSavedCallback2 = new ImageSaver.OnImageSavedCallback() { // from class: androidx.camera.core.ImageCapture.3
            @Override // androidx.camera.core.ImageSaver.OnImageSavedCallback
            public void onImageSaved(OutputFileResults outputFileResults) {
                onImageSavedCallback.onImageSaved(outputFileResults);
            }

            @Override // androidx.camera.core.ImageSaver.OnImageSavedCallback
            public void onError(ImageSaver.SaveError saveError, String str, Throwable th) {
                onImageSavedCallback.onError(new ImageCaptureException(AnonymousClass9.$SwitchMap$androidx$camera$core$ImageSaver$SaveError[saveError.ordinal()] != 1 ? 0 : 1, str, th));
            }
        };
        sendImageCaptureRequest(CameraXExecutors.mainThreadExecutor(), new OnImageCapturedCallback() { // from class: androidx.camera.core.ImageCapture.4
            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onCaptureSuccess(ImageProxy imageProxy) {
                ImageCapture.this.mIoExecutor.execute(new ImageSaver(imageProxy, outputFileOptions, imageProxy.getImageInfo().getRotationDegrees(), executor, onImageSavedCallback2));
            }

            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onError(ImageCaptureException imageCaptureException) {
                onImageSavedCallback.onError(imageCaptureException);
            }
        });
    }

    /* JADX INFO: renamed from: androidx.camera.core.ImageCapture$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$ImageSaver$SaveError;

        static {
            int[] iArr = new int[ImageSaver.SaveError.values().length];
            $SwitchMap$androidx$camera$core$ImageSaver$SaveError = iArr;
            try {
                iArr[ImageSaver.SaveError.FILE_IO_FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @Override // androidx.camera.core.UseCase
    public void onStateDetached() {
        abortImageCaptureRequests();
    }

    private void abortImageCaptureRequests() {
        this.mImageCaptureRequestProcessor.cancelRequests(new CameraClosedException("Camera is closed."));
    }

    private void sendImageCaptureRequest(Executor executor, final OnImageCapturedCallback onImageCapturedCallback) {
        CameraInternal camera = getCamera();
        if (camera == null) {
            executor.execute(new Runnable() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m78x1bb57f01(onImageCapturedCallback);
                }
            });
            return;
        }
        this.mImageCaptureRequestProcessor.sendRequest(new ImageCaptureRequest(camera.getCameraInfoInternal().getSensorRotationDegrees(this.mUserSettingConfig.getTargetRotation(0)), getJpegQuality(), this.mUserSettingConfig.getTargetAspectRatioCustom(null), getViewPortCropRect(), executor, onImageCapturedCallback));
    }

    /* JADX INFO: renamed from: lambda$sendImageCaptureRequest$5$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ void m78x1bb57f01(OnImageCapturedCallback onImageCapturedCallback) {
        onImageCapturedCallback.onError(new ImageCaptureException(4, "Not bound to a valid Camera [" + this + "]", null));
    }

    private int getJpegQuality() {
        int i = this.mCaptureMode;
        if (i == 0) {
            return 100;
        }
        if (i == 1) {
            return 95;
        }
        throw new IllegalStateException("CaptureMode " + this.mCaptureMode + " is invalid");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: takePictureInternal, reason: merged with bridge method [inline-methods] */
    public ListenableFuture<ImageProxy> m73lambda$createPipeline$1$androidxcameracoreImageCapture(final ImageCaptureRequest imageCaptureRequest) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m82lambda$takePictureInternal$9$androidxcameracoreImageCapture(imageCaptureRequest, completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$takePictureInternal$9$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ Object m82lambda$takePictureInternal$9$androidxcameracoreImageCapture(final ImageCaptureRequest imageCaptureRequest, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mImageReader.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda4
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                ImageCapture.lambda$takePictureInternal$6(completer, imageReaderProxy);
            }
        }, CameraXExecutors.mainThreadExecutor());
        final TakePictureState takePictureState = new TakePictureState();
        final FutureChain futureChainTransformAsync = FutureChain.from(preTakePicture(takePictureState)).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda5
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f$0.m81lambda$takePictureInternal$7$androidxcameracoreImageCapture(imageCaptureRequest, (Void) obj);
            }
        }, this.mExecutor);
        Futures.addCallback(futureChainTransformAsync, new FutureCallback<Void>() { // from class: androidx.camera.core.ImageCapture.5
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Void r2) {
                ImageCapture.this.postTakePicture(takePictureState);
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                ImageCapture.this.postTakePicture(takePictureState);
                completer.setException(th);
            }
        }, this.mExecutor);
        completer.addCancellationListener(new Runnable() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                futureChainTransformAsync.cancel(true);
            }
        }, CameraXExecutors.directExecutor());
        return "takePictureInternal";
    }

    static /* synthetic */ void lambda$takePictureInternal$6(CallbackToFutureAdapter.Completer completer, ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            if (imageProxyAcquireLatestImage != null) {
                if (!completer.set(imageProxyAcquireLatestImage)) {
                    imageProxyAcquireLatestImage.close();
                }
            } else {
                completer.setException(new IllegalStateException("Unable to acquire image"));
            }
        } catch (IllegalStateException e) {
            completer.setException(e);
        }
    }

    /* JADX INFO: renamed from: lambda$takePictureInternal$7$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ ListenableFuture m81lambda$takePictureInternal$7$androidxcameracoreImageCapture(ImageCaptureRequest imageCaptureRequest, Void r2) throws Exception {
        return issueTakePicture(imageCaptureRequest);
    }

    static class ImageCaptureRequestProcessor implements ForwardingImageProxy.OnImageCloseListener {
        private final ImageCaptor mImageCaptor;
        private final int mMaxImages;
        private final Deque<ImageCaptureRequest> mPendingRequests = new ArrayDeque();
        ImageCaptureRequest mCurrentRequest = null;
        ListenableFuture<ImageProxy> mCurrentRequestFuture = null;
        int mOutstandingImages = 0;
        final Object mLock = new Object();

        interface ImageCaptor {
            ListenableFuture<ImageProxy> capture(ImageCaptureRequest imageCaptureRequest);
        }

        ImageCaptureRequestProcessor(int i, ImageCaptor imageCaptor) {
            this.mMaxImages = i;
            this.mImageCaptor = imageCaptor;
        }

        public void sendRequest(ImageCaptureRequest imageCaptureRequest) {
            synchronized (this.mLock) {
                this.mPendingRequests.offer(imageCaptureRequest);
                Log.d(ImageCapture.TAG, String.format("Send image capture request [current, pending] = [%d, %d]", Integer.valueOf(this.mCurrentRequest != null ? 1 : 0), Integer.valueOf(this.mPendingRequests.size())));
                processNextRequest();
            }
        }

        public void cancelRequests(Throwable th) {
            ImageCaptureRequest imageCaptureRequest;
            ListenableFuture<ImageProxy> listenableFuture;
            ArrayList arrayList;
            synchronized (this.mLock) {
                imageCaptureRequest = this.mCurrentRequest;
                this.mCurrentRequest = null;
                listenableFuture = this.mCurrentRequestFuture;
                this.mCurrentRequestFuture = null;
                arrayList = new ArrayList(this.mPendingRequests);
                this.mPendingRequests.clear();
            }
            if (imageCaptureRequest != null && listenableFuture != null) {
                imageCaptureRequest.notifyCallbackError(ImageCapture.getError(th), th.getMessage(), th);
                listenableFuture.cancel(true);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((ImageCaptureRequest) it.next()).notifyCallbackError(ImageCapture.getError(th), th.getMessage(), th);
            }
        }

        @Override // androidx.camera.core.ForwardingImageProxy.OnImageCloseListener
        public void onImageClose(ImageProxy imageProxy) {
            synchronized (this.mLock) {
                this.mOutstandingImages--;
                processNextRequest();
            }
        }

        void processNextRequest() {
            synchronized (this.mLock) {
                if (this.mCurrentRequest != null) {
                    return;
                }
                if (this.mOutstandingImages >= this.mMaxImages) {
                    Log.w(ImageCapture.TAG, "Too many acquire images. Close image to be able to process next.");
                    return;
                }
                final ImageCaptureRequest imageCaptureRequestPoll = this.mPendingRequests.poll();
                if (imageCaptureRequestPoll == null) {
                    return;
                }
                this.mCurrentRequest = imageCaptureRequestPoll;
                ListenableFuture<ImageProxy> listenableFutureCapture = this.mImageCaptor.capture(imageCaptureRequestPoll);
                this.mCurrentRequestFuture = listenableFutureCapture;
                Futures.addCallback(listenableFutureCapture, new FutureCallback<ImageProxy>() { // from class: androidx.camera.core.ImageCapture.ImageCaptureRequestProcessor.1
                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onSuccess(ImageProxy imageProxy) {
                        synchronized (ImageCaptureRequestProcessor.this.mLock) {
                            Preconditions.checkNotNull(imageProxy);
                            SingleCloseImageProxy singleCloseImageProxy = new SingleCloseImageProxy(imageProxy);
                            singleCloseImageProxy.addOnImageCloseListener(ImageCaptureRequestProcessor.this);
                            ImageCaptureRequestProcessor.this.mOutstandingImages++;
                            imageCaptureRequestPoll.dispatchImage(singleCloseImageProxy);
                            ImageCaptureRequestProcessor.this.mCurrentRequest = null;
                            ImageCaptureRequestProcessor.this.mCurrentRequestFuture = null;
                            ImageCaptureRequestProcessor.this.processNextRequest();
                        }
                    }

                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onFailure(Throwable th) {
                        synchronized (ImageCaptureRequestProcessor.this.mLock) {
                            if (!(th instanceof CancellationException)) {
                                imageCaptureRequestPoll.notifyCallbackError(ImageCapture.getError(th), th != null ? th.getMessage() : "Unknown error", th);
                            }
                            ImageCaptureRequestProcessor.this.mCurrentRequest = null;
                            ImageCaptureRequestProcessor.this.mCurrentRequestFuture = null;
                            ImageCaptureRequestProcessor.this.processNextRequest();
                        }
                    }
                }, CameraXExecutors.directExecutor());
            }
        }
    }

    public String toString() {
        return "ImageCapture:" + getName();
    }

    static int getError(Throwable th) {
        if (th instanceof CameraClosedException) {
            return 3;
        }
        return th instanceof CaptureFailedException ? 2 : 0;
    }

    @Override // androidx.camera.core.UseCase
    public void clear() {
        abortImageCaptureRequests();
        clearPipeline();
        this.mExecutor.shutdown();
    }

    @Override // androidx.camera.core.UseCase
    protected Size onSuggestedResolutionUpdated(Size size) {
        SessionConfig.Builder builderCreatePipeline = createPipeline(getCameraId(), this.mUserSettingConfig, size);
        this.mSessionConfigBuilder = builderCreatePipeline;
        updateSessionConfig(builderCreatePipeline.build());
        notifyActive();
        return size;
    }

    private ListenableFuture<Void> preTakePicture(final TakePictureState takePictureState) {
        return FutureChain.from(getPreCaptureStateIfNeeded()).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda8
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f$0.m76lambda$preTakePicture$10$androidxcameracoreImageCapture(takePictureState, (CameraCaptureResult) obj);
            }
        }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda9
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f$0.m77lambda$preTakePicture$11$androidxcameracoreImageCapture(takePictureState, (CameraCaptureResult) obj);
            }
        }, this.mExecutor).transform(new Function() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda10
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return ImageCapture.lambda$preTakePicture$12((Boolean) obj);
            }
        }, this.mExecutor);
    }

    /* JADX INFO: renamed from: lambda$preTakePicture$10$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ ListenableFuture m76lambda$preTakePicture$10$androidxcameracoreImageCapture(TakePictureState takePictureState, CameraCaptureResult cameraCaptureResult) throws Exception {
        takePictureState.mPreCaptureState = cameraCaptureResult;
        triggerAfIfNeeded(takePictureState);
        if (isAePrecaptureRequired(takePictureState)) {
            return triggerAePrecapture(takePictureState);
        }
        return Futures.immediateFuture(null);
    }

    /* JADX INFO: renamed from: lambda$preTakePicture$11$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ ListenableFuture m77lambda$preTakePicture$11$androidxcameracoreImageCapture(TakePictureState takePictureState, CameraCaptureResult cameraCaptureResult) throws Exception {
        return check3AConverged(takePictureState);
    }

    void postTakePicture(TakePictureState takePictureState) {
        cancelAfAeTrigger(takePictureState);
    }

    private ListenableFuture<CameraCaptureResult> getPreCaptureStateIfNeeded() {
        if (this.mEnableCheck3AConverged || getFlashMode() == 0) {
            return this.mSessionCallbackChecker.checkCaptureResult(new CaptureCallbackChecker.CaptureResultChecker<CameraCaptureResult>() { // from class: androidx.camera.core.ImageCapture.6
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // androidx.camera.core.ImageCapture.CaptureCallbackChecker.CaptureResultChecker
                public CameraCaptureResult check(CameraCaptureResult cameraCaptureResult) {
                    if (ImageCapture.DEBUG) {
                        Log.d(ImageCapture.TAG, "preCaptureState, AE=" + cameraCaptureResult.getAeState() + " AF =" + cameraCaptureResult.getAfState() + " AWB=" + cameraCaptureResult.getAwbState());
                    }
                    return cameraCaptureResult;
                }
            });
        }
        return Futures.immediateFuture(null);
    }

    boolean isAePrecaptureRequired(TakePictureState takePictureState) {
        int flashMode = getFlashMode();
        if (flashMode == 0) {
            return takePictureState.mPreCaptureState.getAeState() == CameraCaptureMetaData.AeState.FLASH_REQUIRED;
        }
        if (flashMode == 1) {
            return true;
        }
        if (flashMode == 2) {
            return false;
        }
        throw new AssertionError(getFlashMode());
    }

    ListenableFuture<Boolean> check3AConverged(TakePictureState takePictureState) {
        if (!this.mEnableCheck3AConverged && !takePictureState.mIsAePrecaptureTriggered) {
            return Futures.immediateFuture(false);
        }
        return this.mSessionCallbackChecker.checkCaptureResult(new CaptureCallbackChecker.CaptureResultChecker<Boolean>() { // from class: androidx.camera.core.ImageCapture.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.camera.core.ImageCapture.CaptureCallbackChecker.CaptureResultChecker
            public Boolean check(CameraCaptureResult cameraCaptureResult) {
                if (ImageCapture.DEBUG) {
                    Log.d(ImageCapture.TAG, "checkCaptureResult, AE=" + cameraCaptureResult.getAeState() + " AF =" + cameraCaptureResult.getAfState() + " AWB=" + cameraCaptureResult.getAwbState());
                }
                return ImageCapture.this.is3AConverged(cameraCaptureResult) ? true : null;
            }
        }, 1000L, false);
    }

    boolean is3AConverged(CameraCaptureResult cameraCaptureResult) {
        if (cameraCaptureResult == null) {
            return false;
        }
        return (cameraCaptureResult.getAfMode() == CameraCaptureMetaData.AfMode.ON_CONTINUOUS_AUTO || cameraCaptureResult.getAfMode() == CameraCaptureMetaData.AfMode.OFF || cameraCaptureResult.getAfMode() == CameraCaptureMetaData.AfMode.UNKNOWN || cameraCaptureResult.getAfState() == CameraCaptureMetaData.AfState.FOCUSED || cameraCaptureResult.getAfState() == CameraCaptureMetaData.AfState.LOCKED_FOCUSED || cameraCaptureResult.getAfState() == CameraCaptureMetaData.AfState.LOCKED_NOT_FOCUSED) && (cameraCaptureResult.getAeState() == CameraCaptureMetaData.AeState.CONVERGED || cameraCaptureResult.getAeState() == CameraCaptureMetaData.AeState.FLASH_REQUIRED || cameraCaptureResult.getAeState() == CameraCaptureMetaData.AeState.UNKNOWN) && (cameraCaptureResult.getAwbState() == CameraCaptureMetaData.AwbState.CONVERGED || cameraCaptureResult.getAwbState() == CameraCaptureMetaData.AwbState.UNKNOWN);
    }

    void triggerAfIfNeeded(TakePictureState takePictureState) {
        if (this.mEnableCheck3AConverged && takePictureState.mPreCaptureState.getAfMode() == CameraCaptureMetaData.AfMode.ON_MANUAL_AUTO && takePictureState.mPreCaptureState.getAfState() == CameraCaptureMetaData.AfState.INACTIVE) {
            triggerAf(takePictureState);
        }
    }

    private void triggerAf(TakePictureState takePictureState) {
        if (DEBUG) {
            Log.d(TAG, "triggerAf");
        }
        takePictureState.mIsAfTriggered = true;
        getCameraControl().triggerAf().addListener(new Runnable() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                ImageCapture.lambda$triggerAf$13();
            }
        }, CameraXExecutors.directExecutor());
    }

    ListenableFuture<CameraCaptureResult> triggerAePrecapture(TakePictureState takePictureState) {
        if (DEBUG) {
            Log.d(TAG, "triggerAePrecapture");
        }
        takePictureState.mIsAePrecaptureTriggered = true;
        return getCameraControl().triggerAePrecapture();
    }

    void cancelAfAeTrigger(TakePictureState takePictureState) {
        if (takePictureState.mIsAfTriggered || takePictureState.mIsAePrecaptureTriggered) {
            getCameraControl().cancelAfAeTrigger(takePictureState.mIsAfTriggered, takePictureState.mIsAePrecaptureTriggered);
            takePictureState.mIsAfTriggered = false;
            takePictureState.mIsAePrecaptureTriggered = false;
        }
    }

    ListenableFuture<Void> issueTakePicture(ImageCaptureRequest imageCaptureRequest) {
        CaptureBundle captureBundle;
        if (DEBUG) {
            Log.d(TAG, "issueTakePicture");
        }
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        if (this.mProcessingImageReader != null) {
            captureBundle = getCaptureBundle(null);
            if (captureBundle == null) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture cannot set empty CaptureBundle."));
            }
            if (captureBundle.getCaptureStages().size() > this.mMaxCaptureStages) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture has CaptureStages > Max CaptureStage size"));
            }
            this.mProcessingImageReader.setCaptureBundle(captureBundle);
        } else {
            captureBundle = getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
            if (captureBundle.getCaptureStages().size() > 1) {
                return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture have no CaptureProcess set with CaptureBundle size > 1."));
            }
        }
        for (final CaptureStage captureStage : captureBundle.getCaptureStages()) {
            final CaptureConfig.Builder builder = new CaptureConfig.Builder();
            builder.setTemplateType(this.mCaptureConfig.getTemplateType());
            builder.addImplementationOptions(this.mCaptureConfig.getImplementationOptions());
            builder.addAllCameraCaptureCallbacks(this.mSessionConfigBuilder.getSingleCameraCaptureCallbacks());
            builder.addSurface(this.mDeferrableSurface);
            builder.addImplementationOption(CaptureConfig.OPTION_ROTATION, Integer.valueOf(imageCaptureRequest.mRotationDegrees));
            builder.addImplementationOption(CaptureConfig.OPTION_JPEG_QUALITY, Integer.valueOf(imageCaptureRequest.mJpegQuality));
            builder.addImplementationOptions(captureStage.getCaptureConfig().getImplementationOptions());
            builder.setTag(captureStage.getCaptureConfig().getTag());
            builder.addCameraCaptureCallback(this.mMetadataMatchingCaptureCallback);
            arrayList.add(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda15
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m75lambda$issueTakePicture$14$androidxcameracoreImageCapture(builder, arrayList2, captureStage, completer);
                }
            }));
        }
        getCameraControl().submitCaptureRequests(arrayList2);
        return Futures.transform(Futures.allAsList(arrayList), new Function() { // from class: androidx.camera.core.ImageCapture$$ExternalSyntheticLambda1
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return ImageCapture.lambda$issueTakePicture$15((List) obj);
            }
        }, CameraXExecutors.directExecutor());
    }

    /* JADX INFO: renamed from: lambda$issueTakePicture$14$androidx-camera-core-ImageCapture, reason: not valid java name */
    /* synthetic */ Object m75lambda$issueTakePicture$14$androidxcameracoreImageCapture(CaptureConfig.Builder builder, List list, CaptureStage captureStage, final CallbackToFutureAdapter.Completer completer) throws Exception {
        builder.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.core.ImageCapture.8
            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCompleted(CameraCaptureResult cameraCaptureResult) {
                completer.set(null);
            }

            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureFailed(CameraCaptureFailure cameraCaptureFailure) {
                completer.setException(new CaptureFailedException("Capture request failed with reason " + cameraCaptureFailure.getReason()));
            }

            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCancelled() {
                completer.setException(new CameraClosedException("Capture request is cancelled because camera is closed"));
            }
        });
        list.add(builder.build());
        return "issueTakePicture[stage=" + captureStage.getId() + "]";
    }

    static final class CaptureFailedException extends RuntimeException {
        CaptureFailedException(String str, Throwable th) {
            super(str, th);
        }

        CaptureFailedException(String str) {
            super(str);
        }
    }

    private CaptureBundle getCaptureBundle(CaptureBundle captureBundle) {
        List<CaptureStage> captureStages = this.mCaptureBundle.getCaptureStages();
        return (captureStages == null || captureStages.isEmpty()) ? captureBundle : CaptureBundles.createCaptureBundle(captureStages);
    }

    public static abstract class OnImageCapturedCallback {
        public void onError(ImageCaptureException imageCaptureException) {
        }

        public void onCaptureSuccess(ImageProxy imageProxy) {
            imageProxy.close();
        }
    }

    public static final class Defaults implements ConfigProvider<ImageCaptureConfig> {
        private static final int DEFAULT_CAPTURE_MODE = 1;
        private static final ImageCaptureConfig DEFAULT_CONFIG = new Builder().setCaptureMode(1).setFlashMode(2).setSurfaceOccupancyPriority(4).getUseCaseConfig();
        private static final int DEFAULT_FLASH_MODE = 2;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 4;

        @Override // androidx.camera.core.impl.ConfigProvider
        public ImageCaptureConfig getConfig(CameraInfo cameraInfo) {
            return DEFAULT_CONFIG;
        }
    }

    public static final class OutputFileOptions {
        private static final Metadata EMPTY_METADATA = new Metadata();
        private final ContentResolver mContentResolver;
        private final ContentValues mContentValues;
        private final File mFile;
        private final Metadata mMetadata;
        private final OutputStream mOutputStream;
        private final Uri mSaveCollection;

        OutputFileOptions(File file, ContentResolver contentResolver, Uri uri, ContentValues contentValues, OutputStream outputStream, Metadata metadata) {
            this.mFile = file;
            this.mContentResolver = contentResolver;
            this.mSaveCollection = uri;
            this.mContentValues = contentValues;
            this.mOutputStream = outputStream;
            this.mMetadata = metadata == null ? EMPTY_METADATA : metadata;
        }

        File getFile() {
            return this.mFile;
        }

        ContentResolver getContentResolver() {
            return this.mContentResolver;
        }

        Uri getSaveCollection() {
            return this.mSaveCollection;
        }

        ContentValues getContentValues() {
            return this.mContentValues;
        }

        OutputStream getOutputStream() {
            return this.mOutputStream;
        }

        Metadata getMetadata() {
            return this.mMetadata;
        }

        public static final class Builder {
            private ContentResolver mContentResolver;
            private ContentValues mContentValues;
            private File mFile;
            private Metadata mMetadata;
            private OutputStream mOutputStream;
            private Uri mSaveCollection;

            public Builder(File file) {
                this.mFile = file;
            }

            public Builder(ContentResolver contentResolver, Uri uri, ContentValues contentValues) {
                this.mContentResolver = contentResolver;
                this.mSaveCollection = uri;
                this.mContentValues = contentValues;
            }

            public Builder(OutputStream outputStream) {
                this.mOutputStream = outputStream;
            }

            public Builder setMetadata(Metadata metadata) {
                this.mMetadata = metadata;
                return this;
            }

            public OutputFileOptions build() {
                return new OutputFileOptions(this.mFile, this.mContentResolver, this.mSaveCollection, this.mContentValues, this.mOutputStream, this.mMetadata);
            }
        }
    }

    public static class OutputFileResults {
        private Uri mSavedUri;

        OutputFileResults(Uri uri) {
            this.mSavedUri = uri;
        }

        public Uri getSavedUri() {
            return this.mSavedUri;
        }
    }

    public static final class Metadata {
        private boolean mIsReversedHorizontal;
        private boolean mIsReversedVertical;
        private Location mLocation;

        public boolean isReversedHorizontal() {
            return this.mIsReversedHorizontal;
        }

        public void setReversedHorizontal(boolean z) {
            this.mIsReversedHorizontal = z;
        }

        public boolean isReversedVertical() {
            return this.mIsReversedVertical;
        }

        public void setReversedVertical(boolean z) {
            this.mIsReversedVertical = z;
        }

        public Location getLocation() {
            return this.mLocation;
        }

        public void setLocation(Location location) {
            this.mLocation = location;
        }
    }

    static final class TakePictureState {
        CameraCaptureResult mPreCaptureState = CameraCaptureResult.EmptyCameraCaptureResult.create();
        boolean mIsAfTriggered = false;
        boolean mIsAePrecaptureTriggered = false;

        TakePictureState() {
        }
    }

    static final class CaptureCallbackChecker extends CameraCaptureCallback {
        private static final long NO_TIMEOUT = 0;
        private final Set<CaptureResultListener> mCaptureResultListeners = new HashSet();

        public interface CaptureResultChecker<T> {
            T check(CameraCaptureResult cameraCaptureResult);
        }

        private interface CaptureResultListener {
            boolean onCaptureResult(CameraCaptureResult cameraCaptureResult);
        }

        CaptureCallbackChecker() {
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureCompleted(CameraCaptureResult cameraCaptureResult) {
            deliverCaptureResultToListeners(cameraCaptureResult);
        }

        <T> ListenableFuture<T> checkCaptureResult(CaptureResultChecker<T> captureResultChecker) {
            return checkCaptureResult(captureResultChecker, 0L, null);
        }

        <T> ListenableFuture<T> checkCaptureResult(final CaptureResultChecker<T> captureResultChecker, final long j, final T t) {
            if (j < 0) {
                throw new IllegalArgumentException("Invalid timeout value: " + j);
            }
            final long jElapsedRealtime = j != 0 ? SystemClock.elapsedRealtime() : 0L;
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.ImageCapture$CaptureCallbackChecker$$ExternalSyntheticLambda0
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m83x26a9d6b9(captureResultChecker, jElapsedRealtime, j, t, completer);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$checkCaptureResult$0$androidx-camera-core-ImageCapture$CaptureCallbackChecker, reason: not valid java name */
        /* synthetic */ Object m83x26a9d6b9(final CaptureResultChecker captureResultChecker, final long j, final long j2, final Object obj, final CallbackToFutureAdapter.Completer completer) throws Exception {
            addListener(new CaptureResultListener() { // from class: androidx.camera.core.ImageCapture.CaptureCallbackChecker.1
                @Override // androidx.camera.core.ImageCapture.CaptureCallbackChecker.CaptureResultListener
                public boolean onCaptureResult(CameraCaptureResult cameraCaptureResult) {
                    Object objCheck = captureResultChecker.check(cameraCaptureResult);
                    if (objCheck != null) {
                        completer.set(objCheck);
                        return true;
                    }
                    if (j <= 0 || SystemClock.elapsedRealtime() - j <= j2) {
                        return false;
                    }
                    completer.set(obj);
                    return true;
                }
            });
            return "checkCaptureResult";
        }

        private void deliverCaptureResultToListeners(CameraCaptureResult cameraCaptureResult) {
            synchronized (this.mCaptureResultListeners) {
                HashSet hashSet = null;
                for (CaptureResultListener captureResultListener : new HashSet(this.mCaptureResultListeners)) {
                    if (captureResultListener.onCaptureResult(cameraCaptureResult)) {
                        if (hashSet == null) {
                            hashSet = new HashSet();
                        }
                        hashSet.add(captureResultListener);
                    }
                }
                if (hashSet != null) {
                    this.mCaptureResultListeners.removeAll(hashSet);
                }
            }
        }

        void addListener(CaptureResultListener captureResultListener) {
            synchronized (this.mCaptureResultListeners) {
                this.mCaptureResultListeners.add(captureResultListener);
            }
        }
    }

    static class ImageCaptureRequest {
        private final OnImageCapturedCallback mCallback;
        AtomicBoolean mDispatched = new AtomicBoolean(false);
        final int mJpegQuality;
        private final Executor mListenerExecutor;
        final int mRotationDegrees;
        private final Rational mTargetRatio;
        private final Rect mViewPortCropRect;

        ImageCaptureRequest(int i, int i2, Rational rational, Rect rect, Executor executor, OnImageCapturedCallback onImageCapturedCallback) {
            this.mRotationDegrees = i;
            this.mJpegQuality = i2;
            if (rational != null) {
                Preconditions.checkArgument(!rational.isZero(), "Target ratio cannot be zero");
                Preconditions.checkArgument(rational.floatValue() > 0.0f, "Target ratio must be positive");
            }
            this.mTargetRatio = rational;
            this.mViewPortCropRect = rect;
            this.mListenerExecutor = executor;
            this.mCallback = onImageCapturedCallback;
        }

        void dispatchImage(ImageProxy imageProxy) {
            Size size;
            int rotation;
            if (!this.mDispatched.compareAndSet(false, true)) {
                imageProxy.close();
                return;
            }
            if (imageProxy.getFormat() == 256) {
                try {
                    ByteBuffer buffer = imageProxy.getPlanes()[0].getBuffer();
                    buffer.rewind();
                    byte[] bArr = new byte[buffer.capacity()];
                    buffer.get(bArr);
                    Exif exifCreateFromInputStream = Exif.createFromInputStream(new ByteArrayInputStream(bArr));
                    buffer.rewind();
                    size = new Size(exifCreateFromInputStream.getWidth(), exifCreateFromInputStream.getHeight());
                    rotation = exifCreateFromInputStream.getRotation();
                } catch (IOException e) {
                    notifyCallbackError(1, "Unable to parse JPEG exif", e);
                    imageProxy.close();
                    return;
                }
            } else {
                rotation = this.mRotationDegrees;
                size = null;
            }
            final SettableImageProxy settableImageProxy = new SettableImageProxy(imageProxy, size, ImmutableImageInfo.create(imageProxy.getImageInfo().getTag(), imageProxy.getImageInfo().getTimestamp(), rotation));
            Rect rect = this.mViewPortCropRect;
            if (rect != null) {
                settableImageProxy.setCropRect(rect);
            } else {
                Rational rational = this.mTargetRatio;
                if (rational != null) {
                    if (rotation % 180 != 0) {
                        rational = new Rational(this.mTargetRatio.getDenominator(), this.mTargetRatio.getNumerator());
                    }
                    Size size2 = new Size(settableImageProxy.getWidth(), settableImageProxy.getHeight());
                    if (ImageUtil.isAspectRatioValid(size2, rational)) {
                        settableImageProxy.setCropRect(ImageUtil.computeCropRectFromAspectRatio(size2, rational));
                    }
                }
            }
            try {
                this.mListenerExecutor.execute(new Runnable() { // from class: androidx.camera.core.ImageCapture$ImageCaptureRequest$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m84xf35699e1(settableImageProxy);
                    }
                });
            } catch (RejectedExecutionException unused) {
                Log.e(ImageCapture.TAG, "Unable to post to the supplied executor.");
                imageProxy.close();
            }
        }

        /* JADX INFO: renamed from: lambda$dispatchImage$0$androidx-camera-core-ImageCapture$ImageCaptureRequest, reason: not valid java name */
        /* synthetic */ void m84xf35699e1(ImageProxy imageProxy) {
            this.mCallback.onCaptureSuccess(imageProxy);
        }

        void notifyCallbackError(final int i, final String str, final Throwable th) {
            if (this.mDispatched.compareAndSet(false, true)) {
                try {
                    this.mListenerExecutor.execute(new Runnable() { // from class: androidx.camera.core.ImageCapture$ImageCaptureRequest$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m85xe8eba4db(i, str, th);
                        }
                    });
                } catch (RejectedExecutionException unused) {
                    Log.e(ImageCapture.TAG, "Unable to post to the supplied executor.");
                }
            }
        }

        /* JADX INFO: renamed from: lambda$notifyCallbackError$1$androidx-camera-core-ImageCapture$ImageCaptureRequest, reason: not valid java name */
        /* synthetic */ void m85xe8eba4db(int i, String str, Throwable th) {
            this.mCallback.onError(new ImageCaptureException(i, str, th));
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<ImageCapture, ImageCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, IoConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public /* bridge */ /* synthetic */ Builder setSupportedResolutions(List list) {
            return setSupportedResolutions((List<Pair<Integer, Size[]>>) list);
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public /* bridge */ /* synthetic */ Object setTargetClass(Class cls) {
            return setTargetClass((Class<ImageCapture>) cls);
        }

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableOptionsBundle) {
            this.mMutableConfig = mutableOptionsBundle;
            Class cls = (Class) mutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (cls != null && !cls.equals(ImageCapture.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            setTargetClass(ImageCapture.class);
        }

        public static Builder fromConfig(ImageCaptureConfig imageCaptureConfig) {
            return new Builder(MutableOptionsBundle.from((Config) imageCaptureConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public ImageCaptureConfig getUseCaseConfig() {
            return new ImageCaptureConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public ImageCapture build() {
            if (getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO, null) != null && getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_TARGET_RESOLUTION, null) != null) {
                throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
            }
            Integer num = (Integer) getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_BUFFER_FORMAT, null);
            if (num != null) {
                Preconditions.checkArgument(getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, null) == null, "Cannot set buffer format with CaptureProcessor defined.");
                getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, num);
            } else if (getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, null) != null) {
                getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 35);
            } else {
                getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 256);
            }
            return new ImageCapture(getUseCaseConfig());
        }

        public Builder setCaptureMode(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IMAGE_CAPTURE_MODE, Integer.valueOf(i));
            return this;
        }

        public Builder setFlashMode(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_FLASH_MODE, Integer.valueOf(i));
            return this;
        }

        public Builder setCaptureBundle(CaptureBundle captureBundle) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_BUNDLE, captureBundle);
            return this;
        }

        public Builder setCaptureProcessor(CaptureProcessor captureProcessor) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, captureProcessor);
            return this;
        }

        public Builder setBufferFormat(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_BUFFER_FORMAT, Integer.valueOf(i));
            return this;
        }

        public Builder setMaxCaptureStages(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_MAX_CAPTURE_STAGES, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setSupportedResolutions(List<Pair<Integer, Size[]>> list) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_SUPPORTED_RESOLUTIONS, list);
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetClass(Class<ImageCapture> cls) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_CLASS, cls);
            if (getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(cls.getCanonicalName() + Parameters.DEFAULT_OPTION_PREFIXES + UUID.randomUUID());
            }
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetName(String str) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_NAME, str);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetAspectRatioCustom(Rational rational) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, rational);
            getMutableConfig().removeOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetAspectRatio(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetRotation(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ROTATION, Integer.valueOf(i));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setTargetResolution(Size size) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_RESOLUTION, size);
            if (size != null) {
                getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(size.getWidth(), size.getHeight()));
            }
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
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_MAX_RESOLUTION, size);
            return this;
        }

        public Builder setImageReaderProxyProvider(ImageReaderProxyProvider imageReaderProxyProvider) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IMAGE_READER_PROXY_PROVIDER, imageReaderProxyProvider);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.internal.IoConfig.Builder
        public Builder setIoExecutor(Executor executor) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IO_EXECUTOR, executor);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setDefaultSessionConfig(SessionConfig sessionConfig) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setDefaultCaptureConfig(CaptureConfig captureConfig) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setCameraSelector(CameraSelector cameraSelector) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public Builder setSurfaceOccupancyPriority(int i) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(i));
            return this;
        }

        @Override // androidx.camera.core.internal.UseCaseEventConfig.Builder
        public Builder setUseCaseEventCallback(UseCase.EventCallback eventCallback) {
            getMutableConfig().insertOption(ImageCaptureConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
            return this;
        }
    }
}
