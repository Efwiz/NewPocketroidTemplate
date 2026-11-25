package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhi extends zzans<zzhi, zzhh> implements zzapg {
    private static final zzhi zzb;
    private int zze;
    private zzamk zzf = zzamk.zzb;
    private String zzg = "";
    private zzhl zzh;

    static {
        zzhi zzhiVar = new zzhi();
        zzb = zzhiVar;
        zzans.zzM(zzhi.class, zzhiVar);
    }

    private zzhi() {
    }

    public static zzhh zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzhi zzhiVar, zzamk zzamkVar) {
        zzamkVar.getClass();
        zzhiVar.zze |= 1;
        zzhiVar.zzf = zzamkVar;
    }

    static /* synthetic */ void zze(zzhi zzhiVar, String str) {
        str.getClass();
        zzhiVar.zze |= 2;
        zzhiVar.zzg = str;
    }

    static /* synthetic */ void zzf(zzhi zzhiVar, zzhl zzhlVar) {
        zzhlVar.getClass();
        zzhiVar.zzh = zzhlVar;
        zzhiVar.zze |= 4;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0004\u0003\u0000\u0000\u0000\u0001ဈ\u0001\u0002ည\u0000\u0004ဉ\u0002", new Object[]{"zze", "zzg", "zzf", "zzh"});
        }
        if (i2 == 3) {
            return new zzhi();
        }
        zzhg zzhgVar = null;
        if (i2 == 4) {
            return new zzhh(zzhgVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
