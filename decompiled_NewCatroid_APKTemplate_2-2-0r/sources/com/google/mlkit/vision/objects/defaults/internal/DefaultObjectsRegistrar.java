package com.google.mlkit.vision.objects.defaults.internal;

import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzar;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.common.internal.MultiFlavorDetectorCreator;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes10.dex */
public class DefaultObjectsRegistrar implements ComponentRegistrar {
    public static final /* synthetic */ int zza = 0;

    @Override // com.google.firebase.components.ComponentRegistrar
    public final List<Component<?>> getComponents() {
        zzar zzarVar = new zzar();
        zzarVar.zzc(Component.builder(zzb.class).add(Dependency.required((Class<?>) MlKitContext.class)).factory(new ComponentFactory() { // from class: com.google.mlkit.vision.objects.defaults.internal.zzf
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                int i = DefaultObjectsRegistrar.zza;
                return new zzb((MlKitContext) componentContainer.get(MlKitContext.class));
            }
        }).build());
        zzarVar.zzc(Component.builder(zza.class).add(Dependency.required((Class<?>) zzb.class)).add(Dependency.required((Class<?>) ExecutorSelector.class)).factory(new ComponentFactory() { // from class: com.google.mlkit.vision.objects.defaults.internal.zzg
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                int i = DefaultObjectsRegistrar.zza;
                return new zza((zzb) componentContainer.get(zzb.class), (ExecutorSelector) componentContainer.get(ExecutorSelector.class));
            }
        }).build());
        zzarVar.zzc(Component.intoSetBuilder(MultiFlavorDetectorCreator.Registration.class).add(Dependency.requiredProvider((Class<?>) zza.class)).factory(new ComponentFactory() { // from class: com.google.mlkit.vision.objects.defaults.internal.zzh
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                int i = DefaultObjectsRegistrar.zza;
                return new MultiFlavorDetectorCreator.Registration(ObjectDetectorOptions.class, componentContainer.getProvider(zza.class));
            }
        }).build());
        return zzarVar.zzd();
    }
}
