package org.catrobat.catroid.sensing;

import android.graphics.PointF;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.utils.NativeLookOptimizer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/CollisionDetection.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class CollisionDetection {
    private static final Vector2 intersectV1 = new Vector2();
    private static final Vector2 intersectV2 = new Vector2();
    private static final Vector2 edgeFirstPoint = new Vector2();
    private static final Vector2 edgeSecondPoint = new Vector2();
    private static final Vector2 fingerStart = new Vector2();
    private static final Vector2 fingerEnd = new Vector2();
    private static final Vector2 fingerCenter = new Vector2();
    private static final Circle fingerCollisionCircle = new Circle();

    private CollisionDetection() {
    }

    public static boolean checkCollisionBetweenLooks(Look firstLook, Look secondLook) {
        if (firstLook != null && secondLook != null && firstLook.isVisible() && firstLook.isLookVisible() && secondLook.isVisible() && secondLook.isLookVisible()) {
            Rectangle hitbox = firstLook.getHitbox();
            Rectangle hitbox2 = secondLook.getHitbox();
            if (hitbox != null && hitbox2 != null && hitbox.overlaps(hitbox2)) {
                Polygon[] currentCollisionPolygon = firstLook.getCurrentCollisionPolygon();
                Polygon[] currentCollisionPolygon2 = secondLook.getCurrentCollisionPolygon();
                if (currentCollisionPolygon != null && currentCollisionPolygon2 != null && currentCollisionPolygon.length != 0 && currentCollisionPolygon2.length != 0) {
                    if (NativeLookOptimizer.isWorking.booleanValue()) {
                        float[][] fArr = new float[currentCollisionPolygon.length][];
                        for (int i = 0; i < currentCollisionPolygon.length; i++) {
                            Polygon polygon = currentCollisionPolygon[i];
                            fArr[i] = polygon != null ? polygon.getTransformedVertices() : new float[0];
                        }
                        float[][] fArr2 = new float[currentCollisionPolygon2.length][];
                        for (int i2 = 0; i2 < currentCollisionPolygon2.length; i2++) {
                            Polygon polygon2 = currentCollisionPolygon2[i2];
                            fArr2[i2] = polygon2 != null ? polygon2.getTransformedVertices() : new float[0];
                        }
                        return NativeLookOptimizer.checkSingleCollision(fArr, fArr2);
                    }
                    return checkCollisionBetweenPolygonArrays(currentCollisionPolygon, currentCollisionPolygon2);
                }
            }
        }
        return false;
    }

    private static boolean checkCollisionBetweenPolygonArrays(Polygon[] first, Polygon[] second) {
        Rectangle[] rectangleArrCreateBoundingBoxesOfCollisionPolygons = createBoundingBoxesOfCollisionPolygons(first);
        Rectangle[] rectangleArrCreateBoundingBoxesOfCollisionPolygons2 = createBoundingBoxesOfCollisionPolygons(second);
        for (int i = 0; i < first.length; i++) {
            for (int i2 = 0; i2 < second.length; i2++) {
                if (rectangleArrCreateBoundingBoxesOfCollisionPolygons[i].overlaps(rectangleArrCreateBoundingBoxesOfCollisionPolygons2[i2]) && intersectPolygons(first[i], second[i2])) {
                    return true;
                }
            }
        }
        return checkContainment(first, second, rectangleArrCreateBoundingBoxesOfCollisionPolygons, rectangleArrCreateBoundingBoxesOfCollisionPolygons2);
    }

    private static boolean checkCollisionBetweenPolygonArraysSAT(Polygon[] first, Polygon[] second, Rectangle[] firstBoxes, Rectangle[] secondBoxes) {
        Rectangle rectangle;
        Rectangle rectangle2;
        if (first != null && second != null) {
            for (int i = 0; i < first.length; i++) {
                Polygon polygon = first[i];
                if (polygon != null && polygon.getTransformedVertices() != null && polygon.getTransformedVertices().length >= 6) {
                    for (int i2 = 0; i2 < second.length; i2++) {
                        Polygon polygon2 = second[i2];
                        if (polygon2 != null && polygon2.getTransformedVertices() != null && polygon2.getTransformedVertices().length >= 6 && (rectangle = firstBoxes[i]) != null && (rectangle2 = secondBoxes[i2]) != null && rectangle.overlaps(rectangle2) && Intersector.overlapConvexPolygons(polygon, polygon2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static Rectangle[] createBoundingBoxesOfCollisionPolygons(Polygon[] polygons) {
        Rectangle[] rectangleArr = new Rectangle[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            Polygon polygon = polygons[i];
            if (polygon != null) {
                rectangleArr[i] = polygon.getBoundingRectangle();
            } else {
                rectangleArr[i] = new Rectangle();
            }
        }
        return rectangleArr;
    }

    public static boolean intersectPolygons(Polygon first, Polygon second) {
        float[] transformedVertices;
        int length;
        if (first == null || second == null || (length = (transformedVertices = first.getTransformedVertices()).length) < 4) {
            return false;
        }
        Vector2 vector2 = intersectV1;
        Vector2 vector3 = intersectV2;
        int i = 0;
        while (i < length) {
            vector2.x = transformedVertices[i];
            vector2.y = transformedVertices[i + 1];
            int i2 = i + 2;
            vector3.x = transformedVertices[i2 % length];
            vector3.y = transformedVertices[(i + 3) % length];
            if (Intersector.intersectSegmentPolygon(vector2, vector3, second)) {
                return true;
            }
            i = i2;
        }
        return false;
    }

    private static boolean checkVertexContainment(Polygon[] inner, Polygon[] outer, Rectangle[] innerBoxes, Rectangle[] outerBoxes) {
        Rectangle rectangle;
        Rectangle rectangle2;
        for (int i = 0; i < inner.length; i++) {
            Polygon polygon = inner[i];
            if (polygon != null) {
                float[] transformedVertices = polygon.getTransformedVertices();
                if (transformedVertices.length >= 2) {
                    float f = transformedVertices[0];
                    float f2 = transformedVertices[1];
                    for (Rectangle rectangle3 : outerBoxes) {
                        if (rectangle3 != null && rectangle3.contains(f, f2)) {
                            for (int i2 = 0; i2 < outer.length; i2++) {
                                Polygon polygon2 = outer[i2];
                                if (polygon2 != null && (rectangle = innerBoxes[i]) != null && (rectangle2 = outerBoxes[i2]) != null && rectangle.overlaps(rectangle2) && polygon2.contains(f, f2)) {
                                    return true;
                                }
                            }
                            break;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    private static boolean checkContainment(Polygon[] first, Polygon[] second, Rectangle[] firstBoxes, Rectangle[] secondBoxes) {
        return checkVertexContainment(first, second, firstBoxes, secondBoxes) || checkVertexContainment(second, first, secondBoxes, firstBoxes);
    }

    public static String getSecondSpriteNameFromCollisionFormulaString(String formula, Project currentProject) {
        int iLastIndexOf;
        String name2 = null;
        if (currentProject != null && formula != null) {
            int length = formula.length();
            for (Scene scene : currentProject.getSceneList()) {
                if (scene != null) {
                    for (Sprite sprite : scene.getSpriteList()) {
                        if (sprite != null && sprite.getName() != null && formula.endsWith(sprite.getName()) && (iLastIndexOf = formula.lastIndexOf(sprite.getName())) >= 0 && iLastIndexOf < length) {
                            name2 = sprite.getName();
                            length = iLastIndexOf;
                        }
                    }
                }
            }
        }
        return name2;
    }

    public static boolean collidesWithEdge(Polygon[] currentCollisionPolygon, Rectangle screen) {
        float[] transformedVertices;
        int length;
        if (currentCollisionPolygon != null && screen != null) {
            Vector2 vector2 = edgeFirstPoint;
            Vector2 vector3 = edgeSecondPoint;
            for (Polygon polygon : currentCollisionPolygon) {
                if (polygon != null && (length = (transformedVertices = polygon.getTransformedVertices()).length) >= 4) {
                    int i = 0;
                    while (i < length) {
                        vector2.set(transformedVertices[i], transformedVertices[i + 1]);
                        int i2 = i + 2;
                        vector3.set(transformedVertices[i2 % length], transformedVertices[(i + 3) % length]);
                        boolean zContains = screen.contains(vector2);
                        boolean zContains2 = screen.contains(vector3);
                        if (zContains != zContains2) {
                            return true;
                        }
                        if (!zContains && !zContains2 && Intersector.intersectSegmentRectangle(vector2, vector3, screen)) {
                            return true;
                        }
                        i = i2;
                    }
                }
            }
        }
        return false;
    }

    public static double collidesWithFinger(Polygon[] currentCollisionPolygon, ArrayList<PointF> touchingPoints) {
        if (currentCollisionPolygon == null || touchingPoints == null || touchingPoints.isEmpty()) {
            return 0.0d;
        }
        Vector2 vector2 = fingerStart;
        Vector2 vector3 = fingerEnd;
        Vector2 vector4 = fingerCenter;
        for (PointF pointF : touchingPoints) {
            if (pointF != null) {
                vector4.set(pointF.x, pointF.y);
                boolean z = false;
                int i = 0;
                for (Polygon polygon : currentCollisionPolygon) {
                    if (polygon != null) {
                        Rectangle boundingRectangle = polygon.getBoundingRectangle();
                        Circle circle = fingerCollisionCircle;
                        circle.set(vector4.x, vector4.y, 50.0f);
                        if (Intersector.overlaps(circle, boundingRectangle)) {
                            float[] transformedVertices = polygon.getTransformedVertices();
                            int length = transformedVertices.length / 2;
                            if (length < 2) {
                                continue;
                            } else {
                                int i2 = 0;
                                while (i2 < length) {
                                    int i3 = i2 + 1;
                                    int i4 = i2 * 2;
                                    vector2.set(transformedVertices[i4], transformedVertices[i4 + 1]);
                                    int i5 = (i3 % length) * 2;
                                    vector3.set(transformedVertices[i5], transformedVertices[i5 + 1]);
                                    if (Intersector.intersectSegmentCircle(vector2, vector3, vector4, 2500.0f)) {
                                        z = true;
                                        break;
                                    }
                                    i2 = i3;
                                }
                                if (z) {
                                    break;
                                }
                                if (polygon.contains(vector4.x, vector4.y)) {
                                    i++;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
                if (z || i % 2 != 0) {
                    return 1.0d;
                }
            }
        }
        return 0.0d;
    }

    public static List<Sprite[]> findAllCollisions(List<Sprite> sprites) {
        if (sprites == null || sprites.size() < 2) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (Sprite sprite : sprites) {
            if (sprite != null && sprite.look != null && sprite.look.isVisible() && sprite.look.isLookVisible()) {
                arrayList.add(sprite);
            }
        }
        if (arrayList.size() < 2) {
            return new ArrayList();
        }
        float[][][] fArr = new float[arrayList.size()][][];
        for (int i = 0; i < arrayList.size(); i++) {
            Polygon[] currentCollisionPolygon = ((Sprite) arrayList.get(i)).look.getCurrentCollisionPolygon();
            if (currentCollisionPolygon == null) {
                fArr[i] = new float[0][];
            } else {
                fArr[i] = new float[currentCollisionPolygon.length][];
                for (int i2 = 0; i2 < currentCollisionPolygon.length; i2++) {
                    Polygon polygon = currentCollisionPolygon[i2];
                    if (polygon != null) {
                        fArr[i][i2] = polygon.getTransformedVertices();
                    } else {
                        fArr[i][i2] = new float[0];
                    }
                }
            }
        }
        int[] iArrCheckAllCollisions = NativeLookOptimizer.checkAllCollisions(fArr);
        ArrayList arrayList2 = new ArrayList();
        if (iArrCheckAllCollisions != null) {
            for (int i3 = 0; i3 < iArrCheckAllCollisions.length; i3 += 2) {
                arrayList2.add(new Sprite[]{(Sprite) arrayList.get(iArrCheckAllCollisions[i3]), (Sprite) arrayList.get(iArrCheckAllCollisions[i3 + 1])});
            }
        }
        return arrayList2;
    }
}
