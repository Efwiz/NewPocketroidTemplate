package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import io.netty.util.internal.StringUtil;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zza {
    public static String zza(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (zzb(str.charAt(i))) {
                char[] charArray = str.toCharArray();
                while (i < length) {
                    char c = charArray[i];
                    if (zzb(c)) {
                        charArray[i] = (char) (c ^ StringUtil.SPACE);
                    }
                    i++;
                }
                return String.valueOf(charArray);
            }
            i++;
        }
        return str;
    }

    public static boolean zzb(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
