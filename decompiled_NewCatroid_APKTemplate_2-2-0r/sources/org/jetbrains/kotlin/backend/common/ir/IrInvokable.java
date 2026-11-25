package org.jetbrains.kotlin.backend.common.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;

/* JADX INFO: compiled from: IrInlineUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/IrInvokable;", "Lorg/jetbrains/kotlin/backend/common/ir/IrInlinable;", "invokable", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)V", "getInvokable", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInvokable extends IrInlinable {
    private final IrValueDeclaration invokable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrInvokable(IrValueDeclaration invokable) {
        super(null);
        Intrinsics.checkNotNullParameter(invokable, "invokable");
        this.invokable = invokable;
    }

    public final IrValueDeclaration getInvokable() {
        return this.invokable;
    }
}
