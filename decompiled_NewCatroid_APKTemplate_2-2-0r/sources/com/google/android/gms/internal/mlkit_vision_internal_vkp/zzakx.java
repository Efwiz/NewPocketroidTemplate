package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzakx extends zzans<zzakx, zzakw> implements zzapg {
    private static final zzakx zzb;
    private int zze;
    private String zzf = "";
    private double zzg = 1.0d;
    private zzaob<String> zzh = zzans.zzI();

    static {
        zzakx zzakxVar = new zzakx();
        zzb = zzakxVar;
        zzans.zzM(zzakx.class, zzakxVar);
    }

    private zzakx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u000f\u0003\u0000\u0001\u0000\u0001ဈ\u0000\u0002က\u0001\u000f\u001a", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzakx();
        }
        zzakv zzakvVar = null;
        if (i2 == 4) {
            return new zzakw(zzakvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
