package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SubCategoryHeaderBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: SubCategoryHeaderBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0017R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/bricks/SubCategoryHeaderBrick;", "Lorg/catrobat/catroid/content/bricks/NoteBrick;", "title", "", "templateBrick", "Lorg/catrobat/catroid/content/bricks/Brick;", "(Ljava/lang/String;Lorg/catrobat/catroid/content/bricks/Brick;)V", "isExpanded", "", "()Z", "setExpanded", "(Z)V", "isStacked", "setStacked", "getTemplateBrick", "()Lorg/catrobat/catroid/content/bricks/Brick;", "getTitle", "()Ljava/lang/String;", "findBrickLayout", "Lorg/catrobat/catroid/ui/BrickLayout;", Promotion.ACTION_VIEW, "Landroid/view/View;", "getPrototypeView", "context", "Landroid/content/Context;", "getView", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubCategoryHeaderBrick extends NoteBrick {
    private boolean isExpanded;
    private boolean isStacked;
    private final Brick templateBrick;
    private final String title;

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    public SubCategoryHeaderBrick(String title, Brick templateBrick) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(templateBrick, "templateBrick");
        this.title = title;
        this.templateBrick = templateBrick;
        this.isExpanded = true;
    }

    public final Brick getTemplateBrick() {
        return this.templateBrick;
    }

    public final String getTitle() {
        return this.title;
    }

    /* JADX INFO: renamed from: isExpanded, reason: from getter */
    public final boolean getIsExpanded() {
        return this.isExpanded;
    }

    public final void setExpanded(boolean z) {
        this.isExpanded = z;
    }

    /* JADX INFO: renamed from: isStacked, reason: from getter */
    public final boolean getIsStacked() {
        return this.isStacked;
    }

    public final void setStacked(boolean z) {
        this.isStacked = z;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getPrototypeView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        float f = context.getResources().getDisplayMetrics().density;
        SubCategoryHeaderView subCategoryHeaderView = new SubCategoryHeaderView(context);
        subCategoryHeaderView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        subCategoryHeaderView.setPadding(0, 0, 0, 0);
        View prototypeView = this.templateBrick.getPrototypeView(context);
        if (prototypeView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) prototypeView;
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
        Intrinsics.checkNotNull(prototypeView);
        BrickLayout brickLayoutFindBrickLayout = findBrickLayout(prototypeView);
        int i = R.color.solid_white;
        if (brickLayoutFindBrickLayout != null) {
            if (brickLayoutFindBrickLayout instanceof ViewGroup) {
                brickLayoutFindBrickLayout.setClipChildren(false);
                brickLayoutFindBrickLayout.setClipToPadding(false);
            }
            int iCoerceAtLeast = RangesKt.coerceAtLeast((int) (2.0f * f), 2);
            int i2 = iCoerceAtLeast + ((int) (4 * f));
            int i3 = (int) (2 * f);
            int i4 = iCoerceAtLeast + i3;
            Drawable background = brickLayoutFindBrickLayout.getBackground();
            if (background != null) {
                brickLayoutFindBrickLayout.setBackground(new OutlinedNinePatchDrawable(background, -1, iCoerceAtLeast, i2, i4, f));
            }
            brickLayoutFindBrickLayout.setPadding(brickLayoutFindBrickLayout.getPaddingLeft() + i2, brickLayoutFindBrickLayout.getPaddingTop() + i4, brickLayoutFindBrickLayout.getPaddingRight() + i2, brickLayoutFindBrickLayout.getPaddingBottom() + i4);
            brickLayoutFindBrickLayout.removeAllViews();
            TextView textView = new TextView(context);
            textView.setId(R.id.brick_header_title);
            textView.setText(this.title);
            textView.setTextSize(17.0f);
            textView.setTextColor(ContextCompat.getColor(context, i));
            textView.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
            textView.setPadding(0, 0, (int) (32 * f), 0);
            brickLayoutFindBrickLayout.addView(textView);
            ViewGroup.LayoutParams layoutParams = brickLayoutFindBrickLayout.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
            if (marginLayoutParams == null) {
                marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -2);
            }
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, (int) (6 * f), marginLayoutParams.rightMargin, i3);
            brickLayoutFindBrickLayout.setLayoutParams(marginLayoutParams);
        } else {
            i = R.color.solid_white;
        }
        TextView textView2 = new TextView(context);
        textView2.setId(R.id.brick_header_arrow);
        textView2.setText(this.isExpanded ? "▼" : "▶");
        textView2.setTextSize(14.0f);
        textView2.setTextColor(ContextCompat.getColor(context, i));
        textView2.setGravity(17);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 8388629;
        layoutParams2.rightMargin = (int) (24 * f);
        textView2.setLayoutParams(layoutParams2);
        prototypeView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        subCategoryHeaderView.addView(prototypeView);
        subCategoryHeaderView.addView(textView2);
        return subCategoryHeaderView;
    }

    private final BrickLayout findBrickLayout(View view) {
        if (view instanceof BrickLayout) {
            return (BrickLayout) view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            Intrinsics.checkNotNull(childAt);
            BrickLayout brickLayoutFindBrickLayout = findBrickLayout(childAt);
            if (brickLayoutFindBrickLayout != null) {
                return brickLayoutFindBrickLayout;
            }
        }
        return null;
    }
}
