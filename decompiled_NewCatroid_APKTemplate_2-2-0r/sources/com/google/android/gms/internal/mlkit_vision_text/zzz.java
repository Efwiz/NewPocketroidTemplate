package com.google.android.gms.internal.mlkit_vision_text;

import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzz {
    private static final Logger zza = Logger.getLogger(zzz.class.getName());
    private static final zzy zzb = new zzy(null);

    private zzz() {
    }

    static boolean zza(@NullableDecl String str) {
        return str == null || str.isEmpty();
    }
}
