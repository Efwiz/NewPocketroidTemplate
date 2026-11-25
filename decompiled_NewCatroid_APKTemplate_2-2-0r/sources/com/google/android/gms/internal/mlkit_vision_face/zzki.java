package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzki {
    public static zzkh zzf(String str) {
        zzkf zzkfVar = new zzkf();
        zzkfVar.zza(str);
        zzkfVar.zzb(false);
        zzkfVar.zzc(true);
        zzkfVar.zzd(Priority.VERY_LOW);
        zzkfVar.zze(1);
        return zzkfVar;
    }

    public abstract String zza();

    public abstract boolean zzb();

    public abstract boolean zzc();

    public abstract Priority zzd();

    public abstract int zze();
}
