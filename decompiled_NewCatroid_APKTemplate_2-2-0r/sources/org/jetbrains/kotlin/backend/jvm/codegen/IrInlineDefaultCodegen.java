package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.mapping.IrCallableMethod;
import org.jetbrains.kotlin.codegen.inline.MethodBodyVisitor;
import org.jetbrains.kotlin.codegen.inline.SMAPAndMethodNode;
import org.jetbrains.kotlin.codegen.inline.SourceMapCopier;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IrInlineDefaultCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J0\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineDefaultCodegen;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineCallGenerator;", "()V", "genInlineCall", "", "callableMethod", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrCallableMethod;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "isInsideIfCondition", "", "genValueAndPut", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "argumentExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "parameterType", "Lorg/jetbrains/org/objectweb/asm/Type;", "blockInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInlineDefaultCodegen implements IrInlineCallGenerator {
    public static final IrInlineDefaultCodegen INSTANCE = new IrInlineDefaultCodegen();

    private IrInlineDefaultCodegen() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrCallGenerator
    public void genValueAndPut(IrValueParameter irValueParameter, IrExpression argumentExpression, Type parameterType, ExpressionCodegen codegen, BlockInfo blockInfo) {
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(blockInfo, "blockInfo");
        if ((argumentExpression instanceof IrGetValue) || !(argumentExpression instanceof IrTypeOperatorCall)) {
            return;
        }
        boolean z = ((IrTypeOperatorCall) argumentExpression).getArgument() instanceof IrGetValue;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrInlineCallGenerator
    public void genInlineCall(IrCallableMethod callableMethod, ExpressionCodegen codegen, IrFunctionAccessExpression expression, boolean isInsideIfCondition) {
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunction owner = expression.getSymbol().getOwner();
        SMAPAndMethodNode sMAPAndMethodNodeGenerateMethodNode = codegen.getClassCodegen().generateMethodNode(owner);
        SourceMapCopier sourceMapCopier = new SourceMapCopier(codegen.getSmap(), sMAPAndMethodNodeGenerateMethodNode.getClassSMAP(), null, 4, null);
        sMAPAndMethodNodeGenerateMethodNode.getNode().accept(new MethodBodyVisitor((Type.getArgumentsAndReturnSizes(callableMethod.getAsmMethod().getDescriptor()) >>> 2) - (IrCodegenUtilsKt.isStatic(owner) ? 1 : 0), sourceMapCopier, codegen.getVisitor()) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.IrInlineDefaultCodegen.genInlineCall.1
            final /* synthetic */ int $argsSize;
            final /* synthetic */ SourceMapCopier $childSourceMapper;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(instructionAdapter);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLocalVariable(String name2, String desc, String signature, Label start, Label end, int index) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                Intrinsics.checkNotNullParameter(start, "start");
                Intrinsics.checkNotNullParameter(end, "end");
                if (index >= this.$argsSize) {
                    super.visitLocalVariable(name2, desc, signature, start, end, index);
                }
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLineNumber(int line, Label start) {
                super.visitLineNumber(this.$childSourceMapper.mapLineNumber(line), start);
            }
        });
    }
}
