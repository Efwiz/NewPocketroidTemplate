package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.ArrayIndicesHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.CharSequenceIndicesHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.CollectionIndicesHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.DefaultProgressionHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.DownToHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.RangeToHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.RangeUntilHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.ReversedHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.UntilHandler;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: RangeContainsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R(\u0010\b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n0\tX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\tX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR&\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00130\n0\tX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/RangeHeaderInfoBuilder;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "scopeOwnerSymbol", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lkotlin/jvm/functions/Function0;)V", "callHandlers", "", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "", "getCallHandlers", "()Ljava/util/List;", "expressionHandlers", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/DefaultProgressionHandler;", "getExpressionHandlers", "progressionHandlers", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "getProgressionHandlers", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class RangeHeaderInfoBuilder extends HeaderInfoBuilder {
    private final List<HeaderInfoHandler> callHandlers;
    private final List<DefaultProgressionHandler> expressionHandlers;
    private final List<HeaderInfoHandler<IrCall, ProgressionType>> progressionHandlers;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RangeHeaderInfoBuilder(CommonBackendContext context, Function0<? extends IrSymbol> scopeOwnerSymbol) {
        super(context, scopeOwnerSymbol, true);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        this.progressionHandlers = CollectionsKt.listOf((Object[]) new HeaderInfoHandler[]{new CollectionIndicesHandler(context), new ArrayIndicesHandler(context), new CharSequenceIndicesHandler(context), new UntilHandler(context), new RangeUntilHandler(context), new DownToHandler(context), new RangeToHandler(context)});
        this.callHandlers = CollectionsKt.listOf((Object[]) new HeaderInfoHandler[]{FloatingPointRangeToHandler.INSTANCE, new ComparableRangeToHandler(context), new ReversedHandler(context, this)});
        this.expressionHandlers = CollectionsKt.listOf(new DefaultProgressionHandler(context, true));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder
    protected List<HeaderInfoHandler<IrCall, ProgressionType>> getProgressionHandlers() {
        return this.progressionHandlers;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder
    protected List<HeaderInfoHandler> getCallHandlers() {
        return this.callHandlers;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder
    protected List<DefaultProgressionHandler> getExpressionHandlers() {
        return this.expressionHandlers;
    }
}
