package com.google.android.gms.tagmanager;

import android.util.LruCache;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzda extends LruCache {
    final /* synthetic */ zzr zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzda(zzdb zzdbVar, int i, zzr zzrVar) {
        super(1048576);
        this.zza = zzrVar;
    }

    @Override // android.util.LruCache
    protected final int sizeOf(Object obj, Object obj2) {
        return this.zza.zza(obj, obj2);
    }
}
