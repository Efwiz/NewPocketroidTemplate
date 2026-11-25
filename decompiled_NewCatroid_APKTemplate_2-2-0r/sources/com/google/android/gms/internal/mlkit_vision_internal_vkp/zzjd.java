package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjd extends zzans<zzjd, zzjc> implements zzapg {
    private static final zzjd zzb;
    private int zze;
    private zzaob<zzja> zzf = zzI();
    private String zzg = "";

    static {
        zzjd zzjdVar = new zzjd();
        zzb = zzjdVar;
        zzans.zzM(zzjd.class, zzjdVar);
    }

    private zzjd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဈ\u0000", new Object[]{"zze", "zzf", zzja.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzjd();
        }
        zzjb zzjbVar = null;
        if (i2 == 4) {
            return new zzjc(zzjbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
