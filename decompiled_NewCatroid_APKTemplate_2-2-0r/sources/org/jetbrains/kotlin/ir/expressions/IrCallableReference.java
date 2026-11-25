package org.jetbrains.kotlin.ir.expressions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrCallableReference.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004R\u0018\u0010\u0005\u001a\u00028\u0000X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "()V", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrCallableReference<S extends IrSymbol> extends IrMemberAccessExpression<S> {
    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression, org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public abstract S getSymbol();

    public abstract void setSymbol(S s);
}
