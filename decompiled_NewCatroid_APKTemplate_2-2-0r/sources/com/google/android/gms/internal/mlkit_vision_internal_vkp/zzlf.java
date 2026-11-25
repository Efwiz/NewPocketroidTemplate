package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzlf<E> extends zzlb<E> {
    static final zzlf<Object> zza;
    private static final Object[] zzd;
    final transient Object[] zzb;
    final transient Object[] zzc;
    private final transient int zze;
    private final transient int zzf;
    private final transient int zzg;

    static {
        Object[] objArr = new Object[0];
        zzd = objArr;
        zza = new zzlf<>(objArr, 0, objArr, 0, 0);
    }

    zzlf(Object[] objArr, int i, Object[] objArr2, int i2, int i3) {
        this.zzb = objArr;
        this.zze = i;
        this.zzc = objArr2;
        this.zzf = i2;
        this.zzg = i3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@CheckForNull Object obj) {
        Object[] objArr = this.zzc;
        if (obj == null || objArr.length == 0) {
            return false;
        }
        int iZza = zzks.zza(obj.hashCode());
        while (true) {
            int i = iZza & this.zzf;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iZza = i + 1;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zze;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return zzg().listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzg);
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zzb() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    /* JADX INFO: renamed from: zzd */
    public final zzlh<E> iterator() {
        return zzg().listIterator(0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkv
    final Object[] zze() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb
    final zzkz<E> zzh() {
        return zzkz.zzh(this.zzb, this.zzg);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlb
    final boolean zzk() {
        return true;
    }
}
