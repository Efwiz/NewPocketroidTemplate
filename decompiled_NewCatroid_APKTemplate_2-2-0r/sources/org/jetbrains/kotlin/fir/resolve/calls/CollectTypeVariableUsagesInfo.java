package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.inference.ConeTypeParameterBasedTypeVariable;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeDeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: CollectTypeVariableUsagesInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002JF\u0010\u0015\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u00160\u0013*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u001c\b\u0002\u0010\u001c\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u001b\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u00160\u0013H\u0002J\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u0013*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001b*\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J.\u0010 \u001a\u00020\u000f*\u00020\u00192\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u00182\b\b\u0002\u0010%\u001a\u00020\u000fH\u0002J8\u0010&\u001a\u00020\u000f*\u00020\u00192\u0006\u0010!\u001a\u00020\"2\u0006\u0010'\u001a\u00020\u00172\u001a\u0010(\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u00160\u0013H\u0002J(\u0010)\u001a\u00020\u000f*\u00020\u00192\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u00112\n\u0010*\u001a\u0006\u0012\u0002\b\u00030+H\u0002J\f\u0010,\u001a\u00020\u0004*\u00020-H\u0002¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CollectTypeVariableUsagesInfo;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isContainedInInvariantOrContravariantPositionsAmongTypeParameters", "", "checkingTypeVariable", "Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeParameterBasedTypeVariable;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getDependentTypeParameters", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "variable", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "dependentTypeParametersSeen", "getDependingOnTypeParameter", "getTypeParameterByVariable", "typeConstructor", "isContainedInInvariantOrContravariantPositions", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "variableTypeConstructor", "baseType", "wasOutVariance", "isContainedInInvariantOrContravariantPositionsAmongUpperBound", "checkingType", "dependentTypeParameters", "isContainedInInvariantOrContravariantPositionsWithDependencies", "candidateSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectTypeVariableUsagesInfo extends ResolutionStage {
    public static final CollectTypeVariableUsagesInfo INSTANCE = new CollectTypeVariableUsagesInfo();

    private CollectTypeVariableUsagesInfo() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        if (symbol instanceof FirConstructorSymbol) {
            List<FirTypeParameterRef> typeParameters = ((FirConstructor) ((FirConstructorSymbol) symbol).getFir()).getTypeParameters();
            for (ConeTypeVariable coneTypeVariable : candidate.getFreshVariables()) {
                if ((coneTypeVariable instanceof ConeTypeParameterBasedTypeVariable) && isContainedInInvariantOrContravariantPositionsAmongTypeParameters((ConeTypeParameterBasedTypeVariable) coneTypeVariable, typeParameters)) {
                    recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter(coneTypeVariable);
                }
            }
        } else if (symbol instanceof FirCallableSymbol) {
            FirSession session = resolutionContext.getSession();
            for (ConeTypeVariable coneTypeVariable2 : candidate.getFreshVariables()) {
                if ((coneTypeVariable2 instanceof ConeTypeParameterBasedTypeVariable) && isContainedInInvariantOrContravariantPositionsWithDependencies(candidate.getSystem(), session, (ConeTypeParameterBasedTypeVariable) coneTypeVariable2, (FirCallableSymbol) symbol)) {
                    recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter(coneTypeVariable2);
                }
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isContainedInInvariantOrContravariantPositionsAmongTypeParameters(ConeTypeParameterBasedTypeVariable checkingTypeVariable, List<? extends FirTypeParameterRef> typeParameters) {
        List<? extends FirTypeParameterRef> list = typeParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (FirTypeParameterRef firTypeParameterRef : list) {
            if (((FirTypeParameter) firTypeParameterRef.getSymbol().getFir()).getVariance() != Variance.OUT_VARIANCE && Intrinsics.areEqual(firTypeParameterRef.getSymbol(), checkingTypeVariable.getTypeParameterSymbol())) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ boolean isContainedInInvariantOrContravariantPositions$default(CollectTypeVariableUsagesInfo collectTypeVariableUsagesInfo, NewConstraintSystemImpl newConstraintSystemImpl, FirSession firSession, ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor, ConeKotlinType coneKotlinType, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return collectTypeVariableUsagesInfo.isContainedInInvariantOrContravariantPositions(newConstraintSystemImpl, firSession, coneTypeVariableTypeConstructor, coneKotlinType, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isContainedInInvariantOrContravariantPositions(NewConstraintSystemImpl newConstraintSystemImpl, FirSession firSession, ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor, ConeKotlinType coneKotlinType, boolean z) {
        TypeConstructorMarker typeParameterByVariable;
        if (!(coneKotlinType instanceof ConeClassLikeType) || (typeParameterByVariable = getTypeParameterByVariable(newConstraintSystemImpl, coneTypeVariableTypeConstructor)) == null) {
            return false;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) coneKotlinType).getLookupTag(), firSession);
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirClassLikeDeclaration firClassLikeDeclaration2 = firClassLikeDeclaration instanceof FirTypeParameterRefsOwner ? firClassLikeDeclaration : null;
        if (firClassLikeDeclaration2 == null) {
            return false;
        }
        List<FirTypeParameterRef> typeParameters = firClassLikeDeclaration2.getTypeParameters();
        if (typeParameters.size() < coneKotlinType.getTypeArguments().length) {
            return false;
        }
        ConeTypeProjection[] typeArguments = coneKotlinType.getTypeArguments();
        int length = typeArguments.length;
        for (int i = 0; i < length; i++) {
            ConeTypeProjection coneTypeProjection = typeArguments[i];
            ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
            if (type != null && !ConeTypeUtilsKt.isMarkedNullable(type)) {
                boolean z2 = z && (((FirTypeParameter) typeParameters.get(i).getSymbol().getFir()).getVariance() == Variance.OUT_VARIANCE || coneTypeProjection.getKind() == ProjectionKind.OUT);
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = newConstraintSystemImpl.typeConstructor(type);
                if (((Intrinsics.areEqual(typeConstructorMarkerTypeConstructor, typeParameterByVariable) || Intrinsics.areEqual(typeConstructorMarkerTypeConstructor, coneTypeVariableTypeConstructor)) && !z2) || isContainedInInvariantOrContravariantPositions(newConstraintSystemImpl, firSession, coneTypeVariableTypeConstructor, type, z2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isContainedInInvariantOrContravariantPositionsWithDependencies(final NewConstraintSystemImpl newConstraintSystemImpl, FirSession firSession, ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable, FirCallableSymbol<?> firCallableSymbol) {
        boolean z;
        FirTypeRef returnTypeRef = ((FirCallableDeclaration) firCallableSymbol.getFir()).getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = !(type instanceof ConeKotlinType) ? null : type;
        if (coneKotlinType == null) {
            return false;
        }
        ConeTypeVariableTypeConstructor typeConstructor = coneTypeParameterBasedTypeVariable.getTypeConstructor();
        if (isContainedInInvariantOrContravariantPositions$default(this, newConstraintSystemImpl, firSession, typeConstructor, coneKotlinType, false, 8, null)) {
            return true;
        }
        ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor = typeConstructor;
        List<ConeTypeVariableTypeConstructor> dependingOnTypeParameter = getDependingOnTypeParameter(newConstraintSystemImpl, coneTypeVariableTypeConstructor);
        if (!(dependingOnTypeParameter instanceof Collection) || !dependingOnTypeParameter.isEmpty()) {
            Iterator<T> it = dependingOnTypeParameter.iterator();
            while (it.hasNext()) {
                if (isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystemImpl, firSession, (ConeTypeVariableTypeConstructor) it.next(), coneKotlinType, false, 8, null)) {
                    return true;
                }
            }
        }
        List<? extends Pair<ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>> dependentTypeParameters$default = getDependentTypeParameters$default(this, newConstraintSystemImpl, coneTypeVariableTypeConstructor, null, 2, null);
        List<? extends Pair<ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>> list = dependentTypeParameters$default;
        boolean z2 = list instanceof Collection;
        if (z2 && list.isEmpty()) {
            z = z2;
        } else {
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                boolean z3 = z2;
                if (isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystemImpl, firSession, (ConeTypeVariableTypeConstructor) ((Pair) it2.next()).getFirst(), coneKotlinType, false, 8, null)) {
                    return true;
                }
                z2 = z3;
            }
            z = z2;
        }
        if (!isContainedInInvariantOrContravariantPositionsAmongUpperBound(newConstraintSystemImpl, firSession, typeConstructor, dependentTypeParameters$default)) {
            return false;
        }
        if (z && list.isEmpty()) {
            return false;
        }
        Iterator<T> it3 = list.iterator();
        while (it3.hasNext()) {
            final ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor2 = (ConeTypeVariableTypeConstructor) ((Pair) it3.next()).component1();
            if (newConstraintSystemImpl.contains(coneKotlinType, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CollectTypeVariableUsagesInfo$isContainedInInvariantOrContravariantPositionsWithDependencies$3$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it4) {
                    Intrinsics.checkNotNullParameter(it4, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(TypeSystemContextHelpersKt.typeConstructor(it4, newConstraintSystemImpl), CollectTypeVariableUsagesInfo.INSTANCE.getTypeParameterByVariable(newConstraintSystemImpl, coneTypeVariableTypeConstructor2)) && !newConstraintSystemImpl.isMarkedNullable(it4));
                }
            })) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List getDependentTypeParameters$default(CollectTypeVariableUsagesInfo collectTypeVariableUsagesInfo, NewConstraintSystemImpl newConstraintSystemImpl, TypeConstructorMarker typeConstructorMarker, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return collectTypeVariableUsagesInfo.getDependentTypeParameters(newConstraintSystemImpl, typeConstructorMarker, list);
    }

    private final List<Pair<ConeTypeVariableTypeConstructor, ConeKotlinType>> getDependentTypeParameters(final NewConstraintSystemImpl newConstraintSystemImpl, final TypeConstructorMarker typeConstructorMarker, final List<? extends Pair<? extends TypeConstructorMarker, ? extends ConeKotlinType>> list) {
        List<Pair<ConeTypeVariableTypeConstructor, ConeKotlinType>> listEmptyList;
        List list2 = SequencesKt.toList(SequencesKt.filter(SequencesKt.flatMapIterable(MapsKt.asSequence(newConstraintSystemImpl.getBuilder().currentStorage().getNotFixedTypeVariables()), new Function1<Map.Entry<? extends TypeConstructorMarker, ? extends VariableWithConstraints>, List<? extends Pair<? extends ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>>>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CollectTypeVariableUsagesInfo$getDependentTypeParameters$dependentTypeParameters$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<Pair<ConeTypeVariableTypeConstructor, ConeKotlinType>> invoke(Map.Entry<? extends TypeConstructorMarker, ? extends VariableWithConstraints> entry) {
                Object next;
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                TypeConstructorMarker key = entry.getKey();
                VariableWithConstraints value = entry.getValue();
                if (!(key instanceof ConeTypeVariableTypeConstructor)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                List<Constraint> constraints = value.getConstraints();
                ArrayList arrayList = new ArrayList();
                for (Object obj : constraints) {
                    Constraint constraint = (Constraint) obj;
                    if ((constraint.getPosition().getFrom() instanceof ConeDeclaredUpperBoundConstraintPosition) && constraint.getKind() == ConstraintKind.UPPER) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                final NewConstraintSystemImpl newConstraintSystemImpl2 = newConstraintSystemImpl;
                final TypeConstructorMarker typeConstructorMarker2 = typeConstructorMarker;
                ArrayList arrayList3 = new ArrayList();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    Pair pair = null;
                    if (!Intrinsics.areEqual(newConstraintSystemImpl2.typeConstructor(((Constraint) it.next()).getType()), typeConstructorMarker2)) {
                        Iterator it2 = arrayList2.iterator();
                        do {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                        } while (!newConstraintSystemImpl2.contains(((Constraint) next).getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CollectTypeVariableUsagesInfo$getDependentTypeParameters$dependentTypeParameters$1$1$suitableUpperBound$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(KotlinTypeMarker it3) {
                                Intrinsics.checkNotNullParameter(it3, "it");
                                return Boolean.valueOf(Intrinsics.areEqual(newConstraintSystemImpl2.typeConstructor(it3), typeConstructorMarker2));
                            }
                        }));
                        Constraint constraint2 = (Constraint) next;
                        ConeKotlinType coneKotlinType = (ConeKotlinType) (constraint2 != null ? constraint2.getType() : null);
                        if (coneKotlinType != null) {
                            pair = TuplesKt.to(key, coneKotlinType);
                        }
                    } else {
                        pair = TuplesKt.to(key, null);
                    }
                    if (pair != null) {
                        arrayList3.add(pair);
                    }
                }
                return arrayList3;
            }
        }), new Function1<Pair<? extends ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CollectTypeVariableUsagesInfo$getDependentTypeParameters$dependentTypeParameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Pair<ConeTypeVariableTypeConstructor, ? extends ConeKotlinType> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((list.contains(it) || Intrinsics.areEqual(it.getFirst(), typeConstructorMarker)) ? false : true);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Pair<? extends ConeTypeVariableTypeConstructor, ? extends ConeKotlinType> pair) {
                return invoke2((Pair<ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>) pair);
            }
        }));
        List list3 = list2;
        SmartList smartList = new SmartList();
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor = (ConeTypeVariableTypeConstructor) ((Pair) it.next()).component1();
            if (!Intrinsics.areEqual(coneTypeVariableTypeConstructor, typeConstructorMarker)) {
                listEmptyList = INSTANCE.getDependentTypeParameters(newConstraintSystemImpl, coneTypeVariableTypeConstructor, CollectionsKt.plus((Collection) list3, (Iterable) list));
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(smartList, listEmptyList);
        }
        return CollectionsKt.plus((Collection) list3, (Iterable) smartList);
    }

    private final boolean isContainedInInvariantOrContravariantPositionsAmongUpperBound(NewConstraintSystemImpl newConstraintSystemImpl, FirSession firSession, ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor, List<? extends Pair<ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>> list) {
        List<? extends Pair<ConeTypeVariableTypeConstructor, ? extends ConeKotlinType>> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor2 = coneTypeVariableTypeConstructor;
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor3 = (ConeTypeVariableTypeConstructor) pair.component1();
            ConeKotlinType coneKotlinType = (ConeKotlinType) pair.component2();
            if (coneKotlinType == null || isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystemImpl, firSession, coneTypeVariableTypeConstructor2, coneKotlinType, false, 8, null)) {
                return true;
            }
            coneTypeVariableTypeConstructor2 = coneTypeVariableTypeConstructor3;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TypeConstructorMarker getTypeParameterByVariable(NewConstraintSystemImpl newConstraintSystemImpl, ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor) {
        FirTypeParameterSymbol typeParameterSymbol;
        TypeVariableMarker typeVariableMarker = newConstraintSystemImpl.getBuilder().currentStorage().getAllTypeVariables().get(coneTypeVariableTypeConstructor);
        ConeTypeParameterLookupTag lookupTag = null;
        ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable = typeVariableMarker instanceof ConeTypeParameterBasedTypeVariable ? (ConeTypeParameterBasedTypeVariable) typeVariableMarker : null;
        if (coneTypeParameterBasedTypeVariable != null && (typeParameterSymbol = coneTypeParameterBasedTypeVariable.getTypeParameterSymbol()) != null) {
            lookupTag = typeParameterSymbol.getLookupTag();
        }
        return lookupTag;
    }

    private final List<ConeTypeVariableTypeConstructor> getDependingOnTypeParameter(NewConstraintSystemImpl newConstraintSystemImpl, TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = newConstraintSystemImpl.getBuilder().currentStorage().getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Constraint constraint : constraints) {
            ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor = null;
            if ((constraint.getPosition().getFrom() instanceof ConeDeclaredUpperBoundConstraintPosition) && constraint.getKind() == ConstraintKind.UPPER) {
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = newConstraintSystemImpl.typeConstructor(constraint.getType());
                if (typeConstructorMarkerTypeConstructor instanceof ConeTypeVariableTypeConstructor) {
                    coneTypeVariableTypeConstructor = (ConeTypeVariableTypeConstructor) typeConstructorMarkerTypeConstructor;
                }
            }
            if (coneTypeVariableTypeConstructor != null) {
                arrayList.add(coneTypeVariableTypeConstructor);
            }
        }
        return arrayList;
    }

    private final void recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter(ConeTypeVariable coneTypeVariable) {
        coneTypeVariable.getTypeConstructor().recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter();
    }
}
