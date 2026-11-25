package org.jetbrains.kotlin.ir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;

/* JADX INFO: compiled from: IrPrivateSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0011\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrAnonymousInitializerSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindableSymbolBase;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "irClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrAnonymousInitializerSymbolImpl extends IrBindableSymbolBase<ClassDescriptor, IrAnonymousInitializer> implements IrAnonymousInitializerSymbol {
    /* JADX WARN: Multi-variable type inference failed */
    public IrAnonymousInitializerSymbolImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ IrAnonymousInitializerSymbolImpl(ClassDescriptor classDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : classDescriptor);
    }

    public IrAnonymousInitializerSymbolImpl(ClassDescriptor classDescriptor) {
        super(classDescriptor);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrAnonymousInitializerSymbolImpl(IrClassSymbol irClassSymbol) {
        this(irClassSymbol.getDescriptor());
        Intrinsics.checkNotNullParameter(irClassSymbol, "irClassSymbol");
    }
}
