package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzag extends zzans<zzag, zzaf> implements zzapg {
    private static final zzag zzb;
    private int zze;
    private zzaob<zzam> zzf = zzI();
    private String zzg = "";

    static {
        zzag zzagVar = new zzag();
        zzb = zzagVar;
        zzans.zzM(zzag.class, zzagVar);
    }

    private zzag() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဈ\u0000", new Object[]{"zze", "zzf", zzam.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzag();
        }
        zzae zzaeVar = null;
        if (i2 == 4) {
            return new zzaf(zzaeVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final List<zzam> zzc() {
        return this.zzf;
    }
}
