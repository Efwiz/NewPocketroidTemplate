package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public interface zzbc<K, V> {
    boolean zza(@NullableDecl K k, @NullableDecl V v);

    Collection<V> zzb(@NullableDecl K k);

    Map<K, Collection<V>> zzg();

    Set<K> zzh();
}
