package com.artemis;

import com.artemis.Component;
import com.artemis.utils.Bag;

/* JADX INFO: loaded from: classes8.dex */
abstract class ComponentRemover<A extends Component> {
    final Bag<A> components;
    protected final ComponentPool pool;

    abstract boolean has(int i);

    abstract void mark(int i);

    abstract void purge();

    abstract boolean unmark(int i);

    public ComponentRemover(Bag<A> bag, ComponentPool componentPool) {
        this.components = bag;
        this.pool = componentPool;
    }
}
