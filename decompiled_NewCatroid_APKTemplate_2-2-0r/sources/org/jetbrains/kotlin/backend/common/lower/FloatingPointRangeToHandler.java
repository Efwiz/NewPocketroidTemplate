package org.jetbrains.kotlin.backend.common.lower;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.loops.FloatingPointRangeHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: RangeContainsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÀ\u0002\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u0002H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FloatingPointRangeToHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "", "()V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/FloatingPointRangeHeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "matchIterable", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FloatingPointRangeToHandler implements HeaderInfoHandler {
    public static final FloatingPointRangeToHandler INSTANCE = new FloatingPointRangeToHandler();

    private FloatingPointRangeToHandler() {
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        IrType type;
        IrValueParameter extensionReceiverParameter;
        IrType type2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) owner.getValueParameters());
        return (irValueParameter == null || (type = irValueParameter.getType()) == null || (!IrTypePredicatesKt.isFloat(type) && !IrTypePredicatesKt.isDouble(type)) || (extensionReceiverParameter = owner.getExtensionReceiverParameter()) == null || (type2 = extensionReceiverParameter.getType()) == null || ((!IrTypePredicatesKt.isFloat(type2) && !IrTypePredicatesKt.isDouble(type2)) || !Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName(new StringBuilder("kotlin.ranges.").append(OperatorNameConventions.RANGE_TO).toString())))) ? false : true;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public FloatingPointRangeHeaderInfo build(IrCall expression, Void data, IrSymbol scopeOwner) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        return new FloatingPointRangeHeaderInfo(extensionReceiver, valueArgument);
    }
}
