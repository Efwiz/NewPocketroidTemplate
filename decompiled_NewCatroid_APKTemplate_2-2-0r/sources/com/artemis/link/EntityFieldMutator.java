package com.artemis.link;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;

/* JADX INFO: loaded from: classes8.dex */
class EntityFieldMutator implements UniFieldMutator {
    private World world;

    EntityFieldMutator() {
    }

    @Override // com.artemis.link.UniFieldMutator
    public int read(Component component, Field field) {
        try {
            Entity entity = (Entity) field.get(component);
            if (entity != null) {
                return entity.getId();
            }
            return -1;
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.artemis.link.UniFieldMutator
    public void write(int i, Component component, Field field) {
        Entity entity;
        if (i != -1) {
            try {
                entity = this.world.getEntity(i);
            } catch (ReflectionException e) {
                throw new RuntimeException(e);
            }
        } else {
            entity = null;
        }
        field.set(component, entity);
    }

    @Override // com.artemis.link.UniFieldMutator
    public void setWorld(World world) {
        this.world = world;
    }
}
