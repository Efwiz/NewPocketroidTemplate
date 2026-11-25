package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbk extends zzans<zzbk, zzbj> implements zzapg {
    private static final zzbk zzb;
    private int zze;
    private float zzi;
    private byte zzj = 2;
    private String zzf = "";
    private String zzg = "";
    private zzaob<zzakg> zzh = zzI();

    static {
        zzbk zzbkVar = new zzbk();
        zzb = zzbkVar;
        zzans.zzM(zzbk.class, zzbkVar);
    }

    private zzbk() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzj);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0001\u0001ဈ\u0000\u0002ဈ\u0001\u0003Л\u0004ခ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh", zzakg.class, "zzi"});
        }
        if (i2 == 3) {
            return new zzbk();
        }
        zzbi zzbiVar = null;
        if (i2 == 4) {
            return new zzbj(zzbiVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzj = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
