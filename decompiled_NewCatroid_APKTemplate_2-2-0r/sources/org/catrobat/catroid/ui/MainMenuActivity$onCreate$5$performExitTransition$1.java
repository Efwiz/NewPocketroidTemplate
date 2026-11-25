package org.catrobat.catroid.ui;

import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.catrobat.catroid.databinding.ActivityLoadingBinding;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity$onCreate$5$performExitTransition$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: MainMenuActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainMenuActivity$onCreate$5$performExitTransition$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ViewGroup $windowContent;
    final /* synthetic */ MainMenuActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainMenuActivity$onCreate$5$performExitTransition$1(MainMenuActivity mainMenuActivity, ViewGroup viewGroup) {
        super(0);
        this.this$0 = mainMenuActivity;
        this.$windowContent = viewGroup;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        ActivityLoadingBinding activityLoadingBinding = this.this$0.loadingBinding;
        ActivityLoadingBinding activityLoadingBinding2 = null;
        if (activityLoadingBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding = null;
        }
        ConstraintLayout root = activityLoadingBinding.getRoot();
        ActivityLoadingBinding activityLoadingBinding3 = this.this$0.loadingBinding;
        if (activityLoadingBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding3 = null;
        }
        root.setPivotX(activityLoadingBinding3.getRoot().getWidth() / 2.0f);
        ActivityLoadingBinding activityLoadingBinding4 = this.this$0.loadingBinding;
        if (activityLoadingBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding4 = null;
        }
        ConstraintLayout root2 = activityLoadingBinding4.getRoot();
        ActivityLoadingBinding activityLoadingBinding5 = this.this$0.loadingBinding;
        if (activityLoadingBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding5 = null;
        }
        root2.setPivotY(activityLoadingBinding5.getRoot().getHeight() / 2.0f);
        ActivityLoadingBinding activityLoadingBinding6 = this.this$0.loadingBinding;
        if (activityLoadingBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
        } else {
            activityLoadingBinding2 = activityLoadingBinding6;
        }
        ViewPropertyAnimator interpolator = activityLoadingBinding2.getRoot().animate().scaleX(3.5f).scaleY(3.5f).alpha(0.0f).setDuration(600L).setInterpolator(new AccelerateInterpolator());
        final ViewGroup viewGroup = this.$windowContent;
        final MainMenuActivity mainMenuActivity = this.this$0;
        interpolator.withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$performExitTransition$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainMenuActivity$onCreate$5$performExitTransition$1.invoke$lambda$0(viewGroup, mainMenuActivity);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ViewGroup viewGroup, MainMenuActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityLoadingBinding activityLoadingBinding = this$0.loadingBinding;
        if (activityLoadingBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding = null;
        }
        viewGroup.removeView(activityLoadingBinding.getRoot());
    }
}
