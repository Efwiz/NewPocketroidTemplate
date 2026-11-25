package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzata extends zzans<zzata, zzasz> implements zzapg {
    private static final zzata zzb;
    private int zze;
    private zzaob<zzato> zzf = zzI();
    private zzatc zzg;
    private zzaru zzh;

    static {
        zzata zzataVar = new zzata();
        zzb = zzataVar;
        zzans.zzM(zzata.class, zzataVar);
    }

    private zzata() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u001b\u0002ဉ\u0000\u0003ဉ\u0001", new Object[]{"zze", "zzf", zzato.class, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzata();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzasz(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
