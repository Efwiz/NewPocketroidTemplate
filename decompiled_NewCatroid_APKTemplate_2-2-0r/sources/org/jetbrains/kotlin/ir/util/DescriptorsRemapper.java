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

/* JADX INFO: compiled from: DescriptorsRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u001cJ\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0004\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\tH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0004\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0004\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0004\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0004\u001a\u00020\u0015H\u0016J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0004\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0004\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0004\u001a\u00020\u001bH\u0016¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;", "", "remapDeclaredClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor", "remapDeclaredConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "remapDeclaredEnumEntry", "remapDeclaredExternalPackageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "remapDeclaredField", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "remapDeclaredFilePackageFragment", "remapDeclaredLocalDelegatedProperty", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "remapDeclaredProperty", "remapDeclaredScript", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "remapDeclaredSimpleFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "remapDeclaredTypeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "remapDeclaredTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "remapDeclaredValueParameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "remapDeclaredVariable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Default", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DescriptorsRemapper {

    /* JADX INFO: compiled from: DescriptorsRemapper.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static ClassDescriptor remapDeclaredClass(DescriptorsRemapper descriptorsRemapper, ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static ClassConstructorDescriptor remapDeclaredConstructor(DescriptorsRemapper descriptorsRemapper, ClassConstructorDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static ClassDescriptor remapDeclaredEnumEntry(DescriptorsRemapper descriptorsRemapper, ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static PackageFragmentDescriptor remapDeclaredExternalPackageFragment(DescriptorsRemapper descriptorsRemapper, PackageFragmentDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static PropertyDescriptor remapDeclaredField(DescriptorsRemapper descriptorsRemapper, PropertyDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static PackageFragmentDescriptor remapDeclaredFilePackageFragment(DescriptorsRemapper descriptorsRemapper, PackageFragmentDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static VariableDescriptorWithAccessors remapDeclaredLocalDelegatedProperty(DescriptorsRemapper descriptorsRemapper, VariableDescriptorWithAccessors descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static PropertyDescriptor remapDeclaredProperty(DescriptorsRemapper descriptorsRemapper, PropertyDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static ScriptDescriptor remapDeclaredScript(DescriptorsRemapper descriptorsRemapper, ScriptDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static FunctionDescriptor remapDeclaredSimpleFunction(DescriptorsRemapper descriptorsRemapper, FunctionDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static TypeAliasDescriptor remapDeclaredTypeAlias(DescriptorsRemapper descriptorsRemapper, TypeAliasDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static TypeParameterDescriptor remapDeclaredTypeParameter(DescriptorsRemapper descriptorsRemapper, TypeParameterDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static ParameterDescriptor remapDeclaredValueParameter(DescriptorsRemapper descriptorsRemapper, ParameterDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }

        public static VariableDescriptor remapDeclaredVariable(DescriptorsRemapper descriptorsRemapper, VariableDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return descriptor;
        }
    }

    ClassDescriptor remapDeclaredClass(ClassDescriptor descriptor);

    ClassConstructorDescriptor remapDeclaredConstructor(ClassConstructorDescriptor descriptor);

    ClassDescriptor remapDeclaredEnumEntry(ClassDescriptor descriptor);

    PackageFragmentDescriptor remapDeclaredExternalPackageFragment(PackageFragmentDescriptor descriptor);

    PropertyDescriptor remapDeclaredField(PropertyDescriptor descriptor);

    PackageFragmentDescriptor remapDeclaredFilePackageFragment(PackageFragmentDescriptor descriptor);

    VariableDescriptorWithAccessors remapDeclaredLocalDelegatedProperty(VariableDescriptorWithAccessors descriptor);

    PropertyDescriptor remapDeclaredProperty(PropertyDescriptor descriptor);

    ScriptDescriptor remapDeclaredScript(ScriptDescriptor descriptor);

    FunctionDescriptor remapDeclaredSimpleFunction(FunctionDescriptor descriptor);

    TypeAliasDescriptor remapDeclaredTypeAlias(TypeAliasDescriptor descriptor);

    TypeParameterDescriptor remapDeclaredTypeParameter(TypeParameterDescriptor descriptor);

    ParameterDescriptor remapDeclaredValueParameter(ParameterDescriptor descriptor);

    VariableDescriptor remapDeclaredVariable(VariableDescriptor descriptor);

    /* JADX INFO: compiled from: DescriptorsRemapper.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper$Default;", "Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements DescriptorsRemapper {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public ClassDescriptor remapDeclaredClass(ClassDescriptor classDescriptor) {
            return DefaultImpls.remapDeclaredClass(this, classDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public ClassConstructorDescriptor remapDeclaredConstructor(ClassConstructorDescriptor classConstructorDescriptor) {
            return DefaultImpls.remapDeclaredConstructor(this, classConstructorDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public ClassDescriptor remapDeclaredEnumEntry(ClassDescriptor classDescriptor) {
            return DefaultImpls.remapDeclaredEnumEntry(this, classDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public PackageFragmentDescriptor remapDeclaredExternalPackageFragment(PackageFragmentDescriptor packageFragmentDescriptor) {
            return DefaultImpls.remapDeclaredExternalPackageFragment(this, packageFragmentDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public PropertyDescriptor remapDeclaredField(PropertyDescriptor propertyDescriptor) {
            return DefaultImpls.remapDeclaredField(this, propertyDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public PackageFragmentDescriptor remapDeclaredFilePackageFragment(PackageFragmentDescriptor packageFragmentDescriptor) {
            return DefaultImpls.remapDeclaredFilePackageFragment(this, packageFragmentDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public VariableDescriptorWithAccessors remapDeclaredLocalDelegatedProperty(VariableDescriptorWithAccessors variableDescriptorWithAccessors) {
            return DefaultImpls.remapDeclaredLocalDelegatedProperty(this, variableDescriptorWithAccessors);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public PropertyDescriptor remapDeclaredProperty(PropertyDescriptor propertyDescriptor) {
            return DefaultImpls.remapDeclaredProperty(this, propertyDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public ScriptDescriptor remapDeclaredScript(ScriptDescriptor scriptDescriptor) {
            return DefaultImpls.remapDeclaredScript(this, scriptDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public FunctionDescriptor remapDeclaredSimpleFunction(FunctionDescriptor functionDescriptor) {
            return DefaultImpls.remapDeclaredSimpleFunction(this, functionDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public TypeAliasDescriptor remapDeclaredTypeAlias(TypeAliasDescriptor typeAliasDescriptor) {
            return DefaultImpls.remapDeclaredTypeAlias(this, typeAliasDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public TypeParameterDescriptor remapDeclaredTypeParameter(TypeParameterDescriptor typeParameterDescriptor) {
            return DefaultImpls.remapDeclaredTypeParameter(this, typeParameterDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public ParameterDescriptor remapDeclaredValueParameter(ParameterDescriptor parameterDescriptor) {
            return DefaultImpls.remapDeclaredValueParameter(this, parameterDescriptor);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorsRemapper
        public VariableDescriptor remapDeclaredVariable(VariableDescriptor variableDescriptor) {
            return DefaultImpls.remapDeclaredVariable(this, variableDescriptor);
        }
    }
}
