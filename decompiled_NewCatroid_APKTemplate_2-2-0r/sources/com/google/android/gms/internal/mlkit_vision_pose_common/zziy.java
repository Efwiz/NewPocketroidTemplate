package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zziy extends AbstractList<String> implements zzgw, RandomAccess {
    private final zzgw zza;

    public zziy(zzgw zzgwVar) {
        this.zza = zzgwVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgw
    public final zzgw zze() {
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgw
    public final Object zza(int i) {
        return this.zza.zza(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgw
    public final void zza(zzez zzezVar) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zzix(this, i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzja(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgw
    public final List<?> zzd() {
        return this.zza.zzd();
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return (String) this.zza.get(i);
    }
}
