package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: DelegatePackageMemberDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fH\u0016J\u001a\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015H\u0016J*\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fH\u0016J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0016J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0016R\u001a\u0010\u0002\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0003¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/DelegatePackageMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;)V", "getDelegate", "()Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "setDelegate", "containsFile", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getAllDeclaredSubPackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "getClassOrObjectDeclarations", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo;", "name", "getDeclarationNames", "", "getDeclarations", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "getDestructuringDeclarationsEntries", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "getFunctionDeclarations", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getPackageFiles", "getPropertyDeclarations", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getScriptDeclarations", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "getTypeAliasDeclarations", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DelegatePackageMemberDeclarationProvider implements PackageMemberDeclarationProvider {
    private PackageMemberDeclarationProvider delegate;

    public DelegatePackageMemberDeclarationProvider(PackageMemberDeclarationProvider delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public final PackageMemberDeclarationProvider getDelegate() {
        return this.delegate;
    }

    public final void setDelegate(PackageMemberDeclarationProvider packageMemberDeclarationProvider) {
        Intrinsics.checkNotNullParameter(packageMemberDeclarationProvider, "<set-?>");
        this.delegate = packageMemberDeclarationProvider;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public Collection<FqName> getAllDeclaredSubPackages(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.delegate.getAllDeclaredSubPackages(nameFilter);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public Collection<KtFile> getPackageFiles() {
        return this.delegate.getPackageFiles();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public boolean containsFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.delegate.containsFile(file);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtDeclaration> getDeclarations(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.delegate.getDeclarations(kindFilter, nameFilter);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtNamedFunction> getFunctionDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.getFunctionDeclarations(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtProperty> getPropertyDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.getPropertyDeclarations(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtDestructuringDeclarationEntry> getDestructuringDeclarationsEntries(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.getDestructuringDeclarationsEntries(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtClassOrObjectInfo<?>> getClassOrObjectDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.getClassOrObjectDeclarations(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtScriptInfo> getScriptDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.getScriptDeclarations(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtTypeAlias> getTypeAliasDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.getTypeAliasDeclarations(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Set<Name> getDeclarationNames() {
        return this.delegate.getDeclarationNames();
    }
}
