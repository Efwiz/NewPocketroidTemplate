package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESConstants;", "", "()V", "falseValue", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "getFalseValue", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "notNullValue", "getNotNullValue", "nullValue", "getNullValue", "trueValue", "getTrueValue", "wildcard", "getWildcard", "booleanValue", "value", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESConstants {
    public static final ESConstants INSTANCE = new ESConstants();
    private static final ESConstant trueValue = new ESConstant(BooleanConstantReference.INSTANCE.getTRUE(), ESBooleanType.INSTANCE);
    private static final ESConstant falseValue = new ESConstant(BooleanConstantReference.INSTANCE.getFALSE(), ESBooleanType.INSTANCE);
    private static final ESConstant nullValue = new ESConstant(ConstantReference.INSTANCE.getNULL(), ESNullableNothingType.INSTANCE);
    private static final ESConstant notNullValue = new ESConstant(ConstantReference.INSTANCE.getNOT_NULL(), ESAnyType.INSTANCE);
    private static final ESConstant wildcard = new ESConstant(ConstantReference.INSTANCE.getWILDCARD(), ESNullableAnyType.INSTANCE);

    private ESConstants() {
    }

    public final ESConstant getTrueValue() {
        return trueValue;
    }

    public final ESConstant getFalseValue() {
        return falseValue;
    }

    public final ESConstant getNullValue() {
        return nullValue;
    }

    public final ESConstant getNotNullValue() {
        return notNullValue;
    }

    public final ESConstant getWildcard() {
        return wildcard;
    }

    public final ESConstant booleanValue(boolean value) {
        return value ? trueValue : falseValue;
    }
}
