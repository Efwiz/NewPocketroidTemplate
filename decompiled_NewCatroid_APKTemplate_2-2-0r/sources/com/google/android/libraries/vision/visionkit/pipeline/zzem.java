package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbaj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzix;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzem extends zzans<zzem, zzek> implements zzapg {
    private static final zzem zzb;
    private int zze;
    private int zzf;
    private zzbaj zzg;
    private zzix zzh;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzds zzi;
    private zzaa zzj;
    private byte zzk = 2;

    static {
        zzem zzemVar = new zzem();
        zzb = zzemVar;
        zzans.zzM(zzem.class, zzemVar);
    }

    private zzem() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzk);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0001\u0001ဉ\u0002\u0002ဉ\u0003\u0003ဌ\u0000\u0004ဉ\u0004\u0005ᐉ\u0001", new Object[]{"zze", "zzh", "zzi", "zzf", zzel.zza, "zzj", "zzg"});
        }
        if (i2 == 3) {
            return new zzem();
        }
        zzej zzejVar = null;
        if (i2 == 4) {
            return new zzek(zzejVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzk = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
