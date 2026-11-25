package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public interface NullableFunction<Param, Result> extends Function<Param, Result> {

    @Deprecated
    public static final NullableFunction<?, ?> NULL = new NullableFunction<Object, Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.NullableFunction.1
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public Object fun(Object obj) {
            return null;
        }

        public String toString() {
            return "NullableFunction.NULL";
        }
    };
}
