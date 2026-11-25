package com.badlogic.gdx.scenes.scene2d.actions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/RepeatAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RepeatAction extends DelegateAction {
    public static final int FOREVER = -1;
    private int executedCount;
    private boolean finished;
    private int repeatCount;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    protected boolean delegate(float f) {
        if (this.executedCount == this.repeatCount) {
            return true;
        }
        if (!this.action.act(f)) {
            return false;
        }
        if (this.finished) {
            return true;
        }
        int i = this.repeatCount;
        if (i > 0) {
            this.executedCount++;
        }
        if (this.executedCount == i) {
            return true;
        }
        if (this.action == null) {
            return false;
        }
        this.action.restart();
        return false;
    }

    public void finish() {
        this.finished = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.executedCount = 0;
        this.finished = false;
    }

    public void setCount(int i) {
        this.repeatCount = i;
    }

    public int getCount() {
        return this.repeatCount;
    }
}
