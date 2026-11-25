package com.google.android.gms.internal.mlkit_vision_text;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzky extends LazyInstanceMap<zzkg, zzko> {
    private zzky() {
    }

    /* synthetic */ zzky(zzkx zzkxVar) {
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zzko create(zzkg zzkgVar) {
        zzkg zzkgVar2 = zzkgVar;
        MlKitContext mlKitContext = MlKitContext.getInstance();
        return new zzko(mlKitContext.getApplicationContext(), (SharedPrefManager) mlKitContext.get(SharedPrefManager.class), new zzkh(MlKitContext.getInstance().getApplicationContext(), zzkgVar2), zzkgVar2.zza());
    }
}
