package org.catrobat.catroid.physics.shapebuilder;

import android.graphics.Point;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import org.catrobat.catroid.physics.PhysicsWorldConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/shapebuilder/PhysicsShapeBuilderStrategyRectangle.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsShapeBuilderStrategyRectangle implements PhysicsShapeBuilderStrategy {
    @Override // org.catrobat.catroid.physics.shapebuilder.PhysicsShapeBuilderStrategy
    public Shape[] build(Pixmap pixmap, float scale) {
        if (pixmap == null) {
            return null;
        }
        Point point = null;
        Point point2 = null;
        for (int i = 0; i < pixmap.getHeight(); i++) {
            for (int i2 = 0; i2 < pixmap.getWidth(); i2++) {
                if ((pixmap.getPixel(i2, i) & 255) > 0) {
                    if (point == null) {
                        point = new Point(i2, i);
                        point2 = new Point(i2, i);
                    } else {
                        if (i2 < point.x) {
                            point.x = i2;
                        } else if (i2 > point2.x) {
                            point2.x = i2;
                        }
                        if (i < point.y) {
                            point.y = i;
                        } else if (i > point2.y) {
                            point2.y = i;
                        }
                    }
                }
            }
        }
        if (point == null) {
            return null;
        }
        int i3 = point2.x - point.x;
        int i4 = point2.y - point.y;
        if (i3 == 0) {
            i3 = 1;
        }
        if (i4 == 0) {
            i4 = 1;
        }
        float fConvertNormalToBox2dCoordinate = PhysicsWorldConverter.convertNormalToBox2dCoordinate(i3) / 2.0f;
        float fConvertNormalToBox2dCoordinate2 = PhysicsWorldConverter.convertNormalToBox2dCoordinate(i4) / 2.0f;
        Vector2 vector2 = new Vector2(fConvertNormalToBox2dCoordinate - PhysicsWorldConverter.convertNormalToBox2dCoordinate(pixmap.getWidth() / 2.0f), fConvertNormalToBox2dCoordinate2 - PhysicsWorldConverter.convertNormalToBox2dCoordinate(pixmap.getHeight() / 2.0f));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(fConvertNormalToBox2dCoordinate, fConvertNormalToBox2dCoordinate2, vector2, 0.0f);
        return new Shape[]{polygonShape};
    }
}
