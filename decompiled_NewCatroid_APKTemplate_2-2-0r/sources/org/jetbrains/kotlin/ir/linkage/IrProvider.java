package org.jetbrains.kotlin.ir.linkage;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "", "getDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrProvider {
    IrDeclaration getDeclaration(IrSymbol symbol);
}
