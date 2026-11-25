package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzalb extends zzans<zzalb, zzala> implements zzapg {
    private static final zzalb zzb;
    private int zze;
    private zzaob<zzald> zzf = zzI();
    private zzaob<zzakx> zzg = zzI();
    private int zzh;

    static {
        zzalb zzalbVar = new zzalb();
        zzb = zzalbVar;
        zzans.zzM(zzalb.class, zzalbVar);
    }

    private zzalb() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0002\u0000\u0001\u001b\u0002\u001b\u0003င\u0000", new Object[]{"zze", "zzf", zzald.class, "zzg", zzakx.class, "zzh"});
        }
        if (i2 == 3) {
            return new zzalb();
        }
        zzakv zzakvVar = null;
        if (i2 == 4) {
            return new zzala(zzakvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
