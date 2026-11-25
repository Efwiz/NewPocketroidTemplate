package org.catrobat.catroid.content;

import android.graphics.PointF;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Queue;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/PenConfiguration.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PenConfiguration {
    private Queue<Queue<PointF>> positions = new Queue<>();
    private boolean penDown = false;
    private double penSize = 3.15d;
    private PenColor penColor = new PenColor(0.0f, 0.0f, 1.0f, 1.0f);
    private boolean stamp = false;
    private int queuesToFinish = 0;

    public void drawLinesForSprite(Float screenRatio, Camera camera) {
        ShapeRenderer shapeRenderer = StageActivity.getActiveStageListener().shapeRenderer;
        shapeRenderer.setColor(new Color(this.penColor.r, this.penColor.g, this.penColor.b, this.penColor.a));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        while (currentQueueHasJobToHandle()) {
            drawLine(screenRatio, shapeRenderer, camera);
            updateQueues();
        }
        shapeRenderer.end();
    }

    private boolean currentQueueHasJobToHandle() {
        return !this.positions.isEmpty() && (this.positions.first().size > 1 || this.queuesToFinish > 0);
    }

    public void drawAllLines(ShapeRenderer renderer, Float screenRatio, Camera camera) {
        renderer.setColor(new Color(this.penColor.r, this.penColor.g, this.penColor.b, this.penColor.a));
        while (currentQueueHasJobToHandle()) {
            drawLine(screenRatio, renderer, camera);
            updateQueues();
        }
        renderer.flush();
    }

    private void drawLine(Float screenRatio, ShapeRenderer renderer, Camera camera) {
        PointF pointFRemoveFirst = this.positions.first().removeFirst();
        PointF pointFFirst = this.positions.first().first();
        pointFRemoveFirst.x += camera.position.x;
        pointFRemoveFirst.y += camera.position.y;
        pointFFirst.x += camera.position.x;
        pointFFirst.y += camera.position.y;
        if (pointFRemoveFirst.x != pointFFirst.x || pointFRemoveFirst.y != pointFFirst.y) {
            Float fValueOf = Float.valueOf(((float) this.penSize) * screenRatio.floatValue());
            renderer.circle(pointFRemoveFirst.x, pointFRemoveFirst.y, fValueOf.floatValue() / 2.0f);
            renderer.rectLine(pointFRemoveFirst.x, pointFRemoveFirst.y, pointFFirst.x, pointFFirst.y, fValueOf.floatValue());
            renderer.circle(pointFFirst.x, pointFFirst.y, fValueOf.floatValue() / 2.0f);
        }
        pointFFirst.x -= camera.position.x;
        pointFFirst.y -= camera.position.y;
    }

    private void updateQueues() {
        if (this.queuesToFinish <= 0 || this.positions.first().size > 1) {
            return;
        }
        this.positions.removeFirst();
        this.queuesToFinish--;
    }

    public void addQueue() {
        this.positions.addLast(new Queue<>());
    }

    public void addPosition(PointF position) {
        this.positions.last().addLast(position);
    }

    public void incrementQueuesToFinish() {
        this.queuesToFinish++;
    }

    public void decrementQueuesToFinish() {
        this.queuesToFinish--;
    }

    public void setPenDown(boolean penDown) {
        this.penDown = penDown;
    }

    public boolean isPenDown() {
        return this.penDown;
    }

    public void setPenSize(double penSize) {
        this.penSize = penSize;
    }

    public void setPenColor(PenColor penColor) {
        this.penColor = penColor;
    }

    public void setStamp(boolean stamp) {
        this.stamp = stamp;
    }

    public boolean hasStamp() {
        return this.stamp;
    }

    public Queue<Queue<PointF>> getPositions() {
        return this.positions;
    }
}
