package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UnsignedProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: RangeToHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0002H\u0002J\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u00162\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0002H\u0016J\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u0016*\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0003H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/RangeToHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "allowedMethods", "", "", "preferJavaLikeCounterLoop", "", "progressionElementTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionHeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "convertIrCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irCall", "convertIrConst", "irConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "matchIterable", "convertToExclusiveUpperBound", "progressionType", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeToHandler implements HeaderInfoHandler<IrCall, ProgressionType> {
    private final List<String> allowedMethods;
    private final CommonBackendContext context;
    private final boolean preferJavaLikeCounterLoop;
    private final Collection<IrType> progressionElementTypes;

    public RangeToHandler(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.preferJavaLikeCounterLoop = context.getPreferJavaLikeCounterLoop();
        this.progressionElementTypes = context.getIr().getSymbols().getProgressionElementTypes();
        this.allowedMethods = CollectionsKt.listOf((Object[]) new String[]{"kotlin.ByteArray.<get-size>", "kotlin.CharArray.<get-size>", "kotlin.String.<get-length>", "kotlin.ShortArray.<get-size>", "kotlin.IntArray.<get-size>", "kotlin.LongArray.<get-size>", "kotlin.FloatArray.<get-size>", "kotlin.DoubleArray.<get-size>", "kotlin.BooleanArray.<get-size>", "kotlin.collections.List.<get-size>", "kotlin.collections.MutableList.<get-size>", "kotlin.CharSequence.<get-length>", "kotlin.collections.Set.<get-size>", "kotlin.collections.MutableSet.<get-size>", "kotlin.collections.Map.<get-size>", "kotlin.collections.MutableMap.<get-size>"});
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        Collection<IrType> collection = this.progressionElementTypes;
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) owner.getValueParameters());
        if (CollectionsKt.contains(collection, irValueParameter != null ? irValueParameter.getType() : null)) {
            Collection<IrType> collection2 = this.progressionElementTypes;
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            if (CollectionsKt.contains(collection2, dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null) && Intrinsics.areEqual(owner.getName(), OperatorNameConventions.RANGE_TO)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public ProgressionHeaderInfo build(IrCall expression, ProgressionType data, IrSymbol scopeOwner) {
        IrExpression irExpressionConvertToExclusiveUpperBound;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrConstImpl irConstImplIrInt$default = ExpressionHelpersKt.irInt$default(declarationIrBuilderCreateIrBuilder, 1, null, 2, null);
        ProgressionDirection progressionDirection = ProgressionDirection.INCREASING;
        if (this.preferJavaLikeCounterLoop && (irExpressionConvertToExclusiveUpperBound = convertToExclusiveUpperBound(valueArgument, data)) != null) {
            return new ProgressionHeaderInfo(data, dispatchReceiver, irExpressionConvertToExclusiveUpperBound, irConstImplIrInt$default, false, false, false, progressionDirection, null, valueArgument, 288, null);
        }
        return new ProgressionHeaderInfo(data, dispatchReceiver, valueArgument, irConstImplIrInt$default, false, false, null, progressionDirection, null, null, 880, null);
    }

    private final IrExpression convertToExclusiveUpperBound(IrExpression irExpression, ProgressionType progressionType) {
        Long constLongValue;
        if ((progressionType instanceof UnsignedProgressionType) && (this.preferJavaLikeCounterLoop || ((constLongValue = UtilsKt.getConstLongValue(irExpression)) != null && constLongValue.longValue() == -1))) {
            return null;
        }
        if (irExpression instanceof IrConst) {
            return convertIrConst((IrConst) irExpression);
        }
        if (irExpression instanceof IrCall) {
            return convertIrCall((IrCall) irExpression);
        }
        return null;
    }

    private static final String convertIrCall$dispatchReceiverName(IrCall irCall) {
        IrSimpleFunctionSymbol symbol;
        IrSimpleFunction owner;
        IrExpression dispatchReceiver = irCall.getDispatchReceiver();
        FqName fqNameWhenAvailable = null;
        IrCall irCall2 = dispatchReceiver instanceof IrCall ? (IrCall) dispatchReceiver : null;
        if (irCall2 != null && (symbol = irCall2.getSymbol()) != null && (owner = symbol.getOwner()) != null) {
            fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(owner);
        }
        return String.valueOf(fqNameWhenAvailable);
    }

    private final IrExpression convertIrCall(IrCall irCall) {
        if (!Intrinsics.areEqual(irCall.getOrigin(), IrStatementOrigin.MINUS.INSTANCE)) {
            return null;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        IrConst irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
        if ((irConst != null ? Intrinsics.areEqual(irConst.getValue(), (Object) 1) : false) && this.allowedMethods.contains(convertIrCall$dispatchReceiverName(irCall))) {
            return irCall.getDispatchReceiver();
        }
        return null;
    }

    private final IrExpression convertIrConst(IrConst<?> irConst) {
        int startOffset = irConst.getStartOffset();
        int endOffset = irConst.getEndOffset();
        IrType type = irConst.getType();
        IrConstKind<?> kind = irConst.getKind();
        if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
            char cCharValue = IrConstKind.Char.INSTANCE.valueOf(irConst).charValue();
            return cCharValue != 65535 ? IrConstImpl.INSTANCE.m7208char(startOffset, endOffset, type, (char) (cCharValue + 1)) : null;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
            byte bByteValue = IrConstKind.Byte.INSTANCE.valueOf(irConst).byteValue();
            return bByteValue != 127 ? IrConstImpl.INSTANCE.m7207byte(startOffset, endOffset, type, (byte) (bByteValue + 1)) : null;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
            short sShortValue = IrConstKind.Short.INSTANCE.valueOf(irConst).shortValue();
            return sShortValue != Short.MAX_VALUE ? IrConstImpl.INSTANCE.m7213short(startOffset, endOffset, type, (short) (sShortValue + 1)) : null;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
            int iIntValue = IrConstKind.Int.INSTANCE.valueOf(irConst).intValue();
            return iIntValue != Integer.MAX_VALUE ? IrConstImpl.INSTANCE.m7211int(startOffset, endOffset, type, iIntValue + 1) : null;
        }
        if (!Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
            return null;
        }
        long jLongValue = IrConstKind.Long.INSTANCE.valueOf(irConst).longValue();
        return jLongValue != Long.MAX_VALUE ? IrConstImpl.INSTANCE.m7212long(startOffset, endOffset, type, 1 + jLongValue) : null;
    }
}
