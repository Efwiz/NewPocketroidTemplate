package com.google.android.gms.internal.mlkit_vision_face;

import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlc {
    private static zzlb zza;
    private static final zzbm<String> zzb = zzbm.zzf(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "vision-common", "play-services-mlkit-barcode-scanning", "barcode-scanning", "play-services-mlkit-face-detection", "face-detection", "play-services-mlkit-image-labeling", "play-services-mlkit-text-recognition");

    public static synchronized zzkq zza(String str) {
        zzkh zzkhVarZzf;
        zzkhVarZzf = zzki.zzf(str);
        zzkhVarZzf.zzb(zzb.contains(str));
        return zzb(zzkhVarZzf.zzf());
    }

    public static synchronized zzkq zzb(zzki zzkiVar) {
        if (zza == null) {
            zza = new zzlb(null);
        }
        return zza.get(zzkiVar);
    }
}
