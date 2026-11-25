package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzako extends zzans<zzako, zzakn> implements zzapg {
    private static final zzako zzb;
    private int zze;
    private zzaix zzf;
    private int zzi;
    private int zzj;
    private byte zzk = 2;
    private String zzg = "";
    private zzany zzh = zzG();

    static {
        zzako zzakoVar = new zzako();
        zzb = zzakoVar;
        zzans.zzM(zzako.class, zzakoVar);
    }

    private zzako() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzk);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0001\u0001ᐉ\u0000\u0002ဈ\u0001\u0003\u0016\u0004င\u0002\u0005ဌ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzawm.zza()});
        }
        if (i2 == 3) {
            return new zzako();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzakn(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzk = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
