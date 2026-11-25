package org.jetbrains.kotlin.descriptors.impl;

import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;

/* JADX INFO: loaded from: classes4.dex */
public class DeclarationDescriptorVisitorEmptyBodies<R, D> implements DeclarationDescriptorVisitor<R, D> {
    public R visitDeclarationDescriptor(DeclarationDescriptor declarationDescriptor, D d) {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitVariableDescriptor(VariableDescriptor variableDescriptor, D d) {
        return visitDeclarationDescriptor(variableDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitFunctionDescriptor(FunctionDescriptor functionDescriptor, D d) {
        return visitDeclarationDescriptor(functionDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, D d) {
        return visitDeclarationDescriptor(typeParameterDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, D d) {
        return visitDeclarationDescriptor(packageFragmentDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, D d) {
        return visitDeclarationDescriptor(packageViewDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitClassDescriptor(ClassDescriptor classDescriptor, D d) {
        return visitDeclarationDescriptor(classDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, D d) {
        return visitDeclarationDescriptor(typeAliasDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitModuleDeclaration(ModuleDescriptor moduleDescriptor, D d) {
        return visitDeclarationDescriptor(moduleDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, D d) {
        return visitFunctionDescriptor(constructorDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitScriptDescriptor(ScriptDescriptor scriptDescriptor, D d) {
        return visitClassDescriptor(scriptDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, D d) {
        return visitVariableDescriptor(propertyDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, D d) {
        return visitVariableDescriptor(valueParameterDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, D d) {
        return visitFunctionDescriptor(propertyGetterDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, D d) {
        return visitFunctionDescriptor(propertySetterDescriptor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public R visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, D d) {
        return visitDeclarationDescriptor(receiverParameterDescriptor, d);
    }
}
