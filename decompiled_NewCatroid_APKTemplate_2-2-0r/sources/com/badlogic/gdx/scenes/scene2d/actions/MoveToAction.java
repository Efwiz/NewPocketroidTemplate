package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/MoveToAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class MoveToAction extends TemporalAction {
    private int alignment = 12;
    private float endX;
    private float endY;
    private float startX;
    private float startY;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        this.startX = this.target.getX(this.alignment);
        this.startY = this.target.getY(this.alignment);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float f) {
        float f2;
        float f3;
        if (f == 0.0f) {
            f3 = this.startX;
            f2 = this.startY;
        } else if (f == 1.0f) {
            f3 = this.endX;
            f2 = this.endY;
        } else {
            float f4 = this.startX;
            float f5 = f4 + ((this.endX - f4) * f);
            float f6 = this.startY;
            f2 = f6 + ((this.endY - f6) * f);
            f3 = f5;
        }
        this.target.setPosition(f3, f2, this.alignment);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.alignment = 12;
    }

    public void setStartPosition(float f, float f2) {
        this.startX = f;
        this.startY = f2;
    }

    public void setPosition(float f, float f2) {
        this.endX = f;
        this.endY = f2;
    }

    public void setPosition(float f, float f2, int i) {
        this.endX = f;
        this.endY = f2;
        this.alignment = i;
    }

    public float getX() {
        return this.endX;
    }

    public void setX(float f) {
        this.endX = f;
    }

    public float getY() {
        return this.endY;
    }

    public void setY(float f) {
        this.endY = f;
    }

    public float getStartX() {
        return this.startX;
    }

    public float getStartY() {
        return this.startY;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setAlignment(int i) {
        this.alignment = i;
    }
}
