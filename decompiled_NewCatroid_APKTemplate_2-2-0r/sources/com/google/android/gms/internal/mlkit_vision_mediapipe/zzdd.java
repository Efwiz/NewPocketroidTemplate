package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzdd extends RuntimeException {
    private final zza zza;
    private final String zzb;

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public enum zza {
        OK("ok"),
        CANCELLED("canceled"),
        UNKNOWN("unknown"),
        INVALID_ARGUMENT("invalid argument"),
        DEADLINE_EXCEEDED("deadline exceeded"),
        NOT_FOUND("not found"),
        ALREADY_EXISTS("already exists"),
        PERMISSION_DENIED("permission denied"),
        RESOURCE_EXHAUSTED("resource exhausted"),
        FAILED_PRECONDITION("failed precondition"),
        ABORTED("aborted"),
        OUT_OF_RANGE("out of range"),
        UNIMPLEMENTED("unimplemented"),
        INTERNAL("internal"),
        UNAVAILABLE("unavailable"),
        DATA_LOSS("data loss"),
        UNAUTHENTICATED("unauthenticated");

        private final String zzr;

        zza(String str) {
            this.zzr = str;
        }

        public final String zza() {
            return this.zzr;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private zzdd(int i, String str) {
        String strZza = zza.values()[i].zza();
        super(new StringBuilder(String.valueOf(strZza).length() + 2 + String.valueOf(str).length()).append(strZza).append(": ").append(str).toString());
        this.zza = zza.values()[i];
        this.zzb = str;
    }

    zzdd(int i, byte[] bArr) {
        this(i, new String(bArr, zzb.zza));
    }
}
