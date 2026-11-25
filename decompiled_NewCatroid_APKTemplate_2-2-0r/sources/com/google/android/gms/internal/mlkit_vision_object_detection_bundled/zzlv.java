package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlv {
    private final zzlt zza;
    private final Boolean zzb;
    private final Boolean zzc;
    private final Integer zzd = null;
    private final Float zze = null;
    private final zzkw zzf = null;

    /* synthetic */ zzlv(zzls zzlsVar, zzlu zzluVar) {
        this.zza = zzlsVar.zza;
        this.zzb = zzlsVar.zzb;
        this.zzc = zzlsVar.zzc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzlv)) {
            return false;
        }
        zzlv zzlvVar = (zzlv) obj;
        if (Objects.equal(this.zza, zzlvVar.zza) && Objects.equal(this.zzb, zzlvVar.zzb) && Objects.equal(this.zzc, zzlvVar.zzc)) {
            Integer num = zzlvVar.zzd;
            if (Objects.equal(null, null)) {
                Float f = zzlvVar.zze;
                if (Objects.equal(null, null)) {
                    zzkw zzkwVar = zzlvVar.zzf;
                    if (Objects.equal(null, null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, null, null, null);
    }

    public final zzlt zza() {
        return this.zza;
    }

    public final Boolean zzb() {
        return this.zzc;
    }

    public final Boolean zzc() {
        return this.zzb;
    }
}
