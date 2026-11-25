package com.google.android.gms.internal.mlkit_common;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkb extends LazyInstanceMap<zzjk, zzjr> {
    private zzkb() {
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zzjr create(zzjk zzjkVar) {
        zzjk zzjkVar2 = zzjkVar;
        MlKitContext mlKitContext = MlKitContext.getInstance();
        return new zzjr(mlKitContext.getApplicationContext(), (SharedPrefManager) mlKitContext.get(SharedPrefManager.class), new zzjl(MlKitContext.getInstance().getApplicationContext(), zzjkVar2), zzjkVar2.zzb());
    }

    /* synthetic */ zzkb(zzka zzkaVar) {
    }
}
