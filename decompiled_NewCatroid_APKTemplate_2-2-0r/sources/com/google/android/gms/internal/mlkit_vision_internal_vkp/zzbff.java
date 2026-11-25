package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbff extends zzans<zzbff, zzbfe> implements zzapg {
    private static final zzbff zzb;
    private int zzg;
    private String zze = "";
    private String zzf = "";
    private String zzh = "";

    static {
        zzbff zzbffVar = new zzbff();
        zzb = zzbffVar;
        zzans.zzM(zzbff.class, zzbffVar);
    }

    private zzbff() {
    }

    public static zzbfe zze() {
        return zzb.zzz();
    }

    public static zzbff zzg() {
        return zzb;
    }

    static /* synthetic */ void zzk(zzbff zzbffVar, String str) {
        str.getClass();
        zzbffVar.zze = str;
    }

    static /* synthetic */ void zzl(zzbff zzbffVar, String str) {
        str.getClass();
        zzbffVar.zzf = str;
    }

    static /* synthetic */ void zzn(zzbff zzbffVar, String str) {
        str.getClass();
        zzbffVar.zzh = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003\u0004\u0004Ȉ", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzbff();
        }
        zzbfd zzbfdVar = null;
        if (i2 == 4) {
            return new zzbfe(zzbfdVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final int zzc() {
        return this.zzg;
    }

    public final String zzh() {
        return this.zzh;
    }

    public final String zzi() {
        return this.zzf;
    }

    public final String zzj() {
        return this.zze;
    }
}
