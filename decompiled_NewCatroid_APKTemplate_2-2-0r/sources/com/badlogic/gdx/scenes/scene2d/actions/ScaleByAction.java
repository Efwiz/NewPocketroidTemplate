package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/ScaleByAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ScaleByAction extends RelativeTemporalAction {
    private float amountX;
    private float amountY;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction
    protected void updateRelative(float f) {
        this.target.scaleBy(this.amountX * f, this.amountY * f);
    }

    public void setAmount(float f, float f2) {
        this.amountX = f;
        this.amountY = f2;
    }

    public void setAmount(float f) {
        this.amountX = f;
        this.amountY = f;
    }

    public float getAmountX() {
        return this.amountX;
    }

    public void setAmountX(float f) {
        this.amountX = f;
    }

    public float getAmountY() {
        return this.amountY;
    }

    public void setAmountY(float f) {
        this.amountY = f;
    }
}
