package com.artemis.managers;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.artemis.EntitySubscription;
import com.artemis.utils.BitVector;
import com.artemis.utils.IntBag;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class TagManager extends BaseSystem {
    private final Map<String, Entity> entitiesByTag = new HashMap();
    private final Map<Entity, String> tagsByEntity = new HashMap();
    private final BitVector registered = new BitVector();

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
    }

    @Override // com.artemis.BaseSystem
    protected void initialize() {
        this.world.getAspectSubscriptionManager().get(Aspect.all()).addSubscriptionListener(new EntitySubscription.SubscriptionListener() { // from class: com.artemis.managers.TagManager.1
            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void inserted(IntBag intBag) {
            }

            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void removed(IntBag intBag) {
                TagManager.this.deleted(intBag);
            }
        });
    }

    void deleted(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            int i2 = data[i];
            if (this.registered.get(i2)) {
                this.entitiesByTag.remove(this.tagsByEntity.remove(this.world.getEntity(i2)));
                this.registered.clear(i2);
            }
        }
    }

    public void register(String str, Entity entity) {
        unregister(str);
        if (getTag(entity) != null) {
            unregister(getTag(entity));
        }
        this.entitiesByTag.put(str, entity);
        this.tagsByEntity.put(entity, str);
        this.registered.set(entity.getId());
    }

    public void register(String str, int i) {
        register(str, this.world.getEntity(i));
    }

    public void unregister(String str) {
        Entity entityRemove = this.entitiesByTag.remove(str);
        if (entityRemove != null) {
            this.tagsByEntity.remove(entityRemove);
            this.registered.clear(entityRemove.getId());
        }
    }

    public boolean isRegistered(String str) {
        return this.entitiesByTag.containsKey(str);
    }

    public Entity getEntity(String str) {
        return this.entitiesByTag.get(str);
    }

    public int getEntityId(String str) {
        Entity entity = getEntity(str);
        if (entity != null) {
            return entity.getId();
        }
        return -1;
    }

    public String getTag(Entity entity) {
        return this.tagsByEntity.get(entity);
    }

    public String getTag(int i) {
        return getTag(this.world.getEntity(i));
    }

    public Collection<String> getRegisteredTags() {
        return this.tagsByEntity.values();
    }
}
