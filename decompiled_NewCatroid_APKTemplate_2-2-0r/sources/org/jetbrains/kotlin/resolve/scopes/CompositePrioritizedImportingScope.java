package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: Scopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tH\u0016J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J2\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00112\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\r\u001a\u00020\nH\u0016J\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00112\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/CompositePrioritizedImportingScope;", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "primaryScope", "secondaryScope", "(Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "computeImportedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "changeNamesForAliased", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getContributedPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositePrioritizedImportingScope implements ImportingScope {
    private final ImportingScope primaryScope;
    private final ImportingScope secondaryScope;

    public CompositePrioritizedImportingScope(ImportingScope primaryScope, ImportingScope secondaryScope) {
        Intrinsics.checkNotNullParameter(primaryScope, "primaryScope");
        Intrinsics.checkNotNullParameter(secondaryScope, "secondaryScope");
        this.primaryScope = primaryScope;
        this.secondaryScope = secondaryScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        return ImportingScope.DefaultImpls.definitelyDoesNotContainName(this, name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return ImportingScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
        ImportingScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    /* JADX INFO: renamed from: getParent */
    public ImportingScope get$newParent() {
        ImportingScope importingScope = this.primaryScope.get$newParent();
        return importingScope == null ? this.secondaryScope.get$newParent() : importingScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    /* JADX INFO: renamed from: getContributedPackage */
    public PackageViewDescriptor mo7443getContributedPackage(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PackageViewDescriptor packageViewDescriptorMo7443getContributedPackage = this.primaryScope.mo7443getContributedPackage(name2);
        return packageViewDescriptorMo7443getContributedPackage == null ? this.secondaryScope.mo7443getContributedPackage(name2) : packageViewDescriptorMo7443getContributedPackage;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.union(this.primaryScope.getContributedDescriptors(kindFilter, nameFilter, changeNamesForAliased), this.secondaryScope.getContributedDescriptors(kindFilter, nameFilter, changeNamesForAliased));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Set<Name> computeImportedNames() {
        Set<Name> setComputeImportedNames = this.primaryScope.computeImportedNames();
        Set<Name> setComputeImportedNames2 = this.secondaryScope.computeImportedNames();
        if (setComputeImportedNames == null) {
            return setComputeImportedNames2;
        }
        Set<Name> setUnion = CollectionsKt.union(setComputeImportedNames, setComputeImportedNames2 == null ? SetsKt.emptySet() : setComputeImportedNames2);
        return setUnion == null ? setComputeImportedNames2 : setUnion;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(this.primaryScope.getClass().getSimpleName());
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor contributedClassifier = this.primaryScope.mo7465getContributedClassifier(name2, location);
        return contributedClassifier == null ? this.secondaryScope.mo7465getContributedClassifier(name2, location) : contributedClassifier;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.union(this.primaryScope.getContributedVariables(name2, location), this.secondaryScope.getContributedVariables(name2, location));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.union(this.primaryScope.getContributedFunctions(name2, location), this.secondaryScope.getContributedFunctions(name2, location));
    }
}
