package org.jetbrains.kotlin.resolve.scopes;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Scopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0004J*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0016J2\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u000eH\u0016R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/BaseImportingScope;", "Lorg/jetbrains/kotlin/resolve/scopes/BaseHierarchicalScope;", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "parent", "(Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;)V", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "changeNamesForAliased", "getContributedPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "name", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseImportingScope extends BaseHierarchicalScope implements ImportingScope {
    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    /* JADX INFO: renamed from: getContributedPackage */
    public PackageViewDescriptor mo7443getContributedPackage(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return null;
    }

    public BaseImportingScope(ImportingScope importingScope) {
        super(importingScope);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public ImportingScope getParent() {
        return (ImportingScope) super.getParent();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return getContributedDescriptors(kindFilter, nameFilter, false);
    }

    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.emptyList();
    }
}
