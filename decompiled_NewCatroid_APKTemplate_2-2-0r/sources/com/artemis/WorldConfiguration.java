package com.artemis;

import com.artemis.injection.Injector;
import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes8.dex */
public final class WorldConfiguration {
    public static final int ASPECT_SUBSCRIPTION_MANAGER_IDX = 2;
    public static final int COMPONENT_MANAGER_IDX = 0;
    public static final int ENTITY_MANAGER_IDX = 1;
    private boolean alwaysDelayComponentRemoval;
    protected int expectedEntityCount;
    protected Map<String, Object> injectables;
    protected Injector injector;
    protected SystemInvocationStrategy invocationStrategy;
    private Set<Class<? extends BaseSystem>> registered;
    final Bag<BaseSystem> systems;

    public WorldConfiguration() {
        Bag<BaseSystem> bag = new Bag<>((Class<BaseSystem>) BaseSystem.class);
        this.systems = bag;
        this.expectedEntityCount = 128;
        this.injectables = new HashMap();
        this.alwaysDelayComponentRemoval = false;
        this.registered = new HashSet();
        bag.add(null);
        bag.add(null);
        bag.add(null);
    }

    public int expectedEntityCount() {
        return this.expectedEntityCount;
    }

    public WorldConfiguration expectedEntityCount(int i) {
        this.expectedEntityCount = i;
        return this;
    }

    public WorldConfiguration setInjector(Injector injector) {
        if (injector == null) {
            throw new NullPointerException("Injector must not be null");
        }
        this.injector = injector;
        return this;
    }

    public WorldConfiguration setInvocationStrategy(SystemInvocationStrategy systemInvocationStrategy) {
        systemInvocationStrategy.getClass();
        this.invocationStrategy = systemInvocationStrategy;
        return this;
    }

    public WorldConfiguration register(Object obj) {
        return register(obj.getClass().getName(), obj);
    }

    public WorldConfiguration register(String str, Object obj) {
        this.injectables.put(str, obj);
        return this;
    }

    public WorldConfiguration setSystem(Class<? extends BaseSystem> cls) {
        try {
            return setSystem((BaseSystem) ClassReflection.newInstance(cls));
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends BaseSystem> WorldConfiguration setSystem(T t) {
        this.systems.add(t);
        if (this.registered.add((Class<? extends BaseSystem>) t.getClass())) {
            return this;
        }
        throw new RuntimeException(t.getClass().getSimpleName() + " already added to " + getClass().getSimpleName());
    }

    void initialize(World world, Injector injector, AspectSubscriptionManager aspectSubscriptionManager) {
        if (this.invocationStrategy == null) {
            this.invocationStrategy = new InvocationStrategy();
        }
        this.invocationStrategy.setWorld(world);
        world.invocationStrategy = this.invocationStrategy;
        this.systems.set(0, world.getComponentManager());
        this.systems.set(1, world.getEntityManager());
        this.systems.set(2, aspectSubscriptionManager);
        for (BaseSystem baseSystem : this.systems) {
            world.partition.systems.put(baseSystem.getClass(), baseSystem);
            baseSystem.setWorld(world);
            if (ClassReflection.isInstance(Manager.class, baseSystem)) {
                ((Manager) baseSystem).registerManager();
            }
        }
        injector.initialize(world, this.injectables);
        initializeSystems(injector);
        aspectSubscriptionManager.processComponentIdentity(0, new BitVector());
        this.invocationStrategy.setSystems(this.systems);
        this.invocationStrategy.initialize();
    }

    private void initializeSystems(Injector injector) {
        int size = this.systems.size();
        for (int i = 0; i < size; i++) {
            injector.inject(this.systems.get(i));
        }
        int size2 = this.systems.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.systems.get(i2).initialize();
        }
    }

    public boolean isAlwaysDelayComponentRemoval() {
        return this.alwaysDelayComponentRemoval;
    }

    public void setAlwaysDelayComponentRemoval(boolean z) {
        this.alwaysDelayComponentRemoval = z;
    }
}
