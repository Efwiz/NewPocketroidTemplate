package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/RemoveActorAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RemoveActorAction extends Action {
    private boolean removed;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float f) {
        if (!this.removed) {
            this.removed = true;
            this.target.remove();
        }
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.removed = false;
    }
}
