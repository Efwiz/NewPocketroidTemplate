package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcb extends zzans<zzcb, zzca> implements zzapg {
    private static final zzcb zzb;
    private int zze;
    private zzaob<String> zzf = zzans.zzI();
    private long zzg;

    static {
        zzcb zzcbVar = new zzcb();
        zzb = zzcbVar;
        zzans.zzM(zzcb.class, zzcbVar);
    }

    private zzcb() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001a\u0002ဂ\u0000", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzcb();
        }
        zzbz zzbzVar = null;
        if (i2 == 4) {
            return new zzca(zzbzVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
