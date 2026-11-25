package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzazr extends zzans<zzazr, zzazq> implements zzapg {
    private static final zzazr zzb;
    private zzanx zze = zzF();
    private zzanx zzf = zzF();
    private zzanx zzg = zzF();
    private zzanx zzh = zzF();
    private zzanx zzi = zzF();
    private zzanx zzj = zzF();

    static {
        zzazr zzazrVar = new zzazr();
        zzb = zzazrVar;
        zzans.zzM(zzazr.class, zzazrVar);
    }

    private zzazr() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0006\u0000\u0001\u0013\u0002\u0013\u0003\u0013\u0004\u0013\u0005\u0013\u0006\u0013", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzazr();
        }
        zzazn zzaznVar = null;
        if (i2 == 4) {
            return new zzazq(zzaznVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
