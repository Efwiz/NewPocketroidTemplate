package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class DelayedEntityProcessingSystem extends EntitySystem {
    private float acc;
    private float delay;
    private boolean running;

    protected abstract float getRemainingDelay(Entity entity);

    protected abstract void processDelta(Entity entity, float f);

    protected abstract void processExpired(Entity entity);

    public DelayedEntityProcessingSystem(Aspect.Builder builder) {
        super(builder);
    }

    public DelayedEntityProcessingSystem() {
    }

    @Override // com.artemis.BaseSystem
    protected final void processSystem() {
        Bag<Entity> entities = getEntities();
        int size = entities.size();
        if (size == 0) {
            stop();
            return;
        }
        this.delay = Float.MAX_VALUE;
        Entity[] data = entities.getData();
        for (int i = 0; size > i; i++) {
            Entity entity = data[i];
            processDelta(entity, this.acc);
            float remainingDelay = getRemainingDelay(entity);
            if (remainingDelay <= 0.0f) {
                processExpired(entity);
            } else {
                offerDelay(remainingDelay);
            }
        }
        this.acc = 0.0f;
    }

    @Override // com.artemis.EntitySystem
    public void inserted(Entity entity) {
        float remainingDelay = getRemainingDelay(entity);
        processDelta(entity, -this.acc);
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
