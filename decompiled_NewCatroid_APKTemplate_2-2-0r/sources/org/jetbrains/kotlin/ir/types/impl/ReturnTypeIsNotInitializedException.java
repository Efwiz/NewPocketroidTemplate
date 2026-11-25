package org.jetbrains.kotlin.ir.types.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/ReturnTypeIsNotInitializedException;", "Ljava/lang/IllegalStateException;", "Lkotlin/IllegalStateException;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnTypeIsNotInitializedException extends IllegalStateException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReturnTypeIsNotInitializedException(IrFunction function) {
        super("Return type is not initialized for function '" + function.getName() + '\'');
        Intrinsics.checkNotNullParameter(function, "function");
    }
}
