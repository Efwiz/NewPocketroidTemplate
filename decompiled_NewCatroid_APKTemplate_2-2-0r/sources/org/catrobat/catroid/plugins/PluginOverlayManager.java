package org.catrobat.catroid.plugins;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.material.badge.BadgeDrawable;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginOverlayManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginOverlayManager {
    private static volatile PluginOverlayManager instance;
    private Activity currentActivity;
    private final Map<String, View> pluginViews = new HashMap();
    private WindowManager windowManager;

    private PluginOverlayManager() {
    }

    public static PluginOverlayManager getInstance() {
        if (instance == null) {
            synchronized (PluginOverlayManager.class) {
                if (instance == null) {
                    instance = new PluginOverlayManager();
                }
            }
        }
        return instance;
    }

    public void attach(Activity activity) {
        this.currentActivity = activity;
        this.windowManager = activity.getWindowManager();
        Log.d("PluginOverlayManager", "Attached to activity: " + activity.getLocalClassName());
    }

    public void detach(Activity activity) {
        if (this.currentActivity == activity) {
            this.currentActivity = null;
            this.windowManager = null;
            Log.d("PluginOverlayManager", "Detached from activity: " + activity.getLocalClassName());
        }
    }

    public void addView(String viewId, View view, int x, int y) {
        addView(viewId, view, x, y, -2, -2);
    }

    public void addAsRenderEffect(String viewId, View view) {
        if (this.windowManager == null) {
            Log.e("PluginOverlayManager", "Cannot add render effect, WindowManager is not available.");
            return;
        }
        if (this.pluginViews.containsKey(viewId)) {
            removeView(viewId);
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, XKeySym.XK_Greek_phi, 24, -3);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.x = 0;
        layoutParams.y = 0;
        try {
            this.windowManager.addView(view, layoutParams);
            this.pluginViews.put(viewId, view);
            Log.d("PluginOverlayManager", "Render effect added with id: " + viewId);
        } catch (Exception e) {
            Log.e("PluginOverlayManager", "Failed to add render effect to WindowManager", e);
        }
    }

    public void addView(String viewId, View view, int x, int y, int width, int height) {
        if (this.windowManager == null) {
            Log.e("PluginOverlayManager", "Cannot add view, WindowManager is not available.");
            return;
        }
        if (this.pluginViews.containsKey(viewId)) {
            removeView(viewId);
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, height, XKeySym.XK_Greek_phi, 40, -3);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.x = x;
        layoutParams.y = y;
        try {
            this.windowManager.addView(view, layoutParams);
            this.pluginViews.put(viewId, view);
            Log.d("PluginOverlayManager", "View added with id: " + viewId + " at (" + x + "," + y + ") size (" + width + "," + height + Constant.AFTER_QUTO);
        } catch (Exception e) {
            Log.e("PluginOverlayManager", "Failed to add view to WindowManager", e);
        }
    }

    public void removeView(String viewId) {
        View viewRemove = this.pluginViews.remove(viewId);
        if (viewRemove != null) {
            if (this.windowManager != null && viewRemove.isAttachedToWindow()) {
                try {
                    this.windowManager.removeView(viewRemove);
                    Log.d("PluginOverlayManager", "View removed with id: " + viewId);
                    return;
                } catch (Exception e) {
                    Log.e("PluginOverlayManager", "Failed to remove view from WindowManager", e);
                    return;
                }
            }
            if (this.windowManager == null) {
                Log.w("PluginOverlayManager", "Attempted to remove view but WindowManager was null.");
            }
        }
    }
}
