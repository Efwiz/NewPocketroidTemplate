package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbet extends zzans<zzbet, zzbes> implements zzapg {
    private static final zzbet zzb;
    private zzbek zze;
    private double zzf;
    private double zzg;

    static {
        zzbet zzbetVar = new zzbet();
        zzb = zzbetVar;
        zzans.zzM(zzbet.class, zzbetVar);
    }

    private zzbet() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0002\u0000\u0003\u0000", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzbet();
        }
        zzber zzberVar = null;
        if (i2 == 4) {
            return new zzbes(zzberVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
