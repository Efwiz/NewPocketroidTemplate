package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhu extends zzans<zzhu, zzht> implements zzapg {
    private static final zzhu zzb;
    private zzanx zze = zzF();

    static {
        zzhu zzhuVar = new zzhu();
        zzb = zzhuVar;
        zzans.zzM(zzhu.class, zzhuVar);
    }

    private zzhu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001$", new Object[]{"zze"});
        }
        if (i2 == 3) {
            return new zzhu();
        }
        zzhp zzhpVar = null;
        if (i2 == 4) {
            return new zzht(zzhpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
