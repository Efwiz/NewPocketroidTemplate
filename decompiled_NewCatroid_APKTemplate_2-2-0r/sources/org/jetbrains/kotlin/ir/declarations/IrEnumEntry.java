package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrEnumEntry.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0019\u001a\u0002H\u001a\"\u0004\b\u0000\u0010\u001a\"\u0004\b\u0001\u0010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001b0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010\u001fJ/\u0010 \u001a\u00020!\"\u0004\b\u0000\u0010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u0002H\u001b0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010\"J)\u0010#\u001a\u00020!\"\u0004\b\u0000\u0010\u001b2\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u001b0%2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010&R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000b8&X§\u0004¢\u0006\f\u0012\u0004\b\f\u0010\u0003\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "()V", "correspondingClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getCorrespondingClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "setCorrespondingClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "initializerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "getInitializerExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setInitializerExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrEnumEntry extends IrDeclarationBase implements IrDeclarationWithName {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrClass getCorrespondingClass();

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract ClassDescriptor getDescriptor();

    public abstract IrExpressionBody getInitializerExpression();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrEnumEntrySymbol getSymbol();

    public abstract void setCorrespondingClass(IrClass irClass);

    public abstract void setInitializerExpression(IrExpressionBody irExpressionBody);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitEnumEntry2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        IrExpressionBody initializerExpression = getInitializerExpression();
        if (initializerExpression != null) {
            initializerExpression.accept(visitor, data);
        }
        IrClass correspondingClass = getCorrespondingClass();
        if (correspondingClass != null) {
            correspondingClass.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrExpressionBody initializerExpression = getInitializerExpression();
        setInitializerExpression(initializerExpression != null ? initializerExpression.transform((IrElementTransformer) transformer, (Object) data) : null);
        IrClass correspondingClass = getCorrespondingClass();
        setCorrespondingClass((IrClass) (correspondingClass != null ? correspondingClass.transform(transformer, data) : null));
    }
}
