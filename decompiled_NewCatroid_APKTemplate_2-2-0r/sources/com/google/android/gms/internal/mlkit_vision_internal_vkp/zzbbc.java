package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbbc extends zzans<zzbbc, zzbbb> implements zzapg {
    private static final zzbbc zzb;
    private int zze;
    private int zzf;
    private int zzh;
    private boolean zzi;
    private byte zzj = 2;
    private zzaob<zzbbg> zzg = zzI();

    static {
        zzbbc zzbbcVar = new zzbbc();
        zzb = zzbbcVar;
        zzans.zzM(zzbbc.class, zzbbcVar);
    }

    private zzbbc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzj);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0003\u0006\u0004\u0000\u0001\u0002\u0003ᔄ\u0000\u0004Л\u0005င\u0001\u0006ဇ\u0002", new Object[]{"zze", "zzf", "zzg", zzbbg.class, "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzbbc();
        }
        zzbaz zzbazVar = null;
        if (i2 == 4) {
            return new zzbbb(zzbazVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzj = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
