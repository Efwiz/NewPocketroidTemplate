package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzvp extends AbstractSet<Map.Entry> {
    final /* synthetic */ zzvu zza;

    zzvp(zzvu zzvuVar) {
        this.zza = zzvuVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return (obj instanceof Map.Entry) && this.zza.zzb((Map.Entry) obj) != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry> iterator() {
        return new zzvo(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        zzvt zzvtVarZzb;
        if (!(obj instanceof Map.Entry) || (zzvtVarZzb = this.zza.zzb((Map.Entry) obj)) == null) {
            return false;
        }
        this.zza.zze(zzvtVarZzb, true);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.zzc;
    }
}
