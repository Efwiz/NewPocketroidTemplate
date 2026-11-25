package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdu extends zzans<zzdu, zzdr> implements zzapg {
    private static final zzdu zzb;
    private zzaob<zzdt> zze = zzI();

    static {
        zzdu zzduVar = new zzdu();
        zzb = zzduVar;
        zzans.zzM(zzdu.class, zzduVar);
    }

    private zzdu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzdt.class});
        }
        if (i2 == 3) {
            return new zzdu();
        }
        zzdq zzdqVar = null;
        if (i2 == 4) {
            return new zzdr(zzdqVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
