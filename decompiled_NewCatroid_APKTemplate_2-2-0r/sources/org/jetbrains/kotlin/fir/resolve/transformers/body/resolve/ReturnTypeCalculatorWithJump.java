package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.resolve.FirRegularTowerDataContexts;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideSubstitution;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculatorKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001:\u0001-B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u001a\b\u0002\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\fJ\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\bH\u0002J\u0010\u0010)\u001a\u00020'2\u0006\u0010(\u001a\u00020\bH\u0014J\u0010\u0010*\u001a\u00020+2\u0006\u0010(\u001a\u00020\bH\u0002J\u0010\u0010,\u001a\u00020'2\u0006\u0010(\u001a\u00020\bH\u0016R#\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0017\u001a\u0004\u0018\u00010\u00162\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016@FX\u0086\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ReturnTypeCalculatorWithJump;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "implicitBodyResolveComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;", "designationMapForLocalClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "nonLocalDeclarationResolver", "(Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;Ljava/util/Map;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ReturnTypeCalculatorWithJump;)V", "getDesignationMapForLocalClasses", "()Ljava/util/Map;", "fakeOverrideTypeCalculator", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "getFakeOverrideTypeCalculator", "()Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "getImplicitBodyResolveComputationSession", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;", "context", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "outerBodyResolveContext", "getOuterBodyResolveContext$annotations", "()V", "getOuterBodyResolveContext", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "setOuterBodyResolveContext", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)V", "outerTowerDataContexts", "Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;", "getOuterTowerDataContexts", "()Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;", "setOuterTowerDataContexts", "(Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;)V", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "computeReturnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "declaration", "resolveDeclaration", "resolvedToContractsIfNecessary", "", "tryCalculateReturnTypeOrNull", "FakeOverrideTypeCalculatorWithJump", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ReturnTypeCalculatorWithJump extends ReturnTypeCalculator {
    private final Map<FirCallableDeclaration, List<FirClassLikeDeclaration>> designationMapForLocalClasses;
    private final FakeOverrideTypeCalculator fakeOverrideTypeCalculator;
    private final ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession;
    private final ReturnTypeCalculatorWithJump nonLocalDeclarationResolver;
    private BodyResolveContext outerBodyResolveContext;
    private FirRegularTowerDataContexts outerTowerDataContexts;
    private final ScopeSession scopeSession;

    public static /* synthetic */ void getOuterBodyResolveContext$annotations() {
    }

    protected final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final ImplicitBodyResolveComputationSession getImplicitBodyResolveComputationSession() {
        return this.implicitBodyResolveComputationSession;
    }

    public /* synthetic */ ReturnTypeCalculatorWithJump(ScopeSession scopeSession, ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession, Map map, ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scopeSession, implicitBodyResolveComputationSession, (i & 4) != 0 ? MapsKt.emptyMap() : map, (i & 8) != 0 ? null : returnTypeCalculatorWithJump);
    }

    public final Map<FirCallableDeclaration, List<FirClassLikeDeclaration>> getDesignationMapForLocalClasses() {
        return this.designationMapForLocalClasses;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ReturnTypeCalculatorWithJump(ScopeSession scopeSession, ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession, Map<FirCallableDeclaration, ? extends List<? extends FirClassLikeDeclaration>> designationMapForLocalClasses, ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump) {
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(implicitBodyResolveComputationSession, "implicitBodyResolveComputationSession");
        Intrinsics.checkNotNullParameter(designationMapForLocalClasses, "designationMapForLocalClasses");
        this.scopeSession = scopeSession;
        this.implicitBodyResolveComputationSession = implicitBodyResolveComputationSession;
        this.designationMapForLocalClasses = designationMapForLocalClasses;
        this.nonLocalDeclarationResolver = returnTypeCalculatorWithJump;
        this.fakeOverrideTypeCalculator = new FakeOverrideTypeCalculatorWithJump();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator
    public FakeOverrideTypeCalculator getFakeOverrideTypeCalculator() {
        return this.fakeOverrideTypeCalculator;
    }

    public final BodyResolveContext getOuterBodyResolveContext() {
        return this.outerBodyResolveContext;
    }

    public final void setOuterBodyResolveContext(BodyResolveContext bodyResolveContext) {
        this.outerBodyResolveContext = bodyResolveContext;
        this.outerTowerDataContexts = bodyResolveContext != null ? bodyResolveContext.getRegularTowerDataContexts() : null;
    }

    public final FirRegularTowerDataContexts getOuterTowerDataContexts() {
        return this.outerTowerDataContexts;
    }

    public final void setOuterTowerDataContexts(FirRegularTowerDataContexts firRegularTowerDataContexts) {
        this.outerTowerDataContexts = firRegularTowerDataContexts;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator
    public FirResolvedTypeRef tryCalculateReturnTypeOrNull(FirCallableDeclaration declaration) {
        List<FirValueParameter> valueParameters;
        FirValueParameter firValueParameter;
        FirNamedFunctionSymbol originalForWrappedIntegerOperator;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (Intrinsics.areEqual(declaration.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
            return ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault().tryCalculateReturnType(declaration);
        }
        if ((declaration instanceof FirValueParameter) && (declaration.getReturnTypeRef() instanceof FirImplicitTypeRef)) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Unsupported: implicit VP type", null, 2, null));
            declaration.replaceReturnTypeRef(firErrorTypeRefBuilder.mo6809build());
        }
        if ((declaration instanceof FirSimpleFunction) && (originalForWrappedIntegerOperator = FirIntegerConstantOperatorScopeKt.getOriginalForWrappedIntegerOperator((FirSimpleFunction) declaration)) != null) {
            tryCalculateReturnTypeOrNull((FirCallableDeclaration) originalForWrappedIntegerOperator.getFir());
        }
        resolvedToContractsIfNecessary(declaration);
        FirTypeRef returnTypeRef = declaration.getReturnTypeRef();
        if (returnTypeRef instanceof FirResolvedTypeRef) {
            return (FirResolvedTypeRef) returnTypeRef;
        }
        if (declaration instanceof FirSyntheticProperty) {
            return tryCalculateReturnType(((FirSyntheticProperty) declaration).getGetter().getDelegate());
        }
        DelegatedWrapperData delegatedWrapperData = ClassMembersKt.getDelegatedWrapperData(declaration);
        FirCallableDeclaration wrapped = delegatedWrapperData != null ? delegatedWrapperData.getWrapped() : null;
        if (wrapped != null) {
            FirResolvedTypeRef firResolvedTypeRefTryCalculateReturnType = tryCalculateReturnType(wrapped);
            if (declaration.getReturnTypeRef() instanceof FirImplicitTypeRef) {
                declaration.replaceReturnTypeRef(firResolvedTypeRefTryCalculateReturnType);
            }
            return firResolvedTypeRefTryCalculateReturnType;
        }
        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(declaration)) {
            FakeOverrideSubstitution fakeOverrideSubstitution = FakeOverrideTypeCalculatorKt.getFakeOverrideSubstitution(declaration.getAttributes());
            if (fakeOverrideSubstitution == null) {
                FirTypeRef returnTypeRef2 = declaration.getReturnTypeRef();
                Intrinsics.checkNotNull(returnTypeRef2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
                return (FirResolvedTypeRef) returnTypeRef2;
            }
            synchronized (fakeOverrideSubstitution) {
                if (FakeOverrideTypeCalculatorKt.getFakeOverrideSubstitution(declaration.getAttributes()) == null) {
                    FirTypeRef returnTypeRef3 = declaration.getReturnTypeRef();
                    Intrinsics.checkNotNull(returnTypeRef3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
                    return (FirResolvedTypeRef) returnTypeRef3;
                }
                ConeSubstitutor substitutor = fakeOverrideSubstitution.getSubstitutor();
                FirDeclaration fir = fakeOverrideSubstitution.component2().getFir();
                Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
                FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(declaration.getReturnTypeRef(), substitutor.substituteOrSelf(tryCalculateReturnType((FirCallableDeclaration) fir).getType()), null, 2, null);
                declaration.replaceReturnTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
                if (declaration instanceof FirProperty) {
                    FirPropertyAccessor getter = ((FirProperty) declaration).getGetter();
                    if (getter != null) {
                        getter.replaceReturnTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
                    }
                    FirPropertyAccessor setter = ((FirProperty) declaration).getSetter();
                    if (setter != null && (valueParameters = setter.getValueParameters()) != null && (firValueParameter = (FirValueParameter) CollectionsKt.firstOrNull((List) valueParameters)) != null) {
                        firValueParameter.replaceReturnTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
                    }
                }
                FakeOverrideTypeCalculatorKt.setFakeOverrideSubstitution(declaration.getAttributes(), null);
                return firResolvedTypeRefResolvedTypeFromPrototype$default;
            }
        }
        ImplicitBodyResolveComputationStatus status$resolve = this.implicitBodyResolveComputationSession.getStatus$resolve(declaration.getSymbol());
        if (status$resolve instanceof ImplicitBodyResolveComputationStatus.Computed) {
            return ((ImplicitBodyResolveComputationStatus.Computed) status$resolve).getResolvedTypeRef();
        }
        if (!(status$resolve instanceof ImplicitBodyResolveComputationStatus.Computing)) {
            return computeReturnTypeRef(declaration);
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder2.setDiagnostic(new ConeSimpleDiagnostic("cycle", DiagnosticKind.RecursionInImplicitTypes));
        return firErrorTypeRefBuilder2.mo6809build();
    }

    private final void resolvedToContractsIfNecessary(FirCallableDeclaration declaration) {
        if ((!(declaration instanceof FirProperty) || ((FirProperty) declaration).getIsLocal()) && (!(declaration instanceof FirSimpleFunction) || Intrinsics.areEqual(((FirSimpleFunction) declaration).getStatus().getVisibility(), Visibilities.Local.INSTANCE))) {
            return;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(declaration, FirResolvePhase.CONTRACTS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirResolvedTypeRef computeReturnTypeRef(FirCallableDeclaration declaration) {
        FirTypeRef returnTypeRef = declaration.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        if (firResolvedTypeRef != null) {
            return firResolvedTypeRef;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = declaration.getSymbol();
        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(symbol)) {
            throw new IllegalArgumentException(("fakeOverrideSubstitution was not calculated for substitution or intersection override: " + symbol + " with " + declaration.getReturnTypeRef()).toString());
        }
        return resolveDeclaration(declaration);
    }

    protected FirResolvedTypeRef resolveDeclaration(FirCallableDeclaration declaration) {
        Pair pair;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirSession session = declaration.getModuleData().getSession();
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = declaration.getSymbol();
        if (this.designationMapForLocalClasses.containsKey(declaration)) {
            pair = TuplesKt.to(MapsKt.getValue(this.designationMapForLocalClasses, declaration), this.outerBodyResolveContext);
        } else {
            ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump = this.nonLocalDeclarationResolver;
            if (returnTypeCalculatorWithJump != null) {
                return returnTypeCalculatorWithJump.resolveDeclaration(declaration);
            }
            FirProvider firProvider = FirProviderKt.getFirProvider(session);
            FirFile firCallableContainerFile = firProvider.getFirCallableContainerFile(symbol);
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(symbol);
            Sequence sequenceGenerateSequence = SequencesKt.generateSequence(coneClassLikeLookupTagContainingClassLookupTag != null ? coneClassLikeLookupTagContainingClassLookupTag.getClassId() : null, new Function1<ClassId, ClassId>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.ReturnTypeCalculatorWithJump$resolveDeclaration$outerClasses$1
                @Override // kotlin.jvm.functions.Function1
                public final ClassId invoke(ClassId classId) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    return classId.getOuterClassId();
                }
            });
            ArrayList arrayList = new ArrayList();
            Iterator it = sequenceGenerateSequence.iterator();
            while (it.hasNext()) {
                arrayList.add(firProvider.getFirClassifierByFqName((ClassId) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            if (firCallableContainerFile != null) {
                ArrayList arrayList3 = arrayList2;
                if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                    Iterator it2 = arrayList3.iterator();
                    while (it2.hasNext()) {
                        if (((FirClassLikeDeclaration) it2.next()) == null) {
                        }
                    }
                }
                pair = TuplesKt.to(CollectionsKt.plus((Collection) CollectionsKt.listOf(firCallableContainerFile), (Iterable) CollectionsKt.asReversed(CollectionsKt.filterNotNull(arrayList3))), null);
            }
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Cannot calculate return type (local class/object?)", DiagnosticKind.InferenceError));
            return firErrorTypeRefBuilder.mo6809build();
        }
        List list = (List) pair.component1();
        BodyResolveContext bodyResolveContext = (BodyResolveContext) pair.component2();
        FirRegularTowerDataContexts regularTowerDataContexts = bodyResolveContext != null ? bodyResolveContext.getRegularTowerDataContexts() : null;
        if (bodyResolveContext != null) {
            FirRegularTowerDataContexts firRegularTowerDataContexts = this.outerTowerDataContexts;
            Intrinsics.checkNotNull(firRegularTowerDataContexts);
            bodyResolveContext.setRegularTowerDataContexts(firRegularTowerDataContexts);
        }
        FirDesignatedBodyResolveTransformerForReturnTypeCalculator firDesignatedBodyResolveTransformerForReturnTypeCalculator = new FirDesignatedBodyResolveTransformerForReturnTypeCalculator(CollectionsKt.plus((Collection<? extends FirCallableDeclaration>) CollectionsKt.drop(list, 1), declaration).iterator(), session, this.scopeSession, this.implicitBodyResolveComputationSession, this, bodyResolveContext);
        ((FirDeclaration) CollectionsKt.first(list)).transform(firDesignatedBodyResolveTransformerForReturnTypeCalculator, ResolutionMode.ContextDependent.INSTANCE);
        FirElement lastResult = firDesignatedBodyResolveTransformerForReturnTypeCalculator.getLastResult();
        FirCallableDeclaration firCallableDeclaration = lastResult instanceof FirCallableDeclaration ? (FirCallableDeclaration) lastResult : null;
        if (firCallableDeclaration == null) {
            StringBuilder sb = new StringBuilder("Unexpected lastResult: ");
            FirElement lastResult2 = firDesignatedBodyResolveTransformerForReturnTypeCalculator.getLastResult();
            throw new IllegalStateException(sb.append(lastResult2 != null ? UtilsKt.render(lastResult2) : null).toString().toString());
        }
        FirTypeRef returnTypeRef = firCallableDeclaration.getReturnTypeRef();
        if (!(returnTypeRef instanceof FirResolvedTypeRef)) {
            throw new IllegalArgumentException(UtilsKt.render(firCallableDeclaration).toString());
        }
        if (regularTowerDataContexts != null) {
            bodyResolveContext.setRegularTowerDataContexts(regularTowerDataContexts);
        }
        return (FirResolvedTypeRef) returnTypeRef;
    }

    /* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0005H\u0014¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ReturnTypeCalculatorWithJump$FakeOverrideTypeCalculatorWithJump;", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator$AbstractFakeOverrideTypeCalculator;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ReturnTypeCalculatorWithJump;)V", "getResolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class FakeOverrideTypeCalculatorWithJump extends FakeOverrideTypeCalculator.AbstractFakeOverrideTypeCalculator {
        public FakeOverrideTypeCalculatorWithJump() {
        }

        @Override // org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator.AbstractFakeOverrideTypeCalculator
        protected FirResolvedTypeRef getResolvedTypeRef(FirCallableDeclaration firCallableDeclaration) {
            Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
            return ReturnTypeCalculatorWithJump.this.computeReturnTypeRef(firCallableDeclaration);
        }
    }
}
