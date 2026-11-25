package com.google.android.gms.internal.cast;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzed extends Handler {
    private final Looper zza;

    public zzed() {
        this.zza = Looper.getMainLooper();
    }

    public zzed(Looper looper) {
        super(looper);
        this.zza = Looper.getMainLooper();
    }
}
