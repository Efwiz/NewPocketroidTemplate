package org.catrobat.paintroid.ui.zoomwindow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.UserPreferences;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: DefaultZoomWindowController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\u0018\u0000 D2\u00020\u0001:\u0002DEB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0012\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u001c\u0010.\u001a\u0004\u0018\u00010\u00102\b\u0010/\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u000201H\u0016J\n\u00103\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u00104\u001a\u00020'H\u0002J\u0018\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u000208H\u0002J\u0014\u0010:\u001a\u0004\u0018\u00010\u00102\b\u0010/\u001a\u0004\u0018\u00010\u0010H\u0002J\u0018\u0010;\u001a\u0002012\u0006\u0010<\u001a\u00020\u00182\u0006\u0010=\u001a\u00020\u0018H\u0016J\u0012\u0010>\u001a\u0002012\b\u0010/\u001a\u0004\u0018\u00010\u0010H\u0016J\u0010\u0010?\u001a\u0002012\u0006\u0010@\u001a\u000206H\u0002J\u0010\u0010A\u001a\u0002012\u0006\u0010=\u001a\u00020\u0018H\u0002J\u0010\u0010B\u001a\u0002062\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010C\u001a\u0002012\u0006\u0010<\u001a\u00020\u00182\u0006\u0010=\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController;", "Lorg/catrobat/paintroid/ui/zoomwindow/ZoomWindowController;", "activity", "Lorg/catrobat/paintroid/MainActivity;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "(Lorg/catrobat/paintroid/MainActivity;Lorg/catrobat/paintroid/contract/LayerContracts$Model;Lorg/catrobat/paintroid/tools/Workspace;Lorg/catrobat/paintroid/tools/ToolReference;Lorg/catrobat/paintroid/UserPreferences;)V", "getActivity", "()Lorg/catrobat/paintroid/MainActivity;", "backgroundBitmap", "Landroid/graphics/Bitmap;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "canvasRect", "Landroid/graphics/Rect;", "checkeredPattern", "Landroid/graphics/Paint;", "chequeredBackgroundBitmap", "coordinates", "Landroid/graphics/PointF;", "framePaint", "greyBackgroundBitmap", "getLayerModel", "()Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "getSharedPreferences", "()Lorg/catrobat/paintroid/UserPreferences;", "getToolReference", "()Lorg/catrobat/paintroid/tools/ToolReference;", "getWorkspace", "()Lorg/catrobat/paintroid/tools/Workspace;", "zoomWindow", "Landroid/widget/RelativeLayout;", "zoomWindowBitmap", "zoomWindowDiameter", "", "zoomWindowImage", "Landroid/widget/ImageView;", "checkIfToolCompatibleWithZoomWindow", "Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController$Constants;", "tool", "Lorg/catrobat/paintroid/tools/Tool;", "cropBitmap", "bitmap", "dismiss", "", "dismissOnPinch", "getBitmap", "getSizeOfZoomWindow", "isPointOnCanvas", "", "pointX", "", "pointY", "mergeBackground", "onMove", "drawingSurfaceCoordinates", "displayCoordinates", "setBitmap", "setLayoutAlignment", "right", "setZoomWindowPosition", "shouldBeInTheRight", "show", "Companion", "Constants", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultZoomWindowController implements ZoomWindowController {
    public static final int initialZoomValue = 100;
    public static final int zoomFactor = 25;
    public static final int zoomPercentStepValue = 50;
    private final MainActivity activity;
    private final Bitmap backgroundBitmap;
    private final Rect canvasRect;
    private final Paint checkeredPattern;
    private final Bitmap chequeredBackgroundBitmap;
    private PointF coordinates;
    private final Paint framePaint;
    private final Bitmap greyBackgroundBitmap;
    private final LayerContracts.Model layerModel;
    private final UserPreferences sharedPreferences;
    private final ToolReference toolReference;
    private final Workspace workspace;
    private final RelativeLayout zoomWindow;
    private Bitmap zoomWindowBitmap;
    private final int zoomWindowDiameter;
    private final ImageView zoomWindowImage;

    /* JADX INFO: compiled from: DefaultZoomWindowController.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController$Constants;", "", "(Ljava/lang/String;I)V", "NOT_COMPATIBLE", "COMPATIBLE", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum Constants {
        NOT_COMPATIBLE,
        COMPATIBLE
    }

    public DefaultZoomWindowController(MainActivity activity, LayerContracts.Model layerModel, Workspace workspace, ToolReference toolReference, UserPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(toolReference, "toolReference");
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        this.activity = activity;
        this.layerModel = layerModel;
        this.workspace = workspace;
        this.toolReference = toolReference;
        this.sharedPreferences = sharedPreferences;
        Rect rect = new Rect();
        this.canvasRect = rect;
        Paint paint = new Paint();
        this.checkeredPattern = paint;
        Paint paint2 = new Paint();
        this.framePaint = paint2;
        int dimensionPixelSize = activity.getResources().getDimensionPixelSize(R.dimen.pocketpaint_zoom_window_diameter);
        this.zoomWindowDiameter = dimensionPixelSize;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(layerModel.getWidth(), layerModel.getHeight(), Bitmap.Config.ARGB_8888);
        this.chequeredBackgroundBitmap = bitmapCreateBitmap;
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(layerModel.getWidth() + dimensionPixelSize, layerModel.getHeight() + dimensionPixelSize, Bitmap.Config.ARGB_8888);
        this.greyBackgroundBitmap = bitmapCreateBitmap2;
        Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(layerModel.getWidth() + dimensionPixelSize, layerModel.getHeight() + dimensionPixelSize, Bitmap.Config.ARGB_8888);
        this.backgroundBitmap = bitmapCreateBitmap3;
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(activity.getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        Canvas canvas = bitmapCreateBitmap == null ? null : new Canvas(bitmapCreateBitmap);
        rect.set(0, 0, layerModel.getWidth(), layerModel.getHeight());
        if (canvas != null) {
            canvas.drawRect(rect, paint);
        }
        if (canvas != null) {
            canvas.drawRect(rect, paint2);
        }
        new Canvas(bitmapCreateBitmap2).drawColor(activity.getResources().getColor(R.color.pocketpaint_main_drawing_surface_background));
        Canvas canvas2 = new Canvas(bitmapCreateBitmap3);
        canvas2.drawBitmap(bitmapCreateBitmap2, new Matrix(), null);
        canvas2.drawBitmap(bitmapCreateBitmap, dimensionPixelSize / 2.0f, dimensionPixelSize / 2.0f, (Paint) null);
        View viewFindViewById = activity.findViewById(R.id.pocketpaint_zoom_window);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "activity.findViewById(R.….pocketpaint_zoom_window)");
        this.zoomWindow = (RelativeLayout) viewFindViewById;
        View viewFindViewById2 = activity.findViewById(R.id.pocketpaint_zoom_window_image);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "activity.findViewById(R.…tpaint_zoom_window_image)");
        this.zoomWindowImage = (ImageView) viewFindViewById2;
    }

    public final MainActivity getActivity() {
        return this.activity;
    }

    public final LayerContracts.Model getLayerModel() {
        return this.layerModel;
    }

    public final Workspace getWorkspace() {
        return this.workspace;
    }

    public final ToolReference getToolReference() {
        return this.toolReference;
    }

    public final UserPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    public void show(PointF drawingSurfaceCoordinates, PointF displayCoordinates) {
        Intrinsics.checkNotNullParameter(drawingSurfaceCoordinates, "drawingSurfaceCoordinates");
        Intrinsics.checkNotNullParameter(displayCoordinates, "displayCoordinates");
        if (checkIfToolCompatibleWithZoomWindow(this.toolReference.getTool()) == Constants.COMPATIBLE && isPointOnCanvas(drawingSurfaceCoordinates.x, drawingSurfaceCoordinates.y)) {
            setZoomWindowPosition(displayCoordinates);
            this.zoomWindow.setVisibility(0);
            this.zoomWindowImage.setImageBitmap(cropBitmap(this.workspace.getBitmapOfAllLayers(), drawingSurfaceCoordinates));
        }
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    public void dismiss() {
        this.zoomWindow.setVisibility(8);
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    public void dismissOnPinch() {
        this.zoomWindow.setVisibility(8);
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    public void onMove(PointF drawingSurfaceCoordinates, PointF displayCoordinates) {
        Intrinsics.checkNotNullParameter(drawingSurfaceCoordinates, "drawingSurfaceCoordinates");
        Intrinsics.checkNotNullParameter(displayCoordinates, "displayCoordinates");
        if (checkIfToolCompatibleWithZoomWindow(this.toolReference.getTool()) == Constants.COMPATIBLE) {
            setZoomWindowPosition(displayCoordinates);
            if (isPointOnCanvas(drawingSurfaceCoordinates.x, drawingSurfaceCoordinates.y)) {
                if (this.zoomWindow.getVisibility() == 8) {
                    this.zoomWindow.setVisibility(0);
                }
                this.coordinates = drawingSurfaceCoordinates;
                return;
            }
            dismiss();
        }
    }

    private final void setZoomWindowPosition(PointF displayCoordinates) {
        if (shouldBeInTheRight(displayCoordinates)) {
            setLayoutAlignment(true);
        } else {
            setLayoutAlignment(false);
        }
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    public void setBitmap(Bitmap bitmap) {
        ImageView imageView = this.zoomWindowImage;
        PointF pointF = this.coordinates;
        imageView.setImageBitmap(pointF == null ? null : cropBitmap(bitmap, pointF));
        this.zoomWindowBitmap = bitmap;
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    /* JADX INFO: renamed from: getBitmap, reason: from getter */
    public Bitmap getZoomWindowBitmap() {
        return this.zoomWindowBitmap;
    }

    private final boolean isPointOnCanvas(float pointX, float pointY) {
        return pointX > 0.0f && pointX < ((float) this.layerModel.getWidth()) && pointY > 0.0f && pointY < ((float) this.layerModel.getHeight());
    }

    private final boolean shouldBeInTheRight(PointF coordinates) {
        return coordinates.x < ((float) (this.activity.getResources().getDisplayMetrics().widthPixels / 2)) && coordinates.y < ((float) (this.activity.getResources().getDisplayMetrics().heightPixels / 2));
    }

    private final void setLayoutAlignment(boolean right) {
        ViewGroup.LayoutParams layoutParams = this.zoomWindowImage.getLayoutParams();
        if (layoutParams == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        }
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        if (right) {
            layoutParams2.addRule(11);
            layoutParams2.removeRule(9);
        } else {
            layoutParams2.addRule(9);
            layoutParams2.removeRule(11);
        }
        this.zoomWindowImage.setLayoutParams(layoutParams2);
    }

    private final Bitmap cropBitmap(Bitmap bitmap, PointF coordinates) {
        Bitmap bitmapMergeBackground = mergeBackground(bitmap);
        int iRoundToInt = (MathKt.roundToInt(coordinates.x) + (this.zoomWindowDiameter / 2)) - (getSizeOfZoomWindow() / 2);
        int iRoundToInt2 = (MathKt.roundToInt(coordinates.y) + (this.zoomWindowDiameter / 2)) - (getSizeOfZoomWindow() / 2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(getSizeOfZoomWindow(), getSizeOfZoomWindow(), Bitmap.Config.ARGB_8888);
        Canvas canvas = bitmapCreateBitmap == null ? null : new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, getSizeOfZoomWindow(), getSizeOfZoomWindow());
        RectF rectF = new RectF(rect);
        if (canvas != null) {
            canvas.drawOval(rectF, paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if (bitmapMergeBackground != null && canvas != null) {
            canvas.drawBitmap(bitmapMergeBackground, new Rect(iRoundToInt, iRoundToInt2, getSizeOfZoomWindow() + iRoundToInt, getSizeOfZoomWindow() + iRoundToInt2), rect, paint);
        }
        return bitmapCreateBitmap;
    }

    private final Bitmap mergeBackground(Bitmap bitmap) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.layerModel.getWidth() + this.zoomWindowDiameter, this.layerModel.getHeight() + this.zoomWindowDiameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(this.backgroundBitmap, new Matrix(), null);
        if (bitmap != null) {
            int i = this.zoomWindowDiameter;
            canvas.drawBitmap(bitmap, i / 2.0f, i / 2.0f, (Paint) null);
        }
        return bitmapCreateBitmap;
    }

    private final int getSizeOfZoomWindow() {
        return this.zoomWindowDiameter - (((this.sharedPreferences.getPreferenceZoomWindowZoomPercentage() - 100) / 50) * 25);
    }

    @Override // org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController
    public Constants checkIfToolCompatibleWithZoomWindow(Tool tool) {
        ToolType toolType;
        String strName = null;
        if (tool != null && (toolType = tool.getToolType()) != null) {
            strName = toolType.name();
        }
        return Intrinsics.areEqual(strName, ToolType.LINE.name()) ? true : Intrinsics.areEqual(strName, ToolType.CURSOR.name()) ? true : Intrinsics.areEqual(strName, ToolType.WATERCOLOR.name()) ? true : Intrinsics.areEqual(strName, ToolType.SPRAY.name()) ? true : Intrinsics.areEqual(strName, ToolType.BRUSH.name()) ? true : Intrinsics.areEqual(strName, ToolType.ERASER.name()) ? true : Intrinsics.areEqual(strName, ToolType.PIPETTE.name()) ? true : Intrinsics.areEqual(strName, ToolType.CLIP.name()) ? true : Intrinsics.areEqual(strName, ToolType.SMUDGE.name()) ? Constants.COMPATIBLE : Constants.NOT_COMPATIBLE;
    }
}
