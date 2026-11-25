package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbav extends zzans<zzbav, zzbau> implements zzapg {
    private static final zzbav zzb;
    private int zze;
    private double zzf;
    private double zzg;
    private double zzi;
    private boolean zzj;
    private double zzk;
    private double zzl;
    private byte zzm = 2;
    private String zzh = "";

    static {
        zzbav zzbavVar = new zzbav();
        zzb = zzbavVar;
        zzans.zzM(zzbav.class, zzbavVar);
    }

    private zzbav() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzm);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0007\u0000\u0001\u0001\b\u0007\u0000\u0000\u0002\u0001ᔀ\u0000\u0002ᔀ\u0001\u0004က\u0005\u0005က\u0006\u0006ဇ\u0004\u0007က\u0003\bဈ\u0002", new Object[]{"zze", "zzf", "zzg", "zzk", "zzl", "zzj", "zzi", "zzh"});
        }
        if (i2 == 3) {
            return new zzbav();
        }
        zzbat zzbatVar = null;
        if (i2 == 4) {
            return new zzbau(zzbatVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzm = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
