package org.jetbrains.kotlin.fir.resolve.calls;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: ConstructorProcessing.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aL\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a4\u0010\u000f\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0016\u0010\u0006\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a&\u0010\u0010\u001a\u0004\u0018\u0001H\u0011\"\u0004\b\u0000\u0010\u0011*\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0002\b\u0003\u0018\u00010\u0012H\u0082\u0002¢\u0006\u0002\u0010\u0013\u001a&\u0010\u0014\u001a\u0004\u0018\u0001H\u0011\"\u0004\b\u0000\u0010\u0011*\u000e\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u0011\u0018\u00010\u0012H\u0082\u0002¢\u0006\u0002\u0010\u0013\u001a&\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a(\u0010\u001a\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001b*\u00020\u00172\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d\u001a\u001c\u0010\u001e\u001a\u00020\u000e*\u00020\u001f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001aD\u0010 \u001a\u00020\u0001*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0016\u0010\u0006\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030!\u0012\u0004\u0012\u00020\u00010\u0007H\u0000\u001aD\u0010\"\u001a\u00020\u0001*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0016\u0010\u0006\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030!\u0012\u0004\u0012\u00020\u00010\u0007H\u0000¨\u0006#"}, d2 = {"processConstructors", "", "matchedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "includeInnerConstructors", "", "processSyntheticConstructors", "component1", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Pair;", "(Lkotlin/Pair;)Ljava/lang/Object;", "component2", "getFirstClassifierOrNull", "Lorg/jetbrains/kotlin/fir/resolve/calls/SymbolWithSubstitutor;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "getSingleVisibleClassifier", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isInvisibleOrHidden", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "processConstructorsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "processFunctionsAndConstructorsByName", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstructorProcessingKt {

    /* JADX INFO: compiled from: ConstructorProcessing.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void processConstructorsByName(FirScope firScope, CallInfo callInfo, FirSession session, BodyResolveComponents bodyResolveComponents, boolean z, Function1<? super FirCallableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(processor, "processor");
        SymbolWithSubstitutor firstClassifierOrNull = getFirstClassifierOrNull(firScope, callInfo, session, bodyResolveComponents);
        if (firstClassifierOrNull == null) {
            return;
        }
        FirClassifierSymbol<?> firClassifierSymbolComponent1 = firstClassifierOrNull.component1();
        ConeSubstitutor substitutor = firstClassifierOrNull.getSubstitutor();
        FirClassLikeSymbol firClassLikeSymbol = firClassifierSymbolComponent1 instanceof FirClassLikeSymbol ? (FirClassLikeSymbol) firClassifierSymbolComponent1 : null;
        if (firClassLikeSymbol == null) {
            return;
        }
        processConstructors(firClassLikeSymbol, substitutor, processor, session, bodyResolveComponents, z);
        processSyntheticConstructors(firClassLikeSymbol, processor, bodyResolveComponents);
    }

    public static final void processFunctionsAndConstructorsByName(FirScope firScope, CallInfo callInfo, FirSession session, BodyResolveComponents bodyResolveComponents, boolean z, Function1<? super FirCallableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(processor, "processor");
        processConstructorsByName(firScope, callInfo, session, bodyResolveComponents, z, processor);
        firScope.processFunctionsByName(callInfo.getName(), processor);
    }

    public static final FirClassifierSymbol<?> getSingleVisibleClassifier(FirScope firScope, final FirSession session, final BodyResolveComponents bodyResolveComponents, Name name2) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        Intrinsics.checkNotNullParameter(name2, "name");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt$getSingleVisibleClassifier$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                invoke2(firClassifierSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassifierSymbol<?> classifierSymbol) {
                Intrinsics.checkNotNullParameter(classifierSymbol, "classifierSymbol");
                if (ConstructorProcessingKt.isInvisibleOrHidden(classifierSymbol.getFir(), session, bodyResolveComponents)) {
                    return;
                }
                linkedHashSet.add(classifierSymbol);
            }
        };
        firScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt$getSingleVisibleClassifier$lambda$0$$inlined$processClassifiersByName$1
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
        return (FirClassifierSymbol) CollectionsKt.singleOrNull(linkedHashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInvisibleOrHidden(FirDeclaration firDeclaration, FirSession firSession, BodyResolveComponents bodyResolveComponents) {
        if ((firDeclaration instanceof FirMemberDeclaration) && !FirVisibilityChecker.isVisible$default(FirVisibilityCheckerKt.getVisibilityChecker(firSession), (FirMemberDeclaration) firDeclaration, firSession, bodyResolveComponents.getFile(), bodyResolveComponents.getContainingDeclarations(), null, false, null, false, null, XKeySym.XK_Racute, null)) {
            return true;
        }
        DeprecationInfo deprecationForCallSite = DeprecationUtilsKt.getDeprecationForCallSite(firDeclaration.getSymbol(), firSession, new AnnotationUseSiteTarget[0]);
        return deprecationForCallSite != null && deprecationForCallSite.getDeprecationLevel() == DeprecationLevelValue.HIDDEN;
    }

    private static final SymbolWithSubstitutor getFirstClassifierOrNull(FirScope firScope, CallInfo callInfo, final FirSession firSession, final BodyResolveComponents bodyResolveComponents) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        firScope.processClassifiersByNameWithSubstitution(callInfo.getName(), new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt.getFirstClassifierOrNull.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                invoke2(firClassifierSymbol, coneSubstitutor);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
            /* JADX WARN: Type inference failed for: r1v2, types: [T, org.jetbrains.kotlin.fir.resolve.calls.SymbolWithSubstitutor] */
            /* JADX WARN: Type inference failed for: r1v4, types: [T, org.jetbrains.kotlin.fir.resolve.calls.SymbolWithSubstitutor] */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor substitutor) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                boolean zIsInvisibleOrHidden = ConstructorProcessingKt.isInvisibleOrHidden(symbol.getFir(), firSession, bodyResolveComponents);
                boolean z = !zIsInvisibleOrHidden;
                if (!zIsInvisibleOrHidden && !booleanRef.element) {
                    booleanRef.element = true;
                    booleanRef2.element = false;
                    objectRef.element = new SymbolWithSubstitutor(symbol, substitutor);
                    return;
                }
                SymbolWithSubstitutor symbolWithSubstitutor = objectRef.element;
                if ((symbolWithSubstitutor != null ? symbolWithSubstitutor.getSymbol() : null) == symbol) {
                    return;
                }
                if (objectRef.element != null) {
                    if (booleanRef.element == z) {
                        booleanRef2.element = true;
                    }
                } else {
                    booleanRef.element = z;
                    objectRef.element = new SymbolWithSubstitutor(symbol, substitutor);
                }
            }
        });
        Object obj = objectRef.element;
        if (booleanRef2.element) {
            obj = null;
        }
        return (SymbolWithSubstitutor) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void processSyntheticConstructors(FirClassLikeSymbol<?> firClassLikeSymbol, Function1<? super FirFunctionSymbol<?>, Unit> function1, BodyResolveComponents bodyResolveComponents) {
        FirSimpleFunction samConstructor = bodyResolveComponents.getSamResolver().getSamConstructor((FirClassLikeDeclaration) firClassLikeSymbol.getFir());
        if (samConstructor != null) {
            function1.invoke(samConstructor.getSymbol());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void processConstructors(FirClassLikeSymbol<?> firClassLikeSymbol, ConeSubstitutor coneSubstitutor, final Function1<? super FirFunctionSymbol<?>, Unit> function1, FirSession firSession, BodyResolveComponents bodyResolveComponents, final boolean z) {
        FirTypeScope firTypeScopeScopeForClass;
        FirElement fir = firClassLikeSymbol.getFir();
        try {
            if (firClassLikeSymbol instanceof FirTypeAliasSymbol) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassLikeSymbol, FirResolvePhase.TYPES);
                ConeKotlinType type = ((FirResolvedTypeRef) ((FirTypeAlias) ((FirTypeAliasSymbol) firClassLikeSymbol).getFir()).getExpandedTypeRef()).getType();
                if (type != null) {
                    ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, firSession, null, 2, null);
                    FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(coneClassLikeTypeFullyExpandedType$default, firSession, bodyResolveComponents.getScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
                    firTypeScopeScopeForClass = firTypeScopeScope != null ? new TypeAliasConstructorsSubstitutingScope((FirTypeAliasSymbol) firClassLikeSymbol, firTypeScopeScope, bodyResolveComponents.getOuterClassManager().outerType(coneClassLikeTypeFullyExpandedType$default)) : null;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
                }
            } else if (firClassLikeSymbol instanceof FirClassSymbol) {
                E fir2 = firClassLikeSymbol.getFir();
                Intrinsics.checkNotNull(fir2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirClass");
                FirClass firClass = (FirClass) fir2;
                firTypeScopeScopeForClass = WhenMappings.$EnumSwitchMapping$0[firClass.getClassKind().ordinal()] == 1 ? null : FirKotlinScopeProviderKt.scopeForClass(firClass, coneSubstitutor, firSession, bodyResolveComponents.getScopeSession(), firClass.getSymbol().getLookupTag(), FirResolvePhase.STATUS);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            if (firTypeScopeScopeForClass != null) {
                firTypeScopeScopeForClass.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ConstructorProcessingKt$processConstructors$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                        invoke2(firConstructorSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirConstructorSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (z || !((FirMemberDeclaration) it.getFir()).getStatus().isInner()) {
                            function1.invoke(it);
                        }
                    }
                });
                Unit unit = Unit.INSTANCE;
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(fir, th);
            throw null;
        }
    }
}
