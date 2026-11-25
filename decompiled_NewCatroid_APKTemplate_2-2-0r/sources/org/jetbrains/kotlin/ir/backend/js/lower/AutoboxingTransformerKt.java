package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;

/* JADX INFO: compiled from: AutoboxingTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0082\u0010¨\u0006\u0005"}, d2 = {"isGetUnit", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AutoboxingTransformerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isGetUnit(IrExpression irExpression, IrBuiltIns irBuiltIns) {
        while (irExpression instanceof IrContainerExpression) {
            IrStatement irStatement = (IrStatement) CollectionsKt.lastOrNull((List) ((IrContainerExpression) irExpression).getStatements());
            if (!(irStatement instanceof IrExpression)) {
                return false;
            }
            irExpression = (IrExpression) irStatement;
        }
        if (irExpression instanceof IrConstructorCall) {
            return Intrinsics.areEqual(irExpression.getType(), irBuiltIns.getUnitType());
        }
        if (irExpression instanceof IrGetObjectValue) {
            return Intrinsics.areEqual(((IrGetObjectValue) irExpression).getSymbol(), irBuiltIns.getUnitClass());
        }
        return false;
    }
}
