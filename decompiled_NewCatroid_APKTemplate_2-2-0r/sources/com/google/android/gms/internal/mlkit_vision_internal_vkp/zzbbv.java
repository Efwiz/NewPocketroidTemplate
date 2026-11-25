package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbbv extends zzans<zzbbv, zzbbu> implements zzapg {
    private static final zzbbv zzb;
    private int zze;
    private boolean zzh;
    private boolean zzp;
    private float zzf = 0.05f;
    private float zzg = 0.5f;
    private int zzi = 10;
    private int zzj = 200;
    private float zzk = 0.8f;
    private int zzl = 4;
    private int zzm = 10;
    private float zzn = 0.2f;
    private float zzo = 0.1f;

    static {
        zzbbv zzbbvVar = new zzbbv();
        zzb = zzbbvVar;
        zzans.zzM(zzbbv.class, zzbbvVar);
    }

    private zzbbv() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003င\u0003\u0004င\u0004\u0005င\u0006\u0006င\u0007\u0007ခ\b\bခ\t\tဇ\n\nခ\u0005\u000bဇ\u0002", new Object[]{"zze", "zzf", "zzg", "zzi", "zzj", "zzl", "zzm", "zzn", "zzo", "zzp", "zzk", "zzh"});
        }
        if (i2 == 3) {
            return new zzbbv();
        }
        zzbbt zzbbtVar = null;
        if (i2 == 4) {
            return new zzbbu(zzbbtVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
