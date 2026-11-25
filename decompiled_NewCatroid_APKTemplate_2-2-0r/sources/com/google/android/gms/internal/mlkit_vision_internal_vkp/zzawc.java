package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzawc extends zzano<zzawc, zzawb> implements zzapg {
    private static final zzawc zze;
    private int zzf;
    private Object zzh;
    private int zzj;
    private int zzk;
    private int zzl;
    private int zzg = 0;
    private byte zzm = 2;
    private String zzi = "";

    static {
        zzawc zzawcVar = new zzawc();
        zze = zzawcVar;
        zzans.zzM(zzawc.class, zzawcVar);
    }

    private zzawc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzm);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0007\u0001\u0001\u0001\b\u0007\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003င\u0002\u0004င\u0003\u0006ွ\u0000\u0007ွ\u0000\bံ\u0000", new Object[]{"zzh", "zzg", "zzf", "zzi", "zzj", "zzk", "zzl"});
        }
        if (i2 == 3) {
            return new zzawc();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzawb(zzavrVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzm = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
