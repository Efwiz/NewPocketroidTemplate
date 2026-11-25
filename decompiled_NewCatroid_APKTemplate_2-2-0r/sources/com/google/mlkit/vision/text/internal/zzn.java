package com.google.mlkit.vision.text.internal;

import android.content.Context;
import android.os.SystemClock;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.internal.mlkit_vision_text.zzeb;
import com.google.android.gms.internal.mlkit_vision_text.zzih;
import com.google.android.gms.internal.mlkit_vision_text.zzin;
import com.google.android.gms.internal.mlkit_vision_text.zzio;
import com.google.android.gms.internal.mlkit_vision_text.zziq;
import com.google.android.gms.internal.mlkit_vision_text.zzjr;
import com.google.android.gms.internal.mlkit_vision_text.zzjs;
import com.google.android.gms.internal.mlkit_vision_text.zzkm;
import com.google.android.gms.internal.mlkit_vision_text.zzko;
import com.google.android.gms.internal.mlkit_vision_text.zzkp;
import com.google.android.gms.internal.mlkit_vision_text.zzkz;
import com.google.android.gms.internal.mlkit_vision_text.zzla;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.MLTask;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageUtils;
import com.google.mlkit.vision.text.Text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzn extends MLTask<Text, InputImage> {
    static boolean zza = true;
    private static final ImageUtils zzb = ImageUtils.getInstance();
    private final zzj zzc;
    private final zzko zzd;

    public zzn(MlKitContext mlKitContext) {
        zzko zzkoVarZza = zzkz.zza("play-services-mlkit-text-recognition");
        Context applicationContext = mlKitContext.getApplicationContext();
        zzj zzbVar = GoogleApiAvailabilityLight.getInstance().getApkVersion(applicationContext) >= 204690000 ? new zzb(applicationContext) : new zzc(applicationContext);
        this.zzd = zzkoVarZza;
        this.zzc = zzbVar;
    }

    static final /* synthetic */ zzkp zzd(long j, zzin zzinVar, InputImage inputImage) {
        zzjr zzjrVar = new zzjr();
        zzih zzihVar = new zzih();
        zzihVar.zza(Long.valueOf(j));
        zzihVar.zzb(zzinVar);
        zzihVar.zzc(Boolean.valueOf(zza));
        zzihVar.zzd(true);
        zzihVar.zze(true);
        zzjrVar.zza(zzihVar.zzf());
        ImageUtils imageUtils = zzb;
        zzjrVar.zzb(zzla.zza(imageUtils.getMobileVisionImageFormat(inputImage), imageUtils.getMobileVisionImageSize(inputImage)));
        zzjs zzjsVarZzc = zzjrVar.zzc();
        zziq zziqVar = new zziq();
        zziqVar.zzc(false);
        zziqVar.zzd(zzjsVarZzc);
        return zzkp.zzc(zziqVar);
    }

    private final void zze(final zzin zzinVar, long j, final InputImage inputImage) {
        final long jElapsedRealtime = SystemClock.elapsedRealtime() - j;
        this.zzd.zza(new zzkm(jElapsedRealtime, zzinVar, inputImage) { // from class: com.google.mlkit.vision.text.internal.zzl
            private final long zza;
            private final zzin zzb;
            private final InputImage zzc;

            {
                this.zza = jElapsedRealtime;
                this.zzb = zzinVar;
                this.zzc = inputImage;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_text.zzkm
            public final zzkp zza() {
                return zzn.zzd(this.zza, this.zzb, this.zzc);
            }
        }, zzio.ON_DEVICE_TEXT_DETECT);
        zzeb zzebVar = new zzeb();
        zzebVar.zza(zzinVar);
        zzebVar.zzb(Boolean.valueOf(zza));
        ImageUtils imageUtils = zzb;
        zzebVar.zzc(zzla.zza(imageUtils.getMobileVisionImageFormat(inputImage), imageUtils.getMobileVisionImageSize(inputImage)));
        this.zzd.zzb(zzebVar.zzd(), jElapsedRealtime, zzio.AGGREGATED_ON_DEVICE_TEXT_DETECTION, zzm.zza);
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final synchronized void load() throws MlKitException {
        this.zzc.zza();
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final synchronized void release() {
        zza = true;
        this.zzc.zzc();
    }

    @Override // com.google.mlkit.common.sdkinternal.MLTask
    /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final synchronized Text run(InputImage inputImage) throws MlKitException {
        Text textZzb;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        try {
            textZzb = this.zzc.zzb(inputImage);
            zze(zzin.NO_ERROR, jElapsedRealtime, inputImage);
            zza = false;
        } catch (MlKitException e) {
            zze(e.getErrorCode() == 14 ? zzin.MODEL_NOT_DOWNLOADED : zzin.UNKNOWN_ERROR, jElapsedRealtime, inputImage);
            throw e;
        }
        return textZzb;
    }
}
