package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ql, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1298Ql extends ArrayDeque {
    private boolean a;

    private C1298Ql(List list) {
        super(list);
        this.a = false;
    }

    public static C1298Ql a(Object... objArr) {
        C1298Ql c1298Ql = new C1298Ql(Arrays.asList(objArr));
        c1298Ql.a = true;
        return c1298Ql;
    }

    @Override // java.util.ArrayDeque, java.util.AbstractCollection, java.util.Collection, java.util.Deque, java.util.Queue
    public final boolean add(Object obj) {
        if (this.a) {
            throw new C3405y10("Modification not allowed on immutable structure");
        }
        return super.add(obj);
    }

    @Override // java.util.ArrayDeque, java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public final boolean addAll(Collection collection) {
        if (this.a) {
            throw new C3405y10("Modification not allowed on immutable structure");
        }
        return super.addAll(collection);
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final void addFirst(Object obj) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final void addLast(Object obj) {
        if (this.a) {
            throw new C3405y10("Modification not allowed on immutable structure");
        }
        super.addLast(obj);
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final Object pop() {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final void push(Object obj) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public final boolean remove(Object obj) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.AbstractCollection, java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final Object removeFirst() {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final boolean removeFirstOccurrence(Object obj) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Collection
    public final boolean removeIf(Predicate predicate) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final Object removeLast() {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque
    public final boolean removeLastOccurrence(Object obj) {
        throw new C3405y10("Modification not allowed on immutable structure");
    }

    @Override // java.util.ArrayDeque, java.util.Deque, java.util.Queue
    public final Object remove() {
        throw new C3405y10("Modification not allowed on immutable structure");
    }
}
