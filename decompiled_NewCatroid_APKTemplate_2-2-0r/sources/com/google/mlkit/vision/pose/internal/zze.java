package com.google.mlkit.vision.pose.internal;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.mlkit.common.sdkinternal.MlKitContext;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zze implements ComponentFactory {
    static final ComponentFactory zza = new zze();

    private zze() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        return new zza((MlKitContext) componentContainer.get(MlKitContext.class));
    }
}
