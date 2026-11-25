package org.catrobat.paintroid.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.UserPreferences;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.listener.DrawingSurfaceListener;
import org.catrobat.paintroid.model.LayerModel;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.viewholder.DrawerLayoutViewHolder;
import org.catrobat.paintroid.ui.zoomwindow.DefaultZoomWindowController;
import org.catrobat.paintroid.ui.zoomwindow.ZoomWindowController;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: DrawingSurface.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000 C2\u00020\u00012\u00020\u0002:\u0002CDB\u001b\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B\u0011\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\bJ\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0012\u00100\u001a\u0004\u0018\u0001012\u0006\u0010-\u001a\u00020.H\u0002J\u0012\u00102\u001a\u00020,2\b\u00103\u001a\u0004\u0018\u000101H\u0002J\b\u00104\u001a\u00020,H\u0014J\b\u00105\u001a\u00020,H\u0014J\u0010\u00106\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0006\u00107\u001a\u00020,JN\u00108\u001a\u00020,2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010%\u001a\u00020&2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010)\u001a\u00020*2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u00109\u001a\u00020,2\b\u0010:\u001a\u0004\u0018\u000101J(\u0010;\u001a\u00020,2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\n2\u0006\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020\nH\u0016J\u0010\u0010A\u001a\u00020,2\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010B\u001a\u00020,2\u0006\u0010<\u001a\u00020=H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082.¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lorg/catrobat/paintroid/ui/DrawingSurface;", "Landroid/view/SurfaceView;", "Landroid/view/SurfaceHolder$Callback;", "context", "Landroid/content/Context;", "attrSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "bgColor", "", "canvasRect", "Landroid/graphics/Rect;", "checkeredPattern", "Landroid/graphics/Paint;", "drawerLayoutViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/DrawerLayoutViewHolder;", "drawingSurfaceListener", "Lorg/catrobat/paintroid/listener/DrawingSurfaceListener;", "drawingThread", "Lorg/catrobat/paintroid/ui/DrawingSurfaceThread;", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "framePaint", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "perspective", "Lorg/catrobat/paintroid/ui/Perspective;", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "surfaceDirty", "", "surfaceLock", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "surfaceReady", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "zoomController", "Lorg/catrobat/paintroid/ui/zoomwindow/ZoomWindowController;", "doDraw", "", "surfaceViewCanvas", "Landroid/graphics/Canvas;", "drawBackground", "drawLayers", "Landroid/graphics/Bitmap;", "handleZoomCompatibility", "currentBitmap", "onAttachedToWindow", "onDetachedFromWindow", "prepareCanvas", "refreshDrawingSurface", "setArguments", "setBitmap", "bitmap", "surfaceChanged", "holder", "Landroid/view/SurfaceHolder;", ConfigConstants.CONFIG_KEY_FORMAT, "width", "height", "surfaceCreated", "surfaceDestroyed", "Companion", "DrawLoop", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = DrawingSurface.class.getName();
    private int bgColor;
    private final Rect canvasRect;
    private final Paint checkeredPattern;
    private DrawerLayoutViewHolder drawerLayoutViewHolder;
    private DrawingSurfaceListener drawingSurfaceListener;
    private DrawingSurfaceThread drawingThread;
    private FragmentManager fragmentManager;
    private final Paint framePaint;
    private CountingIdlingResource idlingResource;
    private LayerContracts.Model layerModel;
    private Perspective perspective;
    private UserPreferences sharedPreferences;
    private boolean surfaceDirty;
    private Object surfaceLock;
    private boolean surfaceReady;
    private ToolOptionsViewController toolOptionsViewController;
    private ToolReference toolReference;
    private ZoomWindowController zoomController;

    public DrawingSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.canvasRect = new Rect();
        Paint paint = new Paint();
        this.framePaint = paint;
        Paint paint2 = new Paint();
        this.checkeredPattern = paint2;
        this.surfaceLock = new Object();
        this.bgColor = ContextCompat.getColor(getContext(), R.color.pocketpaint_main_drawing_surface_background);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint2.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        DrawingSurfaceListener drawingSurfaceListener = new DrawingSurfaceListener(new DrawingSurfaceListener.DrawingSurfaceListenerCallback() { // from class: org.catrobat.paintroid.ui.DrawingSurface$callback$1
            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public Tool getCurrentTool() {
                ToolReference toolReference = this.this$0.toolReference;
                if (toolReference == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolReference");
                    toolReference = null;
                }
                return toolReference.getTool();
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public void multiplyPerspectiveScale(float factor) {
                Perspective perspective = this.this$0.perspective;
                if (perspective == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("perspective");
                    perspective = null;
                }
                perspective.multiplyScale(factor);
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public void translatePerspective(float x, float y) {
                Perspective perspective = this.this$0.perspective;
                if (perspective == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("perspective");
                    perspective = null;
                }
                perspective.translate(x, y);
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public void convertToCanvasFromSurface(PointF surfacePoint) {
                Intrinsics.checkNotNullParameter(surfacePoint, "surfacePoint");
                Perspective perspective = this.this$0.perspective;
                if (perspective == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("perspective");
                    perspective = null;
                }
                perspective.convertToCanvasFromSurface(surfacePoint);
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public ToolOptionsViewController getToolOptionsViewController() {
                ToolOptionsViewController toolOptionsViewController = this.this$0.toolOptionsViewController;
                if (toolOptionsViewController != null) {
                    return toolOptionsViewController;
                }
                Intrinsics.throwUninitializedPropertyAccessException("toolOptionsViewController");
                return null;
            }
        }, getResources().getDisplayMetrics().density);
        this.drawingSurfaceListener = drawingSurfaceListener;
        setOnTouchListener(drawingSurfaceListener);
    }

    public DrawingSurface(Context context) {
        super(context);
        this.canvasRect = new Rect();
        Paint paint = new Paint();
        this.framePaint = paint;
        Paint paint2 = new Paint();
        this.checkeredPattern = paint2;
        this.surfaceLock = new Object();
        this.bgColor = ContextCompat.getColor(getContext(), R.color.pocketpaint_main_drawing_surface_background);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint2.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        DrawingSurfaceListener drawingSurfaceListener = new DrawingSurfaceListener(new DrawingSurfaceListener.DrawingSurfaceListenerCallback() { // from class: org.catrobat.paintroid.ui.DrawingSurface$callback$1
            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public Tool getCurrentTool() {
                ToolReference toolReference = this.this$0.toolReference;
                if (toolReference == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolReference");
                    toolReference = null;
                }
                return toolReference.getTool();
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public void multiplyPerspectiveScale(float factor) {
                Perspective perspective = this.this$0.perspective;
                if (perspective == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("perspective");
                    perspective = null;
                }
                perspective.multiplyScale(factor);
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public void translatePerspective(float x, float y) {
                Perspective perspective = this.this$0.perspective;
                if (perspective == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("perspective");
                    perspective = null;
                }
                perspective.translate(x, y);
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public void convertToCanvasFromSurface(PointF surfacePoint) {
                Intrinsics.checkNotNullParameter(surfacePoint, "surfacePoint");
                Perspective perspective = this.this$0.perspective;
                if (perspective == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("perspective");
                    perspective = null;
                }
                perspective.convertToCanvasFromSurface(surfacePoint);
            }

            @Override // org.catrobat.paintroid.listener.DrawingSurfaceListener.DrawingSurfaceListenerCallback
            public ToolOptionsViewController getToolOptionsViewController() {
                ToolOptionsViewController toolOptionsViewController = this.this$0.toolOptionsViewController;
                if (toolOptionsViewController != null) {
                    return toolOptionsViewController;
                }
                Intrinsics.throwUninitializedPropertyAccessException("toolOptionsViewController");
                return null;
            }
        }, getResources().getDisplayMetrics().density);
        this.drawingSurfaceListener = drawingSurfaceListener;
        setOnTouchListener(drawingSurfaceListener);
    }

    public final void setArguments(LayerContracts.Model layerModel, Perspective perspective, ToolReference toolReference, CountingIdlingResource idlingResource, FragmentManager fragmentManager, ToolOptionsViewController toolOptionsViewController, DrawerLayoutViewHolder drawerLayoutViewHolder, ZoomWindowController zoomController, UserPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Intrinsics.checkNotNullParameter(perspective, "perspective");
        Intrinsics.checkNotNullParameter(toolReference, "toolReference");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(fragmentManager, "fragmentManager");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(drawerLayoutViewHolder, "drawerLayoutViewHolder");
        Intrinsics.checkNotNullParameter(zoomController, "zoomController");
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        this.layerModel = layerModel;
        this.perspective = perspective;
        this.toolReference = toolReference;
        this.toolOptionsViewController = toolOptionsViewController;
        this.idlingResource = idlingResource;
        this.fragmentManager = fragmentManager;
        this.drawerLayoutViewHolder = drawerLayoutViewHolder;
        this.zoomController = zoomController;
        this.drawingSurfaceListener.setZoomController(zoomController, sharedPreferences);
        this.sharedPreferences = sharedPreferences;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void doDraw(Canvas surfaceViewCanvas) {
        LayerContracts.Model model = this.layerModel;
        if (model == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model = null;
        }
        synchronized (model) {
            if (this.surfaceReady) {
                prepareCanvas(surfaceViewCanvas);
                drawBackground(surfaceViewCanvas);
                handleZoomCompatibility(drawLayers(surfaceViewCanvas));
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void prepareCanvas(Canvas surfaceViewCanvas) {
        Rect rect = this.canvasRect;
        LayerContracts.Model model = this.layerModel;
        Perspective perspective = null;
        if (model == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model = null;
        }
        int width = model.getWidth();
        LayerContracts.Model model2 = this.layerModel;
        if (model2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model2 = null;
        }
        rect.set(0, 0, width, model2.getHeight());
        Perspective perspective2 = this.perspective;
        if (perspective2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("perspective");
        } else {
            perspective = perspective2;
        }
        perspective.applyToCanvas(surfaceViewCanvas);
    }

    private final void drawBackground(Canvas surfaceViewCanvas) {
        surfaceViewCanvas.save();
        surfaceViewCanvas.clipOutRect(this.canvasRect);
        surfaceViewCanvas.drawColor(this.bgColor, PorterDuff.Mode.SRC);
        surfaceViewCanvas.restore();
        surfaceViewCanvas.drawRect(this.canvasRect, this.checkeredPattern);
        surfaceViewCanvas.drawRect(this.canvasRect, this.framePaint);
    }

    private final Bitmap drawLayers(Canvas surfaceViewCanvas) {
        Integer numValueOf;
        ToolType toolType;
        ToolReference toolReference = this.toolReference;
        LayerContracts.Model model = null;
        if (toolReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolReference");
            toolReference = null;
        }
        Tool tool = toolReference.getTool();
        LayerContracts.Model model2 = this.layerModel;
        if (model2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model2 = null;
        }
        int width = model2.getWidth();
        LayerContracts.Model model3 = this.layerModel;
        if (model3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model3 = null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, model3.getHeight(), Bitmap.Config.ARGB_8888);
        LayerContracts.Model model4 = this.layerModel;
        if (model4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model4 = null;
        }
        LayerContracts.Layer currentLayer = model4.getCurrentLayer();
        if (currentLayer == null) {
            numValueOf = null;
        } else {
            LayerContracts.Model model5 = this.layerModel;
            if (model5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layerModel");
                model5 = null;
            }
            numValueOf = Integer.valueOf(model5.getLayerIndexOf(currentLayer));
        }
        if (StringsKt.equals$default((tool == null || (toolType = tool.getToolType()) == null) ? null : toolType.name(), ToolType.ERASER.name(), false, 2, null)) {
            LayerContracts.Model model6 = this.layerModel;
            if (model6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layerModel");
                model6 = null;
            }
            ((LayerModel) model6).drawLayersOntoCanvas(surfaceViewCanvas);
            surfaceViewCanvas.setBitmap(bitmapCreateBitmap);
            LayerContracts.Model model7 = this.layerModel;
            if (model7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            } else {
                model = model7;
            }
            ((LayerModel) model).drawLayersOntoCanvasCorrectOrderEraser(surfaceViewCanvas, numValueOf, tool);
        } else {
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            LayerContracts.Model model8 = this.layerModel;
            if (model8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            } else {
                model = model8;
            }
            ((LayerModel) model).drawLayersOntoCanvasCorrectOrder(surfaceViewCanvas, numValueOf, canvas, tool);
        }
        return bitmapCreateBitmap;
    }

    private final void handleZoomCompatibility(final Bitmap currentBitmap) {
        ToolReference toolReference = this.toolReference;
        ZoomWindowController zoomWindowController = null;
        if (toolReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolReference");
            toolReference = null;
        }
        Tool tool = toolReference.getTool();
        ZoomWindowController zoomWindowController2 = this.zoomController;
        if (zoomWindowController2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("zoomController");
        } else {
            zoomWindowController = zoomWindowController2;
        }
        if (zoomWindowController.checkIfToolCompatibleWithZoomWindow(tool) == DefaultZoomWindowController.Constants.COMPATIBLE) {
            getHandler().post(new Runnable() { // from class: org.catrobat.paintroid.ui.DrawingSurface$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DrawingSurface.m5972handleZoomCompatibility$lambda2(this.f$0, currentBitmap);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleZoomCompatibility$lambda-2, reason: not valid java name */
    public static final void m5972handleZoomCompatibility$lambda2(DrawingSurface this$0, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ZoomWindowController zoomWindowController = this$0.zoomController;
        if (zoomWindowController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("zoomController");
            zoomWindowController = null;
        }
        zoomWindowController.setBitmap(bitmap);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getHolder().addCallback(this);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getHolder().removeCallback(this);
    }

    public final void refreshDrawingSurface() {
        Object obj = this.surfaceLock;
        if (obj == null) {
            return;
        }
        synchronized (obj) {
            this.surfaceDirty = true;
            obj.notify();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final synchronized void setBitmap(Bitmap bitmap) {
        LayerContracts.Model model = this.layerModel;
        if (model == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerModel");
            model = null;
        }
        LayerContracts.Layer currentLayer = model.getCurrentLayer();
        if (currentLayer != null) {
            Intrinsics.checkNotNull(bitmap);
            currentLayer.setBitmap(bitmap);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.surfaceReady = true;
        Perspective perspective = this.perspective;
        Perspective perspective2 = null;
        if (perspective == null) {
            Intrinsics.throwUninitializedPropertyAccessException("perspective");
            perspective = null;
        }
        if (perspective.getCallResetScaleAndTransformationOnStartUp() != 2) {
            Perspective perspective3 = this.perspective;
            if (perspective3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("perspective");
                perspective3 = null;
            }
            perspective3.resetScaleAndTranslation();
            Perspective perspective4 = this.perspective;
            if (perspective4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("perspective");
                perspective4 = null;
            }
            perspective4.setCallResetScaleAndTransformationOnStartUp(perspective4.getCallResetScaleAndTransformationOnStartUp() + 1);
        }
        Perspective perspective5 = this.perspective;
        if (perspective5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("perspective");
        } else {
            perspective2 = perspective5;
        }
        Rect surfaceFrame = holder.getSurfaceFrame();
        Intrinsics.checkNotNullExpressionValue(surfaceFrame, "holder.surfaceFrame");
        perspective2.setSurfaceFrame(surfaceFrame);
        DrawingSurfaceThread drawingSurfaceThread = this.drawingThread;
        if (drawingSurfaceThread != null) {
            drawingSurfaceThread.start();
        }
        refreshDrawingSurface();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.setFormat(1);
        DrawingSurfaceThread drawingSurfaceThread = this.drawingThread;
        if (drawingSurfaceThread != null) {
            drawingSurfaceThread.stop();
        }
        this.drawingThread = new DrawingSurfaceThread(this, new DrawLoop(this));
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.surfaceReady = false;
        DrawingSurfaceThread drawingSurfaceThread = this.drawingThread;
        if (drawingSurfaceThread == null) {
            return;
        }
        drawingSurfaceThread.stop();
    }

    /* JADX INFO: compiled from: DrawingSurface.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/ui/DrawingSurface$DrawLoop;", "Ljava/lang/Runnable;", "(Lorg/catrobat/paintroid/ui/DrawingSurface;)V", "holder", "Landroid/view/SurfaceHolder;", "getHolder", "()Landroid/view/SurfaceHolder;", "run", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private final class DrawLoop implements Runnable {
        private final SurfaceHolder holder;
        final /* synthetic */ DrawingSurface this$0;

        public DrawLoop(DrawingSurface this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
            SurfaceHolder holder = this$0.getHolder();
            Intrinsics.checkNotNullExpressionValue(holder, "getHolder()");
            this.holder = holder;
        }

        public final SurfaceHolder getHolder() {
            return this.holder;
        }

        /* JADX WARN: Code duplicated, block: B:64:0x00ac A[Catch: all -> 0x00c1, TryCatch #0 {all -> 0x00c1, blocks: (B:33:0x0050, B:35:0x0056, B:37:0x005d, B:40:0x0063, B:62:0x00a6, B:64:0x00ac, B:66:0x00b3, B:70:0x00c0, B:69:0x00b9, B:51:0x0089, B:53:0x008f, B:55:0x0096, B:58:0x009c), top: B:77:0x0033 }] */
        /* JADX WARN: Code duplicated, block: B:65:0x00b2  */
        /* JADX WARN: Code duplicated, block: B:68:0x00b8  */
        /* JADX WARN: Code duplicated, block: B:69:0x00b9 A[Catch: all -> 0x00c1, TryCatch #0 {all -> 0x00c1, blocks: (B:33:0x0050, B:35:0x0056, B:37:0x005d, B:40:0x0063, B:62:0x00a6, B:64:0x00ac, B:66:0x00b3, B:70:0x00c0, B:69:0x00b9, B:51:0x0089, B:53:0x008f, B:55:0x0096, B:58:0x009c), top: B:77:0x0033 }] */
        @Override // java.lang.Runnable
        public void run() {
            Canvas canvas;
            CountingIdlingResource countingIdlingResource;
            Object obj = this.this$0.surfaceLock;
            if (obj != null) {
                DrawingSurface drawingSurface = this.this$0;
                synchronized (obj) {
                    if (drawingSurface.surfaceDirty || !drawingSurface.surfaceReady) {
                        drawingSurface.surfaceDirty = false;
                    } else {
                        try {
                            obj.wait();
                        } catch (InterruptedException unused) {
                            return;
                        }
                    }
                    if (!drawingSurface.surfaceReady) {
                        return;
                    } else {
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
            SurfaceHolder surfaceHolder = this.holder;
            DrawingSurface drawingSurface2 = this.this$0;
            synchronized (surfaceHolder) {
                CountingIdlingResource countingIdlingResource2 = null;
                try {
                    try {
                        CountingIdlingResource countingIdlingResource3 = drawingSurface2.idlingResource;
                        if (countingIdlingResource3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("idlingResource");
                            countingIdlingResource3 = null;
                        }
                        countingIdlingResource3.increment();
                        Canvas canvasLockCanvas = getHolder().lockCanvas();
                        if (canvasLockCanvas != null) {
                            try {
                                drawingSurface2.doDraw(canvasLockCanvas);
                            } catch (IllegalArgumentException e) {
                                canvas = canvasLockCanvas;
                                e = e;
                                try {
                                    Integer.valueOf(Log.e(DrawingSurface.TAG, "run: ", e));
                                    CountingIdlingResource countingIdlingResource4 = drawingSurface2.idlingResource;
                                    if (countingIdlingResource4 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("idlingResource");
                                    } else {
                                        countingIdlingResource2 = countingIdlingResource4;
                                    }
                                    countingIdlingResource2.decrement();
                                    if (canvas != null) {
                                        getHolder().unlockCanvasAndPost(canvas);
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    countingIdlingResource = drawingSurface2.idlingResource;
                                    if (countingIdlingResource == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("idlingResource");
                                    } else {
                                        countingIdlingResource2 = countingIdlingResource;
                                    }
                                    countingIdlingResource2.decrement();
                                    if (canvas == null) {
                                        getHolder().unlockCanvasAndPost(canvas);
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                canvas = canvasLockCanvas;
                                th = th2;
                                countingIdlingResource = drawingSurface2.idlingResource;
                                if (countingIdlingResource == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("idlingResource");
                                } else {
                                    countingIdlingResource2 = countingIdlingResource;
                                }
                                countingIdlingResource2.decrement();
                                if (canvas == null) {
                                    getHolder().unlockCanvasAndPost(canvas);
                                }
                                throw th;
                            }
                        }
                        CountingIdlingResource countingIdlingResource5 = drawingSurface2.idlingResource;
                        if (countingIdlingResource5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("idlingResource");
                        } else {
                            countingIdlingResource2 = countingIdlingResource5;
                        }
                        countingIdlingResource2.decrement();
                        if (canvasLockCanvas != null) {
                            getHolder().unlockCanvasAndPost(canvasLockCanvas);
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                } catch (IllegalArgumentException e2) {
                    e = e2;
                    canvas = null;
                } catch (Throwable th4) {
                    th = th4;
                    canvas = null;
                }
            }
        }
    }
}
