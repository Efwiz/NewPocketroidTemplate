package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfg {
    private final zzko zza;
    private final Boolean zzb;
    private final Boolean zzc;
    private final zzkd zzd = null;
    private final zzlv zze;

    /* synthetic */ zzfg(zzfe zzfeVar, zzff zzffVar) {
        this.zza = zzfeVar.zza;
        this.zzb = zzfeVar.zzb;
        this.zzc = zzfeVar.zzc;
        this.zze = zzfeVar.zzd;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfg)) {
            return false;
        }
        zzfg zzfgVar = (zzfg) obj;
        if (Objects.equal(this.zza, zzfgVar.zza) && Objects.equal(this.zzb, zzfgVar.zzb) && Objects.equal(this.zzc, zzfgVar.zzc)) {
            zzkd zzkdVar = zzfgVar.zzd;
            if (Objects.equal(null, null) && Objects.equal(this.zze, zzfgVar.zze)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, null, this.zze);
    }

    public final zzko zza() {
        return this.zza;
    }

    public final zzlv zzb() {
        return this.zze;
    }

    public final Boolean zzc() {
        return this.zzb;
    }

    public final Boolean zzd() {
        return this.zzc;
    }
}
