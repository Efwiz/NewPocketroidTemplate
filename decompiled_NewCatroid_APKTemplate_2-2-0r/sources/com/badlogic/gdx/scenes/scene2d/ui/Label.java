package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.StringBuilder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Label.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Label extends Widget {
    private BitmapFontCache cache;
    private String ellipsis;
    private boolean fontScaleChanged;
    private float fontScaleX;
    private float fontScaleY;
    private int intValue;
    private int labelAlign;
    private float lastPrefHeight;
    private final GlyphLayout layout;
    private int lineAlign;
    private float prefHeight;
    private boolean prefSizeInvalid;
    private float prefWidth;
    private LabelStyle style;
    private final StringBuilder text;
    private boolean wrap;
    private static final Color tempColor = new Color();
    private static final GlyphLayout prefSizeLayout = new GlyphLayout();

    public Label(CharSequence charSequence, Skin skin) {
        this(charSequence, (LabelStyle) skin.get(LabelStyle.class));
    }

    public Label(CharSequence charSequence, Skin skin, String str) {
        this(charSequence, (LabelStyle) skin.get(str, LabelStyle.class));
    }

    public Label(CharSequence charSequence, Skin skin, String str, Color color) {
        this(charSequence, new LabelStyle(skin.getFont(str), color));
    }

    public Label(CharSequence charSequence, Skin skin, String str, String str2) {
        this(charSequence, new LabelStyle(skin.getFont(str), skin.getColor(str2)));
    }

    public Label(CharSequence charSequence, LabelStyle labelStyle) {
        this.layout = new GlyphLayout();
        StringBuilder stringBuilder = new StringBuilder();
        this.text = stringBuilder;
        this.intValue = Integer.MIN_VALUE;
        this.labelAlign = 8;
        this.lineAlign = 8;
        this.prefSizeInvalid = true;
        this.fontScaleX = 1.0f;
        this.fontScaleY = 1.0f;
        this.fontScaleChanged = false;
        if (charSequence != null) {
            stringBuilder.append(charSequence);
        }
        setStyle(labelStyle);
        if (charSequence == null || charSequence.length() <= 0) {
            return;
        }
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void setStyle(LabelStyle labelStyle) {
        if (labelStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        if (labelStyle.font == null) {
            throw new IllegalArgumentException("Missing LabelStyle font.");
        }
        this.style = labelStyle;
        this.cache = labelStyle.font.newFontCache();
        invalidateHierarchy();
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    public boolean setText(int i) {
        if (this.intValue == i) {
            return false;
        }
        this.text.clear();
        this.text.append(i);
        this.intValue = i;
        invalidateHierarchy();
        return true;
    }

    public void setText(CharSequence charSequence) {
        if (charSequence == null) {
            if (this.text.length == 0) {
                return;
            } else {
                this.text.clear();
            }
        } else if (charSequence instanceof StringBuilder) {
            if (this.text.equals(charSequence)) {
                return;
            }
            this.text.clear();
            this.text.append((StringBuilder) charSequence);
        } else {
            if (textEquals(charSequence)) {
                return;
            }
            this.text.clear();
            this.text.append(charSequence);
        }
        this.intValue = Integer.MIN_VALUE;
        invalidateHierarchy();
    }

    public boolean textEquals(CharSequence charSequence) {
        int i = this.text.length;
        char[] cArr = this.text.chars;
        if (i != charSequence.length()) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (cArr[i2] != charSequence.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    public StringBuilder getText() {
        return this.text;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void invalidate() {
        super.invalidate();
        this.prefSizeInvalid = true;
    }

    private void scaleAndComputePrefSize() {
        BitmapFont font = this.cache.getFont();
        float scaleX = font.getScaleX();
        float scaleY = font.getScaleY();
        if (this.fontScaleChanged) {
            font.getData().setScale(this.fontScaleX, this.fontScaleY);
        }
        computePrefSize(prefSizeLayout);
        if (this.fontScaleChanged) {
            font.getData().setScale(scaleX, scaleY);
        }
    }

    protected void computePrefSize(GlyphLayout glyphLayout) {
        this.prefSizeInvalid = false;
        if (this.wrap && this.ellipsis == null) {
            float width = getWidth();
            if (this.style.background != null) {
                width = (Math.max(width, this.style.background.getMinWidth()) - this.style.background.getLeftWidth()) - this.style.background.getRightWidth();
            }
            glyphLayout.setText(this.cache.getFont(), this.text, Color.WHITE, width, 8, true);
        } else {
            glyphLayout.setText(this.cache.getFont(), this.text);
        }
        this.prefWidth = glyphLayout.width;
        this.prefHeight = glyphLayout.height;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public void layout() {
        float leftWidth;
        float bottomHeight;
        float f;
        float f2;
        GlyphLayout glyphLayout;
        float f3;
        float f4;
        float descent;
        BitmapFont font = this.cache.getFont();
        float scaleX = font.getScaleX();
        float scaleY = font.getScaleY();
        if (this.fontScaleChanged) {
            font.getData().setScale(this.fontScaleX, this.fontScaleY);
        }
        boolean z = this.wrap && this.ellipsis == null;
        if (z) {
            float prefHeight = getPrefHeight();
            if (prefHeight != this.lastPrefHeight) {
                this.lastPrefHeight = prefHeight;
                invalidateHierarchy();
            }
        }
        float width = getWidth();
        float height = getHeight();
        Drawable drawable = this.style.background;
        if (drawable != null) {
            float leftWidth2 = drawable.getLeftWidth();
            float bottomHeight2 = drawable.getBottomHeight();
            leftWidth = width - (drawable.getLeftWidth() + drawable.getRightWidth());
            bottomHeight = height - (drawable.getBottomHeight() + drawable.getTopHeight());
            f = leftWidth2;
            f2 = bottomHeight2;
        } else {
            leftWidth = width;
            bottomHeight = height;
            f = 0.0f;
            f2 = 0.0f;
        }
        GlyphLayout glyphLayout2 = this.layout;
        if (z || this.text.indexOf("\n") != -1) {
            StringBuilder stringBuilder = this.text;
            glyphLayout = glyphLayout2;
            glyphLayout2.setText(font, stringBuilder, 0, stringBuilder.length, Color.WHITE, leftWidth, this.lineAlign, z, this.ellipsis);
            float f5 = glyphLayout.width;
            float f6 = glyphLayout.height;
            int i = this.labelAlign;
            if ((i & 8) == 0) {
                f += (i & 16) != 0 ? leftWidth - f5 : (leftWidth - f5) / 2.0f;
            }
            f3 = f5;
            f4 = f6;
        } else {
            f4 = font.getData().capHeight;
            glyphLayout = glyphLayout2;
            f3 = leftWidth;
        }
        float f7 = f;
        int i2 = this.labelAlign;
        if ((i2 & 2) != 0) {
            descent = f2 + (this.cache.getFont().isFlipped() ? 0.0f : bottomHeight - f4) + this.style.font.getDescent();
        } else if ((i2 & 4) != 0) {
            descent = (f2 + (this.cache.getFont().isFlipped() ? bottomHeight - f4 : 0.0f)) - this.style.font.getDescent();
        } else {
            descent = f2 + ((bottomHeight - f4) / 2.0f);
        }
        if (!this.cache.getFont().isFlipped()) {
            descent += f4;
        }
        StringBuilder stringBuilder2 = this.text;
        glyphLayout.setText(font, stringBuilder2, 0, stringBuilder2.length, Color.WHITE, f3, this.lineAlign, z, this.ellipsis);
        this.cache.setText(glyphLayout, f7, descent);
        if (this.fontScaleChanged) {
            font.getData().setScale(scaleX, scaleY);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        validate();
        Color color = tempColor.set(getColor());
        color.a *= f;
        if (this.style.background != null) {
            batch.setColor(color.r, color.g, color.b, color.a);
            this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        if (this.style.fontColor != null) {
            color.mul(this.style.fontColor);
        }
        this.cache.tint(color);
        this.cache.setPosition(getX(), getY());
        this.cache.draw(batch);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        if (this.wrap) {
            return 0.0f;
        }
        if (this.prefSizeInvalid) {
            scaleAndComputePrefSize();
        }
        float f = this.prefWidth;
        Drawable drawable = this.style.background;
        return drawable != null ? Math.max(f + drawable.getLeftWidth() + drawable.getRightWidth(), drawable.getMinWidth()) : f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        if (this.prefSizeInvalid) {
            scaleAndComputePrefSize();
        }
        float descent = this.prefHeight - ((this.style.font.getDescent() * (this.fontScaleChanged ? this.fontScaleY / this.style.font.getScaleY() : 1.0f)) * 2.0f);
        Drawable drawable = this.style.background;
        return drawable != null ? Math.max(descent + drawable.getTopHeight() + drawable.getBottomHeight(), drawable.getMinHeight()) : descent;
    }

    public GlyphLayout getGlyphLayout() {
        return this.layout;
    }

    public void setWrap(boolean z) {
        this.wrap = z;
        invalidateHierarchy();
    }

    public boolean getWrap() {
        return this.wrap;
    }

    public int getLabelAlign() {
        return this.labelAlign;
    }

    public int getLineAlign() {
        return this.lineAlign;
    }

    public void setAlignment(int i) {
        setAlignment(i, i);
    }

    public void setAlignment(int i, int i2) {
        this.labelAlign = i;
        if ((i2 & 8) != 0) {
            this.lineAlign = 8;
        } else if ((i2 & 16) != 0) {
            this.lineAlign = 16;
        } else {
            this.lineAlign = 1;
        }
        invalidate();
    }

    public void setFontScale(float f) {
        setFontScale(f, f);
    }

    public void setFontScale(float f, float f2) {
        this.fontScaleChanged = true;
        this.fontScaleX = f;
        this.fontScaleY = f2;
        invalidateHierarchy();
    }

    public float getFontScaleX() {
        return this.fontScaleX;
    }

    public void setFontScaleX(float f) {
        setFontScale(f, this.fontScaleY);
    }

    public float getFontScaleY() {
        return this.fontScaleY;
    }

    public void setFontScaleY(float f) {
        setFontScale(this.fontScaleX, f);
    }

    public void setEllipsis(String str) {
        this.ellipsis = str;
    }

    public void setEllipsis(boolean z) {
        if (z) {
            this.ellipsis = "...";
        } else {
            this.ellipsis = null;
        }
    }

    protected BitmapFontCache getBitmapFontCache() {
        return this.cache;
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
        return (name3.indexOf(36) != -1 ? "Label " : "") + name3 + ": " + ((Object) this.text);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Label$LabelStyle.class
      classes8.dex
     */
    public static class LabelStyle {
        public Drawable background;
        public BitmapFont font;
        public Color fontColor;

        public LabelStyle() {
        }

        public LabelStyle(BitmapFont bitmapFont, Color color) {
            this.font = bitmapFont;
            this.fontColor = color;
        }

        public LabelStyle(LabelStyle labelStyle) {
            this.font = labelStyle.font;
            if (labelStyle.fontColor != null) {
                this.fontColor = new Color(labelStyle.fontColor);
            }
            this.background = labelStyle.background;
        }
    }
}
