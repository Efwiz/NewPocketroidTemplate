package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;

/* JADX INFO: compiled from: NullDescriptorsRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0005\u001a\u00020\u0007H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0005\u001a\u00020\rH\u0016J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0005\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0005\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0005\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/NullDescriptorsRemapper;", "Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;", "()V", "remapDeclaredClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor", "remapDeclaredConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "remapDeclaredEnumEntry", "remapDeclaredField", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "remapDeclaredProperty", "remapDeclaredSimpleFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "remapDeclaredTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "remapDeclaredValueParameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "remapDeclaredVariable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullDescriptorsRemapper implements DescriptorsRemapper {
    public static final NullDescriptorsRemapper INSTANCE = new NullDescriptorsRemapper();

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public ClassDescriptor remapDeclaredClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public ClassConstructorDescriptor remapDeclaredConstructor(ClassConstructorDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public ClassDescriptor remapDeclaredEnumEntry(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public PropertyDescriptor remapDeclaredField(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public PropertyDescriptor remapDeclaredProperty(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public FunctionDescriptor remapDeclaredSimpleFunction(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public TypeParameterDescriptor remapDeclaredTypeParameter(TypeParameterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public ParameterDescriptor remapDeclaredValueParameter(ParameterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public VariableDescriptor remapDeclaredVariable(VariableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    private NullDescriptorsRemapper() {
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public PackageFragmentDescriptor remapDeclaredExternalPackageFragment(PackageFragmentDescriptor packageFragmentDescriptor) {
        return DescriptorsRemapper.DefaultImpls.remapDeclaredExternalPackageFragment(this, packageFragmentDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public PackageFragmentDescriptor remapDeclaredFilePackageFragment(PackageFragmentDescriptor packageFragmentDescriptor) {
        return DescriptorsRemapper.DefaultImpls.remapDeclaredFilePackageFragment(this, packageFragmentDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public VariableDescriptorWithAccessors remapDeclaredLocalDelegatedProperty(VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
        return DescriptorsRemapper.DefaultImpls.remapDeclaredLocalDelegatedProperty(this, variableDescriptorWithAccessors);
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public ScriptDescriptor remapDeclaredScript(ScriptDescriptor scriptDescriptor) {
        return DescriptorsRemapper.DefaultImpls.remapDeclaredScript(this, scriptDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
    public TypeAliasDescriptor remapDeclaredTypeAlias(TypeAliasDescriptor typeAliasDescriptor) {
        return DescriptorsRemapper.DefaultImpls.remapDeclaredTypeAlias(this, typeAliasDescriptor);
    }
}
