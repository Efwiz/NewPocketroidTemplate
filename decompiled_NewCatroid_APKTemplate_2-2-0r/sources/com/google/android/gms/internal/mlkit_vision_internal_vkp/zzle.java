package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzle<E> extends zzkz<E> {
    static final zzkz<Object> zza = new zzle(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzle(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzkk.zza(i, this.zzc, "index");
        E e = (E) this.zzb[i];
        e.getClass();
        return e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkz, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final Object[] zze() {
        return this.zzb;
    }
}
