package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/PrimitiveComparison;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "primitiveNumberType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "operatorToken", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "(Lorg/jetbrains/kotlin/builtins/PrimitiveType;Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;)V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveComparison extends IntrinsicMethod {
    private final KtSingleValueToken operatorToken;
    private final PrimitiveType primitiveNumberType;

    public PrimitiveComparison(PrimitiveType primitiveNumberType, KtSingleValueToken operatorToken) {
        Intrinsics.checkNotNullParameter(primitiveNumberType, "primitiveNumberType");
        Intrinsics.checkNotNullParameter(operatorToken, "operatorToken");
        this.primitiveNumberType = primitiveNumberType;
        this.operatorToken = operatorToken;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        Type type = Type.getType(JvmPrimitiveType.get(this.primitiveNumberType).getDesc());
        List<IrExpression> listReceiverAndArgs = JvmIrUtilsKt.receiverAndArgs(expression);
        IrExpression irExpression = listReceiverAndArgs.get(0);
        IrExpression irExpression2 = listReceiverAndArgs.get(1);
        ExpressionCodegen expressionCodegen = codegen;
        PromisedValue promisedValue = (PromisedValue) irExpression.accept(expressionCodegen, data);
        Intrinsics.checkNotNull(type);
        MaterialValue materialValueMaterializedAt$default = PromisedValueKt.materializedAt$default(promisedValue, type, irExpression.getType(), false, 4, null);
        MaterialValue materialValueMaterializedAt$default2 = PromisedValueKt.materializedAt$default((PromisedValue) irExpression2.accept(expressionCodegen, data), type, irExpression2.getType(), false, 4, null);
        if (!codegen.getContext().getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperIeee754Comparisons) && ((Intrinsics.areEqual(type, Type.FLOAT_TYPE) || Intrinsics.areEqual(type, Type.DOUBLE_TYPE)) && (JvmIrUtilsKt.isSmartcastFromHigherThanNullable(irExpression, codegen.getContext()) || JvmIrUtilsKt.isSmartcastFromHigherThanNullable(irExpression2, codegen.getContext())))) {
            return new NonIEEE754FloatComparison(this.operatorToken, materialValueMaterializedAt$default, materialValueMaterializedAt$default2);
        }
        return new BooleanComparison(this.operatorToken, materialValueMaterializedAt$default, materialValueMaterializedAt$default2);
    }
}
