package org.catrobat.paintroid.ui.dragndrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.presenter.LayerPresenter;
import org.catrobat.paintroid.ui.LayerAdapter;

/* JADX INFO: compiled from: DragAndDropListView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 A2\u00020\u00012\u00020\u0002:\u0001AB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010 2\u0006\u0010)\u001a\u00020\nH\u0002J\u0012\u0010*\u001a\u0004\u0018\u00010 2\u0006\u0010+\u001a\u00020\nH\u0002J\u0012\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\"H\u0002J\b\u0010/\u001a\u00020$H\u0002J\u0010\u00100\u001a\u00020-2\u0006\u0010\u001b\u001a\u00020\nH\u0002J\u0010\u00101\u001a\u00020$2\u0006\u00102\u001a\u00020\nH\u0002J\b\u00103\u001a\u00020$H\u0003J\u0010\u00104\u001a\u00020-2\u0006\u00105\u001a\u000206H\u0016J\u000e\u00107\u001a\u00020$2\u0006\u00108\u001a\u00020\u0012J\u0012\u00109\u001a\u00020$2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002J\u000e\u0010:\u001a\u00020$2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010;\u001a\u00020$2\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010<\u001a\u00020$H\u0016J\u0018\u0010=\u001a\u00020$2\u0006\u0010>\u001a\u00020\n2\u0006\u0010?\u001a\u00020-H\u0002J\b\u0010@\u001a\u00020$H\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lorg/catrobat/paintroid/ui/dragndrop/DragAndDropListView;", "Landroidx/recyclerview/widget/RecyclerView;", "Lorg/catrobat/paintroid/ui/dragndrop/ListItemDragHandler;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attributes", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "downY", "", "hoveringListItem", "Landroid/graphics/drawable/BitmapDrawable;", "initialPosition", "layerAdapter", "Lorg/catrobat/paintroid/ui/LayerAdapter;", "manager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getManager$Paintroid_signedRelease", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "setManager$Paintroid_signedRelease", "(Landroidx/recyclerview/widget/LinearLayoutManager;)V", "mergePosition", "offsetToCenter", "position", "presenter", "Lorg/catrobat/paintroid/ui/dragndrop/DragAndDropPresenter;", "scrollAmount", Promotion.ACTION_VIEW, "Landroid/view/View;", "viewBounds", "Landroid/graphics/Rect;", "dispatchDraw", "", "canvas", "Landroid/graphics/Canvas;", "getHoveringListItem", "getItemAbove", "itemPositionAbove", "getItemBelow", "itemPositionBelow", "handleScroll", "", "bounds", "handleTouchUp", "isPositionValid", "mergeItems", "mergeWith", "notifyDataSetChanged", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "setLayerAdapter", "adapter", "setOffsetToCenter", "setPresenter", "startDragging", "stopDragging", "swapItems", "swapWith", "isScrolling", "swapListItems", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DragAndDropListView extends RecyclerView implements ListItemDragHandler {
    private static final int ALPHA_VALUE = 192;
    private static final int BRIGHTNESS_ADD_VALUE = 2236962;
    private static final int BRIGHTNESS_MUL_VALUE = 16777215;
    private float downY;
    private BitmapDrawable hoveringListItem;
    private int initialPosition;
    private LayerAdapter layerAdapter;
    public LinearLayoutManager manager;
    private int mergePosition;
    private int offsetToCenter;
    private int position;
    private DragAndDropPresenter presenter;
    private int scrollAmount;
    private View view;
    private Rect viewBounds;

    public final LinearLayoutManager getManager$Paintroid_signedRelease() {
        LinearLayoutManager linearLayoutManager = this.manager;
        if (linearLayoutManager != null) {
            return linearLayoutManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("manager");
        return null;
    }

    public final void setManager$Paintroid_signedRelease(LinearLayoutManager linearLayoutManager) {
        Intrinsics.checkNotNullParameter(linearLayoutManager, "<set-?>");
        this.manager = linearLayoutManager;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragAndDropListView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        setHasFixedSize(true);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setHasStableIds(true);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragAndDropListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        setHasFixedSize(true);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setHasStableIds(true);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragAndDropListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        setHasFixedSize(true);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setHasStableIds(true);
    }

    public final void setPresenter(DragAndDropPresenter presenter) {
        Intrinsics.checkNotNullParameter(presenter, "presenter");
        this.presenter = presenter;
    }

    public final void setLayerAdapter(LayerAdapter adapter) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        this.layerAdapter = adapter;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.hoveringListItem == null) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        if (action == 0) {
            this.downY = event.getY();
        } else if (action == 1) {
            handleTouchUp();
            this.downY = event.getY();
            notifyDataSetChanged();
            getParent().requestDisallowInterceptTouchEvent(false);
        } else if (action == 2) {
            getParent().requestDisallowInterceptTouchEvent(true);
            float y = event.getY();
            float f = this.downY;
            this.downY = (f + (y - f)) - this.offsetToCenter;
            Rect rect = this.viewBounds;
            if (rect != null) {
                rect.offsetTo(rect.left, (int) this.downY);
                int iMin = Math.min(getHeight() - rect.height(), Math.max(0, rect.top));
                int iHeight = rect.height() + iMin;
                BitmapDrawable bitmapDrawable = this.hoveringListItem;
                if (bitmapDrawable != null) {
                    bitmapDrawable.setBounds(rect.left, iMin, rect.right, iHeight);
                }
            }
            boolean zHandleScroll = handleScroll(this.viewBounds);
            invalidate();
            if (!zHandleScroll) {
                swapListItems();
            }
        } else if (action == 3) {
            stopDragging();
        }
        return true;
    }

    private final boolean handleScroll(Rect bounds) {
        Integer numValueOf = bounds == null ? null : Integer.valueOf(bounds.top);
        Integer numValueOf2 = bounds != null ? Integer.valueOf(bounds.height()) : null;
        if (this.scrollAmount == 0) {
            this.scrollAmount = getHeight() / 5;
        }
        if (numValueOf != null && canScrollVertically(-1) && numValueOf.intValue() <= 0) {
            int iFindFirstVisibleItemPosition = getManager$Paintroid_signedRelease().findFirstVisibleItemPosition();
            smoothScrollBy(0, -this.scrollAmount);
            swapItems(iFindFirstVisibleItemPosition, true);
            return true;
        }
        if (numValueOf == null || numValueOf2 == null || !canScrollVertically(1) || numValueOf.intValue() <= getHeight() - 300) {
            return false;
        }
        int iFindLastVisibleItemPosition = getManager$Paintroid_signedRelease().findLastVisibleItemPosition();
        smoothScrollBy(0, this.scrollAmount);
        swapItems(iFindLastVisibleItemPosition, true);
        return true;
    }

    @Override // org.catrobat.paintroid.ui.dragndrop.ListItemDragHandler
    public void startDragging(int position, View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        View view2 = this.view;
        if (view2 != null) {
            view2.setVisibility(0);
        }
        this.view = view;
        this.initialPosition = position;
        this.position = position;
        view.setVisibility(4);
        this.hoveringListItem = getHoveringListItem(view);
        setOffsetToCenter(this.viewBounds);
        invalidate();
    }

    @Override // org.catrobat.paintroid.ui.dragndrop.ListItemDragHandler
    public void stopDragging() {
        if (this.hoveringListItem == null) {
            return;
        }
        DragAndDropPresenter dragAndDropPresenter = this.presenter;
        if (dragAndDropPresenter instanceof LayerPresenter) {
            if (dragAndDropPresenter == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.presenter.LayerPresenter");
            }
            ((LayerPresenter) dragAndDropPresenter).resetMergeColor(this.mergePosition);
        }
        this.mergePosition = -1;
        View view = this.view;
        if (view != null) {
            view.setVisibility(0);
        }
        this.view = null;
        this.hoveringListItem = null;
        invalidate();
        notifyDataSetChanged();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.dispatchDraw(canvas);
        BitmapDrawable bitmapDrawable = this.hoveringListItem;
        if (bitmapDrawable == null) {
            return;
        }
        bitmapDrawable.draw(canvas);
    }

    private final BitmapDrawable getHoveringListItem(View view) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        LightingColorFilter lightingColorFilter = new LightingColorFilter(16777215, BRIGHTNESS_ADD_VALUE);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmapCreateBitmap);
        bitmapDrawable.setColorFilter(lightingColorFilter);
        Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        this.viewBounds = rect;
        bitmapDrawable.setBounds(rect);
        bitmapDrawable.setAlpha(192);
        return bitmapDrawable;
    }

    private final void setOffsetToCenter(Rect viewBounds) {
        if (viewBounds == null) {
            return;
        }
        this.offsetToCenter = viewBounds.height() / 2;
    }

    private final View getItemAbove(int itemPositionAbove) {
        if (!isPositionValid(itemPositionAbove)) {
            return null;
        }
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerAdapter");
            layerAdapter = null;
        }
        LayerContracts.LayerViewHolder viewHolderAt = layerAdapter.getViewHolderAt(itemPositionAbove);
        if (viewHolderAt == null) {
            return null;
        }
        return viewHolderAt.getView();
    }

    private final View getItemBelow(int itemPositionBelow) {
        if (!isPositionValid(itemPositionBelow)) {
            return null;
        }
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerAdapter");
            layerAdapter = null;
        }
        LayerContracts.LayerViewHolder viewHolderAt = layerAdapter.getViewHolderAt(itemPositionBelow);
        if (viewHolderAt == null) {
            return null;
        }
        return viewHolderAt.getView();
    }

    private final void swapItems(int swapWith, boolean isScrolling) {
        DragAndDropPresenter dragAndDropPresenter = this.presenter;
        if (dragAndDropPresenter != null) {
            this.position = dragAndDropPresenter.swapItemsVisually(this.position, swapWith);
        }
        View view = this.view;
        if (view != null) {
            view.setVisibility(0);
        }
        this.mergePosition = -1;
        if (isScrolling) {
            RecyclerView.Adapter adapter = getAdapter();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemMoved(this.position, swapWith);
            return;
        }
        notifyDataSetChanged();
    }

    private final void mergeItems(int mergeWith) {
        DragAndDropPresenter dragAndDropPresenter = this.presenter;
        if (dragAndDropPresenter != null) {
            dragAndDropPresenter.markMergeable(this.position, mergeWith);
        }
        this.mergePosition = mergeWith;
    }

    private final void notifyDataSetChanged() {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerAdapter");
            layerAdapter = null;
        }
        layerAdapter.clearViewHolders();
    }

    private final void swapListItems() {
        boolean z;
        boolean z2;
        boolean z3;
        int i = this.position;
        int i2 = i - 1;
        boolean z4 = true;
        int i3 = i + 1;
        View itemBelow = getItemBelow(i3);
        View itemAbove = getItemAbove(i2);
        if (itemAbove != null) {
            z2 = this.downY < itemAbove.getY();
            View view = this.view;
            z = view != null && this.downY < itemAbove.getY() + (((float) view.getHeight()) / 2.0f);
        } else {
            z = false;
            z2 = false;
        }
        if (itemBelow != null) {
            z3 = this.downY > itemBelow.getY();
            View view2 = this.view;
            if (view2 == null || this.downY <= itemBelow.getY() - (view2.getHeight() / 2.0f)) {
                z4 = false;
            }
        } else {
            z4 = false;
            z3 = false;
        }
        if (z3 || z2) {
            if (z3) {
                i2 = i3;
            }
            swapItems(i2, false);
        } else if (z || z4) {
            if (!z) {
                i2 = i3;
            }
            mergeItems(i2);
        } else if (this.mergePosition != -1) {
            this.mergePosition = -1;
            notifyDataSetChanged();
        }
    }

    private final boolean isPositionValid(int position) {
        if (position < 0) {
            return false;
        }
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerAdapter");
            layerAdapter = null;
        }
        return position < layerAdapter.getItemCount();
    }

    private final void handleTouchUp() {
        int i = this.mergePosition;
        if (i != -1) {
            DragAndDropPresenter dragAndDropPresenter = this.presenter;
            if (dragAndDropPresenter != null) {
                dragAndDropPresenter.mergeItems(this.initialPosition, i);
            }
        } else {
            DragAndDropPresenter dragAndDropPresenter2 = this.presenter;
            if (dragAndDropPresenter2 != null) {
                dragAndDropPresenter2.reorderItems(this.initialPosition, this.position);
            }
        }
        stopDragging();
    }
}
