package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzfr {
    private static volatile boolean zza = false;
    private static boolean zzb = true;
    private static volatile zzfr zzc;
    private static final zzfr zzd = new zzfr(true);
    private final Map<Object, Object> zze;

    public static zzfr zza() {
        zzfr zzfrVar = zzc;
        if (zzfrVar == null) {
            synchronized (zzfr.class) {
                zzfrVar = zzc;
                if (zzfrVar == null) {
                    zzfrVar = zzd;
                    zzc = zzfrVar;
                }
            }
        }
        return zzfrVar;
    }

    zzfr() {
        this.zze = new HashMap();
    }

    private zzfr(boolean z) {
        this.zze = Collections.emptyMap();
    }
}
