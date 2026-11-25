package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096@¢\u0006\u0002\u0010\u000fJL\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00130\u00122\u0016\u0010\u0014\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u00170\u0015H\u0002J,\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u00132\f\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u001d\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00040\u001e2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 H\u0002J\"\u0010\u001d\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00040\u001e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0013*\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040\u0013*\u00020#2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckDslScopeViolation;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "dslMarkerClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkImpl", "dslMarkersProvider", "Lkotlin/Function0;", "", "isImplicitReceiverMatching", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "", "getDslMarkersOfImplicitReceiver", "boundSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "collectDslMarkerAnnotations", "", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getDslMarkersOfThisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckDslScopeViolation extends ResolutionStage {
    public static final CheckDslScopeViolation INSTANCE = new CheckDslScopeViolation();
    private static final ClassId dslMarkerClassId;

    private CheckDslScopeViolation() {
    }

    static {
        ClassId classIdFromString = ClassId.fromString("kotlin/DslMarker");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        dslMarkerClassId = classIdFromString;
    }

    private static final void check$checkReceiver(Candidate candidate, CheckerSink checkerSink, final ResolutionContext resolutionContext, final FirExpression firExpression) {
        FirReference reference = firExpression != null ? FirExpressionUtilKt.toReference(firExpression) : null;
        final FirThisReference firThisReference = reference instanceof FirThisReference ? (FirThisReference) reference : null;
        if (firThisReference != null && firThisReference.getIsImplicit()) {
            INSTANCE.checkImpl(candidate, checkerSink, resolutionContext, new Function0<Set<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckDslScopeViolation$check$checkReceiver$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends ClassId> invoke() {
                    return CheckDslScopeViolation.INSTANCE.getDslMarkersOfImplicitReceiver(firThisReference.getBoundSymbol(), FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), resolutionContext);
                }
            }, new Function1<ImplicitReceiverValue<?>, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckDslScopeViolation$check$checkReceiver$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ImplicitReceiverValue<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(it.getReceiverExpression(), firExpression));
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, final ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        Set<FirExpression> setKeySet;
        check$checkReceiver(candidate, checkerSink, resolutionContext, candidate.getDispatchReceiver());
        check$checkReceiver(candidate, checkerSink, resolutionContext, candidate.getChosenExtensionReceiver());
        FirExpression dispatchReceiver = candidate.getDispatchReceiver();
        if (dispatchReceiver != null && (typeRef = dispatchReceiver.getTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(typeRef)) != null && (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneType, resolutionContext.getSession())) != null && FunctionalTypeUtilsKt.isSomeFunctionType(coneKotlinTypeFullyExpandedType, resolutionContext.getSession())) {
            FirBasedSymbol<?> symbol = candidate.getSymbol();
            FirNamedFunctionSymbol firNamedFunctionSymbol = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
            if (Intrinsics.areEqual(firNamedFunctionSymbol != null ? firNamedFunctionSymbol.getName() : null, OperatorNameConventions.INVOKE)) {
                LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
                FirExpression firExpression = (argumentMapping == null || (setKeySet = argumentMapping.keySet()) == null) ? null : (FirExpression) CollectionsKt.firstOrNull(setKeySet);
                final FirThisReceiverExpression firThisReceiverExpression = firExpression instanceof FirThisReceiverExpression ? (FirThisReceiverExpression) firExpression : null;
                if (firThisReceiverExpression != null && firThisReceiverExpression.getIsImplicit()) {
                    checkImpl(candidate, checkerSink, resolutionContext, new Function0<Set<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckDslScopeViolation.check.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Set<? extends ClassId> invoke() {
                            return CheckDslScopeViolation.INSTANCE.getDslMarkersOfThisReceiverExpression(firThisReceiverExpression, resolutionContext);
                        }
                    }, new Function1<ImplicitReceiverValue<?>, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckDslScopeViolation.check.3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(ImplicitReceiverValue<?> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return Boolean.valueOf(Intrinsics.areEqual(it.getBoundSymbol(), firThisReceiverExpression.getCalleeReference().getBoundSymbol()));
                        }
                    });
                }
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }

    private final void checkImpl(Candidate candidate, CheckerSink sink, ResolutionContext context, Function0<? extends Set<ClassId>> dslMarkersProvider, Function1<? super ImplicitReceiverValue<?>, Boolean> isImplicitReceiverMatching) {
        Iterator<ImplicitReceiverValue<?>> it = context.getBodyResolveContext().getImplicitReceiverStack().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            ImplicitReceiverValue<?> next = it.next();
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (isImplicitReceiverMatching.invoke(next).booleanValue()) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        List listDrop = CollectionsKt.drop(context.getBodyResolveContext().getImplicitReceiverStack(), i + 1);
        if (listDrop.isEmpty()) {
            return;
        }
        Set<ClassId> setInvoke = dslMarkersProvider.invoke();
        if (setInvoke.isEmpty()) {
            return;
        }
        List list = listDrop;
        if ((list instanceof Collection) && list.isEmpty()) {
            return;
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            Set<ClassId> dslMarkersOfImplicitReceiver = INSTANCE.getDslMarkersOfImplicitReceiver((ImplicitReceiverValue) it2.next(), context);
            if (!(dslMarkersOfImplicitReceiver instanceof Collection) || !dslMarkersOfImplicitReceiver.isEmpty()) {
                Iterator<T> it3 = dslMarkersOfImplicitReceiver.iterator();
                while (it3.hasNext()) {
                    if (setInvoke.contains((ClassId) it3.next())) {
                        sink.reportDiagnostic(new DslScopeViolation(candidate.getSymbol()));
                        return;
                    }
                }
            }
        }
    }

    private final Set<ClassId> getDslMarkersOfImplicitReceiver(ImplicitReceiverValue<?> implicitReceiverValue, ResolutionContext resolutionContext) {
        return INSTANCE.getDslMarkersOfImplicitReceiver(implicitReceiverValue.getBoundSymbol(), implicitReceiverValue.getType(), resolutionContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final Set<ClassId> getDslMarkersOfImplicitReceiver(FirBasedSymbol<?> boundSymbol, ConeKotlinType type, ResolutionContext context) {
        FirAnonymousFunction firAnonymousFunction;
        ConeKotlinType matchingParameterFunctionType;
        ConeTypeProjection coneTypeProjection;
        ConeKotlinType type2;
        Set<ClassId> setCreateSetBuilder = SetsKt.createSetBuilder();
        FirAnonymousFunctionSymbol firAnonymousFunctionSymbol = boundSymbol instanceof FirAnonymousFunctionSymbol ? (FirAnonymousFunctionSymbol) boundSymbol : null;
        if (firAnonymousFunctionSymbol != null && (firAnonymousFunction = (FirAnonymousFunction) firAnonymousFunctionSymbol.getFir()) != null && (matchingParameterFunctionType = ClassMembersKt.getMatchingParameterFunctionType(firAnonymousFunction)) != null) {
            CheckDslScopeViolation checkDslScopeViolation = INSTANCE;
            checkDslScopeViolation.collectDslMarkerAnnotations(setCreateSetBuilder, context, CustomAnnotationTypeAttributeKt.getCustomAnnotations(matchingParameterFunctionType.getAttributes()));
            if (matchingParameterFunctionType.getAttributes().contains(CompilerConeAttributes.ExtensionFunctionType.INSTANCE) && (coneTypeProjection = (ConeTypeProjection) ArraysKt.firstOrNull(matchingParameterFunctionType.getTypeArguments())) != null && (type2 = ConeTypeProjectionKt.getType(coneTypeProjection)) != null) {
                checkDslScopeViolation.collectDslMarkerAnnotations(setCreateSetBuilder, context, type2);
            }
        }
        INSTANCE.collectDslMarkerAnnotations(setCreateSetBuilder, context, type);
        return SetsKt.build(setCreateSetBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<ClassId> getDslMarkersOfThisReceiverExpression(FirThisReceiverExpression firThisReceiverExpression, ResolutionContext resolutionContext) {
        Set<ClassId> setCreateSetBuilder = SetsKt.createSetBuilder();
        INSTANCE.collectDslMarkerAnnotations(setCreateSetBuilder, resolutionContext, FirTypeUtilsKt.getConeType(firThisReceiverExpression.getTypeRef()));
        return SetsKt.build(setCreateSetBuilder);
    }

    private final void collectDslMarkerAnnotations(Set<ClassId> set, ResolutionContext resolutionContext, ConeKotlinType coneKotlinType) {
        ConeClassLikeType coneClassLikeType;
        FirClassLikeSymbol<?> symbol;
        ConeClassLikeType coneClassLikeTypeDirectExpansionType$default;
        List<ConeKotlinType> supertypes;
        collectDslMarkerAnnotations(set, resolutionContext, CustomAnnotationTypeAttributeKt.getCustomAnnotations(coneKotlinType.getAttributes()));
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            collectDslMarkerAnnotations(set, resolutionContext, coneFlexibleType.getLowerBound());
            collectDslMarkerAnnotations(set, resolutionContext, coneFlexibleType.getUpperBound());
            return;
        }
        if (coneKotlinType instanceof ConeCapturedType) {
            ConeCapturedType coneCapturedType = (ConeCapturedType) coneKotlinType;
            if (coneCapturedType.getConstructor().getProjection().getKind() != ProjectionKind.OUT || (supertypes = coneCapturedType.getConstructor().getSupertypes()) == null) {
                return;
            }
            Iterator<T> it = supertypes.iterator();
            while (it.hasNext()) {
                INSTANCE.collectDslMarkerAnnotations(set, resolutionContext, (ConeKotlinType) it.next());
            }
            return;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            collectDslMarkerAnnotations(set, resolutionContext, ((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
            return;
        }
        if (!(coneKotlinType instanceof ConeIntersectionType)) {
            if (!(coneKotlinType instanceof ConeClassLikeType) || (symbol = TypeUtilsKt.toSymbol((coneClassLikeType = (ConeClassLikeType) coneKotlinType), resolutionContext.getSession())) == null) {
                return;
            }
            collectDslMarkerAnnotations(set, resolutionContext, symbol.getResolvedAnnotationsWithClassIds());
            if (symbol instanceof FirClassSymbol) {
                Iterator<ConeKotlinType> it2 = ((FirClassSymbol) symbol).getResolvedSuperTypes().iterator();
                while (it2.hasNext()) {
                    collectDslMarkerAnnotations(set, resolutionContext, it2.next());
                }
                return;
            } else {
                if (!(symbol instanceof FirTypeAliasSymbol) || (coneClassLikeTypeDirectExpansionType$default = TypeExpansionUtilsKt.directExpansionType$default(coneClassLikeType, resolutionContext.getSession(), null, 2, null)) == null) {
                    return;
                }
                INSTANCE.collectDslMarkerAnnotations(set, resolutionContext, coneClassLikeTypeDirectExpansionType$default);
                return;
            }
        }
        Iterator<T> it3 = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes().iterator();
        while (it3.hasNext()) {
            INSTANCE.collectDslMarkerAnnotations(set, resolutionContext, (ConeKotlinType) it3.next());
        }
    }

    private final void collectDslMarkerAnnotations(Set<ClassId> set, ResolutionContext resolutionContext, Collection<? extends FirAnnotation> collection) {
        Iterator<? extends FirAnnotation> it = collection.iterator();
        while (it.hasNext()) {
            FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(it.next().getAnnotationTypeRef()), resolutionContext.getSession()), resolutionContext.getSession());
            FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
            if (firClassSymbol != null && FirAnnotationUtilsKt.hasAnnotation(firClassSymbol, dslMarkerClassId, resolutionContext.getSession())) {
                set.add(firClassSymbol.getClassId());
            }
        }
    }
}
