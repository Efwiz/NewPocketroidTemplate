package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public class Ref<T> {
    private T myValue;

    public Ref() {
    }

    public Ref(T t) {
        this.myValue = t;
    }

    public final T get() {
        return this.myValue;
    }

    public final void set(T t) {
        this.myValue = t;
    }

    public static <T> Ref<T> create() {
        return new Ref<>();
    }

    public static <T> Ref<T> create(T t) {
        return new Ref<>(t);
    }

    public String toString() {
        return String.valueOf(this.myValue);
    }
}
