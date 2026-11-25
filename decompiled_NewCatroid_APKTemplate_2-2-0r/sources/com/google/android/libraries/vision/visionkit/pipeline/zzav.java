package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzav extends zzans<zzav, zzau> implements zzapg {
    private static final zzav zzb;
    private int zze;
    private float zzf;
    private long zzg;
    private long zzh;
    private long zzi;

    static {
        zzav zzavVar = new zzav();
        zzb = zzavVar;
        zzans.zzM(zzav.class, zzavVar);
    }

    private zzav() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ခ\u0000\u0002ဂ\u0001\u0003ဂ\u0002\u0004ဂ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzav();
        }
        zzaq zzaqVar = null;
        if (i2 == 4) {
            return new zzau(zzaqVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
