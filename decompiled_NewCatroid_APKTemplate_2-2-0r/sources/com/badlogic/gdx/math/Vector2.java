package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.NumberUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Vector2.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Vector2 implements Serializable, Vector<Vector2> {
    public static final Vector2 X = new Vector2(1.0f, 0.0f);
    public static final Vector2 Y = new Vector2(0.0f, 1.0f);
    public static final Vector2 Zero = new Vector2(0.0f, 0.0f);
    private static final long serialVersionUID = 913902788239530931L;
    public float x;
    public float y;

    public static float dot(float f, float f2, float f3, float f4) {
        return (f * f3) + (f2 * f4);
    }

    public static float dst2(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (f5 * f5) + (f6 * f6);
    }

    public static float len2(float f, float f2) {
        return (f * f) + (f2 * f2);
    }

    public Vector2() {
    }

    public Vector2(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public Vector2(Vector2 vector2) {
        set(vector2);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 cpy() {
        return new Vector2(this);
    }

    public static float len(float f, float f2) {
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float len() {
        float f = this.x;
        float f2 = this.y;
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float len2() {
        float f = this.x;
        float f2 = this.y;
        return (f * f) + (f2 * f2);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 set(Vector2 vector2) {
        this.x = vector2.x;
        this.y = vector2.y;
        return this;
    }

    public Vector2 set(float f, float f2) {
        this.x = f;
        this.y = f2;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 sub(Vector2 vector2) {
        this.x -= vector2.x;
        this.y -= vector2.y;
        return this;
    }

    public Vector2 sub(float f, float f2) {
        this.x -= f;
        this.y -= f2;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 nor() {
        float fLen = len();
        if (fLen != 0.0f) {
            this.x /= fLen;
            this.y /= fLen;
        }
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 add(Vector2 vector2) {
        this.x += vector2.x;
        this.y += vector2.y;
        return this;
    }

    public Vector2 add(float f, float f2) {
        this.x += f;
        this.y += f2;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public float dot(Vector2 vector2) {
        return (this.x * vector2.x) + (this.y * vector2.y);
    }

    public float dot(float f, float f2) {
        return (this.x * f) + (this.y * f2);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 scl(float f) {
        this.x *= f;
        this.y *= f;
        return this;
    }

    public Vector2 scl(float f, float f2) {
        this.x *= f;
        this.y *= f2;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 scl(Vector2 vector2) {
        this.x *= vector2.x;
        this.y *= vector2.y;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 mulAdd(Vector2 vector2, float f) {
        this.x += vector2.x * f;
        this.y += vector2.y * f;
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 mulAdd(Vector2 vector2, Vector2 vector3) {
        this.x += vector2.x * vector3.x;
        this.y += vector2.y * vector3.y;
        return this;
    }

    public boolean idt(Vector2 vector2) {
        return this.x == vector2.x && this.y == vector2.y;
    }

    public static float dst(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (float) Math.sqrt((f5 * f5) + (f6 * f6));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float dst(Vector2 vector2) {
        float f = vector2.x - this.x;
        float f2 = vector2.y - this.y;
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public float dst(float f, float f2) {
        float f3 = f - this.x;
        float f4 = f2 - this.y;
        return (float) Math.sqrt((f3 * f3) + (f4 * f4));
    }

    @Override // com.badlogic.gdx.math.Vector
    public float dst2(Vector2 vector2) {
        float f = vector2.x - this.x;
        float f2 = vector2.y - this.y;
        return (f * f) + (f2 * f2);
    }

    public float dst2(float f, float f2) {
        float f3 = f - this.x;
        float f4 = f2 - this.y;
        return (f3 * f3) + (f4 * f4);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 limit(float f) {
        return limit2(f * f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 limit2(float f) {
        float fLen2 = len2();
        return fLen2 > f ? scl((float) Math.sqrt(f / fLen2)) : this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 clamp(float f, float f2) {
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

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 setLength(float f) {
        return setLength2(f * f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 setLength2(float f) {
        float fLen2 = len2();
        return (fLen2 == 0.0f || fLen2 == f) ? this : scl((float) Math.sqrt(f / fLen2));
    }

    public String toString() {
        return "(" + this.x + "," + this.y + Constant.AFTER_QUTO;
    }

    public Vector2 fromString(String str) {
        int iIndexOf = str.indexOf(44, 1);
        if (iIndexOf != -1 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            try {
                return set(Float.parseFloat(str.substring(1, iIndexOf)), Float.parseFloat(str.substring(iIndexOf + 1, str.length() - 1)));
            } catch (NumberFormatException unused) {
            }
        }
        throw new GdxRuntimeException("Malformed Vector2: " + str);
    }

    public Vector2 mul(Matrix3 matrix3) {
        float f = (this.x * matrix3.val[0]) + (this.y * matrix3.val[3]) + matrix3.val[6];
        float f2 = (this.x * matrix3.val[1]) + (this.y * matrix3.val[4]) + matrix3.val[7];
        this.x = f;
        this.y = f2;
        return this;
    }

    public float crs(Vector2 vector2) {
        return (this.x * vector2.y) - (this.y * vector2.x);
    }

    public float crs(float f, float f2) {
        return (this.x * f2) - (this.y * f);
    }

    @Deprecated
    public float angle() {
        float fAtan2 = ((float) Math.atan2(this.y, this.x)) * 57.295776f;
        return fAtan2 < 0.0f ? fAtan2 + 360.0f : fAtan2;
    }

    @Deprecated
    public float angle(Vector2 vector2) {
        return ((float) Math.atan2(crs(vector2), dot(vector2))) * 57.295776f;
    }

    public float angleDeg() {
        float fAtan2 = ((float) Math.atan2(this.y, this.x)) * 57.295776f;
        return fAtan2 < 0.0f ? fAtan2 + 360.0f : fAtan2;
    }

    public float angleDeg(Vector2 vector2) {
        float fAtan2 = ((float) Math.atan2(vector2.crs(this), vector2.dot(this))) * 57.295776f;
        return fAtan2 < 0.0f ? fAtan2 + 360.0f : fAtan2;
    }

    public static float angleDeg(float f, float f2) {
        float fAtan2 = ((float) Math.atan2(f2, f)) * 57.295776f;
        return fAtan2 < 0.0f ? fAtan2 + 360.0f : fAtan2;
    }

    public float angleRad() {
        return (float) Math.atan2(this.y, this.x);
    }

    public float angleRad(Vector2 vector2) {
        return (float) Math.atan2(vector2.crs(this), vector2.dot(this));
    }

    public static float angleRad(float f, float f2) {
        return (float) Math.atan2(f2, f);
    }

    @Deprecated
    public Vector2 setAngle(float f) {
        return setAngleRad(f * 0.017453292f);
    }

    public Vector2 setAngleDeg(float f) {
        return setAngleRad(f * 0.017453292f);
    }

    public Vector2 setAngleRad(float f) {
        set(len(), 0.0f);
        rotateRad(f);
        return this;
    }

    @Deprecated
    public Vector2 rotate(float f) {
        return rotateRad(f * 0.017453292f);
    }

    @Deprecated
    public Vector2 rotateAround(Vector2 vector2, float f) {
        return sub(vector2).rotateDeg(f).add(vector2);
    }

    public Vector2 rotateDeg(float f) {
        return rotateRad(f * 0.017453292f);
    }

    public Vector2 rotateRad(float f) {
        double d = f;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        float f2 = this.x;
        float f3 = this.y;
        this.x = (f2 * fCos) - (f3 * fSin);
        this.y = (f2 * fSin) + (f3 * fCos);
        return this;
    }

    public Vector2 rotateAroundDeg(Vector2 vector2, float f) {
        return sub(vector2).rotateDeg(f).add(vector2);
    }

    public Vector2 rotateAroundRad(Vector2 vector2, float f) {
        return sub(vector2).rotateRad(f).add(vector2);
    }

    public Vector2 rotate90(int i) {
        float f = this.x;
        if (i >= 0) {
            this.x = -this.y;
            this.y = f;
        } else {
            this.x = this.y;
            this.y = -f;
        }
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 lerp(Vector2 vector2, float f) {
        float f2 = 1.0f - f;
        this.x = (this.x * f2) + (vector2.x * f);
        this.y = (this.y * f2) + (vector2.y * f);
        return this;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 interpolate(Vector2 vector2, float f, Interpolation interpolation) {
        return lerp(vector2, interpolation.apply(f));
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 setToRandomDirection() {
        float fRandom = MathUtils.random(0.0f, 6.2831855f);
        return set(MathUtils.cos(fRandom), MathUtils.sin(fRandom));
    }

    public int hashCode() {
        return ((NumberUtils.floatToIntBits(this.x) + 31) * 31) + NumberUtils.floatToIntBits(this.y);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vector2 vector2 = (Vector2) obj;
        return NumberUtils.floatToIntBits(this.x) == NumberUtils.floatToIntBits(vector2.x) && NumberUtils.floatToIntBits(this.y) == NumberUtils.floatToIntBits(vector2.y);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean epsilonEquals(Vector2 vector2, float f) {
        return vector2 != null && Math.abs(vector2.x - this.x) <= f && Math.abs(vector2.y - this.y) <= f;
    }

    public boolean epsilonEquals(float f, float f2, float f3) {
        return Math.abs(f - this.x) <= f3 && Math.abs(f2 - this.y) <= f3;
    }

    public boolean epsilonEquals(Vector2 vector2) {
        return epsilonEquals(vector2, 1.0E-6f);
    }

    public boolean epsilonEquals(float f, float f2) {
        return epsilonEquals(f, f2, 1.0E-6f);
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
        return this.x == 0.0f && this.y == 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isZero(float f) {
        return len2() < f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isOnLine(Vector2 vector2) {
        return MathUtils.isZero((this.x * vector2.y) - (this.y * vector2.x));
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isOnLine(Vector2 vector2, float f) {
        return MathUtils.isZero((this.x * vector2.y) - (this.y * vector2.x), f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinear(Vector2 vector2, float f) {
        return isOnLine(vector2, f) && dot(vector2) > 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinear(Vector2 vector2) {
        return isOnLine(vector2) && dot(vector2) > 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinearOpposite(Vector2 vector2, float f) {
        return isOnLine(vector2, f) && dot(vector2) < 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isCollinearOpposite(Vector2 vector2) {
        return isOnLine(vector2) && dot(vector2) < 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isPerpendicular(Vector2 vector2) {
        return MathUtils.isZero(dot(vector2));
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean isPerpendicular(Vector2 vector2, float f) {
        return MathUtils.isZero(dot(vector2), f);
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean hasSameDirection(Vector2 vector2) {
        return dot(vector2) > 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public boolean hasOppositeDirection(Vector2 vector2) {
        return dot(vector2) < 0.0f;
    }

    @Override // com.badlogic.gdx.math.Vector
    public Vector2 setZero() {
        this.x = 0.0f;
        this.y = 0.0f;
        return this;
    }
}
