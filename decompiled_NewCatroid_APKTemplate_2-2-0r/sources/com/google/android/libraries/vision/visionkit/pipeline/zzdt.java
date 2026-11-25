package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdt extends zzans<zzdt, zzds> implements zzapg {
    private static final zzdt zzb;
    private int zze;
    private String zzf = "";
    private int zzg;
    private float zzh;
    private long zzi;
    private boolean zzj;
    private float zzk;
    private float zzl;
    private long zzm;
    private int zzn;
    private long zzo;

    static {
        zzdt zzdtVar = new zzdt();
        zzb = zzdtVar;
        zzans.zzM(zzdt.class, zzdtVar);
    }

    private zzdt() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003ခ\u0002\u0004ဂ\u0003\u0005ဇ\u0004\u0006ခ\u0005\u0007ခ\u0006\bဂ\u0007\tင\b\nဂ\t", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo"});
        }
        if (i2 == 3) {
            return new zzdt();
        }
        zzdq zzdqVar = null;
        if (i2 == 4) {
            return new zzds(zzdqVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
