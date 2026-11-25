package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzoh {
    private static zzog zza;

    public static synchronized zznw zza(zznq zznqVar) {
        if (zza == null) {
            zza = new zzog(null);
        }
        return zza.get(zznqVar);
    }

    public static synchronized zznw zzb(String str) {
        return zza(zznq.zzd(str).zzd());
    }
}
