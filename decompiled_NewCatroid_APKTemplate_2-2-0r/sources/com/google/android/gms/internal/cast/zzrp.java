package com.google.android.gms.internal.cast;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzrp extends zzrq {
    private final ListenableFuture zza;

    protected zzrp(ListenableFuture listenableFuture) {
        this.zza = listenableFuture;
    }

    @Override // com.google.android.gms.internal.cast.zzro, com.google.android.gms.internal.cast.zzfi
    protected final /* synthetic */ Object zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.cast.zzrq, com.google.android.gms.internal.cast.zzro
    protected final /* synthetic */ Future zzb() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.cast.zzrq
    protected final ListenableFuture zzc() {
        return this.zza;
    }
}
