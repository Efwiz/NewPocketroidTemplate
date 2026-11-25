package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.EntitySystem;

/* JADX INFO: loaded from: classes8.dex */
public abstract class IntervalEntitySystem extends EntitySystem {
    protected float acc;
    private final float interval;
    private float intervalDelta;

    public IntervalEntitySystem(Aspect.Builder builder, float f) {
        super(builder);
        this.interval = f;
    }

    public IntervalEntitySystem(float f) {
        this.interval = f;
    }

    @Override // com.artemis.BaseSystem
    protected boolean checkProcessing() {
        if (this.intervalDelta > 0.0f && this.acc == 0.0f) {
            this.intervalDelta = 0.0f;
        }
        float timeDelta = this.acc + getTimeDelta();
        this.acc = timeDelta;
        if (timeDelta < this.interval) {
            return false;
        }
        this.intervalDelta = timeDelta;
        this.acc = 0.0f;
        return true;
    }

    protected float getIntervalDelta() {
        return this.intervalDelta + this.acc;
    }

    protected float getTimeDelta() {
        return this.world.getDelta();
    }
}
