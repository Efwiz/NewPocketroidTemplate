package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrPropertyWithLateBinding.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "()V", "isBound", "", "()Z", "acquireSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrPropertyWithLateBinding extends IrProperty {
    public abstract IrPropertyWithLateBinding acquireSymbol(IrPropertySymbol symbol);

    public abstract boolean isBound();
}
