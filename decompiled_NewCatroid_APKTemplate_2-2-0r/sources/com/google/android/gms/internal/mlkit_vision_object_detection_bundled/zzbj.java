package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbj<E> extends zzau<E> {
    static final zzau<Object> zza = new zzbj(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzbj(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzh.zza(i, this.zzc, "index");
        E e = (E) this.zzb[i];
        e.getClass();
        return e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzau, com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    final Object[] zze() {
        return this.zzb;
    }
}
