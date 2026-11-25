package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zztm extends zzans<zztm, zztl> implements zzapg {
    private static final zztm zzb;
    private int zze;
    private zzti zzf;
    private int zzg;
    private long zzh;
    private long zzi;
    private zzaob<zzuo> zzj = zzI();

    static {
        zztm zztmVar = new zztm();
        zzb = zztmVar;
        zzans.zzM(zztm.class, zztmVar);
    }

    private zztm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဃ\u0002\u0004ဃ\u0003\u0005\u001b", new Object[]{"zze", "zzf", "zzg", zzqb.zza, "zzh", "zzi", "zzj", zzuo.class});
        }
        if (i2 == 3) {
            return new zztm();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zztl(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
