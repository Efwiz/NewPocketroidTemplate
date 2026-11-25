package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.android.gms.common.internal.LibraryVersion;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzem implements Callable {
    static final Callable zza = new zzem();

    private zzem() {
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return LibraryVersion.getInstance().getVersion("pose-detection-common");
    }
}
