package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ImageButton.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ImageButton extends Button {
    private final Image image;
    private ImageButtonStyle style;

    public ImageButton(Skin skin) {
        this((ImageButtonStyle) skin.get(ImageButtonStyle.class));
        setSkin(skin);
    }

    public ImageButton(Skin skin, String str) {
        this((ImageButtonStyle) skin.get(str, ImageButtonStyle.class));
        setSkin(skin);
    }

    public ImageButton(ImageButtonStyle imageButtonStyle) {
        super(imageButtonStyle);
        Image imageNewImage = newImage();
        this.image = imageNewImage;
        add(imageNewImage);
        setStyle(imageButtonStyle);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public ImageButton(Drawable drawable) {
        this(new ImageButtonStyle(null, null, null, drawable, null, null));
    }

    public ImageButton(Drawable drawable, Drawable drawable2) {
        this(new ImageButtonStyle(null, null, null, drawable, drawable2, null));
    }

    public ImageButton(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        this(new ImageButtonStyle(null, null, null, drawable, drawable2, drawable3));
    }

    protected Image newImage() {
        return new Image((Drawable) null, Scaling.fit);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button
    public void setStyle(Button.ButtonStyle buttonStyle) {
        if (!(buttonStyle instanceof ImageButtonStyle)) {
            throw new IllegalArgumentException("style must be an ImageButtonStyle.");
        }
        this.style = (ImageButtonStyle) buttonStyle;
        super.setStyle(buttonStyle);
        if (this.image != null) {
            updateImage();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button
    public ImageButtonStyle getStyle() {
        return this.style;
    }

    protected Drawable getImageDrawable() {
        if (isDisabled() && this.style.imageDisabled != null) {
            return this.style.imageDisabled;
        }
        if (isPressed()) {
            if (isChecked() && this.style.imageCheckedDown != null) {
                return this.style.imageCheckedDown;
            }
            if (this.style.imageDown != null) {
                return this.style.imageDown;
            }
        }
        if (isOver()) {
            if (isChecked()) {
                if (this.style.imageCheckedOver != null) {
                    return this.style.imageCheckedOver;
                }
            } else if (this.style.imageOver != null) {
                return this.style.imageOver;
            }
        }
        if (isChecked()) {
            if (this.style.imageChecked != null) {
                return this.style.imageChecked;
            }
            if (isOver() && this.style.imageOver != null) {
                return this.style.imageOver;
            }
        }
        return this.style.imageUp;
    }

    protected void updateImage() {
        this.image.setDrawable(getImageDrawable());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button, com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        updateImage();
        super.draw(batch, f);
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return getCell(this.image);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
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
        return (name3.indexOf(36) != -1 ? "ImageButton " : "") + name3 + ": " + this.image.getDrawable();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ImageButton$ImageButtonStyle.class
      classes8.dex
     */
    public static class ImageButtonStyle extends Button.ButtonStyle {
        public Drawable imageChecked;
        public Drawable imageCheckedDown;
        public Drawable imageCheckedOver;
        public Drawable imageDisabled;
        public Drawable imageDown;
        public Drawable imageOver;
        public Drawable imageUp;

        public ImageButtonStyle() {
        }

        public ImageButtonStyle(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
            super(drawable, drawable2, drawable3);
            this.imageUp = drawable4;
            this.imageDown = drawable5;
            this.imageChecked = drawable6;
        }

        public ImageButtonStyle(ImageButtonStyle imageButtonStyle) {
            super(imageButtonStyle);
            this.imageUp = imageButtonStyle.imageUp;
            this.imageDown = imageButtonStyle.imageDown;
            this.imageOver = imageButtonStyle.imageOver;
            this.imageDisabled = imageButtonStyle.imageDisabled;
            this.imageChecked = imageButtonStyle.imageChecked;
            this.imageCheckedDown = imageButtonStyle.imageCheckedDown;
            this.imageCheckedOver = imageButtonStyle.imageCheckedOver;
        }

        public ImageButtonStyle(Button.ButtonStyle buttonStyle) {
            super(buttonStyle);
        }
    }
}
