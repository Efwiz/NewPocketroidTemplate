package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzce<E> extends zzbm<E> {
    final transient E zza;
    private transient int zzb;

    zzce(E e) {
        e.getClass();
        this.zza = e;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbg, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        return this.zza.equals(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbm, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.zzb;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.zza.hashCode();
        this.zzb = iHashCode;
        return iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbm, com.google.android.gms.internal.mlkit_vision_face.zzbg, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
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

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbm, com.google.android.gms.internal.mlkit_vision_face.zzbg
    /* JADX INFO: renamed from: zza */
    public final zzcg<E> iterator() {
        return new zzbn(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbg
    final int zze(Object[] objArr, int i) {
        objArr[0] = this.zza;
        return 1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbm
    final boolean zzh() {
        return this.zzb != 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbm
    final zzbl<E> zzj() {
        return zzbl.zzg(this.zza);
    }

    zzce(E e, int i) {
        this.zza = e;
        this.zzb = i;
    }
}
