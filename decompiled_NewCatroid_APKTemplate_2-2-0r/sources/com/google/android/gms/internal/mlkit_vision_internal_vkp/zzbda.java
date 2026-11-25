package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbda extends zzans<zzbda, zzbcz> implements zzapg {
    private static final zzbda zzb;
    private String zze = "";
    private zzaob<zzbcx> zzf = zzI();
    private String zzg = "";

    static {
        zzbda zzbdaVar = new zzbda();
        zzb = zzbdaVar;
        zzans.zzM(zzbda.class, zzbdaVar);
    }

    private zzbda() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001Ȉ\u0002\u001b\u0003Ȉ", new Object[]{"zze", "zzf", zzbcx.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzbda();
        }
        zzbcy zzbcyVar = null;
        if (i2 == 4) {
            return new zzbcz(zzbcyVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
