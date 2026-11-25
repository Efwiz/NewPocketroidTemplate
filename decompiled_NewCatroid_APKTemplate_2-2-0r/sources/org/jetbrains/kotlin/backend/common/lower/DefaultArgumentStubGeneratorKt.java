package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;

/* JADX INFO: compiled from: DefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"isMovedReceiver", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefaultArgumentStubGeneratorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isMovedReceiver(IrValueParameter irValueParameter) {
        return Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE) || Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER.INSTANCE);
    }
}
