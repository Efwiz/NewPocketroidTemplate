package com.google.android.gms.cast.internal.media;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class MediaCommon {
    public static String zza(Integer num) {
        if (num == null) {
            return null;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return "REPEAT_OFF";
        }
        if (iIntValue == 1) {
            return "REPEAT_ALL";
        }
        if (iIntValue == 2) {
            return "REPEAT_SINGLE";
        }
        if (iIntValue != 3) {
            return null;
        }
        return "REPEAT_ALL_AND_SHUFFLE";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:19:0x0037  */
    public static Integer mediaRepeatModeFromString(String str) {
        byte b;
        if (str == null) {
            return null;
        }
        switch (str) {
            case "REPEAT_ALL_AND_SHUFFLE":
                b = 3;
                break;
            case "REPEAT_SINGLE":
                b = 2;
                break;
            case "REPEAT_ALL":
                b = 1;
                break;
            case "REPEAT_OFF":
                b = 0;
                break;
            default:
                b = -1;
                break;
        }
        if (b == 0) {
            return 0;
        }
        if (b == 1) {
            return 1;
        }
        if (b != 2) {
            return b != 3 ? null : 3;
        }
        return 2;
    }
}
