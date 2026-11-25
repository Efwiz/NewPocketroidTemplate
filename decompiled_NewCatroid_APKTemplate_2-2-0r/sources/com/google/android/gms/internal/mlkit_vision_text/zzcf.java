package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcf<E> extends zzbl<E> {
    static final zzbl<Object> zza = new zzcf(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzcf(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzaa.zzb(i, this.zzc, "index");
        return (E) this.zzb[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final Object[] zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zzd() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbl, com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zze(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }
}
