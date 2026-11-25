package com.google.android.gms.internal.mlkit_vision_text;

import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzkg {
    public static zzkf zzf(String str) {
        zzkd zzkdVar = new zzkd();
        zzkdVar.zza("play-services-mlkit-text-recognition");
        zzkdVar.zzb(false);
        zzkdVar.zzc(true);
        zzkdVar.zzd(Priority.VERY_LOW);
        zzkdVar.zze(0);
        return zzkdVar;
    }

    public abstract String zza();

    public abstract boolean zzb();

    public abstract boolean zzc();

    public abstract Priority zzd();

    public abstract int zze();
}
