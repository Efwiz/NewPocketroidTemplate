package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzm extends zzans<zzm, zzl> implements zzapg {
    private static final zzm zzb;
    private int zze;
    private zzep zzf;

    static {
        zzm zzmVar = new zzm();
        zzb = zzmVar;
        zzans.zzM(zzm.class, zzmVar);
    }

    private zzm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzm();
        }
        zzk zzkVar = null;
        if (i2 == 4) {
            return new zzl(zzkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
