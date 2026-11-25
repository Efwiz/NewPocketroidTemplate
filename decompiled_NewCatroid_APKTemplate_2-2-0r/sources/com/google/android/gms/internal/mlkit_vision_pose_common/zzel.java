package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.mlkit.common.sdkinternal.SharedPrefManager;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzel implements Callable {
    private final SharedPrefManager zza;

    private zzel(SharedPrefManager sharedPrefManager) {
        this.zza = sharedPrefManager;
    }

    static Callable zza(SharedPrefManager sharedPrefManager) {
        return new zzel(sharedPrefManager);
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return this.zza.getMlSdkInstanceId();
    }
}
