package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzafp {
    private final zzahd zza = zzahn.zzb("shared-acceleration-allowlist");

    private final void zzc(zzagb zzagbVar, zzadr zzadrVar) {
        zzahd zzahdVar = this.zza;
        zzads zzadsVar = new zzads();
        zzadsVar.zzc(zzagbVar.zza().zzk());
        zzahdVar.zzb(zzahf.zzf(zzadsVar), zzadrVar);
    }

    public final void zza(zzagb zzagbVar) {
        zzc(zzagbVar, zzadr.ACCELERATION_ALLOWLIST_FETCH);
    }

    public final void zzb(zzagb zzagbVar) {
        zzc(zzagbVar, zzadr.ACCELERATION_ALLOWLIST_GET);
    }
}
