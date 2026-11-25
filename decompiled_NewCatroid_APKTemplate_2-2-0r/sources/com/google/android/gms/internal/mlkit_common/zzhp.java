package com.google.android.gms.internal.mlkit_common;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhp {
    private final zzhl zza;
    private final zzhn zzb = null;
    private final zzhn zzc = null;
    private final Boolean zzd = null;

    /* synthetic */ zzhp(zzhm zzhmVar, zzho zzhoVar) {
        this.zza = zzhmVar.zza;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzhp)) {
            return false;
        }
        zzhp zzhpVar = (zzhp) obj;
        if (Objects.equal(this.zza, zzhpVar.zza)) {
            zzhn zzhnVar = zzhpVar.zzb;
            if (Objects.equal(null, null)) {
                zzhn zzhnVar2 = zzhpVar.zzc;
                if (Objects.equal(null, null)) {
                    Boolean bool = zzhpVar.zzd;
                    if (Objects.equal(null, null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, null, null, null);
    }

    public final zzhl zza() {
        return this.zza;
    }
}
