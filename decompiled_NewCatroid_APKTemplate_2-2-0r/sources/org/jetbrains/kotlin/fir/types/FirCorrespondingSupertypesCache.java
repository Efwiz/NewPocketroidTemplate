package org.jetbrains.kotlin.fir.types;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;

/* JADX INFO: compiled from: FirCorrespondingSupertypesCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J2\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u000bH\u0002J,\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0016\u0018\u00010\b2\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u000bH\u0002J\u001e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dR4\u0010\u0005\u001a(\u0012\u0004\u0012\u00020\u0007\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0018\u00010\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirCorrespondingSupertypesCache;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "captureType", ModuleXmlParser.TYPE, "typeSystemContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "computeSupertypePolicyAndPutInMap", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "supertype", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "resultingMap", "", "", "state", "computeSupertypesMap", "subtypeLookupTag", "getCorrespondingSupertypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "supertypeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCorrespondingSupertypesCache implements FirSessionComponent {
    private final FirCache<ConeClassLikeLookupTag, Map<ConeClassLikeLookupTag, List<ConeClassLikeType>>, TypeCheckerState> cache;
    private final FirSession session;

    public FirCorrespondingSupertypesCache(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.cache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(1000, 0.5f, new Function2<ConeClassLikeLookupTag, TypeCheckerState, Map<ConeClassLikeLookupTag, ? extends List<? extends ConeClassLikeType>>>() { // from class: org.jetbrains.kotlin.fir.types.FirCorrespondingSupertypesCache$cache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Map<ConeClassLikeLookupTag, List<ConeClassLikeType>> invoke(ConeClassLikeLookupTag lookupTag, TypeCheckerState typeCheckerState) {
                Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
                Intrinsics.checkNotNullParameter(typeCheckerState, "typeCheckerState");
                return this.this$0.computeSupertypesMap(lookupTag, typeCheckerState);
            }
        });
    }

    public final List<ConeClassLikeType> getCorrespondingSupertypes(ConeKotlinType type, TypeConstructorMarker supertypeConstructor) {
        List<ConeClassLikeType> orDefault;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(supertypeConstructor, "supertypeConstructor");
        if ((type instanceof ConeClassLikeType) && (supertypeConstructor instanceof ConeClassLikeLookupTag)) {
            ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(this.session);
            TypeCheckerState typeCheckerStateNewTypeCheckerState = typeContext.newTypeCheckerState(false, true);
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            ConeClassLikeLookupTag lookupTag = coneClassLikeType.getLookupTag();
            if (Intrinsics.areEqual(lookupTag, supertypeConstructor)) {
                return CollectionsKt.listOf(captureType(coneClassLikeType, typeContext));
            }
            Map<ConeClassLikeLookupTag, List<ConeClassLikeType>> value = this.cache.getValue(lookupTag, typeCheckerStateNewTypeCheckerState);
            if (value != null && (orDefault = value.getOrDefault(supertypeConstructor, CollectionsKt.emptyList())) != null) {
                if (type.getTypeArguments().length == 0) {
                    return orDefault;
                }
                TypeCheckerState.SupertypesPolicy supertypesPolicySubstitutionSupertypePolicy = typeContext.substitutionSupertypePolicy(captureType(coneClassLikeType, typeContext));
                List<ConeClassLikeType> list = orDefault;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    SimpleTypeMarker simpleTypeMarkerMo7474transformType = supertypesPolicySubstitutionSupertypePolicy.mo7474transformType(typeCheckerStateNewTypeCheckerState, (ConeClassLikeType) it.next());
                    Intrinsics.checkNotNull(simpleTypeMarkerMo7474transformType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
                    arrayList.add((ConeClassLikeType) simpleTypeMarkerMo7474transformType);
                }
                return arrayList;
            }
        }
        return null;
    }

    private final ConeClassLikeType captureType(ConeClassLikeType type, ConeTypeContext typeSystemContext) {
        ConeClassLikeType coneClassLikeType = type;
        SimpleTypeMarker simpleTypeMarkerCaptureFromArguments = typeSystemContext.captureFromArguments(coneClassLikeType, CaptureStatus.FOR_SUBTYPING);
        if (simpleTypeMarkerCaptureFromArguments != null) {
            coneClassLikeType = simpleTypeMarkerCaptureFromArguments;
        }
        Intrinsics.checkNotNull(coneClassLikeType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
        return (ConeClassLikeType) coneClassLikeType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final Map<ConeClassLikeLookupTag, List<ConeClassLikeType>> computeSupertypesMap(ConeClassLikeLookupTag subtypeLookupTag, TypeCheckerState state) {
        FirClassLikeDeclaration firClassLikeDeclaration;
        ConeLookupTagBasedType[] coneLookupTagBasedTypeArr;
        List<FirTypeParameterRef> typeParameters;
        HashMap map = new HashMap();
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(subtypeLookupTag, this.session);
        if (symbol != null && (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) != null) {
            FirClassLikeDeclaration firClassLikeDeclaration2 = firClassLikeDeclaration instanceof FirTypeParameterRefsOwner ? firClassLikeDeclaration : null;
            if (firClassLikeDeclaration2 == null || (typeParameters = firClassLikeDeclaration2.getTypeParameters()) == null) {
                coneLookupTagBasedTypeArr = null;
            } else {
                List<FirTypeParameterRef> list = typeParameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(TypeConstructionUtilsKt.constructType$default((ConeClassifierLookupTag) ((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null));
                }
                coneLookupTagBasedTypeArr = (ConeLookupTagBasedType[]) arrayList.toArray(new ConeLookupTagBasedType[0]);
            }
            if (coneLookupTagBasedTypeArr == null) {
                coneLookupTagBasedTypeArr = new ConeLookupTagBasedType[0];
            }
            ConeClassLikeType coneClassLikeTypeConstructClassType$default = TypeConstructionUtilsKt.constructClassType$default(subtypeLookupTag, coneLookupTagBasedTypeArr, false, null, 4, null);
            if ((coneClassLikeTypeConstructClassType$default instanceof ConeClassLikeType) && LookupTagUtilsKt.toSymbol(coneClassLikeTypeConstructClassType$default.getLookupTag(), this.session) != null) {
                state.initialize();
                ArrayDeque<SimpleTypeMarker> supertypesDeque = state.getSupertypesDeque();
                Intrinsics.checkNotNull(supertypesDeque);
                Set<SimpleTypeMarker> supertypesSet = state.getSupertypesSet();
                Intrinsics.checkNotNull(supertypesSet);
                supertypesDeque.push(coneClassLikeTypeConstructClassType$default);
                while (!supertypesDeque.isEmpty()) {
                    if (supertypesSet.size() > 1000) {
                        throw new IllegalStateException(("Too many supertypes for type: " + coneClassLikeTypeConstructClassType$default + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
                    }
                    SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
                    Intrinsics.checkNotNull(simpleTypeMarkerPop);
                    if (supertypesSet.add(simpleTypeMarkerPop)) {
                        TypeCheckerState.SupertypesPolicy supertypesPolicyComputeSupertypePolicyAndPutInMap = computeSupertypePolicyAndPutInMap(simpleTypeMarkerPop, map, state);
                        if (Intrinsics.areEqual(supertypesPolicyComputeSupertypePolicyAndPutInMap, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                            supertypesPolicyComputeSupertypePolicyAndPutInMap = null;
                        }
                        if (supertypesPolicyComputeSupertypePolicyAndPutInMap == null) {
                            continue;
                        } else {
                            TypeSystemContext typeSystemContext = state.getTypeSystemContext();
                            Iterator<KotlinTypeMarker> it2 = typeSystemContext.supertypes(typeSystemContext.typeConstructor(simpleTypeMarkerPop)).iterator();
                            while (it2.hasNext()) {
                                SimpleTypeMarker simpleTypeMarkerMo7474transformType = supertypesPolicyComputeSupertypePolicyAndPutInMap.mo7474transformType(state, it2.next());
                                if (!(simpleTypeMarkerMo7474transformType instanceof ConeClassLikeType) || LookupTagUtilsKt.toSymbol(((ConeClassLikeType) simpleTypeMarkerMo7474transformType).getLookupTag(), this.session) == null) {
                                    state.clear();
                                } else {
                                    supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                                }
                            }
                        }
                    }
                }
                state.clear();
                map.remove(subtypeLookupTag);
                return map;
            }
        }
        return null;
    }

    private final TypeCheckerState.SupertypesPolicy computeSupertypePolicyAndPutInMap(SimpleTypeMarker supertype, Map<ConeClassLikeLookupTag, List<ConeClassLikeType>> resultingMap, TypeCheckerState state) {
        Intrinsics.checkNotNull(supertype, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) supertype;
        ConeClassLikeLookupTag lookupTag = coneClassLikeType.getLookupTag();
        ConeClassLikeType coneClassLikeType2 = (ConeClassLikeType) state.getTypeSystemContext().captureFromArguments(supertype, CaptureStatus.FOR_SUBTYPING);
        if (coneClassLikeType2 != null) {
            coneClassLikeType = coneClassLikeType2;
        }
        final FirCorrespondingSupertypesCache$computeSupertypePolicyAndPutInMap$list$1 firCorrespondingSupertypesCache$computeSupertypePolicyAndPutInMap$list$1 = new Function1<ConeClassLikeLookupTag, List<ConeClassLikeType>>() { // from class: org.jetbrains.kotlin.fir.types.FirCorrespondingSupertypesCache$computeSupertypePolicyAndPutInMap$list$1
            @Override // kotlin.jvm.functions.Function1
            public final List<ConeClassLikeType> invoke(ConeClassLikeLookupTag it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new ArrayList();
            }
        };
        List<ConeClassLikeType> listComputeIfAbsent = resultingMap.computeIfAbsent(lookupTag, new Function() { // from class: org.jetbrains.kotlin.fir.types.FirCorrespondingSupertypesCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return FirCorrespondingSupertypesCache.computeSupertypePolicyAndPutInMap$lambda$5(firCorrespondingSupertypesCache$computeSupertypePolicyAndPutInMap$list$1, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listComputeIfAbsent, "computeIfAbsent(...)");
        listComputeIfAbsent.add(coneClassLikeType);
        if (state.getTypeSystemContext().argumentsCount(coneClassLikeType) == 0) {
            return TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
        }
        return state.getTypeSystemContext().substitutionSupertypePolicy(coneClassLikeType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List computeSupertypePolicyAndPutInMap$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (List) tmp0.invoke(obj);
    }
}
