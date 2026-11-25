package com.google.android.gms.internal.mlkit_vision_face;

import android.content.Context;
import com.google.android.gms.common.internal.TelemetryLogging;
import com.google.android.gms.common.internal.TelemetryLoggingClient;
import com.google.android.gms.common.internal.TelemetryLoggingOptions;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkr {
    private final TelemetryLoggingClient zza;
    private final AtomicLong zzc = new AtomicLong(-1);
    private final zzle zzb = zzle.zza();

    zzkr(Context context, String str) {
        this.zza = TelemetryLogging.getClient(context, TelemetryLoggingOptions.builder().setApi("mlkit:vision").build());
    }

    public static zzkr zza(Context context) {
        return new zzkr(context, "mlkit:vision");
    }

    public final synchronized void zzb(int i, int i2, long j, long j2) {
    }
}
