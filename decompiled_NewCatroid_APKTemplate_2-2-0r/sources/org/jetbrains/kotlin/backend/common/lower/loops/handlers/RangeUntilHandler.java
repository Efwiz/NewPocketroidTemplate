package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: RangeUntilHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/RangeUntilHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "progressionElementTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "matchIterable", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeUntilHandler implements HeaderInfoHandler<IrCall, ProgressionType> {
    private final CommonBackendContext context;
    private final Collection<IrType> progressionElementTypes;

    public RangeUntilHandler(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.progressionElementTypes = context.getIr().getSymbols().getProgressionElementTypes();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        Collection<IrType> collection = this.progressionElementTypes;
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) owner.getValueParameters());
        if (CollectionsKt.contains(collection, irValueParameter != null ? irValueParameter.getType() : null) && owner.getExtensionReceiverParameter() == null) {
            Collection<IrType> collection2 = this.progressionElementTypes;
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            if (CollectionsKt.contains(collection2, dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null) && Intrinsics.areEqual(owner.getName().asString(), "rangeUntil")) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public HeaderInfo build(IrCall expression, ProgressionType data, IrSymbol scopeOwner) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        return new ProgressionHeaderInfo(data, dispatchReceiver, valueArgument, ExpressionHelpersKt.irInt$default(declarationIrBuilderCreateIrBuilder, 1, null, 2, null), false, false, false, ProgressionDirection.INCREASING, null, null, ParserBasicInformation.NUM_RULES, null);
    }
}
