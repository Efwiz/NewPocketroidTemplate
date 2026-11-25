package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: ZoomableImageView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0001OB\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0019\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tB!\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010)\u001a\u00020*J\b\u0010+\u001a\u00020*H\u0002J \u0010,\u001a\u00020!2\u0006\u0010-\u001a\u00020!2\u0006\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u00020!H\u0002J \u00100\u001a\u00020!2\u0006\u00101\u001a\u00020!2\u0006\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u00020!H\u0002J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u00106\u001a\u00020*2\u0006\u00107\u001a\u000208H\u0014J(\u00109\u001a\u0002032\u0006\u0010:\u001a\u0002052\u0006\u0010;\u001a\u0002052\u0006\u0010<\u001a\u00020!2\u0006\u0010=\u001a\u00020!H\u0016J\u0010\u0010>\u001a\u00020*2\u0006\u00104\u001a\u000205H\u0016J\u0018\u0010?\u001a\u00020*2\u0006\u0010@\u001a\u00020\u000b2\u0006\u0010A\u001a\u00020\u000bH\u0014J(\u0010B\u001a\u0002032\u0006\u0010:\u001a\u0002052\u0006\u0010;\u001a\u0002052\u0006\u0010<\u001a\u00020!2\u0006\u0010=\u001a\u00020!H\u0016J\u0010\u0010C\u001a\u00020*2\u0006\u00104\u001a\u000205H\u0016J\u0010\u0010D\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u001a\u0010E\u001a\u0002032\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010H\u001a\u000205H\u0016J\u0010\u0010I\u001a\u00020*2\u0006\u0010J\u001a\u00020\u0016H\u0002J\u0010\u0010K\u001a\u00020*2\u0006\u0010L\u001a\u00020\u001aH\u0016J\u000e\u0010M\u001a\u00020*2\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010N\u001a\u00020*H\u0002R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006P"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ZoomableImageView;", "Landroidx/appcompat/widget/AppCompatImageView;", "Landroid/view/View$OnTouchListener;", "Landroid/view/GestureDetector$OnGestureListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "backgroundSurfaceColor", "boarderPaint", "Landroid/graphics/Paint;", "canvasRect", "Landroid/graphics/Rect;", "checkeredPattern", "gestureDetector", "Landroid/view/GestureDetector;", "lastPoint", "Landroid/graphics/PointF;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/paintroid/colorpicker/OnImageViewPointClickedListener;", "mBitmap", "Landroid/graphics/Bitmap;", "mMatrix", "Landroid/graphics/Matrix;", "matrixValues", "", "mode", "origHeight", "", "origWidth", "saveScale", "scaleDetector", "Landroid/view/ScaleGestureDetector;", "startPoint", "viewHeight", "viewWidth", "correctTranslation", "", "fitToScreen", "getCorrectTranslation", "trans", "viewSize", "contentSize", "getFixDragTrans", "delta", "onDown", "", "motionEvent", "Landroid/view/MotionEvent;", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onFling", "mE", "mE1", RegisterSpec.PREFIX, "v1", "onLongPress", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onScroll", "onShowPress", "onSingleTapUp", "onTouch", Promotion.ACTION_VIEW, "Landroid/view/View;", "event", "performColorColorChanged", KHRLightsPunctual.GLTFLight.TYPE_POINT, "setImageBitmap", "bitmap", "setListener", "updateLP", "ScaleListener", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ZoomableImageView extends AppCompatImageView implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private int backgroundSurfaceColor;
    private final Paint boarderPaint;
    private final Rect canvasRect;
    private final Paint checkeredPattern;
    private GestureDetector gestureDetector;
    private PointF lastPoint;
    private OnImageViewPointClickedListener listener;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private float[] matrixValues;
    private int mode;
    private float origHeight;
    private float origWidth;
    private float saveScale;
    private ScaleGestureDetector scaleDetector;
    private PointF startPoint;
    private float viewHeight;
    private float viewWidth;

    private final float getCorrectTranslation(float trans, float viewSize, float contentSize) {
        float f;
        float f2;
        if (contentSize <= viewSize) {
            f2 = viewSize - contentSize;
            f = 0.0f;
        } else {
            f = viewSize - contentSize;
            f2 = 0.0f;
        }
        if (trans < f) {
            return (-trans) + f;
        }
        if (trans > f2) {
            return (-trans) + f2;
        }
        return 0.0f;
    }

    private final float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0.0f;
        }
        return delta;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent mE, MotionEvent mE1, float v, float v1) {
        Intrinsics.checkNotNullParameter(mE, "mE");
        Intrinsics.checkNotNullParameter(mE1, "mE1");
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent mE, MotionEvent mE1, float v, float v1) {
        Intrinsics.checkNotNullParameter(mE, "mE");
        Intrinsics.checkNotNullParameter(mE1, "mE1");
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZoomableImageView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.canvasRect = new Rect();
        Paint paint = new Paint();
        this.boarderPaint = paint;
        Paint paint2 = new Paint();
        this.checkeredPattern = paint2;
        this.mMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.saveScale = 1.0f;
        this.lastPoint = new PointF();
        this.startPoint = new PointF();
        setVisibility(8);
        super.setClickable(true);
        this.scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener(this));
        this.matrixValues = new float[9];
        setImageMatrix(this.mMatrix);
        setScaleType(ImageView.ScaleType.MATRIX);
        this.gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
        this.backgroundSurfaceColor = ContextCompat.getColor(getContext(), R.color.pocketpaint_color_picker_surface_background);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint2.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        postDelayed(new Runnable() { // from class: org.catrobat.paintroid.colorpicker.ZoomableImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ZoomableImageView.m5910_init_$lambda0(this.f$0);
            }
        }, 150L);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZoomableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.canvasRect = new Rect();
        Paint paint = new Paint();
        this.boarderPaint = paint;
        Paint paint2 = new Paint();
        this.checkeredPattern = paint2;
        this.mMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.saveScale = 1.0f;
        this.lastPoint = new PointF();
        this.startPoint = new PointF();
        setVisibility(8);
        super.setClickable(true);
        this.scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener(this));
        this.matrixValues = new float[9];
        setImageMatrix(this.mMatrix);
        setScaleType(ImageView.ScaleType.MATRIX);
        this.gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
        this.backgroundSurfaceColor = ContextCompat.getColor(getContext(), R.color.pocketpaint_color_picker_surface_background);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint2.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        postDelayed(new Runnable() { // from class: org.catrobat.paintroid.colorpicker.ZoomableImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ZoomableImageView.m5910_init_$lambda0(this.f$0);
            }
        }, 150L);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZoomableImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.canvasRect = new Rect();
        Paint paint = new Paint();
        this.boarderPaint = paint;
        Paint paint2 = new Paint();
        this.checkeredPattern = paint2;
        this.mMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.saveScale = 1.0f;
        this.lastPoint = new PointF();
        this.startPoint = new PointF();
        setVisibility(8);
        super.setClickable(true);
        this.scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener(this));
        this.matrixValues = new float[9];
        setImageMatrix(this.mMatrix);
        setScaleType(ImageView.ScaleType.MATRIX);
        this.gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
        this.backgroundSurfaceColor = ContextCompat.getColor(getContext(), R.color.pocketpaint_color_picker_surface_background);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint2.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        postDelayed(new Runnable() { // from class: org.catrobat.paintroid.colorpicker.ZoomableImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ZoomableImageView.m5910_init_$lambda0(this.f$0);
            }
        }, 150L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
    public static final void m5910_init_$lambda0(ZoomableImageView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setVisibility(0);
    }

    public final void setListener(OnImageViewPointClickedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.mBitmap = bitmap;
        invalidate();
        updateLP();
        super.setImageBitmap(bitmap);
    }

    /* JADX INFO: compiled from: ZoomableImageView.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ZoomableImageView$ScaleListener;", "Landroid/view/ScaleGestureDetector$SimpleOnScaleGestureListener;", "(Lorg/catrobat/paintroid/colorpicker/ZoomableImageView;)V", "onScale", "", "detector", "Landroid/view/ScaleGestureDetector;", "onScaleBegin", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private final class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        final /* synthetic */ ZoomableImageView this$0;

        public ScaleListener(ZoomableImageView this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            this.this$0.mode = 2;
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x0080  */
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            float scaleFactor = detector.getScaleFactor();
            float f = this.this$0.saveScale;
            this.this$0.saveScale *= scaleFactor;
            float f2 = 50.0f;
            if (this.this$0.saveScale > 50.0f) {
                this.this$0.saveScale = 50.0f;
            } else {
                f2 = 0.75f;
                if (this.this$0.saveScale < 0.75f) {
                    this.this$0.saveScale = 0.75f;
                }
                if (this.this$0.origWidth * this.this$0.saveScale <= this.this$0.viewWidth && this.this$0.origHeight * this.this$0.saveScale > this.this$0.viewHeight) {
                    this.this$0.mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                } else {
                    this.this$0.mMatrix.postScale(scaleFactor, scaleFactor, this.this$0.viewWidth / 2.0f, this.this$0.viewHeight / 2.0f);
                }
                this.this$0.correctTranslation();
                return true;
            }
            scaleFactor = f2 / f;
            if (this.this$0.origWidth * this.this$0.saveScale <= this.this$0.viewWidth) {
                this.this$0.mMatrix.postScale(scaleFactor, scaleFactor, this.this$0.viewWidth / 2.0f, this.this$0.viewHeight / 2.0f);
            } else {
                this.this$0.mMatrix.postScale(scaleFactor, scaleFactor, this.this$0.viewWidth / 2.0f, this.this$0.viewHeight / 2.0f);
            }
            this.this$0.correctTranslation();
            return true;
        }
    }

    private final void fitToScreen() {
        if (getDrawable() == null || getDrawable().getIntrinsicWidth() == 0 || getDrawable().getIntrinsicHeight() == 0) {
            return;
        }
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        float fCoerceAtMost = RangesKt.coerceAtMost(this.viewWidth / intrinsicWidth, this.viewHeight / intrinsicHeight);
        this.mMatrix.setScale(fCoerceAtMost, fCoerceAtMost);
        float f = (this.viewHeight - (intrinsicHeight * fCoerceAtMost)) / 2.0f;
        float f2 = (this.viewWidth - (fCoerceAtMost * intrinsicWidth)) / 2.0f;
        this.mMatrix.postTranslate(f2, f);
        float f3 = 2;
        this.origWidth = this.viewWidth - (f2 * f3);
        this.origHeight = this.viewHeight - (f3 * f);
        setImageMatrix(this.mMatrix);
    }

    public final void correctTranslation() {
        this.mMatrix.getValues(this.matrixValues);
        float[] fArr = this.matrixValues;
        float f = fArr[2];
        float f2 = fArr[5];
        float correctTranslation = getCorrectTranslation(f, this.viewWidth, this.origWidth * this.saveScale);
        float correctTranslation2 = getCorrectTranslation(f2, this.viewHeight, this.origHeight * this.saveScale);
        if (correctTranslation == 0.0f && correctTranslation2 == 0.0f) {
            return;
        }
        this.mMatrix.postTranslate(correctTranslation, correctTranslation2);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        this.viewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        if (this.saveScale == 1.0f) {
            fitToScreen();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        canvas.setMatrix(this.mMatrix);
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            Rect rect = this.canvasRect;
            if (bitmap == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBitmap");
                bitmap = null;
            }
            int width = bitmap.getWidth();
            Bitmap bitmap2 = this.mBitmap;
            if (bitmap2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBitmap");
                bitmap2 = null;
            }
            rect.set(0, 0, width, bitmap2.getHeight());
            canvas.save();
            canvas.clipOutRect(this.canvasRect);
            canvas.drawColor(this.backgroundSurfaceColor, PorterDuff.Mode.SRC);
            canvas.restore();
            canvas.drawRect(this.canvasRect, this.checkeredPattern);
            canvas.drawRect(this.canvasRect, this.boarderPaint);
            Bitmap bitmap3 = this.mBitmap;
            if (bitmap3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBitmap");
                bitmap3 = null;
            }
            canvas.drawBitmap(bitmap3, 0.0f, 0.0f, (Paint) null);
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.scaleDetector.onTouchEvent(event);
        this.gestureDetector.onTouchEvent(event);
        PointF pointF = new PointF(event.getX(), event.getY());
        updateLP();
        int action = event.getAction();
        if (action == 0) {
            this.lastPoint.set(pointF);
            this.startPoint.set(this.lastPoint);
            this.mode = 1;
        } else if (action == 1) {
            this.mode = 3;
            int iAbs = (int) Math.abs(event.getX() - this.startPoint.x);
            int iAbs2 = (int) Math.abs(event.getY() - this.startPoint.y);
            if (iAbs < 1 && iAbs2 < 1) {
                performClick();
                performColorColorChanged(new PointF(event.getX(), event.getY()));
            }
        } else if (action == 2) {
            if (this.mode == 2 && this.saveScale > 0.75f) {
                float f = pointF.x - this.lastPoint.x;
                float f2 = pointF.y - this.lastPoint.y;
                this.mMatrix.postTranslate(getFixDragTrans(f, this.viewWidth, this.origWidth * this.saveScale), getFixDragTrans(f2, this.viewHeight, this.origHeight * this.saveScale));
                correctTranslation();
                this.lastPoint.set(pointF.x, pointF.y);
            }
            if (event.getPointerCount() == 1 && this.mode == 1) {
                performColorColorChanged(new PointF(event.getX(), event.getY()));
            }
        } else if (action == 5) {
            this.lastPoint.set(pointF);
            this.startPoint.set(this.lastPoint);
            this.mode = 2;
        } else if (action == 6) {
            this.mode = 0;
        }
        setImageMatrix(this.mMatrix);
        return false;
    }

    private final void updateLP() {
        ZoomableImageView zoomableImageView = this;
        final ViewGroup.LayoutParams layoutParams = zoomableImageView.getLayoutParams();
        if (layoutParams != null) {
            post(new Runnable() { // from class: org.catrobat.paintroid.colorpicker.ZoomableImageView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ZoomableImageView.m5911updateLP$lambda4$lambda3(this.f$0, layoutParams);
                }
            });
            zoomableImageView.setLayoutParams(layoutParams);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: updateLP$lambda-4$lambda-3, reason: not valid java name */
    public static final void m5911updateLP$lambda4$lambda3(ZoomableImageView this$0, ViewGroup.LayoutParams this_updateLayoutParams) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_updateLayoutParams, "$this_updateLayoutParams");
        if (Build.VERSION.SDK_INT >= 30) {
            Object systemService = this$0.getContext().getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
            if (systemService == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
            }
            WindowMetrics currentWindowMetrics = ((WindowManager) systemService).getCurrentWindowMetrics();
            Intrinsics.checkNotNullExpressionValue(currentWindowMetrics, "windowManager.currentWindowMetrics");
            Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            Intrinsics.checkNotNullExpressionValue(insetsIgnoringVisibility, "windowMetrics.windowInse…t()\n                    )");
            int i = insetsIgnoringVisibility.right + insetsIgnoringVisibility.left;
            int i2 = insetsIgnoringVisibility.top + insetsIgnoringVisibility.bottom;
            Rect bounds = currentWindowMetrics.getBounds();
            Intrinsics.checkNotNullExpressionValue(bounds, "windowMetrics.bounds");
            this_updateLayoutParams.width = bounds.width() - i;
            this_updateLayoutParams.height = bounds.height() - i2;
            return;
        }
        Point point = new Point();
        Display display = this$0.getDisplay();
        if (display != null) {
            display.getSize(point);
        }
        this_updateLayoutParams.height = point.y;
        this_updateLayoutParams.width = point.x;
    }

    private final void performColorColorChanged(PointF point) {
        Bitmap bitmap = this.mBitmap;
        OnImageViewPointClickedListener onImageViewPointClickedListener = null;
        if (bitmap == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBitmap");
            bitmap = null;
        }
        Matrix matrix = new Matrix();
        getImageMatrix().invert(matrix);
        float[] fArr = {point.x, point.y};
        matrix.mapPoints(fArr);
        int i = (int) fArr[0];
        int i2 = (int) fArr[1];
        if (i < 0 || i >= bitmap.getWidth() || i2 < 0 || i2 >= bitmap.getHeight()) {
            return;
        }
        int pixel = bitmap.getPixel(i, i2);
        int iArgb = Color.argb(Color.alpha(pixel), Color.red(pixel), Color.green(pixel), Color.blue(pixel));
        OnImageViewPointClickedListener onImageViewPointClickedListener2 = this.listener;
        if (onImageViewPointClickedListener2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        } else {
            onImageViewPointClickedListener = onImageViewPointClickedListener2;
        }
        onImageViewPointClickedListener.colorChanged(iArgb);
    }
}
