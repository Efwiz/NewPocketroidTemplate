package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzagd extends IOException {
    private final zzyf zza;

    zzagd(zzyf zzyfVar, String str) {
        super(str);
        this.zza = zzyfVar;
    }

    static zzagd zzb(String str, Exception exc) {
        return new zzagd(zzyf.RPC_CLIENT_ERROR, str, exc);
    }

    final zzyf zza() {
        return this.zza;
    }

    zzagd(zzyf zzyfVar, String str, Throwable th) {
        super(str, th);
        this.zza = zzyfVar;
    }
}
