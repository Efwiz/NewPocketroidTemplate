package org.catrobat.catroid.ui.dragndrop;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.EndBrick;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dragndrop/BrickListView.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickListView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020+J\u0010\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020/H\u0016J\u001c\u00100\u001a\u00020+2\b\u00101\u001a\u0004\u0018\u0001022\b\u0010.\u001a\u0004\u0018\u00010/H\u0007J\u0012\u00103\u001a\u0004\u0018\u0001022\u0006\u00104\u001a\u00020\tH\u0002J\u0014\u00105\u001a\u00020+2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\t07J\u0006\u00108\u001a\u00020+J\u0010\u00109\u001a\u00020\u001f2\u0006\u0010:\u001a\u00020\tH\u0002J\u0010\u0010;\u001a\u00020\u001f2\u0006\u0010<\u001a\u00020=H\u0017J\u0012\u0010>\u001a\u00020+2\b\u00101\u001a\u0004\u0018\u000102H\u0002J\b\u0010?\u001a\u00020+H\u0002J\u0010\u0010@\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0016J\u0010\u0010C\u001a\u00020+2\u0006\u0010(\u001a\u00020)H\u0002J\u001a\u0010D\u001a\u00020+2\b\u0010E\u001a\u0004\u0018\u0001022\u0006\u0010F\u001a\u00020\tH\u0002J\u0010\u0010G\u001a\u00020+2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\u0006\u0010H\u001a\u00020+J\b\u0010I\u001a\u00020+H\u0002R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b\"\u0010!R\u000e\u0010#\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006J"}, d2 = {"Lorg/catrobat/catroid/ui/dragndrop/BrickListView;", "Landroid/widget/ListView;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attributes", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "brickAdapterInterface", "Lorg/catrobat/catroid/ui/dragndrop/BrickAdapterInterface;", "brickPositionsToHighlight", "", "getBrickPositionsToHighlight", "()Ljava/util/List;", "brickToMove", "Lorg/catrobat/catroid/content/bricks/Brick;", "currentPositionOfHoveringBrick", "downY", "", "dragMode", "Lorg/catrobat/catroid/ui/dragndrop/DragMode;", "getDragMode", "()Lorg/catrobat/catroid/ui/dragndrop/DragMode;", "setDragMode", "(Lorg/catrobat/catroid/ui/dragndrop/DragMode;)V", "hoveringDrawable", "Landroid/graphics/drawable/BitmapDrawable;", "invalidateHoveringItem", "", "isCurrentlyHighlighted", "()Z", "isCurrentlyMoving", "lowerScrollBound", "motionEventId", "offsetToCenter", "translucentBlack", "upperScrollBound", "viewBounds", "Landroid/graphics/Rect;", "cancelHighlighting", "", "cancelMove", "dispatchDraw", "canvas", "Landroid/graphics/Canvas;", "drawHighlightedItem", Promotion.ACTION_VIEW, "Landroid/view/View;", "getChildAtVisiblePosition", "positionInAdapter", "highlightControlStructureBricks", "positions", "", "highlightMovingItem", "isPositionValid", "position", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "prepareHoveringItem", "scrollWhileDragging", "setAdapter", "adapter", "Landroid/widget/ListAdapter;", "setOffsetToCenter", "startAnimationToSwap", "viewToSwapWith", "translationY", "startMoving", "stopMoving", "swapListItems", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickListView extends ListView {
    private BrickAdapterInterface brickAdapterInterface;
    private final List<Integer> brickPositionsToHighlight;
    private Brick brickToMove;
    private int currentPositionOfHoveringBrick;
    private float downY;
    private DragMode dragMode;
    private BitmapDrawable hoveringDrawable;
    private boolean invalidateHoveringItem;
    private int lowerScrollBound;
    private int motionEventId;
    private int offsetToCenter;
    private final int translucentBlack;
    private int upperScrollBound;
    private final Rect viewBounds;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dragndrop/BrickListView$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickListView.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DragMode.values().length];
            try {
                iArr[DragMode.NEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DragMode.LEGACY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final DragMode getDragMode() {
        return this.dragMode;
    }

    public final void setDragMode(DragMode dragMode) {
        Intrinsics.checkNotNullParameter(dragMode, "<set-?>");
        this.dragMode = dragMode;
    }

    public BrickListView(Context context) {
        super(context);
        this.viewBounds = new Rect();
        this.motionEventId = -1;
        this.translucentBlack = Color.argb(128, 0, 0, 0);
        this.dragMode = DragMode.NEW;
        this.brickPositionsToHighlight = new ArrayList();
    }

    public BrickListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.viewBounds = new Rect();
        this.motionEventId = -1;
        this.translucentBlack = Color.argb(128, 0, 0, 0);
        this.dragMode = DragMode.NEW;
        this.brickPositionsToHighlight = new ArrayList();
    }

    public BrickListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.viewBounds = new Rect();
        this.motionEventId = -1;
        this.translucentBlack = Color.argb(128, 0, 0, 0);
        this.dragMode = DragMode.NEW;
        this.brickPositionsToHighlight = new ArrayList();
    }

    public final List<Integer> getBrickPositionsToHighlight() {
        return this.brickPositionsToHighlight;
    }

    public final boolean isCurrentlyMoving() {
        return this.hoveringDrawable != null;
    }

    public final boolean isCurrentlyHighlighted() {
        return !this.brickPositionsToHighlight.isEmpty();
    }

    public final void highlightMovingItem() {
        BitmapDrawable bitmapDrawable = this.hoveringDrawable;
        Intrinsics.checkNotNull(bitmapDrawable);
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(bitmapDrawable, "alpha", 255, 0);
        objectAnimatorOfInt.setDuration(250L);
        objectAnimatorOfInt.setRepeatMode(2);
        objectAnimatorOfInt.setRepeatCount(5);
        objectAnimatorOfInt.start();
        objectAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.dragndrop.BrickListView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BrickListView.highlightMovingItem$lambda$0(this.f$0, valueAnimator);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void highlightMovingItem$lambda$0(BrickListView this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.invalidate();
    }

    public final void cancelHighlighting() {
        this.brickPositionsToHighlight.clear();
        invalidate();
    }

    public final void highlightControlStructureBricks(Collection<Integer> positions) {
        Intrinsics.checkNotNullParameter(positions, "positions");
        cancelHighlighting();
        this.brickPositionsToHighlight.addAll(positions);
        invalidate();
    }

    public final void startMoving(Brick brickToMove) {
        DragMode dragMode;
        if (SettingsFragment.isOldDragEnabled(CatroidApplication.getAppContext())) {
            dragMode = DragMode.NEW;
        } else {
            dragMode = DragMode.LEGACY;
        }
        this.dragMode = dragMode;
        cancelMove();
        ArrayList arrayList = new ArrayList();
        if (brickToMove != null) {
            brickToMove.addToFlatList(arrayList);
        }
        if (((brickToMove != null ? brickToMove.getParent() : null) instanceof CompositeBrick) && (brickToMove instanceof EndBrick)) {
            this.brickToMove = brickToMove;
            arrayList.clear();
        } else {
            if (brickToMove != arrayList.get(0)) {
                return;
            }
            this.brickToMove = arrayList.get(0);
            arrayList.remove(0);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.dragMode.ordinal()];
        if (i == 1) {
            this.upperScrollBound = getHeight() / 4;
            this.lowerScrollBound = (getHeight() * 3) / 4;
        } else if (i == 2) {
            this.upperScrollBound = getHeight() / 8;
            this.lowerScrollBound = getHeight() / 48;
        }
        BrickAdapterInterface brickAdapterInterface = this.brickAdapterInterface;
        Intrinsics.checkNotNull(brickAdapterInterface);
        int position = brickAdapterInterface.getPosition(this.brickToMove);
        this.currentPositionOfHoveringBrick = position;
        this.invalidateHoveringItem = true;
        prepareHoveringItem(getChildAtVisiblePosition(position));
        BrickAdapterInterface brickAdapterInterface2 = this.brickAdapterInterface;
        if (brickAdapterInterface2 != null) {
            brickAdapterInterface2.setItemVisible(this.currentPositionOfHoveringBrick, false);
        }
        BrickAdapterInterface brickAdapterInterface3 = this.brickAdapterInterface;
        Intrinsics.checkNotNull(brickAdapterInterface3);
        if (brickAdapterInterface3.removeItems(arrayList)) {
            return;
        }
        invalidateViews();
    }

    public final void stopMoving() {
        BrickAdapterInterface brickAdapterInterface = this.brickAdapterInterface;
        if (brickAdapterInterface != null) {
            brickAdapterInterface.moveItemTo(this.currentPositionOfHoveringBrick, this.brickToMove);
        }
        cancelMove();
    }

    public final void cancelMove() {
        BrickAdapterInterface brickAdapterInterface = this.brickAdapterInterface;
        if (brickAdapterInterface != null) {
            brickAdapterInterface.setAllPositionsVisible();
        }
        this.brickToMove = null;
        this.hoveringDrawable = null;
        this.motionEventId = -1;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0063  */
    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.hoveringDrawable == null) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        if (action == 0) {
            this.downY = event.getY();
            this.motionEventId = event.getPointerId(0);
        } else if (action == 1) {
            stopMoving();
        } else if (action == 2) {
            float y = event.getY();
            float f = this.downY;
            this.downY = (f + (y - f)) - this.offsetToCenter;
            Rect rect = this.viewBounds;
            rect.offsetTo(rect.left, (int) this.downY);
            BitmapDrawable bitmapDrawable = this.hoveringDrawable;
            if (bitmapDrawable != null) {
                bitmapDrawable.setBounds(this.viewBounds);
            }
            invalidate();
            swapListItems();
            scrollWhileDragging();
        } else if (action == 3) {
            stopMoving();
        } else if (action == 6 && event.getPointerId((event.getAction() & 65280) >> 8) == this.motionEventId) {
            stopMoving();
        }
        return true;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        View childAtVisiblePosition;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.dispatchDraw(canvas);
        if (this.brickToMove != null || !this.brickPositionsToHighlight.isEmpty()) {
            canvas.drawColor(this.translucentBlack);
        }
        if (this.invalidateHoveringItem && (childAtVisiblePosition = getChildAtVisiblePosition(this.currentPositionOfHoveringBrick)) != null) {
            this.invalidateHoveringItem = false;
            prepareHoveringItem(childAtVisiblePosition);
        }
        BitmapDrawable bitmapDrawable = this.hoveringDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.draw(canvas);
        }
        Iterator<Integer> it = this.brickPositionsToHighlight.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            int firstVisiblePosition = getFirstVisiblePosition();
            if (iIntValue <= getLastVisiblePosition() && firstVisiblePosition <= iIntValue) {
                drawHighlightedItem(getChildAtVisiblePosition(iIntValue), canvas);
            }
        }
    }

    public final void drawHighlightedItem(View view, Canvas canvas) {
        if (view == null || canvas == null) {
            return;
        }
        float left = view.getLeft();
        float top = view.getTop();
        int iSave = canvas.save();
        canvas.translate(left, top);
        try {
            view.draw(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    private final void prepareHoveringItem(View view) {
        if (view == null) {
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        this.viewBounds.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        Resources resources = getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmapCreateBitmap);
        bitmapDrawable.setBounds(this.viewBounds);
        this.hoveringDrawable = bitmapDrawable;
        setOffsetToCenter(this.viewBounds);
    }

    private final void setOffsetToCenter(Rect viewBounds) {
        this.offsetToCenter = viewBounds.height() / 2;
    }

    private final void swapListItems() {
        int i = this.currentPositionOfHoveringBrick;
        int i2 = i - 1;
        int i3 = i + 1;
        View childAtVisiblePosition = isPositionValid(i3) ? getChildAtVisiblePosition(i3) : null;
        View childAtVisiblePosition2 = isPositionValid(i2) ? getChildAtVisiblePosition(i2) : null;
        boolean z = childAtVisiblePosition != null && this.downY > childAtVisiblePosition.getY();
        boolean z2 = childAtVisiblePosition2 != null && this.downY < childAtVisiblePosition2.getY();
        if (z || z2) {
            if (z) {
                i2 = i3;
            }
            int iHeight = this.viewBounds.height();
            int i4 = z ? 10 - iHeight : iHeight - 10;
            BrickAdapterInterface brickAdapterInterface = this.brickAdapterInterface;
            if (brickAdapterInterface == null || !brickAdapterInterface.onItemMove(this.currentPositionOfHoveringBrick, i2)) {
                return;
            }
            BrickAdapterInterface brickAdapterInterface2 = this.brickAdapterInterface;
            if (brickAdapterInterface2 != null) {
                brickAdapterInterface2.setItemVisible(this.currentPositionOfHoveringBrick, true);
            }
            this.currentPositionOfHoveringBrick = i2;
            BrickAdapterInterface brickAdapterInterface3 = this.brickAdapterInterface;
            if (brickAdapterInterface3 != null) {
                brickAdapterInterface3.setItemVisible(i2, false);
            }
            if (!z) {
                childAtVisiblePosition = childAtVisiblePosition2;
            }
            startAnimationToSwap(childAtVisiblePosition, i4);
        }
    }

    private final void startAnimationToSwap(View viewToSwapWith, int translationY) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewToSwapWith, (Property<View, Float>) ListView.TRANSLATION_Y, translationY);
        objectAnimatorOfFloat.setDuration(250L);
        objectAnimatorOfFloat.start();
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: org.catrobat.catroid.ui.dragndrop.BrickListView.startAnimationToSwap.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                BrickListView.this.invalidateViews();
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:27:0x006e  */
    /* JADX WARN: Code duplicated, block: B:29:? A[RETURN, SYNTHETIC] */
    private final void scrollWhileDragging() {
        DragMode dragMode;
        float f;
        int i;
        if (SettingsFragment.isOldDragEnabled(CatroidApplication.getAppContext())) {
            dragMode = DragMode.NEW;
        } else {
            dragMode = DragMode.LEGACY;
        }
        this.dragMode = dragMode;
        int i2 = WhenMappings.$EnumSwitchMapping$0[dragMode.ordinal()];
        if (i2 == 1) {
            int height = getHeight() / 5;
            float f2 = this.downY;
            int i3 = this.upperScrollBound;
            if (f2 >= i3) {
                int i4 = this.lowerScrollBound;
                if (f2 > i4) {
                    f = 20 * ((f2 - i4) / height);
                }
                if (i != 0) {
                    smoothScrollBy(i, 0);
                }
            }
            f = (-20) * ((i3 - f2) / height);
            i = (int) f;
            if (i != 0) {
                smoothScrollBy(i, 0);
            }
        }
        if (i2 == 2) {
            float f3 = this.downY;
            if (f3 > this.lowerScrollBound) {
                smoothScrollBy(15, 0);
            } else if (f3 < this.upperScrollBound) {
                smoothScrollBy(-15, 0);
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        i = 0;
        if (i != 0) {
            smoothScrollBy(i, 0);
        }
    }

    private final View getChildAtVisiblePosition(int positionInAdapter) {
        return getChildAt(positionInAdapter - getFirstVisiblePosition());
    }

    private final boolean isPositionValid(int position) {
        return position >= 0 && position < getCount();
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter adapter) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        if (!(adapter instanceof BrickAdapterInterface)) {
            throw new IllegalArgumentException("Adapter has to implement the BrickListView.AdapterInterface.".toString());
        }
        super.setAdapter(adapter);
        this.brickAdapterInterface = (BrickAdapterInterface) adapter;
    }
}
