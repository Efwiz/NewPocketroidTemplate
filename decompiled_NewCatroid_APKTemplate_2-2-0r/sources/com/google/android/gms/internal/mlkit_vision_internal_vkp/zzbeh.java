package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbeh extends zzans<zzbeh, zzbeg> implements zzapg {
    private static final zzbeh zzb;
    private zzbq zze;
    private zzaob<zzbcu> zzf = zzI();

    static {
        zzbeh zzbehVar = new zzbeh();
        zzb = zzbehVar;
        zzans.zzM(zzbeh.class, zzbehVar);
    }

    private zzbeh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001\t\u0002\u001b", new Object[]{"zze", "zzf", zzbcu.class});
        }
        if (i2 == 3) {
            return new zzbeh();
        }
        zzbef zzbefVar = null;
        if (i2 == 4) {
            return new zzbeg(zzbefVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
