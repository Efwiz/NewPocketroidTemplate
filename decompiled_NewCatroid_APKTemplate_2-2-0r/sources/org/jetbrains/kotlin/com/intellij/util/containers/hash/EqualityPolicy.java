package org.jetbrains.kotlin.com.intellij.util.containers.hash;

/* JADX INFO: loaded from: classes3.dex */
public interface EqualityPolicy<T> {
    public static final EqualityPolicy<?> IDENTITY = new EqualityPolicy<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
        public boolean isEqual(Object obj, Object obj2) {
            return obj == obj2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
        public int getHashCode(Object obj) {
            return System.identityHashCode(obj);
        }
    };
    public static final EqualityPolicy<?> CANONICAL = new EqualityPolicy<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy.2
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
        public int getHashCode(Object obj) {
            if (obj != null) {
                return obj.hashCode();
            }
            return 0;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
        public boolean isEqual(Object obj, Object obj2) {
            if (obj != null) {
                return obj.equals(obj2);
            }
            return obj2 == null;
        }
    };

    int getHashCode(T t);

    boolean isEqual(T t, T t2);
}
