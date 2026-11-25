package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcg<E> extends zzbm<E> {
    static final zzcg<Object> zza = new zzcg<>(new Object[0], 0, null, 0, 0);
    final transient Object[] zzb;
    final transient Object[] zzc;
    private final transient int zzd;
    private final transient int zze;
    private final transient int zzf;

    zzcg(Object[] objArr, int i, Object[] objArr2, int i2, int i3) {
        this.zzb = objArr;
        this.zzc = objArr2;
        this.zzd = i2;
        this.zze = i;
        this.zzf = i3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@NullableDecl Object obj) {
        Object[] objArr = this.zzc;
        if (obj == null || objArr == null) {
            return false;
        }
        int iZzb = zzbd.zzb(obj);
        while (true) {
            int i = iZzb & this.zzd;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iZzb = i + 1;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zze;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm, com.google.android.gms.internal.mlkit_vision_text.zzbg, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return zzi().listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm, com.google.android.gms.internal.mlkit_vision_text.zzbg
    /* JADX INFO: renamed from: zza */
    public final zzcm<E> iterator() {
        return zzi().listIterator(0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final Object[] zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zzd() {
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbg
    final int zze(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzf);
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm
    final boolean zzh() {
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbm
    final zzbl<E> zzj() {
        return zzbl.zzi(this.zzb, this.zzf);
    }
}
