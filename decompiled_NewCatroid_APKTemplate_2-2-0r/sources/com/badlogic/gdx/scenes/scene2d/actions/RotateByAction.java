package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/RotateByAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RotateByAction extends RelativeTemporalAction {
    private float amount;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction
    protected void updateRelative(float f) {
        this.target.rotateBy(this.amount * f);
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float f) {
        this.amount = f;
    }
}
