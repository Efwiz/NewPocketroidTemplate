package com.google.android.libraries.vision.visionkit.pipeline;

import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcp extends zzans<zzcp, zzco> implements zzapg {
    private static final zzcp zzb;
    private int zze;
    private String zzf = "";
    private long zzg;

    static {
        zzcp zzcpVar = new zzcp();
        zzb = zzcpVar;
        zzans.zzM(zzcp.class, zzcpVar);
    }

    private zzcp() {
    }

    public static zzco zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzcp zzcpVar, String str) {
        zzcpVar.zze |= 1;
        zzcpVar.zzf = "MobileObjectLocalizerV3_1TfLiteClient";
    }

    static /* synthetic */ void zze(zzcp zzcpVar, long j) {
        zzcpVar.zze |= 2;
        zzcpVar.zzg = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzcp();
        }
        zzcm zzcmVar = null;
        if (i2 == 4) {
            return new zzco(zzcmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
