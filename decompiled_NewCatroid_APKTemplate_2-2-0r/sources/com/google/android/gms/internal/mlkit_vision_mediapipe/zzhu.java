package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhu extends AbstractList<String> implements zzfp, RandomAccess {
    private final zzfp zza;

    public zzhu(zzfp zzfpVar) {
        this.zza = zzfpVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfp
    public final zzfp b_() {
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfp
    public final Object zzb(int i) {
        return this.zza.zzb(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zzht(this, i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzhw(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfp
    public final List<?> zzb() {
        return this.zza.zzb();
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return (String) this.zza.get(i);
    }
}
