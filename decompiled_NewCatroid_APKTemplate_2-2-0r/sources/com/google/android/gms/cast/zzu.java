package com.google.android.gms.cast;

import java.util.Collection;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzu {
    private String zza = "com.google.android.gms.cast.CATEGORY_CAST";
    private String zzb;
    private Collection zzc;

    private zzu() {
    }

    /* synthetic */ zzu(zzt zztVar) {
    }

    static /* synthetic */ zzu zza(zzu zzuVar, String str) {
        zzuVar.zzb = str;
        return zzuVar;
    }

    static /* synthetic */ zzu zzb(zzu zzuVar, String str) {
        zzuVar.zza = "com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK";
        return zzuVar;
    }

    static /* synthetic */ zzu zzc(zzu zzuVar, Collection collection) {
        zzuVar.zzc = collection;
        return zzuVar;
    }

    static /* bridge */ /* synthetic */ zzw zzd(zzu zzuVar) {
        return new zzw(zzuVar.zza, zzuVar.zzb, zzuVar.zzc, false, true, null);
    }
}
