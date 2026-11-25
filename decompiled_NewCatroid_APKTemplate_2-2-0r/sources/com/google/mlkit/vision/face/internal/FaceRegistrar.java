package com.google.mlkit.vision.face.internal;

import com.google.android.gms.internal.mlkit_vision_face.zzbl;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
public class FaceRegistrar implements ComponentRegistrar {
    public static final /* synthetic */ int zza = 0;

    @Override // com.google.firebase.components.ComponentRegistrar
    public final List<Component<?>> getComponents() {
        return zzbl.zzh(Component.builder(zze.class).add(Dependency.required((Class<?>) MlKitContext.class)).factory(zzj.zza).build(), Component.builder(zzd.class).add(Dependency.required((Class<?>) zze.class)).add(Dependency.required((Class<?>) ExecutorSelector.class)).factory(zzk.zza).build());
    }
}
