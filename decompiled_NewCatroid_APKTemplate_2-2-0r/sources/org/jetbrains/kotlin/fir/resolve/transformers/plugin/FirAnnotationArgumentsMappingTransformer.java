package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirResolveContextCollector;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: FirAnnotationArgumentsMappingTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ%\u0010\"\u001a\u0002H#\"\u0004\b\u0000\u0010#2\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0%H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010&R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\u00020\u00128F¢\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR$\u0010\u001b\u001a\u00020\u001c8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001b\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirAnnotationArgumentsMappingTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)V", "declarationsResolveTransformerForAnnotationArgumentsMapping", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirDeclarationsResolveTransformerForAnnotationArgumentsMapping;", "declarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "getDeclarationsTransformer$annotations", "()V", "getDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "expressionsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getExpressionsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "isInsideAnnotationArgument", "", "isInsideAnnotationArgument$annotations", "()Z", "setInsideAnnotationArgument", "(Z)V", "usualDeclarationTransformer", "insideAnnotationArgument", "R", "action", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirAnnotationArgumentsMappingTransformer extends FirAbstractBodyResolveTransformerDispatcher {
    private final FirDeclarationsResolveTransformerForAnnotationArgumentsMapping declarationsResolveTransformerForAnnotationArgumentsMapping;
    private final FirExpressionsResolveTransformer expressionsTransformer;
    private boolean isInsideAnnotationArgument;
    private final FirDeclarationsResolveTransformer usualDeclarationTransformer;

    public static /* synthetic */ void getDeclarationsTransformer$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void isInsideAnnotationArgument$annotations() {
    }

    public /* synthetic */ FirAnnotationArgumentsMappingTransformer(FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase, BodyResolveContext bodyResolveContext, ReturnTypeCalculator returnTypeCalculator, FirResolveContextCollector firResolveContextCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, firResolvePhase, (i & 8) != 0 ? null : bodyResolveContext, (i & 16) != 0 ? ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault() : returnTypeCalculator, (i & 32) != 0 ? null : firResolveContextCollector);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAnnotationArgumentsMappingTransformer(FirSession session, ScopeSession scopeSession, FirResolvePhase resolvePhase, BodyResolveContext bodyResolveContext, ReturnTypeCalculator returnTypeCalculator, FirResolveContextCollector firResolveContextCollector) {
        super(session, resolvePhase, false, scopeSession, returnTypeCalculator, bodyResolveContext, firResolveContextCollector);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        this.expressionsTransformer = new FirExpressionTransformerForAnnotationArgumentsMapping(this);
        FirAnnotationArgumentsMappingTransformer firAnnotationArgumentsMappingTransformer = this;
        this.declarationsResolveTransformerForAnnotationArgumentsMapping = new FirDeclarationsResolveTransformerForAnnotationArgumentsMapping(firAnnotationArgumentsMappingTransformer);
        this.usualDeclarationTransformer = new FirDeclarationsResolveTransformer(firAnnotationArgumentsMappingTransformer);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
    public final FirExpressionsResolveTransformer getExpressionsTransformer() {
        return this.expressionsTransformer;
    }

    /* JADX INFO: renamed from: isInsideAnnotationArgument, reason: from getter */
    public final boolean getIsInsideAnnotationArgument() {
        return this.isInsideAnnotationArgument;
    }

    public final void setInsideAnnotationArgument(boolean z) {
        this.isInsideAnnotationArgument = z;
    }

    public final <R> R insideAnnotationArgument(Function0<? extends R> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        boolean isInsideAnnotationArgument = getIsInsideAnnotationArgument();
        setInsideAnnotationArgument(true);
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setInsideAnnotationArgument(isInsideAnnotationArgument);
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
    public final FirDeclarationsResolveTransformer getDeclarationsTransformer() {
        return this.isInsideAnnotationArgument ? this.usualDeclarationTransformer : this.declarationsResolveTransformerForAnnotationArgumentsMapping;
    }
}
