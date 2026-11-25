package com.artemis.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class EntityProcessingSystem extends EntitySystem {
    protected abstract void process(Entity entity);

    public EntityProcessingSystem(Aspect.Builder builder) {
        super(builder);
    }

    public EntityProcessingSystem() {
    }

    @Override // com.artemis.BaseSystem
    protected final void processSystem() {
        Bag<Entity> entities = getEntities();
        Entity[] data = entities.getData();
        int size = entities.size();
        for (int i = 0; size > i; i++) {
            process(data[i]);
        }
    }
}
