package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;

/* JADX INFO: loaded from: classes8.dex */
public abstract class IntervalSystem extends BaseEntitySystem {
    protected float acc;
    private final float interval;
    private float intervalDelta;

    public IntervalSystem(Aspect.Builder builder, float f) {
        super(builder);
        this.interval = f;
    }

    @Override // com.artemis.BaseSystem
    protected boolean checkProcessing() {
        float timeDelta = this.acc + getTimeDelta();
        this.acc = timeDelta;
        float f = this.interval;
        if (timeDelta < f) {
            return false;
        }
        float f2 = timeDelta - f;
        this.acc = f2;
        this.intervalDelta = f2 - this.intervalDelta;
        return true;
    }

    protected float getIntervalDelta() {
        return this.interval + this.intervalDelta;
    }

    protected float getTimeDelta() {
        return this.world.getDelta();
    }
}
