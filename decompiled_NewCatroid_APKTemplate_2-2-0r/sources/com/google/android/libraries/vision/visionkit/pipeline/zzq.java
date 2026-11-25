package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbby;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzq extends zzans<zzq, zzp> implements zzapg {
    private static final zzq zzb;
    private int zze;
    private int zzf = 0;
    private Object zzg;
    private zziu zzh;

    static {
        zzq zzqVar = new zzq();
        zzb = zzqVar;
        zzans.zzM(zzq.class, zzqVar);
    }

    private zzq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0001\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ြ\u0000\u0003ြ\u0000", new Object[]{"zzg", "zzf", "zze", "zzh", com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbn.class, zzbby.class});
        }
        if (i2 == 3) {
            return new zzq();
        }
        zzo zzoVar = null;
        if (i2 == 4) {
            return new zzp(zzoVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
