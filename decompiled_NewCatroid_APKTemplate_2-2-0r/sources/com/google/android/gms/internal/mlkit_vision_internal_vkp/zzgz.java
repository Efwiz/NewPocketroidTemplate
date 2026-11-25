package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzgz extends zzans<zzgz, zzgy> implements zzapg {
    private static final zzgz zzb;
    private int zze;
    private String zzf = "";
    private float zzg;

    static {
        zzgz zzgzVar = new zzgz();
        zzb = zzgzVar;
        zzans.zzM(zzgz.class, zzgzVar);
    }

    private zzgz() {
    }

    public static zzgy zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzgz zzgzVar, String str) {
        zzgzVar.zze |= 1;
        zzgzVar.zzf = "/m/0bl9f";
    }

    static /* synthetic */ void zze(zzgz zzgzVar, float f) {
        zzgzVar.zze |= 2;
        zzgzVar.zzg = 0.46f;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ခ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzgz();
        }
        zzgx zzgxVar = null;
        if (i2 == 4) {
            return new zzgy(zzgxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
