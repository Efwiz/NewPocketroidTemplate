package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbci;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzx extends zzans<zzx, zzw> implements zzapg {
    private static final zzx zzb;
    private int zze;
    private zzbci zzf;
    private int zzh;
    private String zzg = "";
    private int zzi = 93;

    static {
        zzx zzxVar = new zzx();
        zzb = zzxVar;
        zzans.zzM(zzx.class, zzxVar);
    }

    private zzx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဈ\u0001\u0003င\u0002\u0004င\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzx();
        }
        zzv zzvVar = null;
        if (i2 == 4) {
            return new zzw(zzvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
