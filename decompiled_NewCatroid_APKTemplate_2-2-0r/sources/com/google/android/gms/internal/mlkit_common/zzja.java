package com.google.android.gms.internal.mlkit_common;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzja implements Closeable {
    private static final Map<String, zzja> zza = new HashMap();

    static void zza() {
        zza.clear();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw null;
    }
}
