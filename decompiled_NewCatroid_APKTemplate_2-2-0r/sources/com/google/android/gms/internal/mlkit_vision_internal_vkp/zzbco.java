package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbco extends zzans<zzbco, zzbcn> implements zzapg {
    private static final zzbco zzb;
    private String zze = "";
    private zzaob<zzbcu> zzf = zzI();

    static {
        zzbco zzbcoVar = new zzbco();
        zzb = zzbcoVar;
        zzans.zzM(zzbco.class, zzbcoVar);
    }

    private zzbco() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ȉ\u0002\u001b", new Object[]{"zze", "zzf", zzbcu.class});
        }
        if (i2 == 3) {
            return new zzbco();
        }
        zzbcm zzbcmVar = null;
        if (i2 == 4) {
            return new zzbcn(zzbcmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
