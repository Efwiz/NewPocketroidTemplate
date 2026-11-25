package com.google.android.gms.internal.mlkit_vision_face;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbk extends zzbl {
    final transient int zza;
    final transient int zzb;
    final /* synthetic */ zzbl zzc;

    zzbk(zzbl zzblVar, int i, int i2) {
        this.zzc = zzblVar;
        this.zza = i;
        this.zzb = i2;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzaa.zzb(i, this.zzb, "index");
        return this.zzc.get(i + this.zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbl, java.util.List
    public final /* bridge */ /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbg
    final Object[] zzb() {
        return this.zzc.zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbg
    final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbg
    final int zzd() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbl
    /* JADX INFO: renamed from: zzf */
    public final zzbl subList(int i, int i2) {
        zzaa.zzd(i, i2, this.zzb);
        zzbl zzblVar = this.zzc;
        int i3 = this.zza;
        return zzblVar.subList(i + i3, i2 + i3);
    }
}
