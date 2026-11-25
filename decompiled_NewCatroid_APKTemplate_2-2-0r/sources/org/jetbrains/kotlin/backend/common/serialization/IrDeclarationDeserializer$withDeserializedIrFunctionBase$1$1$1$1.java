package org.jetbrains.kotlin.backend.common.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBase;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: IrDeclarationDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005*\u0002H\u0004H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "invoke", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class IrDeclarationDeserializer$withDeserializedIrFunctionBase$1$1$1$1<T> extends Lambda implements Function1<T, Unit> {
    final /* synthetic */ IrFunctionBase $proto;
    final /* synthetic */ IrDeclarationDeserializer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrDeclarationDeserializer$withDeserializedIrFunctionBase$1$1$1$1(IrDeclarationDeserializer irDeclarationDeserializer, IrFunctionBase irFunctionBase) {
        super(1);
        this.this$0 = irDeclarationDeserializer;
        this.$proto = irFunctionBase;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((IrFunction) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Incorrect types in method signature: (TT;)V */
    public final void invoke(IrFunction withBodyGuard) {
        IrValueParameter irValueParameterDeserializeIrValueParameter$default;
        IrValueParameter irValueParameterDeserializeIrValueParameter$default2;
        Intrinsics.checkNotNullParameter(withBodyGuard, "$this$withBodyGuard");
        IrDeclarationDeserializer irDeclarationDeserializer = this.this$0;
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter> valueParameterList = this.$proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        withBodyGuard.setValueParameters(irDeclarationDeserializer.deserializeValueParameters(valueParameterList));
        if (this.$proto.hasDispatchReceiver()) {
            IrDeclarationDeserializer irDeclarationDeserializer2 = this.this$0;
            org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter dispatchReceiver = this.$proto.getDispatchReceiver();
            Intrinsics.checkNotNullExpressionValue(dispatchReceiver, "getDispatchReceiver(...)");
            irValueParameterDeserializeIrValueParameter$default = IrDeclarationDeserializer.deserializeIrValueParameter$default(irDeclarationDeserializer2, dispatchReceiver, -1, false, 4, null);
        } else {
            irValueParameterDeserializeIrValueParameter$default = null;
        }
        withBodyGuard.setDispatchReceiverParameter(irValueParameterDeserializeIrValueParameter$default);
        if (this.$proto.hasExtensionReceiver()) {
            IrDeclarationDeserializer irDeclarationDeserializer3 = this.this$0;
            org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter extensionReceiver = this.$proto.getExtensionReceiver();
            Intrinsics.checkNotNullExpressionValue(extensionReceiver, "getExtensionReceiver(...)");
            irValueParameterDeserializeIrValueParameter$default2 = IrDeclarationDeserializer.deserializeIrValueParameter$default(irDeclarationDeserializer3, extensionReceiver, -1, false, 4, null);
        } else {
            irValueParameterDeserializeIrValueParameter$default2 = null;
        }
        withBodyGuard.setExtensionReceiverParameter(irValueParameterDeserializeIrValueParameter$default2);
        withBodyGuard.setContextReceiverParametersCount(this.$proto.hasContextReceiverParametersCount() ? this.$proto.getContextReceiverParametersCount() : 0);
        withBodyGuard.setBody(this.$proto.hasBody() ? (IrBody) this.this$0.deserializeStatementBody(this.$proto.getBody()) : null);
    }
}
