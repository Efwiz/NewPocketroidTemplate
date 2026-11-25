package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzt {
    private final ConcurrentMap<Object, zzr> zza = new ConcurrentHashMap();

    zzt() {
    }

    final zzr zza(Object obj) {
        zzr zzrVar = this.zza.get(obj);
        if (zzrVar != null) {
            return zzrVar;
        }
        zzr zzrVar2 = new zzr();
        zzr zzrVarPutIfAbsent = this.zza.putIfAbsent(obj, zzrVar2);
        return zzrVarPutIfAbsent != null ? zzrVarPutIfAbsent : zzrVar2;
    }
}
