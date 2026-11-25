package com.hivemq.client.internal.util.collections;

/* JADX INFO: loaded from: classes10.dex */
public class HandleList<E> extends NodeList<Handle<E>> {

    public static class Handle<E> extends NodeList.Node<Handle<E>> {
        private final E element;

        Handle(E e) {
            this.element = e;
        }

        public E getElement() {
            return this.element;
        }
    }

    public Handle<E> add(E e) {
        Handle<E> handle = new Handle<>(e);
        add(handle);
        return handle;
    }
}
