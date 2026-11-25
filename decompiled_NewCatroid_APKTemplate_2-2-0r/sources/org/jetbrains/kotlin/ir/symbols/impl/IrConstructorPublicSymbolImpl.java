package org.jetbrains.kotlin.ir.symbols.impl;

import com.reandroid.apkeditor.Options;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: IrPublicSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0019\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrConstructorPublicSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindablePublicSymbolBase;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", Options.TYPE_SIG, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstructorPublicSymbolImpl extends IrBindablePublicSymbolBase<ClassConstructorDescriptor, IrConstructor> implements IrConstructorSymbol {
    public /* synthetic */ IrConstructorPublicSymbolImpl(IdSignature idSignature, ClassConstructorDescriptor classConstructorDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(idSignature, (i & 2) != 0 ? null : classConstructorDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrPublicSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ FunctionDescriptor getDescriptor() {
        return (FunctionDescriptor) getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrBindablePublicSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrFunction getOwner() {
        return (IrFunction) getOwner();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrBindablePublicSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrReturnTarget getOwner() {
        return (IrReturnTarget) getOwner();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrConstructorPublicSymbolImpl(IdSignature sig, ClassConstructorDescriptor classConstructorDescriptor) {
        super(sig, classConstructorDescriptor);
        Intrinsics.checkNotNullParameter(sig, "sig");
    }
}
