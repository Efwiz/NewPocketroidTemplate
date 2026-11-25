package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ImageTextButton.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ImageTextButton extends Button {
    private final Image image;
    private Label label;
    private ImageTextButtonStyle style;

    public ImageTextButton(String str, Skin skin) {
        this(str, (ImageTextButtonStyle) skin.get(ImageTextButtonStyle.class));
        setSkin(skin);
    }

    public ImageTextButton(String str, Skin skin, String str2) {
        this(str, (ImageTextButtonStyle) skin.get(str2, ImageTextButtonStyle.class));
        setSkin(skin);
    }

    public ImageTextButton(String str, ImageTextButtonStyle imageTextButtonStyle) {
        super(imageTextButtonStyle);
        this.style = imageTextButtonStyle;
        defaults().space(3.0f);
        Image imageNewImage = newImage();
        this.image = imageNewImage;
        Label labelNewLabel = newLabel(str, new Label.LabelStyle(imageTextButtonStyle.font, imageTextButtonStyle.fontColor));
        this.label = labelNewLabel;
        labelNewLabel.setAlignment(1);
        add(imageNewImage);
        add(this.label);
        setStyle(imageTextButtonStyle);
        setSize(getPrefWidth(), getPrefHeight());
    }

    protected Image newImage() {
        return new Image((Drawable) null, Scaling.fit);
    }

    protected Label newLabel(String str, Label.LabelStyle labelStyle) {
        return new Label(str, labelStyle);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button
    public void setStyle(Button.ButtonStyle buttonStyle) {
        if (!(buttonStyle instanceof ImageTextButtonStyle)) {
            throw new IllegalArgumentException("style must be a ImageTextButtonStyle.");
        }
        ImageTextButtonStyle imageTextButtonStyle = (ImageTextButtonStyle) buttonStyle;
        this.style = imageTextButtonStyle;
        super.setStyle(buttonStyle);
        if (this.image != null) {
            updateImage();
        }
        Label label = this.label;
        if (label != null) {
            Label.LabelStyle style = label.getStyle();
            style.font = imageTextButtonStyle.font;
            style.fontColor = getFontColor();
            this.label.setStyle(style);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button
    public ImageTextButtonStyle getStyle() {
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

    protected Color getFontColor() {
        if (isDisabled() && this.style.disabledFontColor != null) {
            return this.style.disabledFontColor;
        }
        if (isPressed()) {
            if (isChecked() && this.style.checkedDownFontColor != null) {
                return this.style.checkedDownFontColor;
            }
            if (this.style.downFontColor != null) {
                return this.style.downFontColor;
            }
        }
        if (isOver()) {
            if (isChecked()) {
                if (this.style.checkedOverFontColor != null) {
                    return this.style.checkedOverFontColor;
                }
            } else if (this.style.overFontColor != null) {
                return this.style.overFontColor;
            }
        }
        boolean zHasKeyboardFocus = hasKeyboardFocus();
        if (isChecked()) {
            if (zHasKeyboardFocus && this.style.checkedFocusedFontColor != null) {
                return this.style.checkedFocusedFontColor;
            }
            if (this.style.checkedFontColor != null) {
                return this.style.checkedFontColor;
            }
            if (isOver() && this.style.overFontColor != null) {
                return this.style.overFontColor;
            }
        }
        return (!zHasKeyboardFocus || this.style.focusedFontColor == null) ? this.style.fontColor : this.style.focusedFontColor;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button, com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        updateImage();
        this.label.getStyle().fontColor = getFontColor();
        super.draw(batch, f);
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return getCell(this.image);
    }

    public void setLabel(Label label) {
        getLabelCell().setActor(label);
        this.label = label;
    }

    public Label getLabel() {
        return this.label;
    }

    public Cell getLabelCell() {
        return getCell(this.label);
    }

    public void setText(CharSequence charSequence) {
        this.label.setText(charSequence);
    }

    public CharSequence getText() {
        return this.label.getText();
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
        return (name3.indexOf(36) != -1 ? "ImageTextButton " : "") + name3 + ": " + this.image.getDrawable() + " " + ((Object) this.label.getText());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ImageTextButton$ImageTextButtonStyle.class
      classes8.dex
     */
    public static class ImageTextButtonStyle extends TextButton.TextButtonStyle {
        public Drawable imageChecked;
        public Drawable imageCheckedDown;
        public Drawable imageCheckedOver;
        public Drawable imageDisabled;
        public Drawable imageDown;
        public Drawable imageOver;
        public Drawable imageUp;

        public ImageTextButtonStyle() {
        }

        public ImageTextButtonStyle(Drawable drawable, Drawable drawable2, Drawable drawable3, BitmapFont bitmapFont) {
            super(drawable, drawable2, drawable3, bitmapFont);
        }

        public ImageTextButtonStyle(ImageTextButtonStyle imageTextButtonStyle) {
            super(imageTextButtonStyle);
            this.imageUp = imageTextButtonStyle.imageUp;
            this.imageDown = imageTextButtonStyle.imageDown;
            this.imageOver = imageTextButtonStyle.imageOver;
            this.imageDisabled = imageTextButtonStyle.imageDisabled;
            this.imageChecked = imageTextButtonStyle.imageChecked;
            this.imageCheckedDown = imageTextButtonStyle.imageCheckedDown;
            this.imageCheckedOver = imageTextButtonStyle.imageCheckedOver;
        }

        public ImageTextButtonStyle(TextButton.TextButtonStyle textButtonStyle) {
            super(textButtonStyle);
        }
    }
}
