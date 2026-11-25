package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: IrIllegalArgumentException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIllegalArgumentException;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "exceptionTypeDescriptor", "Lorg/jetbrains/org/objectweb/asm/Type;", "getExceptionTypeDescriptor", "()Lorg/jetbrains/org/objectweb/asm/Type;", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrIllegalArgumentException extends IntrinsicMethod {
    public static final IrIllegalArgumentException INSTANCE = new IrIllegalArgumentException();
    private static final Type exceptionTypeDescriptor;

    private IrIllegalArgumentException() {
    }

    static {
        Type type = Type.getType((Class<?>) IllegalArgumentException.class);
        Intrinsics.checkNotNull(type);
        exceptionTypeDescriptor = type;
    }

    public final Type getExceptionTypeDescriptor() {
        return exceptionTypeDescriptor;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        return new IntrinsicFunction(expression, signature, classCodegen, CollectionsKt.listOf(AsmTypes.JAVA_STRING_TYPE)) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.IrIllegalArgumentException.toCallable.1
            @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
            public void genInvokeInstruction(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                v.invokespecial(IrIllegalArgumentException.INSTANCE.getExceptionTypeDescriptor().getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, AsmTypes.JAVA_STRING_TYPE), false);
                v.athrow();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
            public StackValue invoke(InstructionAdapter v, ExpressionCodegen codegen, BlockInfo data, IrFunctionAccessExpression expression2) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(data, "data");
                Intrinsics.checkNotNullParameter(expression2, "expression");
                codegen.markLineNumber(expression2, true);
                v.anew(IrIllegalArgumentException.INSTANCE.getExceptionTypeDescriptor());
                v.dup();
                return super.invoke(v, codegen, data, expression2);
            }
        };
    }
}
