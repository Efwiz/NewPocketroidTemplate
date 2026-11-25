package com.google.android.gms.internal.cast;

import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzer extends zzev {
    static final zzer zza = new zzer();

    private zzer() {
    }

    public final boolean equals(@CheckForNull Object obj) {
        return obj == this;
    }

    public final int hashCode() {
        return 2040732332;
    }

    public final String toString() {
        return "Optional.absent()";
    }

    @Override // com.google.android.gms.internal.cast.zzev
    public final Object zza(Object obj) {
        zzez.zzc(obj, "use Optional.orNull() instead of Optional.or(null)");
        return obj;
    }
}
