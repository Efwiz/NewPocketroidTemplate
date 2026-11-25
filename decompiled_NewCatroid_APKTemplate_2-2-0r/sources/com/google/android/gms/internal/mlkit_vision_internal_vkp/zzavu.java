package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavu extends zzans<zzavu, zzavt> implements zzapg {
    private static final zzavu zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private byte zzh = 2;

    static {
        zzavu zzavuVar = new zzavu();
        zzb = zzavuVar;
        zzans.zzM(zzavu.class, zzavuVar);
    }

    private zzavu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔄ\u0000\u0002ᔄ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzavu();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzavt(zzavrVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
