package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.graphics.Color;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/AlphaAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AlphaAction extends TemporalAction {
    private Color color;
    private float end;
    private float start;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        if (this.color == null) {
            this.color = this.target.getColor();
        }
        this.start = this.color.a;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float f) {
        if (f == 0.0f) {
            this.color.a = this.start;
        } else if (f == 1.0f) {
            this.color.a = this.end;
        } else {
            Color color = this.color;
            float f2 = this.start;
            color.a = f2 + ((this.end - f2) * f);
        }
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

    public float getAlpha() {
        return this.end;
    }

    public void setAlpha(float f) {
        this.end = f;
    }
}
