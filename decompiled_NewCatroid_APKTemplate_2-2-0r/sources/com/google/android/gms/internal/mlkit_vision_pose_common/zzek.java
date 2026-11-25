package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzek implements ComponentFactory {
    static final ComponentFactory zza = new zzek();

    private zzek() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        return new zzeh((zzeg) componentContainer.get(zzeg.class));
    }
}
