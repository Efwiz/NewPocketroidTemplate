package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/viewprovider/DefaultScrollerViewProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DefaultScrollerViewProvider extends ScrollerViewProvider {
    private View bubble;
    private View handle;

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider
    public View provideHandleView(ViewGroup container) {
        this.handle = new View(getContext());
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_inset_top);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_inset_bottom);
        int dimensionPixelSize3 = !getScroller().isVertical() ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_inset);
        this.handle.setBackground(new InsetDrawable(ContextCompat.getDrawable(getContext(), R.drawable.pocketmusic_fastscroller_handle), dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize2));
        this.handle.setLayoutParams(new ViewGroup.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_clickable_width), getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_height)));
        return this.handle;
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider
    public View provideBubbleView(ViewGroup container) {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.pocketmusic_fastscroller_bubble, container, false);
        this.bubble = viewInflate;
        return viewInflate;
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider
    public TextView provideBubbleTextView() {
        return (TextView) this.bubble;
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider
    public int getBubbleOffset() {
        float width;
        int width2;
        if (getScroller().isVertical()) {
            width = this.handle.getHeight() / 2.0f;
            width2 = this.bubble.getHeight();
        } else {
            width = this.handle.getWidth() / 2.0f;
            width2 = this.bubble.getWidth();
        }
        return (int) (width - width2);
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider
    protected ViewBehavior provideHandleBehavior() {
        return new DefaultHandleBehavior(new VisibilityAnimationManager.Builder(this.handle).withHideDelay(2000).withHideAnimator(R.animator.pocketmusic_fastscroller_fade_out).withShowAnimator(R.animator.pocketmusic_fastscroller_fade_in).build());
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider
    protected ViewBehavior provideBubbleBehavior() {
        return new DefaultBubbleBehavior(new VisibilityAnimationManager.Builder(this.bubble).withHideDelay(0).withPivotX(0.5f).withPivotY(1.0f).build());
    }
}
