package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzgl;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjd;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdh extends zzans<zzdh, zzdg> implements zzapg {
    private static final zzdh zzb;
    private int zze;
    private zzgl zzf;
    private zzaob<zzjd> zzg = zzI();
    private zzaob<com.google.android.gms.internal.mlkit_vision_internal_vkp.zzct> zzh = zzI();

    static {
        zzdh zzdhVar = new zzdh();
        zzb = zzdhVar;
        zzans.zzM(zzdh.class, zzdhVar);
    }

    private zzdh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0002\u0000\u0001ဉ\u0000\u0002\u001b\u0003\u001b", new Object[]{"zze", "zzf", "zzg", zzjd.class, "zzh", com.google.android.gms.internal.mlkit_vision_internal_vkp.zzct.class});
        }
        if (i2 == 3) {
            return new zzdh();
        }
        zzdf zzdfVar = null;
        if (i2 == 4) {
            return new zzdg(zzdfVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
