package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;

/* JADX INFO: compiled from: IrDescriptorBasedFunctionFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class IrDescriptorBasedFunctionFactory$getPackageFragment$1 extends FunctionReference implements Function1<PackageFragmentDescriptor, IrExternalPackageFragment> {
    IrDescriptorBasedFunctionFactory$getPackageFragment$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "declareExternalPackageFragmentIfNotExists(Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "declareExternalPackageFragmentIfNotExists";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(DescriptorSymbolTableExtension.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final IrExternalPackageFragment invoke(PackageFragmentDescriptor p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((DescriptorSymbolTableExtension) this.receiver).declareExternalPackageFragmentIfNotExists(p0);
    }
}
