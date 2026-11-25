package com.google.android.libraries.vision.visionkit.pipeline;

import android.graphics.Bitmap;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
class NativePipelineImpl implements zzbt {
    private zzanc zza;
    private zzcd zzb;
    private zzci zzc;

    public NativePipelineImpl(zzcd zzcdVar, zzci zzciVar, zzanc zzancVar) {
        this.zzb = zzcdVar;
        this.zzc = zzciVar;
        this.zza = zzancVar;
    }

    public NativePipelineImpl(String str, zzcd zzcdVar, zzci zzciVar, zzanc zzancVar) {
        this(zzcdVar, zzciVar, zzancVar);
        System.loadLibrary("mlkitcommonpipeline");
    }

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native void close(long j, long j2, long j3, long j4);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native byte[] getAnalyticsLogs(long j);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native long initialize(byte[] bArr, long j, long j2, com.google.android.gms.internal.mlkit_vision_internal_vkp.zza zzaVar, long j3);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native long initializeFrameBufferReleaseCallback(long j);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native long initializeFrameManager();

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native long initializeResultsCallback();

    public void onReleaseAtTimestampUs(long j) {
        this.zzb.zzd(j);
    }

    public void onResult(byte[] bArr) {
        try {
            this.zzc.zze(zzda.zzd(bArr, this.zza));
        } catch (zzaoe e) {
            com.google.android.gms.internal.mlkit_vision_internal_vkp.zzcn.zza.zza(e, "Error in result from JNI layer", new Object[0]);
        }
    }

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native byte[] process(long j, long j2, long j3, byte[] bArr, int i, int i2, int i3, int i4);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native byte[] processBitmap(long j, long j2, Bitmap bitmap, int i, int i2, int i3, int i4);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native byte[] processYuvFrame(long j, long j2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native void start(long j) throws PipelineException;

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native boolean stop(long j);

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public native void waitUntilIdle(long j) throws PipelineException;

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzbt
    public final void zza() {
        this.zza = null;
        this.zzb = null;
        this.zzc = null;
    }
}
