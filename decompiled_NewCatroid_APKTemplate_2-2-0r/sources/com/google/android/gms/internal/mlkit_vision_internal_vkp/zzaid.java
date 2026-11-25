package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaid extends zzans<zzaid, zzaic> implements zzapg {
    private static final zzaid zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh = 2;
    private float zzi;
    private boolean zzj;

    static {
        zzaid zzaidVar = new zzaid();
        zzb = zzaidVar;
        zzans.zzM(zzaid.class, zzaidVar);
    }

    private zzaid() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ခ\u0003\u0005ဇ\u0004", new Object[]{"zze", "zzf", zzaib.zza, "zzg", zzaih.zza, "zzh", zzaig.zza, "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzaid();
        }
        zzahu zzahuVar = null;
        if (i2 == 4) {
            return new zzaic(zzahuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
