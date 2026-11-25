package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhl extends zzans<zzhl, zzhk> implements zzapg {
    private static final zzhl zzb;
    private int zze;
    private int zzf;
    private long zzg;
    private long zzh;

    static {
        zzhl zzhlVar = new zzhl();
        zzb = zzhlVar;
        zzans.zzM(zzhl.class, zzhlVar);
    }

    private zzhl() {
    }

    public static zzhk zze() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzg(zzhl zzhlVar, int i) {
        zzhlVar.zze |= 1;
        zzhlVar.zzf = i;
    }

    static /* synthetic */ void zzh(zzhl zzhlVar, long j) {
        zzhlVar.zze |= 2;
        zzhlVar.zzg = j;
    }

    static /* synthetic */ void zzi(zzhl zzhlVar, long j) {
        zzhlVar.zze |= 4;
        zzhlVar.zzh = j;
    }

    public final int zza() {
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001င\u0000\u0002ဂ\u0001\u0003ဂ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzhl();
        }
        zzhj zzhjVar = null;
        if (i2 == 4) {
            return new zzhk(zzhjVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final long zzc() {
        return this.zzg;
    }

    public final long zzd() {
        return this.zzh;
    }
}
