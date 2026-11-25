package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbs extends zzans<zzbs, zzbq> implements zzapg {
    private static final zzbs zzb;
    private int zze;
    private int zzf;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzda zzg;
    private zzx zzh;

    static {
        zzbs zzbsVar = new zzbs();
        zzb = zzbsVar;
        zzans.zzM(zzbs.class, zzbsVar);
    }

    private zzbs() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zze", "zzf", zzbr.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzbs();
        }
        zzbp zzbpVar = null;
        if (i2 == 4) {
            return new zzbq(zzbpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
