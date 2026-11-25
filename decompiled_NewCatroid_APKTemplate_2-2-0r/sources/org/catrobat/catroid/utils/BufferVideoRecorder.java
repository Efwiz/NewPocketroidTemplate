package org.catrobat.catroid.utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.view.Surface;
import com.badlogic.gdx.utils.ScreenUtils;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.catrobat.catroid.content.RenderTexture;
import org.catrobat.catroid.content.RenderTextureManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/BufferVideoRecorder.class
  classes14.dex
 */
/* JADX INFO: compiled from: BufferVideoRecorder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\rH\u0002J\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010!\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\b\u0010%\u001a\u00020\u001bH\u0002J\b\u0010&\u001a\u00020\u001bH\u0002J&\u0010'\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u0006J\u0006\u0010,\u001a\u00020\u001bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/utils/BufferVideoRecorder;", "", "()V", "activeBufferName", "", "bitrate", "", "fps", "frameCount", "frameIntervalNs", "", "frameQueue", "Ljava/util/concurrent/LinkedBlockingQueue;", "", "height", "isEncodingFinished", "", "isMuxerStarted", "isRecording", "lastFrameTimeNs", "mediaCodec", "Landroid/media/MediaCodec;", "mediaMuxer", "Landroid/media/MediaMuxer;", "videoTrackIndex", "width", "convertRGBAToYUV420SP", "", "rgba", "yuv", "drainEncoder", "bufferInfo", "Landroid/media/MediaCodec$BufferInfo;", "onFrameRendered", "bufferName", "target", "Lorg/catrobat/catroid/content/RenderTexture;", "releaseResources", "runEncoderLoop", "startRecording", "file", "Ljava/io/File;", "targetFps", "targetBitrate", "stopRecordingAndWait", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BufferVideoRecorder {
    private static String activeBufferName;
    private static int frameCount;
    private static long frameIntervalNs;
    private static int height;
    private static boolean isMuxerStarted;
    private static volatile boolean isRecording;
    private static long lastFrameTimeNs;
    private static MediaCodec mediaCodec;
    private static MediaMuxer mediaMuxer;
    private static int width;
    public static final BufferVideoRecorder INSTANCE = new BufferVideoRecorder();
    private static int fps = 30;
    private static int bitrate = 2000000;
    private static int videoTrackIndex = -1;
    private static final LinkedBlockingQueue<byte[]> frameQueue = new LinkedBlockingQueue<>();
    private static volatile boolean isEncodingFinished = true;

    private BufferVideoRecorder() {
    }

    public final void startRecording(String bufferName, File file, int targetFps, int targetBitrate) {
        RenderTexture renderTexture;
        Intrinsics.checkNotNullParameter(bufferName, "bufferName");
        Intrinsics.checkNotNullParameter(file, "file");
        if (isRecording || (renderTexture = RenderTextureManager.INSTANCE.getRenderTextures().get(bufferName)) == null) {
            return;
        }
        isRecording = true;
        isEncodingFinished = false;
        activeBufferName = bufferName;
        width = renderTexture.getWidth();
        height = renderTexture.getHeight();
        fps = targetFps;
        bitrate = targetBitrate;
        frameIntervalNs = 1000000000 / ((long) targetFps);
        lastFrameTimeNs = 0L;
        frameCount = 0;
        frameQueue.clear();
        try {
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat("video/avc", width, height);
            mediaFormatCreateVideoFormat.setInteger("color-format", 21);
            mediaFormatCreateVideoFormat.setInteger("bitrate", bitrate);
            mediaFormatCreateVideoFormat.setInteger("frame-rate", fps);
            mediaFormatCreateVideoFormat.setInteger("i-frame-interval", 1);
            Intrinsics.checkNotNullExpressionValue(mediaFormatCreateVideoFormat, "apply(...)");
            MediaCodec mediaCodecCreateEncoderByType = MediaCodec.createEncoderByType("video/avc");
            mediaCodecCreateEncoderByType.configure(mediaFormatCreateVideoFormat, (Surface) null, (MediaCrypto) null, 1);
            mediaCodecCreateEncoderByType.start();
            mediaCodec = mediaCodecCreateEncoderByType;
            mediaMuxer = new MediaMuxer(file.getAbsolutePath(), 0);
            ThreadsKt.thread$default(true, false, null, "NewCatroidVideoEncoder", 0, new Function0<Unit>() { // from class: org.catrobat.catroid.utils.BufferVideoRecorder.startRecording.2
                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    BufferVideoRecorder.INSTANCE.runEncoderLoop();
                }
            }, 22, null);
        } catch (Exception e) {
            e.printStackTrace();
            releaseResources();
            isEncodingFinished = true;
            isRecording = false;
        }
    }

    public final void onFrameRendered(String bufferName, RenderTexture target) {
        Intrinsics.checkNotNullParameter(bufferName, "bufferName");
        Intrinsics.checkNotNullParameter(target, "target");
        if (isRecording && Intrinsics.areEqual(activeBufferName, bufferName)) {
            long jNanoTime = System.nanoTime();
            long j = lastFrameTimeNs;
            if (j == 0 || jNanoTime - j >= frameIntervalNs) {
                lastFrameTimeNs = jNanoTime;
                target.getFbo().begin();
                byte[] frameBufferPixels = ScreenUtils.getFrameBufferPixels(0, 0, target.getWidth(), target.getHeight(), true);
                target.getFbo().end();
                frameQueue.offer(frameBufferPixels);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void runEncoderLoop() {
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int i = ((width * height) * 3) / 2;
        byte[] bArr = new byte[i];
        while (true) {
            try {
                try {
                    if (!isRecording && frameQueue.isEmpty()) {
                        break;
                    }
                    byte[] bArrPoll = frameQueue.poll(50L, TimeUnit.MILLISECONDS);
                    if (bArrPoll != null) {
                        convertRGBAToYUV420SP(bArrPoll, width, height, bArr);
                        MediaCodec mediaCodec2 = mediaCodec;
                        if (mediaCodec2 == null) {
                            break;
                        }
                        int iDequeueInputBuffer = mediaCodec2.dequeueInputBuffer(10000L);
                        if (iDequeueInputBuffer >= 0) {
                            ByteBuffer inputBuffer = mediaCodec2.getInputBuffer(iDequeueInputBuffer);
                            if (inputBuffer != null) {
                                inputBuffer.clear();
                                inputBuffer.put(bArr);
                                mediaCodec2.queueInputBuffer(iDequeueInputBuffer, 0, i, (((long) frameCount) * PackingOptions.SEGMENT_LIMIT) / ((long) fps), 0);
                                frameCount++;
                            }
                        }
                        drainEncoder(bufferInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                releaseResources();
                isEncodingFinished = true;
                throw th;
            }
        }
        MediaCodec mediaCodec3 = mediaCodec;
        if (mediaCodec3 != null) {
            int iDequeueInputBuffer2 = mediaCodec3.dequeueInputBuffer(10000L);
            if (iDequeueInputBuffer2 >= 0) {
                mediaCodec3.queueInputBuffer(iDequeueInputBuffer2, 0, 0, 0L, 4);
            }
            drainEncoder(bufferInfo);
        }
        releaseResources();
        isEncodingFinished = true;
    }

    private final void drainEncoder(MediaCodec.BufferInfo bufferInfo) {
        MediaMuxer mediaMuxer2;
        ByteBuffer outputBuffer;
        MediaCodec mediaCodec2 = mediaCodec;
        if (mediaCodec2 == null || (mediaMuxer2 = mediaMuxer) == null) {
            return;
        }
        while (true) {
            int iDequeueOutputBuffer = mediaCodec2.dequeueOutputBuffer(bufferInfo, 10000L);
            if (iDequeueOutputBuffer == -1) {
                return;
            }
            if (iDequeueOutputBuffer == -2) {
                if (isMuxerStarted) {
                    throw new RuntimeException("Format changed twice");
                }
                MediaFormat outputFormat = mediaCodec2.getOutputFormat();
                Intrinsics.checkNotNullExpressionValue(outputFormat, "getOutputFormat(...)");
                videoTrackIndex = mediaMuxer2.addTrack(outputFormat);
                mediaMuxer2.start();
                isMuxerStarted = true;
            } else if (iDequeueOutputBuffer >= 0 && (outputBuffer = mediaCodec2.getOutputBuffer(iDequeueOutputBuffer)) != null) {
                if ((bufferInfo.flags & 2) != 0) {
                    bufferInfo.size = 0;
                }
                if (bufferInfo.size != 0) {
                    if (!isMuxerStarted) {
                        throw new RuntimeException("Muxer not started");
                    }
                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    mediaMuxer2.writeSampleData(videoTrackIndex, outputBuffer, bufferInfo);
                }
                mediaCodec2.releaseOutputBuffer(iDequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    return;
                }
            }
        }
    }

    public final void stopRecordingAndWait() {
        if (isRecording) {
            isRecording = false;
            while (!isEncodingFinished) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                    return;
                }
            }
        }
    }

    public final boolean isEncodingFinished() {
        return isEncodingFinished;
    }

    private final void releaseResources() {
        MediaMuxer mediaMuxer2;
        try {
            MediaCodec mediaCodec2 = mediaCodec;
            if (mediaCodec2 != null) {
                mediaCodec2.stop();
            }
            MediaCodec mediaCodec3 = mediaCodec;
            if (mediaCodec3 != null) {
                mediaCodec3.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaCodec = null;
        try {
            if (isMuxerStarted && (mediaMuxer2 = mediaMuxer) != null) {
                mediaMuxer2.stop();
            }
            MediaMuxer mediaMuxer3 = mediaMuxer;
            if (mediaMuxer3 != null) {
                mediaMuxer3.release();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        mediaMuxer = null;
        isMuxerStarted = false;
        videoTrackIndex = -1;
        activeBufferName = null;
    }

    private final void convertRGBAToYUV420SP(byte[] rgba, int width2, int height2, byte[] yuv) {
        int i = width2 * height2;
        int i2 = 0;
        for (int i3 = 0; i3 < height2; i3++) {
            int i4 = 0;
            while (i4 < width2) {
                int i5 = ((i3 * width2) + i4) * 4;
                int i6 = rgba[i5] & 255;
                int i7 = rgba[i5 + 1] & 255;
                int i8 = rgba[i5 + 2] & 255;
                int i9 = (((((i6 * 66) + (i7 * 129)) + (i8 * 25)) + 128) >> 8) + 16;
                int i10 = (((((i6 * (-38)) - (i7 * 74)) + (i8 * 112)) + 128) >> 8) + 128;
                int i11 = (((((i6 * 112) - (i7 * 94)) - (i8 * 18)) + 128) >> 8) + 128;
                int i12 = i2 + 1;
                yuv[i2] = (byte) RangesKt.coerceIn(i9, 0, 255);
                if (i3 % 2 == 0 && i4 % 2 == 0) {
                    int i13 = i + 1;
                    yuv[i] = (byte) RangesKt.coerceIn(i10, 0, 255);
                    i += 2;
                    yuv[i13] = (byte) RangesKt.coerceIn(i11, 0, 255);
                }
                i4++;
                i2 = i12;
            }
        }
    }
}
