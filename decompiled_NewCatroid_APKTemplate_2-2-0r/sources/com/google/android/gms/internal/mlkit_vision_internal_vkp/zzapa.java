package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapa {
    zzapa() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzaoz zzaozVar = (zzaoz) obj;
        zzaoy zzaoyVar = (zzaoy) obj2;
        int iZza = 0;
        if (!zzaozVar.isEmpty()) {
            for (Map.Entry entry : zzaozVar.entrySet()) {
                iZza += zzaoyVar.zza(i, entry.getKey(), entry.getValue());
            }
        }
        return iZza;
    }

    public static final boolean zzb(Object obj) {
        return !((zzaoz) obj).zze();
    }

    public static final Object zzc(Object obj, Object obj2) {
        zzaoz zzaozVarZzb = (zzaoz) obj;
        zzaoz zzaozVar = (zzaoz) obj2;
        if (!zzaozVar.isEmpty()) {
            if (!zzaozVarZzb.zze()) {
                zzaozVarZzb = zzaozVarZzb.zzb();
            }
            zzaozVarZzb.zzd(zzaozVar);
        }
        return zzaozVarZzb;
    }
}
