package com.artemis;

import com.artemis.Component;

/* JADX INFO: loaded from: classes8.dex */
public abstract class BaseComponentMapper<A extends Component> {
    public final ComponentType type;

    public abstract A create(int i);

    public abstract A get(int i);

    public abstract boolean has(int i);

    public abstract A internalCreate(int i);

    protected abstract void internalRemove(int i);

    public abstract void remove(int i);

    protected BaseComponentMapper(ComponentType componentType) {
        this.type = componentType;
    }

    public static <T extends Component> BaseComponentMapper<T> getFor(Class<T> cls, World world) {
        return world.getMapper(cls);
    }

    public A get(Entity entity) {
        return (A) get(entity.getId());
    }

    public boolean has(Entity entity) {
        return has(entity.getId());
    }

    public A create(Entity entity) {
        return (A) create(entity.getId());
    }

    public void remove(Entity entity) {
        remove(entity.getId());
    }

    public A getSafe(int i, A a) {
        A a2 = (A) get(i);
        return a2 != null ? a2 : a;
    }

    public A set(int i, boolean z) {
        if (z) {
            return (A) create(i);
        }
        remove(i);
        return null;
    }

    public A set(Entity entity, boolean z) {
        return (A) set(entity.getId(), z);
    }

    public A getSafe(Entity entity, A a) {
        return (A) getSafe(entity.getId(), a);
    }

    public ComponentType getType() {
        return this.type;
    }

    public String toString() {
        return "ComponentMapper[" + this.type.getType().getSimpleName() + ']';
    }
}
