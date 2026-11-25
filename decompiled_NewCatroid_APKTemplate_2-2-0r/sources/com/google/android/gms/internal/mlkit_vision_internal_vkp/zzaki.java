package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaki extends zzans<zzaki, zzakh> implements zzapg {
    private static final zzaki zzb;
    private int zze;
    private int zzf;
    private float zzh;
    private byte zzi = 2;
    private zzany zzg = zzG();

    static {
        zzaki zzakiVar = new zzaki();
        zzb = zzakiVar;
        zzans.zzM(zzaki.class, zzakiVar);
    }

    private zzaki() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0002\u0001ᔄ\u0000\u0002ᔁ\u0001\u0003\u0016", new Object[]{"zze", "zzf", "zzh", "zzg"});
        }
        if (i2 == 3) {
            return new zzaki();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzakh(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
