package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirNamedArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnsupportedCallableReferenceTarget;
import org.jetbrains.kotlin.fir.resolve.inference.InputOutputTypes;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedAtomsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.expressions.CoercionStrategy;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a8\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a4\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\u0006\u0010\u001a\u001a\u00020\u0001H\u0002\u001a*\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a.\u0010 \u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020!0\u000b2\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020!H\u0002\u001a\u0014\u0010&\u001a\u00020\u0006*\u00020\u001d2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a0\u0010'\u001a\u0004\u0018\u00010\f*\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010+\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001a\u001a\u00020\u0001H\u0002\u001a\u0014\u0010,\u001a\u00020\u0001*\u00020\u00172\u0006\u0010-\u001a\u00020$H\u0002\u001a\n\u0010.\u001a\u00020\u0006*\u00020\u0007\u001a\u000e\u0010/\u001a\u00020\u0006*\u0004\u0018\u00010\fH\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\b\"\u0015\u0010\t\u001a\u00020\u0006*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\t\u0010\b¨\u00060"}, d2 = {"index", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getIndex", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)I", "isBaseTypeForNumberedReferenceTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "isUnitOrFlexibleUnit", "buildReflectionType", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceAdaptation;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "receiverType", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "createFakeArgumentsForReference", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "expectedArgumentCount", "inputTypes", "unboundReceiverCount", "createKPropertyType", "propertyOrField", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "varargParameterTypeByExpectedParameter", "Lorg/jetbrains/kotlin/fir/resolve/calls/VarargMappingState;", "expectedParameterType", "substitutedParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "varargMappingState", "canBeMutableReference", "getCallableReferenceAdaptation", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "expectedType", "indexOf", "valueParameter", "isKCallableType", "needCompatibilityResolveForCallableReference", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceResolutionKt {

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VarargMappingState.values().length];
            try {
                iArr[VarargMappingState.MAPPED_WITH_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VarargMappingState.MAPPED_WITH_PLAIN_ARGS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VarargMappingState.UNMAPPED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:21:0x007c  */
    /* JADX WARN: Code duplicated, block: B:24:0x0092 A[LOOP:1: B:22:0x008c->B:24:0x0092, LOOP_END] */
    public static final Pair<ConeKotlinType, CallableReferenceAdaptation> buildReflectionType(FirCallableDeclaration firCallableDeclaration, ConeKotlinType coneKotlinType, Candidate candidate, ResolutionContext resolutionContext) {
        ConeClassLikeType coneType;
        Iterator<T> it;
        FunctionTypeKind.Function functionSpecialFunctionTypeKind;
        CallableReferenceConversionStrategy suspendConversionStrategy;
        FirResolvedTypeRef firResolvedTypeRefTryCalculateReturnType = resolutionContext.getBodyResolveComponents().getReturnTypeCalculator().tryCalculateReturnType(firCallableDeclaration);
        if (!(firCallableDeclaration instanceof FirFunction)) {
            return firCallableDeclaration instanceof FirVariable ? TuplesKt.to(createKPropertyType((FirVariable) firCallableDeclaration, coneKotlinType, firResolvedTypeRefTryCalculateReturnType, candidate), null) : TuplesKt.to(new ConeErrorType(new ConeUnsupportedCallableReferenceTarget(candidate), false, null, null, 14, null), null);
        }
        int i = coneKotlinType != null ? 1 : 0;
        BodyResolveComponents bodyResolveComponents = resolutionContext.getBodyResolveComponents();
        FirSession session = resolutionContext.getSession();
        FirFunction firFunction = (FirFunction) firCallableDeclaration;
        ConeKotlinType expectedType = candidate.getCallInfo().getExpectedType();
        CallableReferenceAdaptation callableReferenceAdaptation = getCallableReferenceAdaptation(bodyResolveComponents, session, firFunction, expectedType != null ? ConeTypeUtilsKt.lowerBoundIfFlexible(expectedType) : null, i);
        ArrayList arrayList = new ArrayList();
        if (firCallableDeclaration.getReceiverParameter() == null && coneKotlinType != null) {
            arrayList.add(coneKotlinType);
        }
        if (callableReferenceAdaptation != null) {
            CollectionsKt.addAll(arrayList, callableReferenceAdaptation.getArgumentTypes());
            if (callableReferenceAdaptation.getCoercionStrategy() == CoercionStrategy.COERCION_TO_UNIT) {
                coneType = resolutionContext.getSession().getBuiltinTypes().getUnitType().getType();
            } else {
                coneType = FirTypeUtilsKt.getConeType(firResolvedTypeRefTryCalculateReturnType);
            }
            if (coneType == null) {
                coneType = FirTypeUtilsKt.getConeType(firResolvedTypeRefTryCalculateReturnType);
                it = firFunction.getValueParameters().iterator();
                while (it.hasNext()) {
                    arrayList.add(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef()));
                }
            }
        } else {
            coneType = FirTypeUtilsKt.getConeType(firResolvedTypeRefTryCalculateReturnType);
            it = firFunction.getValueParameters().iterator();
            while (it.hasNext()) {
                arrayList.add(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef()));
            }
        }
        if ((callableReferenceAdaptation == null || (suspendConversionStrategy = callableReferenceAdaptation.getSuspendConversionStrategy()) == null || (functionSpecialFunctionTypeKind = suspendConversionStrategy.getKind()) == null) && (functionSpecialFunctionTypeKind = FunctionalTypeUtilsKt.specialFunctionTypeKind(firFunction, resolutionContext.getSession())) == null) {
            functionSpecialFunctionTypeKind = FunctionTypeKind.Function.INSTANCE;
        }
        FunctionTypeKind functionTypeKindReflectKind = callableReferenceAdaptation == null ? functionSpecialFunctionTypeKind.reflectKind() : functionSpecialFunctionTypeKind.nonReflectKind();
        if (firCallableDeclaration.getReceiverParameter() == null) {
            coneKotlinType = null;
        }
        List<FirContextReceiver> contextReceivers = firCallableDeclaration.getContextReceivers();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it2 = contextReceivers.iterator();
        while (it2.hasNext()) {
            arrayList2.add(FirTypeUtilsKt.getConeType(((FirContextReceiver) it2.next()).getTypeRef()));
        }
        return TuplesKt.to(ResolveUtilsKt.createFunctionType(functionTypeKindReflectKind, arrayList, coneKotlinType, coneType, arrayList2), callableReferenceAdaptation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean needCompatibilityResolveForCallableReference(CallableReferenceAdaptation callableReferenceAdaptation) {
        if (callableReferenceAdaptation == null) {
            return false;
        }
        if (callableReferenceAdaptation.getDefaults() == 0 && Intrinsics.areEqual(callableReferenceAdaptation.getSuspendConversionStrategy(), CallableReferenceConversionStrategy.NoConversion.INSTANCE) && callableReferenceAdaptation.getCoercionStrategy() == CoercionStrategy.NO_COERCION) {
            Collection<ResolvedCallArgument> collectionValues = callableReferenceAdaptation.getMappedArguments().values();
            if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                return false;
            }
            Iterator<T> it = collectionValues.iterator();
            while (it.hasNext()) {
                if (((ResolvedCallArgument) it.next()) instanceof ResolvedCallArgument.VarargArgument) {
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:94:0x020f  */
    private static final CallableReferenceAdaptation getCallableReferenceAdaptation(BodyResolveComponents bodyResolveComponents, FirSession firSession, FirFunction firFunction, ConeKotlinType coneKotlinType, int i) {
        InputOutputTypes inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType;
        CoercionStrategy coercionStrategy;
        Map mapEmptyMap;
        CallableReferenceConversionStrategy.NoConversion customConversion;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        VarargMappingState varargMappingStateComponent2;
        ConeKotlinType coneKotlinTypeComponent1;
        int i2 = i;
        if (coneKotlinType == null || isKCallableType(coneKotlinType) || (inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType = PostponedAtomsKt.extractInputOutputTypesFromCallableReferenceExpectedType(coneKotlinType, firSession)) == null) {
            return null;
        }
        List<ConeKotlinType> listComponent1 = inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.component1();
        ConeKotlinType outputType = inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getOutputType();
        int size = listComponent1.size() - i2;
        if (size < 0) {
            return null;
        }
        List<FirExpression> listCreateFakeArgumentsForReference = createFakeArgumentsForReference(firFunction, size, listComponent1, i2);
        ConeSimpleKotlinType dispatchReceiverType = firFunction.getDispatchReceiverType();
        ArgumentMapping argumentMappingMapArguments = FirArgumentsToParametersMapperKt.mapArguments(bodyResolveComponents, listCreateFakeArgumentsForReference, firFunction, dispatchReceiverType != null ? ScopeUtilsKt.scope(dispatchReceiverType, firSession, bodyResolveComponents.getScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS) : null, false);
        List<ResolutionDiagnostic> diagnostics = argumentMappingMapArguments.getDiagnostics();
        if (!(diagnostics instanceof Collection) || !diagnostics.isEmpty()) {
            Iterator<T> it = diagnostics.iterator();
            while (it.hasNext()) {
                if (!CandidateApplicabilityKt.isSuccess(((ResolutionDiagnostic) it.next()).getApplicability())) {
                    return null;
                }
            }
        }
        VarargMappingState varargMappingState = VarargMappingState.UNMAPPED;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        int size2 = listCreateFakeArgumentsForReference.size();
        ConeKotlinType[] coneKotlinTypeArr = new ConeKotlinType[size2];
        Iterator<Map.Entry<FirValueParameter, ResolvedCallArgument>> it2 = argumentMappingMapArguments.getParameterToCallArgumentMap().entrySet().iterator();
        int i3 = 0;
        while (it2.hasNext()) {
            Map.Entry<FirValueParameter, ResolvedCallArgument> next = it2.next();
            FirValueParameter key = next.getKey();
            ResolvedCallArgument value = next.getValue();
            for (FirExpression firExpression : value.getArguments()) {
                int index = getIndex(firExpression);
                Iterator<Map.Entry<FirValueParameter, ResolvedCallArgument>> it3 = it2;
                FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.getOrNull(firFunction.getValueParameters(), indexOf(firFunction, key));
                if (firValueParameter != null) {
                    if (firValueParameter.getIsVararg()) {
                        Pair<ConeKotlinType, VarargMappingState> pairVarargParameterTypeByExpectedParameter = varargParameterTypeByExpectedParameter(listComponent1.get(index + i2), firValueParameter, varargMappingState);
                        coneKotlinTypeComponent1 = pairVarargParameterTypeByExpectedParameter.component1();
                        varargMappingStateComponent2 = pairVarargParameterTypeByExpectedParameter.component2();
                        int i4 = WhenMappings.$EnumSwitchMapping$0[varargMappingStateComponent2.ordinal()];
                        if (i4 == 1) {
                            linkedHashMap.put(key, new ResolvedCallArgument.SimpleArgument(firExpression));
                        } else if (i4 == 2) {
                            LinkedHashMap linkedHashMap3 = linkedHashMap2;
                            Object obj = linkedHashMap3.get(key);
                            if (obj == null) {
                                obj = (List) new ArrayList();
                                linkedHashMap3.put(key, obj);
                            }
                            ((List) obj).add(firExpression);
                        }
                    } else {
                        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
                        linkedHashMap.put(key, value);
                        varargMappingStateComponent2 = varargMappingState;
                        coneKotlinTypeComponent1 = coneType;
                    }
                    coneKotlinTypeArr[index] = coneKotlinTypeComponent1;
                    i2 = i;
                    varargMappingState = varargMappingStateComponent2;
                }
                it2 = it3;
            }
            Iterator<Map.Entry<FirValueParameter, ResolvedCallArgument>> it4 = it2;
            if (Intrinsics.areEqual(value, ResolvedCallArgument.DefaultArgument.INSTANCE)) {
                i3++;
                linkedHashMap.put(key, value);
            }
            i2 = i;
            it2 = it4;
        }
        for (int i5 = 0; i5 < size2; i5++) {
            if (coneKotlinTypeArr[i5] == null) {
                return null;
            }
        }
        LinkedHashMap linkedHashMap4 = linkedHashMap2;
        for (Map.Entry entry : linkedHashMap4.entrySet()) {
            linkedHashMap.put((FirValueParameter) entry.getKey(), new ResolvedCallArgument.VarargArgument((List) entry.getValue()));
        }
        boolean z = !linkedHashMap4.isEmpty();
        for (FirValueParameter firValueParameter2 : firFunction.getValueParameters()) {
            if (firValueParameter2.getIsVararg()) {
                LinkedHashMap linkedHashMap5 = linkedHashMap;
                if (!linkedHashMap5.containsKey(firValueParameter2)) {
                    linkedHashMap5.put(firValueParameter2, new ResolvedCallArgument.VarargArgument(CollectionsKt.emptyList()));
                    z = true;
                }
            }
        }
        FirTypeRef returnTypeRef = firFunction.getReturnTypeRef();
        if (!isUnitOrFlexibleUnit(outputType)) {
            coercionStrategy = CoercionStrategy.NO_COERCION;
        } else {
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            if (type == null || (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(type, firSession)) == null || !ConeBuiltinTypeUtilsKt.isUnit(coneKotlinTypeFullyExpandedType)) {
                coercionStrategy = CoercionStrategy.COERCION_TO_UNIT;
            } else {
                coercionStrategy = CoercionStrategy.NO_COERCION;
            }
        }
        CoercionStrategy coercionStrategy2 = coercionStrategy;
        if (isBaseTypeForNumberedReferenceTypes(coneKotlinType)) {
            mapEmptyMap = MapsKt.emptyMap();
        } else {
            mapEmptyMap = linkedHashMap;
        }
        FunctionTypeKind functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(coneKotlinType, firSession);
        if (functionTypeKind == null || FunctionTypeKindKt.isBasicFunctionOrKFunction(functionTypeKind)) {
            functionTypeKind = null;
        }
        FunctionTypeKind functionTypeKindSpecialFunctionTypeKind = FunctionalTypeUtilsKt.specialFunctionTypeKind(firFunction, firSession);
        if (functionTypeKind != null && functionTypeKindSpecialFunctionTypeKind == null) {
            customConversion = new CallableReferenceConversionStrategy.CustomConversion(functionTypeKind);
        } else {
            customConversion = CallableReferenceConversionStrategy.NoConversion.INSTANCE;
        }
        if (i3 == 0 && !z && coercionStrategy2 == CoercionStrategy.NO_COERCION && Intrinsics.areEqual(customConversion, CallableReferenceConversionStrategy.NoConversion.INSTANCE)) {
            return null;
        }
        return new CallableReferenceAdaptation(coneKotlinTypeArr, coercionStrategy2, i3, mapEmptyMap, customConversion);
    }

    private static final Pair<ConeKotlinType, VarargMappingState> varargParameterTypeByExpectedParameter(ConeKotlinType coneKotlinType, FirValueParameter firValueParameter, VarargMappingState varargMappingState) {
        ConeKotlinType coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()));
        if (coneKotlinTypeArrayElementType != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[varargMappingState.ordinal()];
            if (i == 1) {
                return TuplesKt.to(null, VarargMappingState.MAPPED_WITH_ARRAY);
            }
            if (i == 2) {
                if (ArrayUtilsKt.isPotentiallyArray(coneKotlinType)) {
                    return TuplesKt.to(null, VarargMappingState.MAPPED_WITH_PLAIN_ARGS);
                }
                return TuplesKt.to(coneKotlinTypeArrayElementType, VarargMappingState.MAPPED_WITH_PLAIN_ARGS);
            }
            if (i == 3) {
                if (ArrayUtilsKt.isPotentiallyArray(coneKotlinType)) {
                    return TuplesKt.to(ArrayUtilsKt.createOutArrayType$default(coneKotlinTypeArrayElementType, false, false, 3, null), VarargMappingState.MAPPED_WITH_ARRAY);
                }
                return TuplesKt.to(coneKotlinTypeArrayElementType, VarargMappingState.MAPPED_WITH_PLAIN_ARGS);
            }
            throw new NoWhenBranchMatchedException();
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Vararg parameter " + firValueParameter.getClass() + " does not have vararg type", null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, "expectedParameterType", coneKotlinType);
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "substitutedParameter", firValueParameter);
        exceptionAttachmentBuilder.withEntry("varargMappingState", varargMappingState.toString());
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    private static final int indexOf(FirFunction firFunction, FirValueParameter firValueParameter) {
        return firFunction.getValueParameters().indexOf(firValueParameter);
    }

    public static final boolean isUnitOrFlexibleUnit(ConeKotlinType coneKotlinType) {
        ClassId classId;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType);
        if (ConeTypeUtilsKt.isNullable(coneSimpleKotlinTypeLowerBoundIfFlexible) || (classId = ConeTypeUtilsKt.getClassId(coneSimpleKotlinTypeLowerBoundIfFlexible)) == null) {
            return false;
        }
        return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUnit());
    }

    private static final boolean isBaseTypeForNumberedReferenceTypes(ConeKotlinType coneKotlinType) {
        ClassId classId = ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType));
        if (classId == null) {
            return false;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getKProperty()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getKMutableProperty())) {
            return true;
        }
        return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getKCallable());
    }

    private static final int getIndex(FirExpression firExpression) {
        if (firExpression instanceof FirNamedArgumentExpression) {
            return getIndex(((FirNamedArgumentExpression) firExpression).getExpression());
        }
        if (firExpression instanceof FirFakeArgumentForCallableReference) {
            return ((FirFakeArgumentForCallableReference) firExpression).getIndex();
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0056  */
    private static final List<FirExpression> createFakeArgumentsForReference(FirFunction firFunction, int i, List<? extends ConeKotlinType> list, int i2) {
        Name name2;
        FirExpression firFakeArgumentForCallableReference;
        IntRange intRangeUntil = RangesKt.until(0, i);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        boolean z = false;
        boolean z2 = false;
        ConeKotlinType coneKotlinType = null;
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            ConeKotlinType coneKotlinType2 = (ConeKotlinType) CollectionsKt.getOrNull(list, iNextInt + i2);
            if (z && !Intrinsics.areEqual(coneKotlinType, coneKotlinType2)) {
                z2 = true;
            }
            FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.getOrNull(firFunction.getValueParameters(), iNextInt);
            if (z2) {
                if ((firValueParameter != null ? firValueParameter.getDefaultValue() : null) != null) {
                    name2 = firValueParameter.getName();
                } else {
                    name2 = null;
                }
            } else {
                name2 = null;
            }
            if (firValueParameter != null && firValueParameter.getIsVararg()) {
                coneKotlinType = coneKotlinType2;
                z = true;
            }
            if (name2 == null) {
                firFakeArgumentForCallableReference = new FirFakeArgumentForCallableReference(iNextInt);
            } else {
                FirNamedArgumentExpressionBuilder firNamedArgumentExpressionBuilder = new FirNamedArgumentExpressionBuilder();
                firNamedArgumentExpressionBuilder.setExpression(new FirFakeArgumentForCallableReference(iNextInt));
                firNamedArgumentExpressionBuilder.setName(name2);
                firNamedArgumentExpressionBuilder.setSpread(false);
                firFakeArgumentForCallableReference = firNamedArgumentExpressionBuilder.mo6809build();
            }
            arrayList.add(firFakeArgumentForCallableReference);
        }
        return arrayList;
    }

    public static final boolean isKCallableType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneKotlinType), StandardClassIds.INSTANCE.getKCallable());
    }

    private static final ConeKotlinType createKPropertyType(FirVariable firVariable, ConeKotlinType coneKotlinType, FirResolvedTypeRef firResolvedTypeRef, Candidate candidate) {
        return ResolveUtilsKt.createKPropertyType(coneKotlinType, firResolvedTypeRef.getType(), canBeMutableReference(firVariable, candidate));
    }

    private static final boolean canBeMutableReference(FirVariable firVariable, Candidate candidate) {
        if (!firVariable.getIsVar()) {
            return false;
        }
        if (firVariable instanceof FirField) {
            return true;
        }
        FirVariable firVariable2 = firVariable;
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firVariable2) || (firVariable2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firVariable2) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firVariable2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firVariable2) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    DelegatedWrapperData delegatedWrapperData = ClassMembersKt.getDelegatedWrapperData(firVariable2);
                    originalForSubstitutionOverrideAttr = delegatedWrapperData != null ? delegatedWrapperData.getWrapped() : null;
                }
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firVariable2 = originalForSubstitutionOverrideAttr;
        }
        FirVariable firVariable3 = (FirVariable) firVariable2;
        KtSourceElement source = firVariable3.getSource();
        if (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) {
            if (!(firVariable3.getSetter() instanceof FirMemberDeclaration)) {
                return false;
            }
            FirVisibilityChecker visibilityChecker = FirVisibilityCheckerKt.getVisibilityChecker(candidate.getCallInfo().getSession());
            FirPropertyAccessor setter = firVariable3.getSetter();
            Intrinsics.checkNotNull(setter);
            if (!VisibilityUtilsKt.isVisible$default(visibilityChecker, setter, candidate, false, 4, null)) {
                return false;
            }
        }
        return true;
    }
}
