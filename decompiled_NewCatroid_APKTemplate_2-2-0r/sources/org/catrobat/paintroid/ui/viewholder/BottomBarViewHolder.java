package org.catrobat.paintroid.ui.viewholder;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.MainActivityContracts;

/* JADX INFO: compiled from: BottomBarViewHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/ui/viewholder/BottomBarViewHolder;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomBarViewHolder;", "layout", "Landroid/view/View;", "(Landroid/view/View;)V", "isVisible", "", "()Z", "getLayout", "()Landroid/view/View;", "hide", "", "show", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BottomBarViewHolder implements MainActivityContracts.BottomBarViewHolder {
    private final View layout;

    public BottomBarViewHolder(View layout) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        this.layout = layout;
    }

    public final View getLayout() {
        return this.layout;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomBarViewHolder
    public boolean isVisible() {
        return this.layout.getVisibility() == 0;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomBarViewHolder
    public void show() {
        this.layout.setVisibility(0);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.BottomBarViewHolder
    public void hide() {
        this.layout.setVisibility(8);
    }
}
