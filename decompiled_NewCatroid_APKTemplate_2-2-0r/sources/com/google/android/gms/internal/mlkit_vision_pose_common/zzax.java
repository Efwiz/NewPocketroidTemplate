package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzax {
    static <V> V zza(Map<?, V> map, @NullableDecl Object obj) {
        zzh.zza(map);
        try {
            return map.get(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    static boolean zzb(Map<?, ?> map, Object obj) {
        zzh.zza(map);
        try {
            return map.containsKey(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    static <V> V zzc(Map<?, V> map, Object obj) {
        zzh.zza(map);
        try {
            return map.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }
}
