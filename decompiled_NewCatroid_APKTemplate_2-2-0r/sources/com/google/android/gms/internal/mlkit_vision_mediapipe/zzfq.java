package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfq extends zzdo<String> implements zzfp, RandomAccess {
    private static final zzfq zza;
    private static final zzfp zzb;
    private final List<Object> zzc;

    public zzfq() {
        this(10);
    }

    public zzfq(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    private zzfq(ArrayList<Object> arrayList) {
        this.zzc = arrayList;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzc();
        if (collection instanceof zzfp) {
            collection = ((zzfp) collection).zzb();
        }
        boolean zAddAll = this.zzc.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zzc();
        this.zzc.clear();
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfp
    public final Object zzb(int i) {
        return this.zzc.get(i);
    }

    private static String zza(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzdu) {
            return ((zzdu) obj).zzb();
        }
        return zzfe.zzb((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfp
    public final List<?> zzb() {
        return Collections.unmodifiableList(this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfp
    public final zzfp b_() {
        return zza() ? new zzhu(this) : this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        zzc();
        return zza(this.zzc.set(i, (String) obj));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzc();
        Object objRemove = this.zzc.remove(i);
        this.modCount++;
        return zza(objRemove);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, com.google.android.gms.internal.mlkit_vision_mediapipe.zzff
    public final /* bridge */ /* synthetic */ boolean zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzc();
        this.zzc.add(i, (String) obj);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdo, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzff
    public final /* synthetic */ zzff zza(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzc);
        return new zzfq((ArrayList<Object>) arrayList);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzc.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzdu) {
            zzdu zzduVar = (zzdu) obj;
            String strZzb = zzduVar.zzb();
            if (zzduVar.zzc()) {
                this.zzc.set(i, strZzb);
            }
            return strZzb;
        }
        byte[] bArr = (byte[]) obj;
        String strZzb2 = zzfe.zzb(bArr);
        if (zzfe.zza(bArr)) {
            this.zzc.set(i, strZzb2);
        }
        return strZzb2;
    }

    static {
        zzfq zzfqVar = new zzfq();
        zza = zzfqVar;
        zzfqVar.c_();
        zzb = zzfqVar;
    }
}
