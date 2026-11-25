package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrValueParameter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010(\u001a\u0002H)\"\u0004\b\u0000\u0010)\"\u0004\b\u0001\u0010*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u0002H*0,2\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u0010.J/\u0010/\u001a\u000200\"\u0004\b\u0000\u0010*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002H*0,2\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u00101J)\u00102\u001a\u00020\u0000\"\u0004\b\u0000\u0010*2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H*042\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u00105J)\u00106\u001a\u000200\"\u0004\b\u0000\u0010*2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H*042\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u00107R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000b8&X§\u0004¢\u0006\f\u0012\u0004\b\f\u0010\u0003\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\u00020\u0010X¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\u00020\u0016X¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u0016X¦\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u0017\"\u0004\b\u001b\u0010\u0019R\u0018\u0010\u001c\u001a\u00020\u0016X¦\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u0017\"\u0004\b\u001d\u0010\u0019R\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u0004\u0018\u00010#X¦\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "()V", "defaultValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "getDefaultValue", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setDefaultValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "index", "", "getIndex", "()I", "setIndex", "(I)V", "isCrossinline", "", "()Z", "setCrossinline", "(Z)V", "isHidden", "setHidden", "isNoinline", "setNoinline", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "varargElementType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getVarargElementType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setVarargElementType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrValueParameter extends IrDeclarationBase implements IrValueDeclaration {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrExpressionBody getDefaultValue();

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract ParameterDescriptor getDescriptor();

    public abstract int getIndex();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrValueParameterSymbol getSymbol();

    public abstract IrType getVarargElementType();

    public abstract boolean isCrossinline();

    public abstract boolean isHidden();

    public abstract boolean isNoinline();

    public abstract void setCrossinline(boolean z);

    public abstract void setDefaultValue(IrExpressionBody irExpressionBody);

    public abstract void setHidden(boolean z);

    public abstract void setIndex(int i);

    public abstract void setNoinline(boolean z);

    public abstract void setVarargElementType(IrType irType);

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrElement transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitValueParameter(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> IrValueParameter transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Object objAccept = accept(transformer, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
        return (IrValueParameter) objAccept;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        IrExpressionBody defaultValue = getDefaultValue();
        if (defaultValue != null) {
            defaultValue.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrExpressionBody defaultValue = getDefaultValue();
        setDefaultValue(defaultValue != null ? defaultValue.transform((IrElementTransformer) transformer, (Object) data) : null);
    }
}
