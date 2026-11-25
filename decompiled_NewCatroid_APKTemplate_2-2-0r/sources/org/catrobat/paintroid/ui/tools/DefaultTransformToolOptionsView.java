package org.catrobat.paintroid.ui.tools;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSeekBar;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.catrobat.paintroid.tools.options.TransformToolOptionsView;

/* JADX INFO: compiled from: DefaultTransformToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0016H\u0016J\u0010\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultTransformToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "callback", "Lorg/catrobat/paintroid/tools/options/TransformToolOptionsView$Callback;", "heightEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "heightTextWatcher", "Lorg/catrobat/paintroid/ui/tools/DefaultTransformToolOptionsView$TransformToolSizeTextWatcher;", "percentageText", "resizeSeekBar", "Landroidx/appcompat/widget/AppCompatSeekBar;", "root", "widthEditText", "widthTextWatcher", "hideKeyboard", "", "setCallback", "setHeight", "height", "", "setHeightFilter", "numberRangeFilter", "Lorg/catrobat/paintroid/ui/tools/NumberRangeFilter;", "setWidth", "width", "setWidthFilter", "Companion", "TransformToolSizeTextWatcher", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultTransformToolOptionsView implements TransformToolOptionsView {
    private static final String TAG = "DefaultTransformToolOptionsView";
    private TransformToolOptionsView.Callback callback;
    private final AppCompatEditText heightEditText;
    private final TransformToolSizeTextWatcher heightTextWatcher;
    private final AppCompatEditText percentageText;
    private final AppCompatSeekBar resizeSeekBar;
    private final ViewGroup root;
    private final AppCompatEditText widthEditText;
    private final TransformToolSizeTextWatcher widthTextWatcher;

    public DefaultTransformToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        this.root = rootView;
        View viewInflate = LayoutInflater.from(rootView.getContext()).inflate(R.layout.dialog_pocketpaint_transform_tool, rootView);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_transform_width_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "optionsView.findViewById…nt_transform_width_value)");
        AppCompatEditText appCompatEditText = (AppCompatEditText) viewFindViewById;
        this.widthEditText = appCompatEditText;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_transform_height_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "optionsView.findViewById…t_transform_height_value)");
        AppCompatEditText appCompatEditText2 = (AppCompatEditText) viewFindViewById2;
        this.heightEditText = appCompatEditText2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.pocketpaint_transform_resize_seekbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "optionsView.findViewById…transform_resize_seekbar)");
        AppCompatSeekBar appCompatSeekBar = (AppCompatSeekBar) viewFindViewById3;
        this.resizeSeekBar = appCompatSeekBar;
        View viewFindViewById4 = viewInflate.findViewById(R.id.pocketpaint_transform_resize_percentage_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "optionsView.findViewById…m_resize_percentage_text)");
        AppCompatEditText appCompatEditText3 = (AppCompatEditText) viewFindViewById4;
        this.percentageText = appCompatEditText3;
        TransformToolSizeTextWatcher transformToolSizeTextWatcher = new TransformToolSizeTextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView.1
            @Override // org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView.TransformToolSizeTextWatcher
            protected void setValue(float value) {
                TransformToolOptionsView.Callback callback = DefaultTransformToolOptionsView.this.callback;
                if (callback == null) {
                    return;
                }
                callback.setBoxWidth(value);
            }
        };
        this.widthTextWatcher = transformToolSizeTextWatcher;
        TransformToolSizeTextWatcher transformToolSizeTextWatcher2 = new TransformToolSizeTextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView.2
            @Override // org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView.TransformToolSizeTextWatcher
            protected void setValue(float value) {
                TransformToolOptionsView.Callback callback = DefaultTransformToolOptionsView.this.callback;
                if (callback == null) {
                    return;
                }
                callback.setBoxHeight(value);
            }
        };
        this.heightTextWatcher = transformToolSizeTextWatcher2;
        appCompatEditText.addTextChangedListener(transformToolSizeTextWatcher);
        appCompatEditText2.addTextChangedListener(transformToolSizeTextWatcher2);
        appCompatEditText3.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(appCompatSeekBar.getProgress())}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        appCompatEditText3.setText(str);
        appCompatEditText3.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int i;
                Intrinsics.checkNotNullParameter(editable, "editable");
                try {
                    i = Integer.parseInt(String.valueOf(DefaultTransformToolOptionsView.this.percentageText.getText()));
                } catch (NumberFormatException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage != null) {
                        Log.d(DefaultTransformToolOptionsView.TAG, localizedMessage);
                    }
                    i = 1;
                }
                DefaultTransformToolOptionsView.this.resizeSeekBar.setProgress(i);
                DefaultTransformToolOptionsView.this.percentageText.setSelection(DefaultTransformToolOptionsView.this.percentageText.length());
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_auto_crop_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6011_init_$lambda0(this.f$0, view);
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_set_center_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6012_init_$lambda1(this.f$0, view);
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_rotate_left_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6013_init_$lambda2(this.f$0, view);
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_rotate_right_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6014_init_$lambda3(this.f$0, view);
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_flip_horizontal_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6015_init_$lambda4(this.f$0, view);
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_flip_vertical_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6016_init_$lambda5(this.f$0, view);
            }
        });
        viewInflate.findViewById(R.id.pocketpaint_transform_apply_resize_btn).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultTransformToolOptionsView.m6017_init_$lambda6(this.f$0, view);
            }
        });
        appCompatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultTransformToolOptionsView.11
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                if (progress != 0) {
                    AppCompatEditText appCompatEditText4 = DefaultTransformToolOptionsView.this.percentageText;
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    String str2 = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(progress)}, 1));
                    Intrinsics.checkNotNullExpressionValue(str2, "java.lang.String.format(locale, format, *args)");
                    appCompatEditText4.setText(str2);
                    return;
                }
                seekBar.setProgress(1);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                AppCompatEditText appCompatEditText4 = DefaultTransformToolOptionsView.this.percentageText;
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                String str2 = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(seekBar.getProgress())}, 1));
                Intrinsics.checkNotNullExpressionValue(str2, "java.lang.String.format(locale, format, *args)");
                appCompatEditText4.setText(str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
    public static final void m6011_init_$lambda0(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.autoCropClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-1, reason: not valid java name */
    public static final void m6012_init_$lambda1(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.setCenterClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-2, reason: not valid java name */
    public static final void m6013_init_$lambda2(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.rotateCounterClockwiseClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-3, reason: not valid java name */
    public static final void m6014_init_$lambda3(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.rotateClockwiseClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-4, reason: not valid java name */
    public static final void m6015_init_$lambda4(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.flipHorizontalClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-5, reason: not valid java name */
    public static final void m6016_init_$lambda5(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.flipVerticalClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-6, reason: not valid java name */
    public static final void m6017_init_$lambda6(DefaultTransformToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideKeyboard();
        TransformToolOptionsView.Callback callback = this$0.callback;
        if (callback != null) {
            callback.applyResizeClicked(this$0.resizeSeekBar.getProgress());
        }
        TransformToolOptionsView.Callback callback2 = this$0.callback;
        if (callback2 != null) {
            callback2.hideToolOptions();
        }
        this$0.resizeSeekBar.setProgress(100);
    }

    @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView
    public void setWidthFilter(NumberRangeFilter numberRangeFilter) {
        Intrinsics.checkNotNullParameter(numberRangeFilter, "numberRangeFilter");
        this.widthEditText.setFilters(new InputFilter[]{numberRangeFilter});
    }

    @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView
    public void setHeightFilter(NumberRangeFilter numberRangeFilter) {
        Intrinsics.checkNotNullParameter(numberRangeFilter, "numberRangeFilter");
        this.heightEditText.setFilters(new InputFilter[]{numberRangeFilter});
    }

    @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView
    public void setCallback(TransformToolOptionsView.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }

    @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView
    public void setWidth(int width) {
        AppCompatEditText appCompatEditText = this.widthEditText;
        appCompatEditText.removeTextChangedListener(this.widthTextWatcher);
        appCompatEditText.setText(String.valueOf(width));
        appCompatEditText.addTextChangedListener(this.widthTextWatcher);
    }

    @Override // org.catrobat.paintroid.tools.options.TransformToolOptionsView
    public void setHeight(int height) {
        AppCompatEditText appCompatEditText = this.heightEditText;
        appCompatEditText.removeTextChangedListener(this.heightTextWatcher);
        appCompatEditText.setText(String.valueOf(height));
        appCompatEditText.addTextChangedListener(this.heightTextWatcher);
    }

    /* JADX INFO: compiled from: DefaultTransformToolOptionsView.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016J(\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H$¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultTransformToolOptionsView$TransformToolSizeTextWatcher;", "Landroid/text/TextWatcher;", "()V", "afterTextChanged", "", "editable", "Landroid/text/Editable;", "beforeTextChanged", "s", "", "start", "", "count", "after", "onTextChanged", "before", "setValue", "value", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static abstract class TransformToolSizeTextWatcher implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Intrinsics.checkNotNullParameter(s, "s");
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Intrinsics.checkNotNullParameter(s, "s");
        }

        protected abstract void setValue(float value);

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            Intrinsics.checkNotNullParameter(editable, "editable");
            String string = editable.toString();
            if (string.length() == 0) {
                string = SchemaSymbols.ATTVAL_TRUE_1;
            }
            try {
                Number number = NumberFormat.getIntegerInstance().parse(string);
                Float fValueOf = number == null ? null : Float.valueOf(number.floatValue());
                if (fValueOf == null) {
                    return;
                }
                setValue(fValueOf.floatValue());
            } catch (ParseException e) {
                String message = e.getMessage();
                if (message == null) {
                    return;
                }
                Log.e(DefaultTransformToolOptionsView.TAG, message);
            }
        }
    }

    private final void hideKeyboard() {
        Object systemService = this.root.getContext().getSystemService("input_method");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).hideSoftInputFromWindow(this.root.getWindowToken(), 2);
    }
}
