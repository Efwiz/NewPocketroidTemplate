package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/SizeByAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SizeByAction extends RelativeTemporalAction {
    private float amountHeight;
    private float amountWidth;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction
    protected void updateRelative(float f) {
        this.target.sizeBy(this.amountWidth * f, this.amountHeight * f);
    }

    public void setAmount(float f, float f2) {
        this.amountWidth = f;
        this.amountHeight = f2;
    }

    public float getAmountWidth() {
        return this.amountWidth;
    }

    public void setAmountWidth(float f) {
        this.amountWidth = f;
    }

    public float getAmountHeight() {
        return this.amountHeight;
    }

    public void setAmountHeight(float f) {
        this.amountHeight = f;
    }
}
