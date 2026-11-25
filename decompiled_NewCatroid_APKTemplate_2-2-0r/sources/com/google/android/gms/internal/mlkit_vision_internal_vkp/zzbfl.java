package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbfl extends zzans<zzbfl, zzbfk> implements zzapg {
    private static final zzbfl zzb;
    private String zze = "";
    private String zzf = "";
    private String zzg = "";
    private String zzh = "";
    private String zzi = "";

    static {
        zzbfl zzbflVar = new zzbfl();
        zzb = zzbflVar;
        zzans.zzM(zzbfl.class, zzbflVar);
    }

    private zzbfl() {
    }

    public static zzbfk zzc() {
        return zzb.zzz();
    }

    public static zzbfl zzf() {
        return zzb;
    }

    static /* synthetic */ void zzl(zzbfl zzbflVar, String str) {
        str.getClass();
        zzbflVar.zze = str;
    }

    static /* synthetic */ void zzm(zzbfl zzbflVar, String str) {
        str.getClass();
        zzbflVar.zzh = str;
    }

    static /* synthetic */ void zzn(zzbfl zzbflVar, String str) {
        str.getClass();
        zzbflVar.zzi = str;
    }

    static /* synthetic */ void zzo(zzbfl zzbflVar, String str) {
        str.getClass();
        zzbflVar.zzf = str;
    }

    static /* synthetic */ void zzp(zzbfl zzbflVar, String str) {
        str.getClass();
        zzbflVar.zzg = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzbfl();
        }
        zzbfj zzbfjVar = null;
        if (i2 == 4) {
            return new zzbfk(zzbfjVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final String zzg() {
        return this.zzi;
    }

    public final String zzh() {
        return this.zze;
    }

    public final String zzi() {
        return this.zzh;
    }

    public final String zzj() {
        return this.zzg;
    }

    public final String zzk() {
        return this.zzf;
    }
}
