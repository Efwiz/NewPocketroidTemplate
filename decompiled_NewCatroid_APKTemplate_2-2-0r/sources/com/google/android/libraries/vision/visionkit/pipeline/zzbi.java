package com.google.android.libraries.vision.visionkit.pipeline;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbi<T> {
    private final int zza;
    private final Map<Long, T> zzb = new HashMap();

    public zzbi(int i) {
        this.zza = i;
    }

    public final synchronized void zza(long j) {
        this.zzb.remove(Long.valueOf(j));
    }

    public final synchronized boolean zzb(T t, long j) {
        if (this.zzb.size() != this.zza) {
            this.zzb.put(Long.valueOf(j), t);
            return true;
        }
        com.google.android.gms.internal.mlkit_vision_internal_vkp.zzcn zzcnVar = com.google.android.gms.internal.mlkit_vision_internal_vkp.zzcn.zza;
        StringBuilder sb = new StringBuilder(47);
        sb.append("Buffer is full. Drop frame ");
        sb.append(j);
        zzcnVar.zzc(this, sb.toString(), new Object[0]);
        return false;
    }
}
