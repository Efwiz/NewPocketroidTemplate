package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.utils.IntBag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class IntervalIteratingSystem extends IntervalSystem {
    protected abstract void process(int i);

    public IntervalIteratingSystem(Aspect.Builder builder, float f) {
        super(builder, f);
    }

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
        IntBag entities = this.subscription.getEntities();
        int[] data = entities.getData();
        int size = entities.size();
        for (int i = 0; size > i; i++) {
            process(data[i]);
        }
    }
}
