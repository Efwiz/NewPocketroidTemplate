package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;

/* JADX INFO: compiled from: HeaderProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderProcessor;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "headerInfoBuilder", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;", "scopeOwnerSymbol", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;Lkotlin/jvm/functions/Function0;)V", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "extractHeader", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HeaderProcessor {
    private final CommonBackendContext context;
    private final HeaderInfoBuilder headerInfoBuilder;
    private final Function0<IrSymbol> scopeOwnerSymbol;
    private final Symbols symbols;

    /* JADX WARN: Multi-variable type inference failed */
    public HeaderProcessor(CommonBackendContext context, HeaderInfoBuilder headerInfoBuilder, Function0<? extends IrSymbol> scopeOwnerSymbol) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(headerInfoBuilder, "headerInfoBuilder");
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        this.context = context;
        this.headerInfoBuilder = headerInfoBuilder;
        this.scopeOwnerSymbol = scopeOwnerSymbol;
        this.symbols = context.getIr().getSymbols();
    }

    public final ForLoopHeader extractHeader(IrVariable variable) {
        IrExpression dispatchReceiver;
        HeaderInfo headerInfo;
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.areEqual(variable.getOrigin(), IrDeclarationOrigin.FOR_LOOP_ITERATOR.INSTANCE);
        if (!IrTypeUtilsKt.isSubtypeOfClass(variable.getType(), this.symbols.getIterator())) {
            return null;
        }
        IrExpression initializer = variable.getInitializer();
        IrCall irCall = initializer instanceof IrCall ? (IrCall) initializer : null;
        if (irCall == null) {
            dispatchReceiver = null;
        } else if (irCall.getExtensionReceiver() != null) {
            dispatchReceiver = irCall.getExtensionReceiver();
        } else {
            dispatchReceiver = irCall.getDispatchReceiver();
        }
        if (dispatchReceiver == null || (headerInfo = (HeaderInfo) dispatchReceiver.accept(this.headerInfoBuilder, irCall)) == null) {
            return null;
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, this.scopeOwnerSymbol.invoke(), variable.getStartOffset(), variable.getEndOffset());
        if (headerInfo instanceof IndexedGetHeaderInfo) {
            return new IndexedGetLoopHeader((IndexedGetHeaderInfo) headerInfo, declarationIrBuilderCreateIrBuilder, this.context);
        }
        if (headerInfo instanceof ProgressionHeaderInfo) {
            return new ProgressionLoopHeader((ProgressionHeaderInfo) headerInfo, declarationIrBuilderCreateIrBuilder, this.context);
        }
        if (headerInfo instanceof WithIndexHeaderInfo) {
            return new WithIndexLoopHeader((WithIndexHeaderInfo) headerInfo, declarationIrBuilderCreateIrBuilder, this.context);
        }
        if (headerInfo instanceof IterableHeaderInfo) {
            return new IterableLoopHeader((IterableHeaderInfo) headerInfo);
        }
        if (headerInfo instanceof FloatingPointRangeHeaderInfo ? true : headerInfo instanceof ComparableRangeInfo) {
            throw new IllegalStateException(("Unexpected " + Reflection.getOrCreateKotlinClass(headerInfo.getClass()).getSimpleName() + " for loops").toString());
        }
        throw new NoWhenBranchMatchedException();
    }
}
