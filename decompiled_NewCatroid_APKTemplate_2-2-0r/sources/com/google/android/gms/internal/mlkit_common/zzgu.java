package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzgu implements zzay {
    UNKNOWN(0),
    TRANSLATE(1);

    private final int zzd;

    zzgu(int i) {
        this.zzd = i;
    }

    public static zzgu zzb(int i) {
        for (zzgu zzguVar : values()) {
            if (zzguVar.zzd == i) {
                return zzguVar;
            }
        }
        return UNKNOWN;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzay
    public final int zza() {
        return this.zzd;
    }
}
