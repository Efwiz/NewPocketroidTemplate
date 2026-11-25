package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/viewprovider/VisibilityAnimationManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VisibilityAnimationManager {
    protected AnimatorSet hideAnimator;
    private float pivotXRelative;
    private float pivotYRelative;
    protected AnimatorSet showAnimator;
    protected final View view;

    protected VisibilityAnimationManager(final View view, int showAnimator, int hideAnimator, float pivotXRelative, float pivotYRelative, int hideDelay) {
        this.view = view;
        this.pivotXRelative = pivotXRelative;
        this.pivotYRelative = pivotYRelative;
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), hideAnimator);
        this.hideAnimator = animatorSet;
        animatorSet.setStartDelay(hideDelay);
        this.hideAnimator.setTarget(view);
        AnimatorSet animatorSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), showAnimator);
        this.showAnimator = animatorSet2;
        animatorSet2.setTarget(view);
        this.hideAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.VisibilityAnimationManager.1
            boolean wasCanceled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!this.wasCanceled) {
                    view.setVisibility(4);
                }
                this.wasCanceled = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                this.wasCanceled = true;
            }
        });
        updatePivot();
    }

    public void show() {
        this.hideAnimator.cancel();
        if (this.view.getVisibility() == 4) {
            this.view.setVisibility(0);
            updatePivot();
            this.showAnimator.start();
        }
    }

    public void hide() {
        updatePivot();
        this.hideAnimator.start();
    }

    protected void updatePivot() {
        View view = this.view;
        view.setPivotX(this.pivotXRelative * view.getMeasuredWidth());
        View view2 = this.view;
        view2.setPivotY(this.pivotYRelative * view2.getMeasuredHeight());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/viewprovider/VisibilityAnimationManager$AbsBuilder.class
      classes14.dex
     */
    public static abstract class AbsBuilder<T extends VisibilityAnimationManager> {
        protected final View view;
        protected int showAnimatorResource = R.animator.pocketmusic_fastscroller_show;
        protected int hideAnimatorResource = R.animator.pocketmusic_fastscroller_hide;
        protected int hideDelay = 1000;
        protected float pivotX = 0.5f;
        protected float pivotY = 0.5f;

        public abstract T build();

        public AbsBuilder(View view) {
            this.view = view;
        }

        public AbsBuilder<T> withShowAnimator(int showAnimatorResource) {
            this.showAnimatorResource = showAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideAnimator(int hideAnimatorResource) {
            this.hideAnimatorResource = hideAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideDelay(int hideDelay) {
            this.hideDelay = hideDelay;
            return this;
        }

        public AbsBuilder<T> withPivotX(float pivotX) {
            this.pivotX = pivotX;
            return this;
        }

        public AbsBuilder<T> withPivotY(float pivotY) {
            this.pivotY = pivotY;
            return this;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/viewprovider/VisibilityAnimationManager$Builder.class
      classes14.dex
     */
    public static class Builder extends AbsBuilder<VisibilityAnimationManager> {
        public Builder(View view) {
            super(view);
        }

        @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.VisibilityAnimationManager.AbsBuilder
        public VisibilityAnimationManager build() {
            return new VisibilityAnimationManager(this.view, this.showAnimatorResource, this.hideAnimatorResource, this.pivotX, this.pivotY, this.hideDelay);
        }
    }
}
