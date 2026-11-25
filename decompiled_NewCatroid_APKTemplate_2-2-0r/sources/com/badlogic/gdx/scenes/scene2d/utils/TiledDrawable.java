package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/TiledDrawable.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TiledDrawable extends TextureRegionDrawable {
    private int align;
    private final Color color;
    private float scale;

    public TiledDrawable() {
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scale = 1.0f;
        this.align = 12;
    }

    public TiledDrawable(TextureRegion textureRegion) {
        super(textureRegion);
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scale = 1.0f;
        this.align = 12;
    }

    public TiledDrawable(TextureRegionDrawable textureRegionDrawable) {
        super(textureRegionDrawable);
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scale = 1.0f;
        this.align = 12;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable, com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable, com.badlogic.gdx.scenes.scene2d.utils.Drawable
    public void draw(Batch batch, float f, float f2, float f3, float f4) {
        float packedColor = batch.getPackedColor();
        batch.setColor(batch.getColor().mul(this.color));
        draw(batch, getRegion(), f, f2, f3, f4, this.scale, this.align);
        batch.setPackedColor(packedColor);
    }

    public static void draw(Batch batch, TextureRegion textureRegion, float f, float f2, float f3, float f4, float f5, int i) {
        int i2;
        float f6;
        float f7;
        int i3;
        float f8;
        float f9;
        float f10;
        int i4;
        float f11;
        float f12;
        float f13;
        float f14;
        int i5;
        float f15;
        float f16;
        float f17;
        float f18;
        float regionWidth = textureRegion.getRegionWidth() * f5;
        float regionHeight = textureRegion.getRegionHeight() * f5;
        Texture texture = textureRegion.getTexture();
        float width = texture.getWidth() * f5;
        float height = texture.getHeight() * f5;
        float u = textureRegion.getU();
        float v = textureRegion.getV();
        float u2 = textureRegion.getU2();
        float v2 = textureRegion.getV2();
        float f19 = f3 / regionWidth;
        int i6 = (int) f19;
        int i7 = 1;
        if (Align.isLeft(i)) {
            i2 = i6;
            f7 = f3 - (i6 * regionWidth);
            f6 = 0.0f;
        } else if (Align.isRight(i)) {
            i2 = i6;
            f6 = f3 - (i6 * regionWidth);
            f7 = 0.0f;
        } else {
            if (i6 != 0) {
                if (i6 % 2 != 1) {
                    i6--;
                }
                i2 = i6;
                f6 = (f3 - (i6 * regionWidth)) * 0.5f;
            } else {
                i2 = i6;
                f6 = 0.0f;
            }
            f7 = f6;
        }
        float f20 = f4 / regionHeight;
        int i8 = (int) f20;
        if (Align.isTop(i)) {
            i3 = i8;
            f8 = f4 - (i8 * regionHeight);
            f9 = 0.0f;
        } else if (Align.isBottom(i)) {
            i3 = i8;
            f9 = f4 - (i8 * regionHeight);
            f8 = 0.0f;
        } else {
            if (i8 != 0) {
                if (i8 % 2 != 1) {
                    i8--;
                }
                i3 = i8;
                f8 = (f4 - (i8 * regionHeight)) * 0.5f;
            } else {
                i3 = i8;
                f8 = 0.0f;
            }
            f9 = f8;
        }
        if (f6 > 0.0f) {
            float f21 = u2 - (f6 / width);
            if (f8 > 0.0f) {
                batch.draw(texture, f, f2, f6, f8, f21, v + (f8 / height), u2, v);
                f10 = f2 + f8;
            } else {
                f10 = f2;
            }
            if (i3 == 0 && Align.isCenterVertical(i)) {
                float f22 = (v2 - v) * 0.5f * (1.0f - f20);
                batch.draw(texture, f, f10, f6, f4, f21, v2 - f22, u2, v + f22);
                f10 += f4;
            } else {
                for (int i9 = 0; i9 < i3; i9++) {
                    batch.draw(texture, f, f10, f6, regionHeight, f21, v2, u2, v);
                    f10 += regionHeight;
                }
            }
            if (f9 > 0.0f) {
                batch.draw(texture, f, f10, f6, f9, f21, v2, u2, v2 - (f9 / height));
            }
        } else {
            f20 = f20;
            i7 = 1;
            regionWidth = regionWidth;
            width = width;
            i3 = i3;
            i2 = i2;
            f10 = f2;
        }
        if (f8 > 0.0f) {
            float f23 = f + f6;
            float f24 = v + (f8 / height);
            if (i2 == 0 && Align.isCenterHorizontal(i)) {
                float f25 = (u2 - u) * 0.5f * (1.0f - f19);
                batch.draw(texture, f23, f2, f3, f8, u + f25, f24, u2 - f25, v);
            } else {
                float f26 = f23;
                for (int i10 = 0; i10 < i2; i10++) {
                    batch.draw(texture, f26, f2, regionWidth, f8, u, f24, u2, v);
                    f26 += regionWidth;
                }
            }
            f10 = f2;
        }
        float f27 = f + f6;
        if (i2 == 0 && Align.isCenterHorizontal(i)) {
            float f28 = (u2 - u) * 0.5f * (1.0f - f19);
            f13 = f3;
            f12 = u2 - f28;
            f11 = u + f28;
            i4 = i7;
        } else {
            i4 = i2;
            f11 = u;
            f12 = u2;
            f13 = regionWidth;
        }
        if (i3 == 0 && Align.isCenterVertical(i)) {
            float f29 = (v2 - v) * 0.5f * (1.0f - f20);
            f14 = f4;
            f16 = v2 - f29;
            i5 = i7;
            f15 = v + f29;
        } else {
            f14 = regionHeight;
            i5 = i3;
            f15 = v;
            f16 = v2;
        }
        float f30 = f27;
        int i11 = 0;
        while (i11 < i4) {
            f10 = f2 + f8;
            int i12 = 0;
            while (i12 < i5) {
                batch.draw(texture, f30, f10, f13, f14, f11, f16, f12, f15);
                f10 += f14;
                i12++;
                i11 = i11;
                i5 = i5;
                i4 = i4;
            }
            f30 += f13;
            i11++;
        }
        if (f9 > 0.0f) {
            float f31 = v2 - (f9 / height);
            if (i2 == 0 && Align.isCenterHorizontal(i)) {
                float f32 = (u2 - u) * 0.5f * (1.0f - f19);
                batch.draw(texture, f27, f10, f3, f9, u + f32, v2, u2 - f32, f31);
                f27 += f3;
            } else {
                for (int i13 = 0; i13 < i2; i13++) {
                    batch.draw(texture, f27, f10, regionWidth, f9, u, v2, u2, f31);
                    f27 += regionWidth;
                }
            }
        } else {
            f27 = f30;
        }
        if (f7 > 0.0f) {
            float f33 = u + (f7 / width);
            if (f8 > 0.0f) {
                batch.draw(texture, f27, f2, f7, f8, u, v + (f8 / height), f33, v);
                f17 = f2 + f8;
            } else {
                f17 = f2;
            }
            if (i3 == 0 && Align.isCenterVertical(i)) {
                float f34 = (v2 - v) * 0.5f * (1.0f - f20);
                batch.draw(texture, f27, f17, f7, f4, u, v2 - f34, f33, v + f34);
                f18 = f17 + f4;
            } else {
                float f35 = f17;
                for (int i14 = 0; i14 < i3; i14++) {
                    batch.draw(texture, f27, f35, f7, regionHeight, u, v2, f33, v);
                    f35 += regionHeight;
                }
                f18 = f35;
            }
            if (f9 > 0.0f) {
                batch.draw(texture, f27, f18, f7, f9, u, v2, f33, v2 - (f9 / height));
            }
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable, com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable
    public void draw(Batch batch, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        throw new UnsupportedOperationException();
    }

    public Color getColor() {
        return this.color;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    public int getAlign() {
        return this.align;
    }

    public void setAlign(int i) {
        this.align = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
    public TiledDrawable tint(Color color) {
        TiledDrawable tiledDrawable = new TiledDrawable(this);
        tiledDrawable.color.set(color);
        tiledDrawable.setLeftWidth(getLeftWidth());
        tiledDrawable.setRightWidth(getRightWidth());
        tiledDrawable.setTopHeight(getTopHeight());
        tiledDrawable.setBottomHeight(getBottomHeight());
        return tiledDrawable;
    }
}
