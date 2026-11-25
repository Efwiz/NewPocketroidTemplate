package org.jetbrains.kotlin.com.intellij.util;

import java.util.Arrays;
import java.util.Collections;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;

/* JADX INFO: loaded from: classes3.dex */
public final class Functions {
    private static final Function.Mono<?> ID = new Function.Mono<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.1
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public Object fun(Object obj) {
            return obj;
        }

        public String toString() {
            return "Functions.ID";
        }
    };
    private static final Function<?, String> TO_STRING = new Function<Object, String>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.2
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public String fun(Object obj) {
            return String.valueOf(obj);
        }

        public String toString() {
            return "Functions.TO_STRING";
        }
    };
    private static final Function<Pair<?, ?>, Object> PAIR_FIRST = new Function<Pair<?, ?>, Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.6
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public Object fun(Pair<?, ?> pair) {
            return Pair.getFirst(pair);
        }
    };
    private static final Function<Pair<?, ?>, Object> PAIR_SECOND = new Function<Pair<?, ?>, Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.7
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public Object fun(Pair<?, ?> pair) {
            return Pair.getSecond(pair);
        }
    };
    private static final Function<Object[], Iterable<Object>> WRAP_ARRAY = new Function<Object[], Iterable<Object>>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.8
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public Iterable<Object> fun(Object[] objArr) {
            return objArr == null ? Collections.emptyList() : Arrays.asList(objArr);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 7) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 7) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "clazz";
        } else if (i == 2) {
            objArr[0] = "f1";
        } else if (i == 3) {
            objArr[0] = "f2";
        } else if (i != 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/Functions";
        } else {
            objArr[0] = "map";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 7:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/Functions";
                break;
            case 4:
            case 5:
                objArr[1] = "compose";
                break;
            case 6:
                objArr[1] = "TO_STRING";
                break;
            case 8:
                objArr[1] = "pairFirst";
                break;
            case 9:
                objArr[1] = "pairSecond";
                break;
            case 10:
                objArr[1] = "wrapArray";
                break;
            default:
                objArr[1] = "identity";
                break;
        }
        if (i == 1) {
            objArr[2] = "cast";
        } else if (i == 2 || i == 3) {
            objArr[2] = "compose";
        } else if (i == 7) {
            objArr[2] = "fromMap";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 7) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static <A> Function.Mono<A> id() {
        return (Function.Mono) identity();
    }

    public static <A, B> Function<A, B> constant(final B b) {
        return new Function<A, B>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public B fun(A a) {
                return (B) b;
            }
        };
    }

    public static <A, B> Function<A, B> identity() {
        Function.Mono<?> mono = ID;
        if (mono == null) {
            $$$reportNull$$$0(0);
        }
        return mono;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <A, B, C> Function<A, C> compose(final Function<? super A, ? extends B> function, final Function<? super B, ? extends C> function2) {
        if (function == 0) {
            $$$reportNull$$$0(2);
        }
        if (function2 == 0) {
            $$$reportNull$$$0(3);
        }
        if (function != identity() && function2 != identity()) {
            return new Function<A, C>() { // from class: org.jetbrains.kotlin.com.intellij.util.Functions.4
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public C fun(A a) {
                    return (C) function2.fun(function.fun(a));
                }
            };
        }
        if (function == function2) {
            return identity();
        }
        if (function == identity()) {
            if (function2 == 0) {
                $$$reportNull$$$0(4);
            }
            return function2;
        }
        if (function == 0) {
            $$$reportNull$$$0(5);
        }
        return function;
    }
}
