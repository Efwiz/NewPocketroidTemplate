package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzayu;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbc extends zzans<zzbc, zzbb> implements zzapg {
    private static final zzbc zzb;
    private int zze;
    private Object zzg;
    private int zzf = 0;
    private byte zzi = 2;
    private zzaob<zzayu> zzh = zzI();

    static {
        zzbc zzbcVar = new zzbc();
        zzb = zzbcVar;
        zzans.zzM(zzbc.class, zzbcVar);
    }

    private zzbc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0001\u0001\u0001\u0003\u0002\u0000\u0001\u0001\u0001်\u0000\u0003Л", new Object[]{"zzg", "zzf", "zze", "zzh", zzayu.class});
        }
        if (i2 == 3) {
            return new zzbc();
        }
        zzba zzbaVar = null;
        if (i2 == 4) {
            return new zzbb(zzbaVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
