package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/MenuProvider.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public interface MenuProvider {
    void onCreateMenu(Menu menu, MenuInflater menuInflater);

    default void onMenuClosed(Menu menu) {
    }

    boolean onMenuItemSelected(MenuItem menuItem);

    default void onPrepareMenu(Menu menu) {
    }
}
