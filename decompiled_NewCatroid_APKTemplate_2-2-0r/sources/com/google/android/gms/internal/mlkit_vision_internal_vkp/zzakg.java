package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzakg extends zzans<zzakg, zzakf> implements zzapg {
    private static final zzakg zzb;
    private int zze;
    private int zzf;
    private zzaix zzh;
    private zzaix zzi;
    private float zzk;
    private zzaix zzm;
    private zzaix zzn;
    private zzajn zzo;
    private zzakk zzq;
    private byte zzr = 2;
    private zzany zzg = zzG();
    private String zzj = "";
    private zzaob<zzajb> zzl = zzI();
    private boolean zzp = true;

    static {
        zzakg zzakgVar = new zzakg();
        zzb = zzakgVar;
        zzans.zzM(zzakg.class, zzakgVar);
    }

    private zzakg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzr);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0002\b\u0001ᔄ\u0000\u0002ᔉ\u0001\u0003ᐉ\u0002\u0004ဈ\u0003\u0005ခ\u0004\u0006ᐉ\u0005\u0007ᐉ\u0006\bᐉ\u0007\tဇ\b\nᐉ\t\u000bЛ\f\u0016", new Object[]{"zze", "zzf", "zzh", "zzi", "zzj", "zzk", "zzm", "zzn", "zzo", "zzp", "zzq", "zzl", zzajb.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzakg();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzakf(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzr = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
