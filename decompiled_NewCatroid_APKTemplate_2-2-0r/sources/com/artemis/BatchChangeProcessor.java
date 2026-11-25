package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.IntBag;

/* JADX INFO: loaded from: classes8.dex */
final class BatchChangeProcessor {
    private final AspectSubscriptionManager asm;
    final BitVector changed;
    private final BitVector deleted;
    private final WildBag<EntityEdit> edited;
    private final BitVector pendingPurge;
    private final Bag<EntityEdit> pool;
    final WildBag<ComponentRemover> purgatories;
    private final IntBag toPurge;
    private final World world;

    BatchChangeProcessor(World world) {
        BitVector bitVector = new BitVector();
        this.changed = bitVector;
        this.purgatories = new WildBag<>(ComponentRemover.class);
        BitVector bitVector2 = new BitVector();
        this.deleted = bitVector2;
        BitVector bitVector3 = new BitVector();
        this.pendingPurge = bitVector3;
        this.toPurge = new IntBag();
        this.pool = new Bag<>();
        this.edited = new WildBag<>(EntityEdit.class);
        this.world = world;
        this.asm = world.getAspectSubscriptionManager();
        EntityManager entityManager = world.getEntityManager();
        entityManager.registerEntityStore(bitVector);
        entityManager.registerEntityStore(bitVector2);
        entityManager.registerEntityStore(bitVector3);
    }

    boolean isDeleted(int i) {
        return this.pendingPurge.unsafeGet(i);
    }

    void delete(int i) {
        this.deleted.unsafeSet(i);
        this.pendingPurge.unsafeSet(i);
        this.changed.unsafeClear(i);
    }

    EntityEdit obtainEditor(int i) {
        int size = this.edited.size();
        if (size != 0) {
            int i2 = size - 1;
            if (this.edited.get(i2).getEntityId() == i) {
                return this.edited.get(i2);
            }
        }
        EntityEdit entityEdit = entityEdit();
        this.edited.add(entityEdit);
        entityEdit.entityId = i;
        return entityEdit;
    }

    private EntityEdit entityEdit() {
        if (this.pool.isEmpty()) {
            return new EntityEdit(this.world);
        }
        return this.pool.removeLast();
    }

    void update() {
        while (true) {
            if (!this.changed.isEmpty() || !this.deleted.isEmpty()) {
                this.asm.process(this.changed, this.deleted);
                purgeComponents();
            } else {
                clean();
                return;
            }
        }
    }

    void purgeComponents() {
        int size = this.purgatories.size();
        for (int i = 0; size > i; i++) {
            this.purgatories.get(i).purge();
        }
        this.purgatories.setSize(0);
    }

    IntBag getPendingPurge() {
        this.pendingPurge.toIntBag(this.toPurge);
        this.pendingPurge.clear();
        return this.toPurge;
    }

    private boolean clean() {
        if (this.edited.isEmpty()) {
            return false;
        }
        EntityEdit[] data = this.edited.getData();
        int size = this.edited.size();
        for (int i = 0; size > i; i++) {
            this.pool.add(data[i]);
        }
        this.edited.setSize(0);
        return true;
    }
}
