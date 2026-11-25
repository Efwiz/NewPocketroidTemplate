package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzaoe extends IOException {
    private zzapf zza;
    private boolean zzb;

    public zzaoe(IOException iOException) {
        super(iOException.getMessage(), iOException);
        this.zza = null;
    }

    static zzaod zza() {
        return new zzaod("Protocol message tag had invalid wire type.");
    }

    static zzaoe zzb() {
        return new zzaoe("Protocol message end-group tag did not match expected tag.");
    }

    static zzaoe zzc() {
        return new zzaoe("Protocol message contained an invalid tag (zero).");
    }

    static zzaoe zzd() {
        return new zzaoe("Protocol message had invalid UTF-8.");
    }

    static zzaoe zze() {
        return new zzaoe("CodedInputStream encountered a malformed varint.");
    }

    static zzaoe zzf() {
        return new zzaoe("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzaoe zzg() {
        return new zzaoe("Failed to parse the message.");
    }

    static zzaoe zzi() {
        return new zzaoe("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    static zzaoe zzj() {
        return new zzaoe("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    public final zzaoe zzh(zzapf zzapfVar) {
        this.zza = zzapfVar;
        return this;
    }

    final void zzk() {
        this.zzb = true;
    }

    final boolean zzl() {
        return this.zzb;
    }

    public zzaoe(String str) {
        super(str);
        this.zza = null;
    }
}
