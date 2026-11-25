package com.google.android.gms.internal.mlkit_vision_face;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzlb extends LazyInstanceMap<zzki, zzkq> {
    private zzlb() {
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zzkq create(zzki zzkiVar) {
        zzki zzkiVar2 = zzkiVar;
        MlKitContext mlKitContext = MlKitContext.getInstance();
        return new zzkq(mlKitContext.getApplicationContext(), (SharedPrefManager) mlKitContext.get(SharedPrefManager.class), new zzkj(MlKitContext.getInstance().getApplicationContext(), zzkiVar2), zzkiVar2.zza());
    }

    /* synthetic */ zzlb(zzla zzlaVar) {
    }
}
