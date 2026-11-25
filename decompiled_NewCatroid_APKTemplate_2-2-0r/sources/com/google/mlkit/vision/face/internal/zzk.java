package com.google.mlkit.vision.face.internal;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zzk implements ComponentFactory {
    static final ComponentFactory zza = new zzk();

    private zzk() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        return new zzd((zze) componentContainer.get(zze.class), (ExecutorSelector) componentContainer.get(ExecutorSelector.class));
    }
}
