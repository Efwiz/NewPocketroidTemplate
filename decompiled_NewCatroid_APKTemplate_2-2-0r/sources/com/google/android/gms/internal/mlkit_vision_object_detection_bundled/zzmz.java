package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzmz {
    private final String zza;

    zzmz(String str) {
        Preconditions.checkNotNull(str, "Null FID");
        int length = str.length();
        boolean z = length == 22;
        String strValueOf = String.valueOf(str);
        Preconditions.checkArgument(z, strValueOf.length() != 0 ? "Invalid FID: must be exactly 22 characters: ".concat(strValueOf) : new String("Invalid FID: must be exactly 22 characters: "));
        char cCharAt = str.charAt(0);
        boolean z2 = cCharAt >= 'c' && cCharAt <= 'f';
        String strValueOf2 = String.valueOf(str);
        Preconditions.checkArgument(z2, strValueOf2.length() != 0 ? "Invalid FID: must start with [c-f]: ".concat(strValueOf2) : new String("Invalid FID: must start with [c-f]: "));
        for (int i = 0; i < length; i++) {
            char cCharAt2 = str.charAt(i);
            boolean z3 = (cCharAt2 >= '0' && cCharAt2 <= '9') || (cCharAt2 >= 'a' && cCharAt2 <= 'z') || ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || cCharAt2 == '-' || cCharAt2 == '_');
            String strValueOf3 = String.valueOf(str);
            Preconditions.checkArgument(z3, strValueOf3.length() != 0 ? "Invalid FID: must contain only URL-safe base-64 characters: ".concat(strValueOf3) : new String("Invalid FID: must contain only URL-safe base-64 characters: "));
        }
        this.zza = str;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzmz) {
            return this.zza.equals(((zzmz) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final String zza() {
        return this.zza;
    }
}
