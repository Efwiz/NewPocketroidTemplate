package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: ResolutionResultsCache.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0007J\u0013\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002J:\u0010\f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0010\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryResolutionResultsCache;", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache;", "parentCache", "(Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache;)V", "innerCache", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCacheImpl;", "commit", "", "get", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache$CachedData;", "call", "Lorg/jetbrains/kotlin/psi/Call;", PsiKeyword.RECORD, "results", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "deferredComputation", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "resolutionTrace", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TemporaryResolutionResultsCache implements ResolutionResultsCache {
    private final ResolutionResultsCacheImpl innerCache;
    private final ResolutionResultsCache parentCache;

    public TemporaryResolutionResultsCache(ResolutionResultsCache parentCache) {
        Intrinsics.checkNotNullParameter(parentCache, "parentCache");
        this.parentCache = parentCache;
        this.innerCache = new ResolutionResultsCacheImpl();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache
    public void record(Call call, OverloadResolutionResultsImpl<? extends CallableDescriptor> results, BasicCallResolutionContext deferredComputation, TracingStrategy tracing, DelegatingBindingTrace resolutionTrace) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(results, "results");
        Intrinsics.checkNotNullParameter(deferredComputation, "deferredComputation");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        Intrinsics.checkNotNullParameter(resolutionTrace, "resolutionTrace");
        this.innerCache.record(call, results, deferredComputation, tracing, resolutionTrace);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache
    public ResolutionResultsCache.CachedData get(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        ResolutionResultsCache.CachedData cachedData = this.innerCache.get(call);
        return cachedData == null ? this.parentCache.get(call) : cachedData;
    }

    public final void commit() {
        ResolutionResultsCache resolutionResultsCache = this.parentCache;
        if (resolutionResultsCache instanceof ResolutionResultsCacheImpl) {
            ((ResolutionResultsCacheImpl) resolutionResultsCache).addData(this.innerCache);
        } else if (resolutionResultsCache instanceof TemporaryResolutionResultsCache) {
            ((TemporaryResolutionResultsCache) resolutionResultsCache).innerCache.addData(this.innerCache);
        }
    }
}
