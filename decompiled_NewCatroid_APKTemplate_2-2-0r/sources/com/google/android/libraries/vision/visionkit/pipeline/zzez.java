package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzez extends zzans<zzez, zzey> implements zzapg {
    private static final zzez zzb;
    private int zze;
    private String zzf = "";
    private zzaob<String> zzg = zzans.zzI();
    private boolean zzh;

    static {
        zzez zzezVar = new zzez();
        zzb = zzezVar;
        zzans.zzM(zzez.class, zzezVar);
    }

    private zzez() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001a\u0003ဇ\u0001", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzez();
        }
        zzew zzewVar = null;
        if (i2 == 4) {
            return new zzey(zzewVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
