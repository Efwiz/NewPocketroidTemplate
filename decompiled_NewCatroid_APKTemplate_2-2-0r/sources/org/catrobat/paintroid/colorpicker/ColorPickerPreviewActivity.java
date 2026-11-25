package org.catrobat.paintroid.colorpicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorPickerPreviewActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00192\u00020\u00012\u00020\u0002:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u000fH\u0014J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tH\u0002J\b\u0010\u0018\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerPreviewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/catrobat/paintroid/colorpicker/OnImageViewPointClickedListener;", "()V", "backAction", "Landroid/widget/ImageView;", "colorPreview", "Landroid/view/View;", "currentColor", "", "doneAction", "oldChosenColor", "previewSurface", "Lorg/catrobat/paintroid/colorpicker/ZoomableImageView;", "colorChanged", "", TypedValues.Custom.S_COLOR, "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "saveAndFinish", "setCurrentColor", "showSaveChangesDialog", "Companion", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorPickerPreviewActivity extends AppCompatActivity implements OnImageViewPointClickedListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static Bitmap pickableImage;
    private ImageView backAction;
    private View colorPreview;
    private int currentColor;
    private ImageView doneAction;
    private int oldChosenColor;
    private ZoomableImageView previewSurface;

    /* JADX INFO: compiled from: ColorPickerPreviewActivity.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorPickerPreviewActivity$Companion;", "", "()V", "pickableImage", "Landroid/graphics/Bitmap;", "getPickableImage", "()Landroid/graphics/Bitmap;", "setPickableImage", "(Landroid/graphics/Bitmap;)V", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Bitmap getPickableImage() {
            return ColorPickerPreviewActivity.pickableImage;
        }

        public final void setPickableImage(Bitmap bitmap) {
            ColorPickerPreviewActivity.pickableImage = bitmap;
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00c3  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Unit unit;
        Bitmap bitmap;
        Bundle extras;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker_preview);
        if (isFinishing()) {
            return;
        }
        View viewFindViewById = findViewById(R.id.backAction);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.backAction)");
        this.backAction = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.previewSurface);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.previewSurface)");
        ZoomableImageView zoomableImageView = (ZoomableImageView) viewFindViewById2;
        this.previewSurface = zoomableImageView;
        ImageView imageView = null;
        if (zoomableImageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("previewSurface");
            zoomableImageView = null;
        }
        zoomableImageView.setBackgroundColor(0);
        ZoomableImageView zoomableImageView2 = this.previewSurface;
        if (zoomableImageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("previewSurface");
            zoomableImageView2 = null;
        }
        zoomableImageView2.setListener(this);
        View viewFindViewById3 = findViewById(R.id.colorPreview);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.colorPreview)");
        this.colorPreview = viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.doneAction);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.doneAction)");
        this.doneAction = (ImageView) viewFindViewById4;
        Intent intent = getIntent();
        if (intent != null && (extras = intent.getExtras()) != null) {
            int i = extras.getInt(ColorPickerDialogKt.COLOR_EXTRA);
            this.oldChosenColor = i;
            setCurrentColor(i);
        }
        Bitmap bitmap2 = pickableImage;
        if (bitmap2 == null) {
            unit = null;
        } else {
            ZoomableImageView zoomableImageView3 = this.previewSurface;
            if (zoomableImageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("previewSurface");
                zoomableImageView3 = null;
            }
            zoomableImageView3.setImageBitmap(bitmap2);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            Bundle extras2 = getIntent().getExtras();
            Integer numValueOf = extras2 == null ? null : Integer.valueOf(extras2.getInt(ColorPickerDialogKt.BITMAP_HEIGHT_EXTRA));
            Bundle extras3 = getIntent().getExtras();
            Integer numValueOf2 = extras3 == null ? null : Integer.valueOf(extras3.getInt(ColorPickerDialogKt.BITMAP_WIDTH_EXTRA));
            if (numValueOf2 == null) {
                bitmap = null;
            } else {
                int iIntValue = numValueOf2.intValue();
                if (numValueOf == null) {
                    bitmap = null;
                } else {
                    bitmap = Bitmap.createBitmap(numValueOf.intValue(), iIntValue, Bitmap.Config.ARGB_8888);
                }
            }
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
            }
            ZoomableImageView zoomableImageView4 = this.previewSurface;
            if (zoomableImageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("previewSurface");
                zoomableImageView4 = null;
            }
            Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
            zoomableImageView4.setImageBitmap(bitmap);
        }
        ImageView imageView2 = this.doneAction;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("doneAction");
            imageView2 = null;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerPreviewActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorPickerPreviewActivity.m5900onCreate$lambda6(this.f$0, view);
            }
        });
        ImageView imageView3 = this.backAction;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backAction");
        } else {
            imageView = imageView3;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerPreviewActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorPickerPreviewActivity.m5901onCreate$lambda7(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreate$lambda-6, reason: not valid java name */
    public static final void m5900onCreate$lambda6(ColorPickerPreviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveAndFinish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreate$lambda-7, reason: not valid java name */
    public static final void m5901onCreate$lambda7(ColorPickerPreviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onBackPressed();
    }

    private final void saveAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(ColorPickerDialogKt.COLOR_EXTRA, this.currentColor);
        setResult(-1, intent);
        finish();
    }

    private final void setCurrentColor(int color) {
        this.currentColor = color;
        BitmapShader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        View view = this.colorPreview;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("colorPreview");
            view = null;
        }
        view.setBackground(ColorPickerDialog.CustomColorDrawable.INSTANCE.createDrawable(bitmapShader, color));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(67108864);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.oldChosenColor != this.currentColor) {
            showSaveChangesDialog();
        } else {
            super.onBackPressed();
        }
    }

    private final void showSaveChangesDialog() {
        new MaterialAlertDialogBuilder(this).setTitle(R.string.color_picker_save_dialog_title).setMessage(R.string.color_picker_save_dialog_msg).setNegativeButton(R.string.color_picker_no, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerPreviewActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ColorPickerPreviewActivity.m5903showSaveChangesDialog$lambda9(this.f$0, dialogInterface, i);
            }
        }).setPositiveButton(R.string.color_picker_yes, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorPickerPreviewActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ColorPickerPreviewActivity.m5902showSaveChangesDialog$lambda10(this.f$0, dialogInterface, i);
            }
        }).create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showSaveChangesDialog$lambda-9, reason: not valid java name */
    public static final void m5903showSaveChangesDialog$lambda9(ColorPickerPreviewActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialogInterface, "dialogInterface");
        dialogInterface.dismiss();
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showSaveChangesDialog$lambda-10, reason: not valid java name */
    public static final void m5902showSaveChangesDialog$lambda10(ColorPickerPreviewActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialogInterface, "dialogInterface");
        dialogInterface.dismiss();
        this$0.saveAndFinish();
    }

    @Override // org.catrobat.paintroid.colorpicker.OnImageViewPointClickedListener
    public void colorChanged(int color) {
        setCurrentColor(color);
    }
}
