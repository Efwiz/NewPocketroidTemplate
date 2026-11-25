package org.jetbrains.kotlin.builtins.functions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionInterfacePackageFragment;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FunctionInterfaceFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\f\u001a\u00020\u000bH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionInterfacePackageFragmentImpl;", "Lorg/jetbrains/kotlin/builtins/FunctionInterfacePackageFragment;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageFragmentDescriptorImpl;", "classDescriptorFactory", "Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "name", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/name/FqName;)V", "memberScope", "Lorg/jetbrains/kotlin/builtins/functions/FunctionInterfaceMemberScope;", "getMemberScope", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionInterfacePackageFragmentImpl extends PackageFragmentDescriptorImpl implements FunctionInterfacePackageFragment {
    private final FunctionInterfaceMemberScope memberScope;

    @Override // org.jetbrains.kotlin.builtins.FunctionInterfacePackageFragment, org.jetbrains.kotlin.builtins.BuiltInsPackageFragment
    public boolean isFallback() {
        return FunctionInterfacePackageFragment.DefaultImpls.isFallback(this);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionInterfacePackageFragmentImpl(ClassDescriptorFactory classDescriptorFactory, ModuleDescriptor module, FqName name2) {
        super(module, name2);
        Intrinsics.checkNotNullParameter(classDescriptorFactory, "classDescriptorFactory");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.memberScope = new FunctionInterfaceMemberScope(classDescriptorFactory, getFqName());
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public FunctionInterfaceMemberScope getMemberScope() {
        return this.memberScope;
    }
}
