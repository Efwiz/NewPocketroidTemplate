package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzto extends zzans<zzto, zztn> implements zzapg {
    private static final zzto zzb;
    private int zze;
    private zzqv zzf;
    private zzqr zzg;
    private zzts zzh;
    private long zzi;

    static {
        zzto zztoVar = new zzto();
        zzb = zztoVar;
        zzans.zzM(zzto.class, zztoVar);
    }

    private zzto() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဃ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzto();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zztn(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
