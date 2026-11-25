package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcx extends zzans<zzcx, zzcv> implements zzapg {
    private static final zzcx zzb;
    private int zze;
    private int zzf;
    private int zzg = 2;
    private String zzh = "";

    static {
        zzcx zzcxVar = new zzcx();
        zzb = zzcxVar;
        zzans.zzM(zzcx.class, zzcxVar);
    }

    private zzcx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001\u0003ဈ\u0002", new Object[]{"zze", "zzf", zzcw.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzcx();
        }
        zzcu zzcuVar = null;
        if (i2 == 4) {
            return new zzcv(zzcuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
