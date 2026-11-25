package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeCannotInferValueParameterType;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirUnitExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: InferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a>\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"extractLambdaInfoFromFunctionType", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "argument", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "returnTypeVariable", "Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaReturnType;", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "allowCoercionToExtensionReceiver", "", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InferenceUtilsKt {
    /* JADX WARN: Code duplicated, block: B:130:0x0227  */
    /* JADX WARN: Code duplicated, block: B:83:0x013f  */
    /* JADX WARN: Code duplicated, block: B:90:0x0150  */
    public static final ResolvedLambdaAtom extractLambdaInfoFromFunctionType(ConeKotlinType coneKotlinType, FirAnonymousFunction argument, ConeTypeVariableForLambdaReturnType coneTypeVariableForLambdaReturnType, BodyResolveComponents components, Candidate candidate, boolean z) {
        boolean z2;
        boolean z3;
        char c;
        ConeKotlinType type;
        FirTypeRef returnTypeRef;
        List<ConeKotlinType> list;
        List<ConeKotlinType> listSubList;
        List<FirStatement> statements;
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(components, "components");
        FirSession session = components.getSession();
        if (coneKotlinType == null) {
            return null;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return extractLambdaInfoFromFunctionType(((ConeFlexibleType) coneKotlinType).getLowerBound(), argument, coneTypeVariableForLambdaReturnType, components, candidate, z);
        }
        FunctionTypeKind functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null) {
            return null;
        }
        FunctionTypeKind.Function functionExtractSingleSpecialKindForFunction = FirFunctionTypeKindServiceKt.getFunctionTypeService(session).extractSingleSpecialKindForFunction(argument.getSymbol());
        if (functionExtractSingleSpecialKindForFunction == null) {
            functionExtractSingleSpecialKindForFunction = !argument.getIsLambda() ? FunctionTypeKind.Function.INSTANCE : null;
        }
        FirBlock body = argument.getBody();
        FirStatement firStatement = (body == null || (statements = body.getStatements()) == null) ? null : (FirStatement) CollectionsKt.singleOrNull((List) statements);
        FirReturnExpression firReturnExpression = firStatement instanceof FirReturnExpression ? (FirReturnExpression) firStatement : null;
        if (org.jetbrains.kotlin.fir.types.InferenceUtilsKt.getReturnType(argument) == null && firReturnExpression != null && Intrinsics.areEqual(firReturnExpression.getTarget().getLabeledElement(), argument) && (firReturnExpression.getResult() instanceof FirUnitExpression)) {
            argument.replaceReturnTypeRef(session.getBuiltinTypes().getUnitType());
        }
        ConeKotlinType returnType = org.jetbrains.kotlin.fir.types.InferenceUtilsKt.getReturnType(argument);
        if (returnType == null) {
            returnType = FunctionalTypeUtilsKt.returnType(coneKotlinType, session);
        }
        ConeKotlinType coneKotlinType2 = returnType;
        ConeKotlinType coneKotlinTypeReceiverType = argument.getIsLambda() ? FunctionalTypeUtilsKt.receiverType(coneKotlinType, session) : org.jetbrains.kotlin.fir.types.InferenceUtilsKt.getReceiverType(argument);
        int contextReceiversNumberForFunctionType = argument.getIsLambda() ? CompilerConeAttributesKt.getContextReceiversNumberForFunctionType(coneKotlinType) : argument.getContextReceivers().size();
        List<ConeKotlinType> listValueParameterTypesIncludingReceiver = FunctionalTypeUtilsKt.valueParameterTypesIncludingReceiver(coneKotlinType, session);
        boolean zIsExtensionFunctionType = TypeUtilsKt.isExtensionFunctionType(coneKotlinType, session);
        int i = ((coneKotlinTypeReceiverType == null || !zIsExtensionFunctionType) ? 0 : 1) + contextReceiversNumberForFunctionType;
        List<ConeKotlinType> listDrop = i > 0 ? CollectionsKt.drop(listValueParameterTypesIncludingReceiver, i) : listValueParameterTypesIncludingReceiver;
        List<FirValueParameter> valueParameters = argument.getValueParameters();
        if (!argument.getIsLambda() || argument.getHasExplicitParameterList() || listDrop.size() >= 2) {
            if (z && argument.getIsLambda() && zIsExtensionFunctionType && listValueParameterTypesIncludingReceiver.size() == valueParameters.size()) {
                FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.firstOrNull((List) valueParameters);
                ConeKotlinType coneKotlinType3 = (ConeKotlinType) CollectionsKt.firstOrNull((List) listValueParameterTypesIncludingReceiver);
                if (firValueParameter == null || (returnTypeRef = firValueParameter.getReturnTypeRef()) == null) {
                    type = null;
                } else {
                    FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                    type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    if (!(type instanceof ConeKotlinType)) {
                        type = null;
                    }
                }
                if (Intrinsics.areEqual(type, coneKotlinType3 != null ? coneKotlinType3.getType() : null)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (z2) {
                valueParameters = CollectionsKt.drop(valueParameters, 1);
            }
            List<FirValueParameter> list2 = valueParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            int i2 = 0;
            for (Object obj : list2) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                FirValueParameter firValueParameter2 = (FirValueParameter) obj;
                FirTypeRef returnTypeRef2 = firValueParameter2.getReturnTypeRef();
                FirResolvedTypeRef firResolvedTypeRef2 = returnTypeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef2 : null;
                ConeErrorType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
                if (!(type2 instanceof ConeKotlinType)) {
                    type2 = null;
                }
                if (type2 == null && (type2 = (ConeKotlinType) CollectionsKt.getOrNull(listDrop, i2)) == null) {
                    c = 2;
                    type2 = new ConeErrorType(new ConeCannotInferValueParameterType(firValueParameter2.getSymbol(), null, 2, null), false, null, null, 14, null);
                } else {
                    c = 2;
                }
                arrayList.add(type2);
                z2 = z2;
                i2 = i3;
            }
            z3 = z2;
            listDrop = arrayList;
        } else {
            z3 = false;
        }
        if (contextReceiversNumberForFunctionType == 0) {
            listSubList = CollectionsKt.emptyList();
        } else {
            if (argument.getIsLambda()) {
                listSubList = listValueParameterTypesIncludingReceiver.subList(0, contextReceiversNumberForFunctionType);
            } else {
                List<FirContextReceiver> contextReceivers = argument.getContextReceivers();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
                Iterator<T> it = contextReceivers.iterator();
                while (it.hasNext()) {
                    arrayList2.add(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef()));
                }
                list = arrayList2;
            }
            if (functionExtractSingleSpecialKindForFunction != null) {
                functionTypeKind = functionExtractSingleSpecialKindForFunction;
            }
            return new ResolvedLambdaAtom(argument, coneKotlinType, functionTypeKind, coneKotlinTypeReceiverType, list, listDrop, coneKotlinType2, coneTypeVariableForLambdaReturnType, candidate, z3);
        }
        list = listSubList;
        if (functionExtractSingleSpecialKindForFunction != null) {
            functionTypeKind = functionExtractSingleSpecialKindForFunction;
        }
        return new ResolvedLambdaAtom(argument, coneKotlinType, functionTypeKind, coneKotlinTypeReceiverType, list, listDrop, coneKotlinType2, coneTypeVariableForLambdaReturnType, candidate, z3);
    }
}
