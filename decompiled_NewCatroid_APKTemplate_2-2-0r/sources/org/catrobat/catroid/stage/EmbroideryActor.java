package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.List;
import java.util.ListIterator;
import org.catrobat.catroid.embroidery.EmbroideryPatternManager;
import org.catrobat.catroid.embroidery.StitchPoint;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/EmbroideryActor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EmbroideryActor extends Actor {
    private final EmbroideryPatternManager embroideryPatternManager;
    private final ShapeRenderer shapeRenderer;
    private final float stitchSize;

    public EmbroideryActor(float screenRatio, EmbroideryPatternManager embroideryPatternManager, ShapeRenderer shapeRenderer) {
        this.stitchSize = screenRatio * 3.15f;
        this.embroideryPatternManager = embroideryPatternManager;
        this.shapeRenderer = shapeRenderer;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        List<StitchPoint> embroideryPatternList = this.embroideryPatternManager.getEmbroideryPatternList();
        ListIterator<StitchPoint> listIterator = embroideryPatternList.listIterator();
        if (embroideryPatternList.size() >= 2) {
            batch.end();
            StitchPoint next = listIterator.next();
            this.shapeRenderer.setColor(next.getColor());
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            drawCircle(next);
            boolean zIsColorChangePoint = false;
            while (listIterator.hasNext()) {
                StitchPoint next2 = listIterator.next();
                zIsColorChangePoint |= next2.isColorChangePoint();
                if (!zIsColorChangePoint) {
                    this.shapeRenderer.setColor(next.getColor());
                    drawLine(next, next2);
                }
                if (next2.isConnectingPoint()) {
                    this.shapeRenderer.setColor(next2.getColor());
                    drawCircle(next2);
                    zIsColorChangePoint = false;
                }
                next = next2;
            }
            this.shapeRenderer.end();
            batch.begin();
        }
    }

    void drawCircle(StitchPoint stitchPoint) {
        this.shapeRenderer.circle(stitchPoint.getX(), stitchPoint.getY(), this.stitchSize);
    }

    void drawLine(StitchPoint stitchPoint1, StitchPoint stitchPoint2) {
        this.shapeRenderer.rectLine(stitchPoint1.getX(), stitchPoint1.getY(), stitchPoint2.getX(), stitchPoint2.getY(), this.stitchSize);
    }

    public float getStitchSize() {
        return this.stitchSize;
    }
}
