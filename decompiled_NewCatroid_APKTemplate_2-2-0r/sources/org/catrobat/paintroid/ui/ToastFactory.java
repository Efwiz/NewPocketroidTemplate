package org.catrobat.paintroid.ui;

import android.content.Context;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ToastFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\"\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0007J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/ui/ToastFactory;", "", "()V", "currentToast", "Landroid/widget/Toast;", "cancelToast", "", "makeText", "context", "Landroid/content/Context;", "resId", "", "duration", NotificationCompat.CATEGORY_MESSAGE, "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ToastFactory {
    public static final ToastFactory INSTANCE = new ToastFactory();
    private static Toast currentToast;

    private ToastFactory() {
    }

    private final void cancelToast() {
        Toast toast = currentToast;
        if (toast != null) {
            if (toast == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentToast");
                toast = null;
            }
            toast.cancel();
        }
    }

    @JvmStatic
    public static final Toast makeText(Context context, int resId, int duration) {
        Intrinsics.checkNotNullParameter(context, "context");
        INSTANCE.cancelToast();
        Toast toastMakeText = Toast.makeText(context, resId, duration);
        Intrinsics.checkNotNullExpressionValue(toastMakeText, "makeText(context, resId, duration)");
        currentToast = toastMakeText;
        if (toastMakeText != null) {
            return toastMakeText;
        }
        Intrinsics.throwUninitializedPropertyAccessException("currentToast");
        return null;
    }

    @JvmStatic
    public static final Toast makeText(Context context, String msg, int duration) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(msg, "msg");
        INSTANCE.cancelToast();
        Toast toastMakeText = Toast.makeText(context, msg, duration);
        Intrinsics.checkNotNullExpressionValue(toastMakeText, "makeText(context, msg, duration)");
        currentToast = toastMakeText;
        if (toastMakeText != null) {
            return toastMakeText;
        }
        Intrinsics.throwUninitializedPropertyAccessException("currentToast");
        return null;
    }
}
