package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzad extends zzans<zzad, zzac> implements zzapg {
    private static final zzad zzb;
    private int zze;
    private int zzg;
    private zzaob<zziu> zzf = zzI();
    private zzaob<String> zzh = zzans.zzI();

    static {
        zzad zzadVar = new zzad();
        zzb = zzadVar;
        zzans.zzM(zzad.class, zzadVar);
    }

    private zzad() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0002\u0000\u0001\u001b\u0002င\u0000\u0003\u001a", new Object[]{"zze", "zzf", zziu.class, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzad();
        }
        zzab zzabVar = null;
        if (i2 == 4) {
            return new zzac(zzabVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
