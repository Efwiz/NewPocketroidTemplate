package org.catrobat.paintroid.model;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: Layer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u000eH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001a\u0010\b\u001a\u00020\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/model/Layer;", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "bitmap", "Landroid/graphics/Bitmap;", "(Landroid/graphics/Bitmap;)V", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "isVisible", "", "()Z", "setVisible", "(Z)V", "opacityPercentage", "", "getOpacityPercentage", "()I", "setOpacityPercentage", "(I)V", "getValueForOpacityPercentage", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class Layer implements LayerContracts.Layer {
    private Bitmap bitmap;
    private boolean isVisible;
    private int opacityPercentage;

    public Layer(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.bitmap = bitmap;
        this.isVisible = true;
        this.opacityPercentage = 100;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    public void setBitmap(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "<set-?>");
        this.bitmap = bitmap;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    /* JADX INFO: renamed from: isVisible, reason: from getter */
    public boolean getIsVisible() {
        return this.isVisible;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    public void setVisible(boolean z) {
        this.isVisible = z;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    public int getOpacityPercentage() {
        return this.opacityPercentage;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    public void setOpacityPercentage(int i) {
        this.opacityPercentage = i;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Layer
    public int getValueForOpacityPercentage() {
        return (int) ((getOpacityPercentage() / 100) * 255);
    }
}
