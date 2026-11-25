package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcj {
    private static boolean zza(char c) {
        if ('a' > c || c > 'z') {
            return 'A' <= c && c <= 'Z';
        }
        return true;
    }

    public static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(str).concat(" must not be null"));
    }

    public static String zza(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("identifier must not be empty");
        }
        if (!zza(str.charAt(0))) {
            String strValueOf = String.valueOf(str);
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "identifier must start with an ASCII letter: ".concat(strValueOf) : new String("identifier must start with an ASCII letter: "));
        }
        for (int i = 1; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (!zza(cCharAt) && ((cCharAt < '0' || cCharAt > '9') && cCharAt != '_')) {
                String strValueOf2 = String.valueOf(str);
                throw new IllegalArgumentException(strValueOf2.length() != 0 ? "identifier must contain only ASCII letters, digits or underscore: ".concat(strValueOf2) : new String("identifier must contain only ASCII letters, digits or underscore: "));
            }
        }
        return str;
    }
}
