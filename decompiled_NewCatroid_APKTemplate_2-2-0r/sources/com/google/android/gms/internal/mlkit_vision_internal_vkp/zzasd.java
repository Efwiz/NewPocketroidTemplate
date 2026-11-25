package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzasd extends zzans<zzasd, zzasb> implements zzapg {
    private static final zzasd zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private zzaob<String> zzh = zzans.zzI();
    private int zzi;

    static {
        zzasd zzasdVar = new zzasd();
        zzb = zzasdVar;
        zzans.zzM(zzasd.class, zzasdVar);
    }

    private zzasd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဌ\u0000\u0002င\u0001\u0003\u001a\u0004င\u0002", new Object[]{"zze", "zzf", zzasc.zza, "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzasd();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzasb(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
