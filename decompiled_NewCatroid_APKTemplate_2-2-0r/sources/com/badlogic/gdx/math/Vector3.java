package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.NumberUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Vector3.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Vector3 implements Serializable, Vector<Vector3> {
    private static final long serialVersionUID = 3840054589595372522L;
    public float x;
    public float y;
    public float z;
    public static final Vector3 X = new Vector3(1.0f, 0.0f, 0.0f);
    public static final Vector3 Y = new Vector3(0.0f, 1.0f, 0.0f);
    public static final Vector3 Z = new Vector3(0.0f, 0.0f, 1.0f);
    public static final Vector3 Zero = new Vector3(0.0f, 0.0f, 0.0f);
    private static final Matrix4 tmpMat = new Matrix4();

    public static float dot(float f, float f2, float f3, float f4, float f5, float f6) {
        return (f * f4) + (f2 * f5) + (f3 * f6);
    }

    public static float dst2(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f4 - f;
        float f8 = f5 - f2;
        float f9 = f6 - f3;
        return (f7 * f7) + (f8 * f8) + (f9 * f9);
    }

    public static float len2(float f, float f2, float f3) {
        return (f * f) + (f2 * f2) + (f3 * f3);
    }

    public Vector3() {
    }

    public Vector3(float f, float f2, float f3) {
        set(f, f2, f3);
    }

    public Vector3(Vector3 vector3) {
        set(vector3);
    }

    public Vector3(float[] fArr) {
        set(fArr[0], fArr[1], fArr[2]);
    }

    public Vector3(Vector2 vector2, float f) {
        set(vector2.x, vector2.y, f);
    }

    public Vector3 set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 set(Vector3 vector3) {
        return set(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 set(float[] fArr) {
        return set(fArr[0], fArr[1], fArr[2]);
    }

    public Vector3 set(Vector2 vector2, float f) {
        return set(vector2.x, vector2.y, f);
    }

    public Vector3 setFromSpherical(float f, float f2) {
        float fCos = MathUtils.cos(f2);
        float fSin = MathUtils.sin(f2);
        return set(MathUtils.cos(f) * fSin, MathUtils.sin(f) * fSin, fCos);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 setToRandomDirection() {
        return setFromSpherical(MathUtils.random() * 6.2831855f, (float) Math.acos((MathUtils.random() * 2.0f) - 1.0f));
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 cpy() {
        return new Vector3(this);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 add(Vector3 vector3) {
        return add(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 add(float f, float f2, float f3) {
        return set(this.x + f, this.y + f2, this.z + f3);
    }

    public Vector3 add(float f) {
        return set(this.x + f, this.y + f, this.z + f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 sub(Vector3 vector3) {
        return sub(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 sub(float f, float f2, float f3) {
        return set(this.x - f, this.y - f2, this.z - f3);
    }

    public Vector3 sub(float f) {
        return set(this.x - f, this.y - f, this.z - f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 scl(float f) {
        return set(this.x * f, this.y * f, this.z * f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 scl(Vector3 vector3) {
        return set(this.x * vector3.x, this.y * vector3.y, this.z * vector3.z);
    }

    public Vector3 scl(float f, float f2, float f3) {
        return set(this.x * f, this.y * f2, this.z * f3);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 mulAdd(Vector3 vector3, float f) {
        this.x += vector3.x * f;
        this.y += vector3.y * f;
        this.z += vector3.z * f;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 mulAdd(Vector3 vector3, Vector3 vector4) {
        this.x += vector3.x * vector4.x;
        this.y += vector3.y * vector4.y;
        this.z += vector3.z * vector4.z;
        return this;
    }

    public static float len(float f, float f2, float f3) {
        return (float) Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float len() {
        float f = this.x;
        float f2 = this.y;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.z;
        return (float) Math.sqrt(f3 + (f4 * f4));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float len2() {
        float f = this.x;
        float f2 = this.y;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.z;
        return f3 + (f4 * f4);
    }

    public boolean idt(Vector3 vector3) {
        return this.x == vector3.x && this.y == vector3.y && this.z == vector3.z;
    }

    public static float dst(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f4 - f;
        float f8 = f5 - f2;
        float f9 = f6 - f3;
        return (float) Math.sqrt((f7 * f7) + (f8 * f8) + (f9 * f9));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float dst(Vector3 vector3) {
        float f = vector3.x - this.x;
        float f2 = vector3.y - this.y;
        float f3 = vector3.z - this.z;
        return (float) Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
    }

    public float dst(float f, float f2, float f3) {
        float f4 = f - this.x;
        float f5 = f2 - this.y;
        float f6 = f3 - this.z;
        return (float) Math.sqrt((f4 * f4) + (f5 * f5) + (f6 * f6));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float dst2(Vector3 vector3) {
        float f = vector3.x - this.x;
        float f2 = vector3.y - this.y;
        float f3 = vector3.z - this.z;
        return (f * f) + (f2 * f2) + (f3 * f3);
    }

    public float dst2(float f, float f2, float f3) {
        float f4 = f - this.x;
        float f5 = f2 - this.y;
        float f6 = f3 - this.z;
        return (f4 * f4) + (f5 * f5) + (f6 * f6);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 nor() {
        float fLen2 = len2();
        return (fLen2 == 0.0f || fLen2 == 1.0f) ? this : scl(1.0f / ((float) Math.sqrt(fLen2)));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float dot(Vector3 vector3) {
        return (this.x * vector3.x) + (this.y * vector3.y) + (this.z * vector3.z);
    }

    public float dot(float f, float f2, float f3) {
        return (this.x * f) + (this.y * f2) + (this.z * f3);
    }

    public Vector3 crs(Vector3 vector3) {
        float f = this.y;
        float f2 = vector3.z;
        float f3 = this.z;
        float f4 = vector3.y;
        float f5 = (f * f2) - (f3 * f4);
        float f6 = vector3.x;
        float f7 = this.x;
        return set(f5, (f3 * f6) - (f2 * f7), (f7 * f4) - (f * f6));
    }

    public Vector3 crs(float f, float f2, float f3) {
        float f4 = this.y;
        float f5 = this.z;
        float f6 = (f4 * f3) - (f5 * f2);
        float f7 = this.x;
        return set(f6, (f5 * f) - (f3 * f7), (f7 * f2) - (f4 * f));
    }

    public Vector3 mul4x3(float[] fArr) {
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[3] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[6] * f5) + fArr[9], (fArr[1] * f) + (fArr[4] * f3) + (fArr[7] * f5) + fArr[10], (f * fArr[2]) + (f3 * fArr[5]) + (f5 * fArr[8]) + fArr[11]);
    }

    public Vector3 mul(Matrix4 matrix4) {
        float[] fArr = matrix4.val;
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[4] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[8] * f5) + fArr[12], (fArr[1] * f) + (fArr[5] * f3) + (fArr[9] * f5) + fArr[13], (f * fArr[2]) + (f3 * fArr[6]) + (f5 * fArr[10]) + fArr[14]);
    }

    public Vector3 traMul(Matrix4 matrix4) {
        float[] fArr = matrix4.val;
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[1] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[2] * f5) + fArr[3], (fArr[4] * f) + (fArr[5] * f3) + (fArr[6] * f5) + fArr[7], (f * fArr[8]) + (f3 * fArr[9]) + (f5 * fArr[10]) + fArr[11]);
    }

    public Vector3 mul(Matrix3 matrix3) {
        float[] fArr = matrix3.val;
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[3] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[6] * f5), (fArr[1] * f) + (fArr[4] * f3) + (fArr[7] * f5), (f * fArr[2]) + (f3 * fArr[5]) + (f5 * fArr[8]));
    }

    public Vector3 traMul(Matrix3 matrix3) {
        float[] fArr = matrix3.val;
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[1] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[2] * f5), (fArr[3] * f) + (fArr[4] * f3) + (fArr[5] * f5), (f * fArr[6]) + (f3 * fArr[7]) + (f5 * fArr[8]));
    }

    public Vector3 mul(Quaternion quaternion) {
        return quaternion.transform(this);
    }

    public Vector3 prj(Matrix4 matrix4) {
        float[] fArr = matrix4.val;
        float f = this.x;
        float f2 = fArr[3] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[7] * f3);
        float f5 = this.z;
        float f6 = 1.0f / ((f4 + (fArr[11] * f5)) + fArr[15]);
        return set(((fArr[0] * f) + (fArr[4] * f3) + (fArr[8] * f5) + fArr[12]) * f6, ((fArr[1] * f) + (fArr[5] * f3) + (fArr[9] * f5) + fArr[13]) * f6, ((f * fArr[2]) + (f3 * fArr[6]) + (f5 * fArr[10]) + fArr[14]) * f6);
    }

    public Vector3 rot(Matrix4 matrix4) {
        float[] fArr = matrix4.val;
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[4] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[8] * f5), (fArr[1] * f) + (fArr[5] * f3) + (fArr[9] * f5), (f * fArr[2]) + (f3 * fArr[6]) + (f5 * fArr[10]));
    }

    public Vector3 unrotate(Matrix4 matrix4) {
        float[] fArr = matrix4.val;
        float f = this.x;
        float f2 = fArr[0] * f;
        float f3 = this.y;
        float f4 = f2 + (fArr[1] * f3);
        float f5 = this.z;
        return set(f4 + (fArr[2] * f5), (fArr[4] * f) + (fArr[5] * f3) + (fArr[6] * f5), (f * fArr[8]) + (f3 * fArr[9]) + (f5 * fArr[10]));
    }

    public Vector3 untransform(Matrix4 matrix4) {
        float[] fArr = matrix4.val;
        float f = this.x;
        float f2 = fArr[12];
        float f3 = f - f2;
        this.x = f3;
        float f4 = this.y - f2;
        this.y = f4;
        float f5 = this.z - f2;
        this.z = f5;
        return set((fArr[0] * f3) + (fArr[1] * f4) + (fArr[2] * f5), (fArr[4] * f3) + (fArr[5] * f4) + (fArr[6] * f5), (f3 * fArr[8]) + (f4 * fArr[9]) + (f5 * fArr[10]));
    }

    public Vector3 rotate(float f, float f2, float f3, float f4) {
        return mul(tmpMat.setToRotation(f2, f3, f4, f));
    }

    public Vector3 rotateRad(float f, float f2, float f3, float f4) {
        return mul(tmpMat.setToRotationRad(f2, f3, f4, f));
    }

    public Vector3 rotate(Vector3 vector3, float f) {
        Matrix4 matrix4 = tmpMat;
        matrix4.setToRotation(vector3, f);
        return mul(matrix4);
    }

    public Vector3 rotateRad(Vector3 vector3, float f) {
        Matrix4 matrix4 = tmpMat;
        matrix4.setToRotationRad(vector3, f);
        return mul(matrix4);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isUnit() {
        return isUnit(1.0E-9f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isUnit(float f) {
        return Math.abs(len2() - 1.0f) < f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isZero() {
        return this.x == 0.0f && this.y == 0.0f && this.z == 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isZero(float f) {
        return len2() < f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isOnLine(Vector3 vector3, float f) {
        float f2 = this.y;
        float f3 = vector3.z;
        float f4 = this.z;
        float f5 = vector3.y;
        float f6 = (f2 * f3) - (f4 * f5);
        float f7 = vector3.x;
        float f8 = this.x;
        return len2(f6, (f4 * f7) - (f3 * f8), (f8 * f5) - (f2 * f7)) <= f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isOnLine(Vector3 vector3) {
        float f = this.y;
        float f2 = vector3.z;
        float f3 = this.z;
        float f4 = vector3.y;
        float f5 = (f * f2) - (f3 * f4);
        float f6 = vector3.x;
        float f7 = this.x;
        return len2(f5, (f3 * f6) - (f2 * f7), (f7 * f4) - (f * f6)) <= 1.0E-6f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinear(Vector3 vector3, float f) {
        return isOnLine(vector3, f) && hasSameDirection(vector3);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinear(Vector3 vector3) {
        return isOnLine(vector3) && hasSameDirection(vector3);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinearOpposite(Vector3 vector3, float f) {
        return isOnLine(vector3, f) && hasOppositeDirection(vector3);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinearOpposite(Vector3 vector3) {
        return isOnLine(vector3) && hasOppositeDirection(vector3);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isPerpendicular(Vector3 vector3) {
        return MathUtils.isZero(dot(vector3));
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isPerpendicular(Vector3 vector3, float f) {
        return MathUtils.isZero(dot(vector3), f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean hasSameDirection(Vector3 vector3) {
        return dot(vector3) > 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean hasOppositeDirection(Vector3 vector3) {
        return dot(vector3) < 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 lerp(Vector3 vector3, float f) {
        float f2 = this.x;
        this.x = f2 + ((vector3.x - f2) * f);
        float f3 = this.y;
        this.y = f3 + ((vector3.y - f3) * f);
        float f4 = this.z;
        this.z = f4 + (f * (vector3.z - f4));
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 interpolate(Vector3 vector3, float f, Interpolation interpolation) {
        return lerp(vector3, interpolation.apply(0.0f, 1.0f, f));
    }

    public Vector3 slerp(Vector3 vector3, float f) {
        float fDot = dot(vector3);
        double d = fDot;
        if (d > 0.9995d || d < -0.9995d) {
            return lerp(vector3, f);
        }
        double dAcos = ((float) Math.acos(d)) * f;
        float fSin = (float) Math.sin(dAcos);
        float f2 = vector3.x - (this.x * fDot);
        float f3 = vector3.y - (this.y * fDot);
        float f4 = vector3.z - (this.z * fDot);
        float f5 = (f2 * f2) + (f3 * f3) + (f4 * f4);
        float fSqrt = fSin * (f5 >= 1.0E-4f ? 1.0f / ((float) Math.sqrt(f5)) : 1.0f);
        return scl((float) Math.cos(dAcos)).add(f2 * fSqrt, f3 * fSqrt, f4 * fSqrt).nor();
    }

    public String toString() {
        return "(" + this.x + "," + this.y + "," + this.z + Constant.AFTER_QUTO;
    }

    public Vector3 fromString(String str) {
        int iIndexOf = str.indexOf(44, 1);
        int i = iIndexOf + 1;
        int iIndexOf2 = str.indexOf(44, i);
        if (iIndexOf != -1 && iIndexOf2 != -1 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            try {
                return set(Float.parseFloat(str.substring(1, iIndexOf)), Float.parseFloat(str.substring(i, iIndexOf2)), Float.parseFloat(str.substring(iIndexOf2 + 1, str.length() - 1)));
            } catch (NumberFormatException unused) {
            }
        }
        throw new GdxRuntimeException("Malformed Vector3: " + str);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 limit(float f) {
        return limit2(f * f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 limit2(float f) {
        float fLen2 = len2();
        if (fLen2 > f) {
            scl((float) Math.sqrt(f / fLen2));
        }
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 setLength(float f) {
        return setLength2(f * f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 setLength2(float f) {
        float fLen2 = len2();
        return (fLen2 == 0.0f || fLen2 == f) ? this : scl((float) Math.sqrt(f / fLen2));
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 clamp(float f, float f2) {
        float fLen2 = len2();
        if (fLen2 == 0.0f) {
            return this;
        }
        float f3 = f2 * f2;
        if (fLen2 > f3) {
            return scl((float) Math.sqrt(f3 / fLen2));
        }
        float f4 = f * f;
        return fLen2 < f4 ? scl((float) Math.sqrt(f4 / fLen2)) : this;
    }

    public int hashCode() {
        return ((((NumberUtils.floatToIntBits(this.x) + 31) * 31) + NumberUtils.floatToIntBits(this.y)) * 31) + NumberUtils.floatToIntBits(this.z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vector3 vector3 = (Vector3) obj;
        return NumberUtils.floatToIntBits(this.x) == NumberUtils.floatToIntBits(vector3.x) && NumberUtils.floatToIntBits(this.y) == NumberUtils.floatToIntBits(vector3.y) && NumberUtils.floatToIntBits(this.z) == NumberUtils.floatToIntBits(vector3.z);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean epsilonEquals(Vector3 vector3, float f) {
        return vector3 != null && Math.abs(vector3.x - this.x) <= f && Math.abs(vector3.y - this.y) <= f && Math.abs(vector3.z - this.z) <= f;
    }

    public boolean epsilonEquals(float f, float f2, float f3, float f4) {
        return Math.abs(f - this.x) <= f4 && Math.abs(f2 - this.y) <= f4 && Math.abs(f3 - this.z) <= f4;
    }

    public boolean epsilonEquals(Vector3 vector3) {
        return epsilonEquals(vector3, 1.0E-6f);
    }

    public boolean epsilonEquals(float f, float f2, float f3) {
        return epsilonEquals(f, f2, f3, 1.0E-6f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector3 setZero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        return this;
    }
}
