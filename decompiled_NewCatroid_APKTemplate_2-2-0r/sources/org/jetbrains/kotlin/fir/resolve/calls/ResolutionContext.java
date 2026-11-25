package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponentsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;

/* JADX INFO: compiled from: ResolutionContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "bodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)V", "getBodyResolveComponents", "()Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "getBodyResolveContext", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "inferenceComponents", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "getInferenceComponents", "()Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "getTypeContext", "()Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolutionContext {
    private final BodyResolveComponents bodyResolveComponents;
    private final BodyResolveContext bodyResolveContext;
    private final FirSession session;

    public ResolutionContext(FirSession session, BodyResolveComponents bodyResolveComponents, BodyResolveContext bodyResolveContext) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(bodyResolveContext, "bodyResolveContext");
        this.session = session;
        this.bodyResolveComponents = bodyResolveComponents;
        this.bodyResolveContext = bodyResolveContext;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final BodyResolveComponents getBodyResolveComponents() {
        return this.bodyResolveComponents;
    }

    public final BodyResolveContext getBodyResolveContext() {
        return this.bodyResolveContext;
    }

    public final ConeInferenceContext getTypeContext() {
        return TypeComponentsKt.getTypeContext(this.session);
    }

    public final InferenceComponents getInferenceComponents() {
        return InferenceComponentsKt.getInferenceComponents(this.session);
    }

    public final ReturnTypeCalculator getReturnTypeCalculator() {
        return this.bodyResolveComponents.getReturnTypeCalculator();
    }
}
