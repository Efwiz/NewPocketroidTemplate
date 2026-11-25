package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaj<K, V> extends zzu<K, V> {

    @NullableDecl
    private final K zza;
    private int zzb;
    private final /* synthetic */ zzaa zzc;

    zzaj(zzaa zzaaVar, int i) {
        this.zzc = zzaaVar;
        this.zza = (K) zzaaVar.zzb[i];
        this.zzb = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzu, java.util.Map.Entry
    @NullableDecl
    public final K getKey() {
        return this.zza;
    }

    private final void zza() {
        int i = this.zzb;
        if (i == -1 || i >= this.zzc.size() || !zze.zza(this.zza, this.zzc.zzb[this.zzb])) {
            this.zzb = this.zzc.zza(this.zza);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzu, java.util.Map.Entry
    @NullableDecl
    public final V getValue() {
        Map<K, V> mapZzb = this.zzc.zzb();
        if (mapZzb != null) {
            return mapZzb.get(this.zza);
        }
        zza();
        if (this.zzb == -1) {
            return null;
        }
        return (V) this.zzc.zzc[this.zzb];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzu, java.util.Map.Entry
    public final V setValue(V v) {
        Map<K, V> mapZzb = this.zzc.zzb();
        if (mapZzb != null) {
            return mapZzb.put(this.zza, v);
        }
        zza();
        if (this.zzb == -1) {
            this.zzc.put(this.zza, v);
            return null;
        }
        V v2 = (V) this.zzc.zzc[this.zzb];
        this.zzc.zzc[this.zzb] = v;
        return v2;
    }
}
