package org.jetbrains.kotlin.com.intellij.util.diff;

import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;

/* JADX INFO: loaded from: classes3.dex */
public interface FlyweightCapableTreeStructure<T> {
    void disposeChildren(T[] tArr, int i);

    int getChildren(T t, Ref<T[]> ref);

    int getEndOffset(T t);

    T getParent(T t);

    T getRoot();

    int getStartOffset(T t);

    CharSequence toString(T t);
}
