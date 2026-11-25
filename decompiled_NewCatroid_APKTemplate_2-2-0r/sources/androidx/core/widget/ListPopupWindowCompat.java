package androidx.core.widget;

import android.view.View;
import android.widget.ListPopupWindow;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/ListPopupWindowCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/widget/ListPopupWindowCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class ListPopupWindowCompat {
    private ListPopupWindowCompat() {
    }

    @Deprecated
    public static View.OnTouchListener createDragToOpenListener(Object obj, View view) {
        return createDragToOpenListener((ListPopupWindow) obj, view);
    }

    public static View.OnTouchListener createDragToOpenListener(ListPopupWindow listPopupWindow, View view) {
        return listPopupWindow.createDragToOpenListener(view);
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/ListPopupWindowCompat$Api19Impl.class */
    @RequiresApi(19)
    static class Api19Impl {
        private Api19Impl() {
        }

        @DoNotInline
        static View.OnTouchListener createDragToOpenListener(ListPopupWindow listPopupWindow, View src) {
            return listPopupWindow.createDragToOpenListener(src);
        }
    }
}
