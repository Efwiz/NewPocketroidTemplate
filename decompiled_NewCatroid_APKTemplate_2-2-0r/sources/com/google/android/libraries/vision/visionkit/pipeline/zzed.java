package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzed extends zzans<zzed, zzec> implements zzapg {
    private static final zzed zzb;
    private int zze;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbb zzf;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbh zzg;
    private zzea zzh;
    private boolean zzi;
    private byte zzj = 2;

    static {
        zzed zzedVar = new zzed();
        zzb = zzedVar;
        zzans.zzM(zzed.class, zzedVar);
    }

    private zzed() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzj);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0001\u0001ဉ\u0000\u0002ဇ\u0003\u0003ᐉ\u0002\u0004ဉ\u0001", new Object[]{"zze", "zzf", "zzi", "zzh", "zzg"});
        }
        if (i2 == 3) {
            return new zzed();
        }
        zzeb zzebVar = null;
        if (i2 == 4) {
            return new zzec(zzebVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzj = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
