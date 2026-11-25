package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ed, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0947Ed {
    static final /* synthetic */ boolean c = true;
    private final Deque a;
    private final Set b;

    private C0947Ed(ArrayDeque arrayDeque, Set set) {
        this.a = arrayDeque;
        this.b = set;
    }

    public static C0947Ed a() {
        return new C0947Ed(new ArrayDeque(), OU.c());
    }

    public final ArrayDeque b(Object obj) {
        ArrayDeque arrayDeque = new ArrayDeque();
        do {
            if (!c && this.a.isEmpty()) {
                throw new AssertionError();
            }
            arrayDeque.addLast(this.a.removeLast());
        } while (arrayDeque.getLast() != obj);
        Iterator itDescendingIterator = arrayDeque.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            this.a.addLast(itDescendingIterator.next());
        }
        return arrayDeque;
    }

    public final boolean a(Object obj) {
        return this.b.contains(obj);
    }

    public final void a(AbstractC1034Hd abstractC1034Hd) {
        abstractC1034Hd.getClass();
        if (abstractC1034Hd instanceof C1005Gd) {
            Object obj = abstractC1034Hd.a;
            this.a.addLast(obj);
            boolean zAdd = this.b.add(obj);
            if (!c && !zAdd) {
                throw new AssertionError();
            }
            return;
        }
        boolean z = c;
        if (!z && !(abstractC1034Hd instanceof C0979Fd)) {
            throw new AssertionError();
        }
        Object obj2 = abstractC1034Hd.a;
        Object objRemoveLast = this.a.removeLast();
        if (!z && objRemoveLast != obj2) {
            throw new AssertionError();
        }
        boolean zRemove = this.b.remove(objRemoveLast);
        if (!z && !zRemove) {
            throw new AssertionError();
        }
    }
}
