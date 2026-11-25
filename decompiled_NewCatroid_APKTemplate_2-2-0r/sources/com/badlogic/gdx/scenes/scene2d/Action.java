package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/Action.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class Action implements Pool.Poolable {
    protected Actor actor;
    private Pool pool;
    protected Actor target;

    public abstract boolean act(float f);

    public void restart() {
    }

    public void setActor(Actor actor) {
        Pool pool;
        this.actor = actor;
        if (this.target == null) {
            setTarget(actor);
        }
        if (actor != null || (pool = this.pool) == null) {
            return;
        }
        pool.free(this);
        this.pool = null;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setTarget(Actor actor) {
        this.target = actor;
    }

    public Actor getTarget() {
        return this.target;
    }

    @Override // com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        this.actor = null;
        this.target = null;
        this.pool = null;
        restart();
    }

    public Pool getPool() {
        return this.pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public String toString() {
        String name2 = getClass().getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            name2 = name2.substring(iLastIndexOf + 1);
        }
        return name2.endsWith("Action") ? name2.substring(0, name2.length() - 6) : name2;
    }
}
