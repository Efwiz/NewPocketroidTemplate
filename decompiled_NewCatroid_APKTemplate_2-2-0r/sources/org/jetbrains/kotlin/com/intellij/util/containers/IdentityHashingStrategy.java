package org.jetbrains.kotlin.com.intellij.util.containers;

/* JADX INFO: compiled from: HashingStrategy.java */
/* JADX INFO: loaded from: classes3.dex */
final class IdentityHashingStrategy<T> implements HashingStrategy<T> {
    static final HashingStrategy<?> INSTANCE = new IdentityHashingStrategy();

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
    public boolean equals(T t, T t2) {
        return t == t2;
    }

    IdentityHashingStrategy() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
    public int hashCode(T t) {
        return System.identityHashCode(t);
    }
}
