package com.google.android.gms.internal.mlkit_vision_common;

import java.util.List;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzn extends zzo {
    final transient int zza;
    final transient int zzb;
    final /* synthetic */ zzo zzc;

    zzn(zzo zzoVar, int i, int i2) {
        this.zzc = zzoVar;
        this.zza = i;
        this.zzb = i2;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzf.zza(i, this.zzb, "index");
        return this.zzc.get(i + this.zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzo, java.util.List
    public final /* bridge */ /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzk
    final int zzb() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzk
    final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzk
    @CheckForNull
    final Object[] zze() {
        return this.zzc.zze();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzo
    /* JADX INFO: renamed from: zzf */
    public final zzo subList(int i, int i2) {
        zzf.zzc(i, i2, this.zzb);
        zzo zzoVar = this.zzc;
        int i3 = this.zza;
        return zzoVar.subList(i + i3, i2 + i3);
    }
}
