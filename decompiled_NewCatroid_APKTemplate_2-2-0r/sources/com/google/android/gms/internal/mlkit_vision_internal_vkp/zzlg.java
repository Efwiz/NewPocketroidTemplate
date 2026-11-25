package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzlg<E> extends zzlb<E> {
    final transient E zza;
    private transient int zzb;

    zzlg(E e) {
        e.getClass();
        this.zza = e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.equals(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.zzb;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.zza.hashCode();
        this.zzb = iHashCode;
        return iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return new zzlc(this.zza);
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

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zza(Object[] objArr, int i) {
        objArr[0] = this.zza;
        return 1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    /* JADX INFO: renamed from: zzd */
    public final zzlh<E> iterator() {
        return new zzlc(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb
    final zzkz<E> zzh() {
        return zzkz.zzj(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb
    final boolean zzk() {
        return this.zzb != 0;
    }

    zzlg(E e, int i) {
        this.zza = e;
        this.zzb = i;
    }
}
