package com.google.android.gms.internal.gtm;

import com.google.android.gms.common.internal.Preconditions;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaz extends com.google.android.gms.analytics.zzj<zzaz> {
    private final Map<String, Object> zza = new HashMap();

    public final String toString() {
        return zza(this.zza);
    }

    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(com.google.android.gms.analytics.zzj zzjVar) {
        zzaz zzazVar = (zzaz) zzjVar;
        Preconditions.checkNotNull(zzazVar);
        zzazVar.zza.putAll(this.zza);
    }

    public final Map<String, Object> zzd() {
        return Collections.unmodifiableMap(this.zza);
    }

    public final void zze(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        if (str != null && str.startsWith(ContainerUtils.FIELD_DELIMITER)) {
            str = str.substring(1);
        }
        Preconditions.checkNotEmpty(str, "Name can not be empty or \"&\"");
        this.zza.put(str, str2);
    }
}
