package org.jetbrains.kotlin.ir.expressions;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrConstructorCall.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u0016\"\u0004\b\u0001\u0010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010\u001bR\u0018\u0010\u0003\u001a\u00020\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\nX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\u00020\u0010X¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "()V", "constructorTypeArgumentsCount", "", "getConstructorTypeArgumentsCount", "()I", "setConstructorTypeArgumentsCount", "(I)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "setSource", "(Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstructorCall extends IrFunctionAccessExpression {
    public abstract int getConstructorTypeArgumentsCount();

    public abstract SourceElement getSource();

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression, org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public abstract IrConstructorSymbol getSymbol();

    public abstract void setConstructorTypeArgumentsCount(int i);

    public abstract void setSource(SourceElement sourceElement);

    public abstract void setSymbol(IrConstructorSymbol irConstructorSymbol);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConstructorCall(this, data);
    }
}
