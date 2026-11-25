package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ProgressBar.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ProgressBar extends Widget implements Disableable {
    private float animateDuration;
    private float animateFromValue;
    private Interpolation animateInterpolation;
    private float animateTime;
    boolean disabled;
    float max;
    float min;
    float position;
    private boolean programmaticChangeEvents;
    private boolean round;
    float stepSize;
    private ProgressBarStyle style;
    private float value;
    final boolean vertical;
    private Interpolation visualInterpolation;

    public ProgressBar(float f, float f2, float f3, boolean z, Skin skin) {
        this(f, f2, f3, z, (ProgressBarStyle) skin.get("default-".concat(z ? "vertical" : "horizontal"), ProgressBarStyle.class));
    }

    public ProgressBar(float f, float f2, float f3, boolean z, Skin skin, String str) {
        this(f, f2, f3, z, (ProgressBarStyle) skin.get(str, ProgressBarStyle.class));
    }

    public ProgressBar(float f, float f2, float f3, boolean z, ProgressBarStyle progressBarStyle) {
        this.animateInterpolation = Interpolation.linear;
        this.visualInterpolation = Interpolation.linear;
        this.round = true;
        this.programmaticChangeEvents = true;
        if (f > f2) {
            throw new IllegalArgumentException("max must be > min. min,max: " + f + ", " + f2);
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("stepSize must be > 0: " + f3);
        }
        setStyle(progressBarStyle);
        this.min = f;
        this.max = f2;
        this.stepSize = f3;
        this.vertical = z;
        this.value = f;
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void setStyle(ProgressBarStyle progressBarStyle) {
        if (progressBarStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = progressBarStyle;
        invalidateHierarchy();
    }

    public ProgressBarStyle getStyle() {
        return this.style;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void act(float f) {
        super.act(f);
        float f2 = this.animateTime;
        if (f2 > 0.0f) {
            this.animateTime = f2 - f;
            Stage stage = getStage();
            if (stage == null || !stage.getActionsRequestRendering()) {
                return;
            }
            Gdx.graphics.requestRendering();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        float leftWidth;
        Drawable drawable = this.style.knob;
        Drawable knobDrawable = getKnobDrawable();
        Drawable backgroundDrawable = getBackgroundDrawable();
        Drawable knobBeforeDrawable = getKnobBeforeDrawable();
        Drawable knobAfterDrawable = getKnobAfterDrawable();
        Color color = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float bottomHeight = 0.0f;
        float minHeight = drawable == null ? 0.0f : drawable.getMinHeight();
        float minWidth = drawable == null ? 0.0f : drawable.getMinWidth();
        float visualPercent = getVisualPercent();
        batch.setColor(color.r, color.g, color.b, color.a * f);
        if (this.vertical) {
            if (backgroundDrawable != null) {
                drawRound(batch, backgroundDrawable, x + ((width - backgroundDrawable.getMinWidth()) * 0.5f), y, backgroundDrawable.getMinWidth(), height);
                float topHeight = backgroundDrawable.getTopHeight();
                bottomHeight = backgroundDrawable.getBottomHeight();
                height -= topHeight + bottomHeight;
            }
            float f2 = height - minHeight;
            float fClamp = MathUtils.clamp(f2 * visualPercent, 0.0f, f2);
            this.position = bottomHeight + fClamp;
            float f3 = minHeight * 0.5f;
            if (knobBeforeDrawable != null) {
                drawRound(batch, knobBeforeDrawable, x + ((width - knobBeforeDrawable.getMinWidth()) * 0.5f), y + bottomHeight, knobBeforeDrawable.getMinWidth(), fClamp + f3);
            }
            if (knobAfterDrawable != null) {
                drawRound(batch, knobAfterDrawable, x + ((width - knobAfterDrawable.getMinWidth()) * 0.5f), this.position + y + f3, knobAfterDrawable.getMinWidth(), f2 - (this.round ? (float) Math.ceil(fClamp - f3) : fClamp - f3));
            }
            if (knobDrawable != 0) {
                float minWidth2 = knobDrawable.getMinWidth();
                float minHeight2 = knobDrawable.getMinHeight();
                drawRound(batch, knobDrawable, x + ((width - minWidth2) * 0.5f), y + this.position + ((minHeight - minHeight2) * 0.5f), minWidth2, minHeight2);
                return;
            }
            return;
        }
        if (backgroundDrawable != null) {
            drawRound(batch, backgroundDrawable, x, Math.round(((height - backgroundDrawable.getMinHeight()) * 0.5f) + y), width, Math.round(backgroundDrawable.getMinHeight()));
            leftWidth = backgroundDrawable.getLeftWidth();
            width -= backgroundDrawable.getRightWidth() + leftWidth;
        } else {
            leftWidth = 0.0f;
        }
        float f4 = width - minWidth;
        float fClamp2 = MathUtils.clamp(f4 * visualPercent, 0.0f, f4);
        this.position = leftWidth + fClamp2;
        float f5 = minWidth * 0.5f;
        if (knobBeforeDrawable != null) {
            drawRound(batch, knobBeforeDrawable, x + leftWidth, y + ((height - knobBeforeDrawable.getMinHeight()) * 0.5f), fClamp2 + f5, knobBeforeDrawable.getMinHeight());
        }
        if (knobAfterDrawable != null) {
            drawRound(batch, knobAfterDrawable, this.position + x + f5, y + ((height - knobAfterDrawable.getMinHeight()) * 0.5f), f4 - (this.round ? (float) Math.ceil(fClamp2 - f5) : fClamp2 - f5), knobAfterDrawable.getMinHeight());
        }
        if (knobDrawable != null) {
            float minWidth3 = knobDrawable.getMinWidth();
            float minHeight3 = knobDrawable.getMinHeight();
            drawRound(batch, knobDrawable, x + this.position + ((minWidth - minWidth3) * 0.5f), y + ((height - minHeight3) * 0.5f), minWidth3, minHeight3);
        }
    }

    private void drawRound(Batch batch, Drawable drawable, float f, float f2, float f3, float f4) {
        if (this.round) {
            f = (float) Math.floor(f);
            f2 = (float) Math.floor(f2);
            f3 = (float) Math.ceil(f3);
            f4 = (float) Math.ceil(f4);
        }
        drawable.draw(batch, f, f2, f3, f4);
    }

    public float getValue() {
        return this.value;
    }

    public float getVisualValue() {
        float f = this.animateTime;
        return f > 0.0f ? this.animateInterpolation.apply(this.animateFromValue, this.value, 1.0f - (f / this.animateDuration)) : this.value;
    }

    public void updateVisualValue() {
        this.animateTime = 0.0f;
    }

    public float getPercent() {
        float f = this.min;
        float f2 = this.max;
        if (f == f2) {
            return 0.0f;
        }
        return (this.value - f) / (f2 - f);
    }

    public float getVisualPercent() {
        if (this.min == this.max) {
            return 0.0f;
        }
        Interpolation interpolation = this.visualInterpolation;
        float visualValue = getVisualValue();
        float f = this.min;
        return interpolation.apply((visualValue - f) / (this.max - f));
    }

    protected Drawable getBackgroundDrawable() {
        return (!this.disabled || this.style.disabledBackground == null) ? this.style.background : this.style.disabledBackground;
    }

    protected Drawable getKnobDrawable() {
        return (!this.disabled || this.style.disabledKnob == null) ? this.style.knob : this.style.disabledKnob;
    }

    protected Drawable getKnobBeforeDrawable() {
        return (!this.disabled || this.style.disabledKnobBefore == null) ? this.style.knobBefore : this.style.disabledKnobBefore;
    }

    protected Drawable getKnobAfterDrawable() {
        return (!this.disabled || this.style.disabledKnobAfter == null) ? this.style.knobAfter : this.style.disabledKnobAfter;
    }

    protected float getKnobPosition() {
        return this.position;
    }

    public boolean setValue(float f) {
        float fClamp = clamp(round(f));
        float f2 = this.value;
        if (fClamp == f2) {
            return false;
        }
        float visualValue = getVisualValue();
        this.value = fClamp;
        if (this.programmaticChangeEvents) {
            ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent) Pools.obtain(ChangeListener.ChangeEvent.class);
            boolean zFire = fire(changeEvent);
            Pools.free(changeEvent);
            if (zFire) {
                this.value = f2;
                return false;
            }
        }
        float f3 = this.animateDuration;
        if (f3 <= 0.0f) {
            return true;
        }
        this.animateFromValue = visualValue;
        this.animateTime = f3;
        return true;
    }

    protected float round(float f) {
        return Math.round(f / this.stepSize) * this.stepSize;
    }

    protected float clamp(float f) {
        return MathUtils.clamp(f, this.min, this.max);
    }

    public void setRange(float f, float f2) {
        if (f > f2) {
            throw new IllegalArgumentException("min must be <= max: " + f + " <= " + f2);
        }
        this.min = f;
        this.max = f2;
        float f3 = this.value;
        if (f3 < f) {
            setValue(f);
        } else if (f3 > f2) {
            setValue(f2);
        }
    }

    public void setStepSize(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("steps must be > 0: " + f);
        }
        this.stepSize = f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        if (!this.vertical) {
            return 140.0f;
        }
        Drawable drawable = this.style.knob;
        Drawable backgroundDrawable = getBackgroundDrawable();
        return Math.max(drawable == null ? 0.0f : drawable.getMinWidth(), backgroundDrawable != null ? backgroundDrawable.getMinWidth() : 0.0f);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        if (this.vertical) {
            return 140.0f;
        }
        Drawable drawable = this.style.knob;
        Drawable backgroundDrawable = getBackgroundDrawable();
        return Math.max(drawable == null ? 0.0f : drawable.getMinHeight(), backgroundDrawable != null ? backgroundDrawable.getMinHeight() : 0.0f);
    }

    public float getMinValue() {
        return this.min;
    }

    public float getMaxValue() {
        return this.max;
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setAnimateDuration(float f) {
        this.animateDuration = f;
    }

    public void setAnimateInterpolation(Interpolation interpolation) {
        if (interpolation == null) {
            throw new IllegalArgumentException("animateInterpolation cannot be null.");
        }
        this.animateInterpolation = interpolation;
    }

    public void setVisualInterpolation(Interpolation interpolation) {
        this.visualInterpolation = interpolation;
    }

    public void setRound(boolean z) {
        this.round = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public void setDisabled(boolean z) {
        this.disabled = z;
    }

    public boolean isAnimating() {
        return this.animateTime > 0.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public boolean isDisabled() {
        return this.disabled;
    }

    public boolean isVertical() {
        return this.vertical;
    }

    public void setProgrammaticChangeEvents(boolean z) {
        this.programmaticChangeEvents = z;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ProgressBar$ProgressBarStyle.class
      classes8.dex
     */
    public static class ProgressBarStyle {
        public Drawable background;
        public Drawable disabledBackground;
        public Drawable disabledKnob;
        public Drawable disabledKnobAfter;
        public Drawable disabledKnobBefore;
        public Drawable knob;
        public Drawable knobAfter;
        public Drawable knobBefore;

        public ProgressBarStyle() {
        }

        public ProgressBarStyle(Drawable drawable, Drawable drawable2) {
            this.background = drawable;
            this.knob = drawable2;
        }

        public ProgressBarStyle(ProgressBarStyle progressBarStyle) {
            this.background = progressBarStyle.background;
            this.disabledBackground = progressBarStyle.disabledBackground;
            this.knob = progressBarStyle.knob;
            this.disabledKnob = progressBarStyle.disabledKnob;
            this.knobBefore = progressBarStyle.knobBefore;
            this.disabledKnobBefore = progressBarStyle.disabledKnobBefore;
            this.knobAfter = progressBarStyle.knobAfter;
            this.disabledKnobAfter = progressBarStyle.disabledKnobAfter;
        }
    }
}
