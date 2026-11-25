package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzok extends zzans<zzok, zzoj> implements zzapg {
    private static final zzanz<Integer, Object> zzb = new zzoi();
    private static final zzok zze;
    private int zzf;
    private zzrs zzg;
    private zzrs zzh;
    private zzany zzi = zzG();
    private long zzj;

    static {
        zzok zzokVar = new zzok();
        zze = zzokVar;
        zzans.zzM(zzok.class, zzokVar);
    }

    private zzok() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004ဃ\u0002", new Object[]{"zzf", "zzg", "zzh", "zzi", zzqb.zza, "zzj"});
        }
        if (i2 == 3) {
            return new zzok();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzoj(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zze;
    }
}
