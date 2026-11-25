package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zziu extends RuntimeException {
    private final List<String> zza;

    public zziu(zzhl zzhlVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.zza = null;
    }
}
