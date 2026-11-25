package com.google.android.gms.internal.mlkit_vision_text;

import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzam extends zzak implements ListIterator {
    final /* synthetic */ zzan zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzam(zzan zzanVar) {
        super(zzanVar);
        this.zzd = zzanVar;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        boolean zIsEmpty = this.zzd.isEmpty();
        zza();
        ((ListIterator) this.zza).add(obj);
        zzao.zzl(this.zzd.zzf);
        if (zIsEmpty) {
            this.zzd.zzc();
        }
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        zza();
        return ((ListIterator) this.zza).hasPrevious();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        zza();
        return ((ListIterator) this.zza).nextIndex();
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        zza();
        return ((ListIterator) this.zza).previous();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        zza();
        return ((ListIterator) this.zza).previousIndex();
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        zza();
        ((ListIterator) this.zza).set(obj);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzam(zzan zzanVar, int i) {
        super(zzanVar, ((List) zzanVar.zzb).listIterator(i));
        this.zzd = zzanVar;
    }
}
