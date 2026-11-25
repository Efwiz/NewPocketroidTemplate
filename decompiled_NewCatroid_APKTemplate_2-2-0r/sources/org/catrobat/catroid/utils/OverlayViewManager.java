package org.catrobat.catroid.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.material.badge.BadgeDrawable;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/OverlayViewManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: OverlayViewManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0007J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000fH\u0007J \u0010\u0012\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/OverlayViewManager;", "", "()V", "overlayViews", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Landroid/view/View;", "removeAllOverlays", "", "setDragHandle", "handleViewId", "targetOverlayId", "setViewAsOverlay", "viewId", "isOverlay", "", "setViewDraggable", "draggable", "updateOverlayPosition", "xPos", "", "yPos", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverlayViewManager {
    public static final OverlayViewManager INSTANCE = new OverlayViewManager();
    private static final ConcurrentHashMap<String, View> overlayViews = new ConcurrentHashMap<>();

    private OverlayViewManager() {
    }

    @JvmStatic
    public static final void setViewAsOverlay(final String viewId, final boolean isOverlay) {
        final StageActivity stageActivity;
        Intrinsics.checkNotNullParameter(viewId, "viewId");
        final Context appContext = CatroidApplication.getAppContext();
        Object systemService = appContext.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        final WindowManager windowManager = (WindowManager) systemService;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                OverlayViewManager.setViewAsOverlay$lambda$5(stageActivity, viewId, isOverlay, appContext, windowManager);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setViewAsOverlay$lambda$5(StageActivity stage, String viewId, boolean z, Context context, final WindowManager windowManager) {
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(viewId, "$viewId");
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        try {
            final View viewFromStage = stage.getViewFromStage(viewId);
            if (viewFromStage == null) {
                return;
            }
            if (z) {
                if (!Settings.canDrawOverlays(context)) {
                    Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
                    intent.addFlags(268435456);
                    context.startActivity(intent);
                    return;
                }
                ConcurrentHashMap<String, View> concurrentHashMap = overlayViews;
                if (concurrentHashMap.containsKey(viewId)) {
                    return;
                }
                ViewParent parent = viewFromStage.getParent();
                ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                if (viewGroup != null) {
                    viewGroup.removeView(viewFromStage);
                }
                ViewGroup.LayoutParams layoutParams = viewFromStage.getLayoutParams();
                FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
                int i = layoutParams2 != null ? layoutParams2.width : -2;
                int i2 = layoutParams2 != null ? layoutParams2.height : -2;
                int i3 = layoutParams2 != null ? layoutParams2.leftMargin : 0;
                int i4 = layoutParams2 != null ? layoutParams2.topMargin : 0;
                final WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i, i2, XKeySym.XK_Greek_phi, 262440, -3);
                layoutParams3.gravity = BadgeDrawable.TOP_START;
                layoutParams3.x = i3;
                layoutParams3.y = i4;
                viewFromStage.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        return OverlayViewManager.setViewAsOverlay$lambda$5$lambda$2(layoutParams3, windowManager, viewFromStage, view, motionEvent);
                    }
                });
                viewFromStage.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        return OverlayViewManager.setViewAsOverlay$lambda$5$lambda$3(view, windowInsets);
                    }
                });
                windowManager.addView(viewFromStage, layoutParams3);
                concurrentHashMap.put(viewId, viewFromStage);
                return;
            }
            View viewRemove = overlayViews.remove(viewId);
            if (viewRemove == null) {
                return;
            }
            try {
                windowManager.removeView(viewRemove);
            } catch (Exception e) {
                e.printStackTrace();
            }
            FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(viewRemove.getWidth(), viewRemove.getHeight());
            layoutParams4.gravity = BadgeDrawable.TOP_START;
            viewRemove.setOnApplyWindowInsetsListener(null);
            viewRemove.setOnTouchListener(null);
            stage.addViewToStage(viewId, viewRemove, layoutParams4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setViewAsOverlay$lambda$5$lambda$2(WindowManager.LayoutParams params, WindowManager windowManager, View view, View view2, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(params, "$params");
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        Intrinsics.checkNotNullParameter(view, "$view");
        if (motionEvent.getAction() == 4) {
            if ((params.flags & 8) != 0) {
                return false;
            }
            params.flags |= 8;
            windowManager.updateViewLayout(view, params);
            return false;
        }
        if ((params.flags & 8) == 0) {
            return false;
        }
        params.flags &= -9;
        windowManager.updateViewLayout(view, params);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final WindowInsets setViewAsOverlay$lambda$5$lambda$3(View v, WindowInsets insets) {
        int i;
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(insets, "insets");
        if (Build.VERSION.SDK_INT >= 30) {
            boolean zIsVisible = insets.isVisible(WindowInsets.Type.ime());
            StageActivity.isKeyboardVisible = zIsVisible;
            if (zIsVisible && (i = insets.getInsets(WindowInsets.Type.ime()).bottom) > 200 && i > StageActivity.realKeyboardHeight) {
                StageActivity.realKeyboardHeight = i;
            }
        } else {
            int systemWindowInsetBottom = insets.getSystemWindowInsetBottom();
            if (systemWindowInsetBottom > 100) {
                StageActivity.isKeyboardVisible = true;
                if (systemWindowInsetBottom > 200 && systemWindowInsetBottom > StageActivity.realKeyboardHeight) {
                    StageActivity.realKeyboardHeight = systemWindowInsetBottom;
                }
            } else if (systemWindowInsetBottom < 50) {
                StageActivity.isKeyboardVisible = false;
            }
        }
        return v.onApplyWindowInsets(insets);
    }

    @JvmStatic
    public static final void updateOverlayPosition(String viewId, final int xPos, final int yPos) {
        StageActivity stageActivity;
        Intrinsics.checkNotNullParameter(viewId, "viewId");
        final View view = overlayViews.get(viewId);
        if (view == null) {
            return;
        }
        Object systemService = CatroidApplication.getAppContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        final WindowManager windowManager = (WindowManager) systemService;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                OverlayViewManager.updateOverlayPosition$lambda$6(view, xPos, yPos, windowManager);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateOverlayPosition$lambda$6(View view, int i, int i2, WindowManager windowManager) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            WindowManager.LayoutParams layoutParams2 = layoutParams instanceof WindowManager.LayoutParams ? (WindowManager.LayoutParams) layoutParams : null;
            if (layoutParams2 == null) {
                return;
            }
            layoutParams2.x = i;
            layoutParams2.y = i2;
            windowManager.updateViewLayout(view, layoutParams2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JvmStatic
    public static final void removeAllOverlays() {
        Object systemService = CatroidApplication.getAppContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        WindowManager windowManager = (WindowManager) systemService;
        Iterator<Map.Entry<String, View>> it = overlayViews.entrySet().iterator();
        while (it.hasNext()) {
            try {
                windowManager.removeView(it.next().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        overlayViews.clear();
    }

    @JvmStatic
    public static final void setDragHandle(final String handleViewId, final String targetOverlayId) {
        final StageActivity stageActivity;
        Intrinsics.checkNotNullParameter(handleViewId, "handleViewId");
        Intrinsics.checkNotNullParameter(targetOverlayId, "targetOverlayId");
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                OverlayViewManager.setDragHandle$lambda$8(stageActivity, handleViewId, targetOverlayId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setDragHandle$lambda$8(StageActivity stage, String handleViewId, String targetOverlayId) {
        final View view;
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(handleViewId, "$handleViewId");
        Intrinsics.checkNotNullParameter(targetOverlayId, "$targetOverlayId");
        View viewFromStage = stage.getViewFromStage(handleViewId);
        if (viewFromStage == null || (view = overlayViews.get(targetOverlayId)) == null) {
            return;
        }
        Object systemService = CatroidApplication.getAppContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        final WindowManager windowManager = (WindowManager) systemService;
        final Ref.IntRef intRef = new Ref.IntRef();
        final Ref.IntRef intRef2 = new Ref.IntRef();
        final Ref.FloatRef floatRef = new Ref.FloatRef();
        final Ref.FloatRef floatRef2 = new Ref.FloatRef();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        viewFromStage.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return OverlayViewManager.setDragHandle$lambda$8$lambda$7(view, intRef, intRef2, floatRef, floatRef2, booleanRef, windowManager, view2, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setDragHandle$lambda$8$lambda$7(View targetView, Ref.IntRef initialX, Ref.IntRef initialY, Ref.FloatRef initialTouchX, Ref.FloatRef initialTouchY, Ref.BooleanRef isMoving, WindowManager windowManager, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(targetView, "$targetView");
        Intrinsics.checkNotNullParameter(initialX, "$initialX");
        Intrinsics.checkNotNullParameter(initialY, "$initialY");
        Intrinsics.checkNotNullParameter(initialTouchX, "$initialTouchX");
        Intrinsics.checkNotNullParameter(initialTouchY, "$initialTouchY");
        Intrinsics.checkNotNullParameter(isMoving, "$isMoving");
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
        WindowManager.LayoutParams layoutParams2 = layoutParams instanceof WindowManager.LayoutParams ? (WindowManager.LayoutParams) layoutParams : null;
        if (layoutParams2 == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            initialX.element = layoutParams2.x;
            initialY.element = layoutParams2.y;
            initialTouchX.element = motionEvent.getRawX();
            initialTouchY.element = motionEvent.getRawY();
            isMoving.element = false;
            return false;
        }
        if (action == 1) {
            return isMoving.element;
        }
        if (action != 2) {
            return false;
        }
        int rawX = (int) (motionEvent.getRawX() - initialTouchX.element);
        int rawY = (int) (motionEvent.getRawY() - initialTouchY.element);
        if (Math.abs(rawX) <= 10 && Math.abs(rawY) <= 10) {
            return false;
        }
        isMoving.element = true;
        layoutParams2.x = initialX.element + rawX;
        layoutParams2.y = initialY.element + rawY;
        windowManager.updateViewLayout(targetView, layoutParams2);
        return true;
    }

    @JvmStatic
    public static final void setViewDraggable(String viewId, final boolean draggable) {
        StageActivity stageActivity;
        Intrinsics.checkNotNullParameter(viewId, "viewId");
        final View view = overlayViews.get(viewId);
        if (view == null) {
            return;
        }
        Object systemService = CatroidApplication.getAppContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        final WindowManager windowManager = (WindowManager) systemService;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                OverlayViewManager.setViewDraggable$lambda$11(draggable, view, windowManager);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setViewDraggable$lambda$11(boolean z, View view, final WindowManager windowManager) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        if (z) {
            final Ref.IntRef intRef = new Ref.IntRef();
            final Ref.IntRef intRef2 = new Ref.IntRef();
            final Ref.FloatRef floatRef = new Ref.FloatRef();
            final Ref.FloatRef floatRef2 = new Ref.FloatRef();
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            view.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return OverlayViewManager.setViewDraggable$lambda$11$lambda$9(windowManager, intRef, intRef2, floatRef, floatRef2, booleanRef, view2, motionEvent);
                }
            });
            return;
        }
        view.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.utils.OverlayViewManager$$ExternalSyntheticLambda4
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return OverlayViewManager.setViewDraggable$lambda$11$lambda$10(windowManager, view2, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setViewDraggable$lambda$11$lambda$9(WindowManager windowManager, Ref.IntRef initialX, Ref.IntRef initialY, Ref.FloatRef initialTouchX, Ref.FloatRef initialTouchY, Ref.BooleanRef isMoving, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        Intrinsics.checkNotNullParameter(initialX, "$initialX");
        Intrinsics.checkNotNullParameter(initialY, "$initialY");
        Intrinsics.checkNotNullParameter(initialTouchX, "$initialTouchX");
        Intrinsics.checkNotNullParameter(initialTouchY, "$initialTouchY");
        Intrinsics.checkNotNullParameter(isMoving, "$isMoving");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        WindowManager.LayoutParams layoutParams2 = layoutParams instanceof WindowManager.LayoutParams ? (WindowManager.LayoutParams) layoutParams : null;
        if (layoutParams2 == null) {
            return false;
        }
        ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
        WindowManager.LayoutParams layoutParams4 = layoutParams3 instanceof WindowManager.LayoutParams ? (WindowManager.LayoutParams) layoutParams3 : null;
        if (layoutParams4 != null && motionEvent.getAction() != 4 && (layoutParams4.flags & 8) != 0) {
            layoutParams4.flags &= -9;
            windowManager.updateViewLayout(view, layoutParams4);
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            initialX.element = layoutParams2.x;
            initialY.element = layoutParams2.y;
            initialTouchX.element = motionEvent.getRawX();
            initialTouchY.element = motionEvent.getRawY();
            isMoving.element = false;
            return false;
        }
        if (action == 1) {
            return isMoving.element;
        }
        if (action == 2) {
            int rawX = (int) (motionEvent.getRawX() - initialTouchX.element);
            int rawY = (int) (motionEvent.getRawY() - initialTouchY.element);
            if (Math.abs(rawX) <= 10 && Math.abs(rawY) <= 10) {
                return false;
            }
            isMoving.element = true;
            layoutParams2.x = initialX.element + rawX;
            layoutParams2.y = initialY.element + rawY;
            windowManager.updateViewLayout(view, layoutParams2);
            return true;
        }
        if (action != 4 || layoutParams4 == null || (layoutParams4.flags & 8) != 0) {
            return false;
        }
        layoutParams4.flags |= 8;
        windowManager.updateViewLayout(view, layoutParams4);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setViewDraggable$lambda$11$lambda$10(WindowManager windowManager, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(windowManager, "$windowManager");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        WindowManager.LayoutParams layoutParams2 = layoutParams instanceof WindowManager.LayoutParams ? (WindowManager.LayoutParams) layoutParams : null;
        if (layoutParams2 == null) {
            return false;
        }
        if (motionEvent.getAction() == 4) {
            if ((layoutParams2.flags & 8) == 0) {
                layoutParams2.flags |= 8;
                windowManager.updateViewLayout(view, layoutParams2);
            }
        } else if ((layoutParams2.flags & 8) != 0) {
            layoutParams2.flags &= -9;
            windowManager.updateViewLayout(view, layoutParams2);
        }
        return false;
    }
}
