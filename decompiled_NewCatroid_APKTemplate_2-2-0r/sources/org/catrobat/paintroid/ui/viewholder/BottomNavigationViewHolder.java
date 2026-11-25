package org.catrobat.paintroid.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.ui.BottomNavigationLandscape;
import org.catrobat.paintroid.ui.BottomNavigationPortrait;

/* JADX INFO: compiled from: BottomNavigationViewHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0002J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0005H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0012\u0010\u001c\u001a\u00020\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/paintroid/ui/viewholder/BottomNavigationViewHolder;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationViewHolder;", "layout", "Landroid/view/View;", ResConfigBase.NAME_orientation, "", "context", "Landroid/content/Context;", "(Landroid/view/View;ILandroid/content/Context;)V", "bottomNavigation", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationAppearance;", "bottomNavigationView", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "getBottomNavigationView", "()Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "colorButton", "Landroid/widget/ImageView;", "colorItemView", "Lcom/google/android/material/bottomnavigation/BottomNavigationItemView;", "enableColorItemView", "", "show", "", "hide", "initColorButton", "setAppearance", "setColorButtonColor", TypedValues.Custom.S_COLOR, "showCurrentTool", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BottomNavigationViewHolder implements MainActivityContracts.BottomNavigationViewHolder {
    private final MainActivityContracts.BottomNavigationAppearance bottomNavigation;
    private final BottomNavigationView bottomNavigationView;
    private final ImageView colorButton;
    private final BottomNavigationItemView colorItemView;
    private final View layout;
    private final int orientation;

    public BottomNavigationViewHolder(View layout, int i, Context context) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(context, "context");
        this.layout = layout;
        this.orientation = i;
        View viewFindViewById = layout.findViewById(R.id.pocketpaint_bottom_navigation);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "layout.findViewById(R.id…tpaint_bottom_navigation)");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) viewFindViewById;
        this.bottomNavigationView = bottomNavigationView;
        this.bottomNavigation = setAppearance(context);
        View childAt = bottomNavigationView.getChildAt(0);
        if (childAt == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomnavigation.BottomNavigationMenuView");
        }
        View childAt2 = ((BottomNavigationMenuView) childAt).getChildAt(2);
        if (childAt2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomnavigation.BottomNavigationItemView");
        }
        BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) childAt2;
        this.colorItemView = bottomNavigationItemView;
        View viewFindViewById2 = bottomNavigationItemView.findViewById(R.id.icon);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "colorItemView.findViewById(R.id.icon)");
        this.colorButton = (ImageView) viewFindViewById2;
        initColorButton();
    }

    public final BottomNavigationView getBottomNavigationView() {
        return this.bottomNavigationView;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationViewHolder
    public void show() {
        this.layout.setVisibility(0);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationViewHolder
    public void hide() {
        this.layout.setVisibility(8);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationViewHolder
    public void showCurrentTool(ToolType toolType) {
        if (toolType == null) {
            return;
        }
        this.bottomNavigation.showCurrentTool(toolType);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationViewHolder
    public void enableColorItemView(boolean show) {
        this.colorItemView.setClickable(show);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationViewHolder
    public void setColorButtonColor(int color) {
        this.colorButton.setColorFilter(color);
    }

    private final void initColorButton() {
        ImageView imageView = this.colorButton;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundColor(-1);
        imageView.setPadding(2, 2, 2, 2);
    }

    private final MainActivityContracts.BottomNavigationAppearance setAppearance(Context context) {
        if (this.orientation == 1) {
            return new BottomNavigationPortrait(this.bottomNavigationView);
        }
        return new BottomNavigationLandscape(context, this.bottomNavigationView);
    }
}
