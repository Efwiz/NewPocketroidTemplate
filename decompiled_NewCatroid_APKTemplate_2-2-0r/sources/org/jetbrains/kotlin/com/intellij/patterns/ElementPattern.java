package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public interface ElementPattern<T> {
    boolean accepts(Object obj);

    boolean accepts(Object obj, ProcessingContext processingContext);

    ElementPatternCondition<T> getCondition();
}
