package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Date;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzom {
    private final Date zza;
    private final zzok zzb;
    private final String zzc;

    private zzom(Date date, int i, zzok zzokVar, String str) {
        this.zza = date;
        this.zzb = zzokVar;
        this.zzc = str;
    }

    public static zzom zzb(Date date) {
        return new zzom(date, 1, null, null);
    }

    public static zzom zzc(zzok zzokVar, String str) {
        return new zzom(zzokVar.zzc(), 0, zzokVar, str);
    }

    public final zzok zza() {
        return this.zzb;
    }
}
