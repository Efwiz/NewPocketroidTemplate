package org.jetbrains.kotlin.ir.interpreter.transformer;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterChecker;

/* JADX INFO: compiled from: IrConstDeclarationAnnotationTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u001e\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cH\u0016¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstDeclarationAnnotationTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstAnnotationTransformer;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "checker", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "", "onError", "suppressExceptions", "", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "data", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;", "visitFile", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstDeclarationAnnotationTransformer extends IrConstAnnotationTransformer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrConstDeclarationAnnotationTransformer(IrInterpreter interpreter, IrFile irFile, EvaluationMode mode, IrInterpreterChecker checker, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        super(interpreter, irFile, mode, checker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z);
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(checker, "checker");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public IrFile visitFile2(IrFile declaration, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        transformAnnotations(declaration);
        return super.visitFile2(declaration, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public IrStatement visitDeclaration2(IrDeclarationBase declaration, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        transformAnnotations(declaration);
        return super.visitDeclaration2(declaration, data);
    }
}
