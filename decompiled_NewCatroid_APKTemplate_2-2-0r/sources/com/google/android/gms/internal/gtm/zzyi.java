package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzyi extends zzuj {
    private zzyi() {
        super(true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:17:0x003b  */
    @Override // com.google.android.gms.internal.gtm.zzuj
    public final <CT extends zzwk> zzux<CT, ?> zzc(CT ct, int i) {
        byte b;
        switch (ct.getClass().getName()) {
            case "com.google.android.gms.internal.gtm.zzak":
                b = 3;
                break;
            case "com.google.android.gms.internal.gtm.zztw":
                b = 0;
                break;
            case "com.google.android.gms.internal.gtm.zztz":
                b = 2;
                break;
            case "com.google.android.gms.internal.gtm.zzub":
                b = 1;
                break;
            default:
                b = -1;
                break;
        }
        if (b == 0) {
            switch (i) {
                case 21596320:
                    return zzyq.zzd;
                case 21623477:
                    return zzyq.zzb;
                case 21713708:
                    return zzyq.zza;
                case 23459630:
                    return zzyq.zzc;
                case 26652850:
                    return zzyq.zze;
                case 53697879:
                    return zzyq.zzf;
                case 56871503:
                    return zzyq.zzg;
                default:
                    return null;
            }
        }
        if (b == 1) {
            switch (i) {
                case 21467048:
                    return zzyq.zzk;
                case 21596320:
                    return zzyq.zzj;
                case 21623477:
                    return zzyq.zzi;
                case 21713708:
                    return zzyq.zzh;
                case 26652850:
                    return zzyq.zzl;
                default:
                    return null;
            }
        }
        if (b == 2) {
            if (i == 21596320) {
                return zzyq.zzm;
            }
            if (i != 28993747) {
                return null;
            }
            return zzyq.zzn;
        }
        if (b != 3) {
            return null;
        }
        if (i == 101) {
            return zzae.zza;
        }
        if (i != 47497405) {
            return null;
        }
        return zze.zza;
    }

    /* synthetic */ zzyi(zzyg zzygVar) {
        super(true);
    }
}
