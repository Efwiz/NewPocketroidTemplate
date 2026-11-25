package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaoy<K, V> {
    private final zzaox<K, V> zza;
    private final K zzb;
    private final V zzc;

    private zzaoy(zzarj zzarjVar, K k, zzarj zzarjVar2, V v) {
        this.zza = new zzaox<>(zzarjVar, k, zzarjVar2, v);
        this.zzb = k;
        this.zzc = v;
    }

    static <K, V> int zzb(zzaox<K, V> zzaoxVar, K k, V v) {
        return zzanh.zza(zzaoxVar.zza, 1, k) + zzanh.zza(zzaoxVar.zzc, 2, v);
    }

    public static <K, V> zzaoy<K, V> zzd(zzarj zzarjVar, K k, zzarj zzarjVar2, V v) {
        return new zzaoy<>(zzarjVar, k, zzarjVar2, v);
    }

    static <K, V> void zze(zzamx zzamxVar, zzaox<K, V> zzaoxVar, K k, V v) throws IOException {
        zzanh.zzl(zzamxVar, zzaoxVar.zza, 1, k);
        zzanh.zzl(zzamxVar, zzaoxVar.zzc, 2, v);
    }

    public final int zza(int i, K k, V v) {
        int iZzL = zzamx.zzL(i);
        int iZzb = zzb(this.zza, k, v);
        return iZzL + zzamx.zzM(iZzb) + iZzb;
    }

    final zzaox<K, V> zzc() {
        return this.zza;
    }
}
