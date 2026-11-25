package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaxt extends zzano<zzaxt, zzaxm> implements zzapg {
    private static final zzaxt zze;
    private int zzf;
    private zzaxl zzg;
    private float zzi;
    private float zzj;
    private float zzk;
    private float zzl;
    private float zzm;
    private long zzp;
    private long zzq;
    private long zzr;
    private float zzs;
    private byte zzt = 2;
    private zzaob<zzaxs> zzh = zzI();
    private zzaob<zzaxo> zzn = zzI();
    private zzaob<zzaxj> zzo = zzI();

    static {
        zzaxt zzaxtVar = new zzaxt();
        zze = zzaxtVar;
        zzans.zzM(zzaxt.class, zzaxtVar);
    }

    private zzaxt() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzt);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0003\u0000\u0001ဉ\u0000\u0002\u001b\u0003ခ\u0001\u0004ခ\u0002\u0005ခ\u0003\u0006ခ\u0004\u0007\u001b\b\u001b\tဃ\u0007\nခ\t\u000bဃ\b\fဃ\u0006\rခ\u0005", new Object[]{"zzf", "zzg", "zzh", zzaxs.class, "zzi", "zzj", "zzk", "zzl", "zzn", zzaxo.class, "zzo", zzaxj.class, "zzq", "zzs", "zzr", "zzp", "zzm"});
        }
        if (i2 == 3) {
            return new zzaxt();
        }
        zzaxg zzaxgVar = null;
        if (i2 == 4) {
            return new zzaxm(zzaxgVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzt = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
