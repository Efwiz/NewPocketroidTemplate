package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: UnitToVoidLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\f\u0010\u0019\u001a\u00020\u001a*\u00020\u001aH\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/UnitToVoidLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/lower/AbstractValueUsageTransformer;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "shouldVoidify", "", "expr", "Lorg/jetbrains/kotlin/ir/IrStatement;", "voidify", "useAsStatement", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnitToVoidLowering extends AbstractValueUsageTransformer implements FileLoweringPass {
    private final IrBuiltIns builtIns;
    private final WasmBackendContext context;
    private final WasmSymbols symbols;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnitToVoidLowering(WasmBackendContext context) {
        super(context.getIrBuiltIns(), false, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.builtIns = context.getIrBuiltIns();
        this.symbols = context.getWasmSymbols();
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    public final IrBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final WasmSymbols getSymbols() {
        return this.symbols;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsStatement(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (!Intrinsics.areEqual(irExpression.getType(), this.builtIns.getUnitType())) {
            return irExpression;
        }
        IrExpression irExpression2 = irExpression;
        if (!shouldVoidify(irExpression2)) {
            return irExpression;
        }
        IrStatement irStatementVoidify = voidify(irExpression2);
        Intrinsics.checkNotNull(irStatementVoidify, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        return (IrExpression) irStatementVoidify;
    }

    private final boolean shouldVoidify(IrStatement expr) {
        if (!(expr instanceof IrExpression)) {
            return true;
        }
        IrExpression irExpression = (IrExpression) expr;
        if (Intrinsics.areEqual(irExpression.getType(), this.symbols.getVoidType()) || Intrinsics.areEqual(irExpression.getType(), this.builtIns.getNothingType())) {
            return false;
        }
        if (expr instanceof IrContainerExpression) {
            IrContainerExpression irContainerExpression = (IrContainerExpression) expr;
            if (!irContainerExpression.getStatements().isEmpty() && !(expr instanceof IrReturnableBlock) && shouldVoidify((IrStatement) CollectionsKt.last((List) irContainerExpression.getStatements()))) {
                return true;
            }
        } else {
            if (expr instanceof IrWhen ? true : expr instanceof IrTry) {
                return true;
            }
            if ((expr instanceof IrTypeOperatorCall) && ((IrTypeOperatorCall) expr).getOperator() == IrTypeOperator.IMPLICIT_COERCION_TO_UNIT) {
                return true;
            }
        }
        return false;
    }

    private final IrStatement voidify(IrStatement expr) {
        if (!(expr instanceof IrExpression)) {
            return expr;
        }
        IrExpression irExpression = (IrExpression) expr;
        if (Intrinsics.areEqual(irExpression.getType(), this.symbols.getVoidType()) || Intrinsics.areEqual(irExpression.getType(), this.builtIns.getNothingType())) {
            return expr;
        }
        if (expr instanceof IrContainerExpression) {
            irExpression.setType(this.symbols.getVoidType());
            IrContainerExpression irContainerExpression = (IrContainerExpression) expr;
            IrStatement irStatement = (IrStatement) CollectionsKt.lastOrNull((List) irContainerExpression.getStatements());
            if (irStatement != null) {
                irContainerExpression.getStatements().set(CollectionsKt.getLastIndex(irContainerExpression.getStatements()), voidify(irStatement));
            }
            return expr;
        }
        int i = 0;
        if (expr instanceof IrWhen) {
            irExpression.setType(this.symbols.getVoidType());
            IrWhen irWhen = (IrWhen) expr;
            for (Object obj : irWhen.getBranches()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrBranch irBranch = irWhen.getBranches().get(i);
                IrStatement irStatementVoidify = voidify(((IrBranch) obj).getResult());
                Intrinsics.checkNotNull(irStatementVoidify, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                irBranch.setResult((IrExpression) irStatementVoidify);
                i = i2;
            }
            return expr;
        }
        if (expr instanceof IrTry) {
            irExpression.setType(this.symbols.getVoidType());
            IrTry irTry = (IrTry) expr;
            IrStatement irStatementVoidify2 = voidify(irTry.getTryResult());
            Intrinsics.checkNotNull(irStatementVoidify2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            irTry.setTryResult((IrExpression) irStatementVoidify2);
            for (Object obj2 : irTry.getCatches()) {
                int i3 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrCatch irCatch = irTry.getCatches().get(i);
                IrStatement irStatementVoidify3 = voidify(((IrCatch) obj2).getResult());
                Intrinsics.checkNotNull(irStatementVoidify3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                irCatch.setResult((IrExpression) irStatementVoidify3);
                i = i3;
            }
            return expr;
        }
        if (expr instanceof IrTypeOperatorCall) {
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) expr;
            if (irTypeOperatorCall.getOperator() == IrTypeOperator.IMPLICIT_COERCION_TO_UNIT) {
                return voidify(irTypeOperatorCall.getArgument());
            }
        }
        IrCallImpl irCallImpl = new IrCallImpl(expr.getStartOffset(), expr.getEndOffset(), this.symbols.getVoidType(), this.symbols.findVoidConsumer(irExpression.getType()), 0, 1, null, null, 192, null);
        irCallImpl.putValueArgument(0, irExpression);
        return irCallImpl;
    }
}
