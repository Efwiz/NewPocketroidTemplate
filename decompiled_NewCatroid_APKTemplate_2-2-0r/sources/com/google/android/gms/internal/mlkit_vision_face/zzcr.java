package com.google.android.gms.internal.mlkit_vision_face;

import java.lang.annotation.Annotation;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcr implements zzcw {
    private final int zza;
    private final zzcv zzb;

    zzcr(int i, zzcv zzcvVar) {
        this.zza = i;
        this.zzb = zzcvVar;
    }

    @Override // java.lang.annotation.Annotation
    public final Class<? extends Annotation> annotationType() {
        return zzcw.class;
    }

    @Override // java.lang.annotation.Annotation
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcw)) {
            return false;
        }
        zzcw zzcwVar = (zzcw) obj;
        return this.zza == zzcwVar.zza() && this.zzb.equals(zzcwVar.zzb());
    }

    @Override // java.lang.annotation.Annotation
    public final int hashCode() {
        return (this.zza ^ 14552422) + (this.zzb.hashCode() ^ 2041407134);
    }

    @Override // java.lang.annotation.Annotation
    public final String toString() {
        return "@com.google.firebase.encoders.proto.Protobuf(tag=" + this.zza + "intEncoding=" + this.zzb + ')';
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzcw
    public final int zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzcw
    public final zzcv zzb() {
        return this.zzb;
    }
}
