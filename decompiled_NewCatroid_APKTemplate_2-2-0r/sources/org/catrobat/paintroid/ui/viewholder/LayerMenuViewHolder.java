package org.catrobat.paintroid.ui.viewholder;

import android.view.View;
import com.google.android.material.navigation.NavigationView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: LayerMenuViewHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\b¨\u0006\u0018"}, d2 = {"Lorg/catrobat/paintroid/ui/viewholder/LayerMenuViewHolder;", "Lorg/catrobat/paintroid/contract/LayerContracts$LayerMenuViewHolder;", "layerLayout", "Lcom/google/android/material/navigation/NavigationView;", "(Lcom/google/android/material/navigation/NavigationView;)V", "layerAddButton", "Landroid/view/View;", "getLayerAddButton", "()Landroid/view/View;", "layerDeleteButton", "getLayerDeleteButton", "layerOpacityButton", "getLayerOpacityButton", "layerVisibilityButton", "getLayerVisibilityButton", "disableAddLayerButton", "", "disableLayerOpacityButton", "disableLayerVisibilityButton", "disableRemoveLayerButton", "enableAddLayerButton", "enableRemoveLayerButton", "isShown", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LayerMenuViewHolder implements LayerContracts.LayerMenuViewHolder {
    private final View layerAddButton;
    private final View layerDeleteButton;
    private final NavigationView layerLayout;
    private final View layerOpacityButton;
    private final View layerVisibilityButton;

    public LayerMenuViewHolder(NavigationView layerLayout) {
        Intrinsics.checkNotNullParameter(layerLayout, "layerLayout");
        this.layerLayout = layerLayout;
        View viewFindViewById = layerLayout.findViewById(R.id.pocketpaint_layer_side_nav_button_add);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "layerLayout.findViewById…ayer_side_nav_button_add)");
        this.layerAddButton = viewFindViewById;
        View viewFindViewById2 = layerLayout.findViewById(R.id.pocketpaint_layer_side_nav_button_delete);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "layerLayout.findViewById…r_side_nav_button_delete)");
        this.layerDeleteButton = viewFindViewById2;
        View viewFindViewById3 = layerLayout.findViewById(R.id.pocketpaint_layer_side_nav_button_visibility);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "layerLayout.findViewById…de_nav_button_visibility)");
        this.layerVisibilityButton = viewFindViewById3;
        View viewFindViewById4 = layerLayout.findViewById(R.id.pocketpaint_layer_side_nav_button_opacity);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "layerLayout.findViewById…_side_nav_button_opacity)");
        this.layerOpacityButton = viewFindViewById4;
    }

    public final View getLayerAddButton() {
        return this.layerAddButton;
    }

    public final View getLayerDeleteButton() {
        return this.layerDeleteButton;
    }

    public final View getLayerVisibilityButton() {
        return this.layerVisibilityButton;
    }

    public final View getLayerOpacityButton() {
        return this.layerOpacityButton;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public boolean isShown() {
        return this.layerLayout.isShown();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public void disableAddLayerButton() {
        this.layerAddButton.setEnabled(false);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public void enableAddLayerButton() {
        this.layerAddButton.setEnabled(true);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public void disableRemoveLayerButton() {
        this.layerDeleteButton.setEnabled(false);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public void enableRemoveLayerButton() {
        this.layerDeleteButton.setEnabled(true);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public void disableLayerVisibilityButton() {
        this.layerVisibilityButton.setEnabled(false);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.LayerMenuViewHolder
    public void disableLayerOpacityButton() {
        this.layerOpacityButton.setEnabled(false);
    }
}
