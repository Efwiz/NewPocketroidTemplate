package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.ArrayIterationHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.CharSequenceIterationHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.DefaultIterableHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.DefaultProgressionHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.DefaultSequenceHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.ReversedHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.StringIterationHandler;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR(\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e0\tX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/NestedHeaderInfoBuilderForWithIndex;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "scopeOwnerSymbol", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lkotlin/jvm/functions/Function0;)V", "callHandlers", "", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/ReversedHandler;", "getCallHandlers", "()Ljava/util/List;", "expressionHandlers", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "", "getExpressionHandlers", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NestedHeaderInfoBuilderForWithIndex extends HeaderInfoBuilder {
    private final List<ReversedHandler> callHandlers;
    private final List<HeaderInfoHandler> expressionHandlers;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedHeaderInfoBuilderForWithIndex(CommonBackendContext context, Function0<? extends IrSymbol> scopeOwnerSymbol) {
        super(context, scopeOwnerSymbol, false, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        this.callHandlers = CollectionsKt.listOf(new ReversedHandler(context, this));
        this.expressionHandlers = CollectionsKt.listOf((Object[]) new HeaderInfoHandler[]{new ArrayIterationHandler(context), new DefaultProgressionHandler(context, false, 2, null), new StringIterationHandler(context), new CharSequenceIterationHandler(context, false, 2, null), new DefaultIterableHandler(context), new DefaultSequenceHandler(context)});
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder
    protected List<ReversedHandler> getCallHandlers() {
        return this.callHandlers;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder
    protected List<HeaderInfoHandler> getExpressionHandlers() {
        return this.expressionHandlers;
    }
}
