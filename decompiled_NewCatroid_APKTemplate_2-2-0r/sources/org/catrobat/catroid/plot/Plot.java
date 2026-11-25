package org.catrobat.catroid.plot;

import android.graphics.PointF;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Queue;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plot/Plot.class
  classes14.dex
 */
/* JADX INFO: compiled from: Plot.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005J\b\u0010\u0017\u001a\u00020\u0010H\u0002J,\u0010\u0018\u001a(\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0004j\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006J\u001e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001eJ \u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0018\u0010 \u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010!\u001a\u00020\u0015J\u0006\u0010\"\u001a\u00020\u0015J\u0006\u0010#\u001a\u00020\u0015J\u000e\u0010#\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005J\b\u0010$\u001a\u00020\u0015H\u0002R>\u0010\u0003\u001a2\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u00060\u0004j\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\f\"\u0004\b\u0013\u0010\u000e¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/plot/Plot;", "", "()V", "dataPointLists", "Ljava/util/ArrayList;", "Landroid/graphics/PointF;", "Lkotlin/collections/ArrayList;", "drawQueue", "Lcom/badlogic/gdx/utils/Queue;", "height", "", "getHeight", "()F", "setHeight", "(F)V", "isPlotting", "", "width", "getWidth", "setWidth", "addPoint", "", KHRLightsPunctual.GLTFLight.TYPE_POINT, "canDraw", "data", "drawAllLines", "renderer", "Lcom/badlogic/gdx/graphics/glutils/ShapeRenderer;", "screenRatio", "camera", "Lcom/badlogic/gdx/graphics/Camera;", "drawLine", "drawLinesForSprite", "pause", "resume", "startNewPlotLine", "updateQueue", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Plot {
    private ArrayList<ArrayList<PointF>> dataPointLists = new ArrayList<>();
    private Queue<Queue<PointF>> drawQueue = new Queue<>();
    private float height;
    private boolean isPlotting;
    private float width;

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final float getHeight() {
        return this.height;
    }

    public final void setHeight(float f) {
        this.height = f;
    }

    public final void pause() {
        this.isPlotting = false;
    }

    public final void resume() {
        this.isPlotting = true;
    }

    /* JADX INFO: renamed from: isPlotting, reason: from getter */
    public final boolean getIsPlotting() {
        return this.isPlotting;
    }

    public final void startNewPlotLine() {
        this.dataPointLists.add(new ArrayList<>());
        this.drawQueue.addLast(new Queue<>());
    }

    public final void startNewPlotLine(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        this.dataPointLists.add(CollectionsKt.arrayListOf(point));
        this.drawQueue.addLast(new Queue<>());
        this.drawQueue.last().addLast(point);
    }

    public final void addPoint(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        ((ArrayList) CollectionsKt.last((List) this.dataPointLists)).add(point);
        this.drawQueue.last().addLast(point);
    }

    public final ArrayList<ArrayList<PointF>> data() {
        return this.dataPointLists;
    }

    private final boolean canDraw() {
        return this.drawQueue.size > 2 || (!this.drawQueue.isEmpty() && this.drawQueue.last().size > 2);
    }

    private final void updateQueue() {
        if (this.drawQueue.isEmpty() || this.drawQueue.size == 1 || this.drawQueue.first().size != 1) {
            return;
        }
        this.drawQueue.removeFirst();
    }

    public final void drawLinesForSprite(float screenRatio, Camera camera) {
        StageListener stageListener;
        if (camera == null) {
            return;
        }
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        ShapeRenderer shapeRenderer = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.shapeRenderer;
        if (shapeRenderer != null) {
            shapeRenderer.setColor(new Color(0.0f, 0.0f, 0.0f, 255.0f));
        }
        if (shapeRenderer != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }
        while (canDraw()) {
            if (shapeRenderer != null) {
                drawLine(screenRatio, shapeRenderer, camera);
            }
            updateQueue();
        }
        if (shapeRenderer != null) {
            shapeRenderer.end();
        }
        this.width = camera.viewportWidth;
        this.height = camera.viewportHeight;
    }

    public final void drawAllLines(ShapeRenderer renderer, float screenRatio, Camera camera) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(camera, "camera");
        renderer.setColor(new Color(0.0f, 0.0f, 0.0f, 255.0f));
        while (canDraw()) {
            drawLine(screenRatio, renderer, camera);
            updateQueue();
        }
        this.width = camera.viewportWidth;
        this.height = camera.viewportHeight;
    }

    private final void drawLine(float screenRatio, ShapeRenderer renderer, Camera camera) {
        PointF pointFRemoveFirst = this.drawQueue.first().removeFirst();
        Intrinsics.checkNotNullExpressionValue(pointFRemoveFirst, "removeFirst(...)");
        PointF pointF = pointFRemoveFirst;
        PointF pointFFirst = this.drawQueue.first().first();
        Intrinsics.checkNotNullExpressionValue(pointFFirst, "first(...)");
        PointF pointF2 = pointFFirst;
        pointF.x += camera.position.x;
        pointF.y += camera.position.y;
        pointF2.x += camera.position.x;
        pointF2.y += camera.position.y;
        if (pointF.x != pointF2.x || pointF.y != pointF2.y) {
            float f = screenRatio * 2.0f;
            float f2 = f / 2;
            renderer.circle(pointF.x, pointF.y, f2);
            renderer.rectLine(pointF.x, pointF.y, pointF2.x, pointF2.y, f);
            renderer.circle(pointF2.x, pointF2.y, f2);
        }
        pointF2.x -= camera.position.x;
        pointF2.y -= camera.position.y;
    }
}
