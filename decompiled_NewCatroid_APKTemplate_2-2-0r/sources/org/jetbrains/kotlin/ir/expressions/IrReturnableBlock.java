package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrReturnableBlock.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrReturnTarget;", "()V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrReturnableBlock extends IrBlock implements IrReturnTarget, IrSymbolOwner {
    @Override // org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrReturnableBlockSymbol getSymbol();
}
