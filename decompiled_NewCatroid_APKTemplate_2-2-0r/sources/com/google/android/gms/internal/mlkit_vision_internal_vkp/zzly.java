package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.lang.annotation.Annotation;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzly implements zzmd {
    private final int zza;
    private final zzmc zzb;

    zzly(int i, zzmc zzmcVar) {
        this.zza = i;
        this.zzb = zzmcVar;
    }

    @Override // java.lang.annotation.Annotation
    public final Class<? extends Annotation> annotationType() {
        return zzmd.class;
    }

    @Override // java.lang.annotation.Annotation
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzmd)) {
            return false;
        }
        zzmd zzmdVar = (zzmd) obj;
        return this.zza == zzmdVar.zza() && this.zzb.equals(zzmdVar.zzb());
    }

    @Override // java.lang.annotation.Annotation
    public final int hashCode() {
        return (this.zza ^ 14552422) + (this.zzb.hashCode() ^ 2041407134);
    }

    @Override // java.lang.annotation.Annotation
    public final String toString() {
        return "@com.google.firebase.encoders.proto.Protobuf(tag=" + this.zza + "intEncoding=" + this.zzb + ')';
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzmd
    public final int zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzmd
    public final zzmc zzb() {
        return this.zzb;
    }
}
