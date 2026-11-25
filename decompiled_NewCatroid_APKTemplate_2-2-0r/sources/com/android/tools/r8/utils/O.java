package com.android.tools.r8.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class O<T> {
    private Object a;

    public O() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    void a(Consumer consumer) {
        if (c()) {
            consumer.accept(a());
        }
    }

    Object b() {
        Object obj = this.a;
        this.a = null;
        return obj;
    }

    public boolean c() {
        return this.a != null;
    }

    public final boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.a, ((O) obj).a);
    }

    public final int hashCode() {
        return Objects.hashCode(this.a);
    }

    public O(Object obj) {
        a(obj);
    }

    Object a(Supplier supplier) {
        if (!c()) {
            a(supplier.get());
        }
        return this.a;
    }

    Object a() {
        return this.a;
    }

    public T a(Function<T, T> function) {
        T t = (T) a();
        a(function.apply(t));
        return t;
    }

    void a(Object obj) {
        this.a = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean a(Predicate predicate) {
        return c() && predicate.test(a());
    }
}
