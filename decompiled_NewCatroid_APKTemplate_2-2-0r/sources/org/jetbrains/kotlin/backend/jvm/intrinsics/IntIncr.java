package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IntIncr.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntIncr;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "isPrefix", "", "(Z)V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntIncr extends IntrinsicMethod {
    private final boolean isPrefix;

    public IntIncr(boolean z) {
        this.isPrefix = z;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression valueArgument = expression.getValueArgument(0);
        IrGetValue irGetValue = valueArgument instanceof IrGetValue ? (IrGetValue) valueArgument : null;
        if (irGetValue == null) {
            throw new IllegalStateException(("IrGetValue expected as valueArgument #0: " + DumpIrTreeKt.dump$default(expression, null, 1, null)).toString());
        }
        IrExpression valueArgument2 = expression.getValueArgument(1);
        IrConst<?> irConst = valueArgument2 instanceof IrConst ? (IrConst) valueArgument2 : null;
        if (irConst == null) {
            throw new IllegalStateException(("IrConst expected as valueArgument #1: " + DumpIrTreeKt.dump$default(expression, null, 1, null)).toString());
        }
        if (!Intrinsics.areEqual(irConst.getKind(), IrConstKind.Int.INSTANCE)) {
            throw new IllegalStateException(("Int const expected: " + DumpIrTreeKt.dump$default(irConst, null, 1, null)).toString());
        }
        int iIntValue = IrConstKind.Int.INSTANCE.valueOf(irConst).intValue();
        if (iIntValue > 127 || iIntValue < -128) {
            throw new IllegalStateException(("Int const should be in (Byte.MIN_VALUE .. Byte.MAX_VALUE): " + DumpIrTreeKt.dump$default(irConst, null, 1, null)).toString());
        }
        int index = codegen.getFrameMap().getIndex(irGetValue.getSymbol());
        if (index == -1) {
            throw new IllegalStateException(("Unmapped variable: " + RenderIrElementKt.render$default(irGetValue, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (this.isPrefix) {
            codegen.getMv().iinc(index, iIntValue);
            PromisedValueKt.materialize((PromisedValue) irGetValue.accept(codegen, data));
        } else {
            PromisedValueKt.materialize((PromisedValue) irGetValue.accept(codegen, data));
            codegen.getMv().iinc(index, iIntValue);
        }
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        return new MaterialValue(codegen, INT_TYPE, irGetValue.getType());
    }
}
