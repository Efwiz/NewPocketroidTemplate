package com.artemis;

import com.artemis.Component;
import com.artemis.utils.Bag;

/* JADX INFO: loaded from: classes8.dex */
public class ImmediateComponentRemover<A extends Component> extends ComponentRemover<A> {
    @Override // com.artemis.ComponentRemover
    boolean has(int i) {
        return false;
    }

    @Override // com.artemis.ComponentRemover
    void purge() {
    }

    @Override // com.artemis.ComponentRemover
    boolean unmark(int i) {
        return false;
    }

    public ImmediateComponentRemover(Bag<A> bag, ComponentPool componentPool) {
        super(bag, componentPool);
    }

    @Override // com.artemis.ComponentRemover
    void mark(int i) {
        PooledComponent pooledComponent;
        if (this.pool != null && (pooledComponent = (PooledComponent) this.components.get(i)) != null) {
            this.pool.free(pooledComponent);
        }
        this.components.unsafeSet(i, null);
    }
}
