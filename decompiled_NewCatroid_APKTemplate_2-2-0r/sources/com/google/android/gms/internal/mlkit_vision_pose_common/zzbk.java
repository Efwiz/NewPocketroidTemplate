package com.google.android.gms.internal.mlkit_vision_pose_common;

import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbk extends zzbn {
    public static int zza(int i, int i2, int i3) {
        if (!(i2 <= 1073741823)) {
            throw new IllegalArgumentException(zzg.zza("min (%s) must be less than or equal to max (%s)", Integer.valueOf(i2), Integer.valueOf(LockFreeTaskQueueCore.MAX_CAPACITY_MASK)));
        }
        return Math.min(Math.max(i, i2), LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }
}
