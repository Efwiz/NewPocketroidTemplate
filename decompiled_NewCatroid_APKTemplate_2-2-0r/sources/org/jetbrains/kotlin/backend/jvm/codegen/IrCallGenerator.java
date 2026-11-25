package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.mapping.IrCallableMethod;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IrCallGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0017J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016J(\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J0\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrCallGenerator;", "", "afterCallEnd", "", "beforeCallStart", "genCall", "callableMethod", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrCallableMethod;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "isInsideIfCondition", "", "genValueAndPut", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "argumentExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "parameterType", "Lorg/jetbrains/org/objectweb/asm/Type;", "blockInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "DefaultCallGenerator", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrCallGenerator {
    default void afterCallEnd() {
    }

    default void beforeCallStart() {
    }

    default void genCall(IrCallableMethod callableMethod, ExpressionCodegen codegen, IrFunctionAccessExpression expression, boolean isInsideIfCondition) {
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(expression, "expression");
        codegen.getMv().visitMethodInsn(callableMethod.getInvokeOpcode(), callableMethod.getOwner().getInternalName(), callableMethod.getAsmMethod().getName(), callableMethod.getAsmMethod().getDescriptor(), callableMethod.getIsInterfaceMethod());
    }

    default void genValueAndPut(IrValueParameter irValueParameter, IrExpression argumentExpression, Type parameterType, ExpressionCodegen codegen, BlockInfo blockInfo) {
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(blockInfo, "blockInfo");
        codegen.gen(argumentExpression, parameterType, codegen.getRealType(irValueParameter), blockInfo);
    }

    /* JADX INFO: compiled from: IrCallGenerator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrCallGenerator$DefaultCallGenerator;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrCallGenerator;", "()V", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultCallGenerator implements IrCallGenerator {
        public static final DefaultCallGenerator INSTANCE = new DefaultCallGenerator();

        private DefaultCallGenerator() {
        }
    }
}
