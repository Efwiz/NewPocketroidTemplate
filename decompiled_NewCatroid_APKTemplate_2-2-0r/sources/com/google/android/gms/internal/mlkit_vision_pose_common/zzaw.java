package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaw<K, V> extends zzbj<Map.Entry<K, V>, K> {
    zzaw(Iterator it) {
        super(it);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbj
    final /* synthetic */ Object zza(Object obj) {
        return ((Map.Entry) obj).getKey();
    }
}
