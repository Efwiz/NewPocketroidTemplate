package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.List;

/* JADX INFO: Add missing generic type declarations: [E] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzas<E> extends zzar<E> {
    private final transient int zza;
    private final transient int zzb;
    private final /* synthetic */ zzar zzc;

    zzas(zzar zzarVar, int i, int i2) {
        this.zzc = zzarVar;
        this.zza = i;
        this.zzb = i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final Object[] zzb() {
        return this.zzc.zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final int zzd() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzh.zza(i, this.zzb);
        return this.zzc.get(i + this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzar
    /* JADX INFO: renamed from: zza */
    public final zzar<E> subList(int i, int i2) {
        zzh.zza(i, i2, this.zzb);
        zzar zzarVar = this.zzc;
        int i3 = this.zza;
        return (zzar) zzarVar.subList(i + i3, i2 + i3);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzar, java.util.List
    public final /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }
}
