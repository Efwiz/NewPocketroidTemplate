package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BinaryOp.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/BinaryOp;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "opcode", "", "(I)V", "shift", "", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryOp extends IntrinsicMethod {
    private final int opcode;

    public BinaryOp(int i) {
        this.opcode = i;
    }

    private final boolean shift() {
        int i = this.opcode;
        return i == 120 || i == 122 || i == 124;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        List<Type> listListOf;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        final Type returnType = signature.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        final Type typeNumberFunctionOperandType = AsmUtil.numberFunctionOperandType(returnType);
        Intrinsics.checkNotNullExpressionValue(typeNumberFunctionOperandType, "numberFunctionOperandType(...)");
        if (!IrTypePredicatesKt.isChar(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(expression.getSymbol().getOwner())))) {
            Type[] typeArr = new Type[2];
            typeArr[0] = typeNumberFunctionOperandType;
            typeArr[1] = shift() ? Type.INT_TYPE : typeNumberFunctionOperandType;
            listListOf = CollectionsKt.listOf((Object[]) typeArr);
        } else {
            listListOf = CollectionsKt.listOf((Object[]) new Type[]{Type.CHAR_TYPE, signature.getValueParameters().get(0).getAsmType()});
        }
        return IntrinsicFunction.INSTANCE.create(expression, signature, classCodegen, listListOf, new Function2<IntrinsicFunction, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.BinaryOp.toCallable.1
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
                it.visitInsn(returnType.getOpcode(this.opcode));
                StackValue.coerce(typeNumberFunctionOperandType, returnType, it);
            }
        });
    }
}
