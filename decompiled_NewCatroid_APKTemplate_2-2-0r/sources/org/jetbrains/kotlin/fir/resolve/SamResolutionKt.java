package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeIntermediateDiagnostic;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: SamResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u00182\u0006\u0010\u0014\u001a\u00020\fH\u0002\u001a,\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u00182\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001H\u0002\u001a\u0014\u0010\u001e\u001a\u00020\u001f*\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\fH\u0002\u001a\u001e\u0010 \u001a\u0004\u0018\u00010\u001a*\u00020\u00182\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\f\u0010!\u001a\u00020\u0006*\u00020\u0018H\u0002\u001a\u0012\u0010\"\u001a\u00020\u0006*\u00020\u001a2\u0006\u0010#\u001a\u00020\u0006\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u001f\u0010\n\u001a\u00020\u000b*\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006$"}, d2 = {"PUBLIC_METHOD_NAMES_IN_OBJECT", "", "", "SAM_PARAMETER_NAME", "Lorg/jetbrains/kotlin/name/Name;", "resolvedIsAbstract", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getResolvedIsAbstract", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "samConstructorStorage", "Lorg/jetbrains/kotlin/fir/resolve/FirSamConstructorStorage;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSamConstructorStorage", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/FirSamConstructorStorage;", "samConstructorStorage$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "buildSubstitutorForSamTypeAlias", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "session", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "computeSamCandidateNames", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "findSingleAbstractMethodByNames", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "samCandidateNames", "getFunctionTypeForAbstractMethod", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "getSingleAbstractMethodOrNull", "hasMoreThenOneAbstractFunctionOrHasAbstractProperty", "isPublicInObject", "checkOnlyName", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SamResolutionKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(SamResolutionKt.class, "resolve"), "samConstructorStorage", "getSamConstructorStorage(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/FirSamConstructorStorage;"))};
    private static final Set<String> PUBLIC_METHOD_NAMES_IN_OBJECT;
    private static final Name SAM_PARAMETER_NAME;
    private static final ArrayMapAccessor samConstructorStorage$delegate;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x0089  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeSubstitutor buildSubstitutorForSamTypeAlias(FirTypeParameterRefsOwner firTypeParameterRefsOwner, FirSession firSession, ConeClassLikeType coneClassLikeType) {
        ConeClassLikeType type;
        if (firTypeParameterRefsOwner.getTypeParameters().isEmpty()) {
            return null;
        }
        List<Pair> listZip = CollectionsKt.zip(firTypeParameterRefsOwner.getTypeParameters(), coneClassLikeType.getTypeArguments());
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
        for (Pair pair : listZip) {
            FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) pair.component1();
            ConeTypeProjection coneTypeProjection = (ConeTypeProjection) pair.component2();
            ConeKotlinTypeProjection coneKotlinTypeProjection = coneTypeProjection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) coneTypeProjection : null;
            if (coneKotlinTypeProjection == null || (type = coneKotlinTypeProjection.getType()) == null) {
                FirTypeRef firTypeRef = (FirTypeRef) CollectionsKt.firstOrNull((List) ((FirTypeParameter) firTypeParameterRef.getSymbol().getFir()).getBounds());
                if (firTypeRef == null) {
                    type = null;
                } else {
                    FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
                    type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    if (!(type instanceof ConeKotlinType)) {
                        type = null;
                    }
                }
                if (type == null) {
                    type = firSession.getBuiltinTypes().getNullableAnyType().getType();
                }
            }
            Pair pair2 = new Pair(firTypeParameterRef.getSymbol(), type);
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        return SubstitutorsKt.substitutorByMap(linkedHashMap, firSession);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirSimpleFunction getSingleAbstractMethodOrNull(FirRegularClass firRegularClass, FirSession firSession, ScopeSession scopeSession) {
        if (firRegularClass.getClassKind() != ClassKind.INTERFACE || hasMoreThenOneAbstractFunctionOrHasAbstractProperty(firRegularClass)) {
            return null;
        }
        return findSingleAbstractMethodByNames(firRegularClass, firSession, scopeSession, computeSamCandidateNames(firRegularClass, firSession));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final Set<Name> computeSamCandidateNames(FirRegularClass firRegularClass, FirSession firSession) {
        List listLookupSuperTypes$default = SupertypeUtilsKt.lookupSuperTypes$default((FirClass) firRegularClass, true, true, firSession, false, (SupertypeSupplier) null, 32, (Object) null);
        List listMutableListOf = CollectionsKt.mutableListOf(firRegularClass);
        Iterator it = listLookupSuperTypes$default.iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(FirSymbolProviderKt.getSymbolProvider(firSession), ((ConeClassLikeType) it.next()).getLookupTag());
            FirRegularClassSymbol firRegularClassSymbol = symbolByLookupTag instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbolByLookupTag : null;
            FirRegularClass firRegularClass2 = firRegularClassSymbol != null ? (FirRegularClass) firRegularClassSymbol.getFir() : null;
            if (firRegularClass2 != null) {
                listMutableListOf.add(firRegularClass2);
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it2 = listMutableListOf.iterator();
        while (it2.hasNext()) {
            for (FirDeclaration firDeclaration : ((FirRegularClass) it2.next()).getDeclarations()) {
                if (firDeclaration instanceof FirProperty) {
                    if (getResolvedIsAbstract((FirCallableDeclaration) firDeclaration)) {
                        linkedHashSet.add(((FirProperty) firDeclaration).getName());
                    }
                } else if ((firDeclaration instanceof FirSimpleFunction) && getResolvedIsAbstract((FirCallableDeclaration) firDeclaration)) {
                    linkedHashSet.add(((FirSimpleFunction) firDeclaration).getName());
                }
            }
        }
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirSimpleFunction findSingleAbstractMethodByNames(FirRegularClass firRegularClass, FirSession firSession, ScopeSession scopeSession, Set<Name> set) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) firRegularClass, firSession, scopeSession, false, (FirResolvePhase) null);
        for (Name name2 : set) {
            if (booleanRef.element) {
                break;
            }
            firTypeScopeUnsubstitutedScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.SamResolutionKt.findSingleAbstractMethodByNames.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if ((it instanceof FirPropertySymbol) && SamResolutionKt.getResolvedIsAbstract((FirCallableDeclaration) ((FirPropertySymbol) it).getFir())) {
                        booleanRef.element = true;
                    }
                }
            });
            if (booleanRef.element) {
                break;
            }
            firTypeScopeUnsubstitutedScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.SamResolutionKt.findSingleAbstractMethodByNames.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke2(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r2v2, types: [T, org.jetbrains.kotlin.fir.declarations.FirSimpleFunction] */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                    Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                    ?? r2 = (FirSimpleFunction) functionSymbol.getFir();
                    if (!SamResolutionKt.getResolvedIsAbstract((FirCallableDeclaration) r2) || SamResolutionKt.isPublicInObject(r2, false)) {
                        return;
                    }
                    if (objectRef.element != null) {
                        booleanRef.element = true;
                    } else {
                        objectRef.element = r2;
                    }
                }
            });
        }
        if (!booleanRef.element && objectRef.element != 0) {
            T t = objectRef.element;
            Intrinsics.checkNotNull(t);
            if (((FirSimpleFunction) t).getTypeParameters().isEmpty()) {
                return (FirSimpleFunction) objectRef.element;
            }
        }
        return null;
    }

    private static final boolean hasMoreThenOneAbstractFunctionOrHasAbstractProperty(FirRegularClass firRegularClass) {
        boolean z = false;
        for (FirDeclaration firDeclaration : firRegularClass.getDeclarations()) {
            if ((firDeclaration instanceof FirProperty) && getResolvedIsAbstract((FirCallableDeclaration) firDeclaration)) {
                return true;
            }
            if ((firDeclaration instanceof FirSimpleFunction) && getResolvedIsAbstract((FirCallableDeclaration) firDeclaration) && !isPublicInObject((FirSimpleFunction) firDeclaration, true)) {
                if (z) {
                    return true;
                }
                z = true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getResolvedIsAbstract(FirCallableDeclaration firCallableDeclaration) {
        return firCallableDeclaration.getSymbol().getResolvedStatus().getModality() == Modality.ABSTRACT;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:
    
        if (r4.equals("getClass") != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:
    
        if (r4.equals("notifyAll") != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0046, code lost:
    
        if (r4.equals("hashCode") != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ad, code lost:
    
        if (r4.equals("notify") != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d7, code lost:
    
        if (r4.equals("toString") != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e1, code lost:
    
        return r3.getValueParameters().isEmpty();
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean isPublicInObject(org.jetbrains.kotlin.fir.declarations.FirSimpleFunction r3, boolean r4) {
        /*
            Method dump skipped, instruction units count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.SamResolutionKt.isPublicInObject(org.jetbrains.kotlin.fir.declarations.FirSimpleFunction, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConeLookupTagBasedType getFunctionTypeForAbstractMethod(FirSimpleFunction firSimpleFunction, FirSession firSession) {
        ConeErrorType coneType;
        FirTypeRef typeRef;
        List<FirValueParameter> valueParameters = firSimpleFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (true) {
            coneType = null;
            if (!it.hasNext()) {
                break;
            }
            FirValueParameter firValueParameter = (FirValueParameter) it.next();
            FirTypeRef returnTypeRef = firValueParameter.getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            coneType = type instanceof ConeKotlinType ? type : null;
            if (coneType == null) {
                coneType = new ConeErrorType(new ConeIntermediateDiagnostic("No type for parameter " + firValueParameter), false, null, null, 14, null);
            }
            arrayList.add(coneType);
        }
        ArrayList arrayList2 = arrayList;
        List<FirContextReceiver> contextReceivers = firSimpleFunction.getContextReceivers();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        for (FirContextReceiver firContextReceiver : contextReceivers) {
            FirTypeRef typeRef2 = firContextReceiver.getTypeRef();
            FirResolvedTypeRef firResolvedTypeRef2 = typeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef2 : null;
            ConeErrorType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
            if (!(type2 instanceof ConeKotlinType)) {
                type2 = null;
            }
            if (type2 == null) {
                type2 = new ConeErrorType(new ConeIntermediateDiagnostic("No type for context receiver " + firContextReceiver), false, null, null, 14, null);
            }
            arrayList3.add(type2);
        }
        ArrayList arrayList4 = arrayList3;
        FunctionTypeKind.Function functionExtractSingleSpecialKindForFunction = FirFunctionTypeKindServiceKt.getFunctionTypeService(firSession).extractSingleSpecialKindForFunction(firSimpleFunction.getSymbol());
        if (functionExtractSingleSpecialKindForFunction == null) {
            functionExtractSingleSpecialKindForFunction = FunctionTypeKind.Function.INSTANCE;
        }
        FirReceiverParameter receiverParameter = firSimpleFunction.getReceiverParameter();
        if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null) {
            coneType = FirTypeUtilsKt.getConeType(typeRef);
        }
        return ResolveUtilsKt.createFunctionType(functionExtractSingleSpecialKindForFunction, arrayList2, coneType, FirTypeUtilsKt.getConeType(firSimpleFunction.getReturnTypeRef()), arrayList4);
    }

    static {
        Name nameIdentifier = Name.identifier("function");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        SAM_PARAMETER_NAME = nameIdentifier;
        PUBLIC_METHOD_NAMES_IN_OBJECT = SetsKt.setOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode", "getClass", "wait", "notify", "notifyAll", "toString"});
        samConstructorStorage$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirSamConstructorStorage.class), (Object) null, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirSamConstructorStorage getSamConstructorStorage(FirSession firSession) {
        return (FirSamConstructorStorage) samConstructorStorage$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
