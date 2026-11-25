package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.utils.Bag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class IntervalEntityProcessingSystem extends IntervalEntitySystem {
    protected abstract void process(Entity entity);

    public IntervalEntityProcessingSystem(Aspect.Builder builder, float f) {
        super(builder, f);
    }

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
        processEntities(getEntities());
    }

    protected void processEntities(Bag<Entity> bag) {
        Entity[] data = bag.getData();
        int size = bag.size();
        for (int i = 0; size > i; i++) {
            process(data[i]);
        }
    }
}
