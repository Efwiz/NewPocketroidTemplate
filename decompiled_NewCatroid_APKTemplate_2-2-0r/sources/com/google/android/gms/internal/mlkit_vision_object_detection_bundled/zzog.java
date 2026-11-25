package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzog extends LazyInstanceMap<zznq, zznw> {
    private zzog() {
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zznw create(zznq zznqVar) {
        zznq zznqVar2 = zznqVar;
        MlKitContext mlKitContext = MlKitContext.getInstance();
        return new zznw(mlKitContext.getApplicationContext(), (SharedPrefManager) mlKitContext.get(SharedPrefManager.class), new zznr(MlKitContext.getInstance().getApplicationContext(), zznqVar2), zznqVar2.zzb());
    }

    /* synthetic */ zzog(zzof zzofVar) {
    }
}
