package org.jetbrains.kotlin.resolve.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: compiled from: LazyPackageMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nH\u0016J*\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0014J\u001e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0019H\u0014J\u001e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0019H\u0014J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0014J\u0010\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0014J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0018\u0010%\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020)H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyPackageMemberScope;", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/AbstractLazyMemberScope;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "resolveSession", "Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "declarationProvider", "thisPackage", "(Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "getClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getFunctionNames", "getNonDeclaredClasses", "", "name", "result", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getNonDeclaredFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getNonDeclaredProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getScopeForInitializerResolution", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getScopeForMemberDeclarationResolution", "getVariableNames", "recordLookup", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyPackageMemberScope extends AbstractLazyMemberScope<PackageFragmentDescriptor, PackageMemberDeclarationProvider> {
    private final ResolveSession resolveSession;

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredFunctions(Name name2, Set<SimpleFunctionDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredProperties(Name name2, Set<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public LazyPackageMemberScope(ResolveSession resolveSession, PackageMemberDeclarationProvider declarationProvider, PackageFragmentDescriptor thisPackage) {
        Intrinsics.checkNotNullParameter(resolveSession, "resolveSession");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(thisPackage, "thisPackage");
        BindingTrace trace = resolveSession.getTrace();
        Intrinsics.checkNotNullExpressionValue(trace, "<get-trace>(...)");
        super(resolveSession, declarationProvider, thisPackage, trace, null, 16, null);
        this.resolveSession = resolveSession;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return computeDescriptorsFromDeclaredElements(kindFilter, nameFilter, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected LexicalScope getScopeForMemberDeclarationResolution(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FileScopeProvider fileScopeProvider = this.resolveSession.getFileScopeProvider();
        KtFile containingKtFile = declaration.getContainingKtFile();
        Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
        return fileScopeProvider.getFileResolutionScope(containingKtFile);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected LexicalScope getScopeForInitializerResolution(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getScopeForMemberDeclarationResolution(declaration);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredClasses(Name name2, Set<ClassDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        getC().getSyntheticResolveExtension().generateSyntheticClasses(getThisDescriptor(), name2, getC(), getDeclarationProvider(), result);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(getC().getLookupTracker(), location, getThisDescriptor(), name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return getDeclarationProvider().getDeclarationNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return getDeclarationProvider().getDeclarationNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return getDeclarationProvider().getDeclarationNames();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    public String toString() {
        return "lazy scope for package " + getThisDescriptor().getShortName();
    }
}
