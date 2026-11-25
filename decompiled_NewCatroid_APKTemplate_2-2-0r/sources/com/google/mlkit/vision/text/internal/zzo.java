package com.google.mlkit.vision.text.internal;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.mlkit.common.sdkinternal.MlKitContext;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zzo implements ComponentFactory {
    static final ComponentFactory zza = new zzo();

    private zzo() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        int i = TextRegistrar.zza;
        return new zzn((MlKitContext) componentContainer.get(MlKitContext.class));
    }
}
