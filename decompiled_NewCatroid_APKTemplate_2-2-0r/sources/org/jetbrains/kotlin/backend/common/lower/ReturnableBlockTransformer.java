package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;

/* JADX INFO: compiled from: ReturnableBlockLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0011H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\r\u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017²\u0006\n\u0010\u0018\u001a\u00020\u0019X\u008a\u0084\u0002²\u0006\n\u0010\u001a\u001a\u00020\u001bX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ReturnableBlockTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "containerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getContainerSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "labelCnt", "", "returnMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitContainerExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitReturn", "ir.backend.common", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDoWhileLoopImpl;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnableBlockTransformer extends IrElementTransformerVoidWithContext {
    private final IrSymbol containerSymbol;
    private final CommonBackendContext context;
    private int labelCnt;
    private final Map<IrReturnableBlockSymbol, Function1<IrReturn, IrExpression>> returnMap;

    public ReturnableBlockTransformer(CommonBackendContext context, IrSymbol irSymbol) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.containerSymbol = irSymbol;
        this.returnMap = new LinkedHashMap();
    }

    public /* synthetic */ ReturnableBlockTransformer(CommonBackendContext commonBackendContext, IrSymbol irSymbol, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? null : irSymbol);
    }

    public final IrSymbol getContainerSymbol() {
        return this.containerSymbol;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        IrExpression irExpressionInvoke;
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        Function1<IrReturn, IrExpression> function1 = this.returnMap.get(expression.getReturnTargetSymbol());
        return (function1 == null || (irExpressionInvoke = function1.invoke(expression)) == null) ? expression : irExpressionInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(final IrContainerExpression expression) {
        IrSymbol scopeOwnerSymbol;
        Iterator it;
        IrInlinedFunctionBlock irInlinedFunctionBlock;
        IrType unitType;
        Scope scope;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!(expression instanceof IrReturnableBlock)) {
            return super.visitContainerExpression(expression);
        }
        ScopeWithIr currentScope = getCurrentScope();
        if (currentScope == null || (scope = currentScope.getScope()) == null || (scopeOwnerSymbol = scope.getScopeOwnerSymbol()) == null) {
            scopeOwnerSymbol = this.containerSymbol;
        }
        IrSymbol irSymbol = scopeOwnerSymbol;
        CommonBackendContext commonBackendContext = this.context;
        Intrinsics.checkNotNull(irSymbol);
        final DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(commonBackendContext, irSymbol, 0, 0, 6, (Object) null);
        final Lazy lazy = LazyKt.lazy(new Function0<IrVariable>() { // from class: org.jetbrains.kotlin.backend.common.lower.ReturnableBlockTransformer$visitContainerExpression$variable$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrVariable invoke() {
                Scope scope2 = declarationIrBuilderCreateIrBuilder$default.getScope();
                IrType type = expression.getType();
                StringBuilder sb = new StringBuilder("tmp$ret$");
                ReturnableBlockTransformer returnableBlockTransformer = this;
                int i = returnableBlockTransformer.labelCnt;
                returnableBlockTransformer.labelCnt = i + 1;
                return IrBuilderKt.createTmpVariable(scope2, type, (120 & 2) != 0 ? null : sb.append(i).toString(), (120 & 4) != 0 ? false : true, (120 & 8) == 0 ? null : null, (120 & 16) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (120 & 32) != 0 ? -1 : 0, (120 & 64) == 0 ? 0 : -1);
            }
        });
        final Lazy lazy2 = LazyKt.lazy(new Function0<IrDoWhileLoopImpl>() { // from class: org.jetbrains.kotlin.backend.common.lower.ReturnableBlockTransformer$visitContainerExpression$loop$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrDoWhileLoopImpl invoke() {
                IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(expression.getStartOffset(), expression.getEndOffset(), this.getContext().getIrBuiltIns().getUnitType(), expression.getOrigin());
                ReturnableBlockTransformer returnableBlockTransformer = this;
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                StringBuilder sb = new StringBuilder("l$ret$");
                int i = returnableBlockTransformer.labelCnt;
                returnableBlockTransformer.labelCnt = i + 1;
                irDoWhileLoopImpl.setLabel(sb.append(i).toString());
                irDoWhileLoopImpl.setCondition(ExpressionHelpersKt.irBoolean(declarationIrBuilder, false));
                return irDoWhileLoopImpl;
            }
        });
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        IrReturnableBlock irReturnableBlock = (IrReturnableBlock) expression;
        this.returnMap.put(irReturnableBlock.getSymbol(), new Function1<IrReturn, IrExpression>() { // from class: org.jetbrains.kotlin.backend.common.lower.ReturnableBlockTransformer.visitContainerExpression.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrReturn returnExpression) {
                Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
                booleanRef.element = true;
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                Lazy<IrVariable> lazy3 = lazy;
                Lazy<IrDoWhileLoopImpl> lazy4 = lazy2;
                IrReturn irReturn = returnExpression;
                IrType type = irReturn.getType();
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irReturn.getStartOffset(), irReturn.getEndOffset(), null, type, true);
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSet$default(irBlockBuilder2, ReturnableBlockTransformer.visitContainerExpression$lambda$0(lazy3).getSymbol(), returnExpression.getValue(), (IrStatementOrigin) null, 4, (Object) null));
                irBlockBuilder.unaryPlus(IrBuilderKt.irBreak(irBlockBuilder2, ReturnableBlockTransformer.visitContainerExpression$lambda$1(lazy4)));
                return irBlockBuilder.getIrBlockBody();
            }
        });
        List<IrStatement> statements = expression.getStatements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(statements, 10));
        Iterator it2 = statements.iterator();
        int i = 0;
        while (it2.hasNext()) {
            Object next = it2.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatementVisitContainerExpression$transformSingleStatement = (IrStatement) next;
            boolean z = true;
            if (expression.getStatements().size() == 1 && (irStatementVisitContainerExpression$transformSingleStatement instanceof IrInlinedFunctionBlock)) {
                IrInlinedFunctionBlock irInlinedFunctionBlock2 = (IrInlinedFunctionBlock) irStatementVisitContainerExpression$transformSingleStatement;
                int lastIndex = CollectionsKt.getLastIndex(irInlinedFunctionBlock2.getStatements());
                int i3 = 0;
                for (IrStatement irStatement : irInlinedFunctionBlock2.getStatements()) {
                    int i4 = i3 + 1;
                    boolean z2 = i3 == lastIndex ? z : false;
                    Iterator it3 = it2;
                    int i5 = lastIndex;
                    IrInlinedFunctionBlock irInlinedFunctionBlock3 = irInlinedFunctionBlock2;
                    boolean z3 = z;
                    IrStatement irStatement2 = irStatementVisitContainerExpression$transformSingleStatement;
                    IrStatement irStatementVisitContainerExpression$transformSingleStatement2 = visitContainerExpression$transformSingleStatement(expression, this, booleanRef, declarationIrBuilderCreateIrBuilder$default, lazy, irStatement, z2);
                    irInlinedFunctionBlock3.getStatements().set(i3, irStatementVisitContainerExpression$transformSingleStatement2);
                    if (z2) {
                        IrExpression irExpression = irStatementVisitContainerExpression$transformSingleStatement2 instanceof IrExpression ? (IrExpression) irStatementVisitContainerExpression$transformSingleStatement2 : null;
                        if (irExpression == null || (unitType = irExpression.getType()) == null) {
                            unitType = this.context.getIrBuiltIns().getUnitType();
                        }
                        irInlinedFunctionBlock = irInlinedFunctionBlock3;
                        irInlinedFunctionBlock.setType(unitType);
                    } else {
                        irInlinedFunctionBlock = irInlinedFunctionBlock3;
                    }
                    irInlinedFunctionBlock2 = irInlinedFunctionBlock;
                    i3 = i4;
                    irStatementVisitContainerExpression$transformSingleStatement = irStatement2;
                    it2 = it3;
                    lastIndex = i5;
                    z = z3;
                }
                it = it2;
            } else {
                it = it2;
                irStatementVisitContainerExpression$transformSingleStatement = visitContainerExpression$transformSingleStatement(expression, this, booleanRef, declarationIrBuilderCreateIrBuilder$default, lazy, irStatementVisitContainerExpression$transformSingleStatement, i == CollectionsKt.getLastIndex(expression.getStatements()));
            }
            arrayList.add(irStatementVisitContainerExpression$transformSingleStatement);
            i = i2;
            it2 = it;
        }
        ArrayList arrayList2 = arrayList;
        this.returnMap.remove(irReturnableBlock.getSymbol());
        if (!booleanRef.element) {
            return new IrBlockImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), arrayList2);
        }
        visitContainerExpression$lambda$1(lazy2).setBody(new IrBlockImpl(expression.getStartOffset(), expression.getEndOffset(), this.context.getIrBuiltIns().getUnitType(), expression.getOrigin(), arrayList2));
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrContainerExpression irContainerExpression = expression;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irContainerExpression.getStartOffset(), irContainerExpression.getEndOffset(), expression.getOrigin(), irContainerExpression.getType(), false, 64, null);
        irBlockBuilder.unaryPlus(visitContainerExpression$lambda$0(lazy));
        irBlockBuilder.unaryPlus(visitContainerExpression$lambda$1(lazy2));
        if (!IrTypePredicatesKt.isUnit(expression.getType())) {
            expression.setType(irBlockBuilder.getContext().getIrBuiltIns().getUnitType());
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet((IrBuilderWithScope) IrBuilderKt.at(irBlockBuilder, -1, -1), visitContainerExpression$lambda$0(lazy)));
        }
        return irBlockBuilder.getIrBlockBody();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrVariable visitContainerExpression$lambda$0(Lazy<? extends IrVariable> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrDoWhileLoopImpl visitContainerExpression$lambda$1(Lazy<IrDoWhileLoopImpl> lazy) {
        return lazy.getValue();
    }

    private static final IrStatement visitContainerExpression$transformSingleStatement(IrContainerExpression irContainerExpression, ReturnableBlockTransformer returnableBlockTransformer, Ref.BooleanRef booleanRef, DeclarationIrBuilder declarationIrBuilder, Lazy<? extends IrVariable> lazy, IrStatement irStatement, boolean z) {
        if (z && (irStatement instanceof IrReturn)) {
            IrReturn irReturn = (IrReturn) irStatement;
            if (Intrinsics.areEqual(irReturn.getReturnTargetSymbol(), ((IrReturnableBlock) irContainerExpression).getSymbol())) {
                returnableBlockTransformer.transformChildrenVoid(irStatement);
                return !booleanRef.element ? irReturn.getValue() : ExpressionHelpersKt.irSet$default(declarationIrBuilder, visitContainerExpression$lambda$0(lazy).getSymbol(), irReturn.getValue(), (IrStatementOrigin) null, 4, (Object) null);
            }
        }
        return IrElementsKt.transformStatement(irStatement, returnableBlockTransformer);
    }
}
