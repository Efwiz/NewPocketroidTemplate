package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzm extends zzuz<zzm, zzl> implements zzwl {
    private static final zzm zza;
    private byte zzg = 2;
    private zzvh<zzk> zze = zzag();
    private zzvh<zzg> zzf = zzag();

    static {
        zzm zzmVar = new zzm();
        zza = zzmVar;
        zzuz.zzak(zzm.class, zzmVar);
    }

    private zzm() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzg);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0002\u0002\u0001Л\u0002Л", new Object[]{"zze", zzk.class, "zzf", zzg.class});
        }
        if (i2 == 3) {
            return new zzm();
        }
        zzc zzcVar = null;
        if (i2 == 4) {
            return new zzl(zzcVar);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzg = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
