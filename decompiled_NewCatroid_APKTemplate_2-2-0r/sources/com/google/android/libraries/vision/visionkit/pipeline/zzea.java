package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbaj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzea extends zzans<zzea, zzdz> implements zzapg {
    private static final zzea zzb;
    private int zze;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbh zzf;
    private zziu zzg;
    private zzbaj zzh;
    private zziu zzi;
    private byte zzj = 2;

    static {
        zzea zzeaVar = new zzea();
        zzb = zzeaVar;
        zzans.zzM(zzea.class, zzeaVar);
    }

    private zzea() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzj);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0001\u0001ဉ\u0000\u0002ဉ\u0001\u0003ᐉ\u0002\u0004ဉ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzea();
        }
        zzdy zzdyVar = null;
        if (i2 == 4) {
            return new zzdz(zzdyVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzj = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
