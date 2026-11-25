package com.google.android.gms.internal.mlkit_vision_text;

import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkz {
    private static zzky zza;
    private static final zzbm<String> zzb = zzbm.zzf(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "vision-common", "play-services-mlkit-barcode-scanning", "barcode-scanning", "play-services-mlkit-face-detection", "face-detection", "play-services-mlkit-image-labeling", "play-services-mlkit-text-recognition");

    public static synchronized zzko zza(String str) {
        zzkf zzkfVarZzf;
        zzkfVarZzf = zzkg.zzf("play-services-mlkit-text-recognition");
        zzkfVarZzf.zzb(zzb.contains("play-services-mlkit-text-recognition"));
        return zzb(zzkfVarZzf.zzf());
    }

    public static synchronized zzko zzb(zzkg zzkgVar) {
        if (zza == null) {
            zza = new zzky(null);
        }
        return zza.get(zzkgVar);
    }
}
