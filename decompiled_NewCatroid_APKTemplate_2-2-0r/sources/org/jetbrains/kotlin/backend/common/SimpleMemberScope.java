package org.jetbrains.kotlin.backend.common;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: SimpleMemberScope.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001a\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J*\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00140\u0013H\u0016J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/SimpleMemberScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "members", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Ljava/util/List;)V", "getMembers", "()Ljava/util/List;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleMemberScope extends MemberScopeImpl {
    private final List<DeclarationDescriptor> members;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleMemberScope(List<? extends DeclarationDescriptor> members) {
        Intrinsics.checkNotNullParameter(members, "members");
        this.members = members;
    }

    public final List<DeclarationDescriptor> getMembers() {
        return this.members;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<DeclarationDescriptor> list = this.members;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof ClassifierDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (Intrinsics.areEqual(((ClassifierDescriptor) obj2).getShortName(), name2)) {
                arrayList2.add(obj2);
            }
        }
        return (ClassifierDescriptor) CollectionUtilKt.atMostOne(arrayList2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<DeclarationDescriptor> list = this.members;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof PropertyDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (Intrinsics.areEqual(((PropertyDescriptor) obj2).getShortName(), name2)) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        List<DeclarationDescriptor> list = this.members;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof SimpleFunctionDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (Intrinsics.areEqual(((SimpleFunctionDescriptor) obj2).getShortName(), name2)) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        List<DeclarationDescriptor> list = this.members;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
            if (kindFilter.accepts(declarationDescriptor)) {
                Name name2 = declarationDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (nameFilter.invoke(name2).booleanValue()) {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure, reason: merged with bridge method [inline-methods] */
    public Void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }
}
