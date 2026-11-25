package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzlx;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzev extends zzans<zzev, zzeu> implements zzapg {
    private static final zzev zzb;
    private int zze;
    private String zzf = "";
    private zzaob<String> zzg = zzans.zzI();
    private zzaob<String> zzh = zzans.zzI();
    private zzaob<String> zzi = zzans.zzI();
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbb zzj;
    private zzlx zzk;

    static {
        zzev zzevVar = new zzev();
        zzb = zzevVar;
        zzans.zzM(zzev.class, zzevVar);
    }

    private zzev() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0003\u0000\u0001ဈ\u0000\u0002\u001a\u0003ဉ\u0001\u0004\u001a\u0005ဉ\u0002\u0006\u001a", new Object[]{"zze", "zzf", "zzg", "zzj", "zzi", "zzk", "zzh"});
        }
        if (i2 == 3) {
            return new zzev();
        }
        zzet zzetVar = null;
        if (i2 == 4) {
            return new zzeu(zzetVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
