package org.catrobat.catroid.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/OtherPranksManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: OtherPranksManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\rJ\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\rR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ui/OtherPranksManager;", "", "()V", "isPrankRunning", "", "()Z", "setPrankRunning", "(Z)V", "doBarrelRoll", "", "activity", "Landroid/app/Activity;", "originalView", "Landroid/view/View;", "gravityDrop", Promotion.ACTION_VIEW, "runAway", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OtherPranksManager {
    public static final OtherPranksManager INSTANCE = new OtherPranksManager();
    private static boolean isPrankRunning;

    private OtherPranksManager() {
    }

    public final boolean isPrankRunning() {
        return isPrankRunning;
    }

    public final void setPrankRunning(boolean z) {
        isPrankRunning = z;
    }

    public final void runAway(Activity activity, View view) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(view, "view");
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) decorView;
        int width = viewGroup.getWidth() - view.getWidth();
        int height = viewGroup.getHeight() - view.getHeight();
        float fRandom = (float) (Math.random() * ((double) width));
        view.animate().x(fRandom).y((float) (Math.random() * ((double) height))).setDuration(250L).setInterpolator(new DecelerateInterpolator()).start();
    }

    public final void doBarrelRoll(Activity activity, final View originalView) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(originalView, "originalView");
        if (isPrankRunning) {
            return;
        }
        isPrankRunning = true;
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
        decorView.animate().rotationBy(360.0f).setDuration(1200L).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.OtherPranksManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                OtherPranksManager.doBarrelRoll$lambda$0(originalView);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doBarrelRoll$lambda$0(View originalView) {
        Intrinsics.checkNotNullParameter(originalView, "$originalView");
        isPrankRunning = false;
        originalView.performClick();
    }

    public final void gravityDrop(Activity activity, final View view) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(view, "view");
        if (isPrankRunning) {
            return;
        }
        isPrankRunning = true;
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        final float y = view.getY();
        view.animate().y(((ViewGroup) decorView).getHeight() - view.getHeight()).setDuration(800L).setInterpolator(new BounceInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.OtherPranksManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                OtherPranksManager.gravityDrop$lambda$3(view, y);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gravityDrop$lambda$3(final View view, final float f) {
        Intrinsics.checkNotNullParameter(view, "$view");
        view.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.OtherPranksManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                OtherPranksManager.gravityDrop$lambda$3$lambda$2(view, f);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gravityDrop$lambda$3$lambda$2(final View view, float f) {
        Intrinsics.checkNotNullParameter(view, "$view");
        view.animate().y(f).setDuration(300L).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.OtherPranksManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                OtherPranksManager.gravityDrop$lambda$3$lambda$2$lambda$1(view);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gravityDrop$lambda$3$lambda$2$lambda$1(View view) {
        Intrinsics.checkNotNullParameter(view, "$view");
        isPrankRunning = false;
        view.performClick();
    }
}
