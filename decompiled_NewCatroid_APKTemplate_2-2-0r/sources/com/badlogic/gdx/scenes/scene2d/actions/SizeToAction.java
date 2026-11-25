package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/SizeToAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SizeToAction extends TemporalAction {
    private float endHeight;
    private float endWidth;
    private float startHeight;
    private float startWidth;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        this.startWidth = this.target.getWidth();
        this.startHeight = this.target.getHeight();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float f) {
        float f2;
        float f3;
        if (f == 0.0f) {
            f3 = this.startWidth;
            f2 = this.startHeight;
        } else if (f == 1.0f) {
            f3 = this.endWidth;
            f2 = this.endHeight;
        } else {
            float f4 = this.startWidth;
            float f5 = f4 + ((this.endWidth - f4) * f);
            float f6 = this.startHeight;
            f2 = f6 + ((this.endHeight - f6) * f);
            f3 = f5;
        }
        this.target.setSize(f3, f2);
    }

    public void setSize(float f, float f2) {
        this.endWidth = f;
        this.endHeight = f2;
    }

    public float getWidth() {
        return this.endWidth;
    }

    public void setWidth(float f) {
        this.endWidth = f;
    }

    public float getHeight() {
        return this.endHeight;
    }

    public void setHeight(float f) {
        this.endHeight = f;
    }
}
