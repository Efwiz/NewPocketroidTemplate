package org.catrobat.catroid.ui.dialogs;

import android.content.Context;
import android.view.WindowManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/DebugMenuManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DebugMenuManager {
    private static final DebugMenuManager INSTANCE = new DebugMenuManager();
    private DebugMenuView debugMenuView;

    private DebugMenuManager() {
    }

    public static DebugMenuManager getInstance() {
        return INSTANCE;
    }

    public void updateIfVisible() {
        DebugMenuView debugMenuView = this.debugMenuView;
        if (debugMenuView != null) {
            debugMenuView.update();
        }
    }

    public void show(Context context) {
        if (this.debugMenuView == null) {
            this.debugMenuView = new DebugMenuView(context);
            WindowManager windowManager = (WindowManager) context.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
            DebugMenuView debugMenuView = this.debugMenuView;
            windowManager.addView(debugMenuView, debugMenuView.getLayoutParams());
        }
    }

    public void hide() {
        DebugMenuView debugMenuView = this.debugMenuView;
        if (debugMenuView != null) {
            ((WindowManager) debugMenuView.getContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW)).removeView(this.debugMenuView);
            this.debugMenuView = null;
        }
    }

    private WindowManager.LayoutParams getLayoutParamsFromView() {
        if (this.debugMenuView != null) {
            return new WindowManager.LayoutParams(this.debugMenuView.getLayoutParams().width, this.debugMenuView.getLayoutParams().height, this.debugMenuView.getLayoutParams().type, this.debugMenuView.getLayoutParams().flags, this.debugMenuView.getLayoutParams().format);
        }
        return null;
    }
}
