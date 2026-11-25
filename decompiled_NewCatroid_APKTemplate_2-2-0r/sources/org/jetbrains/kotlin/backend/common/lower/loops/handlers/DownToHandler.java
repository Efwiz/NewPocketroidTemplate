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
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DownToHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016J\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0003H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/DownToHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "preferJavaLikeCounterLoop", "", "progressionElementTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionHeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "matchIterable", "convertToExclusiveLowerBound", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "progressionType", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DownToHandler implements HeaderInfoHandler<IrCall, ProgressionType> {
    private final CommonBackendContext context;
    private final boolean preferJavaLikeCounterLoop;
    private final Collection<IrType> progressionElementTypes;

    public DownToHandler(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.preferJavaLikeCounterLoop = context.getPreferJavaLikeCounterLoop();
        this.progressionElementTypes = context.getIr().getSymbols().getProgressionElementTypes();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        Collection<IrType> collection = this.progressionElementTypes;
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) owner.getValueParameters());
        if (CollectionsKt.contains(collection, irValueParameter != null ? irValueParameter.getType() : null)) {
            Collection<IrType> collection2 = this.progressionElementTypes;
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            if (CollectionsKt.contains(collection2, extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null) && Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName("kotlin.ranges.downTo"))) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public ProgressionHeaderInfo build(IrCall expression, ProgressionType data, IrSymbol scopeOwner) {
        IrExpression irExpressionConvertToExclusiveLowerBound;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrConstImpl irConstImplIrInt$default = ExpressionHelpersKt.irInt$default(declarationIrBuilderCreateIrBuilder, -1, null, 2, null);
        ProgressionDirection progressionDirection = ProgressionDirection.DECREASING;
        if (this.preferJavaLikeCounterLoop && (irExpressionConvertToExclusiveLowerBound = convertToExclusiveLowerBound(valueArgument, data)) != null) {
            return new ProgressionHeaderInfo(data, extensionReceiver, irExpressionConvertToExclusiveLowerBound, irConstImplIrInt$default, false, false, false, progressionDirection, null, valueArgument, 288, null);
        }
        return new ProgressionHeaderInfo(data, extensionReceiver, valueArgument, irConstImplIrInt$default, false, false, null, progressionDirection, null, null, 880, null);
    }

    private final IrExpression convertToExclusiveLowerBound(IrExpression irExpression, ProgressionType progressionType) {
        Long constLongValue;
        if ((progressionType instanceof UnsignedProgressionType) && (this.preferJavaLikeCounterLoop || ((constLongValue = UtilsKt.getConstLongValue(irExpression)) != null && constLongValue.longValue() == 0))) {
            return null;
        }
        IrConst<?> irConst = irExpression instanceof IrConst ? (IrConst) irExpression : null;
        if (irConst == null) {
            return null;
        }
        IrConstKind<?> kind = irConst.getKind();
        if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
            char cCharValue = IrConstKind.Char.INSTANCE.valueOf(irConst).charValue();
            return cCharValue != 0 ? IrConstImpl.INSTANCE.m7208char(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), (char) (cCharValue - 1)) : null;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
            byte bByteValue = IrConstKind.Byte.INSTANCE.valueOf(irConst).byteValue();
            return bByteValue != -128 ? IrConstImpl.INSTANCE.m7207byte(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), (byte) (bByteValue - 1)) : null;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
            short sShortValue = IrConstKind.Short.INSTANCE.valueOf(irConst).shortValue();
            return sShortValue != Short.MIN_VALUE ? IrConstImpl.INSTANCE.m7213short(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), (short) (sShortValue - 1)) : null;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
            int iIntValue = IrConstKind.Int.INSTANCE.valueOf(irConst).intValue();
            return iIntValue != Integer.MIN_VALUE ? IrConstImpl.INSTANCE.m7211int(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), iIntValue - 1) : null;
        }
        if (!Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
            return null;
        }
        long jLongValue = IrConstKind.Long.INSTANCE.valueOf(irConst).longValue();
        return jLongValue != Long.MIN_VALUE ? IrConstImpl.INSTANCE.m7212long(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), jLongValue - 1) : null;
    }
}
