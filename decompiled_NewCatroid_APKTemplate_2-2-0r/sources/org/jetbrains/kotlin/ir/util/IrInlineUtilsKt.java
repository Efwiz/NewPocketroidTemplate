package org.jetbrains.kotlin.ir.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;

/* JADX INFO: compiled from: IrInlineUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0002\u001a\n\u0010\u0015\u001a\u00020\u0014*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n\"\u0015\u0010\u000b\u001a\u00020\f*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"inlineDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "getInlineDeclaration", "(Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "inlineFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInlineFunction", "(Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "(Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "innerInlinedBlockOrThis", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "getInnerInlinedBlockOrThis", "(Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "sourceFileSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getSourceFileSymbol", "(Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;)Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "isFunctionInlining", "", "isLambdaInlining", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrInlineUtilsKt {
    public static final IrDeclaration getInlineDeclaration(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        IrElement inlinedElement = irInlinedFunctionBlock.getInlinedElement();
        if (inlinedElement instanceof IrFunction) {
            return (IrDeclaration) inlinedElement;
        }
        if (inlinedElement instanceof IrFunctionExpression) {
            return ((IrFunctionExpression) inlinedElement).getFunction();
        }
        if (inlinedElement instanceof IrFunctionReference) {
            return ((IrFunctionReference) inlinedElement).getSymbol().getOwner();
        }
        if (inlinedElement instanceof IrPropertyReference) {
            return ((IrPropertyReference) inlinedElement).getSymbol().getOwner();
        }
        throw new AssertionError("Not supported ir element for inlining " + DumpIrTreeKt.dump$default(inlinedElement, null, 1, null));
    }

    private static final IrFunction getInlineFunction(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        IrElement inlinedElement = irInlinedFunctionBlock.getInlinedElement();
        if (inlinedElement instanceof IrFunction) {
            return (IrFunction) inlinedElement;
        }
        if (inlinedElement instanceof IrFunctionExpression) {
            return ((IrFunctionExpression) inlinedElement).getFunction();
        }
        if (inlinedElement instanceof IrFunctionReference) {
            IrFunction owner = ((IrFunctionReference) inlinedElement).getSymbol().getOwner();
            if (owner.getIsInline()) {
                return owner;
            }
        }
        return null;
    }

    public static final boolean isFunctionInlining(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        return irInlinedFunctionBlock.getInlinedElement() instanceof IrFunction;
    }

    public static final boolean isLambdaInlining(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        return !isFunctionInlining(irInlinedFunctionBlock);
    }

    public static final IrContainerExpression getInnerInlinedBlockOrThis(IrContainerExpression irContainerExpression) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(irContainerExpression, "<this>");
        IrReturnableBlock irReturnableBlock = irContainerExpression instanceof IrReturnableBlock ? (IrReturnableBlock) irContainerExpression : null;
        IrStatement irStatement = (irReturnableBlock == null || (statements = irReturnableBlock.getStatements()) == null) ? null : (IrStatement) CollectionsKt.singleOrNull((List) statements);
        IrInlinedFunctionBlock irInlinedFunctionBlock = irStatement instanceof IrInlinedFunctionBlock ? (IrInlinedFunctionBlock) irStatement : null;
        return irInlinedFunctionBlock != null ? irInlinedFunctionBlock : irContainerExpression;
    }

    public static final IrFunction getInlineFunction(IrReturnableBlock irReturnableBlock) {
        Intrinsics.checkNotNullParameter(irReturnableBlock, "<this>");
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) irReturnableBlock.getStatements());
        IrInlinedFunctionBlock irInlinedFunctionBlock = objSingleOrNull instanceof IrInlinedFunctionBlock ? (IrInlinedFunctionBlock) objSingleOrNull : null;
        if (irInlinedFunctionBlock != null) {
            return getInlineFunction(irInlinedFunctionBlock);
        }
        return null;
    }

    public static final IrFileSymbol getSourceFileSymbol(IrReturnableBlock irReturnableBlock) {
        IrFile fileOrNull;
        Intrinsics.checkNotNullParameter(irReturnableBlock, "<this>");
        IrFunction inlineFunction = getInlineFunction(irReturnableBlock);
        if (inlineFunction == null || (fileOrNull = IrUtilsKt.getFileOrNull(inlineFunction)) == null) {
            return null;
        }
        return fileOrNull.getSymbol();
    }
}
