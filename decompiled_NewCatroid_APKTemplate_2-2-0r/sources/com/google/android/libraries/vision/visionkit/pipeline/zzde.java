package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzde extends zzans<zzde, zzdc> implements zzapg {
    private static final zzde zzb;
    private int zze;
    private int zzf = 1;
    private int zzg = 5;

    static {
        zzde zzdeVar = new zzde();
        zzb = zzdeVar;
        zzans.zzM(zzde.class, zzdeVar);
    }

    private zzde() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001", new Object[]{"zze", "zzf", zzdd.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzde();
        }
        zzdb zzdbVar = null;
        if (i2 == 4) {
            return new zzdc(zzdbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
