package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbfi extends zzans<zzbfi, zzbfh> implements zzapg {
    private static final zzbfi zzb;
    private zzbfl zze;
    private zzbff zzf;
    private String zzg = "";

    static {
        zzbfi zzbfiVar = new zzbfi();
        zzb = zzbfiVar;
        zzans.zzM(zzbfi.class, zzbfiVar);
    }

    private zzbfi() {
    }

    public static zzbfh zze() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzi(zzbfi zzbfiVar, zzbfl zzbflVar) {
        zzbflVar.getClass();
        zzbfiVar.zze = zzbflVar;
    }

    static /* synthetic */ void zzj(zzbfi zzbfiVar, zzbff zzbffVar) {
        zzbffVar.getClass();
        zzbfiVar.zzf = zzbffVar;
    }

    static /* synthetic */ void zzk(zzbfi zzbfiVar, String str) {
        str.getClass();
        zzbfiVar.zzg = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0002\t\u0003Ȉ", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzbfi();
        }
        zzbfg zzbfgVar = null;
        if (i2 == 4) {
            return new zzbfh(zzbfgVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzbff zzc() {
        zzbff zzbffVar = this.zzf;
        return zzbffVar == null ? zzbff.zzg() : zzbffVar;
    }

    public final zzbfl zzg() {
        zzbfl zzbflVar = this.zze;
        return zzbflVar == null ? zzbfl.zzf() : zzbflVar;
    }

    public final String zzh() {
        return this.zzg;
    }
}
