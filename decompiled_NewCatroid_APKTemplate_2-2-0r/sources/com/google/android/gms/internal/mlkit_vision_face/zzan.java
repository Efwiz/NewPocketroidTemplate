package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
class zzan extends zzal implements List {
    final /* synthetic */ zzao zzf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzan(@NullableDecl zzao zzaoVar, Object obj, @NullableDecl List list, zzal zzalVar) {
        super(zzaoVar, obj, list, zzalVar);
        this.zzf = zzaoVar;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        zza();
        boolean zIsEmpty = this.zzb.isEmpty();
        ((List) this.zzb).add(i, obj);
        zzao.zzl(this.zzf);
        if (zIsEmpty) {
            zzc();
        }
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zAddAll = ((List) this.zzb).addAll(i, collection);
        if (!zAddAll) {
            return zAddAll;
        }
        zzao.zzm(this.zzf, this.zzb.size() - size);
        if (size != 0) {
            return zAddAll;
        }
        zzc();
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zza();
        return ((List) this.zzb).get(i);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        zza();
        return ((List) this.zzb).indexOf(obj);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        zza();
        return ((List) this.zzb).lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        zza();
        return new zzam(this);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        zza();
        Object objRemove = ((List) this.zzb).remove(i);
        zzao.zzk(this.zzf);
        zzb();
        return objRemove;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        zza();
        return ((List) this.zzb).set(i, obj);
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        zza();
        zzao zzaoVar = this.zzf;
        Object obj = this.zza;
        List listSubList = ((List) this.zzb).subList(i, i2);
        zzal zzalVar = this.zzc;
        if (zzalVar == null) {
            zzalVar = this;
        }
        return zzaoVar.zzg(obj, listSubList, zzalVar);
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        zza();
        return new zzam(this, i);
    }
}
