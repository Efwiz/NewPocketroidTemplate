package com.google.android.gms.cast.internal;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzm extends zzae {
    final /* synthetic */ TaskCompletionSource zza;

    zzm(zzn zznVar, TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    @Override // com.google.android.gms.cast.internal.zzaf
    public final void zzb(Bundle bundle) {
        this.zza.setResult(bundle);
    }
}
