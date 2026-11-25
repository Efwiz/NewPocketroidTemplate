package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzeh extends zzans<zzeh, zzeg> implements zzapg {
    private static final zzeh zzb;
    private int zze;
    private String zzf = "";

    static {
        zzeh zzehVar = new zzeh();
        zzb = zzehVar;
        zzans.zzM(zzeh.class, zzehVar);
    }

    private zzeh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzeh();
        }
        zzee zzeeVar = null;
        if (i2 == 4) {
            return new zzeg(zzeeVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
