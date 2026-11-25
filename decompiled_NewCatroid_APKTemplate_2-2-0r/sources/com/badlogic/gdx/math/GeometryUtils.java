package com.badlogic.gdx.math;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/GeometryUtils.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class GeometryUtils {
    private static final Vector2 tmp1 = new Vector2();
    private static final Vector2 tmp2 = new Vector2();
    private static final Vector2 tmp3 = new Vector2();

    private GeometryUtils() {
    }

    public static Vector2 toBarycoord(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5, Vector2 vector6) {
        Vector2 vector2Sub = tmp1.set(vector4).sub(vector3);
        Vector2 vector2Sub2 = tmp2.set(vector5).sub(vector3);
        Vector2 vector2Sub3 = tmp3.set(vector2).sub(vector3);
        float fDot = vector2Sub.dot(vector2Sub);
        float fDot2 = vector2Sub.dot(vector2Sub2);
        float fDot3 = vector2Sub2.dot(vector2Sub2);
        float fDot4 = vector2Sub3.dot(vector2Sub);
        float fDot5 = vector2Sub3.dot(vector2Sub2);
        float f = (fDot * fDot3) - (fDot2 * fDot2);
        vector6.x = ((fDot3 * fDot4) - (fDot2 * fDot5)) / f;
        vector6.y = ((fDot * fDot5) - (fDot2 * fDot4)) / f;
        return vector6;
    }

    public static boolean barycoordInsideTriangle(Vector2 vector2) {
        return vector2.x >= 0.0f && vector2.y >= 0.0f && vector2.x + vector2.y <= 1.0f;
    }

    public static Vector2 fromBarycoord(Vector2 vector2, Vector2 vector3, Vector2 vector4, Vector2 vector5, Vector2 vector6) {
        float f = (1.0f - vector2.x) - vector2.y;
        vector6.x = (vector3.x * f) + (vector2.x * vector4.x) + (vector2.y * vector5.x);
        vector6.y = (f * vector3.y) + (vector2.x * vector4.y) + (vector2.y * vector5.y);
        return vector6;
    }

    public static float fromBarycoord(Vector2 vector2, float f, float f2, float f3) {
        return (((1.0f - vector2.x) - vector2.y) * f) + (vector2.x * f2) + (vector2.y * f3);
    }

    public static float lowestPositiveRoot(float f, float f2, float f3) {
        float f4 = (f2 * f2) - ((4.0f * f) * f3);
        if (f4 < 0.0f) {
            return Float.NaN;
        }
        float fSqrt = (float) Math.sqrt(f4);
        float f5 = 1.0f / (f * 2.0f);
        float f6 = -f2;
        float f7 = (f6 - fSqrt) * f5;
        float f8 = (f6 + fSqrt) * f5;
        if (f7 > f8) {
            f7 = f8;
            f8 = f7;
        }
        if (f7 > 0.0f) {
            return f7;
        }
        if (f8 > 0.0f) {
            return f8;
        }
        return Float.NaN;
    }

    public static boolean colinear(float f, float f2, float f3, float f4, float f5, float f6) {
        return Math.abs(((f5 - f3) * (f4 - f2)) - ((f3 - f) * (f6 - f4))) < 1.0E-6f;
    }

    public static Vector2 triangleCentroid(float f, float f2, float f3, float f4, float f5, float f6, Vector2 vector2) {
        vector2.x = ((f + f3) + f5) / 3.0f;
        vector2.y = ((f2 + f4) + f6) / 3.0f;
        return vector2;
    }

    public static Vector2 triangleCircumcenter(float f, float f2, float f3, float f4, float f5, float f6, Vector2 vector2) {
        float f7 = f3 - f;
        float f8 = f4 - f2;
        float f9 = f5 - f3;
        float f10 = f6 - f4;
        float f11 = f - f5;
        float f12 = f2 - f6;
        float f13 = (f9 * f8) - (f7 * f10);
        if (Math.abs(f13) < 1.0E-6f) {
            throw new IllegalArgumentException("Triangle points must not be colinear.");
        }
        float f14 = f13 * 2.0f;
        float f15 = (f * f) + (f2 * f2);
        float f16 = (f3 * f3) + (f4 * f4);
        float f17 = (f5 * f5) + (f6 * f6);
        vector2.set((((f10 * f15) + (f12 * f16)) + (f8 * f17)) / f14, (-(((f15 * f9) + (f16 * f11)) + (f17 * f7))) / f14);
        return vector2;
    }

    public static float triangleCircumradius(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7;
        float f8;
        float f9 = f4 - f2;
        if (Math.abs(f9) < 1.0E-6f) {
            float f10 = (-(f5 - f3)) / (f6 - f4);
            float f11 = (f5 + f3) / 2.0f;
            f7 = (f3 + f) / 2.0f;
            f8 = (f10 * (f7 - f11)) + ((f4 + f6) / 2.0f);
        } else {
            float f12 = f6 - f4;
            if (Math.abs(f12) < 1.0E-6f) {
                float f13 = (-(f3 - f)) / f9;
                float f14 = (f + f3) / 2.0f;
                f7 = (f5 + f3) / 2.0f;
                f8 = (f13 * (f7 - f14)) + ((f4 + f2) / 2.0f);
            } else {
                float f15 = (-(f3 - f)) / f9;
                float f16 = (-(f5 - f3)) / f12;
                float f17 = (f + f3) / 2.0f;
                float f18 = (f3 + f5) / 2.0f;
                float f19 = (f2 + f4) / 2.0f;
                f7 = ((((f15 * f17) - (f18 * f16)) + ((f4 + f6) / 2.0f)) - f19) / (f15 - f16);
                f8 = (f15 * (f7 - f17)) + f19;
            }
        }
        float f20 = f - f7;
        float f21 = f2 - f8;
        return (float) Math.sqrt((f20 * f20) + (f21 * f21));
    }

    public static float triangleQuality(float f, float f2, float f3, float f4, float f5, float f6) {
        return ((float) Math.sqrt(Math.min((f * f) + (f2 * f2), Math.min((f3 * f3) + (f4 * f4), (f5 * f5) + (f6 * f6))))) / triangleCircumradius(f, f2, f3, f4, f5, f6);
    }

    public static float triangleArea(float f, float f2, float f3, float f4, float f5, float f6) {
        return Math.abs(((f - f5) * (f4 - f2)) - ((f - f3) * (f6 - f2))) * 0.5f;
    }

    public static Vector2 quadrilateralCentroid(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Vector2 vector2) {
        float f9 = ((f3 + f) + f5) / 3.0f;
        float f10 = ((f4 + f2) + f6) / 3.0f;
        vector2.x = f9 - ((f9 - (((f + f7) + f5) / 3.0f)) / 2.0f);
        vector2.y = f10 - ((f10 - (((f2 + f8) + f6) / 3.0f)) / 2.0f);
        return vector2;
    }

    public static Vector2 polygonCentroid(float[] fArr, int i, int i2, Vector2 vector2) {
        if (i2 < 6) {
            throw new IllegalArgumentException("A polygon must have 3 or more coordinate pairs.");
        }
        int i3 = i2 + i;
        int i4 = i3 - 2;
        float f = fArr[i4];
        float f2 = fArr[i3 - 1];
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        while (i <= i4) {
            float f6 = fArr[i];
            float f7 = fArr[i + 1];
            float f8 = (f * f7) - (f6 * f2);
            f3 += f8;
            f4 += (f + f6) * f8;
            f5 += (f2 + f7) * f8;
            i += 2;
            f = f6;
            f2 = f7;
        }
        if (f3 == 0.0f) {
            vector2.x = 0.0f;
            vector2.y = 0.0f;
        } else {
            float f9 = f3 * 0.5f * 6.0f;
            vector2.x = f4 / f9;
            vector2.y = f5 / f9;
        }
        return vector2;
    }

    public static float polygonArea(float[] fArr, int i, int i2) {
        int i3 = i2 + i;
        int i4 = i3 - 2;
        float f = fArr[i4];
        float f2 = fArr[i3 - 1];
        float f3 = 0.0f;
        while (i <= i4) {
            float f4 = fArr[i];
            float f5 = fArr[i + 1];
            f3 += (f * f5) - (f2 * f4);
            i += 2;
            f = f4;
            f2 = f5;
        }
        return f3 * 0.5f;
    }

    public static void ensureCCW(float[] fArr) {
        ensureCCW(fArr, 0, fArr.length);
    }

    public static void ensureCCW(float[] fArr, int i, int i2) {
        if (isClockwise(fArr, i, i2)) {
            reverseVertices(fArr, i, i2);
        }
    }

    public static void ensureClockwise(float[] fArr) {
        ensureClockwise(fArr, 0, fArr.length);
    }

    public static void ensureClockwise(float[] fArr, int i, int i2) {
        if (isClockwise(fArr, i, i2)) {
            return;
        }
        reverseVertices(fArr, i, i2);
    }

    public static void reverseVertices(float[] fArr, int i, int i2) {
        int i3 = (i + i2) - 2;
        int i4 = (i2 / 2) + i;
        while (i < i4) {
            int i5 = i3 - i;
            float f = fArr[i];
            int i6 = i + 1;
            float f2 = fArr[i6];
            fArr[i] = fArr[i5];
            int i7 = i5 + 1;
            fArr[i6] = fArr[i7];
            fArr[i5] = f;
            fArr[i7] = f2;
            i += 2;
        }
    }

    public static boolean isClockwise(float[] fArr, int i, int i2) {
        if (i2 <= 2) {
            return false;
        }
        int i3 = i2 + i;
        int i4 = i3 - 2;
        float f = fArr[i4];
        float f2 = fArr[i3 - 1];
        float f3 = 0.0f;
        while (i <= i4) {
            float f4 = fArr[i];
            float f5 = fArr[i + 1];
            f3 += (f * f5) - (f2 * f4);
            i += 2;
            f = f4;
            f2 = f5;
        }
        return f3 < 0.0f;
    }

    public static boolean isCCW(float[] fArr, int i, int i2) {
        return !isClockwise(fArr, i, i2);
    }
}
