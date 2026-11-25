package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.IntBag;

/* JADX INFO: loaded from: classes8.dex */
public class EntitySubscription {
    private final BitVector activeEntityIds;
    final BitVector aspectCache = new BitVector();
    private final IntBag entities;
    final SubscriptionExtra extra;
    private final BitVector insertedIds;
    private final BitVector removedIds;

    public interface SubscriptionListener {
        void inserted(IntBag intBag);

        void removed(IntBag intBag);
    }

    EntitySubscription(World world, Aspect.Builder builder) {
        this.extra = new SubscriptionExtra(builder.build(world), builder);
        BitVector bitVector = new BitVector();
        this.activeEntityIds = bitVector;
        this.entities = new IntBag();
        BitVector bitVector2 = new BitVector();
        this.insertedIds = bitVector2;
        BitVector bitVector3 = new BitVector();
        this.removedIds = bitVector3;
        EntityManager entityManager = world.getEntityManager();
        entityManager.registerEntityStore(bitVector);
        entityManager.registerEntityStore(bitVector2);
        entityManager.registerEntityStore(bitVector3);
    }

    public IntBag getEntities() {
        if (this.entities.isEmpty() && !this.activeEntityIds.isEmpty()) {
            rebuildCompressedActives();
        }
        return this.entities;
    }

    public BitVector getActiveEntityIds() {
        return this.activeEntityIds;
    }

    public Aspect getAspect() {
        return this.extra.aspect;
    }

    public Aspect.Builder getAspectBuilder() {
        return this.extra.aspectReflection;
    }

    void processComponentIdentity(int i, BitVector bitVector) {
        this.aspectCache.ensureCapacity(i);
        this.aspectCache.set(i, this.extra.aspect.isInterested(bitVector));
    }

    void rebuildCompressedActives() {
        this.activeEntityIds.toIntBag(this.entities);
    }

    final void check(int i, int i2) {
        boolean zUnsafeGet = this.aspectCache.unsafeGet(i2);
        boolean zUnsafeGet2 = this.activeEntityIds.unsafeGet(i);
        if (zUnsafeGet && !zUnsafeGet2) {
            insert(i);
        } else {
            if (zUnsafeGet || !zUnsafeGet2) {
                return;
            }
            remove(i);
        }
    }

    private void remove(int i) {
        this.activeEntityIds.unsafeClear(i);
        this.removedIds.unsafeSet(i);
    }

    private void insert(int i) {
        this.activeEntityIds.unsafeSet(i);
        this.insertedIds.unsafeSet(i);
    }

    void process(IntBag intBag, IntBag intBag2) {
        deleted(intBag2);
        changed(intBag);
        informEntityChanges();
    }

    void processAll(IntBag intBag, IntBag intBag2) {
        deletedAll(intBag2);
        changed(intBag);
        informEntityChanges();
    }

    void informEntityChanges() {
        if (this.insertedIds.isEmpty() && this.removedIds.isEmpty()) {
            return;
        }
        transferBitsToInts(this.extra.inserted, this.extra.removed);
        this.extra.informEntityChanges();
        this.entities.setSize(0);
    }

    private void transferBitsToInts(IntBag intBag, IntBag intBag2) {
        this.insertedIds.toIntBag(intBag);
        this.removedIds.toIntBag(intBag2);
        this.insertedIds.clear();
        this.removedIds.clear();
    }

    private void changed(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i += 2) {
            int i2 = data[i];
            boolean zUnsafeGet = this.aspectCache.unsafeGet(data[i + 1]);
            boolean zUnsafeGet2 = this.activeEntityIds.unsafeGet(i2);
            if (zUnsafeGet && !zUnsafeGet2) {
                insert(i2);
            } else if (!zUnsafeGet && zUnsafeGet2) {
                remove(i2);
            }
        }
    }

    private void deleted(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            int i2 = data[i];
            if (this.activeEntityIds.unsafeGet(i2)) {
                remove(i2);
            }
        }
    }

    private void deletedAll(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            int i2 = data[i];
            this.activeEntityIds.unsafeClear(i2);
            this.removedIds.unsafeSet(i2);
        }
    }

    public void addSubscriptionListener(SubscriptionListener subscriptionListener) {
        this.extra.listeners.add(subscriptionListener);
    }

    public void removeSubscriptionListener(SubscriptionListener subscriptionListener) {
        this.extra.listeners.remove(subscriptionListener);
    }

    public String toString() {
        return "EntitySubscription[" + getAspectBuilder() + "]";
    }

    public static class SubscriptionExtra {
        final Aspect aspect;
        final Aspect.Builder aspectReflection;
        final IntBag inserted = new IntBag();
        final IntBag removed = new IntBag();
        final Bag<SubscriptionListener> listeners = new Bag<>();

        public SubscriptionExtra(Aspect aspect, Aspect.Builder builder) {
            this.aspect = aspect;
            this.aspectReflection = builder;
        }

        void informEntityChanges() {
            informListeners();
            this.removed.setSize(0);
            this.inserted.setSize(0);
        }

        private void informListeners() {
            int size = this.listeners.size();
            for (int i = 0; size > i; i++) {
                SubscriptionListener subscriptionListener = this.listeners.get(i);
                if (this.removed.size() > 0) {
                    subscriptionListener.removed(this.removed);
                }
                if (this.inserted.size() > 0) {
                    subscriptionListener.inserted(this.inserted);
                }
            }
        }
    }
}
