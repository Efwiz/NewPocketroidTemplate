package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import kotlin.time.DurationKt;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjp extends zzans<zzjp, zzjo> implements zzapg {
    private static final zzjp zzb;
    private int zze;
    private zzbcd zzr;
    private long zzf = 1000000000;
    private float zzg = 0.2f;
    private float zzh = 0.6f;
    private float zzi = 0.6f;
    private float zzj = 0.5f;
    private int zzk = 3;
    private float zzl = -0.5f;
    private float zzm = -0.5f;
    private int zzn = DurationKt.NANOS_IN_MILLIS;
    private float zzo = 10.0f;
    private float zzp = 0.8f;
    private float zzq = 1.5f;
    private float zzs = 0.15f;
    private float zzt = 0.5f;
    private float zzu = 0.3f;
    private float zzv = 3.0f;
    private float zzw = 3.0f;
    private int zzx = 5;
    private int zzy = 5;
    private float zzz = 0.5f;

    static {
        zzjp zzjpVar = new zzjp();
        zzb = zzjpVar;
        zzans.zzM(zzjp.class, zzjpVar);
    }

    private zzjp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0015\u0000\u0001\u0001\u0015\u0015\u0000\u0000\u0000\u0001ဂ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003\u0005ခ\u0004\u0006င\u0005\u0007ခ\u0006\bခ\u0007\tင\b\nခ\t\u000bခ\n\fဉ\f\rခ\u000b\u000eခ\r\u000fခ\u000e\u0010ခ\u000f\u0011ခ\u0010\u0012ခ\u0011\u0013င\u0012\u0014င\u0013\u0015ခ\u0014", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzr", "zzq", "zzs", "zzt", "zzu", "zzv", "zzw", "zzx", "zzy", "zzz"});
        }
        if (i2 == 3) {
            return new zzjp();
        }
        zzjn zzjnVar = null;
        if (i2 == 4) {
            return new zzjo(zzjnVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
