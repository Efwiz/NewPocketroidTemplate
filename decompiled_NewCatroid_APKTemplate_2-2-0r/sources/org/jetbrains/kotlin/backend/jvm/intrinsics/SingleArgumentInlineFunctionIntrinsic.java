package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.IrExpressionLambdaImpl;
import org.jetbrains.kotlin.backend.jvm.codegen.IrSourceCompilerForInline;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: SingleArgumentInlineFunctionIntrinsic.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/SingleArgumentInlineFunctionIntrinsic;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SingleArgumentInlineFunctionIntrinsic extends IntrinsicMethod {
    public static final SingleArgumentInlineFunctionIntrinsic INSTANCE = new SingleArgumentInlineFunctionIntrinsic();

    private SingleArgumentInlineFunctionIntrinsic() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrSourceCompilerForInline irSourceCompilerForInline = new IrSourceCompilerForInline(codegen.getState(), expression, expression.getSymbol().getOwner(), codegen, data);
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrFunctionReference irFunctionReferenceUnwrapInlineLambda = JvmIrInlineUtilsKt.unwrapInlineLambda(valueArgument);
        if (irFunctionReferenceUnwrapInlineLambda != null) {
            IrExpressionLambdaImpl irExpressionLambdaImpl = new IrExpressionLambdaImpl(codegen, irFunctionReferenceUnwrapInlineLambda);
            irExpressionLambdaImpl.generateLambdaBody(irSourceCompilerForInline);
            codegen.getContext().getTypeToCachedSMAP().put(irExpressionLambdaImpl.getLambdaClassType(), irExpressionLambdaImpl.getNode().getClassSMAP());
        }
        return PromisedValueKt.getUnitValue(codegen);
    }
}
