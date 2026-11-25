package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbah extends zzans<zzbah, zzbag> implements zzapg {
    private static final zzbah zzb;
    private int zze;
    private int zzf;
    private long zzg;
    private long zzh;

    static {
        zzbah zzbahVar = new zzbah();
        zzb = zzbahVar;
        zzans.zzM(zzbah.class, zzbahVar);
    }

    private zzbah() {
    }

    public static zzbag zzc() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzf(zzbah zzbahVar, int i) {
        zzbahVar.zze |= 1;
        zzbahVar.zzf = i;
    }

    static /* synthetic */ void zzg(zzbah zzbahVar, long j) {
        zzbahVar.zze |= 2;
        zzbahVar.zzg = j;
    }

    static /* synthetic */ void zzh(zzbah zzbahVar, long j) {
        zzbahVar.zze |= 4;
        zzbahVar.zzh = j;
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
            return new zzbah();
        }
        zzbac zzbacVar = null;
        if (i2 == 4) {
            return new zzbag(zzbacVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
