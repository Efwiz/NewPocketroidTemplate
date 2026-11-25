package org.catrobat.catroid.ui.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView.class
  classes14.dex
 */
/* JADX INFO: compiled from: AutoScrollableRecyclerView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000I\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0017\u0018\u0000 \"2\u00020\u0001:\u0002\"#B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010\u0019J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u0006\u0010 \u001a\u00020\u001fJ\u0006\u0010!\u001a\u00020\u001fR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013¨\u0006$"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView;", "Landroidx/recyclerview/widget/RecyclerView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "delayMillis", "", "itemsCount", "getItemsCount", "()I", "setItemsCount", "(I)V", "scrollHandler", "Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView$ScrollHandler;", "getScrollHandler", "()Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView$ScrollHandler;", "scrollHandler$delegate", "Lkotlin/Lazy;", "createScroller", "org/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView$createScroller$1", "position", "(I)Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView$createScroller$1;", "dispatchTouchEvent", "", "e", "Landroid/view/MotionEvent;", "pauseAutoScroll", "", "resumeAutoScroll", "scrollNext", "Companion", "ScrollHandler", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AutoScrollableRecyclerView extends RecyclerView {
    private static final int DELAY_BETWEEN_SCROLLS = 5000;
    private static final int DURATION_OF_SCROLL = 500;
    private static final int WHAT_SCROLL = 1;
    private final long delayMillis;
    private int itemsCount;

    /* JADX INFO: renamed from: scrollHandler$delegate, reason: from kotlin metadata */
    private final Lazy scrollHandler;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AutoScrollableRecyclerView(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AutoScrollableRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ AutoScrollableRecyclerView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoScrollableRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.scrollHandler = LazyKt.lazy(new Function0<ScrollHandler>() { // from class: org.catrobat.catroid.ui.recyclerview.AutoScrollableRecyclerView$scrollHandler$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AutoScrollableRecyclerView.ScrollHandler invoke() {
                return new AutoScrollableRecyclerView.ScrollHandler(this.this$0);
            }
        });
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AutoScrollableRecyclerView, 0, 0);
        try {
            this.delayMillis = typedArrayObtainStyledAttributes.getInt(0, 5000);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private final ScrollHandler getScrollHandler() {
        return (ScrollHandler) this.scrollHandler.getValue();
    }

    public final int getItemsCount() {
        return this.itemsCount;
    }

    public final void setItemsCount(int i) {
        this.itemsCount = i;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.catrobat.catroid.ui.recyclerview.AutoScrollableRecyclerView$createScroller$1] */
    private final AnonymousClass1 createScroller(int position) {
        ?? r1 = new LinearSmoothScroller(getContext()) { // from class: org.catrobat.catroid.ui.recyclerview.AutoScrollableRecyclerView.createScroller.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected int calculateTimeForScrolling(int dx) {
                return 500;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected int getHorizontalSnapPreference() {
                return 1;
            }
        };
        r1.setTargetPosition(position);
        return r1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent e) {
        Integer numValueOf = e != null ? Integer.valueOf(e.getAction()) : null;
        if (numValueOf != null && numValueOf.intValue() == 1) {
            resumeAutoScroll();
        } else if (numValueOf != null && numValueOf.intValue() == 0) {
            pauseAutoScroll();
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(e);
    }

    private final void pauseAutoScroll() {
        getScrollHandler().removeMessages(1);
    }

    public final void resumeAutoScroll() {
        getScrollHandler().removeMessages(1);
        getScrollHandler().sendEmptyMessageDelayed(1, this.delayMillis);
    }

    public final void scrollNext() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int iFindLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition() + 1;
        if (iFindLastVisibleItemPosition >= this.itemsCount) {
            iFindLastVisibleItemPosition = 0;
        }
        linearLayoutManager.startSmoothScroll(createScroller(iFindLastVisibleItemPosition));
        getScrollHandler().sendEmptyMessageDelayed(1, this.delayMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView$ScrollHandler.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AutoScrollableRecyclerView.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView$ScrollHandler;", "Landroid/os/Handler;", "autoScrollableRecyclerView", "Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView;", "(Lorg/catrobat/catroid/ui/recyclerview/AutoScrollableRecyclerView;)V", "autoScrollViewPager", "Ljava/lang/ref/WeakReference;", "handleMessage", "", NotificationCompat.CATEGORY_MESSAGE, "Landroid/os/Message;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ScrollHandler extends Handler {
        private final WeakReference<AutoScrollableRecyclerView> autoScrollViewPager;

        public ScrollHandler(AutoScrollableRecyclerView autoScrollableRecyclerView) {
            Intrinsics.checkNotNullParameter(autoScrollableRecyclerView, "autoScrollableRecyclerView");
            this.autoScrollViewPager = new WeakReference<>(autoScrollableRecyclerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            super.handleMessage(msg);
            AutoScrollableRecyclerView autoScrollableRecyclerView = this.autoScrollViewPager.get();
            if (autoScrollableRecyclerView != null) {
                autoScrollableRecyclerView.scrollNext();
            }
        }
    }
}
