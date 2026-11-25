package com.google.android.gms.internal.gtm;

import android.content.Context;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsb {
    public static final Integer zza = 0;
    public static final Integer zzb = 1;
    private final Context zzc;
    private final ExecutorService zzd;

    public zzsb(Context context) {
        ExecutorService executorServiceZza = zzfz.zza().zza(2);
        this.zzc = context;
        this.zzd = executorServiceZza;
    }
}
