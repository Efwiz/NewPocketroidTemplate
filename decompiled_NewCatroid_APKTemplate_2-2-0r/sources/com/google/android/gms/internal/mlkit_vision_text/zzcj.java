package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcj<E> extends zzbm<E> {
    final transient E zza;
    private transient int zzb;

    zzcj(E e) {
        e.getClass();
        this.zza = e;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        return this.zza.equals(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.zzb;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.zza.hashCode();
        this.zzb = iHashCode;
        return iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm, com.google.android.gms.internal.mlkit_vision_text.zzbg, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return new zzbn(this.zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String string = this.zza.toString();
        StringBuilder sb = new StringBuilder(String.valueOf(string).length() + 2);
        sb.append('[');
        sb.append(string);
        sb.append(']');
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm, com.google.android.gms.internal.mlkit_vision_text.zzbg
    /* JADX INFO: renamed from: zza */
    public final zzcm<E> iterator() {
        return new zzbn(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zze(Object[] objArr, int i) {
        objArr[0] = this.zza;
        return 1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm
    final boolean zzh() {
        return this.zzb != 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm
    final zzbl<E> zzj() {
        return zzbl.zzg(this.zza);
    }

    zzcj(E e, int i) {
        this.zza = e;
        this.zzb = i;
    }
}
