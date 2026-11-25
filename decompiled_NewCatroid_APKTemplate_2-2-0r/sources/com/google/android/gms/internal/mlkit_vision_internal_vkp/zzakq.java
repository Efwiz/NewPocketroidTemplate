package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzakq extends zzans<zzakq, zzakp> implements zzapg {
    private static final zzakq zzb;
    private int zze;
    private int zzg;
    private int zzh;
    private zzaks zzk;
    private zzajy zzm;
    private zzajv zzn;
    private byte zzp = 2;
    private zzamk zzf = zzamk.zzb;
    private String zzi = "";
    private zzaob<zzajr> zzj = zzI();
    private zzaob<zzako> zzl = zzI();
    private zzaob<zzakm> zzo = zzI();

    static {
        zzakq zzakqVar = new zzakq();
        zzb = zzakqVar;
        zzans.zzM(zzakq.class, zzakqVar);
    }

    private zzakq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzp);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0003\u0004\u0001ᔊ\u0000\u0002ဈ\u0003\u0003Л\u0004ဉ\u0004\u0005Л\u0006ဉ\u0005\u0007ဉ\u0006\bЛ\tင\u0001\nင\u0002", new Object[]{"zze", "zzf", "zzi", "zzj", zzajr.class, "zzk", "zzl", zzako.class, "zzm", "zzn", "zzo", zzakm.class, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzakq();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzakp(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzp = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
