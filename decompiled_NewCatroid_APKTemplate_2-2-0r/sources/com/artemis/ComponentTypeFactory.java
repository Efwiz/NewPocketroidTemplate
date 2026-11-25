package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import java.util.IdentityHashMap;

/* JADX INFO: loaded from: classes8.dex */
public class ComponentTypeFactory {
    private final ComponentManager cm;
    int initialMapperCapacity;
    private final IdentityHashMap<Class<? extends Component>, ComponentType> componentTypes = new IdentityHashMap<>();
    private final Bag<ComponentTypeListener> listeners = new Bag<>();
    final Bag<ComponentType> types = new Bag<>(ComponentType.class);

    public interface ComponentTypeListener {
        void initialize(Bag<ComponentType> bag);

        void onCreated(ComponentType componentType);
    }

    public ComponentTypeFactory(ComponentManager componentManager, int i) {
        this.cm = componentManager;
        this.initialMapperCapacity = i;
    }

    public ComponentType getTypeFor(Class<? extends Component> cls) {
        ComponentType componentType = this.componentTypes.get(cls);
        return componentType == null ? createComponentType(cls) : componentType;
    }

    private ComponentType createComponentType(Class<? extends Component> cls) {
        try {
            if ((ClassReflection.getConstructor(cls, new Class[0]).getModifiers() & 1) == 0) {
                throw new InvalidComponentException(cls, "missing public constructor");
            }
            ComponentType componentType = new ComponentType(cls, this.types.size());
            this.componentTypes.put(cls, componentType);
            this.types.add(componentType);
            this.cm.registerComponentType(componentType, this.initialMapperCapacity);
            for (int i = 0; i < this.listeners.size(); i++) {
                this.listeners.get(i).onCreated(componentType);
            }
            return componentType;
        } catch (ReflectionException e) {
            throw new InvalidComponentException(cls, "missing public constructor", e);
        }
    }

    public ComponentType getTypeFor(int i) {
        return this.types.get(i);
    }

    public int getIndexFor(Class<? extends Component> cls) {
        return getTypeFor(cls).getIndex();
    }

    public void register(ComponentTypeListener componentTypeListener) {
        this.listeners.add(componentTypeListener);
        componentTypeListener.initialize(this.types);
    }
}
