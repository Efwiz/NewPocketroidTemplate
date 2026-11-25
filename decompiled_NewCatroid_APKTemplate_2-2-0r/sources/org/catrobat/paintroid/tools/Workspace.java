package org.catrobat.paintroid.tools;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.List;
import kotlin.Metadata;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.ui.Perspective;

/* JADX INFO: compiled from: Workspace.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H&J\u0010\u00100\u001a\u00020/2\u0006\u00101\u001a\u00020/H&J\u0010\u00102\u001a\u00020/2\u0006\u00103\u001a\u00020/H&J\u0010\u00104\u001a\u00020-2\u0006\u00105\u001a\u000206H&J\b\u00107\u001a\u000208H&J\b\u00109\u001a\u000208H&R\u001a\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\t\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0018\u001a\u00020\u0019X¦\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\u00020\u001fX¦\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0012\u0010$\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010!R\u0012\u0010&\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0011R\u0012\u0010(\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u0011R\u0012\u0010*\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u0011¨\u0006:"}, d2 = {"Lorg/catrobat/paintroid/tools/Workspace;", "", "bitmapListOfAllLayers", "", "Landroid/graphics/Bitmap;", "getBitmapListOfAllLayers", "()Ljava/util/List;", "bitmapOfAllLayers", "getBitmapOfAllLayers", "()Landroid/graphics/Bitmap;", "bitmapOfCurrentLayer", "getBitmapOfCurrentLayer", "setBitmapOfCurrentLayer", "(Landroid/graphics/Bitmap;)V", "currentLayerIndex", "", "getCurrentLayerIndex", "()I", "height", "getHeight", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "getLayerModel", "()Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "perspective", "Lorg/catrobat/paintroid/ui/Perspective;", "getPerspective", "()Lorg/catrobat/paintroid/ui/Perspective;", "setPerspective", "(Lorg/catrobat/paintroid/ui/Perspective;)V", "scale", "", "getScale", "()F", "setScale", "(F)V", "scaleForCenterBitmap", "getScaleForCenterBitmap", "surfaceHeight", "getSurfaceHeight", "surfaceWidth", "getSurfaceWidth", "width", "getWidth", "contains", "", KHRLightsPunctual.GLTFLight.TYPE_POINT, "Landroid/graphics/PointF;", "getCanvasPointFromSurfacePoint", "surfacePoint", "getSurfacePointFromCanvasPoint", "coordinate", "intersectsWith", "rectF", "Landroid/graphics/RectF;", "invalidate", "", "resetPerspective", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface Workspace {
    boolean contains(PointF point);

    List<Bitmap> getBitmapListOfAllLayers();

    Bitmap getBitmapOfAllLayers();

    Bitmap getBitmapOfCurrentLayer();

    PointF getCanvasPointFromSurfacePoint(PointF surfacePoint);

    int getCurrentLayerIndex();

    int getHeight();

    LayerContracts.Model getLayerModel();

    Perspective getPerspective();

    float getScale();

    float getScaleForCenterBitmap();

    int getSurfaceHeight();

    PointF getSurfacePointFromCanvasPoint(PointF coordinate);

    int getSurfaceWidth();

    int getWidth();

    boolean intersectsWith(RectF rectF);

    void invalidate();

    void resetPerspective();

    void setBitmapOfCurrentLayer(Bitmap bitmap);

    void setPerspective(Perspective perspective);

    void setScale(float f);
}
