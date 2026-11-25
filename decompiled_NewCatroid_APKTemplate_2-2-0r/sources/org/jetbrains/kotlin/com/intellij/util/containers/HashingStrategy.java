package org.jetbrains.kotlin.com.intellij.util.containers;

/* JADX INFO: loaded from: classes3.dex */
public interface HashingStrategy<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/HashingStrategy";
        if (i == 1) {
            objArr[1] = "identity";
        } else if (i == 2) {
            objArr[1] = "caseInsensitive";
        } else if (i != 3) {
            objArr[1] = "canonical";
        } else {
            objArr[1] = "caseInsensitiveCharSequence";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    boolean equals(T t, T t2);

    int hashCode(T t);

    static <T> HashingStrategy<T> canonical() {
        HashingStrategy<T> hashingStrategy = (HashingStrategy<T>) CanonicalHashingStrategy.INSTANCE;
        if (hashingStrategy == null) {
            $$$reportNull$$$0(0);
        }
        return hashingStrategy;
    }

    static <T> HashingStrategy<T> identity() {
        HashingStrategy<T> hashingStrategy = (HashingStrategy<T>) IdentityHashingStrategy.INSTANCE;
        if (hashingStrategy == null) {
            $$$reportNull$$$0(1);
        }
        return hashingStrategy;
    }
}
