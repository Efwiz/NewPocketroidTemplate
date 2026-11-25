package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzalg extends zzans<zzalg, zzalf> implements zzapg {
    private static final zzalg zzb;
    private int zze;
    private float zzf;
    private float zzg;
    private float zzh;
    private boolean zzm;
    private float zzn;
    private float zzo;
    private byte zzp = 2;
    private String zzi = "";
    private int zzj = -1;
    private float zzk = -1.0f;
    private float zzl = -1.0f;

    static {
        zzalg zzalgVar = new zzalg();
        zzb = zzalgVar;
        zzans.zzM(zzalg.class, zzalgVar);
    }

    private zzalg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzp);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\n\u0000\u0001\u0001\u000b\n\u0000\u0000\u0001\u0001ᔁ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ဈ\u0003\u0005င\u0004\u0007ခ\u0005\bခ\u0006\tဇ\u0007\nခ\b\u000bခ\t", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo"});
        }
        if (i2 == 3) {
            return new zzalg();
        }
        zzale zzaleVar = null;
        if (i2 == 4) {
            return new zzalf(zzaleVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzp = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
