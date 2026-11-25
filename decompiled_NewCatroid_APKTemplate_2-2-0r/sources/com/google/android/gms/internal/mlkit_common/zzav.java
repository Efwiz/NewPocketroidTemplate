package com.google.android.gms.internal.mlkit_common;

import java.lang.annotation.Annotation;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzav implements zzba {
    private final int zza;
    private final zzaz zzb;

    zzav(int i, zzaz zzazVar) {
        this.zza = i;
        this.zzb = zzazVar;
    }

    @Override // java.lang.annotation.Annotation
    public final Class<? extends Annotation> annotationType() {
        return zzba.class;
    }

    @Override // java.lang.annotation.Annotation
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzba)) {
            return false;
        }
        zzba zzbaVar = (zzba) obj;
        return this.zza == zzbaVar.zza() && this.zzb.equals(zzbaVar.zzb());
    }

    @Override // java.lang.annotation.Annotation
    public final int hashCode() {
        return (this.zza ^ 14552422) + (this.zzb.hashCode() ^ 2041407134);
    }

    @Override // java.lang.annotation.Annotation
    public final String toString() {
        return "@com.google.firebase.encoders.proto.Protobuf(tag=" + this.zza + "intEncoding=" + this.zzb + ')';
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzba
    public final int zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzba
    public final zzaz zzb() {
        return this.zzb;
    }
}
