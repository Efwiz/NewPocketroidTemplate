package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.utils.IntBag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class DelayedIteratingSystem extends BaseEntitySystem {
    private float acc;
    private float delay;
    private boolean running;

    protected abstract float getRemainingDelay(int i);

    protected abstract void processDelta(int i, float f);

    protected abstract void processExpired(int i);

    public DelayedIteratingSystem(Aspect.Builder builder) {
        super(builder);
    }

    public DelayedIteratingSystem() {
    }

    @Override // com.artemis.BaseSystem
    protected final void processSystem() {
        IntBag entities = this.subscription.getEntities();
        int size = entities.size();
        if (size == 0) {
            stop();
            return;
        }
        this.delay = Float.MAX_VALUE;
        int[] data = entities.getData();
        for (int i = 0; size > i; i++) {
            int i2 = data[i];
            processDelta(i2, this.acc);
            float remainingDelay = getRemainingDelay(i2);
            if (remainingDelay <= 0.0f) {
                processExpired(i2);
            } else {
                offerDelay(remainingDelay);
            }
        }
        this.acc = 0.0f;
    }

    @Override // com.artemis.BaseEntitySystem
    protected void inserted(int i) {
        float remainingDelay = getRemainingDelay(i);
        processDelta(i, -this.acc);
        if (remainingDelay > 0.0f) {
            offerDelay(remainingDelay);
        }
    }

    @Override // com.artemis.BaseSystem
    protected final boolean checkProcessing() {
        if (!this.running) {
            return false;
        }
        float timeDelta = this.acc + getTimeDelta();
        this.acc = timeDelta;
        return timeDelta >= this.delay;
    }

    protected float getTimeDelta() {
        return this.world.getDelta();
    }

    public void offerDelay(float f) {
        if (!this.running) {
            this.running = true;
            this.delay = f;
        } else {
            this.delay = Math.min(this.delay, f);
        }
    }

    public float getInitialTimeDelay() {
        return this.delay;
    }

    public float getRemainingTimeUntilProcessing() {
        if (this.running) {
            return this.delay - this.acc;
        }
        return 0.0f;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void stop() {
        this.running = false;
        this.acc = 0.0f;
    }
}
