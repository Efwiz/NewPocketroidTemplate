package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.AbstractMap;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: JsReturnableBlockLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0019H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f`\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsReturnableBlockTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "labelCnt", "", "map", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lkotlin/collections/HashMap;", "unitType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "unitValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "getUnitValue", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsReturnableBlockTransformer extends IrElementTransformerVoidWithContext {
    private final CommonBackendContext context;
    private int labelCnt;
    private HashMap<IrReturnableBlockSymbol, IrVariable> map;
    private final IrType unitType;

    public JsReturnableBlockTransformer(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.map = new HashMap<>();
        this.unitType = context.getIrBuiltIns().getUnitType();
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    private final IrGetObjectValueImpl getUnitValue() {
        return new IrGetObjectValueImpl(-1, -1, this.unitType, this.context.getIrBuiltIns().getUnitClass());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!(expression instanceof IrReturnableBlock)) {
            return super.visitBlock(expression);
        }
        transformChildrenVoid(expression);
        IrReturnableBlock irReturnableBlock = (IrReturnableBlock) expression;
        IrVariable irVariableRemove = this.map.remove(irReturnableBlock.getSymbol());
        if (irVariableRemove == null) {
            return expression;
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irReturnableBlock.getSymbol(), 0, 0, 6, (Object) null);
        expression.setType(this.unitType);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrStatementOrigin origin = expression.getOrigin();
        IrType type = irVariableRemove.getType();
        IrBlock irBlock = expression;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irBlock.getStartOffset(), irBlock.getEndOffset(), origin, type, true);
        irBlockBuilder.unaryPlus(irVariableRemove);
        irBlockBuilder.unaryPlus(expression);
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, irVariableRemove));
        return irBlockBuilder.getIrBlockBody();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrReturn irReturn = expression;
        transformChildrenVoid(irReturn);
        IrReturnTargetSymbol returnTargetSymbol = expression.getReturnTargetSymbol();
        if (!(returnTargetSymbol instanceof IrReturnableBlockSymbol)) {
            return expression;
        }
        AbstractMap abstractMap = this.map;
        Object objCreateTmpVariable = abstractMap.get(returnTargetSymbol);
        if (objCreateTmpVariable == null) {
            ScopeWithIr currentScope = getCurrentScope();
            Intrinsics.checkNotNull(currentScope);
            Scope scope = currentScope.getScope();
            IrType type = ((IrReturnableBlockSymbol) returnTargetSymbol).getOwner().getType();
            StringBuilder sb = new StringBuilder("tmp$ret$");
            int i = this.labelCnt;
            this.labelCnt = i + 1;
            objCreateTmpVariable = IrBuilderKt.createTmpVariable(scope, type, (120 & 2) != 0 ? null : sb.append(i).toString(), (120 & 4) != 0 ? false : true, (120 & 8) == 0 ? null : null, (120 & 16) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (120 & 32) != 0 ? -1 : 0, (120 & 64) == 0 ? 0 : -1);
            abstractMap.put(returnTargetSymbol, objCreateTmpVariable);
        }
        IrBuilderWithScope irBuilderWithScope = (IrBuilderWithScope) IrBuilderKt.at(LowerUtilsKt.createIrBuilder$default(this.context, returnTargetSymbol, 0, 0, 6, (Object) null), -1, -1);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, true);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSet$default((IrBuilderWithScope) LowerUtilsKt.at(irBlockBuilder2, irReturn), ((IrVariable) objCreateTmpVariable).getSymbol(), expression.getValue(), (IrStatementOrigin) null, 4, (Object) null));
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irReturn((IrBuilderWithScope) IrBuilderKt.at(irBlockBuilder2, -1, -1), getUnitValue()));
        return irBlockBuilder.getIrBlockBody();
    }
}
