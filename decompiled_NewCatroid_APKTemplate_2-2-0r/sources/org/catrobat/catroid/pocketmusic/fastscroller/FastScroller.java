package org.catrobat.catroid.pocketmusic.fastscroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.DefaultScrollerViewProvider;
import org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ScrollerViewProvider;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/FastScroller.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FastScroller extends LinearLayout {
    private static final int STYLE_NONE = -1;
    private View bubble;
    private int bubbleColor;
    private int bubbleOffset;
    private int bubbleTextAppearance;
    private TextView bubbleTextView;
    private View handle;
    private int handleColor;
    private boolean manuallyChangingPosition;
    private TactScrollRecyclerView recyclerView;
    private final RecyclerViewScrollListener scrollListener;
    private int scrollerOrientation;
    private SectionTitleProvider titleProvider;
    private ScrollerViewProvider viewProvider;

    public FastScroller(Context context) {
        this(context, null);
    }

    public FastScroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.scrollListener = new RecyclerViewScrollListener(this);
        setClipChildren(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.FastScroller, org.DanVexTeam.NewCatroidApkTemplate.R.attr.FastScroller_style, 0);
        try {
            this.bubbleColor = typedArrayObtainStyledAttributes.getColor(0, -1);
            this.handleColor = typedArrayObtainStyledAttributes.getColor(2, -1);
            this.bubbleTextAppearance = typedArrayObtainStyledAttributes.getResourceId(1, -1);
            typedArrayObtainStyledAttributes.recycle();
            setViewProvider(new DefaultScrollerViewProvider());
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void setViewProvider(ScrollerViewProvider viewProvider) {
        removeAllViews();
        this.viewProvider = viewProvider;
        viewProvider.setFastScroller(this);
        this.bubble = viewProvider.provideBubbleView(this);
        this.handle = viewProvider.provideHandleView(this);
        this.bubbleTextView = viewProvider.provideBubbleTextView();
        addView(this.bubble);
        addView(this.handle);
    }

    public void setRecyclerView(TactScrollRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView.getAdapter() instanceof SectionTitleProvider) {
            this.titleProvider = (SectionTitleProvider) recyclerView.getAdapter();
        }
        recyclerView.addOnScrollListener(this.scrollListener);
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int orientation) {
        this.scrollerOrientation = orientation;
        super.setOrientation(orientation == 0 ? 1 : 0);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initHandleMovement();
        this.bubbleOffset = this.viewProvider.getBubbleOffset();
        applyStyling();
        if (isInEditMode()) {
            return;
        }
        this.scrollListener.updateHandlePosition(this.recyclerView);
    }

    private void applyStyling() {
        int i = this.bubbleColor;
        if (i != -1) {
            setBackgroundTint(this.bubbleTextView, i);
        }
        int i2 = this.handleColor;
        if (i2 != -1) {
            setBackgroundTint(this.handle, i2);
        }
        int i3 = this.bubbleTextAppearance;
        if (i3 != -1) {
            TextViewCompat.setTextAppearance(this.bubbleTextView, i3);
        }
    }

    private void setBackgroundTint(View view, int color) {
        Drawable drawableWrap = DrawableCompat.wrap(view.getBackground());
        if (drawableWrap == null) {
            return;
        }
        DrawableCompat.setTint(drawableWrap.mutate(), color);
        view.setBackground(drawableWrap);
    }

    private void initHandleMovement() {
        this.handle.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.pocketmusic.fastscroller.FastScroller.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0 || event.getAction() == 2) {
                    if (FastScroller.this.titleProvider != null && event.getAction() == 0) {
                        FastScroller.this.viewProvider.onHandleGrabbed();
                    }
                    FastScroller.this.manuallyChangingPosition = true;
                    float relativeTouchPosition = FastScroller.this.getRelativeTouchPosition(event);
                    FastScroller.this.setScrollerPosition(relativeTouchPosition);
                    FastScroller.this.setRecyclerViewPosition(relativeTouchPosition);
                    return true;
                }
                if (event.getAction() != 1) {
                    return false;
                }
                FastScroller.this.manuallyChangingPosition = false;
                if (FastScroller.this.titleProvider != null) {
                    FastScroller.this.viewProvider.onHandleReleased();
                }
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getRelativeTouchPosition(MotionEvent event) {
        float rawX;
        int width;
        int width2;
        if (isVertical()) {
            rawX = event.getRawY() - getViewRawCoordinate(this.handle, 1);
            width = getHeight();
            width2 = this.handle.getHeight();
        } else {
            rawX = event.getRawX() - getViewRawCoordinate(this.handle, 0);
            width = getWidth();
            width2 = this.handle.getWidth();
        }
        return rawX / (width - width2);
    }

    private float getViewRawCoordinate(View view, int vectorIndex) {
        int[] iArr = new int[2];
        iArr[vectorIndex] = (int) view.getY();
        ((View) view.getParent()).getLocationInWindow(iArr);
        return iArr[vectorIndex];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRecyclerViewPosition(float relativePos) {
        TextView textView;
        TactScrollRecyclerView tactScrollRecyclerView = this.recyclerView;
        if (tactScrollRecyclerView == null) {
            return;
        }
        int itemCount = tactScrollRecyclerView.getAdapter().getItemCount();
        int valueInRange = (int) getValueInRange(0.0f, itemCount - 2, (int) (relativePos * itemCount));
        int iFindFirstCompletelyVisibleItemPosition = valueInRange - ((LinearLayoutManager) this.recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        TactScrollRecyclerView tactScrollRecyclerView2 = this.recyclerView;
        tactScrollRecyclerView2.scrollBy(iFindFirstCompletelyVisibleItemPosition * tactScrollRecyclerView2.getTactViewWidth(), 0);
        SectionTitleProvider sectionTitleProvider = this.titleProvider;
        if (sectionTitleProvider == null || (textView = this.bubbleTextView) == null) {
            return;
        }
        textView.setText(sectionTitleProvider.getSectionTitle(valueInRange));
    }

    void setScrollerPosition(float relativePos) {
        if (isVertical()) {
            this.bubble.setY(getValueInRange(0.0f, getHeight() - this.bubble.getHeight(), ((getHeight() - this.handle.getHeight()) * relativePos) + this.bubbleOffset));
            this.handle.setY(getValueInRange(0.0f, getHeight() - this.handle.getHeight(), relativePos * (getHeight() - this.handle.getHeight())));
        } else {
            this.bubble.setX(getValueInRange(0.0f, getWidth() - this.bubble.getWidth(), ((getWidth() - this.handle.getWidth()) * relativePos) + this.bubbleOffset));
            this.handle.setX(getValueInRange(0.0f, getWidth() - this.handle.getWidth(), relativePos * (getWidth() - this.handle.getWidth())));
        }
    }

    public boolean isVertical() {
        return this.scrollerOrientation == 1;
    }

    boolean shouldUpdateHandlePosition() {
        return (this.handle == null || this.manuallyChangingPosition || this.recyclerView.getChildCount() <= 0) ? false : true;
    }

    ScrollerViewProvider getViewProvider() {
        return this.viewProvider;
    }

    private float getValueInRange(float min, float max, float value) {
        return Math.min(Math.max(min, value), max);
    }
}
