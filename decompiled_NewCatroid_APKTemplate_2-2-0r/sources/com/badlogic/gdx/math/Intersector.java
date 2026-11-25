package com.badlogic.gdx.math;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.OrientedBoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import java.util.Arrays;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Intersector.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class Intersector {
    private static final Vector3 v0 = new Vector3();
    private static final Vector3 v1 = new Vector3();
    private static final Vector3 v2 = new Vector3();
    private static final FloatArray floatArray = new FloatArray();
    private static final FloatArray floatArray2 = new FloatArray();
    private static final Vector2 ip = new Vector2();
    private static final Vector2 ep1 = new Vector2();
    private static final Vector2 ep2 = new Vector2();
    private static final Vector2 s = new Vector2();
    private static final Vector2 e = new Vector2();
    static Vector2 v2a = new Vector2();
    static Vector2 v2b = new Vector2();
    static Vector2 v2c = new Vector2();
    static Vector2 v2d = new Vector2();
    private static final Plane p = new Plane(new Vector3(), 0.0f);
    private static final Vector3 i = new Vector3();
    private static final Vector3 dir = new Vector3();
    private static final Vector3 start = new Vector3();
    static Vector3 best = new Vector3();
    static Vector3 tmp = new Vector3();
    static Vector3 tmp1 = new Vector3();
    static Vector3 tmp2 = new Vector3();
    static Vector3 tmp3 = new Vector3();
    static Vector3 intersection = new Vector3();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Intersector$MinimumTranslationVector.class
      classes8.dex
     */
    public static class MinimumTranslationVector {
        public Vector2 normal = new Vector2();
        public float depth = 0.0f;
    }

    static float det(float f, float f2, float f3, float f4) {
        return (f * f4) - (f2 * f3);
    }

    static double detd(double d, double d2, double d3, double d4) {
        return (d * d4) - (d2 * d3);
    }

    public static boolean isPointInTriangle(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = f - f3;
        float f10 = f2 - f4;
        boolean z = ((f5 - f3) * f10) - ((f6 - f4) * f9) > 0.0f;
        if ((((f7 - f3) * f10) - ((f8 - f4) * f9) > 0.0f) == z) {
            return false;
        }
        return (((((f7 - f5) * (f2 - f6)) - ((f8 - f6) * (f - f5))) > 0.0f ? 1 : ((((f7 - f5) * (f2 - f6)) - ((f8 - f6) * (f - f5))) == 0.0f ? 0 : -1)) > 0) == z;
    }

    private Intersector() {
    }

    public static boolean isPointInTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        Vector3 vector7 = v0;
        vector7.set(vector4).sub(vector3);
        Vector3 vector8 = v1;
        vector8.set(vector5).sub(vector3);
        Vector3 vector9 = v2;
        vector9.set(vector6).sub(vector3);
        vector8.crs(vector9);
        vector9.crs(vector7);
        if (vector8.dot(vector9) < 0.0f) {
            return false;
        }
        vector7.crs(vector9.set(vector5).sub(vector3));
        return vector8.dot(vector7) >= 0.0f;
    }

    public static boolean isPointInTriangle(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5) {
        return isPointInTriangle(vector2.x, vector2.y, vector3.x, vector3.y, vector4.x, vector4.y, vector5.x, vector5.y);
    }

    public static boolean intersectSegmentPlane(Vector3 vector3, Vector3 vector4, Plane plane, Vector3 vector5) {
        Vector3 vector3Sub = v0.set(vector4).sub(vector3);
        float fDot = vector3Sub.dot(plane.getNormal());
        if (fDot == 0.0f) {
            return false;
        }
        float f = (-(vector3.dot(plane.getNormal()) + plane.getD())) / fDot;
        if (f < 0.0f || f > 1.0f) {
            return false;
        }
        vector5.set(vector3).add(vector3Sub.scl(f));
        return true;
    }

    public static int pointLineSide(Vector2 vector2, Vector2 vector3, Vector2 vector4) {
        return (int) Math.signum(((vector3.x - vector2.x) * (vector4.y - vector2.y)) - ((vector3.y - vector2.y) * (vector4.x - vector2.x)));
    }

    public static int pointLineSide(float f, float f2, float f3, float f4, float f5, float f6) {
        return (int) Math.signum(((f3 - f) * (f6 - f2)) - ((f4 - f2) * (f5 - f)));
    }

    public static boolean isPointInPolygon(Array<Vector2> array, Vector2 vector2) {
        Vector2 vector2Peek = array.peek();
        float f = vector2.x;
        float f2 = vector2.y;
        int i2 = 0;
        boolean z = false;
        while (i2 < array.size) {
            Vector2 vector3 = array.get(i2);
            if (((vector3.y < f2 && vector2Peek.y >= f2) || (vector2Peek.y < f2 && vector3.y >= f2)) && vector3.x + (((f2 - vector3.y) / (vector2Peek.y - vector3.y)) * (vector2Peek.x - vector3.x)) < f) {
                z = !z;
            }
            i2++;
            vector2Peek = vector3;
        }
        return z;
    }

    public static boolean isPointInPolygon(float[] fArr, int i2, int i3, float f, float f2) {
        float f3 = fArr[i2];
        float f4 = fArr[i2 + 1];
        int i4 = i2 + 3;
        int i5 = i2 + i3;
        boolean z = false;
        float f5 = f4;
        while (i4 < i5) {
            float f6 = fArr[i4];
            if ((f6 < f2 && f5 >= f2) || (f5 < f2 && f6 >= f2)) {
                float f7 = fArr[i4 - 1];
                if (f7 + (((f2 - f6) / (f5 - f6)) * (fArr[i4 - 3] - f7)) < f) {
                    z = !z;
                }
            }
            i4 += 2;
            f5 = f6;
        }
        return (((f4 >= f2 || f5 < f2) && (f5 >= f2 || f4 < f2)) || f3 + (((f2 - f4) / (f5 - f4)) * (fArr[i4 + (-3)] - f3)) >= f) ? z : !z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7, types: [int] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.badlogic.gdx.utils.FloatArray] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static boolean intersectPolygons(Polygon polygon, Polygon polygon2, Polygon polygon3) {
        ?? r2 = 0;
        if (polygon.getVertices().length == 0 || polygon2.getVertices().length == 0) {
            return false;
        }
        Vector2 vector2 = ip;
        Vector2 vector3 = ep1;
        Vector2 vector4 = ep2;
        Vector2 vector5 = s;
        Vector2 vector6 = e;
        FloatArray floatArray3 = floatArray;
        ?? r8 = floatArray2;
        floatArray3.clear();
        r8.clear();
        r8.addAll(polygon.getTransformedVertices());
        float[] transformedVertices = polygon2.getTransformedVertices();
        int length = transformedVertices.length - 2;
        int i2 = 0;
        while (i2 <= length) {
            vector3.set(transformedVertices[i2], transformedVertices[i2 + 1]);
            if (i2 < length) {
                vector4.set(transformedVertices[i2 + 2], transformedVertices[i2 + 3]);
            } else {
                vector4.set(transformedVertices[r2], transformedVertices[1]);
            }
            if (r8.size == 0) {
                return r2;
            }
            vector5.set(r8.get(r8.size - 2), r8.get(r8.size - 1));
            for (?? r14 = r2; r14 < r8.size; r14 += 2) {
                vector6.set(r8.get(r14), r8.get(r14 + 1));
                boolean z = pointLineSide(vector4, vector3, vector5) > 0;
                if (pointLineSide(vector4, vector3, vector6) > 0) {
                    if (!z) {
                        intersectLines(vector5, vector6, vector3, vector4, vector2);
                        if (floatArray3.size < 2 || floatArray3.get(floatArray3.size - 2) != vector2.x || floatArray3.get(floatArray3.size - 1) != vector2.y) {
                            floatArray3.add(vector2.x);
                            floatArray3.add(vector2.y);
                        }
                    }
                    floatArray3.add(vector6.x);
                    floatArray3.add(vector6.y);
                } else if (z) {
                    intersectLines(vector5, vector6, vector3, vector4, vector2);
                    floatArray3.add(vector2.x);
                    floatArray3.add(vector2.y);
                }
                vector5.set(vector6.x, vector6.y);
            }
            r8.clear();
            r8.addAll(floatArray3);
            floatArray3.clear();
            i2 += 2;
            r2 = 0;
        }
        if (r8.size >= 6 && r8.get(0) == r8.get(r8.size - 2) && r8.get(1) == r8.get(r8.size - 1)) {
            r8.setSize(r8.size - 2);
        }
        if (r8.size < 6) {
            return false;
        }
        if (polygon3 != null) {
            polygon3.resetTransformations();
            if (polygon3.getVertices().length == r8.size) {
                System.arraycopy(r8.items, 0, polygon3.getVertices(), 0, r8.size);
            } else {
                polygon3.setVertices(r8.toArray());
            }
        }
        return true;
    }

    public static boolean intersectPolygons(FloatArray floatArray3, FloatArray floatArray4) {
        if (isPointInPolygon(floatArray3.items, 0, floatArray3.size, floatArray4.items[0], floatArray4.items[1]) || isPointInPolygon(floatArray4.items, 0, floatArray4.size, floatArray3.items[0], floatArray3.items[1])) {
            return true;
        }
        return intersectPolygonEdges(floatArray3, floatArray4);
    }

    public static boolean intersectPolygonEdges(FloatArray floatArray3, FloatArray floatArray4) {
        int i2 = floatArray3.size;
        int i3 = i2 - 2;
        int i4 = floatArray4.size;
        int i5 = i4 - 2;
        float[] fArr = floatArray3.items;
        float[] fArr2 = floatArray4.items;
        float f = fArr[i3];
        float f2 = fArr[i2 - 1];
        int i6 = 0;
        while (i6 <= i3) {
            float f3 = fArr[i6];
            float f4 = fArr[i6 + 1];
            int i7 = 0;
            float f5 = fArr2[i5];
            float f6 = fArr2[i4 - 1];
            while (i7 <= i5) {
                float f7 = fArr2[i7];
                float f8 = fArr2[i7 + 1];
                int i8 = i7;
                int i9 = i6;
                if (intersectSegments(f, f2, f3, f4, f5, f6, f7, f8, null)) {
                    return true;
                }
                i7 = i8 + 2;
                f5 = f7;
                f6 = f8;
                i6 = i9;
            }
            i6 += 2;
            f = f3;
            f2 = f4;
        }
        return false;
    }

    public static float distanceLinePoint(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f3 - f;
        float f8 = f4 - f2;
        return Math.abs(((f5 - f) * f8) - ((f6 - f2) * f7)) / Vector2.len(f7, f8);
    }

    public static float distanceSegmentPoint(float f, float f2, float f3, float f4, float f5, float f6) {
        return nearestSegmentPoint(f, f2, f3, f4, f5, f6, v2a).dst(f5, f6);
    }

    public static float distanceSegmentPoint(Vector2 vector2, Vector2 vector3, Vector2 vector4) {
        return nearestSegmentPoint(vector2, vector3, vector4, v2a).dst(vector4);
    }

    public static Vector2 nearestSegmentPoint(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5) {
        float fDst2 = vector2.dst2(vector3);
        if (fDst2 == 0.0f) {
            return vector5.set(vector2);
        }
        float f = (((vector4.x - vector2.x) * (vector3.x - vector2.x)) + ((vector4.y - vector2.y) * (vector3.y - vector2.y))) / fDst2;
        if (f <= 0.0f) {
            return vector5.set(vector2);
        }
        if (f >= 1.0f) {
            return vector5.set(vector3);
        }
        return vector5.set(vector2.x + ((vector3.x - vector2.x) * f), vector2.y + (f * (vector3.y - vector2.y)));
    }

    public static Vector2 nearestSegmentPoint(float f, float f2, float f3, float f4, float f5, float f6, Vector2 vector2) {
        float f7 = f3 - f;
        float f8 = f4 - f2;
        float f9 = (f7 * f7) + (f8 * f8);
        if (f9 == 0.0f) {
            return vector2.set(f, f2);
        }
        float f10 = (((f5 - f) * f7) + ((f6 - f2) * f8)) / f9;
        if (f10 <= 0.0f) {
            return vector2.set(f, f2);
        }
        if (f10 >= 1.0f) {
            return vector2.set(f3, f4);
        }
        return vector2.set(f + (f7 * f10), f2 + (f10 * f8));
    }

    public static boolean intersectSegmentCircle(Vector2 vector2, Vector2 vector3, Vector2 vector4, float f) {
        tmp.set(vector3.x - vector2.x, vector3.y - vector2.y, 0.0f);
        tmp1.set(vector4.x - vector2.x, vector4.y - vector2.y, 0.0f);
        float fLen = tmp.len();
        float fDot = tmp1.dot(tmp.nor());
        if (fDot <= 0.0f) {
            tmp2.set(vector2.x, vector2.y, 0.0f);
        } else if (fDot >= fLen) {
            tmp2.set(vector3.x, vector3.y, 0.0f);
        } else {
            tmp3.set(tmp.scl(fDot));
            tmp2.set(tmp3.x + vector2.x, tmp3.y + vector2.y, 0.0f);
        }
        float f2 = vector4.x - tmp2.x;
        float f3 = vector4.y - tmp2.y;
        return (f2 * f2) + (f3 * f3) <= f;
    }

    public static boolean intersectSegmentCircle(Vector2 vector2, Vector2 vector3, Circle circle, MinimumTranslationVector minimumTranslationVector) {
        v2a.set(vector3).sub(vector2);
        v2b.set(circle.x - vector2.x, circle.y - vector2.y);
        float fLen = v2a.len();
        float fDot = v2b.dot(v2a.nor());
        if (fDot <= 0.0f) {
            v2c.set(vector2);
        } else if (fDot >= fLen) {
            v2c.set(vector3);
        } else {
            v2d.set(v2a.scl(fDot));
            v2c.set(v2d).add(vector2);
        }
        v2a.set(v2c.x - circle.x, v2c.y - circle.y);
        if (minimumTranslationVector != null) {
            if (v2a.equals(Vector2.Zero)) {
                v2d.set(vector3.y - vector2.y, vector2.x - vector3.x);
                minimumTranslationVector.normal.set(v2d).nor();
                minimumTranslationVector.depth = circle.radius;
            } else {
                minimumTranslationVector.normal.set(v2a).nor();
                minimumTranslationVector.depth = circle.radius - v2a.len();
            }
        }
        return v2a.len2() <= circle.radius * circle.radius;
    }

    public static boolean intersectFrustumBounds(Frustum frustum, BoundingBox boundingBox) {
        if (frustum.pointInFrustum(boundingBox.getCorner000(tmp)) || frustum.pointInFrustum(boundingBox.getCorner001(tmp)) || frustum.pointInFrustum(boundingBox.getCorner010(tmp)) || frustum.pointInFrustum(boundingBox.getCorner011(tmp)) || frustum.pointInFrustum(boundingBox.getCorner100(tmp)) || frustum.pointInFrustum(boundingBox.getCorner101(tmp)) || frustum.pointInFrustum(boundingBox.getCorner110(tmp)) || frustum.pointInFrustum(boundingBox.getCorner111(tmp))) {
            return true;
        }
        boolean zContains = false;
        for (Vector3 vector3 : frustum.planePoints) {
            zContains |= boundingBox.contains(vector3);
        }
        return zContains;
    }

    public static boolean intersectFrustumBounds(Frustum frustum, OrientedBoundingBox orientedBoundingBox) {
        boolean zPointInFrustum = false;
        for (Vector3 vector3 : orientedBoundingBox.getVertices()) {
            zPointInFrustum |= frustum.pointInFrustum(vector3);
        }
        if (zPointInFrustum) {
            return true;
        }
        boolean zContains = false;
        for (Vector3 vector4 : frustum.planePoints) {
            zContains |= orientedBoundingBox.contains(vector4);
        }
        return zContains;
    }

    public static float intersectRayRay(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5) {
        float f = vector4.x - vector2.x;
        float f2 = vector4.y - vector2.y;
        float f3 = (vector3.x * vector5.y) - (vector3.y * vector5.x);
        if (f3 == 0.0f) {
            return Float.POSITIVE_INFINITY;
        }
        return (f * (vector5.y / f3)) - (f2 * (vector5.x / f3));
    }

    public static boolean intersectRayPlane(Ray ray, Plane plane, Vector3 vector3) {
        float fDot = ray.direction.dot(plane.getNormal());
        if (fDot != 0.0f) {
            float f = (-(ray.origin.dot(plane.getNormal()) + plane.getD())) / fDot;
            if (f < 0.0f) {
                return false;
            }
            if (vector3 != null) {
                vector3.set(ray.origin).add(v0.set(ray.direction).scl(f));
            }
            return true;
        }
        if (plane.testPoint(ray.origin) != Plane.PlaneSide.OnPlane) {
            return false;
        }
        if (vector3 != null) {
            vector3.set(ray.origin);
        }
        return true;
    }

    public static float intersectLinePlane(float f, float f2, float f3, float f4, float f5, float f6, Plane plane, Vector3 vector3) {
        Vector3 vector3Sub = tmp.set(f4, f5, f6).sub(f, f2, f3);
        Vector3 vector4 = tmp2.set(f, f2, f3);
        float fDot = vector3Sub.dot(plane.getNormal());
        if (fDot != 0.0f) {
            float f7 = (-(vector4.dot(plane.getNormal()) + plane.getD())) / fDot;
            if (vector3 != null) {
                vector3.set(vector4).add(vector3Sub.scl(f7));
            }
            return f7;
        }
        if (plane.testPoint(vector4) != Plane.PlaneSide.OnPlane) {
            return -1.0f;
        }
        if (vector3 != null) {
            vector3.set(vector4);
        }
        return 0.0f;
    }

    public static boolean intersectPlanes(Plane plane, Plane plane2, Plane plane3, Vector3 vector3) {
        tmp1.set(plane.normal).crs(plane2.normal);
        tmp2.set(plane2.normal).crs(plane3.normal);
        tmp3.set(plane3.normal).crs(plane.normal);
        float f = -plane.normal.dot(tmp2);
        if (Math.abs(f) < 1.0E-6f) {
            return false;
        }
        tmp1.scl(plane3.d);
        tmp2.scl(plane.d);
        tmp3.scl(plane2.d);
        vector3.set(tmp1.x + tmp2.x + tmp3.x, tmp1.y + tmp2.y + tmp3.y, tmp1.z + tmp2.z + tmp3.z);
        vector3.scl(1.0f / f);
        return true;
    }

    public static boolean intersectRayTriangle(Ray ray, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        Vector3 vector3Sub = v0.set(vector4).sub(vector3);
        Vector3 vector3Sub2 = v1.set(vector5).sub(vector3);
        Vector3 vector3Crs = v2.set(ray.direction).crs(vector3Sub2);
        float fDot = vector3Sub.dot(vector3Crs);
        if (MathUtils.isZero(fDot)) {
            Plane plane = p;
            plane.set(vector3, vector4, vector5);
            if (plane.testPoint(ray.origin) != Plane.PlaneSide.OnPlane || !isPointInTriangle(ray.origin, vector3, vector4, vector5)) {
                return false;
            }
            if (vector6 != null) {
                vector6.set(ray.origin);
            }
            return true;
        }
        float f = 1.0f / fDot;
        Vector3 vector3Sub3 = i.set(ray.origin).sub(vector3);
        float fDot2 = vector3Sub3.dot(vector3Crs) * f;
        if (fDot2 >= 0.0f && fDot2 <= 1.0f) {
            Vector3 vector3Crs2 = vector3Sub3.crs(vector3Sub);
            float fDot3 = ray.direction.dot(vector3Crs2) * f;
            if (fDot3 >= 0.0f && fDot2 + fDot3 <= 1.0f) {
                float fDot4 = vector3Sub2.dot(vector3Crs2) * f;
                if (fDot4 < 0.0f) {
                    return false;
                }
                if (vector6 != null) {
                    if (fDot4 <= 1.0E-6f) {
                        vector6.set(ray.origin);
                    } else {
                        ray.getEndPoint(vector6, fDot4);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean intersectRaySphere(Ray ray, Vector3 vector3, float f, Vector3 vector4) {
        float fDot = ray.direction.dot(vector3.x - ray.origin.x, vector3.y - ray.origin.y, vector3.z - ray.origin.z);
        if (fDot < 0.0f) {
            return false;
        }
        float fDst2 = vector3.dst2(ray.origin.x + (ray.direction.x * fDot), ray.origin.y + (ray.direction.y * fDot), ray.origin.z + (ray.direction.z * fDot));
        float f2 = f * f;
        if (fDst2 > f2) {
            return false;
        }
        if (vector4 == null) {
            return true;
        }
        vector4.set(ray.direction).scl(fDot - ((float) Math.sqrt(f2 - fDst2))).add(ray.origin);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:113:0x0279  */
    /* JADX WARN: Code duplicated, block: B:22:0x0074  */
    public static boolean intersectRayBounds(Ray ray, BoundingBox boundingBox, Vector3 vector3) {
        boolean z;
        float f;
        boolean z2 = true;
        if (boundingBox.contains(ray.origin)) {
            if (vector3 != null) {
                vector3.set(ray.origin);
            }
            return true;
        }
        if (ray.origin.x > boundingBox.min.x || ray.direction.x <= 0.0f) {
            z = false;
            f = 0.0f;
        } else {
            float f2 = (boundingBox.min.x - ray.origin.x) / ray.direction.x;
            if (f2 >= 0.0f) {
                Vector3 vector4 = v2;
                vector4.set(ray.direction).scl(f2).add(ray.origin);
                if (vector4.y < boundingBox.min.y || vector4.y > boundingBox.max.y || vector4.z < boundingBox.min.z || vector4.z > boundingBox.max.z) {
                    z = false;
                    f = 0.0f;
                } else {
                    f = f2;
                    z = true;
                }
            } else {
                z = false;
                f = 0.0f;
            }
        }
        if (ray.origin.x >= boundingBox.max.x && ray.direction.x < 0.0f) {
            float f3 = (boundingBox.max.x - ray.origin.x) / ray.direction.x;
            if (f3 >= 0.0f) {
                Vector3 vector5 = v2;
                vector5.set(ray.direction).scl(f3).add(ray.origin);
                if (vector5.y >= boundingBox.min.y && vector5.y <= boundingBox.max.y && vector5.z >= boundingBox.min.z && vector5.z <= boundingBox.max.z && (!z || f3 < f)) {
                    z = true;
                    f = f3;
                }
            }
        }
        if (ray.origin.y <= boundingBox.min.y && ray.direction.y > 0.0f) {
            float f4 = (boundingBox.min.y - ray.origin.y) / ray.direction.y;
            if (f4 >= 0.0f) {
                Vector3 vector6 = v2;
                vector6.set(ray.direction).scl(f4).add(ray.origin);
                if (vector6.x >= boundingBox.min.x && vector6.x <= boundingBox.max.x && vector6.z >= boundingBox.min.z && vector6.z <= boundingBox.max.z && (!z || f4 < f)) {
                    z = true;
                    f = f4;
                }
            }
        }
        if (ray.origin.y >= boundingBox.max.y && ray.direction.y < 0.0f) {
            float f5 = (boundingBox.max.y - ray.origin.y) / ray.direction.y;
            if (f5 >= 0.0f) {
                Vector3 vector7 = v2;
                vector7.set(ray.direction).scl(f5).add(ray.origin);
                if (vector7.x >= boundingBox.min.x && vector7.x <= boundingBox.max.x && vector7.z >= boundingBox.min.z && vector7.z <= boundingBox.max.z && (!z || f5 < f)) {
                    z = true;
                    f = f5;
                }
            }
        }
        if (ray.origin.z <= boundingBox.min.z && ray.direction.z > 0.0f) {
            float f6 = (boundingBox.min.z - ray.origin.z) / ray.direction.z;
            if (f6 >= 0.0f) {
                Vector3 vector8 = v2;
                vector8.set(ray.direction).scl(f6).add(ray.origin);
                if (vector8.x >= boundingBox.min.x && vector8.x <= boundingBox.max.x && vector8.y >= boundingBox.min.y && vector8.y <= boundingBox.max.y && (!z || f6 < f)) {
                    z = true;
                    f = f6;
                }
            }
        }
        if (ray.origin.z < boundingBox.max.z || ray.direction.z >= 0.0f) {
            z2 = z;
        } else {
            float f7 = (boundingBox.max.z - ray.origin.z) / ray.direction.z;
            if (f7 >= 0.0f) {
                Vector3 vector9 = v2;
                vector9.set(ray.direction).scl(f7).add(ray.origin);
                if (vector9.x < boundingBox.min.x || vector9.x > boundingBox.max.x || vector9.y < boundingBox.min.y || vector9.y > boundingBox.max.y || (z && f7 >= f)) {
                    z2 = z;
                } else {
                    f = f7;
                }
            } else {
                z2 = z;
            }
        }
        if (z2 && vector3 != null) {
            vector3.set(ray.direction).scl(f).add(ray.origin);
            if (vector3.x < boundingBox.min.x) {
                vector3.x = boundingBox.min.x;
            } else if (vector3.x > boundingBox.max.x) {
                vector3.x = boundingBox.max.x;
            }
            if (vector3.y < boundingBox.min.y) {
                vector3.y = boundingBox.min.y;
            } else if (vector3.y > boundingBox.max.y) {
                vector3.y = boundingBox.max.y;
            }
            if (vector3.z < boundingBox.min.z) {
                vector3.z = boundingBox.min.z;
            } else if (vector3.z > boundingBox.max.z) {
                vector3.z = boundingBox.max.z;
            }
        }
        return z2;
    }

    public static boolean intersectRayBoundsFast(Ray ray, BoundingBox boundingBox) {
        return intersectRayBoundsFast(ray, boundingBox.getCenter(tmp1), boundingBox.getDimensions(tmp2));
    }

    public static boolean intersectRayBoundsFast(Ray ray, Vector3 vector3, Vector3 vector4) {
        float f = 1.0f / ray.direction.x;
        float f2 = 1.0f / ray.direction.y;
        float f3 = 1.0f / ray.direction.z;
        float f4 = ((vector3.x - (vector4.x * 0.5f)) - ray.origin.x) * f;
        float f5 = ((vector3.x + (vector4.x * 0.5f)) - ray.origin.x) * f;
        if (f4 > f5) {
            f5 = f4;
            f4 = f5;
        }
        float f6 = ((vector3.y - (vector4.y * 0.5f)) - ray.origin.y) * f2;
        float f7 = ((vector3.y + (vector4.y * 0.5f)) - ray.origin.y) * f2;
        if (f6 > f7) {
            f7 = f6;
            f6 = f7;
        }
        float f8 = ((vector3.z - (vector4.z * 0.5f)) - ray.origin.z) * f3;
        float f9 = ((vector3.z + (vector4.z * 0.5f)) - ray.origin.z) * f3;
        if (f8 > f9) {
            f8 = f9;
            f9 = f8;
        }
        float fMax = Math.max(Math.max(f4, f6), f8);
        float fMin = Math.min(Math.min(f5, f7), f9);
        return fMin >= 0.0f && fMin >= fMax;
    }

    public static boolean intersectRayOrientedBoundsFast(Ray ray, OrientedBoundingBox orientedBoundingBox) {
        return intersectRayOrientedBounds(ray, orientedBoundingBox, null);
    }

    public static boolean intersectRayOrientedBoundsFast(Ray ray, BoundingBox boundingBox, Matrix4 matrix4) {
        return intersectRayOrientedBounds(ray, boundingBox, matrix4, null);
    }

    public static boolean intersectRayOrientedBounds(Ray ray, OrientedBoundingBox orientedBoundingBox, Vector3 vector3) {
        return intersectRayOrientedBounds(ray, orientedBoundingBox.getBounds(), orientedBoundingBox.getTransform(), vector3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x013b, code lost:
    
        if ((r2 + r18.max.z) >= 0.0f) goto L64;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean intersectRayOrientedBounds(com.badlogic.gdx.math.collision.Ray r17, com.badlogic.gdx.math.collision.BoundingBox r18, com.badlogic.gdx.math.Matrix4 r19, com.badlogic.gdx.math.Vector3 r20) {
        /*
            Method dump skipped, instruction units count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.math.Intersector.intersectRayOrientedBounds(com.badlogic.gdx.math.collision.Ray, com.badlogic.gdx.math.collision.BoundingBox, com.badlogic.gdx.math.Matrix4, com.badlogic.gdx.math.Vector3):boolean");
    }

    public static boolean intersectRayTriangles(Ray ray, float[] fArr, Vector3 vector3) {
        if (fArr.length % 9 != 0) {
            throw new RuntimeException("triangles array size is not a multiple of 9");
        }
        float f = Float.MAX_VALUE;
        boolean z = false;
        for (int i2 = 0; i2 < fArr.length; i2 += 9) {
            if (intersectRayTriangle(ray, tmp1.set(fArr[i2], fArr[i2 + 1], fArr[i2 + 2]), tmp2.set(fArr[i2 + 3], fArr[i2 + 4], fArr[i2 + 5]), tmp3.set(fArr[i2 + 6], fArr[i2 + 7], fArr[i2 + 8]), tmp)) {
                float fDst2 = ray.origin.dst2(tmp);
                if (fDst2 < f) {
                    best.set(tmp);
                    f = fDst2;
                    z = true;
                }
            }
        }
        if (!z) {
            return false;
        }
        if (vector3 != null) {
            vector3.set(best);
        }
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, float[] fArr, short[] sArr, int i2, Vector3 vector3) {
        if (sArr.length % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        float f = Float.MAX_VALUE;
        boolean z = false;
        for (int i3 = 0; i3 < sArr.length; i3 += 3) {
            int i4 = sArr[i3] * i2;
            int i5 = sArr[i3 + 1] * i2;
            int i6 = sArr[i3 + 2] * i2;
            if (intersectRayTriangle(ray, tmp1.set(fArr[i4], fArr[i4 + 1], fArr[i4 + 2]), tmp2.set(fArr[i5], fArr[i5 + 1], fArr[i5 + 2]), tmp3.set(fArr[i6], fArr[i6 + 1], fArr[i6 + 2]), tmp)) {
                float fDst2 = ray.origin.dst2(tmp);
                if (fDst2 < f) {
                    best.set(tmp);
                    f = fDst2;
                    z = true;
                }
            }
        }
        if (!z) {
            return false;
        }
        if (vector3 != null) {
            vector3.set(best);
        }
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, List<Vector3> list, Vector3 vector3) {
        if (list.size() % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        float f = Float.MAX_VALUE;
        boolean z = false;
        for (int i2 = 0; i2 < list.size(); i2 += 3) {
            if (intersectRayTriangle(ray, list.get(i2), list.get(i2 + 1), list.get(i2 + 2), tmp)) {
                float fDst2 = ray.origin.dst2(tmp);
                if (fDst2 < f) {
                    best.set(tmp);
                    f = fDst2;
                    z = true;
                }
            }
        }
        if (!z) {
            return false;
        }
        if (vector3 != null) {
            vector3.set(best);
        }
        return true;
    }

    public static boolean intersectBoundsPlaneFast(BoundingBox boundingBox, Plane plane) {
        return intersectBoundsPlaneFast(boundingBox.getCenter(tmp1), boundingBox.getDimensions(tmp2).scl(0.5f), plane.normal, plane.d);
    }

    public static boolean intersectBoundsPlaneFast(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f) {
        return Math.abs(vector5.dot(vector3) - f) <= ((vector4.x * Math.abs(vector5.x)) + (vector4.y * Math.abs(vector5.y))) + (vector4.z * Math.abs(vector5.z));
    }

    public static boolean intersectLines(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5, Vector2 vector6) {
        return intersectLines(vector2.x, vector2.y, vector3.x, vector3.y, vector4.x, vector4.y, vector5.x, vector5.y, vector6);
    }

    public static boolean intersectLines(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Vector2 vector2) {
        float f9 = f8 - f6;
        float f10 = f3 - f;
        float f11 = f7 - f5;
        float f12 = f4 - f2;
        float f13 = (f9 * f10) - (f11 * f12);
        if (f13 == 0.0f) {
            return false;
        }
        if (vector2 == null) {
            return true;
        }
        float f14 = ((f11 * (f2 - f6)) - (f9 * (f - f5))) / f13;
        vector2.set(f + (f10 * f14), f2 + (f12 * f14));
        return true;
    }

    public static boolean intersectLinePolygon(Vector2 vector2, Vector2 vector3, Polygon polygon) {
        float[] transformedVertices = polygon.getTransformedVertices();
        float f = vector2.x;
        float f2 = vector2.y;
        float f3 = vector3.x;
        float f4 = vector3.y;
        int length = transformedVertices.length;
        float f5 = transformedVertices[length - 2];
        float f6 = transformedVertices[length - 1];
        int i2 = 0;
        while (i2 < length) {
            float f7 = transformedVertices[i2];
            float f8 = transformedVertices[i2 + 1];
            float f9 = f8 - f6;
            float f10 = f7 - f5;
            float f11 = ((f3 - f) * f9) - ((f4 - f2) * f10);
            if (f11 != 0.0f) {
                float f12 = ((f10 * (f2 - f6)) - (f9 * (f - f5))) / f11;
                if (f12 >= 0.0f && f12 <= 1.0f) {
                    return true;
                }
            }
            i2 += 2;
            f5 = f7;
            f6 = f8;
        }
        return false;
    }

    public static boolean intersectRectangles(Rectangle rectangle, Rectangle rectangle2, Rectangle rectangle3) {
        if (!rectangle.overlaps(rectangle2)) {
            return false;
        }
        rectangle3.x = Math.max(rectangle.x, rectangle2.x);
        rectangle3.width = Math.min(rectangle.x + rectangle.width, rectangle2.x + rectangle2.width) - rectangle3.x;
        rectangle3.y = Math.max(rectangle.y, rectangle2.y);
        rectangle3.height = Math.min(rectangle.y + rectangle.height, rectangle2.y + rectangle2.height) - rectangle3.y;
        return true;
    }

    public static boolean intersectSegmentRectangle(float f, float f2, float f3, float f4, Rectangle rectangle) {
        float f5 = rectangle.x + rectangle.width;
        float f6 = rectangle.y + rectangle.height;
        if (intersectSegments(f, f2, f3, f4, rectangle.x, rectangle.y, rectangle.x, f6, null) || intersectSegments(f, f2, f3, f4, rectangle.x, rectangle.y, f5, rectangle.y, null) || intersectSegments(f, f2, f3, f4, f5, rectangle.y, f5, f6, null) || intersectSegments(f, f2, f3, f4, rectangle.x, f6, f5, f6, null)) {
            return true;
        }
        return rectangle.contains(f, f2);
    }

    public static boolean intersectSegmentRectangle(Vector2 vector2, Vector2 vector3, Rectangle rectangle) {
        return intersectSegmentRectangle(vector2.x, vector2.y, vector3.x, vector3.y, rectangle);
    }

    public static boolean intersectSegmentPolygon(Vector2 vector2, Vector2 vector3, Polygon polygon) {
        float[] transformedVertices = polygon.getTransformedVertices();
        float f = vector2.x;
        float f2 = vector2.y;
        float f3 = vector3.x;
        float f4 = vector3.y;
        int length = transformedVertices.length;
        float f5 = transformedVertices[length - 2];
        float f6 = transformedVertices[length - 1];
        int i2 = 0;
        while (i2 < length) {
            float f7 = transformedVertices[i2];
            float f8 = transformedVertices[i2 + 1];
            float f9 = f8 - f6;
            float f10 = f3 - f;
            float f11 = f7 - f5;
            float f12 = f4 - f2;
            float f13 = (f9 * f10) - (f11 * f12);
            if (f13 != 0.0f) {
                float f14 = f2 - f6;
                float f15 = f - f5;
                float f16 = ((f11 * f14) - (f9 * f15)) / f13;
                if (f16 >= 0.0f && f16 <= 1.0f) {
                    float f17 = ((f10 * f14) - (f12 * f15)) / f13;
                    if (f17 >= 0.0f && f17 <= 1.0f) {
                        return true;
                    }
                }
            }
            i2 += 2;
            f5 = f7;
            f6 = f8;
        }
        return false;
    }

    public static boolean intersectSegments(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5, Vector2 vector6) {
        return intersectSegments(vector2.x, vector2.y, vector3.x, vector3.y, vector4.x, vector4.y, vector5.x, vector5.y, vector6);
    }

    public static boolean intersectSegments(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Vector2 vector2) {
        float f9 = f8 - f6;
        float f10 = f3 - f;
        float f11 = f7 - f5;
        float f12 = f4 - f2;
        float f13 = (f9 * f10) - (f11 * f12);
        if (f13 == 0.0f) {
            return false;
        }
        float f14 = f2 - f6;
        float f15 = f - f5;
        float f16 = ((f11 * f14) - (f9 * f15)) / f13;
        if (f16 >= 0.0f && f16 <= 1.0f) {
            float f17 = ((f14 * f10) - (f15 * f12)) / f13;
            if (f17 >= 0.0f && f17 <= 1.0f) {
                if (vector2 == null) {
                    return true;
                }
                vector2.set(f + (f10 * f16), f2 + (f12 * f16));
                return true;
            }
        }
        return false;
    }

    public static boolean overlaps(Circle circle, Circle circle2) {
        return circle.overlaps(circle2);
    }

    public static boolean overlaps(Rectangle rectangle, Rectangle rectangle2) {
        return rectangle.overlaps(rectangle2);
    }

    public static boolean overlaps(Circle circle, Rectangle rectangle) {
        float f = circle.x;
        float f2 = circle.y;
        if (circle.x < rectangle.x) {
            f = rectangle.x;
        } else if (circle.x > rectangle.x + rectangle.width) {
            f = rectangle.x + rectangle.width;
        }
        if (circle.y < rectangle.y) {
            f2 = rectangle.y;
        } else if (circle.y > rectangle.y + rectangle.height) {
            f2 = rectangle.y + rectangle.height;
        }
        float f3 = f - circle.x;
        float f4 = f2 - circle.y;
        return (f3 * f3) + (f4 * f4) < circle.radius * circle.radius;
    }

    public static boolean overlapConvexPolygons(Polygon polygon, Polygon polygon2) {
        return overlapConvexPolygons(polygon, polygon2, (MinimumTranslationVector) null);
    }

    public static boolean overlapConvexPolygons(Polygon polygon, Polygon polygon2, MinimumTranslationVector minimumTranslationVector) {
        return overlapConvexPolygons(polygon.getTransformedVertices(), polygon2.getTransformedVertices(), minimumTranslationVector);
    }

    public static boolean overlapConvexPolygons(float[] fArr, float[] fArr2, MinimumTranslationVector minimumTranslationVector) {
        return overlapConvexPolygons(fArr, 0, fArr.length, fArr2, 0, fArr2.length, minimumTranslationVector);
    }

    public static boolean overlapConvexPolygons(float[] fArr, int i2, int i3, float[] fArr2, int i4, int i5, MinimumTranslationVector minimumTranslationVector) {
        if (minimumTranslationVector != null) {
            minimumTranslationVector.depth = Float.MAX_VALUE;
            minimumTranslationVector.normal.setZero();
        }
        boolean zOverlapsOnAxisOfShape = overlapsOnAxisOfShape(fArr2, i4, i5, fArr, i2, i3, minimumTranslationVector, true);
        if (zOverlapsOnAxisOfShape) {
            zOverlapsOnAxisOfShape = overlapsOnAxisOfShape(fArr, i2, i3, fArr2, i4, i5, minimumTranslationVector, false);
        }
        if (zOverlapsOnAxisOfShape) {
            return true;
        }
        if (minimumTranslationVector == null) {
            return false;
        }
        minimumTranslationVector.depth = 0.0f;
        minimumTranslationVector.normal.setZero();
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00bc A[PHI: r6
      0x00bc: PHI (r6v10 float) = (r6v4 float), (r6v11 float) binds: [B:53:0x00c9, B:43:0x00b9] A[DONT_GENERATE, DONT_INLINE]] */
    private static boolean overlapsOnAxisOfShape(float[] fArr, int i2, int i3, float[] fArr2, int i4, int i5, MinimumTranslationVector minimumTranslationVector, boolean z) {
        float f;
        float f2;
        int i6 = i2 + i3;
        int i7 = i4 + i5;
        int i8 = i2;
        while (i8 < i6) {
            float f3 = fArr[i8];
            float f4 = fArr[i8 + 1];
            int i9 = i8 + 2;
            float f5 = fArr[i9 % i3];
            float f6 = f4 - fArr[(i8 + 3) % i3];
            float f7 = -(f3 - f5);
            float fLen = Vector2.len(f6, f7);
            float f8 = f6 / fLen;
            float f9 = f7 / fLen;
            float fMax = -3.4028235E38f;
            float fMin = Float.MAX_VALUE;
            float fMax2 = -3.4028235E38f;
            float fMin2 = Float.MAX_VALUE;
            for (int i10 = i2; i10 < i6; i10 += 2) {
                float f10 = (fArr[i10] * f8) + (fArr[i10 + 1] * f9);
                fMin2 = Math.min(fMin2, f10);
                fMax2 = Math.max(fMax2, f10);
            }
            for (int i11 = i4; i11 < i7; i11 += 2) {
                float f11 = (fArr2[i11] * f8) + (fArr2[i11 + 1] * f9);
                fMin = Math.min(fMin, f11);
                fMax = Math.max(fMax, f11);
            }
            if (fMax2 < fMin || fMax < fMin2) {
                return false;
            }
            if (minimumTranslationVector != null) {
                float fMin3 = Math.min(fMax2, fMax) - Math.max(fMin2, fMin);
                boolean z2 = fMin2 < fMin && fMax2 > fMax;
                boolean z3 = fMin < fMin2 && fMax > fMax2;
                if (z2 || z3) {
                    float fAbs = Math.abs(fMin2 - fMin);
                    float fAbs2 = Math.abs(fMax2 - fMax);
                    fMin3 += Math.min(fAbs, fAbs2);
                    f = fAbs;
                    f2 = fAbs2;
                } else {
                    f = 0.0f;
                    f2 = 0.0f;
                }
                if (minimumTranslationVector.depth > fMin3) {
                    minimumTranslationVector.depth = fMin3;
                    if (z) {
                        boolean z4 = fMin2 < fMin;
                        if (!z4) {
                            f8 = -f8;
                        }
                        if (!z4) {
                            f9 = -f9;
                        }
                    } else {
                        boolean z5 = fMin2 > fMin;
                        if (!z5) {
                            f8 = -f8;
                        }
                        if (!z5) {
                            f9 = -f9;
                        }
                    }
                    if (z2 || z3) {
                        boolean z6 = f > f2;
                        if (!z6) {
                            f8 = -f8;
                        }
                        if (!z6) {
                            f9 = -f9;
                        }
                    }
                    minimumTranslationVector.normal.set(f8, f9);
                }
            }
            i8 = i9;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void splitTriangle(float[] fArr, Plane plane, SplitTriangle splitTriangle) {
        int length = fArr.length / 3;
        int i2 = plane.testPoint(fArr[0], fArr[1], fArr[2]) == Plane.PlaneSide.Back ? 1 : 0;
        int i3 = plane.testPoint(fArr[length], fArr[length + 1], fArr[length + 2]) == Plane.PlaneSide.Back ? 1 : 0;
        int i4 = length * 2;
        int i5 = plane.testPoint(fArr[i4], fArr[i4 + 1], fArr[i4 + 2]) == Plane.PlaneSide.Back ? 1 : 0;
        splitTriangle.reset();
        if (i2 == i3 && i3 == i5) {
            splitTriangle.total = 1;
            if (i2 != 0) {
                splitTriangle.numBack = 1;
                System.arraycopy(fArr, 0, splitTriangle.back, 0, fArr.length);
                return;
            } else {
                splitTriangle.numFront = 1;
                System.arraycopy(fArr, 0, splitTriangle.front, 0, fArr.length);
                return;
            }
        }
        splitTriangle.total = 3;
        splitTriangle.numFront = (i2 ^ 1) + (i3 ^ 1) + (i5 ^ 1);
        splitTriangle.numBack = splitTriangle.total - splitTriangle.numFront;
        splitTriangle.setSide(i2 ^ 1);
        if (i2 != i3) {
            splitEdge(fArr, 0, length, length, plane, splitTriangle.edgeSplit, 0);
            splitTriangle.add(fArr, 0, length);
            splitTriangle.add(splitTriangle.edgeSplit, 0, length);
            splitTriangle.setSide(!splitTriangle.getSide());
            splitTriangle.add(splitTriangle.edgeSplit, 0, length);
        } else {
            splitTriangle.add(fArr, 0, length);
        }
        int i6 = length + length;
        int i7 = i5;
        if (i3 != i7) {
            splitEdge(fArr, length, i6, length, plane, splitTriangle.edgeSplit, 0);
            splitTriangle.add(fArr, length, length);
            splitTriangle.add(splitTriangle.edgeSplit, 0, length);
            splitTriangle.setSide(!splitTriangle.getSide());
            splitTriangle.add(splitTriangle.edgeSplit, 0, length);
        } else {
            splitTriangle.add(fArr, length, length);
        }
        if (i7 != i2) {
            splitEdge(fArr, i6, 0, length, plane, splitTriangle.edgeSplit, 0);
            splitTriangle.add(fArr, i6, length);
            splitTriangle.add(splitTriangle.edgeSplit, 0, length);
            splitTriangle.setSide(!splitTriangle.getSide());
            splitTriangle.add(splitTriangle.edgeSplit, 0, length);
        } else {
            splitTriangle.add(fArr, i6, length);
        }
        if (splitTriangle.numFront == 2) {
            System.arraycopy(splitTriangle.front, i4, splitTriangle.front, length * 3, i4);
            System.arraycopy(splitTriangle.front, 0, splitTriangle.front, length * 5, length);
        } else {
            System.arraycopy(splitTriangle.back, i4, splitTriangle.back, length * 3, i4);
            System.arraycopy(splitTriangle.back, 0, splitTriangle.back, length * 5, length);
        }
    }

    private static void splitEdge(float[] fArr, int i2, int i3, int i4, Plane plane, float[] fArr2, int i5) {
        float fIntersectLinePlane = intersectLinePlane(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i3], fArr[i3 + 1], fArr[i3 + 2], plane, intersection);
        fArr2[i5] = intersection.x;
        fArr2[i5 + 1] = intersection.y;
        fArr2[i5 + 2] = intersection.z;
        for (int i6 = 3; i6 < i4; i6++) {
            float f = fArr[i2 + i6];
            fArr2[i5 + i6] = f + ((fArr[i3 + i6] - f) * fIntersectLinePlane);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Intersector$SplitTriangle.class
      classes8.dex
     */
    public static class SplitTriangle {
        public float[] back;
        float[] edgeSplit;
        public float[] front;
        public int numBack;
        public int numFront;
        public int total;
        boolean frontCurrent = false;
        int frontOffset = 0;
        int backOffset = 0;

        public SplitTriangle(int i) {
            int i2 = i * 6;
            this.front = new float[i2];
            this.back = new float[i2];
            this.edgeSplit = new float[i];
        }

        public String toString() {
            return "SplitTriangle [front=" + Arrays.toString(this.front) + ", back=" + Arrays.toString(this.back) + ", numFront=" + this.numFront + ", numBack=" + this.numBack + ", total=" + this.total + "]";
        }

        void setSide(boolean z) {
            this.frontCurrent = z;
        }

        boolean getSide() {
            return this.frontCurrent;
        }

        void add(float[] fArr, int i, int i2) {
            if (this.frontCurrent) {
                System.arraycopy(fArr, i, this.front, this.frontOffset, i2);
                this.frontOffset += i2;
            } else {
                System.arraycopy(fArr, i, this.back, this.backOffset, i2);
                this.backOffset += i2;
            }
        }

        void reset() {
            this.frontCurrent = false;
            this.frontOffset = 0;
            this.backOffset = 0;
            this.numFront = 0;
            this.numBack = 0;
            this.total = 0;
        }
    }

    public static boolean hasOverlap(Vector3[] vector3Arr, Vector3[] vector3Arr2, Vector3[] vector3Arr3) {
        for (Vector3 vector3 : vector3Arr) {
            float fMax = -3.4028235E38f;
            float fMin = Float.MAX_VALUE;
            float fMax2 = -3.4028235E38f;
            float fMin2 = Float.MAX_VALUE;
            for (Vector3 vector4 : vector3Arr2) {
                float fDot = vector4.dot(vector3);
                fMin2 = Math.min(fMin2, fDot);
                fMax2 = Math.max(fMax2, fDot);
            }
            for (Vector3 vector5 : vector3Arr3) {
                float fDot2 = vector5.dot(vector3);
                fMin = Math.min(fMin, fDot2);
                fMax = Math.max(fMax, fDot2);
            }
            if (fMax2 < fMin || fMax < fMin2) {
                return false;
            }
        }
        return true;
    }
}
