package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ArrayGet.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/ArrayGet;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayGet extends IntrinsicMethod {
    public static final ArrayGet INSTANCE = new ArrayGet();

    private ArrayGet() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        ExpressionCodegen expressionCodegen = codegen;
        Type typeCorrectElementType = AsmUtil.correctElementType(PromisedValueKt.materializedAt((PromisedValue) dispatchReceiver.accept(expressionCodegen, data), dispatchReceiver.getType()).getType());
        Intrinsics.checkNotNullExpressionValue(typeCorrectElementType, "correctElementType(...)");
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        PromisedValue promisedValue = (PromisedValue) valueArgument.accept(expressionCodegen, data);
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        PromisedValueKt.materializeAt(promisedValue, INT_TYPE, codegen.getContext().getIrBuiltIns().getIntType());
        codegen.getMv().aload(typeCorrectElementType);
        return new MaterialValue(codegen, typeCorrectElementType, expression.getType());
    }
}
