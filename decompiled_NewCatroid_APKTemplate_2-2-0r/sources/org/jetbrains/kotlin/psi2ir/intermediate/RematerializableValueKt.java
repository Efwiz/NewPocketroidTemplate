package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;

/* JADX INFO: compiled from: RematerializableValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aD\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0000\u001a<\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0000¨\u0006\u0010"}, d2 = {"createTemporaryVariableInBlock", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "irExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "nameHint", "", "startOffset", "", "endOffset", "declareTemporaryVariableInBlock", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RematerializableValueKt {
    public static /* synthetic */ IntermediateValue createTemporaryVariableInBlock$default(Scope scope, IrGeneratorContext irGeneratorContext, IrExpression irExpression, IrStatementContainer irStatementContainer, String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            str = null;
        }
        String str2 = str;
        if ((i3 & 16) != 0) {
            i = irExpression.getStartOffset();
        }
        int i4 = i;
        if ((i3 & 32) != 0) {
            i2 = irExpression.getEndOffset();
        }
        return createTemporaryVariableInBlock(scope, irGeneratorContext, irExpression, irStatementContainer, str2, i4, i2);
    }

    public static final IntermediateValue createTemporaryVariableInBlock(Scope scope, IrGeneratorContext context, IrExpression irExpression, IrStatementContainer block, String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(scope, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(block, "block");
        return new VariableLValue(context, declareTemporaryVariableInBlock(scope, irExpression, block, str, i, i2), null, 0, 0, 28, null);
    }

    public static /* synthetic */ IrVariable declareTemporaryVariableInBlock$default(Scope scope, IrExpression irExpression, IrStatementContainer irStatementContainer, String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            str = null;
        }
        String str2 = str;
        if ((i3 & 8) != 0) {
            i = irExpression.getStartOffset();
        }
        int i4 = i;
        if ((i3 & 16) != 0) {
            i2 = irExpression.getEndOffset();
        }
        return declareTemporaryVariableInBlock(scope, irExpression, irStatementContainer, str2, i4, i2);
    }

    public static final IrVariable declareTemporaryVariableInBlock(Scope scope, IrExpression irExpression, IrStatementContainer block, String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(scope, "<this>");
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(block, "block");
        IrVariable irVariableCreateTemporaryVariable = scope.createTemporaryVariable(irExpression, (108 & 2) != 0 ? null : str, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpression.getStartOffset() : i, (108 & 64) != 0 ? irExpression.getEndOffset() : i2);
        block.getStatements().add(irVariableCreateTemporaryVariable);
        return irVariableCreateTemporaryVariable;
    }
}
