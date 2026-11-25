package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzve extends RuntimeException {
    public zzve(String str) {
        super("Did not consume the entire document.");
    }

    public zzve(String str, Throwable th) {
        super(str, th);
    }

    public zzve(Throwable th) {
        super(th);
    }
}
