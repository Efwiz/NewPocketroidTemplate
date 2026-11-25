package androidx.core.widget;

import android.widget.ListView;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/ListViewCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/widget/ListViewCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class ListViewCompat {
    @Deprecated
    public static void scrollListBy(ListView listView, int i) {
        listView.scrollListBy(i);
    }

    @Deprecated
    public static boolean canScrollList(ListView listView, int i) {
        return listView.canScrollList(i);
    }

    private ListViewCompat() {
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/ListViewCompat$Api19Impl.class */
    @RequiresApi(19)
    static class Api19Impl {
        private Api19Impl() {
        }

        @DoNotInline
        static void scrollListBy(ListView absListView, int y) {
            absListView.scrollListBy(y);
        }

        @DoNotInline
        static boolean canScrollList(ListView absListView, int direction) {
            return absListView.canScrollList(direction);
        }
    }
}
