package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbew extends zzans<zzbew, zzbev> implements zzapg {
    private static final zzbew zzb;
    private zzaob<zzbet> zze = zzI();

    static {
        zzbew zzbewVar = new zzbew();
        zzb = zzbewVar;
        zzans.zzM(zzbew.class, zzbewVar);
    }

    private zzbew() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzbet.class});
        }
        if (i2 == 3) {
            return new zzbew();
        }
        zzbeu zzbeuVar = null;
        if (i2 == 4) {
            return new zzbev(zzbeuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
