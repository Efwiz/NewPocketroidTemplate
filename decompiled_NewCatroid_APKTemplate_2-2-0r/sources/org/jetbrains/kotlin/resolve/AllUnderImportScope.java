package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.BaseImportingScope;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeKt;
import org.jetbrains.kotlin.utils.Printer;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: AllUnderImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 $2\u00020\u0001:\u0001$B/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\nJ\u0010\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0016J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J2\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00052\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00052\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AllUnderImportScope;", "Lorg/jetbrains/kotlin/resolve/scopes/BaseImportingScope;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "scope1", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "scope2", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Ljava/util/Collection;Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;)V", "excludedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "computeImportedNames", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "changeNamesForAliased", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AllUnderImportScope extends BaseImportingScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Set<Name> excludedNames;
    private final MemberScope scope1;
    private final MemberScope scope2;

    public /* synthetic */ AllUnderImportScope(DeclarationDescriptor declarationDescriptor, Collection collection, MemberScope memberScope, MemberScope memberScope2, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, collection, memberScope, memberScope2);
    }

    private AllUnderImportScope(DeclarationDescriptor declarationDescriptor, Collection<FqName> collection, MemberScope memberScope, MemberScope memberScope2) {
        Set<Name> set;
        super(null);
        this.scope1 = memberScope;
        this.scope2 = memberScope2;
        if (collection.isEmpty()) {
            set = SetsKt.emptySet();
        } else {
            FqName fqNameSafe = DescriptorUtils.getFqNameSafe(declarationDescriptor);
            Intrinsics.checkNotNullExpressionValue(fqNameSafe, "getFqNameSafe(...)");
            ArrayList arrayList = new ArrayList();
            for (FqName fqName : collection) {
                Name nameShortName = Intrinsics.areEqual(fqName.parent(), fqNameSafe) ? fqName.shortName() : null;
                if (nameShortName != null) {
                    arrayList.add(nameShortName);
                }
            }
            set = CollectionsKt.toSet(arrayList);
        }
        this.excludedNames = set;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Set<Name> computeImportedNames() {
        Set<Name> setComputeAllNames;
        Set<Name> setComputeAllNames2 = MemberScopeKt.computeAllNames(this.scope1);
        MemberScope memberScope = this.scope2;
        if (memberScope == null) {
            return setComputeAllNames2;
        }
        if (setComputeAllNames2 == null || (setComputeAllNames = MemberScopeKt.computeAllNames(memberScope)) == null) {
            return null;
        }
        if (setComputeAllNames2.isEmpty()) {
            return setComputeAllNames;
        }
        Set<Name> mutableSet = CollectionsKt.toMutableSet(setComputeAllNames2);
        mutableSet.addAll(setComputeAllNames);
        return mutableSet;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseImportingScope, org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, final Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (!this.excludedNames.isEmpty()) {
            nameFilter = new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.AllUnderImportScope$getContributedDescriptors$nameFilterToUse$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Name it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(!this.this$0.excludedNames.contains(it) && nameFilter.invoke(it).booleanValue());
                }
            };
        }
        DescriptorKindFilter descriptorKindFilterWithoutKinds = kindFilter.withoutKinds(DescriptorKindFilter.INSTANCE.getPACKAGES_MASK());
        SmartList smartList = new SmartList();
        MemberScope memberScope = this.scope1;
        MemberScope memberScope2 = this.scope2;
        if (memberScope != null) {
            for (Object obj : memberScope.getContributedDescriptors(descriptorKindFilterWithoutKinds, nameFilter)) {
                if (!(((DeclarationDescriptor) obj) instanceof PackageViewDescriptor)) {
                    smartList.add(obj);
                }
            }
        }
        if (memberScope2 != null) {
            for (Object obj2 : memberScope2.getContributedDescriptors(descriptorKindFilterWithoutKinds, nameFilter)) {
                if (!(((DeclarationDescriptor) obj2) instanceof PackageViewDescriptor)) {
                    smartList.add(obj2);
                }
            }
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.excludedNames.contains(name2)) {
            return null;
        }
        ClassifierDescriptor contributedClassifier = this.scope1.mo7465getContributedClassifier(name2, location);
        MemberScope memberScope = this.scope2;
        ClassifierDescriptor contributedClassifier2 = memberScope != null ? memberScope.mo7465getContributedClassifier(name2, location) : null;
        if (contributedClassifier == null || contributedClassifier2 == null) {
            return contributedClassifier == null ? contributedClassifier2 : contributedClassifier;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.excludedNames.contains(name2)) {
            return CollectionsKt.emptyList();
        }
        MemberScope memberScope = this.scope1;
        MemberScope memberScope2 = this.scope2;
        Collection contributedVariables = memberScope != null ? memberScope.getContributedVariables(name2, location) : CollectionsKt.emptyList();
        if (memberScope2 == null) {
            return contributedVariables;
        }
        Collection contributedVariables2 = memberScope2.getContributedVariables(name2, location);
        if (contributedVariables.isEmpty()) {
            return contributedVariables2;
        }
        List mutableList = CollectionsKt.toMutableList(contributedVariables);
        mutableList.addAll(contributedVariables2);
        return mutableList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.excludedNames.contains(name2)) {
            return CollectionsKt.emptyList();
        }
        MemberScope memberScope = this.scope1;
        MemberScope memberScope2 = this.scope2;
        Collection contributedFunctions = memberScope != null ? memberScope.getContributedFunctions(name2, location) : CollectionsKt.emptyList();
        if (memberScope2 == null) {
            return contributedFunctions;
        }
        Collection contributedFunctions2 = memberScope2.getContributedFunctions(name2, location);
        if (contributedFunctions.isEmpty()) {
            return contributedFunctions2;
        }
        List mutableList = CollectionsKt.toMutableList(contributedFunctions);
        mutableList.addAll(contributedFunctions2);
        return mutableList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.scope1.mo7479recordLookup(name2, location);
        MemberScope memberScope = this.scope2;
        if (memberScope != null) {
            memberScope.mo7479recordLookup(name2, location);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName());
    }

    /* JADX INFO: compiled from: AllUnderImportScope.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AllUnderImportScope$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ImportingScope create(DeclarationDescriptor descriptor, Collection<FqName> excludedImportNames) {
            MemberScope memberScope;
            ImportingScope.Empty allUnderImportScope;
            MemberScope unsubstitutedInnerClassesScope;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
            boolean z = descriptor instanceof ClassDescriptor;
            if (z) {
                memberScope = ((ClassDescriptor) descriptor).getStaticScope();
            } else {
                boolean z2 = descriptor instanceof PackageViewDescriptor;
                memberScope = ((PackageViewDescriptor) descriptor).getMemberScope();
            }
            MemberScope memberScope2 = memberScope;
            Intrinsics.checkNotNull(memberScope2);
            MemberScope memberScope3 = (!z || (unsubstitutedInnerClassesScope = ((ClassDescriptor) descriptor).getUnsubstitutedInnerClassesScope()) == MemberScope.Empty.INSTANCE) ? null : unsubstitutedInnerClassesScope;
            if (memberScope2 == MemberScope.Empty.INSTANCE) {
                if (memberScope3 == null || memberScope3 == MemberScope.Empty.INSTANCE) {
                    allUnderImportScope = ImportingScope.Empty.INSTANCE;
                } else {
                    allUnderImportScope = new AllUnderImportScope(descriptor, excludedImportNames, memberScope3, null, null);
                }
                return allUnderImportScope;
            }
            return new AllUnderImportScope(descriptor, excludedImportNames, memberScope2, memberScope3, null);
        }
    }
}
