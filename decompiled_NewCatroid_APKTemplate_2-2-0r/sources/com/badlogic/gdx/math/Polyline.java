package com.badlogic.gdx.math;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Polyline.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Polyline implements Shape2D {
    private Rectangle bounds;
    private boolean calculateLength;
    private boolean calculateScaledLength;
    private boolean dirty;
    private float length;
    private float[] localVertices;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float scaledLength;
    private float[] worldVertices;
    private float x;
    private float y;

    @Override // com.badlogic.gdx.math.Shape2D
    public boolean contains(float f, float f2) {
        return false;
    }

    @Override // com.badlogic.gdx.math.Shape2D
    public boolean contains(Vector2 vector2) {
        return false;
    }

    public Polyline() {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.calculateScaledLength = true;
        this.calculateLength = true;
        this.dirty = true;
        this.localVertices = new float[0];
    }

    public Polyline(float[] fArr) {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.calculateScaledLength = true;
        this.calculateLength = true;
        this.dirty = true;
        if (fArr.length < 4) {
            throw new IllegalArgumentException("polylines must contain at least 2 points.");
        }
        this.localVertices = fArr;
    }

    public float[] getVertices() {
        return this.localVertices;
    }

    public float[] getTransformedVertices() {
        if (!this.dirty) {
            return this.worldVertices;
        }
        this.dirty = false;
        float[] fArr = this.localVertices;
        float[] fArr2 = this.worldVertices;
        if (fArr2 == null || fArr2.length < fArr.length) {
            this.worldVertices = new float[fArr.length];
        }
        float[] fArr3 = this.worldVertices;
        float f = this.x;
        float f2 = this.y;
        float f3 = this.originX;
        float f4 = this.originY;
        float f5 = this.scaleX;
        float f6 = this.scaleY;
        boolean z = (f5 == 1.0f && f6 == 1.0f) ? false : true;
        float f7 = this.rotation;
        float fCosDeg = MathUtils.cosDeg(f7);
        float fSinDeg = MathUtils.sinDeg(f7);
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            float f8 = fArr[i] - f3;
            int i2 = i + 1;
            float f9 = fArr[i2] - f4;
            if (z) {
                f8 *= f5;
                f9 *= f6;
            }
            if (f7 != 0.0f) {
                float f10 = (fCosDeg * f8) - (fSinDeg * f9);
                f9 = (f8 * fSinDeg) + (f9 * fCosDeg);
                f8 = f10;
            }
            fArr3[i] = f8 + f + f3;
            fArr3[i2] = f2 + f9 + f4;
        }
        return fArr3;
    }

    public float getLength() {
        if (!this.calculateLength) {
            return this.length;
        }
        int i = 0;
        this.calculateLength = false;
        this.length = 0.0f;
        int length = this.localVertices.length - 2;
        while (i < length) {
            float[] fArr = this.localVertices;
            int i2 = i + 2;
            float f = fArr[i2] - fArr[i];
            float f2 = fArr[i + 1] - fArr[i + 3];
            this.length += (float) Math.sqrt((f * f) + (f2 * f2));
            i = i2;
        }
        return this.length;
    }

    public float getScaledLength() {
        if (!this.calculateScaledLength) {
            return this.scaledLength;
        }
        int i = 0;
        this.calculateScaledLength = false;
        this.scaledLength = 0.0f;
        int length = this.localVertices.length - 2;
        while (i < length) {
            float[] fArr = this.localVertices;
            int i2 = i + 2;
            float f = fArr[i2];
            float f2 = this.scaleX;
            float f3 = (f * f2) - (fArr[i] * f2);
            float f4 = fArr[i + 1];
            float f5 = this.scaleY;
            float f6 = (f4 * f5) - (fArr[i + 3] * f5);
            this.scaledLength += (float) Math.sqrt((f3 * f3) + (f6 * f6));
            i = i2;
        }
        return this.scaledLength;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setOrigin(float f, float f2) {
        this.originX = f;
        this.originY = f2;
        this.dirty = true;
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.dirty = true;
    }

    public void setVertices(float[] fArr) {
        if (fArr.length < 4) {
            throw new IllegalArgumentException("polylines must contain at least 2 points.");
        }
        this.localVertices = fArr;
        this.dirty = true;
    }

    public void setRotation(float f) {
        this.rotation = f;
        this.dirty = true;
    }

    public void rotate(float f) {
        this.rotation += f;
        this.dirty = true;
    }

    public void setScale(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
        this.dirty = true;
        this.calculateScaledLength = true;
    }

    public void scale(float f) {
        this.scaleX += f;
        this.scaleY += f;
        this.dirty = true;
        this.calculateScaledLength = true;
    }

    public void calculateLength() {
        this.calculateLength = true;
    }

    public void calculateScaledLength() {
        this.calculateScaledLength = true;
    }

    public void dirty() {
        this.dirty = true;
    }

    public void translate(float f, float f2) {
        this.x += f;
        this.y += f2;
        this.dirty = true;
    }

    public Rectangle getBoundingRectangle() {
        float[] transformedVertices = getTransformedVertices();
        float f = transformedVertices[0];
        float f2 = transformedVertices[1];
        int length = transformedVertices.length;
        float f3 = f2;
        float f4 = f;
        float f5 = f3;
        for (int i = 2; i < length; i += 2) {
            float f6 = transformedVertices[i];
            if (f > f6) {
                f = f6;
            }
            float f7 = transformedVertices[i + 1];
            if (f5 > f7) {
                f5 = f7;
            }
            if (f4 < f6) {
                f4 = f6;
            }
            if (f3 < f7) {
                f3 = f7;
            }
        }
        if (this.bounds == null) {
            this.bounds = new Rectangle();
        }
        this.bounds.x = f;
        this.bounds.y = f5;
        this.bounds.width = f4 - f;
        this.bounds.height = f3 - f5;
        return this.bounds;
    }
}
