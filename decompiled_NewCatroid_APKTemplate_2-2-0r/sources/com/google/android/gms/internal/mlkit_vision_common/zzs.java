package com.google.android.gms.internal.mlkit_vision_common;

import java.lang.annotation.Annotation;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzs implements zzx {
    private final int zza;
    private final zzw zzb;

    zzs(int i, zzw zzwVar) {
        this.zza = i;
        this.zzb = zzwVar;
    }

    @Override // java.lang.annotation.Annotation
    public final Class<? extends Annotation> annotationType() {
        return zzx.class;
    }

    @Override // java.lang.annotation.Annotation
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzx)) {
            return false;
        }
        zzx zzxVar = (zzx) obj;
        return this.zza == zzxVar.zza() && this.zzb.equals(zzxVar.zzb());
    }

    @Override // java.lang.annotation.Annotation
    public final int hashCode() {
        return (this.zza ^ 14552422) + (this.zzb.hashCode() ^ 2041407134);
    }

    @Override // java.lang.annotation.Annotation
    public final String toString() {
        return "@com.google.firebase.encoders.proto.Protobuf(tag=" + this.zza + "intEncoding=" + this.zzb + ')';
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzx
    public final int zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzx
    public final zzw zzb() {
        return this.zzb;
    }
}
