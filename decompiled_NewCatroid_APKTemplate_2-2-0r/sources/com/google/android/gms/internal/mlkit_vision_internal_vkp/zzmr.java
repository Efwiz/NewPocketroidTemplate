package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzmr extends zzans<zzmr, zzmo> implements zzapg {
    private static final zzanz<Integer, Object> zzb = new zzml();
    private static final zzanz<Integer, Object> zze = new zzmm();
    private static final zzanz<Integer, Object> zzf = new zzmn();
    private static final zzmr zzg;
    private int zzh;
    private long zzi;
    private zzany zzj = zzG();
    private zzany zzk = zzG();
    private zzany zzl = zzG();
    private String zzm = "";
    private int zzn;
    private int zzo;

    static {
        zzmr zzmrVar = new zzmr();
        zzg = zzmrVar;
        zzans.zzM(zzmr.class, zzmrVar);
    }

    private zzmr() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzg, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0003\u0000\u0001ဃ\u0000\u0002\u001e\u0003\u001e\u0004\u001e\u0005ဈ\u0001\u0006ဌ\u0002\u0007င\u0003", new Object[]{"zzh", "zzi", "zzj", zzmq.zza, "zzk", zzmq.zza, "zzl", zzmq.zza, "zzm", "zzn", zzmp.zza, "zzo"});
        }
        if (i2 == 3) {
            return new zzmr();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzmo(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzg;
    }
}
