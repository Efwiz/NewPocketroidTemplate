package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaol extends zzalq<String> implements RandomAccess, zzaom {
    public static final zzaom zza;
    private static final zzaol zzb;
    private final List<Object> zzc;

    static {
        zzaol zzaolVar = new zzaol(10);
        zzb = zzaolVar;
        zzaolVar.zzb();
        zza = zzaolVar;
    }

    public zzaol() {
        this(10);
    }

    private static String zzj(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj instanceof zzamk ? ((zzamk) obj).zzw(zzaoc.zza) : zzaoc.zzh((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalq, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ void add(int i, Object obj) {
        zza();
        this.zzc.add(i, (String) obj);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalq, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends String> collection) {
        zza();
        if (collection instanceof zzaom) {
            collection = ((zzaom) collection).zzh();
        }
        boolean zAddAll = this.zzc.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalq, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zza();
        this.zzc.clear();
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalq, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object remove(int i) {
        zza();
        Object objRemove = this.zzc.remove(i);
        this.modCount++;
        return zzj(objRemove);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalq, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        zza();
        return zzj(this.zzc.set(i, (String) obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob
    public final /* bridge */ /* synthetic */ zzaob zzd(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzc);
        return new zzaol((ArrayList<Object>) arrayList);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final zzaom zze() {
        return zzc() ? new zzaqy(this) : this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final Object zzf(int i) {
        return this.zzc.get(i);
    }

    @Override // java.util.AbstractList, java.util.List
    /* JADX INFO: renamed from: zzg, reason: merged with bridge method [inline-methods] */
    public final String get(int i) {
        Object obj = this.zzc.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzamk) {
            zzamk zzamkVar = (zzamk) obj;
            String strZzw = zzamkVar.zzw(zzaoc.zza);
            if (zzamkVar.zzn()) {
                this.zzc.set(i, strZzw);
            }
            return strZzw;
        }
        byte[] bArr = (byte[]) obj;
        String strZzh = zzaoc.zzh(bArr);
        if (zzaoc.zzj(bArr)) {
            this.zzc.set(i, strZzh);
        }
        return strZzh;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final List<?> zzh() {
        return Collections.unmodifiableList(this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaom
    public final void zzi(zzamk zzamkVar) {
        zza();
        this.zzc.add(zzamkVar);
        this.modCount++;
    }

    public zzaol(int i) {
        this.zzc = new ArrayList(i);
    }

    private zzaol(ArrayList<Object> arrayList) {
        this.zzc = arrayList;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalq, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }
}
