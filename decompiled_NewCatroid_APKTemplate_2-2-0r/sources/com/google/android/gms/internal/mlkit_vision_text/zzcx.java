package com.google.android.gms.internal.mlkit_vision_text;

import java.lang.annotation.Annotation;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcx implements zzdc {
    private final int zza;
    private final zzdb zzb;

    zzcx(int i, zzdb zzdbVar) {
        this.zza = i;
        this.zzb = zzdbVar;
    }

    @Override // java.lang.annotation.Annotation
    public final Class<? extends Annotation> annotationType() {
        return zzdc.class;
    }

    @Override // java.lang.annotation.Annotation
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdc)) {
            return false;
        }
        zzdc zzdcVar = (zzdc) obj;
        return this.zza == zzdcVar.zza() && this.zzb.equals(zzdcVar.zzb());
    }

    @Override // java.lang.annotation.Annotation
    public final int hashCode() {
        return (this.zza ^ 14552422) + (this.zzb.hashCode() ^ 2041407134);
    }

    @Override // java.lang.annotation.Annotation
    public final String toString() {
        return "@com.google.firebase.encoders.proto.Protobuf(tag=" + this.zza + "intEncoding=" + this.zzb + ')';
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzdc
    public final int zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzdc
    public final zzdb zzb() {
        return this.zzb;
    }
}
