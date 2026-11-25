package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Interner<T> {
    public abstract void clear();

    public abstract Set<T> getValues();

    public abstract T intern(T t);

    public static <T> Interner<T> createWeakInterner() {
        return new WeakInterner();
    }

    public static Interner<String> createStringInterner() {
        return new HashSetInterner();
    }
}
