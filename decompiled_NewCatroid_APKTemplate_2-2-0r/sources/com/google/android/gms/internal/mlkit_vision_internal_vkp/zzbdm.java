package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdm extends zzans<zzbdm, zzbdl> implements zzapg {
    private static final zzbdm zzb;
    private zzanx zze = zzF();

    static {
        zzbdm zzbdmVar = new zzbdm();
        zzb = zzbdmVar;
        zzans.zzM(zzbdm.class, zzbdmVar);
    }

    private zzbdm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001$", new Object[]{"zze"});
        }
        if (i2 == 3) {
            return new zzbdm();
        }
        zzbdk zzbdkVar = null;
        if (i2 == 4) {
            return new zzbdl(zzbdkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
