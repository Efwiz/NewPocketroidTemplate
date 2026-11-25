package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdk extends zzans<zzdk, zzdj> implements zzapg {
    private static final zzdk zzb;
    private int zze;
    private boolean zzf;

    static {
        zzdk zzdkVar = new zzdk();
        zzb = zzdkVar;
        zzans.zzM(zzdk.class, zzdkVar);
    }

    private zzdk() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဇ\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzdk();
        }
        zzdi zzdiVar = null;
        if (i2 == 4) {
            return new zzdj(zzdiVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
