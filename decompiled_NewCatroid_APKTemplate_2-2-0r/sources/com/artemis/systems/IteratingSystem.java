package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.utils.IntBag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class IteratingSystem extends BaseEntitySystem {
    protected abstract void process(int i);

    public IteratingSystem(Aspect.Builder builder) {
        super(builder);
    }

    public IteratingSystem() {
    }

    @Override // com.artemis.BaseSystem
    protected final void processSystem() {
        IntBag entities = this.subscription.getEntities();
        int[] data = entities.getData();
        int size = entities.size();
        for (int i = 0; size > i; i++) {
            process(data[i]);
        }
    }
}
