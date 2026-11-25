package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrProperty.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u00032\u00020\u00052\u00020\u00062\u00020\u0007B\u0005¢\u0006\u0002\u0010\bJ5\u0010/\u001a\u0002H0\"\u0004\b\u0000\u00100\"\u0004\b\u0001\u001012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u0002H0\u0012\u0004\u0012\u0002H1032\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u00105J/\u00106\u001a\u000207\"\u0004\b\u0000\u001012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u0002H1032\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u00108J)\u00109\u001a\u000207\"\u0004\b\u0000\u001012\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H10;2\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u0010<R\u001a\u0010\t\u001a\u0004\u0018\u00010\nX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u00108&X§\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\b\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010\u001c\"\u0004\b \u0010\u001eR\u0018\u0010!\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b!\u0010\u001c\"\u0004\b\"\u0010\u001eR\u0018\u0010#\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR\u0018\u0010%\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b%\u0010\u001c\"\u0004\b&\u0010\u001eR\u0018\u0010'\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b'\u0010\u001c\"\u0004\b(\u0010\u001eR\u001a\u0010)\u001a\u0004\u0018\u00010\u0015X¦\u000e¢\u0006\f\u001a\u0004\b*\u0010\u0017\"\u0004\b+\u0010\u0019R\u0012\u0010,\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrPossiblyExternalDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;", "()V", "backingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getBackingField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setBackingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setGetter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "isConst", "", "()Z", "setConst", "(Z)V", "isDelegated", "setDelegated", "isExpect", "setExpect", "isFakeOverride", "setFakeOverride", "isLateinit", "setLateinit", "isVar", "setVar", "setter", "getSetter", "setSetter", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrProperty extends IrDeclarationBase implements IrAttributeContainer, IrMemberWithContainerSource, IrMetadataSourceOwner, IrOverridableDeclaration<IrPropertySymbol>, IrPossiblyExternalDeclaration {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrField getBackingField();

    public abstract PropertyDescriptor getDescriptor();

    public abstract IrSimpleFunction getGetter();

    public abstract IrSimpleFunction getSetter();

    public abstract IrPropertySymbol getSymbol();

    public abstract boolean isConst();

    public abstract boolean isDelegated();

    public abstract boolean isExpect();

    public abstract boolean isFakeOverride();

    public abstract boolean isLateinit();

    public abstract boolean isVar();

    public abstract void setBackingField(IrField irField);

    public abstract void setConst(boolean z);

    public abstract void setDelegated(boolean z);

    public abstract void setExpect(boolean z);

    public abstract void setFakeOverride(boolean z);

    public abstract void setGetter(IrSimpleFunction irSimpleFunction);

    public abstract void setLateinit(boolean z);

    public abstract void setSetter(IrSimpleFunction irSimpleFunction);

    public abstract void setVar(boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitProperty2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        IrField backingField = getBackingField();
        if (backingField != null) {
            backingField.accept(visitor, data);
        }
        IrSimpleFunction getter = getGetter();
        if (getter != null) {
            getter.accept(visitor, data);
        }
        IrSimpleFunction setter = getSetter();
        if (setter != null) {
            setter.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrField backingField = getBackingField();
        setBackingField((IrField) (backingField != null ? backingField.transform(transformer, data) : null));
        IrSimpleFunction getter = getGetter();
        setGetter((IrSimpleFunction) (getter != null ? getter.transform(transformer, data) : null));
        IrSimpleFunction setter = getSetter();
        setSetter((IrSimpleFunction) (setter != null ? setter.transform(transformer, data) : null));
    }
}
