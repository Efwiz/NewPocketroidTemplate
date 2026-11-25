package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: Increment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Increment;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "myDelta", "", "(I)V", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Increment extends IntrinsicMethod {
    private final int myDelta;

    public Increment(int i) {
        this.myDelta = i;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, final JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        return IntrinsicFunction.Companion.create$default(IntrinsicFunction.INSTANCE, expression, signature, classCodegen, null, new Function2<IntrinsicFunction, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.Increment.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicFunction intrinsicFunction, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicFunction, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicFunction create, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(create, "$this$create");
                Intrinsics.checkNotNullParameter(it, "it");
                DescriptorAsmUtil.genIncrement(signature.getReturnType(), this.myDelta, it);
            }
        }, 8, null);
    }
}
