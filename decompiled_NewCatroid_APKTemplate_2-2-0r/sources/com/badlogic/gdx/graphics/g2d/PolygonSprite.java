package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PolygonSprite.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PolygonSprite {
    private boolean dirty;
    private float height;
    private float originX;
    private float originY;
    PolygonRegion region;
    private float rotation;
    private float[] vertices;
    private float width;
    private float x;
    private float y;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private Rectangle bounds = new Rectangle();
    private final Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);

    public PolygonSprite(PolygonRegion polygonRegion) {
        setRegion(polygonRegion);
        setSize(polygonRegion.region.regionWidth, polygonRegion.region.regionHeight);
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public PolygonSprite(PolygonSprite polygonSprite) {
        set(polygonSprite);
    }

    public void set(PolygonSprite polygonSprite) {
        if (polygonSprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }
        setRegion(polygonSprite.region);
        this.x = polygonSprite.x;
        this.y = polygonSprite.y;
        this.width = polygonSprite.width;
        this.height = polygonSprite.height;
        this.originX = polygonSprite.originX;
        this.originY = polygonSprite.originY;
        this.rotation = polygonSprite.rotation;
        this.scaleX = polygonSprite.scaleX;
        this.scaleY = polygonSprite.scaleY;
        this.color.set(polygonSprite.color);
    }

    public void setBounds(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.dirty = true;
    }

    public void setSize(float f, float f2) {
        this.width = f;
        this.height = f2;
        this.dirty = true;
    }

    public void setPosition(float f, float f2) {
        translate(f - this.x, f2 - this.y);
    }

    public void setX(float f) {
        translateX(f - this.x);
    }

    public void setY(float f) {
        translateY(f - this.y);
    }

    public void translateX(float f) {
        this.x += f;
        if (this.dirty) {
            return;
        }
        float[] fArr = this.vertices;
        for (int i = 0; i < fArr.length; i += 5) {
            fArr[i] = fArr[i] + f;
        }
    }

    public void translateY(float f) {
        this.y += f;
        if (this.dirty) {
            return;
        }
        float[] fArr = this.vertices;
        for (int i = 1; i < fArr.length; i += 5) {
            fArr[i] = fArr[i] + f;
        }
    }

    public void translate(float f, float f2) {
        this.x += f;
        this.y += f2;
        if (this.dirty) {
            return;
        }
        float[] fArr = this.vertices;
        for (int i = 0; i < fArr.length; i += 5) {
            fArr[i] = fArr[i] + f;
            int i2 = i + 1;
            fArr[i2] = fArr[i2] + f2;
        }
    }

    public void setColor(Color color) {
        this.color.set(color);
        float floatBits = color.toFloatBits();
        float[] fArr = this.vertices;
        for (int i = 2; i < fArr.length; i += 5) {
            fArr[i] = floatBits;
        }
    }

    public void setColor(float f, float f2, float f3, float f4) {
        this.color.set(f, f2, f3, f4);
        float floatBits = this.color.toFloatBits();
        float[] fArr = this.vertices;
        for (int i = 2; i < fArr.length; i += 5) {
            fArr[i] = floatBits;
        }
    }

    public void setOrigin(float f, float f2) {
        this.originX = f;
        this.originY = f2;
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

    public void setScale(float f) {
        this.scaleX = f;
        this.scaleY = f;
        this.dirty = true;
    }

    public void setScale(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
        this.dirty = true;
    }

    public void scale(float f) {
        this.scaleX += f;
        this.scaleY += f;
        this.dirty = true;
    }

    public float[] getVertices() {
        if (!this.dirty) {
            return this.vertices;
        }
        int i = 0;
        this.dirty = false;
        float f = this.originX;
        float f2 = this.originY;
        float f3 = this.scaleX;
        float f4 = this.scaleY;
        PolygonRegion polygonRegion = this.region;
        float[] fArr = this.vertices;
        float[] fArr2 = polygonRegion.vertices;
        float f5 = this.x + f;
        float f6 = this.y + f2;
        float regionWidth = this.width / polygonRegion.region.getRegionWidth();
        float regionHeight = this.height / polygonRegion.region.getRegionHeight();
        float fCosDeg = MathUtils.cosDeg(this.rotation);
        float fSinDeg = MathUtils.sinDeg(this.rotation);
        int length = fArr2.length;
        int i2 = 0;
        while (i < length) {
            float f7 = ((fArr2[i] * regionWidth) - f) * f3;
            float f8 = ((fArr2[i + 1] * regionHeight) - f2) * f4;
            fArr[i2] = ((fCosDeg * f7) - (fSinDeg * f8)) + f5;
            fArr[i2 + 1] = (f7 * fSinDeg) + (f8 * fCosDeg) + f6;
            i += 2;
            i2 += 5;
        }
        return fArr;
    }

    public Rectangle getBoundingRectangle() {
        float[] vertices = getVertices();
        float f = vertices[0];
        float f2 = vertices[1];
        float f3 = f;
        float f4 = f2;
        for (int i = 5; i < vertices.length; i += 5) {
            float f5 = vertices[i];
            float f6 = vertices[i + 1];
            if (f > f5) {
                f = f5;
            }
            if (f3 < f5) {
                f3 = f5;
            }
            if (f4 > f6) {
                f4 = f6;
            }
            if (f2 < f6) {
                f2 = f6;
            }
        }
        this.bounds.x = f;
        this.bounds.y = f4;
        this.bounds.width = f3 - f;
        this.bounds.height = f2 - f4;
        return this.bounds;
    }

    public void draw(PolygonSpriteBatch polygonSpriteBatch) {
        PolygonRegion polygonRegion = this.region;
        polygonSpriteBatch.draw(polygonRegion.region.texture, getVertices(), 0, this.vertices.length, polygonRegion.triangles, 0, polygonRegion.triangles.length);
    }

    public void draw(PolygonSpriteBatch polygonSpriteBatch, float f) {
        Color color = getColor();
        float f2 = color.a;
        color.a *= f;
        setColor(color);
        draw(polygonSpriteBatch);
        color.a = f2;
        setColor(color);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
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

    public Color getColor() {
        return this.color;
    }

    public Color getPackedColor() {
        Color.abgr8888ToColor(this.color, this.vertices[2]);
        return this.color;
    }

    public void setRegion(PolygonRegion polygonRegion) {
        this.region = polygonRegion;
        float[] fArr = polygonRegion.vertices;
        float[] fArr2 = polygonRegion.textureCoords;
        int length = (fArr.length / 2) * 5;
        float[] fArr3 = this.vertices;
        if (fArr3 == null || fArr3.length != length) {
            this.vertices = new float[length];
        }
        float floatBits = this.color.toFloatBits();
        float[] fArr4 = this.vertices;
        int i = 0;
        for (int i2 = 2; i2 < length; i2 += 5) {
            fArr4[i2] = floatBits;
            fArr4[i2 + 1] = fArr2[i];
            fArr4[i2 + 2] = fArr2[i + 1];
            i += 2;
        }
        this.dirty = true;
    }

    public PolygonRegion getRegion() {
        return this.region;
    }
}
