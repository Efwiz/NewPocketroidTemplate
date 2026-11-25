package org.jetbrains.kotlin.ir.symbols.impl;

import com.reandroid.apkeditor.Options;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: IrPublicSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0019\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrTypeParameterPublicSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindablePublicSymbolBase;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", Options.TYPE_SIG, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeParameterPublicSymbolImpl extends IrBindablePublicSymbolBase<TypeParameterDescriptor, IrTypeParameter> implements IrTypeParameterSymbol {
    public /* synthetic */ IrTypeParameterPublicSymbolImpl(IdSignature idSignature, TypeParameterDescriptor typeParameterDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(idSignature, (i & 2) != 0 ? null : typeParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrPublicSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ ClassifierDescriptor getDescriptor() {
        return (ClassifierDescriptor) getDescriptor();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrTypeParameterPublicSymbolImpl(IdSignature sig, TypeParameterDescriptor typeParameterDescriptor) {
        super(sig, typeParameterDescriptor);
        Intrinsics.checkNotNullParameter(sig, "sig");
    }
}
