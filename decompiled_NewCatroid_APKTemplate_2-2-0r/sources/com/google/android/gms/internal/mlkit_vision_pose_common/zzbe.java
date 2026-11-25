package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbe<E> extends zzar<E> {
    static final zzar<Object> zza = new zzbe(new Object[0], 0);
    private final transient Object[] zzb;
    private final transient int zzc;

    zzbe(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final int zzc() {
        return 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final Object[] zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final int zzd() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzar, com.google.android.gms.internal.mlkit_vision_pose_common.zzan
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzh.zza(i, this.zzc);
        return (E) this.zzb[i];
    }
}
