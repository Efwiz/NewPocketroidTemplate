package com.artemis;

import com.artemis.injection.CachedInjector;
import com.artemis.injection.Injector;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class World {
    final boolean alwaysDelayComponentRemoval;
    final AspectSubscriptionManager asm;
    final BatchChangeProcessor batchProcessor;
    private final ComponentManager cm;
    public float delta;
    private final EntityManager em;
    SystemInvocationStrategy invocationStrategy;
    final WorldSegment partition;
    final Bag<BaseSystem> systemsBag;

    public World() {
        this(new WorldConfiguration());
    }

    public World(WorldConfiguration worldConfiguration) {
        WorldSegment worldSegment = new WorldSegment(worldConfiguration);
        this.partition = worldSegment;
        Bag<BaseSystem> bag = worldConfiguration.systems;
        this.systemsBag = bag;
        ComponentManager componentManager = (ComponentManager) bag.get(0);
        EntityManager entityManager = (EntityManager) bag.get(1);
        AspectSubscriptionManager aspectSubscriptionManager = (AspectSubscriptionManager) bag.get(2);
        this.cm = componentManager == null ? new ComponentManager(worldConfiguration.expectedEntityCount()) : componentManager;
        this.em = entityManager == null ? new EntityManager(worldConfiguration.expectedEntityCount()) : entityManager;
        aspectSubscriptionManager = aspectSubscriptionManager == null ? new AspectSubscriptionManager() : aspectSubscriptionManager;
        this.asm = aspectSubscriptionManager;
        this.batchProcessor = new BatchChangeProcessor(this);
        this.alwaysDelayComponentRemoval = worldConfiguration.isAlwaysDelayComponentRemoval();
        worldConfiguration.initialize(this, worldSegment.injector, aspectSubscriptionManager);
    }

    public void inject(Object obj) {
        inject(obj, true);
    }

    public void inject(Object obj, boolean z) {
        boolean zIsInjectable = this.partition.injector.isInjectable(obj);
        if (!zIsInjectable && z) {
            throw new MundaneWireException("Attempted injection on " + obj.getClass().getName() + ", which is annotated with @SkipWire");
        }
        if (zIsInjectable) {
            this.partition.injector.inject(obj);
        }
    }

    public <T> T getRegistered(String str) {
        return (T) this.partition.injector.getRegistered(str);
    }

    public <T> T getRegistered(Class<T> cls) {
        return (T) this.partition.injector.getRegistered(cls);
    }

    public void dispose() {
        ArrayList arrayList = new ArrayList();
        Iterator<BaseSystem> it = this.systemsBag.iterator();
        while (it.hasNext()) {
            try {
                it.next().dispose();
            } catch (Exception e) {
                arrayList.add(e);
            }
        }
        if (arrayList.size() > 0) {
            throw new ArtemisMultiException(arrayList);
        }
    }

    public EntityEdit edit(int i) {
        if (!this.em.isActive(i)) {
            throw new RuntimeException("Issued edit on deleted " + i);
        }
        return this.batchProcessor.obtainEditor(i);
    }

    public int compositionId(int i) {
        return this.cm.getIdentity(i);
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public ComponentManager getComponentManager() {
        return this.cm;
    }

    public AspectSubscriptionManager getAspectSubscriptionManager() {
        return this.asm;
    }

    public float getDelta() {
        return this.delta;
    }

    public void setDelta(float f) {
        this.delta = f;
    }

    public void deleteEntity(Entity entity) {
        delete(entity.f80id);
    }

    public void delete(int i) {
        this.batchProcessor.delete(i);
    }

    public Entity createEntity() {
        Entity entityCreateEntityInstance = this.em.createEntityInstance();
        this.batchProcessor.changed.unsafeSet(entityCreateEntityInstance.getId());
        return entityCreateEntityInstance;
    }

    public int create() {
        int iCreate = this.em.create();
        this.batchProcessor.changed.unsafeSet(iCreate);
        return iCreate;
    }

    public Entity createEntity(Archetype archetype) {
        Entity entityCreateEntityInstance = this.em.createEntityInstance();
        int id2 = entityCreateEntityInstance.getId();
        archetype.transmuter.perform(id2);
        this.cm.setIdentity(entityCreateEntityInstance.f80id, archetype.compositionId);
        this.batchProcessor.changed.unsafeSet(id2);
        return entityCreateEntityInstance;
    }

    public int create(Archetype archetype) {
        int iCreate = this.em.create();
        archetype.transmuter.perform(iCreate);
        this.cm.setIdentity(iCreate, archetype.compositionId);
        this.batchProcessor.changed.unsafeSet(iCreate);
        return iCreate;
    }

    public Entity getEntity(int i) {
        return this.em.getEntity(i);
    }

    public ImmutableBag<BaseSystem> getSystems() {
        return this.systemsBag;
    }

    public <T extends BaseSystem> T getSystem(Class<T> cls) {
        return (T) this.partition.systems.get(cls);
    }

    protected void setInvocationStrategy(SystemInvocationStrategy systemInvocationStrategy) {
        this.invocationStrategy = systemInvocationStrategy;
        systemInvocationStrategy.setWorld(this);
        systemInvocationStrategy.setSystems(this.systemsBag);
        systemInvocationStrategy.initialize();
    }

    public void process() {
        this.invocationStrategy.process();
        IntBag pendingPurge = this.batchProcessor.getPendingPurge();
        if (pendingPurge.isEmpty()) {
            return;
        }
        this.cm.clean(pendingPurge);
        this.em.clean(pendingPurge);
        this.batchProcessor.purgeComponents();
    }

    public <T extends Component> ComponentMapper<T> getMapper(Class<T> cls) {
        return this.cm.getMapper(cls);
    }

    public Injector getInjector() {
        return this.partition.injector;
    }

    public <T extends SystemInvocationStrategy> T getInvocationStrategy() {
        return (T) this.invocationStrategy;
    }

    static class WorldSegment {
        final Injector injector;
        final Map<Class<?>, BaseSystem> systems = new IdentityHashMap();

        WorldSegment(WorldConfiguration worldConfiguration) {
            this.injector = worldConfiguration.injector != null ? worldConfiguration.injector : new CachedInjector();
        }
    }

    public boolean isAlwaysDelayComponentRemoval() {
        return this.alwaysDelayComponentRemoval;
    }
}
