package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzla {
    public static zzif zza(int i, int i2) {
        zzie zzieVar;
        zzid zzidVar = new zzid();
        if (i == -1) {
            zzieVar = zzie.BITMAP;
        } else if (i == 35) {
            zzieVar = zzie.YUV_420_888;
        } else if (i == 842094169) {
            zzieVar = zzie.YV12;
        } else if (i != 16) {
            zzieVar = i != 17 ? zzie.UNKNOWN_FORMAT : zzie.NV21;
        } else {
            zzieVar = zzie.NV16;
        }
        zzidVar.zza(zzieVar);
        zzidVar.zzb(Integer.valueOf(i2));
        return zzidVar.zzc();
    }
}
