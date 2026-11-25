package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzan extends zzans<zzan, zzal> implements zzapg {
    private static final zzan zzb;
    private int zze;
    private int zzf;
    private float zzg = 1.0f;

    static {
        zzan zzanVar = new zzan();
        zzb = zzanVar;
        zzans.zzM(zzan.class, zzanVar);
    }

    private zzan() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001", new Object[]{"zze", "zzf", zzam.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzan();
        }
        zzak zzakVar = null;
        if (i2 == 4) {
            return new zzal(zzakVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
