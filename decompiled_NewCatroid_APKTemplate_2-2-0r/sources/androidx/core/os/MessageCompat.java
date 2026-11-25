package androidx.core.os;

import android.os.Message;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/MessageCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/MessageCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class MessageCompat {
    private static boolean sTryIsAsynchronous = true;
    private static boolean sTrySetAsynchronous = true;

    public static void setAsynchronous(Message message, boolean z) {
        Api22Impl.setAsynchronous(message, z);
    }

    public static boolean isAsynchronous(Message message) {
        return Api22Impl.isAsynchronous(message);
    }

    private MessageCompat() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/MessageCompat$Api22Impl.class
      classes.dex
     */
    static class Api22Impl {
        private Api22Impl() {
        }

        static boolean isAsynchronous(Message message) {
            return message.isAsynchronous();
        }

        static void setAsynchronous(Message message, boolean z) {
            message.setAsynchronous(z);
        }
    }
}
