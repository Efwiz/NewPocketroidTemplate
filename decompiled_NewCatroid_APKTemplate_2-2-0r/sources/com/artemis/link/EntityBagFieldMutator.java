package com.artemis.link;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;

/* JADX INFO: loaded from: classes8.dex */
class EntityBagFieldMutator implements MultiFieldMutator<Bag<Entity>, Component> {
    private EntitySubscription all;
    private final Bag<Entity> empty = new Bag<>();

    EntityBagFieldMutator() {
    }

    @Override // com.artemis.link.MultiFieldMutator
    public void validate(int i, Bag<Entity> bag, LinkListener linkListener) {
        int i2 = 0;
        while (bag.size() > i2) {
            Entity entity = bag.get(i2);
            if (!this.all.getActiveEntityIds().unsafeGet(entity.getId())) {
                int i3 = i2 - 1;
                bag.remove(i2);
                if (linkListener != null) {
                    linkListener.onTargetDead(i, entity.getId());
                }
                i2 = i3;
            }
            i2++;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.artemis.link.MultiFieldMutator
    public Bag<Entity> read(Component component, Field field) {
        try {
            Bag<Entity> bag = (Bag) field.get(component);
            return bag != null ? bag : this.empty;
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.artemis.link.MultiFieldMutator
    public void setWorld(World world) {
        this.all = world.getAspectSubscriptionManager().get(Aspect.all());
    }
}
