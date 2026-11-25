package com.google.android.libraries.vision.visionkit.pipeline;

import android.graphics.Bitmap;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzcc implements zzcd, zzci {
    protected final zzanc zza;
    private final zzbi<zzbh> zzb;
    private final zzbt zzc;
    private long zzd;
    private final long zze;
    private final long zzf;
    private final long zzg;

    public zzcc(zzcg zzcgVar, String str) {
        zzanc zzancVarZzb = zzanc.zzb();
        zzancVarZzb = zzancVarZzb == null ? zzanc.zza() : zzancVarZzb;
        if (zzcgVar.zzj()) {
            this.zzc = new zzbu(this);
        } else if (zzcgVar.zzi()) {
            this.zzc = new NativePipelineImpl(this, this, zzancVarZzb);
        } else {
            this.zzc = new NativePipelineImpl("mlkitcommonpipeline", this, this, zzancVarZzb);
        }
        if (zzcgVar.zzk()) {
            this.zzb = new zzbi<>(zzcgVar.zza());
        } else {
            this.zzb = new zzbi<>(10);
        }
        this.zza = zzancVarZzb;
        long jInitializeFrameManager = this.zzc.initializeFrameManager();
        this.zze = jInitializeFrameManager;
        long jInitializeFrameBufferReleaseCallback = this.zzc.initializeFrameBufferReleaseCallback(jInitializeFrameManager);
        this.zzf = jInitializeFrameBufferReleaseCallback;
        long jInitializeResultsCallback = this.zzc.initializeResultsCallback();
        this.zzg = jInitializeResultsCallback;
        this.zzd = this.zzc.initialize(zzcgVar.zzw(), jInitializeFrameBufferReleaseCallback, jInitializeResultsCallback, null, 0L);
    }

    public final zzkg<zzd> zza() {
        byte[] analyticsLogs = this.zzc.getAnalyticsLogs(this.zzd);
        if (analyticsLogs == null) {
            return zzkg.zzd();
        }
        try {
            return zzkg.zze(zzd.zze(analyticsLogs, zzanc.zza()));
        } catch (zzaoe e) {
            throw new IllegalStateException("Could not parse analytics logs", e);
        }
    }

    public final zzkg<zzda> zzb(zzbh zzbhVar) {
        byte[] bArrProcess;
        if (this.zzd == 0) {
            throw new IllegalStateException("Pipeline has been closed or was not initialized");
        }
        if (this.zzb.zzb(zzbhVar, zzbhVar.zza()) && (bArrProcess = this.zzc.process(this.zzd, this.zze, zzbhVar.zza(), zzbhVar.zzc(), zzbhVar.zzb().zzb(), zzbhVar.zzb().zza(), zzbhVar.zzd() - 1, zzbhVar.zze() - 1)) != null) {
            try {
                return zzkg.zze(zzda.zzd(bArrProcess, this.zza));
            } catch (zzaoe e) {
                throw new IllegalStateException("Could not parse results", e);
            }
        }
        return zzkg.zzd();
    }

    public final synchronized void zzc() {
        long j = this.zzd;
        if (j != 0) {
            this.zzc.stop(j);
            this.zzc.close(this.zzd, this.zze, this.zzf, this.zzg);
            this.zzd = 0L;
            this.zzc.zza();
        }
    }

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzcd
    public final void zzd(long j) {
        this.zzb.zza(j);
    }

    @Override // com.google.android.libraries.vision.visionkit.pipeline.zzci
    public final void zze(zzda zzdaVar) {
        com.google.android.gms.internal.mlkit_vision_internal_vkp.zzcn zzcnVar = com.google.android.gms.internal.mlkit_vision_internal_vkp.zzcn.zza;
        String strValueOf = String.valueOf(zzdaVar);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 27);
        sb.append("Pipeline received results: ");
        sb.append(strValueOf);
        zzcnVar.zzb(this, sb.toString(), new Object[0]);
    }

    public final void zzg() {
        long j = this.zzd;
        if (j == 0) {
            throw new IllegalStateException("Pipeline has been closed or was not initialized");
        }
        if (!this.zzc.stop(j)) {
            throw new IllegalStateException("Pipeline did not stop successfully.");
        }
    }

    public final void zzf() throws PipelineException {
        long j = this.zzd;
        if (j == 0) {
            throw new PipelineException(zzch.FAILED_PRECONDITION.ordinal(), "Pipeline has been closed or was not initialized");
        }
        try {
            this.zzc.start(j);
            this.zzc.waitUntilIdle(this.zzd);
        } catch (PipelineException e) {
            this.zzc.stop(this.zzd);
            throw e;
        }
    }

    public final zzkg<zzda> zzh(long j, Bitmap bitmap, int i) {
        if (this.zzd == 0) {
            throw new IllegalStateException("Pipeline has been closed or was not initialized");
        }
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            String strValueOf = String.valueOf(bitmap.getConfig());
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 26);
            sb.append("Unsupported bitmap config ");
            sb.append(strValueOf);
            throw new IllegalArgumentException(sb.toString());
        }
        byte[] bArrProcessBitmap = this.zzc.processBitmap(this.zzd, j, bitmap, bitmap.getWidth(), bitmap.getHeight(), 0, i - 1);
        if (bArrProcessBitmap == null) {
            return zzkg.zzd();
        }
        try {
            return zzkg.zze(zzda.zzd(bArrProcessBitmap, this.zza));
        } catch (zzaoe e) {
            throw new IllegalStateException("Could not parse results", e);
        }
    }

    public final zzkg<zzda> zzi(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.zzd == 0) {
            throw new IllegalStateException("Pipeline has been closed or was not initialized");
        }
        if (!byteBuffer.isDirect() || !byteBuffer2.isDirect() || !byteBuffer3.isDirect()) {
            throw new IllegalStateException("Byte buffers are not direct.");
        }
        byte[] bArrProcessYuvFrame = this.zzc.processYuvFrame(this.zzd, j, byteBuffer, byteBuffer2, byteBuffer3, i, i2, i3, i4, i5, i6 - 1);
        if (bArrProcessYuvFrame == null) {
            return zzkg.zzd();
        }
        try {
            return zzkg.zze(zzda.zzd(bArrProcessYuvFrame, this.zza));
        } catch (zzaoe e) {
            throw new IllegalStateException("Could not parse results", e);
        }
    }
}
