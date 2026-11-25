package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzft {
    public static com.google.android.libraries.vision.visionkit.pipeline.zzcg zza(String str, float f, int i, String str2) {
        zzhh zzhhVarZza;
        zzhh zzhhVarZza2 = zzhi.zza();
        zzhhVarZza2.zzc(str);
        if (str2.isEmpty()) {
            zzhhVarZza = null;
        } else {
            zzhhVarZza = zzhi.zza();
            zzhhVarZza.zzc(str2);
        }
        return zzd(zzhhVarZza2, f, i, zzhhVarZza);
    }

    public static com.google.android.libraries.vision.visionkit.pipeline.zzcg zzb(zzhl zzhlVar, float f, int i, String str, Context context) throws IOException {
        zzhh zzhhVarZza;
        zzhh zzhhVarZza2 = zzhi.zza();
        zzhhVarZza2.zzb(zzhlVar);
        if (str.isEmpty()) {
            zzhhVarZza = null;
        } else {
            zzhhVarZza = zzhi.zza();
            zzhhVarZza.zza(zzamk.zzu(context.getAssets().open(str)));
        }
        return zzd(zzhhVarZza2, f, i, zzhhVarZza);
    }

    public static com.google.android.libraries.vision.visionkit.pipeline.zzcg zzc(Context context, float f, int i) throws IOException {
        zzhh zzhhVarZza = zzhi.zza();
        zzhhVarZza.zza(zzamk.zzu(context.getAssets().open("mlkit_label_default_model/mobile_ica_8bit_with_metadata_tflite")));
        return zzd(zzhhVarZza, f, i, null);
    }

    private static com.google.android.libraries.vision.visionkit.pipeline.zzcg zzd(zzhh zzhhVar, float f, int i, zzhh zzhhVar2) {
        zzir zzirVarZza = zziu.zza();
        zzirVarZza.zze(zzhhVar);
        zzirVarZza.zzd(i);
        if (f >= 0.0f) {
            zzirVarZza.zzg(f);
        }
        if (zzhhVar2 != null) {
            zzirVarZza.zzh(zzhhVar2);
        }
        com.google.android.libraries.vision.visionkit.pipeline.zzcf zzcfVarZzc = com.google.android.libraries.vision.visionkit.pipeline.zzcg.zzc();
        com.google.android.libraries.vision.visionkit.pipeline.zzdm zzdmVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzdp.zza();
        zzdmVarZza.zza(zzirVarZza);
        zzdmVarZza.zzf(true);
        zzcfVarZzc.zzc(zzdmVarZza);
        com.google.android.libraries.vision.visionkit.pipeline.zzfc zzfcVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzfd.zza();
        zzfcVarZza.zzb(2);
        zzcfVarZzc.zzd(zzfcVarZza);
        com.google.android.libraries.vision.visionkit.pipeline.zzi zziVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzj.zza();
        zziVarZza.zza(2);
        zzcfVarZzc.zzb(zziVarZza);
        return zzcfVarZzc.zzt();
    }
}
