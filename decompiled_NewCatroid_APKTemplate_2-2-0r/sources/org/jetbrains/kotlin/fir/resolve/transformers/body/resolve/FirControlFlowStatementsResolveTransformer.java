package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirTargetElement;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatusKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyExpressionBlock;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.FirRegularTowerDataContexts;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionModeKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSyntheticCallGenerator;
import org.jetbrains.kotlin.fir.resolve.transformers.FirWhenExhaustivenessTransformer;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirWhenSubjectImportingScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;

/* JADX INFO: compiled from: FirControlFlowStatementsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J(\u0010\u0017\u001a\u00020\u0011\"\b\b\u0000\u0010\u0018*\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\"\u001a\u00020\u00112\u0006\u0010#\u001a\u00020$2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020*2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020-2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u0002002\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\f\u00101\u001a\u000202*\u00020*H\u0002J\f\u00103\u001a\u00020**\u00020*H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirControlFlowStatementsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirPartialBodyResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "syntheticCallGenerator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "getSyntheticCallGenerator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSyntheticCallGenerator;", "whenExhaustivenessTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirWhenExhaustivenessTransformer;", "transformCatch", "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", PsiKeyword.CATCH, "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformDoWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "transformElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "transformJump", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "transformReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "transformThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "transformTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "transformWhenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "whenBranch", "transformWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "transformWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "transformWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "isOneBranch", "", "replaceReturnTypeIfNotExhaustive", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirControlFlowStatementsResolveTransformer extends FirPartialBodyResolveTransformer {
    private final FirWhenExhaustivenessTransformer whenExhaustivenessTransformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirControlFlowStatementsResolveTransformer(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.whenExhaustivenessTransformer = new FirWhenExhaustivenessTransformer(getTransformer().getComponents());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirSyntheticCallGenerator getSyntheticCallGenerator() {
        return getTransformer().getComponents().getSyntheticCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhileLoop(FirWhileLoop whileLoop, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        ResolutionMode.ContextIndependent contextIndependent = ResolutionMode.ContextIndependent.INSTANCE;
        FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterWhileLoop(whileLoop);
        FirWhileLoop firWhileLoopTransformCondition = whileLoop.transformCondition((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default((FirTypeRef) getSession().getBuiltinTypes().getBooleanType(), false, 2, (Object) null));
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitWhileLoopCondition(firWhileLoopTransformCondition);
        FirWhileLoop firWhileLoopTransformBlock = firWhileLoopTransformCondition.transformBlock((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), contextIndependent);
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitWhileLoop(firWhileLoopTransformBlock);
        return firWhileLoopTransformBlock.transformOtherChildren((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), contextIndependent);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDoWhileLoop(FirDoWhileLoop doWhileLoop, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        BodyResolveContext context = getTransformer().getContext();
        FirSession session = getSession();
        FirTowerDataContext towerDataContext = context.getTowerDataContext();
        try {
            context.addLocalScope(new FirLocalScope(session));
            ResolutionMode.ContextIndependent contextIndependent = ResolutionMode.ContextIndependent.INSTANCE;
            getComponents().getDataFlowAnalyzer().enterDoWhileLoop(doWhileLoop);
            FirExpressionsResolveTransformer expressionsTransformer = getTransformer().getExpressionsTransformer();
            if (expressionsTransformer != null) {
                expressionsTransformer.transformBlockInCurrentScope$resolve(doWhileLoop.getBlock(), contextIndependent);
            }
            getComponents().getDataFlowAnalyzer().enterDoWhileLoopCondition(doWhileLoop);
            FirDoWhileLoop firDoWhileLoopTransformCondition = doWhileLoop.transformCondition((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default((FirTypeRef) getSession().getBuiltinTypes().getBooleanType(), false, 2, (Object) null));
            getComponents().getDataFlowAnalyzer().exitDoWhileLoop(firDoWhileLoopTransformCondition);
            return firDoWhileLoopTransformCondition.transformOtherChildren((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), contextIndependent);
        } finally {
            context.replaceTowerDataContext(towerDataContext);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhenExpression(final FirWhenExpression whenExpression, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if ((whenExpression.getCalleeReference() instanceof FirResolvedNamedReference) && !(whenExpression.getTypeRef() instanceof FirImplicitTypeRef)) {
            return whenExpression;
        }
        Iterator<T> it = whenExpression.getAnnotations().iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(this, data);
        }
        getComponents().getDataFlowAnalyzer().enterWhenExpression(whenExpression);
        return (FirStatement) getTransformer().getContext().withWhenExpression(whenExpression, getSession(), new Function0<FirWhenExpression>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirControlFlowStatementsResolveTransformer.transformWhenExpression.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:52:0x00fc A[Catch: all -> 0x0173, TryCatch #0 {all -> 0x0173, blocks: (B:29:0x009b, B:31:0x00a6, B:33:0x00ac, B:35:0x00b2, B:37:0x00b6, B:38:0x00dd, B:40:0x00e1, B:44:0x00e9, B:49:0x00f3, B:51:0x00f9, B:53:0x0100, B:52:0x00fc, B:54:0x011e, B:56:0x012c, B:60:0x0148, B:59:0x0144, B:61:0x014e), top: B:69:0x009b }] */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final FirWhenExpression invoke() {
                ResolutionMode resolutionMode;
                ResolutionMode resolutionModeCopy$default;
                FirRegularClassSymbol firRegularClassSymbol;
                ConeClassLikeLookupTag lookupTag;
                FirTypeRef typeRef;
                ConeKotlinType coneType;
                FirWhenExpression firWhenExpressionTransformSubject = whenExpression.transformSubject(this.getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                FirExpression subject = firWhenExpressionTransformSubject.getSubject();
                ConeKotlinType coneKotlinTypeFullyExpandedType = (subject == null || (typeRef = subject.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null) ? null : TypeExpansionUtilsKt.fullyExpandedType(coneType, this.getSession());
                BodyResolveContext context = this.getTransformer().getContext();
                FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components = this.getTransformer().getComponents();
                FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
                ResolutionMode resolutionMode2 = data;
                FirSession session = components.getSession();
                boolean zSupportsFeature = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.ContextSensitiveEnumResolutionInWhen);
                if (zSupportsFeature) {
                    ConeClassLikeType coneClassLikeType = coneKotlinTypeFullyExpandedType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinTypeFullyExpandedType : null;
                    if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(lookupTag, session)) == null || ((FirRegularClass) firRegularClassSymbol.getFir()).getClassKind() != ClassKind.ENUM_CLASS) {
                        firRegularClassSymbol = null;
                    }
                    context.getWhenSubjectImportingScopes().add(firRegularClassSymbol != null ? new FirWhenSubjectImportingScope(firRegularClassSymbol.getClassId(), session, components.getScopeSession()) : null);
                }
                try {
                    boolean z = false;
                    if (!firWhenExpressionTransformSubject.getBranches().isEmpty()) {
                        if (firControlFlowStatementsResolveTransformer.isOneBranch(firWhenExpressionTransformSubject) && resolutionMode2.getForceFullCompletion() && !(resolutionMode2 instanceof ResolutionMode.WithExpectedType)) {
                            firWhenExpressionTransformSubject = firWhenExpressionTransformSubject.transformBranches(firControlFlowStatementsResolveTransformer.getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                            firWhenExpressionTransformSubject.replaceTypeRef(((FirWhenBranch) CollectionsKt.first((List) firWhenExpressionTransformSubject.getBranches())).getResult().getTypeRef());
                        } else {
                            ResolutionMode.WithExpectedType withExpectedType = resolutionMode2 instanceof ResolutionMode.WithExpectedType ? (ResolutionMode.WithExpectedType) resolutionMode2 : null;
                            if (withExpectedType != null) {
                                if (withExpectedType.getFromCast()) {
                                    withExpectedType = null;
                                }
                                if (withExpectedType != null && (resolutionModeCopy$default = ResolutionMode.WithExpectedType.copy$default(withExpectedType, false, false, 1, null)) != null) {
                                    resolutionMode = resolutionModeCopy$default;
                                } else {
                                    resolutionMode = ResolutionMode.ContextDependent.INSTANCE;
                                }
                            } else {
                                resolutionMode = ResolutionMode.ContextDependent.INSTANCE;
                            }
                            firWhenExpressionTransformSubject = firControlFlowStatementsResolveTransformer.getSyntheticCallGenerator().generateCalleeForWhenExpression(firWhenExpressionTransformSubject.transformBranches(firControlFlowStatementsResolveTransformer.getTransformer(), resolutionMode), firControlFlowStatementsResolveTransformer.getTransformer().getResolutionContext());
                            z = true;
                        }
                    }
                    Object objTransformSingle = FirTransformerUtilKt.transformSingle(firWhenExpressionTransformSubject, firControlFlowStatementsResolveTransformer.whenExhaustivenessTransformer, null);
                    if (z) {
                        objTransformSingle = (FirWhenExpression) firControlFlowStatementsResolveTransformer.getComponents().getCallCompleter().completeCall((FirResolvable) objTransformSingle, ExhaustivenessStatusKt.isProperlyExhaustive((FirWhenExpression) objTransformSingle) ? resolutionMode2 : ResolutionMode.ContextIndependent.INSTANCE);
                    }
                    firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitWhenExpression((FirWhenExpression) objTransformSingle, resolutionMode2.getForceFullCompletion());
                    return firControlFlowStatementsResolveTransformer.replaceReturnTypeIfNotExhaustive((FirWhenExpression) objTransformSingle);
                } finally {
                    if (zSupportsFeature) {
                        context.getWhenSubjectImportingScopes().removeLast();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirWhenExpression replaceReturnTypeIfNotExhaustive(FirWhenExpression firWhenExpression) {
        if (!ExhaustivenessStatusKt.isProperlyExhaustive(firWhenExpression)) {
            FirWhenExpression firWhenExpression2 = firWhenExpression;
            firWhenExpression2.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firWhenExpression2.getTypeRef(), getSession().getBuiltinTypes().getUnitType().getType(), null, 2, null));
        }
        return firWhenExpression;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isOneBranch(FirWhenExpression firWhenExpression) {
        if (firWhenExpression.getBranches().size() == 1) {
            return true;
        }
        if (firWhenExpression.getBranches().size() > 2) {
            return false;
        }
        FirWhenBranch firWhenBranch = (FirWhenBranch) CollectionsKt.last((List) firWhenExpression.getBranches());
        return firWhenBranch.getSource() != null && (firWhenBranch.getCondition() instanceof FirElseIfTrueCondition) && (firWhenBranch.getResult() instanceof FirEmptyExpressionBlock);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirWhenBranch transformWhenBranch(FirWhenBranch whenBranch, ResolutionMode data) {
        FirWhenBranch firWhenBranchTransformCondition;
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        Intrinsics.checkNotNullParameter(data, "data");
        FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterWhenBranchCondition(whenBranch);
        BodyResolveContext context = getTransformer().getContext();
        FirWhenSubjectImportingScope firWhenSubjectImportingScopeLastOrNull = context.getWhenSubjectImportingScopes().lastOrNull();
        if (firWhenSubjectImportingScopeLastOrNull == null) {
            firWhenBranchTransformCondition = whenBranch.transformCondition(getTransformer(), ResolutionModeKt.withExpectedType$default((FirTypeRef) getSession().getBuiltinTypes().getBooleanType(), false, 2, (Object) null));
        } else {
            FirRegularTowerDataContexts firRegularTowerDataContexts = new FirRegularTowerDataContexts(context.getTowerDataContext().addNonLocalScope(firWhenSubjectImportingScopeLastOrNull), null, null, null, null, null, null, null, 254, null);
            FirRegularTowerDataContexts regularTowerDataContexts = context.getRegularTowerDataContexts();
            context.setRegularTowerDataContexts(firRegularTowerDataContexts);
            try {
                firWhenBranchTransformCondition = whenBranch.transformCondition(getTransformer(), ResolutionModeKt.withExpectedType$default((FirTypeRef) getSession().getBuiltinTypes().getBooleanType(), false, 2, (Object) null));
                context.setRegularTowerDataContexts(regularTowerDataContexts);
            } catch (Throwable th) {
                context.setRegularTowerDataContexts(regularTowerDataContexts);
                throw th;
            }
        }
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitWhenBranchCondition(firWhenBranchTransformCondition);
        FirWhenBranch firWhenBranchTransformResult = firWhenBranchTransformCondition.transformResult(getTransformer(), data);
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitWhenBranchResult(firWhenBranchTransformResult);
        return firWhenBranchTransformResult;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, ResolutionMode data) {
        FirTypeRef returnTypeRef;
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirWhenExpression firWhenExpression = (FirWhenExpression) whenSubjectExpression.getWhenRef().getValue();
        FirExpression subject = firWhenExpression.getSubject();
        if (subject == null || (returnTypeRef = subject.getTypeRef()) == null) {
            FirVariable subjectVariable = firWhenExpression.getSubjectVariable();
            returnTypeRef = subjectVariable != null ? subjectVariable.getReturnTypeRef() : null;
        }
        if (returnTypeRef != null) {
            whenSubjectExpression.replaceTypeRef(returnTypeRef);
        }
        getComponents().getDataFlowAnalyzer().exitWhenSubjectExpression(whenSubjectExpression);
        return ResolveUtilsKt.transformWhenSubjectExpressionUsingSmartcastInfo(getTransformer().getComponents(), whenSubjectExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTryExpression(FirTryExpression tryExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if ((tryExpression.getCalleeReference() instanceof FirResolvedNamedReference) && !(tryExpression.getTypeRef() instanceof FirImplicitTypeRef)) {
            return tryExpression;
        }
        tryExpression.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterTryExpression(tryExpression);
        tryExpression.transformTryBlock(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitTryMainBlock();
        tryExpression.transformCatches(this, ResolutionMode.ContextDependent.INSTANCE);
        FirTryExpression firTryExpressionTransformFinallyBlock = (FirTryExpression) firControlFlowStatementsResolveTransformer.getComponents().getCallCompleter().completeCall(getSyntheticCallGenerator().generateCalleeForTryExpression(tryExpression, getTransformer().getResolutionContext()), data);
        if (firTryExpressionTransformFinallyBlock.getFinallyBlock() != null) {
            firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterFinallyBlock();
            firTryExpressionTransformFinallyBlock = firTryExpressionTransformFinallyBlock.transformFinallyBlock(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitFinallyBlock();
        }
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitTryExpression(data.getForceFullCompletion());
        return firTryExpressionTransformFinallyBlock;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirCatch transformCatch(FirCatch firCatch, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        Intrinsics.checkNotNullParameter(data, "data");
        FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterCatchClause(firCatch);
        firCatch.getParameter().transformReturnTypeRef((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        BodyResolveContext context = getTransformer().getContext();
        FirSession session = getSession();
        FirTowerDataContext towerDataContext = context.getTowerDataContext();
        try {
            context.addLocalScope(new FirLocalScope(session));
            firCatch.transformParameter(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            FirCatch firCatchTransformBlock = firCatch.transformBlock(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
            context.replaceTowerDataContext(towerDataContext);
            firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitCatchClause(firCatchTransformBlock);
            return firCatchTransformBlock;
        } catch (Throwable th) {
            context.replaceTowerDataContext(towerDataContext);
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirTargetElement> FirStatement transformJump(FirJump<E> jump, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(data, "data");
        FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterJump(jump);
        FirStatement firStatementTransformExpression = getTransformer().transformExpression((FirExpression) jump, data);
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitJump(jump);
        return firStatementTransformExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformReturnExpression(FirReturnExpression returnExpression, ResolutionMode data) {
        ResolutionMode.ContextIndependent withExpectedType;
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirFunction firFunction = (FirFunction) returnExpression.getTarget().getLabeledElement();
        FirTypeRef returnTypeRef = firFunction.getReturnTypeRef();
        if (getTransformer().getContext().getAnonymousFunctionsAnalyzedInDependentContext().contains(firFunction.getSymbol())) {
            withExpectedType = ResolutionMode.ContextDependent.INSTANCE;
        } else if (returnTypeRef instanceof FirResolvedTypeRef) {
            withExpectedType = new ResolutionMode.WithExpectedType((FirResolvedTypeRef) returnTypeRef, false, true, false, false, false, 58, null);
        } else {
            withExpectedType = ResolutionMode.ContextIndependent.INSTANCE;
        }
        return transformJump((FirJump) returnExpression, withExpectedType);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformThrowExpression(FirThrowExpression throwExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        throwExpression.replaceTypeRef((FirTypeRef) throwExpression.getTypeRef().transform(getTransformer(), data));
        throwExpression.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        throwExpression.transformException(getTransformer(), ResolutionModeKt.withExpectedType$default((FirTypeRef) getSession().getBuiltinTypes().getThrowableType(), false, 2, (Object) null));
        getComponents().getDataFlowAnalyzer().exitThrowExceptionNode(throwExpression);
        return throwExpression;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x004a  */
    /* JADX WARN: Code duplicated, block: B:70:0x0147  */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformElvisExpression(FirElvisExpression elvisExpression, ResolutionMode data) {
        ConeKotlinType type;
        ConeKotlinType coneKotlinTypeWithNullability$default;
        ResolutionMode resolutionModeWithExpectedTypeNullable$default;
        boolean z;
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (elvisExpression.getCalleeReference() instanceof FirResolvedNamedReference) {
            return elvisExpression;
        }
        elvisExpression.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        if ((data instanceof ResolutionMode.WithExpectedType) && !data.getForceFullCompletion()) {
            data = null;
        }
        if (data == null) {
            data = ResolutionMode.ContextDependent.INSTANCE;
        }
        FirTypeRef expectedType = getExpectedType(data);
        if (expectedType == null) {
            type = null;
        } else {
            FirResolvedTypeRef firResolvedTypeRef = expectedType instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) expectedType : null;
            type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
        }
        ResolutionMode.WithExpectedType withExpectedType = data instanceof ResolutionMode.WithExpectedType ? (ResolutionMode.WithExpectedType) data : null;
        boolean z2 = withExpectedType != null && withExpectedType.getMayBeCoercionToUnitApplied();
        if (z2 && type != null && CallableReferenceResolutionKt.isUnitOrFlexibleUnit(type)) {
            resolutionModeWithExpectedTypeNullable$default = ResolutionModeKt.withExpectedType(type, true);
        } else {
            if (type != null) {
                coneKotlinTypeWithNullability$default = TypeUtilsKt.withNullability$default(type, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(getSession()), null, false, 12, null);
            } else {
                coneKotlinTypeWithNullability$default = null;
            }
            resolutionModeWithExpectedTypeNullable$default = ResolutionModeKt.withExpectedTypeNullable$default(coneKotlinTypeWithNullability$default, false, 2, null);
        }
        FirControlFlowStatementsResolveTransformer firControlFlowStatementsResolveTransformer = this;
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().enterElvis(elvisExpression);
        elvisExpression.transformLhs(getTransformer(), resolutionModeWithExpectedTypeNullable$default);
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitElvisLhs(elvisExpression);
        elvisExpression.transformRhs(getTransformer(), ResolutionModeKt.withExpectedTypeNullable(type, z2));
        FirElvisExpression firElvisExpression = (FirElvisExpression) firControlFlowStatementsResolveTransformer.getComponents().getCallCompleter().completeCall(getSyntheticCallGenerator().generateCalleeForElvisExpression(elvisExpression, getTransformer().getResolutionContext()), data);
        FirTypeRef typeRef = firElvisExpression.getRhs().getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef2 = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
        if (!(type2 instanceof ConeKotlinType)) {
            type2 = null;
        }
        if (type2 == null || !ConeBuiltinTypeUtilsKt.isNothing(type2)) {
            z = false;
        } else {
            FirTypeRef typeRef2 = firElvisExpression.getLhs().getTypeRef();
            FirResolvedTypeRef firResolvedTypeRef3 = typeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef2 : null;
            ConeKotlinType type3 = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
            if (!(type3 instanceof ConeKotlinType)) {
                type3 = null;
            }
            if (type3 != null) {
                firElvisExpression.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firElvisExpression.getTypeRef(), TypeUtilsKt.convertToNonRawVersion(TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default(type3, TypeComponentsKt.getTypeContext(getSession()), false, 2, null)), null, 2, null));
                z = true;
            } else {
                z = false;
            }
        }
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        FirTypeRef typeRef3 = firElvisExpression.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef4 = typeRef3 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef3 : null;
        ConeKotlinType type4 = firResolvedTypeRef4 != null ? firResolvedTypeRef4.getType() : null;
        if (!(type4 instanceof ConeKotlinType)) {
            type4 = null;
        }
        if (type4 != null && typeContext.isNullableType(type4)) {
            FirTypeRef typeRef4 = firElvisExpression.getRhs().getTypeRef();
            FirResolvedTypeRef firResolvedTypeRef5 = typeRef4 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef4 : null;
            ConeKotlinType type5 = firResolvedTypeRef5 != null ? firResolvedTypeRef5.getType() : null;
            if (!(type5 instanceof ConeKotlinType)) {
                type5 = null;
            }
            if (type5 != null && !typeContext.isNullableType(type5)) {
                firElvisExpression.replaceTypeRef(TypeUtilsKt.withReplacedConeType$default(firElvisExpression.getTypeRef(), TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default(FirTypeUtilsKt.getConeType(firElvisExpression.getTypeRef()), TypeComponentsKt.getTypeContext(typeContext.get$session()), false, 2, null), null, 2, null));
            }
        }
        firControlFlowStatementsResolveTransformer.getComponents().getDataFlowAnalyzer().exitElvis(elvisExpression, z, data.getForceFullCompletion());
        return firElvisExpression;
    }
}
