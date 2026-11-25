package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhs extends zzans<zzhs, zzhr> implements zzapg {
    private static final zzhs zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private String zzi = "";
    private String zzj = "";

    static {
        zzhs zzhsVar = new zzhs();
        zzb = zzhsVar;
        zzans.zzM(zzhs.class, zzhsVar);
    }

    private zzhs() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဋ\u0000\u0002ဋ\u0001\u0003ဋ\u0002\u0004ဈ\u0003\u0005ဈ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzhs();
        }
        zzhp zzhpVar = null;
        if (i2 == 4) {
            return new zzhr(zzhpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
