package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzahm extends LazyInstanceMap<zzagx, zzahd> {
    private zzahm() {
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zzahd create(zzagx zzagxVar) {
        zzagx zzagxVar2 = zzagxVar;
        MlKitContext mlKitContext = MlKitContext.getInstance();
        return new zzahd(mlKitContext.getApplicationContext(), (SharedPrefManager) mlKitContext.get(SharedPrefManager.class), new zzagy(MlKitContext.getInstance().getApplicationContext(), zzagxVar2), zzagxVar2.zzb());
    }

    /* synthetic */ zzahm(zzahl zzahlVar) {
    }
}
