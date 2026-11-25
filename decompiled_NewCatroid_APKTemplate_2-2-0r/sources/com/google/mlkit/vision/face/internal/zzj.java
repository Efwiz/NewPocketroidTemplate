package com.google.mlkit.vision.face.internal;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.mlkit.common.sdkinternal.MlKitContext;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zzj implements ComponentFactory {
    static final ComponentFactory zza = new zzj();

    private zzj() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        int i = FaceRegistrar.zza;
        return new zze((MlKitContext) componentContainer.get(MlKitContext.class));
    }
}
