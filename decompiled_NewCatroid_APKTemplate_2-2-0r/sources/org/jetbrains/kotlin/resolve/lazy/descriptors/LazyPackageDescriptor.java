package org.jetbrains.kotlin.resolve.lazy.descriptors;

import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: loaded from: classes6.dex */
public class LazyPackageDescriptor extends PackageFragmentDescriptorImpl implements LazyEntity {
    private final PackageMemberDeclarationProvider declarationProvider;
    private final MemberScope memberScope;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "fqName";
        } else if (i == 2) {
            objArr[0] = "resolveSession";
        } else if (i == 3) {
            objArr[0] = "declarationProvider";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyPackageDescriptor";
        } else {
            objArr[0] = "module";
        }
        if (i == 4) {
            objArr[1] = "getMemberScope";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/resolve/lazy/descriptors/LazyPackageDescriptor";
        } else {
            objArr[1] = "getDeclarationProvider";
        }
        if (i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyPackageDescriptor(ModuleDescriptor moduleDescriptor, FqName fqName, ResolveSession resolveSession, PackageMemberDeclarationProvider packageMemberDeclarationProvider) {
        super(moduleDescriptor, fqName);
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (fqName == null) {
            $$$reportNull$$$0(1);
        }
        if (resolveSession == null) {
            $$$reportNull$$$0(2);
        }
        if (packageMemberDeclarationProvider == null) {
            $$$reportNull$$$0(3);
        }
        this.declarationProvider = packageMemberDeclarationProvider;
        this.memberScope = new LazyPackageMemberScope(resolveSession, packageMemberDeclarationProvider, this);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public MemberScope getMemberScope() {
        MemberScope memberScope = this.memberScope;
        if (memberScope == null) {
            $$$reportNull$$$0(4);
        }
        return memberScope;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyEntity
    public void forceResolveAllContents() {
        ForceResolveUtil.forceResolveAllContents(this.memberScope);
    }

    public PackageMemberDeclarationProvider getDeclarationProvider() {
        PackageMemberDeclarationProvider packageMemberDeclarationProvider = this.declarationProvider;
        if (packageMemberDeclarationProvider == null) {
            $$$reportNull$$$0(5);
        }
        return packageMemberDeclarationProvider;
    }
}
