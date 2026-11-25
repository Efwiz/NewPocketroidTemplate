package org.catrobat.paintroid.ui.tools;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.drawable.DrawableShape;
import org.catrobat.paintroid.tools.drawable.DrawableStyle;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.catrobat.paintroid.tools.options.ShapeToolOptionsView;

/* JADX INFO: compiled from: DefaultShapeToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u0019H\u0002J\b\u0010$\u001a\u00020\u0019H\u0002J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010'\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010(\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010)\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u001fH\u0002J8\u0010+\u001a\u00020\u00192\u0006\u0010,\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020\u001f2\u0006\u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u001fH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultShapeToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "callback", "Lorg/catrobat/paintroid/tools/options/ShapeToolOptionsView$Callback;", "circleButton", "Landroidx/appcompat/widget/AppCompatImageButton;", "fillButton", "heartButton", "outlineButton", "outlineTextView", "Landroidx/appcompat/widget/AppCompatTextView;", "outlineView", "Landroid/view/View;", "outlineWidthEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "outlineWidthSeekBar", "Landroidx/appcompat/widget/AppCompatSeekBar;", "shapeToolDialogTitle", "shapeToolFillOutline", "squareButton", "starButton", "initializeListeners", "", "onDrawTypeClicked", "drawType", "Lorg/catrobat/paintroid/tools/drawable/DrawableStyle;", "onOutlineWidthChanged", "outlineWidth", "", "onShapeClicked", "shape", "Lorg/catrobat/paintroid/tools/drawable/DrawableShape;", "resetDrawTypeActivated", "resetShapeActivated", "setCallback", "setDrawTypeActivated", "setShapeActivated", "setShapeOutlineWidth", "setShapeToolDialogTitle", "title", "setupUI", "fillTitle", "squareButtonResource", "circleButtonResource", "heartButtonResource", "starButtonResource", "visibility", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultShapeToolOptionsView implements ShapeToolOptionsView {
    private ShapeToolOptionsView.Callback callback;
    private final AppCompatImageButton circleButton;
    private final AppCompatImageButton fillButton;
    private final AppCompatImageButton heartButton;
    private final AppCompatImageButton outlineButton;
    private final AppCompatTextView outlineTextView;
    private final View outlineView;
    private final AppCompatEditText outlineWidthEditText;
    private final AppCompatSeekBar outlineWidthSeekBar;
    private final AppCompatTextView shapeToolDialogTitle;
    private final AppCompatTextView shapeToolFillOutline;
    private final AppCompatImageButton squareButton;
    private final AppCompatImageButton starButton;

    /* JADX INFO: compiled from: DefaultShapeToolOptionsView.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[DrawableShape.values().length];
            iArr[DrawableShape.RECTANGLE.ordinal()] = 1;
            iArr[DrawableShape.OVAL.ordinal()] = 2;
            iArr[DrawableShape.HEART.ordinal()] = 3;
            iArr[DrawableShape.STAR.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[DrawableStyle.values().length];
            iArr2[DrawableStyle.FILL.ordinal()] = 1;
            iArr2[DrawableStyle.STROKE.ordinal()] = 2;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public DefaultShapeToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        View viewInflate = LayoutInflater.from(rootView.getContext()).inflate(R.layout.dialog_pocketpaint_shapes, rootView);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_shapes_square_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocketpaint_shapes_square_btn)");
        this.squareButton = (AppCompatImageButton) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_shapes_circle_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.pocketpaint_shapes_circle_btn)");
        this.circleButton = (AppCompatImageButton) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.pocketpaint_shapes_heart_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.pocketpaint_shapes_heart_btn)");
        this.heartButton = (AppCompatImageButton) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.pocketpaint_shapes_star_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.pocketpaint_shapes_star_btn)");
        this.starButton = (AppCompatImageButton) viewFindViewById4;
        View viewFindViewById5 = viewInflate.findViewById(R.id.pocketpaint_shape_ibtn_fill);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.pocketpaint_shape_ibtn_fill)");
        this.fillButton = (AppCompatImageButton) viewFindViewById5;
        View viewFindViewById6 = viewInflate.findViewById(R.id.pocketpaint_shape_ibtn_outline);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.pocketpaint_shape_ibtn_outline)");
        this.outlineButton = (AppCompatImageButton) viewFindViewById6;
        View viewFindViewById7 = viewInflate.findViewById(R.id.pocketpaint_shape_tool_dialog_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.pocket…_shape_tool_dialog_title)");
        this.shapeToolDialogTitle = (AppCompatTextView) viewFindViewById7;
        View viewFindViewById8 = viewInflate.findViewById(R.id.pocketpaint_shape_tool_fill_outline);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.pocket…_shape_tool_fill_outline)");
        this.shapeToolFillOutline = (AppCompatTextView) viewFindViewById8;
        View viewFindViewById9 = viewInflate.findViewById(R.id.pocketpaint_outline_view_border);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.pocketpaint_outline_view_border)");
        this.outlineView = viewFindViewById9;
        View viewFindViewById10 = viewInflate.findViewById(R.id.pocketpaint_outline_view_text_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.pocket…t_outline_view_text_view)");
        this.outlineTextView = (AppCompatTextView) viewFindViewById10;
        View viewFindViewById11 = viewInflate.findViewById(R.id.pocketpaint_shape_stroke_width_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.pocket…pe_stroke_width_seek_bar)");
        AppCompatSeekBar appCompatSeekBar = (AppCompatSeekBar) viewFindViewById11;
        this.outlineWidthSeekBar = appCompatSeekBar;
        View viewFindViewById12 = viewInflate.findViewById(R.id.pocketpaint_shape_outline_edit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.pocketpaint_shape_outline_edit)");
        AppCompatEditText appCompatEditText = (AppCompatEditText) viewFindViewById12;
        this.outlineWidthEditText = appCompatEditText;
        appCompatEditText.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        appCompatEditText.setText("25");
        appCompatSeekBar.setProgress(25);
        initializeListeners();
        setShapeActivated(DrawableShape.RECTANGLE);
        setDrawTypeActivated(DrawableStyle.FILL);
    }

    private final void initializeListeners() {
        this.squareButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultShapeToolOptionsView.m5992initializeListeners$lambda1(this.f$0, view);
            }
        });
        this.circleButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultShapeToolOptionsView.m5993initializeListeners$lambda2(this.f$0, view);
            }
        });
        this.heartButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultShapeToolOptionsView.m5994initializeListeners$lambda3(this.f$0, view);
            }
        });
        this.starButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultShapeToolOptionsView.m5995initializeListeners$lambda4(this.f$0, view);
            }
        });
        this.fillButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultShapeToolOptionsView.m5996initializeListeners$lambda5(this.f$0, view);
            }
        });
        this.outlineButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultShapeToolOptionsView.m5997initializeListeners$lambda6(this.f$0, view);
            }
        });
        this.outlineWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView.initializeListeners.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                if (progress < 1) {
                    progress = 1;
                }
                if (fromUser) {
                    seekBar.setProgress(progress);
                }
                DefaultShapeToolOptionsView.this.outlineWidthEditText.setText(String.valueOf(progress));
                DefaultShapeToolOptionsView.this.onOutlineWidthChanged(progress);
            }
        });
        this.outlineWidthEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultShapeToolOptionsView.initializeListeners.8
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
                    i = Integer.parseInt(String.valueOf(DefaultShapeToolOptionsView.this.outlineWidthEditText.getText()));
                } catch (NumberFormatException unused) {
                    i = 1;
                }
                DefaultShapeToolOptionsView.this.outlineWidthSeekBar.setProgress(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-1, reason: not valid java name */
    public static final void m5992initializeListeners$lambda1(DefaultShapeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShapeClicked(DrawableShape.RECTANGLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-2, reason: not valid java name */
    public static final void m5993initializeListeners$lambda2(DefaultShapeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShapeClicked(DrawableShape.OVAL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-3, reason: not valid java name */
    public static final void m5994initializeListeners$lambda3(DefaultShapeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShapeClicked(DrawableShape.HEART);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-4, reason: not valid java name */
    public static final void m5995initializeListeners$lambda4(DefaultShapeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShapeClicked(DrawableShape.STAR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-5, reason: not valid java name */
    public static final void m5996initializeListeners$lambda5(DefaultShapeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onDrawTypeClicked(DrawableStyle.FILL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-6, reason: not valid java name */
    public static final void m5997initializeListeners$lambda6(DefaultShapeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onDrawTypeClicked(DrawableStyle.STROKE);
    }

    private final void onShapeClicked(DrawableShape shape) {
        ShapeToolOptionsView.Callback callback = this.callback;
        if (callback != null) {
            callback.setToolType(shape);
        }
        setShapeActivated(shape);
    }

    private final void onDrawTypeClicked(DrawableStyle drawType) {
        ShapeToolOptionsView.Callback callback = this.callback;
        if (callback != null) {
            callback.setDrawType(drawType);
        }
        setDrawTypeActivated(drawType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onOutlineWidthChanged(int outlineWidth) {
        ShapeToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.setOutlineWidth(outlineWidth);
    }

    private final void resetShapeActivated() {
        View[] viewArr = {this.squareButton, this.circleButton, this.heartButton, this.starButton};
        int i = 0;
        while (i < 4) {
            View view = viewArr[i];
            i++;
            view.setSelected(false);
        }
    }

    private final void resetDrawTypeActivated() {
        this.fillButton.setSelected(false);
        this.outlineButton.setSelected(false);
    }

    private final void setShapeToolDialogTitle(int title) {
        this.shapeToolDialogTitle.setText(title);
    }

    @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView
    public void setShapeActivated(DrawableShape shape) {
        Intrinsics.checkNotNullParameter(shape, "shape");
        resetShapeActivated();
        int i = WhenMappings.$EnumSwitchMapping$0[shape.ordinal()];
        if (i == 1) {
            this.squareButton.setSelected(true);
            setShapeToolDialogTitle(R.string.shape_tool_dialog_rect_title);
            return;
        }
        if (i == 2) {
            this.circleButton.setSelected(true);
            setShapeToolDialogTitle(R.string.shape_tool_dialog_ellipse_title);
        } else if (i == 3) {
            this.heartButton.setSelected(true);
            setShapeToolDialogTitle(R.string.shape_tool_dialog_heart_title);
        } else {
            if (i != 4) {
                return;
            }
            this.starButton.setSelected(true);
            setShapeToolDialogTitle(R.string.shape_tool_dialog_star_title);
        }
    }

    private final void setupUI(int fillTitle, int squareButtonResource, int circleButtonResource, int heartButtonResource, int starButtonResource, int visibility) {
        this.shapeToolFillOutline.setText(fillTitle);
        this.squareButton.setImageResource(squareButtonResource);
        this.circleButton.setImageResource(circleButtonResource);
        this.heartButton.setImageResource(heartButtonResource);
        this.starButton.setImageResource(starButtonResource);
        this.outlineWidthSeekBar.setVisibility(visibility);
        this.outlineWidthEditText.setVisibility(visibility);
        this.outlineView.setVisibility(visibility);
        this.outlineTextView.setVisibility(visibility);
    }

    @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView
    public void setDrawTypeActivated(DrawableStyle drawType) {
        Intrinsics.checkNotNullParameter(drawType, "drawType");
        resetDrawTypeActivated();
        int i = WhenMappings.$EnumSwitchMapping$1[drawType.ordinal()];
        if (i == 1) {
            this.fillButton.setSelected(true);
            setupUI(R.string.shape_tool_dialog_fill_title, R.drawable.ic_pocketpaint_rectangle, R.drawable.ic_pocketpaint_circle, R.drawable.ic_pocketpaint_heart, R.drawable.ic_pocketpaint_star, 8);
        } else {
            if (i != 2) {
                return;
            }
            this.outlineButton.setSelected(true);
            setupUI(R.string.shape_tool_dialog_outline_title, R.drawable.ic_pocketpaint_rectangle_out, R.drawable.ic_pocketpaint_circle_out, R.drawable.ic_pocketpaint_heart_out, R.drawable.ic_pocketpaint_star_out, 0);
        }
    }

    @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView
    public void setShapeOutlineWidth(int outlineWidth) {
        this.outlineWidthSeekBar.setProgress(outlineWidth);
        AppCompatEditText appCompatEditText = this.outlineWidthEditText;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(outlineWidth)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        appCompatEditText.setText(str);
    }

    @Override // org.catrobat.paintroid.tools.options.ShapeToolOptionsView
    public void setCallback(ShapeToolOptionsView.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }
}
