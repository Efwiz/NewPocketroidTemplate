package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: coroutineCodegenUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/ResolvedCallWithRealDescriptor;", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "fakeContinuationExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "getFakeContinuationExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ResolvedCallWithRealDescriptor {
    private final KtExpression fakeContinuationExpression;
    private final ResolvedCall<?> resolvedCall;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ResolvedCallWithRealDescriptor copy$default(ResolvedCallWithRealDescriptor resolvedCallWithRealDescriptor, ResolvedCall resolvedCall, KtExpression ktExpression, int i, Object obj) {
        if ((i & 1) != 0) {
            resolvedCall = resolvedCallWithRealDescriptor.resolvedCall;
        }
        if ((i & 2) != 0) {
            ktExpression = resolvedCallWithRealDescriptor.fakeContinuationExpression;
        }
        return resolvedCallWithRealDescriptor.copy(resolvedCall, ktExpression);
    }

    public final ResolvedCall<?> component1() {
        return this.resolvedCall;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KtExpression getFakeContinuationExpression() {
        return this.fakeContinuationExpression;
    }

    public final ResolvedCallWithRealDescriptor copy(ResolvedCall<?> resolvedCall, KtExpression fakeContinuationExpression) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(fakeContinuationExpression, "fakeContinuationExpression");
        return new ResolvedCallWithRealDescriptor(resolvedCall, fakeContinuationExpression);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResolvedCallWithRealDescriptor)) {
            return false;
        }
        ResolvedCallWithRealDescriptor resolvedCallWithRealDescriptor = (ResolvedCallWithRealDescriptor) other;
        return Intrinsics.areEqual(this.resolvedCall, resolvedCallWithRealDescriptor.resolvedCall) && Intrinsics.areEqual(this.fakeContinuationExpression, resolvedCallWithRealDescriptor.fakeContinuationExpression);
    }

    public int hashCode() {
        return (this.resolvedCall.hashCode() * 31) + this.fakeContinuationExpression.hashCode();
    }

    public String toString() {
        return "ResolvedCallWithRealDescriptor(resolvedCall=" + this.resolvedCall + ", fakeContinuationExpression=" + this.fakeContinuationExpression + ')';
    }

    public ResolvedCallWithRealDescriptor(ResolvedCall<?> resolvedCall, KtExpression fakeContinuationExpression) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(fakeContinuationExpression, "fakeContinuationExpression");
        this.resolvedCall = resolvedCall;
        this.fakeContinuationExpression = fakeContinuationExpression;
    }

    public final KtExpression getFakeContinuationExpression() {
        return this.fakeContinuationExpression;
    }

    public final ResolvedCall<?> getResolvedCall() {
        return this.resolvedCall;
    }
}
