package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.FunctionsKt;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: MemberScopeImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004H\u0016J\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016J*\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J#\u0010\u0013\u001a\r\u0012\t\u0012\u00070\u0014¢\u0006\u0002\b\u00150\f2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016J#\u0010\u0016\u001a\r\u0012\t\u0012\u00070\u0017¢\u0006\u0002\b\u00150\f2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH&¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "()V", "getClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getFunctionNames", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MemberScopeImpl implements MemberScope {
    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public abstract void mo6105printScopeStructure(Printer p);

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        return MemberScope.DefaultImpls.definitelyDoesNotContainName(this, name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return MemberScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
        MemberScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        Collection<DeclarationDescriptor> contributedDescriptors = getContributedDescriptors(DescriptorKindFilter.FUNCTIONS, FunctionsKt.alwaysTrue());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : contributedDescriptors) {
            if (obj instanceof SimpleFunctionDescriptor) {
                Name name2 = ((SimpleFunctionDescriptor) obj).getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                linkedHashSet.add(name2);
            }
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        Collection<DeclarationDescriptor> contributedDescriptors = getContributedDescriptors(DescriptorKindFilter.VARIABLES, FunctionsKt.alwaysTrue());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : contributedDescriptors) {
            if (obj instanceof SimpleFunctionDescriptor) {
                Name name2 = ((SimpleFunctionDescriptor) obj).getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                linkedHashSet.add(name2);
            }
        }
        return linkedHashSet;
    }
}
