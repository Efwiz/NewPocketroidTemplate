package com.google.android.gms.internal.cast;

import java.io.Serializable;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzev implements Serializable {
    zzev() {
    }

    public static zzev zzb() {
        return zzer.zza;
    }

    public static zzev zzc(@CheckForNull Object obj) {
        return obj == null ? zzer.zza : new zzfa(obj);
    }

    public abstract Object zza(Object obj);
}
