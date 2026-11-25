package com.google.android.gms.internal.mlkit_common;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhl {
    private final String zza;
    private final zzhj zzc;
    private final String zze;
    private final zzhi zzf;
    private final String zzb = null;
    private final String zzd = null;
    private final Long zzg = null;
    private final Boolean zzh = null;
    private final Boolean zzi = null;

    /* synthetic */ zzhl(zzhh zzhhVar, zzhk zzhkVar) {
        this.zza = zzhhVar.zza;
        this.zzc = zzhhVar.zzb;
        this.zze = zzhhVar.zzc;
        this.zzf = zzhhVar.zzd;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzhl)) {
            return false;
        }
        zzhl zzhlVar = (zzhl) obj;
        if (Objects.equal(this.zza, zzhlVar.zza)) {
            String str = zzhlVar.zzb;
            if (Objects.equal(null, null) && Objects.equal(this.zzc, zzhlVar.zzc)) {
                String str2 = zzhlVar.zzd;
                if (Objects.equal(null, null) && Objects.equal(this.zze, zzhlVar.zze) && Objects.equal(this.zzf, zzhlVar.zzf)) {
                    Long l = zzhlVar.zzg;
                    if (Objects.equal(null, null)) {
                        Boolean bool = zzhlVar.zzh;
                        if (Objects.equal(null, null)) {
                            Boolean bool2 = zzhlVar.zzi;
                            if (Objects.equal(null, null)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, null, this.zzc, null, this.zze, this.zzf, null, null, null);
    }

    public final zzhi zza() {
        return this.zzf;
    }

    public final zzhj zzb() {
        return this.zzc;
    }

    public final String zzc() {
        return this.zze;
    }

    public final String zzd() {
        return this.zza;
    }
}
