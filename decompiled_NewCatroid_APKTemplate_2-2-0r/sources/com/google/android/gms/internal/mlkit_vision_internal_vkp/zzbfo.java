package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbfo extends zzans<zzbfo, zzbfn> implements zzapg {
    private static final zzbfo zzb;
    private String zze = "";
    private String zzf = "";
    private int zzg;

    static {
        zzbfo zzbfoVar = new zzbfo();
        zzb = zzbfoVar;
        zzans.zzM(zzbfo.class, zzbfoVar);
    }

    private zzbfo() {
    }

    public static zzbfn zze() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzi(zzbfo zzbfoVar, String str) {
        str.getClass();
        zzbfoVar.zze = str;
    }

    static /* synthetic */ void zzj(zzbfo zzbfoVar, String str) {
        str.getClass();
        zzbfoVar.zzf = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003\u0004", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzbfo();
        }
        zzbfm zzbfmVar = null;
        if (i2 == 4) {
            return new zzbfn(zzbfmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final int zzc() {
        return this.zzg;
    }

    public final String zzg() {
        return this.zze;
    }

    public final String zzh() {
        return this.zzf;
    }
}
