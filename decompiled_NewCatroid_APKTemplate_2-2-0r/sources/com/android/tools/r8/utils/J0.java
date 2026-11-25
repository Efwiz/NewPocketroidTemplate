package com.android.tools.r8.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class J0<T> {
    static final /* synthetic */ boolean b = true;
    private final HashMap a = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set d(Object obj) {
        return new HashSet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Set<T> a(T t) {
        Object objB = b(t);
        if (objB == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        for (Object obj : this.a.keySet()) {
            if (b(obj).equals(objB)) {
                hashSet.add(obj);
            }
        }
        return hashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T b(T t) {
        T t2 = (T) this.a.get(t);
        if (t2 == null) {
            return null;
        }
        Object obj = this.a.get(t2);
        if (t2 == obj) {
            return t2;
        }
        T t3 = (T) b(obj);
        this.a.put(t, t3);
        return t3;
    }

    public boolean c(T t) {
        T tB = b(t);
        return tB == null || tB.equals(t);
    }

    public T e(T t) {
        boolean z = b;
        if (!z && this.a.containsKey(t)) {
            throw new AssertionError();
        }
        this.a.put(t, t);
        if (z || b(t) == t) {
            return t;
        }
        throw new AssertionError();
    }

    public final String toString() {
        Map<T, Set<T>> mapA = a();
        final StringBuilder sbAppend = new StringBuilder().append("Number of sets: ").append(mapA.keySet().size()).append(System.lineSeparator());
        mapA.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.J0$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                J0.a(sbAppend, obj, (Set) obj2);
            }
        });
        return sbAppend.toString();
    }

    public T a(T t, T t2) {
        boolean z = b;
        if (!z && t == null) {
            throw new AssertionError();
        }
        if (!z && t2 == null) {
            throw new AssertionError();
        }
        if (t == t2) {
            return t;
        }
        if (!z && this.a.get(t) != t) {
            throw new AssertionError();
        }
        if (!z && this.a.get(t2) != t2) {
            throw new AssertionError();
        }
        this.a.put(t2, t);
        if (!z && b(t) != t) {
            throw new AssertionError();
        }
        if (z || b(t2) == t) {
            return t;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Map<T, Set<T>> a() {
        HashMap map = new HashMap();
        for (Object obj : this.a.keySet()) {
            ((Set) map.computeIfAbsent(b(obj), new Function() { // from class: com.android.tools.r8.utils.J0$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return J0.d(obj2);
                }
            })).add(obj);
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final StringBuilder sb, Object obj, Set set) {
        sb.append("Representative: ").append(obj).append(System.lineSeparator());
        set.forEach(new Consumer() { // from class: com.android.tools.r8.utils.J0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                J0.a(sb, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, Object obj) {
        sb.append("    ").append(obj).append(System.lineSeparator());
    }
}
