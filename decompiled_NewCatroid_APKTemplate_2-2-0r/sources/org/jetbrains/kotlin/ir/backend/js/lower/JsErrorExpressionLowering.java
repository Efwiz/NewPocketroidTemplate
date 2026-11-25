package org.jetbrains.kotlin.ir.backend.js.lower;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.ErrorExpressionLowering;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: JsErrorExpressionLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\rH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsErrorExpressionLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/ErrorExpressionLowering;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "errorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "stringType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "buildThrowError", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, MediaTrack.ROLE_DESCRIPTION, "", "transformErrorExpression", "expression", "nodeKind", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsErrorExpressionLowering extends ErrorExpressionLowering {
    private final IrSimpleFunctionSymbol errorSymbol;
    private final IrType stringType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsErrorExpressionLowering(JsIrBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.stringType = context.getIrBuiltIns().getNothingType();
        this.errorSymbol = context.getErrorCodeSymbol();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.ErrorExpressionLowering
    public IrExpression transformErrorExpression(IrExpression expression, String nodeKind) {
        String str;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(nodeKind, "nodeKind");
        IrErrorExpression irErrorExpression = expression instanceof IrErrorExpression ? (IrErrorExpression) expression : null;
        if (irErrorExpression != null && (str = nodeKind + ": " + irErrorExpression.getDescription()) != null) {
            nodeKind = str;
        }
        return buildThrowError(expression, nodeKind);
    }

    private final IrExpression buildThrowError(IrExpression element, String description) {
        if (this.errorSymbol == null) {
            throw new IllegalArgumentException("Should be non-null if errors are allowed".toString());
        }
        IrCallImpl irCallImpl = new IrCallImpl(element.getStartOffset(), element.getEndOffset(), getNothingType(), this.errorSymbol, 0, 1, null, null);
        irCallImpl.putValueArgument(0, IrConstImpl.INSTANCE.string(irCallImpl.getStartOffset(), irCallImpl.getEndOffset(), this.stringType, description));
        return irCallImpl;
    }
}
