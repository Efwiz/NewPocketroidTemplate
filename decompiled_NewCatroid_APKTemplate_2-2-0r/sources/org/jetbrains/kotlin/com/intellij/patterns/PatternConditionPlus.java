package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PatternConditionPlus<Target, Value> extends PatternCondition<Target> implements PairProcessor<Value, ProcessingContext> {
    private final ElementPattern myValuePattern;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/patterns/PatternConditionPlus", "accepts"));
    }

    public abstract boolean processValues(Target target, ProcessingContext processingContext, PairProcessor<? super Value, ? super ProcessingContext> pairProcessor);

    public PatternConditionPlus(String str, ElementPattern elementPattern) {
        super(str);
        this.myValuePattern = elementPattern;
    }

    public ElementPattern getValuePattern() {
        return this.myValuePattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
    public boolean accepts(Target target, ProcessingContext processingContext) {
        if (target == null) {
            $$$reportNull$$$0(0);
        }
        return !processValues(target, processingContext, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.PairProcessor
    public final boolean process(Value value, ProcessingContext processingContext) {
        return !this.myValuePattern.accepts(value, processingContext);
    }
}
