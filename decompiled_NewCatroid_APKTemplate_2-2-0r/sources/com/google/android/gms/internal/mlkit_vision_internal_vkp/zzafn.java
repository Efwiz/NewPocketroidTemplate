package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.util.Log;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzafn implements zzahq {
    private zzarq zza;
    private final zzagb zzb;
    private final String zzc;
    private final String zzd;
    private final String zze = "com.google.perception";
    private final zzaft zzf;
    private final zzagg zzg;
    private final zzagc zzh;
    private final zzafp zzi;

    zzafn(zzagb zzagbVar, String str, String str2, String str3, int i, zzaft zzaftVar, zzagg zzaggVar, zzagc zzagcVar, zzafp zzafpVar) {
        this.zzb = zzagbVar;
        this.zzc = str;
        this.zzd = str2;
        this.zzf = zzaftVar;
        this.zzg = zzaggVar;
        this.zzh = zzagcVar;
        this.zzi = zzafpVar;
    }

    public final zzarq zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahq
    public final boolean zzb() {
        zzafp zzafpVar;
        zzagb zzagbVar = new zzagb(this.zze, 2);
        zzagbVar.zzf();
        try {
            Log.d("AccelerationAllowlist", "Calling goldblum client");
            this.zza = this.zzh.zza(this.zzf, this.zzg, this.zzc, this.zzd, this.zze, 2);
            return true;
        } catch (zzagd e) {
            Log.d("AccelerationAllowlist", "Exception calling goldblum", e);
            this.zzb.zzb(e.zza());
            zzagbVar.zzd(e.zza());
            return false;
        } finally {
            zzagbVar.zze();
            this.zzi.zza(zzagbVar);
        }
    }
}
