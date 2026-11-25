package org.jetbrains.kotlin.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: Scopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0014J\u0010\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H&J*\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J6\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH&J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0007H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "parent", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "computeImportedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "changeNamesForAliased", "getContributedPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "name", "Empty", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ImportingScope extends HierarchicalScope {
    Set<Name> computeImportedNames();

    Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased);

    /* JADX INFO: renamed from: getContributedPackage */
    PackageViewDescriptor mo7443getContributedPackage(Name name2);

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    /* JADX INFO: renamed from: getParent */
    ImportingScope get$newParent();

    /* JADX INFO: compiled from: Scopes.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean definitelyDoesNotContainName(ImportingScope importingScope, Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return HierarchicalScope.DefaultImpls.definitelyDoesNotContainName(importingScope, name2);
        }

        public static DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(ImportingScope importingScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return HierarchicalScope.DefaultImpls.getContributedClassifierIncludeDeprecated(importingScope, name2, location);
        }

        public static void recordLookup(ImportingScope importingScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            HierarchicalScope.DefaultImpls.recordLookup(importingScope, name2, location);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Collection getContributedDescriptors$default(ImportingScope importingScope, DescriptorKindFilter descriptorKindFilter, Function1 function1, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getContributedDescriptors");
        }
        if ((i & 1) != 0) {
            descriptorKindFilter = DescriptorKindFilter.ALL;
        }
        if ((i & 2) != 0) {
            function1 = MemberScope.INSTANCE.getALL_NAME_FILTER();
        }
        return importingScope.getContributedDescriptors(descriptorKindFilter, function1, z);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    default Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return getContributedDescriptors(kindFilter, nameFilter, false);
    }

    /* JADX INFO: compiled from: Scopes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope$Empty;", "Lorg/jetbrains/kotlin/resolve/scopes/BaseImportingScope;", "()V", "computeImportedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "definitelyDoesNotContainName", "", "name", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends BaseImportingScope {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public boolean definitelyDoesNotContainName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return true;
        }

        private Empty() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public void printStructure(Printer p) {
            Intrinsics.checkNotNullParameter(p, "p");
            p.println("ImportingScope.Empty");
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
        public Set<Name> computeImportedNames() {
            return SetsKt.emptySet();
        }
    }
}
