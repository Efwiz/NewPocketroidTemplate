package org.catrobat.paintroid.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.tools.Tool;

/* JADX INFO: compiled from: LayerModel.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010*\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0004H\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 J3\u0010!\u001a\u00020\u001e2\b\u0010\"\u001a\u0004\u0018\u00010 2\b\u0010#\u001a\u0004\u0018\u00010\n2\b\u0010$\u001a\u0004\u0018\u00010 2\b\u0010%\u001a\u0004\u0018\u00010&¢\u0006\u0002\u0010'J)\u0010(\u001a\u00020\u001e2\b\u0010\"\u001a\u0004\u0018\u00010 2\b\u0010#\u001a\u0004\u0018\u00010\n2\b\u0010%\u001a\u0004\u0018\u00010&¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010,0+H\u0016J\n\u0010-\u001a\u0004\u0018\u00010,H\u0016J\u0012\u0010.\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0010\u0010/\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0004H\u0016J\u0016\u00100\u001a\b\u0012\u0004\u0012\u00020\u0004012\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0010\u00102\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\nH\u0016J\b\u00104\u001a\u00020\u001eH\u0016J\u0018\u00105\u001a\u00020\u001e2\u0006\u00103\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0004H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR$\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0012j\b\u0012\u0004\u0012\u00020\u0004`\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u000e¨\u00066"}, d2 = {"Lorg/catrobat/paintroid/model/LayerModel;", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "()V", "currentLayer", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "getCurrentLayer", "()Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "setCurrentLayer", "(Lorg/catrobat/paintroid/contract/LayerContracts$Layer;)V", "height", "", "getHeight", "()I", "setHeight", "(I)V", "layerCount", "getLayerCount", "layers", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getLayers", "()Ljava/util/ArrayList;", "width", "getWidth", "setWidth", "addLayerAt", "", "index", "layer", "drawLayersOntoCanvas", "", "canvas", "Landroid/graphics/Canvas;", "drawLayersOntoCanvasCorrectOrder", "surfaceViewCanvas", "currentLayerIndex", "drawingBoardCanvas", "tool", "Lorg/catrobat/paintroid/tools/Tool;", "(Landroid/graphics/Canvas;Ljava/lang/Integer;Landroid/graphics/Canvas;Lorg/catrobat/paintroid/tools/Tool;)V", "drawLayersOntoCanvasCorrectOrderEraser", "(Landroid/graphics/Canvas;Ljava/lang/Integer;Lorg/catrobat/paintroid/tools/Tool;)V", "getBitmapListOfAllLayers", "", "Landroid/graphics/Bitmap;", "getBitmapOfAllLayers", "getLayerAt", "getLayerIndexOf", "listIterator", "", "removeLayerAt", "position", "reset", "setLayerAt", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class LayerModel implements LayerContracts.Model {
    private LayerContracts.Layer currentLayer;
    private int height;
    private final ArrayList<LayerContracts.Layer> layers = new ArrayList<>();
    private int width;

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public LayerContracts.Layer getCurrentLayer() {
        return this.currentLayer;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public void setCurrentLayer(LayerContracts.Layer layer) {
        this.currentLayer = layer;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public int getWidth() {
        return this.width;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public void setWidth(int i) {
        this.width = i;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public int getHeight() {
        return this.height;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public void setHeight(int i) {
        this.height = i;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public ArrayList<LayerContracts.Layer> getLayers() {
        return this.layers;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public int getLayerCount() {
        return getLayers().size();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public void reset() {
        getLayers().clear();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public LayerContracts.Layer getLayerAt(int index) {
        return (LayerContracts.Layer) CollectionsKt.getOrNull(getLayers(), index);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public int getLayerIndexOf(LayerContracts.Layer layer) {
        Intrinsics.checkNotNullParameter(layer, "layer");
        return getLayers().indexOf(layer);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public boolean addLayerAt(int index, LayerContracts.Layer layer) {
        Intrinsics.checkNotNullParameter(layer, "layer");
        try {
            getLayers().add(index, layer);
            return true;
        } catch (IndexOutOfBoundsException unused) {
            return false;
        }
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public ListIterator<LayerContracts.Layer> listIterator(int index) {
        ListIterator<LayerContracts.Layer> listIterator = getLayers().listIterator(index);
        Intrinsics.checkNotNullExpressionValue(listIterator, "layers.listIterator(index)");
        return listIterator;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public void setLayerAt(int position, LayerContracts.Layer layer) {
        Intrinsics.checkNotNullParameter(layer, "layer");
        getLayers().set(position, layer);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public boolean removeLayerAt(int position) {
        try {
            getLayers().remove(position);
            return true;
        } catch (IndexOutOfBoundsException unused) {
            return false;
        }
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public synchronized Bitmap getBitmapOfAllLayers() {
        synchronized (this) {
            Canvas canvas = null;
            if (getLayers().isEmpty()) {
                return null;
            }
            Bitmap bitmap = getLayers().get(0).getBitmap();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            if (bitmapCreateBitmap != null) {
                canvas = new Canvas(bitmapCreateBitmap);
            }
            drawLayersOntoCanvas(canvas);
            return bitmapCreateBitmap;
        }
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Model
    public List<Bitmap> getBitmapListOfAllLayers() {
        ArrayList<LayerContracts.Layer> layers = getLayers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(layers, 10));
        Iterator<T> it = layers.iterator();
        while (it.hasNext()) {
            arrayList.add(((LayerContracts.Layer) it.next()).getBitmap());
        }
        return arrayList;
    }

    public final void drawLayersOntoCanvas(Canvas canvas) {
        for (LayerContracts.Layer layer : CollectionsKt.asReversedMutable(getLayers())) {
            if (layer.getIsVisible()) {
                Paint paint = new Paint();
                paint.setFilterBitmap(false);
                paint.setAlpha(layer.getValueForOpacityPercentage());
                if (canvas != null) {
                    canvas.drawBitmap(layer.getBitmap(), 0.0f, 0.0f, paint);
                }
            }
        }
    }

    public final void drawLayersOntoCanvasCorrectOrder(Canvas surfaceViewCanvas, Integer currentLayerIndex, Canvas drawingBoardCanvas, Tool tool) {
        for (LayerContracts.Layer layer : CollectionsKt.asReversedMutable(getLayers())) {
            int layerIndexOf = getLayerIndexOf(layer);
            if (layer.getIsVisible()) {
                Paint paint = new Paint();
                paint.setFilterBitmap(false);
                paint.setAlpha(layer.getValueForOpacityPercentage());
                if (surfaceViewCanvas != null) {
                    surfaceViewCanvas.drawBitmap(layer.getBitmap(), 0.0f, 0.0f, paint);
                }
                if (drawingBoardCanvas != null) {
                    drawingBoardCanvas.drawBitmap(layer.getBitmap(), 0.0f, 0.0f, paint);
                }
                if (surfaceViewCanvas != null && currentLayerIndex != null && layerIndexOf == currentLayerIndex.intValue() && tool != null) {
                    tool.draw(surfaceViewCanvas);
                }
                if (drawingBoardCanvas != null && currentLayerIndex != null && layerIndexOf == currentLayerIndex.intValue() && tool != null) {
                    tool.draw(drawingBoardCanvas);
                }
            }
        }
    }

    public final void drawLayersOntoCanvasCorrectOrderEraser(Canvas surfaceViewCanvas, Integer currentLayerIndex, Tool tool) {
        for (LayerContracts.Layer layer : CollectionsKt.asReversedMutable(getLayers())) {
            int layerIndexOf = getLayerIndexOf(layer);
            if (layer.getIsVisible()) {
                Paint paint = new Paint();
                paint.setFilterBitmap(false);
                paint.setAlpha(layer.getValueForOpacityPercentage());
                if (surfaceViewCanvas != null) {
                    surfaceViewCanvas.drawBitmap(layer.getBitmap(), 0.0f, 0.0f, paint);
                }
                if (surfaceViewCanvas != null && currentLayerIndex != null && layerIndexOf == currentLayerIndex.intValue()) {
                    LayerContracts.Layer currentLayer = getCurrentLayer();
                    Bitmap bitmap = currentLayer == null ? null : currentLayer.getBitmap();
                    Canvas canvas = bitmap != null ? new Canvas(bitmap) : null;
                    if (canvas != null && tool != null) {
                        tool.draw(canvas);
                    }
                    if (bitmap != null) {
                        surfaceViewCanvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                    }
                }
            }
        }
    }
}
