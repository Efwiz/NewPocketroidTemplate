package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzho extends zzans<zzho, zzhn> implements zzapg {
    private static final zzho zzb;
    private int zze;
    private Object zzg;
    private boolean zzh;
    private float zzl;
    private float zzm;
    private float zzn;
    private int zzf = 0;
    private byte zzo = 2;
    private zzaob<zzhf> zzi = zzI();
    private zzanx zzj = zzF();
    private float zzk = 0.15f;

    static {
        zzho zzhoVar = new zzho();
        zzb = zzhoVar;
        zzans.zzM(zzho.class, zzhoVar);
    }

    private zzho() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzo);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\b\u0001\u0001\u0002\u000b\b\u0000\u0002\u0001\u0002ᐼ\u0000\u0003ဇ\u0000\u0004\u001b\u0005\u0013\u0006ခ\u0002\u0007ခ\u0003\bခ\u0004\u000bခ\u0005", new Object[]{"zzg", "zzf", "zze", zzij.class, "zzh", "zzi", zzhf.class, "zzj", "zzk", "zzl", "zzm", "zzn"});
        }
        if (i2 == 3) {
            return new zzho();
        }
        zzhm zzhmVar = null;
        if (i2 == 4) {
            return new zzhn(zzhmVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzo = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
