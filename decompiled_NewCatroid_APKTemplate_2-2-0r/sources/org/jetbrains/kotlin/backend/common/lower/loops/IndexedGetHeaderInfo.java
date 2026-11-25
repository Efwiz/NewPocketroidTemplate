package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/IndexedGetHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "first", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "last", "step", "canCacheLast", "", "objectVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "expressionHandler", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndexedGetIterationHandler;", "(Lorg/jetbrains/kotlin/backend/common/ir/Symbols;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;ZLorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndexedGetIterationHandler;)V", "getExpressionHandler", "()Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndexedGetIterationHandler;", "getObjectVariable", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "asReversed", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IndexedGetHeaderInfo extends NumericHeaderInfo {
    private final IndexedGetIterationHandler expressionHandler;
    private final IrVariable objectVariable;

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo
    public HeaderInfo asReversed() {
        return null;
    }

    public /* synthetic */ IndexedGetHeaderInfo(Symbols symbols, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, boolean z, IrVariable irVariable, IndexedGetIterationHandler indexedGetIterationHandler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(symbols, irExpression, irExpression2, irExpression3, (i & 16) != 0 ? true : z, irVariable, indexedGetIterationHandler);
    }

    public final IrVariable getObjectVariable() {
        return this.objectVariable;
    }

    public final IndexedGetIterationHandler getExpressionHandler() {
        return this.expressionHandler;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IndexedGetHeaderInfo(Symbols symbols, IrExpression first, IrExpression last, IrExpression step, boolean z, IrVariable objectVariable, IndexedGetIterationHandler expressionHandler) {
        super(new IntProgressionType(symbols), first, last, step, false, z, false, ProgressionDirection.INCREASING, null);
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(last, "last");
        Intrinsics.checkNotNullParameter(step, "step");
        Intrinsics.checkNotNullParameter(objectVariable, "objectVariable");
        Intrinsics.checkNotNullParameter(expressionHandler, "expressionHandler");
        this.objectVariable = objectVariable;
        this.expressionHandler = expressionHandler;
    }
}
