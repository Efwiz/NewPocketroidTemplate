package org.catrobat.paintroid.tools.options;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: ToolOptionsViewController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0007H&J\b\u0010\u000b\u001a\u00020\u0007H&J\b\u0010\f\u001a\u00020\u0007H&J\b\u0010\r\u001a\u00020\u0007H&J\b\u0010\u000e\u001a\u00020\u0007H&J\b\u0010\u000f\u001a\u00020\u0007H&J\b\u0010\u0010\u001a\u00020\u0007H&J\b\u0010\u0011\u001a\u00020\u0007H&J \u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH&J \u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0018"}, d2 = {"Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "Lorg/catrobat/paintroid/tools/options/ToolOptionsVisibilityController;", "toolSpecificOptionsLayout", "Landroid/view/ViewGroup;", "getToolSpecificOptionsLayout", "()Landroid/view/ViewGroup;", "animateBottomAndTopNavigation", "", "hide", "", "disable", "disableHide", CommonCompilerArguments.ENABLE, "enableHide", "hideCheckmark", "removeToolViews", "resetToOrigin", "showCheckmark", "slideDown", Promotion.ACTION_VIEW, "Landroid/view/View;", "willHide", "showOptionsView", "slideUp", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ToolOptionsViewController extends ToolOptionsVisibilityController {
    void animateBottomAndTopNavigation(boolean hide);

    void disable();

    void disableHide();

    void enable();

    void enableHide();

    ViewGroup getToolSpecificOptionsLayout();

    void hideCheckmark();

    void removeToolViews();

    void resetToOrigin();

    void showCheckmark();

    void slideDown(View view, boolean willHide, boolean showOptionsView);

    void slideUp(View view, boolean willHide, boolean showOptionsView);
}
