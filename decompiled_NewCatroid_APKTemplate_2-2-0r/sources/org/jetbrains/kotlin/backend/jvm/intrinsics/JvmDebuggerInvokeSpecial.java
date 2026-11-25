package org.jetbrains.kotlin.backend.jvm.intrinsics;

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
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmDebuggerInvokeSpecial.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/JvmDebuggerInvokeSpecial;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDebuggerInvokeSpecial extends IntrinsicMethod {
    public static final JvmDebuggerInvokeSpecial INSTANCE = new JvmDebuggerInvokeSpecial();

    private JvmDebuggerInvokeSpecial() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrFunctionAccessExpression irFunctionAccessExpression = expression;
        String stringConstArgument = JvmIrUtilsKt.getStringConstArgument(irFunctionAccessExpression, 0);
        String stringConstArgument2 = JvmIrUtilsKt.getStringConstArgument(irFunctionAccessExpression, 1);
        String stringConstArgument3 = JvmIrUtilsKt.getStringConstArgument(irFunctionAccessExpression, 2);
        boolean booleanConstArgument = JvmIrUtilsKt.getBooleanConstArgument(irFunctionAccessExpression, 3);
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        PromisedValueKt.materialize((PromisedValue) dispatchReceiver.accept(codegen, data));
        codegen.getMv().invokespecial(stringConstArgument, stringConstArgument2, stringConstArgument3, booleanConstArgument);
        Type returnType = Type.getReturnType(stringConstArgument3);
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        return new MaterialValue(codegen, returnType, expression.getType());
    }
}
