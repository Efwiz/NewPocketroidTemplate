package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;

/* JADX INFO: loaded from: classes8.dex */
public final class Entity {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    int f80id;
    private final World world;

    protected Entity(World world, int i) {
        this.world = world;
        this.f80id = i;
    }

    public int getId() {
        return this.f80id;
    }

    protected BitVector getComponentBits() {
        return this.world.getComponentManager().componentBits(this.f80id);
    }

    public EntityEdit edit() {
        return this.world.edit(this.f80id);
    }

    public String toString() {
        return "Entity[" + this.f80id + "]";
    }

    public boolean isActive() {
        return this.world.getEntityManager().isActive(this.f80id);
    }

    public Component getComponent(ComponentType componentType) {
        return this.world.getComponentManager().getComponent(this.f80id, componentType);
    }

    public <T extends Component> T getComponent(Class<T> cls) {
        return (T) getComponent(this.world.getComponentManager().typeFactory.getTypeFor((Class<? extends Component>) cls));
    }

    public Bag<Component> getComponents(Bag<Component> bag) {
        return this.world.getComponentManager().getComponentsFor(this.f80id, bag);
    }

    public void deleteFromWorld() {
        this.world.delete(this.f80id);
    }

    public World getWorld() {
        return this.world;
    }

    public int getCompositionId() {
        return this.world.getComponentManager().getIdentity(this.f80id);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.f80id == ((Entity) obj).f80id;
    }

    public boolean equals(Entity entity) {
        return entity != null && entity.f80id == this.f80id;
    }

    public int hashCode() {
        return this.f80id;
    }
}
