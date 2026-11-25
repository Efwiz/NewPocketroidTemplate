package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PropertyPatternCondition<T, P> extends PatternConditionPlus<T, P> {
    public abstract P getPropertyValue(Object obj);

    @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternConditionPlus
    public boolean processValues(T t, ProcessingContext processingContext, PairProcessor<? super P, ? super ProcessingContext> pairProcessor) {
        return pairProcessor.process(getPropertyValue(t), processingContext);
    }
}
