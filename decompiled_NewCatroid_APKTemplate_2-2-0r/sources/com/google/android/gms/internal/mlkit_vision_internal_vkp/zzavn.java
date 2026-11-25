package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavn extends zzans<zzavn, zzavl> implements zzapg {
    private static final zzavn zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private zzanx zzh = zzF();
    private int zzi;

    static {
        zzavn zzavnVar = new zzavn();
        zzb = zzavnVar;
        zzans.zzM(zzavn.class, zzavnVar);
    }

    private zzavn() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001င\u0000\u0002င\u0001\u0003$\u0004ဌ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", zzavm.zza});
        }
        if (i2 == 3) {
            return new zzavn();
        }
        zzavk zzavkVar = null;
        if (i2 == 4) {
            return new zzavl(zzavkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
