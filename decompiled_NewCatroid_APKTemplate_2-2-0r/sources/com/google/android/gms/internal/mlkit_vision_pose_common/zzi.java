package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzi<K, V> extends zzl<K, V> implements zzau<K, V> {
    protected zzi(Map<K, Collection<V>> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzl
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public abstract List<V> zzb();

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzl
    final Collection<V> zza(K k, Collection<V> collection) {
        return zza(k, (List) collection, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzl, com.google.android.gms.internal.mlkit_vision_pose_common.zzbc
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public List<V> zzb(@NullableDecl K k) {
        return (List) super.zzb(k);
    }
}
