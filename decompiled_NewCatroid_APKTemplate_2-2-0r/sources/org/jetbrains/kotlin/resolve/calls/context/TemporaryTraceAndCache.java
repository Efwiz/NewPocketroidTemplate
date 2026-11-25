package org.jetbrains.kotlin.resolve.calls.context;

import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;

/* JADX INFO: loaded from: classes6.dex */
public class TemporaryTraceAndCache {
    public final TemporaryResolutionResultsCache cache;
    public final TemporaryBindingTrace trace;

    public TemporaryTraceAndCache(ResolutionContext resolutionContext, String str, KtExpression ktExpression) {
        this.trace = TemporaryBindingTrace.create(resolutionContext.trace, str, ktExpression);
        this.cache = new TemporaryResolutionResultsCache(resolutionContext.resolutionResultsCache);
    }

    public static TemporaryTraceAndCache create(ResolutionContext resolutionContext, String str, KtExpression ktExpression) {
        return new TemporaryTraceAndCache(resolutionContext, str, ktExpression);
    }

    public void commit() {
        this.trace.commit();
        this.cache.commit();
    }
}
