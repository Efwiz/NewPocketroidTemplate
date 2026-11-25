package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzld {
    public static zzih zza(int i, int i2) {
        zzig zzigVar;
        zzif zzifVar = new zzif();
        if (i == -1) {
            zzigVar = zzig.BITMAP;
        } else if (i == 35) {
            zzigVar = zzig.YUV_420_888;
        } else if (i == 842094169) {
            zzigVar = zzig.YV12;
        } else if (i != 16) {
            zzigVar = i != 17 ? zzig.UNKNOWN_FORMAT : zzig.NV21;
        } else {
            zzigVar = zzig.NV16;
        }
        zzifVar.zza(zzigVar);
        zzifVar.zzb(Integer.valueOf(i2));
        return zzifVar.zzc();
    }
}
