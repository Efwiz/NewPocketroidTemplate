package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Slider.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Slider extends ProgressBar {
    int button;
    int draggingPointer;
    boolean mouseOver;
    private float[] snapValues;
    private float threshold;
    private Interpolation visualInterpolationInverse;

    public Slider(float f, float f2, float f3, boolean z, Skin skin) {
        this(f, f2, f3, z, (SliderStyle) skin.get("default-".concat(z ? "vertical" : "horizontal"), SliderStyle.class));
    }

    public Slider(float f, float f2, float f3, boolean z, Skin skin, String str) {
        this(f, f2, f3, z, (SliderStyle) skin.get(str, SliderStyle.class));
    }

    public Slider(float f, float f2, float f3, boolean z, SliderStyle sliderStyle) {
        super(f, f2, f3, z, sliderStyle);
        this.button = -1;
        this.draggingPointer = -1;
        this.visualInterpolationInverse = Interpolation.linear;
        addListener(new InputListener() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Slider.1
            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent inputEvent, float f4, float f5, int i, int i2) {
                if (Slider.this.disabled) {
                    return false;
                }
                if ((Slider.this.button != -1 && Slider.this.button != i2) || Slider.this.draggingPointer != -1) {
                    return false;
                }
                Slider.this.draggingPointer = i;
                Slider.this.calculatePositionAndValue(f4, f5);
                return true;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent inputEvent, float f4, float f5, int i, int i2) {
                if (i != Slider.this.draggingPointer) {
                    return;
                }
                Slider.this.draggingPointer = -1;
                if (inputEvent.isTouchFocusCancel() || !Slider.this.calculatePositionAndValue(f4, f5)) {
                    ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent) Pools.obtain(ChangeListener.ChangeEvent.class);
                    Slider.this.fire(changeEvent);
                    Pools.free(changeEvent);
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent inputEvent, float f4, float f5, int i) {
                Slider.this.calculatePositionAndValue(f4, f5);
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void enter(InputEvent inputEvent, float f4, float f5, int i, Actor actor) {
                if (i == -1) {
                    Slider.this.mouseOver = true;
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void exit(InputEvent inputEvent, float f4, float f5, int i, Actor actor) {
                if (i == -1) {
                    Slider.this.mouseOver = false;
                }
            }
        });
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
    public SliderStyle getStyle() {
        return (SliderStyle) super.getStyle();
    }

    public boolean isOver() {
        return this.mouseOver;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
    protected Drawable getBackgroundDrawable() {
        SliderStyle sliderStyle = (SliderStyle) super.getStyle();
        if (this.disabled && sliderStyle.disabledBackground != null) {
            return sliderStyle.disabledBackground;
        }
        if (!isDragging() || sliderStyle.backgroundDown == null) {
            return (!this.mouseOver || sliderStyle.backgroundOver == null) ? sliderStyle.background : sliderStyle.backgroundOver;
        }
        return sliderStyle.backgroundDown;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
    protected Drawable getKnobDrawable() {
        SliderStyle sliderStyle = (SliderStyle) super.getStyle();
        if (this.disabled && sliderStyle.disabledKnob != null) {
            return sliderStyle.disabledKnob;
        }
        if (!isDragging() || sliderStyle.knobDown == null) {
            return (!this.mouseOver || sliderStyle.knobOver == null) ? sliderStyle.knob : sliderStyle.knobOver;
        }
        return sliderStyle.knobDown;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
    protected Drawable getKnobBeforeDrawable() {
        SliderStyle sliderStyle = (SliderStyle) super.getStyle();
        if (this.disabled && sliderStyle.disabledKnobBefore != null) {
            return sliderStyle.disabledKnobBefore;
        }
        if (!isDragging() || sliderStyle.knobBeforeDown == null) {
            return (!this.mouseOver || sliderStyle.knobBeforeOver == null) ? sliderStyle.knobBefore : sliderStyle.knobBeforeOver;
        }
        return sliderStyle.knobBeforeDown;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
    protected Drawable getKnobAfterDrawable() {
        SliderStyle sliderStyle = (SliderStyle) super.getStyle();
        if (this.disabled && sliderStyle.disabledKnobAfter != null) {
            return sliderStyle.disabledKnobAfter;
        }
        if (!isDragging() || sliderStyle.knobAfterDown == null) {
            return (!this.mouseOver || sliderStyle.knobAfterOver == null) ? sliderStyle.knobAfter : sliderStyle.knobAfterOver;
        }
        return sliderStyle.knobAfterDown;
    }

    boolean calculatePositionAndValue(float f, float f2) {
        float fApply;
        Drawable drawable = getStyle().knob;
        Drawable backgroundDrawable = getBackgroundDrawable();
        float f3 = this.position;
        float minValue = getMinValue();
        float maxValue = getMaxValue();
        if (this.vertical) {
            float height = (getHeight() - backgroundDrawable.getTopHeight()) - backgroundDrawable.getBottomHeight();
            float minHeight = drawable == null ? 0.0f : drawable.getMinHeight();
            this.position = (f2 - backgroundDrawable.getBottomHeight()) - (0.5f * minHeight);
            float f4 = height - minHeight;
            fApply = minValue + ((maxValue - minValue) * this.visualInterpolationInverse.apply(this.position / f4));
            this.position = Math.max(Math.min(0.0f, backgroundDrawable.getBottomHeight()), this.position);
            this.position = Math.min(f4, this.position);
        } else {
            float width = (getWidth() - backgroundDrawable.getLeftWidth()) - backgroundDrawable.getRightWidth();
            float minWidth = drawable == null ? 0.0f : drawable.getMinWidth();
            this.position = (f - backgroundDrawable.getLeftWidth()) - (0.5f * minWidth);
            float f5 = width - minWidth;
            fApply = minValue + ((maxValue - minValue) * this.visualInterpolationInverse.apply(this.position / f5));
            this.position = Math.max(Math.min(0.0f, backgroundDrawable.getLeftWidth()), this.position);
            this.position = Math.min(f5, this.position);
        }
        float fSnap = (Gdx.input.isKeyPressed(59) || Gdx.input.isKeyPressed(60)) ? fApply : snap(fApply);
        boolean value = setValue(fSnap);
        if (fSnap == fApply) {
            this.position = f3;
        }
        return value;
    }

    protected float snap(float f) {
        float[] fArr = this.snapValues;
        if (fArr == null || fArr.length == 0) {
            return f;
        }
        float f2 = 0.0f;
        int i = 0;
        float f3 = -1.0f;
        while (true) {
            float[] fArr2 = this.snapValues;
            if (i >= fArr2.length) {
                break;
            }
            float f4 = fArr2[i];
            float fAbs = Math.abs(f - f4);
            if (fAbs <= this.threshold && (f3 == -1.0f || fAbs < f3)) {
                f2 = f4;
                f3 = fAbs;
            }
            i++;
        }
        return f3 == -1.0f ? f : f2;
    }

    public void setSnapToValues(float f, float... fArr) {
        if (fArr != null && fArr.length == 0) {
            throw new IllegalArgumentException("values cannot be empty.");
        }
        this.snapValues = fArr;
        this.threshold = f;
    }

    @Deprecated
    public void setSnapToValues(float[] fArr, float f) {
        setSnapToValues(f, fArr);
    }

    public float[] getSnapToValues() {
        return this.snapValues;
    }

    public float getSnapToValuesThreshold() {
        return this.threshold;
    }

    public boolean isDragging() {
        return this.draggingPointer != -1;
    }

    public void setButton(int i) {
        this.button = i;
    }

    public void setVisualInterpolationInverse(Interpolation interpolation) {
        this.visualInterpolationInverse = interpolation;
    }

    public void setVisualPercent(float f) {
        setValue(this.min + ((this.max - this.min) * this.visualInterpolationInverse.apply(f)));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Slider$SliderStyle.class
      classes8.dex
     */
    public static class SliderStyle extends ProgressBar.ProgressBarStyle {
        public Drawable backgroundDown;
        public Drawable backgroundOver;
        public Drawable knobAfterDown;
        public Drawable knobAfterOver;
        public Drawable knobBeforeDown;
        public Drawable knobBeforeOver;
        public Drawable knobDown;
        public Drawable knobOver;

        public SliderStyle() {
        }

        public SliderStyle(Drawable drawable, Drawable drawable2) {
            super(drawable, drawable2);
        }

        public SliderStyle(SliderStyle sliderStyle) {
            super(sliderStyle);
            this.backgroundOver = sliderStyle.backgroundOver;
            this.backgroundDown = sliderStyle.backgroundDown;
            this.knobOver = sliderStyle.knobOver;
            this.knobDown = sliderStyle.knobDown;
            this.knobBeforeOver = sliderStyle.knobBeforeOver;
            this.knobBeforeDown = sliderStyle.knobBeforeDown;
            this.knobAfterOver = sliderStyle.knobAfterOver;
            this.knobAfterDown = sliderStyle.knobAfterDown;
        }
    }
}
