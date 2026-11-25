package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: ProgressionType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ULongProgressionType;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/UnsignedProgressionType;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "allowUnsignedBounds", "", "(Lorg/jetbrains/kotlin/backend/common/ir/Symbols;Z)V", "zeroStepExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ULongProgressionType extends UnsignedProgressionType {
    /* JADX WARN: Illegal instructions before constructor call */
    public ULongProgressionType(Symbols symbols, boolean z) {
        IrClassSymbol uLong;
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        if (z) {
            uLong = symbols.getULong();
            Intrinsics.checkNotNull(uLong);
        } else {
            uLong = symbols.getLong();
        }
        IrClass owner = uLong.getOwner();
        IrClass owner2 = symbols.getLong().getOwner();
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getProgressionLastElementByReturnType = symbols.getGetProgressionLastElementByReturnType();
        IrClassSymbol uLong2 = symbols.getULong();
        Intrinsics.checkNotNull(uLong2);
        super(symbols, owner, owner2, 0L, -1L, getProgressionLastElementByReturnType.get(uLong2), IrTypesKt.getDefaultType(symbols.getULong()), (IrSimpleFunctionSymbol) MapsKt.getValue(symbols.getToULongByExtensionReceiver(), symbols.getLong()));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType
    public IrConstImpl<Long> zeroStepExpression(DeclarationIrBuilder declarationIrBuilder) {
        Intrinsics.checkNotNullParameter(declarationIrBuilder, "<this>");
        return ExpressionHelpersKt.irLong$default(declarationIrBuilder, 0L, null, 2, null);
    }
}
