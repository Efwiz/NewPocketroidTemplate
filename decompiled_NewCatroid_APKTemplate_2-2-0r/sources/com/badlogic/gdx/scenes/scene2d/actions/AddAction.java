package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/AddAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AddAction extends Action {
    private Action action;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float f) {
        this.target.addAction(this.action);
        return true;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        Action action = this.action;
        if (action != null) {
            action.restart();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.action = null;
    }
}
