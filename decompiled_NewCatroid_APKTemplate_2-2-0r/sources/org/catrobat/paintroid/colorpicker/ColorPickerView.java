package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorPickerView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001:\u0002:;B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\"\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u000fH\u0002J\u0006\u0010%\u001a\u00020\u000fJ\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020'H\u0014J\b\u0010)\u001a\u00020'H\u0002J\b\u0010*\u001a\u00020'H\u0014J\u0018\u0010+\u001a\u00020'2\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000fH\u0014J\u0010\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u000200H\u0014J\b\u00101\u001a\u000200H\u0014J\u0016\u00102\u001a\u00020'2\u0006\u0010\b\u001a\u00020\t2\u0006\u00103\u001a\u00020\u0015J\u0010\u00104\u001a\u00020'2\b\u00105\u001a\u0004\u0018\u00010\u000bJ\u001a\u00106\u001a\u00020'2\u0006\u00107\u001a\u00020\u000f2\n\b\u0002\u00108\u001a\u0004\u0018\u00010#J\b\u00109\u001a\u00020'H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerView;", "Landroidx/appcompat/widget/LinearLayoutCompat;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "alphaSliderView", "Lorg/catrobat/paintroid/colorpicker/AlphaSliderView;", "colorChangedListener", "Lorg/catrobat/paintroid/colorpicker/OnColorChangedListener;", "hsvSelectorView", "Lorg/catrobat/paintroid/colorpicker/HSVSelectorView;", "initialColor", "", "getInitialColor", "()I", "setInitialColor", "(I)V", "isOpenedFromFormulaEditorInCatroid", "", "()Z", "setOpenedFromFormulaEditorInCatroid", "(Z)V", "preSelectorView", "Lorg/catrobat/paintroid/colorpicker/PresetSelectorView;", "rgbSelectorView", "Lorg/catrobat/paintroid/colorpicker/RgbSelectorView;", "getRgbSelectorView", "()Lorg/catrobat/paintroid/colorpicker/RgbSelectorView;", "selectedColor", "tabHost", "Landroid/widget/TabHost;", "createTabView", "Landroid/view/View;", "iconResourceId", "getSelectedColor", "hideKeyboard", "", "onAttachedToWindow", "onColorChanged", "onDetachedFromWindow", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onRestoreInstanceState", "state", "Landroid/os/Parcelable;", "onSaveInstanceState", "setAlphaSlider", "catroidFlag", "setOnColorChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setSelectedColor", TypedValues.Custom.S_COLOR, "sender", "showKeyboard", "ColorTabContentFactory", "SavedState", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorPickerView extends LinearLayoutCompat {
    private AlphaSliderView alphaSliderView;
    private OnColorChangedListener colorChangedListener;
    private final HSVSelectorView hsvSelectorView;
    private int initialColor;
    private boolean isOpenedFromFormulaEditorInCatroid;
    private final PresetSelectorView preSelectorView;
    private final RgbSelectorView rgbSelectorView;
    private int selectedColor;
    private final TabHost tabHost;

    public final RgbSelectorView getRgbSelectorView() {
        return this.rgbSelectorView;
    }

    public final int getInitialColor() {
        return this.initialColor;
    }

    public final void setInitialColor(int i) {
        this.initialColor = i;
    }

    /* JADX INFO: renamed from: isOpenedFromFormulaEditorInCatroid, reason: from getter */
    public final boolean getIsOpenedFromFormulaEditorInCatroid() {
        return this.isOpenedFromFormulaEditorInCatroid;
    }

    public final void setOpenedFromFormulaEditorInCatroid(boolean z) {
        this.isOpenedFromFormulaEditorInCatroid = z;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorPickerView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.selectedColor = ViewCompat.MEASURED_STATE_MASK;
        View viewInflate = LinearLayoutCompat.inflate(getContext(), R.layout.color_picker_colorselectview, null);
        addView(viewInflate);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        this.rgbSelectorView = new RgbSelectorView(context2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "context");
        this.preSelectorView = new PresetSelectorView(context3);
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        this.hsvSelectorView = new HSVSelectorView(context4);
        Context context5 = getContext();
        Intrinsics.checkNotNullExpressionValue(context5, "context");
        this.alphaSliderView = new AlphaSliderView(context5);
        View viewFindViewById = viewInflate.findViewById(R.id.color_picker_colorview_tabColors);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "tabView.findViewById(R.i…cker_colorview_tabColors)");
        TabHost tabHost = (TabHost) viewFindViewById;
        this.tabHost = tabHost;
        tabHost.setup();
        ColorTabContentFactory colorTabContentFactory = new ColorTabContentFactory(this);
        Context context6 = getContext();
        Intrinsics.checkNotNullExpressionValue(context6, "context");
        ColorTabContentFactory colorTabContentFactory2 = colorTabContentFactory;
        TabHost.TabSpec content = tabHost.newTabSpec("PRE").setIndicator(createTabView(context6, R.drawable.ic_color_picker_tab_preset)).setContent(colorTabContentFactory2);
        Context context7 = getContext();
        Intrinsics.checkNotNullExpressionValue(context7, "context");
        TabHost.TabSpec content2 = tabHost.newTabSpec("HSV").setIndicator(createTabView(context7, R.drawable.ic_color_picker_tab_hsv)).setContent(colorTabContentFactory2);
        Context context8 = getContext();
        Intrinsics.checkNotNullExpressionValue(context8, "context");
        final TabHost.TabSpec content3 = tabHost.newTabSpec("RGB").setIndicator(createTabView(context8, R.drawable.ic_color_picker_tab_rgba)).setContent(colorTabContentFactory2);
        tabHost.addTab(content);
        tabHost.addTab(content2);
        tabHost.addTab(content3);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerView$$ExternalSyntheticLambda0
            @Override // android.widget.TabHost.OnTabChangeListener
            public final void onTabChanged(String str) {
                ColorPickerView.m5904lambda2$lambda1(content3, this, str);
            }
        });
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorPickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.selectedColor = ViewCompat.MEASURED_STATE_MASK;
        View viewInflate = LinearLayoutCompat.inflate(getContext(), R.layout.color_picker_colorselectview, null);
        addView(viewInflate);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        this.rgbSelectorView = new RgbSelectorView(context2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "context");
        this.preSelectorView = new PresetSelectorView(context3);
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        this.hsvSelectorView = new HSVSelectorView(context4);
        Context context5 = getContext();
        Intrinsics.checkNotNullExpressionValue(context5, "context");
        this.alphaSliderView = new AlphaSliderView(context5);
        View viewFindViewById = viewInflate.findViewById(R.id.color_picker_colorview_tabColors);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "tabView.findViewById(R.i…cker_colorview_tabColors)");
        TabHost tabHost = (TabHost) viewFindViewById;
        this.tabHost = tabHost;
        tabHost.setup();
        ColorTabContentFactory colorTabContentFactory = new ColorTabContentFactory(this);
        Context context6 = getContext();
        Intrinsics.checkNotNullExpressionValue(context6, "context");
        ColorTabContentFactory colorTabContentFactory2 = colorTabContentFactory;
        TabHost.TabSpec content = tabHost.newTabSpec("PRE").setIndicator(createTabView(context6, R.drawable.ic_color_picker_tab_preset)).setContent(colorTabContentFactory2);
        Context context7 = getContext();
        Intrinsics.checkNotNullExpressionValue(context7, "context");
        TabHost.TabSpec content2 = tabHost.newTabSpec("HSV").setIndicator(createTabView(context7, R.drawable.ic_color_picker_tab_hsv)).setContent(colorTabContentFactory2);
        Context context8 = getContext();
        Intrinsics.checkNotNullExpressionValue(context8, "context");
        final TabHost.TabSpec content3 = tabHost.newTabSpec("RGB").setIndicator(createTabView(context8, R.drawable.ic_color_picker_tab_rgba)).setContent(colorTabContentFactory2);
        tabHost.addTab(content);
        tabHost.addTab(content2);
        tabHost.addTab(content3);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerView$$ExternalSyntheticLambda0
            @Override // android.widget.TabHost.OnTabChangeListener
            public final void onTabChanged(String str) {
                ColorPickerView.m5904lambda2$lambda1(content3, this, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: lambda-2$lambda-1, reason: not valid java name */
    public static final void m5904lambda2$lambda1(TabHost.TabSpec tabSpec, ColorPickerView this$0, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (Intrinsics.areEqual(str, tabSpec.getTag())) {
            this$0.alphaSliderView.setVisibility(8);
            this$0.showKeyboard();
        } else {
            AlphaSliderView alphaSliderView = this$0.alphaSliderView;
            alphaSliderView.setVisibility((alphaSliderView.getIsCatroid() && alphaSliderView.getOpenedFromFormulaEditorInCatroidFlag()) ? 8 : 0);
            this$0.hideKeyboard();
        }
    }

    public final void setAlphaSlider(AlphaSliderView alphaSliderView, boolean catroidFlag) {
        Intrinsics.checkNotNullParameter(alphaSliderView, "alphaSliderView");
        this.alphaSliderView = alphaSliderView;
        alphaSliderView.setCatroid(catroidFlag);
        alphaSliderView.setOpenedFromFormulaEditorInCatroidFlag(getIsOpenedFromFormulaEditorInCatroid());
    }

    private final View createTabView(Context context, int iconResourceId) {
        View tabView = LinearLayoutCompat.inflate(context, R.layout.color_picker_tab_image_only, null);
        ((AppCompatImageView) tabView.findViewById(R.id.color_picker_tab_icon)).setBackgroundResource(iconResourceId);
        Intrinsics.checkNotNullExpressionValue(tabView, "tabView");
        return tabView;
    }

    public static /* synthetic */ void setSelectedColor$default(ColorPickerView colorPickerView, int i, View view, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            view = null;
        }
        colorPickerView.setSelectedColor(i, view);
    }

    public final void setSelectedColor(int color, View sender) {
        if (this.selectedColor == color) {
            return;
        }
        this.selectedColor = color;
        if (!Intrinsics.areEqual(sender, this.rgbSelectorView)) {
            this.rgbSelectorView.setSelectedColor(color, this.isOpenedFromFormulaEditorInCatroid);
        }
        if (!Intrinsics.areEqual(sender, this.preSelectorView)) {
            this.preSelectorView.setSelectedColor(color);
        }
        if (!Intrinsics.areEqual(sender, this.hsvSelectorView)) {
            this.hsvSelectorView.setSelectedColor(color);
        }
        if (!Intrinsics.areEqual(sender, this.alphaSliderView)) {
            this.alphaSliderView.setSelectedColor(color);
        }
        onColorChanged();
    }

    public final int getSelectedColor() {
        return this.selectedColor;
    }

    private final void hideKeyboard() {
        Object systemService = getContext().getSystemService("input_method");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).hideSoftInputFromWindow(getRootView().getWindowToken(), 0);
    }

    private final void showKeyboard() {
        Object systemService = getContext().getSystemService("input_method");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).toggleSoftInputFromWindow(getRootView().getApplicationWindowToken(), 1, 0);
    }

    private final void onColorChanged() {
        OnColorChangedListener onColorChangedListener = this.colorChangedListener;
        if (onColorChangedListener == null) {
            return;
        }
        onColorChangedListener.colorChanged(this.selectedColor);
    }

    public final void setOnColorChangedListener(OnColorChangedListener listener) {
        this.colorChangedListener = listener;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.tabHost.getCurrentTabTag());
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable state) {
        Intrinsics.checkNotNullParameter(state, "state");
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            super.onRestoreInstanceState(savedState.getSuperState());
            this.tabHost.setCurrentTabByTag(savedState.getCurrentTabTag());
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.preSelectorView.setOnColorChangedListener(new PresetSelectorView.OnColorChangedListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerView$$ExternalSyntheticLambda1
            @Override // org.catrobat.paintroid.colorpicker.PresetSelectorView.OnColorChangedListener
            public final void colorChanged(int i) {
                ColorPickerView.m5905onAttachedToWindow$lambda4(this.f$0, i);
            }
        });
        this.hsvSelectorView.getHsvColorPickerView().setOnColorChangedListener(new HSVColorPickerView.OnColorChangedListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerView$$ExternalSyntheticLambda2
            @Override // org.catrobat.paintroid.colorpicker.HSVColorPickerView.OnColorChangedListener
            public final void colorChanged(int i) {
                ColorPickerView.m5906onAttachedToWindow$lambda5(this.f$0, i);
            }
        });
        this.rgbSelectorView.setOnColorChangedListener(new RgbSelectorView.OnColorChangedListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerView$$ExternalSyntheticLambda3
            @Override // org.catrobat.paintroid.colorpicker.RgbSelectorView.OnColorChangedListener
            public final void colorChanged(int i) {
                ColorPickerView.m5907onAttachedToWindow$lambda6(this.f$0, i);
            }
        });
        AlphaSlider alphaSlider = this.alphaSliderView.getAlphaSlider();
        if (alphaSlider == null) {
            return;
        }
        alphaSlider.setOnColorChangedListener(new AlphaSlider.OnColorChangedListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerView.onAttachedToWindow.4
            @Override // org.catrobat.paintroid.colorpicker.AlphaSlider.OnColorChangedListener
            public void colorChanged(int color) {
                ColorPickerView colorPickerView = ColorPickerView.this;
                colorPickerView.setSelectedColor(color, colorPickerView.alphaSliderView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onAttachedToWindow$lambda-4, reason: not valid java name */
    public static final void m5905onAttachedToWindow$lambda4(ColorPickerView this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setSelectedColor(i, this$0.preSelectorView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onAttachedToWindow$lambda-5, reason: not valid java name */
    public static final void m5906onAttachedToWindow$lambda5(ColorPickerView this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setSelectedColor(i, this$0.hsvSelectorView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onAttachedToWindow$lambda-6, reason: not valid java name */
    public static final void m5907onAttachedToWindow$lambda6(ColorPickerView this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setSelectedColor(i, this$0.getRgbSelectorView());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.preSelectorView.setOnColorChangedListener(null);
        this.hsvSelectorView.getHsvColorPickerView().setOnColorChangedListener(null);
        this.rgbSelectorView.setOnColorChangedListener(null);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    /* JADX INFO: compiled from: ColorPickerView.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerView$ColorTabContentFactory;", "Landroid/widget/TabHost$TabContentFactory;", "(Lorg/catrobat/paintroid/colorpicker/ColorPickerView;)V", "createTabContent", "Landroid/view/View;", "tag", "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class ColorTabContentFactory implements TabHost.TabContentFactory {
        final /* synthetic */ ColorPickerView this$0;

        public ColorTabContentFactory(ColorPickerView this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // android.widget.TabHost.TabContentFactory
        public View createTabContent(String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            int iHashCode = tag.hashCode();
            if (iHashCode != 71851) {
                if (iHashCode != 79491) {
                    if (iHashCode == 81069 && tag.equals("RGB")) {
                        return this.this$0.getRgbSelectorView();
                    }
                } else if (tag.equals("PRE")) {
                    return this.this$0.preSelectorView;
                }
            } else if (tag.equals("HSV")) {
                return this.this$0.hsvSelectorView;
            }
            throw new IllegalArgumentException("Invalid TAG");
        }
    }

    /* JADX INFO: compiled from: ColorPickerView.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerView$SavedState;", "Landroid/view/View$BaseSavedState;", FirebaseAnalytics.Param.SOURCE, "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "superState", "Landroid/os/Parcelable;", "currentTabTag", "", "(Landroid/os/Parcelable;Ljava/lang/String;)V", "getCurrentTabTag", "()Ljava/lang/String;", "setCurrentTabTag", "(Ljava/lang/String;)V", "writeToParcel", "", "dest", OverlayablePolicy.NAME_flags, "", "CREATOR", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class SavedState extends View.BaseSavedState {

        /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private String currentTabTag;

        public final String getCurrentTabTag() {
            return this.currentTabTag;
        }

        public final void setCurrentTabTag(String str) {
            this.currentTabTag = str;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SavedState(Parcel source) {
            super(source);
            Intrinsics.checkNotNullParameter(source, "source");
            this.currentTabTag = source.readString();
        }

        public SavedState(Parcelable parcelable, String str) {
            super(parcelable);
            this.currentTabTag = str;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            Intrinsics.checkNotNullParameter(dest, "dest");
            super.writeToParcel(dest, flags);
            dest.writeString(this.currentTabTag);
        }

        /* JADX INFO: renamed from: org.catrobat.paintroid.colorpicker.ColorPickerView$SavedState$CREATOR, reason: from kotlin metadata */
        /* JADX INFO: compiled from: ColorPickerView.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerView$SavedState$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lorg/catrobat/paintroid/colorpicker/ColorPickerView$SavedState;", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lorg/catrobat/paintroid/colorpicker/ColorPickerView$SavedState;", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class Companion implements Parcelable.Creator<SavedState> {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                Intrinsics.checkNotNullParameter(parcel, "parcel");
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }
}
