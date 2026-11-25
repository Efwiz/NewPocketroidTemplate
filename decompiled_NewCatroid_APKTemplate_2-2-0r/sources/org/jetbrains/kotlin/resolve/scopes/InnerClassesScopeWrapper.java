package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: InnerClassesScopeWrapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\fH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b0\u0016H\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/InnerClassesScopeWrapper;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "workerScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "(Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;)V", "getWorkerScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "definitelyDoesNotContainName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getFunctionNames", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InnerClassesScopeWrapper extends MemberScopeImpl {
    private final MemberScope workerScope;

    public InnerClassesScopeWrapper(MemberScope workerScope) {
        Intrinsics.checkNotNullParameter(workerScope, "workerScope");
        this.workerScope = workerScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public /* bridge */ /* synthetic */ Collection getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1 function1) {
        return getContributedDescriptors(descriptorKindFilter, (Function1<? super Name, Boolean>) function1);
    }

    public final MemberScope getWorkerScope() {
        return this.workerScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor contributedClassifier = this.workerScope.mo7465getContributedClassifier(name2, location);
        ClassifierDescriptor classifierDescriptor = null;
        if (contributedClassifier != null) {
            ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
            if (classDescriptor != null) {
                classifierDescriptor = classDescriptor;
            } else {
                classifierDescriptor = (ClassifierDescriptorWithTypeParameters) (contributedClassifier instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) contributedClassifier : null);
            }
        }
        return classifierDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public List<ClassifierDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        DescriptorKindFilter descriptorKindFilterRestrictedToKindsOrNull = kindFilter.restrictedToKindsOrNull(DescriptorKindFilter.INSTANCE.getCLASSIFIERS_MASK());
        if (descriptorKindFilterRestrictedToKindsOrNull == null) {
            return CollectionsKt.emptyList();
        }
        Collection<DeclarationDescriptor> contributedDescriptors = this.workerScope.getContributedDescriptors(descriptorKindFilterRestrictedToKindsOrNull, nameFilter);
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedDescriptors) {
            if (obj instanceof ClassifierDescriptorWithTypeParameters) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println("InnerClassesScopeWrapper for scope:");
        this.workerScope.mo6105printScopeStructure(p);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return this.workerScope.getFunctionNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return this.workerScope.getVariableNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return this.workerScope.getClassifierNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.workerScope.definitelyDoesNotContainName(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.workerScope.mo7479recordLookup(name2, location);
    }

    public String toString() {
        return "Classes from " + this.workerScope;
    }
}
