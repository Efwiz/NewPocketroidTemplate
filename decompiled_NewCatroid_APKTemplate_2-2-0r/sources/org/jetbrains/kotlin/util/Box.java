package org.jetbrains.kotlin.util;

/* JADX INFO: loaded from: classes6.dex */
public class Box<T> {
    private final T data;

    public Box(T t) {
        this.data = t;
    }

    public T getData() {
        return this.data;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
