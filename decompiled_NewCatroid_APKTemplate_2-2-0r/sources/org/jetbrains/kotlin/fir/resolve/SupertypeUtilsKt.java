package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.SmartSet;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SupertypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a*\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\tH\u0002\u001a0\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t0\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00072\u0006\u0010\b\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u000b\u001a\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u000b\u001aH\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u00072\u0010\u0010\u001d\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e0\u00072\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020 2\b\b\u0002\u0010#\u001a\u00020$\u001a>\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u00072\u0006\u0010%\u001a\u00020&2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020 2\b\b\u0002\u0010#\u001a\u00020$\u001a0\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u00072\n\u0010'\u001a\u0006\u0012\u0002\b\u00030\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b\u001a-\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u0002H*0\u0001\"\n\b\u0000\u0010)\u0018\u0001*\u00020+\"\n\b\u0001\u0010*\u0018\u0001*\u00020,H\u0086\b\u001aX\u0010-\u001a\u00020.*\u0006\u0012\u0002\b\u00030\u001e2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u0017002\u0010\u00101\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e022\u0006\u0010!\u001a\u00020 2\u0006\u0010\u001f\u001a\u00020 2\u0006\u00103\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u001c\u00104\u001a\u00020\u0017*\u00020\u00172\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u0016\u00105\u001a\u00020 *\u0004\u0018\u00010\u00172\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a6\u00106\u001a\u00020 *\u00020&2\u0006\u00107\u001a\u0002082\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u00109\u001a\u00020 2\b\b\u0002\u0010#\u001a\u00020$2\b\b\u0002\u0010\u001f\u001a\u00020 \u001a.\u00106\u001a\u00020 *\u0006\u0012\u0002\b\u00030\u00022\u0006\u00107\u001a\u0002082\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u00109\u001a\u00020 2\u0006\u0010\u001f\u001a\u00020 \u001a\u0012\u0010:\u001a\u00020 *\u00020&2\u0006\u0010\u0011\u001a\u00020\u000b\"!\u0010\u0000\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006;"}, d2 = {"USE_SITE", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getUSE_SITE", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "collectSymbolsForType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "computeNonTrivialTypeArgumentForScopeSubstitutor", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "originalTypeArgument", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "session", "capturedTypeArgument", "createSubstitutionForScope", "", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "createSubstitutionForSupertype", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "superType", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "lookupSuperTypes", "symbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "lookupInterfaces", "", "deep", "substituteTypes", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", NativeSymbol.TYPE_NAME, "scopeSessionKey", SchemaSymbols.ATTVAL_ID, "FS", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "collectSuperTypes", "", SchemaSymbols.ATTVAL_LIST, "", "visitedSymbols", "", "substituteSuperTypes", "computePartialExpansion", "isClassBasedType", "isSubclassOf", "ownerLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "isStrict", "isThereLoopInSupertypes", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SupertypeUtilsKt {
    private static final ScopeSessionKey<FirClassSymbol<?>, FirTypeScope> USE_SITE = new ScopeSessionKey<FirClassSymbol<?>, FirTypeScope>() { // from class: org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt$special$$inlined$scopeSessionKey$1
    };

    /* JADX INFO: compiled from: SupertypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProjectionKind.values().length];
            try {
                iArr[ProjectionKind.OUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionKind.STAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final List<FirClassSymbol<?>> collectSymbolsForType(ConeKotlinType type, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        ArrayList arrayList = new ArrayList();
        collectSymbolsForType$collectClassIds(type, useSiteSession, arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) it.next(), useSiteSession);
            FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
            if (firClassSymbol != null) {
                arrayList2.add(firClassSymbol);
            }
        }
        return arrayList2;
    }

    private static final void collectSymbolsForType$collectClassIds(ConeKotlinType coneKotlinType, FirSession firSession, List<ConeClassLikeLookupTag> list) {
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType), firSession);
        if (coneSimpleKotlinTypeFullyExpandedType instanceof ConeClassLikeType) {
            CollectionsKt.addIfNotNull(list, ((ConeClassLikeType) coneSimpleKotlinTypeFullyExpandedType).getLookupTag());
        } else if (coneSimpleKotlinTypeFullyExpandedType instanceof ConeIntersectionType) {
            Iterator<T> it = ((ConeIntersectionType) coneSimpleKotlinTypeFullyExpandedType).getIntersectedTypes().iterator();
            while (it.hasNext()) {
                collectSymbolsForType$collectClassIds((ConeKotlinType) it.next(), firSession, list);
            }
        }
    }

    public static /* synthetic */ List lookupSuperTypes$default(List list, boolean z, boolean z2, FirSession firSession, boolean z3, SupertypeSupplier supertypeSupplier, int i, Object obj) {
        if ((i & 32) != 0) {
            supertypeSupplier = SupertypeSupplier.Default.INSTANCE;
        }
        return lookupSuperTypes((List<? extends FirClassifierSymbol<?>>) list, z, z2, firSession, z3, supertypeSupplier);
    }

    public static final List<ConeClassLikeType> lookupSuperTypes(List<? extends FirClassifierSymbol<?>> symbols, boolean z, boolean z2, FirSession useSiteSession, boolean z3, SupertypeSupplier supertypeSupplier) {
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        SmartList smartList = new SmartList();
        SmartSet smartSetCreate = SmartSet.INSTANCE.create();
        Iterator<? extends FirClassifierSymbol<?>> it = symbols.iterator();
        while (it.hasNext()) {
            collectSuperTypes(it.next(), smartList, smartSetCreate, z2, z, z3, useSiteSession, supertypeSupplier);
        }
        return smartList;
    }

    public static /* synthetic */ List lookupSuperTypes$default(FirClass firClass, boolean z, boolean z2, FirSession firSession, boolean z3, SupertypeSupplier supertypeSupplier, int i, Object obj) {
        if ((i & 32) != 0) {
            supertypeSupplier = SupertypeSupplier.Default.INSTANCE;
        }
        return lookupSuperTypes(firClass, z, z2, firSession, z3, supertypeSupplier);
    }

    public static final List<ConeClassLikeType> lookupSuperTypes(FirClass klass, boolean z, boolean z2, FirSession useSiteSession, boolean z3, SupertypeSupplier supertypeSupplier) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        SmartList smartList = new SmartList();
        collectSuperTypes(klass.getSymbol(), smartList, SmartSet.INSTANCE.create(), z2, z, z3, useSiteSession, supertypeSupplier);
        return smartList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isSubclassOf(FirClassSymbol<?> firClassSymbol, ConeClassLikeLookupTag ownerLookupTag, FirSession session, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(ownerLookupTag, "ownerLookupTag");
        Intrinsics.checkNotNullParameter(session, "session");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassSymbol, FirResolvePhase.SUPER_TYPES);
        return isSubclassOf((FirClass) firClassSymbol.getFir(), ownerLookupTag, session, z, SupertypeSupplier.Default.INSTANCE, z2);
    }

    public static /* synthetic */ boolean isSubclassOf$default(FirClass firClass, ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession firSession, boolean z, SupertypeSupplier supertypeSupplier, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            supertypeSupplier = SupertypeSupplier.Default.INSTANCE;
        }
        SupertypeSupplier supertypeSupplier2 = supertypeSupplier;
        if ((i & 16) != 0) {
            z2 = true;
        }
        return isSubclassOf(firClass, coneClassLikeLookupTag, firSession, z, supertypeSupplier2, z2);
    }

    public static final boolean isSubclassOf(FirClass firClass, ConeClassLikeLookupTag ownerLookupTag, FirSession session, boolean z, SupertypeSupplier supertypeSupplier, boolean z2) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(ownerLookupTag, "ownerLookupTag");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        if (Intrinsics.areEqual(firClass.getSymbol().getLookupTag(), ownerLookupTag)) {
            return !z;
        }
        List<ConeClassLikeType> listLookupSuperTypes = lookupSuperTypes(firClass, z2, true, session, false, supertypeSupplier);
        if (!(listLookupSuperTypes instanceof Collection) || !listLookupSuperTypes.isEmpty()) {
            Iterator<T> it = listLookupSuperTypes.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((ConeClassLikeType) it.next()).getLookupTag(), ownerLookupTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isThereLoopInSupertypes(FirClass firClass, FirSession session) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        SmartSet smartSetCreate = SmartSet.INSTANCE.create();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        isThereLoopInSupertypes$dfs(smartSetCreate, linkedHashSet, booleanRef, session, firClass.getSymbol());
        return booleanRef.element;
    }

    private static final void isThereLoopInSupertypes$dfs(Set<FirClassifierSymbol<?>> set, Set<FirClassifierSymbol<?>> set2, Ref.BooleanRef booleanRef, FirSession firSession, FirClassifierSymbol<?> firClassifierSymbol) {
        ConeClassLikeType expandedConeType;
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        if (set.contains(firClassifierSymbol)) {
            return;
        }
        if (!set2.add(firClassifierSymbol)) {
            booleanRef.element = true;
            return;
        }
        Object fir = firClassifierSymbol.getFir();
        if (!(fir instanceof FirClass)) {
            if ((fir instanceof FirTypeAlias) && (expandedConeType = FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) fir)) != null && (lookupTag = expandedConeType.getLookupTag()) != null && (symbol = LookupTagUtilsKt.toSymbol(lookupTag, firSession)) != null) {
                isThereLoopInSupertypes$dfs(set, set2, booleanRef, firSession, symbol);
            }
        } else {
            Iterator<T> it = FirDeclarationUtilKt.getSuperConeTypes((FirClass) fir).iterator();
            while (it.hasNext()) {
                FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) it.next()).getLookupTag(), firSession);
                if (symbol2 != null) {
                    isThereLoopInSupertypes$dfs(set, set2, booleanRef, firSession, symbol2);
                }
            }
        }
        set.add(firClassifierSymbol);
        set2.remove(firClassifierSymbol);
    }

    public static final List<ConeClassLikeType> lookupSuperTypes(FirClassifierSymbol<?> symbol, boolean z, boolean z2, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        SmartList smartList = new SmartList();
        collectSuperTypes(symbol, smartList, SmartSet.INSTANCE.create(), z2, z, false, useSiteSession, SupertypeSupplier.Default.INSTANCE);
        return smartList;
    }

    public static final /* synthetic */ <ID, FS extends FirScope> ScopeSessionKey<ID, FS> scopeSessionKey() {
        Intrinsics.needClassReification();
        return (ScopeSessionKey) new ScopeSessionKey<ID, FS>() { // from class: org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt.scopeSessionKey.1
        };
    }

    public static final ScopeSessionKey<FirClassSymbol<?>, FirTypeScope> getUSE_SITE() {
        return USE_SITE;
    }

    public static final Map<FirTypeParameterSymbol, ConeKotlinType> createSubstitutionForScope(List<? extends FirTypeParameterRef> typeParameters, ConeClassLikeType type, FirSession session) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeType coneClassLikeType = type;
        SimpleTypeMarker simpleTypeMarkerCaptureFromArguments = TypeComponentsKt.getTypeContext(session).captureFromArguments(coneClassLikeType, CaptureStatus.FROM_EXPRESSION);
        if (simpleTypeMarkerCaptureFromArguments != null) {
            coneClassLikeType = simpleTypeMarkerCaptureFromArguments;
        }
        ConeTypeProjection[] typeArguments = ((ConeClassLikeType) coneClassLikeType).getTypeArguments();
        Iterable<IndexedValue> iterableWithIndex = kotlin.collections.CollectionsKt.withIndex(typeParameters);
        ArrayList arrayList = new ArrayList();
        for (IndexedValue indexedValue : iterableWithIndex) {
            int index = indexedValue.getIndex();
            FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) indexedValue.component2();
            ConeTypeProjection coneTypeProjection = (ConeTypeProjection) ArraysKt.getOrNull(typeArguments, index);
            Pair pair = null;
            if (coneTypeProjection != null) {
                if (!(coneTypeProjection instanceof ConeKotlinType)) {
                    throw new IllegalArgumentException(("There should left no projections after capture conversion, but " + coneTypeProjection + " found at " + index).toString());
                }
                ConeTypeProjection coneTypeProjection2 = (ConeTypeProjection) ArraysKt.getOrNull(type.getTypeArguments(), index);
                if (coneTypeProjection2 != null) {
                    FirTypeParameterSymbol symbol = firTypeParameterRef.getSymbol();
                    ConeKotlinType coneKotlinType = (ConeKotlinType) coneTypeProjection;
                    ConeKotlinType coneKotlinTypeComputeNonTrivialTypeArgumentForScopeSubstitutor = computeNonTrivialTypeArgumentForScopeSubstitutor(symbol, coneTypeProjection2, session, coneKotlinType);
                    if (coneKotlinTypeComputeNonTrivialTypeArgumentForScopeSubstitutor != null) {
                        coneKotlinType = coneKotlinTypeComputeNonTrivialTypeArgumentForScopeSubstitutor;
                    }
                    pair = TuplesKt.to(symbol, coneKotlinType);
                }
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return MapsKt.toMap(arrayList);
    }

    private static final ConeKotlinType computeNonTrivialTypeArgumentForScopeSubstitutor(FirTypeParameterSymbol firTypeParameterSymbol, ConeTypeProjection coneTypeProjection, FirSession firSession, ConeKotlinType coneKotlinType) {
        if (firTypeParameterSymbol.getVariance() != Variance.OUT_VARIANCE) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[coneTypeProjection.getKind().ordinal()];
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            return TypeComponentsKt.getTypeApproximator(firSession).approximateToSuperType(coneKotlinType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
        }
        ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
        Intrinsics.checkNotNull(type);
        return type;
    }

    private static final ConeClassLikeType computePartialExpansion(ConeClassLikeType coneClassLikeType, final FirSession firSession, final SupertypeSupplier supertypeSupplier) {
        return TypeExpansionUtilsKt.fullyExpandedType(coneClassLikeType, firSession, new Function1<FirTypeAlias, ConeClassLikeType>() { // from class: org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt.computePartialExpansion.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeClassLikeType invoke(FirTypeAlias it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return supertypeSupplier.expansionForTypeAlias(it, firSession);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void collectSuperTypes(FirClassifierSymbol<?> firClassifierSymbol, List<ConeClassLikeType> list, Set<FirClassifierSymbol<?>> set, boolean z, boolean z2, boolean z3, FirSession firSession, SupertypeSupplier supertypeSupplier) {
        ConeClassLikeType coneClassLikeTypeComputePartialExpansion;
        FirClassLikeSymbol<?> symbol;
        if (set.add(firClassifierSymbol)) {
            if (firClassifierSymbol instanceof FirClassSymbol) {
                List<ConeClassLikeType> listForClass = supertypeSupplier.forClass((FirClass) ((FirClassSymbol) firClassifierSymbol).getFir(), firSession);
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = listForClass.iterator();
                while (it.hasNext()) {
                    ConeClassLikeType coneClassLikeTypeComputePartialExpansion2 = computePartialExpansion((ConeClassLikeType) it.next(), firSession, supertypeSupplier);
                    if (!z2 && !isClassBasedType(coneClassLikeTypeComputePartialExpansion2, firSession)) {
                        coneClassLikeTypeComputePartialExpansion2 = null;
                    }
                    if (coneClassLikeTypeComputePartialExpansion2 != null) {
                        arrayList.add(coneClassLikeTypeComputePartialExpansion2);
                    }
                }
                List<ConeClassLikeType> list2 = list;
                ArrayList<ConeClassLikeType> arrayList2 = arrayList;
                kotlin.collections.CollectionsKt.addAll(list2, arrayList2);
                if (z) {
                    for (ConeClassLikeType coneClassLikeType : arrayList2) {
                        if (!(coneClassLikeType instanceof ConeErrorType)) {
                            if (z3) {
                                SmartList<ConeClassLikeType> smartList = new SmartList();
                                FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), firSession);
                                if (symbol2 != null) {
                                    collectSuperTypes(symbol2, smartList, set, z, z2, z3, firSession, supertypeSupplier);
                                }
                                ConeSubstitutor coneSubstitutorCreateSubstitutionForSupertype = createSubstitutionForSupertype(coneClassLikeType, firSession);
                                for (ConeClassLikeType coneClassLikeType2 : smartList) {
                                    Intrinsics.checkNotNull(coneClassLikeType2);
                                    ConeKotlinType coneKotlinTypeSubstituteOrSelf = coneSubstitutorCreateSubstitutionForSupertype.substituteOrSelf(coneClassLikeType2);
                                    Intrinsics.checkNotNull(coneKotlinTypeSubstituteOrSelf, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
                                    list2.add((ConeClassLikeType) coneKotlinTypeSubstituteOrSelf);
                                }
                            } else {
                                FirClassLikeSymbol<?> symbol3 = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), firSession);
                                if (symbol3 != null) {
                                    collectSuperTypes(symbol3, list, set, z, z2, z3, firSession, supertypeSupplier);
                                }
                            }
                        }
                    }
                    return;
                }
                return;
            }
            if (firClassifierSymbol instanceof FirTypeAliasSymbol) {
                ConeClassLikeType coneClassLikeTypeExpansionForTypeAlias = supertypeSupplier.expansionForTypeAlias((FirTypeAlias) ((FirTypeAliasSymbol) firClassifierSymbol).getFir(), firSession);
                if (coneClassLikeTypeExpansionForTypeAlias == null || (coneClassLikeTypeComputePartialExpansion = computePartialExpansion(coneClassLikeTypeExpansionForTypeAlias, firSession, supertypeSupplier)) == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeComputePartialExpansion.getLookupTag(), firSession)) == null) {
                    return;
                }
                collectSuperTypes(symbol, list, set, z, z2, z3, firSession, supertypeSupplier);
                return;
            }
            throw new IllegalStateException("?!id:1".toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean isClassBasedType(ConeClassLikeType coneClassLikeType, FirSession firSession) {
        ConeClassLikeLookupTag lookupTag;
        if (coneClassLikeType instanceof ConeErrorType) {
            return false;
        }
        FirClassLikeSymbol<?> symbol = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, firSession);
        FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        if (firClassSymbol == null) {
            return false;
        }
        if (!(firClassSymbol instanceof FirAnonymousObjectSymbol)) {
            if (!(firClassSymbol instanceof FirRegularClassSymbol)) {
                throw new NoWhenBranchMatchedException();
            }
            if (((FirRegularClass) ((FirRegularClassSymbol) firClassSymbol).getFir()).getClassKind() != ClassKind.CLASS) {
                return false;
            }
        }
        return true;
    }

    public static final ConeSubstitutor createSubstitutionForSupertype(ConeLookupTagBasedType superType, FirSession session) {
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassifierSymbol<?> symbol = LookupTagUtilsKt.toSymbol(superType.getLookupTag(), session);
        Object fir = symbol != null ? symbol.getFir() : null;
        FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
        if (firRegularClass == null) {
            return ConeSubstitutor.Empty.INSTANCE;
        }
        ConeTypeProjection[] typeArguments = superType.getTypeArguments();
        ArrayList arrayList = new ArrayList(typeArguments.length);
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            ConeErrorType coneErrorType = coneTypeProjection instanceof ConeKotlinType ? (ConeKotlinType) coneTypeProjection : null;
            if (coneErrorType == null) {
                coneErrorType = new ConeErrorType(new ConeSimpleDiagnostic("illegal projection usage", DiagnosticKind.IllegalProjectionUsage), false, null, null, 14, null);
            }
            arrayList.add(coneErrorType);
        }
        ArrayList arrayList2 = arrayList;
        List<FirTypeParameterRef> typeParameters = firRegularClass.getTypeParameters();
        ArrayList arrayList3 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList3.add(((FirTypeParameterRef) it.next()).getSymbol());
        }
        return new ConeSubstitutorByMap(MapsKt.toMap(kotlin.collections.CollectionsKt.zip(arrayList3, arrayList2)), session);
    }
}
