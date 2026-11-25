package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkd {
    private final zzkb zza;
    private final Integer zzb;
    private final Integer zzc = null;
    private final Boolean zzd = null;

    /* synthetic */ zzkd(zzka zzkaVar, zzkc zzkcVar) {
        this.zza = zzkaVar.zza;
        this.zzb = zzkaVar.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkd)) {
            return false;
        }
        zzkd zzkdVar = (zzkd) obj;
        if (Objects.equal(this.zza, zzkdVar.zza) && Objects.equal(this.zzb, zzkdVar.zzb)) {
            Integer num = zzkdVar.zzc;
            if (Objects.equal(null, null)) {
                Boolean bool = zzkdVar.zzd;
                if (Objects.equal(null, null)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, null, null);
    }

    public final zzkb zza() {
        return this.zza;
    }

    public final Integer zzb() {
        return this.zzb;
    }
}
