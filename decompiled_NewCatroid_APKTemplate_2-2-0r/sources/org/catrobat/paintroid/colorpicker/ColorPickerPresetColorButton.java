package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorPickerPresetColorButton.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001c\u0010\u0004\u001a\u00020\u0005@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerPresetColorButton;", "Landroidx/appcompat/widget/AppCompatImageButton;", "context", "Landroid/content/Context;", TypedValues.Custom.S_COLOR, "", "(Landroid/content/Context;I)V", "getColor", "()I", "setColor", "(I)V", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorPickerPresetColorButton extends AppCompatImageButton {
    private int color;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorPickerPresetColorButton(Context context) {
        this(context, 0, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ ColorPickerPresetColorButton(Context context, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? ViewCompat.MEASURED_STATE_MASK : i);
    }

    public final int getColor() {
        return this.color;
    }

    public final void setColor(int i) {
        this.color = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorPickerPresetColorButton(Context context, int i) {
        super(context, null, R.attr.borderlessButtonStyle);
        Intrinsics.checkNotNullParameter(context, "context");
        this.color = i;
        setBackground(ColorPickerDialog.CustomColorDrawable.INSTANCE.createDrawable(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT), this.color));
    }
}
