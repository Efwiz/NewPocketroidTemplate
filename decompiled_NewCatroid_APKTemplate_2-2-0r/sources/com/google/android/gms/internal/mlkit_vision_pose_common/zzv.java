package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.List;
import java.util.ListIterator;

/* JADX INFO: Add missing generic type declarations: [V] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzv<V> extends zzt implements ListIterator<V> {
    private final /* synthetic */ zzs zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzv(zzs zzsVar) {
        super(zzsVar);
        this.zzb = zzsVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzv(zzs zzsVar, int i) {
        super(zzsVar, ((List) zzsVar.zzb).listIterator(i));
        this.zzb = zzsVar;
    }

    private final ListIterator<V> zzb() {
        zza();
        return (ListIterator) this.zza;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return zzb().hasPrevious();
    }

    @Override // java.util.ListIterator
    public final V previous() {
        return zzb().previous();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return zzb().nextIndex();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return zzb().previousIndex();
    }

    @Override // java.util.ListIterator
    public final void set(V v) {
        zzb().set(v);
    }

    @Override // java.util.ListIterator
    public final void add(V v) {
        boolean zIsEmpty = this.zzb.isEmpty();
        zzb().add(v);
        zzl.zzc(this.zzb.zzd);
        if (zIsEmpty) {
            this.zzb.zzc();
        }
    }
}
