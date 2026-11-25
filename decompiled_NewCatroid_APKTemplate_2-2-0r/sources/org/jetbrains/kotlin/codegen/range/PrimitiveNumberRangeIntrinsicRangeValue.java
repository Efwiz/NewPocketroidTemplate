package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGeneratorKt;
import org.jetbrains.kotlin.codegen.range.comparison.RangeContainsTypeInfo;
import org.jetbrains.kotlin.codegen.range.forLoop.ForInDefinitelySafeSimpleProgressionLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.CallBasedInExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InFloatingPointRangeLiteralExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InIntegralContinuousRangeExpressionGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.CharValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.IntegerValueConstant;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: PrimitiveNumberRangeIntrinsicRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J<\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0004J:\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J:\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J(\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 2\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0014J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\f\u001a\u00020\rH$J\u0018\u0010$\u001a\u00020\u00172\u000e\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0014J\u0018\u0010&\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(H\u0002J\u0018\u0010)\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010'\u001a\u00020\u0015H\u0002J\u0018\u0010*\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010'\u001a\u00020\u001cH\u0002J\f\u0010+\u001a\u00020\u0011*\u00020\u0011H\u0004R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/PrimitiveNumberRangeIntrinsicRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/CallIntrinsicRangeValue;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "elementKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getElementKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "createConstBoundedForLoopGeneratorOrNull", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "startValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "endExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "step", "", "isStartInclusive", "", "createConstBoundedIntForLoopGenerator", "endIntValue", "createConstBoundedLongForLoopGenerator", "endLongValue", "", "createIntrinsicInExpressionGenerator", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "resolvedCall", "getBoundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "isIntrinsicInCall", "resolvedCallForIn", "isProhibitedCharConstEndValue", "endValue", "", "isProhibitedIntConstEndValue", "isProhibitedLongConstEndValue", "coerceToRangeElementTypeIfRequired", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PrimitiveNumberRangeIntrinsicRangeValue extends CallIntrinsicRangeValue {
    private final KotlinType elementKotlinType;

    private final boolean isProhibitedCharConstEndValue(int step, char endValue) {
        return endValue == (step == 1 ? (char) 65535 : (char) 0);
    }

    private final boolean isProhibitedIntConstEndValue(int step, int endValue) {
        return endValue == (step == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
    }

    private final boolean isProhibitedLongConstEndValue(int step, long endValue) {
        return endValue == (step == 1 ? Long.MAX_VALUE : Long.MIN_VALUE);
    }

    protected abstract BoundedValue getBoundedValue(ExpressionCodegen codegen);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrimitiveNumberRangeIntrinsicRangeValue(ResolvedCall<? extends CallableDescriptor> rangeCall) {
        KotlinType rangeOrProgressionElementType$default;
        super(rangeCall);
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        KotlinType returnType = rangeCall.getResultingDescriptor().getReturnType();
        if (returnType == null || (rangeOrProgressionElementType$default = RangeUtilKt.getRangeOrProgressionElementType$default(returnType, null, 2, null)) == null) {
            throw new AssertionError("Unexpected range ");
        }
        this.elementKotlinType = rangeOrProgressionElementType$default;
    }

    protected final KotlinType getElementKotlinType() {
        return this.elementKotlinType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.CallIntrinsicRangeValue
    protected boolean isIntrinsicInCall(ResolvedCall<? extends CallableDescriptor> resolvedCallForIn) {
        Intrinsics.checkNotNullParameter(resolvedCallForIn, "resolvedCallForIn");
        CallableDescriptor resultingDescriptor = resolvedCallForIn.getResultingDescriptor();
        return RangeUtilKt.isPrimitiveRangeContains(resultingDescriptor) || RangeUtilKt.isClosedFloatingPointRangeContains(resultingDescriptor) || RangeUtilKt.isPrimitiveNumberRangeExtensionContainsPrimitiveNumber(resultingDescriptor) || RangeUtilKt.isUnsignedIntegerRangeContains(resultingDescriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.range.CallIntrinsicRangeValue
    protected InExpressionGenerator createIntrinsicInExpressionGenerator(ExpressionCodegen codegen, KtSimpleNameExpression operatorReference, ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        RangeContainsTypeInfo rangeContainsTypeInfo = ComparisonGeneratorKt.getRangeContainsTypeInfo(resolvedCall);
        if (rangeContainsTypeInfo == null) {
            return new CallBasedInExpressionGenerator(codegen, operatorReference);
        }
        ComparisonGenerator comparisonGeneratorForRangeContainsCall = ComparisonGeneratorKt.getComparisonGeneratorForRangeContainsCall(codegen, rangeContainsTypeInfo);
        if (comparisonGeneratorForRangeContainsCall == null) {
            return new CallBasedInExpressionGenerator(codegen, operatorReference);
        }
        Type comparedType = comparisonGeneratorForRangeContainsCall.getComparedType();
        if (Intrinsics.areEqual(comparedType, Type.DOUBLE_TYPE) ? true : Intrinsics.areEqual(comparedType, Type.FLOAT_TYPE)) {
            BoundedValue boundedValue = getBoundedValue(codegen);
            if (!(boundedValue instanceof BoundedValue)) {
                boundedValue = null;
            }
            if (boundedValue == null) {
                throw new AssertionError("Floating point intrinsic range value should be a range literal");
            }
            FrameMap frameMap = codegen.getFrameMap();
            Intrinsics.checkNotNullExpressionValue(frameMap, "<get-frameMap>(...)");
            return new InFloatingPointRangeLiteralExpressionGenerator(operatorReference, boundedValue, comparisonGeneratorForRangeContainsCall, frameMap);
        }
        BoundedValue boundedValue2 = getBoundedValue(codegen);
        FrameMap frameMap2 = codegen.getFrameMap();
        Intrinsics.checkNotNullExpressionValue(frameMap2, "<get-frameMap>(...)");
        return new InIntegralContinuousRangeExpressionGenerator(operatorReference, rangeContainsTypeInfo, boundedValue2, comparisonGeneratorForRangeContainsCall, frameMap2);
    }

    protected final StackValue coerceToRangeElementTypeIfRequired(StackValue stackValue) {
        Intrinsics.checkNotNullParameter(stackValue, "<this>");
        KotlinType returnType = getRangeCall().getResultingDescriptor().getReturnType();
        Intrinsics.checkNotNull(returnType);
        KotlinType rangeOrProgressionElementType$default = RangeUtilKt.getRangeOrProgressionElementType$default(returnType, null, 2, null);
        Intrinsics.checkNotNull(rangeOrProgressionElementType$default);
        if (KotlinBuiltIns.isUInt(rangeOrProgressionElementType$default)) {
            return UnsignedNumbersCoercionKt.coerceUnsignedToUInt(stackValue, rangeOrProgressionElementType$default);
        }
        return KotlinBuiltIns.isULong(rangeOrProgressionElementType$default) ? UnsignedNumbersCoercionKt.coerceUnsignedToULong(stackValue, rangeOrProgressionElementType$default) : stackValue;
    }

    public static /* synthetic */ ForLoopGenerator createConstBoundedForLoopGeneratorOrNull$default(PrimitiveNumberRangeIntrinsicRangeValue primitiveNumberRangeIntrinsicRangeValue, ExpressionCodegen expressionCodegen, KtForExpression ktForExpression, StackValue stackValue, KtExpression ktExpression, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createConstBoundedForLoopGeneratorOrNull");
        }
        if ((i2 & 32) != 0) {
            z = true;
        }
        return primitiveNumberRangeIntrinsicRangeValue.createConstBoundedForLoopGeneratorOrNull(expressionCodegen, ktForExpression, stackValue, ktExpression, i, z);
    }

    protected final ForLoopGenerator createConstBoundedForLoopGeneratorOrNull(ExpressionCodegen codegen, KtForExpression forExpression, StackValue startValue, KtExpression endExpression, int step, boolean isStartInclusive) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(startValue, "startValue");
        Intrinsics.checkNotNullParameter(endExpression, "endExpression");
        ConstantValue<?> compileTimeConstant = codegen.getCompileTimeConstant(endExpression);
        IntegerValueConstant integerValueConstant = compileTimeConstant instanceof IntegerValueConstant ? (IntegerValueConstant) compileTimeConstant : null;
        if (integerValueConstant == null) {
            return null;
        }
        if (integerValueConstant instanceof ByteValue) {
            byte bByteValue = ((ByteValue) integerValueConstant).getValue().byteValue();
            if (isProhibitedIntConstEndValue(step, bByteValue)) {
                return null;
            }
            return createConstBoundedIntForLoopGenerator(codegen, forExpression, startValue, bByteValue, step, isStartInclusive);
        }
        if (integerValueConstant instanceof ShortValue) {
            short sShortValue = ((ShortValue) integerValueConstant).getValue().shortValue();
            if (isProhibitedIntConstEndValue(step, sShortValue)) {
                return null;
            }
            return createConstBoundedIntForLoopGenerator(codegen, forExpression, startValue, sShortValue, step, isStartInclusive);
        }
        if (integerValueConstant instanceof IntValue) {
            int iIntValue = ((IntValue) integerValueConstant).getValue().intValue();
            if (isProhibitedIntConstEndValue(step, iIntValue)) {
                return null;
            }
            return createConstBoundedIntForLoopGenerator(codegen, forExpression, startValue, iIntValue, step, isStartInclusive);
        }
        if (integerValueConstant instanceof CharValue) {
            char cCharValue = ((CharValue) integerValueConstant).getValue().charValue();
            if (isProhibitedCharConstEndValue(step, cCharValue)) {
                return null;
            }
            return createConstBoundedIntForLoopGenerator(codegen, forExpression, startValue, cCharValue, step, isStartInclusive);
        }
        if (!(integerValueConstant instanceof LongValue)) {
            return null;
        }
        long jLongValue = ((LongValue) integerValueConstant).getValue().longValue();
        if (isProhibitedLongConstEndValue(step, jLongValue)) {
            return null;
        }
        return createConstBoundedLongForLoopGenerator(codegen, forExpression, startValue, jLongValue, step, isStartInclusive);
    }

    private final ForLoopGenerator createConstBoundedIntForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, StackValue startValue, int endIntValue, int step, boolean isStartInclusive) {
        StackValue stackValueIntegerConstant = StackValue.integerConstant(endIntValue, codegen.asmType(this.elementKotlinType));
        Intrinsics.checkNotNullExpressionValue(stackValueIntegerConstant, "integerConstant(...)");
        return new ForInDefinitelySafeSimpleProgressionLoopGenerator(codegen, forExpression, startValue, isStartInclusive, stackValueIntegerConstant, true, ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(this.elementKotlinType), step);
    }

    private final ForLoopGenerator createConstBoundedLongForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, StackValue startValue, long endLongValue, int step, boolean isStartInclusive) {
        StackValue stackValueConstant = StackValue.constant(Long.valueOf(endLongValue), codegen.asmType(this.elementKotlinType), this.elementKotlinType);
        Intrinsics.checkNotNullExpressionValue(stackValueConstant, "constant(...)");
        return new ForInDefinitelySafeSimpleProgressionLoopGenerator(codegen, forExpression, startValue, isStartInclusive, stackValueConstant, true, ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(this.elementKotlinType), step);
    }
}
