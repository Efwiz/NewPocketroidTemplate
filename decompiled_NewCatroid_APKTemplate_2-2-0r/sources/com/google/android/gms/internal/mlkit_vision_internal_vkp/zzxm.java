package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzxm extends zzans<zzxm, zzxl> implements zzapg {
    private static final zzanz<Integer, zzalm> zzb = new zzxk();
    private static final zzxm zze;
    private zzany zzf = zzG();

    static {
        zzxm zzxmVar = new zzxm();
        zze = zzxmVar;
        zzans.zzM(zzxm.class, zzxmVar);
    }

    private zzxm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"zzf", zzalm.zza()});
        }
        if (i2 == 3) {
            return new zzxm();
        }
        zzxj zzxjVar = null;
        if (i2 == 4) {
            return new zzxl(zzxjVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zze;
    }
}
