package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/RelativeTemporalAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class RelativeTemporalAction extends TemporalAction {
    private float lastPercent;

    protected abstract void updateRelative(float f);

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        this.lastPercent = 0.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float f) {
        updateRelative(f - this.lastPercent);
        this.lastPercent = f;
    }
}
