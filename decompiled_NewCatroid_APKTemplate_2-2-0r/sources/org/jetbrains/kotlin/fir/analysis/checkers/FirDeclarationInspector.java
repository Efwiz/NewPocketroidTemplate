package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponent;
import org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNameConflictsTracker;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOverloadabilityHelperKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.impl.FirPackageMemberScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: FirConflictsHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0015H\u0002J2\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u001c2\u0018\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u001f0\u001eH\u0002J\u000e\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"J\u0016\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020&JD\u0010'\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\u00152\n\u0010*\u001a\u0006\u0012\u0002\b\u00030\t2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u0015H\u0002J\u0018\u0010-\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u0007H\u0002J\f\u0010/\u001a\u00020\u0012*\u000200H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000RA\u0010\u0005\u001a2\u0012\u0004\u0012\u00020\u0007\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b0\u0006j\u0018\u0012\u0004\u0012\u00020\u0007\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirDeclarationInspector;", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)V", "declarationConflictingSymbols", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/utils/SmartSet;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lkotlin/collections/HashMap;", "getDeclarationConflictingSymbols", "()Ljava/util/HashMap;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "areCompatibleMainFunctions", "", "declaration1", "file1", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "declaration2", "file2", "collect", "", "declaration", "representation", "", "map", "", "", "collectClassMembers", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "collectTopLevel", "file", "packageMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirPackageMemberScope;", "collectTopLevelConflict", "declarationPresentation", "containingFile", "conflictingSymbol", "conflictingPresentation", "conflictingFile", "isOverloadable", "conflicting", "representsMainFunctionAllowingConflictingOverloads", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationInspector {
    private final CheckerContext context;
    private final HashMap<FirDeclaration, SmartSet<FirBasedSymbol<?>>> declarationConflictingSymbols;

    public FirDeclarationInspector(CheckerContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.declarationConflictingSymbols = new HashMap<>();
    }

    private final FirSession getSession() {
        return this.context.getSessionHolder().getSession();
    }

    public final HashMap<FirDeclaration, SmartSet<FirBasedSymbol<?>>> getDeclarationConflictingSymbols() {
        return this.declarationConflictingSymbols;
    }

    public final void collectClassMembers(FirRegularClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (FirDeclaration firDeclaration : klass.getDeclarations()) {
            if (FirConflictsHelpersKt.isCollectable(firDeclaration)) {
                if (firDeclaration instanceof FirSimpleFunction) {
                    collect(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent((FirSimpleFunction) firDeclaration), linkedHashMap2);
                } else if (firDeclaration instanceof FirRegularClass) {
                    collect(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent((FirRegularClass) firDeclaration), linkedHashMap);
                } else if (firDeclaration instanceof FirTypeAlias) {
                    collect(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent((FirTypeAlias) firDeclaration), linkedHashMap);
                } else if (firDeclaration instanceof FirVariable) {
                    collect(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent((FirVariable) firDeclaration), linkedHashMap);
                }
            }
        }
    }

    public final void collectTopLevel(final FirFile file, FirPackageMemberScope packageMemberScope) {
        HashMap<ClassId, Set<FirNameConflictsTracker.ClassifierWithFile>> redeclaredClassifiers;
        Set<FirNameConflictsTracker.ClassifierWithFile> set;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(packageMemberScope, "packageMemberScope");
        for (Map.Entry entry : FirConflictsHelpersKt.groupTopLevelByName(file.getDeclarations()).entrySet()) {
            final Name name2 = (Name) entry.getKey();
            final DeclarationBuckets declarationBuckets = (DeclarationBuckets) entry.getValue();
            boolean z = (declarationBuckets.getClassLikes().isEmpty() && declarationBuckets.getProperties().isEmpty()) ? false : true;
            boolean zIsEmpty = declarationBuckets.getSimpleFunctions().isEmpty();
            final boolean z2 = !zIsEmpty;
            if (!zIsEmpty || !declarationBuckets.getConstructors().isEmpty()) {
                packageMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirDeclarationInspector.collectTopLevel.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                        invoke2(firNamedFunctionSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirNamedFunctionSymbol firNamedFunctionSymbol = it;
                        FirDeclarationInspector.collectTopLevel$collect$default(this, file, name2, declarationBuckets.getSimpleFunctions(), firNamedFunctionSymbol, null, null, 96, null);
                        FirDeclarationInspector.collectTopLevel$collect$default(this, file, name2, declarationBuckets.getConstructors(), firNamedFunctionSymbol, null, null, 96, null);
                    }
                });
            }
            if (z || !zIsEmpty) {
                packageMemberScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirDeclarationInspector.collectTopLevel.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        FirDeclarationInspector.collectTopLevel$collectFromClassifierSource$default(declarationBuckets, z2, this, file, name2, symbol, null, null, 192, null);
                    }
                });
                FirNameConflictsTrackerComponent nameConflictsTracker = FirNameConflictsTrackerComponentKt.getNameConflictsTracker(getSession());
                if (nameConflictsTracker != null) {
                    FirNameConflictsTracker firNameConflictsTracker = nameConflictsTracker instanceof FirNameConflictsTracker ? (FirNameConflictsTracker) nameConflictsTracker : null;
                    if (firNameConflictsTracker != null && (redeclaredClassifiers = firNameConflictsTracker.getRedeclaredClassifiers()) != null && (set = redeclaredClassifiers.get(new ClassId(UtilsKt.getPackageFqName(file), name2))) != null) {
                        for (FirNameConflictsTracker.ClassifierWithFile classifierWithFile : set) {
                            collectTopLevel$collectFromClassifierSource$default(declarationBuckets, z2, this, file, name2, classifierWithFile.getClassifier(), null, classifierWithFile.getFile(), 64, null);
                        }
                    }
                }
                for (Pair<FirClassLikeDeclaration, String> pair : declarationBuckets.getClassLikes()) {
                    collectTopLevel$collectFromClassifierSource(declarationBuckets, z2, this, file, name2, pair.component1().getSymbol(), pair.component2(), file);
                }
            }
            if (z || !declarationBuckets.getExtensionProperties().isEmpty()) {
                packageMemberScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirDeclarationInspector.collectTopLevel.5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                        invoke2(firVariableSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirVariableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirVariableSymbol<?> firVariableSymbol = it;
                        FirDeclarationInspector.collectTopLevel$collect$default(this, file, name2, declarationBuckets.getClassLikes(), firVariableSymbol, null, null, 96, null);
                        FirDeclarationInspector.collectTopLevel$collect$default(this, file, name2, declarationBuckets.getProperties(), firVariableSymbol, null, null, 96, null);
                        FirDeclarationInspector.collectTopLevel$collect$default(this, file, name2, declarationBuckets.getExtensionProperties(), firVariableSymbol, null, null, 96, null);
                    }
                });
            }
        }
    }

    static /* synthetic */ void collectTopLevel$collect$default(FirDeclarationInspector firDeclarationInspector, FirFile firFile, Name name2, List list, FirBasedSymbol firBasedSymbol, String str, FirFile firFile2, int i, Object obj) {
        collectTopLevel$collect(firDeclarationInspector, firFile, name2, list, firBasedSymbol, (i & 32) != 0 ? null : str, (i & 64) != 0 ? null : firFile2);
    }

    private static final void collectTopLevel$collect(FirDeclarationInspector firDeclarationInspector, FirFile firFile, Name name2, List<? extends Pair<? extends FirDeclaration, String>> list, FirBasedSymbol<?> firBasedSymbol, String str, FirFile firFile2) {
        for (Pair<? extends FirDeclaration, String> pair : list) {
            FirDeclaration firDeclarationComponent1 = pair.component1();
            firDeclarationInspector.collectTopLevelConflict(firDeclarationComponent1, pair.component2(), firFile, firBasedSymbol, str, firFile2);
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(firDeclarationInspector.getSession());
            if (lookupTracker != null) {
                String strAsString = UtilsKt.getPackageFqName(firFile).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                lookupTracker.recordLookup(name2, strAsString, firDeclarationComponent1.getSource(), firFile.getSource());
            }
        }
    }

    static /* synthetic */ void collectTopLevel$collectFromClassifierSource$default(DeclarationBuckets declarationBuckets, boolean z, FirDeclarationInspector firDeclarationInspector, FirFile firFile, Name name2, FirClassifierSymbol firClassifierSymbol, String str, FirFile firFile2, int i, Object obj) {
        collectTopLevel$collectFromClassifierSource(declarationBuckets, z, firDeclarationInspector, firFile, name2, firClassifierSymbol, (i & 64) != 0 ? null : str, (i & 128) != 0 ? null : firFile2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void collectTopLevel$collectFromClassifierSource(final DeclarationBuckets declarationBuckets, boolean z, final FirDeclarationInspector firDeclarationInspector, final FirFile firFile, final Name name2, FirClassifierSymbol<?> firClassifierSymbol, String str, FirFile firFile2) {
        FirClassifierSymbol<?> firClassifierSymbol2 = firClassifierSymbol;
        collectTopLevel$collect(firDeclarationInspector, firFile, name2, declarationBuckets.getClassLikes(), firClassifierSymbol2, str, firFile2);
        collectTopLevel$collect(firDeclarationInspector, firFile, name2, declarationBuckets.getProperties(), firClassifierSymbol2, str, firFile2);
        if (z && (firClassifierSymbol instanceof FirRegularClassSymbol)) {
            FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) firClassifierSymbol;
            if (firRegularClassSymbol.getClassKind() == ClassKind.OBJECT || firRegularClassSymbol.getClassKind() == ClassKind.ENUM_ENTRY) {
                return;
            }
            FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassifierSymbol2, FirResolvePhase.STATUS);
            final FirRegularClass firRegularClass = (FirRegularClass) firRegularClassSymbol.getFir();
            FirHelpersKt.unsubstitutedScope(firRegularClass, firDeclarationInspector.context).processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirDeclarationInspector$collectTopLevel$collectFromClassifierSource$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                public final void invoke2(FirConstructorSymbol constructor) {
                    Intrinsics.checkNotNullParameter(constructor, "constructor");
                    FirDeclarationInspector.collectTopLevel$collect$default(firDeclarationInspector, firFile, name2, declarationBuckets.getSimpleFunctions(), constructor, FirRedeclarationPresenter.INSTANCE.represent((FirConstructor) constructor.getFir(), firRegularClass), null, 64, null);
                }
            });
        }
    }

    private final void collectTopLevelConflict(FirDeclaration declaration, String declarationPresentation, FirFile containingFile, FirBasedSymbol<?> conflictingSymbol, String conflictingPresentation, FirFile conflictingFile) {
        FirFile firCallableContainerFile;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(conflictingSymbol, FirResolvePhase.STATUS);
        FirDeclaration fir = conflictingSymbol.getFir();
        if (Intrinsics.areEqual(fir, declaration) || !Intrinsics.areEqual(declaration.getModuleData(), fir.getModuleData())) {
            return;
        }
        if (Intrinsics.areEqual(conflictingPresentation == null ? FirRedeclarationPresenter.INSTANCE.represent(fir) : conflictingPresentation, declarationPresentation)) {
            if (conflictingFile != null) {
                firCallableContainerFile = conflictingFile;
            } else if (conflictingSymbol instanceof FirClassLikeSymbol) {
                firCallableContainerFile = FirProviderKt.getFirProvider(getSession()).getFirClassifierContainerFileIfAny((FirClassLikeSymbol<?>) conflictingSymbol);
            } else {
                firCallableContainerFile = conflictingSymbol instanceof FirCallableSymbol ? FirProviderKt.getFirProvider(getSession()).getFirCallableContainerFile((FirCallableSymbol) conflictingSymbol) : null;
            }
            if (FirConflictsHelpersKt.isCollectable(fir) && !areCompatibleMainFunctions(declaration, containingFile, fir, firCallableContainerFile)) {
                if ((!(fir instanceof FirMemberDeclaration) || FirVisibilityChecker.isVisible$default(FirVisibilityCheckerKt.getVisibilityChecker(getSession()), (FirMemberDeclaration) fir, getSession(), containingFile, CollectionsKt.emptyList(), null, false, null, false, null, 480, null)) && !isOverloadable(declaration, fir)) {
                    HashMap<FirDeclaration, SmartSet<FirBasedSymbol<?>>> map = this.declarationConflictingSymbols;
                    SmartSet<FirBasedSymbol<?>> smartSetCreate = map.get(declaration);
                    if (smartSetCreate == null) {
                        smartSetCreate = SmartSet.INSTANCE.create();
                        map.put(declaration, smartSetCreate);
                    }
                    smartSetCreate.add(conflictingSymbol);
                }
            }
        }
    }

    private final boolean representsMainFunctionAllowingConflictingOverloads(FirSimpleFunction firSimpleFunction) {
        FirTypeRef returnTypeRef;
        ConeKotlinType coneType;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        if (Intrinsics.areEqual(firSimpleFunction.getName(), StandardNames.MAIN) && FirConflictsHelpersKt.isTopLevel(firSimpleFunction.getSymbol().getCallableId()) && FirConflictsHelpersKt.getHasMainFunctionStatus(firSimpleFunction) && firSimpleFunction.getReceiverParameter() == null && firSimpleFunction.getTypeParameters().isEmpty()) {
            if (firSimpleFunction.getValueParameters().isEmpty()) {
                return true;
            }
            FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.singleOrNull((List) firSimpleFunction.getValueParameters());
            if (firValueParameter == null || (returnTypeRef = firValueParameter.getReturnTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(returnTypeRef)) == null || (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneType, getSession())) == null || !ConeBuiltinTypeUtilsKt.isNonPrimitiveArray(coneKotlinTypeFullyExpandedType)) {
                return false;
            }
            Object objSingleOrNull = ArraysKt.singleOrNull(coneKotlinTypeFullyExpandedType.getTypeArguments());
            ConeKotlinTypeProjection coneKotlinTypeProjection = objSingleOrNull instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) objSingleOrNull : null;
            if (coneKotlinTypeProjection == null) {
                return false;
            }
            if ((coneKotlinTypeProjection instanceof ConeKotlinType) || (coneKotlinTypeProjection instanceof ConeKotlinTypeProjectionOut)) {
                return ConeBuiltinTypeUtilsKt.isString(TypeExpansionUtilsKt.fullyExpandedType(coneKotlinTypeProjection.getType(), getSession()));
            }
            return false;
        }
        return false;
    }

    private final boolean areCompatibleMainFunctions(FirDeclaration declaration1, FirFile file1, FirDeclaration declaration2, FirFile file2) {
        return !Intrinsics.areEqual(file1, file2) && (declaration1 instanceof FirSimpleFunction) && (declaration2 instanceof FirSimpleFunction) && representsMainFunctionAllowingConflictingOverloads((FirSimpleFunction) declaration1) && representsMainFunctionAllowingConflictingOverloads((FirSimpleFunction) declaration2);
    }

    private final boolean isOverloadable(FirDeclaration declaration, FirDeclaration conflicting) {
        if (!FirConflictsHelpersKt.isExpectAndActual(declaration, conflicting) && FirAnnotationUtilsKt.hasLowPriorityAnnotation(declaration.getAnnotations()) == FirAnnotationUtilsKt.hasLowPriorityAnnotation(conflicting.getAnnotations())) {
            return (declaration instanceof FirCallableDeclaration) && (conflicting instanceof FirCallableDeclaration) && FirDeclarationOverloadabilityHelperKt.getDeclarationOverloadabilityHelper(getSession()).isOverloadable((FirCallableDeclaration) declaration, (FirCallableDeclaration) conflicting);
        }
        return true;
    }

    private final void collect(FirDeclaration declaration, String representation, Map<String, List<FirDeclaration>> map) {
        ArrayList arrayList = map.get(representation);
        if (arrayList == null) {
            arrayList = new ArrayList();
            map.put(representation, arrayList);
        }
        List<FirDeclaration> list = arrayList;
        list.add(declaration);
        SmartSet<FirBasedSymbol<?>> smartSetCreate = SmartSet.INSTANCE.create();
        for (FirDeclaration firDeclaration : list) {
            if (!Intrinsics.areEqual(firDeclaration, declaration) && !isOverloadable(declaration, firDeclaration)) {
                smartSetCreate.add(firDeclaration.getSymbol());
                HashMap<FirDeclaration, SmartSet<FirBasedSymbol<?>>> map2 = this.declarationConflictingSymbols;
                SmartSet<FirBasedSymbol<?>> smartSetCreate2 = map2.get(firDeclaration);
                if (smartSetCreate2 == null) {
                    smartSetCreate2 = SmartSet.INSTANCE.create();
                    map2.put(firDeclaration, smartSetCreate2);
                }
                smartSetCreate2.add(declaration.getSymbol());
            }
        }
        this.declarationConflictingSymbols.put(declaration, smartSetCreate);
    }
}
