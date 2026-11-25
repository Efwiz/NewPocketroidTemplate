package com.google.mlkit.vision.text.internal;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zzp implements ComponentFactory {
    static final ComponentFactory zza = new zzp();

    private zzp() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        return new zzk((zzn) componentContainer.get(zzn.class), (ExecutorSelector) componentContainer.get(ExecutorSelector.class));
    }
}
