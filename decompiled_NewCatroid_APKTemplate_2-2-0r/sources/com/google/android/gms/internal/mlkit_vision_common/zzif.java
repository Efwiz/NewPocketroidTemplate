package com.google.android.gms.internal.mlkit_vision_common;

import com.google.mlkit.common.sdkinternal.LazyInstanceMap;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzif extends LazyInstanceMap<zzhr, zzhx> {
    private zzif() {
    }

    @Override // com.google.mlkit.common.sdkinternal.LazyInstanceMap
    protected final /* bridge */ /* synthetic */ zzhx create(zzhr zzhrVar) {
        zzhr zzhrVar2 = zzhrVar;
        MlKitContext mlKitContext = MlKitContext.getInstance();
        return new zzhx(mlKitContext.getApplicationContext(), (SharedPrefManager) mlKitContext.get(SharedPrefManager.class), new zzhs(MlKitContext.getInstance().getApplicationContext(), zzhrVar2), zzhrVar2.zzb());
    }

    /* synthetic */ zzif(zzie zzieVar) {
    }
}
