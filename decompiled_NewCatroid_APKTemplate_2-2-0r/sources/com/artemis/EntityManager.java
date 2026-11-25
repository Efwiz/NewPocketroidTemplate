package com.artemis;

import com.artemis.annotations.SkipWire;
import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.IntBag;
import com.artemis.utils.IntDeque;

/* JADX INFO: loaded from: classes8.dex */
@SkipWire
public class EntityManager extends BaseSystem {
    final Bag<Entity> entities;
    private Bag<BitVector> entityBitVectors;
    private final IntDeque limbo;
    private int nextId;
    private final BitVector recycled;

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
    }

    protected EntityManager(int i) {
        BitVector bitVector = new BitVector();
        this.recycled = bitVector;
        this.limbo = new IntDeque();
        this.entityBitVectors = new Bag<>(BitVector.class);
        this.entities = new Bag<>(i);
        registerEntityStore(bitVector);
    }

    protected Entity createEntityInstance() {
        return obtain();
    }

    protected int create() {
        return obtain().f80id;
    }

    void clean(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            int i2 = data[i];
            if (!this.recycled.unsafeGet(i2)) {
                free(i2);
            }
        }
    }

    public boolean isActive(int i) {
        return !this.recycled.unsafeGet(i);
    }

    public void registerEntityStore(BitVector bitVector) {
        bitVector.ensureCapacity(this.entities.getCapacity());
        this.entityBitVectors.add(bitVector);
    }

    protected Entity getEntity(int i) {
        return this.entities.get(i);
    }

    public boolean reset() {
        if (this.world.getAspectSubscriptionManager().get(Aspect.all()).getActiveEntityIds().cardinality() > 0) {
            return false;
        }
        this.limbo.clear();
        this.recycled.clear();
        this.entities.clear();
        this.nextId = 0;
        return true;
    }

    private Entity createEntity(int i) {
        Entity entity = new Entity(this.world, i);
        if (entity.f80id >= this.entities.getCapacity()) {
            growEntityStores();
        }
        this.entities.set(entity.f80id, entity);
        return entity;
    }

    private void growEntityStores() {
        int capacity = this.entities.getCapacity() * 2;
        this.entities.ensureCapacity(capacity);
        this.world.getComponentManager().ensureCapacity(capacity);
        int size = this.entityBitVectors.size();
        for (int i = 0; size > i; i++) {
            this.entityBitVectors.get(i).ensureCapacity(capacity);
        }
    }

    private Entity obtain() {
        if (this.limbo.isEmpty()) {
            int i = this.nextId;
            this.nextId = i + 1;
            return createEntity(i);
        }
        int iPopFirst = this.limbo.popFirst();
        this.recycled.unsafeClear(iPopFirst);
        return this.entities.get(iPopFirst);
    }

    private void free(int i) {
        this.limbo.add(i);
        this.recycled.unsafeSet(i);
    }
}
