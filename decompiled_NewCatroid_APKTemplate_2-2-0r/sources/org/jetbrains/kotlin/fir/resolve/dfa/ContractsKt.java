package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.KtBooleanExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.fir.contracts.description.ConeContractConstantValues;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: contracts.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a{\u0010\u0000\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0001j\u0004\u0018\u0001`\u0004*\u00020\u00052\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u0002`\n2\u0010\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\r0\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\"\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0012\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u00040\u0011¢\u0006\u0002\u0010\u0013\u001a\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015*\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0016j\u0002`\u0017¨\u0006\u0018"}, d2 = {"approveContractStatement", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatements;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "statement", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeBooleanExpression;", "arguments", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "approveOperationStatement", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;[Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "toOperation", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeConstantReference;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContractsKt {
    public static final Operation toOperation(KtConstantReference<ConeKotlinType, ConeDiagnostic> ktConstantReference) {
        Intrinsics.checkNotNullParameter(ktConstantReference, "<this>");
        if (Intrinsics.areEqual(ktConstantReference, ConeContractConstantValues.INSTANCE.getWILDCARD())) {
            return null;
        }
        if (Intrinsics.areEqual(ktConstantReference, ConeContractConstantValues.INSTANCE.getNULL())) {
            return Operation.EqNull;
        }
        if (Intrinsics.areEqual(ktConstantReference, ConeContractConstantValues.INSTANCE.getNOT_NULL())) {
            return Operation.NotEqNull;
        }
        if (Intrinsics.areEqual(ktConstantReference, ConeContractConstantValues.INSTANCE.getTRUE())) {
            return Operation.EqTrue;
        }
        if (Intrinsics.areEqual(ktConstantReference, ConeContractConstantValues.INSTANCE.getFALSE())) {
            return Operation.EqFalse;
        }
        throw new IllegalArgumentException(ktConstantReference + " can not be transformed to Operation");
    }

    private static final Map<RealVariable, TypeStatement> approveContractStatement$processEqNull(DataFlowVariable dataFlowVariable, Function1<? super OperationStatement, ? extends Map<RealVariable, ? extends TypeStatement>> function1, boolean z) {
        return (Map) function1.invoke(new OperationStatement(dataFlowVariable, z ? Operation.EqNull : Operation.NotEqNull));
    }

    private static final Map<RealVariable, TypeStatement> approveContractStatement$visit(KtBooleanExpression<ConeKotlinType, ConeDiagnostic> ktBooleanExpression, DataFlowVariable[] dataFlowVariableArr, LogicSystem logicSystem, ConeSubstitutor coneSubstitutor, Function1<? super OperationStatement, ? extends Map<RealVariable, ? extends TypeStatement>> function1, boolean z) {
        Map<RealVariable, TypeStatement> map;
        ConeKotlinType coneKotlinTypeSubstituteOrNull;
        Map<RealVariable, TypeStatement> mapEmptyMap;
        if (ktBooleanExpression instanceof KtBooleanConstantReference) {
            if (z == Intrinsics.areEqual(ktBooleanExpression, ConeContractConstantValues.INSTANCE.getTRUE())) {
                return null;
            }
            return MapsKt.emptyMap();
        }
        if (ktBooleanExpression instanceof KtLogicalNot) {
            return approveContractStatement$visit(((KtLogicalNot) ktBooleanExpression).getArg(), dataFlowVariableArr, logicSystem, coneSubstitutor, function1, !z);
        }
        if (ktBooleanExpression instanceof KtIsInstancePredicate) {
            KtIsInstancePredicate ktIsInstancePredicate = (KtIsInstancePredicate) ktBooleanExpression;
            DataFlowVariable dataFlowVariable = (DataFlowVariable) ArraysKt.getOrNull(dataFlowVariableArr, ktIsInstancePredicate.getArg().getParameterIndex() + 1);
            if (dataFlowVariable != null) {
                boolean z2 = z == ktIsInstancePredicate.getIsNegated();
                if (coneSubstitutor == null || (coneKotlinTypeSubstituteOrNull = coneSubstitutor.substituteOrNull((ConeKotlinType) ktIsInstancePredicate.getType())) == null) {
                    coneKotlinTypeSubstituteOrNull = (ConeKotlinType) ktIsInstancePredicate.getType();
                }
                if (ConeBuiltinTypeUtilsKt.isAny(coneKotlinTypeSubstituteOrNull)) {
                    mapEmptyMap = approveContractStatement$processEqNull(dataFlowVariable, function1, !z2);
                } else if (ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinTypeSubstituteOrNull)) {
                    mapEmptyMap = approveContractStatement$processEqNull(dataFlowVariable, function1, z2);
                } else {
                    if ((z2 && !FirTypeUtilsKt.getCanBeNull((ConeKotlinType) ktIsInstancePredicate.getType())) || (!z2 && ConeTypeUtilsKt.isMarkedNullable((ConeKotlinType) ktIsInstancePredicate.getType()))) {
                        mapEmptyMap = approveContractStatement$processEqNull(dataFlowVariable, function1, false);
                    } else {
                        mapEmptyMap = MapsKt.emptyMap();
                    }
                    if (z2 && (dataFlowVariable instanceof RealVariable)) {
                        mapEmptyMap = logicSystem.andForTypeStatements(mapEmptyMap, MapsKt.mapOf(TuplesKt.to(dataFlowVariable, ModelKt.typeEq((RealVariable) dataFlowVariable, coneKotlinTypeSubstituteOrNull))));
                    }
                }
                Map<RealVariable, TypeStatement> map2 = mapEmptyMap;
                if (map2 != null) {
                    return map2;
                }
            }
            return MapsKt.emptyMap();
        }
        if (ktBooleanExpression instanceof KtIsNullPredicate) {
            KtIsNullPredicate ktIsNullPredicate = (KtIsNullPredicate) ktBooleanExpression;
            DataFlowVariable dataFlowVariable2 = (DataFlowVariable) ArraysKt.getOrNull(dataFlowVariableArr, ktIsNullPredicate.getArg().getParameterIndex() + 1);
            if (dataFlowVariable2 != null) {
                Map<RealVariable, TypeStatement> mapApproveContractStatement$processEqNull = approveContractStatement$processEqNull(dataFlowVariable2, function1, z == ktIsNullPredicate.getIsNegated());
                if (mapApproveContractStatement$processEqNull != null) {
                    return mapApproveContractStatement$processEqNull;
                }
            }
            return MapsKt.emptyMap();
        }
        if (ktBooleanExpression instanceof KtBooleanValueParameterReference) {
            DataFlowVariable dataFlowVariable3 = (DataFlowVariable) ArraysKt.getOrNull(dataFlowVariableArr, ((KtBooleanValueParameterReference) ktBooleanExpression).getParameterIndex() + 1);
            return (dataFlowVariable3 == null || (map = (Map) function1.invoke(ModelKt.eq(dataFlowVariable3, z ^ true))) == null) ? MapsKt.emptyMap() : map;
        }
        if (ktBooleanExpression instanceof KtBinaryLogicExpression) {
            KtBinaryLogicExpression ktBinaryLogicExpression = (KtBinaryLogicExpression) ktBooleanExpression;
            Map<RealVariable, TypeStatement> mapApproveContractStatement$visit = approveContractStatement$visit(ktBinaryLogicExpression.getLeft(), dataFlowVariableArr, logicSystem, coneSubstitutor, function1, z);
            Map<RealVariable, TypeStatement> mapApproveContractStatement$visit2 = approveContractStatement$visit(ktBinaryLogicExpression.getRight(), dataFlowVariableArr, logicSystem, coneSubstitutor, function1, z);
            boolean z3 = z != (ktBinaryLogicExpression.getKind() == LogicOperationKind.AND);
            if (mapApproveContractStatement$visit == null) {
                if (z3) {
                    return null;
                }
                return mapApproveContractStatement$visit2;
            }
            if (mapApproveContractStatement$visit2 == null) {
                if (z3) {
                    return null;
                }
                return mapApproveContractStatement$visit;
            }
            if (z3) {
                return logicSystem.andForTypeStatements(mapApproveContractStatement$visit, mapApproveContractStatement$visit2);
            }
            return logicSystem.orForTypeStatements(mapApproveContractStatement$visit, mapApproveContractStatement$visit2);
        }
        return MapsKt.emptyMap();
    }

    public static final Map<RealVariable, TypeStatement> approveContractStatement(LogicSystem logicSystem, KtBooleanExpression<ConeKotlinType, ConeDiagnostic> statement, DataFlowVariable[] arguments, ConeSubstitutor coneSubstitutor, Function1<? super OperationStatement, ? extends Map<RealVariable, ? extends TypeStatement>> approveOperationStatement) {
        Intrinsics.checkNotNullParameter(logicSystem, "<this>");
        Intrinsics.checkNotNullParameter(statement, "statement");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(approveOperationStatement, "approveOperationStatement");
        return approveContractStatement$visit(statement, arguments, logicSystem, coneSubstitutor, approveOperationStatement, false);
    }
}
