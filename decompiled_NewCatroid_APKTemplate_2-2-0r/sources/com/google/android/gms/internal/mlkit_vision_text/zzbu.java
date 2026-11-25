package com.google.android.gms.internal.mlkit_vision_text;

import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbu {
    public static <F, T> List<T> zza(List<F> list, zzu<? super F, ? extends T> zzuVar) {
        return list instanceof RandomAccess ? new zzbr(list, zzuVar) : new zzbt(list, zzuVar);
    }
}
