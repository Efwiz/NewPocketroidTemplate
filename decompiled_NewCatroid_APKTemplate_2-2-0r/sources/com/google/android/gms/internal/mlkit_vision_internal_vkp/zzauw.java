package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzauw extends zzans<zzauw, zzauv> implements zzapg {
    private static final zzauw zzb;
    private int zze;
    private int zzg;
    private long zzi;
    private String zzf = "";
    private String zzh = "";

    static {
        zzauw zzauwVar = new zzauw();
        zzb = zzauwVar;
        zzans.zzM(zzauw.class, zzauwVar);
    }

    private zzauw() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003ဈ\u0002\u0004ဂ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzauw();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzauv(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final long zzc() {
        return this.zzi;
    }

    public final String zzf() {
        return this.zzf;
    }

    public final String zzg() {
        return this.zzh;
    }
}
