package org.catrobat.paintroid.tools.implementation;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.ui.Perspective;

/* JADX INFO: compiled from: DefaultWorkspace.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001>B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u0002032\u0006\u00105\u001a\u000203H\u0016J\u0010\u00106\u001a\u0002032\u0006\u00107\u001a\u000203H\u0016J\u0010\u00108\u001a\u0002012\u0006\u00109\u001a\u00020:H\u0016J\b\u0010;\u001a\u00020<H\u0016J\b\u0010=\u001a\u00020<H\u0016R\u001c\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u000b8VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0010\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R$\u0010#\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010%R\u0014\u0010*\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u0018R\u0014\u0010,\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u0018R\u0014\u0010.\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\u0018¨\u0006?"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultWorkspace;", "Lorg/catrobat/paintroid/tools/Workspace;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "perspective", "Lorg/catrobat/paintroid/ui/Perspective;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/paintroid/tools/implementation/DefaultWorkspace$Listener;", "(Lorg/catrobat/paintroid/contract/LayerContracts$Model;Lorg/catrobat/paintroid/ui/Perspective;Lorg/catrobat/paintroid/tools/implementation/DefaultWorkspace$Listener;)V", "bitmapListOfAllLayers", "", "Landroid/graphics/Bitmap;", "getBitmapListOfAllLayers", "()Ljava/util/List;", "bitmapOfAllLayers", "getBitmapOfAllLayers", "()Landroid/graphics/Bitmap;", "bitmapOfCurrentLayer", "getBitmapOfCurrentLayer", "setBitmapOfCurrentLayer", "(Landroid/graphics/Bitmap;)V", "currentLayerIndex", "", "getCurrentLayerIndex", "()I", "height", "getHeight", "getLayerModel", "()Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "getPerspective", "()Lorg/catrobat/paintroid/ui/Perspective;", "setPerspective", "(Lorg/catrobat/paintroid/ui/Perspective;)V", "zoomFactor", "", "scale", "getScale", "()F", "setScale", "(F)V", "scaleForCenterBitmap", "getScaleForCenterBitmap", "surfaceHeight", "getSurfaceHeight", "surfaceWidth", "getSurfaceWidth", "width", "getWidth", "contains", "", KHRLightsPunctual.GLTFLight.TYPE_POINT, "Landroid/graphics/PointF;", "getCanvasPointFromSurfacePoint", "surfacePoint", "getSurfacePointFromCanvasPoint", "coordinate", "intersectsWith", "rectF", "Landroid/graphics/RectF;", "invalidate", "", "resetPerspective", "Listener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultWorkspace implements Workspace {
    private Bitmap bitmapOfCurrentLayer;
    private final LayerContracts.Model layerModel;
    private final Listener listener;
    private Perspective perspective;

    /* JADX INFO: compiled from: DefaultWorkspace.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultWorkspace$Listener;", "", "invalidate", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Listener {
        void invalidate();
    }

    public DefaultWorkspace(LayerContracts.Model layerModel, Perspective perspective, Listener listener) {
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Intrinsics.checkNotNullParameter(perspective, "perspective");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.layerModel = layerModel;
        this.perspective = perspective;
        this.listener = listener;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public LayerContracts.Model getLayerModel() {
        return this.layerModel;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public Perspective getPerspective() {
        return this.perspective;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public void setPerspective(Perspective perspective) {
        Intrinsics.checkNotNullParameter(perspective, "<set-?>");
        this.perspective = perspective;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public int getHeight() {
        return getLayerModel().getHeight();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public int getWidth() {
        return getLayerModel().getWidth();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public int getSurfaceWidth() {
        return getPerspective().surfaceWidth;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public int getSurfaceHeight() {
        return getPerspective().surfaceHeight;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public Bitmap getBitmapOfAllLayers() {
        return getLayerModel().getBitmapOfAllLayers();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public List<Bitmap> getBitmapListOfAllLayers() {
        return getLayerModel().getBitmapListOfAllLayers();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public void setBitmapOfCurrentLayer(Bitmap bitmap) {
        this.bitmapOfCurrentLayer = bitmap;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public Bitmap getBitmapOfCurrentLayer() {
        Bitmap bitmap;
        LayerContracts.Layer currentLayer = getLayerModel().getCurrentLayer();
        if (currentLayer == null || (bitmap = currentLayer.getBitmap()) == null) {
            return null;
        }
        return Bitmap.createBitmap(bitmap);
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public int getCurrentLayerIndex() {
        LayerContracts.Layer currentLayer = getLayerModel().getCurrentLayer();
        if (currentLayer == null) {
            return -1;
        }
        return getLayerModel().getLayerIndexOf(currentLayer);
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public float getScaleForCenterBitmap() {
        return getPerspective().getScaleForCenterBitmap();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public float getScale() {
        return getPerspective().getSurfaceScale();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public void setScale(float f) {
        getPerspective().setScale(f);
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public void resetPerspective() {
        getPerspective().setBitmapDimensions(getWidth(), getHeight());
        getPerspective().resetScaleAndTranslation();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public PointF getCanvasPointFromSurfacePoint(PointF surfacePoint) {
        Intrinsics.checkNotNullParameter(surfacePoint, "surfacePoint");
        return getPerspective().getCanvasPointFromSurfacePoint(surfacePoint);
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public void invalidate() {
        this.listener.invalidate();
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public boolean contains(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        return point.x < ((float) getWidth()) && point.x >= 0.0f && point.y < ((float) getHeight()) && point.y >= 0.0f;
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public boolean intersectsWith(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        return 0.0f < rectF.right && rectF.left < ((float) getWidth()) && 0.0f < rectF.bottom && rectF.top < ((float) getHeight());
    }

    @Override // org.catrobat.paintroid.tools.Workspace
    public PointF getSurfacePointFromCanvasPoint(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return getPerspective().getSurfacePointFromCanvasPoint(coordinate);
    }
}
