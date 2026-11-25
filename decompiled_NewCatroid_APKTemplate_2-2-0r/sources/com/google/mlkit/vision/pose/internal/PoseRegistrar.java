package com.google.mlkit.vision.pose.internal;

import com.google.android.gms.internal.mlkit_vision_pose_common.zzar;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzeg;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzeh;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzej;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseRegistrar implements ComponentRegistrar {
    @Override // com.google.firebase.components.ComponentRegistrar
    public List<Component<?>> getComponents() {
        return zzar.zza(zzeh.zza, zzeg.zza, zzej.zza, Component.builder(zza.class).add(Dependency.required((Class<?>) MlKitContext.class)).factory(zze.zza).build());
    }
}
