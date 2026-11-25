package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.graphics.Color;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/ColorAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ColorAction extends TemporalAction {
    private Color color;
    private final Color end = new Color();
    private float startA;
    private float startB;
    private float startG;
    private float startR;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        if (this.color == null) {
            this.color = this.target.getColor();
        }
        this.startR = this.color.r;
        this.startG = this.color.g;
        this.startB = this.color.b;
        this.startA = this.color.a;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float f) {
        if (f == 0.0f) {
            this.color.set(this.startR, this.startG, this.startB, this.startA);
            return;
        }
        if (f == 1.0f) {
            this.color.set(this.end);
            return;
        }
        this.color.set(this.startR + ((this.end.r - this.startR) * f), this.startG + ((this.end.g - this.startG) * f), this.startB + ((this.end.b - this.startB) * f), this.startA + ((this.end.a - this.startA) * f));
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.color = null;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getEndColor() {
        return this.end;
    }

    public void setEndColor(Color color) {
        this.end.set(color);
    }
}
