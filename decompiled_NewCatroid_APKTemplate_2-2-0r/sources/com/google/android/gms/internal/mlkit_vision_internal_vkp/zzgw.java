package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzgw extends zzans<zzgw, zzgv> implements zzapg {
    private static final zzgw zzb;
    private int zze;
    private int zzf;
    private float zzg;
    private String zzh = "";
    private String zzi = "";

    static {
        zzgw zzgwVar = new zzgw();
        zzb = zzgwVar;
        zzans.zzM(zzgw.class, zzgwVar);
    }

    private zzgw() {
    }

    public final float zza() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ခ\u0001\u0003ဈ\u0002\u0004ဈ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzgw();
        }
        zzgu zzguVar = null;
        if (i2 == 4) {
            return new zzgv(zzguVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final int zzc() {
        return this.zzf;
    }

    public final String zze() {
        return this.zzi;
    }

    public final String zzf() {
        return this.zzh;
    }
}
