package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ThrowIntrinsics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/ThrowException;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "exceptionClass", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThrowException extends IntrinsicMethod {
    private final Type exceptionClass;

    public ThrowException(Type exceptionClass) {
        Intrinsics.checkNotNullParameter(exceptionClass, "exceptionClass");
        this.exceptionClass = exceptionClass;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        codegen.getMv().anew(this.exceptionClass);
        codegen.getMv().dup();
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        codegen.gen(valueArgument, JAVA_STRING_TYPE, codegen.getContext().getIrBuiltIns().getStringType(), data);
        codegen.getMv().invokespecial(this.exceptionClass.getInternalName(), "<init>", "(Ljava/lang/String;)V", false);
        codegen.getMv().athrow();
        return null;
    }
}
