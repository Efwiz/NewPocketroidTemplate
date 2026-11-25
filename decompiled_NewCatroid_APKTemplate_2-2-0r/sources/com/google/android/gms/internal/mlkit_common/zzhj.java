package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzhj implements zzay {
    SOURCE_UNKNOWN(0),
    APP_ASSET(1),
    LOCAL(2),
    CLOUD(3),
    SDK_BUILT_IN(4),
    URI(5);

    private final int zzh;

    zzhj(int i) {
        this.zzh = i;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzay
    public final int zza() {
        return this.zzh;
    }
}
