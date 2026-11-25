package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/DelayAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DelayAction extends DelegateAction {
    private float duration;
    private float time;

    public DelayAction() {
    }

    public DelayAction(float f) {
        this.duration = f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    protected boolean delegate(float f) {
        float f2 = this.time;
        float f3 = this.duration;
        if (f2 < f3) {
            float f4 = f2 + f;
            this.time = f4;
            if (f4 < f3) {
                return false;
            }
            f = f4 - f3;
        }
        if (this.action == null) {
            return true;
        }
        return this.action.act(f);
    }

    public void finish() {
        this.time = this.duration;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.time = 0.0f;
    }

    public float getTime() {
        return this.time;
    }

    public void setTime(float f) {
        this.time = f;
    }

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float f) {
        this.duration = f;
    }
}
