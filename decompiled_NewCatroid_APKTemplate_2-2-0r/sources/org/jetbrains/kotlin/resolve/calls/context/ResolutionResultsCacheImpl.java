package org.jetbrains.kotlin.resolve.calls.context;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.HashMap;
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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0000J\u0013\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u0005H\u0096\u0002J:\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00052\u0010\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCacheImpl;", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache;", "()V", "data", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache$CachedData;", "addData", "", ResponseCacheMiddleware.CACHE, "get", "call", PsiKeyword.RECORD, "results", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "deferredComputation", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "resolutionTrace", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolutionResultsCacheImpl implements ResolutionResultsCache {
    private final HashMap<Call, ResolutionResultsCache.CachedData> data = new HashMap<>();

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache
    public void record(Call call, OverloadResolutionResultsImpl<? extends CallableDescriptor> results, BasicCallResolutionContext deferredComputation, TracingStrategy tracing, DelegatingBindingTrace resolutionTrace) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(results, "results");
        Intrinsics.checkNotNullParameter(deferredComputation, "deferredComputation");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        Intrinsics.checkNotNullParameter(resolutionTrace, "resolutionTrace");
        this.data.put(call, new ResolutionResultsCache.CachedData(results, deferredComputation, tracing, resolutionTrace));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache
    public ResolutionResultsCache.CachedData get(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return this.data.get(call);
    }

    public final void addData(ResolutionResultsCacheImpl cache) {
        Intrinsics.checkNotNullParameter(cache, "cache");
        this.data.putAll(cache.data);
    }
}
