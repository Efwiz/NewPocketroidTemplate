package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.ws.WebSocketProtocol;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;

/* JADX INFO: compiled from: ProgressionType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/CharProgressionType;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "(Lorg/jetbrains/kotlin/backend/common/ir/Symbols;)V", "zeroStepExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CharProgressionType extends ProgressionType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CharProgressionType(Symbols symbols) {
        super(symbols.getChar().getOwner(), symbols.getInt().getOwner(), 0L, WebSocketProtocol.PAYLOAD_SHORT_MAX, symbols.getGetProgressionLastElementByReturnType().get(symbols.getInt()), null);
        Intrinsics.checkNotNullParameter(symbols, "symbols");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType
    public IrConstImpl<Integer> zeroStepExpression(DeclarationIrBuilder declarationIrBuilder) {
        Intrinsics.checkNotNullParameter(declarationIrBuilder, "<this>");
        return ExpressionHelpersKt.irInt$default(declarationIrBuilder, 0, null, 2, null);
    }
}
