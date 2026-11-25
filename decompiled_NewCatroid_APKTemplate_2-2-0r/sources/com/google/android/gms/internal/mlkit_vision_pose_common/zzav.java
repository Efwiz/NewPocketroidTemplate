package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzav {
    static void zza(Iterator<?> it) {
        zzh.zza(it);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }
}
