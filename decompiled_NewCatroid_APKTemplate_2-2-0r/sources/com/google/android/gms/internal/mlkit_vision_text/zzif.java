package com.google.android.gms.internal.mlkit_vision_text;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzif {
    private final zzie zza;
    private final Integer zzb;
    private final Integer zzc = null;

    /* synthetic */ zzif(zzid zzidVar, zzic zzicVar) {
        this.zza = zzidVar.zza;
        this.zzb = zzidVar.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzif)) {
            return false;
        }
        zzif zzifVar = (zzif) obj;
        if (Objects.equal(this.zza, zzifVar.zza) && Objects.equal(this.zzb, zzifVar.zzb)) {
            Integer num = zzifVar.zzc;
            if (Objects.equal(null, null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, null);
    }

    public final zzie zza() {
        return this.zza;
    }

    public final Integer zzb() {
        return this.zzb;
    }
}
