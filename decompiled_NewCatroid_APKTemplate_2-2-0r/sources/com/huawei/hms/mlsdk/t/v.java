package com.huawei.hms.mlsdk.t;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import androidx.lifecycle.CoroutineLiveDataKt;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: AudioCodec.java */
/* JADX INFO: loaded from: classes10.dex */
public class v implements z {
    private y a;
    private z.a b;
    private volatile boolean c = false;
    private ByteBuffer d;
    private ByteArrayInputStream e;

    /* JADX INFO: compiled from: AudioCodec.java */
    private class b implements Runnable {
        /* synthetic */ b(a aVar) {
        }

        @Override // java.lang.Runnable
        public void run() {
            v.a(v.this);
        }
    }

    public void a(z.a aVar) {
        this.b = aVar;
    }

    public void b() {
        this.c = true;
    }

    public void a(y yVar) {
        this.d = ByteBuffer.allocate(4096);
        this.a = yVar;
        this.e = new ByteArrayInputStream(this.a.e());
    }

    public void a() {
        j0.b().a(new b(null));
    }

    static /* synthetic */ void a(v vVar) {
        if (vVar.a.i() || vVar.a.g() || vVar.a.j() || vVar.a.e().length == 0) {
            i0.c("AudioCodec", "Empty audio, return;");
            vVar.b.a(vVar.a);
            vVar.b.a(vVar.a.f());
            return;
        }
        if (vVar.a.e().length <= 324) {
            i0.c("AudioCodec", "invalidAudio, return;");
            vVar.b.a(vVar.a.f());
            return;
        }
        MediaExtractor mediaExtractor = new MediaExtractor();
        MediaFormat mediaFormat = null;
        try {
            mediaExtractor.setDataSource(new b0(vVar.a));
        } catch (IOException unused) {
            mediaExtractor = null;
        }
        if (mediaExtractor == null) {
            vVar.b.a(vVar.a.f(), 203, "Audio decode error: setDataSource err.");
            vVar.b.a(vVar.a.f());
            return;
        }
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
            if (trackFormat.getString("mime").startsWith("audio/")) {
                mediaExtractor.selectTrack(i);
                mediaFormat = trackFormat;
                break;
            }
        }
        if (mediaFormat == null) {
            mediaExtractor.release();
            vVar.b.a(vVar.a.f(), 203, "Audio decode error: MediaFormat err.");
            vVar.b.a(vVar.a.f());
            return;
        }
        MediaCodec mediaCodecA = vVar.a(mediaFormat);
        if (mediaCodecA == null) {
            mediaExtractor.release();
            vVar.b.a(vVar.a.f(), 203, "Audio decode error: createDecoder err.");
            vVar.b.a(vVar.a.f());
        } else {
            if (vVar.a(true, mediaCodecA, mediaExtractor)) {
                return;
            }
            vVar.b.a(vVar.a.f(), 203, "Audio decode error: createDecoder err.");
        }
    }

    private MediaCodec a(MediaFormat mediaFormat) {
        try {
            MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
            mediaCodecCreateDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
            mediaCodecCreateDecoderByType.start();
            return mediaCodecCreateDecoderByType;
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0072  */
    private boolean a(boolean z, MediaCodec mediaCodec, MediaExtractor mediaExtractor) {
        int iA;
        ByteBuffer[] byteBufferArr;
        long j;
        i0.c("AudioCodec", "A segment of audio starts to be decoded.");
        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
        ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int i = 0;
        ByteBuffer[] outputBuffers2 = outputBuffers;
        boolean z2 = false;
        boolean z3 = false;
        while (!z2) {
            if (z3) {
                byteBufferArr = outputBuffers2;
                j = 5000;
            } else {
                try {
                    try {
                        int iDequeueInputBuffer = mediaCodec.dequeueInputBuffer(CoroutineLiveDataKt.DEFAULT_TIMEOUT);
                        if (iDequeueInputBuffer >= 0) {
                            ByteBuffer byteBuffer = inputBuffers[iDequeueInputBuffer];
                            if (z) {
                                iA = mediaExtractor.readSampleData(byteBuffer, i);
                            } else {
                                iA = a(byteBuffer);
                            }
                            int i2 = iA;
                            if (i2 < 0) {
                                byteBufferArr = outputBuffers2;
                                j = 5000;
                                mediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
                                z3 = true;
                            } else {
                                byteBufferArr = outputBuffers2;
                                j = 5000;
                                if (z) {
                                    mediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, i2, mediaExtractor.getSampleTime(), 0);
                                    mediaExtractor.advance();
                                } else {
                                    mediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, i2, -1L, 0);
                                }
                            }
                        } else {
                            byteBufferArr = outputBuffers2;
                            j = 5000;
                        }
                    } catch (Throwable th) {
                        try {
                            mediaCodec.stop();
                            mediaCodec.release();
                        } catch (IllegalStateException e) {
                            i0.b("AudioCodec", com.huawei.hms.mlsdk.t.a.a("codec stop exception: ").append(e.getMessage()).toString());
                        }
                        if (z) {
                            mediaExtractor.release();
                        }
                        this.b.a(this.a.f());
                        throw th;
                    }
                } catch (IllegalStateException e2) {
                    i0.b("AudioCodec", "startDecode gets an exception: " + e2.getMessage());
                    try {
                        mediaCodec.stop();
                        mediaCodec.release();
                    } catch (IllegalStateException e3) {
                        i0.b("AudioCodec", com.huawei.hms.mlsdk.t.a.a("codec stop exception: ").append(e3.getMessage()).toString());
                    }
                    if (z) {
                        mediaExtractor.release();
                    }
                    this.b.a(this.a.f());
                    return false;
                }
            }
            int iDequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(bufferInfo, j);
            if (iDequeueOutputBuffer < 0) {
                if (iDequeueOutputBuffer == -3) {
                    outputBuffers2 = mediaCodec.getOutputBuffers();
                } else if (iDequeueOutputBuffer == -2) {
                    String str = "output format has changed to " + mediaCodec.getOutputFormat();
                }
                i = 0;
            } else if ((bufferInfo.flags & 2) != 0) {
                mediaCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
            } else {
                if (bufferInfo.size != 0) {
                    ByteBuffer byteBuffer2 = byteBufferArr[iDequeueOutputBuffer];
                    byteBuffer2.position(bufferInfo.offset);
                    byteBuffer2.limit(bufferInfo.offset + bufferInfo.size);
                    byte[] bArr = new byte[bufferInfo.size];
                    byteBuffer2.get(bArr);
                    if (this.c) {
                        try {
                            mediaCodec.stop();
                            mediaCodec.release();
                        } catch (IllegalStateException e4) {
                            i0.b("AudioCodec", com.huawei.hms.mlsdk.t.a.a("codec stop exception: ").append(e4.getMessage()).toString());
                        }
                        if (z) {
                            mediaExtractor.release();
                        }
                        this.b.a(this.a.f());
                        return true;
                    }
                    this.b.a(new y.b().a(this.a).a(bArr).a(2).a());
                }
                mediaCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    z2 = true;
                }
            }
            outputBuffers2 = byteBufferArr;
            i = 0;
        }
        try {
            mediaCodec.stop();
            mediaCodec.release();
        } catch (IllegalStateException e5) {
            i0.b("AudioCodec", com.huawei.hms.mlsdk.t.a.a("codec stop exception: ").append(e5.getMessage()).toString());
        }
        if (z) {
            mediaExtractor.release();
        }
        this.b.a(this.a.f());
        return true;
    }

    private int a(ByteBuffer byteBuffer) {
        int i;
        while (true) {
            ByteBuffer byteBuffer2 = this.d;
            if (byteBuffer2 == null) {
                i0.a("AudioCodec", "readSampleData mByteBuffer is null");
                break;
            }
            int iRemaining = byteBuffer2.remaining();
            if (iRemaining == 0) {
                break;
            }
            byte[] bArr = new byte[iRemaining];
            try {
                i = this.e.read(bArr);
            } catch (IOException e) {
                i0.b("AudioCodec", com.huawei.hms.mlsdk.t.a.a("read byte array input stream exception").append(e.getMessage()).toString());
                i = 0;
            }
            i0.a("AudioCodec", "readSampleData read from stream " + i);
            if (i > 0) {
                this.d.put(bArr, 0, i);
            } else if (i == -1) {
                break;
            }
        }
        ByteBuffer byteBuffer3 = this.d;
        if (byteBuffer3 == null) {
            i0.a("AudioCodec", "mByteBuffer is null");
            return -1;
        }
        if (byteBuffer3.position() == 0) {
            return -1;
        }
        i0.a("AudioCodec", com.huawei.hms.mlsdk.t.a.a("readSampleData outBuffer.remaining()=").append(byteBuffer.remaining()).toString());
        int iCapacity = this.d.capacity() - this.d.remaining();
        if (1620 <= iCapacity) {
            iCapacity = 1620;
        }
        byte[] bArr2 = new byte[iCapacity];
        com.huawei.hms.mlsdk.t.a.a("outBytes.length=").append(iCapacity).toString();
        this.d.flip();
        this.d.get(bArr2);
        byteBuffer.put(bArr2);
        this.d.compact();
        return iCapacity;
    }
}
