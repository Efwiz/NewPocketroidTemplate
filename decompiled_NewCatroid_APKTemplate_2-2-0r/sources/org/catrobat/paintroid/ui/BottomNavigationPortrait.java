package org.catrobat.paintroid.ui;

import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.tools.ToolType;

/* JADX INFO: compiled from: BottomNavigationPortrait.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/ui/BottomNavigationPortrait;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationAppearance;", "bottomNavigationView", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "(Lcom/google/android/material/bottomnavigation/BottomNavigationView;)V", "showCurrentTool", "", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BottomNavigationPortrait implements MainActivityContracts.BottomNavigationAppearance {
    private final BottomNavigationView bottomNavigationView;

    public BottomNavigationPortrait(BottomNavigationView bottomNavigationView) {
        Intrinsics.checkNotNullParameter(bottomNavigationView, "bottomNavigationView");
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomNavigationAppearance
    public void showCurrentTool(ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolType, "toolType");
        MenuItem menuItemFindItem = this.bottomNavigationView.getMenu().findItem(R.id.action_current_tool);
        if (StringsKt.equals(menuItemFindItem.toString(), toolType.name(), true)) {
            return;
        }
        menuItemFindItem.setIcon(toolType.getDrawableResource());
        menuItemFindItem.setTitle(toolType.getNameResource());
    }
}
