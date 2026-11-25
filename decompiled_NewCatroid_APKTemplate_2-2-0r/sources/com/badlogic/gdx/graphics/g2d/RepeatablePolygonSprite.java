package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/RepeatablePolygonSprite.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RepeatablePolygonSprite {
    private int cols;
    private float density;
    private float gridHeight;
    private float gridWidth;
    private TextureRegion region;
    private int rows;
    private boolean dirty = true;
    private Array<float[]> parts = new Array<>();
    private Array<float[]> vertices = new Array<>();
    private Array<short[]> indices = new Array<>();
    public float x = 0.0f;
    public float y = 0.0f;
    private Color color = Color.WHITE;
    private Vector2 offset = new Vector2();

    public void setPolygon(TextureRegion textureRegion, float[] fArr) {
        setPolygon(textureRegion, fArr, -1.0f);
    }

    public void setPolygon(TextureRegion textureRegion, float[] fArr, float f) {
        this.region = textureRegion;
        Polygon polygon = new Polygon(offset(fArr));
        Polygon polygon2 = new Polygon();
        Polygon polygon3 = new Polygon();
        EarClippingTriangulator earClippingTriangulator = new EarClippingTriangulator();
        Rectangle boundingRectangle = polygon.getBoundingRectangle();
        if (f == -1.0f) {
            f = boundingRectangle.getWidth() / textureRegion.getRegionWidth();
        }
        float regionHeight = textureRegion.getRegionHeight() / textureRegion.getRegionWidth();
        this.cols = (int) Math.ceil(f);
        float width = boundingRectangle.getWidth() / f;
        this.gridWidth = width;
        this.gridHeight = regionHeight * width;
        this.rows = (int) Math.ceil(boundingRectangle.getHeight() / this.gridHeight);
        for (int i = 0; i < this.cols; i++) {
            int i2 = 0;
            while (i2 < this.rows) {
                float f2 = i;
                float f3 = this.gridWidth;
                float f4 = i2;
                float f5 = this.gridHeight;
                i2++;
                float f6 = i2;
                float f7 = i + 1;
                polygon2.setVertices(new float[]{f2 * f3, f4 * f5, f2 * f3, f6 * f5, f7 * f3, f6 * f5, f7 * f3, f4 * f5});
                Intersector.intersectPolygons(polygon, polygon2, polygon3);
                float[] vertices = polygon3.getVertices();
                if (vertices.length > 0) {
                    this.parts.add(snapToGrid(vertices));
                    this.indices.add(earClippingTriangulator.computeTriangles(vertices).toArray());
                } else {
                    this.parts.add(null);
                }
            }
        }
        buildVertices();
    }

    private float[] snapToGrid(float[] fArr) {
        for (int i = 0; i < fArr.length; i += 2) {
            float f = fArr[i];
            float f2 = this.gridWidth;
            float f3 = (f / f2) % 1.0f;
            int i2 = i + 1;
            float f4 = (fArr[i2] / this.gridHeight) % 1.0f;
            if (f3 > 0.99f || f3 < 0.01f) {
                fArr[i] = f2 * Math.round(f / f2);
            }
            if (f4 > 0.99f || f4 < 0.01f) {
                float f5 = this.gridHeight;
                fArr[i2] = f5 * Math.round(fArr[i2] / f5);
            }
        }
        return fArr;
    }

    private float[] offset(float[] fArr) {
        this.offset.set(fArr[0], fArr[1]);
        for (int i = 0; i < fArr.length - 1; i += 2) {
            float f = this.offset.x;
            float f2 = fArr[i];
            if (f > f2) {
                this.offset.x = f2;
            }
            float f3 = this.offset.y;
            float f4 = fArr[i + 1];
            if (f3 > f4) {
                this.offset.y = f4;
            }
        }
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            fArr[i2] = fArr[i2] - this.offset.x;
            int i3 = i2 + 1;
            fArr[i3] = fArr[i3] - this.offset.y;
        }
        return fArr;
    }

    private void buildVertices() {
        this.vertices.clear();
        for (int i = 0; i < this.parts.size; i++) {
            float[] fArr = this.parts.get(i);
            if (fArr != null) {
                float[] fArr2 = new float[(fArr.length * 5) / 2];
                int i2 = this.rows;
                int i3 = i / i2;
                int i4 = i % i2;
                int i5 = 0;
                for (int i6 = 0; i6 < fArr.length; i6 += 2) {
                    fArr2[i5] = fArr[i6] + this.offset.x + this.x;
                    int i7 = i6 + 1;
                    fArr2[i5 + 1] = fArr[i7] + this.offset.y + this.y;
                    int i8 = i5 + 3;
                    fArr2[i5 + 2] = this.color.toFloatBits();
                    float f = fArr[i6];
                    float f2 = this.gridWidth;
                    float f3 = (f % f2) / f2;
                    float f4 = fArr[i7];
                    float f5 = this.gridHeight;
                    float f6 = (f4 % f5) / f5;
                    if (f == i3 * f2) {
                        f3 = 0.0f;
                    }
                    float f7 = 1.0f;
                    if (f == (i3 + 1) * f2) {
                        f3 = 1.0f;
                    }
                    if (f4 == i4 * f5) {
                        f6 = 0.0f;
                    }
                    if (f4 != (i4 + 1) * f5) {
                        f7 = f6;
                    }
                    float u = this.region.getU() + ((this.region.getU2() - this.region.getU()) * f3);
                    float v = this.region.getV() + ((this.region.getV2() - this.region.getV()) * f7);
                    int i9 = i5 + 4;
                    fArr2[i8] = u;
                    i5 += 5;
                    fArr2[i9] = v;
                }
                this.vertices.add(fArr2);
            }
        }
        this.dirty = false;
    }

    public void draw(PolygonSpriteBatch polygonSpriteBatch) {
        if (this.dirty) {
            buildVertices();
        }
        for (int i = 0; i < this.vertices.size; i++) {
            polygonSpriteBatch.draw(this.region.getTexture(), this.vertices.get(i), 0, this.vertices.get(i).length, this.indices.get(i), 0, this.indices.get(i).length);
        }
    }

    public void setColor(Color color) {
        this.color = color;
        this.dirty = true;
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.dirty = true;
    }
}
