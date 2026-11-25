package org.catrobat.paintroid.colorpicker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ColorPickerDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 >2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002>?B\u0005¢\u0006\u0002\u0010\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0016J\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u000eH\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u000eH\u0016J\b\u0010$\u001a\u00020\u001fH\u0016J\"\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u000e2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0010\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020,H\u0016J\u0012\u0010-\u001a\u00020\u001f2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u0012\u00100\u001a\u0002012\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u0010\u00102\u001a\u00020\u001f2\u0006\u00103\u001a\u00020/H\u0016J\u000e\u00104\u001a\u00020\u001f2\u0006\u00105\u001a\u00020\u0010J\u0010\u00106\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u000eH\u0002J \u00107\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0002J\u0018\u0010;\u001a\u00020\u001f2\u0006\u0010<\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u000eH\u0002J\u0010\u0010=\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0017\u0010\u0004\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerDialog;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "Lorg/catrobat/paintroid/colorpicker/OnColorChangedListener;", "Lorg/catrobat/paintroid/colorpicker/OnColorInHistoryChangedListener;", "()V", "alphaSliderView", "Lorg/catrobat/paintroid/colorpicker/AlphaSliderView;", "checkeredShader", "Landroid/graphics/Shader;", "colorHistoryView", "Lorg/catrobat/paintroid/colorpicker/ColorHistoryView;", "colorPickerView", "Lorg/catrobat/paintroid/colorpicker/ColorPickerView;", "colorToApply", "", "currentBitmap", "Landroid/graphics/Bitmap;", "currentColorView", "Landroid/view/View;", "newColorView", "onColorPickedListener", "", "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "getOnColorPickedListener$annotations", "getOnColorPickedListener", "()Ljava/util/List;", "setOnColorPickedListener", "(Ljava/util/List;)V", "pipetteBtn", "Lcom/google/android/material/button/MaterialButton;", "addOnColorPickedListener", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "colorChanged", TypedValues.Custom.S_COLOR, "colorInHistoryChanged", "dismiss", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCancel", "dialog", "Landroid/content/DialogInterface;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onSaveInstanceState", "outState", "setBitmap", "bitmap", "setCurrentColor", "setInitialColor", "catroidFlag", "", "openedFromFormulaEditorInCatroidFlag", "setViewColor", Promotion.ACTION_VIEW, "updateColorChange", "Companion", "CustomColorDrawable", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorPickerDialog extends AppCompatDialogFragment implements OnColorChangedListener, OnColorInHistoryChangedListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "ColorPickerDialog";
    private AlphaSliderView alphaSliderView;
    private Shader checkeredShader;
    private ColorHistoryView colorHistoryView;
    private ColorPickerView colorPickerView;
    private int colorToApply;
    private Bitmap currentBitmap;
    private View currentColorView;
    private View newColorView;
    private List<OnColorPickedListener> onColorPickedListener = new ArrayList();
    private MaterialButton pipetteBtn;

    public static /* synthetic */ void getOnColorPickedListener$annotations() {
    }

    public final List<OnColorPickedListener> getOnColorPickedListener() {
        return this.onColorPickedListener;
    }

    public final void setOnColorPickedListener(List<OnColorPickedListener> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.onColorPickedListener = list;
    }

    /* JADX INFO: compiled from: ColorPickerDialog.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u0007J*\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerDialog$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/colorpicker/ColorPickerDialog;", "initialColor", "", "catroidFlag", "", "openedFromFormulaEditorInCatroidFlag", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ColorPickerDialog newInstance$default(Companion companion, int i, boolean z, boolean z2, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                z2 = false;
            }
            return companion.newInstance(i, z, z2);
        }

        @Deprecated(message = "Does not support ColorHistory")
        public final ColorPickerDialog newInstance(int initialColor, boolean catroidFlag, boolean openedFromFormulaEditorInCatroidFlag) {
            ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
            int iArgb = Color.argb(openedFromFormulaEditorInCatroidFlag ? 255 : Color.alpha(initialColor), Color.red(initialColor), Color.green(initialColor), Color.blue(initialColor));
            Bundle bundle = new Bundle();
            bundle.putInt("InitialColor", iArgb);
            bundle.putInt("CurrentColor", iArgb);
            bundle.putBoolean("InitialCatroidFlag", catroidFlag);
            bundle.putBoolean("OpenedFromFormulaEditorCatroidFlag", openedFromFormulaEditorInCatroidFlag);
            bundle.putIntegerArrayList("colorHistory", new ArrayList<>());
            colorPickerDialog.setArguments(bundle);
            return colorPickerDialog;
        }

        public static /* synthetic */ ColorPickerDialog newInstance$default(Companion companion, int i, boolean z, boolean z2, ColorHistory colorHistory, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                z2 = false;
            }
            return companion.newInstance(i, z, z2, colorHistory);
        }

        public final ColorPickerDialog newInstance(int initialColor, boolean catroidFlag, boolean openedFromFormulaEditorInCatroidFlag, ColorHistory colorHistory) {
            Intrinsics.checkNotNullParameter(colorHistory, "colorHistory");
            ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
            int iArgb = Color.argb(openedFromFormulaEditorInCatroidFlag ? 255 : Color.alpha(initialColor), Color.red(initialColor), Color.green(initialColor), Color.blue(initialColor));
            Bundle bundle = new Bundle();
            bundle.putInt("InitialColor", iArgb);
            bundle.putInt("CurrentColor", iArgb);
            bundle.putBoolean("InitialCatroidFlag", catroidFlag);
            bundle.putBoolean("OpenedFromFormulaEditorCatroidFlag", openedFromFormulaEditorInCatroidFlag);
            bundle.putIntegerArrayList("colorHistory", colorHistory.getColors());
            colorPickerDialog.setArguments(bundle);
            return colorPickerDialog;
        }
    }

    public final void setBitmap(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.currentBitmap = bitmap;
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ColorPickerView colorPickerView = null;
        View viewInflate = requireActivity().getLayoutInflater().inflate(R.layout.color_picker_dialog_view, (ViewGroup) null);
        View viewFindViewById = viewInflate.findViewById(R.id.color_picker_current_color_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "dialogView.findViewById(…icker_current_color_view)");
        this.currentColorView = viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.color_picker_pipette_btn);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "dialogView.findViewById(…color_picker_pipette_btn)");
        MaterialButton materialButton = (MaterialButton) viewFindViewById2;
        this.pipetteBtn = materialButton;
        if (materialButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipetteBtn");
            materialButton = null;
        }
        materialButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorPickerDialog.m5896onCreateDialog$lambda0(this.f$0, view);
            }
        });
        View viewFindViewById3 = viewInflate.findViewById(R.id.color_picker_new_color_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "dialogView.findViewById(…or_picker_new_color_view)");
        this.newColorView = viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.color_picker_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "dialogView.findViewById(R.id.color_picker_view)");
        ColorPickerView colorPickerView2 = (ColorPickerView) viewFindViewById4;
        this.colorPickerView = colorPickerView2;
        if (colorPickerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView2 = null;
        }
        colorPickerView2.setOnColorChangedListener(this);
        View viewFindViewById5 = viewInflate.findViewById(R.id.color_alpha_slider);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "dialogView.findViewById(R.id.color_alpha_slider)");
        this.alphaSliderView = (AlphaSliderView) viewFindViewById5;
        View viewFindViewById6 = viewInflate.findViewById(R.id.color_history_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "dialogView.findViewById(R.id.color_history_view)");
        ColorHistoryView colorHistoryView = (ColorHistoryView) viewFindViewById6;
        this.colorHistoryView = colorHistoryView;
        if (colorHistoryView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorHistoryView");
            colorHistoryView = null;
        }
        colorHistoryView.setOnColorInHistoryChangedListener(this);
        ColorHistoryView colorHistoryView2 = this.colorHistoryView;
        if (colorHistoryView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorHistoryView");
            colorHistoryView2 = null;
        }
        View viewFindViewById7 = viewInflate.findViewById(R.id.color_history_text_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "dialogView.findViewById(…olor_history_text_layout)");
        colorHistoryView2.setTextLayout((LinearLayout) viewFindViewById7);
        if (savedInstanceState == null) {
            savedInstanceState = requireArguments();
            Intrinsics.checkNotNullExpressionValue(savedInstanceState, "requireArguments()");
        }
        boolean z = savedInstanceState.getBoolean("InitialCatroidFlag");
        boolean z2 = savedInstanceState.getBoolean("OpenedFromFormulaEditorCatroidFlag");
        ColorPickerView colorPickerView3 = this.colorPickerView;
        if (colorPickerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView3 = null;
        }
        colorPickerView3.setOpenedFromFormulaEditorInCatroid(z2);
        ArrayList<Integer> integerArrayList = savedInstanceState.getIntegerArrayList("colorHistory");
        if (integerArrayList != null) {
            ColorHistoryView colorHistoryView3 = this.colorHistoryView;
            if (colorHistoryView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("colorHistoryView");
                colorHistoryView3 = null;
            }
            colorHistoryView3.updateColorHistory(integerArrayList);
        }
        setCurrentColor(savedInstanceState.getInt("CurrentColor", ViewCompat.MEASURED_STATE_MASK));
        setInitialColor(savedInstanceState.getInt("InitialColor", ViewCompat.MEASURED_STATE_MASK), z, z2);
        ColorPickerView colorPickerView4 = this.colorPickerView;
        if (colorPickerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView4 = null;
        }
        AlphaSliderView alphaSliderView = this.alphaSliderView;
        if (alphaSliderView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSliderView");
            alphaSliderView = null;
        }
        colorPickerView4.setAlphaSlider(alphaSliderView, z);
        ColorPickerView colorPickerView5 = this.colorPickerView;
        if (colorPickerView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView5 = null;
        }
        colorPickerView5.getRgbSelectorView().setAlphaRow(z, z2);
        ColorPickerView colorPickerView6 = this.colorPickerView;
        if (colorPickerView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
        } else {
            colorPickerView = colorPickerView6;
        }
        this.colorToApply = colorPickerView.getInitialColor();
        final AlertDialog alertDialogCreate = new MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme).setNegativeButton(R.string.color_picker_cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ColorPickerDialog.m5897onCreateDialog$lambda3(dialogInterface, i);
            }
        }).setPositiveButton(R.string.color_picker_apply, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerDialog$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ColorPickerDialog.m5898onCreateDialog$lambda4(this.f$0, dialogInterface, i);
            }
        }).setView(viewInflate).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "MaterialAlertDialogBuild…ew)\n            .create()");
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                ColorPickerDialog.m5899onCreateDialog$lambda5(alertDialogCreate, dialogInterface);
            }
        });
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-0, reason: not valid java name */
    public static final void m5896onCreateDialog$lambda0(ColorPickerDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MaterialButton materialButton = this$0.pipetteBtn;
        if (materialButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipetteBtn");
            materialButton = null;
        }
        materialButton.setEnabled(false);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new ColorPickerDialog$onCreateDialog$1$1(this$0, view, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-3, reason: not valid java name */
    public static final void m5897onCreateDialog$lambda3(DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(dialogInterface, "dialogInterface");
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-4, reason: not valid java name */
    public static final void m5898onCreateDialog$lambda4(ColorPickerDialog this$0, DialogInterface noName_0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(noName_0, "$noName_0");
        this$0.updateColorChange(this$0.colorToApply);
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-5, reason: not valid java name */
    public static final void m5899onCreateDialog$lambda5(AlertDialog materialDialog, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(materialDialog, "$materialDialog");
        Window window = materialDialog.getWindow();
        if (window != null) {
            window.clearFlags(131080);
        }
        Window window2 = materialDialog.getWindow();
        if (window2 == null) {
            return;
        }
        window2.setSoftInputMode(3);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        updateColorChange(this.colorToApply);
        super.onCancel(dialog);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.checkeredShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

    @Override // org.catrobat.paintroid.colorpicker.OnColorChangedListener
    public void colorChanged(int color) {
        AlphaSliderView alphaSliderView = this.alphaSliderView;
        View view = null;
        if (alphaSliderView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSliderView");
            alphaSliderView = null;
        }
        if (alphaSliderView.getVisibility() == 8) {
            ColorPickerView colorPickerView = this.colorPickerView;
            if (colorPickerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
                colorPickerView = null;
            }
            if (colorPickerView.getRgbSelectorView().getSeekBarAlpha().getVisibility() == 8) {
                AlphaSliderView alphaSliderView2 = this.alphaSliderView;
                if (alphaSliderView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alphaSliderView");
                    alphaSliderView2 = null;
                }
                AlphaSlider alphaSlider = alphaSliderView2.getAlphaSlider();
                if (alphaSlider != null) {
                    alphaSlider.invalidate();
                }
                AlphaSliderView alphaSliderView3 = this.alphaSliderView;
                if (alphaSliderView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alphaSliderView");
                    alphaSliderView3 = null;
                }
                AlphaSlider alphaSlider2 = alphaSliderView3.getAlphaSlider();
                Integer numValueOf = alphaSlider2 == null ? null : Integer.valueOf(alphaSlider2.getAlphaValue());
                float[] fArr = new float[3];
                Color.colorToHSV(color, fArr);
                Integer numValueOf2 = numValueOf == null ? null : Integer.valueOf(Color.HSVToColor(numValueOf.intValue(), fArr));
                if (numValueOf2 != null) {
                    View view2 = this.newColorView;
                    if (view2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("newColorView");
                    } else {
                        view = view2;
                    }
                    setViewColor(view, numValueOf2.intValue());
                    this.colorToApply = numValueOf2.intValue();
                    return;
                }
                return;
            }
        }
        ColorPickerView colorPickerView2 = this.colorPickerView;
        if (colorPickerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView2 = null;
        }
        if (colorPickerView2.getRgbSelectorView().getSeekBarAlpha().getVisibility() != 8) {
            AlphaSliderView alphaSliderView4 = this.alphaSliderView;
            if (alphaSliderView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alphaSliderView");
                alphaSliderView4 = null;
            }
            AlphaSlider alphaSlider3 = alphaSliderView4.getAlphaSlider();
            if (alphaSlider3 != null) {
                alphaSlider3.setAlphaValue(Color.alpha(color));
            }
        }
        View view3 = this.newColorView;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("newColorView");
        } else {
            view = view3;
        }
        setViewColor(view, color);
        this.colorToApply = color;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        ColorPickerView colorPickerView = this.colorPickerView;
        ColorHistoryView colorHistoryView = null;
        if (colorPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView = null;
        }
        outState.putInt("CurrentColor", colorPickerView.getSelectedColor());
        ColorPickerView colorPickerView2 = this.colorPickerView;
        if (colorPickerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView2 = null;
        }
        outState.putInt("InitialColor", colorPickerView2.getInitialColor());
        ColorHistoryView colorHistoryView2 = this.colorHistoryView;
        if (colorHistoryView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorHistoryView");
        } else {
            colorHistoryView = colorHistoryView2;
        }
        outState.putSerializable("colorHistory", colorHistoryView.getColorHistory());
    }

    public final void addOnColorPickedListener(OnColorPickedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onColorPickedListener.add(listener);
    }

    private final void updateColorChange(int color) {
        Iterator<T> it = this.onColorPickedListener.iterator();
        while (it.hasNext()) {
            ((OnColorPickedListener) it.next()).colorChanged(color);
        }
    }

    private final void setInitialColor(int color, boolean catroidFlag, boolean openedFromFormulaEditorInCatroidFlag) {
        View view = this.currentColorView;
        ColorPickerView colorPickerView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentColorView");
            view = null;
        }
        setViewColor(view, color);
        AlphaSliderView alphaSliderView = this.alphaSliderView;
        if (alphaSliderView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSliderView");
            alphaSliderView = null;
        }
        alphaSliderView.setVisibility((catroidFlag && openedFromFormulaEditorInCatroidFlag) ? 8 : 0);
        ColorPickerView colorPickerView2 = this.colorPickerView;
        if (colorPickerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
        } else {
            colorPickerView = colorPickerView2;
        }
        colorPickerView.setInitialColor(color);
    }

    private final void setCurrentColor(int color) {
        View view = this.newColorView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("newColorView");
            view = null;
        }
        setViewColor(view, color);
        ColorPickerView colorPickerView = this.colorPickerView;
        if (colorPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView = null;
        }
        ColorPickerView.setSelectedColor$default(colorPickerView, color, null, 2, null);
    }

    @Override // androidx.fragment.app.DialogFragment
    public void dismiss() {
        if (getShowsDialog()) {
            super.dismiss();
        } else {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    /* JADX INFO: compiled from: ColorPickerDialog.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerDialog$CustomColorDrawable;", "Landroid/graphics/drawable/ColorDrawable;", "checkeredShader", "Landroid/graphics/Shader;", TypedValues.Custom.S_COLOR, "", "(Landroid/graphics/Shader;I)V", "backgroundPaint", "Landroid/graphics/Paint;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "Companion", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class CustomColorDrawable extends ColorDrawable {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private Paint backgroundPaint;

        public /* synthetic */ CustomColorDrawable(Shader shader, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(shader, i);
        }

        @JvmStatic
        public static final Drawable createDrawable(Shader shader, int i) {
            return INSTANCE.createDrawable(shader, i);
        }

        private CustomColorDrawable(Shader shader, int i) {
            super(i);
            if (Color.alpha(getColor()) != 255) {
                Paint paint = new Paint();
                this.backgroundPaint = paint;
                paint.setShader(shader);
            }
        }

        /* JADX INFO: compiled from: ColorPickerDialog.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerDialog$CustomColorDrawable$Companion;", "", "()V", "createDrawable", "Landroid/graphics/drawable/Drawable;", "checkeredShader", "Landroid/graphics/Shader;", TypedValues.Custom.S_COLOR, "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final Drawable createDrawable(Shader checkeredShader, int color) {
                Intrinsics.checkNotNullParameter(checkeredShader, "checkeredShader");
                return new RippleDrawable(ColorStateList.valueOf(-1), new CustomColorDrawable(checkeredShader, color, null), null);
            }
        }

        @Override // android.graphics.drawable.ColorDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Intrinsics.checkNotNullParameter(canvas, "canvas");
            Paint paint = this.backgroundPaint;
            if (paint != null) {
                canvas.drawRect(getBounds(), paint);
            }
            super.draw(canvas);
        }
    }

    private final void setViewColor(View view, int color) {
        CustomColorDrawable.Companion companion = CustomColorDrawable.INSTANCE;
        Shader shader = this.checkeredShader;
        if (shader == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkeredShader");
            shader = null;
        }
        view.setBackground(companion.createDrawable(shader, color));
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null) {
            setCurrentColor(data.getIntExtra(ColorPickerDialogKt.COLOR_EXTRA, 0));
        }
    }

    @Override // org.catrobat.paintroid.colorpicker.OnColorInHistoryChangedListener
    public void colorInHistoryChanged(int color) {
        ColorPickerView colorPickerView = this.colorPickerView;
        if (colorPickerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPickerView");
            colorPickerView = null;
        }
        ColorPickerView.setSelectedColor$default(colorPickerView, color, null, 2, null);
        colorChanged(color);
    }
}
