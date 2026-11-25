package org.catrobat.paintroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.tools.ToolType;

/* JADX INFO: compiled from: BottomNavigationLandscape.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/ui/BottomNavigationLandscape;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationAppearance;", "context", "Landroid/content/Context;", "bottomNavigationView", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "(Landroid/content/Context;Lcom/google/android/material/bottomnavigation/BottomNavigationView;)V", "bottomNavigationMenuView", "Lcom/google/android/material/bottomnavigation/BottomNavigationMenuView;", "setAppearance", "", "showCurrentTool", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BottomNavigationLandscape implements MainActivityContracts.BottomNavigationAppearance {
    private final BottomNavigationMenuView bottomNavigationMenuView;
    private final BottomNavigationView bottomNavigationView;

    public BottomNavigationLandscape(Context context, BottomNavigationView bottomNavigationView) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bottomNavigationView, "bottomNavigationView");
        this.bottomNavigationView = bottomNavigationView;
        View childAt = bottomNavigationView.getChildAt(0);
        if (childAt == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomnavigation.BottomNavigationMenuView");
        }
        this.bottomNavigationMenuView = (BottomNavigationMenuView) childAt;
        setAppearance(context);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationAppearance
    public void showCurrentTool(ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolType, "toolType");
        View childAt = this.bottomNavigationMenuView.getChildAt(1);
        ImageView imageView = (ImageView) childAt.findViewById(R.id.icon);
        TextView textView = (TextView) childAt.findViewById(R.id.title);
        imageView.setImageResource(toolType.getDrawableResource());
        textView.setText(toolType.getNameResource());
    }

    private final void setAppearance(Context context) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        Menu menu = this.bottomNavigationView.getMenu();
        Intrinsics.checkNotNullExpressionValue(menu, "bottomNavigationView.menu");
        int size = menu.size();
        if (size <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i + 1;
            View childAt = this.bottomNavigationMenuView.getChildAt(i);
            if (childAt == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomnavigation.BottomNavigationItemView");
            }
            BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) childAt;
            View viewInflate = layoutInflaterFrom.inflate(R.layout.pocketpaint_layout_bottom_navigation_item, (ViewGroup) this.bottomNavigationMenuView, false);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.icon);
            TextView textView = (TextView) viewInflate.findViewById(R.id.title);
            imageView.setImageDrawable(menu.getItem(i).getIcon());
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.pocketpaint_welcome_dot_active));
            textView.setText(menu.getItem(i).getTitle());
            bottomNavigationItemView.removeAllViews();
            bottomNavigationItemView.addView(viewInflate);
            if (i2 >= size) {
                return;
            } else {
                i = i2;
            }
        }
    }
}
