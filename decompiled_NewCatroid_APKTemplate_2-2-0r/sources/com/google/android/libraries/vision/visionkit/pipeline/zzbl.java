package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbl extends zzans<zzbl, zzbk> implements zzapg {
    private static final zzbl zzb;
    private int zze;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzdz zzf;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzef zzg;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzec zzh;

    static {
        zzbl zzblVar = new zzbl();
        zzb = zzblVar;
        zzans.zzM(zzbl.class, zzblVar);
    }

    private zzbl() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0004\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0004ဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzbl();
        }
        zzbj zzbjVar = null;
        if (i2 == 4) {
            return new zzbk(zzbjVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
