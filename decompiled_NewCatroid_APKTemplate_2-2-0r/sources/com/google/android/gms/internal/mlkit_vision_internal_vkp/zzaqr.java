package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaqr extends zzans<zzaqr, zzaqq> implements zzapg {
    private static final zzaqr zzb;
    private long zze;
    private int zzf;

    static {
        zzaqr zzaqrVar = new zzaqr();
        zzb = zzaqrVar;
        zzans.zzM(zzaqr.class, zzaqrVar);
    }

    private zzaqr() {
    }

    public static zzaqq zze() {
        return zzb.zzz();
    }

    public static zzaqr zzg() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return new zzapq(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzaqr();
        }
        zzaqp zzaqpVar = null;
        if (i2 == 4) {
            return new zzaqq(zzaqpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final long zzc() {
        return this.zze;
    }
}
