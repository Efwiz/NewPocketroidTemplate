package com.google.android.gms.internal.cast;

import com.huawei.hms.framework.common.ContainerUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfr {
    private final Object zza;
    private final Object zzb;
    private final Object zzc;

    zzfr(Object obj, Object obj2, Object obj3) {
        this.zza = obj;
        this.zzb = obj2;
        this.zzc = obj3;
    }

    final IllegalArgumentException zza() {
        Object obj = this.zzc;
        Object obj2 = this.zzb;
        Object obj3 = this.zza;
        return new IllegalArgumentException("Multiple entries with same key: " + String.valueOf(obj3) + ContainerUtils.KEY_VALUE_DELIMITER + String.valueOf(obj2) + " and " + String.valueOf(obj3) + ContainerUtils.KEY_VALUE_DELIMITER + String.valueOf(obj));
    }
}
