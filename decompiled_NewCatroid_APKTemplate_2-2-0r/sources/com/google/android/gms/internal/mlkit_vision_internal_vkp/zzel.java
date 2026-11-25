package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzel extends zzans<zzel, zzek> implements zzapg {
    private static final zzel zzb;
    private int zze;
    private int zzg;
    private int zzh;
    private float zzj;
    private zzaob<zzamk> zzf = zzI();
    private String zzi = "";

    static {
        zzel zzelVar = new zzel();
        zzb = zzelVar;
        zzans.zzM(zzel.class, zzelVar);
    }

    private zzel() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001\u001c\u0002င\u0000\u0003င\u0001\u0004ဈ\u0002\u0005ခ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzel();
        }
        zzej zzejVar = null;
        if (i2 == 4) {
            return new zzek(zzejVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
