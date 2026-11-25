package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfd extends zzans<zzfd, zzfc> implements zzapg {
    private static final zzfd zzb;
    private int zze;
    private int zzf = 1;
    private boolean zzg;

    static {
        zzfd zzfdVar = new zzfd();
        zzb = zzfdVar;
        zzans.zzM(zzfd.class, zzfdVar);
    }

    private zzfd() {
    }

    public static zzfc zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzfd zzfdVar, boolean z) {
        zzfdVar.zze |= 2;
        zzfdVar.zzg = true;
    }

    static /* synthetic */ void zze(zzfd zzfdVar, int i) {
        zzfdVar.zzf = i - 1;
        zzfdVar.zze |= 1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001", new Object[]{"zze", "zzf", zzn.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzfd();
        }
        zzfb zzfbVar = null;
        if (i2 == 4) {
            return new zzfc(zzfbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
