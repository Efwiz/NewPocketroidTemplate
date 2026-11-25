package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.IntegerValueConstant;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PrimitiveNumberRangeLiteralRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004H\u0002\u001a\u0014\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0014\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\u000b"}, d2 = {"canBeSpecializedByExcludingHighBound", "", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isArraySizeAccess", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isArraySizeMinusOne", "isConstantOne", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveNumberRangeLiteralRangeValueKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean canBeSpecializedByExcludingHighBound(ExpressionCodegen expressionCodegen, ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        KtExpression firstArgumentExpression = CallUtilKt.getFirstArgumentExpression(resolvedCall);
        Intrinsics.checkNotNull(firstArgumentExpression);
        return isArraySizeMinusOne(expressionCodegen, firstArgumentExpression);
    }

    private static final boolean isArraySizeMinusOne(ExpressionCodegen expressionCodegen, KtExpression ktExpression) {
        if (ktExpression instanceof KtBinaryExpression) {
            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) ktExpression;
            KtExpression left = ktBinaryExpression.getLeft();
            Intrinsics.checkNotNull(left);
            if (isArraySizeAccess(expressionCodegen, left) && ktBinaryExpression.getOperationToken() == KtTokens.MINUS) {
                KtExpression right = ktBinaryExpression.getRight();
                Intrinsics.checkNotNull(right);
                if (isConstantOne(expressionCodegen, right)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final boolean isConstantOne(ExpressionCodegen expressionCodegen, KtExpression ktExpression) {
        ConstantValue<?> compileTimeConstant = expressionCodegen.getCompileTimeConstant(ktExpression);
        return (compileTimeConstant instanceof IntegerValueConstant) && Intrinsics.areEqual(compileTimeConstant.getValue(), (Object) 1);
    }

    private static final boolean isArraySizeAccess(ExpressionCodegen expressionCodegen, KtExpression ktExpression) {
        if (!(ktExpression instanceof KtDotQualifiedExpression)) {
            return false;
        }
        KtDotQualifiedExpression ktDotQualifiedExpression = (KtDotQualifiedExpression) ktExpression;
        KtExpression selectorExpression = ktDotQualifiedExpression.getSelectorExpression();
        KotlinType type = expressionCodegen.getBindingContext().getType(ktDotQualifiedExpression.getReceiverExpression());
        return type != null && expressionCodegen.asmType(type).getSort() == 9 && (selectorExpression instanceof KtNameReferenceExpression) && Intrinsics.areEqual(((KtNameReferenceExpression) selectorExpression).getText(), "size");
    }
}
