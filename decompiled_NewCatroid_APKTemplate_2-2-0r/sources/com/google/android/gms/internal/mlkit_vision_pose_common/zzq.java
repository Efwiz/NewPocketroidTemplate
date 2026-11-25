package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
class zzq<V> extends AbstractCollection<V> {

    @NullableDecl
    final K zza;
    Collection<V> zzb;

    @NullableDecl
    final zzq zzc;
    final /* synthetic */ zzl zzd;

    @NullableDecl
    private final Collection<V> zze;

    zzq(@NullableDecl zzl zzlVar, K k, @NullableDecl Collection<V> collection, zzq zzqVar) {
        this.zzd = zzlVar;
        this.zza = k;
        this.zzb = collection;
        this.zzc = zzqVar;
        this.zze = zzqVar == null ? null : zzqVar.zzb;
    }

    final void zza() {
        Collection<V> collection;
        zzq zzqVar = this.zzc;
        if (zzqVar != null) {
            zzqVar.zza();
            if (this.zzc.zzb != this.zze) {
                throw new ConcurrentModificationException();
            }
        } else {
            if (!this.zzb.isEmpty() || (collection = (Collection) this.zzd.zza.get(this.zza)) == null) {
                return;
            }
            this.zzb = collection;
        }
    }

    final void zzb() {
        zzq<V> zzqVar = this;
        while (true) {
            zzq<V> zzqVar2 = zzqVar.zzc;
            if (zzqVar2 == null) {
                break;
            } else {
                zzqVar = zzqVar2;
            }
        }
        if (zzqVar.zzb.isEmpty()) {
            zzqVar.zzd.zza.remove(zzqVar.zza);
        }
    }

    final void zzc() {
        zzq<V> zzqVar = this;
        while (true) {
            zzq<V> zzqVar2 = zzqVar.zzc;
            if (zzqVar2 == null) {
                zzqVar.zzd.zza.put(zzqVar.zza, zzqVar.zzb);
                return;
            }
            zzqVar = zzqVar2;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        zza();
        return this.zzb.size();
    }

    @Override // java.util.Collection
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        zza();
        return this.zzb.equals(obj);
    }

    @Override // java.util.Collection
    public int hashCode() {
        zza();
        return this.zzb.hashCode();
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        zza();
        return this.zzb.toString();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<V> iterator() {
        zza();
        return new zzt(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean add(V v) {
        zza();
        boolean zIsEmpty = this.zzb.isEmpty();
        boolean zAdd = this.zzb.add(v);
        if (zAdd) {
            zzl.zzc(this.zzd);
            if (zIsEmpty) {
                zzc();
            }
        }
        return zAdd;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends V> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zAddAll = this.zzb.addAll(collection);
        if (zAddAll) {
            zzl.zza(this.zzd, this.zzb.size() - size);
            if (size == 0) {
                zzc();
            }
        }
        return zAddAll;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        zza();
        return this.zzb.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        zza();
        return this.zzb.containsAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        int size = size();
        if (size == 0) {
            return;
        }
        this.zzb.clear();
        zzl.zzb(this.zzd, size);
        zzb();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        zza();
        boolean zRemove = this.zzb.remove(obj);
        if (zRemove) {
            zzl.zzb(this.zzd);
            zzb();
        }
        return zRemove;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zRemoveAll = this.zzb.removeAll(collection);
        if (zRemoveAll) {
            zzl.zza(this.zzd, this.zzb.size() - size);
            zzb();
        }
        return zRemoveAll;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        zzh.zza(collection);
        int size = size();
        boolean zRetainAll = this.zzb.retainAll(collection);
        if (zRetainAll) {
            zzl.zza(this.zzd, this.zzb.size() - size);
            zzb();
        }
        return zRetainAll;
    }
}
