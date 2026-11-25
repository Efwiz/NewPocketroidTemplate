package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLocalDelegatedProperty.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J5\u0010)\u001a\u0002H*\"\u0004\b\u0000\u0010*\"\u0004\b\u0001\u0010+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H+0-2\u0006\u0010.\u001a\u0002H+H\u0016¢\u0006\u0002\u0010/J/\u00100\u001a\u000201\"\u0004\b\u0000\u0010+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002H+0-2\u0006\u0010.\u001a\u0002H+H\u0016¢\u0006\u0002\u00102J)\u00103\u001a\u000201\"\u0004\b\u0000\u0010+2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H+052\u0006\u0010.\u001a\u0002H+H\u0016¢\u0006\u0002\u00106R\u0018\u0010\u0006\u001a\u00020\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\r8&X§\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0005\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\u0012X¦\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\u00020\u0018X¦\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u0012X¦\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u0014\"\u0004\b\u001e\u0010\u0016R\u0012\u0010\u001f\u001a\u00020 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0018\u0010#\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "()V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getDelegate", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "setDelegate", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setGetter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "isVar", "", "()Z", "setVar", "(Z)V", "setter", "getSetter", "setSetter", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrLocalDelegatedProperty extends IrDeclarationBase implements IrDeclarationWithName, IrMetadataSourceOwner, IrSymbolOwner {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrVariable getDelegate();

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract VariableDescriptorWithAccessors getDescriptor();

    public abstract IrSimpleFunction getGetter();

    public abstract IrSimpleFunction getSetter();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrLocalDelegatedPropertySymbol getSymbol();

    public abstract IrType getType();

    public abstract boolean isVar();

    public abstract void setDelegate(IrVariable irVariable);

    public abstract void setGetter(IrSimpleFunction irSimpleFunction);

    public abstract void setSetter(IrSimpleFunction irSimpleFunction);

    public abstract void setType(IrType irType);

    public abstract void setVar(boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitLocalDelegatedProperty2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getDelegate().accept(visitor, data);
        getGetter().accept(visitor, data);
        IrSimpleFunction setter = getSetter();
        if (setter != null) {
            setter.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrElement irElementTransform = getDelegate().transform(transformer, data);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
        setDelegate((IrVariable) irElementTransform);
        IrElement irElementTransform2 = getGetter().transform(transformer, data);
        Intrinsics.checkNotNull(irElementTransform2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        setGetter((IrSimpleFunction) irElementTransform2);
        IrSimpleFunction setter = getSetter();
        setSetter((IrSimpleFunction) (setter != null ? setter.transform(transformer, data) : null));
    }
}
