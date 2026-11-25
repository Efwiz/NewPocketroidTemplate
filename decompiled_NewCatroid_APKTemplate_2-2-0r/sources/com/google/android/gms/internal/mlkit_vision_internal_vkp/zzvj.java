package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzvj<T> {
    public abstract T zza(zzxf zzxfVar) throws IOException;

    public abstract void zzb(zzxh zzxhVar, T t) throws IOException;

    public final zzvj<T> zzc() {
        return new zzvi(this);
    }
}
