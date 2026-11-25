package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Scaling;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Image.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Image extends Widget {
    private int align;
    private Drawable drawable;
    private float imageHeight;
    private float imageWidth;
    private float imageX;
    private float imageY;
    private Scaling scaling;

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinHeight() {
        return 0.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinWidth() {
        return 0.0f;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Image() {
        this((Drawable) null);
    }

    public Image(NinePatch ninePatch) {
        this(new NinePatchDrawable(ninePatch), Scaling.stretch, 1);
    }

    public Image(TextureRegion textureRegion) {
        this(new TextureRegionDrawable(textureRegion), Scaling.stretch, 1);
    }

    public Image(Texture texture) {
        this(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public Image(Skin skin, String str) {
        this(skin.getDrawable(str), Scaling.stretch, 1);
    }

    public Image(Drawable drawable) {
        this(drawable, Scaling.stretch, 1);
    }

    public Image(Drawable drawable, Scaling scaling) {
        this(drawable, scaling, 1);
    }

    public Image(Drawable drawable, Scaling scaling, int i) {
        this.align = 1;
        setDrawable(drawable);
        this.scaling = scaling;
        this.align = i;
        setSize(getPrefWidth(), getPrefHeight());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        Drawable drawable = this.drawable;
        if (drawable == null) {
            return;
        }
        float minWidth = drawable.getMinWidth();
        float minHeight = this.drawable.getMinHeight();
        float width = getWidth();
        float height = getHeight();
        Vector2 vector2Apply = this.scaling.apply(minWidth, minHeight, width, height);
        this.imageWidth = vector2Apply.x;
        float f = vector2Apply.y;
        this.imageHeight = f;
        int i = this.align;
        if ((i & 8) != 0) {
            this.imageX = 0.0f;
        } else if ((i & 16) != 0) {
            this.imageX = (int) (width - this.imageWidth);
        } else {
            this.imageX = (int) ((width / 2.0f) - (this.imageWidth / 2.0f));
        }
        if ((i & 2) != 0) {
            this.imageY = (int) (height - f);
        } else if ((i & 4) != 0) {
            this.imageY = 0.0f;
        } else {
            this.imageY = (int) ((height / 2.0f) - (f / 2.0f));
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        validate();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * f);
        float x = getX();
        float y = getY();
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        if (this.drawable instanceof TransformDrawable) {
            float rotation = getRotation();
            if (scaleX != 1.0f || scaleY != 1.0f || rotation != 0.0f) {
                ((TransformDrawable) this.drawable).draw(batch, x + this.imageX, y + this.imageY, getOriginX() - this.imageX, getOriginY() - this.imageY, this.imageWidth, this.imageHeight, scaleX, scaleY, rotation);
                return;
            }
        }
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.draw(batch, x + this.imageX, y + this.imageY, this.imageWidth * scaleX, this.imageHeight * scaleY);
        }
    }

    public void setDrawable(Skin skin, String str) {
        setDrawable(skin.getDrawable(str));
    }

    public void setDrawable(Drawable drawable) {
        if (this.drawable == drawable) {
            return;
        }
        if (drawable == null || getPrefWidth() != drawable.getMinWidth() || getPrefHeight() != drawable.getMinHeight()) {
            invalidateHierarchy();
        }
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public void setScaling(Scaling scaling) {
        if (scaling == null) {
            throw new IllegalArgumentException("scaling cannot be null.");
        }
        this.scaling = scaling;
        invalidate();
    }

    public void setAlign(int i) {
        this.align = i;
        invalidate();
    }

    public int getAlign() {
        return this.align;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        Drawable drawable = this.drawable;
        if (drawable != null) {
            return drawable.getMinWidth();
        }
        return 0.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        Drawable drawable = this.drawable;
        if (drawable != null) {
            return drawable.getMinHeight();
        }
        return 0.0f;
    }

    public float getImageX() {
        return this.imageX;
    }

    public float getImageY() {
        return this.imageY;
    }

    public float getImageWidth() {
        return this.imageWidth;
    }

    public float getImageHeight() {
        return this.imageHeight;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public String toString() {
        String name2 = getName();
        if (name2 != null) {
            return name2;
        }
        String name3 = getClass().getName();
        int iLastIndexOf = name3.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            name3 = name3.substring(iLastIndexOf + 1);
        }
        return (name3.indexOf(36) != -1 ? "Image " : "") + name3 + ": " + this.drawable;
    }
}
