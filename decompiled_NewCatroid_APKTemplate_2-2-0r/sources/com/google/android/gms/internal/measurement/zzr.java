package com.google.android.gms.internal.measurement;

import androidx.core.app.NotificationCompat;
import java.util.List;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.0.2 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzr extends zzal {
    private final zzv zzk;

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        return zzaq.zzc;
    }

    public zzr(zzv zzvVar) {
        super("internal.logger");
        this.zzk = zzvVar;
        this.zzb.put(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, new zzu(this, false, true));
        this.zzb.put(NotificationCompat.GROUP_KEY_SILENT, new zzq(this, NotificationCompat.GROUP_KEY_SILENT));
        ((zzal) this.zzb.get(NotificationCompat.GROUP_KEY_SILENT)).zza(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, new zzu(this, true, true));
        this.zzb.put("unmonitored", new zzt(this, "unmonitored"));
        ((zzal) this.zzb.get("unmonitored")).zza(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, new zzu(this, false, false));
    }
}
