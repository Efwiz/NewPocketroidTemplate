package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzih {
    private final zzig zza;
    private final Integer zzb;
    private final Integer zzc = null;

    /* synthetic */ zzih(zzif zzifVar, zzie zzieVar) {
        this.zza = zzifVar.zza;
        this.zzb = zzifVar.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzih)) {
            return false;
        }
        zzih zzihVar = (zzih) obj;
        if (Objects.equal(this.zza, zzihVar.zza) && Objects.equal(this.zzb, zzihVar.zzb)) {
            Integer num = zzihVar.zzc;
            if (Objects.equal(null, null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, null);
    }

    public final zzig zza() {
        return this.zza;
    }

    public final Integer zzb() {
        return this.zzb;
    }
}
