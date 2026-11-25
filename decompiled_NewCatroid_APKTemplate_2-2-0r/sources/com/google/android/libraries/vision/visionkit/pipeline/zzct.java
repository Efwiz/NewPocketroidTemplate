package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzct extends zzans<zzct, zzcs> implements zzapg {
    private static final zzct zzb;
    private int zze;
    private boolean zzf;
    private String zzg = "";

    static {
        zzct zzctVar = new zzct();
        zzb = zzctVar;
        zzans.zzM(zzct.class, zzctVar);
    }

    private zzct() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဇ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzct();
        }
        zzcr zzcrVar = null;
        if (i2 == 4) {
            return new zzcs(zzcrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
