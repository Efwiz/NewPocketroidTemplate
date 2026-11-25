package org.catrobat.catroid.physics.shapebuilder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.LinkedList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/shapebuilder/PhysicsShapeScaleUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PhysicsShapeScaleUtils {
    public static final float COORDINATE_SCALING_DECIMAL_ACCURACY = 100.0f;

    private PhysicsShapeScaleUtils() {
    }

    public static Shape[] scaleShapes(Shape[] shapes, float targetScale) {
        return scaleShapes(shapes, targetScale, 1.0f);
    }

    public static Shape[] scaleShapes(Shape[] shapes, float targetScale, float originScale) {
        if (shapes == null || shapes.length == 0 || targetScale == 0.0f || originScale == 0.0f) {
            return null;
        }
        if (targetScale == originScale) {
            return shapes;
        }
        float f = targetScale / originScale;
        LinkedList linkedList = new LinkedList();
        if (shapes != null) {
            for (Shape shape : shapes) {
                LinkedList linkedList2 = new LinkedList();
                PolygonShape polygonShape = (PolygonShape) shape;
                for (int i = 0; i < polygonShape.getVertexCount(); i++) {
                    Vector2 vector2 = new Vector2();
                    polygonShape.getVertex(i, vector2);
                    linkedList2.add(scaleCoordinate(vector2, f));
                }
                PolygonShape polygonShape2 = new PolygonShape();
                polygonShape2.set((Vector2[]) linkedList2.toArray(new Vector2[linkedList2.size()]));
                linkedList.add(polygonShape2);
            }
        }
        return (Shape[]) linkedList.toArray(new Shape[linkedList.size()]);
    }

    private static Vector2 scaleCoordinate(Vector2 vertex, float scaleFactor) {
        Vector2 vector2 = new Vector2(vertex);
        vector2.x = scaleCoordinate(vector2.x, scaleFactor);
        vector2.y = scaleCoordinate(vector2.y, scaleFactor);
        return vector2;
    }

    public static float scaleCoordinate(float coordinates, float scaleFactor) {
        return Math.round((coordinates * scaleFactor) * 100.0f) / 100.0f;
    }
}
