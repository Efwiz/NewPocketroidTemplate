package androidx.camera.core;

import android.location.Location;
import android.media.AudioRecord;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import com.beust.jcommander.Parameters;
import com.google.common.util.concurrent.ListenableFuture;
import com.hivemq.client.mqtt.MqttProxyConfig;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class VideoCapture extends UseCase {
    private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    private static final int DEQUE_TIMEOUT_USEC = 10000;
    public static final int ERROR_ENCODER = 1;
    public static final int ERROR_MUXER = 2;
    public static final int ERROR_RECORDING_IN_PROGRESS = 3;
    public static final int ERROR_UNKNOWN = 0;
    private static final String TAG = "VideoCapture";
    private static final String VIDEO_MIME_TYPE = "video/avc";
    private int mAudioBitRate;
    private final MediaCodec.BufferInfo mAudioBufferInfo;
    private int mAudioBufferSize;
    private int mAudioChannelCount;
    private MediaCodec mAudioEncoder;
    private final Handler mAudioHandler;
    private final HandlerThread mAudioHandlerThread;
    private AudioRecord mAudioRecorder;
    private int mAudioSampleRate;
    private int mAudioTrackIndex;
    Surface mCameraSurface;
    private DeferrableSurface mDeferrableSurface;
    private final AtomicBoolean mEndOfAudioStreamSignal;
    private final AtomicBoolean mEndOfAudioVideoSignal;
    private final AtomicBoolean mEndOfVideoStreamSignal;
    private final AtomicBoolean mIsFirstAudioSampleWrite;
    private final AtomicBoolean mIsFirstVideoSampleWrite;
    private boolean mIsRecording;
    private MediaMuxer mMuxer;
    private final Object mMuxerLock;
    private boolean mMuxerStarted;
    private final MediaCodec.BufferInfo mVideoBufferInfo;
    MediaCodec mVideoEncoder;
    private final Handler mVideoHandler;
    private final HandlerThread mVideoHandlerThread;
    private int mVideoTrackIndex;
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final Metadata EMPTY_METADATA = new Metadata();
    private static final int[] CamcorderQuality = {8, 6, 5, 4};
    private static final short[] sAudioEncoding = {2, 3, 4};

    public static final class Metadata {
        public Location location;
    }

    public interface OnVideoSavedCallback {
        void onError(int i, String str, Throwable th);

        void onVideoSaved(File file);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoCaptureError {
    }

    VideoCapture(VideoCaptureConfig videoCaptureConfig) {
        super(videoCaptureConfig);
        this.mVideoBufferInfo = new MediaCodec.BufferInfo();
        this.mMuxerLock = new Object();
        HandlerThread handlerThread = new HandlerThread("CameraX-video encoding thread");
        this.mVideoHandlerThread = handlerThread;
        HandlerThread handlerThread2 = new HandlerThread("CameraX-audio encoding thread");
        this.mAudioHandlerThread = handlerThread2;
        this.mEndOfVideoStreamSignal = new AtomicBoolean(true);
        this.mEndOfAudioStreamSignal = new AtomicBoolean(true);
        this.mEndOfAudioVideoSignal = new AtomicBoolean(true);
        this.mAudioBufferInfo = new MediaCodec.BufferInfo();
        this.mIsFirstVideoSampleWrite = new AtomicBoolean(false);
        this.mIsFirstAudioSampleWrite = new AtomicBoolean(false);
        this.mMuxerStarted = false;
        this.mIsRecording = false;
        handlerThread.start();
        this.mVideoHandler = new Handler(handlerThread.getLooper());
        handlerThread2.start();
        this.mAudioHandler = new Handler(handlerThread2.getLooper());
    }

    private static MediaFormat createMediaFormat(VideoCaptureConfig videoCaptureConfig, Size size) {
        MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, size.getWidth(), size.getHeight());
        mediaFormatCreateVideoFormat.setInteger("color-format", 2130708361);
        mediaFormatCreateVideoFormat.setInteger("bitrate", videoCaptureConfig.getBitRate());
        mediaFormatCreateVideoFormat.setInteger("frame-rate", videoCaptureConfig.getVideoFrameRate());
        mediaFormatCreateVideoFormat.setInteger("i-frame-interval", videoCaptureConfig.getIFrameInterval());
        return mediaFormatCreateVideoFormat;
    }

    @Override // androidx.camera.core.UseCase
    public UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo cameraInfo) {
        VideoCaptureConfig videoCaptureConfig = (VideoCaptureConfig) CameraX.getDefaultUseCaseConfig(VideoCaptureConfig.class, cameraInfo);
        if (videoCaptureConfig != null) {
            return Builder.fromConfig(videoCaptureConfig);
        }
        return null;
    }

    @Override // androidx.camera.core.UseCase
    protected Size onSuggestedResolutionUpdated(Size size) {
        if (this.mCameraSurface != null) {
            this.mVideoEncoder.stop();
            this.mVideoEncoder.release();
            this.mAudioEncoder.stop();
            this.mAudioEncoder.release();
            releaseCameraSurface(false);
        }
        try {
            this.mVideoEncoder = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE);
            this.mAudioEncoder = MediaCodec.createEncoderByType(AUDIO_MIME_TYPE);
            setupEncoder(getCameraId(), size);
            return size;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create MediaCodec due to: " + e.getCause());
        }
    }

    public void startRecording(File file, Executor executor, OnVideoSavedCallback onVideoSavedCallback) {
        this.mIsFirstVideoSampleWrite.set(false);
        this.mIsFirstAudioSampleWrite.set(false);
        startRecording(file, EMPTY_METADATA, executor, onVideoSavedCallback);
    }

    public void startRecording(final File file, Metadata metadata, Executor executor, OnVideoSavedCallback onVideoSavedCallback) {
        Log.i(TAG, "startRecording");
        final VideoSavedListenerWrapper videoSavedListenerWrapper = new VideoSavedListenerWrapper(executor, onVideoSavedCallback);
        if (!this.mEndOfAudioVideoSignal.get()) {
            videoSavedListenerWrapper.onError(3, "It is still in video recording!", null);
            return;
        }
        try {
            this.mAudioRecorder.startRecording();
            CameraInternal camera = getCamera();
            final String cameraId = getCameraId();
            final Size attachedSurfaceResolution = getAttachedSurfaceResolution();
            try {
                Log.i(TAG, "videoEncoder start");
                this.mVideoEncoder.start();
                Log.i(TAG, "audioEncoder start");
                this.mAudioEncoder.start();
                int sensorRotationDegrees = camera.getCameraInfoInternal().getSensorRotationDegrees(((ImageOutputConfig) getUseCaseConfig()).getTargetRotation(0));
                try {
                    synchronized (this.mMuxerLock) {
                        MediaMuxer mediaMuxer = new MediaMuxer(file.getAbsolutePath(), 0);
                        this.mMuxer = mediaMuxer;
                        mediaMuxer.setOrientationHint(sensorRotationDegrees);
                        if (metadata.location != null) {
                            this.mMuxer.setLocation((float) metadata.location.getLatitude(), (float) metadata.location.getLongitude());
                        }
                    }
                    this.mEndOfVideoStreamSignal.set(false);
                    this.mEndOfAudioStreamSignal.set(false);
                    this.mEndOfAudioVideoSignal.set(false);
                    this.mIsRecording = true;
                    notifyActive();
                    this.mAudioHandler.post(new Runnable() { // from class: androidx.camera.core.VideoCapture.1
                        @Override // java.lang.Runnable
                        public void run() {
                            VideoCapture.this.audioEncode(videoSavedListenerWrapper);
                        }
                    });
                    this.mVideoHandler.post(new Runnable() { // from class: androidx.camera.core.VideoCapture.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (VideoCapture.this.videoEncode(videoSavedListenerWrapper, cameraId, attachedSurfaceResolution)) {
                                return;
                            }
                            videoSavedListenerWrapper.onVideoSaved(file);
                        }
                    });
                } catch (IOException e) {
                    setupEncoder(cameraId, attachedSurfaceResolution);
                    videoSavedListenerWrapper.onError(2, "MediaMuxer creation failed!", e);
                }
            } catch (IllegalStateException e2) {
                setupEncoder(cameraId, attachedSurfaceResolution);
                videoSavedListenerWrapper.onError(1, "Audio/Video encoder start fail", e2);
            }
        } catch (IllegalStateException e3) {
            videoSavedListenerWrapper.onError(1, "AudioRecorder start fail", e3);
        }
    }

    public void stopRecording() {
        Log.i(TAG, "stopRecording");
        notifyInactive();
        if (this.mEndOfAudioVideoSignal.get() || !this.mIsRecording) {
            return;
        }
        this.mEndOfAudioStreamSignal.set(true);
    }

    @Override // androidx.camera.core.UseCase
    public void clear() {
        this.mVideoHandlerThread.quitSafely();
        this.mAudioHandlerThread.quitSafely();
        MediaCodec mediaCodec = this.mAudioEncoder;
        if (mediaCodec != null) {
            mediaCodec.release();
            this.mAudioEncoder = null;
        }
        AudioRecord audioRecord = this.mAudioRecorder;
        if (audioRecord != null) {
            audioRecord.release();
            this.mAudioRecorder = null;
        }
        if (this.mCameraSurface != null) {
            releaseCameraSurface(true);
        }
    }

    private void releaseCameraSurface(final boolean z) {
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface == null) {
            return;
        }
        final MediaCodec mediaCodec = this.mVideoEncoder;
        deferrableSurface.close();
        this.mDeferrableSurface.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.VideoCapture$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VideoCapture.lambda$releaseCameraSurface$0(z, mediaCodec);
            }
        }, CameraXExecutors.mainThreadExecutor());
        if (z) {
            this.mVideoEncoder = null;
        }
        this.mCameraSurface = null;
        this.mDeferrableSurface = null;
    }

    static /* synthetic */ void lambda$releaseCameraSurface$0(boolean z, MediaCodec mediaCodec) {
        if (!z || mediaCodec == null) {
            return;
        }
        mediaCodec.release();
    }

    public void setTargetRotation(int i) {
        VideoCaptureConfig videoCaptureConfig = (VideoCaptureConfig) getUseCaseConfig();
        Builder builderFromConfig = Builder.fromConfig(videoCaptureConfig);
        int targetRotation = videoCaptureConfig.getTargetRotation(-1);
        if (targetRotation == -1 || targetRotation != i) {
            UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(builderFromConfig, i);
            updateUseCaseConfig(builderFromConfig.getUseCaseConfig());
        }
    }

    void setupEncoder(final String str, final Size size) {
        VideoCaptureConfig videoCaptureConfig = (VideoCaptureConfig) getUseCaseConfig();
        this.mVideoEncoder.reset();
        this.mVideoEncoder.configure(createMediaFormat(videoCaptureConfig, size), (Surface) null, (MediaCrypto) null, 1);
        if (this.mCameraSurface != null) {
            releaseCameraSurface(false);
        }
        final Surface surfaceCreateInputSurface = this.mVideoEncoder.createInputSurface();
        this.mCameraSurface = surfaceCreateInputSurface;
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(videoCaptureConfig);
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        ImmediateSurface immediateSurface = new ImmediateSurface(this.mCameraSurface);
        this.mDeferrableSurface = immediateSurface;
        ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
        Objects.requireNonNull(surfaceCreateInputSurface);
        terminationFuture.addListener(new Runnable() { // from class: androidx.camera.core.VideoCapture$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                surfaceCreateInputSurface.release();
            }
        }, CameraXExecutors.mainThreadExecutor());
        builderCreateFrom.addSurface(this.mDeferrableSurface);
        builderCreateFrom.addErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.VideoCapture.3
            @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
            public void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
                if (VideoCapture.this.isCurrentCamera(str)) {
                    VideoCapture.this.setupEncoder(str, size);
                }
            }
        });
        updateSessionConfig(builderCreateFrom.build());
        setAudioParametersByCamcorderProfile(size, str);
        this.mAudioEncoder.reset();
        this.mAudioEncoder.configure(createAudioMediaFormat(), (Surface) null, (MediaCrypto) null, 1);
        AudioRecord audioRecord = this.mAudioRecorder;
        if (audioRecord != null) {
            audioRecord.release();
        }
        AudioRecord audioRecordAutoConfigAudioRecordSource = autoConfigAudioRecordSource(videoCaptureConfig);
        this.mAudioRecorder = audioRecordAutoConfigAudioRecordSource;
        if (audioRecordAutoConfigAudioRecordSource == null) {
            Log.e(TAG, "AudioRecord object cannot initialized correctly!");
        }
        this.mVideoTrackIndex = -1;
        this.mAudioTrackIndex = -1;
        this.mIsRecording = false;
    }

    private boolean writeVideoEncodedBuffer(int i) {
        if (i < 0) {
            Log.e(TAG, "Output buffer should not have negative index: " + i);
            return false;
        }
        ByteBuffer outputBuffer = this.mVideoEncoder.getOutputBuffer(i);
        if (outputBuffer == null) {
            Log.d(TAG, "OutputBuffer was null.");
            return false;
        }
        if (this.mAudioTrackIndex >= 0 && this.mVideoTrackIndex >= 0 && this.mVideoBufferInfo.size > 0) {
            outputBuffer.position(this.mVideoBufferInfo.offset);
            outputBuffer.limit(this.mVideoBufferInfo.offset + this.mVideoBufferInfo.size);
            this.mVideoBufferInfo.presentationTimeUs = System.nanoTime() / 1000;
            synchronized (this.mMuxerLock) {
                if (!this.mIsFirstVideoSampleWrite.get()) {
                    Log.i(TAG, "First video sample written.");
                    this.mIsFirstVideoSampleWrite.set(true);
                }
                this.mMuxer.writeSampleData(this.mVideoTrackIndex, outputBuffer, this.mVideoBufferInfo);
            }
        }
        this.mVideoEncoder.releaseOutputBuffer(i, false);
        return (this.mVideoBufferInfo.flags & 4) != 0;
    }

    private boolean writeAudioEncodedBuffer(int i) {
        ByteBuffer outputBuffer = getOutputBuffer(this.mAudioEncoder, i);
        outputBuffer.position(this.mAudioBufferInfo.offset);
        if (this.mAudioTrackIndex >= 0 && this.mVideoTrackIndex >= 0 && this.mAudioBufferInfo.size > 0 && this.mAudioBufferInfo.presentationTimeUs > 0) {
            try {
                synchronized (this.mMuxerLock) {
                    if (!this.mIsFirstAudioSampleWrite.get()) {
                        Log.i(TAG, "First audio sample written.");
                        this.mIsFirstAudioSampleWrite.set(true);
                    }
                    this.mMuxer.writeSampleData(this.mAudioTrackIndex, outputBuffer, this.mAudioBufferInfo);
                }
            } catch (Exception e) {
                Log.e(TAG, "audio error:size=" + this.mAudioBufferInfo.size + "/offset=" + this.mAudioBufferInfo.offset + "/timeUs=" + this.mAudioBufferInfo.presentationTimeUs);
                e.printStackTrace();
            }
        }
        this.mAudioEncoder.releaseOutputBuffer(i, false);
        return (this.mAudioBufferInfo.flags & 4) != 0;
    }

    boolean videoEncode(OnVideoSavedCallback onVideoSavedCallback, String str, Size size) {
        boolean zWriteVideoEncodedBuffer = false;
        boolean z = false;
        while (!zWriteVideoEncodedBuffer && !z) {
            if (this.mEndOfVideoStreamSignal.get()) {
                this.mVideoEncoder.signalEndOfInputStream();
                this.mEndOfVideoStreamSignal.set(false);
            }
            int iDequeueOutputBuffer = this.mVideoEncoder.dequeueOutputBuffer(this.mVideoBufferInfo, 10000L);
            if (iDequeueOutputBuffer == -2) {
                if (this.mMuxerStarted) {
                    onVideoSavedCallback.onError(1, "Unexpected change in video encoding format.", null);
                    z = true;
                }
                synchronized (this.mMuxerLock) {
                    int iAddTrack = this.mMuxer.addTrack(this.mVideoEncoder.getOutputFormat());
                    this.mVideoTrackIndex = iAddTrack;
                    if (this.mAudioTrackIndex >= 0 && iAddTrack >= 0) {
                        this.mMuxerStarted = true;
                        Log.i(TAG, "media mMuxer start");
                        this.mMuxer.start();
                    }
                }
            } else {
                zWriteVideoEncodedBuffer = writeVideoEncodedBuffer(iDequeueOutputBuffer);
            }
        }
        try {
            Log.i(TAG, "videoEncoder stop");
            this.mVideoEncoder.stop();
        } catch (IllegalStateException e) {
            onVideoSavedCallback.onError(1, "Video encoder stop failed!", e);
            z = true;
        }
        try {
            synchronized (this.mMuxerLock) {
                MediaMuxer mediaMuxer = this.mMuxer;
                if (mediaMuxer != null) {
                    if (this.mMuxerStarted) {
                        mediaMuxer.stop();
                    }
                    this.mMuxer.release();
                    this.mMuxer = null;
                }
            }
        } catch (IllegalStateException e2) {
            onVideoSavedCallback.onError(2, "Muxer stop failed!", e2);
            z = true;
        }
        this.mMuxerStarted = false;
        setupEncoder(str, size);
        notifyReset();
        this.mEndOfAudioVideoSignal.set(true);
        Log.i(TAG, "Video encode thread end.");
        return z;
    }

    boolean audioEncode(OnVideoSavedCallback onVideoSavedCallback) {
        boolean zWriteAudioEncodedBuffer = false;
        while (!zWriteAudioEncodedBuffer && this.mIsRecording) {
            if (this.mEndOfAudioStreamSignal.get()) {
                this.mEndOfAudioStreamSignal.set(false);
                this.mIsRecording = false;
            }
            MediaCodec mediaCodec = this.mAudioEncoder;
            if (mediaCodec != null && this.mAudioRecorder != null) {
                int iDequeueInputBuffer = mediaCodec.dequeueInputBuffer(-1L);
                if (iDequeueInputBuffer >= 0) {
                    ByteBuffer inputBuffer = getInputBuffer(this.mAudioEncoder, iDequeueInputBuffer);
                    inputBuffer.clear();
                    int i = this.mAudioRecorder.read(inputBuffer, this.mAudioBufferSize);
                    if (i > 0) {
                        this.mAudioEncoder.queueInputBuffer(iDequeueInputBuffer, 0, i, System.nanoTime() / 1000, this.mIsRecording ? 0 : 4);
                    }
                }
                do {
                    int iDequeueOutputBuffer = this.mAudioEncoder.dequeueOutputBuffer(this.mAudioBufferInfo, 0L);
                    if (iDequeueOutputBuffer == -2) {
                        synchronized (this.mMuxerLock) {
                            int iAddTrack = this.mMuxer.addTrack(this.mAudioEncoder.getOutputFormat());
                            this.mAudioTrackIndex = iAddTrack;
                            if (iAddTrack >= 0 && this.mVideoTrackIndex >= 0) {
                                this.mMuxerStarted = true;
                                this.mMuxer.start();
                            }
                        }
                    } else if (iDequeueOutputBuffer != -1) {
                        zWriteAudioEncodedBuffer = writeAudioEncodedBuffer(iDequeueOutputBuffer);
                    }
                    if (iDequeueOutputBuffer < 0) {
                        break;
                    }
                } while (!zWriteAudioEncodedBuffer);
            }
        }
        try {
            Log.i(TAG, "audioRecorder stop");
            this.mAudioRecorder.stop();
        } catch (IllegalStateException e) {
            onVideoSavedCallback.onError(1, "Audio recorder stop failed!", e);
        }
        try {
            this.mAudioEncoder.stop();
        } catch (IllegalStateException e2) {
            onVideoSavedCallback.onError(1, "Audio encoder stop failed!", e2);
        }
        Log.i(TAG, "Audio encode thread end");
        this.mEndOfVideoStreamSignal.set(true);
        return false;
    }

    private ByteBuffer getInputBuffer(MediaCodec mediaCodec, int i) {
        return mediaCodec.getInputBuffer(i);
    }

    private ByteBuffer getOutputBuffer(MediaCodec mediaCodec, int i) {
        return mediaCodec.getOutputBuffer(i);
    }

    private MediaFormat createAudioMediaFormat() {
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(AUDIO_MIME_TYPE, this.mAudioSampleRate, this.mAudioChannelCount);
        mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
        mediaFormatCreateAudioFormat.setInteger("bitrate", this.mAudioBitRate);
        return mediaFormatCreateAudioFormat;
    }

    private AudioRecord autoConfigAudioRecordSource(VideoCaptureConfig videoCaptureConfig) {
        for (short s : sAudioEncoding) {
            int i = this.mAudioChannelCount == 1 ? 16 : 12;
            int audioRecordSource = videoCaptureConfig.getAudioRecordSource();
            try {
                int minBufferSize = AudioRecord.getMinBufferSize(this.mAudioSampleRate, i, s);
                if (minBufferSize <= 0) {
                    minBufferSize = videoCaptureConfig.getAudioMinBufferSize();
                }
                int i2 = minBufferSize;
                AudioRecord audioRecord = new AudioRecord(audioRecordSource, this.mAudioSampleRate, i, s, i2 * 2);
                if (audioRecord.getState() == 1) {
                    this.mAudioBufferSize = i2;
                    Log.i(TAG, "source: " + audioRecordSource + " audioSampleRate: " + this.mAudioSampleRate + " channelConfig: " + i + " audioFormat: " + ((int) s) + " bufferSize: " + i2);
                    return audioRecord;
                }
                continue;
            } catch (Exception e) {
                Log.e(TAG, "Exception, keep trying.", e);
            }
        }
        return null;
    }

    private void setAudioParametersByCamcorderProfile(Size size, String str) {
        for (int i : CamcorderQuality) {
            if (CamcorderProfile.hasProfile(Integer.parseInt(str), i)) {
                CamcorderProfile camcorderProfile = CamcorderProfile.get(Integer.parseInt(str), i);
                if (size.getWidth() == camcorderProfile.videoFrameWidth && size.getHeight() == camcorderProfile.videoFrameHeight) {
                    this.mAudioChannelCount = camcorderProfile.audioChannels;
                    this.mAudioSampleRate = camcorderProfile.audioSampleRate;
                    this.mAudioBitRate = camcorderProfile.audioBitRate;
                    return;
                }
            }
        }
        VideoCaptureConfig videoCaptureConfig = (VideoCaptureConfig) getUseCaseConfig();
        this.mAudioChannelCount = videoCaptureConfig.getAudioChannelCount();
        this.mAudioSampleRate = videoCaptureConfig.getAudioSampleRate();
        this.mAudioBitRate = videoCaptureConfig.getAudioBitRate();
    }

    public static final class Defaults implements ConfigProvider<VideoCaptureConfig> {
        private static final int DEFAULT_AUDIO_BIT_RATE = 64000;
        private static final int DEFAULT_AUDIO_CHANNEL_COUNT = 1;
        private static final int DEFAULT_AUDIO_MIN_BUFFER_SIZE = 1024;
        private static final int DEFAULT_AUDIO_RECORD_SOURCE = 1;
        private static final int DEFAULT_AUDIO_SAMPLE_RATE = 8000;
        private static final int DEFAULT_BIT_RATE = 8388608;
        private static final VideoCaptureConfig DEFAULT_CONFIG;
        private static final int DEFAULT_INTRA_FRAME_INTERVAL = 1;
        private static final Size DEFAULT_MAX_RESOLUTION;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 3;
        private static final int DEFAULT_VIDEO_FRAME_RATE = 30;

        static {
            Size size = new Size(1920, MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT);
            DEFAULT_MAX_RESOLUTION = size;
            DEFAULT_CONFIG = new Builder().setVideoFrameRate(30).setBitRate(8388608).setIFrameInterval(1).setAudioBitRate(DEFAULT_AUDIO_BIT_RATE).setAudioSampleRate(8000).setAudioChannelCount(1).setAudioRecordSource(1).setAudioMinBufferSize(1024).setMaxResolution(size).setSurfaceOccupancyPriority(3).getUseCaseConfig();
        }

        @Override // androidx.camera.core.impl.ConfigProvider
        public VideoCaptureConfig getConfig(CameraInfo cameraInfo) {
            return DEFAULT_CONFIG;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class VideoSavedListenerWrapper implements OnVideoSavedCallback {
        Executor mExecutor;
        OnVideoSavedCallback mOnVideoSavedCallback;

        VideoSavedListenerWrapper(Executor executor, OnVideoSavedCallback onVideoSavedCallback) {
            this.mExecutor = executor;
            this.mOnVideoSavedCallback = onVideoSavedCallback;
        }

        /* JADX INFO: renamed from: lambda$onVideoSaved$0$androidx-camera-core-VideoCapture$VideoSavedListenerWrapper, reason: not valid java name */
        /* synthetic */ void m100x99849d14(File file) {
            this.mOnVideoSavedCallback.onVideoSaved(file);
        }

        @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
        public void onVideoSaved(final File file) {
            try {
                this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.VideoCapture$VideoSavedListenerWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m100x99849d14(file);
                    }
                });
            } catch (RejectedExecutionException unused) {
                Log.e(VideoCapture.TAG, "Unable to post to the supplied executor.");
            }
        }

        @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
        public void onError(final int i, final String str, final Throwable th) {
            try {
                this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.VideoCapture$VideoSavedListenerWrapper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m99xe8a64259(i, str, th);
                    }
                });
            } catch (RejectedExecutionException unused) {
                Log.e(VideoCapture.TAG, "Unable to post to the supplied executor.");
            }
        }

        /* JADX INFO: renamed from: lambda$onError$1$androidx-camera-core-VideoCapture$VideoSavedListenerWrapper, reason: not valid java name */
        /* synthetic */ void m99xe8a64259(int i, String str, Throwable th) {
            this.mOnVideoSavedCallback.onError(i, str, th);
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<VideoCapture, VideoCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public /* bridge */ /* synthetic */ Builder setSupportedResolutions(List list) {
            return setSupportedResolutions((List<Pair<Integer, Size[]>>) list);
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public /* bridge */ /* synthetic */ Object setTargetClass(Class cls) {
            return setTargetClass((Class<VideoCapture>) cls);
        }

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(MutableOptionsBundle mutableOptionsBundle) {
            this.mMutableConfig = mutableOptionsBundle;
            Class cls = (Class) mutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (cls != null && !cls.equals(VideoCapture.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            setTargetClass(VideoCapture.class);
        }

        public static Builder fromConfig(VideoCaptureConfig videoCaptureConfig) {
            return new Builder(MutableOptionsBundle.from((Config) videoCaptureConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        public VideoCaptureConfig getUseCaseConfig() {
            return new VideoCaptureConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        public VideoCapture build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) != null && getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) != null) {
                throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
            }
            return new VideoCapture(getUseCaseConfig());
        }

        public Builder setVideoFrameRate(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_VIDEO_FRAME_RATE, Integer.valueOf(i));
            return this;
        }

        public Builder setBitRate(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_BIT_RATE, Integer.valueOf(i));
            return this;
        }

        public Builder setIFrameInterval(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_INTRA_FRAME_INTERVAL, Integer.valueOf(i));
            return this;
        }

        public Builder setAudioBitRate(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_BIT_RATE, Integer.valueOf(i));
            return this;
        }

        public Builder setAudioSampleRate(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_SAMPLE_RATE, Integer.valueOf(i));
            return this;
        }

        public Builder setAudioChannelCount(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_CHANNEL_COUNT, Integer.valueOf(i));
            return this;
        }

        public Builder setAudioRecordSource(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_RECORD_SOURCE, Integer.valueOf(i));
            return this;
        }

        public Builder setAudioMinBufferSize(int i) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_MIN_BUFFER_SIZE, Integer.valueOf(i));
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetClass(Class<VideoCapture> cls) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, cls);
            if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(cls.getCanonicalName() + Parameters.DEFAULT_OPTION_PREFIXES + UUID.randomUUID());
            }
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        public Builder setTargetName(String str) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, str);
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
            if (size != null) {
                getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(size.getWidth(), size.getHeight()));
            }
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        public Builder setDefaultResolution(Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, size);
            return null;
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
            getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
            return this;
        }
    }
}
