package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzao<K, V> extends zzu<K, V> implements Serializable {

    @NullableDecl
    private final K zza;

    @NullableDecl
    private final V zzb;

    zzao(@NullableDecl K k, @NullableDecl V v) {
        this.zza = k;
        this.zzb = v;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzu, java.util.Map.Entry
    @NullableDecl
    public final K getKey() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzu, java.util.Map.Entry
    @NullableDecl
    public final V getValue() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzu, java.util.Map.Entry
    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
