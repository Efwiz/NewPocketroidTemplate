package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavx extends zzans<zzavx, zzavs> implements zzapg {
    private static final zzavx zzb;
    private int zze;
    private Object zzg;
    private int zzf = 0;
    private byte zzh = 2;

    static {
        zzavx zzavxVar = new zzavx();
        zzb = zzavxVar;
        zzans.zzM(zzavx.class, zzavxVar);
    }

    private zzavx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0001\u0001\u0001\u0003\u0003\u0000\u0000\u0003\u0001ᐼ\u0000\u0002ᐼ\u0000\u0003ᐼ\u0000", new Object[]{"zzg", "zzf", "zze", zzavw.class, zzaif.class, zzaia.class});
        }
        if (i2 == 3) {
            return new zzavx();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzavs(zzavrVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
