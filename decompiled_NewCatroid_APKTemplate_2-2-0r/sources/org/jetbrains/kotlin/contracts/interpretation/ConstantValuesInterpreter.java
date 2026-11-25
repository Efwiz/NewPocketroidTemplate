package org.jetbrains.kotlin.contracts.interpretation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;

/* JADX INFO: compiled from: ConstantValuesInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/contracts/interpretation/ConstantValuesInterpreter;", "", "()V", "interpretConstant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantValuesInterpreter {
    public final ESConstant interpretConstant(ConstantReference constantReference) {
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        if (Intrinsics.areEqual(constantReference, BooleanConstantReference.INSTANCE.getTRUE())) {
            return ESConstants.INSTANCE.getTrueValue();
        }
        if (Intrinsics.areEqual(constantReference, BooleanConstantReference.INSTANCE.getFALSE())) {
            return ESConstants.INSTANCE.getFalseValue();
        }
        if (Intrinsics.areEqual(constantReference, ConstantReference.INSTANCE.getNULL())) {
            return ESConstants.INSTANCE.getNullValue();
        }
        if (Intrinsics.areEqual(constantReference, ConstantReference.INSTANCE.getNOT_NULL())) {
            return ESConstants.INSTANCE.getNotNullValue();
        }
        if (Intrinsics.areEqual(constantReference, ConstantReference.INSTANCE.getWILDCARD())) {
            return ESConstants.INSTANCE.getWildcard();
        }
        return null;
    }
}
