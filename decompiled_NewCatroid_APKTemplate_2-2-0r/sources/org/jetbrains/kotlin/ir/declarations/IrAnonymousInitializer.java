package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrAnonymousInitializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0017\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u001b2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\u001dJ/\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u0002H\u00190\u001b2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010 J)\u0010!\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u00192\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00190#2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010$R\u0018\u0010\u0003\u001a\u00020\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\n8&X§\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "()V", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isStatic", "", "()Z", "setStatic", "(Z)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrAnonymousInitializer extends IrDeclarationBase {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrBlockBody getBody();

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract ClassDescriptor getDescriptor();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrAnonymousInitializerSymbol getSymbol();

    public abstract boolean isStatic();

    public abstract void setBody(IrBlockBody irBlockBody);

    public abstract void setStatic(boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnonymousInitializer2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getBody().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrBody irBodyTransform = getBody().transform((IrElementTransformer) transformer, (Object) data);
        Intrinsics.checkNotNull(irBodyTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        setBody((IrBlockBody) irBodyTransform);
    }
}
