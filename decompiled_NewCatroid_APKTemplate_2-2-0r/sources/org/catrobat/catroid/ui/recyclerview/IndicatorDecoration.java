package org.catrobat.catroid.ui.recyclerview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.adapter.FeaturedProjectsAdapter;
import org.catrobat.catroid.utils.ExtensionsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/IndicatorDecoration.class
  classes14.dex
 */
/* JADX INFO: compiled from: IndicatorDecoration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 $2\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\f\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/IndicatorDecoration;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "diffRadius", "", "evaluator", "Landroid/animation/ArgbEvaluator;", "getEvaluator", "()Landroid/animation/ArgbEvaluator;", "evaluator$delegate", "Lkotlin/Lazy;", "interpolator", "Landroid/view/animation/AccelerateDecelerateInterpolator;", "getInterpolator", "()Landroid/view/animation/AccelerateDecelerateInterpolator;", "interpolator$delegate", "margin", "paint", "Landroid/graphics/Paint;", "getPaint", "()Landroid/graphics/Paint;", "paint$delegate", "selectedColor", "", "unselectedColor", "unselectedRadius", "onDrawOver", "", "canvas", "Landroid/graphics/Canvas;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IndicatorDecoration extends RecyclerView.ItemDecoration {
    private static final float DIFF_RADIUS = 1.5f;
    private static final float MARGIN = 8.0f;
    private static final float UNSELECTED_RADIUS = 4.0f;
    private final float diffRadius;

    /* JADX INFO: renamed from: evaluator$delegate, reason: from kotlin metadata */
    private final Lazy evaluator;

    /* JADX INFO: renamed from: interpolator$delegate, reason: from kotlin metadata */
    private final Lazy interpolator;
    private final float margin;

    /* JADX INFO: renamed from: paint$delegate, reason: from kotlin metadata */
    private final Lazy paint;
    private final int selectedColor;
    private final int unselectedColor;
    private final float unselectedRadius;

    public IndicatorDecoration(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.selectedColor = ContextCompat.getColor(context, R.color.indicator_selected_color);
        this.unselectedColor = ContextCompat.getColor(context, R.color.indicator_unselected_color);
        this.diffRadius = ExtensionsKt.dpToPx(context, DIFF_RADIUS);
        float fDpToPx = ExtensionsKt.dpToPx(context, 4.0f);
        this.unselectedRadius = fDpToPx;
        this.margin = fDpToPx + ExtensionsKt.dpToPx(context, 8.0f);
        this.paint = LazyKt.lazy(new Function0<Paint>() { // from class: org.catrobat.catroid.ui.recyclerview.IndicatorDecoration$paint$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Paint invoke() {
                Paint paint = new Paint();
                paint.setColor(this.this$0.unselectedColor);
                paint.setStyle(Paint.Style.FILL);
                return paint;
            }
        });
        this.evaluator = LazyKt.lazy(new Function0<ArgbEvaluator>() { // from class: org.catrobat.catroid.ui.recyclerview.IndicatorDecoration$evaluator$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ArgbEvaluator invoke() {
                return new ArgbEvaluator();
            }
        });
        this.interpolator = LazyKt.lazy(new Function0<AccelerateDecelerateInterpolator>() { // from class: org.catrobat.catroid.ui.recyclerview.IndicatorDecoration$interpolator$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final AccelerateDecelerateInterpolator invoke() {
                return new AccelerateDecelerateInterpolator();
            }
        });
    }

    private final Paint getPaint() {
        return (Paint) this.paint.getValue();
    }

    private final ArgbEvaluator getEvaluator() {
        return (ArgbEvaluator) this.evaluator.getValue();
    }

    private final AccelerateDecelerateInterpolator getInterpolator() {
        return (AccelerateDecelerateInterpolator) this.interpolator.getValue();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        super.onDrawOver(canvas, parent, state);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null) {
            if (adapter.getItemCount() <= 0) {
                adapter = null;
            }
            if (adapter != null) {
                int itemCount = ((FeaturedProjectsAdapter) adapter).getItemCount();
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                if (linearLayoutManager != null) {
                    int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int i = iFindFirstVisibleItemPosition % itemCount;
                    View viewFindViewByPosition = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
                    if (viewFindViewByPosition != null) {
                        float width = (parent.getWidth() - (this.margin * (itemCount - 1))) / 2;
                        float height = parent.getHeight() - this.margin;
                        float interpolation = getInterpolator().getInterpolation((viewFindViewByPosition.getLeft() * (-1)) / viewFindViewByPosition.getWidth());
                        for (int i2 = 0; i2 < itemCount; i2++) {
                            float f = this.unselectedRadius;
                            if (i2 == i) {
                                float f2 = 1 - interpolation;
                                f += this.diffRadius * f2;
                                Paint paint = getPaint();
                                Object objEvaluate = getEvaluator().evaluate(f2, Integer.valueOf(this.unselectedColor), Integer.valueOf(this.selectedColor));
                                Intrinsics.checkNotNull(objEvaluate, "null cannot be cast to non-null type kotlin.Int");
                                paint.setColor(((Integer) objEvaluate).intValue());
                            } else if (i2 == i + 1) {
                                f += this.diffRadius * interpolation;
                                Paint paint2 = getPaint();
                                Object objEvaluate2 = getEvaluator().evaluate(interpolation, Integer.valueOf(this.unselectedColor), Integer.valueOf(this.selectedColor));
                                Intrinsics.checkNotNull(objEvaluate2, "null cannot be cast to non-null type kotlin.Int");
                                paint2.setColor(((Integer) objEvaluate2).intValue());
                            } else {
                                getPaint().setColor(this.unselectedColor);
                            }
                            canvas.drawCircle((i2 * this.margin) + width, height, f, getPaint());
                        }
                    }
                }
            }
        }
    }
}
