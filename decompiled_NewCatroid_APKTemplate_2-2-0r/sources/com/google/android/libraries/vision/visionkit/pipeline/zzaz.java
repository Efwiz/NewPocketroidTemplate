package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaz extends zzans<zzaz, zzay> implements zzapg {
    private static final zzaz zzb;
    private int zze;
    private int zzf;

    static {
        zzaz zzazVar = new zzaz();
        zzb = zzazVar;
        zzans.zzM(zzaz.class, zzazVar);
    }

    private zzaz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001င\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzaz();
        }
        zzax zzaxVar = null;
        if (i2 == 4) {
            return new zzay(zzaxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
