package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzfi extends IOException {
    private zzgi zza;

    public zzfi(String str) {
        super(str);
        this.zza = null;
    }

    public final zzfi zza(zzgi zzgiVar) {
        this.zza = zzgiVar;
        return this;
    }

    static zzfi zza() {
        return new zzfi("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzfi zzb() {
        return new zzfi("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzfi zzc() {
        return new zzfi("Protocol message contained an invalid tag (zero).");
    }

    static zzfh zzd() {
        return new zzfh("Protocol message tag had invalid wire type.");
    }

    static zzfi zze() {
        return new zzfi("Failed to parse the message.");
    }

    static zzfi zzf() {
        return new zzfi("Protocol message had invalid UTF-8.");
    }
}
