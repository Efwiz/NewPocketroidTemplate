package com.android.tools.r8.graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class G {
    static final /* synthetic */ boolean f = true;
    final Q0 a;
    final int b;
    final HashSet c = new HashSet();
    final ArrayDeque d = new ArrayDeque();
    boolean e = false;

    G(Q0 q0, int i) {
        this.a = q0;
        this.b = i;
    }

    abstract void a(AbstractC0727q0 abstractC0727q0);

    public void a(Iterable iterable, Consumer consumer) {
        Iterator it = iterable.iterator();
        while (true) {
            if (!it.hasNext() && this.d.isEmpty()) {
                this.c.clear();
                return;
            }
            if (this.d.isEmpty()) {
                a((AbstractC0727q0) it.next());
                if (this.d.isEmpty()) {
                    continue;
                }
            }
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) this.d.removeFirst();
            if (!this.c.add(abstractC0727q0)) {
                continue;
            } else {
                if (!f && this.b == 4 && !abstractC0727q0.e0()) {
                    throw new AssertionError();
                }
                consumer.accept(abstractC0727q0);
            }
        }
    }
}
