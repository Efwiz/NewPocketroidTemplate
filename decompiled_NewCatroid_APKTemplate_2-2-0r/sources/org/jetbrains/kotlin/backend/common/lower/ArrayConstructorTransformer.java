package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.ir.IrInlinable;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ArrayConstructorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ArrayConstructorTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Companion", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ArrayConstructorTransformer extends IrElementTransformerVoidWithContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final IrSymbolOwner container;
    private final CommonBackendContext context;

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public final IrSymbolOwner getContainer() {
        return this.container;
    }

    public ArrayConstructorTransformer(CommonBackendContext context, IrSymbolOwner container) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(container, "container");
        this.context = context;
        this.container = container;
    }

    /* JADX INFO: compiled from: ArrayConstructorLowering.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ArrayConstructorTransformer$Companion;", "", "()V", "arrayInlineToSizeConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "arrayInlineToSizeConstructor$ir_backend_common", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrFunctionSymbol arrayInlineToSizeConstructor$ir_backend_common(CommonBackendContext context, IrConstructor irConstructor) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(irConstructor, "irConstructor");
            IrClassSymbol symbol = AdditionalIrUtilsKt.getConstructedClass(irConstructor).getSymbol();
            IrConstructorSymbol irConstructorSymbol = null;
            if (irConstructor.getValueParameters().size() != 2) {
                return null;
            }
            if (Intrinsics.areEqual(symbol, context.getIrBuiltIns().getArrayClass())) {
                return context.getIr().getSymbols().getArrayOfNulls();
            }
            if (!context.getIrBuiltIns().getPrimitiveArraysToPrimitiveTypes().containsKey(symbol)) {
                return null;
            }
            boolean z = false;
            for (IrConstructorSymbol irConstructorSymbol2 : IrUtilsKt.getConstructors(symbol)) {
                if (irConstructorSymbol2.getOwner().getValueParameters().size() == 1) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irConstructorSymbol = irConstructorSymbol2;
                    z = true;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            return irConstructorSymbol;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunctionSymbol irFunctionSymbolArrayInlineToSizeConstructor$ir_backend_common = INSTANCE.arrayInlineToSizeConstructor$ir_backend_common(this.context, expression.getSymbol().getOwner());
        if (irFunctionSymbolArrayInlineToSizeConstructor$ir_backend_common == null) {
            return super.visitConstructorCall(expression);
        }
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        ArrayConstructorTransformer arrayConstructorTransformer = this;
        IrExpression irExpressionTransform = valueArgument.transform((IrElementTransformer<? super Object>) arrayConstructorTransformer, (Object) null);
        IrExpression valueArgument2 = expression.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2);
        IrExpression irExpressionTransform2 = valueArgument2.transform((IrElementTransformer<? super Object>) arrayConstructorTransformer, (Object) null);
        if (IrTypePredicatesKt.isNothing(irExpressionTransform2.getType())) {
            return irExpressionTransform2;
        }
        ScopeWithIr currentScope = getCurrentScope();
        if (currentScope == null) {
            currentScope = createScope(this.container);
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), expression.getStartOffset(), expression.getEndOffset(), null, null, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, (IrExpression) ExpressionHelpersKt.irInt$default(irBlockBuilder3, 0, null, 2, null), (String) null, true, (IrDeclarationOrigin) null, (IrType) null, 26, (Object) null);
        IrVariable irVariableCreateTmpVariable$default2 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, irExpressionTransform, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder3, irFunctionSymbolArrayInlineToSizeConstructor$ir_backend_common, expression.getType());
        IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionAccessExpressionIrCall, expression, 0, 2, null);
        IrVariable irVariable = irVariableCreateTmpVariable$default2;
        irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable));
        IrVariable irVariableCreateTmpVariable$default3 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, (IrExpression) irFunctionAccessExpressionIrCall, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
        IrInlinable irInlinableAsInlinable = IrInlineUtilsKt.asInlinable(irExpressionTransform2, irBlockBuilder2);
        IrWhileLoopImpl irWhileLoopImplIrWhile$default = IrBuilderKt.irWhile$default(irBlockBuilder3, null, 1, null);
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = irBlockBuilder.getContext().getIrBuiltIns().getLessFunByOperandType().get(IrTypesKt.getClassifierOrFail(irVariableCreateTmpVariable$default.getType()));
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder3, irSimpleFunctionSymbol);
        IrVariable irVariable2 = irVariableCreateTmpVariable$default;
        irCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable2));
        irCall.putValueArgument(1, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable));
        irWhileLoopImplIrWhile$default.setCondition(irCall);
        IrBlockBuilder irBlockBuilder4 = new IrBlockBuilder(irBlockBuilder3.getContext(), irBlockBuilder3.getScope(), irBlockBuilder3.getStartOffset(), irBlockBuilder3.getEndOffset(), null, null, false, 64, null);
        IrBlockBuilder irBlockBuilder5 = irBlockBuilder4;
        IrVariable irVariableCreateTmpVariable$default4 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder4, (IrExpression) ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable2), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
        IrClass irClass = IrTypesKt.getClass(irVariableCreateTmpVariable$default3.getType());
        Intrinsics.checkNotNull(irClass);
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.SET)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunction = irSimpleFunction2;
                z = true;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBuilder5, irSimpleFunction);
        IrVariable irVariable3 = irVariableCreateTmpVariable$default3;
        irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable3));
        boolean z2 = false;
        irFunctionAccessExpressionIrCall2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder5, irVariableCreateTmpVariable$default4));
        irFunctionAccessExpressionIrCall2.putValueArgument(1, IrInlineUtilsKt.inline(irInlinableAsInlinable, ExpressionHelpersKt.getParent(irBlockBuilder5), (List<? extends IrValueDeclaration>) CollectionsKt.listOf(irVariableCreateTmpVariable$default4)));
        irBlockBuilder4.unaryPlus(irFunctionAccessExpressionIrCall2);
        IrClass irClass2 = IrTypesKt.getClass(irVariableCreateTmpVariable$default.getType());
        Intrinsics.checkNotNull(irClass2);
        IrSimpleFunction irSimpleFunction3 = null;
        for (IrSimpleFunction irSimpleFunction4 : IrUtilsKt.getFunctions(irClass2)) {
            if (Intrinsics.areEqual(irSimpleFunction4.getName(), OperatorNameConventions.INC)) {
                if (z2) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z2 = true;
                irSimpleFunction3 = irSimpleFunction4;
            }
        }
        if (!z2) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        irBlockBuilder4.unaryPlus(ExpressionHelpersKt.irSet(irBlockBuilder5, irVariableCreateTmpVariable$default.getSymbol(), ExpressionHelpersKt.irCallOp(irBlockBuilder5, irSimpleFunction3.getSymbol(), irVariableCreateTmpVariable$default.getType(), ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable2), (24 & 8) != 0 ? null : null, (24 & 16) != 0 ? null : null), IrStatementOrigin.PREFIX_INCR.INSTANCE));
        irWhileLoopImplIrWhile$default.setBody(irBlockBuilder4.getIrBlockBody());
        irBlockBuilder.unaryPlus(irWhileLoopImplIrWhile$default);
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable3));
        return irBlockBuilder.getIrBlockBody();
    }
}
