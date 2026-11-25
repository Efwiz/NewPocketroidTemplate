package com.google.android.gms.internal.mlkit_vision_text;

import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbt<F, T> extends AbstractSequentialList<T> implements Serializable {
    final List<F> zza;
    final zzu<? super F, ? extends T> zzb;

    zzbt(List<F> list, zzu<? super F, ? extends T> zzuVar) {
        list.getClass();
        this.zza = list;
        zzuVar.getClass();
        this.zzb = zzuVar;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public final ListIterator<T> listIterator(int i) {
        return new zzbs(this, this.zza.listIterator(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }
}
