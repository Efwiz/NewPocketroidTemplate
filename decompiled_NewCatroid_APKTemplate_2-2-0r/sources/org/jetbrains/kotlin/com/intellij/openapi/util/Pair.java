package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Comparator;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class Pair<A, B> {
    private static final Pair EMPTY = create(null, null);
    public final A first;
    public final B second;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "second";
        } else if (i != 2) {
            objArr[0] = "first";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/Pair";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/Pair";
        } else {
            objArr[1] = "empty";
        }
        if (i != 2) {
            objArr[2] = "createNonNull";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<>(a, b);
    }

    public static <A, B> NonNull<A, B> createNonNull(A a, B b) {
        if (a == null) {
            $$$reportNull$$$0(0);
        }
        if (b == null) {
            $$$reportNull$$$0(1);
        }
        return new NonNull<>(a, b);
    }

    public static <A, B> Pair<A, B> pair(A a, B b) {
        return new Pair<>(a, b);
    }

    public static <A, B> Function<A, Pair<A, B>> createFunction(final B b) {
        return new Function<A, Pair<A, B>>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Pair.1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public Pair<A, B> fun(A a) {
                return Pair.create(a, b);
            }
        };
    }

    public static <T> T getFirst(Pair<T, ?> pair) {
        if (pair != null) {
            return pair.first;
        }
        return null;
    }

    public static <T> T getSecond(Pair<?, T> pair) {
        if (pair != null) {
            return pair.second;
        }
        return null;
    }

    public static <A, B> Pair<A, B> empty() {
        Pair<A, B> pair = EMPTY;
        if (pair == null) {
            $$$reportNull$$$0(2);
        }
        return pair;
    }

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            if (Comparing.equal(this.first, pair.first) && Comparing.equal(this.second, pair.second)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        A a = this.first;
        int iHashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        return iHashCode + (b != null ? b.hashCode() : 0);
    }

    public String toString() {
        return "<" + this.first + "," + this.second + ">";
    }

    public static class NonNull<A, B> extends Pair<A, B> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "first";
            } else {
                objArr[0] = "second";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/Pair$NonNull";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NonNull(A a, B b) {
            super(a, b);
            if (a == null) {
                $$$reportNull$$$0(0);
            }
            if (b == null) {
                $$$reportNull$$$0(1);
            }
        }
    }

    public static <A extends Comparable<? super A>, B> Comparator<Pair<A, B>> comparingByFirst() {
        return new Comparator<Pair<A, B>>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Pair.2
            @Override // java.util.Comparator
            public int compare(Pair<A, B> pair, Pair<A, B> pair2) {
                return ((Comparable) pair.first).compareTo(pair2.first);
            }
        };
    }

    public static <A, B extends Comparable<? super B>> Comparator<Pair<A, B>> comparingBySecond() {
        return new Comparator<Pair<A, B>>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Pair.3
            @Override // java.util.Comparator
            public int compare(Pair<A, B> pair, Pair<A, B> pair2) {
                return ((Comparable) pair.second).compareTo(pair2.second);
            }
        };
    }
}
