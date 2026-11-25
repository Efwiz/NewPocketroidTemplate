package com.flask.colorpicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/* JADX INFO: loaded from: classes.dex */
public class ColorPickerPreference extends Preference {
    protected boolean alphaSlider;
    protected ImageView colorIndicator;
    protected int density;
    protected boolean lightSlider;
    private String pickerButtonCancel;
    private String pickerButtonOk;
    private String pickerTitle;
    protected int selectedColor;
    protected ColorPickerView.WHEEL_TYPE wheelType;

    public ColorPickerPreference(Context context) {
        super(context);
        this.selectedColor = 0;
    }

    public ColorPickerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.selectedColor = 0;
        initWith(context, attributeSet);
    }

    public ColorPickerPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.selectedColor = 0;
        initWith(context, attributeSet);
    }

    private void initWith(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ColorPickerPreference);
        try {
            this.alphaSlider = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ColorPickerPreference_alphaSlider, false);
            this.lightSlider = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ColorPickerPreference_lightnessSlider, false);
            this.density = typedArrayObtainStyledAttributes.getInt(R.styleable.ColorPickerPreference_density, 10);
            this.wheelType = ColorPickerView.WHEEL_TYPE.indexOf(typedArrayObtainStyledAttributes.getInt(R.styleable.ColorPickerPreference_wheelType, 0));
            this.selectedColor = typedArrayObtainStyledAttributes.getInt(R.styleable.ColorPickerPreference_initialColor, -1);
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.ColorPickerPreference_pickerTitle);
            this.pickerTitle = string;
            if (string == null) {
                this.pickerTitle = "Choose color";
            }
            String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.ColorPickerPreference_pickerButtonCancel);
            this.pickerButtonCancel = string2;
            if (string2 == null) {
                this.pickerButtonCancel = "cancel";
            }
            String string3 = typedArrayObtainStyledAttributes.getString(R.styleable.ColorPickerPreference_pickerButtonOk);
            this.pickerButtonOk = string3;
            if (string3 == null) {
                this.pickerButtonOk = "ok";
            }
            typedArrayObtainStyledAttributes.recycle();
            setWidgetLayoutResource(R.layout.color_widget);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        Resources resources = view.getContext().getResources();
        ImageView imageView = (ImageView) view.findViewById(R.id.color_indicator);
        this.colorIndicator = imageView;
        Drawable drawable = imageView.getDrawable();
        GradientDrawable gradientDrawable = (drawable == null || !(drawable instanceof GradientDrawable)) ? null : (GradientDrawable) drawable;
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(1);
        }
        int iDarken = isEnabled() ? this.selectedColor : darken(this.selectedColor, 0.5f);
        gradientDrawable.setColor(iDarken);
        gradientDrawable.setStroke((int) TypedValue.applyDimension(1, 1.0f, resources.getDisplayMetrics()), darken(iDarken, 0.8f));
        this.colorIndicator.setImageDrawable(gradientDrawable);
    }

    public void setValue(int i) {
        if (callChangeListener(Integer.valueOf(i))) {
            this.selectedColor = i;
            persistInt(i);
            notifyChanged();
        }
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        setValue(z ? getPersistedInt(0) : ((Integer) obj).intValue());
    }

    @Override // android.preference.Preference
    protected void onClick() {
        ColorPickerDialogBuilder negativeButton = ColorPickerDialogBuilder.with(getContext()).setTitle(this.pickerTitle).initialColor(this.selectedColor).wheelType(this.wheelType).density(this.density).setPositiveButton(this.pickerButtonOk, new ColorPickerClickListener() { // from class: com.flask.colorpicker.ColorPickerPreference.1
            @Override // com.flask.colorpicker.builder.ColorPickerClickListener
            public void onClick(DialogInterface dialogInterface, int i, Integer[] numArr) {
                ColorPickerPreference.this.setValue(i);
            }
        }).setNegativeButton(this.pickerButtonCancel, (DialogInterface.OnClickListener) null);
        boolean z = this.alphaSlider;
        if (!z && !this.lightSlider) {
            negativeButton.noSliders();
        } else if (!z) {
            negativeButton.lightnessSliderOnly();
        } else if (!this.lightSlider) {
            negativeButton.alphaSliderOnly();
        }
        negativeButton.build().show();
    }

    public static int darken(int i, float f) {
        return Color.argb(Color.alpha(i), Math.max((int) (Color.red(i) * f), 0), Math.max((int) (Color.green(i) * f), 0), Math.max((int) (Color.blue(i) * f), 0));
    }
}
