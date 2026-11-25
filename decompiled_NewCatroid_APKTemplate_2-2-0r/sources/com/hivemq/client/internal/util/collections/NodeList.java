package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.collections.NodeList.Node;

/* JADX INFO: loaded from: classes10.dex */
public class NodeList<N extends Node<N>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private N first;
    private N last;
    private int size;

    public static abstract class Node<N extends Node<N>> {
        N next;
        N prev;

        public N getPrev() {
            return this.prev;
        }

        public N getNext() {
            return this.next;
        }
    }

    public void add(N n) {
        N n2 = this.last;
        if (n2 == null) {
            this.last = n;
            this.first = n;
        } else {
            n2.next = n;
            n.prev = n2;
            this.last = n;
        }
        this.size++;
    }

    public void addFirst(N n) {
        N n2 = this.first;
        if (n2 == null) {
            this.last = n;
            this.first = n;
        } else {
            n2.prev = n;
            n.next = n2;
            this.first = n;
        }
        this.size++;
    }

    public void remove(N n) {
        N n2 = n.prev;
        N n3 = n.next;
        if (n2 == null) {
            this.first = n3;
        } else {
            n2.next = n3;
        }
        if (n3 == null) {
            this.last = n2;
        } else {
            n3.prev = n2;
        }
        this.size--;
    }

    public void replace(N n, N n2) {
        N n3 = n.prev;
        N n4 = n.next;
        n2.prev = n3;
        n2.next = n4;
        if (n3 == null) {
            this.first = n2;
        } else {
            n3.next = n2;
        }
        if (n4 == null) {
            this.last = n2;
        } else {
            n4.prev = n2;
        }
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public N getFirst() {
        return this.first;
    }

    public N getLast() {
        return this.last;
    }
}
