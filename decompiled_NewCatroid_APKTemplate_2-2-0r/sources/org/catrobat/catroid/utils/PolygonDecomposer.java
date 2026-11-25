package org.catrobat.catroid.utils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/PolygonDecomposer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PolygonDecomposer {
    private PolygonDecomposer() {
    }

    public static Array<Polygon> decompose(Polygon concavePolygon) {
        float[] vertices = concavePolygon.getVertices();
        Array array = new Array(vertices.length / 2);
        for (int i = 0; i < vertices.length; i += 2) {
            array.add(new Vector2(vertices[i], vertices[i + 1]));
        }
        Array<Array<Vector2>> arrayDecompose = decompose((Array<Vector2>) array);
        Array<Polygon> array2 = new Array<>();
        Array.ArrayIterator<Array<Vector2>> it = arrayDecompose.iterator();
        while (it.hasNext()) {
            Array<Vector2> next = it.next();
            float[] fArr = new float[next.size * 2];
            for (int i2 = 0; i2 < next.size; i2++) {
                int i3 = i2 * 2;
                fArr[i3] = next.get(i2).x;
                fArr[i3 + 1] = next.get(i2).y;
            }
            array2.add(new Polygon(fArr));
        }
        return array2;
    }

    private static Array<Array<Vector2>> decompose(Array<Vector2> vertices) {
        Array<Array<Vector2>> array = new Array<>();
        int i = vertices.size;
        if (i < 3) {
            return array;
        }
        int i2 = 0;
        while (i2 < i) {
            int i3 = i2 + 1;
            int i4 = i3 % i;
            int i5 = (i2 + 2) % i;
            Vector2 vector2 = vertices.get(i2);
            Vector2 vector3 = vertices.get(i4);
            if (area(vector2, vector3, vertices.get(i5)) > 0.0f) {
                for (int i6 = 0; i6 < i; i6++) {
                    if (i6 != i2 && i6 != i4 && i6 != i5) {
                        Vector2 vector4 = vertices.get(i6);
                        if (isInside(vector2, vector3, vertices.get(i5), vector4)) {
                            vector2.dst2(vector4);
                            int i7 = i6 + 1;
                            if (i7 > i - 1) {
                                i7 = 0;
                            }
                            vector2.dst2(vertices.get(i7));
                            for (int i8 = 0; i8 < i; i8++) {
                                Array<Vector2> arrayCopy = copy(vertices, i2, i8);
                                Array<Vector2> arrayCopy2 = copy(vertices, i8, i2);
                                array.addAll(decompose(arrayCopy));
                                array.addAll(decompose(arrayCopy2));
                            }
                            return array;
                        }
                    }
                }
            }
            i2 = i3;
        }
        array.add(vertices);
        return array;
    }

    private static float area(Vector2 a, Vector2 b, Vector2 c) {
        return (a.x * (b.y - c.y)) + (b.x * (c.y - a.y)) + (c.x * (a.y - b.y));
    }

    private static boolean isInside(Vector2 a, Vector2 b, Vector2 c, Vector2 p) {
        return area(a, b, p) >= 0.0f && area(b, c, p) >= 0.0f && area(c, a, p) >= 0.0f;
    }

    private static Array<Vector2> copy(Array<Vector2> vertices, int i, int j) {
        Array<Vector2> array = new Array<>();
        while (i != j) {
            array.add(vertices.get(i));
            i = (i + 1) % vertices.size;
        }
        array.add(vertices.get(j));
        return array;
    }
}
