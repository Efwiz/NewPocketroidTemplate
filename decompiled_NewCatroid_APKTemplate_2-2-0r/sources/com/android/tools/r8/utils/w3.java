package com.android.tools.r8.utils;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class w3 {
    static final /* synthetic */ boolean e = true;
    final t3 a;
    final u3 b;
    private int c;
    private t3 d;

    private w3(String str, int i, y3 y3Var) {
        this.c = 0;
        this.d = new t3("<zero>", false);
        this.a = (t3) y3Var.b.peek();
        this.b = new u3(this, str, y3Var.c, i);
    }

    public void a(Collection collection) {
        final boolean z = this.b.b;
        final ArrayDeque arrayDeque = new ArrayDeque();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            y3 y3Var = (y3) it.next();
            if (y3Var != y3.b()) {
                if (!e && !y3Var.b.isEmpty()) {
                    throw new AssertionError("Expected sub-timing to have completed prior to merge");
                }
                this.c++;
                u3 u3Var = this.b;
                long j = u3Var.d;
                t3 t3Var = y3Var.a;
                u3Var.d = j + t3Var.d;
                if (t3Var.d > this.d.d) {
                    this.d = t3Var;
                }
                arrayDeque.addLast(new v3(u3Var, t3Var));
            }
        }
        while (!arrayDeque.isEmpty()) {
            final v3 v3Var = (v3) arrayDeque.pollFirst();
            v3Var.b.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.w3$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    w3.a(v3Var, z, arrayDeque, (String) obj, (t3) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(v3 v3Var, final boolean z, Deque deque, String str, t3 t3Var) {
        t3 t3Var2 = (t3) v3Var.a.c.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.utils.w3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return w3.a(z, (String) obj);
            }
        });
        t3Var2.d += t3Var.d;
        t3Var2.g = t3Var.g;
        if (t3Var.c.isEmpty()) {
            return;
        }
        deque.addLast(new v3(t3Var2, t3Var));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ t3 a(boolean z, String str) {
        return new t3(str, z);
    }

    public void a() {
        if (!e && this.a.c.containsKey(this.b.a)) {
            throw new AssertionError();
        }
        this.b.b();
        LinkedHashMap linkedHashMap = this.a.c;
        u3 u3Var = this.b;
        linkedHashMap.put(u3Var.a, u3Var);
    }
}
