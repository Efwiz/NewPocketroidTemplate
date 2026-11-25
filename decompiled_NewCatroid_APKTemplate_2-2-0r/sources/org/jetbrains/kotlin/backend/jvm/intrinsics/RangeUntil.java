package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: RangeUntil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/RangeUntil;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeUntil extends IntrinsicMethod {
    public static final RangeUntil INSTANCE = new RangeUntil();

    private RangeUntil() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        return new IntrinsicFunction(expression, signature, classCodegen, IntrinsicFunctionKt.argTypes(expression, classCodegen)) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.RangeUntil.toCallable.1
            final /* synthetic */ JvmMethodSignature $signature;

            {
                this.$signature = signature;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
            public void genInvokeInstruction(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                Type returnType = this.$signature.getReturnType();
                Type[] typeArr = (Type[]) getArgsTypes().toArray(new Type[0]);
                v.invokestatic("kotlin/ranges/RangesKt", "until", Type.getMethodDescriptor(returnType, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), false);
            }
        };
    }
}
