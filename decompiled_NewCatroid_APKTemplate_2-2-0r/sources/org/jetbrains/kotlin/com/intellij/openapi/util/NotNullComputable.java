package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface NotNullComputable<T> extends Computable<T> {
    public static final NotNullComputable<Boolean> TRUE = new NotNullComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullComputable, org.jetbrains.kotlin.com.intellij.openapi.util.Computable
        public final Object compute() {
            return Boolean.TRUE;
        }
    };

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
    T compute();
}
