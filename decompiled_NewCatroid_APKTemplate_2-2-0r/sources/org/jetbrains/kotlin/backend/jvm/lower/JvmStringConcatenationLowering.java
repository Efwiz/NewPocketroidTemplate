package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: JvmStringConcatenationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001f\u0010\u0013\u001a\u0004\u0018\u00010\u00072\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015H\u0082\bJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\nH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\"\u0010\"\u001a\u00020\u001f*\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001f0%2\u0006\u0010&\u001a\u00020\u001fH\u0002J\f\u0010'\u001a\u00020\u0017*\u00020\u0007H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00070\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmStringConcatenationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "appendAnyNFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "appendFunctionsByParameterType", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "appendStringNFunction", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "stringBuilder", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "toStringFunction", "findStringBuilderAppendFunctionWithParameter", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "typeToAppendFunction", ModuleXmlParser.TYPE, "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "appendWindow", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "arguments", "", "stringBuilder0", "isAppendFunction", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmStringConcatenationLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final IrSimpleFunction appendAnyNFunction;
    private final Map<IrType, IrSimpleFunction> appendFunctionsByParameterType;
    private final IrSimpleFunction appendStringNFunction;
    private final IrConstructor constructor;
    private final JvmBackendContext context;
    private final IrClass stringBuilder;
    private final IrSimpleFunction toStringFunction;

    public JvmStringConcatenationLowering(JvmBackendContext context) {
        IrSimpleFunction next;
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        IrClass owner = context.getIr().getSymbols().getStringBuilder().getOwner();
        this.stringBuilder = owner;
        IrSimpleFunction irSimpleFunction = null;
        IrConstructor irConstructor = null;
        boolean z = false;
        for (IrConstructor irConstructor2 : IrUtilsKt.getConstructors(owner)) {
            if (irConstructor2.getValueParameters().isEmpty()) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irConstructor = irConstructor2;
            }
        }
        if (z) {
            this.constructor = irConstructor;
            this.toStringFunction = JvmStringConcatenationLoweringKt.getToStringFunction(this.stringBuilder);
            Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(this.stringBuilder).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                IrSimpleFunction irSimpleFunction2 = next;
                if (isAppendFunction(irSimpleFunction2) && IrTypePredicatesKt.isNullableAny(irSimpleFunction2.getValueParameters().get(0).getType())) {
                    break;
                }
            }
            IrSimpleFunction irSimpleFunction3 = next;
            Intrinsics.checkNotNull(irSimpleFunction3);
            this.appendAnyNFunction = irSimpleFunction3;
            for (IrSimpleFunction irSimpleFunction4 : IrUtilsKt.getFunctions(this.stringBuilder)) {
                IrSimpleFunction irSimpleFunction5 = irSimpleFunction4;
                if (isAppendFunction(irSimpleFunction5) && Intrinsics.areEqual(IrTypesKt.getClassOrNull(irSimpleFunction5.getValueParameters().get(0).getType()), this.context.getIrBuiltIns().getStringClass())) {
                    irSimpleFunction = irSimpleFunction4;
                    break;
                }
            }
            IrSimpleFunction irSimpleFunction6 = irSimpleFunction;
            Intrinsics.checkNotNull(irSimpleFunction6);
            this.appendStringNFunction = irSimpleFunction6;
            Sequence sequenceFilter = SequencesKt.filter(IrUtilsKt.getFunctions(this.stringBuilder), new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmStringConcatenationLowering$appendFunctionsByParameterType$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrSimpleFunction it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return Boolean.valueOf(this.this$0.isAppendFunction(it2));
                }
            });
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : sequenceFilter) {
                linkedHashMap.put(((IrSimpleFunction) obj).getValueParameters().get(0).getType(), obj);
            }
            this.appendFunctionsByParameterType = linkedHashMap;
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAppendFunction(IrSimpleFunction irSimpleFunction) {
        return Intrinsics.areEqual(irSimpleFunction.getName().asString(), "append") && irSimpleFunction.getValueParameters().size() == 1;
    }

    private final IrSimpleFunction typeToAppendFunction(IrType type) {
        IrSimpleFunction irSimpleFunction = this.appendFunctionsByParameterType.get(type);
        if (irSimpleFunction != null) {
            return irSimpleFunction;
        }
        if (Intrinsics.areEqual(IrTypesKt.getClassOrNull(type), this.context.getIrBuiltIns().getStringClass())) {
            return this.appendStringNFunction;
        }
        return this.appendAnyNFunction;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrStringConcatenation irStringConcatenation = expression;
        IrElementTransformerVoidKt.transformChildrenVoid(irStringConcatenation, this);
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope, irStringConcatenation);
        List<IrExpression> arguments = expression.getArguments();
        if (arguments.isEmpty()) {
            return ExpressionHelpersKt.irString(jvmIrBuilderCreateJvmIrBuilder, "");
        }
        if (arguments.size() == 1) {
            IrExpression irExpressionLowerInlineClassArgument = JvmStringConcatenationLoweringKt.lowerInlineClassArgument(jvmIrBuilderCreateJvmIrBuilder, arguments.get(0));
            return irExpressionLowerInlineClassArgument == null ? JvmStringConcatenationLoweringKt.callToString(jvmIrBuilderCreateJvmIrBuilder, JvmStringConcatenationLoweringKt.unwrapImplicitNotNull(arguments.get(0))) : irExpressionLowerInlineClassArgument;
        }
        if (arguments.size() < 23) {
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, this.toStringFunction);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(appendWindow(jvmIrBuilderCreateJvmIrBuilder, arguments, ExpressionHelpersKt.irCall(jvmIrBuilder, this.constructor)));
            return irFunctionAccessExpressionIrCall;
        }
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilderCreateJvmIrBuilder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder2.getContext(), jvmIrBuilder2.getScope(), jvmIrBuilder2.getStartOffset(), jvmIrBuilder2.getEndOffset(), null, null, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, ExpressionHelpersKt.irCall(irBlockBuilder2, this.constructor), null, null, false, null, 30, null);
        Iterator it = CollectionsKt.windowed(arguments, 23, 23, true).iterator();
        while (it.hasNext()) {
            irBlockBuilder.unaryPlus(appendWindow(jvmIrBuilderCreateJvmIrBuilder, (List) it.next(), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default)));
        }
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBuilder2, this.toStringFunction);
        irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default));
        irBlockBuilder.unaryPlus(irFunctionAccessExpressionIrCall2);
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression appendWindow(JvmIrBuilder jvmIrBuilder, List<? extends IrExpression> list, IrExpression irExpression) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            IrExpression irExpressionNormalizeArgument = JvmStringConcatenationLoweringKt.normalizeArgument(jvmIrBuilder, (IrExpression) it.next());
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, typeToAppendFunction(irExpressionNormalizeArgument.getType()));
            irFunctionAccessExpressionIrCall.setDispatchReceiver(irExpression);
            IrExpression irExpressionLowerInlineClassArgument = JvmStringConcatenationLoweringKt.lowerInlineClassArgument(jvmIrBuilder, irExpressionNormalizeArgument);
            if (irExpressionLowerInlineClassArgument == null) {
                irExpressionLowerInlineClassArgument = JvmStringConcatenationLoweringKt.unwrapImplicitNotNull(irExpressionNormalizeArgument);
            }
            irFunctionAccessExpressionIrCall.putValueArgument(0, irExpressionLowerInlineClassArgument);
            irExpression = irFunctionAccessExpressionIrCall;
        }
        return irExpression;
    }
}
