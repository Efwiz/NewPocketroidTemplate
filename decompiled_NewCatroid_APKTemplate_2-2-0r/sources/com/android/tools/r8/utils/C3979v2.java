package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: renamed from: com.android.tools.r8.utils.v2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3979v2<T, S> {
    private Object a;
    private Object b;

    public C3979v2() {
        this(null, null);
    }

    public T a() {
        return (T) this.a;
    }

    public S b() {
        return (S) this.b;
    }

    public final boolean equals(Object obj) {
        throw new C3405y10("Pair does not want to support equality!");
    }

    public final int hashCode() {
        throw new C3405y10("Pair does not want to support hashing!");
    }

    public final String toString() {
        return "Pair{" + this.a + ", " + this.b + "}";
    }

    public C3979v2(T t, S s) {
        this.a = t;
        this.b = s;
    }

    public final void a(Comparable comparable) {
        this.a = comparable;
    }

    public final void b(Comparable comparable) {
        this.b = comparable;
    }

    public static <T, S> C3979v2<T, S> a(T t, S s) {
        return new C3979v2<>(t, s);
    }
}
