package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzep extends zzans<zzep, zzeo> implements zzapg {
    private static final zzep zzb;
    private int zze;
    private int zzf = 0;
    private Object zzg;

    static {
        zzep zzepVar = new zzep();
        zzb = zzepVar;
        zzans.zzM(zzep.class, zzepVar);
    }

    private zzep() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0001\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ြ\u0000\u0002ြ\u0000", new Object[]{"zzg", "zzf", "zze", zzbf.class, zzcb.class});
        }
        if (i2 == 3) {
            return new zzep();
        }
        zzen zzenVar = null;
        if (i2 == 4) {
            return new zzeo(zzenVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
