package com.google.android.gms.internal.mlkit_vision_text;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
class zzal extends AbstractCollection {

    @NullableDecl
    final Object zza;
    Collection zzb;

    @NullableDecl
    final zzal zzc;

    @NullableDecl
    final Collection zzd;
    final /* synthetic */ zzao zze;

    zzal(@NullableDecl zzao zzaoVar, Object obj, @NullableDecl Collection collection, zzal zzalVar) {
        this.zze = zzaoVar;
        this.zza = obj;
        this.zzb = collection;
        this.zzc = zzalVar;
        this.zzd = zzalVar == null ? null : zzalVar.zzb;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        zza();
        boolean zIsEmpty = this.zzb.isEmpty();
        boolean zAdd = this.zzb.add(obj);
        if (!zAdd) {
            return zAdd;
        }
        zzao.zzl(this.zze);
        if (!zIsEmpty) {
            return zAdd;
        }
        zzc();
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zAddAll = this.zzb.addAll(collection);
        if (!zAddAll) {
            return zAddAll;
        }
        zzao.zzm(this.zze, this.zzb.size() - size);
        if (size != 0) {
            return zAddAll;
        }
        zzc();
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        int size = size();
        if (size == 0) {
            return;
        }
        this.zzb.clear();
        zzao.zzn(this.zze, size);
        zzb();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        zza();
        return this.zzb.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean containsAll(Collection<?> collection) {
        zza();
        return this.zzb.containsAll(collection);
    }

    @Override // java.util.Collection
    public final boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        zza();
        return this.zzb.equals(obj);
    }

    @Override // java.util.Collection
    public final int hashCode() {
        zza();
        return this.zzb.hashCode();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        zza();
        return new zzak(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        zza();
        boolean zRemove = this.zzb.remove(obj);
        if (zRemove) {
            zzao.zzk(this.zze);
            zzb();
        }
        return zRemove;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean removeAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zRemoveAll = this.zzb.removeAll(collection);
        if (zRemoveAll) {
            zzao.zzm(this.zze, this.zzb.size() - size);
            zzb();
        }
        return zRemoveAll;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        zza();
        return this.zzb.size();
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        zza();
        return this.zzb.toString();
    }

    final void zza() {
        Collection collection;
        zzal zzalVar = this.zzc;
        if (zzalVar != null) {
            zzalVar.zza();
            if (this.zzc.zzb != this.zzd) {
                throw new ConcurrentModificationException();
            }
        } else {
            if (!this.zzb.isEmpty() || (collection = (Collection) this.zze.zza.get(this.zza)) == null) {
                return;
            }
            this.zzb = collection;
        }
    }

    final void zzb() {
        zzal zzalVar = this.zzc;
        if (zzalVar != null) {
            zzalVar.zzb();
        } else if (this.zzb.isEmpty()) {
            this.zze.zza.remove(this.zza);
        }
    }

    final void zzc() {
        zzal zzalVar = this.zzc;
        if (zzalVar != null) {
            zzalVar.zzc();
        } else {
            this.zze.zza.put(this.zza, this.zzb);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean retainAll(Collection<?> collection) {
        collection.getClass();
        int size = size();
        boolean zRetainAll = this.zzb.retainAll(collection);
        if (zRetainAll) {
            zzao.zzm(this.zze, this.zzb.size() - size);
            zzb();
        }
        return zRetainAll;
    }
}
