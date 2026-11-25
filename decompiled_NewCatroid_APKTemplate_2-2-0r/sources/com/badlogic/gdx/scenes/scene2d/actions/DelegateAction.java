package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/DelegateAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class DelegateAction extends Action {
    protected Action action;

    protected abstract boolean delegate(float f);

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public final boolean act(float f) {
        Pool pool = getPool();
        setPool(null);
        try {
            return delegate(f);
        } finally {
            setPool(pool);
        }
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

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setActor(Actor actor) {
        Action action = this.action;
        if (action != null) {
            action.setActor(actor);
        }
        super.setActor(actor);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setTarget(Actor actor) {
        Action action = this.action;
        if (action != null) {
            action.setTarget(actor);
        }
        super.setTarget(actor);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public String toString() {
        return super.toString() + (this.action == null ? "" : "(" + this.action + Constant.AFTER_QUTO);
    }
}
