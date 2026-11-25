package com.artemis.utils;

import com.artemis.Archetype;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.artemis.managers.TagManager;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.reflect.ClassReflection;
import java.util.UUID;

/* JADX INFO: loaded from: classes8.dex */
public class EntityBuilder {
    protected final EntityEdit edit;
    protected final World world;

    public EntityBuilder(World world) {
        this.world = world;
        this.edit = world.createEntity().edit();
    }

    public EntityBuilder(World world, Archetype archetype) {
        this.world = world;
        this.edit = world.createEntity(archetype).edit();
    }

    public EntityBuilder with(Component component) {
        this.edit.add(component);
        return this;
    }

    public EntityBuilder with(Component component, Component component2) {
        this.edit.add(component);
        this.edit.add(component2);
        return this;
    }

    public EntityBuilder with(Component component, Component component2, Component component3) {
        this.edit.add(component);
        this.edit.add(component2);
        this.edit.add(component3);
        return this;
    }

    public EntityBuilder with(Component component, Component component2, Component component3, Component component4) {
        this.edit.add(component);
        this.edit.add(component2);
        this.edit.add(component3);
        this.edit.add(component4);
        return this;
    }

    public EntityBuilder with(Component component, Component component2, Component component3, Component component4, Component component5) {
        this.edit.add(component);
        this.edit.add(component2);
        this.edit.add(component3);
        this.edit.add(component4);
        this.edit.add(component5);
        return this;
    }

    public EntityBuilder with(Component... componentArr) {
        for (Component component : componentArr) {
            this.edit.add(component);
        }
        return this;
    }

    public EntityBuilder with(Class<? extends Component> cls) {
        this.edit.create(cls);
        return this;
    }

    public EntityBuilder with(Class<? extends Component> cls, Class<? extends Component> cls2) {
        this.edit.create(cls);
        this.edit.create(cls2);
        return this;
    }

    public EntityBuilder with(Class<? extends Component> cls, Class<? extends Component> cls2, Class<? extends Component> cls3) {
        this.edit.create(cls);
        this.edit.create(cls2);
        this.edit.create(cls3);
        return this;
    }

    public EntityBuilder with(Class<? extends Component> cls, Class<? extends Component> cls2, Class<? extends Component> cls3, Class<? extends Component> cls4) {
        this.edit.create(cls);
        this.edit.create(cls2);
        this.edit.create(cls3);
        this.edit.create(cls4);
        return this;
    }

    public EntityBuilder with(Class<? extends Component> cls, Class<? extends Component> cls2, Class<? extends Component> cls3, Class<? extends Component> cls4, Class<? extends Component> cls5) {
        this.edit.create(cls);
        this.edit.create(cls2);
        this.edit.create(cls3);
        this.edit.create(cls4);
        this.edit.create(cls5);
        return this;
    }

    public EntityBuilder with(Class<? extends Component>... clsArr) {
        for (Class<? extends Component> cls : clsArr) {
            this.edit.create(cls);
        }
        return this;
    }

    public EntityBuilder UUID(UUID uuid) {
        ((UuidEntityManager) resolveManager(UuidEntityManager.class)).setUuid(this.edit.getEntity(), uuid);
        return this;
    }

    public EntityBuilder player(String str) {
        ((PlayerManager) resolveManager(PlayerManager.class)).setPlayer(this.edit.getEntity(), str);
        return this;
    }

    public EntityBuilder tag(String str) {
        ((TagManager) resolveManager(TagManager.class)).register(str, this.edit.getEntity());
        return this;
    }

    public EntityBuilder group(String str) {
        ((GroupManager) resolveManager(GroupManager.class)).add(this.edit.getEntity(), str);
        return this;
    }

    public EntityBuilder groups(String... strArr) {
        for (int i = 0; strArr.length > i; i++) {
            group(strArr[i]);
        }
        return this;
    }

    public Entity build() {
        return this.edit.getEntity();
    }

    protected <T extends BaseSystem> T resolveManager(Class<T> cls) {
        T t = (T) this.world.getSystem(cls);
        if (t != null) {
            return t;
        }
        throw new RuntimeException("Register " + ClassReflection.getSimpleName(cls) + " with your artemis world.");
    }
}
