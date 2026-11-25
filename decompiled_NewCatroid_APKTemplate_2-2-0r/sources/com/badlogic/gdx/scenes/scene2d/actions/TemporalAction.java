package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/TemporalAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class TemporalAction extends Action {
    private boolean began;
    private boolean complete;
    private float duration;
    private Interpolation interpolation;
    private boolean reverse;
    private float time;

    protected void begin() {
    }

    protected void end() {
    }

    protected abstract void update(float f);

    public TemporalAction() {
    }

    public TemporalAction(float f) {
        this.duration = f;
    }

    public TemporalAction(float f, Interpolation interpolation) {
        this.duration = f;
        this.interpolation = interpolation;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float f) {
        boolean z = true;
        if (this.complete) {
            return true;
        }
        Pool pool = getPool();
        setPool(null);
        try {
            if (!this.began) {
                begin();
                this.began = true;
            }
            float f2 = this.time + f;
            this.time = f2;
            float f3 = this.duration;
            if (f2 < f3) {
                z = false;
            }
            this.complete = z;
            float fApply = z ? 1.0f : f2 / f3;
            Interpolation interpolation = this.interpolation;
            if (interpolation != null) {
                fApply = interpolation.apply(fApply);
            }
            if (this.reverse) {
                fApply = 1.0f - fApply;
            }
            update(fApply);
            if (this.complete) {
                end();
            }
            return this.complete;
        } finally {
            setPool(pool);
        }
    }

    public void finish() {
        this.time = this.duration;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.time = 0.0f;
        this.began = false;
        this.complete = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.reverse = false;
        this.interpolation = null;
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

    public Interpolation getInterpolation() {
        return this.interpolation;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    public boolean isReverse() {
        return this.reverse;
    }

    public void setReverse(boolean z) {
        this.reverse = z;
    }

    public boolean isComplete() {
        return this.complete;
    }
}
