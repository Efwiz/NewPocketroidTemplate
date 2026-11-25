package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzw<K, V> extends zzz<K, V> {
    private transient int zza;

    public static <K, V> zzw<K, V> zzf() {
        return new zzw<>();
    }

    private zzw() {
        this(12, 3);
    }

    private zzw(int i, int i2) {
        super(new zzaa(12));
        zzy.zza(3, "expectedValuesPerKey");
        this.zza = 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzi
    /* JADX INFO: renamed from: zza */
    public final List<V> zzb() {
        return new ArrayList(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx
    public final /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx, com.google.android.gms.internal.mlkit_vision_pose_common.zzbc
    public final /* bridge */ /* synthetic */ Map zzg() {
        return super.zzg();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzl, com.google.android.gms.internal.mlkit_vision_pose_common.zzx, com.google.android.gms.internal.mlkit_vision_pose_common.zzbc
    public final /* bridge */ /* synthetic */ boolean zza(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.zza(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzi, com.google.android.gms.internal.mlkit_vision_pose_common.zzau
    /* JADX INFO: renamed from: zza */
    public final /* bridge */ /* synthetic */ List zzb(@NullableDecl Object obj) {
        return super.zzb(obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzl
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzi, com.google.android.gms.internal.mlkit_vision_pose_common.zzl
    final /* synthetic */ Collection zzb() {
        return zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx
    public final /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx, com.google.android.gms.internal.mlkit_vision_pose_common.zzbc
    public final /* bridge */ /* synthetic */ Set zzh() {
        return super.zzh();
    }
}
