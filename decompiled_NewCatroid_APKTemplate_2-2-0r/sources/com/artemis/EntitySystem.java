package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.ReflectionUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public abstract class EntitySystem extends BaseEntitySystem implements EntitySubscription.SubscriptionListener {
    static final int FLAG_INSERTED = 1;
    static final int FLAG_REMOVED = 2;
    private WildBag<Entity> entities;
    private int methodFlags;
    private boolean shouldSyncEntities;

    public EntitySystem(Aspect.Builder builder) {
        super(builder);
        this.entities = new WildBag<>(Entity.class);
    }

    public EntitySystem() {
        this.entities = new WildBag<>(Entity.class);
    }

    @Override // com.artemis.BaseEntitySystem, com.artemis.BaseSystem
    protected void setWorld(World world) {
        super.setWorld(world);
        if (ReflectionUtil.implementsObserver(this, "inserted")) {
            this.methodFlags |= 1;
        }
        if (ReflectionUtil.implementsObserver(this, "removed")) {
            this.methodFlags |= 2;
        }
    }

    @Override // com.artemis.BaseEntitySystem, com.artemis.EntitySubscription.SubscriptionListener
    public final void inserted(IntBag intBag) {
        this.shouldSyncEntities = true;
        if ((1 & this.methodFlags) > 0) {
            super.inserted(intBag);
        }
    }

    @Override // com.artemis.BaseEntitySystem
    protected final void inserted(int i) {
        inserted(this.world.getEntity(i));
    }

    @Override // com.artemis.BaseEntitySystem, com.artemis.EntitySubscription.SubscriptionListener
    public final void removed(IntBag intBag) {
        this.shouldSyncEntities = true;
        if ((this.methodFlags & 2) > 0) {
            super.removed(intBag);
        }
    }

    @Override // com.artemis.BaseEntitySystem
    protected final void removed(int i) {
        removed(this.world.getEntity(i));
    }

    public void inserted(Entity entity) {
        throw new RuntimeException("everything changes");
    }

    public void removed(Entity entity) {
        throw new RuntimeException("everything breaks");
    }

    public Bag<Entity> getEntities() {
        if (this.shouldSyncEntities) {
            int size = this.entities.size();
            this.entities.setSize(0);
            IntBag entities = this.subscription.getEntities();
            int[] data = entities.getData();
            for (int i = 0; i < entities.size(); i++) {
                this.entities.add(this.world.getEntity(data[i]));
            }
            if (size > this.entities.size()) {
                Arrays.fill(this.entities.getData(), this.entities.size(), size, (Object) null);
            }
            this.shouldSyncEntities = false;
        }
        return this.entities;
    }
}
