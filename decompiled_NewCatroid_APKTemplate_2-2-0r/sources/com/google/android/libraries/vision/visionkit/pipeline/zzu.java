package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbci;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzu extends zzans<zzu, zzs> implements zzapg {
    private static final zzu zzb;
    private int zze;
    private zzbci zzf;
    private String zzg = "";
    private int zzh;
    private boolean zzi;
    private int zzj;

    static {
        zzu zzuVar = new zzu();
        zzb = zzuVar;
        zzans.zzM(zzu.class, zzuVar);
    }

    private zzu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဈ\u0001\u0003င\u0002\u0004ဇ\u0003\u0005ဌ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzt.zza});
        }
        if (i2 == 3) {
            return new zzu();
        }
        zzr zzrVar = null;
        if (i2 == 4) {
            return new zzs(zzrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
