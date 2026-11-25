package org.jetbrains.kotlin.codegen.range.comparison;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0018\u0010\t\u001a\u0004\u0018\u00010\b2\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\u001a\f\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0002¨\u0006\u0010"}, d2 = {"getComparisonGeneratorForKotlinType", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getComparisonGeneratorForRangeContainsCall", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "rangeContainsTypeInfo", "Lorg/jetbrains/kotlin/codegen/range/comparison/RangeContainsTypeInfo;", "getRangeContainsTypeInfo", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isPrimitiveIntOrCoercible", "", "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ComparisonGeneratorKt {
    public static final ComparisonGenerator getComparisonGeneratorForKotlinType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        if (KotlinBuiltIns.isChar(kotlinType)) {
            return IntComparisonGeneratorKt.getCharComparisonGenerator();
        }
        if (KotlinBuiltIns.isByte(kotlinType) || KotlinBuiltIns.isShort(kotlinType) || KotlinBuiltIns.isInt(kotlinType)) {
            return IntComparisonGeneratorKt.getIntComparisonGenerator();
        }
        if (KotlinBuiltIns.isLong(kotlinType)) {
            return LongComparisonGenerator.INSTANCE;
        }
        if (KotlinBuiltIns.isFloat(kotlinType)) {
            return FloatingPointComparisonGeneratorKt.getFloatComparisonGenerator();
        }
        if (KotlinBuiltIns.isDouble(kotlinType)) {
            return FloatingPointComparisonGeneratorKt.getDoubleComparisonGenerator();
        }
        if (KotlinBuiltIns.isUInt(kotlinType)) {
            return UnsignedIntegerComparisonGeneratorKt.getUIntComparisonGenerator();
        }
        if (KotlinBuiltIns.isULong(kotlinType)) {
            return UnsignedIntegerComparisonGeneratorKt.getULongComparisonGenerator();
        }
        throw new UnsupportedOperationException("Unexpected element type: " + kotlinType);
    }

    public static final RangeContainsTypeInfo getRangeContainsTypeInfo(ResolvedCall<? extends CallableDescriptor> call) {
        KotlinType type;
        KotlinType type2;
        Intrinsics.checkNotNullParameter(call, "call");
        CallableDescriptor resultingDescriptor = call.getResultingDescriptor();
        ReceiverParameterDescriptor extensionReceiverParameter = resultingDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null) {
            ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = resultingDescriptor.mo6753getDispatchReceiverParameter();
            type = receiverParameterDescriptorMo6753getDispatchReceiverParameter != null ? receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType() : null;
            if (type == null) {
                return null;
            }
        }
        KotlinType rangeOrProgressionElementType$default = RangeUtilKt.getRangeOrProgressionElementType$default(type, null, 2, null);
        if (rangeOrProgressionElementType$default == null) {
            return null;
        }
        List<ValueParameterDescriptor> valueParameters = resultingDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
        if (valueParameterDescriptor == null || (type2 = valueParameterDescriptor.getType()) == null) {
            return null;
        }
        return new RangeContainsTypeInfo(rangeOrProgressionElementType$default, type2);
    }

    public static final ComparisonGenerator getComparisonGeneratorForRangeContainsCall(ExpressionCodegen codegen, RangeContainsTypeInfo rangeContainsTypeInfo) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(rangeContainsTypeInfo, "rangeContainsTypeInfo");
        KotlinType rangeElementType = rangeContainsTypeInfo.getRangeElementType();
        KotlinType valueParameterType = rangeContainsTypeInfo.getValueParameterType();
        Type typeAsmType = codegen.asmType(rangeElementType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        Type typeAsmType2 = codegen.asmType(valueParameterType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType2, "asmType(...)");
        if (Intrinsics.areEqual(typeAsmType, typeAsmType2)) {
            return getComparisonGeneratorForKotlinType(rangeElementType);
        }
        if (KotlinBuiltIns.isUInt(rangeElementType)) {
            return KotlinBuiltIns.isULong(valueParameterType) ? null : UnsignedIntegerComparisonGeneratorKt.getUIntComparisonGenerator();
        }
        if (KotlinBuiltIns.isULong(rangeElementType)) {
            return UnsignedIntegerComparisonGeneratorKt.getULongComparisonGenerator();
        }
        if (isPrimitiveIntOrCoercible(typeAsmType) && isPrimitiveIntOrCoercible(typeAsmType2)) {
            return IntComparisonGeneratorKt.getIntComparisonGenerator();
        }
        if ((isPrimitiveIntOrCoercible(typeAsmType) && Intrinsics.areEqual(typeAsmType2, Type.LONG_TYPE)) || (isPrimitiveIntOrCoercible(typeAsmType2) && Intrinsics.areEqual(typeAsmType, Type.LONG_TYPE))) {
            return LongComparisonGenerator.INSTANCE;
        }
        if ((Intrinsics.areEqual(typeAsmType, Type.FLOAT_TYPE) && Intrinsics.areEqual(typeAsmType2, Type.DOUBLE_TYPE)) || (Intrinsics.areEqual(typeAsmType, Type.DOUBLE_TYPE) && Intrinsics.areEqual(typeAsmType2, Type.FLOAT_TYPE))) {
            return FloatingPointComparisonGeneratorKt.getDoubleComparisonGenerator();
        }
        return null;
    }

    private static final boolean isPrimitiveIntOrCoercible(Type type) {
        return Intrinsics.areEqual(type, Type.INT_TYPE) || Intrinsics.areEqual(type, Type.SHORT_TYPE) || Intrinsics.areEqual(type, Type.BYTE_TYPE);
    }
}
