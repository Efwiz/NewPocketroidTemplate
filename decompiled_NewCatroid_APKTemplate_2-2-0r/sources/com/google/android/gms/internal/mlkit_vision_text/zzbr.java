package com.google.android.gms.internal.mlkit_vision_text;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbr<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
    final List<F> zza;
    final zzu<? super F, ? extends T> zzb;

    zzbr(List<F> list, zzu<? super F, ? extends T> zzuVar) {
        list.getClass();
        this.zza = list;
        zzuVar.getClass();
        this.zzb = zzuVar;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractList, java.util.List
    public final T get(int i) {
        return this.zzb.zza(this.zza.get(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.zza.isEmpty();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<T> iterator() {
        return listIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<T> listIterator(int i) {
        return new zzbq(this, this.zza.listIterator(i));
    }

    @Override // java.util.AbstractList, java.util.List
    public final T remove(int i) {
        return this.zzb.zza(this.zza.remove(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }
}
