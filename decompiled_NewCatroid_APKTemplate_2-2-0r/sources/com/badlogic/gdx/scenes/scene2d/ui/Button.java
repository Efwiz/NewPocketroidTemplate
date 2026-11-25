package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Button.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Button extends Table implements Disableable {
    ButtonGroup buttonGroup;
    private ClickListener clickListener;
    boolean isChecked;
    boolean isDisabled;
    private boolean programmaticChangeEvents;
    private ButtonStyle style;

    public Button(Skin skin) {
        super(skin);
        this.programmaticChangeEvents = true;
        initialize();
        setStyle((ButtonStyle) skin.get(ButtonStyle.class));
        setSize(getPrefWidth(), getPrefHeight());
    }

    public Button(Skin skin, String str) {
        super(skin);
        this.programmaticChangeEvents = true;
        initialize();
        setStyle((ButtonStyle) skin.get(str, ButtonStyle.class));
        setSize(getPrefWidth(), getPrefHeight());
    }

    public Button(Actor actor, Skin skin, String str) {
        this(actor, (ButtonStyle) skin.get(str, ButtonStyle.class));
        setSkin(skin);
    }

    public Button(Actor actor, ButtonStyle buttonStyle) {
        this.programmaticChangeEvents = true;
        initialize();
        add(actor);
        setStyle(buttonStyle);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public Button(ButtonStyle buttonStyle) {
        this.programmaticChangeEvents = true;
        initialize();
        setStyle(buttonStyle);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public Button() {
        this.programmaticChangeEvents = true;
        initialize();
    }

    private void initialize() {
        setTouchable(Touchable.enabled);
        ClickListener clickListener = new ClickListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Button.1
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ClickListener
            public void clicked(InputEvent inputEvent, float f, float f2) {
                if (Button.this.isDisabled()) {
                    return;
                }
                Button button = Button.this;
                button.setChecked(!button.isChecked, true);
            }
        };
        this.clickListener = clickListener;
        addListener(clickListener);
    }

    public Button(Drawable drawable) {
        this(new ButtonStyle(drawable, null, null));
    }

    public Button(Drawable drawable, Drawable drawable2) {
        this(new ButtonStyle(drawable, drawable2, null));
    }

    public Button(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        this(new ButtonStyle(drawable, drawable2, drawable3));
    }

    public Button(Actor actor, Skin skin) {
        this(actor, (ButtonStyle) skin.get(ButtonStyle.class));
    }

    public void setChecked(boolean z) {
        setChecked(z, this.programmaticChangeEvents);
    }

    void setChecked(boolean z, boolean z2) {
        if (this.isChecked == z) {
            return;
        }
        ButtonGroup buttonGroup = this.buttonGroup;
        if (buttonGroup == null || buttonGroup.canCheck(this, z)) {
            this.isChecked = z;
            if (z2) {
                ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent) Pools.obtain(ChangeListener.ChangeEvent.class);
                if (fire(changeEvent)) {
                    this.isChecked = !z;
                }
                Pools.free(changeEvent);
            }
        }
    }

    public void toggle() {
        setChecked(!this.isChecked);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public boolean isPressed() {
        return this.clickListener.isVisualPressed();
    }

    public boolean isOver() {
        return this.clickListener.isOver();
    }

    public ClickListener getClickListener() {
        return this.clickListener;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public boolean isDisabled() {
        return this.isDisabled;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public void setDisabled(boolean z) {
        this.isDisabled = z;
    }

    public void setProgrammaticChangeEvents(boolean z) {
        this.programmaticChangeEvents = z;
    }

    public void setStyle(ButtonStyle buttonStyle) {
        if (buttonStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = buttonStyle;
        setBackground(getBackgroundDrawable());
    }

    public ButtonStyle getStyle() {
        return this.style;
    }

    public ButtonGroup getButtonGroup() {
        return this.buttonGroup;
    }

    protected Drawable getBackgroundDrawable() {
        if (isDisabled() && this.style.disabled != null) {
            return this.style.disabled;
        }
        if (isPressed()) {
            if (isChecked() && this.style.checkedDown != null) {
                return this.style.checkedDown;
            }
            if (this.style.down != null) {
                return this.style.down;
            }
        }
        if (isOver()) {
            if (isChecked()) {
                if (this.style.checkedOver != null) {
                    return this.style.checkedOver;
                }
            } else if (this.style.over != null) {
                return this.style.over;
            }
        }
        boolean zHasKeyboardFocus = hasKeyboardFocus();
        if (isChecked()) {
            if (zHasKeyboardFocus && this.style.checkedFocused != null) {
                return this.style.checkedFocused;
            }
            if (this.style.checked != null) {
                return this.style.checked;
            }
            if (isOver() && this.style.over != null) {
                return this.style.over;
            }
        }
        return (!zHasKeyboardFocus || this.style.focused == null) ? this.style.up : this.style.focused;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        float f2;
        float f3;
        validate();
        setBackground(getBackgroundDrawable());
        if (isPressed() && !isDisabled()) {
            f2 = this.style.pressedOffsetX;
            f3 = this.style.pressedOffsetY;
        } else if (isChecked() && !isDisabled()) {
            f2 = this.style.checkedOffsetX;
            f3 = this.style.checkedOffsetY;
        } else {
            f2 = this.style.unpressedOffsetX;
            f3 = this.style.unpressedOffsetY;
        }
        boolean z = (f2 == 0.0f && f3 == 0.0f) ? false : true;
        SnapshotArray<Actor> children = getChildren();
        if (z) {
            for (int i = 0; i < children.size; i++) {
                children.get(i).moveBy(f2, f3);
            }
        }
        super.draw(batch, f);
        if (z) {
            for (int i2 = 0; i2 < children.size; i2++) {
                children.get(i2).moveBy(-f2, -f3);
            }
        }
        Stage stage = getStage();
        if (stage == null || !stage.getActionsRequestRendering() || isPressed() == this.clickListener.isPressed()) {
            return;
        }
        Gdx.graphics.requestRendering();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        float prefWidth = super.getPrefWidth();
        if (this.style.up != null) {
            prefWidth = Math.max(prefWidth, this.style.up.getMinWidth());
        }
        if (this.style.down != null) {
            prefWidth = Math.max(prefWidth, this.style.down.getMinWidth());
        }
        return this.style.checked != null ? Math.max(prefWidth, this.style.checked.getMinWidth()) : prefWidth;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        float prefHeight = super.getPrefHeight();
        if (this.style.up != null) {
            prefHeight = Math.max(prefHeight, this.style.up.getMinHeight());
        }
        if (this.style.down != null) {
            prefHeight = Math.max(prefHeight, this.style.down.getMinHeight());
        }
        return this.style.checked != null ? Math.max(prefHeight, this.style.checked.getMinHeight()) : prefHeight;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinWidth() {
        return getPrefWidth();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Table, com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getMinHeight() {
        return getPrefHeight();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Button$ButtonStyle.class
      classes8.dex
     */
    public static class ButtonStyle {
        public Drawable checked;
        public Drawable checkedDown;
        public Drawable checkedFocused;
        public float checkedOffsetX;
        public float checkedOffsetY;
        public Drawable checkedOver;
        public Drawable disabled;
        public Drawable down;
        public Drawable focused;
        public Drawable over;
        public float pressedOffsetX;
        public float pressedOffsetY;
        public float unpressedOffsetX;
        public float unpressedOffsetY;
        public Drawable up;

        public ButtonStyle() {
        }

        public ButtonStyle(Drawable drawable, Drawable drawable2, Drawable drawable3) {
            this.up = drawable;
            this.down = drawable2;
            this.checked = drawable3;
        }

        public ButtonStyle(ButtonStyle buttonStyle) {
            this.up = buttonStyle.up;
            this.down = buttonStyle.down;
            this.over = buttonStyle.over;
            this.focused = buttonStyle.focused;
            this.disabled = buttonStyle.disabled;
            this.checked = buttonStyle.checked;
            this.checkedOver = buttonStyle.checkedOver;
            this.checkedDown = buttonStyle.checkedDown;
            this.checkedFocused = buttonStyle.checkedFocused;
            this.pressedOffsetX = buttonStyle.pressedOffsetX;
            this.pressedOffsetY = buttonStyle.pressedOffsetY;
            this.unpressedOffsetX = buttonStyle.unpressedOffsetX;
            this.unpressedOffsetY = buttonStyle.unpressedOffsetY;
            this.checkedOffsetX = buttonStyle.checkedOffsetX;
            this.checkedOffsetY = buttonStyle.checkedOffsetY;
        }
    }
}
