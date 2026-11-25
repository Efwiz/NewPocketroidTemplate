package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzim extends zzans<zzim, zzil> implements zzapg {
    private static final zzim zzb;
    private zzaob<zzip> zze = zzI();

    static {
        zzim zzimVar = new zzim();
        zzb = zzimVar;
        zzans.zzM(zzim.class, zzimVar);
    }

    private zzim() {
    }

    public static zzim zzc() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzip.class});
        }
        if (i2 == 3) {
            return new zzim();
        }
        zzik zzikVar = null;
        if (i2 == 4) {
            return new zzil(zzikVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final List<zzip> zzd() {
        return this.zze;
    }
}
