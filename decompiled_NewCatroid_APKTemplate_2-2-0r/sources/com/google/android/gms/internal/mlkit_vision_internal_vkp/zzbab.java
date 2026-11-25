package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbab extends zzans<zzbab, zzbaa> implements zzapg {
    private static final zzbab zzb;
    private int zze;
    private int zzf = 0;
    private Object zzg;

    static {
        zzbab zzbabVar = new zzbab();
        zzb = zzbabVar;
        zzans.zzM(zzbab.class, zzbabVar);
    }

    private zzbab() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0001\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ြ\u0000\u0002ြ\u0000", new Object[]{"zzg", "zzf", "zze", zzazt.class, zzazp.class});
        }
        if (i2 == 3) {
            return new zzbab();
        }
        zzazz zzazzVar = null;
        if (i2 == 4) {
            return new zzbaa(zzazzVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
