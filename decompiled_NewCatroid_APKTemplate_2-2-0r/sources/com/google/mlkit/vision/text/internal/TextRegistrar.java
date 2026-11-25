package com.google.mlkit.vision.text.internal;

import com.google.android.gms.internal.mlkit_vision_text.zzbl;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public class TextRegistrar implements ComponentRegistrar {
    public static final /* synthetic */ int zza = 0;

    @Override // com.google.firebase.components.ComponentRegistrar
    public final List<Component<?>> getComponents() {
        return zzbl.zzh(Component.builder(zzn.class).add(Dependency.required((Class<?>) MlKitContext.class)).factory(zzo.zza).build(), Component.builder(zzk.class).add(Dependency.required((Class<?>) zzn.class)).add(Dependency.required((Class<?>) ExecutorSelector.class)).factory(zzp.zza).build());
    }
}
