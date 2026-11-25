package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzayk extends zzano<zzayk, zzayd> implements zzapg {
    private static final zzayk zze;
    private int zzf;
    private zzayc zzg;
    private float zzi;
    private float zzj;
    private zzayc zzm;
    private zzaxt zzn;
    private byte zzp = 2;
    private zzaob<zzayj> zzh = zzI();
    private zzaob<zzayf> zzk = zzI();
    private zzamk zzl = zzamk.zzb;
    private zzaob<zzaya> zzo = zzI();

    static {
        zzayk zzaykVar = new zzayk();
        zze = zzaykVar;
        zzans.zzM(zzayk.class, zzaykVar);
    }

    private zzayk() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzp);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0003\u0001\u0001ဉ\u0000\u0002\u001b\u0003ခ\u0001\u0004\u001b\u0005ᐉ\u0005\u0006\u001b\u0007ည\u0003\bဉ\u0004\tခ\u0002", new Object[]{"zzf", "zzg", "zzh", zzayj.class, "zzi", "zzk", zzayf.class, "zzn", "zzo", zzaya.class, "zzl", "zzm", "zzj"});
        }
        if (i2 == 3) {
            return new zzayk();
        }
        zzaxx zzaxxVar = null;
        if (i2 == 4) {
            return new zzayd(zzaxxVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzp = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
