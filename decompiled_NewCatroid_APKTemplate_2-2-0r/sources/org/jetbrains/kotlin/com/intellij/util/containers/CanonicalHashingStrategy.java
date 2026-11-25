package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Objects;

/* JADX INFO: compiled from: HashingStrategy.java */
/* JADX INFO: loaded from: classes3.dex */
final class CanonicalHashingStrategy<T> implements HashingStrategy<T> {
    static final HashingStrategy<?> INSTANCE = new CanonicalHashingStrategy();

    CanonicalHashingStrategy() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
    public int hashCode(T t) {
        return Objects.hashCode(t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
    public boolean equals(T t, T t2) {
        return Objects.equals(t, t2);
    }
}
