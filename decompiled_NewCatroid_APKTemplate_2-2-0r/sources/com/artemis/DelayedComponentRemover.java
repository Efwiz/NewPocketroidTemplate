package com.artemis;

import com.artemis.Component;
import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;

/* JADX INFO: loaded from: classes8.dex */
class DelayedComponentRemover<A extends Component> extends ComponentRemover<A> {
    final BatchChangeProcessor batchProcessor;
    final BitVector idBits;

    DelayedComponentRemover(Bag<A> bag, ComponentPool componentPool, BatchChangeProcessor batchChangeProcessor) {
        super(bag, componentPool);
        this.idBits = new BitVector();
        this.batchProcessor = batchChangeProcessor;
    }

    @Override // com.artemis.ComponentRemover
    void mark(int i) {
        if (this.idBits.isEmpty()) {
            this.batchProcessor.purgatories.add(this);
        }
        this.idBits.set(i);
    }

    @Override // com.artemis.ComponentRemover
    boolean unmark(int i) {
        if (!this.idBits.get(i)) {
            return false;
        }
        this.idBits.clear(i);
        if (this.pool == null) {
            return true;
        }
        this.pool.free((PooledComponent) this.components.get(i));
        return true;
    }

    @Override // com.artemis.ComponentRemover
    void purge() {
        if (this.pool != null) {
            purgeWithPool();
        } else {
            purgeNoPool();
        }
        this.idBits.clear();
    }

    @Override // com.artemis.ComponentRemover
    boolean has(int i) {
        return this.idBits.get(i);
    }

    private void purgeWithPool() {
        int iNextSetBit = this.idBits.nextSetBit(0);
        while (iNextSetBit >= 0) {
            this.pool.free((PooledComponent) this.components.get(iNextSetBit));
            this.components.unsafeSet(iNextSetBit, null);
            iNextSetBit = this.idBits.nextSetBit(iNextSetBit + 1);
        }
    }

    private void purgeNoPool() {
        int iNextSetBit = this.idBits.nextSetBit(0);
        while (iNextSetBit >= 0) {
            this.components.unsafeSet(iNextSetBit, null);
            iNextSetBit = this.idBits.nextSetBit(iNextSetBit + 1);
        }
    }
}
