package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/TimeScaleAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TimeScaleAction extends DelegateAction {
    private float scale;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    protected boolean delegate(float f) {
        if (this.action == null) {
            return true;
        }
        return this.action.act(f * this.scale);
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }
}
