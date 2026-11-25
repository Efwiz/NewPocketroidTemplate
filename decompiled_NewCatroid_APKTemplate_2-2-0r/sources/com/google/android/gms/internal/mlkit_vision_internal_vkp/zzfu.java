package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import androidx.work.PeriodicWorkRequest;
import java.io.File;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfu {
    public static final zziu zza;
    private static final zzkz<String> zzb;

    static {
        zzir zzirVarZza = zziu.zza();
        zzirVarZza.zzb("");
        zza = zzirVarZza.zzt();
        zzb = zzkz.zzj("/m/0jbk");
    }

    public static com.google.android.libraries.vision.visionkit.pipeline.zzcg zza(com.google.android.libraries.vision.visionkit.pipeline.zzdm zzdmVar) {
        com.google.android.libraries.vision.visionkit.pipeline.zzcf zzcfVarZzc = com.google.android.libraries.vision.visionkit.pipeline.zzcg.zzc();
        zzcfVarZzc.zzc(zzdmVar);
        com.google.android.libraries.vision.visionkit.pipeline.zzfc zzfcVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzfd.zza();
        zzfcVarZza.zzb(2);
        zzcfVarZzc.zzd(zzfcVarZza);
        return zzcfVarZzc.zzt();
    }

    public static com.google.android.libraries.vision.visionkit.pipeline.zzcg zzb(com.google.android.libraries.vision.visionkit.pipeline.zzdm zzdmVar) {
        com.google.android.libraries.vision.visionkit.pipeline.zzcf zzcfVarZzc = com.google.android.libraries.vision.visionkit.pipeline.zzcg.zzc();
        zzcfVarZzc.zzc(zzdmVar);
        com.google.android.libraries.vision.visionkit.pipeline.zzfc zzfcVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzfd.zza();
        zzfcVarZza.zza(true);
        zzfcVarZza.zzb(1);
        zzcfVarZzc.zzd(zzfcVarZza);
        return zzcfVarZzc.zzt();
    }

    public static com.google.android.libraries.vision.visionkit.pipeline.zzdm zzc(Context context, boolean z, zzhl zzhlVar, zziu zziuVar) {
        zzaro zzaroVarZze = zzaro.zze();
        com.google.android.libraries.vision.visionkit.pipeline.zzdm zzdmVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzdp.zza();
        zzdmVarZza.zzb(zzl(zzn(context.getAssets(), zzhlVar), zziuVar, z, zzaroVarZze, 0));
        String strZzh = zzh(context);
        if (strZzh != null) {
            zzdmVarZza.zzd(strZzh);
            zzdmVarZza.zze(strZzh);
        }
        return zzdmVarZza;
    }

    public static com.google.android.libraries.vision.visionkit.pipeline.zzdm zzd(Context context, boolean z, zzhl zzhlVar, zziu zziuVar, long j, zzarq zzarqVar) {
        zzbaf zzbafVarZzn = zzn(context.getAssets(), zzhlVar);
        String strZzh = zzh(context);
        int iZzk = zzk(z);
        com.google.android.libraries.vision.visionkit.pipeline.zzdm zzdmVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzdp.zza();
        com.google.android.libraries.vision.visionkit.pipeline.zzdw zzdwVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzdx.zza();
        com.google.android.libraries.vision.visionkit.pipeline.zzcn zzcnVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzcq.zza();
        com.google.android.libraries.vision.visionkit.pipeline.zzco zzcoVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzcp.zza();
        zzcoVarZza.zza("MobileObjectLocalizerV3_1TfLiteClient");
        zzcoVarZza.zzb(PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS);
        zzcnVarZza.zza(zzcoVarZza);
        zzdwVarZza.zza(zzcnVarZza.zzt());
        zzdmVarZza.zzh(zzdwVarZza.zzt());
        if (strZzh != null) {
            zzdmVarZza.zzd(strZzh);
            zzdmVarZza.zze(strZzh);
        }
        if (zzarqVar != null) {
            zzdmVarZza.zzc(zzarqVar);
        }
        zzdmVarZza.zzf(true);
        zzdmVarZza.zzi(zzjv.zzc());
        com.google.android.libraries.vision.visionkit.pipeline.zzbw zzbwVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzby.zza();
        zzbwVarZza.zzc(false);
        zzbwVarZza.zzb(iZzk);
        zzbwVarZza.zzd(0.2f);
        zzbwVarZza.zza(0.0f);
        zzbwVarZza.zze(3);
        zzdmVarZza.zzg(zzbwVarZza);
        zzdmVarZza.zzb(zzl(zzbafVarZzn, zziuVar, z, zzaro.zze(), 0));
        return zzdmVarZza;
    }

    public static zziu zze(String str, String str2, float f, int i) {
        zzir zzirVarZza = zziu.zza();
        zzhh zzhhVarZza = zzhi.zza();
        zzhhVarZza.zzc(str);
        zzirVarZza.zze(zzhhVarZza);
        zzirVarZza.zzd(i);
        if (f >= 0.0f) {
            zzirVarZza.zzg(f);
        }
        if (!str2.isEmpty()) {
            zzhh zzhhVarZza2 = zzhi.zza();
            zzhhVarZza2.zzc(str2);
            zzirVarZza.zzh(zzhhVarZza2);
        }
        return zzirVarZza.zzt();
    }

    public static zziu zzf(Context context, zzhl zzhlVar, String str, float f, int i) throws IOException {
        zzir zzirVarZza = zziu.zza();
        zzhh zzhhVarZza = zzhi.zza();
        zzhhVarZza.zzb(zzhlVar);
        zzirVarZza.zze(zzhhVarZza);
        zzirVarZza.zzd(i);
        if (f >= 0.0f) {
            zzirVarZza.zzg(f);
        }
        if (!str.isEmpty()) {
            zzhh zzhhVarZza2 = zzhi.zza();
            zzhhVarZza2.zza(zzamk.zzu(context.getAssets().open(str)));
            zzirVarZza.zzh(zzhhVarZza2);
        }
        return zzirVarZza.zzt();
    }

    public static zziu zzg(zzhl zzhlVar) {
        zzaro zzaroVarZze = zzaro.zze();
        zzhh zzhhVarZza = zzhi.zza();
        zzhhVarZza.zzb(zzhlVar);
        zzhi zzhiVarZzt = zzhhVarZza.zzt();
        zzir zzirVarZza = zziu.zza();
        zzirVarZza.zza(zzb);
        zzirVarZza.zzc(zzaroVarZze);
        zzirVarZza.zzf(zzhiVarZzt);
        return zzirVarZza.zzt();
    }

    public static String zzh(Context context) {
        File file = new File(context.getCodeCacheDir(), "odt/v1");
        if (file.mkdirs() || file.exists()) {
            return file.toString();
        }
        String strValueOf = String.valueOf(file);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 39);
        sb.append("Unable to create accelerator directory ");
        sb.append(strValueOf);
        Log.e("MlKitObjectsConfigs", sb.toString());
        return null;
    }

    public static String zzi() {
        return "mlkit_odt_default_classifier/labeler_with_validation.tflite";
    }

    public static String zzj() {
        return "mlkit_odt_localizer/localizer_with_validation.tflite";
    }

    private static int zzk(boolean z) {
        return z ? 5 : 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static com.google.android.libraries.vision.visionkit.pipeline.zzao zzl(zzbaf zzbafVar, zziu zziuVar, boolean z, zzaro zzaroVar, int i) {
        com.google.android.libraries.vision.visionkit.pipeline.zzao zzaoVarZza = com.google.android.libraries.vision.visionkit.pipeline.zzap.zza();
        zzaoVarZza.zzd(true);
        zzbad zzbadVarZzc = zzbaj.zzc();
        zzbadVarZzc.zze("MobileSSDTfLiteClient");
        zzbadVarZzc.zza(true);
        zzbadVarZzc.zzc(zzbafVar);
        zzbadVarZzc.zzb(zzaroVar);
        zzaoVarZza.zzc((zzbaj) zzbadVarZzc.zzt());
        zzaoVarZza.zzb(zziuVar);
        zzhb zzhbVarZza = zzhc.zza();
        zzgy zzgyVarZza = zzgz.zza();
        zzgyVarZza.zza("/m/0bl9f");
        zzgyVarZza.zzb(0.46f);
        zzhbVarZza.zza(zzgyVarZza);
        zzaoVarZza.zza(zzhbVarZza);
        zzaoVarZza.zzh(!z);
        zzaoVarZza.zzf(zzk(z));
        zzaoVarZza.zze(0.6f);
        zzaoVarZza.zzg(0);
        return zzaoVarZza;
    }

    private static zzamk zzm(AssetManager assetManager, String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder(str2.length() + 20);
        sb.append("mlkit_odt_localizer/");
        sb.append(str2);
        return zzamk.zzu(assetManager.open(sb.toString()));
    }

    private static zzbaf zzn(AssetManager assetManager, zzhl zzhlVar) {
        try {
            zzbag zzbagVarZzc = zzbah.zzc();
            zzbagVarZzc.zza(zzhlVar.zza());
            zzbagVarZzc.zzc(zzhlVar.zzd());
            zzbagVarZzc.zzb(zzhlVar.zzc());
            zzbah zzbahVarZzt = zzbagVarZzc.zzt();
            zzbae zzbaeVarZzc = zzbaf.zzc();
            zzbaeVarZzc.zzc(zzbahVarZzt);
            zzbaeVarZzc.zzb(zzm(assetManager, "mlkit_odt_localizer", "mobile_object_localizer_labelmap"));
            zzbaeVarZzc.zza(zzm(assetManager, "mlkit_odt_localizer", "mobile_object_localizer_3_1_anchors.pb"));
            return zzbaeVarZzc.zzt();
        } catch (IOException e) {
            Log.e("MlKitObjectsConfigs", "Failed to create detector client options: ", e);
            return zzbaf.zzf();
        }
    }
}
