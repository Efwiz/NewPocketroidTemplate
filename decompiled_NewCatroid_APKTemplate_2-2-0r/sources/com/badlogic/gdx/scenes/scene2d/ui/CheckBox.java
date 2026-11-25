package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/CheckBox.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class CheckBox extends TextButton {
    private Image image;
    private Cell imageCell;
    private CheckBoxStyle style;

    public CheckBox(String str, Skin skin) {
        this(str, (CheckBoxStyle) skin.get(CheckBoxStyle.class));
    }

    public CheckBox(String str, Skin skin, String str2) {
        this(str, (CheckBoxStyle) skin.get(str2, CheckBoxStyle.class));
    }

    public CheckBox(String str, CheckBoxStyle checkBoxStyle) {
        super(str, checkBoxStyle);
        Label label = getLabel();
        label.setAlignment(8);
        Image imageNewImage = newImage();
        this.image = imageNewImage;
        imageNewImage.setDrawable(checkBoxStyle.checkboxOff);
        clearChildren();
        this.imageCell = add(this.image);
        add(label);
        setSize(getPrefWidth(), getPrefHeight());
    }

    protected Image newImage() {
        return new Image((Drawable) null, Scaling.none);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextButton, com.badlogic.gdx.scenes.scene2d.ui.Button
    public void setStyle(Button.ButtonStyle buttonStyle) {
        if (!(buttonStyle instanceof CheckBoxStyle)) {
            throw new IllegalArgumentException("style must be a CheckBoxStyle.");
        }
        this.style = (CheckBoxStyle) buttonStyle;
        super.setStyle(buttonStyle);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextButton, com.badlogic.gdx.scenes.scene2d.ui.Button
    public CheckBoxStyle getStyle() {
        return this.style;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextButton, com.badlogic.gdx.scenes.scene2d.ui.Button, com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        this.image.setDrawable(getImageDrawable());
        super.draw(batch, f);
    }

    protected Drawable getImageDrawable() {
        if (isDisabled()) {
            return (!this.isChecked || this.style.checkboxOnDisabled == null) ? this.style.checkboxOffDisabled : this.style.checkboxOnDisabled;
        }
        boolean z = isOver() && !isDisabled();
        if (!this.isChecked || this.style.checkboxOn == null) {
            return (!z || this.style.checkboxOver == null) ? this.style.checkboxOff : this.style.checkboxOver;
        }
        return (!z || this.style.checkboxOnOver == null) ? this.style.checkboxOn : this.style.checkboxOnOver;
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return this.imageCell;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/CheckBox$CheckBoxStyle.class
      classes8.dex
     */
    public static class CheckBoxStyle extends TextButton.TextButtonStyle {
        public Drawable checkboxOff;
        public Drawable checkboxOffDisabled;
        public Drawable checkboxOn;
        public Drawable checkboxOnDisabled;
        public Drawable checkboxOnOver;
        public Drawable checkboxOver;

        public CheckBoxStyle() {
        }

        public CheckBoxStyle(Drawable drawable, Drawable drawable2, BitmapFont bitmapFont, Color color) {
            this.checkboxOff = drawable;
            this.checkboxOn = drawable2;
            this.font = bitmapFont;
            this.fontColor = color;
        }

        public CheckBoxStyle(CheckBoxStyle checkBoxStyle) {
            super(checkBoxStyle);
            this.checkboxOff = checkBoxStyle.checkboxOff;
            this.checkboxOn = checkBoxStyle.checkboxOn;
            this.checkboxOnOver = checkBoxStyle.checkboxOnOver;
            this.checkboxOver = checkBoxStyle.checkboxOver;
            this.checkboxOnDisabled = checkBoxStyle.checkboxOnDisabled;
            this.checkboxOffDisabled = checkBoxStyle.checkboxOffDisabled;
        }
    }
}
