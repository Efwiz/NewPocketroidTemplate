package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/RemoveListenerAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RemoveListenerAction extends Action {
    private boolean capture;
    private EventListener listener;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float f) {
        if (this.capture) {
            this.target.removeCaptureListener(this.listener);
            return true;
        }
        this.target.removeListener(this.listener);
        return true;
    }

    public EventListener getListener() {
        return this.listener;
    }

    public void setListener(EventListener eventListener) {
        this.listener = eventListener;
    }

    public boolean getCapture() {
        return this.capture;
    }

    public void setCapture(boolean z) {
        this.capture = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.listener = null;
    }
}
