package org.jetbrains.kotlin.ir.declarations;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFunction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u00072\u00020\b2\u00020\tB\u0005¢\u0006\u0002\u0010\nJ/\u0010<\u001a\u00020=\"\u0004\b\u0000\u0010>2\u0012\u0010?\u001a\u000e\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u0002H>0@2\u0006\u0010A\u001a\u0002H>H\u0016¢\u0006\u0002\u0010BJ)\u0010C\u001a\u00020=\"\u0004\b\u0000\u0010>2\f\u0010D\u001a\b\u0012\u0004\u0012\u0002H>0E2\u0006\u0010A\u001a\u0002H>H\u0016¢\u0006\u0002\u0010FR\u001a\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\u0012X¦\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u00188&X§\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\n\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u001dX¦\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u0004\u0018\u00010\u001dX¦\u000e¢\u0006\f\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010!R\u0018\u0010%\u001a\u00020&X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010'\"\u0004\b(\u0010)R\u0018\u0010*\u001a\u00020&X¦\u000e¢\u0006\f\u001a\u0004\b*\u0010'\"\u0004\b+\u0010)R\u0018\u0010,\u001a\u00020-X¦\u000e¢\u0006\f\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u001e\u00106\u001a\b\u0012\u0004\u0012\u00020\u001d07X¦\u000e¢\u0006\f\u001a\u0004\b8\u00109\"\u0004\b:\u0010;¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrPossiblyExternalDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/declarations/IrReturnTarget;", "Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "()V", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "contextReceiverParametersCount", "", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "dispatchReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "isExpect", "", "()Z", "setExpect", "(Z)V", "isInline", "setInline", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "valueParameters", "", "getValueParameters", "()Ljava/util/List;", "setValueParameters", "(Ljava/util/List;)V", "acceptChildren", "", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrFunction extends IrDeclarationBase implements IrDeclarationParent, IrDeclarationWithVisibility, IrMemberWithContainerSource, IrMetadataSourceOwner, IrPossiblyExternalDeclaration, IrReturnTarget, IrSymbolOwner, IrTypeParametersContainer {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrBody getBody();

    public abstract int getContextReceiverParametersCount();

    public abstract FunctionDescriptor getDescriptor();

    public abstract IrValueParameter getDispatchReceiverParameter();

    public abstract IrValueParameter getExtensionReceiverParameter();

    public abstract IrType getReturnType();

    public abstract IrFunctionSymbol getSymbol();

    public abstract List<IrValueParameter> getValueParameters();

    public abstract boolean isExpect();

    public abstract boolean isInline();

    public abstract void setBody(IrBody irBody);

    public abstract void setContextReceiverParametersCount(int i);

    public abstract void setDispatchReceiverParameter(IrValueParameter irValueParameter);

    public abstract void setExpect(boolean z);

    public abstract void setExtensionReceiverParameter(IrValueParameter irValueParameter);

    public abstract void setInline(boolean z);

    public abstract void setReturnType(IrType irType);

    public abstract void setValueParameters(List<? extends IrValueParameter> list);

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getTypeParameters().iterator();
        while (it.hasNext()) {
            ((IrTypeParameter) it.next()).accept(visitor, data);
        }
        IrValueParameter dispatchReceiverParameter = getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            dispatchReceiverParameter.accept(visitor, data);
        }
        IrValueParameter extensionReceiverParameter = getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            extensionReceiverParameter.accept(visitor, data);
        }
        Iterator<T> it2 = getValueParameters().iterator();
        while (it2.hasNext()) {
            ((IrValueParameter) it2.next()).accept(visitor, data);
        }
        IrBody body = getBody();
        if (body != null) {
            body.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeParameters(TransformKt.transformIfNeeded(getTypeParameters(), transformer, data));
        IrValueParameter dispatchReceiverParameter = getDispatchReceiverParameter();
        setDispatchReceiverParameter(dispatchReceiverParameter != null ? dispatchReceiverParameter.transform((IrElementTransformer) transformer, (Object) data) : null);
        IrValueParameter extensionReceiverParameter = getExtensionReceiverParameter();
        setExtensionReceiverParameter(extensionReceiverParameter != null ? extensionReceiverParameter.transform((IrElementTransformer) transformer, (Object) data) : null);
        setValueParameters(TransformKt.transformIfNeeded(getValueParameters(), transformer, data));
        IrBody body = getBody();
        setBody(body != null ? body.transform((IrElementTransformer) transformer, (Object) data) : null);
    }
}
