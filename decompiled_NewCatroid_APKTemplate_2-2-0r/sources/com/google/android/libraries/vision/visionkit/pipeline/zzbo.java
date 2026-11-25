package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzln;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlq;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbo extends zzans<zzbo, zzbn> implements zzapg {
    private static final zzbo zzb;
    private int zze;
    private zzln zzf;
    private zzlq zzg;
    private boolean zzh;
    private byte zzi = 2;

    static {
        zzbo zzboVar = new zzbo();
        zzb = zzboVar;
        zzans.zzM(zzbo.class, zzboVar);
    }

    private zzbo() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0001\u0001ဉ\u0000\u0002ᐉ\u0001\u0003ဇ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzbo();
        }
        zzbm zzbmVar = null;
        if (i2 == 4) {
            return new zzbn(zzbmVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
