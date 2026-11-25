package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavw extends zzans<zzavw, zzavv> implements zzapg {
    private static final zzavw zzb;
    private int zze;
    private zzaif zzg;
    private byte zzh = 2;
    private zzaob<zzavu> zzf = zzI();

    static {
        zzavw zzavwVar = new zzavw();
        zzb = zzavwVar;
        zzans.zzM(zzavw.class, zzavwVar);
    }

    private zzavw() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0002\u0001Л\u0002ᐉ\u0000", new Object[]{"zze", "zzf", zzavu.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzavw();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzavv(zzavrVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
