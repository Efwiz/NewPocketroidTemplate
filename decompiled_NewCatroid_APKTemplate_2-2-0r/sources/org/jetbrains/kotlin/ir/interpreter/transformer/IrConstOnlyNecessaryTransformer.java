package org.jetbrains.kotlin.ir.interpreter.transformer;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterChecker;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;

/* JADX INFO: compiled from: IrConstOnlyNecessaryTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u001e\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016J\u0018\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010 \u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010#\u001a\u00020!2\u0006\u0010\u0018\u001a\u00020$2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstOnlyNecessaryTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstExpressionTransformer;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "checker", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "", "onError", "suppressExceptions", "", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "visitCall", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;", "visitField", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstOnlyNecessaryTransformer extends IrConstExpressionTransformer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrConstOnlyNecessaryTransformer(IrInterpreter interpreter, IrFile irFile, EvaluationMode mode, IrInterpreterChecker checker, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        super(interpreter, irFile, mode, checker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z);
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(checker, "checker");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstExpressionTransformer, org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public IrElement visitCall2(IrCall expression, IrConstTransformer.Data data) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrSimpleFunction owner2 = expression.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner2 instanceof IrSimpleFunction ? owner2 : null;
        boolean z = false;
        if (irSimpleFunction != null && (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsConst()) {
            z = true;
        }
        if (!data.getInAnnotation() && !z) {
            expression.transformChildren(this, data);
            return expression;
        }
        return super.visitCall2(expression, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstExpressionTransformer, org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public IrExpression visitGetField2(IrGetField expression, IrConstTransformer.Data data) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrPropertySymbol correspondingPropertySymbol = expression.getSymbol().getOwner().getCorrespondingPropertySymbol();
        boolean z = false;
        if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsConst()) {
            z = true;
        }
        return (data.getInAnnotation() || z) ? super.visitGetField2(expression, data) : expression;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstExpressionTransformer, org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public IrExpression visitStringConcatenation2(IrStringConcatenation expression, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!data.getInAnnotation()) {
            expression.transformChildren(this, data);
            return expression;
        }
        return super.visitStringConcatenation2(expression, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstExpressionTransformer, org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public IrStatement visitField2(IrField declaration, IrConstTransformer.Data data) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || !owner.getIsConst()) {
            declaration.transformChildren(this, data);
            return declaration;
        }
        return super.visitField2(declaration, data);
    }
}
