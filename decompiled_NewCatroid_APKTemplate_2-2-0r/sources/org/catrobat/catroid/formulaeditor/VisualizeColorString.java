package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Regex;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/VisualizeColorString.class
  classes14.dex
 */
/* JADX INFO: compiled from: VisualizeColorString.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/VisualizeColorString;", "", "context", "Landroid/content/Context;", "colorString", "", "bitmapSize", "", "(Landroid/content/Context;Ljava/lang/String;F)V", "colorValue", "", "getColorValue", "()I", "setColorValue", "(I)V", "drawable", "Landroidx/core/graphics/drawable/RoundedBitmapDrawable;", "getDrawable", "()Landroidx/core/graphics/drawable/RoundedBitmapDrawable;", "setDrawable", "(Landroidx/core/graphics/drawable/RoundedBitmapDrawable;)V", "imageSpan", "Lorg/catrobat/catroid/formulaeditor/VisualizeColorImageSpan;", "getImageSpan", "()Lorg/catrobat/catroid/formulaeditor/VisualizeColorImageSpan;", "setImageSpan", "(Lorg/catrobat/catroid/formulaeditor/VisualizeColorImageSpan;)V", "getColorValueFromColorString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VisualizeColorString {
    private int colorValue;
    private RoundedBitmapDrawable drawable;
    private VisualizeColorImageSpan imageSpan;

    public VisualizeColorString(Context context, String colorString, float f) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(colorString, "colorString");
        this.colorValue = getColorValueFromColorString(colorString);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        bitmapCreateBitmap.setPixel(0, 0, this.colorValue);
        int i = (int) f;
        RoundedBitmapDrawable roundedBitmapDrawableCreate = RoundedBitmapDrawableFactory.create(context.getResources(), Bitmap.createScaledBitmap(bitmapCreateBitmap, i, i, false));
        Intrinsics.checkNotNullExpressionValue(roundedBitmapDrawableCreate, "create(...)");
        this.drawable = roundedBitmapDrawableCreate;
        roundedBitmapDrawableCreate.setCornerRadius(f / 4);
        RoundedBitmapDrawable roundedBitmapDrawable = this.drawable;
        roundedBitmapDrawable.setBounds(15, 0, roundedBitmapDrawable.getIntrinsicWidth() + 15, this.drawable.getIntrinsicHeight());
        this.imageSpan = new VisualizeColorImageSpan(this.drawable, this.colorValue);
    }

    public final RoundedBitmapDrawable getDrawable() {
        return this.drawable;
    }

    public final void setDrawable(RoundedBitmapDrawable roundedBitmapDrawable) {
        Intrinsics.checkNotNullParameter(roundedBitmapDrawable, "<set-?>");
        this.drawable = roundedBitmapDrawable;
    }

    public final VisualizeColorImageSpan getImageSpan() {
        return this.imageSpan;
    }

    public final void setImageSpan(VisualizeColorImageSpan visualizeColorImageSpan) {
        Intrinsics.checkNotNullParameter(visualizeColorImageSpan, "<set-?>");
        this.imageSpan = visualizeColorImageSpan;
    }

    public final int getColorValue() {
        return this.colorValue;
    }

    public final void setColorValue(int i) {
        this.colorValue = i;
    }

    private final int getColorValueFromColorString(String colorString) {
        try {
            return Integer.parseInt(new Regex("[^A-Za-z0-9]").replace(colorString, ""), CharsKt.checkRadix(16));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }
}
