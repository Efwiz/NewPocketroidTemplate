package com.artemis;

/* JADX INFO: loaded from: classes8.dex */
public final class EntityEdit {
    private ComponentManager cm;
    int entityId;

    EntityEdit(World world) {
        this.cm = world.getComponentManager();
    }

    public <T extends Component> T create(Class<T> cls) {
        return (T) this.cm.getMapper(cls).create(this.entityId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EntityEdit add(Component component) {
        return add(component, this.cm.typeFactory.getTypeFor((Class<? extends Component>) component.getClass()));
    }

    public EntityEdit add(Component component, ComponentType componentType) {
        if (componentType.isPooled) {
            throw new InvalidComponentException(component.getClass(), "Use EntityEdit#create(Class<Component>) for adding non-basic component types");
        }
        ComponentMapper mapper = this.cm.getMapper(componentType.getType());
        mapper.create(this.entityId);
        mapper.components.getData()[this.entityId] = component;
        return this;
    }

    public Entity getEntity() {
        return this.cm.world.getEntity(this.entityId);
    }

    public int getEntityId() {
        return this.entityId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EntityEdit remove(Component component) {
        return remove((Class<? extends Component>) component.getClass());
    }

    public EntityEdit remove(ComponentType componentType) {
        this.cm.getMapper(componentType.getType()).remove(this.entityId);
        return this;
    }

    public EntityEdit remove(Class<? extends Component> cls) {
        return remove(this.cm.typeFactory.getTypeFor(cls));
    }

    public String toString() {
        return "EntityEdit[" + this.entityId + "]";
    }
}
