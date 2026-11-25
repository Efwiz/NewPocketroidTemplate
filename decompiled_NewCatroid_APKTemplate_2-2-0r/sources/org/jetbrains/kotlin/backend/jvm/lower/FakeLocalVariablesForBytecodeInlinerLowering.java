package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;

/* JADX INFO: compiled from: FakeLocalVariablesForBytecodeInlinerLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J(\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0011H\u0016J\u0014\u0010\u0019\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FakeLocalVariablesForBytecodeInlinerLowering;", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineReferenceLocator;", "Lorg/jetbrains/kotlin/backend/jvm/lower/FakeInliningLocalVariables;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "declaration", "data", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitInlineLambda", "argument", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "callee", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "scope", "addFakeLocalVariable", "name", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeLocalVariablesForBytecodeInlinerLowering extends IrInlineReferenceLocator implements FileLoweringPass, FakeInliningLocalVariables<IrFunction> {
    private final JvmBackendContext context;

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
        visitFunction2(irFunction, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.FakeInliningLocalVariables
    public JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FakeLocalVariablesForBytecodeInlinerLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.accept(this, null);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        super.visitFunction2(declaration, data);
        addFakeLocalVariableForFun(declaration, declaration);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
    public void visitInlineLambda(IrFunctionReference argument, IrFunction callee, IrValueParameter parameter, IrDeclaration scope) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(scope, "scope");
        addFakeLocalVariableForLambda(argument.getSymbol().getOwner(), argument, callee);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.FakeInliningLocalVariables
    public void addFakeLocalVariable(IrFunction irFunction, String name2) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), irFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBodyBuilder, (IrExpression) ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, 0, null, 2, null), StringsKt.removeSuffix(name2, (CharSequence) CoroutineTransformerKt.FOR_INLINE_SUFFIX), false, (IrDeclarationOrigin) IrDeclarationOrigin.DEFINED.INSTANCE, (IrType) null, 20, (Object) null);
        IrBody body = irFunction.getBody();
        if (body instanceof IrExpressionBody) {
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ((IrExpressionBody) body).getExpression()));
        } else {
            if (!(body instanceof IrBlockBody)) {
                throw new AssertionError("Unexpected body:\n" + DumpIrTreeKt.dump$default(irFunction, null, 1, null));
            }
            Iterator<T> it = ((IrBlockBody) body).getStatements().iterator();
            while (it.hasNext()) {
                irBlockBodyBuilder.unaryPlus((IrStatement) it.next());
            }
        }
        irFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
    }
}
