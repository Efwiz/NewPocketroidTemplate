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
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Equals.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/ExplicitEquals;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExplicitEquals extends IntrinsicMethod {
    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        List<IrExpression> listReceiverAndArgs = JvmIrUtilsKt.receiverAndArgs(expression);
        IrExpression irExpression = listReceiverAndArgs.get(0);
        IrExpression irExpression2 = listReceiverAndArgs.get(1);
        ExpressionCodegen expressionCodegen = codegen;
        PromisedValue promisedValue = (PromisedValue) irExpression.accept(expressionCodegen, data);
        Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
        PromisedValueKt.materializeAt(promisedValue, OBJECT_TYPE, codegen.getContext().getIrBuiltIns().getAnyNType());
        PromisedValue promisedValue2 = (PromisedValue) irExpression2.accept(expressionCodegen, data);
        Type OBJECT_TYPE2 = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE2, "OBJECT_TYPE");
        PromisedValueKt.materializeAt(promisedValue2, OBJECT_TYPE2, codegen.getContext().getIrBuiltIns().getAnyNType());
        codegen.getMv().visitMethodInsn(182, AsmTypes.OBJECT_TYPE.getInternalName(), Namer.EQUALS_METHOD_NAME, Type.getMethodDescriptor(Type.BOOLEAN_TYPE, AsmTypes.OBJECT_TYPE), false);
        Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
        Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
        return new MaterialValue(codegen, BOOLEAN_TYPE, codegen.getContext().getIrBuiltIns().getBooleanType());
    }
}
