package org.jetbrains.kotlin.fir.resolve.calls.tower;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedQualifierBuilder;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt;
import org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDefaultStarImportingScope;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u0006\u0010\u0015\u001a\u00020\nJ8\u0010\u0016\u001a\u00020\u0017\"\f\b\u0000\u0010\u0018*\u0006\u0012\u0002\b\u00030\u00192\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001fH\u0002J\u001e\u0010 \u001a\u0004\u0018\u00010!2\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\"\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001d2\u0010\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0\u001fH\u0016J\"\u0010&\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001d2\u0010\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u001fH\u0016J\"\u0010'\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001d2\u0010\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u001fH\u0016J\u0014\u0010)\u001a\u00020\n2\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0002J\u0016\u0010*\u001a\u00020\r*\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ScopeTowerLevel;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "givenExtensionReceiverOptions", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "withHideMembersOnly", "", "includeInnerConstructors", "dispatchReceiverForStatics", "Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Ljava/util/List;ZZLorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;)V", "getScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "areThereExtensionReceiverOptions", "consumeCallableCandidate", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "candidate", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "processor", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel$TowerScopeLevelProcessor;", "dispatchReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ProcessResult;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "processObjectsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "shouldSkipCandidateWithInconsistentExtensionReceiver", "toResolvedQualifierExpressionReceiver", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopeTowerLevel extends TowerScopeLevel {
    private final BodyResolveComponents bodyResolveComponents;
    private final ExpressionReceiverValue dispatchReceiverForStatics;
    private final List<FirExpression> givenExtensionReceiverOptions;
    private final boolean includeInnerConstructors;
    private final FirScope scope;
    private final boolean withHideMembersOnly;

    public final FirScope getScope() {
        return this.scope;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ScopeTowerLevel(BodyResolveComponents bodyResolveComponents, FirScope scope, List<? extends FirExpression> givenExtensionReceiverOptions, boolean z, boolean z2, ExpressionReceiverValue expressionReceiverValue) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(givenExtensionReceiverOptions, "givenExtensionReceiverOptions");
        this.bodyResolveComponents = bodyResolveComponents;
        this.scope = scope;
        this.givenExtensionReceiverOptions = givenExtensionReceiverOptions;
        this.withHideMembersOnly = z;
        this.includeInnerConstructors = z2;
        this.dispatchReceiverForStatics = expressionReceiverValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirSession getSession() {
        return this.bodyResolveComponents.getSession();
    }

    public final boolean areThereExtensionReceiverOptions() {
        return !this.givenExtensionReceiverOptions.isEmpty();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ReceiverValue dispatchReceiverValue(FirCallableSymbol<?> candidate, CallInfo callInfo) {
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData((FirCallableDeclaration) candidate.getFir());
        if (importedFromObjectOrStaticData != null) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(getSession()).getClassLikeSymbolByClassId(importedFromObjectOrStaticData.getObjectClassId());
            if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
                return toResolvedQualifierExpressionReceiver((FirRegularClassSymbol) classLikeSymbolByClassId, callInfo.getCallSite().getSource());
            }
        }
        if (candidate instanceof FirBackingFieldSymbol) {
            final ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(((FirBackingField) ((FirBackingFieldSymbol) candidate).getFir()).getPropertySymbol());
            if (coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null) {
                return this.bodyResolveComponents.getImplicitReceiverStack().lastDispatchReceiver(new Function1<ImplicitReceiverValue<?>, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.ScopeTowerLevel.dispatchReceiverValue.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(ImplicitReceiverValue<?> implicitReceiverValue) {
                        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
                        Intrinsics.checkNotNullParameter(implicitReceiverValue, "implicitReceiverValue");
                        ConeKotlinType type = implicitReceiverValue.getType();
                        ConeClassLikeLookupTag lookupTag = null;
                        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
                        if (coneClassLikeType != null && (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, ScopeTowerLevel.this.getSession(), null, 2, null)) != null) {
                            lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag();
                        }
                        return Boolean.valueOf(Intrinsics.areEqual(lookupTag, coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull));
                    }
                });
            }
            return this.bodyResolveComponents.getImplicitReceiverStack().lastDispatchReceiver();
        }
        if (candidate.getRawStatus().isStatic()) {
            return this.dispatchReceiverForStatics;
        }
        return null;
    }

    private final boolean shouldSkipCandidateWithInconsistentExtensionReceiver(FirCallableSymbol<?> candidate) {
        if (!(this.scope instanceof FirDefaultStarImportingScope) || !areThereExtensionReceiverOptions()) {
            return false;
        }
        FirResolvedTypeRef resolvedReceiverTypeRef = candidate.getResolvedReceiverTypeRef();
        ConeKotlinType coneType = resolvedReceiverTypeRef != null ? FirTypeUtilsKt.getConeType(resolvedReceiverTypeRef) : null;
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        if (coneClassLikeType == null) {
            return false;
        }
        ConeClassLikeLookupTag lookupTag = coneClassLikeType.getLookupTag();
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        ArrayList arrayList = new ArrayList(typeArguments.length);
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            arrayList.add(ConeStarProjection.INSTANCE);
        }
        ConeClassLikeType coneClassLikeTypeConstructClassType$default = TypeConstructionUtilsKt.constructClassType$default(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeStarProjection[0]), true, null, 4, null);
        List<FirExpression> list = this.givenExtensionReceiverOptions;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(((FirExpression) it.next()).getTypeRef());
                if (!(coneType2 instanceof ConeClassLikeType) ? true : AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(getSession()), (KotlinTypeMarker) coneType2, (KotlinTypeMarker) coneClassLikeTypeConstructClassType$default, false, 8, (Object) null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T extends FirBasedSymbol<?>> void consumeCallableCandidate(FirCallableSymbol<?> candidate, CallInfo callInfo, TowerScopeLevel.TowerScopeLevelProcessor<? super T> processor) {
        FirCallableSymbol<?> symbol;
        FirCallableDeclaration original;
        FirCallableSymbol<?> firCallableSymbol = candidate;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firCallableSymbol, FirResolvePhase.TYPES);
        if (this.withHideMembersOnly && FirAnnotationUtilsKt.getAnnotationByClassId(firCallableSymbol, StandardClassIds.Annotations.INSTANCE.getHidesMembers(), getSession()) == null) {
            return;
        }
        boolean z = this.withHideMembersOnly || areThereExtensionReceiverOptions();
        FirReceiverParameter receiverParameter = ((FirCallableDeclaration) candidate.getFir()).getReceiverParameter();
        if (((receiverParameter != null ? receiverParameter.getTypeRef() : null) == null) == z) {
            return;
        }
        ReceiverValue receiverValueDispatchReceiverValue = dispatchReceiverValue(candidate, callInfo);
        if (receiverValueDispatchReceiverValue == null && shouldSkipCandidateWithInconsistentExtensionReceiver(candidate)) {
            return;
        }
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData((FirCallableDeclaration) candidate.getFir());
        if (importedFromObjectOrStaticData == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || (symbol = original.getSymbol()) == null) {
            symbol = candidate;
        }
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type T of org.jetbrains.kotlin.fir.resolve.calls.tower.ScopeTowerLevel.consumeCallableCandidate");
        TowerScopeLevel.TowerScopeLevelProcessor.consumeCandidate$default(processor, symbol, receiverValueDispatchReceiverValue != null ? receiverValueDispatchReceiverValue.getReceiverExpression() : null, this.givenExtensionReceiverOptions, this.scope, false, false, 48, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processFunctionsByName(final CallInfo info, final TowerScopeLevel.TowerScopeLevelProcessor<? super FirFunctionSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordCallLookup(lookupTracker, info, this.scope.getScopeOwnerLookupNames());
        }
        ConstructorProcessingKt.processFunctionsAndConstructorsByName(this.scope, info, getSession(), this.bodyResolveComponents, this.includeInnerConstructors, new Function1<FirCallableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.ScopeTowerLevel.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirCallableSymbol<?> firCallableSymbol) {
                invoke2(firCallableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirCallableSymbol<?> candidate) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                booleanRef.element = false;
                this.consumeCallableCandidate(candidate, info, processor);
            }
        });
        return booleanRef.element ? ProcessResult.SCOPE_EMPTY : ProcessResult.FOUND;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processPropertiesByName(final CallInfo info, final TowerScopeLevel.TowerScopeLevelProcessor<? super FirVariableSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordCallLookup(lookupTracker, info, this.scope.getScopeOwnerLookupNames());
        }
        this.scope.processPropertiesByName(info.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.ScopeTowerLevel.processPropertiesByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> candidate) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                booleanRef.element = false;
                this.consumeCallableCandidate(candidate, info, processor);
            }
        });
        return booleanRef.element ? ProcessResult.SCOPE_EMPTY : ProcessResult.FOUND;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.tower.TowerScopeLevel
    public ProcessResult processObjectsByName(CallInfo info, final TowerScopeLevel.TowerScopeLevelProcessor<? super FirBasedSymbol<?>> processor) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordCallLookup(lookupTracker, info, this.scope.getScopeOwnerLookupNames());
        }
        FirScope firScope = this.scope;
        Name name2 = info.getName();
        final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.ScopeTowerLevel.processObjectsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                invoke2(firClassifierSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassifierSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                booleanRef.element = false;
                TowerScopeLevel.TowerScopeLevelProcessor.consumeCandidate$default(processor, it, null, CollectionsKt.emptyList(), this.getScope(), true, false, 32, null);
            }
        };
        firScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.ScopeTowerLevel$processObjectsByName$$inlined$processClassifiersByName$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                invoke2(firClassifierSymbol, coneSubstitutor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor coneSubstitutor) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
                function1.invoke(symbol);
            }
        });
        return booleanRef.element ? ProcessResult.SCOPE_EMPTY : ProcessResult.FOUND;
    }

    private final ExpressionReceiverValue toResolvedQualifierExpressionReceiver(FirRegularClassSymbol firRegularClassSymbol, KtSourceElement ktSourceElement) {
        FirResolvedQualifierBuilder firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
        FqName packageFqName = firRegularClassSymbol.getClassId().getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        firResolvedQualifierBuilder.setPackageFqName(packageFqName);
        firResolvedQualifierBuilder.setRelativeClassFqName(firRegularClassSymbol.getClassId().getRelativeClassName());
        firResolvedQualifierBuilder.setSymbol(firRegularClassSymbol);
        firResolvedQualifierBuilder.setSource(ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.ImplicitReceiver.INSTANCE, 0, 0, 6, null) : null);
        FirResolvedQualifier firResolvedQualifierMo6810build = firResolvedQualifierBuilder.mo6809build();
        FirResolvedQualifier firResolvedQualifier = firResolvedQualifierMo6810build;
        firResolvedQualifier.replaceTypeRef(ResolveUtilsKt.typeForQualifier(this.bodyResolveComponents, firResolvedQualifierMo6810build));
        return new ExpressionReceiverValue(firResolvedQualifier);
    }
}
