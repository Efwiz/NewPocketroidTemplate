package org.jetbrains.kotlin.ir.interpreter.transformer;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterChecker;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrConstExpressionTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u001e\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016J\u0018\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010 \u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\"\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010$\u001a\u00020%2\u0006\u0010\u0018\u001a\u00020&2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010'\u001a\u00020%2\u0006\u0010\u0018\u001a\u00020(2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstExpressionTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "checker", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "", "onError", "suppressExceptions", "", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "visitCall", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstExpressionTransformer extends IrConstTransformer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrConstExpressionTransformer(IrInterpreter interpreter, IrFile irFile, EvaluationMode mode, IrInterpreterChecker checker, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        super(interpreter, irFile, mode, checker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z);
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(checker, "checker");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public IrStatement visitFunction2(IrFunction declaration, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        return Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE) ? declaration : super.visitFunction2(declaration, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public IrStatement visitClass2(IrClass declaration, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (declaration.getKind() == ClassKind.ANNOTATION_CLASS) {
            return super.visitClass2(declaration, data.copy(true));
        }
        return super.visitClass2(declaration, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public IrElement visitCall2(IrCall expression, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrCall irCall = expression;
        if (IrConstTransformer.canBeInterpreted$default(this, irCall, null, 1, null)) {
            return interpret(irCall, data.getInAnnotation());
        }
        return super.visitCall2(expression, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public IrStatement visitField2(IrField declaration, IrConstTransformer.Data data) {
        IrExpression expression;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpressionBody initializer = declaration.getInitializer();
        if (initializer == null || (expression = initializer.getExpression()) == null) {
            return declaration;
        }
        if (IrConstTransformer.canBeInterpreted$default(this, IrTreeBuildUtilsKt.createGetField$default(declaration, null, 1, null), null, 1, null)) {
            initializer.setExpression(interpret(expression, true));
        }
        return super.visitField2(declaration, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public IrExpression visitGetField2(IrGetField expression, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrGetField irGetField = expression;
        if (IrConstTransformer.canBeInterpreted$default(this, irGetField, null, 1, null)) {
            return interpret(irGetField, data.getInAnnotation());
        }
        return super.visitGetField2(expression, data);
    }

    private static final IrExpression visitStringConcatenation$wrapInStringConcat(IrExpression irExpression, IrStringConcatenation irStringConcatenation) {
        return new IrStringConcatenationImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irStringConcatenation.getType(), CollectionsKt.listOf(irExpression));
    }

    private static final IrExpression visitStringConcatenation$wrapInToStringConcatAndInterpret(IrExpression irExpression, IrConstExpressionTransformer irConstExpressionTransformer, IrConstTransformer.Data data, IrStringConcatenation irStringConcatenation) {
        return irConstExpressionTransformer.interpret(visitStringConcatenation$wrapInStringConcat(irExpression, irStringConcatenation), data.getInAnnotation());
    }

    private static final String visitStringConcatenation$getConstStringOrEmpty(IrExpression irExpression) {
        return irExpression instanceof IrConst ? String.valueOf(((IrConst) irExpression).getValue()) : "";
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public IrExpression visitStringConcatenation2(IrStringConcatenation expression, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression irExpressionVisitStringConcatenation2 = super.visitStringConcatenation2(expression, data);
        IrStringConcatenation irStringConcatenation = irExpressionVisitStringConcatenation2 instanceof IrStringConcatenation ? (IrStringConcatenation) irExpressionVisitStringConcatenation2 : null;
        if (irStringConcatenation == null) {
            return expression;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (IrExpression irExpression : irStringConcatenation.getArguments()) {
            IrExpression irExpression2 = (IrExpression) CollectionsKt.lastOrNull((List) arrayList);
            IrConstExpressionTransformer irConstExpressionTransformer = this;
            if (!IrConstTransformer.canBeInterpreted$default(irConstExpressionTransformer, visitStringConcatenation$wrapInStringConcat(irExpression, expression), null, 1, null)) {
                arrayList.add(irExpression);
                arrayList2.add(new StringBuilder(visitStringConcatenation$getConstStringOrEmpty(irExpression)));
            } else if (irExpression2 == null || !IrConstTransformer.canBeInterpreted$default(irConstExpressionTransformer, visitStringConcatenation$wrapInStringConcat(irExpression2, expression), null, 1, null)) {
                IrExpression irExpressionVisitStringConcatenation$wrapInToStringConcatAndInterpret = visitStringConcatenation$wrapInToStringConcatAndInterpret(irExpression, this, data, expression);
                arrayList.add(irExpressionVisitStringConcatenation$wrapInToStringConcatAndInterpret);
                arrayList2.add(new StringBuilder(visitStringConcatenation$getConstStringOrEmpty(irExpressionVisitStringConcatenation$wrapInToStringConcatAndInterpret)));
            } else {
                IrExpression irExpressionVisitStringConcatenation$wrapInToStringConcatAndInterpret2 = visitStringConcatenation$wrapInToStringConcatAndInterpret(irExpression, this, data, expression);
                if (!(irExpressionVisitStringConcatenation$wrapInToStringConcatAndInterpret2 instanceof IrConst)) {
                    arrayList.add(irExpression);
                    arrayList2.add(new StringBuilder(visitStringConcatenation$getConstStringOrEmpty(irExpression)));
                } else {
                    ((StringBuilder) CollectionsKt.last((List) arrayList2)).append(String.valueOf(((IrConst) irExpressionVisitStringConcatenation$wrapInToStringConcatAndInterpret2).getValue()));
                    int size = arrayList.size() - 1;
                    IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
                    int iMin = Math.min(irExpression2.getStartOffset(), irExpression.getStartOffset());
                    int iMax = Math.max(irExpression2.getEndOffset(), irExpression.getEndOffset());
                    IrType type = expression.getType();
                    String string = ((StringBuilder) CollectionsKt.last((List) arrayList2)).toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    arrayList.set(size, companion.string(iMin, iMax, type, string));
                }
            }
        }
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) arrayList);
        IrConst irConst = objSingleOrNull instanceof IrConst ? (IrConst) objSingleOrNull : null;
        if (irConst != null && (irConst.getValue() instanceof String)) {
            IrConstImpl.Companion companion2 = IrConstImpl.INSTANCE;
            int startOffset = expression.getStartOffset();
            int endOffset = expression.getEndOffset();
            IrType type2 = expression.getType();
            String string2 = ((StringBuilder) CollectionsKt.single((List) arrayList2)).toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            return companion2.string(startOffset, endOffset, type2, string2);
        }
        return new IrStringConcatenationImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), arrayList);
    }
}
