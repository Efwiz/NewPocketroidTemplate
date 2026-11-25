package org.catrobat.paintroid.ui.tools;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.presenter.MainActivityPresenter;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: DefaultToolOptionsViewController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J\b\u0010\u001f\u001a\u00020\u001dH\u0016J\b\u0010 \u001a\u00020\u001dH\u0016J\b\u0010!\u001a\u00020\u001dH\u0016J\b\u0010\"\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001dH\u0016J\b\u0010#\u001a\u00020\u001dH\u0016J\b\u0010$\u001a\u00020\u001dH\u0002J\b\u0010%\u001a\u00020\u001dH\u0002J\b\u0010&\u001a\u00020\u001dH\u0016J\b\u0010'\u001a\u00020\u001dH\u0016J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010)\u001a\u00020\u001dH\u0016J\b\u0010*\u001a\u00020\u001dH\u0016J\b\u0010+\u001a\u00020\u001dH\u0016J \u0010,\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u000e2\u0006\u0010/\u001a\u00020\u000eH\u0016J \u00100\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u000e2\u0006\u0010/\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultToolOptionsViewController;", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "activity", "Landroid/app/Activity;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "(Landroid/app/Activity;Landroidx/test/espresso/idling/CountingIdlingResource;)V", "getActivity", "()Landroid/app/Activity;", "bottomNavigation", "Landroid/view/ViewGroup;", "callback", "Lorg/catrobat/paintroid/tools/options/ToolOptionsVisibilityController$Callback;", CompilerOptions.ENABLED, "", "hideButtonsEnabled", "getIdlingResource", "()Landroidx/test/espresso/idling/CountingIdlingResource;", "isVisible", "()Z", "mainToolOptions", "toolOptionsShown", "toolSpecificOptionsLayout", "getToolSpecificOptionsLayout", "()Landroid/view/ViewGroup;", "topBar", "Landroid/view/View;", "topBarSpecificViewCheckmark", "animateBottomAndTopNavigation", "", "hide", "disable", "disableHide", CommonCompilerArguments.ENABLE, "enableHide", "hideCheckmark", "notifyHide", "notifyShow", "removeToolViews", "resetToOrigin", "setCallback", "show", "showCheckmark", "showDelayed", "slideDown", Promotion.ACTION_VIEW, "willHide", "showOptionsView", "slideUp", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultToolOptionsViewController implements ToolOptionsViewController {
    private final Activity activity;
    private final ViewGroup bottomNavigation;
    private ToolOptionsVisibilityController.Callback callback;
    private boolean enabled;
    private boolean hideButtonsEnabled;
    private final CountingIdlingResource idlingResource;
    private final ViewGroup mainToolOptions;
    private boolean toolOptionsShown;
    private final View topBar;
    private final View topBarSpecificViewCheckmark;

    public DefaultToolOptionsViewController(Activity activity, CountingIdlingResource idlingResource) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        this.activity = activity;
        this.idlingResource = idlingResource;
        View viewFindViewById = activity.findViewById(R.id.pocketpaint_main_bottom_navigation);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "activity.findViewById(R.…t_main_bottom_navigation)");
        this.bottomNavigation = (ViewGroup) viewFindViewById;
        View viewFindViewById2 = activity.findViewById(R.id.pocketpaint_layout_tool_options);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "activity.findViewById(R.…aint_layout_tool_options)");
        ViewGroup viewGroup = (ViewGroup) viewFindViewById2;
        this.mainToolOptions = viewGroup;
        View viewFindViewById3 = activity.findViewById(R.id.pocketpaint_btn_top_checkmark);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "activity.findViewById(R.…tpaint_btn_top_checkmark)");
        this.topBarSpecificViewCheckmark = viewFindViewById3;
        View viewFindViewById4 = activity.findViewById(R.id.pocketpaint_layout_top_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "activity.findViewById(R.…cketpaint_layout_top_bar)");
        this.topBar = viewFindViewById4;
        this.enabled = true;
        this.hideButtonsEnabled = true;
        viewGroup.setVisibility(4);
    }

    public final Activity getActivity() {
        return this.activity;
    }

    public final CountingIdlingResource getIdlingResource() {
        return this.idlingResource;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public ViewGroup getToolSpecificOptionsLayout() {
        View viewFindViewById = this.activity.findViewById(R.id.pocketpaint_layout_tool_specific_options);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "activity.findViewById(R.…ut_tool_specific_options)");
        return (ViewGroup) viewFindViewById;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController
    /* JADX INFO: renamed from: isVisible, reason: from getter */
    public boolean getToolOptionsShown() {
        return this.toolOptionsShown;
    }

    private final void notifyHide() {
        ToolOptionsVisibilityController.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.onHide();
    }

    private final void notifyShow() {
        ToolOptionsVisibilityController.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.onShow();
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void resetToOrigin() {
        this.toolOptionsShown = false;
        this.mainToolOptions.setVisibility(4);
        this.mainToolOptions.setY(this.bottomNavigation.getY() + this.bottomNavigation.getHeight());
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController
    public void hide() {
        if (this.enabled && this.hideButtonsEnabled) {
            this.idlingResource.increment();
            this.toolOptionsShown = false;
            this.mainToolOptions.animate().y(this.bottomNavigation.getY() + this.bottomNavigation.getHeight());
            this.mainToolOptions.setVisibility(8);
            notifyHide();
            this.idlingResource.decrement();
        }
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void disable() {
        this.enabled = false;
        if (getToolOptionsShown()) {
            resetToOrigin();
        }
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void enable() {
        this.enabled = true;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController
    public void show() {
        if (this.enabled && this.hideButtonsEnabled) {
            this.idlingResource.increment();
            this.toolOptionsShown = true;
            this.mainToolOptions.setVisibility(4);
            this.mainToolOptions.post(new Runnable() { // from class: org.catrobat.paintroid.ui.tools.DefaultToolOptionsViewController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultToolOptionsViewController.m6008show$lambda0(this.f$0);
                }
            });
            notifyShow();
            this.idlingResource.decrement();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: show$lambda-0, reason: not valid java name */
    public static final void m6008show$lambda0(DefaultToolOptionsViewController this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mainToolOptions.animate().y(this$0.bottomNavigation.getY() - this$0.mainToolOptions.getHeight());
        this$0.mainToolOptions.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showDelayed$lambda-1, reason: not valid java name */
    public static final void m6009showDelayed$lambda1(DefaultToolOptionsViewController this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.show();
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController
    public void showDelayed() {
        getToolSpecificOptionsLayout().post(new Runnable() { // from class: org.catrobat.paintroid.ui.tools.DefaultToolOptionsViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DefaultToolOptionsViewController.m6009showDelayed$lambda1(this.f$0);
            }
        });
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void removeToolViews() {
        getToolSpecificOptionsLayout().removeAllViews();
        this.callback = null;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsVisibilityController
    public void setCallback(ToolOptionsVisibilityController.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void showCheckmark() {
        this.topBarSpecificViewCheckmark.setVisibility(0);
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void hideCheckmark() {
        this.topBarSpecificViewCheckmark.setVisibility(8);
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void enableHide() {
        this.hideButtonsEnabled = true;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void disableHide() {
        this.hideButtonsEnabled = false;
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void slideUp(View view, boolean willHide, boolean showOptionsView) {
        TranslateAnimation translateAnimation;
        Intrinsics.checkNotNullParameter(view, "view");
        if (this.enabled && this.hideButtonsEnabled) {
            if (!willHide) {
                view.setVisibility(0);
                this.toolOptionsShown = showOptionsView;
            }
            if (willHide) {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -view.getHeight());
            } else {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, view.getHeight(), 0.0f);
            }
            translateAnimation.setDuration(250L);
            view.startAnimation(translateAnimation);
            if (willHide) {
                view.setVisibility(4);
                this.toolOptionsShown = showOptionsView;
                notifyHide();
                return;
            }
            notifyShow();
        }
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void slideDown(View view, boolean willHide, boolean showOptionsView) {
        TranslateAnimation translateAnimation;
        Intrinsics.checkNotNullParameter(view, "view");
        if (this.enabled && this.hideButtonsEnabled) {
            if (willHide) {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, view.getHeight());
            } else {
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, -view.getHeight(), 0.0f);
            }
            translateAnimation.setDuration(250L);
            view.startAnimation(translateAnimation);
            if (willHide) {
                view.setVisibility(4);
                this.toolOptionsShown = showOptionsView;
                notifyHide();
            } else {
                view.setVisibility(0);
                this.toolOptionsShown = showOptionsView;
                notifyShow();
            }
        }
    }

    @Override // org.catrobat.paintroid.tools.options.ToolOptionsViewController
    public void animateBottomAndTopNavigation(boolean hide) {
        ((MainActivityPresenter) ((MainActivity) this.activity).getPresenter()).hideBottomBarViewHolder();
        if (hide) {
            if (this.topBar.getVisibility() == 0) {
                slideUp(this.topBar, true, this.toolOptionsShown);
            }
            if (this.bottomNavigation.getVisibility() == 0) {
                slideDown(this.bottomNavigation, true, this.toolOptionsShown);
                return;
            }
            return;
        }
        if (hide) {
            return;
        }
        if (this.topBar.getVisibility() == 4) {
            slideDown(this.topBar, false, this.toolOptionsShown);
        }
        if (this.bottomNavigation.getVisibility() == 4) {
            slideUp(this.bottomNavigation, false, this.toolOptionsShown);
        }
    }
}
