package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.NestedHeaderInfoBuilderForWithIndex;
import org.jetbrains.kotlin.backend.common.lower.loops.WithIndexHeaderInfo;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;

/* JADX INFO: compiled from: WithIndexHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ$\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/WithIndexHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "visitor", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NestedHeaderInfoBuilderForWithIndex;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/loops/NestedHeaderInfoBuilderForWithIndex;)V", "supportsUnsignedArrays", "", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "matchIterable", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WithIndexHandler implements HeaderInfoHandler {
    private final CommonBackendContext context;
    private final boolean supportsUnsignedArrays;
    private final NestedHeaderInfoBuilderForWithIndex visitor;

    public WithIndexHandler(CommonBackendContext context, NestedHeaderInfoBuilderForWithIndex visitor) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        this.context = context;
        this.visitor = visitor;
        this.supportsUnsignedArrays = context.getOptimizeLoopsOverUnsignedArrays();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        IrValueParameter extensionReceiverParameter;
        IrType type;
        IrValueParameter extensionReceiverParameter2;
        IrType type2;
        IrValueParameter extensionReceiverParameter3;
        IrType type3;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        if (!owner.getValueParameters().isEmpty() || !Intrinsics.areEqual(owner.getName().asString(), "withIndex")) {
            return false;
        }
        String strAsString = AdditionalIrUtilsKt.getKotlinFqName(owner).asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode != -1726442312) {
            if (iHashCode != -1507077786) {
                if (iHashCode != 831102243 || !strAsString.equals("kotlin.sequences.withIndex") || (extensionReceiverParameter3 = owner.getExtensionReceiverParameter()) == null || (type3 = extensionReceiverParameter3.getType()) == null || !IrTypePredicatesKt.isSequence(type3)) {
                    return false;
                }
            } else {
                if (!strAsString.equals("kotlin.collections.withIndex") || (extensionReceiverParameter2 = owner.getExtensionReceiverParameter()) == null || (type2 = extensionReceiverParameter2.getType()) == null) {
                    return false;
                }
                if (!IrTypePredicatesKt.isArray(type2) && !IrTypeUtilsKt.isPrimitiveArray(type2) && !IrTypePredicatesKt.isIterable(type2) && (!this.supportsUnsignedArrays || !IrTypeUtilsKt.isUnsignedArray(type2))) {
                    return false;
                }
            }
        } else if (!strAsString.equals("kotlin.text.withIndex") || (extensionReceiverParameter = owner.getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null || !org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(type, this.context.getIr().getSymbols().getCharSequence())) {
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public HeaderInfo build(IrCall expression, Void data, IrSymbol scopeOwner) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        HeaderInfo headerInfo = (HeaderInfo) extensionReceiver.accept(this.visitor, null);
        if (headerInfo == null) {
            return null;
        }
        boolean z = headerInfo instanceof WithIndexHeaderInfo;
        return new WithIndexHeaderInfo(headerInfo);
    }
}
