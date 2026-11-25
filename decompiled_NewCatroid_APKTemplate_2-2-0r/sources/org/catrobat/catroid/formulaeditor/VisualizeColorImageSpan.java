package org.catrobat.catroid.formulaeditor;

import android.text.style.ImageSpan;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/VisualizeColorImageSpan.class
  classes14.dex
 */
/* JADX INFO: compiled from: VisualizeColorString.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/VisualizeColorImageSpan;", "Landroid/text/style/ImageSpan;", "drawable", "Landroidx/core/graphics/drawable/RoundedBitmapDrawable;", "colorValue", "", "(Landroidx/core/graphics/drawable/RoundedBitmapDrawable;I)V", "getColorValue", "()I", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VisualizeColorImageSpan extends ImageSpan {
    private final int colorValue;

    public final int getColorValue() {
        return this.colorValue;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VisualizeColorImageSpan(RoundedBitmapDrawable drawable, int i) {
        super(drawable, 0);
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        this.colorValue = i;
    }
}
