package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.properties.ReadWriteProperty;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeserializableClass;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AbstractIrLazyFunction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0006\u0010\u0007\u001a\u00020\u0005R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/AbstractIrLazyFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunctionBase;", "()V", "isDeserializationEnabled", "", "()Z", "tryLoadIr", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractIrLazyFunction extends IrSimpleFunction implements IrLazyFunctionBase {
    public abstract boolean isDeserializationEnabled();

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public Lazy<IrFunction> createInitialSignatureFunction() {
        return IrLazyFunctionBase.DefaultImpls.createInitialSignatureFunction(this);
    }

    public ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return IrLazyFunctionBase.DefaultImpls.createLazyAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return IrLazyFunctionBase.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrValueParameter createReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor, boolean z) {
        return IrLazyFunctionBase.DefaultImpls.createReceiverParameter(this, receiverParameterDescriptor, z);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrType createReturnType() {
        return IrLazyFunctionBase.DefaultImpls.createReturnType(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public List<IrValueParameter> createValueParameters() {
        return IrLazyFunctionBase.DefaultImpls.createValueParameters(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return IrLazyFunctionBase.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    public IrFactory getFactory() {
        return IrLazyFunctionBase.DefaultImpls.getFactory(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrDeclaration getTopLevelDeclaration() {
        return IrLazyFunctionBase.DefaultImpls.getTopLevelDeclaration(this);
    }

    public IrDeclarationParent lazyParent() {
        return IrLazyFunctionBase.DefaultImpls.lazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return IrLazyFunctionBase.DefaultImpls.toIrType(this, kotlinType);
    }

    public final boolean tryLoadIr() {
        if (!getIsInline() || getIsFakeOverride() || !isDeserializationEnabled()) {
            return false;
        }
        IrDeclaration topLevelDeclaration = getTopLevelDeclaration();
        DeserializableClass deserializableClass = topLevelDeclaration instanceof DeserializableClass ? (DeserializableClass) topLevelDeclaration : null;
        if (deserializableClass != null) {
            return deserializableClass.loadIr();
        }
        return false;
    }
}
