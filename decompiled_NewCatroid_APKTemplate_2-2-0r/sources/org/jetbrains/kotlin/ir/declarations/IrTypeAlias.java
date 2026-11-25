package org.jetbrains.kotlin.ir.declarations;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrTypeAlias.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J5\u0010\u001a\u001a\u0002H\u001b\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u001c0\u001e2\u0006\u0010\u001f\u001a\u0002H\u001cH\u0016¢\u0006\u0002\u0010 J/\u0010!\u001a\u00020\"\"\u0004\b\u0000\u0010\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u0002H\u001c0\u001e2\u0006\u0010\u001f\u001a\u0002H\u001cH\u0016¢\u0006\u0002\u0010#J)\u0010$\u001a\u00020\"\"\u0004\b\u0000\u0010\u001c2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001c0&2\u0006\u0010\u001f\u001a\u0002H\u001cH\u0016¢\u0006\u0002\u0010'R\u001a\u0010\u0006\u001a\u00020\u00078&X§\u0004¢\u0006\f\u0012\u0004\b\b\u0010\u0005\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\u0012X¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "()V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "expandedType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getExpandedType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setExpandedType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "isActual", "", "()Z", "setActual", "(Z)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrTypeAlias extends IrDeclarationBase implements IrDeclarationWithName, IrDeclarationWithVisibility, IrTypeParametersContainer {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract TypeAliasDescriptor getDescriptor();

    public abstract IrType getExpandedType();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrTypeAliasSymbol getSymbol();

    public abstract boolean isActual();

    public abstract void setActual(boolean z);

    public abstract void setExpandedType(IrType irType);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeAlias2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getTypeParameters().iterator();
        while (it.hasNext()) {
            ((IrTypeParameter) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeParameters(TransformKt.transformIfNeeded(getTypeParameters(), transformer, data));
    }
}
