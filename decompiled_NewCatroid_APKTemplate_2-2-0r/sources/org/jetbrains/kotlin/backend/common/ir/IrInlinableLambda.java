package org.jetbrains.kotlin.backend.common.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;

/* JADX INFO: compiled from: IrInlineUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/IrInlinableLambda;", "Lorg/jetbrains/kotlin/backend/common/ir/IrInlinable;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "boundReceiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)V", "getBoundReceiver", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInlinableLambda extends IrInlinable {
    private final IrValueDeclaration boundReceiver;
    private final IrSimpleFunction function;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrInlinableLambda(IrSimpleFunction function, IrValueDeclaration irValueDeclaration) {
        super(null);
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
        this.boundReceiver = irValueDeclaration;
    }

    public final IrValueDeclaration getBoundReceiver() {
        return this.boundReceiver;
    }

    public final IrSimpleFunction getFunction() {
        return this.function;
    }
}
