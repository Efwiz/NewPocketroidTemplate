package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzajn extends zzans<zzajn, zzajm> implements zzapg {
    private static final zzajn zzb;
    private int zze;
    private zzaix zzi;
    private byte zzk = 2;
    private zzamk zzf = zzamk.zzb;
    private zzamk zzg = zzamk.zzb;
    private zzamk zzh = zzamk.zzb;
    private String zzj = "";

    static {
        zzajn zzajnVar = new zzajn();
        zzb = zzajnVar;
        zzans.zzM(zzajn.class, zzajnVar);
    }

    private zzajn() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzk);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0002\u0001ᔊ\u0000\u0002ည\u0001\u0003ည\u0002\u0004ᐉ\u0003\u0005ဈ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzajn();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzajm(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzk = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
