package com.google.android.gms.internal.cast;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzta extends IOException {
    zzta() {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.");
    }

    zzta(String str, Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(String.valueOf(str)), th);
    }

    zzta(Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
    }
}
