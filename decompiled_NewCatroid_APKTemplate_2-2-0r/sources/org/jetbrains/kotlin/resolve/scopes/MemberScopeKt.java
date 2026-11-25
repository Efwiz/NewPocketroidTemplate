package org.jetbrains.kotlin.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u0003\u001a1\u0010\u0004\u001a\u00070\u0005¢\u0006\u0002\b\u0006*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nH\u0086\bø\u0001\u0000\u001a3\u0010\r\u001a\t\u0018\u00010\u000e¢\u0006\u0002\b\u0006*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nH\u0086\bø\u0001\u0000\u001a\u0018\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0010\u001a0\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\f0\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0017"}, d2 = {"computeAllNames", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "findFirstFunction", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "name", "", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "", "findFirstVariable", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "flatMapClassifierNamesOrNull", "", "getDescriptorsFiltered", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MemberScopeKt {
    public static final Set<Name> computeAllNames(MemberScope memberScope) {
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        Set<Name> classifierNames = memberScope.getClassifierNames();
        if (classifierNames == null) {
            return null;
        }
        Set<Name> mutableSet = CollectionsKt.toMutableSet(memberScope.getFunctionNames());
        mutableSet.addAll(memberScope.getVariableNames());
        mutableSet.addAll(classifierNames);
        return mutableSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SimpleFunctionDescriptor findFirstFunction(MemberScope memberScope, String name2, Function1<? super CallableMemberDescriptor, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        for (Object obj : memberScope.getContributedFunctions(nameIdentifier, NoLookupLocation.FROM_BACKEND)) {
            if (predicate.invoke(obj).booleanValue()) {
                return (SimpleFunctionDescriptor) obj;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static final PropertyDescriptor findFirstVariable(MemberScope memberScope, String name2, Function1<? super CallableMemberDescriptor, Boolean> predicate) {
        Object next;
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Iterator<T> it = memberScope.getContributedVariables(nameIdentifier, NoLookupLocation.FROM_BACKEND).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (predicate.invoke(next).booleanValue()) {
                return (PropertyDescriptor) next;
            }
        }
        next = null;
        return (PropertyDescriptor) next;
    }

    public static final Set<Name> flatMapClassifierNamesOrNull(Iterable<? extends MemberScope> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        HashSet hashSet = new HashSet();
        Iterator<? extends MemberScope> it = iterable.iterator();
        while (it.hasNext()) {
            Set<Name> classifierNames = it.next().getClassifierNames();
            if (classifierNames == null) {
                hashSet = null;
                break;
            }
            CollectionsKt.addAll(hashSet, classifierNames);
        }
        return hashSet;
    }

    public static /* synthetic */ Collection getDescriptorsFiltered$default(MemberScope memberScope, DescriptorKindFilter descriptorKindFilter, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            descriptorKindFilter = DescriptorKindFilter.ALL;
        }
        if ((i & 2) != 0) {
            function1 = MemberScope.INSTANCE.getALL_NAME_FILTER();
        }
        return getDescriptorsFiltered(memberScope, descriptorKindFilter, function1);
    }

    public static final Collection<DeclarationDescriptor> getDescriptorsFiltered(MemberScope memberScope, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (kindFilter.getKindMask() == 0) {
            return CollectionsKt.emptyList();
        }
        Collection<DeclarationDescriptor> contributedDescriptors = memberScope.getContributedDescriptors(kindFilter, nameFilter);
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedDescriptors) {
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
}
