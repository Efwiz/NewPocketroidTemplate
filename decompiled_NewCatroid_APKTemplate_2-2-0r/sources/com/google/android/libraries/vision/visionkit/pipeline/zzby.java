package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzby extends zzans<zzby, zzbw> implements zzapg {
    private static final zzby zzb;
    private int zze;
    private int zzf = -1;
    private float zzg = 0.3f;
    private int zzh = 5;
    private float zzi = 0.5f;
    private int zzj = 1;
    private boolean zzk = true;
    private float zzl = 0.85f;
    private boolean zzm = true;
    private float zzn;

    static {
        zzby zzbyVar = new zzby();
        zzb = zzbyVar;
        zzans.zzM(zzby.class, zzbyVar);
    }

    private zzby() {
    }

    public static zzbw zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzby zzbyVar, float f) {
        zzbyVar.zze |= 64;
        zzbyVar.zzl = 0.0f;
    }

    static /* synthetic */ void zze(zzby zzbyVar, boolean z) {
        zzbyVar.zze |= 128;
        zzbyVar.zzm = false;
    }

    static /* synthetic */ void zzf(zzby zzbyVar, int i) {
        zzbyVar.zze |= 4;
        zzbyVar.zzh = i;
    }

    static /* synthetic */ void zzg(zzby zzbyVar, float f) {
        zzbyVar.zze |= 8;
        zzbyVar.zzi = 0.2f;
    }

    static /* synthetic */ void zzh(zzby zzbyVar, int i) {
        zzbyVar.zzj = 2;
        zzbyVar.zze |= 16;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\t\u0000\u0001\u0001\u0011\t\u0000\u0000\u0000\u0001င\u0000\u0005ခ\u0001\u0006င\u0002\u0007ခ\u0003\fဌ\u0004\u000eဇ\u0005\u000fခ\u0006\u0010ဇ\u0007\u0011ခ\b", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzbx.zza, "zzk", "zzl", "zzm", "zzn"});
        }
        if (i2 == 3) {
            return new zzby();
        }
        zzbv zzbvVar = null;
        if (i2 == 4) {
            return new zzbw(zzbvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
