package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;

/* JADX INFO: compiled from: ResolutionResultsCache.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0010J\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002J4\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache;", "", "get", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache$CachedData;", "call", "Lorg/jetbrains/kotlin/psi/Call;", PsiKeyword.RECORD, "", "results", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "deferredComputation", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "resolutionTrace", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "CachedData", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ResolutionResultsCache {
    CachedData get(Call call);

    void record(Call call, OverloadResolutionResultsImpl<?> results, BasicCallResolutionContext deferredComputation, TracingStrategy tracing, DelegatingBindingTrace resolutionTrace);

    /* JADX INFO: compiled from: ResolutionResultsCache.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\r\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0016\u001a\u00020\tHÆ\u0003J5\u0010\u0017\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache$CachedData;", "", "resolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "deferredComputation", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "resolutionTrace", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "(Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;)V", "getDeferredComputation", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getResolutionResults", "()Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "getResolutionTrace", "()Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "getTracing", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class CachedData {
        private final BasicCallResolutionContext deferredComputation;
        private final OverloadResolutionResultsImpl<?> resolutionResults;
        private final DelegatingBindingTrace resolutionTrace;
        private final TracingStrategy tracing;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ CachedData copy$default(CachedData cachedData, OverloadResolutionResultsImpl overloadResolutionResultsImpl, BasicCallResolutionContext basicCallResolutionContext, TracingStrategy tracingStrategy, DelegatingBindingTrace delegatingBindingTrace, int i, Object obj) {
            if ((i & 1) != 0) {
                overloadResolutionResultsImpl = cachedData.resolutionResults;
            }
            if ((i & 2) != 0) {
                basicCallResolutionContext = cachedData.deferredComputation;
            }
            if ((i & 4) != 0) {
                tracingStrategy = cachedData.tracing;
            }
            if ((i & 8) != 0) {
                delegatingBindingTrace = cachedData.resolutionTrace;
            }
            return cachedData.copy(overloadResolutionResultsImpl, basicCallResolutionContext, tracingStrategy, delegatingBindingTrace);
        }

        public final OverloadResolutionResultsImpl<?> component1() {
            return this.resolutionResults;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final BasicCallResolutionContext getDeferredComputation() {
            return this.deferredComputation;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final TracingStrategy getTracing() {
            return this.tracing;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final DelegatingBindingTrace getResolutionTrace() {
            return this.resolutionTrace;
        }

        public final CachedData copy(OverloadResolutionResultsImpl<?> resolutionResults, BasicCallResolutionContext deferredComputation, TracingStrategy tracing, DelegatingBindingTrace resolutionTrace) {
            Intrinsics.checkNotNullParameter(resolutionResults, "resolutionResults");
            Intrinsics.checkNotNullParameter(deferredComputation, "deferredComputation");
            Intrinsics.checkNotNullParameter(tracing, "tracing");
            Intrinsics.checkNotNullParameter(resolutionTrace, "resolutionTrace");
            return new CachedData(resolutionResults, deferredComputation, tracing, resolutionTrace);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CachedData)) {
                return false;
            }
            CachedData cachedData = (CachedData) other;
            return Intrinsics.areEqual(this.resolutionResults, cachedData.resolutionResults) && Intrinsics.areEqual(this.deferredComputation, cachedData.deferredComputation) && Intrinsics.areEqual(this.tracing, cachedData.tracing) && Intrinsics.areEqual(this.resolutionTrace, cachedData.resolutionTrace);
        }

        public int hashCode() {
            return (((((this.resolutionResults.hashCode() * 31) + this.deferredComputation.hashCode()) * 31) + this.tracing.hashCode()) * 31) + this.resolutionTrace.hashCode();
        }

        public String toString() {
            return "CachedData(resolutionResults=" + this.resolutionResults + ", deferredComputation=" + this.deferredComputation + ", tracing=" + this.tracing + ", resolutionTrace=" + this.resolutionTrace + ')';
        }

        public CachedData(OverloadResolutionResultsImpl<?> resolutionResults, BasicCallResolutionContext deferredComputation, TracingStrategy tracing, DelegatingBindingTrace resolutionTrace) {
            Intrinsics.checkNotNullParameter(resolutionResults, "resolutionResults");
            Intrinsics.checkNotNullParameter(deferredComputation, "deferredComputation");
            Intrinsics.checkNotNullParameter(tracing, "tracing");
            Intrinsics.checkNotNullParameter(resolutionTrace, "resolutionTrace");
            this.resolutionResults = resolutionResults;
            this.deferredComputation = deferredComputation;
            this.tracing = tracing;
            this.resolutionTrace = resolutionTrace;
        }

        public final OverloadResolutionResultsImpl<?> getResolutionResults() {
            return this.resolutionResults;
        }

        public final BasicCallResolutionContext getDeferredComputation() {
            return this.deferredComputation;
        }

        public final TracingStrategy getTracing() {
            return this.tracing;
        }

        public final DelegatingBindingTrace getResolutionTrace() {
            return this.resolutionTrace;
        }
    }
}
