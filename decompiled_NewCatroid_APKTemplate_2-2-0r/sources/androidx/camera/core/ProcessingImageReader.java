package androidx.camera.core;

import android.media.ImageReader;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class ProcessingImageReader implements ImageReaderProxy {
    private static final String TAG = "ProcessingImageReader";
    private final List<Integer> mCaptureIdList;
    final CaptureProcessor mCaptureProcessor;
    private FutureCallback<List<ImageProxy>> mCaptureStageReadyCallback;
    private boolean mClosed;
    Executor mExecutor;
    private ImageReaderProxy.OnImageAvailableListener mImageProcessedListener;
    private final ImageReaderProxy mInputImageReader;
    ImageReaderProxy.OnImageAvailableListener mListener;
    final Object mLock;
    private final ImageReaderProxy mOutputImageReader;
    final Executor mPostProcessExecutor;
    SettableImageProxyBundle mSettableImageProxyBundle;
    private ImageReaderProxy.OnImageAvailableListener mTransformedListener;

    /* JADX INFO: renamed from: androidx.camera.core.ProcessingImageReader$2, reason: invalid class name */
    class AnonymousClass2 implements ImageReaderProxy.OnImageAvailableListener {
        AnonymousClass2() {
        }

        @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
        public void onImageAvailable(ImageReaderProxy imageReaderProxy) {
            final ImageReaderProxy.OnImageAvailableListener onImageAvailableListener;
            Executor executor;
            synchronized (ProcessingImageReader.this.mLock) {
                onImageAvailableListener = ProcessingImageReader.this.mListener;
                executor = ProcessingImageReader.this.mExecutor;
                ProcessingImageReader.this.mSettableImageProxyBundle.reset();
                ProcessingImageReader.this.setupSettableImageProxyBundleCallbacks();
            }
            if (onImageAvailableListener != null) {
                if (executor != null) {
                    executor.execute(new Runnable() { // from class: androidx.camera.core.ProcessingImageReader$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m92x3c329d5d(onImageAvailableListener);
                        }
                    });
                } else {
                    onImageAvailableListener.onImageAvailable(ProcessingImageReader.this);
                }
            }
        }

        /* JADX INFO: renamed from: lambda$onImageAvailable$0$androidx-camera-core-ProcessingImageReader$2, reason: not valid java name */
        /* synthetic */ void m92x3c329d5d(ImageReaderProxy.OnImageAvailableListener onImageAvailableListener) {
            onImageAvailableListener.onImageAvailable(ProcessingImageReader.this);
        }
    }

    ProcessingImageReader(int i, int i2, int i3, int i4, Executor executor, CaptureBundle captureBundle, CaptureProcessor captureProcessor) {
        this(new MetadataImageReader(i, i2, i3, i4), executor, captureBundle, captureProcessor);
    }

    ProcessingImageReader(ImageReaderProxy imageReaderProxy, Executor executor, CaptureBundle captureBundle, CaptureProcessor captureProcessor) {
        this.mLock = new Object();
        this.mTransformedListener = new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.ProcessingImageReader.1
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public void onImageAvailable(ImageReaderProxy imageReaderProxy2) {
                ProcessingImageReader.this.imageIncoming(imageReaderProxy2);
            }
        };
        this.mImageProcessedListener = new AnonymousClass2();
        this.mCaptureStageReadyCallback = new FutureCallback<List<ImageProxy>>() { // from class: androidx.camera.core.ProcessingImageReader.3
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(List<ImageProxy> list) {
                SettableImageProxyBundle settableImageProxyBundle;
                synchronized (ProcessingImageReader.this.mLock) {
                    settableImageProxyBundle = ProcessingImageReader.this.mSettableImageProxyBundle;
                }
                ProcessingImageReader.this.mCaptureProcessor.process(settableImageProxyBundle);
            }
        };
        this.mClosed = false;
        this.mSettableImageProxyBundle = new SettableImageProxyBundle(Collections.emptyList());
        this.mCaptureIdList = new ArrayList();
        if (imageReaderProxy.getMaxImages() < captureBundle.getCaptureStages().size()) {
            throw new IllegalArgumentException("MetadataImageReader is smaller than CaptureBundle.");
        }
        this.mInputImageReader = imageReaderProxy;
        AndroidImageReaderProxy androidImageReaderProxy = new AndroidImageReaderProxy(ImageReader.newInstance(imageReaderProxy.getWidth(), imageReaderProxy.getHeight(), imageReaderProxy.getImageFormat(), imageReaderProxy.getMaxImages()));
        this.mOutputImageReader = androidImageReaderProxy;
        this.mPostProcessExecutor = executor;
        this.mCaptureProcessor = captureProcessor;
        captureProcessor.onOutputSurface(androidImageReaderProxy.getSurface(), getImageFormat());
        captureProcessor.onResolutionUpdate(new Size(imageReaderProxy.getWidth(), imageReaderProxy.getHeight()));
        setCaptureBundle(captureBundle);
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public ImageProxy acquireLatestImage() {
        ImageProxy imageProxyAcquireLatestImage;
        synchronized (this.mLock) {
            imageProxyAcquireLatestImage = this.mOutputImageReader.acquireLatestImage();
        }
        return imageProxyAcquireLatestImage;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public ImageProxy acquireNextImage() {
        ImageProxy imageProxyAcquireNextImage;
        synchronized (this.mLock) {
            imageProxyAcquireNextImage = this.mOutputImageReader.acquireNextImage();
        }
        return imageProxyAcquireNextImage;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public void close() {
        synchronized (this.mLock) {
            if (this.mClosed) {
                return;
            }
            this.mInputImageReader.close();
            this.mOutputImageReader.close();
            this.mSettableImageProxyBundle.close();
            this.mClosed = true;
        }
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getHeight() {
        int height;
        synchronized (this.mLock) {
            height = this.mInputImageReader.getHeight();
        }
        return height;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getWidth() {
        int width;
        synchronized (this.mLock) {
            width = this.mInputImageReader.getWidth();
        }
        return width;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getImageFormat() {
        int imageFormat;
        synchronized (this.mLock) {
            imageFormat = this.mInputImageReader.getImageFormat();
        }
        return imageFormat;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public int getMaxImages() {
        int maxImages;
        synchronized (this.mLock) {
            maxImages = this.mInputImageReader.getMaxImages();
        }
        return maxImages;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public Surface getSurface() {
        Surface surface;
        synchronized (this.mLock) {
            surface = this.mInputImageReader.getSurface();
        }
        return surface;
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public void setOnImageAvailableListener(ImageReaderProxy.OnImageAvailableListener onImageAvailableListener, Executor executor) {
        synchronized (this.mLock) {
            this.mListener = (ImageReaderProxy.OnImageAvailableListener) Preconditions.checkNotNull(onImageAvailableListener);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
            this.mInputImageReader.setOnImageAvailableListener(this.mTransformedListener, executor);
            this.mOutputImageReader.setOnImageAvailableListener(this.mImageProcessedListener, executor);
        }
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy
    public void clearOnImageAvailableListener() {
        synchronized (this.mLock) {
            this.mListener = null;
            this.mExecutor = null;
            this.mInputImageReader.clearOnImageAvailableListener();
            this.mOutputImageReader.clearOnImageAvailableListener();
            this.mSettableImageProxyBundle.close();
        }
    }

    public void setCaptureBundle(CaptureBundle captureBundle) {
        synchronized (this.mLock) {
            if (captureBundle.getCaptureStages() != null) {
                if (this.mInputImageReader.getMaxImages() < captureBundle.getCaptureStages().size()) {
                    throw new IllegalArgumentException("CaptureBundle is lager than InputImageReader.");
                }
                this.mCaptureIdList.clear();
                for (CaptureStage captureStage : captureBundle.getCaptureStages()) {
                    if (captureStage != null) {
                        this.mCaptureIdList.add(Integer.valueOf(captureStage.getId()));
                    }
                }
            }
            this.mSettableImageProxyBundle = new SettableImageProxyBundle(this.mCaptureIdList);
            setupSettableImageProxyBundleCallbacks();
        }
    }

    CameraCaptureCallback getCameraCaptureCallback() {
        synchronized (this.mLock) {
            ImageReaderProxy imageReaderProxy = this.mInputImageReader;
            if (!(imageReaderProxy instanceof MetadataImageReader)) {
                return null;
            }
            return ((MetadataImageReader) imageReaderProxy).getCameraCaptureCallback();
        }
    }

    void setupSettableImageProxyBundleCallbacks() {
        ArrayList arrayList = new ArrayList();
        Iterator<Integer> it = this.mCaptureIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(this.mSettableImageProxyBundle.getImageProxy(it.next().intValue()));
        }
        Futures.addCallback(Futures.allAsList(arrayList), this.mCaptureStageReadyCallback, this.mPostProcessExecutor);
    }

    void imageIncoming(ImageReaderProxy imageReaderProxy) {
        synchronized (this.mLock) {
            if (this.mClosed) {
                return;
            }
            try {
                ImageProxy imageProxyAcquireNextImage = imageReaderProxy.acquireNextImage();
                if (imageProxyAcquireNextImage != null) {
                    Integer num = (Integer) imageProxyAcquireNextImage.getImageInfo().getTag();
                    if (!this.mCaptureIdList.contains(num)) {
                        Log.w(TAG, "ImageProxyBundle does not contain this id: " + num);
                        imageProxyAcquireNextImage.close();
                    } else {
                        this.mSettableImageProxyBundle.addImageProxy(imageProxyAcquireNextImage);
                    }
                }
            } catch (IllegalStateException e) {
                Log.e(TAG, "Failed to acquire latest image.", e);
            }
        }
    }
}
