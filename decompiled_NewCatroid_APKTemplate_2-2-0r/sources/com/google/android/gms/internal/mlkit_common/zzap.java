package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzap<E> extends zzan<E> {
    static final zzan<Object> zza = new zzap(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzap(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzad.zza(i, this.zzc, "index");
        E e = (E) this.zzb[i];
        e.getClass();
        return e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzan, com.google.android.gms.internal.mlkit_common.zzaj
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzaj
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzaj
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzaj
    final Object[] zze() {
        return this.zzb;
    }
}
