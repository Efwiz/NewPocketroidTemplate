package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextButton.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextButton extends Button {
    private Label label;
    private TextButtonStyle style;

    public TextButton(String str, Skin skin) {
        this(str, (TextButtonStyle) skin.get(TextButtonStyle.class));
        setSkin(skin);
    }

    public TextButton(String str, Skin skin, String str2) {
        this(str, (TextButtonStyle) skin.get(str2, TextButtonStyle.class));
        setSkin(skin);
    }

    public TextButton(String str, TextButtonStyle textButtonStyle) {
        setStyle(textButtonStyle);
        Label labelNewLabel = newLabel(str, new Label.LabelStyle(textButtonStyle.font, textButtonStyle.fontColor));
        this.label = labelNewLabel;
        labelNewLabel.setAlignment(1);
        add(this.label).grow();
        setSize(getPrefWidth(), getPrefHeight());
    }

    protected Label newLabel(String str, Label.LabelStyle labelStyle) {
        return new Label(str, labelStyle);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button
    public void setStyle(Button.ButtonStyle buttonStyle) {
        if (buttonStyle == null) {
            throw new NullPointerException("style cannot be null");
        }
        if (!(buttonStyle instanceof TextButtonStyle)) {
            throw new IllegalArgumentException("style must be a TextButtonStyle.");
        }
        TextButtonStyle textButtonStyle = (TextButtonStyle) buttonStyle;
        this.style = textButtonStyle;
        super.setStyle(buttonStyle);
        Label label = this.label;
        if (label != null) {
            Label.LabelStyle style = label.getStyle();
            style.font = textButtonStyle.font;
            style.fontColor = textButtonStyle.fontColor;
            this.label.setStyle(style);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Button
    public TextButtonStyle getStyle() {
        return this.style;
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
        this.label.getStyle().fontColor = getFontColor();
        super.draw(batch, f);
    }

    public void setLabel(Label label) {
        if (label == null) {
            throw new IllegalArgumentException("label cannot be null.");
        }
        getLabelCell().setActor(label);
        this.label = label;
    }

    public Label getLabel() {
        return this.label;
    }

    public Cell<Label> getLabelCell() {
        return getCell(this.label);
    }

    public void setText(String str) {
        this.label.setText(str);
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
        return (name3.indexOf(36) != -1 ? "TextButton " : "") + name3 + ": " + ((Object) this.label.getText());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextButton$TextButtonStyle.class
      classes8.dex
     */
    public static class TextButtonStyle extends Button.ButtonStyle {
        public Color checkedDownFontColor;
        public Color checkedFocusedFontColor;
        public Color checkedFontColor;
        public Color checkedOverFontColor;
        public Color disabledFontColor;
        public Color downFontColor;
        public Color focusedFontColor;
        public BitmapFont font;
        public Color fontColor;
        public Color overFontColor;

        public TextButtonStyle() {
        }

        public TextButtonStyle(Drawable drawable, Drawable drawable2, Drawable drawable3, BitmapFont bitmapFont) {
            super(drawable, drawable2, drawable3);
            this.font = bitmapFont;
        }

        public TextButtonStyle(TextButtonStyle textButtonStyle) {
            super(textButtonStyle);
            this.font = textButtonStyle.font;
            if (textButtonStyle.fontColor != null) {
                this.fontColor = new Color(textButtonStyle.fontColor);
            }
            if (textButtonStyle.downFontColor != null) {
                this.downFontColor = new Color(textButtonStyle.downFontColor);
            }
            if (textButtonStyle.overFontColor != null) {
                this.overFontColor = new Color(textButtonStyle.overFontColor);
            }
            if (textButtonStyle.focusedFontColor != null) {
                this.focusedFontColor = new Color(textButtonStyle.focusedFontColor);
            }
            if (textButtonStyle.disabledFontColor != null) {
                this.disabledFontColor = new Color(textButtonStyle.disabledFontColor);
            }
            if (textButtonStyle.checkedFontColor != null) {
                this.checkedFontColor = new Color(textButtonStyle.checkedFontColor);
            }
            if (textButtonStyle.checkedDownFontColor != null) {
                this.checkedDownFontColor = new Color(textButtonStyle.checkedDownFontColor);
            }
            if (textButtonStyle.checkedOverFontColor != null) {
                this.checkedOverFontColor = new Color(textButtonStyle.checkedOverFontColor);
            }
            if (textButtonStyle.checkedFocusedFontColor != null) {
                this.checkedFocusedFontColor = new Color(textButtonStyle.checkedFocusedFontColor);
            }
        }
    }
}
