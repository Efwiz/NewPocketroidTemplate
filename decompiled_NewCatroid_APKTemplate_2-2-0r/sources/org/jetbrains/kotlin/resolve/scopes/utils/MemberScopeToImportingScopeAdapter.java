package org.jetbrains.kotlin.resolve.scopes.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeKt;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: ScopeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J2\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000e0\u001c2\u0006\u0010\u001d\u001a\u00020\u000eH\u0016J#\u0010\u001e\u001a\r\u0012\t\u0012\u00070\u001f¢\u0006\u0002\b 0\u00172\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0013\u001a\u00020\fH\u0016J#\u0010#\u001a\r\u0012\t\u0012\u00070$¢\u0006\u0002\b 0\u00172\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020,H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/utils/MemberScopeToImportingScopeAdapter;", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "parent", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "(Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;)V", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "computeImportedNames", "", "Lorg/jetbrains/kotlin/name/Name;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "changeNamesForAliased", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getContributedPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "hashCode", "", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MemberScopeToImportingScopeAdapter implements ImportingScope {
    private final MemberScope memberScope;
    private final ImportingScope parent;

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    /* JADX INFO: renamed from: getContributedPackage */
    public PackageViewDescriptor mo7443getContributedPackage(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return null;
    }

    public MemberScopeToImportingScopeAdapter(ImportingScope importingScope, MemberScope memberScope) {
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        this.parent = importingScope;
        this.memberScope = memberScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        return ImportingScope.DefaultImpls.definitelyDoesNotContainName(this, name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return ImportingScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    public final MemberScope getMemberScope() {
        return this.memberScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public ImportingScope getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
        ImportingScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.memberScope.getContributedDescriptors(kindFilter, nameFilter);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.memberScope.mo7465getContributedClassifier(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.memberScope.getContributedVariables(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.memberScope.getContributedFunctions(name2, location);
    }

    public boolean equals(Object other) {
        return (other instanceof MemberScopeToImportingScopeAdapter) && Intrinsics.areEqual(((MemberScopeToImportingScopeAdapter) other).memberScope, this.memberScope);
    }

    public int hashCode() {
        return this.memberScope.hashCode();
    }

    public String toString() {
        return getClass().getSimpleName() + " for " + this.memberScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Set<Name> computeImportedNames() {
        return MemberScopeKt.computeAllNames(this.memberScope);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName());
        p.pushIndent();
        MemberScope memberScope = this.memberScope;
        Printer printerWithholdIndentOnce = p.withholdIndentOnce();
        Intrinsics.checkNotNullExpressionValue(printerWithholdIndentOnce, "withholdIndentOnce(...)");
        memberScope.mo6105printScopeStructure(printerWithholdIndentOnce);
        p.popIndent();
        p.println("}");
    }
}
