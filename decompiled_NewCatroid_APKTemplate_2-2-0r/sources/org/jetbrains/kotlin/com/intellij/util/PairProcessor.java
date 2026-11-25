package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface PairProcessor<S, T> {
    boolean process(S s, T t);
}
