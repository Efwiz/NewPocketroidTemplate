package com.android.tools.r8.utils;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Q2<V> {
    static final /* synthetic */ boolean d = true;
    private final boolean b;
    private final TreeMap a = new TreeMap();
    private int c = 0;

    public Q2(boolean z) {
        this.b = z;
    }

    public V a(int i) {
        Map.Entry<Integer, V> entryA = a(Integer.valueOf(i));
        if (entryA != null) {
            return entryA.getValue();
        }
        return null;
    }

    public Map.Entry<Integer, V> a(Integer num) {
        Map.Entry<Integer, V> entryFloorEntry = this.a.floorEntry(num);
        if (entryFloorEntry == null || entryFloorEntry.getValue() == null) {
            return null;
        }
        return entryFloorEntry;
    }

    public Q2<V> a(final int i, final int i2, V v) {
        Map.Entry<Integer, V> entryA = a(Integer.valueOf(i2));
        final N n = new N(0);
        boolean zRemoveIf = this.a.navigableKeySet().removeIf(new Predicate() { // from class: com.android.tools.r8.utils.Q2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(i, i2, n, (Integer) obj);
            }
        });
        if (entryA != null) {
            if (!d && !this.b) {
                throw new AssertionError();
            }
            if (zRemoveIf) {
                n.a(Integer.valueOf(((Integer) n.a()).intValue() - 1));
            }
        }
        this.a.put(Integer.valueOf(i), v);
        int i3 = i2 + 1;
        if (!this.a.containsKey(Integer.valueOf(i3))) {
            this.a.put(Integer.valueOf(i3), entryA == null ? null : entryA.getValue());
        }
        this.c = (this.c - ((Integer) n.a()).intValue()) + 1;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(int i, int i2, N n, Integer num) {
        if (i >= num.intValue() || num.intValue() > i2) {
            return false;
        }
        if (!d && !this.b) {
            throw new AssertionError();
        }
        if (this.a.get(num) != null) {
            n.a(Integer.valueOf(((Integer) n.a()).intValue() + 1));
        }
        return true;
    }

    public int a() {
        return this.c;
    }

    public final void a(final Consumer consumer) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.utils.Q2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Q2.a(consumer, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, Object obj) {
        if (obj != null) {
            consumer.accept(obj);
        }
    }
}
