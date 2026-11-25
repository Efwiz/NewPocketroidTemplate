package org.catrobat.paintroid.ui.viewholder;

import androidx.drawerlayout.widget.DrawerLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.MainActivityContracts;

/* JADX INFO: compiled from: DrawerLayoutViewHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/ui/viewholder/DrawerLayoutViewHolder;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$DrawerLayoutViewHolder;", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "(Landroidx/drawerlayout/widget/DrawerLayout;)V", "closeDrawer", "", "gravity", "", "animate", "", "isDrawerOpen", "isDrawerVisible", "openDrawer", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DrawerLayoutViewHolder implements MainActivityContracts.DrawerLayoutViewHolder {
    private final DrawerLayout drawerLayout;

    public DrawerLayoutViewHolder(DrawerLayout drawerLayout) {
        Intrinsics.checkNotNullParameter(drawerLayout, "drawerLayout");
        this.drawerLayout = drawerLayout;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.DrawerLayoutViewHolder
    public void closeDrawer(int gravity, boolean animate) {
        this.drawerLayout.closeDrawer(gravity, animate);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.DrawerLayoutViewHolder
    public boolean isDrawerOpen(int gravity) {
        return this.drawerLayout.isDrawerOpen(gravity);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.DrawerLayoutViewHolder
    public boolean isDrawerVisible(int gravity) {
        return this.drawerLayout.isDrawerVisible(gravity);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.DrawerLayoutViewHolder
    public void openDrawer(int gravity) {
        this.drawerLayout.openDrawer(gravity);
    }
}
