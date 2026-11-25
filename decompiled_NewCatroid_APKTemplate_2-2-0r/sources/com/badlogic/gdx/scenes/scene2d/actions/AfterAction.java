package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/AfterAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AfterAction extends DelegateAction {
    private Array<Action> waitForActions = new Array<>(false, 4);

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void setTarget(Actor actor) {
        if (actor != null) {
            this.waitForActions.addAll(actor.getActions());
        }
        super.setTarget(actor);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.waitForActions.clear();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    protected boolean delegate(float f) {
        Array<Action> actions = this.target.getActions();
        if (actions.size == 1) {
            this.waitForActions.clear();
        }
        for (int i = this.waitForActions.size - 1; i >= 0; i--) {
            if (actions.indexOf(this.waitForActions.get(i), true) == -1) {
                this.waitForActions.removeIndex(i);
            }
        }
        if (this.waitForActions.size > 0) {
            return false;
        }
        return this.action.act(f);
    }
}
