package org.jetbrains.kotlin.ir.interpreter.transformer;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterChecker;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: IrConstAnnotationTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u001e\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0004J\u0010\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!H\u0004J\u0014\u0010\"\u001a\u00020\u001b*\u00020\u001b2\u0006\u0010#\u001a\u00020$H\u0002J\u0014\u0010%\u001a\u00020\u001b*\u00020\u001b2\u0006\u0010&\u001a\u00020$H\u0002J\f\u0010'\u001a\u00020(*\u00020(H\u0002¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstAnnotationTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "checker", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "", "onError", "suppressExceptions", "", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "transformAnnotation", "annotation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "transformAnnotationArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", "valueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "transformAnnotations", "annotationContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "convertToConstIfPossible", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "transformSingleArg", "expectedType", "transformVarArg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstAnnotationTransformer extends IrConstTransformer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrConstAnnotationTransformer(IrInterpreter interpreter, IrFile irFile, EvaluationMode mode, IrInterpreterChecker checker, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        super(interpreter, irFile, mode, checker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z);
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(checker, "checker");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
    }

    protected final void transformAnnotations(IrAnnotationContainer annotationContainer) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        Iterator<T> it = annotationContainer.getAnnotations().iterator();
        while (it.hasNext()) {
            transformAnnotation((IrConstructorCall) it.next());
        }
    }

    private final void transformAnnotation(IrConstructorCall annotation) {
        int valueArgumentsCount = annotation.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = annotation.getValueArgument(i);
            if (valueArgument != null) {
                annotation.putValueArgument(i, transformAnnotationArgument(valueArgument, annotation.getSymbol().getOwner().getValueParameters().get(i)));
            }
        }
    }

    protected final IrExpression transformAnnotationArgument(IrExpression argument, IrValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return argument instanceof IrVararg ? transformVarArg((IrVararg) argument) : transformSingleArg(argument, valueParameter.getType());
    }

    private final IrVararg transformVarArg(IrVararg irVararg) {
        IrExpression expression;
        if (irVararg.getElements().isEmpty()) {
            return irVararg;
        }
        IrVarargImpl irVarargImpl = new IrVarargImpl(irVararg.getStartOffset(), irVararg.getEndOffset(), irVararg.getType(), irVararg.getVarargElementType());
        Iterator<IrVarargElement> it = irVararg.getElements().iterator();
        while (it.hasNext()) {
            IrExpression next = it.next();
            IrSpreadElement irSpreadElement = next instanceof IrSpreadElement ? (IrSpreadElement) next : null;
            if (irSpreadElement != null && (expression = irSpreadElement.getExpression()) != null) {
                next = expression;
            }
            if (!(next instanceof IrVararg)) {
                if (next instanceof IrExpression) {
                    IrExpressionsKt.addElement(irVarargImpl, transformSingleArg((IrExpression) next, irVararg.getVarargElementType()));
                } else {
                    IrExpressionsKt.addElement(irVarargImpl, next);
                }
            } else {
                Iterator<T> it2 = transformVarArg((IrVararg) next).getElements().iterator();
                while (it2.hasNext()) {
                    IrExpressionsKt.addElement(irVarargImpl, (IrVarargElement) it2.next());
                }
            }
        }
        return irVarargImpl;
    }

    private final IrExpression transformSingleArg(IrExpression irExpression, IrType irType) {
        if (IrConstTransformer.canBeInterpreted$default(this, irExpression, null, 1, null)) {
            return convertToConstIfPossible(interpret(irExpression, true), irType);
        }
        if (irExpression instanceof IrConstructorCall) {
            transformAnnotation((IrConstructorCall) irExpression);
        }
        return irExpression;
    }

    private final IrExpression convertToConstIfPossible(IrExpression irExpression, IrType irType) {
        if (!(irExpression instanceof IrConst) || (irType instanceof IrErrorType)) {
            return irExpression;
        }
        if (!IrTypePredicatesKt.isArray(irType)) {
            return UtilsKt.isPrimitiveArray(irType) ? convertToConstIfPossible(irExpression, irExpression.getType()) : IrUtilsKt.toIrConst(((IrConst) irExpression).getValue(), irType, irExpression.getStartOffset(), irExpression.getEndOffset());
        }
        Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.single((List) ((IrSimpleType) irType).getArguments()));
        Intrinsics.checkNotNull(typeOrNull);
        return convertToConstIfPossible(irExpression, typeOrNull);
    }
}
