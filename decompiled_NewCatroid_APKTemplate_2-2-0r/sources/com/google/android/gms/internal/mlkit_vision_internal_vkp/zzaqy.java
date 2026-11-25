package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaqy extends AbstractList<String> implements RandomAccess, zzaom {
    private final zzaom zza;

    public zzaqy(zzaom zzaomVar) {
        this.zza = zzaomVar;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object get(int i) {
        return ((zzaol) this.zza).get(i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zzaqx(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zzaqw(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final zzaom zze() {
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final Object zzf(int i) {
        return this.zza.zzf(i);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final List<?> zzh() {
        return this.zza.zzh();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final void zzi(zzamk zzamkVar) {
        throw new UnsupportedOperationException();
    }
}
