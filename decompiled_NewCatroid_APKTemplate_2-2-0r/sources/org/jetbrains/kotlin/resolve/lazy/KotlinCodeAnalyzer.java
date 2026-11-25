package org.jetbrains.kotlin.resolve.lazy;

import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: loaded from: classes6.dex */
public interface KotlinCodeAnalyzer extends TopLevelDescriptorProvider {
    void forceResolveAll();

    BindingContext getBindingContext();

    ClassDescriptor getClassDescriptor(KtClassOrObject ktClassOrObject, LookupLocation lookupLocation);

    DeclarationScopeProvider getDeclarationScopeProvider();

    FileScopeProvider getFileScopeProvider();

    ModuleDescriptor getModuleDescriptor();

    PackageFragmentProvider getPackageFragmentProvider();

    DeclarationDescriptor resolveToDescriptor(KtDeclaration ktDeclaration);
}
