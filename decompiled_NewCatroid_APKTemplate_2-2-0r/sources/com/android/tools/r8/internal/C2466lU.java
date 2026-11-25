package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2466lU implements Iterator {
    private final Stack a;
    private C2904rC b;

    /* synthetic */ C2466lU(Y5 y5, int i) {
        this(y5);
    }

    private C2904rC a(Y5 y5) {
        while (y5 instanceof C2615nU) {
            C2615nU c2615nU = (C2615nU) y5;
            this.a.push(c2615nU);
            y5 = c2615nU.d;
        }
        return (C2904rC) y5;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b != null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private C2466lU(Y5 y5) {
        this.a = new Stack();
        this.b = a(y5);
    }

    @Override // java.util.Iterator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C2904rC next() {
        C2904rC c2904rCA;
        C2904rC c2904rC = this.b;
        if (c2904rC != null) {
            while (!this.a.isEmpty()) {
                c2904rCA = a(((C2615nU) this.a.pop()).e);
                if (c2904rCA.size() != 0) {
                    this.b = c2904rCA;
                    return c2904rC;
                }
            }
            c2904rCA = null;
            this.b = c2904rCA;
            return c2904rC;
        }
        throw new NoSuchElementException();
    }
}
