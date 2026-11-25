package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

/* JADX INFO: loaded from: classes3.dex */
public interface InnerClassSourceStrategy<T> {
    void accept(T t, StubBuildingVisitor<T> stubBuildingVisitor);

    T findInnerClass(String str, T t);
}
