package org.catrobat.catroid.pocketmusic.fastscroller;

import androidx.recyclerview.widget.RecyclerView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/RecyclerViewScrollListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private int oldScrollState = 0;
    private final FastScroller scroller;

    public RecyclerViewScrollListener(FastScroller scroller) {
        this.scroller = scroller;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(RecyclerView recyclerView, int newScrollState) {
        if (newScrollState == 0 && this.oldScrollState != 0) {
            this.scroller.getViewProvider().onScrollFinished();
        } else if (newScrollState != 0 && this.oldScrollState == 0) {
            this.scroller.getViewProvider().onScrollStarted();
        }
        this.oldScrollState = newScrollState;
        super.onScrollStateChanged(recyclerView, newScrollState);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        if (this.scroller.shouldUpdateHandlePosition()) {
            updateHandlePosition(rv);
        }
        super.onScrolled(rv, dx, dy);
    }

    void updateHandlePosition(RecyclerView rv) {
        int iComputeHorizontalScrollOffset;
        int iComputeHorizontalScrollExtent;
        int iComputeHorizontalScrollRange;
        if (this.scroller.isVertical()) {
            iComputeHorizontalScrollOffset = rv.computeVerticalScrollOffset();
            iComputeHorizontalScrollExtent = rv.computeVerticalScrollExtent();
            iComputeHorizontalScrollRange = rv.computeVerticalScrollRange();
        } else {
            iComputeHorizontalScrollOffset = rv.computeHorizontalScrollOffset();
            iComputeHorizontalScrollExtent = rv.computeHorizontalScrollExtent();
            iComputeHorizontalScrollRange = rv.computeHorizontalScrollRange();
        }
        this.scroller.setScrollerPosition(iComputeHorizontalScrollOffset / (iComputeHorizontalScrollRange - iComputeHorizontalScrollExtent));
    }
}
